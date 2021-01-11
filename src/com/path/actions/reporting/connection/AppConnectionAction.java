package com.path.actions.reporting.connection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.connection.ConnectionBO;
import com.path.dbmaps.vo.IRP_APP_CONNECTIONVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONCO;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONSC;


/**
 * @author: haytham.k
 * AppConnectionAction class
 */

public class AppConnectionAction extends ReportingLookupBaseAction
{
    
    
    IRP_APP_CONNECTIONSC irpAppConSC = new IRP_APP_CONNECTIONSC();
    private ConnectionBO connectionBO;
    IRP_APP_CONNECTIONCO icAppCO = new IRP_APP_CONNECTIONCO();
    IRP_CONNECTIONSSC irpConSC = new IRP_CONNECTIONSSC();
    private String actionType = "";
    private String updates;
    private String appName;
    private BigDecimal conId;
    
    public String execute() throws Exception
    {
        set_showSmartInfoBtn("false");
        return SUCCESS;
    }
    
    /**
     * load app connection grid
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadAppConnectionGrid()throws BaseException
    {
	
	try
	{	SessionCO sessionCO = returnSessionObject();
	    	irpAppConSC.setLangCode(sessionCO.getLanguage());
	    	copyproperties(irpAppConSC);
		List<IRP_APP_CONNECTIONCO>lst=connectionBO.retAppConnectionList(irpAppConSC);
		int lstCnt=connectionBO.retAppConnectionListCount(irpAppConSC);
		setRecords(lstCnt);
		setGridModel(lst);
		
	}
	catch(Exception e)
	{
		//log.error(e, "ERROR in loadAppConnectionGrid AppConnectionAction");
		handleException(e, "Error in load application connection grid","loadAppConnectionGrid.exceptionMsg._key");
	}
	return SUCCESS;
    }
    
    /**
     * display the jsp file(form)
     * @return successAppConDetail
     * @throws Exception
     */
    public String loadAppConMain() throws Exception
    {

	icAppCO = new IRP_APP_CONNECTIONCO();
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayAppCon = new SYS_PARAM_SCREEN_DISPLAYVO();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

	screenDisplayAppCon.setIS_MANDATORY(BigDecimal.ONE);
	businessHm.put("icAppCO.irpAppConnectionVO.APP_NAME", screenDisplayAppCon);
	businessHm.put("icAppCO.irpAppConnectionVO.CON_ID", screenDisplayAppCon);
	icAppCO.setBusinessHm(businessHm);
	if(icAppCO != null && icAppCO.getBusinessHm() != null && icAppCO.getBusinessHm().size() > 0)
	{
	    getAdditionalScreenParams().putAll(icAppCO.getBusinessHm());
	}

	return "successAppConDetail";
    }
    
    /**
     * Construct connection lookup 
     * @return SUCCESS
     * @throws JSONException
     */
    public String loadConnectionLkp() throws JSONException
	{
		try
		{
			fillLookup( "colConnGridId", "/path/appConnection/appConnection_loadConnectionLkpGrid", "Column Templates List");
		}
		catch(Exception e)
		{
			//log.error(e, "ERROR in loadConnectionLkp AppConnectionAction");
			handleException(e, "Error in load connection lookup","loadConnectionLkp.exceptionMsg._key");
		}
		return SUCCESS;
	}
    
    /**
     * Construct the grid of connection lookup.
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadConnectionLkpGrid()throws BaseException
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
		//log.error(e, "ERROR in loadConnectionLkpGrid AppConnectionAction");
		handleException(e, "Error in load connection lookup grid","loadConnectionLkpGrid.exceptionMsg._key");
	}
	return SUCCESS;
    } 
    
    /**
     * Fill the lookup connection data filtered by user.
     * @param gridId
     * @param gridUrl
     * @param gridCaption
     * @return SUCCESS
     * @throws JSONException
     */
    
    public String fillLookup(String gridId,String gridUrl,String gridCaption) throws JSONException
	{
		try
		{
			// Design the Grid by defining the column model and column names
			String[] name = {"CONN_ID","CONN_DESC"};
			String[] colType = {"number","text"};
			String[] titles = {getText("reporting.code"),getText("reporting.description")};
			
			//Defining the Grid
			LookupGrid grid = new LookupGrid();
			//grid.setId(gridId);
			grid.setCaption(gridCaption);
			grid.setRowNum("10");
			//grid.setFilter(false);
			grid.setUrl(gridUrl);
			
			List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
			
			int cols = name.length;
			for(int i=0;i<cols;i++){
				//Defining each column
				LookupGridColumn gridColumn = new LookupGridColumn();
				gridColumn.setName(name[i]);
				gridColumn.setIndex(name[i]);
				gridColumn.setColType(colType[i]);
				gridColumn.setTitle(titles[i]);
				gridColumn.setSearch(true);
				
				//adding column to the list
				lsGridColumn.add(gridColumn);
			}
			lookup(grid, lsGridColumn,null,irpConSC);
		}
		catch(Exception e)
		{
			//log.error(e, "ERROR in fillLookup AppConnectionAction");
			handleException(e, "Error in fill lookup","fillLookup.exceptionMsg._key");
		}
		return SUCCESS;
	}
    
    /**
     * Construct application name lookup 
     * @return SUCCESS
     * @throws JSONException
     */
    public String loadAppNameLkp() throws JSONException
    {
	try
	{
	    fillAppNameLookup( "colAppNameGridId", "/path/appConnection/appConnection_loadAppNameLkpGrid", "Column app name List");
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in loadAppNameLkp AppConnectionAction");
	    handleException(e, "Error in load application name lookup","loadAppNameLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }
    
    /**
     * Construct the grid of application name lookup.
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadAppNameLkpGrid()throws BaseException
    {
    	
    	try
    	{	SessionCO sessionCO = returnSessionObject();
    		irpAppConSC.setLangCode(sessionCO.getLanguage());
    	    	copyproperties(irpAppConSC);
    		List<IRP_APP_CONNECTIONCO>lst=connectionBO.retAppNameList(irpAppConSC);
    		int lstCnt=connectionBO.retAppNameListCount(irpAppConSC);
    		setRecords(lstCnt);
    		setGridModel(lst);	
    	}
    	catch(Exception e)
    	{
	    //log.error(e, "ERROR in loadAppNameLkpGrid AppConnectionAction");
	    handleException(e, "Error in load application name lookup grid","loadAppNameLkpGrid.exceptionMsg._key");
    	}
    	return SUCCESS;
    } 

    /**
     * Fill the lookup application name data filtered by user.
     * @param gridId
     * @param gridUrl
     * @param gridCaption
     * @return SUCCESS
     * @throws JSONException
     */
    public String fillAppNameLookup(String gridId,String gridUrl,String gridCaption) throws JSONException
	{
		try
		{
			// Design the Grid by defining the column model and column names
			String[] name = {"APP_NAME","LONG_DESC"};
			String[] colType = {"text","text"};
			String[] titles = {getText("reporting.code"),getText("reporting.description")};
			
			//Defining the Grid
			LookupGrid grid = new LookupGrid();
			//grid.setId(gridId);
			grid.setCaption(gridCaption);
			grid.setRowNum("10");
			//grid.setFilter(false);
			grid.setUrl(gridUrl);
			
			List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
			
			int cols = name.length;
			for(int i=0;i<cols;i++){
				//Defining each column
				LookupGridColumn gridColumn = new LookupGridColumn();
				gridColumn.setName(name[i]);
				gridColumn.setIndex(name[i]);
				gridColumn.setColType(colType[i]);
				gridColumn.setTitle(titles[i]);
				gridColumn.setSearch(true);
				
				//adding column to the list
				lsGridColumn.add(gridColumn);
			}
			lookup(grid, lsGridColumn,null,irpAppConSC);
			
		}
		catch(Exception e)
		{
		    //log.error(e, "ERROR in fillAppNameLookup AppConnectionAction");
		    handleException(e, "Error in fill application name lookup","fillAppNameLkpGrid.exceptionMsg._key");
		}
		return SUCCESS;
	}

    /**
     * apply the dependency on the application connection lkp
     * @return SUCCESS
     * @throws Exception
     */
    public String applyAppConDependency() throws Exception
	{
		try
		{
		    IRP_APP_CONNECTIONVO icAppVO=new IRP_APP_CONNECTIONVO();
		    icAppVO.setCON_ID(conId);
		    icAppCO =connectionBO.applyAppConDependency(icAppVO);
		 
		}
		catch(Exception e)
		{

		    //log.error(e,"***************** error in applyAppConDependency *************");
		    handleException(e,null , null);
		}
		return SUCCESS;
		
	}

    /**
     * apply the dependency on the application name lkp
     * @return SUCCESS
     * @throws Exception
     */
    public String applyAppNameDependency() throws Exception
	{
		try
		{
		    IRP_APP_CONNECTIONVO icAppVO=new IRP_APP_CONNECTIONVO();
		    icAppVO.setAPP_NAME(appName);
		    icAppCO =connectionBO.applyAppNameDependency(icAppVO);
		 
		}
		catch(Exception e)
		{

		    //log.error(e, "***************** error in applyAppNameDependency *************");
		    handleException(e,null , null);
		}
		return SUCCESS;
		
	}


    /**
     * retrieve application connection detail from db for the selected row
     * @return successConDetail
     * @throws Exception
     */
    public String retrieveAppConnData()throws Exception
    {
	try
	{
	    icAppCO.setAPP_NAME(appName);
	    icAppCO.getIrpAppConnectionVO().setAPP_NAME(appName);
	    icAppCO.getIrpAppConnectionVO().setCON_ID(conId);
	    icAppCO.setActionType(actionType);
	    SessionCO sessionCO = returnSessionObject();
	    icAppCO.setLangCode(sessionCO.getLanguage());
	    icAppCO =connectionBO.retrieveAppConnData(icAppCO);
	    if(icAppCO!=null && icAppCO.getBusinessHm()!=null && icAppCO.getBusinessHm().size() > 0)
	    {
		getAdditionalScreenParams().putAll(icAppCO.getBusinessHm());
	    }
	    
	    applyRetrieveAudit(AuditConstant.DB_APP_CONNECTION_KEY_REF,icAppCO.getIrpAppConnectionVO(),icAppCO.getIrpAppConnectionVO());	
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in retrieveAppConnData in AppConnectionAction");
	    handleException(e, "Error in retrieve application connection data","retrieveAppConnData.exceptionMsg._key");
	}
	return "successAppConDetail";
    }
     
    
    /**
     * save the changes made in the form
     * @return SUCCESS
     */

    public String updateAppCon(){
	
		try {
		    
		    	SessionCO sessionCO = returnSessionObject();
		    	IRP_APP_CONNECTIONVO icAppVO=new IRP_APP_CONNECTIONVO();
			AuditRefCO refCO = initAuditRefCO();
			String pageRef		= get_pageRef();
			BigDecimal compCode 	= sessionCO.getCompanyCode();
			String appName   	= sessionCO.getCurrentAppName();
			String lang		= sessionCO.getLanguage();
			
			// if actionType =add or "" (mode save new)
			if(this.actionType.equals("add") || this.actionType.equals(""))
			{
			    //apply audit
			    refCO.setOperationType(AuditConstant.CREATED);
			    refCO.setKeyRef(AuditConstant.DB_APP_CONNECTION_KEY_REF);
			    icAppVO.setAuditRefCO(refCO);
			    icAppVO.setAPP_NAME(icAppCO.getIrpAppConnectionVO().getAPP_NAME());
			    icAppVO.setCON_ID(icAppCO.getIrpAppConnectionVO().getCON_ID());
			    icAppCO.setIrpAppConnectionVO(icAppVO);
			    icAppCO.setActionType(actionType);

			    connectionBO.insertAppCon(icAppCO,pageRef,compCode,appName,lang);
			}
			// if actionType =edit (mode update)
			else if(this.actionType.equals("edit"))
			{
			    //apply audit
			    refCO.setOperationType(AuditConstant.UPDATE);
			    refCO.setKeyRef(AuditConstant.DB_APP_CONNECTION_KEY_REF);
			    icAppVO.setAuditRefCO(refCO);
			    icAppVO.setAPP_NAME(icAppCO.getIrpAppConnectionVO().getAPP_NAME());
			    icAppVO.setCON_ID(icAppCO.getIrpAppConnectionVO().getCON_ID());
			    icAppVO.setDATE_UPDATED(icAppCO.getIrpAppConnectionVO().getDATE_UPDATED());
			    icAppCO.setIrpAppConnectionVO(icAppVO);
			    IRP_APP_CONNECTIONVO oldAppConnVO=(IRP_APP_CONNECTIONVO)returnAuditObject(IRP_APP_CONNECTIONVO.class);
			    

			    connectionBO.updateAppConDetail(icAppCO,oldAppConnVO,pageRef,compCode,appName,lang);
			}
			
		} catch (Exception e) {
			    //log.error(e, "ERROR in updateAppCon in AppConnectionAction");
			    handleException(e, null,null );
		}
		return SUCCESS;
	}
    
    
    /**
     * delete the selected application connection
     * @return SUCCESS
     */
    public String deleteAppConnection() 
    {
	try
	{
    	    IRP_APP_CONNECTIONVO icAppVO=new IRP_APP_CONNECTIONVO();
    	    //apply audit
    	    AuditRefCO refCO = initAuditRefCO();
            refCO.setOperationType(AuditConstant.DELETE);
            refCO.setKeyRef(AuditConstant.DB_APP_CONNECTION_KEY_REF);
            icAppVO.setAuditRefCO(refCO);
            icAppVO.setAPP_NAME(appName);
	    connectionBO.deleteAppConnection(icAppVO);
	}
	catch(BaseException e)
	{
	    //log.error(e, "ERROR in deleteAppConnection in AppConnectionAction");
	    handleException(e, "Error in delete application connection","deleteAppConnection.exceptionMsg._key");
	}
	return SUCCESS;
    }
    
    
    
    public BigDecimal getConId()
    {
        return conId;
    }

    public void setConId(BigDecimal conId)
    {
        this.conId = conId;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getUpdates()
    {
        return updates;
    }

    public void setUpdates(String updates)
    {
        this.updates = updates;
    }

    public IRP_APP_CONNECTIONCO getIcAppCO()
    {
        return icAppCO;
    }

    public void setIcAppCO(IRP_APP_CONNECTIONCO icAppCO)
    {
        this.icAppCO = icAppCO;
    }

    public IRP_CONNECTIONSSC getIrpConSC()
    {
        return irpConSC;
    }

    public void setIrpConSC(IRP_CONNECTIONSSC irpConSC)
    {
        this.irpConSC = irpConSC;
    }

    public String getActionType()
    {
        return actionType;
    }

    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }

    public IRP_APP_CONNECTIONSC getIrpAppConSC()
    {
        return irpAppConSC;
    }
    public void setIrpAppConSC(IRP_APP_CONNECTIONSC irpAppConSC)
    {
        this.irpAppConSC = irpAppConSC;
    }

    public Object getModel()
    {
	return irpAppConSC;
    }

    public void setConnectionBO(ConnectionBO connectionBO)
    {
        this.connectionBO = connectionBO;
    }
    
//	public Object getModel()
//	{
//		return commonDetailsSC;
//	}
}
