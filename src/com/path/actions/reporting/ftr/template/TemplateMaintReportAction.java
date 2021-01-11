package com.path.actions.reporting.ftr.template;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.FTR_TMPL_REFVO;
import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.struts2.lib.common.base.PathActionContextMethods;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.common.select.SelectCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.FTR_ACCOUNTSCO;
import com.path.vo.reporting.ftr.template.FTR_ACCOUNTSSC;
import com.path.vo.reporting.ftr.template.FTR_MISMATCH_REPORTCO;
import com.path.vo.reporting.ftr.template.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.FTR_TMPL_REFCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTGLSDETCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTGLSDETSC;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;
import com.path.vo.reporting.ftr.template.GLSTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public class TemplateMaintReportAction extends ReportingLookupBaseAction implements ServletRequestAware,
	ServletResponseAware
{
    private List<String> operators;
    private String updates;
    private String updates1;
    private String tempCodeWithLineNb;

    private String contentType;
    private File upload;
    private String fileName;
    private String caption;

    protected HttpServletRequest request;

    private final TmplGridSC tmplGridSC = new TmplGridSC();
    private final CommonDetailsSC commonDetailsSC = new CommonDetailsSC();

    private GLSTMPLTCO templCO = new GLSTMPLTCO(); // used to fill the main form
    private GLSTMPLTCO glstmpltCO = new GLSTMPLTCO();
    private GLSTMPLTVO glstmpltVO = new GLSTMPLTVO();

    private GLSTMPLTGLSDETCO glCO; // used to fill the GL form
    private GLSTMPLT_PARAM_LINKCO crtCO; // used to fill the CRT form
    private FTR_TMPLT_EXPRCO exprOrGLByLineCO;
    private GLSTMPLTVO checkTemplCodeVO;// used to fill the info when checking
    // the existence of the inserted
    // template code
    private GLSTMPLTGLSDETCO checkSubglstmpltCO;// used to fill the info when
    // checking the existence of the
    // inserted subLineNb in GL form
    /*
     * used to apply dependency between the currency code and the currency value
     */
    private GLSTMPLTVO currencyDependencyVO; 
    private CommonDetailsVO commonDetVO; // used to apply dependency between the
    // GL lookups code and value
    private String mode;

    private TemplateBO templateBO;
    private CommonLookupBO commonLookupBO;
    private CommonLibBO commonLibBO;
    /*
     * to fill the display value combo
     */
    private ArrayList<Object> dispValArrListt = new ArrayList<Object>(); 
    /*
     * to fill the bottom border combo
     */
    private ArrayList<Object> bottomBorderArrList = new ArrayList<Object>(); 
    /*
     * to fill the calculationType combo
     */
    private ArrayList<Object> calcTypeArrList = new ArrayList<Object>(); 
    /*
     * to fill the operator combo
     */
    private ArrayList<Object> operatorArrList = new ArrayList<Object>(); 
    private ArrayList<Object> glDispArrList = new ArrayList<Object>();
    /*
     * used to get the operation type when deleting a row from the grid
     */
    private String oper; 
    private String str;
    private String str1;
    private String str2;
    private String str3;
    private BigDecimal code;
    private BigDecimal lineNbrDet;
    private BigDecimal code1;
    private BigDecimal column1; // first parameter sent from the lookup to the
    // server side
    private BigDecimal templateCode;
    private String lineNumber;

    private String EXP_TYPE;
    private String VALUE;
    private String tempCode_LineNb;
    private String groupComboList = "";
    private String updateRelReports;
    private String updateSelecAcc;
    private String relatedRepCode;
    private String reportRef;
    private String reportName;
    private String templateOrder;
    private String appName;
    private BigDecimal interval;
    private BigDecimal startingLineValue;
    private int previousValue;// for the reorganize line functions
    // (addHashmap and dbHashMap share same values
    int firstLoop;// for the reorganize line functions (addHashmap and
    // dbHashMap share same values
    private BigDecimal valueToRemember;
    private int newLineIndicator;
    private BigDecimal div;
    private BigDecimal dpt;
    private BigDecimal fromGL;
    private BigDecimal toGL;
    private BigDecimal newCode;
    private String newLineNumber;  
    private ArrayList<SYS_PARAM_LOV_TRANSVO> daysMonthYear;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> maleFemaleList;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> securityClassList;
    private BigDecimal cmpCode;
    private BigDecimal brCode;

    public BigDecimal getCmpCode()
    {
        return cmpCode;
    }

    public void setCmpCode(BigDecimal cmpCode)
    {
        this.cmpCode = cmpCode;
    }

    public BigDecimal getBrCode()
    {
        return brCode;
    }

    public void setBrCode(BigDecimal brCode)
    {
        this.brCode = brCode;
    }

    public String getStr3()
    {
        return str3;
    }

    public void setStr3(String str3)
    {
        this.str3 = str3;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDaysMonthYear()
    {
        return daysMonthYear;
    }

    public void setDaysMonthYear(ArrayList<SYS_PARAM_LOV_TRANSVO> daysMonthYear)
    {
        this.daysMonthYear = daysMonthYear;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getMaleFemaleList()
    {
        return maleFemaleList;
    }

    public void setMaleFemaleList(ArrayList<SYS_PARAM_LOV_TRANSVO> maleFemaleList)
    {
        this.maleFemaleList = maleFemaleList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getSecurityClassList()
    {
        return securityClassList;
    }

    public void setSecurityClassList(ArrayList<SYS_PARAM_LOV_TRANSVO> securityClassList)
    {
        this.securityClassList = securityClassList;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public String getStr2()
    {
	return str2;
    }

    public void setStr2(String str2)
    {
	this.str2 = str2;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public String getNewLineNumber()
    {
	return newLineNumber;
    }

    public void setNewLineNumber(String newLineNumber)
    {
	this.newLineNumber = newLineNumber;
    }

    public ArrayList<Object> getGlDispArrList()
    {
	return glDispArrList;
    }

    public void setGlDispArrList(ArrayList<Object> glDispArrList)
    {
	this.glDispArrList = glDispArrList;
    }

    public BigDecimal getNewCode()
    {
	return newCode;
    }

    public void setNewCode(BigDecimal newCode)
    {
	this.newCode = newCode;
    }

    public String getUpdateSelecAcc()
    {
	return updateSelecAcc;
    }

    public void setUpdateSelecAcc(String updateSelecAcc)
    {
	this.updateSelecAcc = updateSelecAcc;
    }

    public BigDecimal getDiv()
    {
	return div;
    }

    public void setDiv(BigDecimal div)
    {
	this.div = div;
    }

    public BigDecimal getDpt()
    {
	return dpt;
    }

    public void setDpt(BigDecimal dpt)
    {
	this.dpt = dpt;
    }

    public BigDecimal getFromGL()
    {
	return fromGL;
    }

    public void setFromGL(BigDecimal fromGL)
    {
	this.fromGL = fromGL;
    }

    public BigDecimal getToGL()
    {
	return toGL;
    }

    public void setToGL(BigDecimal toGL)
    {
	this.toGL = toGL;
    }

    public BigDecimal getValueToRemember()
    {
	return valueToRemember;
    }

    public void setValueToRemember(BigDecimal valueToRemember)
    {
	this.valueToRemember = valueToRemember;
    }

    public BigDecimal getInterval()
    {
	return interval;
    }

    public void setInterval(BigDecimal interval)
    {
	this.interval = interval;
    }

    public BigDecimal getStartingLineValue()
    {
	return startingLineValue;
    }

    public void setStartingLineValue(BigDecimal startingLineValue)
    {
	this.startingLineValue = startingLineValue;
    }

    public BigDecimal getLineNbrDet()
    {
	return lineNbrDet;
    }

    public void setLineNbrDet(BigDecimal lineNbrDet)
    {
	this.lineNbrDet = lineNbrDet;
    }

    public String getUploadFileName()
    {
	return fileName;
    }

    public void setUploadFileName(String fileName)
    {
	this.fileName = fileName;
    }

    public String getUploadContentType()
    {
	return contentType;
    }

    public void setUploadContentType(String contentType)
    {
	this.contentType = contentType;
    }

    public File getUpload()
    {
	return upload;
    }

    public void setUpload(File upload)
    {
	this.upload = upload;
    }

    public String getCaption()
    {
	return caption;
    }

    public void setCaption(String caption)
    {
	this.caption = caption;
    }

    public String getReportRef()
    {
	return reportRef;
    }

    public void setReportRef(String reportRef)
    {
	this.reportRef = reportRef;
    }

    public String getReportName()
    {
	return reportName;
    }

    public void setReportName(String reportName)
    {
	this.reportName = reportName;
    }

    public String getTemplateOrder()
    {
	return templateOrder;
    }

    public void setTemplateOrder(String templateOrder)
    {
	this.templateOrder = templateOrder;
    }

    public String getAppName()
    {
	return appName;
    }

    public void setAppName(String appName)
    {
	this.appName = appName;
    }

    public String getRelatedRepCode()
    {
	return relatedRepCode;
    }

    public void setRelatedRepCode(String relatedRepCode)
    {
	this.relatedRepCode = relatedRepCode;
    }

    public String getUpdateRelReports()
    {
	return updateRelReports;
    }

    public void setUpdateRelReports(String updateRelReports)
    {
	this.updateRelReports = updateRelReports;
    }

    public String getGroupComboList()
    {
	return groupComboList;
    }

    public void setGroupComboList(String groupComboList)
    {
	this.groupComboList = groupComboList;
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

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
	this.commonLibBO = commonLibBO;
    }

    public String getTempCode_LineNb()
    {
	return tempCode_LineNb;
    }

    public void setTempCode_LineNb(String tempCodeLineNb)
    {
	tempCode_LineNb = tempCodeLineNb;
    }

    public String getEXP_TYPE()
    {
	return EXP_TYPE;
    }

    public void setEXP_TYPE(String eXPTYPE)
    {
	EXP_TYPE = eXPTYPE;
    }

    public String getVALUE()
    {
	return VALUE;
    }

    public void setVALUE(String vALUE)
    {
	VALUE = vALUE;
    }

    public BigDecimal getTemplateCode()
    {
	return templateCode;
    }

    public void setTemplateCode(BigDecimal templateCode)
    {
	this.templateCode = templateCode;
    }

    public String getLineNumber()
    {
	return lineNumber;
    }

    public void setLineNumber(String lineNumber)
    {
	this.lineNumber = lineNumber;
    }

    public BigDecimal getColumn1()
    {
	return column1;
    }

    public void setColumn1(BigDecimal column1)
    {
	this.column1 = column1;
    }

    public CommonDetailsVO getCommonDetVO()
    {
	return commonDetVO;
    }

    public void setCommonDetVO(CommonDetailsVO commonDetVO)
    {
	this.commonDetVO = commonDetVO;
    }

    public GLSTMPLTVO getCurrencyDependencyVO()
    {
	return currencyDependencyVO;
    }

    public void setCurrencyDependencyVO(GLSTMPLTVO currencyDependencyVO)
    {
	this.currencyDependencyVO = currencyDependencyVO;
    }

    public String getTempCodeWithLineNb()
    {
	return tempCodeWithLineNb;
    }

    public void setTempCodeWithLineNb(String tempCodeWithLineNb)
    {
	this.tempCodeWithLineNb = tempCodeWithLineNb;
    }

    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public ArrayList<Object> getDispValArrListt()
    {
	return dispValArrListt;
    }

    public void setDispValArrListt(ArrayList<Object> dispValArrListt)
    {
	this.dispValArrListt = dispValArrListt;
    }

    public ArrayList<Object> getBottomBorderArrList()
    {
	return bottomBorderArrList;
    }

    public void setBottomBorderArrList(ArrayList<Object> bottomBorderArrList)
    {
	this.bottomBorderArrList = bottomBorderArrList;
    }

    public ArrayList<Object> getCalcTypeArrList()
    {
	return calcTypeArrList;
    }

    public void setCalcTypeArrList(ArrayList<Object> calcTypeArrList)
    {
	this.calcTypeArrList = calcTypeArrList;
    }

    public ArrayList<Object> getOperatorArrList()
    {
	return operatorArrList;
    }

    public void setOperatorArrList(ArrayList<Object> operatorArrList)
    {
	this.operatorArrList = operatorArrList;
    }

    public Object getModel()
    {
	return tmplGridSC;
    }

    /**
     * Method that submits all the informations
     * 
     * @return
     * @throws Exception
     */
    public String submitAll() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    String mode = getMode();
	    if(mode != null && mode.equals("add"))
	    {
		clearSession();
	    }
	    else
	    {

		BigDecimal tempCode = templCO.getGlstmpltVO().getCODE();
		templCO.getGlstmpltVO().setCODE(tempCode);
		if(tempCode == null)
		{
		    tempCode = new BigDecimal("-1");
		}
		BigDecimal compCode = sessionCO.getCompanyCode();

		GLSTMPLTCO sessionTmplCO = repSessionCO.getAllTempl();
		dispValArrListt = sessionTmplCO.getDispValArrList();

		// if mode=delete
		if(mode != null && mode.equals("delete"))
		{
		    GLSTMPLTVO tempVO = new GLSTMPLTVO();
		    tempVO.setCOMP_CODE(compCode);
		    tempVO.setCODE(tempCode);

		    AuditRefCO refCO = initAuditRefCO();
		    refCO.setOperationType(AuditConstant.DELETE);
		    refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		    GLSTMPLTCO lGlstmpltCO = new GLSTMPLTCO();
		    lGlstmpltCO.setGlstmpltVO(tempVO);
		    templateBO.deleteTemplWithDetails(lGlstmpltCO, refCO);
		    // clear the session
		    clearSession();
		}
	    }

	}
	catch(BaseException e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that shows/hide the financial customized reports
     * 
     * @return
     * @throws Exception
     */
    public String fcrDependency() throws Exception
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayFcr = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(glstmpltCO.getFCRStr().equals("true"))
	    {
		screenDisplayFcr.setIS_VISIBLE(BigDecimal.ONE);
		glstmpltCO.getGlstmpltVO().setDISPLAY_VALUES(RepConstantsCommon.FCR_DEFAULT_RA_FORMAT);
	    }
	    else
	    {
		screenDisplayFcr.setIS_VISIBLE(BigDecimal.ZERO);
		glstmpltCO.getGlstmpltVO().setDISPLAY_VALUES(RepConstantsCommon.FCR_DEFAULT_RA_FORMAT);
	    }
	    getAdditionalScreenParams().put("dispValComboId", screenDisplayFcr);
	    getAdditionalScreenParams().put("forDec", screenDisplayFcr);

	    return "success";
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }

    /**
     * method that a template doesn't have any line
     * 
     * @param allTempl
     * @throws Exception
     */
    public void checkIfTemplateWithoutLines(GLSTMPLTCO allTempl) throws Exception
    {
	if(allTempl.getDbLinesMap().size() == 0 && allTempl.getModifLinesMap().size() == 0
		&& allTempl.getAddLinesMap().size() == 0)
	{
	    BOException e = new BOException();
	    e.setMsgIdent("no lines in tempalte being created");
	    throw e;
	}
    }

    /**
     * method that saves all the template's data in the database
     * 
     * @return
     * @throws Exception
     */
    public String saveAll() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    if(allTempl!=null && allTempl.getGlstmpltVO().getCODE().compareTo(glstmpltCO.getGlstmpltVO().getCODE())!=0)
	    {
		throw new BOException(MessageCodes.RECORD_CHANGED);
	    }	    
	    allTempl.getGlstmpltVO().setBRIEF_NAME_ENG(glstmpltCO.getTemplateTitle());
	    allTempl.setTemplateTitle(glstmpltCO.getTemplateTitle());
	    checkIfTemplateWithoutLines(allTempl);
	    if(getAuditTrxNbr() == null || "".equals(getAuditTrxNbr()))
	    {
		String trxNb = sessionCO.getCompanyCode().toString() + sessionCO.getBranchCode().toString()
			+ glstmpltCO.getGlstmpltVO().getCODE().toString();
		setAuditTrxNbr(trxNb);
	    }
	    if(glstmpltCO.getGlstmpltVO().getPER_LINE_GL() != null)
	    {
		allTempl.getGlstmpltVO().setPER_LINE_GL(glstmpltCO.getGlstmpltVO().getPER_LINE_GL());
	    }
	    if(glstmpltCO.getFCRStr().equalsIgnoreCase("true"))// validating
	    {
		allTempl.getGlstmpltVO().setPER_LINE_GL(glstmpltCO.getGlstmpltVO().getFCR_EXCEL_PATH());
	    }
	    int flag = 0;
	    if(allTempl == null)
	    {
		allTempl = new GLSTMPLTCO();
		allTempl.getGlstmpltVO().setCODE(glstmpltCO.getGlstmpltVO().getCODE());
		allTempl.getGlstmpltVO().setLINE_NBR(BigDecimal.ONE);
		flag = 1;
	    }
	    if(null == allTempl.getGlstmpltVO().getLINE_NBR())
	    {
		allTempl.getGlstmpltVO().setLINE_NBR(BigDecimal.ZERO);
	    }
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    // issue : disregard the line_nbr if the line has been added in the
	    // template line's grid.but test for a newly created template
	    // for the case where we are adding a template for the first time
	    if(null == allTempl.getGlstmpltVO().getCODE() && null != glstmpltCO.getGlstmpltVO().getCODE())
	    {
		allTempl.getGlstmpltVO().setCODE(glstmpltCO.getGlstmpltVO().getCODE());
		allTempl.getGlstmpltVO().setBRIEF_NAME_ENG(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ENG());
	    }

	    templateBO.checkRequiredFields(allTempl, this.get_pageRef(), compCode, sessionCO.getCurrentAppName(),
		    sessionCO.getLanguage());
	    if(flag == 1)
	    {
		allTempl = new GLSTMPLTCO();
	    }
	    BigDecimal tempCode = allTempl.getGlstmpltVO().getCODE();
	    if(tempCode == null || tempCode.intValue() == 0)
	    {
		tempCode = glstmpltCO.getGlstmpltVO().getCODE();
	    }

	    // check if it is a new template or we are updating an existing one
	    GLSTMPLTVO lGlstmpltVO = new GLSTMPLTVO();
	    lGlstmpltVO.setCOMP_CODE(compCode);
	    lGlstmpltVO.setCODE(tempCode);

	    GLSTMPLTCO lGlstmpltCO = new GLSTMPLTCO();
	    GLSTMPLTVO existTemplVO = new GLSTMPLTVO();
	    lGlstmpltCO.setGlstmpltVO(existTemplVO);

	    lGlstmpltVO.setLINE_NBR(BigDecimal.ZERO);
	    lGlstmpltVO.setTEMPLATE_TYPE("P");
	    existTemplVO = templateBO.checkTemplCode(lGlstmpltVO);
	    if(existTemplVO == null)
	    {
		lGlstmpltCO.setGlstmpltVO(null);
	    }

	    // stopped
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setTrxNbr(getAuditTrxNbr());
	    // set oldTemplVO stopped this audit momentarily
	    if(allTempl.getGlstmpltVO().getCODE() != null)
	    {
		try
		{
		    refCO.setAuditObj(returnAuditObject(GLSTMPLTVO.class));
		}
		catch(Exception e)
		{
		    log.error(e, "Error occurred in TemplateMaintReportAction.saveAll");
		}
	    }
	    // end

	    GLSTMPLTVO template = new GLSTMPLTVO();
	    template.setCOMP_CODE(compCode);
	    template.setCODE(tempCode);
	    template.setBRIEF_NAME_ENG(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ENG());
	    template.setBRIEF_NAME_ARAB(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ARAB());
	    template.setDISPLAY_VALUES(glstmpltCO.getGlstmpltVO().getDISPLAY_VALUES());
	    template.setLINE_NBR(BigDecimal.ZERO);
	    template.setTEMPLATE_TYPE("P");
	    template.setPER_LINE_GL(glstmpltCO.getGlstmpltVO().getPER_LINE_GL());
	    if("true".equals(glstmpltCO.getFCRStr()))
	    {
		template.setFCR("Y");
	    }
	    else
	    {
		template.setFCR("N");
	    }
	    if(existTemplVO == null)
	    {
		template.setDATE_CREATED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
	    }
	    else
	    {
		template.setDATE_MODIFIED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
	    }
	    GLSTMPLTCO llGlstmpltCO = new GLSTMPLTCO();
	    llGlstmpltCO.setGlstmpltVO(template);
	    // llGlstmpltCO is the line nbr 0 being sent.the allTempl contains
	    // the lines
	    templateBO.saveTemplWithDetails(lGlstmpltCO, llGlstmpltCO, tempCode, returnUserName(), allTempl, refCO,
		    sessionCO.getLanguage());

	}
	catch(BOException e)
	{
	    if(null != e.getMsgIdent() && e.getMsgIdent().equals("regExp"))
	    {
		handleException(null, null, "template.missingExt");
		return ERROR;
	    }

	    else if("no lines in tempalte being created".equals(e.getMsgIdent()))
	    {
		handleException(null, null, "template.creation");
		return ERROR;
	    }
	    else
	    {
		handleException(e, null, null);
	    }

	}
	catch(Exception e)
	{
	    handleException(e, "ctrlRecord.errorInsertUpdate.exeptionMsg._key",
		    "ctrlRecord.errorInsertUpdate.exeptionMsg._key");
	}

	return SUCCESS;
    }

    /**
     * method that resets the session data
     */
    public void clearSession()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO newTemplCO = new GLSTMPLTCO();
	    newTemplCO.setDispValArrList(dispValArrListt);
	    newTemplCO.getGlstmpltVO().setBRIEF_NAME_ENG("");
	    newTemplCO.getGlstmpltVO().setDISPLAY_VALUES("D");
	    newTemplCO.getGlstmpltVO().setCODE(BigDecimal.ZERO);
	    newTemplCO.getGlstmpltVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    repSessionCO.setAllTempl(newTemplCO);
	    glstmpltCO = new GLSTMPLTCO();
	    templCO = new GLSTMPLTCO();
	    // fill the main screen properties
	    templCO.getGlstmpltVO().setBRIEF_NAME_ENG(newTemplCO.getGlstmpltVO().getBRIEF_NAME_ENG());
	    templCO.getGlstmpltVO().setCODE(newTemplCO.getGlstmpltVO().getCODE());
	    templCO.getGlstmpltVO().setDISPLAY_VALUES(newTemplCO.getGlstmpltVO().getDISPLAY_VALUES());
	}
	catch(Exception e)
	{

	    handleException(e, null, null);
	}
    }

    /**
     * method called on the load of the page
     */
    public String execute() throws Exception
    {
	try
	{
	    set_showSmartInfoBtn("true");
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    // clear the allTempl session when opening the Maintenance templ.
	    // menu
	    String menu = request.getParameter("menu");
	    if(menu != null && menu.equals("T00MT"))
	    {
		repSessionCO.setAllTempl(null);
		glstmpltCO = new GLSTMPLTCO();
		templCO = new GLSTMPLTCO();
	    }
	    // initialize the global session object 'allTempl'
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    if(allTempl == null)
	    {
		repSessionCO.setAllTempl(new GLSTMPLTCO());
		allTempl = repSessionCO.getAllTempl();
		allTempl.getGlstmpltVO().setBRIEF_NAME_ENG("");
		allTempl.getGlstmpltVO().setDISPLAY_VALUES("D");
		// get the compCode from the session initialize in the
		// desktopAction of the reporting
		allTempl.getGlstmpltVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    }
	    // initialize the templCO if empty
	    if(templCO == null)
	    {
		templCO = new GLSTMPLTCO();
	    }
	    // fill the main screen properties
	    templCO.getGlstmpltVO().setBRIEF_NAME_ENG(allTempl.getGlstmpltVO().getBRIEF_NAME_ENG());
	    templCO.getGlstmpltVO().setCODE(allTempl.getGlstmpltVO().getCODE());
	    templCO.getGlstmpltVO().setDISPLAY_VALUES(allTempl.getGlstmpltVO().getDISPLAY_VALUES());
	    // fill the combo of display values
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(BigDecimal.ONE); // 1 =VALUE_FORMAT
	    // (check table
	    // sys_param_lov_type)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    dispValArrListt = (ArrayList<Object>) commonLookupBO.getLookupList(sysParamLovVO);
	    // put the list inside the session in order to no go to the db each
	    // time we refresh the page
	    allTempl.setDispValArrList(dispValArrListt);
	    // fill the combo of border
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(3)); // 3 =BORDER (check
	    // table
	    // sys_param_lov_type
	    // in the xls file)
	    bottomBorderArrList = (ArrayList<Object>) commonLookupBO.getLookupList(sysParamLovVO);

	    SYS_PARAM_LOV_TRANSVO sysParamLovVO1 = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO1.setLANG_CODE(sessionCO.getLanguage());
	    sysParamLovVO1.setLOV_TYPE_ID(new BigDecimal(2));
	    glDispArrList = (ArrayList<Object>) commonLookupBO.getLookupList(sysParamLovVO1);

	    set_searchGridId("templGrid");
	    set_showNewInfoBtn("true");
	    loadGroupComboList();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayFcr = new SYS_PARAM_SCREEN_DISPLAYVO();
	    screenDisplayFcr.setIS_VISIBLE(BigDecimal.ZERO);
	    getAdditionalScreenParams().put("dispValComboId", screenDisplayFcr);
	    getAdditionalScreenParams().put("forDec", screenDisplayFcr);
	    return SUCCESS;
	}
	catch(RuntimeException e)
	{
	    throw e;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }

    /**
     * function applyAudit to make my audit and to initialize the audit value
     * 
     * @return string successJson
     */
    public String applyAudit()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String trxNb = sessionCO.getCompanyCode().toString() + sessionCO.getBranchCode().toString()
		    + getTemplateCode().toString();
	    setAuditTrxNbr(trxNb);
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    handleException(e, "Error in audit in template grid", "");
	    return null;
	}
    }

    /**
     * method that opens a template
     * 
     * @return
     * @throws JSONException
     */
    public String openTemplate() throws JSONException
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	SessionCO sessionCO = returnSessionObject();
	Map<String, String[]> paramMap = PathActionContextMethods.returnParameters();
	String[] modeParam = (String[]) paramMap.get("mode");
	String[] tempCodeParam = (String[]) paramMap.get("tempCode");
	String[] tempDescParam = (String[]) paramMap.get("tempDesc");
	String[] dispValParam = (String[]) paramMap.get("dispVal");
	String[] fcrParam = (String[]) paramMap.get("fcr");

	String mode = "";
	String tempCode = "";
	BigDecimal compCode = sessionCO.getCompanyCode();
	String tempDesc = "";
	String dispVal = "";

	if(modeParam != null)
	{
	    mode = modeParam[0];
	}

	if(tempCodeParam != null)
	{
	    tempCode = tempCodeParam[0];
	}
	if(tempDescParam != null)
	{
	    tempDesc = tempDescParam[0];
	}
	if(dispValParam != null)
	{
	    dispVal = dispValParam[0];
	}

	// delete template from the db
	if(mode != null && mode.equals("delete"))
	{
	    GLSTMPLTVO tempVO = new GLSTMPLTVO();
	    tempVO.setCOMP_CODE(compCode);
	    tempVO.setCODE(new BigDecimal(tempCode));
	    try
	    {
		AuditRefCO refCO = initAuditRefCO();
		refCO.setOperationType(AuditConstant.DELETE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		GLSTMPLTCO lGlstmpltCO = new GLSTMPLTCO();
		lGlstmpltCO.setGlstmpltVO(tempVO);
		templateBO.deleteTemplWithDetails(lGlstmpltCO, refCO);
	    }
	    catch(BaseException e)
	    {
		handleException(e, null, null);
	    }
	}
	else if(mode != null && mode.equals("open"))
	{
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    allTempl.getGlstmpltVO().setDISPLAY_VALUES(dispVal);
	    allTempl.getGlstmpltVO().setCODE(new BigDecimal(tempCode));
	    allTempl.getGlstmpltVO().setBRIEF_NAME_ENG(tempDesc);
	    allTempl.getGlstmpltVO().setCOMP_CODE(compCode);
	}

	return "success";
    }

    /**
     * method that saves the template financial checkbox details
     * 
     * @return
     * @throws JSONException
     */
    public String saveTemplDetails() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    allTempl.getGlstmpltVO().setDISPLAY_VALUES(glstmpltCO.getGlstmpltVO().getDISPLAY_VALUES());
	    allTempl.getGlstmpltVO().setCODE(glstmpltCO.getGlstmpltVO().getCODE());
	    allTempl.getGlstmpltVO().setBRIEF_NAME_ENG(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ENG());
	    allTempl.getGlstmpltVO().setCOMP_CODE(compCode);
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }

    /**
     * Method called on the load of the template's main grid
     * 
     * @return
     * @throws JSONException
     */
    public String loadTemplGrid() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(tmplGridSC);
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    tmplGridSC.setCOMP_CODE(compCode);
	    List<GLSTMPLTCO> lstTempl = templateBO.getAllTemplates(tmplGridSC);
	    int templCount = templateBO.getAllTemplCount(tmplGridSC);
	    setRecords(templCount);
	    setGridModel(lstTempl);

	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the load of the related reports grid
     * 
     * @return
     * @throws JSONException
     */
    public String relatedRepGridUrl() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO sessionRep = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO glstmpltCO = sessionRep.getAllTempl();
	    copyproperties(tmplGridSC);
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    tmplGridSC.setCOMP_CODE(compCode);
	    tmplGridSC.setRelatedRepCodeParam(getRelatedRepCode());
	    LinkedHashMap<String, FTR_TMPL_REFCO> dbLinesMap = glstmpltCO.getRelatedReportsList();
	    LinkedHashMap<String, FTR_TMPL_REFCO> addLinesMap = glstmpltCO.getAddedReportsList();
	    LinkedHashMap<String, FTR_TMPL_REFCO> updatedReportsList = glstmpltCO.getUpdatedReportsList();
	    HashMap<String, FTR_TMPL_REFCO> delLinesMap = glstmpltCO.getDeletedReportsList();
	    LinkedHashMap<String, FTR_TMPL_REFCO> finalMap = new LinkedHashMap<String, FTR_TMPL_REFCO>();

	    /*
	     * it means when the dbLinesMap is empty (not loaded yet) and it is
	     * no empty because all the records are deleted
	     */
	    if(dbLinesMap == null || (dbLinesMap.isEmpty() && delLinesMap.isEmpty())) 
	    {
		List<FTR_TMPL_REFCO> relDBList = templateBO.getRelatedReportsList(tmplGridSC);
		for(int i = 0; i < relDBList.size(); i++)
		{
		    FTR_TMPL_REFCO ftrTmplREFCO = relDBList.get(i);

		    dbLinesMap.put(ftrTmplREFCO.getConcatKey(), ftrTmplREFCO);
		}
		glstmpltCO.setRelatedReportsList(dbLinesMap);
	    }
	    finalMap.putAll(dbLinesMap);
	    finalMap.putAll(addLinesMap);

	    copyproperties(tmplGridSC);

	    int nbRec = tmplGridSC.getNbRec();
	    int recToSkip = tmplGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(finalMap.size() < maxRec)
	    {
		maxRec = finalMap.size();
	    }
	    ArrayList<FTR_TMPL_REFCO> lst = new ArrayList<FTR_TMPL_REFCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((FTR_TMPL_REFCO) finalMap.values().toArray()[i]);
	    }

	    // for the modified values replace old ones with new ones in lst
	    Iterator<FTR_TMPL_REFCO> it = lst.iterator();
	    while(it.hasNext())
	    {
		FTR_TMPL_REFCO ftrTmplRefCO = it.next();
		FTR_TMPL_REFCO updatedftrTmplRefCO = updatedReportsList.get(ftrTmplRefCO.getConcatKey());
		if(null != updatedftrTmplRefCO)
		{
		    ftrTmplRefCO.getFtrTmplRefVO().setTEMPLATE_CODE(
			    updatedftrTmplRefCO.getFtrTmplRefVO().getTEMPLATE_CODE());
		    ftrTmplRefCO.getFtrTmplRefVO().setTEMPLATE_ORDER(
			    updatedftrTmplRefCO.getFtrTmplRefVO().getTEMPLATE_ORDER());
		    ftrTmplRefCO.getFtrTmplRefVO().setAPP_NAME(updatedftrTmplRefCO.getFtrTmplRefVO().getAPP_NAME());
		    ftrTmplRefCO.getFtrTmplRefVO().setREPORT_REF(updatedftrTmplRefCO.getFtrTmplRefVO().getREPORT_REF());
		}
	    }

	    setRecords(finalMap.size());
	    setGridModel(lst);

	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String checkOrderInOtherTemplate() throws JSONException
    {
	try
	{
	    TmplGridSC sc = new TmplGridSC();
	    sc.setREPORT_REF(updates);
	    sc.setTEMPLATE_ORDER(code);
	    updates1 = templateBO.checkOrderInOtherTemplate(sc).toString();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that deletes all the related report' s data
     * 
     * @return
     * @throws JSONException
     */
    public String deleteRelRep() throws JSONException
    {
	try
	{
	    ReportingSessionCO sessionRep = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    LinkedHashMap<String, FTR_TMPL_REFCO> deletedReportsListMap = sessionRep.getAllTempl()
		    .getDeletedReportsList();

	    FTR_TMPL_REFVO ftrTmplRefVO = new FTR_TMPL_REFVO();
	    ftrTmplRefVO.setAPP_NAME(appName);
	    ftrTmplRefVO.setTEMPLATE_CODE(templateCode);
	    ftrTmplRefVO.setCOMP_CODE(compCode);
	    ftrTmplRefVO.setREPORT_REF(reportRef);
	    if(RepConstantsCommon.EMPTY_STRING.equals(templateOrder))
	    {
		templateOrder = "0";
	    }
	    ftrTmplRefVO.setTEMPLATE_ORDER(new BigDecimal(templateOrder));
	    String concatKey = compCode.toString() + templateCode.toString() + reportRef.toString()
		    + appName.toString();
	    FTR_TMPL_REFCO ftrTmplRefCO = new FTR_TMPL_REFCO();
	    ftrTmplRefCO.setConcatKey(concatKey);
	    ftrTmplRefCO.setFtrTmplRefVO(ftrTmplRefVO);
	    if(sessionRep.getAllTempl().getRelatedReportsList().get(ftrTmplRefCO.getConcatKey()) == null)
	    {
		sessionRep.getAllTempl().getAddedReportsList().remove(ftrTmplRefCO.getConcatKey());
	    }
	    else
	    {
		deletedReportsListMap.put(ftrTmplRefCO.getConcatKey(), ftrTmplRefCO);
		sessionRep.getAllTempl().getRelatedReportsList().remove(ftrTmplRefCO.getConcatKey());
	    }

	}
	catch(Exception e)
	{
	    handleException(e, "tmplHeader.errorDeleting.exceptionMsg._key", null);

	}
	return SUCCESS;
    }

    private void loadGroupComboList()
    {
	try
	{
	    ArrayList<SelectCO> returnLOV = new ArrayList<SelectCO>();
	    SelectCO selectCO1 = new SelectCO();
	    selectCO1.setCode("1");
	    selectCO1.setDescValue("1");
	    SelectCO selectCO2 = new SelectCO();
	    selectCO2.setCode("2");
	    selectCO2.setDescValue("2");
	    SelectCO selectCO3 = new SelectCO();
	    selectCO3.setCode("3");
	    selectCO3.setDescValue("3");
	    SelectCO selectCO4 = new SelectCO();
	    selectCO4.setCode("0");
	    selectCO4.setDescValue(getText("template.noOrder"));
	    returnLOV.add(selectCO1);
	    returnLOV.add(selectCO2);
	    returnLOV.add(selectCO3);
	    returnLOV.add(selectCO4);
	    for(SelectCO aSelectCO : returnLOV)
	    {
		String code = aSelectCO.getCode();
		String descValue = aSelectCO.getDescValue();
		groupComboList += code + ":" + descValue + ";";
	    }
	    if(groupComboList.length() > 0)
	    {
		groupComboList = ":;" + groupComboList.substring(0, groupComboList.length() - 1);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "Error occurred in TemplateMaintReportAction.loadGroupComboList");
	}
    }

    /**
     * method that generates a subline for the expressions lookup
     * 
     * @return
     * @throws JSONException
     */
    public String genExpLineLkp() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> finalMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();
	    finalMap.putAll(allTempl.getDbLinesMap());
	    finalMap.putAll(allTempl.getAddLinesMap());
	    List<GLSTMPLTCO> expLinesList = new ArrayList<GLSTMPLTCO>(finalMap.values());
	    setRecords(expLinesList.size());
	    setGridModel(expLinesList);
	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";
    }

    /**
     * method called to generate a line number for the exprssions
     * 
     * @return
     * @throws JSONException
     */
    public String genExpLineLkpColl() throws JSONException // load the
    // exprLineLkp grid
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> finalMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();
	    finalMap.putAll(allTempl.getDbLinesMap());
	    finalMap.putAll(allTempl.getAddLinesMap());
	    List<GLSTMPLTCO> expLinesList = new ArrayList<GLSTMPLTCO>(finalMap.values());

	    setRecords(expLinesList.size());
	    setGridModel(expLinesList);
	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that checks for the existance of a line number
     * 
     * @param lineNumber
     * @return
     */
    public boolean checkIfAlreadyExistingLine(int lineNumber)
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	// if reorganize button clicked , all the lines will be present in the
	// dblinesmap,even new ones
	LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	String keyNewLine = allTempl.getGlstmpltVO().getCOMP_CODE().toString()
		+ allTempl.getGlstmpltVO().getCODE().toString() + lineNumber;
	// fix it using the key to distinguish
	Iterator<Map.Entry<BigDecimal, GLSTMPLTCO>> itFmap = dbLinesMap.entrySet().iterator();
	while(itFmap.hasNext())
	{
	    Entry<BigDecimal, GLSTMPLTCO> entry = itFmap.next();
	    BigDecimal key = entry.getKey();
	    GLSTMPLTCO gls = entry.getValue();
	    // condition to exclude the newline being added because on
	    // reorganize it's being added to the dblines
	    if(gls.getGlstmpltVO().getBRIEF_NAME_ENG() != null)
	    {
		/*
		 * in the condition , if the lineNbr is equal to the lineNumber
		 * as parameter,check also if lineNbr is different than the
		 * newLineNbr for the case where the user reorganizes lines and
		 * insert a new line nbr same as an old existing line number
		 * that does not exist now in the grid
		 */
		if(key.toString().equals(keyNewLine)
			&& !gls.getGlstmpltVO().getLINE_NBR().equals(gls.getNewLineNumber()))
		{
		    return true;
		}
		else if((gls.getGlstmpltVO().getLINE_NBR() != null && ((gls.getGlstmpltVO().getLINE_NBR().intValue() == lineNumber)
			&& (gls.getGlstmpltVO().getLINE_NBR().equals(gls.getNewLineNumber())) || gls.getNewLineNumber()
			.intValue() == lineNumber)))
		{
		    return true;
		}
	    }

	}

	LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
	itFmap = addLinesMap.entrySet().iterator();
	while(itFmap.hasNext())
	{

	    Entry<BigDecimal, GLSTMPLTCO> entry = itFmap.next();
	    BigDecimal key = entry.getKey();
	    GLSTMPLTCO gls = entry.getValue();
	    if(key.toString().equals(keyNewLine) && !gls.getGlstmpltVO().getLINE_NBR().equals(gls.getNewLineNumber()))
	    {
		continue;
	    }
	    else if(gls.getGlstmpltVO().getLINE_NBR() != null
		    && gls.getGlstmpltVO().getLINE_NBR().intValue() == lineNumber)
	    {
		return true;
	    }
	}
	return false;
    }

    /**
     * method called on the click of the OK button in the line's form
     * 
     * @return
     * @throws JSONException
     */
    public String validateLine() throws JSONException // submit line form
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    if((!checkIfAlreadyExistingLine(glstmpltCO.getGlstmpltVO().getLINE_NBR().intValue()) && "Add"
		    .equalsIgnoreCase(mode))
		    || "update".equalsIgnoreCase(mode))// 1=>
	    // added
	    // a
	    // reorganized
	    // line
	    {
		GLSTMPLTCO lGLSTMPLTCO = new GLSTMPLTCO();
		GLSTMPLTVO lGlstmpltVO = lGLSTMPLTCO.getGlstmpltVO();
		GLSTMPLTCO allTempl = repSessionCO.getAllTempl();// this is the
		// template
		// being
		// manipulated
		lGlstmpltVO.setPRINTED(BigDecimal.ZERO);
		lGlstmpltVO.setBOLD("NO");
		lGLSTMPLTCO.setPRINTEDStr("NO");
		lGlstmpltVO.setDISPLAY_ZEROS("NO");
		lGlstmpltVO.setSAVE_DATA(RepConstantsCommon.NO);
		lGlstmpltVO.setDISP_LINE_TOT_ZERO("NO");
		lGlstmpltVO.setFOR_ROUND("NO");
		lGlstmpltVO.setIS_SUB_TOTAL("N");
		lGLSTMPLTCO.setIsSubTotalStr("NO");
		lGlstmpltVO.setCSV("NO");
		lGlstmpltVO.setCOMP_CODE(sessionCO.getCompanyCode());
		lGlstmpltVO.setTEMPLATE_TYPE("P");
		lGlstmpltVO.setCSV("N");
		if("true".equals(glstmpltCO.getPRINTEDStr())) // glstmpltCO is
		// the form
		{
		    lGlstmpltVO.setPRINTED(BigDecimal.ONE);
		    lGLSTMPLTCO.setPRINTEDStr("YES");
		}
		if("true".equals(glstmpltCO.getGlstmpltVO().getBOLD()))
		{
		    lGlstmpltVO.setBOLD("YES");
		}
		if("true".equals(glstmpltCO.getGlstmpltVO().getDISPLAY_ZEROS()))
		{
		    lGlstmpltVO.setDISPLAY_ZEROS("YES");
		}
		if(ConstantsCommon.TRUE.equals(glstmpltCO.getGlstmpltVO().getSAVE_DATA()))
		{
		    lGlstmpltVO.setSAVE_DATA(RepConstantsCommon.YES_CAP);
		}
		if("true".equals(glstmpltCO.getGlstmpltVO().getDISP_LINE_TOT_ZERO()))
		{
		    lGlstmpltVO.setDISP_LINE_TOT_ZERO("YES");
		}
		if("true".equals(glstmpltCO.getForRoundStr()))
		{
		    lGlstmpltVO.setFOR_ROUND("YES");
		}
		if("true".equals(glstmpltCO.getCsvStr())) // glstmpltCO is the
		// form
		{
		    lGlstmpltVO.setCSV("Y");
		    lGLSTMPLTCO.setCsvStr("YES");
		}
		if("true".equals(glstmpltCO.getIsSubTotalStr())) // glstmpltCO
		// is the form
		{
		    lGlstmpltVO.setIS_SUB_TOTAL("Y");
		    lGLSTMPLTCO.setIsSubTotalStr("YES");
		}
		lGlstmpltVO.setGL_TYPE(glstmpltCO.getGlstmpltVO().getGL_TYPE());
		lGlstmpltVO.setBRIEF_NAME_ARAB(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ARAB());
		BigDecimal compCode = sessionCO.getCompanyCode();
		LinkedHashMap<BigDecimal, GLSTMPLTCO> addedLinesMap = allTempl.getAddLinesMap();
		LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
		String concatKeys = "";
		if(null == allTempl.getGlstmpltVO().getCODE())// added for when
		// coming from the
		// templatemaint.jsp
		{
		    concatKeys = compCode.toString() + glstmpltCO.getGlstmpltVO().getCODE().toString()
			    + glstmpltCO.getGlstmpltVO().getLINE_NBR().toString();
		}
		else
		{
		    concatKeys = compCode.toString() + allTempl.getGlstmpltVO().getCODE().toString()
			    + glstmpltCO.getGlstmpltVO().getLINE_NBR().toString();

		}
		lGlstmpltVO.setLINE_NBR(glstmpltCO.getGlstmpltVO().getLINE_NBR());
		lGLSTMPLTCO.setNewLineNumber(glstmpltCO.getGlstmpltVO().getLINE_NBR());
		lGLSTMPLTCO.setCURRENCYStr(glstmpltCO.getCURRENCYStr());
		lGlstmpltVO.setBRIEF_NAME_ENG(glstmpltCO.getGlstmpltVO().getBRIEF_NAME_ENG());
		// validation
		if(((lGLSTMPLTCO.getGlstmpltVO().getBRIEF_NAME_ENG()) != null)
			&& ((lGLSTMPLTCO.getGlstmpltVO().getBRIEF_NAME_ENG().equals(""))))
		{
		    lGLSTMPLTCO.getGlstmpltVO().setBRIEF_NAME_ENG(null);
		}
		if(null == glstmpltCO.getGlstmpltVO().getCODE())
		{
		    glstmpltCO.getGlstmpltVO().setCODE(BigDecimal.ONE);
		}
		templateBO.checkRequiredFields(glstmpltCO, this.get_pageRef(), sessionCO.getCompanyCode(), sessionCO
			.getCurrentAppName(), sessionCO.getLanguage());
		// validation
		lGlstmpltVO.setADD_DESC(glstmpltCO.getGlstmpltVO().getADD_DESC());
		lGlstmpltVO.setADD_DESC1(glstmpltCO.getGlstmpltVO().getADD_DESC1());
		lGlstmpltVO.setBOTTOM_BORDER(glstmpltCO.getGlstmpltVO().getBOTTOM_BORDER());
		BigDecimal curr = glstmpltCO.getGlstmpltVO().getCURRENCY();
		if(NumberUtil.isEmptyDecimal(curr))
		{
		    curr = null;
		}
		lGlstmpltVO.setCURRENCY(curr);
		lGlstmpltVO.setCODE(templateCode);
		lGlstmpltVO.setCOMP_CODE(compCode);
		lGlstmpltVO.setPOST_CODE(glstmpltCO.getGlstmpltVO().getPOST_CODE());
		lGlstmpltVO.setPERCENTAGE(glstmpltCO.getGlstmpltVO().getPERCENTAGE());
		GLSTMPLTCO oldglstmpltCO;
		if(addedLinesMap.get(new BigDecimal(concatKeys)) == null)
		{
		    if(dbLinesMap.get(new BigDecimal(concatKeys)) == null)// add
		    {
			addedLinesMap.put(new BigDecimal(concatKeys), lGLSTMPLTCO);
		    }
		    else
		    {

			oldglstmpltCO = dbLinesMap.get(new BigDecimal(concatKeys));
			lGLSTMPLTCO.setAddGLMap(oldglstmpltCO.getAddGLMap());
			lGLSTMPLTCO.setDbGLMap(oldglstmpltCO.getDbGLMap());
			lGLSTMPLTCO.setModifGLMap(oldglstmpltCO.getModifGLMap());
			lGLSTMPLTCO.setDelGLMap(oldglstmpltCO.getDelGLMap());
			lGLSTMPLTCO.setAddExprMap(oldglstmpltCO.getAddExprMap());
			lGLSTMPLTCO.setDbExprMap(oldglstmpltCO.getDbExprMap());
			lGLSTMPLTCO.setModifExprMap(oldglstmpltCO.getModifExprMap());
			lGLSTMPLTCO.setDelExprMap(oldglstmpltCO.getDelExprMap());
			lGLSTMPLTCO.setAddCrtMap(oldglstmpltCO.getAddCrtMap());
			lGLSTMPLTCO.setDbCrtMap(oldglstmpltCO.getDbCrtMap());
			lGLSTMPLTCO.setModifCrtMap(oldglstmpltCO.getModifCrtMap());
			lGLSTMPLTCO.setDelCrtMap(oldglstmpltCO.getDelCrtMap());
			lGLSTMPLTCO.setDbMismatchMap(oldglstmpltCO.getDbMismatchMap());
			lGLSTMPLTCO.setAddMismatchMap(oldglstmpltCO.getAddMismatchMap());
			lGLSTMPLTCO.setDelMismatchMap(oldglstmpltCO.getDelMismatchMap());

			lGLSTMPLTCO.setOldCrtMap(oldglstmpltCO.getOldCrtMap());
			lGLSTMPLTCO.setOldExprMap(oldglstmpltCO.getOldExprMap());
			lGLSTMPLTCO.setOldGLMap(oldglstmpltCO.getOldGLMap());
			lGLSTMPLTCO.setConcatKey(new BigDecimal(concatKeys));// added
			// for
			// the
			// reorganize

			if(allTempl.getOldLinesMap().get(new BigDecimal(concatKeys)) == null)
			{
			    allTempl.getOldLinesMap().put(new BigDecimal(concatKeys), oldglstmpltCO);
			}
			dbLinesMap.put(new BigDecimal(concatKeys), lGLSTMPLTCO);
			allTempl.getModifLinesMap().put(new BigDecimal(concatKeys), lGLSTMPLTCO);
		    }
		}
		else
		{
		    // set the updated map in order to not loose the changes
		    GLSTMPLTCO oldGlstmpltCO = addedLinesMap.get(new BigDecimal(concatKeys));
		    lGLSTMPLTCO.setAddGLMap(oldGlstmpltCO.getAddGLMap());
		    lGLSTMPLTCO.setDbGLMap(oldGlstmpltCO.getDbGLMap());
		    lGLSTMPLTCO.setModifGLMap(oldGlstmpltCO.getModifGLMap());
		    lGLSTMPLTCO.setDelGLMap(oldGlstmpltCO.getDelGLMap());
		    lGLSTMPLTCO.setAddExprMap(oldGlstmpltCO.getAddExprMap());
		    lGLSTMPLTCO.setDbExprMap(oldGlstmpltCO.getDbExprMap());
		    lGLSTMPLTCO.setModifExprMap(oldGlstmpltCO.getModifExprMap());
		    lGLSTMPLTCO.setDelExprMap(oldGlstmpltCO.getDelExprMap());
		    lGLSTMPLTCO.setAddCrtMap(oldGlstmpltCO.getAddCrtMap());
		    lGLSTMPLTCO.setModifCrtMap(oldGlstmpltCO.getModifCrtMap());
		    lGLSTMPLTCO.setDelCrtMap(oldGlstmpltCO.getDelCrtMap());
		    lGLSTMPLTCO.setDbCrtMap(oldGlstmpltCO.getDbCrtMap());
		    lGLSTMPLTCO.setDbMismatchMap(oldGlstmpltCO.getDbMismatchMap());
		    lGLSTMPLTCO.setAddMismatchMap(oldGlstmpltCO.getAddMismatchMap());
		    lGLSTMPLTCO.setDelMismatchMap(oldGlstmpltCO.getDelMismatchMap());

		    lGLSTMPLTCO.setOldCrtMap(oldGlstmpltCO.getOldCrtMap());
		    lGLSTMPLTCO.setOldExprMap(oldGlstmpltCO.getOldExprMap());
		    lGLSTMPLTCO.setOldGLMap(oldGlstmpltCO.getOldGLMap());
		    lGLSTMPLTCO.setConcatKey(new BigDecimal(concatKeys));
		    addedLinesMap.put(new BigDecimal(concatKeys), lGLSTMPLTCO);
		}
	    }
	    else
	    {
		BOException b = new BOException();
		b.setErrorCode(2001);
		throw b;
	    }
	}
	catch(BOException b)
	{
	    if(b.getErrorCode() == null)
	    {
		handleException(b, null, null);
	    }
	    else if(b.getErrorCode().intValue() == 2001)
	    {
		handleException(null, null, "template.lineNumberExists");
	    }
	    return "error";
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return "error";
	}
	return SUCCESS;
    }

    /**
     * method that removes a line from memory
     * 
     * @return
     * @throws JSONException
     */
    public String deleteLine() throws JSONException // load the grid
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    BigDecimal templCode = allTempl.getGlstmpltVO().getCODE();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String concatKeys = compCode.toString() + templCode.toString() + getNewLineNumber();
	    HashMap<BigDecimal, GLSTMPLTCO> addedLinesMap = allTempl.getAddLinesMap();
	    HashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();

	    if(addedLinesMap.get(new BigDecimal(concatKeys)) == null)
	    {
		if(dbLinesMap.get(new BigDecimal(concatKeys)) != null)
		{
		    GLSTMPLTCO glstmpltCO = dbLinesMap.get(new BigDecimal(concatKeys));
		    dbLinesMap.remove(new BigDecimal(concatKeys));
		    allTempl.getModifLinesMap().remove(new BigDecimal(concatKeys));
		    // change the concatKeys inside the delLinesMap
		    concatKeys = compCode.toString() + templCode.toString() + getLineNumber().toString();
		    allTempl.getDelLinesMap().put(new BigDecimal(concatKeys), glstmpltCO);
		}
	    }
	    else
	    {
		addedLinesMap.remove(new BigDecimal(concatKeys));
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);

	}
	return SUCCESS;
    }

    /**
     * method called on the load of the lines grid
     * 
     * @return
     * @throws JSONException
     */
    public String loadTemplLinesGrid() throws JSONException // load the grid
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();

	    BigDecimal compCode = allTempl.getGlstmpltVO().getCOMP_CODE();
	    BigDecimal templCode = allTempl.getGlstmpltVO().getCODE();

	    // if new template
	    if(compCode == null)
	    {
		compCode = sessionCO.getCompanyCode();
	    }
	    if(templCode == null)
	    {
		templCode = new BigDecimal(-1);
	    }

	    GLSTMPLTSC irpTLSCKey = new GLSTMPLTSC();
	    irpTLSCKey.setCOMP_CODE(compCode);
	    irpTLSCKey.setCODE(templCode);
	    irpTLSCKey.setLANG_CODE(sessionCO.getLanguage());

	    // fill the grid from the dbList and addList(not from the db)
	    // because the changes are not saved in the db each time we add a
	    // line
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	    // printHashContents(dbLinesMap, "loadingGriddblines");
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
	    // printHashContents(addLinesMap, "loadingGridaddddlines");
	    HashMap<BigDecimal, GLSTMPLTCO> delLinesMap = allTempl.getDelLinesMap();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> finalMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();

	    /*
	     * it means when the dbLinesMap is empty (not loaded yet) and it is
	     * no empty because all the records are deleted
	     */
	    if(dbLinesMap == null || (dbLinesMap.isEmpty() && delLinesMap.isEmpty())) 
	    {
		List<GLSTMPLTCO> dbLinesList = templateBO.getAllTemplateLines(irpTLSCKey);
		for(int i = 0; i < dbLinesList.size(); i++)
		{
		    GLSTMPLTCO glstmpltCOO = dbLinesList.get(i);

		    dbLinesMap.put(glstmpltCOO.getConcatKey(), glstmpltCOO);
		}
		allTempl.setDbLinesMap(dbLinesMap);
	    }
	    finalMap.putAll(dbLinesMap);
	    finalMap.putAll(addLinesMap);
	    // printHashContents(finalMap, "finalMap");
	    // sorting
	    Iterator<GLSTMPLTCO> it = finalMap.values().iterator();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> temp = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();

	    while(it.hasNext())
	    {
		GLSTMPLTCO gls = it.next();
		int lineNbr = gls.getNewLineNumber().intValue();
		String key = compCode.toString() + templCode.toString() + String.valueOf(lineNbr);
		if(gls.getGlstmpltVO().getBRIEF_NAME_ENG() != null)
		{
		    temp.put(new BigDecimal(key), gls);
		}
	    }
	    finalMap = temp;
	    finalMap = sortHashMapByValuesD(finalMap);
	    // sorting

	    copyproperties(tmplGridSC);

	    int nbRec = tmplGridSC.getNbRec();
	    int recToSkip = tmplGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(finalMap.size() < maxRec)
	    {
		maxRec = finalMap.size();
	    }
	    ArrayList<GLSTMPLTCO> lst = new ArrayList<GLSTMPLTCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((GLSTMPLTCO) finalMap.values().toArray()[i]);
	    }

	    setRecords(finalMap.size());
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that sorts the hashmap's values
     * 
     * @param passedMap
     * @return
     */
    public LinkedHashMap<BigDecimal, GLSTMPLTCO> sortHashMapByValuesD(HashMap<BigDecimal, GLSTMPLTCO> passedMap)
    {
	List<BigDecimal> mapKeys = new ArrayList<BigDecimal>(passedMap.keySet());
	List<GLSTMPLTCO> mapValues = new ArrayList<GLSTMPLTCO>(passedMap.values());
	Collections.sort(mapKeys);

	LinkedHashMap<BigDecimal, GLSTMPLTCO> sortedMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();

	Iterator<GLSTMPLTCO> valueIt = mapValues.iterator();
	while(valueIt.hasNext())
	{
	    valueIt.next();
	    Iterator<BigDecimal> keyIt = mapKeys.iterator();

	    while(keyIt.hasNext())
	    {
		BigDecimal key = keyIt.next();
		GLSTMPLTCO comp1 = passedMap.get(key);
		sortedMap.put(key, comp1);

	    }

	}
	return sortedMap;
    }

    /**
     * method called on the gl's dependency
     * 
     * @return
     * @throws JSONException
     */
    public String applyFromGLDependency() throws JSONException
    {
	try
	{
	    BigDecimal codeGL = getCode();
	    BigDecimal glCompCode = getCode1();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(codeGL == null || codeGL.intValue() == 0 || NumberUtil.isEmptyDecimal(codeGL))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(null);
		commonDetVO.setCODE(null);
	    }
	    else
	    {
		CommonDetailsSC commDet = new CommonDetailsSC();
		commDet.setCOMP_CODE(glCompCode);
		commDet.setGL_CODE(codeGL);
		commonDetVO = templateBO.getFromGLDependency(commDet);
		if(commonDetVO == null)
		{
			if( NumberUtil.isEmptyDecimal(commDet.getGL_CODE())){
				 commonDetVO = new CommonDetailsVO();
				 commonDetVO.setBRIEF_DESC_ENG(null);
				 commonDetVO.setCODE(null);
			}
			/*in case of filling invalid GL ,do not empty the gl code since a confirmation 
			msg will appear after dependency asking if proceed or not*/
			else{
				
				commonDetVO = new CommonDetailsVO();
				commonDetVO.setBRIEF_DESC_ENG("");
				commonDetVO.setCODE(codeGL);		
			}	   

		}
		else
		{
		    commonDetVO.setCODE(codeGL);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the dependency of the filter criteria
     * 
     * @return
     * @throws JSONException
     */
    public String applyFilterCrtDependency() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal crtCode = getCode();
	    if(crtCode == null)
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setCRITERIA_DESCRIPTION(null);
		commonDetVO.setCRITERIA_CODE(null);
	    }
	    else
	    {
		TmplGridSC tmplSC = new TmplGridSC();
		tmplSC.setCOMP_CODE(sessionCO.getCompanyCode());
		tmplSC.setCRITERIA_CODE(crtCode);
		tmplSC.setLANG_CODE(sessionCO.getLanguage());
		commonDetVO = commonLookupBO.getFilterCrtDependency(tmplSC);
		if(commonDetVO == null)
		{
		    commonDetVO = new CommonDetailsVO();
		    commonDetVO.setCRITERIA_DESCRIPTION("");
		    commonDetVO.setCRITERIA_CODE(new BigDecimal(-5));
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the check of the existance of a template code
     * 
     * @return
     * @throws JSONException
     */
    public String checkTemplCode() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String templCodeParam = request.getParameter("templCode");
	    if(templCodeParam != null && !templCodeParam.equals(""))
	    {
		GLSTMPLTVO irpTemplVO = new GLSTMPLTVO();
		irpTemplVO.setCOMP_CODE(compCode);
		irpTemplVO.setCODE(new BigDecimal(templCodeParam));
		GLSTMPLTCO lGlstmpltCO = new GLSTMPLTCO();
		lGlstmpltCO.setGlstmpltVO(irpTemplVO);
		irpTemplVO.setLINE_NBR(BigDecimal.ZERO);
		irpTemplVO.setTEMPLATE_TYPE("P");
		checkTemplCodeVO = templateBO.checkTemplCode(irpTemplVO);
		if(checkTemplCodeVO == null)
		{
		    checkTemplCodeVO = new GLSTMPLTVO();
		    checkTemplCodeVO.setCOMP_CODE(new BigDecimal(-1));
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that builds the filter criteria details lookup
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookUpFilterCrtDet() throws JSONException
    {
	try
	{

	    // Design the Grid by defining the column model and column names
	    // 28 values
	    String[] name = { "CODE_STR", "BRIEF_DESC_ENG", "BRIEF_DESC_ARAB", "SECTOR_CODE", "ADDITIONAL_REFERENCE",
		    "BAS_EXPRESSION_CLASS_CODE", "BAS_EXPRESSION_CLASS_DESC", "LONG_DESC_ARAB", "CATEGORY",
		    "CONTINENT", "FROM_BRANCH", "FROM_CIF", "FROM_CY", "FROM_GL", "FROM_SL", "SECURITY_TYPE", "SERIAL",
		    "STATUS ", "TRADING_COMPANY", "USEFUL_LIFE", "TO_BRANCH", "TO_CIF", "TO_CY", "TO_GL", "TYPE",
		    "BASE_CURRENCY", "LONG_DESC_ENG", "DEPRECIATION_METHOD", "TO_SL","CB_NO","COMP_CODE" };
	    String[] colType = { "text", "text", "text", "number", "text", "text", "text", "text", "text", "text",
		    "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text", "text",
		    "text", "text", "text", "text", "text", "text", "text", "text","number","number" };
	    String[] titles = { getText("reporting.lkpCode"), getText("reporting.lkpBriefEng"),
		    getText("reporting.lkpBriefAr"), getText("reporting.lkpSectorCode"), getText("template.additionalRef")
		    , getText("template.bassExpClassCode"), getText("template.bassExpClassDesc"),
		    getText("long_desc_arab_key"), getText("Category_key"), getText("template.continent"),
		    getText("From_Branch_key"), getText("From_CIF_key"), getText("reporting.fromCY"),
		    getText("template.fromGL"), getText("template.fromSL"), getText("security_type_key"),
		    getText("Serial_key"), getText("Status_key"), getText("template.tradingCurrency"),
		    getText("template.UsefulLife"), getText("To_Branch_key"), getText("To_CIF_key"),
		    getText("reporting.toCY"), getText("template.ToGL"), getText("type_key"),
		    getText("Base_Currency_key"), getText("long_desc_eng_key"), getText("template.depreciationMethod"),
		    getText("template.ToSL"),getText("template.scrtNo"),getText("reporting.companyCode") };
	    String criteriaCode;
	    if(RepConstantsCommon.CRITERIA_GOODS.equals(str2) || RepConstantsCommon.CRITERIA_CONTINENT.equals(str2)
		    || RepConstantsCommon.CRITERIA_DEAL_ECONOMIC_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_VAL_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_MAIN_ECO_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_ECONOMIC_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_SECURITY_ECO_SEC.equals(str2)
		    || RepConstantsCommon.CRITERIA_CAF.equals(str2))
	    {
		criteriaCode = getStr2();
	    }
	    else
	    {
		criteriaCode = getStr1();
	    }
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("");
	    grid.setRowNum("10");
	    grid.setUrl("/path/templateMaintReport/filterCrtDetLookUpGrid");
	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
	    HashMap<String, int[]> mapCols = new HashMap<String, int[]>();
	    mapCols.put(RepConstantsCommon.CRITERIA_COUNTRIES_CTY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put("COO", new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_BRANCH_BR, new int[] { 0, 1, 26, 25 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CBK_CIF_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_NO, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_TYPE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CATEG_GOODS, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_GOODS, new int[] { 0, 1, 26, 2, 7 });
	    boolean isMapColsFilled = false;
	    if(RepConstantsCommon.CRITERIA_CONTINENT.equals(str2))
	    {
		mapCols.put(RepConstantsCommon.CRITERIA_CONTINENT, new int[] { 0, 1, 26, 9, 2, 7 });
	    }
	    //2nd lookup
	    else if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && !StringUtil.isNotEmpty(str3))
	    {
		mapCols.put(RepConstantsCommon.CRITERIA_CAF, new int[] { 0, 1 });
		isMapColsFilled = true;
	    }
	    //smart lookup
	    else if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && StringUtil.isNotEmpty(str3))
	    {
	    mapCols.put(RepConstantsCommon.CRITERIA_CAF, new int[] { 0, 1});
		isMapColsFilled = true;
	    }
	    else
	    {
		mapCols.put(RepConstantsCommon.CRITERIA_CONTINENT, new int[] { 0, 1, 26, 2, 7 });
	    }
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_PROFILE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_CREDIT_RATING, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_COUNTRY_REGION, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TRANSACTION_PURPOSE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CURRENCY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put("SCU", new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_ECONOMIC_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_EMPLOYEE_CATEGORY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_FIXED_ASSETS_CAT, new int[] { 0, 1, 26, 19, 27, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_COLLATERAL_TYPE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_FACILITY_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_GL_TERM, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_IBC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ILC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_LEGAL_STATUS, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_LG_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_PRODUCT_ECO_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_NATIONALITY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_OBC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ONE_OBLIGOR, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_OLC_TYPE, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_OCCUPATION_POSITION, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SECURITY_TYPE, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_PURPOSE_FINANCING, new int[] { 0, 1, 2 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_RELATION, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_REGION_BRANCH, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_PRIORITY, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_LINK_MGMNT, new int[] { 0, 1, 26, 16, 10, 12, 13, 11, 14, 17, 20,
		    22, 23, 21, 28 });
	    mapCols.put(RepConstantsCommon.CRITERIA_PRODUCT_CLASS, new int[] { 0, 1, 26, 8, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_PROV_CATEG, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TRANSACTION_LABEL, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TRANSACTION_TYPE, new int[] { 0, 1, 26, 24, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_VAL_SEC, new int[] { 0, 1, 26, 15, 18 });
	    mapCols.put(RepConstantsCommon.CRITERIA_MAIN_ECO_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ECONOMIC_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CATEG_REGION, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_TENURE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SMART, new int[] { 0, 1, 26 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ASSET_TRANS_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_COLLATERALIZED_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_FOREX_DEAL_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SEC_CATEG, new int[] { 0, 1, 2 });
	    mapCols.put(RepConstantsCommon.CRITERIA_AMT_BY_GL, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_MAT_DATE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_PERIOD, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEBIT_CREDIT, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_TOTAL_DC, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SOURCE_USAGES, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_DEAL_CAT_TYPE, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_RESIDENT, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_AMOUNT, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_MBK, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_CIF_SECURITY_ISSUER, new int[] { 0, 1, 26, 4, 2, 7, 29 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SECURITY_ECO_SEC, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_JOB, new int[] { 0, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION, new int[] { 0, 1 });
	    mapCols.put(RepConstantsCommon.CRITERIA_JVT, new int[] { 0, 4, 1, 26, 2, 7 });
	    mapCols.put(RepConstantsCommon.CRITERIA_IDT, new int[] { 0, 26, 1, 2, 4 });
	    mapCols.put(RepConstantsCommon.CRITERIA_GL_CODE, new int[] { 0, 1});
	    mapCols.put(RepConstantsCommon.CRITERIA_DETAIL_GL_CODE, new int[] { 0, 1});
	    //1st lookup
	    if(!isMapColsFilled)
	    {
		mapCols.put(RepConstantsCommon.CRITERIA_CAF, new int[] { 0, 4, 1, 2, 26, 7 });
	    }

	    String crtCodeKey;
	    int[] colsToDisplay;
	    Entry<String, int[]> entry;
	    Iterator<Map.Entry<String, int[]>> itMapCols = mapCols.entrySet().iterator();
	    LookupGridColumn gridColumn;
	    while(itMapCols.hasNext())
	    {
		entry = itMapCols.next();
		crtCodeKey = entry.getKey();
		if(crtCodeKey.equals(criteriaCode))
		{
		    colsToDisplay = entry.getValue();
		    if(crtCodeKey.equals(RepConstantsCommon.CRITERIA_SMART))
		    {
			titles[1] = getText("reporting.briefDescription");
			titles[2] = getText("reporting.longDescription");
		    }
		    else if(crtCodeKey.equals(RepConstantsCommon.CRITERIA_JVT))
		    {
			titles[0]=getText("reporting.JVType");
		    }
		    else if(crtCodeKey.equals(RepConstantsCommon.CRITERIA_IDT))
		    {
			titles[26]=getText("reporting.types");
			titles[4]=getText("reporting.expiryDays");
		    }
		    else if(crtCodeKey.equals(RepConstantsCommon.CRITERIA_CAF) && !isMapColsFilled)
		    {
			titles[4] = getText("qry.TblName");
		    }
		    for(int i = 0; i < colsToDisplay.length; i++)
		    {
			gridColumn = new LookupGridColumn();
			gridColumn.setName(name[colsToDisplay[i]]);
			gridColumn.setColType(colType[colsToDisplay[i]]);
			gridColumn.setTitle(titles[colsToDisplay[i]]);
			gridColumn.setSearch(true);
			lsGridColumn.add(gridColumn);
		    }
		}
	    }
	    lookup(grid, lsGridColumn, null, tmplGridSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method that fills the criteria lookup
     * 
     * @return
     * @throws Exception
     */
    public String loadFilterCrtDetLookUpGrid() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    String tblName = getStr();
	    List<CommonDetailsVO> crtList;
	    int crtCount;
	    if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(getStr1()))
	    {
		SYS_PARAM_LOV_TRANSVO sclVO = new SYS_PARAM_LOV_TRANSVO();
		sclVO.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_SCL_CRIT_LOV);
		sclVO.setLANG_CODE(sessionCO.getLanguage());
		securityClassList = (ArrayList) commonLookupBO.getLookupList(sclVO);
		crtList = new ArrayList<CommonDetailsVO>();
		for(int i = 0; i < securityClassList.size(); i++)
		{
		    CommonDetailsVO vo = new CommonDetailsVO();
		    vo.setCODE_STR((securityClassList.get(i)).getVALUE_CODE());
		    vo.setBRIEF_DESC_ENG((securityClassList.get(i)).getVALUE_DESC());
		    crtList.add(vo);
		}
		crtCount = securityClassList.size();
	    }
	    // to remove later the second part, after filling table1 and table2
	    // in the table irp_criteria_type
	    else if((tblName != null && tblName.toLowerCase(Locale.ENGLISH).equals(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
		    || (tblName == null || tblName.equals("")))
	    {
		if((tblName == null || tblName.equals("")))
		{
		    tblName = RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_CAP;
		}
		String crtTypeCode = getStr1();
		tmplGridSC.setTABLE_NAME(tblName.toUpperCase(Locale.ENGLISH));
		tmplGridSC.setCRITERIA_TYPE_CODE(crtTypeCode);
		tmplGridSC.setLANG_CODE(sessionCO.getLanguage());
		crtList = templateBO.getFilterCrtDetList(tmplGridSC);
		crtCount = templateBO.getFilterCrtDetListCount(tmplGridSC);
	    }
	    else
	    {
		tmplGridSC.setTABLE_NAME(tblName);
		if(RepConstantsCommon.GL_TERM_VIEW.equals(tblName) || RepConstantsCommon.REGION_BR_VIEW.equals(tblName)
			|| RepConstantsCommon.MAIN_ECO_SEC_VIEW1.equals(tblName)
			|| RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(tblName)
			|| RepConstantsCommon.SMT_VIEW.equals(tblName))
		{
		    tmplGridSC.setCOMP_CODE(null);
		}
		else
		{
		    tmplGridSC.setCOMP_CODE(sessionCO.getCompanyCode());
		}
		tmplGridSC.setLANG_CODE(null);

		if(tblName.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS"))
		{
		    String code1 = getStr1();
		    if(code1 == null || code1.equals(""))
		    {
			code1 = "-1";
		    }
		    tmplGridSC.setSECTOR_CODE(new BigDecimal(code1));
		}
		else if((tblName.toUpperCase(Locale.ENGLISH).equals("V_IRP_DES_SUB")
			|| "V_IRP_COUNTRIES".equals(tblName) || (RepConstantsCommon.SEC_NO_VIEW2.equals(tblName) && !"SEC"
			.equals(getStr1()))))
		{
		    if((RepConstantsCommon.SEC_NO_VIEW2.equals(tblName) || "V_IRP_DES_SUB".equals(tblName) || "V_IRP_COUNTRIES"
			    .equals(tblName))
			    && RepConstantsCommon.EMPTY_STRING.equals(getStr1().trim()))
		    {
			setRecords(0);
			setGridModel(new ArrayList());
			// throw new BOException("choose details1");
			return SUCCESS;
		    }
		    else if(!RepConstantsCommon.CRITERIA_COUNTRIES_CTY.equals(str1) && !"COO".equals(str1))
		    {
			tmplGridSC.setCODE1(new BigDecimal(getStr1()));
		    }
		}
		else
		{
		    tmplGridSC.setSECTOR_CODE(null);
		}
		if((RepConstantsCommon.CRITERIA_GOODS.equals(str2) || RepConstantsCommon.CRITERIA_CONTINENT
			.equals(str2))
			&& RepConstantsCommon.EMPTY_STRING.equals(str1))
		{
		    setRecords(0);
		    setGridModel(new ArrayList());
		    return SUCCESS;
		}
		if(RepConstantsCommon.CRITERIA_SMART.equals(str1))
		{
		    tmplGridSC.setOPTION_CODE(getCode());
		}		
		else if(RepConstantsCommon.CRITERIA_CAF.equals(str1))
		{
		    tmplGridSC.setCB_NO(RepConstantsCommon.ONE);
		}
		else if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && str3==null)
		{
		    if(StringUtil.nullToEmpty(str1).equals(""))
		    {
			return SUCCESS;
		    }
		    tmplGridSC.setCB_NO(RepConstantsCommon.ZERO);
		    tmplGridSC.setCODE(new BigDecimal(str1));
		    tmplGridSC.setTABLE_NAME(RepConstantsCommon.CRT_CAF_VIEW);
		}
		else if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && str3 != null)
		{
		    if(StringUtil.nullToEmpty(str1).equals("") || StringUtil.nullToEmpty(str3).equals(""))
		    {
			return SUCCESS;
		    }
		    tmplGridSC.setCODE_CAF(new BigDecimal(str1));
		    tmplGridSC.setFIELD_SEQ(new BigDecimal(str3));
		}
		if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && StringUtil.isNotEmpty(str1) && StringUtil.isNotEmpty(str3))
		{
			HashMap<String, Object> repSessionCOMap=PathPropertyUtil.convertToMap(sessionCO);
			crtList = templateBO.retCrtAddFieldsDet3Lst(repSessionCOMap,tmplGridSC);
		}
		else
		{
			crtList = templateBO.getFilterCrtDetLList(tmplGridSC);
		}		
		if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && !StringUtil.isNotEmpty(str3))
		{
		    for(int i = 0; i < crtList.size(); i++)
		    {
			crtList.get(i).setCODE_STR(crtList.get(i).getBAS_EXPRESSION_CLASS_CODE());
			crtList.get(i).setBRIEF_DESC_ENG((crtList.get(i).getBAS_EXPRESSION_CLASS_DESC()));
		    }
		}
		//handling criteria IDT(getting description for types column)
		if(RepConstantsCommon.CRITERIA_IDT.equals(str1))
		{
		    SYS_PARAM_LOV_TRANSVO sclVO = new SYS_PARAM_LOV_TRANSVO();
		    sclVO.setLOV_TYPE_ID(RepConstantsCommon.CRITERIA_IDT_LOV);
		    sclVO.setLANG_CODE(RepConstantsCommon.EN_CUR_BRIEF_NAME);
		    ArrayList<SYS_PARAM_LOV_TRANSVO> idtCrtTypesList = (ArrayList) commonLookupBO.getLookupList(sclVO);
		    CommonDetailsVO lVO;
		    SYS_PARAM_LOV_TRANSVO lTransVO;
		    for(int i = 0; i < crtList.size(); i++)
		    {
			lVO = crtList.get(i);
			for(int j = 0; j < idtCrtTypesList.size(); j++)
			{
			    lTransVO = idtCrtTypesList.get(j);
			    if(lTransVO.getVALUE_CODE().equals(lVO.getLONG_DESC_ENG()))
			    {
				lVO.setLONG_DESC_ENG(lTransVO.getVALUE_DESC());
				break;
			    }
			}
		    }
		}
		if(RepConstantsCommon.CRITERIA_CAF.equals(str2) && StringUtil.isNotEmpty(str1) && StringUtil.isNotEmpty(str3))
		{
			HashMap<String, Object> repSessionCOMap=PathPropertyUtil.convertToMap(sessionCO);
			crtCount = templateBO.retCrtAddFieldsDet3LstCount(repSessionCOMap,tmplGridSC);
		}
		else
		{
			crtCount = templateBO.getFilterCrtDetLListCount(tmplGridSC);
		}	
	    }
	    setRecords(crtCount);
	    setGridModel(crtList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";

    }

    /**
     * method that fill the filter criteria lookup
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookUpFilterCrt() throws JSONException
    {
	try
	{

	    // Design the Grid by defining the column model and column names
	    String[] name = { "CRITERIA_CODE", "CRITERIA_DESCRIPTION", "CRITERIA_TYPE_CODE", "COMPONENT",
		    "TABLE_NAME1", "TABLE_NAME2" };
	    String[] colType = { "number", "text", "text", "text", "text", "text" };
	    String[] titles = { getText("criteria.crtCode"), getText("reporting.lkpCrtDesc"), "CRITERIA TYPE CODE",
		    "COMPONENT", "TABLE_NAME1", "TABLE_NAME2" };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("Criterias");
	    grid.setRowNum("10");
	    grid.setShrinkToFit("true");
	    grid.setUrl("/path/templateMaintReport/filterCrtLookUpGrid");

	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(true);

		if(name[i].equals("COMPONENT") || name[i].equals("TABLE_NAME1") || name[i].equals("TABLE_NAME2")
			|| name[i].equals("CRITERIA_TYPE_CODE"))
		{
		    gridColumn.setHidden(true);
		}
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, tmplGridSC);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that loads the criteria lookup
     * 
     * @return
     * @throws Exception
     */
    public String loadFilterCrtLookUpGrid() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    tmplGridSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    tmplGridSC.setLANG_CODE(sessionCO.getLanguage());
	    List<CommonDetailsVO> crtList = commonLookupBO.getFilterCrtList(tmplGridSC);
	    int crtCount = commonLookupBO.getFilterCrtListCount(tmplGridSC);
	    setRecords(crtCount);
	    setGridModel(crtList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";

    }

    /**
     * method the loads the lookup currency
     * 
     * @return
     * @throws Exception
     */
    public String loadLookUpCurrencyGrid() throws Exception
    {
	try
	{
	    setSearchFilter(tmplGridSC);
	    copyproperties(tmplGridSC);
	    SessionCO sessionCO = returnSessionObject();
	    tmplGridSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    List<GLSTMPLTCO> currList = templateBO.getCurrencyList(tmplGridSC);
	    int currCount = templateBO.getCurrencyListCount(tmplGridSC);
	    setRecords(currCount);
	    setGridModel(currList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";

    }

    public String fillLookUpFromGL() throws JSONException
    {
	try
	{
	    fillLookup(BigDecimal.valueOf(5), "/path/templateMaintReport/fromGLDetailsLookUpGrid", "From GL");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that loads the lookup from gl grid
     * 
     * @return
     * @throws Exception
     */
    public String loadLookUpFromGLGrid() throws Exception
    {

	try
	{
	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    BigDecimal glCompCode = getCode();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		glCompCode = new BigDecimal(-100);
	    }

	    tmplGridSC.setCOMP_CODE(glCompCode);

	    int fromGlCount = templateBO.getFromGLListCount(tmplGridSC);
	    List<CommonDetailsVO> fromGLList = templateBO.getFromGLList(tmplGridSC);

	    setRecords(fromGlCount);
	    setGridModel(fromGLList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";

    }

    /**
     * method called on the opening of the gl grid
     * 
     * @return
     * @throws JSONException
     */
    public String openGLList() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    // fill the combo of the calculation type
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(4)); // 4= calculation
	    // type (check
	    // table
	    // sys_param_lov_type
	    // in the xls file)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    calcTypeArrList = (ArrayList<Object>) commonLookupBO.getLookupList(sysParamLovVO);
	    // fill a map with key and value of the calcultion type in order to
	    // get the value of the selected key when needed
	    if(allTempl != null && allTempl.getCalcTypeMap().size() == 0)
	    {
		HashMap<String, String> calcTypeMap = allTempl.getCalcTypeMap();
		for(int i = 0; i < calcTypeArrList.size(); i++)
		{
		    SYS_PARAM_LOV_TRANSVO calcTypeVO = (SYS_PARAM_LOV_TRANSVO) calcTypeArrList.get(i);
		    calcTypeMap.put(calcTypeVO.getVALUE_CODE(), calcTypeVO.getVALUE_DESC());
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called to fill the companies lookup
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookupCompanies() throws JSONException
    {
	try
	{
	    fillLookup(BigDecimal.valueOf(1), "/path/templateMaintReport/compDetailsLookUpGrid", "GL Companies List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called to fill the companies lookup
     * 
     * @return
     * @throws JSONException
     */
    public String loadLookUpCompGrid() throws Exception
    {
	try
	{
	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    List<CommonDetailsVO> lst = templateBO.getCompaniesList(tmplGridSC);
	    int compListCount = templateBO.getCompaniesListCount(tmplGridSC);
	    setRecords(compListCount);
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the companies dependency
     * 
     * @return
     * @throws JSONException
     */
    public String applyCompaniesDependency() throws JSONException
    {
	try
	{
	    BigDecimal compCode = getCode();
	    BigDecimal isColTempl = getCode1();
	    if(NumberUtil.isEmptyDecimal(compCode))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(null);
		commonDetVO.setCODE(null);
		if(isColTempl == null || NumberUtil.isEmptyDecimal(isColTempl))
		{
		    //added condition to avoid marking the other fields mandatory as editable if COC=calcType
		    if(!RepConstantsCommon.CALC_CNT_CIF.equals(updates)
		    	&& !RepConstantsCommon.CALC_INDEP_CNT_CIF.equals(updates)
			    && !RepConstantsCommon.CALC_TYPE_IBC.equals(updates)
			    && !RepConstantsCommon.CALC_TYPE_ILC.equals(updates)
			    && !RepConstantsCommon.CALC_TYPE_LG.equals(updates)
			    && !RepConstantsCommon.CALC_TYPE_OBC.equals(updates)
			    && !RepConstantsCommon.CALC_TYPE_OLC.equals(updates))
		    {
			updateGLDisplay(BigDecimal.ONE, true,null);
		    }
		}
		else
		{
		    updateInputDisplay(BigDecimal.ONE, true);
		}
	    }
	    else
	    {
		TmplGridSC tmplSC = new TmplGridSC();
		tmplSC.setCOMP_CODE(compCode);
		commonDetVO = commonLookupBO.getCompDependency(tmplSC);
		if(commonDetVO == null)
		{
		    commonDetVO = new CommonDetailsVO();
		    commonDetVO.setBRIEF_DESC_ENG(null);
		    commonDetVO.setCODE(null);
		    if(isColTempl == null || NumberUtil.isEmptyDecimal(isColTempl))
		    {
			updateGLDisplay(BigDecimal.ZERO, true,null);
		    }
		    else
		    {
			updateInputDisplay(BigDecimal.ZERO, false);
		    }
		}
		else
		{
		    commonDetVO.setCODE(compCode);
		    if(isColTempl == null || NumberUtil.isEmptyDecimal(isColTempl))
		    {
			if(!RepConstantsCommon.CALC_CNT_CIF.equals(updates)
				&& !RepConstantsCommon.CALC_INDEP_CNT_CIF.equals(updates)
				&& !RepConstantsCommon.CALC_TYPE_IBC.equals(updates)
				&& !RepConstantsCommon.CALC_TYPE_ILC.equals(updates)
				&& !RepConstantsCommon.CALC_TYPE_LG.equals(updates)
				&& !RepConstantsCommon.CALC_TYPE_OBC.equals(updates)
				&& !RepConstantsCommon.CALC_TYPE_OLC.equals(updates))
			{
			    updateGLDisplay(BigDecimal.ZERO, true,null);
			}
		    }
		    else
		    {
			updateInputDisplay(BigDecimal.ZERO, false);
		    }
		}

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the update of input display
     * 
     * @param readOnly
     * @param emptyAll
     */
    public void updateInputDisplay(BigDecimal readOnly, boolean emptyAll)
    {
	SYS_PARAM_SCREEN_DISPLAYVO buisnessElement = new SYS_PARAM_SCREEN_DISPLAYVO();
	buisnessElement.setIS_READONLY(readOnly);
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	hm.put("lookuptxt_brnchCode", buisnessElement);
	hm.put("lookuptxt_brnchCodeTo", buisnessElement);
	hm.put("lookuptxt_FROM_CY", buisnessElement);
	hm.put("lookuptxt_TO_CY", buisnessElement);
	hm.put("lookuptxt_divCodeFrom", buisnessElement);
	hm.put("lookuptxt_divCodeTo", buisnessElement);
	hm.put("lookuptxt_divCodeFrom", buisnessElement);
	hm.put("lookuptxt_divCodeTo", buisnessElement);
	hm.put("lookuptxt_FROM_REGION", buisnessElement);
	hm.put("lookuptxt_TO_REGION", buisnessElement);

	if(emptyAll)
	{
	    hm.put("lookuptxt_depCodeFrom", buisnessElement);
	    hm.put("lookuptxt_TO_DEPT", buisnessElement);
	    hm.put("lookuptxt_TO_DEPT", buisnessElement);
	    hm.put("lookuptxt_FROM_UNIT", buisnessElement);
	    hm.put("lookuptxt_TO_UNIT", buisnessElement);
	}
	setAdditionalScreenParams(hm);

    }

    /**
     * method that adjusts the display of the gl fields
     * @param readOnly
     * @param isComp
     * @param calcType
     */
    public void updateGLDisplay(BigDecimal readOnly, Boolean isComp, String calcType)
    {
	SYS_PARAM_SCREEN_DISPLAYVO buisnessElement = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO dispCompBr = new SYS_PARAM_SCREEN_DISPLAYVO();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	if(RepConstantsCommon.CALC_CNT_CIF.equals(calcType) || (RepConstantsCommon.CALC_TYPE_RTV).equals(calcType)
		|| RepConstantsCommon.CALC_INDEP_CNT_CIF.equals(calcType)
		|| RepConstantsCommon.CALC_TYPE_IBC.equals(calcType)
		|| RepConstantsCommon.CALC_TYPE_ILC.equals(calcType)
		|| RepConstantsCommon.CALC_TYPE_LG.equals(calcType)
		|| RepConstantsCommon.CALC_TYPE_OBC.equals(calcType)
		|| RepConstantsCommon.CALC_TYPE_OLC.equals(calcType)
		|| RepConstantsCommon.CALC_TYPE_LIST.contains(calcType))
	{
	    buisnessElement.setIS_MANDATORY(BigDecimal.ZERO);
	    buisnessElement.setIS_READONLY(BigDecimal.ONE);
	    glCO.getGlstmpltGlsDetVO().setDIV_CODE(null);
	    glCO.setDIV_NAME(null);
	    glCO.getGlstmpltGlsDetVO().setDEPT_CODE(null);
	    glCO.setDEPT_NAME(null);
	    glCO.getGlstmpltGlsDetVO().setFROM_GL(null);
	    glCO.setFROM_GLStr(null);
	    glCO.getGlstmpltGlsDetVO().setTO_GL(null);
	    glCO.setTO_GLStr(null);
	    if((RepConstantsCommon.CALC_TYPE_RTV).equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_LIST.contains(calcType))
	    {
		SessionCO sessionCO = returnSessionObject();
		BigDecimal compCode = sessionCO.getCompanyCode();
		String compDesc = sessionCO.getCompanyName();
		glCO.getGlstmpltGlsDetVO().setGL_COMP(compCode);
		glCO.setGL_COMP_NAME(compDesc);
		glCO.getGlstmpltGlsDetVO().setBRANCH_CODE(null);
		glCO.setBRANCH_NAME(null);
		dispCompBr.setIS_MANDATORY(BigDecimal.ZERO);
		dispCompBr.setIS_READONLY(BigDecimal.ONE);
		if(RepConstantsCommon.CALC_TYPE_LIST.contains(calcType))
		{
		   BigDecimal brCode = sessionCO.getBranchCode();
		   String branchDesc = sessionCO.getBranchName();
		   glCO.getGlstmpltGlsDetVO().setBRANCH_CODE(brCode);
		   glCO.setBRANCH_NAME(branchDesc);
		}
	    }
	    else
	    {
		dispCompBr.setIS_MANDATORY(BigDecimal.ONE);
		dispCompBr.setIS_READONLY(BigDecimal.ZERO);
	    }
	    hm.put("lookuptxt_glCompCode", dispCompBr);
	    hm.put("lookuptxt_branchCode", dispCompBr);
	    hm.put("lookuptxt_divCode", buisnessElement);
	    hm.put("lookuptxt_deptCode", buisnessElement);
	    hm.put("lookuptxt_fromGLCode", buisnessElement);
	    hm.put("lookuptxt_toGLCode", buisnessElement);
	}
	else if(calcType == null)
	{
	    buisnessElement.setIS_READONLY(readOnly);
	    buisnessElement.setIS_MANDATORY(BigDecimal.ONE);

	    if(isComp)
	    {
		hm.put("lookuptxt_branchCode", buisnessElement);
		hm.put("lookuptxt_fromGLCode", buisnessElement);
		hm.put("lookuptxt_toGLCode", buisnessElement);
		hm.put("lookuptxt_divCode", buisnessElement);
		if(readOnly.intValue() == 1)
		{
		    hm.put("lookuptxt_deptCode", buisnessElement);
		}
	    }
	    else
	    {
		hm.put("lookuptxt_deptCode", buisnessElement);
	    }
	}
	else
	{
	    buisnessElement.setIS_MANDATORY(BigDecimal.ONE);
	    buisnessElement.setIS_READONLY(BigDecimal.ZERO);
	    hm.put("lookuptxt_divCode", buisnessElement);
	    hm.put("lookuptxt_deptCode", buisnessElement);
	    hm.put("lookuptxt_fromGLCode", buisnessElement);
	    hm.put("lookuptxt_toGLCode", buisnessElement);
	    hm.put("lookuptxt_glCompCode",buisnessElement);
	    hm.put("lookuptxt_branchCode",buisnessElement);
	    SYS_PARAM_SCREEN_DISPLAYVO dispChkClosingEntry = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(RepConstantsCommon.CALC_TYPE_BALANCE.equals(calcType))
	    {
		dispChkClosingEntry.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    else
	    {
		dispChkClosingEntry.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    hm.put("exclClosingEntry", dispChkClosingEntry);
	}
	setAdditionalScreenParams(hm);
    }

    public String fillLookupBranch() throws JSONException
    {
	try
	{
	    fillLookup(BigDecimal.valueOf(2), "/path/templateMaintReport/branchDetailsLookUpGrid", "Branches List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called the branches lookup
     * 
     * @return
     * @throws Exception
     */
    public String loadLookUpBranchGrid() throws Exception
    {
	try
	{
	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    BigDecimal glCompCode = getCode();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		glCompCode = new BigDecimal(-100);
	    }
	    tmplGridSC.setCOMP_CODE(glCompCode);

	    List<CommonDetailsVO> lst = new ArrayList<CommonDetailsVO>();
	    CommonDetailsVO commonDetailsVO = new CommonDetailsVO();
	    commonDetailsVO.setCODE(BigDecimal.ZERO);
	    commonDetailsVO.setBRIEF_DESC_ENG(getText("all_branch_key"));
	    commonDetailsVO.setLONG_DESC_ENG(getText("all_branch_key"));
	    commonDetailsVO.setBRIEF_DESC_ARAB(getText("all_branch_key"));
	    commonDetailsVO.setLONG_DESC_ARAB(getText("all_branch_key"));
	    lst.add(commonDetailsVO);
	    lst.addAll(templateBO.getBranchesList(tmplGridSC));
	    int branchListCount = templateBO.getBranchesListCount(tmplGridSC);
	    setRecords(branchListCount);
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the branches dependency
     * 
     * @return
     * @throws JSONException
     */
    public String applyBranchDependency() throws JSONException
    {
	try
	{
	    BigDecimal branchCode = getCode1();
	    BigDecimal glCompCode = getCode();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(branchCode == null || branchCode.intValue() == 0)
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(getText("all_branch_key"));
		commonDetVO.setCODE(BigDecimal.ZERO);
	    }
	    else
	    {
		TmplGridSC brSC = new TmplGridSC();
		brSC.setBRANCH_CODE(branchCode);
		brSC.setCOMP_CODE(glCompCode);
		brSC.setGrid(false);
		List<CommonDetailsVO> branchList = templateBO.getBranchesList(brSC);
		commonDetVO = new CommonDetailsVO();
		if(branchList == null || branchList.isEmpty())
		{
		    commonDetVO.setBRIEF_DESC_ENG(null);
		    commonDetVO.setCODE(null);
		}
		else
		{
		    CommonDetailsVO retVal = branchList.get(0);
		    commonDetVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    commonDetVO.setCODE(branchCode);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that fills the division's lookup
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookupDivision() throws JSONException
    {
	try
	{
	    fillLookup(BigDecimal.valueOf(3), "/path/templateMaintReport/divisionDetailsLookUpGrid", "Divisions List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that fills the division's lookup
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookup(BigDecimal isFrom, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = {};
	    String[] colType = {};
	    String[] titles = {};

	    if(BigDecimal.valueOf(1).equals(isFrom) || BigDecimal.valueOf(2).equals(isFrom)) // company
	    // or
	    // branch
	    // lookup
	    {
		name = new String[] { "CODE", "BRIEF_DESC_ENG", "LONG_DESC_ENG", "BASE_CURRENCY", "REMARK" };
		colType = new String[] { "number", "text", "text", "number", "text" };
		titles = new String[] { getText("reporting.lkpCode"), getText("reporting.lkpBriefEng"),
			getText("reporting.lkpLongEng"), getText("Base_CY_key"), getText("reporting.lkpRemark") };
	    }
	    else if(BigDecimal.valueOf(3).equals(isFrom)) // division lookup
	    {
		name = new String[] { "CODE", "BRIEF_DESC_ENG", "LONG_DESC_ENG" };
		colType = new String[] { "number", "text", "text" };
		titles = new String[] { getText("reporting.lkpCode"), getText("reporting.lkpBriefEng"),
			getText("reporting.lkpLongEng") };

	    }
	    else if(BigDecimal.valueOf(4).equals(isFrom)) // department lookup
	    {
	    	name = new String[] { "CODE","BRIEF_DESC_ENG" ,"COMPANY_CODE", "COMPANY_NAME", "DIVISION_CODE", "DIVISION_NAME"};
			colType = new String[] { "number", "text" ,"number", "text", "number", "text"};
			titles = new String[] { getText("reporting.lkpCode"), getText("reporting.lkpBriefName") ,getText("reporting.lkpCompany"), getText("reporting.lkpCompanyName"),
				getText("reporting.lkpDivision"), getText("reporting.lkpDivisionName")};
	    }
	    else if(BigDecimal.valueOf(5).equals(isFrom)) // Gl lookup
	    {
		name = new String[] { "CODE", "BRIEF_DESC_ENG", "LONG_DESC_ENG", "GL_CATEGORY", "GL_TYPE" };
		colType = new String[] { "number", "text", "text", "text", "text" };
		titles = new String[] { getText("reporting.lkpCode"), getText("reporting.lkpBriefEng"),
			getText("reporting.lkpLongEng"), getText("reporting.lkpCategory"),
			getText("reporting.lkpGlType") };

	    }

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    // grid.setId(gridId);
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    // grid.setFilter(false);
	    grid.setUrl(gridUrl);

	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(true);

		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, commonDetailsSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that fills the division's lookup
     * 
     * @return
     * @throws JSONException
     */
    public String loadLookUpDivisonGrid() throws Exception
    {
	try
	{
	    BigDecimal glCompCode = getCode();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		glCompCode = new BigDecimal(-100);
	    }

	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    tmplGridSC.setCOMP_CODE(glCompCode);

	    List<CommonDetailsVO> lst = new ArrayList<CommonDetailsVO>();    
	    tmplGridSC.setBRIEF_DESC_ARAB(getText("gl.allDivisions"));
	    lst.addAll(templateBO.getDivisonsList(tmplGridSC));
	    int divListCount = templateBO.getDivisonsListCount(tmplGridSC);

	    setRecords(divListCount);
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the division's dependency
     * 
     * @return
     * @throws JSONException
     */
    public String applyDivisionDependency() throws JSONException
    {
	try
	{
	    BigDecimal divCode = getCode();
	    BigDecimal glCompCode = getCode1();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(NumberUtil.isEmptyDecimal(divCode))
	    {
		commonDetVO = new CommonDetailsVO();
		updateGLDisplay(BigDecimal.ONE, false,null);
	    }
	    else
	    {
		TmplGridSC divSC = new TmplGridSC();
		divSC.setDIV_CODE(divCode);
		divSC.setCOMP_CODE(glCompCode);
		divSC.setGrid(false);
		divSC.setBRIEF_DESC_ARAB(getText("gl.allDivisions"));
		List<CommonDetailsVO> divList = templateBO.getDivisonsList(divSC);
		commonDetVO = new CommonDetailsVO();
		if(divList == null || divList.isEmpty())
		{
		    commonDetVO.setBRIEF_DESC_ENG(null);
		    commonDetVO.setCODE(null);
		    updateGLDisplay(BigDecimal.ZERO, false,null);
		}
		else
		{
		    CommonDetailsVO retVal = divList.get(0); //case of all divisions
		    if(divList.size()>1) //case of other divisions
		    {
			retVal =divList.get(1);
		    }
		    commonDetVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    commonDetVO.setCODE(divCode);
		    updateGLDisplay(BigDecimal.ZERO, false,null);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * method called on dependeny of calc type dropdown
     * @return
     * @throws JSONException
     */
    public String applyCalcTypeDependency() throws JSONException
    {
	updateGLDisplay(BigDecimal.ONE, false, updates);
	return SUCCESS;
    }
    
    /**
     * method that fills the lookup dept
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookupDept() throws JSONException
    {
	try
	{
	    fillLookup(BigDecimal.valueOf(4), "/path/templateMaintReport/deptDetailsLookUpGrid", "Departments List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that fills the lookup dept
     * 
     * @return
     * @throws JSONException
     */
    public String loadLookUpDeptGrid() throws Exception
    {
	try
	{
	    copyproperties(tmplGridSC);
	    setSearchFilter(tmplGridSC);
	    BigDecimal glCompCode = getCode();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		glCompCode = new BigDecimal(-100);
	    }
	    tmplGridSC.setCOMP_CODE(glCompCode);
	    tmplGridSC.setDIV_CODE(getColumn1());
	    tmplGridSC.setBRIEF_DESC_ENG(getText("gl.allDept"));
	    tmplGridSC.setBRIEF_DESC_ARAB(getText("gl.allDivisions"));
	    List<CommonDetailsVO> lst = new ArrayList<CommonDetailsVO>();
	    lst.addAll(templateBO.getDepartmentsList(tmplGridSC));
	    int deptListCount = templateBO.getDepartmentsListCount(tmplGridSC);

	    setRecords(deptListCount);
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that applies dependency on the dept
     * 
     * @return
     * @throws JSONException
     */
    public String applyDeptDependency() throws JSONException
    {
	try
	{
	    String divCode = getUpdates();
	    BigDecimal deptCode = getCode();
	    BigDecimal glCompCode = getCode1();
	    if(glCompCode == null || glCompCode.intValue() == 0 || NumberUtil.isEmptyDecimal(glCompCode))
	    {
		String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.ENTER_COMPANY_CODE,
			returnSessionObject().getLanguage());
		addDependencyMsg(msg, null);
	    }
	    else if(deptCode == null || deptCode.intValue() == 0)
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(getText("gl.allDept"));
		commonDetVO.setCODE(BigDecimal.ZERO);
	    }
	    else
	    {
		TmplGridSC deptSC = new TmplGridSC();
		deptSC.setDEPT_CODE(deptCode);
		deptSC.setCOMP_CODE(glCompCode);
		deptSC.setGrid(false);
		deptSC.setDIV_CODE(new BigDecimal(divCode));
		List<CommonDetailsVO> deptList = templateBO.getDepartmentsList(deptSC);
		commonDetVO = new CommonDetailsVO();
		if(deptList == null || deptList.isEmpty())
		{
		    commonDetVO.setBRIEF_DESC_ENG(null);
		    commonDetVO.setCODE(null);
		}
		else
		{
		    CommonDetailsVO retVal = deptList.get(0);
		    commonDetVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    commonDetVO.setCODE(deptCode);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that fills the accounts lookup
     * 
     * @return
     * @throws JSONException
     */
    public String fillLookupAcc() throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "COMP_CODE", "BRANCH_CODE", "CURRENCY_CODE", "GL_CODE", "CIF_SUB_NO", "SL_NO",
		    "TEMP_CODE", "TMPLT_LINE_NBR", "SUB_LINE_NBR", "INCLUDE" };
	    String[] colType = { "text", "text", "text", "text", "text", "text", "text", "text", "text", "number" };
	    String[] titles = { "company", "Branch", "currency", "GL", "CIF", "SL", "Template", "Templ line Nb.",
		    "Sub line Nb.", "Include" };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setId("accGridId");
	    grid.setCaption("Accounts list");
	    grid.setRowNum("10");
	    grid.setFilter(false);
	    grid.setMultiselect("true");
	    grid.setUrl("/path/templateMaintReport/accDetailsLookUpGrid");

	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(true);

		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, commonDetailsSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that loads the list of gls
     * 
     * @return
     * @throws JSONException
     */
    public String loadGLList() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = null;
	    String zLineNb = null;
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    if(allTempl != null)
	    {
		LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
		LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
		if(tempCodeWithLineNb != null && !tempCodeWithLineNb.equals(""))
		{
		    zTemplCode = tempCodeWithLineNb.split("~")[0];
		    zLineNb = tempCodeWithLineNb.split("~")[1];
		}

		if(zTemplCode == null || zLineNb == null)
		{
		    List<GLSTMPLTGLSDETCO> GLList = new ArrayList<GLSTMPLTGLSDETCO>();
		    setRecords(GLList.size());
		    setGridModel(GLList);
		}
		else
		{

		    BigDecimal compCode = sessionCO.getCompanyCode();

		    String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();
		    // printHashContents(dbLinesMap, "asdfasdfadsfdbline");
		    // get the glstmpltCO in order to fill it with dbGL ;
		    // first check in the dbLinesList;
		    GLSTMPLTCO glstmpltCOO = getglstmpltCOById(lineConcatKey);
		    boolean addCheckIfReorgOrder = false;// to be sure that it's
		    // not a same line as
		    // an existing one that
		    // already has
		    // gls and the new one does not has to not load the gls of
		    // the already existing db line in the new line object

		    if(glstmpltCOO == null)
		    {
			List<GLSTMPLTGLSDETCO> GLList = new ArrayList<GLSTMPLTGLSDETCO>();
			setRecords(GLList.size());
			setGridModel(GLList);
		    }
		    else
		    {
			Iterator<GLSTMPLTCO> glIt = dbLinesMap.values().iterator();
			while(glIt.hasNext())
			{
			    GLSTMPLTCO lCO = glIt.next();
			    if(glstmpltCOO.getNewLineNumber() != null
				    && (lCO.getGlstmpltVO().getLINE_NBR().intValue() == glstmpltCOO.getNewLineNumber()
					    .intValue() && lCO.getNewLineNumber().intValue() != lCO.getGlstmpltVO()
					    .getLINE_NBR().intValue()))
			    {
				addCheckIfReorgOrder = true;
			    }
			}
			LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> dbGLMap = glstmpltCOO.getDbGLMap();
			LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> addGLMap = glstmpltCOO.getAddGLMap();
			HashMap<BigDecimal, GLSTMPLTGLSDETCO> delGLMap = glstmpltCOO.getDelGLMap();
			LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> finalMap = new LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>();
			// get the data from the DB
			/*
			 * it means when the dbLinesMap is empty and no empty
			 * because all the records are deleted
			 */
			//check if the selected line is an new one or from DB so we will not go to the DB and get the GLs in case of new line
			boolean isNewRec=addLinesMap.get(new BigDecimal(lineConcatKey))==null?false:true; 
			if(!isNewRec && !addCheckIfReorgOrder && (dbGLMap == null || (dbGLMap.isEmpty() && delGLMap.isEmpty()))) 
			{
			    GLSTMPLTGLSDETSC glCO = new GLSTMPLTGLSDETSC();
			    glCO.setCOMP_CODE(compCode);
			    glCO.setCODE(new BigDecimal(zTemplCode));
			    glCO.setLINE_NBR(new BigDecimal(zLineNb));
			    glCO.setLANG_CODE(sessionCO.getLanguage());

			    // get the db gls as list then put it in the
			    // linkedHashMap
			    List<GLSTMPLTGLSDETCO> glsList = templateBO.getGLsByLine(glCO);
			    for(int i = 0; i < glsList.size(); i++)
			    {
				GLSTMPLTGLSDETCO glCOO = glsList.get(i);
				if(BigDecimal.ZERO.equals(glCOO.getGlstmpltGlsDetVO().getBRANCH_CODE()))
				{
				    glCOO.setBRANCH_NAME(getText("all_branch_key"));
				}
				if(BigDecimal.ZERO.equals(glCOO.getGlstmpltGlsDetVO().getDIV_CODE()))
				{
				    glCOO.setDIV_NAME(getText("gl.allDivisions"));
				}
				if(BigDecimal.ZERO.equals(glCOO.getGlstmpltGlsDetVO().getDEPT_CODE()))
				{
				    glCOO.setDEPT_NAME(getText("gl.allDept"));
				}
				dbGLMap.put(glCOO.getConcatKey(), glCOO);
			    }
			    glstmpltCOO.setDbGLMap(dbGLMap);

			}
			finalMap.putAll(dbGLMap);
			finalMap.putAll(addGLMap);
			// code to handle changed lines numbers
			Iterator<GLSTMPLTCO> itAddLines = addLinesMap.values().iterator();
			Iterator<GLSTMPLTGLSDETCO> itFinalMap = finalMap.values().iterator();
			while(itAddLines.hasNext())
			{
			    GLSTMPLTCO tmpl = itAddLines.next();
			    while(itFinalMap.hasNext())
			    {
				GLSTMPLTGLSDETCO gg = itFinalMap.next();
				if(tmpl.getNewLineNumber() != null
					&& tmpl.getGlstmpltVO().getLINE_NBR().equals(
						gg.getGlstmpltGlsDetVO().getLINE_NBR()))
				{
				    gg.getGlstmpltGlsDetVO().setLINE_NBR(tmpl.getNewLineNumber());
				}
			    }
			}

			Iterator<GLSTMPLTCO> itDbLines = dbLinesMap.values().iterator();
			itFinalMap = finalMap.values().iterator();
			while(itDbLines.hasNext())
			{
			    GLSTMPLTCO tmpl = itDbLines.next();
			    while(itFinalMap.hasNext())
			    {
				GLSTMPLTGLSDETCO gg = itFinalMap.next();
				if(tmpl.getNewLineNumber() != null
					&& tmpl.getGlstmpltVO().getLINE_NBR().equals(
						gg.getGlstmpltGlsDetVO().getLINE_NBR()))
				{
				    gg.setNewLineNumber(tmpl.getNewLineNumber());
				}
			    }
			}

			// end code

			//added to fix the loading of data on the next page
		    copyproperties(tmplGridSC);
		    int nbRec = tmplGridSC.getNbRec();
		    int recToSkip = tmplGridSC.getRecToskip();
		    int maxRec = nbRec + recToSkip;
		    if(finalMap.size() < maxRec)
		    {
			maxRec = finalMap.size();
		    }
		    List<GLSTMPLTGLSDETCO> GLList = new ArrayList<GLSTMPLTGLSDETCO>();
		    for(int j = recToSkip; j < maxRec; j++)
		    {
			GLList.add((GLSTMPLTGLSDETCO) finalMap.values().toArray()[j]);
		    }

		    setRecords(finalMap.size());
		    setGridModel(GLList);

		    }
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the click of the ok button in gl form
     * 
     * @return
     * @throws JSONException
     */
    public String validateGL() throws JSONException // submit GL form
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    String lang = sessionCO.getLanguage();
	    String appName = sessionCO.getCurrentAppName();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    HashMap<String, String> calcTypeMap = allTempl.getCalcTypeMap();
	    String calcTypeName = calcTypeMap.get(glCO.getGlstmpltGlsDetVO().getCALC_TYPE());
	    boolean exclClosingEntryChk = glCO.isEXCLUDE_CLOSING_ENTRY_CHK();
	    String templateCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = templateCodeWithLineNb.split("~")[0];
	    String zLineNb = templateCodeWithLineNb.split("~")[1];
	    String exclClosingEntry = exclClosingEntryChk ? "YES" : "NO";
	    glCO.setCALC_TYPE_NAME(calcTypeName);
	    if(NumberUtil.isEmptyDecimal(glCO.getGlstmpltGlsDetVO().getFROM_GL()))
	    {
		glCO.getGlstmpltGlsDetVO().setFROM_GL(null);
		glCO.getGlstmpltGlsDetVO().setTO_GL(null);
	    }
	    BigDecimal percentage = glCO.getGlstmpltGlsDetVO().getPERCENTAGE();
	    if(NumberUtil.isEmptyDecimal(percentage))
	    {
		glCO.getGlstmpltGlsDetVO().setPERCENTAGE(null);
	    }

	    glCO.getGlstmpltGlsDetVO().setEXCLUDE_CLOSING_ENTRY(exclClosingEntry);
	    glCO.getGlstmpltGlsDetVO().setLINE_NBR(new BigDecimal(zLineNb));
	    glCO.getGlstmpltGlsDetVO().setCODE(new BigDecimal(zTemplCode));
	    glCO.getGlstmpltGlsDetVO().setCOMP_CODE(compCode);

	    String glConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString()
		    + glCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().toString();
	    glCO.setConcatKey(new BigDecimal(glConcatKey));
	    fillSelecAccMap(glCO, allTempl, zLineNb);

	    // get the related glstmpltCO

	    String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();

	    // get the glstmpltCO in order to fill the glMap of the glstmpltCO
	    GLSTMPLTCO glstmpltCOO = getglstmpltCOById(lineConcatKey);
	    if(glstmpltCOO.getNewLineNumber() != null)
	    {
		glCO.setNewLineNumber(glstmpltCOO.getNewLineNumber());
	    }
	    // check if it is a new GL
	    LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> addGLMap = glstmpltCOO.getAddGLMap();
	    LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> dbGLMap = glstmpltCOO.getDbGLMap();
	    BigDecimal glConcatKeyNb = new BigDecimal(glConcatKey);
	    GLSTMPLTGLSDETCO modifGLVO = addGLMap.get(glConcatKeyNb);
	    //added the below condition to for special behavior for COC calcType
	    boolean skip = false;
	    String calcType = glCO.getGlstmpltGlsDetVO().getCALC_TYPE();
	    if(RepConstantsCommon.CALC_CNT_CIF.equals(calcType) || RepConstantsCommon.CALC_TYPE_IBC.equals(calcType)
	    	|| RepConstantsCommon.CALC_INDEP_CNT_CIF.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_ILC.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_LG.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_OBC.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_OLC.equals(calcType))
	    {
		if(glCO.getGlstmpltGlsDetVO().getGL_COMP()== null
			|| glCO.getGlstmpltGlsDetVO().getBRANCH_CODE()==null)
		{
		    glCO.getGlstmpltGlsDetVO().setDIV_CODE(BigDecimal.ONE);
		    glCO.getGlstmpltGlsDetVO().setDEPT_CODE(BigDecimal.ONE);
		    glCO.getGlstmpltGlsDetVO().setFROM_GL(BigDecimal.ONE);
		    glCO.getGlstmpltGlsDetVO().setTO_GL(BigDecimal.ONE);
		}
		else
		{
		    skip = true;
		}
	    }
	    else if(RepConstantsCommon.CALC_TYPE_RTV.equals(glCO.getGlstmpltGlsDetVO().getCALC_TYPE())
		    || RepConstantsCommon.CALC_TYPE_LIST.contains(glCO.getGlstmpltGlsDetVO().getCALC_TYPE()))
	    {
		skip = true;
	    }
	    // put validation code
	    if(!skip)
	    {
		templateBO.checkRequiredFields(glCO, get_pageRef(), compCode, appName, lang);
	    }
	    // end put validation code
	    if(modifGLVO == null)
	    {
		if(dbGLMap.get(glConcatKeyNb) == null)// add
		{
		    glstmpltCOO.getAddGLMap().put(new BigDecimal(glConcatKey), glCO);
		}
		else
		{
		    modifGLVO = dbGLMap.get(glConcatKeyNb);

		    if(glstmpltCOO.getOldGLMap().get(glConcatKeyNb) == null)
		    {
			glstmpltCOO.getOldGLMap().put(glConcatKeyNb, modifGLVO);
		    }
		    dbGLMap.put(glConcatKeyNb, glCO);
		    glstmpltCOO.getModifGLMap().put(glConcatKeyNb, glCO);
		}
	    }
	    else
	    // we are modifying a new GL
	    {
		addGLMap.put(glConcatKeyNb, glCO);
	    }
	    glstmpltCOO.setMaxGLSubLineNb(glCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return "error";
	}
	return SUCCESS;
    }

    /**
     * method that fills the select account map
     * 
     * @param glstmpltGlsDetCO
     * @param glstmpltCO
     * @param lineNb
     */
    public void fillSelecAccMap(GLSTMPLTGLSDETCO glstmpltGlsDetCO, GLSTMPLTCO allTempl, String lineNb)
    {
	try
	{
	    int filled = 0;
	    Iterator<GLSTMPLTCO> itDb = allTempl.getDbLinesMap().values().iterator();
	    while(itDb.hasNext())
	    {
		GLSTMPLTCO gls = itDb.next();
		if(gls.getGlstmpltVO().getLINE_NBR().intValue() == (Integer.parseInt(lineNb)))
		{
		    GLSTMPLTGLSDETCO gg = gls.getDbGLMap().get(glstmpltGlsDetCO.getConcatKey());
		    if(gg == null)
		    {
			gg = gls.getAddGLMap().get(glstmpltGlsDetCO.getConcatKey());
			if(gg != null)
			{
			    glstmpltGlsDetCO.setAddSelectAccListMap(gg.getAddSelectAccListMap());
			    filled = 1;
			}
		    }
		    else
		    {
			glstmpltGlsDetCO.setAddSelectAccListMap(gg.getAddSelectAccListMap());
			filled = 1;
		    }

		}
	    }

	    if(filled == 0)
	    {
		Iterator<GLSTMPLTCO> itAd = allTempl.getAddLinesMap().values().iterator();
		while(itAd.hasNext())
		{
		    GLSTMPLTCO gls = itAd.next();
		    if(gls.getGlstmpltVO().getLINE_NBR().intValue() == (Integer.parseInt(lineNb)))
		    {
			GLSTMPLTGLSDETCO gg = gls.getAddGLMap().get(glstmpltGlsDetCO.getConcatKey());
			if(gg == null)
			{
			    gg = gls.getAddGLMap().get(glstmpltGlsDetCO.getConcatKey());
			    if(gg != null)
			    {
				glstmpltGlsDetCO.setAddSelectAccListMap(gg.getAddSelectAccListMap());
				filled = 1;
			    }
			}
			else
			{
			    glstmpltGlsDetCO.setAddSelectAccListMap(gg.getAddSelectAccListMap());
			    filled = 1;
			}
		    }
		}
		//account list should not be filled if COC
		if(filled == 0
			&& !RepConstantsCommon.CALC_CNT_CIF.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_INDEP_CNT_CIF.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
						.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_TYPE_RTV.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_TYPE_IBC.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_TYPE_ILC.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_TYPE_LG.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_TYPE_OBC.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
			&& !RepConstantsCommon.CALC_TYPE_OLC.equals(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
		        && !RepConstantsCommon.CALC_TYPE_LIST.contains(glstmpltGlsDetCO.getGlstmpltGlsDetVO()
				.getCALC_TYPE())
				)// still we didn't fill any map
						 // => it's a new gl
		// line being created from the select accounts
		// screen
		{
		    SessionCO sessionCO = returnSessionObject();
		    FTR_ACCOUNTSSC sc = new FTR_ACCOUNTSSC();
		    BigDecimal compCode = sessionCO.getCompanyCode();
		    BigDecimal branchCode = sessionCO.getBranchCode();
		    sc.setCompCode(compCode);
		    sc.setBranch(branchCode);
		    sc.setTmpCode(glstmpltGlsDetCO.getGlstmpltGlsDetVO().getCODE());
		    sc.setTmpltLineNbr(new BigDecimal(lineNb));
		    sc.setSubLineNbr(glstmpltGlsDetCO.getGlstmpltGlsDetVO().getLINE_NBR_DET());
		    sc.setDiv(glstmpltGlsDetCO.getGlstmpltGlsDetVO().getDIV_CODE());
		    sc.setDpt(glstmpltGlsDetCO.getGlstmpltGlsDetVO().getDEPT_CODE());
		    sc.setFromGL(glstmpltGlsDetCO.getGlstmpltGlsDetVO().getFROM_GL());
		    sc.setToGL(glstmpltGlsDetCO.getGlstmpltGlsDetVO().getTO_GL());
		    sc.setRecToskip(0);
		    sc.setNbRec(templateBO.getAccountsListCount(sc));

		    List<FTR_ACCOUNTSCO> selectAccList = templateBO.getAccountsList(sc);
		    for(int i = 0; i < selectAccList.size(); i++)
		    {
			FTR_ACCOUNTSCO ftrAccountsCO = selectAccList.get(i);

			glstmpltGlsDetCO.getAddSelectAccListMap().put(ftrAccountsCO.getConcatKey(), ftrAccountsCO);
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }

    /**
     * method that gets a template by its id
     * 
     * @param lineConcatKey
     * @return
     */
    public GLSTMPLTCO getglstmpltCOById(String lineConcatKey)
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	HashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	GLSTMPLTCO glstmpltCO = dbLinesMap.get(new BigDecimal(lineConcatKey));
	if(glstmpltCO == null)
	{
	    HashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
	    glstmpltCO = addLinesMap.get(new BigDecimal(lineConcatKey));
	}
	return glstmpltCO;
    }

    public String openExpression() throws JSONException // load the grid
    {
	return SUCCESS;
    }

    public String openExprList() throws JSONException
    {
	return SUCCESS;
    }

    /**
     * method that opens a criteria
     * 
     * @return
     * @throws JSONException
     */
    public String openCriteria() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    // fill the combo of the operation type
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(5)); // 5= operator
	    // (check table
	    // sys_param_lov_type)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    operatorArrList = (ArrayList<Object>) commonLookupBO.getLookupList(sysParamLovVO);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    // fill a map with key and value of the calcultion type in order to
	    // get the value of the selected key when needed
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    if(allTempl.getOperatorsMap().size() == 0)
	    {
		HashMap<String, String> operMap = allTempl.getOperatorsMap();
		for(int i = 0; i < operatorArrList.size(); i++)
		{
		    SYS_PARAM_LOV_TRANSVO operVO = (SYS_PARAM_LOV_TRANSVO) operatorArrList.get(i);
		    operMap.put(operVO.getVALUE_CODE(), operVO.getVALUE_DESC());
		}
	    }
	    // filling the list for the dmy drp dwn
	    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_CRITERIA_DMY);
	    sysVO.setLANG_CODE(sessionCO.getLanguage());
	    daysMonthYear = (ArrayList) commonLookupBO.getLookupList(sysVO);
	    // filling the list for the malefemale drp dwn
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_MALE_FEMALE);
	    maleFemaleList = (ArrayList) commonLookupBO.getLookupList(sysVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    /**
     * function that sets the gender's desc
     * @param language
     * @param crtCO
     */
    public ArrayList retListDesc(BigDecimal LOV_ID, String language, GLSTMPLT_PARAM_LINKCO crtCO,
	    String criteriaCode)
    {
	ArrayList retList=null;
	try
	{
	    SYS_PARAM_LOV_TRANSVO vo = new SYS_PARAM_LOV_TRANSVO();
	    vo.setLOV_TYPE_ID(LOV_ID);
	    vo.setLANG_CODE(language);
	    retList = (ArrayList) commonLookupBO.getLookupList(vo);
	    for(int k = 0; k < retList.size(); k++)
	    {
		vo = (SYS_PARAM_LOV_TRANSVO) retList.get(k);
		if(criteriaCode.equals(RepConstantsCommon.CRITERIA_GENDER))
		{
		    if(vo.getVALUE_CODE().equals(crtCO.getGlstmpltParamLinkVO().getGENDER()))
		    {
			crtCO.setCODE1_NAME(vo.getVALUE_DESC());
			break;
		    }
		}
		else if(criteriaCode.equals(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION)
			&& vo.getVALUE_CODE().equals(crtCO.getGlstmpltParamLinkVO().getSECURITY_CLASS()))
		{
		    crtCO.setCODE1_NAME(vo.getVALUE_DESC());
		    break;
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return retList;
    }
    /**
     * method that loads a criteria list
     * 
     * @return
     * @throws JSONException
     */
    public String loadCriteriaList() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = null;
	    String zLineNb = null;
	    if(tempCodeWithLineNb != null && !tempCodeWithLineNb.equals(""))
	    {
		zTemplCode = tempCodeWithLineNb.split("~")[0];
		zLineNb = tempCodeWithLineNb.split("~")[1];
	    }

	    if(zTemplCode == null || zLineNb == null)
	    {
		List<GLSTMPLT_PARAM_LINKCO> crtList = new ArrayList<GLSTMPLT_PARAM_LINKCO>();
		setRecords(crtList.size());
		setGridModel(crtList);
	    }
	    else
	    {

		BigDecimal compCode = sessionCO.getCompanyCode();

		String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();

		// get the glstmpltCO in order to fill it with dbCrt ;
		// first check in the dbLinesList;
		GLSTMPLTCO glstmpltCOO = getglstmpltCOById(lineConcatKey);

		if(glstmpltCOO == null)
		{
		    List<GLSTMPLT_PARAM_LINKCO> crtList = new ArrayList<GLSTMPLT_PARAM_LINKCO>();
		    setRecords(crtList.size());
		    setGridModel(crtList);
		}
		else
		{
		    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> dbCrtMap = glstmpltCOO.getDbCrtMap();
		    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> addCrtMap = glstmpltCOO.getAddCrtMap();
		    HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> delCrtMap = glstmpltCOO.getDelCrtMap();
		    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> finalMap = new LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();
		    //if false=> line is a new line from reorganize and should not be checked in db
		    //because it will retrieve criteria related to another line
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
		    boolean flag=false;
		    if(allTempl.getNewLinesFromReorganize().get(glstmpltCOO.getNewLineNumber()) == null)
		    {
			flag = true;
		    }	    
		    // get the data from the DB
		    /*
		     * it means when the dbCrtMap is empty and no empty because
		     * all the records are deleted
		     */
		    //check if the selected line is an new one or from DB so we will not go to the DB and get the GLs in case of new line
		    LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
		    boolean isNewRec=addLinesMap.get(new BigDecimal(lineConcatKey))==null?false:true; 
		    if((dbCrtMap == null || (dbCrtMap.isEmpty() && delCrtMap.isEmpty())) && flag && !isNewRec)
		    {
			TmplGridSC crtSC = new TmplGridSC();
			crtSC.setCOMP_CODE(compCode);
			crtSC.setCODE(new BigDecimal(zTemplCode));
			crtSC.setLINE_NBR(new BigDecimal(zLineNb));
			crtSC.setLANG_CODE(sessionCO.getLanguage());
			/*
			 * we get the list cs it is ordered fill it in a
			 * linkedHashMap (dbCrtMap)
			 */
			List<GLSTMPLT_PARAM_LINKCO> dbCrtLst = templateBO.getCrtList(crtSC);
			String table1;
			String table2;
			TmplGridSC detSC;
			List<CommonDetailsVO> detailList;
			CommonDetailsVO retVal;
			for(int i = 0; i < dbCrtLst.size(); i++)
			{
			    GLSTMPLT_PARAM_LINKCO crtCO = dbCrtLst.get(i);
			    // get detail1 and detail2 from tbl1 and tbl2 and
			    // set value inside the CO in order to display
			    // inside the grid
			    // get Detail 1
			    table1 = crtCO.getTABLE_NAME1();
			    if(crtCO.getCRI_TYPE().equals("WCL"))
			    {
				crtCO.setVALUE1("");
				crtCO.setCODE1("");
			    }
			    if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRI_TYPE()))
			    {
				maleFemaleList=retListDesc(RepConstantsCommon.TEMPLATE_MALE_FEMALE, sessionCO
					.getLanguage(), crtCO, RepConstantsCommon.CRITERIA_GENDER);
			    }
			    if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtCO.getCRI_TYPE()))
			    {
				securityClassList=retListDesc(RepConstantsCommon.TEMPLATE_SCL_CRIT_LOV, sessionCO
					.getLanguage(), crtCO, RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION);
			    }

			    if(table1 != null && !table1.trim().equals(""))
			    {
				if(table1.toLowerCase(Locale.ENGLISH).equals(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
				{
				    detSC = new TmplGridSC();
				    detSC.setGrid(false);
				    detSC.setTABLE_NAME(table1.toUpperCase(Locale.ENGLISH));
				    detSC.setCRITERIA_TYPE_CODE(crtCO.getCRITERIA_TYPE_CODE());
				    if(RepConstantsCommon.CRITERIA_MBK.equals(crtCO.getCRITERIA_TYPE_CODE()))
				    {
					detSC
						.setCODE_STR(((crtCO.getGlstmpltParamLinkVO().getOPERATOR1() == null) || crtCO
							.getGlstmpltParamLinkVO().getOPERATOR1().equals("0")) ? null
							: crtCO.getGlstmpltParamLinkVO().getOPERATOR1());
				    }
				    else
				    {
					detSC
						.setCODE_STR(((crtCO.getCODE1() == null) || crtCO.getCODE1()
							.equals("0")) ? null : crtCO.getCODE1());
				    }
				    detSC.setLANG_CODE(sessionCO.getLanguage());
				    detailList = templateBO.getFilterCrtDetList(detSC);
				}
				else
				{
				    detSC = new TmplGridSC();
				    detSC.setGrid(false);
				    detSC.setTABLE_NAME(table1);
				    detSC.setCODE(crtCO.getCODE1() == null ? null : new BigDecimal(crtCO.getCODE1()
					    .toString()));
				    if(RepConstantsCommon.REGION_BR_VIEW.equals(table1)
					    || RepConstantsCommon.MAIN_ECO_SEC_VIEW1.equals(table1)
					    || RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(table1)
					    || RepConstantsCommon.GL_TERM_VIEW.equals(table1)
					    || RepConstantsCommon.SMT_VIEW.equals(table1))
				    {
					detSC.setCOMP_CODE(null);
				    }
				    else
				    {
					detSC.setCOMP_CODE(sessionCO.getCompanyCode());
				    }
				    if(RepConstantsCommon.GL_TERM_VIEW.equals(table1))
				    {
					detSC.setCODE_STR(crtCO.getCODE1());
				    }
				    if(RepConstantsCommon.SMT_VIEW.equals(table1))
				    {
					detSC.setOPTION_CODE(crtCO.getGlstmpltParamLinkVO().getCRITERIA_CODE());
				    }
				    detailList = templateBO.getFilterCrtDetLList(detSC);
				}
				if(detailList != null && !detailList.isEmpty())
				{
				    if(RepConstantsCommon.CRT_CAF_VIEW.equals(table1))
				    {
					CommonDetailsVO cafVO;
					BigDecimal code2 = null;
					for(int j = 0; j < detailList.size(); j++)
					{
					    cafVO = detailList.get(j);
					    if(cafVO.getCODE_STR().equals(crtCO.getCODE1()))
					    {
						crtCO.setCODE1_NAME(cafVO.getBRIEF_DESC_ENG());
					    }
					    if(!(StringUtil.nullToEmpty(cafVO.getBAS_EXPRESSION_CLASS_CODE()))
						    .equals(""))
					    {
						code2 = new BigDecimal(cafVO.getBAS_EXPRESSION_CLASS_CODE());
						if(code2.equals(crtCO.getGlstmpltParamLinkVO().getCODE2()))
						{
						    crtCO.setCODE2_NAME(cafVO.getBAS_EXPRESSION_CLASS_DESC());
						}
					    }
					}
				    }
				    else
				    {
					retVal = detailList.get(0);
					if(retVal != null)
					{
					    crtCO.setCODE1_NAME(retVal.getBRIEF_DESC_ENG());
					    if(crtCO.getCODE1() == null
						    || crtCO.getCODE1().equals(RepConstantsCommon.ZERO))
					    {
						crtCO.setCODE1(retVal.getCODE_STR());
					    }
					}
				    }
				}
			    }
			    table2 = crtCO.getTABLE_NAME2();
			    if(table2 != null && !table2.trim().equals(""))
			    {
				if(table2.toLowerCase(Locale.ENGLISH).equals(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
				{
				    detSC = new TmplGridSC();
				    detSC.setGrid(false);
				    detSC.setTABLE_NAME(table2.toUpperCase(Locale.ENGLISH));
				    detSC.setCRITERIA_TYPE_CODE(crtCO.getCRITERIA_TYPE_CODE());
				    detSC.setCODE_STR(crtCO.getGlstmpltParamLinkVO().getCODE2().toString());
				    detSC.setLANG_CODE(sessionCO.getLanguage());
				    detailList = templateBO.getFilterCrtDetList(detSC);
				}
				else
				{
				    detSC = new TmplGridSC();
				    detSC.setGrid(false);
				    if(table2.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS"))
				    {
					detSC.setSECTOR_CODE((crtCO.getCODE1() == null) ? null : (new BigDecimal(crtCO
						.getCODE1().toString())));
				    }
				    else
				    {
					detSC.setSECTOR_CODE(null);
				    }
				    detSC.setTABLE_NAME(table2);
				    if(RepConstantsCommon.CRT_CAF_VIEW_SMT.equals(table2))
				    {
					detSC.setCODE(crtCO.getGlstmpltParamLinkVO().getSECURITY_CODE1());
				    }
				    else
				    {
					detSC.setCODE(new BigDecimal(crtCO.getGlstmpltParamLinkVO().getCODE2()
						.toString()));
				    }
				    if(!RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(table2)
					    && !RepConstantsCommon.CRT_CAF_VIEW_SMT.equals(table2))
				    {
					detSC.setCOMP_CODE(sessionCO.getCompanyCode());
				    }
				    detSC.setCODE1(new BigDecimal(crtCO.getCODE1()));
				    detailList = templateBO.getFilterCrtDetLList(detSC);
				}
				if(detailList != null && !detailList.isEmpty())
				{
				    if(RepConstantsCommon.CRT_CAF_VIEW_SMT.equals(table2))
				    {
					retVal = detailList.get(0);
					if(retVal != null)
					{
					    crtCO.setCODE3(retVal.getCODE_STR());
					    crtCO.setCODE3_NAME(retVal.getBRIEF_DESC_ENG());
					}
				    }
				    else
				    {
					retVal = detailList.get(0);
					if(retVal != null)
					{
					    crtCO.setCODE2_NAME(retVal.getBRIEF_DESC_ENG());
					}
					if(RepConstantsCommon.CRITERIA_VAL_SEC.equals(crtCO.getCRITERIA_TYPE_CODE()))
					{
					    crtCO.setCODE1_NAME(crtCO.getCODE2_NAME());
					}
				    }
				}
			    }
			    dbCrtMap.put(crtCO.getConcatKey(), crtCO);
			}

			glstmpltCOO.setDbCrtMap(dbCrtMap);

		    }
		    finalMap.putAll(dbCrtMap);
		    finalMap.putAll(addCrtMap);

		    copyproperties(tmplGridSC);
		    int nbRec = tmplGridSC.getNbRec();
		    int recToSkip = tmplGridSC.getRecToskip();
		    int maxRec = nbRec + recToSkip;
		    if(finalMap.size() < maxRec)
		    {
			maxRec = finalMap.size();
		    }
		    ArrayList<GLSTMPLT_PARAM_LINKCO> lst = new ArrayList<GLSTMPLT_PARAM_LINKCO>();
		    for(int i = recToSkip; i < maxRec; i++)
		    {
			lst.add((GLSTMPLT_PARAM_LINKCO) finalMap.values().toArray()[i]);
		    }

		    setRecords(finalMap.size());
		    GLSTMPLT_PARAM_LINKCO co;
		    for(int i = 0; i < lst.size(); i++)
		    {
			co = lst.get(i);
			if(co.getGlstmpltParamLinkVO().getINCLUDE().equalsIgnoreCase("yes"))
			{
			    co.setINCLUDE_TRANS(getText("yes_key"));
			}
			else if(co.getGlstmpltParamLinkVO().getINCLUDE().equalsIgnoreCase("no"))
			{
			    co.setINCLUDE_TRANS(getText("no_key"));
			}
		    }
		    setGridModel(lst);
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * Method that remove the value from the operator property of the first expr object
     * @param dbExprMap
     * @param addExprMap
     */
    public void removeOperatorVal(LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> dbExprMap,
	    LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> addExprMap)
    {
	int smallestLine = 0;
	int currentLine = 0;
	BigDecimal keyToUse = BigDecimal.ZERO;
	boolean firstLoop = true;
	for(Entry<BigDecimal, FTR_TMPLT_EXPRCO> entry : dbExprMap.entrySet())
	{
	    currentLine = entry.getValue().getFtrTmpltExprVO().getLINE_NO().intValue();
	    if(firstLoop)
	    {
		smallestLine = currentLine;
		firstLoop = false;
		keyToUse = entry.getKey();
	    }
	    else if (currentLine < smallestLine)
	    {
		smallestLine = currentLine;
		keyToUse = entry.getKey();
	    }
	}
	if(smallestLine == 0)
	{
	    firstLoop = true;
	}
	for(Entry<BigDecimal, FTR_TMPLT_EXPRCO> entry : addExprMap.entrySet())
	{
	    currentLine = entry.getValue().getFtrTmpltExprVO().getLINE_NO().intValue();
	    if(firstLoop)
	    {
		smallestLine = currentLine;
		firstLoop = false;
		keyToUse = entry.getKey();
	    }
	    else if(currentLine < smallestLine)
	    {
		smallestLine = currentLine;
		keyToUse = entry.getKey();
	    }
	}
	if(dbExprMap.get(keyToUse) == null)
	{
	    if(addExprMap.get(keyToUse) != null)
	    {
		addExprMap.get(keyToUse).getFtrTmpltExprVO().setOPERATOR(null);
	    }
	}
	else
	{
	    dbExprMap.get(keyToUse).getFtrTmpltExprVO().setOPERATOR(null);
	}
    }

    /**
     * method that loads the expressions list
     * 
     * @return
     * @throws JSONException
     */
    public String loadExprList() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = null;
	    String zLineNb = null;

	    if(tempCodeWithLineNb != null && !tempCodeWithLineNb.equals(""))
	    {
		zTemplCode = tempCodeWithLineNb.split("~")[0];
		zLineNb = tempCodeWithLineNb.split("~")[1];
	    }

	    if(zTemplCode == null || zLineNb == null || zTemplCode.equals(""))
	    {
		List<FTR_TMPLT_EXPRCO> exprList = new ArrayList<FTR_TMPLT_EXPRCO>();
		setRecords(exprList.size());
		setGridModel(exprList);
	    }
	    else
	    {

		BigDecimal compCode = sessionCO.getCompanyCode();
		String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();

		// get the glstmpltCO in order to fill it with dbGL ;
		// first check in the dbLinesList;
		GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);

		if(glstmpltCO == null)
		{
		    List<FTR_TMPLT_EXPRCO> exprList = new ArrayList<FTR_TMPLT_EXPRCO>();
		    setRecords(exprList.size());
		    setGridModel(exprList);
		}
		else
		{
		    LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> dbExprMap = glstmpltCO.getDbExprMap();
		    LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> addExprMap = glstmpltCO.getAddExprMap();
		    HashMap<BigDecimal, FTR_TMPLT_EXPRCO> delExprMap = glstmpltCO.getDelExprMap();

		    // check if we are deleting a an expression line before
		    // reloading the grid
		    String exprId = getId();
		    String oper = getOper();
		    if(exprId != null && exprId.indexOf("new_") == -1 && oper != null && oper.equals("del"))
		    {
			FTR_TMPLT_EXPRCO delExprCO = dbExprMap.get(new BigDecimal(exprId));

			if(delExprCO == null)// found in the added expr.
			{
			    delExprCO = addExprMap.get(new BigDecimal(exprId));
			    delExprMap.put(new BigDecimal(exprId), delExprCO);
			    addExprMap.remove(new BigDecimal(exprId));

			}
			else
			// found in the DB
			{
			    delExprMap.put(new BigDecimal(exprId), delExprCO);
			    dbExprMap.remove(new BigDecimal(exprId));
			    // reset the expr_order
			    Iterator<FTR_TMPLT_EXPRCO> itEx = dbExprMap.values().iterator();
			    FTR_TMPLT_EXPRCO exprr;
			    int cnt = 0;
			    while(itEx.hasNext())
			    {
				cnt++;
				exprr = itEx.next();
				exprr.setEXP_ORDER(new BigDecimal(cnt++));
			    }
			}
			removeOperatorVal(dbExprMap,addExprMap);
		    }

		    LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> finalMap = new LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO>();
		    // get the data from the DB
		    /*
		     * it means when the dbExprMap is empty and no empty because
		     * all the records are deleted
		     */
		    if(dbExprMap == null || (dbExprMap.isEmpty() && delExprMap.isEmpty())) 
		    {
			GLSTMPLTSC lineSC = new GLSTMPLTSC();
			lineSC.setCODE(new BigDecimal(zTemplCode));
			lineSC.setCOMP_CODE(compCode);
			lineSC.setLINE_NBR(new BigDecimal(zLineNb));
			/*
			 * we get the list cs it is ordered fill it in a
			 * linkedHashMap (dbExprMap)
			 */
			List<FTR_TMPLT_EXPRCO> dbExprList = templateBO.getExprList(lineSC); 
			for(int i = 0; i < dbExprList.size(); i++)
			{
			    FTR_TMPLT_EXPRCO exprCO = dbExprList.get(i);
			    dbExprMap.put(exprCO.getConcatKey(), exprCO);
			    exprCO.setEXP_ORDER(new BigDecimal(dbExprMap.size()));
			}
			glstmpltCO.setDbExprMap(dbExprMap);

			// if the exprType is Line then put the value of the
			// exprLineNb inside the property VALUE cs we removed
			// the property
			// exprLineNb from the grid and we replace it with the
			// property Value that will be at the same time for
			// 'Line' and 'Value' types
			Iterator<FTR_TMPLT_EXPRCO> it = dbExprMap.values().iterator();
			FTR_TMPLT_EXPRCO expCO;
			while(it.hasNext())
			{
			    expCO = it.next();
			    if(expCO.getFtrTmpltExprVO().getEXP_TYPE().equals("L"))
			    {
				expCO.getFtrTmpltExprVO().setEXP_VALUE(expCO.getFtrTmpltExprVO().getEXP_LINE());
				expCO.getFtrTmpltExprVO().setEXP_LINE(null);
			    }
			}
		    }

		    Iterator<FTR_TMPLT_EXPRCO> itt = addExprMap.values().iterator();
		    int i = 0;
		    while(itt.hasNext())
		    {
			i++;
			FTR_TMPLT_EXPRCO exprCO = itt.next();
			exprCO.setEXP_ORDER(new BigDecimal(dbExprMap.size() + i));
		    }

		    finalMap.putAll(dbExprMap);
		    finalMap.putAll(addExprMap);
		    
		    //added to fix the loading of data on the next page
		    copyproperties(tmplGridSC);
		    int nbRec = tmplGridSC.getNbRec();
		    int recToSkip = tmplGridSC.getRecToskip();
		    int maxRec = nbRec + recToSkip;
		    if(finalMap.size() < maxRec)
		    {
			maxRec = finalMap.size();
		    }
		    ArrayList<FTR_TMPLT_EXPRCO> exprList = new ArrayList<FTR_TMPLT_EXPRCO>();
		    for(int j = recToSkip; j < maxRec; j++)
		    {
		    	exprList.add((FTR_TMPLT_EXPRCO) finalMap.values().toArray()[j]);
		    }

		    setRecords(finalMap.size());
		    setGridModel(exprList);
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that validates an expression
     * 
     * @return
     * @throws JSONException
     */
    public String validateExpr() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    if(updates != null && !updates.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates, FTR_TMPLT_EXPRCO.class);
		ArrayList lstAdd = gu.getLstAdd();
		ArrayList lstMod = gu.getLstModify();
		if(!lstAdd.isEmpty() || !lstMod.isEmpty())
		{

		    FTR_TMPLT_EXPRCO exprCO;
		    String exprConcatKey = "";
		    String tempCodeWithLineNb = getTempCodeWithLineNb();
		    String zTempCode = tempCodeWithLineNb.split("~")[0];
		    String zLineNb = tempCodeWithLineNb.split("~")[1];
		    BigDecimal compCode = sessionCO.getCompanyCode();

		    // get the selected glstmpltCO in order to store the new
		    // Expr inside
		    String lineConcatKey = compCode.toString() + zTempCode.toString() + zLineNb.toString();
		    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
		    LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> dbExprMap = glstmpltCO.getDbExprMap();
		    LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> addExprMap = glstmpltCO.getAddExprMap();
		    HashMap<BigDecimal, FTR_TMPLT_EXPRCO> modifExprMap = glstmpltCO.getModifExprMap();

		    GLSTMPLTSC lineSC = null;
		    FTR_TMPLT_EXPRCO exprDetCO = null;
		    int maxSubLineNb;
		    BigDecimal subLineNb = null;
		    // add the new expr. rows to the addExprMap
		    for(int i = 0; i < lstAdd.size(); i++)
		    {
			maxSubLineNb = glstmpltCO.getMaxSubLineNb();
			// get max.subLineNb+1 from the DB
			if(maxSubLineNb == 0)
			{
			    lineSC = new GLSTMPLTSC();
			    lineSC.setCODE(new BigDecimal(zTempCode));
			    lineSC.setCOMP_CODE(compCode);
			    lineSC.setLINE_NBR(new BigDecimal(zLineNb));
			    exprDetCO = templateBO.getMaxSubLineNb(lineSC);
			    if(exprDetCO == null) // if the glstmpltCO does not
			    // have an expression line
			    {
				subLineNb = BigDecimal.ONE;
			    }
			    else
			    {
				subLineNb = exprDetCO.getFtrTmpltExprVO().getLINE_NO();
			    }
			    glstmpltCO.setMaxSubLineNb(subLineNb.intValue());
			}
			else
			{
			    subLineNb = new BigDecimal(glstmpltCO.getMaxSubLineNb() + 1);
			    glstmpltCO.setMaxSubLineNb(subLineNb.intValue());

			}
			exprCO = (FTR_TMPLT_EXPRCO) lstAdd.get(i);
			exprConcatKey = compCode.toString() + zTempCode + zLineNb + subLineNb.toString();
			exprCO.setConcatKey(new BigDecimal(exprConcatKey));
			exprCO.getFtrTmpltExprVO().setCOMP_CODE(compCode);
			exprCO.getFtrTmpltExprVO().setLINE_NO(subLineNb);
			exprCO.getFtrTmpltExprVO().setCODE(new BigDecimal(zTempCode));
			exprCO.getFtrTmpltExprVO().setTMPLT_LINE_NO(new BigDecimal(zLineNb));
			addExprMap.put(new BigDecimal(exprConcatKey), exprCO);

		    }
		    // update new or db expression
		    BigDecimal exprKey;
		    for(int j = 0; j < lstMod.size(); j++)
		    {
			exprCO = (FTR_TMPLT_EXPRCO) lstMod.get(j);
			exprKey = exprCO.getConcatKey();
			if(addExprMap.get(exprKey) == null)
			{

			    if(glstmpltCO.getOldExprMap().get(exprKey) == null)
			    {
				glstmpltCO.getOldExprMap().put(exprKey, dbExprMap.get(exprKey));
			    }

			    dbExprMap.put(exprKey, exprCO);
			    modifExprMap.put(exprKey, exprCO);
			}
			else
			{
			    addExprMap.put(exprKey, exprCO);
			}

		    }
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";
    }

    public String selectOperatorsUrl() throws JSONException // used to fill the
    // operator combo
    // inside the
    // expression grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(5));
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    operators = (ArrayList) (commonLookupBO.getLookupList(sysParamLovVO));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that generates a criteria sub line number
     * 
     * @return
     * @throws JSONException
     */
    public String generateSubLnNbCrt() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String tempCode = tempCodeWithLineNb.split("~")[0];
	    String lineNb = tempCodeWithLineNb.split("~")[1];
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    int maxCrtSubLineNb;
	    BigDecimal subLineNb = null;
	    String lineConcatKey = compCode.toString() + tempCode + lineNb;
	    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
	    maxCrtSubLineNb = glstmpltCO.getMaxCrtSubLineNb();
	    // get max.subLineNb+1 from the DB
	    // added condition related to deleted map to handle the case where
	    // the user deletes all the gls from the grid
	    // and tries to add a new one(don't get the max from db)
	    if(maxCrtSubLineNb == 0 && glstmpltCO.getDelCrtMap().isEmpty())
	    {
		CommonDetailsSC crtSC = new CommonDetailsSC();
		crtSC.setTEMPLATE_CODE(new BigDecimal(tempCode));
		crtSC.setCOMP_CODE(compCode);
		crtSC.setTEMPLATE_LINE_NO(new BigDecimal(lineNb));
		commonDetVO = templateBO.getMaxCrtSubLineNb(crtSC);
		if(commonDetVO == null) // if the glstmpltCO does not have an
		// expression line
		{
		    subLineNb = BigDecimal.ONE;
		    commonDetVO = new CommonDetailsVO();
		    commonDetVO.setSUB_LINE_NO(subLineNb);
		}
		else
		{
		    subLineNb = commonDetVO.getSUB_LINE_NO();
		}
	    }
	    else
	    {
		subLineNb = new BigDecimal(glstmpltCO.getMaxCrtSubLineNb() + 1);
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setSUB_LINE_NO(subLineNb);

	    }
	}
	catch(BaseException e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    /**
     * method that generates a gl sub line nbr
     * 
     * @return
     * @throws JSONException
     */
    public String generateSubLnNbGL() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String tempCode = tempCodeWithLineNb.split("~")[0];
	    String lineNb = tempCodeWithLineNb.split("~")[1];
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    int maxSubLineNb;
	    BigDecimal subLineNb = null;
	    String lineConcatKey = compCode.toString() + tempCode + lineNb.toString();
	    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
	    maxSubLineNb = glstmpltCO.getMaxGLSubLineNb();
	    // get max.subLineNb+1 from the DB
	    // added condition related to deleted map to handle the case where
	    // the user deletes all the gls from the grid
	    // and tries to add a new one(don't get the max from db)
	    if(maxSubLineNb == 0 && glstmpltCO.getDelGLMap().isEmpty())
	    {
		GLSTMPLTSC lineSC = new GLSTMPLTSC();
		lineSC.setCODE(new BigDecimal(tempCode));
		lineSC.setCOMP_CODE(compCode);
		lineSC.setLINE_NBR(new BigDecimal(lineNb));
		checkSubglstmpltCO = templateBO.getMaxGLSubLineNb(lineSC);
		if(checkSubglstmpltCO == null) // if the glstmpltCO does not
		// have an expression line
		{
		    subLineNb = BigDecimal.ONE;
		    checkSubglstmpltCO = new GLSTMPLTGLSDETCO();
		    checkSubglstmpltCO.getGlstmpltGlsDetVO().setLINE_NBR_DET(subLineNb);
		}
		else
		{
		    subLineNb = checkSubglstmpltCO.getGlstmpltGlsDetVO().getLINE_NBR_DET();
		}
	    }
	    else
	    {
		subLineNb = new BigDecimal(glstmpltCO.getMaxGLSubLineNb() + 1);
		checkSubglstmpltCO = new GLSTMPLTGLSDETCO();
		checkSubglstmpltCO.getGlstmpltGlsDetVO().setLINE_NBR_DET(subLineNb);

	    }
	    if(glstmpltCO.getNewLineNumber() != null)
	    {
		checkSubglstmpltCO.setNewLineNumber(glstmpltCO.getNewLineNumber());
	    }

	}
	catch(BaseException e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    /**
     * method called to check if a line has expression
     * 
     * @return
     * @throws JSONException
     */
    public String checkIfExprLine() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String isExpr = "";
	    String isGL = "";
	    String isCrt = "";
	    String isMismatch = "";

	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal templCode = templateCode;
	    String lineNb = getLineNumber();

	    // get the glstmpltCO
	    String lineConcatKey = compCode.toString() + templCode.toString() + lineNb;
	    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
	    if(null != glstmpltCO)
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
		GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
		LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
		boolean isNewRec=addLinesMap.get(new BigDecimal(lineConcatKey))==null?false:true; 
		// check if the line has a Expr (db or added)
		List<FTR_TMPLT_EXPRCO> dbExprList;
		if(isNewRec)
		{
		    dbExprList=new ArrayList<FTR_TMPLT_EXPRCO>();
		}
		else
		{
		    GLSTMPLTSC lineSC = new GLSTMPLTSC();
		    lineSC.setCODE(templateCode);
		    lineSC.setCOMP_CODE(compCode);
		    lineSC.setLINE_NBR(new BigDecimal(lineNb));
		    dbExprList = templateBO.getExprList(lineSC); 
		}
		

		if(dbExprList.isEmpty())// no expr saved in the DB
		{
		    isExpr = glstmpltCO.getAddExprMap().size() > 0 ? "1" : "0";
		}
		else
		{
		    if(glstmpltCO.getAddExprMap().size() > 0 || (glstmpltCO.getDelExprMap().size() < dbExprList.size()))
		    {
			isExpr = "1";
		    }
		    else
		    {
			isExpr = "0";
		    }
		}
		// check for GLs
		
		List<GLSTMPLTGLSDETCO> dbGLList;
		if(isNewRec)
		{
		    dbGLList=new ArrayList<GLSTMPLTGLSDETCO>();
		}
		else
		{
		    GLSTMPLTGLSDETSC glCO = new GLSTMPLTGLSDETSC();
		    glCO.setCOMP_CODE(compCode);
		    glCO.setCODE(templateCode);
		    glCO.setLINE_NBR(new BigDecimal(lineNb));
		    glCO.setLANG_CODE(sessionCO.getLanguage());
		    dbGLList = templateBO.getGLsByLine(glCO);
		}
		
		if(dbGLList == null || dbGLList.isEmpty())
		{
		    isGL = glstmpltCO.getAddGLMap().size() > 0 ? "1" : "0";
		}
		else
		{
		    if(glstmpltCO.getAddGLMap().size() > 0 || (glstmpltCO.getDelGLMap().size() < dbGLList.size()))
		    {
			isGL = "1";
		    }
		    else
		    {
			isGL = "0";
		    }
		}

		// check for Crts
		List<GLSTMPLT_PARAM_LINKCO> dbCrtList;
		if(isNewRec)
		{
		    dbCrtList=new ArrayList<GLSTMPLT_PARAM_LINKCO>();
		}
		else
		{
		    TmplGridSC crtSC = new TmplGridSC();
		    crtSC.setCOMP_CODE(compCode);
		    crtSC.setCODE(templateCode);
		    crtSC.setLINE_NBR(new BigDecimal(lineNb));
		    crtSC.setLANG_CODE(sessionCO.getLanguage());
		    dbCrtList = templateBO.getCrtList(crtSC);
		}
		
		if(dbCrtList.isEmpty())
		{
		    isCrt = glstmpltCO.getAddCrtMap().size() > 0 ? "1" : "0";
		}
		else
		{
		    if(glstmpltCO.getAddCrtMap().size() > 0 || (glstmpltCO.getDelCrtMap().size() < dbCrtList.size()))
		    {
			isCrt = "1";
		    }
		    else
		    {
			isCrt = "0";
		    }
		}

		// check for the mismatch
		TmplGridSC mismatchSC = new TmplGridSC();
		mismatchSC.setCOMP_CODE(compCode);
		mismatchSC.setCODE(templateCode);
		mismatchSC.setLINE_NBR(new BigDecimal(lineNb));
		mismatchSC.setLANG_CODE(sessionCO.getLanguage());
		List<FTR_MISMATCH_REPORTCO> dbMismatchList = templateBO.getMismatchsList(mismatchSC);
		if(dbMismatchList.isEmpty())
		{
		    isMismatch = glstmpltCO.getAddMismatchMap().size() > 0 ? "1" : "0";
		}
		else
		{
		    if(glstmpltCO.getAddMismatchMap().size() > 0
			    || (glstmpltCO.getDelMismatchMap().size() < dbMismatchList.size()))
		    {
			isMismatch = "1";
		    }
		    else
		    {
			isMismatch = "0";
		    }
		}

		exprOrGLByLineCO = new FTR_TMPLT_EXPRCO();
		exprOrGLByLineCO.setExprOrGLByLine(isGL + isCrt + isExpr + isMismatch);
	    }
	}
	catch(Exception e)
	{

	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that validates a criteria
     * 
     * @return
     * @throws JSONException
     */
    public String validateCrt() throws JSONException // validate Criteria
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    String templateCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = templateCodeWithLineNb.split("~")[0];
	    String zLineNb = templateCodeWithLineNb.split("~")[1];
	    String operName = allTempl.getOperatorsMap().get(crtCO.getGlstmpltParamLinkVO().getOPERATOR());
	    boolean includeChk = crtCO.getINCLUDE_CHK();
	    String include = includeChk ? "YES" : "NO";
	    crtCO.getGlstmpltParamLinkVO().setINCLUDE(include);
	    crtCO.setCRI_LINE_GL("L");
	    crtCO.setOPERATOR_NAME(operName);
	    crtCO.getGlstmpltParamLinkVO().setLINE_NO(new BigDecimal(zLineNb));
	    crtCO.getGlstmpltParamLinkVO().setTEMP_CODE(new BigDecimal(zTemplCode));
	    crtCO.getGlstmpltParamLinkVO().setCOMP_CODE(compCode);
	    //added condition to display in the grid's column details 1 the gender
	    if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRITERIA_TYPE_CODE()))
	    {
		maleFemaleList=retListDesc(RepConstantsCommon.TEMPLATE_MALE_FEMALE, sessionCO.getLanguage(), crtCO,
			RepConstantsCommon.CRITERIA_GENDER);
	    }
	    String crtConcatKey = compCode.toString() + zTemplCode + zLineNb
		    + crtCO.getGlstmpltParamLinkVO().getSUB_LINE_NO().toString();
	    crtCO.setConcatKey(new BigDecimal(crtConcatKey));
	    if(RepConstantsCommon.CRITERIA_VAL_SEC.equals(crtCO.getCRITERIA_TYPE_CODE()))
	    {
		crtCO.setCODE1_NAME(crtCO.getCODE2_NAME());
	    }

	    if(crtCO.getVALUE1() != null
		    && (crtCO.getVALUE1().equals("") || NumberUtil.isEmptyDecimal(new BigDecimal(crtCO.getVALUE1()))))
	    {
		crtCO.setVALUE1(null);
	    }
	    if(crtCO.getVALUE2() != null
		    && (crtCO.getVALUE2().equals("") || NumberUtil.isEmptyDecimal(new BigDecimal(crtCO.getVALUE2()))))
	    {
		crtCO.setVALUE2(null);
	    }

	    // get the related glstmpltCO
	    String lineConcatKey = compCode.toString() + zTemplCode + zLineNb;

	    // get the glstmpltCO in order to fill the crtMap of the glstmpltCO
	    GLSTMPLTCO glstmpltCOO = getglstmpltCOById(lineConcatKey);

	    // check if it is a new crt
	    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> addCrtMap = glstmpltCOO.getAddCrtMap();
	    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> dbCrtMap = glstmpltCOO.getDbCrtMap();
	    // check if the crt has the same info as a crt.already saved
	    fillBusinessHmCrtCO(crtCO);
	    templateBO.checkRequiredFields(crtCO, get_pageRef(), compCode, sessionCO.getCurrentAppName(), sessionCO
		    .getLanguage());

	    BigDecimal crtConcatKeyNb = new BigDecimal(crtConcatKey);
	    GLSTMPLT_PARAM_LINKCO critCO = addCrtMap.get(crtConcatKeyNb);
	    if(critCO == null)
	    {
		if(dbCrtMap.get(crtConcatKeyNb) == null)
		{
		    if(checkIfCrtExist(crtCO, addCrtMap, null) || checkIfCrtExist(crtCO, dbCrtMap, null))
		    {
			setUpdates("1");
		    }
		    else
		    {
			setUpdates("0");
			glstmpltCOO.getAddCrtMap().put(new BigDecimal(crtConcatKey), crtCO);
		    }
		}
		else
		{
		    if(checkIfCrtExist(crtCO, addCrtMap, crtConcatKeyNb)
			    || checkIfCrtExist(crtCO, dbCrtMap, crtConcatKeyNb))
		    {
			setUpdates("1");
		    }
		    else
		    {
			setUpdates("0");
			critCO = dbCrtMap.get(crtConcatKeyNb);
			if(glstmpltCOO.getOldCrtMap().get(crtConcatKeyNb) == null)
			{
			    glstmpltCOO.getOldCrtMap().put(crtConcatKeyNb, critCO);
			}
			dbCrtMap.put(crtConcatKeyNb, crtCO);
			glstmpltCOO.getModifCrtMap().put(crtConcatKeyNb, crtCO);
		    }
		}

	    }
	    else
	    // we are modifying a new criteria
	    {
		if(checkIfCrtExist(crtCO, addCrtMap, crtConcatKeyNb)
			|| checkIfCrtExist(crtCO, dbCrtMap, crtConcatKeyNb))
		{
		    setUpdates("1");
		}
		else
		{
		    setUpdates("0");
		    addCrtMap.put(crtConcatKeyNb, crtCO);
		}
	    }
	    glstmpltCOO.setMaxCrtSubLineNb(crtCO.getGlstmpltParamLinkVO().getSUB_LINE_NO().intValue());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return "error";
	}
	return SUCCESS;
    }

    // public static void printHashContents(HashMap<BigDecimal,
    // GLSTMPLT_PARAM_LINKCO> map, String hashName)
    // {
    // Iterator<Map.Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO>> itFmap =
    // map.entrySet().iterator();
    // while(itFmap.hasNext())
    // {
    // Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO> entry = itFmap.next();
    // BigDecimal theKey = entry.getKey();
    // GLSTMPLT_PARAM_LINKCO gg = entry.getValue();
    // System.out.println("key hash:" + theKey + "     key: " +
    // gg.getConcatKey() + "       new Line: "
    // + gg.getNewLineNumber() + "             true line: " +
    // gg.getGlstmpltParamLinkVO().getSUB_LINE_NO()
    // + "             description: " + gg.getCRITERIA_NAME() + "\n");
    // }
    // }

    /**
     * method the adjusts the display of elements of the screen
     * 
     * @param businessHm
     * @param readOnly
     * @param mandatory
     * @param visible
     * @param labelKey
     * @param compCode
     * @param elementName
     * @param VOCOReference
     * @param propertyName
     */
    public void adjustDisplayCode(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm, BigDecimal readOnly,
	    BigDecimal mandatory, BigDecimal visible, String labelKey, BigDecimal compCode, String elementName,
	    String VOCOReference, String propertyName)
    {
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayCode = new SYS_PARAM_SCREEN_DISPLAYVO();
	screenDisplayCode.setIS_MANDATORY(mandatory);
	screenDisplayCode.setIS_READONLY(readOnly);
	screenDisplayCode.setIS_VISIBLE(visible);
	screenDisplayCode.setLABEL_KEY(labelKey);
	screenDisplayCode.setPROG_REF("T00MT");
	screenDisplayCode.setCOMP_CODE(compCode);
	screenDisplayCode.setELEMENT_NAME(elementName);
	screenDisplayCode.setVO_CO_REFERENCE(VOCOReference);
	screenDisplayCode.setVO_PROPERTY_NAME(propertyName);
	screenDisplayCode.setTRIM_STRING(BigDecimal.ONE);
	screenDisplayCode.setZERO_NOT_ALLOWED(BigDecimal.ZERO);
	if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    screenDisplayCode.setZERO_NOT_ALLOWED(BigDecimal.ZERO);
	}
	screenDisplayCode.setMESSAGE_CODE(null);
	screenDisplayCode.setARABIC_DEPENDANT(BigDecimal.ZERO);
	screenDisplayCode.setAPP_NAME("REP");
	businessHm.put(elementName, screenDisplayCode);
    }

    /**
     * method called on the display of elements in the criteria screen
     * 
     * @param crtCO
     * @throws Exception
     */
    public void fillBusinessHmCrtCO(GLSTMPLT_PARAM_LINKCO crtCO) throws Exception // open
    // line
    // Form
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();

	    HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	    if(crtCO.getCOMPONENT().equals("D") || crtCO.getCOMPONENT().equals("L"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		if(RepConstantsCommon.CRITERIA_SMART.equals(crtCO.getCRITERIA_TYPE_CODE()))
		{
		    GLSTMPLT_CRITERIASC crtSC = new GLSTMPLT_CRITERIASC();
		    crtSC.setCODE(crtCO.getGlstmpltParamLinkVO().getCRITERIA_CODE());
		    crtSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    S_ADDITIONS_OPTIONSVO addOptionsVO = templateBO.checkSmartMandatoryDetails(crtSC);
		    if(addOptionsVO != null && addOptionsVO.getOPTION_TYPE().intValue() > 0)
		    {
			adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE,
				"criteria.details1", compCode, "crtCO.CODE1", null, "CODE1");
		    }
		}

		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.glstmpltParamLinkVO.CODE2", "glstmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", null, "VALUE2");

	    }

	    else if(crtCO.getCOMPONENT().equals("T"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.glstmpltParamLinkVO.CODE2", "glstmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", null, "VALUE2");
	    }

	    else if(crtCO.getCOMPONENT().equals("LL"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.glstmpltParamLinkVO.CODE2", "glstmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", null, "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", null, "VALUE2");
		if(RepConstantsCommon.CRITERIA_CAF.equals(crtCO.getCRITERIA_TYPE_CODE()))
		{
		    adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "criteria.details3",
			    compCode, "crtCO.CODE3", null, "CODE3");
		}
	    }

	    else if(crtCO.getCOMPONENT().equals("DT"))
	    {
		String zOper = crtCO.getCODE1();
		if((">=<=").equals(zOper) || ("><").equals(zOper) || ("><=").equals(zOper) || (">=<").equals(zOper))
		{
		    adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value2",
			    compCode, "crtCO.VALUE2", null, "VALUE2");
		}
		else
		{
		    adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			    compCode, "crtCO.VALUE2", null, "VALUE2");
		}

		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", null, "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.glstmpltParamLinkVO.CODE2", "glstmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", null, "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", null, "VALUE1");

	    }

	    else if(crtCO.getCOMPONENT().equals("TDT") || crtCO.getCOMPONENT().equals("DTT"))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", "crtCO", "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.glstmpltParamLinkVO.CODE2", "glstmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", "crtCO", "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", "crtCO", "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", "crtCO", "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", "crtCO", "VALUE2");
	    }

	    else if(crtCO.getCOMPONENT() == null || crtCO.getCOMPONENT().equals(""))
	    {
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1", "crtCO", "CODE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.glstmpltParamLinkVO.CODE2", "glstmpltParamLinkVO", "CODE2");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details1",
			compCode, "crtCO.CODE1_NAME", "crtCO", "CODE1_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.details2",
			compCode, "crtCO.CODE2_NAME", "crtCO", "CODE2_NAME");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value",
			compCode, "crtCO.VALUE1", "crtCO", "VALUE1");
		adjustDisplayCode(businessHm, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "criteria.value2",
			compCode, "crtCO.VALUE2", "crtCO", "VALUE2");
	    }
	    crtCO.setBusinessHm(businessHm);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

    }

    /**
     * method called to check if a criteria exists
     * 
     * @param crtCO
     * @param addCrtMap
     * @param concatKey
     * @return
     */
    public boolean checkIfCrtExist(GLSTMPLT_PARAM_LINKCO crtCO,
	    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> crtMap, BigDecimal concatKey)
    {
	Iterator<GLSTMPLT_PARAM_LINKCO> it;
	it = crtMap.values().iterator();
	GLSTMPLT_PARAM_LINKCO savedCrtCO;
	while(it.hasNext())
	{
	    savedCrtCO = it.next();
	    if((concatKey == null || !savedCrtCO.getConcatKey().equals(concatKey)) && (
	    // comparing the criteria codes (ex: product class)
		    /* /// */(savedCrtCO.getGlstmpltParamLinkVO().getCRITERIA_CODE()).equals(crtCO
			    .getGlstmpltParamLinkVO().getCRITERIA_CODE())
			    // comparing the code1 (details1)
			    && ((crtCO.getCODE1() != null
				    && !crtCO.getCODE1().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.toString())
				    && savedCrtCO.getCODE1() != null
				    && !savedCrtCO.getCODE1().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.toString()) && ((crtCO
				    .getCODE1()).equals(savedCrtCO.getCODE1()))) || ((savedCrtCO.getCODE1() == null || savedCrtCO
				    .getCODE1().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.toString())) && (crtCO
				    .getCODE1() == null || crtCO.getCODE1().equals(
				    ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.toString()))))
			    // comparing the code2 (details2)
			    && ((crtCO.getGlstmpltParamLinkVO().getCODE2() != null
				    && !crtCO.getGlstmpltParamLinkVO().getCODE2().equals(
					    ConstantsCommon.EMPTY_BIGDECIMAL_VALUE)
				    && savedCrtCO.getGlstmpltParamLinkVO().getCODE2() != null
				    && !savedCrtCO.getGlstmpltParamLinkVO().getCODE2().equals(
					    ConstantsCommon.EMPTY_BIGDECIMAL_VALUE) && ((crtCO.getGlstmpltParamLinkVO()
				    .getCODE2()).equals(savedCrtCO.getGlstmpltParamLinkVO().getCODE2()))) || ((savedCrtCO
				    .getGlstmpltParamLinkVO().getCODE2() == null || savedCrtCO.getGlstmpltParamLinkVO()
				    .getCODE2().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE)) && (crtCO
				    .getGlstmpltParamLinkVO().getCODE2() == null || crtCO.getGlstmpltParamLinkVO()
				    .getCODE2().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))))
			    // comparing the values
			    && ((crtCO.getVALUE1() != null && savedCrtCO.getVALUE1() != null && (crtCO.getVALUE1())
				    .equals(savedCrtCO.getVALUE1())) || (savedCrtCO.getVALUE1() == null && crtCO
				    .getVALUE1() == null)) && ((crtCO.getVALUE2() != null
			    && savedCrtCO.getVALUE2() != null && (crtCO.getVALUE2()).equals(savedCrtCO.getVALUE2())) || (savedCrtCO
			    .getVALUE2() == null && crtCO.getVALUE2() == null))))
	    {
		return true;
	    }
	}
	return false;
    }

    /**
     * method that applies dependency on a criteria
     * 
     * @return
     * @throws JSONException
     */
    public String applyFilterCrtDetDependency() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String detCode = getStr();
	    String tblName = getStr1();
	    String crtTypeCode = getUpdates();
	    
	    if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtTypeCode))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(getUpdates1());
		commonDetVO.setCODE_STR(detCode);
	    }
	    else if(tblName == null || tblName.equals("") || crtTypeCode == null || crtTypeCode.equals("")
		    || detCode == null || detCode.equals(""))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG(null);
		commonDetVO.setCODE_STR(null);
	    }
	    else
	    {
		List<CommonDetailsVO> divList;
		TmplGridSC detSC = new TmplGridSC();
		detSC.setGrid(false);
		if(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL.equals(tblName.toLowerCase(Locale.ENGLISH)))
		{
		    detSC.setTABLE_NAME(tblName.toUpperCase(Locale.ENGLISH));
		    detSC.setCRITERIA_TYPE_CODE(crtTypeCode);
		    detSC.setCODE_STR(detCode);
		    detSC.setLANG_CODE(sessionCO.getLanguage());
		    divList = templateBO.getFilterCrtDetList(detSC);
		}
		else
		{
		    if("V_IRP_SUB_ECO_SECTORS".equals(tblName.toUpperCase(Locale.ENGLISH)))
		    {
			detSC.setSECTOR_CODE(new BigDecimal(getVALUE()));
		    }
		    else
		    {
			detSC.setSECTOR_CODE(null);
		    }
		    if(RepConstantsCommon.SEC_NO_VIEW2.equals(tblName) || "V_IRP_DES_SUB".equals(tblName))
		    {
			// code of the details 2 field
			detSC.setCODE1(new BigDecimal(getVALUE()));
		    }
		    else if(RepConstantsCommon.SEC_NO_VIEW1.equals(tblName) && !"".equals(updates1))
		    {
			detSC.setBRIEF_DESC_ENG(getUpdates1());
		    }
		    detSC.setTABLE_NAME(tblName);
		    detSC.setCODE(new BigDecimal(detCode));
		    if(!StringUtil.isNotEmpty(str3) && crtTypeCode.equals(RepConstantsCommon.CRITERIA_CAF)
			    && RepConstantsCommon.CRT_CAF_VIEW_SMT.equals(tblName))
		    {
			detSC.setCB_NO(RepConstantsCommon.ZERO);
			detSC.setTABLE_NAME(RepConstantsCommon.CRT_CAF_VIEW);
			detSC.setCODE(new BigDecimal(getVALUE()));
			detSC.setBAS_EXPRESSION_CLASS_CODE(new BigDecimal(detCode));
		    }
		    else if(StringUtil.isNotEmpty(str3) && crtTypeCode.equals(RepConstantsCommon.CRITERIA_CAF))
		    {
			detSC.setTABLE_NAME(RepConstantsCommon.CRT_CAF_VIEW_SMT);
			detSC.setCODE(null);
			detSC.setCODE_CAF(new BigDecimal(str1));
			detSC.setFIELD_SEQ(new BigDecimal(VALUE));
			detSC.setBRIEF_DESC_ENG(updates1);
		    }
		    if(RepConstantsCommon.GL_TERM_VIEW.equals(tblName)
			    || RepConstantsCommon.REGION_BR_VIEW.equals(tblName)
			    || RepConstantsCommon.MAIN_ECO_SEC_VIEW1.equals(tblName)
			    || RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(tblName)
			    || RepConstantsCommon.SMT_VIEW.equals(tblName))
		    {
			detSC.setCOMP_CODE(null);
		    }
		    else
		    {
			detSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    }
		    if(RepConstantsCommon.GL_TERM_VIEW.equals(tblName))
		    {
			detSC.setCODE_STR(detCode);
		    }
		    if(RepConstantsCommon.SMT_VIEW.equals(tblName))
		    {
			detSC.setOPTION_CODE(getCode());
		    }
		    //added by fares
		    if(RepConstantsCommon.CRITERIA_CAF.equals(updates) && StringUtil.isNotEmpty(str1) && StringUtil.isNotEmpty(str3))
			{
		    	detSC.setCODE(new BigDecimal(detCode));
				HashMap<String, Object> repSessionCOMap=PathPropertyUtil.convertToMap(sessionCO);
				divList = templateBO.retCrtAddFieldsDet3Dep(repSessionCOMap,detSC);
			}
			else
			{
				divList = templateBO.getFilterCrtDetLList(detSC);
			}		
		    
		}
		commonDetVO = new CommonDetailsVO();
		if(divList == null || divList.isEmpty())
		{
		    commonDetVO.setBRIEF_DESC_ENG("");
		    commonDetVO.setCODE_STR("-5");
		}
		else
		{
		    CommonDetailsVO retVal = divList.get(0);
		    commonDetVO.setCODE_STR(detCode);
		    if(!StringUtil.isNotEmpty(str3) && RepConstantsCommon.CRITERIA_CAF.equals(crtTypeCode)
			    && RepConstantsCommon.CRT_CAF_VIEW_SMT.equals(tblName))
		    {
			commonDetVO.setBRIEF_DESC_ENG(retVal.getBAS_EXPRESSION_CLASS_DESC());
		    }
		    else
		    {
			commonDetVO.setBRIEF_DESC_ENG(retVal.getBRIEF_DESC_ENG());
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    commonDetVO = new CommonDetailsVO();
	    commonDetVO.setBRIEF_DESC_ENG("");
	    commonDetVO.setCODE_STR("-5");
	    return SUCCESS;
	}

	return SUCCESS;
    }

    public String emptyTmplLinesFrm() throws Exception
    {
	glstmpltCO = new GLSTMPLTCO();
	return SUCCESS;
    }

    /**
     * method that removes all template's lines
     * 
     * @return
     * @throws Exception
     */
    public String deleteAllTemplateLines() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	allTempl.setAddLinesMap(new LinkedHashMap<BigDecimal, GLSTMPLTCO>());
	allTempl.getDelLinesMap().putAll(allTempl.getModifLinesMap());
	allTempl.getDelLinesMap().putAll(allTempl.getDbLinesMap());
	allTempl.setDbLinesMap(new LinkedHashMap<BigDecimal, GLSTMPLTCO>());
	allTempl.setModifLinesMap(new LinkedHashMap<BigDecimal, GLSTMPLTCO>());
	return SUCCESS;
    }

    public String createLikeUrl() throws Exception
    {
	return SUCCESS;
    }

    public String reorganizeLines() throws Exception
    {
	return "orgLines";
    }

    public String selectAccount() throws Exception
    {
	return "selAcc";
    }

    /**
     * method that saves a new template created same as another one
     * 
     * @return
     * @throws Exception
     */
    public String saveCreateLike() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTSC sc = new GLSTMPLTSC();
	    sc.setCODE(newCode);
	    templateBO.checkRequiredFields(newCode, get_pageRef(), sessionCO.getCompanyCode(), sessionCO
		    .getCurrentAppName(), sessionCO.getLanguage());
	    sc.setCOMP_CODE(allTempl.getGlstmpltVO().getCOMP_CODE());
	    sc.setOldCode(allTempl.getGlstmpltVO().getCODE());
	    sc.setUserName(sessionCO.getUserName());
	    templateBO.saveCreateLike(sc);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return "error";
	}
	return SUCCESS;
    }

    /**
     * method called on the save of the select accounts lookup
     * 
     * @return
     * @throws JSONException
     */
    public String saveNeededAccounts() throws JSONException
    {
	try
	{
	    if(updateSelecAcc != null && !updateSelecAcc.equals(""))
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
		SessionCO sessionCO = returnSessionObject();
		BigDecimal compCode = sessionCO.getCompanyCode();
		GridUpdates gu = getGridUpdates(updateSelecAcc, FTR_ACCOUNTSCO.class,true);
		ArrayList lstAll= gu.getLstAllRec();
		if(!lstAll.isEmpty())
		{
		    GLSTMPLTCO gls = repSessionCO.getAllTempl();
		    GLSTMPLTCO currLine = gls.getDbLinesMap().get(
			    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
		    // handling lines being newly added
		    if(null == currLine)
		    {
			currLine = gls.getAddLinesMap().get(
				new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
		    }
		    // end handling lines being newly added
		    GLSTMPLTGLSDETCO glCO = currLine.getDbGLMap().get(
			    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber
				    + lineNbrDet.toString()));
		    if(null == glCO)
		    {
			glCO = currLine.getAddGLMap().get(
				new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber
					+ lineNbrDet.toString()));
			if(null != glCO)
			{
			    LinkedHashMap<BigDecimal, FTR_ACCOUNTSCO> addSelectAccListMap = glCO
				    .getAddSelectAccListMap();

			    addSelectAccListMap.clear();
			    Iterator it = lstAll.iterator();
			    while(it.hasNext())
			    {
				FTR_ACCOUNTSCO lCO = (FTR_ACCOUNTSCO) it.next();
				addSelectAccListMap.put(lCO.getConcatKey(), lCO);
			    }
			}
		    }
		    else
		    {
		    	LinkedHashMap<BigDecimal, FTR_ACCOUNTSCO> addSelectAccListMap = glCO.getAddSelectAccListMap();

				Iterator<FTR_ACCOUNTSCO> it = lstAll.iterator();
				while(it.hasNext())
				{
				    FTR_ACCOUNTSCO lCO = it.next();
				    addSelectAccListMap.put(lCO.getConcatKey(), lCO);
				}
		    }

		}

	    }
	}
	catch(RuntimeException e)
	{
	    throw e;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;

    }

    /**
     * method called on the load of the selected accounts grid
     * 
     * @return
     * @throws JSONException
     */
    public String selAccGridUrl() // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO sessionRep = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO gls = sessionRep.getAllTempl();
	    FTR_ACCOUNTSSC sc = new FTR_ACCOUNTSSC();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    sc.setCompCode(cmpCode);
	    sc.setBranch(brCode);
	    sc.setTmpCode(getTemplateCode());
	    sc.setTmpltLineNbr(new BigDecimal(getLineNumber()));
	    sc.setSubLineNbr(getLineNbrDet());
	    sc.setDiv(getDiv());
	    sc.setDpt(getDpt());
	    sc.setFromGL(getFromGL());
	    sc.setToGL(getToGL());
	    sc.setRecToskip(0);
	    GLSTMPLTCO currLine = gls.getDbLinesMap().get(
		    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
	    // handling a line that is not yet in the db
	    if(null == currLine)
	    {
		currLine = gls.getAddLinesMap().get(
			new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
	    }
	    // end handling not yet in db
	    GLSTMPLTGLSDETCO glCO = currLine.getDbGLMap().get(
		    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber + lineNbrDet.toString()));
	    if(glCO == null)
	    {
		glCO = currLine.getAddGLMap().get(
			new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber
				+ lineNbrDet.toString()));
	    }
	 // always fill the dbmap because the add only contains the checked
	    // ones
	    LinkedHashMap<BigDecimal, FTR_ACCOUNTSCO> addSelectAccListMap = (glCO == null ? null : glCO
		    .getAddSelectAccListMap());

	    if(addSelectAccListMap == null || addSelectAccListMap.isEmpty())
	    {
		addSelectAccListMap = new LinkedHashMap<BigDecimal, FTR_ACCOUNTSCO>();
		sc.setNbRec(templateBO.getAccountsListCount(sc));
		List<FTR_ACCOUNTSCO> selectAccList = templateBO.getAccountsList(sc);
		for(int i = 0; i < selectAccList.size(); i++)
		{
		    FTR_ACCOUNTSCO ftrAccountsCO = selectAccList.get(i);
		    addSelectAccListMap.put(ftrAccountsCO.getConcatKey(), ftrAccountsCO);
		}
		if(glCO != null)
		{
		    glCO.setAddSelectAccListMap(addSelectAccListMap);
		}
	    }

	    ArrayList<FTR_ACCOUNTSCO> lst = new ArrayList<FTR_ACCOUNTSCO>();
	    for(int i = 0; i < addSelectAccListMap.size(); i++)
	    {
		lst.add((FTR_ACCOUNTSCO) addSelectAccListMap.values().toArray()[i]);
	    }

	    setGridModel(lst);

	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called when checking the select all checkbox in the select account
     * screen
     * 
     * @return
     * @throws Exception
     */
    public String selectAllAcc() throws Exception
    {
	try
	{
	    String flag = "db";// to check if its a db or add line
	    ReportingSessionCO sessionRep = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    GLSTMPLTCO gls = sessionRep.getAllTempl();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    GLSTMPLTCO currLine = gls.getDbLinesMap().get(
		    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
	    if(null == currLine)
	    {
		currLine = gls.getAddLinesMap().get(
			new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
		flag = "add";
	    }

	    GLSTMPLTGLSDETCO glCO = null;
	    if("db".equals(flag))
	    {
		glCO = currLine.getDbGLMap().get(
			new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber
				+ lineNbrDet.toString()));
		if(glCO == null)
		{
		    glCO = currLine.getAddGLMap().get(
			    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber
				    + lineNbrDet.toString()));
		}

		Iterator<FTR_ACCOUNTSCO> it = glCO.getAddSelectAccListMap().values().iterator();
		while(it.hasNext())
		{
		    FTR_ACCOUNTSCO ftrAccountsCO = it.next();
		    ftrAccountsCO.setIncl("true");
		}
	    }
	    else if("add".equals(flag))
	    {
		glCO = currLine.getAddGLMap().get(
			new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber
				+ lineNbrDet.toString()));
		Iterator<FTR_ACCOUNTSCO> it = glCO.getAddSelectAccListMap().values().iterator();
		while(it.hasNext())
		{
		    FTR_ACCOUNTSCO ftrAccountsCO = it.next();
		    ftrAccountsCO.setIncl("true");
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called when checking the deselect all checkbox in the select
     * account screen
     * 
     * @return
     * @throws Exception
     */
    public String deSelectAllAcc() throws Exception
    {
	try
	{
	    ReportingSessionCO sessionRep = returnReportingSessionObject(this.get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    GLSTMPLTCO gls = sessionRep.getAllTempl();
	    GLSTMPLTCO currLine = gls.getDbLinesMap().get(
		    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber));
	    GLSTMPLTGLSDETCO glCO = currLine.getDbGLMap().get(
		    new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber + lineNbrDet.toString()));
	    if(glCO == null)
	    {
		glCO = currLine.getAddGLMap().get(
			new BigDecimal(compCode.toString() + templateCode.toString() + lineNumber.toString()
				+ lineNbrDet.toString()));
	    }
	    Iterator<FTR_ACCOUNTSCO> it = glCO.getAddSelectAccListMap().values().iterator();
	    while(it.hasNext())
	    {
		FTR_ACCOUNTSCO ftrAccountsCO = it.next();
		ftrAccountsCO.setIncl("false");
		glCO.getAddSelectAccListMap().put(ftrAccountsCO.getConcatKey(), ftrAccountsCO);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String relatedReportsDialogUrl() throws Exception
    {

	return "rltRep";
    }

    /**
     * method called when saving related reports data
     * 
     * @return
     * @throws Exception
     */
    public String saveRelatedReports() throws Exception
    {
	try
	{
	    if(updateRelReports != null && !updateRelReports.equals(""))
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
		SessionCO sessionCO = returnSessionObject();
		String appName = sessionCO.getCurrentAppName();
		GridUpdates gu = getGridUpdates(updateRelReports, FTR_TMPL_REFCO.class);
		ArrayList lstAdd = gu.getLstAdd();
		ArrayList lstMod = gu.getLstModify();
		ArrayList lstDel = gu.getLstDelete();
		if(!lstAdd.isEmpty())
		{
		    Iterator it = lstAdd.iterator();
		    while(it.hasNext())
		    {

			FTR_TMPL_REFCO lFtrTmplCO = (FTR_TMPL_REFCO) it.next();
			lFtrTmplCO.getFtrTmplRefVO().setTEMPLATE_CODE(templateCode);
			lFtrTmplCO.getFtrTmplRefVO().setAPP_NAME(appName);
			lFtrTmplCO.getFtrTmplRefVO().setCOMP_CODE(sessionCO.getCompanyCode());
			lFtrTmplCO.setConcatKey(lFtrTmplCO.getFtrTmplRefVO().getCOMP_CODE().toString()
				+ lFtrTmplCO.getFtrTmplRefVO().getTEMPLATE_CODE().toString()
				+ lFtrTmplCO.getFtrTmplRefVO().getREPORT_REF()
				+ lFtrTmplCO.getFtrTmplRefVO().getAPP_NAME());
			repSessionCO.getAllTempl().getAddedReportsList().put(lFtrTmplCO.getConcatKey(), lFtrTmplCO);
		    }
		}
		if(!lstMod.isEmpty())
		{
		    Iterator it = lstMod.iterator();
		    while(it.hasNext())
		    {
			FTR_TMPL_REFCO lCO = (FTR_TMPL_REFCO) it.next();
			FTR_TMPL_REFCO oldCO = repSessionCO.getAllTempl().getRelatedReportsList().get(
				lCO.getConcatKey());

			if(!oldCO.getFtrTmplRefVO().getREPORT_REF().equals(lCO.getFtrTmplRefVO().getREPORT_REF()))
			{
			    lCO.setOldReportRef(oldCO.getFtrTmplRefVO().getREPORT_REF());
			}

			lCO.getFtrTmplRefVO().setCOMP_CODE(sessionCO.getCompanyCode());
			repSessionCO.getAllTempl().getUpdatedReportsList().put(lCO.getConcatKey(), lCO);
		    }
		}
		if(!lstDel.isEmpty())
		{
		    Iterator it = lstMod.iterator();
		    while(it.hasNext())
		    {
			FTR_TMPL_REFCO lCO = (FTR_TMPL_REFCO) it.next();
			repSessionCO.getAllTempl().getDeletedReportsList().put(lCO.getConcatKey(), lCO);

		    }
		}
	    }
	}
	catch(RuntimeException e)
	{
	    throw e;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called when reorganizing lines order
     * 
     * @return
     * @throws Exception
     */
    public String reorganizeLinesOrder() throws Exception
    {
	try
	{
	    newLineIndicator = 0;
	    firstLoop = 0;
	    if(NumberUtil.isEmptyDecimal(getStartingLineValue()) || NumberUtil.isEmptyDecimal(getInterval()))
	    {
		updates=RepConstantsCommon.ONE;
		return SUCCESS;
	    }
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> finalDbLinesMap;
	    //map used for the mappping (lineNbr)/newLineNumber before reorganize
	    //in order to replace in the line values for expressions
	    HashMap<BigDecimal,BigDecimal> oldNewExprLinesMap = new HashMap<BigDecimal, BigDecimal>();
	    //map used for the mappping old new line number/new newLineNumber in order to replace in the line values for expressions
	    HashMap<BigDecimal,BigDecimal> oldNewNewExprLinesMap = new HashMap<BigDecimal, BigDecimal>();
	    /*
	     * used to store newly added organized lines to avoid conflict with
	     * addlines map put new line numbers for added lines and db lines
	     */
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> tempHash = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();
	    previousValue = getStartingLineValue().intValue();// holds the
	    // newest
	    // newLineNumber
	    dbLinesMap.putAll(addLinesMap);
	    dbLinesMap = sortHashMapByValuesD(dbLinesMap);
	    GLSTMPLTCO glstmpCO;
	    for(Entry<BigDecimal, GLSTMPLTCO> ent : dbLinesMap.entrySet())
	    {
		glstmpCO = ent.getValue();
		oldNewExprLinesMap.put(glstmpCO.getGlstmpltVO().getLINE_NBR(), glstmpCO.getNewLineNumber());
	    }
	    finalDbLinesMap = putNewLinesNumberMap(dbLinesMap, allTempl, tempHash);
	    for(Entry<BigDecimal, GLSTMPLTCO> ent : finalDbLinesMap.entrySet())
	    {
		glstmpCO = ent.getValue();
		oldNewNewExprLinesMap.put(oldNewExprLinesMap.get(glstmpCO.getGlstmpltVO().getLINE_NBR()), glstmpCO
			.getNewLineNumber());
	    }
	    allTempl.getNewLinesFromReorganize().put(glstmpltCO.getNewLineNumber(), glstmpltCO.getNewLineNumber());
	    // printHashContents(finalDbLinesMap, "finalDbLinesMap");
	    // now adding the tempHash lines to the addLines
	    Iterator<GLSTMPLTCO> itTempHash = tempHash.values().iterator();

	    while(itTempHash.hasNext())
	    {
		GLSTMPLTCO gls = itTempHash.next();
		addLinesMap.put(gls.getConcatKey(), gls);
	    }

	    allTempl.setDbLinesMap(finalDbLinesMap);
	    /*
	     * manipulating the dblinesmap to display the new lines in the grid
	     * converting the keys to string because string in the boimpl new
	     */
	    Iterator<Map.Entry<BigDecimal, GLSTMPLTCO>> itFmap = finalDbLinesMap.entrySet().iterator();
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, GLSTMPLTCO> entry = itFmap.next();
		allTempl.getModifLinesMap().put(entry.getKey(), entry.getValue());
	    }
	    // new
	    setNullLlineNumberToNewLineNumber(allTempl.getModifLinesMap());
	    // now for each line go and set the lines in its related GLs
	    Set<BigDecimal> set = allTempl.getDbLinesMap().keySet();
	    Iterator<BigDecimal> it = set.iterator();
	    SessionCO sessionCO = returnSessionObject();
	    while(it.hasNext())
	    {
		BigDecimal theKey = it.next();
		GLSTMPLTCO line = (allTempl.getDbLinesMap().get(theKey));
		// if the dbglmap is not loaded yet should handle this case
		if(line.getDbGLMap().size() == 0
			&& line.getGlstmpltVO().getLINE_NBR() != null
			&& ((newLineIndicator != 0 && line.getNewLineNumber().intValue() != newLineIndicator) || valueToRemember
				.intValue() == 0))
		{
		    GLSTMPLTGLSDETSC glCO = new GLSTMPLTGLSDETSC();
		    glCO.setCOMP_CODE(sessionCO.getCompanyCode());
		    glCO.setCODE(allTempl.getGlstmpltVO().getCODE());
		    glCO.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
		    glCO.setLANG_CODE(sessionCO.getLanguage());
		    // get the db gls as list then put it in the linkedHashMap
		    List<GLSTMPLTGLSDETCO> glsList = templateBO.getGLsByLine(glCO);
		    for(int i = 0; i < glsList.size(); i++)
		    {
			GLSTMPLTGLSDETCO glCOO = glsList.get(i);
			line.getDbGLMap().put(glCOO.getConcatKey(), glCOO);
		    }
		}
		// end handling not loaded gls
		LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> glMap = line.getDbGLMap();
		// modif
		Iterator<Map.Entry<BigDecimal, GLSTMPLTGLSDETCO>> itFglmap = glMap.entrySet().iterator();
		while(itFglmap.hasNext())
		{
		    Entry<BigDecimal, GLSTMPLTGLSDETCO> entry = itFglmap.next();
		    GLSTMPLTGLSDETCO gg = entry.getValue();
		    gg.setNewLineNumber(line.getNewLineNumber());
		    /*
		     * modifying the dbglmap for the display
		     */
		}
		// handling the gls in addGlMap
		LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> addGlMap = line.getAddGLMap();
		// modif
		Iterator<Map.Entry<BigDecimal, GLSTMPLTGLSDETCO>> itaddGlmap = addGlMap.entrySet().iterator();
		while(itaddGlmap.hasNext())
		{
		    Entry<BigDecimal, GLSTMPLTGLSDETCO> entry = itaddGlmap.next();
		    GLSTMPLTGLSDETCO gg = entry.getValue();
		    gg.setNewLineNumber(line.getNewLineNumber());
		}
	    }

	    // handling mismatch
	    it = set.iterator();
	    while(it.hasNext())
	    {
		BigDecimal theKey = it.next();
		GLSTMPLTCO line = (allTempl.getDbLinesMap().get(theKey));
		// if the dbmismap is not loaded yet should handle this case
		if(line.getDbMismatchMap().size() == 0 && line.getGlstmpltVO().getLINE_NBR() != null)
		{
		    TmplGridSC misSC = new TmplGridSC();
		    misSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    misSC.setCODE(templateCode);
		    misSC.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
		    misSC.setLANG_CODE(sessionCO.getLanguage());
		    // get the db mismatches as list then put it in the
		    // linkedHashMap
		    List<FTR_MISMATCH_REPORTCO> misList = templateBO.getMismatchsList(misSC);
		    for(int i = 0; i < misList.size(); i++)
		    {
			FTR_MISMATCH_REPORTCO misCO = misList.get(i);
			line.getDbMismatchMap().put(misCO.getConcatKey(), misCO);
		    }
		}
		// end handling not loaded mismatches
		LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> misMap = line.getDbMismatchMap();
		Iterator<Map.Entry<BigDecimal, FTR_MISMATCH_REPORTCO>> itMis = misMap.entrySet().iterator();
		while(itMis.hasNext())
		{
		    Entry<BigDecimal, FTR_MISMATCH_REPORTCO> entry = itMis.next();
		    FTR_MISMATCH_REPORTCO misCO = entry.getValue();
		    misCO.setNewLineNumber(line.getNewLineNumber());
		    /*
		     * modifying the dbglmap for the display
		     */
		}

		// handling the mismatches in addMisMap
		LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> addMisMap = line.getAddMismatchMap();
		Iterator<Map.Entry<BigDecimal, FTR_MISMATCH_REPORTCO>> itMissMap = addMisMap.entrySet().iterator();
		while(itMissMap.hasNext())
		{
		    FTR_MISMATCH_REPORTCO misCO = itMissMap.next().getValue();
		    misCO.setNewLineNumber(line.getNewLineNumber());
		    /*
		     * modifying the dbglmap for the display
		     */
		}
	    }
	    // end handling mismatch

	    // handling Criterias
	    it = set.iterator();
	    while(it.hasNext())
	    {
		BigDecimal theKey = it.next();
		GLSTMPLTCO line = (allTempl.getDbLinesMap().get(theKey));
		// if the dbcrtMap is not loaded yet should handle this case
		if((line.getDbCrtMap().size() == 0 && line.getGlstmpltVO().getLINE_NBR() != null && ((newLineIndicator != 0 && line
			.getNewLineNumber().intValue() != newLineIndicator) || valueToRemember.intValue() == 0))
			&& allTempl.getNewLinesFromReorganize().get(line.getNewLineNumber()) == null)
		{
		    TmplGridSC crtSC = new TmplGridSC();
		    crtSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    crtSC.setCODE(templateCode);
		    crtSC.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
		    crtSC.setLANG_CODE(sessionCO.getLanguage());
		    // get the db criterias as list then put it in the
		    // linkedHashMap
		    List<GLSTMPLT_PARAM_LINKCO> crtList = templateBO.getCrtList(crtSC);
		    for(int i = 0; i < crtList.size(); i++)
		    {
			GLSTMPLT_PARAM_LINKCO crtCO = crtList.get(i);
			line.getDbCrtMap().put(crtCO.getConcatKey(), crtCO);
		    }
		}
		// end handling not loaded criterias
		LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> crtMap = line.getDbCrtMap();
		// modif
		Iterator<Map.Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO>> itCrtMapEn = crtMap.entrySet().iterator();
		while(itCrtMapEn.hasNext())
		{
		    Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO> entry = itCrtMapEn.next();
		    GLSTMPLT_PARAM_LINKCO crtCO = entry.getValue();
		    crtCO.setNewLineNumber(line.getNewLineNumber());
		}
		// handling the criterias in addCritMap
		LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> addCrtMap = line.getAddCrtMap();
		// modif
		Iterator<Map.Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO>> itAddCrtMapEn = addCrtMap.entrySet().iterator();
		while(itAddCrtMapEn.hasNext())
		{
		    Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO> entry = itAddCrtMapEn.next();
		    GLSTMPLT_PARAM_LINKCO crtCO = entry.getValue();
		    crtCO.setNewLineNumber(line.getNewLineNumber());
		}
	    }
	    // end handling Criterias

	    // handling Expressions
	    it = set.iterator();
	    while(it.hasNext())
	    {
		BigDecimal theKey = it.next();
		GLSTMPLTCO line = (allTempl.getDbLinesMap().get(theKey));
		// if the dbcrtMap is not loaded yet should handle this case
		if((line.getDbExprMap().size() == 0 && line.getGlstmpltVO().getLINE_NBR() != null && ((newLineIndicator != 0 && line
			.getNewLineNumber().intValue() != newLineIndicator) || valueToRemember.intValue() == 0))
			&& allTempl.getNewLinesFromReorganize().get(line.getNewLineNumber()) == null)
		{
		    GLSTMPLTSC exprSC = new GLSTMPLTSC();
		    exprSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    exprSC.setCODE(templateCode);
		    exprSC.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
		    exprSC.setLANG_CODE(sessionCO.getLanguage());
		    // get the db criterias as list then put it in the
		    // linkedHashMap
		    List<FTR_TMPLT_EXPRCO> exprList = templateBO.getExprList(exprSC);
		    for(int i = 0; i < exprList.size(); i++)
		    {
			FTR_TMPLT_EXPRCO exprCO = exprList.get(i);
			if(exprCO.getFtrTmpltExprVO().getEXP_TYPE().equals(RepConstantsCommon.TMPLT_EXPR_LINE))
			{
				exprCO.getFtrTmpltExprVO().setEXP_VALUE(exprCO.getFtrTmpltExprVO().getEXP_LINE());
				exprCO.getFtrTmpltExprVO().setEXP_LINE(null);
			}
			line.getDbExprMap().put(exprCO.getConcatKey(), exprCO);
		    }
		}
		LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> exprMap = line.getDbExprMap();
		// modif
		Iterator<Map.Entry<BigDecimal, FTR_TMPLT_EXPRCO>> itExprMapEn = exprMap.entrySet().iterator();
		while(itExprMapEn.hasNext())
		{
		    Entry<BigDecimal, FTR_TMPLT_EXPRCO> entry = itExprMapEn.next();
		    FTR_TMPLT_EXPRCO exprCO = entry.getValue();
		    exprCO.setNewLineNumber(line.getNewLineNumber());
		    if(RepConstantsCommon.COL_EXP_LINE_TYPE.equals(exprCO.getFtrTmpltExprVO().getEXP_TYPE()))
		    {
			exprCO.getFtrTmpltExprVO().setEXP_VALUE(
				oldNewNewExprLinesMap.get(exprCO.getFtrTmpltExprVO().getEXP_VALUE()));
		    }
		}

		// handling the expressions in addCritMap
		LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> addExprMap = line.getAddExprMap();
		// modif
		Iterator<Map.Entry<BigDecimal, FTR_TMPLT_EXPRCO>> itAddExprMapEn = addExprMap.entrySet().iterator();
		while(itAddExprMapEn.hasNext())
		{
		    Entry<BigDecimal, FTR_TMPLT_EXPRCO> entry = itAddExprMapEn.next();
		    FTR_TMPLT_EXPRCO exprCO = entry.getValue();
		    exprCO.setNewLineNumber(line.getNewLineNumber());
		    if(RepConstantsCommon.COL_EXP_LINE_TYPE.equals(exprCO.getFtrTmpltExprVO().getEXP_TYPE()))
		    {
			exprCO.getFtrTmpltExprVO().setEXP_VALUE(
				oldNewNewExprLinesMap.get(exprCO.getFtrTmpltExprVO().getEXP_VALUE()));
		    }
		}
	    }
	    allTempl.setAddLinesMap(new LinkedHashMap<BigDecimal, GLSTMPLTCO>());
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);

	    return null;
	}
    }

    // public static void printHashContents(LinkedHashMap<BigDecimal,
    // GLSTMPLTCO> map, String hashName)
    // {
    // Iterator<Map.Entry<BigDecimal, GLSTMPLTCO>> itFmap =
    // map.entrySet().iterator();
    // while(itFmap.hasNext())
    // {
    // Entry<BigDecimal, GLSTMPLTCO> entry = itFmap.next();
    // BigDecimal theKey = entry.getKey();
    // GLSTMPLTCO gg = entry.getValue();
    // System.out.println("key hash:" + theKey + "     key: " +
    // gg.getConcatKey() + "       new Line: "
    // + gg.getNewLineNumber() + "             true line: " +
    // gg.getGlstmpltVO().getLINE_NBR()
    // + "             description: " + gg.getGlstmpltVO().getBRIEF_NAME_ENG() +
    // "\n");
    // }
    // }

    /**
     * 
     * @param param
     * @return
     * @throws Exception function that inserts the new line numbers for the
     *             template lines
     */
    public LinkedHashMap<BigDecimal, GLSTMPLTCO> putNewLinesNumberMap(LinkedHashMap<BigDecimal, GLSTMPLTCO> param,
	    GLSTMPLTCO allTempl, LinkedHashMap<BigDecimal, GLSTMPLTCO> tempHash) throws Exception
    {
	int dbLinesMapSize = param.size();
	BigDecimal flagValueRememberPassed = valueToRemember;
	LinkedHashMap<BigDecimal, GLSTMPLTCO> finalHashMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();
	BigDecimal templateCode;// added for the case where value to remember
	BigDecimal compCode = returnSessionObject().getCompanyCode();
	// this is the line added newly on reorganizing
	GLSTMPLTCO newLine = new GLSTMPLTCO();
	//contains the old key of a new line
	BigDecimal oldNewLineKey=null;
	//flag to know if the new line number must be added to the hashmap of newLinesNumbers in colmntmpltco
	boolean toBeAdded = false;
	HashMap<BigDecimal,BigDecimal> tempNewLinesReorg = new HashMap<BigDecimal, BigDecimal>();

	// have to get the smallest line number to set it's value
	for(int i = 0; i < dbLinesMapSize; i++)
	{
	    GLSTMPLTCO line = getObjectHavingSmallestLineNbr(param);
	    if(allTempl.getNewLinesFromReorganize().containsKey(line.getNewLineNumber()))
	    {
		//the new line number has not been changed yet.
		oldNewLineKey=line.getNewLineNumber();
		toBeAdded = true;
	    }
	    templateCode = line.getGlstmpltVO().getCODE();

	    param.remove(line.getConcatKey());
	    if(firstLoop == 0)
	    {
		// case where value to remember first line in the grid
		if(flagValueRememberPassed.intValue() != 0
			&& line.getGlstmpltVO().getLINE_NBR().equals(flagValueRememberPassed)
			&& (line.getGlstmpltVO().getLINE_NBR().equals(line.getNewLineNumber())))
		// if valueToRemember <> 0
		{
		    newLine.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue()));
		    newLineIndicator = newLine.getNewLineNumber().intValue();
		    String concatKey = compCode.toString() + templateCode.toString()
			    + String.valueOf((previousValue + getInterval().intValue()));
		    newLine.setConcatKey(new BigDecimal(concatKey));
		    newLine.getGlstmpltVO().setCODE(line.getGlstmpltVO().getCODE());
		    if(tempHash != null)
		    {
			tempHash.put(new BigDecimal(concatKey), newLine);
		    }
		    line.setNewLineNumber(getStartingLineValue()/*
								 * previousValue+
								 * getInterval
								 * ().intValue()
								 */);
		    glstmpltCO.setNewLineNumber(newLine.getNewLineNumber());
		    previousValue += getInterval().intValue();
		    flagValueRememberPassed = BigDecimal.ZERO;
		    concatKey = compCode.toString() + templateCode.toString() + getStartingLineValue().toString();
		    line.setConcatKey(new BigDecimal(concatKey));
		    finalHashMap.put(line.getConcatKey(), line);
		}
		else
		// no value to remember for the first line in the grid
		{
		    line.setNewLineNumber(getStartingLineValue());
		    String concatKey = compCode.toString() + templateCode.toString()
			    + getStartingLineValue().toString();
		    line.setConcatKey(new BigDecimal(concatKey));
		    finalHashMap.put(line.getConcatKey(), line);
		}
		if(toBeAdded)
		{
		    tempNewLinesReorg.remove(oldNewLineKey);
		    tempNewLinesReorg.put(line.getNewLineNumber(), line.getNewLineNumber());
		    toBeAdded = false;
		}
		firstLoop = 1;
	    }
	    else
	    {
		if(flagValueRememberPassed.intValue() != 0
			&& ((line.getGlstmpltVO().getLINE_NBR().equals(flagValueRememberPassed) && line
				.getNewLineNumber() == null) || (line.getNewLineNumber()
				.equals(flagValueRememberPassed))))// if
		// valueToRemember
		// <> 0
		{

		    newLine.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue() * 2));
		    newLineIndicator = newLine.getNewLineNumber().intValue();
		    String concatKey = compCode.toString() + templateCode.toString()
			    + String.valueOf((previousValue + getInterval().intValue() * 2));
		    // have to check if same concatKey previously inserted for
		    // the case where the user is inserting again a new line
		    // number
		    if(allTempl.getAddLinesMap().get(new BigDecimal(concatKey)) != null)
		    {
			newLine.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue() * 2));
		    }
		    // end check
		    newLine.setConcatKey(new BigDecimal(concatKey));
		    newLine.getGlstmpltVO().setCODE(line.getGlstmpltVO().getCODE());
		    if(tempHash == null)
		    {
			finalHashMap.put(newLine.getConcatKey(), newLine);
		    }
		    else
		    {
			tempHash.put(new BigDecimal(concatKey), newLine);
		    }
		    line.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue()));
		    concatKey = compCode.toString() + templateCode.toString()
			    + String.valueOf((previousValue + getInterval().intValue()));
		    line.setConcatKey(new BigDecimal(concatKey));
		    finalHashMap.put(new BigDecimal(concatKey), line);
		    glstmpltCO.setNewLineNumber(newLine.getNewLineNumber());
		    previousValue += getInterval().intValue() * 2;
		    flagValueRememberPassed = BigDecimal.ZERO;
		}
		else
		// normal case without valueToRemember
		{

		    line.setNewLineNumber(new BigDecimal(previousValue + getInterval().intValue()));
		    previousValue += getInterval().intValue();
		    String newKey = compCode.toString() + line.getGlstmpltVO().getCODE().toString()
			    + line.getNewLineNumber().toString();
		    finalHashMap.put(new BigDecimal(newKey), line);
		    line.setConcatKey(new BigDecimal(newKey));
		}
		if(toBeAdded)
		{
		    tempNewLinesReorg.remove(oldNewLineKey);
		    tempNewLinesReorg.put(line.getNewLineNumber(), line.getNewLineNumber());
		    toBeAdded = false;
		}
	    }

	}
	setNullLlineNumberToNewLineNumber(finalHashMap);
	//setting the session's newLinesFromReorganize
	allTempl.getNewLinesFromReorganize().clear();
	allTempl.setNewLinesFromReorganize((HashMap<BigDecimal, BigDecimal>) tempNewLinesReorg.clone());
	return finalHashMap;
    }

    /**
     * method that sets null line numbers to new numbers
     * 
     * @param finalHashMap
     */
    public void setNullLlineNumberToNewLineNumber(HashMap<BigDecimal, GLSTMPLTCO> finalHashMap)
    {
	Iterator<GLSTMPLTCO> it = finalHashMap.values().iterator();
	while(it.hasNext())
	{
	    GLSTMPLTCO gls = it.next();
	    if(gls.getGlstmpltVO().getLINE_NBR() == null)
	    {
		BigDecimal lnbr = gls.getNewLineNumber();
		gls.getGlstmpltVO().setLINE_NBR(lnbr);
	    }
	}
    }

    /**
     * method that gets the object having smallest line nbr
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public GLSTMPLTCO getObjectHavingSmallestLineNbr(LinkedHashMap<BigDecimal, GLSTMPLTCO> param) throws Exception
    {
	GLSTMPLTCO result = new GLSTMPLTCO();
	int firstRound = 0;
	int smallestNbr = 0;

	Iterator<Map.Entry<BigDecimal, GLSTMPLTCO>> itFmap = param.entrySet().iterator();
	try
	{
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, GLSTMPLTCO> entry = itFmap.next();
		Object theKey = entry.getKey();
		GLSTMPLTCO line = entry.getValue();
		line.setConcatKey((BigDecimal) theKey);
		int currentLineNbr = line.getNewLineNumber().intValue();
		if(firstRound == 0)
		{
		    result = line;
		    smallestNbr = currentLineNbr;
		    firstRound = 1;
		}
		else
		{
		    if(currentLineNbr < smallestNbr)
		    {
			smallestNbr = currentLineNbr;
			result = line;
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return result;
    }

    /**
     * method called to remember a line number
     * 
     * @throws Exception
     */
    public void rememberLine() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    allTempl.setNumberAfter(Integer.parseInt(getLineNumber()));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }

    /**
     * method called to check for a line or value change
     * 
     * @return
     * @throws Exception
     */
    public String lineOrValueChange() throws Exception
    {
	try
	{
	    BigDecimal value = new BigDecimal(getVALUE());

	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    String tempCode_LineNb = getTempCode_LineNb();
	    String tempCode = tempCode_LineNb.split("~")[0];
	    String lineNb = tempCode_LineNb.split("~")[1];
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal lineKey = new BigDecimal(compCode.toString() + tempCode + value.toString());

	    if(allTempl.getAddLinesMap().get(lineKey) == null && allTempl.getDbLinesMap().get(lineKey) == null)
	    {
		setUpdates("false");
	    }
	    else
	    {
		if(getVALUE().equals(lineNb))
		{
		    setUpdates("false");
		}
		else
		{
		    setUpdates("true");
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called on the load of a template lookup
     * 
     * @return
     * @throws Exception
     */
    public String loadTemplatesLookupGrid() throws Exception
    {
	try
	{
	    setSearchFilter(tmplGridSC);
	    copyproperties(tmplGridSC);
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    tmplGridSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    tmplGridSC.setCODE(allTempl.getGlstmpltVO().getCODE());
	    List<GLSTMPLTCO> templatesList = templateBO.getAllTemplates(tmplGridSC);
	    int templatesListCount = templateBO.getAllTemplCount(tmplGridSC);
	    setRecords(templatesListCount);
	    setGridModel(templatesList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;

    }

    /**
     * method that checks the existance of template lines
     * 
     * @return
     * @throws JSONException
     */
    public String existingTemplateLinesLookup() throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "glstmpltVO.LINE_NBR", "glstmpltVO.BRIEF_NAME_ENG" };
	    String[] colType = { "number", "text" };
	    String[] titles = { getText("reporting.code"), getText("template.relatedReports.name") };
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("TEMPLATES");
	    grid.setRowNum("10");
	    grid.setUrl("/path/templateMaintReport/loadTemplatesLinesLookupGrid");
	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(true);

		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, tmplGridSC);
	}
	catch(Exception e)
	{
	    log.error(e, "------------error in tempaltes lookup-----------");
	}
	return "success";
    }

    public String loadTemplatesLinesLookupGrid() throws Exception
    {
	try
	{
	    setSearchFilter(tmplGridSC);
	    copyproperties(tmplGridSC);
	    SessionCO sessionCO = returnSessionObject();
	    tmplGridSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    tmplGridSC.setLANG_CODE(sessionCO.getLanguage());
	    if(!ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(templateCode))
	    {
		tmplGridSC.setCODE(templateCode);
		List<GLSTMPLTCO> templatesList = templateBO.getAllTemplateLines(tmplGridSC);
		int templatesListCount = templateBO.getAllTemplateLinesCount(tmplGridSC);
		setRecords(templatesListCount);
		setGridModel(templatesList);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "success";

    }
    
    /**
     * Method that checks if we have calculation type RTV at the level of GL and
     * prevent the user from inserting other than currency as criteria
     * 
     * @return
     */
    public String checkIfCalcTypeRateValue()
    {
	try
	{
	    updates = RepConstantsCommon.ZERO;
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String templCode = tempCodeWithLineNb.split("~")[0];
	    String lineNb = tempCodeWithLineNb.split("~")[1];
	    String lineConcatKey = compCode.toString() + templCode.toString() + lineNb.toString();
	    GLSTMPLTCO lineCO = getglstmpltCOById(lineConcatKey);
	    // currency criteria
	    if(lineCO.getDbGLMap().size() == 1)
	    {
		GLSTMPLTGLSDETCO glCO =(new ArrayList<GLSTMPLTGLSDETCO>(lineCO.getDbGLMap().values())).get(0);
		if(RepConstantsCommon.CALC_TYPE_RTV.equals(glCO.getGlstmpltGlsDetVO().getCALC_TYPE()))
		{
		    updates = RepConstantsCommon.ONE;
		}
	    }
	    else if(lineCO.getAddGLMap().size() == 1)
	    {
		GLSTMPLTGLSDETCO glCO = (new ArrayList<GLSTMPLTGLSDETCO>(lineCO.getAddGLMap().values())).get(0);
		if(RepConstantsCommon.CALC_TYPE_RTV.equals(glCO.getGlstmpltGlsDetVO().getCALC_TYPE()))
		{
		    updates = RepConstantsCommon.ONE;
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * Method called on gl ok button that checks if the criteria grid is empty
     * or contains only one record having criteria currency included when
     * calculation type RTV is picked in the GL section
     * 
     * @return
     */
    public String checkCriteriaCurrencyIncluded()
    {
	try
	{
	    updates = RepConstantsCommon.ZERO;
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String templCode = tempCodeWithLineNb.split("~")[0];
	    String lineNb = tempCodeWithLineNb.split("~")[1];
	    String lineConcatKey = compCode.toString() + templCode.toString() + lineNb.toString();
	    GLSTMPLTCO lineCO = getglstmpltCOById(lineConcatKey);
	    if(lineCO.getDbCrtMap().isEmpty() && lineCO.getDelCrtMap().isEmpty())
	    {
		LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> dbCrtMap = new LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();
		TmplGridSC crtSC = new TmplGridSC();
		crtSC.setCOMP_CODE(compCode);
		crtSC.setCODE(new BigDecimal(templCode));
		crtSC.setLINE_NBR(new BigDecimal(lineNb));
		crtSC.setLANG_CODE(sessionCO.getLanguage());
		List<GLSTMPLT_PARAM_LINKCO> dbCrtLst = templateBO.getCrtList(crtSC);
		String table1;
		String table2;
		TmplGridSC detSC;
		List<CommonDetailsVO> detailList;
		CommonDetailsVO retVal;
		for(int i = 0; i < dbCrtLst.size(); i++)
		{
		    GLSTMPLT_PARAM_LINKCO crtCO = dbCrtLst.get(i);
		    table1 = crtCO.getTABLE_NAME1();
		    if(RepConstantsCommon.CRITERIA_WCL.equals(crtCO.getCRI_TYPE()))
		    {
			crtCO.setVALUE1("");
			crtCO.setCODE1("");
		    }
		    if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRI_TYPE()))
		    {
			maleFemaleList = retListDesc(RepConstantsCommon.TEMPLATE_MALE_FEMALE, sessionCO.getLanguage(),
				crtCO, RepConstantsCommon.CRITERIA_GENDER);
		    }
		    if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtCO.getCRI_TYPE()))
		    {
			securityClassList = retListDesc(RepConstantsCommon.TEMPLATE_SCL_CRIT_LOV, sessionCO
				.getLanguage(), crtCO, RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION);
		    }

		    if(table1 != null && !table1.trim().equals(""))
		    {
			if(table1.toLowerCase(Locale.ENGLISH).equals(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
			{
			    detSC = new TmplGridSC();
			    detSC.setGrid(false);
			    detSC.setTABLE_NAME(table1.toUpperCase(Locale.ENGLISH));
			    detSC.setCRITERIA_TYPE_CODE(crtCO.getCRITERIA_TYPE_CODE());
			    if(RepConstantsCommon.CRITERIA_MBK.equals(crtCO.getCRITERIA_TYPE_CODE()))
			    {
				detSC
					.setCODE_STR(((crtCO.getGlstmpltParamLinkVO().getOPERATOR1() == null) || crtCO
						.getGlstmpltParamLinkVO().getOPERATOR1()
						.equals(RepConstantsCommon.ZERO)) ? null : crtCO
						.getGlstmpltParamLinkVO().getOPERATOR1());
			    }
			    else
			    {
				detSC.setCODE_STR(((crtCO.getCODE1() == null) || crtCO.getCODE1().equals(
					RepConstantsCommon.ZERO)) ? null : crtCO.getCODE1());
			    }
			    detSC.setLANG_CODE(sessionCO.getLanguage());
			    detailList = templateBO.getFilterCrtDetList(detSC);
			}
			else
			{
			    detSC = new TmplGridSC();
			    detSC.setGrid(false);
			    detSC.setTABLE_NAME(table1);
			    detSC
				    .setCODE(crtCO.getCODE1() == null ? null : new BigDecimal(crtCO.getCODE1()
					    .toString()));
			    if(RepConstantsCommon.REGION_BR_VIEW.equals(table1)
				    || RepConstantsCommon.MAIN_ECO_SEC_VIEW1.equals(table1)
				    || RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(table1)
				    || RepConstantsCommon.GL_TERM_VIEW.equals(table1)
				    || RepConstantsCommon.SMT_VIEW.equals(table1))
			    {
				detSC.setCOMP_CODE(null);
			    }
			    else
			    {
				detSC.setCOMP_CODE(sessionCO.getCompanyCode());
			    }
			    if(RepConstantsCommon.GL_TERM_VIEW.equals(table1))
			    {
				detSC.setCODE_STR(crtCO.getCODE1());
			    }
			    if(RepConstantsCommon.SMT_VIEW.equals(table1))
			    {
				detSC.setOPTION_CODE(crtCO.getGlstmpltParamLinkVO().getCRITERIA_CODE());
			    }
			    detailList = templateBO.getFilterCrtDetLList(detSC);
			}
			if(detailList != null && !detailList.isEmpty())
			{
			    retVal = detailList.get(0);
			    if(retVal != null)
			    {
				crtCO.setCODE1_NAME(retVal.getBRIEF_DESC_ENG());
				if(crtCO.getCODE1() == null || crtCO.getCODE1().equals(RepConstantsCommon.ZERO))
				{
				    crtCO.setCODE1(retVal.getCODE_STR());
				}
			    }
			}
		    }
		    table2 = crtCO.getTABLE_NAME2();
		    if(table2 != null && !table2.trim().equals(""))
		    {
			if(table2.toLowerCase(Locale.ENGLISH).equals(RepConstantsCommon.IRP_CRIT_DTLS_LOOKUP_SMALL))
			{
			    detSC = new TmplGridSC();
			    detSC.setGrid(false);
			    detSC.setTABLE_NAME(table2.toUpperCase(Locale.ENGLISH));
			    detSC.setCRITERIA_TYPE_CODE(crtCO.getCRITERIA_TYPE_CODE());
			    detSC.setCODE_STR(crtCO.getGlstmpltParamLinkVO().getCODE2().toString());
			    detSC.setLANG_CODE(sessionCO.getLanguage());
			    detailList = templateBO.getFilterCrtDetList(detSC);
			}
			else
			{
			    detSC = new TmplGridSC();
			    detSC.setGrid(false);
			    if(table2.toUpperCase(Locale.ENGLISH).equals(RepConstantsCommon.ECO_SEC_CRIT_VIEW))
			    {
				detSC.setSECTOR_CODE((crtCO.getCODE1() == null) ? null : (new BigDecimal(crtCO
					.getCODE1().toString())));
			    }
			    else
			    {
				detSC.setSECTOR_CODE(null);
			    }
			    detSC.setTABLE_NAME(table2);
			    detSC.setCODE(new BigDecimal(crtCO.getGlstmpltParamLinkVO().getCODE2().toString()));
			    if(!RepConstantsCommon.MAIN_ECO_SEC_VIEW2.equals(table2))
			    {
				detSC.setCOMP_CODE(sessionCO.getCompanyCode());
			    }
			    detSC.setCODE1(new BigDecimal(crtCO.getCODE1()));
			    if(RepConstantsCommon.CRITERIA_CAF.equals(crtCO.getCRITERIA_TYPE_CODE()) && StringUtil.isNotEmpty(crtCO.getCODE1()) && !NumberUtil.isEmptyDecimal(crtCO.getGlstmpltParamLinkVO().getCODE2()))

			    {
				TmplGridSC sc= new TmplGridSC();
				sc.setCODE_CAF(new BigDecimal(crtCO.getCODE1()));
				sc.setFIELD_SEQ(crtCO.getGlstmpltParamLinkVO().getCODE2());
				sc.setCODE(crtCO.getGlstmpltParamLinkVO().getSECURITY_CODE1());
				HashMap<String, Object> repSessionCOMap=PathPropertyUtil.convertToMap(sessionCO);
				detailList = templateBO.retCrtAddFieldsDet3Dep(repSessionCOMap,sc);
			    }
			    else
			    {
				detailList = templateBO.getFilterCrtDetLList(detSC);
			    }
			}
			if(detailList != null && !detailList.isEmpty())
			{
			    retVal = detailList.get(0);
			    if(retVal != null)
			    {
				crtCO.setCODE2_NAME(retVal.getBRIEF_DESC_ENG());
			    }
			    if(RepConstantsCommon.CRITERIA_VAL_SEC.equals(crtCO.getCRITERIA_TYPE_CODE()))
			    {
				crtCO.setCODE1_NAME(crtCO.getCODE2_NAME());
			    }
			}
		    }
		    dbCrtMap.put(crtCO.getConcatKey(), crtCO);
		}
		lineCO.setDbCrtMap(dbCrtMap);
	    }
	    // no criteria in db and no criteria in addmap=>not blocking
	    if(lineCO.getDbCrtMap().isEmpty() && lineCO.getAddCrtMap().isEmpty())
	    {
		// 1=> the RTV gl line can be added
		updates = RepConstantsCommon.ONE;
	    }
	    // both maps not emty => blocking
	    // dbcrt only contains 1 and add empty and currency in db =>ok
	    else if(lineCO.getDbCrtMap().size() == 1 && lineCO.getAddCrtMap().isEmpty())
	    {
		// check that only there's is only one line currency in dbcrtmap
		// and included
		GLSTMPLT_PARAM_LINKCO criteriaCO = (new ArrayList<GLSTMPLT_PARAM_LINKCO>(lineCO.getDbCrtMap().values()))
			.get(0);
		if((RepConstantsCommon.CRITERIA_CURRENCY.equals(criteriaCO.getCRITERIA_TYPE_CODE())
		   && RepConstantsCommon.YES_CAP.equals(criteriaCO.getGlstmpltParamLinkVO().getINCLUDE()))
			||("SCU".equals(criteriaCO.getCRITERIA_TYPE_CODE())
			  && RepConstantsCommon.YES_CAP.equals(criteriaCO.getGlstmpltParamLinkVO().getINCLUDE())))
		{
		    updates = RepConstantsCommon.ONE;
		}
	    }
	    else if(lineCO.getAddCrtMap().size() == 1 && lineCO.getDbCrtMap().isEmpty())
	    {
		// check that only there's is only one line currency in dbcrtmap
		// and included
		GLSTMPLT_PARAM_LINKCO criteriaCO = (new ArrayList<GLSTMPLT_PARAM_LINKCO>(lineCO.getAddCrtMap().values()))
			.get(0);
		if(RepConstantsCommon.CRITERIA_CURRENCY.equals(criteriaCO.getCRITERIA_TYPE_CODE())
			&& criteriaCO.getINCLUDE_CHK()
		   ||"SCU".equals(criteriaCO.getCRITERIA_TYPE_CODE())
			&& criteriaCO.getINCLUDE_CHK()	)
		{
		    updates = RepConstantsCommon.ONE;
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public GLSTMPLTVO getCheckTemplCodeVO()
    {
	return checkTemplCodeVO;
    }

    public void setCheckTemplCodeVO(GLSTMPLTVO checkTemplCodeVO)
    {
	this.checkTemplCodeVO = checkTemplCodeVO;
    }

    public GLSTMPLTCO getTemplCO()
    {
	return templCO;
    }

    public void setTemplCO(GLSTMPLTCO templCO)
    {
	this.templCO = templCO;
    }

    public GLSTMPLTGLSDETCO getGlCO()
    {
	return glCO;
    }

    public void setGlCO(GLSTMPLTGLSDETCO glCO)
    {
	this.glCO = glCO;
    }

    public void setTemplateBO(TemplateBO templateBO)
    {
	this.templateBO = templateBO;
    }

    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public String getOper()
    {
	return oper;
    }

    public void setOper(String oper)
    {
	this.oper = oper;
    }

    public GLSTMPLTGLSDETCO getCheckSubglstmpltCO()
    {
	return checkSubglstmpltCO;
    }

    public void setCheckSubglstmpltCO(GLSTMPLTGLSDETCO checkSubglstmpltCO)
    {
	this.checkSubglstmpltCO = checkSubglstmpltCO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public FTR_TMPLT_EXPRCO getExprOrGLByLineCO()
    {
	return exprOrGLByLineCO;
    }

    public void setExprOrGLByLineCO(FTR_TMPLT_EXPRCO exprOrGLByLineCO)
    {
	this.exprOrGLByLineCO = exprOrGLByLineCO;
    }

    public List<String> getOperators()
    {
	return operators;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    public GLSTMPLT_PARAM_LINKCO getCrtCO()
    {
	return crtCO;
    }

    public void setCrtCO(GLSTMPLT_PARAM_LINKCO crtCO)
    {
	this.crtCO = crtCO;
    }

    public BigDecimal getCode1()
    {
	return code1;
    }

    public void setCode1(BigDecimal code1)
    {
	this.code1 = code1;
    }

    public String getStr()
    {
	return str;
    }

    public void setStr(String str)
    {
	this.str = str;
    }

    public String getStr1()
    {
	return str1;
    }

    public void setStr1(String str1)
    {
	this.str1 = str1;
    }
    
    /**
     * This function will check if the template satisfy the RTV condtions
     * */
    public String checkRTVcalcType()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    CommonDetailsSC sc=new CommonDetailsSC();
	    sc.setCODE(code1);
	    sc.setTEMPLATE_CODE(code);
	    sc.setCompCode(sessionCO.getCompanyCode());
	    
	    int isSatisfyRtv=templateBO.checkRTVcalcType(sc);
	    setUpdates1(String.valueOf(isSatisfyRtv));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * Method for cross checking report
     * @return
     */
    public String checkCrosscheckReport()
    {
	try
	{
	    if(returnCommonLibBO().returnIsSybase() == 1)
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		IRP_AD_HOC_REPORTCO repCO = repSessionCO.getReportCO();
		if(ConstantsCommon.REP_APP_NAME.equals(repCO.getAPP_NAME())
			&& RepConstantsCommon.ONE.equals(repCO.getREPORT_TYPE())
			&& RepConstantsCommon.REP_FTR_R0025.equals(repCO.getPROG_REF()))
		{
		    SessionCO sessionCO = returnSessionObject();
		    CommonDetailsSC sc = new CommonDetailsSC();
		    sc.setUserId(sessionCO.getUserName());
		    sc.setAS_OF_DATE(updates1);
		    sc.setTRADE_DATE_VAL(updates);
		    sc.setBaseCurrencyCode(sessionCO.getBaseCurrencyCode());
		    HashMap<String, Integer> resultMap = templateBO.checkCrosscheckReport(sc);
		    setUpdates1(String.valueOf(resultMap.get(RepConstantsCommon.REP_BOB45)));
		    setUpdates(String.valueOf(resultMap.get(RepConstantsCommon.REP_BOB23)));
		}
		else
		{
		    setUpdates(RepConstantsCommon.Y);
		}
	    }
	    else
	    {
		setUpdates(RepConstantsCommon.Y);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * Method to check if the user is able to insert a new line at the selected location or he needs to reorganize the lines before
     * @return
     */
    public String checkIfReorganize()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    LinkedHashMap<BigDecimal, GLSTMPLTCO> finalMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();
	    finalMap.putAll(allTempl.getDbLinesMap());
	    finalMap.putAll(allTempl.getAddLinesMap());
	    finalMap = sortHashMapByValuesD(finalMap);
	    GLSTMPLTCO co;
	    GLSTMPLTCO nextCO;
	    BigDecimal lnNbr;
	    BigDecimal nextLnNbr;
	    int diff;
	    int cnt=0;
	    updates="0";
	    Iterator it =finalMap.values().iterator();
	    while(it.hasNext())
	    {
		cnt++;
		co=(GLSTMPLTCO) it.next();
		lnNbr=co.getNewLineNumber();
		if(lnNbr.equals(code) && cnt <finalMap.size())
		{
		    nextCO=(GLSTMPLTCO) it.next();
		    nextLnNbr=nextCO.getNewLineNumber();
		    diff=nextLnNbr.intValue()-lnNbr.intValue();
		    if(diff ==1)
		    {
			updates="1";
		    }
		    break;
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, "", "");
	}
	return SUCCESS;
    }
}
