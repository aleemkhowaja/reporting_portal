package com.path.actions.reporting.ftr.templateHeader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.ftr.templateHeader.TemplateHeaderBO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERSC;

public class TemplateHeaderAction extends ReportingGridBaseAction implements ServletResponseAware
{
    private TemplateHeaderBO templateHeaderBO;
    private ReportBO reportBO;
    private GLSHEADERSC glsheaderSC = new GLSHEADERSC();
    private List<GLSHEADERCO> glsheaderList;
    private BigDecimal code;
    private String update;
    private GLSHEADERCO glsheaderCO = new GLSHEADERCO();
    private String[] arrayValuesAutoComplete;
    private String mode;
    protected HttpServletResponse response;
    private String returnedHTML;
    private CommonLookupBO commonLookupBO;
    ArrayList<SYS_PARAM_LOV_TRANSVO> tmpltTypeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    
    public ArrayList<SYS_PARAM_LOV_TRANSVO> getTmpltTypeList()
    {
        return tmpltTypeList;
    }

    public void setTmpltTypeList(ArrayList<SYS_PARAM_LOV_TRANSVO> tmpltTypeList)
    {
        this.tmpltTypeList = tmpltTypeList;
    }

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    @Override
    public void setServletResponse(HttpServletResponse response)
    {
	this.response = response;
    }

    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public String[] getArrayValuesAutoComplete()
    {
	return arrayValuesAutoComplete;
    }

    public GLSHEADERCO getGlsheaderCO()
    {
	return glsheaderCO;
    }

    public void setGlsheaderCO(GLSHEADERCO glsheaderCO)
    {
	this.glsheaderCO = glsheaderCO;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    public List<GLSHEADERCO> getGlsheaderList()
    {
	return glsheaderList;
    }

    public void setGlsheaderList(List<GLSHEADERCO> glsheaderList)
    {
	this.glsheaderList = glsheaderList;
    }

    public GLSHEADERSC getGlsheaderSC()
    {
	return glsheaderSC;
    }

    public void setGlsheaderSC(GLSHEADERSC glsheaderSC)
    {
	this.glsheaderSC = glsheaderSC;
    }

    public void setTemplateHeaderBO(TemplateHeaderBO templateHeaderBO)
    {
	this.templateHeaderBO = templateHeaderBO;
    }

    public void setReportBO(ReportBO reportBO)
    {
	this.reportBO = reportBO;
    }

    public String getReturnedHTML()
    {
	return returnedHTML;
    }

    public void setReturnedHTML(String returnedHTML)
    {
	this.returnedHTML = returnedHTML;
    }

    public String execute() throws Exception
    {
	set_showSmartInfoBtn("false");
	set_searchGridId("templateHeaderTable");
	set_showNewInfoBtn("true");
	mode = "add";
	retTmpltTypeList();
	return SUCCESS;
    }

    /**
     * Function called to fill autocomplete textfields with data
     */
    public String retTextFieldsAutoComplete() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    glsheaderSC.setColumnName(update);
	    glsheaderSC.setPreferredLanguage(sessionCO.getLanguage());
	    // lov_type_id of session_properties stored in sys_param_lov_trans
	    glsheaderSC.setLovTypeId(RepConstantsCommon.SESSION_PROPERTIES);
	    glsheaderSC.setTemplateTypeID(RepConstantsCommon.TMPLT_TYPE_LOV_TYPE);
	    // lov_type_id of report_properties stored in sys_param_lov_trans
	    glsheaderSC.setRepLovTypeID(RepConstantsCommon.REPORT_PROPERTIES);
	    glsheaderSC.setTemplateTypeID(RepConstantsCommon.TMPLT_TYPE_LOV_TYPE);
	    // returns the values saved in GLSHEADER
	    ArrayList<String> info = templateHeaderBO.retTextFieldsAutoComplete(glsheaderSC);
	    // returns session and report's properties from sys_param_lov_trans
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SESSION_PROPERTIES);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    ArrayList<SYS_PARAM_LOV_TRANSVO> sessionPropsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    HashMap<String, String> sessionProps = new HashMap<String, String>();
	    for(SYS_PARAM_LOV_TRANSVO vo : sessionPropsList)
	    {
		sessionProps.put(vo.getVALUE_DESC(), vo.getVALUE_CODE());
	    }
	    info.addAll(sessionProps.keySet());
	    repSessionCO.setSessionProps(sessionProps);

	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.REPORT_PROPERTIES);
	    ArrayList<SYS_PARAM_LOV_TRANSVO> reportPropsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    HashMap<String, String> reportProps = new HashMap<String, String>();
	    for(SYS_PARAM_LOV_TRANSVO vo : reportPropsList)
	    {
		reportProps.put(vo.getVALUE_DESC(), vo.getVALUE_CODE());
	    }
	    info.addAll(reportProps.keySet());
	    repSessionCO.setReportProps(reportProps);
	    int listSize = info.size();
	    arrayValuesAutoComplete = new String[listSize];
	    info.toArray(arrayValuesAutoComplete);

	}
	catch(Exception e)
	{
	    handleException(e, "", "tmplHeader.errorRetrieveAutoComplete.exceptionMsg._key");
	    // log.error(e,
	    // "Error in method getTextFieldsAutoComplete in TemplateHeaderAction");
	}
	return "json";
    }

    /**
     * Method called when loading the grid
     */
    public String loadGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(glsheaderSC);
	    glsheaderSC.setPreferredLanguage(sessionCO.getLanguage());
	    glsheaderSC.setLovTypeId(RepConstantsCommon.SESSION_PROPERTIES);
	    glsheaderSC.setRepLovTypeID(RepConstantsCommon.REPORT_PROPERTIES);
	    glsheaderSC.setTemplateTypeID(RepConstantsCommon.TMPLT_TYPE_LOV_TYPE);
	    if(checkNbRec(glsheaderSC))
	    {
		setRecords(templateHeaderBO.getTemplateHeaderCount(glsheaderSC));
	    }
	    glsheaderList = templateHeaderBO.getTemplateHeaderList(glsheaderSC);
	    setGridModel(glsheaderList);
	}
	catch(Exception e)
	{
	    // log.error(e, "Error in method loadGrid in TemplateHeaderAction");
	    handleException(e, "tmplHeader.errorGrid.exceptionMsg._key", null);
	}
	return "json";
    }

    /**
     * Method called when deleting a template header
     * 
     * @return
     */
    public String deleteHeader()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String pageRef = get_pageRef();
	    String appName = sessionCO.getCurrentAppName();
	    GLSHEADERVO glsheaderVO = new GLSHEADERVO();
	    glsheaderVO.setCODE(code);
	    AuditRefCO refCO = initAuditRefCO();
	    String lang = sessionCO.getLanguage();
	    refCO.setOperationType(AuditConstant.DELETE);
	    refCO.setKeyRef(AuditConstant.TEMPLATE_HEADER_KEY_REF);
	    refCO.setProgRef(pageRef);
	    refCO.setAppName(appName);
	    glsheaderVO.setAuditRefCO(refCO);
	    glsheaderCO.setGlsheaderVO(glsheaderVO);
	    templateHeaderBO.deleteTemplateHeader(glsheaderCO, pageRef, compCode, appName, lang);
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
	    screenDisplaySmart.setIS_VISIBLE(BigDecimal.ONE);
	    screenDisplaySmart.setIS_READONLY(BigDecimal.ZERO);
	    getAdditionalScreenParams().put("glsheaderCO.glsheaderVO.CODE", screenDisplaySmart);
	}
	catch(Exception e)
	{
	    // log.error(e, "Error in method delete  in TemplateHeaderAction");
	    handleException(e, "tmplHeader.errorDeleting.exceptionMsg._key", null);
	}
	return "json";
    }

    /**
     * Method called when creating a new header to check if the code already
     * exists
     * 
     * @return
     */
    public String checkIdAvailability()
    {
	try
	{

	    GLSHEADERVO glsheaderVO = new GLSHEADERVO();
	    glsheaderVO.setCODE(code);
	    code = new BigDecimal(Integer.toString(templateHeaderBO.checkIdAvailability(glsheaderVO)));
	    if(code.equals(BigDecimal.ONE))
	    {
		throw new BOException("");
	    }

	}
	catch(Exception e)
	{
	    handleException(e, "", "tmplHeader.codeExists");
	}
	return "json";
    }

    public Object getModel()
    {
	return glsheaderSC;
    }

    /**
     * Method called when saving a newly created or updating a new header
     * 
     * @return
     */
    public String save()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String pageRef = get_pageRef();
	    String appName = sessionCO.getCurrentAppName();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String lang = sessionCO.getLanguage();
	    AuditRefCO refCO = initAuditRefCO();
	    GLSHEADERVO glsheaderVO = glsheaderCO.getGlsheaderVO();
	    // replace values in VO before save
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, String> sessionProps = repSessionCO.getSessionProps();
	    HashMap<String, String> reportProps = repSessionCO.getReportProps();
	    String[] propsArr = { "LEFT1", "LEFT2", "LEFT3", "LEFT4", "CENTER1", "CENTER2", "CENTER3", "RIGHT1",
		    "RIGHT2", "RIGHT3", "RIGHT4" };
	    String propValue;
	    for(int i = 0; i < propsArr.length; i++)
	    {
		propValue = (String) PathPropertyUtil.returnProperty(glsheaderVO, propsArr[i]);
		if(sessionProps.containsKey(propValue))
		{
		    PathPropertyUtil.setProperty(glsheaderVO, propsArr[i], sessionProps.get(propValue));
		}
		else if(reportProps.containsKey(propValue))
		{
		    PathPropertyUtil.setProperty(glsheaderVO, propsArr[i], reportProps.get(propValue));
		}
	    }

	    code = new BigDecimal((templateHeaderBO.checkIdAvailability(glsheaderVO)));
	    if(code.intValue() == 0)
	    {
		mode = "add";
	    }
	    if(this.mode.equals("add"))
	    {
		if(code.intValue() == 0)
		{
		    refCO.setOperationType(AuditConstant.CREATED);
		    refCO.setKeyRef(AuditConstant.TEMPLATE_HEADER_KEY_REF);
		    refCO.setProgRef(pageRef);
		    refCO.setAppName(appName);
		    glsheaderCO.getGlsheaderVO().setAuditRefCO(refCO);
		    templateHeaderBO.insertTemplateHeader(glsheaderCO, pageRef, compCode, appName, lang);
		}
		else
		{
		    this.addActionMessage("tmplHeader.codeExists");
		}
	    }
	    else
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_HEADER_KEY_REF);
		refCO.setProgRef(get_pageRef());
		refCO.setAppName(sessionCO.getCurrentAppName());
		glsheaderCO.getGlsheaderVO().setAuditRefCO(refCO);
		GLSHEADERVO oldGlsheaderVO = ((GLSHEADERVO) returnAuditObject(GLSHEADERVO.class));
		refCO.setAuditObj(oldGlsheaderVO);
		templateHeaderBO.updateTemplateHeader(glsheaderCO, pageRef, compCode, appName, lang);
		GLSHEADERSC glsheaderSC = new GLSHEADERSC();
		glsheaderSC.setCODE(glsheaderCO.getGlsheaderVO().getCODE());
		glsheaderSC.setLovTypeId(RepConstantsCommon.SESSION_PROPERTIES);
		glsheaderSC.setRepLovTypeID(RepConstantsCommon.REPORT_PROPERTIES);
		glsheaderSC.setTemplateTypeID(RepConstantsCommon.TMPLT_TYPE_LOV_TYPE);
		glsheaderSC.setPreferredLanguage(sessionCO.getLanguage());
		glsheaderCO.getGlsheaderVO().setDATE_UPDATED(
			((templateHeaderBO.retrieveTemplateHeader(glsheaderSC)).getDATE_UPDATED()));
	    }
	    glsheaderCO.getGlsheaderVO().getAuditRefCO().setAuditObj(null);
	    putAuditObject(glsheaderCO.getGlsheaderVO());
	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	    return "error";
	}

	return "json";
    }

    /**
     * Mehtod called when updating an existing header to get all required data
     * 
     * @return
     * @throws Exception
     */
    public String retrieveTemplateHeader() throws Exception
    {
	try
	{
	    retTmpltTypeList();    
	    GLSHEADERSC glsheaderSC = new GLSHEADERSC();
	    glsheaderSC.setCODE(code);
	    SessionCO sessionCO = returnSessionObject();
	    glsheaderSC.setPreferredLanguage(sessionCO.getLanguage());
	    glsheaderSC.setLovTypeId(RepConstantsCommon.SESSION_PROPERTIES);
	    glsheaderSC.setRepLovTypeID(RepConstantsCommon.REPORT_PROPERTIES);
	    glsheaderSC.setTemplateTypeID(RepConstantsCommon.TMPLT_TYPE_LOV_TYPE);
	    glsheaderCO.setGlsheaderVO(templateHeaderBO.retrieveTemplateHeader(glsheaderSC));
	    if("createLike".equals(update))
	    {
		mode = "add";
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCode = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayCode.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayCode.setIS_READONLY(BigDecimal.ZERO);
		screenDisplayCode.setIS_MANDATORY(BigDecimal.ONE);
		getAdditionalScreenParams().put("glsheaderCO.glsheaderVO.CODE", screenDisplayCode);

		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayName = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayName.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayName.setIS_READONLY(BigDecimal.ZERO);
		screenDisplayName.setIS_MANDATORY(BigDecimal.ONE);
		getAdditionalScreenParams().put("glsheaderCO.glsheaderVO.BRIEF_NAME_ENG", screenDisplayName);
		glsheaderCO.getGlsheaderVO().setCODE(null);
	    }
	    else
	    {
		mode = "update";
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCode = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayCode.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayCode.setIS_READONLY(BigDecimal.ONE);
		screenDisplayCode.setIS_MANDATORY(BigDecimal.ZERO);
		getAdditionalScreenParams().put("glsheaderCO.glsheaderVO.CODE", screenDisplayCode);

		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayName = new SYS_PARAM_SCREEN_DISPLAYVO();
		screenDisplayName.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayName.setIS_READONLY(BigDecimal.ZERO);
		screenDisplayName.setIS_MANDATORY(BigDecimal.ONE);
		getAdditionalScreenParams().put("glsheaderCO.glsheaderVO.BRIEF_NAME_ENG", screenDisplayName);
		applyRetrieveAudit(AuditConstant.TEMPLATE_HEADER_KEY_REF, glsheaderCO.getGlsheaderVO(), glsheaderCO
			.getGlsheaderVO());
	    }
	}
	catch(Exception e)
	{
	    handleException(e, "tmplHeader.errorRetrieving.exceptionMsg._key", null);
	    // log.error(e, "Exception in TemplateHeaderAction");
	}
	return "successFrm";
    }

    /**
     * Method that prints the JRXML
     * 
     * @throws Exception
     */
    public String previewTemplateHeader() throws Exception
    {
	try
	{
	    byte[] bb = reportBO.addTemplateHeader(glsheaderCO.getGlsheaderVO());
	    response.setContentType("text/html");
	    response.getOutputStream().write(bb);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	    return null;
	}
	catch(Exception e)
	{
	    handleException(e, "tmplHeader.errorPreview.exceptionMsg._key", null);
	    // log.error(e, e.getMessage() +
	    // "Exception in TemplateHeaderAction");
	    return null;
	}
    }

    public String emptyTemplHeaderFrm() throws Exception
    {
	glsheaderCO = new GLSHEADERCO();
	mode = "add";
	// add readonly code
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySmart = new SYS_PARAM_SCREEN_DISPLAYVO();
	screenDisplaySmart.setIS_VISIBLE(BigDecimal.ONE);
	screenDisplaySmart.setIS_READONLY(BigDecimal.ZERO);
	screenDisplaySmart.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("glsheaderCO.glsheaderVO.CODE", screenDisplaySmart);
	retTmpltTypeList();
	return "successFrm";
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }
    
    public List retTmpltTypeList() throws BaseException
    {
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.TMPLT_TYPE_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	tmpltTypeList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	return tmpltTypeList;
    }


}
