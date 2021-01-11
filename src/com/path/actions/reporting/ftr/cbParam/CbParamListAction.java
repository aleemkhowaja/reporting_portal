package com.path.actions.reporting.ftr.cbParam;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.cbParam.CbParamBO;
import com.path.dbmaps.vo.FTR_CB_CODEVO;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.cbParam.CbParamCO;
import com.path.vo.reporting.ftr.cbParam.CbParamSC;

public class CbParamListAction extends ReportingGridBaseAction {

    
    //private static final long serialVersionUID = -2030123301095181550L;
    private CbParamCO cbCO = new CbParamCO();
    private CbParamBO cbParamBO;
    CbParamSC sc = new CbParamSC();
    private String actionType = "";
    private String updates;
    private String entityCode;	 
    private String subEntityCode;
    private String entityCbCode;
    private String compCode;
    private String entityType;
    
    
    
    
    public String getEntityCode()
    {
        return entityCode;
    }

    public void setEntityCode(String entityCode)
    {
        this.entityCode = entityCode;
    }

    public String getSubEntityCode()
    {
        return subEntityCode;
    }

    public void setSubEntityCode(String subEntityCode)
    {
        this.subEntityCode = subEntityCode;
    }

    public String getEntityCbCode()
    {
        return entityCbCode;
    }

    public void setEntityCbCode(String entityCbCode)
    {
        this.entityCbCode = entityCbCode;
    }

    public String getCompCode()
    {
        return compCode;
    }

    public void setCompCode(String compCode)
    {
        this.compCode = compCode;
    }
	
    public String getEntityType()
    {
	return entityType;
    }

    public void setEntityType(String entityType)
    {
	this.entityType = entityType;
    }

    public CbParamCO getCbCO()
    {
	return cbCO;
    }

    public void setCbCO(CbParamCO cbCO)
    {
        this.cbCO = cbCO;
    }

    public String getUpdates() {
	return updates;
    }

    public void setUpdates(String updates) {
	this.updates = updates;
    }

    public String getActionType() {
	return actionType;
    }

    public void setActionType(String actionType) {
	this.actionType = actionType;
    }
    /**
     * function execute to get success if succeed 
     * @return  Success
     */
    public String execute() throws Exception
    {
	try
        {
	    	 this.setActionType("add");
        	 set_showSmartInfoBtn("false");
        	 return SUCCESS;
	}
	catch(Exception e)
	{
	    	//log.error(e, "Error in the execute in Central Bank Parameter");
		handleException(e, "Error in the execute in Central Bank Parameter","exCbParam.exceptionMsg._key");
		return null;
	}
    }

    /**
     * function applyAudit to make my audit and to initialize the audit value
     * @return string successJson
     */
    public String applyAudit() 
    {
	try
	{
	 cbCO.setFtr_cb_codeVO(new FTR_CB_CODEVO());
	 cbCO.getFtr_cb_codeVO().setENTITY_CODE(new BigDecimal(entityCode));
	 cbCO.getFtr_cb_codeVO().setSUB_ENTITY_CODE(new BigDecimal(subEntityCode));
	 if(RepConstantsCommon.EMPTY_STRING.equals(entityCbCode))
	 {
	     cbCO.getFtr_cb_codeVO().setENTITY_CB_CODE(null);
	 }
	 else
	 {
	     cbCO.getFtr_cb_codeVO().setENTITY_CB_CODE(new BigDecimal(entityCbCode));
	 }
	 cbCO.getFtr_cb_codeVO().setCOMP_CODE(returnSessionObject().getCompanyCode());
	 cbCO.getFtr_cb_codeVO().setENTITY_TYPE(updates);
	 SessionCO sessionCO = returnSessionObject();
	 String trxNb=StringUtils.leftPad(sessionCO.getCompanyCode().toString(), 4, "0")+"0000"+StringUtils.leftPad(entityCode, 4, "0")+
	 StringUtils.leftPad(subEntityCode, 4, "0")+StringUtils.leftPad(updates, 2, "0");
	 setAuditTrxNbr(trxNb);
	 return "successJSON";
	}
	catch(Exception e)
	{
		//log.error(e, "Error in audit in Central Bank Parameter");
		handleException(e, "Error in audit in Central Bank Parameter","auditCbParam.exceptionMsg._key");
		return null;
	}
    }
    
    public Object getModel()
    {
    	return sc;
    }

    public void setCbParamBO(CbParamBO cbParamBO) {
		this.cbParamBO = cbParamBO;
    }
    /**
     * function loadGrid to get the data from the database (BO DAO) and load my grid
     * @return string successJson
     */
    public String loadGrid() throws JSONException
    {
	try
	{
		SessionCO sessionCO = returnSessionObject();
		sc.setCOMP_CODE(sessionCO.getCompanyCode());
		sc.setPageRef(get_pageRef());
   	     	sc.setLang(sessionCO.getLanguage());
   	     	sc.setLovTypeId(RepConstantsCommon.TFA_PRODUCT_LOV_TYPE);
		copyproperties(sc);
		int cbParamCount = cbParamBO.retcbParamListCount(sc);
		setRecords(cbParamCount);		
		setGridModel(cbParamBO.getCbParamList(sc));
		//retrieve audit
	}
	catch(Exception e)
	{
		//log.error(e, "Error in method loadGrid in Central Bank Parameter");
		handleException(e, "Error in method loadGrid in Central Bank Parameter","loadCbParam.exceptionMsg._key");
	}
	return "successJSON";
    }
    /**
     * function saveCbParamList to get all modified rows from the user and make my update or insert or delete but for the user it's only update
     * @return string successJson
     */
    public String saveCbParamList() throws Exception
    {
	try{
        	if(updates!=null && !updates.equals(""))
                {
        	     SessionCO sessionCO = returnSessionObject();
        	     sc.setCOMP_CODE(sessionCO.getCompanyCode());
        	     sc.setPageRef(get_pageRef());
        	     sc.setLang(sessionCO.getLanguage());
        	     sc.setLovTypeId(RepConstantsCommon.TFA_PRODUCT_LOV_TYPE);
        	     GridUpdates gu = getGridUpdates(updates, CbParamCO.class);
        	     List<CbParamCO> updatedList = gu.getLstModify();
        	     AuditRefCO refCO = initAuditRefCO();
        	     refCO.setKeyRef(AuditConstant.FTR_CB_CODE_REF);
        	     refCO.setOperationType(AuditConstant.UPDATE);
        	 
        	     cbParamBO.updateCbParamCOList(updatedList,refCO,sc);	
                }
        	
	}
	catch(Exception e)
	{
	    //log.error(e, "Error in saving in Central Bank Parameter");
	    handleException(e, null,null);
	}
	return "successJSON";
    }
}
