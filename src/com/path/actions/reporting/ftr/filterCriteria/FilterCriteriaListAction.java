package com.path.actions.reporting.ftr.filterCriteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.filterCriteria.FilterCriteriaBO;
import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVO;
import com.path.dbmaps.vo.IRP_CRITERIA_TYPEVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIACO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;

public class FilterCriteriaListAction extends ReportingGridBaseAction
{

    /**
	 * 
	 */
    List critTypesList = new ArrayList();
    private GLSTMPLT_CRITERIACO fcCO = new GLSTMPLT_CRITERIACO();
    private FilterCriteriaBO filterCriteriaBO;
    GLSTMPLT_CRITERIASC sc = new GLSTMPLT_CRITERIASC();
    private CommonLookupBO commonLookupBO;
    private GLSTMPLT_CRITERIAVO filterCritVO = new GLSTMPLT_CRITERIAVO();
    private String actionType = "";
    private String code;
    private int usageCount;
    private String updates;
    private String oldDesc;
    private String oldCrtType;
    private String smart;

    public String getSmart()
    {
	return smart;
    }

    public void setSmart(String smart)
    {
	this.smart = smart;
    }

    public String getOldDesc()
    {
	return oldDesc;
    }

    public void setOldDesc(String oldDesc)
    {
	this.oldDesc = oldDesc;
    }

    public String getOldCrtType()
    {
	return oldCrtType;
    }

    public void setOldCrtType(String oldCrtType)
    {
	this.oldCrtType = oldCrtType;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public GLSTMPLT_CRITERIACO getFcCO()
    {
	return fcCO;
    }

    public void setFcCO(GLSTMPLT_CRITERIACO fcCO)
    {
	this.fcCO = fcCO;
    }

    public int getUsageCount()
    {
	return usageCount;
    }

    public void setUsageCount(int usageCount)
    {
	this.usageCount = usageCount;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public List getCritTypesList()
    {
	return critTypesList;
    }

    public void setCritTypesList(List critTypesList)
    {
	this.critTypesList = critTypesList;
    }

    public GLSTMPLT_CRITERIAVO getFilterCritVO()
    {
	return filterCritVO;
    }

    public void setFilterCritVO(GLSTMPLT_CRITERIAVO filterCritVO)
    {
	this.filterCritVO = filterCritVO;
    }

    public String getActionType()
    {
	return actionType;
    }

    public void setActionType(String actionType)
    {
	this.actionType = actionType;
    }

    public String getCode()
    {
	return code;
    }

    public void setCode(String code)
    {
	this.code = code;
    }

    public List getCriteriaTypes()
    {
	SessionCO sessionCO = returnSessionObject();
	try
	{
	    IRP_CRITERIA_TYPEVO vo = new IRP_CRITERIA_TYPEVO();
	    vo.setLANG_CODE(sessionCO.getLanguage());
	    critTypesList = commonLookupBO.getLookupList(vo);
	}
	catch(BaseException e)
	{
	   // log.error(e, "Error getting the list of criteria types");
	    handleException(e, "Error getting the list of criteria types", "returnCritTypes.exceptionMsg._key");
	}
	return critTypesList;
    }

    /*
     * Method called on the opening of the screen
     */
    public String execute() throws Exception
    {
	this.setActionType("add");
	set_showSmartInfoBtn("false");
	set_searchGridId(RepConstantsCommon.FILTER_CRT_TAB_ID);
	set_showNewInfoBtn(ConstantsCommon.TRUE);
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
	screenDisplaySmart.setIS_VISIBLE(BigDecimal.ZERO);
	getAdditionalScreenParams().put("fcCO.glstmpltCriteriaVO.SMART_CODE", screenDisplaySmart);
	getAdditionalScreenParams().put("fcCO.SMART_BRIEF_NAME_ENG", screenDisplaySmart);
	return SUCCESS;
    }

    public Object getModel()
    {
	return sc;
    }

    public void setFilterCriteriaBO(FilterCriteriaBO filterCriteriaBO)
    {
	this.filterCriteriaBO = filterCriteriaBO;
    }

    /*
     * Method called when loading the grid
     */
    public String loadGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(sc);
	    sc.setCOMP_CODE(sessionCO.getCompanyCode());
	    sc.setLANG_CODE(sessionCO.getLanguage());
	    if(checkNbRec(sc))
	    {
		setRecords(filterCriteriaBO.getFilterCritCount(sc));
	    }
	    setGridModel(filterCriteriaBO.getFilterCritList(sc));
	}
	catch(Exception e)
	{
	   // log.error(e, "Error in method loadGrid in FilterCriteriaAction( Error Loading Grid )");
	    handleException(e, "Error loading filter criteria", "loadFilterCrit.exceptionMsg._key");
	}
	return "grid";
    }

    /**
     * This method is called upon trying to update a filter criteria
     * 
     * @return
     */
    public String update()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String pageRef = get_pageRef();
	    String appName = sessionCO.getCurrentAppName();
	    String lang = sessionCO.getLanguage();
	    fcCO.getGlstmpltCriteriaVO().setCOMP_CODE(compCode);
	    AuditRefCO refCO = initAuditRefCO();

	    if(RepConstantsCommon.CRITERIA_SMART.equals(fcCO.getGlstmpltCriteriaVO().getCRI_TYPE()))
	    {
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ONE);
		screenDisplaySmart.setIS_READONLY(BigDecimal.ONE);
		screenDisplaySmart.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplaySmart.setLABEL_KEY("reporting.smartType");
		screenDisplaySmart.setPROG_REF("T01MT");
		screenDisplaySmart.setCOMP_CODE(compCode);
		screenDisplaySmart.setELEMENT_NAME("fcCO.glstmpltCriteriaVO.SMART_CODE");
		screenDisplaySmart.setVO_CO_REFERENCE("glstmpltCriteriaVO");
		screenDisplaySmart.setVO_PROPERTY_NAME("SMART_CODE");
		businessHm.put("fcCO.glstmpltCriteriaVO.SMART_CODE", screenDisplaySmart);
		fcCO.setBusinessHm(businessHm);
	    }

	    if(this.actionType.equals("add"))
	    {
		GLSTMPLT_CRITERIAVO fcVO = filterCriteriaBO.getFilterCritById(fcCO.getGlstmpltCriteriaVO());
		if(fcVO == null)
		{
		    // apply audit
		    refCO.setOperationType(AuditConstant.CREATED);
		    refCO.setKeyRef(AuditConstant.FILTER_CRITERIA_KEY_REF);
		    refCO.setProgRef(pageRef);
		    refCO.setAppName(appName);
		    fcCO.getGlstmpltCriteriaVO().setAuditRefCO(refCO);
		    this.filterCriteriaBO.insertFilterCriteria(fcCO, pageRef, compCode, appName, lang);
		    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCriLineGl = new SYS_PARAM_SCREEN_DISPLAYVO();
		    screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ZERO);
		    getAdditionalScreenParams().put("lineGlCrt", screenDisplayCriLineGl);
		    getAdditionalScreenParams().put("criLineGLId", screenDisplayCriLineGl);
		    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
		    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmartText = new SYS_PARAM_SCREEN_DISPLAYVO();
		    screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ZERO);
		    screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ZERO);
		    screenDisplaySmartText.setIS_READONLY(BigDecimal.ZERO);
		    screenDisplaySmart.setIS_VISIBLE(BigDecimal.ZERO);
		    screenDisplaySmart.setIS_MANDATORY(BigDecimal.ZERO);
		    getAdditionalScreenParams().put("lookuptxt_smartLookupSearch", screenDisplaySmart);
		    getAdditionalScreenParams().put("smartCode", screenDisplaySmartText);
		}
		else
		{
		    this.addActionMessage("Code already exists.Please choose another code.");
		}
	    }
	    else
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.FILTER_CRITERIA_KEY_REF);
		refCO.setProgRef(get_pageRef());
		refCO.setAppName(sessionCO.getCurrentAppName());
		fcCO.getGlstmpltCriteriaVO().setAuditRefCO(refCO);
		GLSTMPLT_CRITERIAVO oldCrtVO = ((GLSTMPLT_CRITERIAVO) returnAuditObject(GLSTMPLT_CRITERIAVO.class));
		oldCrtVO.setLINKED_IND("");
		oldCrtVO.setLINKED_CODE(null);
		refCO.setAuditObj(oldCrtVO);
		if(!RepConstantsCommon.CRITERIA_PER.equals(fcCO.getGlstmpltCriteriaVO().getCRI_TYPE()))
		{
		    fcCO.getGlstmpltCriteriaVO().setCRI_LINE_GL("");
		}
		if(!RepConstantsCommon.CRITERIA_SMART.equals(fcCO.getGlstmpltCriteriaVO().getCRI_TYPE()))
		{
		    oldCrtVO.setSMART_CODE(null);
		}
		if((BigDecimal.ZERO).equals(oldCrtVO.getSMART_CODE()))
		{
		    oldCrtVO.setSMART_CODE(null);
		}
		this.filterCriteriaBO.updateFilterCriteria(fcCO, pageRef, compCode, appName, lang);
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCriLineGl = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ZERO);
		getAdditionalScreenParams().put("lineGlCrt", screenDisplayCriLineGl);
		getAdditionalScreenParams().put("criLineGLId", screenDisplayCriLineGl);
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmartText = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_READONLY(BigDecimal.ZERO);
		screenDisplaySmart.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ZERO);
		getAdditionalScreenParams().put("lookuptxt_smartLookupSearch", screenDisplaySmart);
		getAdditionalScreenParams().put("smartCode", screenDisplaySmartText);
	    }
	    fcCO.getGlstmpltCriteriaVO().getAuditRefCO().setAuditObj(null);
	    putAuditObject(fcCO.getGlstmpltCriteriaVO());
	}

	catch(BOException e)
	{
	    if(e.getErrorCode() != null && e.getErrorCode().intValue() == 2099)
	    {
		//log.error(e, "ERROR in concurrent access");
		handleException(e, "Concurrent access", "reporting.concurrentAccess");
	    }
	    else
	    {
		//log.error(e, "");
		handleException(e, null, null);
	    }
	    return "error";
	}

	catch(BaseException e)
	{
	    //log.error(e, "");
	    handleException(e, null, null);
	}
	return "grid";
    }

    /*
     * Method called to check if a criteria is used when trying to delete it
     */
    public String countOfUsedFilterCriteria()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    GLSTMPLT_CRITERIAVO vo = new GLSTMPLT_CRITERIAVO();
	    vo.setCOMP_CODE(compCode);
	    vo.setCODE(new BigDecimal(code));
	    usageCount = filterCriteriaBO.findCriteriaUsageCount(vo);

	}
	catch(Exception e)
	{
	    //log.error(e, "Error in method countOfUsedFilterCriteria in FilterCriteriaAction");
	    handleException(e, "Error returning the filter criteria usage count",
		    "returnFilterCritUsage.exceptionMsg._key");
	}
	return "grid";
    }

    /*
     * Method called when checking if a criteria code already exists
     */
    public String countOfFilterCriteria()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    GLSTMPLT_CRITERIAVO vo = new GLSTMPLT_CRITERIAVO();
	    vo.setCOMP_CODE(compCode);
	    vo.setCODE(new BigDecimal(code));
	    code = (Integer.toString(filterCriteriaBO.findCriteriaCount(vo)));

	}
	catch(Exception e)
	{
	    //log.error(e, "Error in method countOfFilterCriteria in FilterCriteriaAction");
	    handleException(e, "Error returning the filter criteria count", "returnFilterCritCnt.exceptionMsg._key");
	}
	return "grid";
    }

    /*
     * Method called when trying to delete a criteria
     */
    public String delete()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String pageRef = get_pageRef();
	    String appName = sessionCO.getCurrentAppName();
	    GLSTMPLT_CRITERIAVO vo = new GLSTMPLT_CRITERIAVO();
	    vo.setCOMP_CODE(compCode);
	    vo.setCODE(new BigDecimal(code));
	    // apply audit
	    AuditRefCO refCO = initAuditRefCO();
	    String lang = sessionCO.getLanguage();
	    refCO.setOperationType(AuditConstant.DELETE);
	    refCO.setKeyRef(AuditConstant.FILTER_CRITERIA_KEY_REF);
	    refCO.setProgRef(pageRef);
	    refCO.setAppName(appName);
	    vo.setAuditRefCO(refCO);
	    fcCO.setGlstmpltCriteriaVO(vo);
	    filterCriteriaBO.deleteFilterCriteria(fcCO, pageRef, compCode, appName, lang);
	}
	catch(Exception e)
	{
	   // log.error(e, "Error in method deleteFilterCriteria in FilterCriteriaAction");
	    handleException(e, "Error deleting filter criteria", "deleteFilterCrit.exceptionMsg._key");
	}
	return "grid";
    }

    public String refresh()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setCritTypesList(null);
	return "grid";
    }

    /*
     * Method called on the fill of the form in filter criteria screen
     */
    public String retrieveFitlCrt() throws Exception
    {
	try
	{
	    GLSTMPLT_CRITERIAVO fcVO = new GLSTMPLT_CRITERIAVO();
	    fcVO.setCOMP_CODE(returnSessionObject().getCompanyCode());
	    fcVO.setCODE(new BigDecimal(code));
	    filterCritVO = filterCriteriaBO.retrieveFitlCrt(fcVO);
	    fcCO.setGlstmpltCriteriaVO(filterCritVO);

	    if(fcCO.getGlstmpltCriteriaVO() != null && fcCO.getGlstmpltCriteriaVO().getCRI_TYPE() != null
		    && fcCO.getGlstmpltCriteriaVO().getCRI_TYPE().equals(RepConstantsCommon.CRITERIA_SMART))
	    {
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ONE);
		businessHm.put("fcCO.glstmpltCriteriaVO.SMART_CODE", screenDisplaySmart);
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmartText = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ONE);
		screenDisplaySmartText.setIS_READONLY(BigDecimal.ONE);
		businessHm.put("fcCO.SMART_BRIEF_NAME_ENG", screenDisplaySmartText);
		fcCO.setBusinessHm(businessHm);
		getAdditionalScreenParams().putAll(fcCO.getBusinessHm());

		S_ADDITIONS_OPTIONSSC addSC = new S_ADDITIONS_OPTIONSSC();
		addSC.setOPTION_CODE(fcCO.getGlstmpltCriteriaVO().getSMART_CODE());
		fcCO.setSMART_BRIEF_NAME_ENG(commonLookupBO.getSmartText(addSC));
		fcCO.setSmartInput("inline");
		smart = "1";

		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayAppCon = new SYS_PARAM_SCREEN_DISPLAYVO();
		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> bHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

		screenDisplayAppCon.setIS_MANDATORY(BigDecimal.ONE);
		businessHm.put("fcCO.glstmpltCriteriaVO.SMART_CODE", screenDisplayAppCon);
		businessHm.put("fcCO.SMART_BRIEF_NAME_ENG", screenDisplayAppCon);
		fcCO.setBusinessHm(bHm);
		// hiding cri line gl
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCriLineGl = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ZERO);
		getAdditionalScreenParams().put("lineGlCrt", screenDisplayCriLineGl);
		getAdditionalScreenParams().put("criLineGLId", screenDisplayCriLineGl);

	    }
	    else if(fcCO.getGlstmpltCriteriaVO() != null && fcCO.getGlstmpltCriteriaVO().getCRI_TYPE() != null
		    && RepConstantsCommon.CRITERIA_PER.equalsIgnoreCase(fcCO.getGlstmpltCriteriaVO().getCRI_TYPE()))
	    {
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCriLineGl = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ONE);
		getAdditionalScreenParams().put("lineGlCrt", screenDisplayCriLineGl);
		getAdditionalScreenParams().put("criLineGLId", screenDisplayCriLineGl);
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmartText = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_READONLY(BigDecimal.ZERO);
		screenDisplaySmart.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ZERO);
		getAdditionalScreenParams().put("lookuptxt_smartLookupSearch", screenDisplaySmart);
		getAdditionalScreenParams().put("smartCode", screenDisplaySmartText);
	    }
	    else
	    {
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplaySmart.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ZERO);
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmartText = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_READONLY(BigDecimal.ZERO);
		getAdditionalScreenParams().put("lookuptxt_smartLookupSearch", screenDisplaySmart);
		getAdditionalScreenParams().put("smartCode", screenDisplaySmartText);
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCriLineGl = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ZERO);
		getAdditionalScreenParams().put("lineGlCrt", screenDisplayCriLineGl);
		getAdditionalScreenParams().put("criLineGLId", screenDisplayCriLineGl);
	    }

	    applyRetrieveAudit(AuditConstant.FILTER_CRITERIA_KEY_REF, filterCritVO, filterCritVO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "successMnt";
    }

    /*
     * Method called on the criteria type's drpdwn dependency
     */
    public String applySmartValidation() throws JSONException
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmartText = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCriLineGl = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(fcCO.getGlstmpltCriteriaVO().getCRI_TYPE().equals(RepConstantsCommon.CRITERIA_SMART))
	    {
		screenDisplaySmart.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ONE);
		screenDisplaySmartText.setIS_READONLY(BigDecimal.ONE);
		screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ONE);
	    }
	    else
	    {
		screenDisplaySmart.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmart.setIS_MANDATORY(BigDecimal.ZERO);
		screenDisplaySmart.setValue("");
		screenDisplaySmartText.setIS_READONLY(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplaySmartText.setIS_MANDATORY(BigDecimal.ZERO);
		screenDisplaySmartText.setValue("");
	    }
	    if(RepConstantsCommon.CRITERIA_PER.equalsIgnoreCase(fcCO.getGlstmpltCriteriaVO().getCRI_TYPE()))
	    {
		screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    else
	    {
		screenDisplayCriLineGl.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    getAdditionalScreenParams().put("lookuptxt_smartLookupSearch", screenDisplaySmart);
	    getAdditionalScreenParams().put("smartCode", screenDisplaySmartText);
	    getAdditionalScreenParams().put("lineGlCrt_", screenDisplayCriLineGl);
	    getAdditionalScreenParams().put("criLineGLId", screenDisplayCriLineGl);
	}
	catch(Exception e)
	{
	   // log.error(e, "");
	    handleException(e, null, null);
	}
	return "grid";
    }

}
