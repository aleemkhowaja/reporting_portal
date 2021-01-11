package com.path.actions.reporting.ftr.template;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.template.FTR_TMPL_REFCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public class TemplateMaintExpressionAction extends ReportingLookupBaseAction implements ServletRequestAware,
	ServletResponseAware
{

    private GLSTMPLTCO glstmpltCO = new GLSTMPLTCO();
    private GLSTMPLTVO glstmpltVO = new GLSTMPLTVO();
    private String mode;
    private TemplateBO templateBO;
    private CommonLookupBO commonLookupBO;
    private List<Object> dispValArrListt; // to fill the display value combo
    private List<Object> bottomBorderArrList; // to fill the bottom border combo
    private File upload;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    private String contentType;
    private String fileName;
    private String caption;

    public String input() throws Exception
    {
	return SUCCESS;
    }

    public String getContentType()
    {

	return contentType;
    }

    public void setContentType(String contentType)
    {
	this.contentType = contentType;
    }

    public String getFileName()
    {
	return fileName;
    }

    public void setFileName(String fileName)
    {
	this.fileName = fileName;
    }

    public String getCaption()
    {
	return caption;
    }

    public void setCaption(String caption)
    {
	this.caption = caption;
    }

    public String upload() throws Exception
    {
	return SUCCESS;
    }

    public void setRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    public HttpServletResponse getResponse()
    {
	return response;
    }

    public void setResponse(HttpServletResponse response)
    {
	this.response = response;
    }

    public File getUpload()
    {
	return upload;
    }

    public void setUpload(File upload)
    {
	this.upload = upload;
    }

    public List<Object> getBottomBorderArrList()
    {
	return bottomBorderArrList;
    }

    public void setBottomBorderArrList(List<Object> bottomBorderArrList)
    {
	this.bottomBorderArrList = bottomBorderArrList;
    }

    public List<Object> getDispValArrListt()
    {
	return dispValArrListt;
    }

    public void setDispValArrListt(List<Object> dispValArrListt)
    {
	this.dispValArrListt = dispValArrListt;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public void setTemplateBO(TemplateBO templateBO)
    {
	this.templateBO = templateBO;
    }

    public GLSTMPLTVO getGlstmpltVO()
    {
	return glstmpltVO;
    }

    public void setGlstmpltVO(GLSTMPLTVO glstmpltVO)
    {
	this.glstmpltVO = glstmpltVO;
    }

    public GLSTMPLTCO getGlstmpltCO()
    {
	return glstmpltCO;
    }

    public void setGlstmpltCO(GLSTMPLTCO glstmpltCO)
    {
	this.glstmpltCO = glstmpltCO;
    }

    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public Object getModel()
    {
	return glstmpltCO;
    }

    /**
     * method that fills the related reports map
     * 
     * @param allTempl
     */
    public void fillRelatedReportsMap(GLSTMPLTCO allTempl)
    {
	try
	{
	    TmplGridSC tGridSC = new TmplGridSC();
	    tGridSC.setNbRec(10000);
	    tGridSC.setRelatedRepCodeParam(allTempl.getGlstmpltVO().getCODE().toString());
	    List<FTR_TMPL_REFCO> lrelatedRepCO = templateBO.getRelatedReportsList(tGridSC);
	    Iterator<FTR_TMPL_REFCO> it = lrelatedRepCO.iterator();
	    LinkedHashMap<String, FTR_TMPL_REFCO> lFtrTmplHash = new LinkedHashMap<String, FTR_TMPL_REFCO>();
	    while(it.hasNext())
	    {
		FTR_TMPL_REFCO lCO = it.next();
		lCO.setConcatKey(lCO.getFtrTmplRefVO().getCOMP_CODE().toString()
			+ lCO.getFtrTmplRefVO().getTEMPLATE_CODE().toString() + lCO.getFtrTmplRefVO().getREPORT_REF().toString()
			+ lCO.getFtrTmplRefVO().getAPP_NAME().toString());
		lFtrTmplHash.put(lCO.getConcatKey(), lCO);
	    }
	    allTempl.setRelatedReportsList(lFtrTmplHash);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * method called on the submit of lines
     * 
     * @return
     * @throws JSONException
     */
    public String submitAjax() throws JSONException
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	String mode = getMode();
	if(mode != null && mode.equals("open"))
	{
	    try
	    {

		GLSTMPLTCO oldTempl = repSessionCO.getAllTempl();

		GLSTMPLTCO newTemplCO = new GLSTMPLTCO();
		newTemplCO.setDispValArrList(oldTempl.getDispValArrList());
		dispValArrListt = oldTempl.getDispValArrList();
		if(dispValArrListt.isEmpty())
		{
		    // fill the combo of display values
		    SessionCO sessionCO = returnSessionObject();
		    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
		    sysParamLovVO.setLOV_TYPE_ID(BigDecimal.ONE); // 1
								     // =VALUE_FORMAT
								     // (check
								     // table
								     // sys_param_lov_type)
		    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
		    dispValArrListt = commonLookupBO.getLookupList(sysParamLovVO);
		}
		newTemplCO.getGlstmpltVO().setCODE(glstmpltCO.getGlstmpltVO().getCODE());
		newTemplCO.getGlstmpltVO().setBRIEF_NAME_ENG(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ENG());
		newTemplCO.getGlstmpltVO().setDISPLAY_VALUES(glstmpltCO.getGlstmpltVO().getDISPLAY_VALUES());
		newTemplCO.getGlstmpltVO().setCOMP_CODE(glstmpltCO.getGlstmpltVO().getCOMP_CODE());
		GLSTMPLTVO tmplVO = new GLSTMPLTVO();
		tmplVO.setCOMP_CODE(glstmpltCO.getGlstmpltVO().getCOMP_CODE());
		tmplVO.setCODE(glstmpltCO.getGlstmpltVO().getCODE());
		GLSTMPLTCO lGlstmpltCO = new GLSTMPLTCO();
		lGlstmpltCO.setGlstmpltVO(tmplVO);
		tmplVO.setLINE_NBR(BigDecimal.ZERO);
		tmplVO.setTEMPLATE_TYPE("P");
		tmplVO = templateBO.retrieveTemplate(tmplVO);
		newTemplCO.setGlstmpltVO(tmplVO);
		if(newTemplCO.getGlstmpltVO().getFCR().equals("Y"))
		{
		    newTemplCO.setFCRStr("true");
		}
		else
		{
		    newTemplCO.setFCRStr("false");
		}
		fillRelatedReportsMap(newTemplCO);
		repSessionCO.setAllTempl(newTemplCO);
		glstmpltCO = newTemplCO;
		// for the checkbox show/hide
		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayFcr = new SYS_PARAM_SCREEN_DISPLAYVO();
		if(glstmpltCO.getFCRStr().equals("true"))
		{
		    screenDisplayFcr.setIS_VISIBLE(BigDecimal.ONE);
		}
		else
		{
		    screenDisplayFcr.setIS_VISIBLE(BigDecimal.ZERO);
		}
		getAdditionalScreenParams().put("dispValComboId", screenDisplayFcr);
		getAdditionalScreenParams().put("forDec", screenDisplayFcr);
		// end for the checkbox show/hide
		// call Audit on Retrieve
		// code added temporarily for the smart

		AuditRefCO refCO = new AuditRefCO();
		refCO.setAppName("REP");
		refCO.setRunningDate(new Date());
		refCO.setUserID("BE");
		refCO.setProgRef("T00MT");
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		glstmpltCO.setAuditRefCO(refCO);
		glstmpltCO.getGlstmpltVO().setAuditRefCO(refCO);
		applyRetrieveAudit(AuditConstant.TEMPLATE_KEY_REF, glstmpltCO.getGlstmpltVO(), glstmpltCO);
	    }
	    catch(Exception e)
	    {
		log.error(e, "");
	    }

	    return "successMnt";
	}
	else
	// delete mode
	{
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    allTempl.getGlstmpltVO().setLINE_NBR(BigDecimal.ZERO);
	    allTempl.getGlstmpltVO().setTEMPLATE_TYPE("P");
	    GLSTMPLTVO tempVO = new GLSTMPLTVO();
	    tempVO.setCOMP_CODE(glstmpltCO.getGlstmpltVO().getCOMP_CODE());
	    tempVO.setCODE(glstmpltCO.getGlstmpltVO().getCODE());
	    tempVO.setTEMPLATE_TYPE("P");
	    tempVO.setLINE_NBR(BigDecimal.ZERO);
	    try
	    {
		AuditRefCO refCO = initAuditRefCO();
		refCO.setOperationType(AuditConstant.DELETE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		GLSTMPLTCO lGlstmpltCO = new GLSTMPLTCO();
		lGlstmpltCO.setGlstmpltVO(tempVO);
		templateBO.deleteTemplWithDetails(lGlstmpltCO, refCO);

		clearSession();
	    }
	    catch(BaseException e)
	    {
		handleException(e, null, null);
	    }
	    return "success";
	}
    }

    /**
     * method that removes criterias data from session
     */
    public void clearSession()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO newTemplCO = new GLSTMPLTCO();
	    newTemplCO.setDispValArrList((ArrayList<Object>) dispValArrListt);
	    newTemplCO.getGlstmpltVO().setBRIEF_NAME_ENG("");
	    newTemplCO.getGlstmpltVO().setDISPLAY_VALUES("D");
	    newTemplCO.getGlstmpltVO().setCODE(BigDecimal.ZERO);
	    newTemplCO.getGlstmpltVO().setCOMP_CODE(sessionCO.getCompanyCode());

	    // repSessionCO.setAllTempl(null);
	    repSessionCO.setAllTempl(newTemplCO);

	    glstmpltCO = new GLSTMPLTCO();
	    // glstmpltCO=new GLSTMPLTCO();

	    // fill the main screen properties
	    glstmpltCO.getGlstmpltVO().setBRIEF_NAME_ENG(newTemplCO.getGlstmpltVO().getBRIEF_NAME_ENG());
	    glstmpltCO.getGlstmpltVO().setCODE(newTemplCO.getGlstmpltVO().getCODE());
	    glstmpltCO.getGlstmpltVO().setDISPLAY_VALUES(newTemplCO.getGlstmpltVO().getDISPLAY_VALUES());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }

    /**
     * method that shows a line informations
     * 
     * @return
     * @throws JSONException
     */
    public String openLine() throws JSONException // open line Form
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String mode = request.getParameter("mode");

	    if(mode != null && !mode.equals("edit"))
	    {
		glstmpltCO = new GLSTMPLTCO();
		glstmpltCO.getGlstmpltVO().setBRIEF_NAME_ENG("");
		glstmpltCO.getGlstmpltVO().setADD_DESC("");
		glstmpltCO.getGlstmpltVO().setCODE((new BigDecimal("99")));
	    }
	    // fill the combo of border
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(3)); // 3 =BORDER (check
							     // table
							     // sys_param_lov_type
							     // in the xls file)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    bottomBorderArrList = commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    e.printStackTrace();
	}
	return SUCCESS;
    }

    @Override
    public void setServletRequest(HttpServletRequest arg0)
    {
	request = arg0;
    }
}
