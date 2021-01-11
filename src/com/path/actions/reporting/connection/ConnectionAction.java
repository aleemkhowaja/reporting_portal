package com.path.actions.reporting.connection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.connection.ConnectionBO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_CONNECTIONSCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;

/**
 * @author: haytham.k
 * ConnectionAction.java class
 */

public class ConnectionAction extends ReportingGridBaseAction
{
   private ConnectionBO connectionBO;
   IRP_CONNECTIONSSC irpConSC = new IRP_CONNECTIONSSC();
   private IRP_CONNECTIONSCO icCO=new IRP_CONNECTIONSCO();
   private String updates;
   private String actionType = "";
   private CommonRepFuncBO commonRepFuncBO;
   private BigDecimal connId;
   private String conStatus="success";
   private String conFailedMsg="";

   public String execute() throws Exception
   {
       set_showSmartInfoBtn("false");
       return SUCCESS;
   }
   
   /**
    *  load connection grid
    */
    public String loadConnectionGrid()throws BaseException
    {
	
	try
	{
	    	copyproperties(irpConSC);
		List<IRP_CONNECTIONSVO>lst=connectionBO.retConnectionList(irpConSC);
		int lstCnt=connectionBO.retConnectionListCount(irpConSC);
		setRecords(lstCnt);
		setGridModel(lst);

	}
	catch(Exception e)
	{
		//log.error("Error in loadConnectionGrid() method in ConnectionAction");
		handleException(e, "Error in load connection grid","loadConnectionGrid.exceptionMsg._key");
	}
	return "grid";
    }
    
    /**
     * retrieve connection detail from db for the selected row
     * @return successConDetail
     * @throws Exception
     */
    public String retrieveConnData()throws Exception
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("icVO");
	    IRP_CONNECTIONSVO icVO = (IRP_CONNECTIONSVO)JSONObject.toBean(jsonModel, IRP_CONNECTIONSVO.class);
			
	    icVO =connectionBO.retrieveConnData(icVO);
	    icCO.setIrpConnectionsVO(icVO);
	    icCO.setDbConfPass(icVO.getDB_PASS());
	    icCO.setOldDbPass(icVO.getDB_PASS());
	    //String [] urlData = icVO.getURL().split("@");
	    
	    if(RepConstantsCommon.oracleDriver.equals(icVO.getDBMS()))
	    {
		String [] urlData = icVO.getURL().split("@");
		icCO.setHost(urlData[1]);
	    }
	    else if(RepConstantsCommon.sqlServerDriver.equals(icVO.getDBMS()) || RepConstantsCommon.mySQLDriver.equals(icVO.getDBMS()) )
	    {
		String [] urlData = icVO.getURL().split("//");
		icCO.setHost(urlData[1]);
	    }
	    else
	    {
		String [] urlData = icVO.getURL().split("Tds:");
		icCO.setHost(urlData[1]);
	    }
	    applyRetrieveAudit(AuditConstant.DB_CONNECTION_KEY_REF,icVO,icVO);	
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in retrieveConnData in ConnectionAction");
	    handleException(e, "Error in retrieve connection data","retrieveConnData.exceptionMsg._key");
	}
	return "successConDetail";
    }
    
    /**
     * save the changes made in the form 
     * @return grid
     */
    public String update(){
	
		try {
		    
		    	SessionCO sessionCO = returnSessionObject();
		    	IRP_CONNECTIONSVO icVO=new IRP_CONNECTIONSVO();
			AuditRefCO refCO = initAuditRefCO();
			BigDecimal connId = null;
				
			icVO.setCONN_DESC(icCO.getIrpConnectionsVO().getCONN_DESC());
			icVO.setDBMS(icCO.getIrpConnectionsVO().getDBMS());
			icVO.setUSER_ID(icCO.getIrpConnectionsVO().getUSER_ID());
			icVO.setDB_PASS(icCO.getIrpConnectionsVO().getDB_PASS());

			
			String pageRef		= get_pageRef();
			BigDecimal compCode 	= sessionCO.getCompanyCode();
			String appName   	= sessionCO.getCurrentAppName();
			String lang		= sessionCO.getLanguage();
			if(RepConstantsCommon.oracleDriver.equals(icVO.getDBMS()))
			{	
			    icVO.setURL("jdbc:oracle:thin:@"+icCO.getHost());
			}
			else if(RepConstantsCommon.mySQLDriver.equals(icVO.getDBMS()))
			{
			    icVO.setURL("jdbc:mysql://"+icCO.getHost());
			}
			else if(RepConstantsCommon.sqlServerDriver.equals(icVO.getDBMS()))
			{
			    icVO.setURL("jdbc:sqlserver://"+icCO.getHost());
			}
			else
			{
			    icVO.setURL("jdbc:sybase:Tds:"+icCO.getHost());
			}
			// if actionType =add or "" (mode save new)
			if(this.actionType.equals("add") || this.actionType.equals(""))
			{
			    //apply audit
			    refCO.setOperationType(AuditConstant.CREATED);
			    refCO.setKeyRef(AuditConstant.DB_CONNECTION_KEY_REF);
			    icVO.setAuditRefCO(refCO);
			    
			    connId = commonRepFuncBO.retCounterValue("IRP_CONNECTIONS");
			    icVO.setCONN_ID(connId);
			    icCO.setIrpConnectionsVO(icVO);
			    connectionBO.insertConDetail(icCO,pageRef,compCode,appName,lang);
			}
			// if actionType =edit (mode update)
			else if(this.actionType.equals("edit"))
			{
			    //apply audit
			    refCO.setOperationType(AuditConstant.UPDATE);
			    refCO.setKeyRef(AuditConstant.DB_CONNECTION_KEY_REF);
			    icVO.setAuditRefCO(refCO);
			    IRP_CONNECTIONSVO oldConnVO=(IRP_CONNECTIONSVO)returnAuditObject(IRP_CONNECTIONSVO.class);
			    
			    icVO.setCONN_ID(icCO.getIrpConnectionsVO().getCONN_ID());
			    icVO.setDATE_UPDATED(icCO.getIrpConnectionsVO().getDATE_UPDATED());
			    icCO.setIrpConnectionsVO(icVO);

			    connectionBO.updateConDetail(icCO,oldConnVO,pageRef,compCode,appName,lang);
			}
			
		} catch (BaseException e) {
		        icCO = new IRP_CONNECTIONSCO();
			//log.error(e, "ERROR in update in ConnectionAction");
		        handleException(e, "Error in update connection data","update.exceptionMsg._key");
		}
		return "grid";
	}
    
	
    /**
     * delete the selected connection
     * @return grid
     */
    public String deleteConnection() 
    {
	try
	{
	    int haveAppCon=0;
    	    IRP_CONNECTIONSVO icVO=new IRP_CONNECTIONSVO();
    	    icVO.setCONN_ID(connId);
    	    //apply audit
    	    AuditRefCO refCO = initAuditRefCO();
            refCO.setOperationType(AuditConstant.DELETE);
            refCO.setKeyRef(AuditConstant.DB_CONNECTION_KEY_REF);
            icVO.setAuditRefCO(refCO);
            
            //this method check if the selected connection is connected to an application to prevent delete process.
            //if 0 ->the connection is not linked to any App and the user can delete it else mean a minimum 1 App use this connection and 
            //the user can't delete it.
            haveAppCon = connectionBO.checkIfHaveAppCon(icVO);
            if(haveAppCon==0)
            {
        	connectionBO.deleteConDetail(icVO);
            }
            else
            {
        	conFailedMsg="1";
            }
	}
	catch(BaseException e)
	{
	    //log.error(e, "ERROR in deleteConnection ConnectionAction");
	    handleException(e, "Error in delete connection data","deleteConnection.exceptionMsg._key");
	}
	return "grid";
    }
    
   /**
    * this method test the connection to the db and return the connection problem in case a failed connection 
    * @return grid
    */
    public String testConnection() 
    {
	IRP_CONNECTIONSVO icVO = new IRP_CONNECTIONSVO();
	icVO.setCONN_DESC(icCO.getIrpConnectionsVO().getCONN_DESC());
	icVO.setDBMS(icCO.getIrpConnectionsVO().getDBMS());
	icVO.setUSER_ID(icCO.getIrpConnectionsVO().getUSER_ID());
	icVO.setDB_PASS(icCO.getIrpConnectionsVO().getDB_PASS());

	SessionCO sessionCO = returnSessionObject();
	String pageRef = get_pageRef();
	BigDecimal compCode = sessionCO.getCompanyCode();
	String appName = sessionCO.getCurrentAppName();
	String lang = sessionCO.getLanguage();

	if(this.actionType.equals("edit"))
	{
	    icVO.setCONN_ID(icCO.getIrpConnectionsVO().getCONN_ID());
	}

	if(RepConstantsCommon.oracleDriver.equals(icVO.getDBMS()))
	{
	    icVO.setURL("jdbc:oracle:thin:@" + icCO.getHost());
	}
	else if(RepConstantsCommon.sqlServerDriver.equals(icVO.getDBMS()))
	{
	    icVO.setURL("jdbc:sqlserver://" + icCO.getHost());
	}
	else if(RepConstantsCommon.mySQLDriver.equals(icVO.getDBMS()))
	{
	    icVO.setURL("jdbc:mysql://" + icCO.getHost());
	}
	else
	{
	    icVO.setURL("jdbc:sybase:Tds:" + icCO.getHost());
	}
	 try
	{
	     icCO.setIrpConnectionsVO(icVO);
	     connectionBO.conTest(icCO,pageRef,compCode,appName,lang);
	  
	}
	catch(BaseException e)
	{	
	    icCO = new IRP_CONNECTIONSCO();
	    handleException(e, null, null);
	    conStatus="missingFields";
	    //conFailedMsg=e.getMessage();
	}
	catch(SQLException e)
	{
	    log.error(e, "Error in testConnection()");
	    conStatus=getText("failedkey");
	    conFailedMsg=e.getMessage();
	}
	catch(ClassNotFoundException e)
	{
	    log.error(e, "Error in testConnection()");
	    conStatus=getText("failedkey");
	    conFailedMsg=e.getMessage();
	}
	
	return "grid";
    }
    
    
    /**
     * get the dbms list
     * @return dbmsFormatList
     */
    public List<DbmsFormat> getDbmsList()
    {
	ArrayList<DbmsFormat> dbmsFormatList = new ArrayList<DbmsFormat>();
	dbmsFormatList.add(new DbmsFormat(RepConstantsCommon.oracleDriver, RepConstantsCommon.oracleDriver));
	dbmsFormatList.add(new DbmsFormat(RepConstantsCommon.sybaseDriver, RepConstantsCommon.sybaseDriver));
	dbmsFormatList.add(new DbmsFormat(RepConstantsCommon.sqlServerDriver, RepConstantsCommon.sqlServerDriver)); 
	dbmsFormatList.add(new DbmsFormat(RepConstantsCommon.mySQLDriver, RepConstantsCommon.mySQLDriver)); 
	
	return dbmsFormatList;
    }

    public static class DbmsFormat
    {
	private final String description;
	private final String code;

	public DbmsFormat(String description, String code)
	{
	    this.description = description;
	    this.code = code;
	}

	public String getDescription()
	{
	    return description;
	}

	public String getCode()
	{
	    return code;
	}
    }
    
    
  
    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }
    
    public String getActionType()
    {
        return actionType;
    }
     
     public void setActionType(String actionType)
     {
         this.actionType = actionType;
     }

     public IRP_CONNECTIONSCO getIcCO()
     {
         return icCO;
     }
     
     public void setIcCO(IRP_CONNECTIONSCO icCO)
     {
         this.icCO = icCO;
     }


    public String getUpdates() 
    {
 	return updates;
    }

    public void setUpdates(String updates) 
    {
 	this.updates = updates;
    }
    
     public void setConnectionBO(ConnectionBO connectionBO)
     {
 	this.connectionBO = connectionBO;
     }
     
     public Object getModel()
     {
 	return irpConSC;
     }

    public BigDecimal getConnId()
    {
        return connId;
    }

    public void setConnId(BigDecimal connId)
    {
        this.connId = connId;
    }

    public String getConStatus()
    {
        return conStatus;
    }

    public void setConStatus(String conStatus)
    {
        this.conStatus = conStatus;
    }

    public String getConFailedMsg()
    {
        return conFailedMsg;
    }

    public void setConFailedMsg(String conFailedMsg)
    {
        this.conFailedMsg = conFailedMsg;
    }

}
