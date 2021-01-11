package com.path.actions.reporting.designer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;

public class QueriesListAction extends ReportingGridBaseAction {

	/**
	 * 
	 */
	//private static final long serialVersionUID = -2434393210280235737L;
	
	private QueryBO queryBO;
	private CommonRepFuncBO commonRepFuncBO;
	private String queryId;
	IRP_AD_HOC_QUERYVO vo = new IRP_AD_HOC_QUERYVO();
	IRP_AD_HOC_QUERYSC sc = new IRP_AD_HOC_QUERYSC();
	private String updates;
	BigDecimal[] queriesId;
	private String isQryArg;
	
	
	
	
	
	public String getIsQryArg()
	{
	    return isQryArg;
	}

	public void setIsQryArg(String isQryArg)
	{
	    this.isQryArg = isQryArg;
	}

	public String openTemplateQueries()
	{
		set_showSmartInfoBtn("false");
		return "templateQueries";
	}

	public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO) {
		this.commonRepFuncBO = commonRepFuncBO;
	}

	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}
	
	public BigDecimal[] getQueriesId() {
		return queriesId;
	}

	public void setQueriesId(BigDecimal[] queriesId) {
		this.queriesId = queriesId;
	}

	@Override
	public Object getModel()
    {
    	return sc;
    }

	public void setQueryBO(QueryBO queryBO) {
		this.queryBO = queryBO;
	}

	public String loadGrid() throws JSONException
    {
		try
		{
			copyproperties(sc);			
			if(checkNbRec(sc))
			{
				setRecords(queryBO.getQueriesCount(sc));	
			}
			setGridModel(queryBO.getQueriesList(sc));
		}
		catch(Exception e)
		{
		    //log.error(e, "Error in method loadGrid in QueriesListAction");
		    handleException(e, "Error loading the queries list","loadQueries.exceptionMsg._key");
		}
		return "grid";
    }
	
	public String delete()  {
		try
		{
			AuditRefCO refCO = initAuditRefCO();
			refCO.setOperationType(AuditConstant.DELETE);
			refCO.setKeyRef(AuditConstant.QUERIES_KEY_REF);
			queryBO.deleteQuery(Arrays.asList(queriesId),refCO);			
		}
		catch(Exception e)
		{
		    //log.error(e, "Error in method delete in QueriesListAction");
		    if(("qryToNotDel").equals(e.getMessage()))
		    {
			handleException(null,null,getText("query.qryToNotDel"));
		    }
		    else
		    {
			handleException(e,null,null);
		    }
		    return "grid";
		}
		return "grid";
	}
	
	public String saveTemplateQuery() {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
		IRP_AD_HOC_QUERYVO vo = new IRP_AD_HOC_QUERYVO();
		vo.setQUERY_NAME(sqlObj.getQUERY_NAME());
		
		if(!NumberUtil.isEmptyDecimal(sqlObj.getCONN_ID()))
		{
			vo.setCONN_ID(sqlObj.getCONN_ID());
		}
		else
		{
			vo.setCONN_ID(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE);
		}
		
		try {
			vo.setQUERY_OBJECT(CommonUtil.objectToBytes(sqlObj));
		} catch (Exception e) {
			//log.error("Error serializing the query xml to byte[].");
			handleException(e, "Error serializing sql object to bytes","saveReport.serialization.exceptionMsg._key");
		}
		
		AuditRefCO refCO = initAuditRefCO();
		if(queryId == null || ("").equals(queryId))
		{
			try {
				BigDecimal id = commonRepFuncBO.retCounterValue("IRP_AD_HOC_QUERY");
				vo.setQUERY_ID(id);
				 refCO.setOperationType(AuditConstant.CREATED);
				 refCO.setKeyRef(AuditConstant.QUERIES_KEY_REF);
				 vo.setAuditRefCO(refCO);
				queryBO.insertQuery(vo);
			} catch (BaseException e) {
				//log.error("Error saving template query.");
				handleException(e, "Error saving template query","saveTempQuery.exceptionMsg._key");
			}			
		}
		else
		{
			vo.setQUERY_ID(new BigDecimal(queryId));
			try {
				 refCO.setOperationType(AuditConstant.UPDATE);
				 refCO.setKeyRef(AuditConstant.QUERIES_KEY_REF);
				 vo.setAuditRefCO(refCO);
				 vo.setDATE_UPDATED(sqlObj.getDATE_UPDATED());
				queryBO.updateQuery(vo);
			} catch (BaseException e) {
				//log.error("Error saving template query.");
				if(e.getMessage()==null || e.getMessage().indexOf("2099")==-1)
				{
					handleException(e, "Error saving template query","saveTempQuery.exceptionMsg._key");					
				}
				else
				{
					handleException(e, null, null);
				}
			//e.printStackTrace();
			}
		}
		
		repSessionCO.setSqlObj(null);
		return "grid";
	}
	
	public String openQuery()
	{
		try {
			ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
			IRP_AD_HOC_QUERYCO queryCO = queryBO.returnQueryById(new BigDecimal(queryId), false);
			queryCO.getSqlObject().setDATE_UPDATED(queryCO.getDATE_UPDATED());
			queryCO.getSqlObject().setIsQryArg(queryCO.getIsQryArg().toString());
			queryCO.getSqlObject().setCONN_ID(queryCO.getCONN_ID());
			repSessionCO.setSqlObj(queryCO.getSqlObject());
			
			//apply audit
			IRP_AD_HOC_QUERYCO qryCO=new IRP_AD_HOC_QUERYCO();
			qryCO.setQUERY_ID(queryCO.getQUERY_ID());
			applyRetrieveAudit(AuditConstant.QUERIES_KEY_REF,qryCO,qryCO);
		} catch (BaseException e) {
			//log.error(e,"Error returning the query.");
			handleException(e, "Error returning the query","openQuery.exceptionMsg._key");
		}  
		catch (IOException e) {
			//log.error(e,"Error deserialising query byte[] to query object.");
			handleException(e, "Error deserialising query bytes to query object","openQuery.deserialising.exceptionMsg._key");
		}
		catch (ClassNotFoundException e) {
			//log.error(e,"Error deserialising query byte[] to query object.");
			handleException(e, "Error deserialising query bytes to query object","openQuery.deserialising.exceptionMsg._key");
		}
		return "queryDesigner";
	}
	
	
	
	
	public String retSelQueryType() throws JSONException
	{
		try
		{
			String result=ConstantsCommon.SQB_QRY_TYPE;
			IRP_AD_HOC_QUERYCO queryCO = queryBO.returnQueryById(new BigDecimal(queryId), false);
			if(queryCO!=null && queryCO.getSqlObject().getEntities().size()>0)
			{
				result=ConstantsCommon.QBE_QRY_TYPE;
			}
			setUpdates(result);
			queryCO.getSqlObject().setIsQryArg(queryCO.getIsQryArg().toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "grid";
	}
	
	public void clearSession(){
		ReportingSessionCO repSessionCO = returnReportingSessionObject();
		repSessionCO.setSqlObj(null);
	}

	public String retTrxNb() throws Exception
	{
		
		JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
		JSONObject jsonModel = jsonFilter.getJSONObject("vo");
		vo = (IRP_AD_HOC_QUERYVO)JSONObject.toBean(jsonModel, IRP_AD_HOC_QUERYVO.class);
		
		 AuditRefCO refCO = initAuditRefCO();
		 refCO.setOperationType(AuditConstant.RETRIEVE);
		 refCO.setKeyRef(AuditConstant.QUERIES_KEY_REF);
		 setAuditTrxNbr(auditBO.checkAuditKey(vo, refCO));
		return "grid";
	}
	
	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
}
