package com.path.actions.reporting.designer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRDatasetRun;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.json.JSONException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.path.actions.ReportAction.SepartorFormat;
import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPTVOKey;
import com.path.dbmaps.vo.PTH_CLIENTSVO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingBaseAction;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.AUTOMATED_IMPORT_REPORTSCO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_CLIENT_REPORTCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.ImageCO;
import com.path.vo.reporting.PTH_CLIENTSCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.ftr.template.AXSCO;

public class UploadDownloadReportAction extends ReportingBaseAction implements ServletRequestAware,
	ServletResponseAware
{
    private String contentType;
    private File upload;
    private String fileName;
    private String caption;
    private IRP_AD_HOC_REPORTCO repCO = new IRP_AD_HOC_REPORTCO();
    private String updates;
    private String concatSubRepName = "";
    private DesignerBO designerBO;
    private QueryBO queryBO;
    private BigDecimal reportId;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    ArrayList<SYS_PARAM_LOV_TRANSVO> reportFormatsList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();

    private CommonLookupBO commonLookupBO;
    private String isSubRep;
    private String haveSubRep;
    private CommonLibBO commonLibBO;
    private String argOrder; // return 1 if arg order is not respected and 0 if
    // it is respected
    private String update;
    private int isSyb;
    private String updatesImages;
    private String repIdStr;
    private ArrayList<BigDecimal> repIdsLst;
    private ArrayList<String> repRefLst;
    private ArrayList<String> repNameLst;
    private ArrayList<String> repIdsStrLst;
    private ArrayList<String> repAppNameLst;
    private ArrayList<BigDecimal> downladFlagLst;
    private String fullBasicExp;
    private String zipPassword;
    private String skipPrefValidation;
    private boolean skipTrans;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> importOptionsList;
    private String importOption;
    private boolean useExistingOptAccess;
    private boolean updateVersionIfEqual;
    private boolean overwriteTrans;
    private List<AUTOMATED_IMPORT_REPORTSCO> autoImportCOList = new ArrayList<AUTOMATED_IMPORT_REPORTSCO>();
    private boolean keepSchedsHyperlinks;
    private BigDecimal code;
    
    public BigDecimal getCode() {
		return code;
	}

	public void setCode(BigDecimal code) {
		this.code = code;
	}

	public boolean isKeepSchedsHyperlinks()
    {
        return keepSchedsHyperlinks;
    }

    public void setKeepSchedsHyperlinks(boolean keepSchedsHyperlinks)
    {
        this.keepSchedsHyperlinks = keepSchedsHyperlinks;
    }

    public List<AUTOMATED_IMPORT_REPORTSCO> getAutoImportCOList()
    {
        return autoImportCOList;
    }

    public void setAutoImportCOList(ArrayList<AUTOMATED_IMPORT_REPORTSCO> autoImportCOList)
    {
        this.autoImportCOList = autoImportCOList;
    }

    public boolean isOverwriteTrans() {
		return overwriteTrans;
	}

	public void setOverwriteTrans(boolean overwriteTrans) {
		this.overwriteTrans = overwriteTrans;
	}
    public boolean isUseExistingOptAccess()
    {
        return useExistingOptAccess;
    }

    public void setUseExistingOptAccess(boolean useExistingOptAccess)
    {
        this.useExistingOptAccess = useExistingOptAccess;
    }
    public boolean isUpdateVersionIfEqual()
    {
        return updateVersionIfEqual;
    }

    public void setUpdateVersionIfEqual(boolean updateVersionIfEqual)
    {
        this.updateVersionIfEqual = updateVersionIfEqual;
    }


    public String getImportOption()
    {
        return importOption;
    }

    public void setImportOption(String importOption)
    {
        this.importOption = importOption;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getImportOptionsList()
    {
        return importOptionsList;
    }

    public void setImportOptionsList(ArrayList<SYS_PARAM_LOV_TRANSVO> importOptionsList)
    {
        this.importOptionsList = importOptionsList;
    }

    ArrayList<PTH_CLIENTSVO> pthClientList = new ArrayList<PTH_CLIENTSVO>();
    private String cltRepFlag;
    ArrayList<SYS_PARAM_LOV_TRANSVO> whenNoDataList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    public ArrayList<SYS_PARAM_LOV_TRANSVO> getWhenNoDataList()
    {
        return whenNoDataList;
    }
    public void setWhenNoDataList(ArrayList<SYS_PARAM_LOV_TRANSVO> whenNoDataList)
    {
        this.whenNoDataList = whenNoDataList;
    }


    public boolean isSkipTrans()
    {
        return skipTrans;
    }

    public void setSkipTrans(boolean skipTrans)
    {
        this.skipTrans = skipTrans;
    }

    public String getCltRepFlag()
    {
        return cltRepFlag;
    }

    public void setCltRepFlag(String cltRepFlag)
    {
        this.cltRepFlag = cltRepFlag;
    }

    public String getSkipPrefValidation()
    {
        return skipPrefValidation;
    }

    public void setSkipPrefValidation(String skipPrefValidation)
    {
        this.skipPrefValidation = skipPrefValidation;
    }

    public ArrayList<String> getRepIdsStrLst()
    {
        return repIdsStrLst;
    }

    public String getZipPassword()
    {
        return zipPassword;
    }

    public void setZipPassword(String zipPassword)
    {
        this.zipPassword = zipPassword;
    }

    public ArrayList<String> getRepAppNameLst()
    {
        return repAppNameLst;
    }

    public void setRepAppNameLst(ArrayList<String> repAppNameLst)
    {
        this.repAppNameLst = repAppNameLst;
    }

    public void setRepIdsStrLst(ArrayList<String> repIdsStrLst)
    {
        this.repIdsStrLst = repIdsStrLst;
    }

    public String getFullBasicExp()
    {
        return fullBasicExp;
    }

    public void setFullBasicExp(String fullBasicExp)
    {
        this.fullBasicExp = fullBasicExp;
    }

    public ArrayList<BigDecimal> getDownladFlagLst()
    {
        return downladFlagLst;
    }

    public void setDownladFlagLst(ArrayList<BigDecimal> downladFlagLst)
    {
        this.downladFlagLst = downladFlagLst;
    }

    public ArrayList<String> getRepNameLst()
    {
        return repNameLst;
    }

    public void setRepNameLst(ArrayList<String> repNameLst)
    {
        this.repNameLst = repNameLst;
    }


    public ArrayList<BigDecimal> getRepIdsLst()
    {
        return repIdsLst;
    }

    public void setRepIdsLst(ArrayList<BigDecimal> repIdsLst)
    {
        this.repIdsLst = repIdsLst;
    }

    public ArrayList<String> getRepRefLst()
    {
        return repRefLst;
    }

    public void setRepRefLst(ArrayList<String> repRefLst)
    {
        this.repRefLst = repRefLst;
    }

    public String getRepIdStr()
    {
        return repIdStr;
    }

    public void setRepIdStr(String repIdStr)
    {
        this.repIdStr = repIdStr;
    }

    public String getUpdatesImages()
    {
	return updatesImages;
    }

    public void setUpdatesImages(String updatesImages)
    {
	this.updatesImages = updatesImages;
    }

    public int getIsSyb()
    {
	return isSyb;
    }

    public void setIsSyb(int isSyb)
    {
	this.isSyb = isSyb;
    }

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public String getArgOrder()
    {
	return argOrder;
    }

    public void setArgOrder(String argOrder)
    {
	this.argOrder = argOrder;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
	this.commonLibBO = commonLibBO;
    }

    public String getIsSubRep()
    {
	return isSubRep;
    }

    public void setIsSubRep(String isSubRep)
    {
	this.isSubRep = isSubRep;
    }

    public String getConcatSubRepName()
    {
	return concatSubRepName;
    }

    public void setConcatSubRepName(String concatSubRepName)
    {
	this.concatSubRepName = concatSubRepName;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public String getHaveSubRep()
    {
	return haveSubRep;
    }

    public void setHaveSubRep(String haveSubRep)
    {
	this.haveSubRep = haveSubRep;
    }

    @Override
    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response)
    {
	this.response = response;
    }

    public BigDecimal getReportId()
    {
	return reportId;
    }

    public void setReportId(BigDecimal reportId)
    {
	this.reportId = reportId;
    }

    private CommonRepFuncBO commonRepFuncBO;

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public IRP_AD_HOC_REPORTCO getRepCO()
    {
	return repCO;
    }

    public void setRepCO(IRP_AD_HOC_REPORTCO repCO)
    {
	this.repCO = repCO;
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

    public String input() throws Exception
    {
	return SUCCESS;
    }

    public String upload() throws Exception
    {
	return SUCCESS;
    }

    public String loadUploadDownload() throws JSONException
    {
	// clear session before starting
	try
	{
	    retWhenNoDataList(false);
	    clearUpDownSession();
	    set_showSmartInfoBtn("false");
	    isSyb = commonLibBO.returnIsSybase();
	    PTH_CTRL1VO pthCtrl1VO =commonLibBO.returnPthCtrl1();
	    cltRepFlag = pthCtrl1VO.getCLIENT_REPORT_FLAG();
	    addScreenDisplayParam();
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "uploadDownload";
    }

    public void addScreenDisplayParam() 
    {
	SYS_PARAM_SCREEN_DISPLAYVO opt = new SYS_PARAM_SCREEN_DISPLAYVO();
	opt.setIS_READONLY(BigDecimal.ONE);
	getAdditionalScreenParams().put("skipOptAxs", opt);
	
	SYS_PARAM_SCREEN_DISPLAYVO menuTitleEng = new SYS_PARAM_SCREEN_DISPLAYVO();
	menuTitleEng.setIS_VISIBLE(BigDecimal.ZERO);
	getAdditionalScreenParams().put("menuTitleEng", menuTitleEng);
	
	SYS_PARAM_SCREEN_DISPLAYVO refId = new SYS_PARAM_SCREEN_DISPLAYVO();
	refId.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("refId", refId);
	
	SYS_PARAM_SCREEN_DISPLAYVO appName = new SYS_PARAM_SCREEN_DISPLAYVO();
	appName.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("lookuptxt_appName", appName);
	
	SYS_PARAM_SCREEN_DISPLAYVO pRef = new SYS_PARAM_SCREEN_DISPLAYVO();
	pRef.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("lookuptxt_fcrPRef", pRef);
	getAdditionalScreenParams().put("pRefStr", pRef);
	
	SYS_PARAM_SCREEN_DISPLAYVO repName = new SYS_PARAM_SCREEN_DISPLAYVO();
	repName.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("repName", repName);
	
	SYS_PARAM_SCREEN_DISPLAYVO format = new SYS_PARAM_SCREEN_DISPLAYVO();
	format.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("format", format);
	
	
	SYS_PARAM_SCREEN_DISPLAYVO categ = new SYS_PARAM_SCREEN_DISPLAYVO();
	categ.setIS_VISIBLE(BigDecimal.ZERO);
	getAdditionalScreenParams().put("lookuptxt_CATEGORY_ID", categ);
	getAdditionalScreenParams().put("CATEGORY_DESC", categ);
	
    }
    public List<SepartorFormat> getCsvSeparators()
    {
	ArrayList<SepartorFormat> csvSeparatorsList = new ArrayList<SepartorFormat>();
	csvSeparatorsList.add(new SepartorFormat(",", ","));
	csvSeparatorsList.add(new SepartorFormat(getText("reporting.tab"), "\t"));
	return csvSeparatorsList;
    }

    public List getReportFormats() throws BaseException
    {
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	reportFormatsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	return reportFormatsList;
    }
   
    
    public String reloadWhenNoDataList()
    {
	try
	{
	 // if empty uploadDownload form - reloadWhenNoDataList
	    if(BigDecimal.valueOf(-1).equals(getReportId()))
	    {
		retWhenNoDataList(true);
	    }
	    else
	    {
		retWhenNoDataList(false);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    
    /**
     * Method that will hide the category lkp and empty its content in addtion to the element app_is_web_yn
     * @return SUCCESS
     */
    public String applyCategoryScreenDisplay()
    {
    	try
    	{
    		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> categDescDisplay = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
    		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> categCodeDisplay = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
    		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> isWebYn = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
    	  	categDescDisplay = returnCommonLibBO().applyDynScreenDisplay("CATEGORY_DESC", "repCO.CATEGORY_DESC", ConstantsCommon.ACTION_TYPE_VISIBLE, "0", categDescDisplay, null);
    	  	categCodeDisplay = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_CATEGORY_ID", "repCO.CATEGORY_ID", ConstantsCommon.ACTION_TYPE_VISIBLE, "0", categCodeDisplay, null);
    	  	categDescDisplay = returnCommonLibBO().applyDynScreenDisplay("CATEGORY_DESC", "repCO.CATEGORY_DESC", ConstantsCommon.ACTION_TYPE_VALUE, "", categDescDisplay, null);
    	  	categCodeDisplay = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_CATEGORY_ID", "repCO.CATEGORY_ID", ConstantsCommon.ACTION_TYPE_VALUE, "", categCodeDisplay, null);
    	  	isWebYn = returnCommonLibBO().applyDynScreenDisplay("APP_IS_WEB_YN", "repCO.APP_IS_WEB_YN_", ConstantsCommon.ACTION_TYPE_VALUE, "", isWebYn, null);
    	    getAdditionalScreenParams().putAll(categDescDisplay);
    	    getAdditionalScreenParams().putAll(categCodeDisplay);
    	    getAdditionalScreenParams().putAll(isWebYn);
    	}
    	catch(Exception e)
    	{
    		log.error(e,"");
    	}
    	return SUCCESS;
    }
    
    public void retWhenNoDataList(Boolean isReset) throws BaseException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.WHEN_NO_DATA_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    whenNoDataList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	    if(!isReset)
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		IRP_AD_HOC_REPORTCO repCO = repSessionCO.getReportCO();
		if(repCO!=null && !repCO.getQueriesList().isEmpty())
		{
		    String outputLayout = repCO.getQueriesList().get(0).getSqlObject().getOutputLayout();
		    if(ConstantsCommon.OUTPUT_LAYOUT_TABULAR.equals(outputLayout))
		    {
			hideTabWhenNoDataTypes();
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public void hideTabWhenNoDataTypes()
    {
	// only show the common tpyes between tabular and freeform
	whenNoDataList.remove(3);
	whenNoDataList.remove(3);
    }
    
    public List<IRP_CONNECTIONSVO> getConnectionsList()
    {
	try
	{
	    return commonLookupBO.returnConnectionsList();
	}
	catch(BaseException e)
	{
	    log.error(e, "Error in getConnectionsList");
	    return null;
	}
    }

    public void clearUpDownSession() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setReportCO(new IRP_AD_HOC_REPORTCO());
    }

    /**
     * this function is used to check if a sub report is uploaded before the
     * main report it return the list of sub report non uploaded with comma
     * separated in case his call is from upload and return the technical report
     * name(the report name in jasper design)in case his call is from save
     * upload
     * 
     * @param jasperDesign
     * @param fromWhere
     * @return
     */

    public String checkIfSubRepUploaded(JasperDesign jasperDesign, String fromWhere) throws Exception
    {
	int subStrExp = 8;
	JRSection detailsBand = jasperDesign.getDetailSection();
	JRElement[] elements = null;
	JRBand[] details = detailsBand.getBands();
	JRDesignSubreport subRep;
	elements = details[0].getElements();
	String subRepTechNameForSave = jasperDesign.getName();
	// List
	try
	{
	    for(int i = 0; i < elements.length; i++)
	    {
		JRElement element = elements[i];
		if(element instanceof JRDesignSubreport)
		{
		    subRep = (JRDesignSubreport) element;
		    String subRepExp = subRep.getExpression().getText();
		    String[] subRepNameSplit = subRepExp.split(" ");
		    String subRepName = subRepNameSplit[2].substring(1, subRepNameSplit[2].length() - subStrExp);

		    String subRepDbName = designerBO.checkIfSubRepUploaded(subRepName);
		    if(subRepDbName == null)
		    {
			concatSubRepName = concatSubRepName + "," + subRepName;
		    }

		}

	    }
	    if(concatSubRepName != null && concatSubRepName.charAt(0) == ',')
	    {
		concatSubRepName = concatSubRepName.substring(1, concatSubRepName.length());
	    }
	}
	catch(BaseException e)
	{

	    log.error(e, "-------Error in uploadReport---------");
	}
	if("fromUploadRep".equals(fromWhere))
	{
	    return concatSubRepName;
	}
	else
	{
	    return subRepTechNameForSave;
	}

    }

    /**
     * Method called on import process from automated screen
     * 
     * @return
     */
    public String startAutomatedImportProcess()
    {
	updates = RepConstantsCommon.EMPTY_STRING;
	AUTOMATED_IMPORT_REPORTSCO co;
	StringBuffer sb = new StringBuffer();
	String logInformation;
	for(int i = 0; i < autoImportCOList.size(); i++)
	{
	    caption = ConstantsCommon.FALSE;
	    co = autoImportCOList.get(i);
	    importOption = co.getACTION();
	    useExistingOptAccess = co.isUSE_EXISTING_OPT();
	    overwriteTrans = co.isSKIP_TRANSLATION();
	    updateVersionIfEqual = co.isUPDATE_VERSION_IF_EQUAL();
	    upload = null;
	    fileName = update + "/" + co.getZIP_FILE_NAME();
	    startImportProcess();
	    if(!updates.isEmpty())
	    {
		logInformation = RepConstantsCommon.AUTO_IMP_WARNINGS;
		if(ConstantsCommon.FALSE.equals(caption))
		{
		    logInformation = RepConstantsCommon.AUTO_IMP_ERRORS;
		}
		updates = "\n\r\n\r\n\r" + co.getZIP_FILE_NAME() + "   /   " + logInformation + ":\n\r" + updates;
		sb.append(updates);
		updates = RepConstantsCommon.EMPTY_STRING;
	    }
	}
	if(sb.length() > 0)
	{
	    // update will be used in the js to check if an error was generated
	    update = RepConstantsCommon.ONE;
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.setWarningsAutomatedImport(new StringBuffer(sb.toString().trim()));
	}
	else
	{
	    update = RepConstantsCommon.ZERO;
	}
	return SUCCESS;
    }

    /**
     * Method that shows the warnings encountered during the automated import
     * process
     * 
     * @return
     */
    public String showWarningsAutomatedImport()
    {
	response.setContentType("application/txt");
	response.addHeader("content-disposition", "attachment;filename=\"" + RepConstantsCommon.AUTO_IMP_LOGS + "."
		+ ConstantsCommon.TXT_EXT + "\"");
	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	try
	{
	    byte[] responseBytes = repSessionCO.getWarningsAutomatedImport().toString().getBytes(CommonUtil.ENCODING);
	    // empty the session
	    removeWarningsFromSession();
	    response.getOutputStream().write(responseBytes);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return null;
    }
    
    public String removeWarningsFromSession()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setWarningsAutomatedImport(new StringBuffer());
	return SUCCESS;
    }
    
    /**
     * Method called to start the import process
     * @return
     */
    public String startImportProcess()
    {
	String result = "successJSON";
	SessionCO sessionCO = returnSessionObject();
	try
	{
	    String userId = sessionCO.getUserName();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal branchCode = sessionCO.getBranchCode();
	    AXSCO axsCO = null;
	    if(RepConstantsCommon.REPLACE.equals(importOption))
	    {
		axsCO = new AXSCO();
		axsCO.setCOMP_CODE(sessionCO.getCompanyCode());
		axsCO.setBRANCH_CODE(sessionCO.getBranchCode());
		axsCO.setUSER_ID(returnUserName());
	    }

	    if(RepConstantsCommon.SKIP.equals(importOption))
	    {
		useExistingOptAccess = true;
		overwriteTrans=false;
		updateVersionIfEqual = false;
	    }
	    HashMap<String,Object> paramMaps = new HashMap<String, Object>();
	    HashMap<String, Object> resultMap = designerBO.importRepFiles(userId, compCode, branchCode, importOption,
		    axsCO, sessionCO, useExistingOptAccess, overwriteTrans,updateVersionIfEqual, getZipPassword(), upload, fileName,get_pageRef(),
		    keepSchedsHyperlinks);
	    // this hm to map the qry ids oldRepQryId,newRepQryId
	    paramMaps.put(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP, resultMap
		    .get(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP));
	    // this hm to map the connection ids old connection Id,new
	    // connection Id
	    paramMaps.put(RepConstantsCommon.CONN_ID_MAP, resultMap.get(RepConstantsCommon.CONN_ID_MAP));
	    // this hm to map the proc ids old proc Id,new proc Id
	    paramMaps.put(RepConstantsCommon.IRP_PROC_MAP, resultMap.get(RepConstantsCommon.IRP_PROC_MAP));
	    // this hm to map the hash table ids old hash table Id,new hash
	    // table Id
	    paramMaps.put(RepConstantsCommon.IRP_HASH_TABLE_MAP, resultMap.get(RepConstantsCommon.IRP_HASH_TABLE_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_AD_HOC_REPORT_MAP, resultMap
		    .get(RepConstantsCommon.IRP_AD_HOC_REPORT_MAP));
	    paramMaps.put(RepConstantsCommon.OLD_NEW_REP_REPLACE_MAP, resultMap
		    .get(RepConstantsCommon.OLD_NEW_REP_REPLACE_MAP));
	    paramMaps
		    .put(RepConstantsCommon.IRP_CONNECTIONS_MAP, resultMap.get(RepConstantsCommon.IRP_CONNECTIONS_MAP));
	    paramMaps.put(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP, resultMap
		    .get(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP));
	    paramMaps.put(RepConstantsCommon.FTR_EXP_XLS_MAP, resultMap.get(RepConstantsCommon.FTR_EXP_XLS_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_REP_ARGUMENTS_MAP, resultMap
		    .get(RepConstantsCommon.IRP_REP_ARGUMENTS_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS_MAP, resultMap
		    .get(RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_REP_PROC_MAP, resultMap.get(RepConstantsCommon.IRP_REP_PROC_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_REP_PROC_PARAMS_MAP, resultMap
		    .get(RepConstantsCommon.IRP_REP_PROC_PARAMS_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_SUB_REPORT_MAP, resultMap.get(RepConstantsCommon.IRP_SUB_REPORT_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_QUERY_ARG_MAPPING_MAP, resultMap
		    .get(RepConstantsCommon.IRP_QUERY_ARG_MAPPING_MAP));
	    paramMaps.put(RepConstantsCommon.PROG_REF_APP_OPT_MAP, resultMap
		    .get(RepConstantsCommon.PROG_REF_APP_OPT_MAP));
	    paramMaps.put(RepConstantsCommon.OPT_MAP, resultMap.get(RepConstantsCommon.OPT_MAP));
	    paramMaps.put(RepConstantsCommon.OPT_EXTENDED_MAP, resultMap.get(RepConstantsCommon.OPT_EXTENDED_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_AD_HOC_QUERY_MAP, resultMap
		    .get(RepConstantsCommon.IRP_AD_HOC_QUERY_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_HASH_TABLE_REPORT_MAP, resultMap
		    .get(RepConstantsCommon.IRP_HASH_TABLE_REPORT_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_CLIENT_REPORT_MAP, resultMap
		    .get(RepConstantsCommon.IRP_CLIENT_REPORT_MAP));
	    paramMaps.put(RepConstantsCommon.PTH_CLIENTS_MAP, resultMap.get(RepConstantsCommon.PTH_CLIENTS_MAP));
	    paramMaps.put(RepConstantsCommon.REP_SUB_REP_LIST_MAP, resultMap
		    .get(RepConstantsCommon.REP_SUB_REP_LIST_MAP));
	    paramMaps.put(RepConstantsCommon.IMPORT_TRANSLATION_MAP, resultMap
		    .get(RepConstantsCommon.IMPORT_TRANSLATION_MAP));
	    BigDecimal excelReportId;
	    IRP_AD_HOC_REPORTVOWithBLOBs adHocRepVO;
	    ArrayList<BaseException> exceptionsLst = (ArrayList<BaseException>) resultMap
		    .get(RepConstantsCommon.EXCEPTIONS_LIST);
	    StringBuffer failedReportsSb = new StringBuffer();
	    StringBuffer failedMainReportsSb = new StringBuffer();
	    LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> irpAddHocRepHM = (LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs>) resultMap
		    .get(RepConstantsCommon.IRP_AD_HOC_REPORT_MAP);
	    Date date = new Date();
	    paramMaps.put(RepConstantsCommon.IMPORT_REPORTS_DATE, date);
	    paramMaps.put(RepConstantsCommon.REP_SNAPSHOT_PARAM_MAP,resultMap.get(RepConstantsCommon.REP_SNAPSHOT_PARAM_MAP));
	    paramMaps.put(RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING_MAP,resultMap.get(RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING_MAP));
	    paramMaps.put(RepConstantsCommon.SUB_REP_MAP,resultMap.get(RepConstantsCommon.SUB_REP_MAP));
	    paramMaps.put(RepConstantsCommon.FILTERS_MAP,resultMap.get(RepConstantsCommon.FILTERS_MAP));
	    paramMaps.put(RepConstantsCommon.ARG_FILTERS_MAP,resultMap.get(RepConstantsCommon.ARG_FILTERS_MAP));
	    String progRef;
	    caption=ConstantsCommon.TRUE;
	    for(Entry<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> entry : irpAddHocRepHM.entrySet())
	    {
		excelReportId = entry.getKey();
		adHocRepVO = entry.getValue();
		progRef = adHocRepVO.getPROG_REF();
		HashMap<String, Object> resMap;
		try
		{
		    resMap = designerBO.saveImportedReport(paramMaps, excelReportId, adHocRepVO, userId, compCode,
			    branchCode, sessionCO, useExistingOptAccess,get_pageRef());
		    paramMaps.put(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP, resMap
			    .get(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP));
		    paramMaps.put(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP, resMap
			    .get(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP));
		}
		catch(BOException e)
		{
		    caption=ConstantsCommon.FALSE;
		    if(RepConstantsCommon.IMPORT_FAILED_MAIN_SUB_REPORTS.equals(e.getMsgIdent()))
		    {
			failedMainReportsSb.append(progRef + ",");
		    }
		    else
		    {
			failedReportsSb.append(progRef + ",");
		    }
		}

	    }
	    HashMap<String, ArrayList<Object>> schedMap = (HashMap<String, ArrayList<Object>>) resultMap
		    .get(RepConstantsCommon.IRP_SCHED_HYPER);
	    if(keepSchedsHyperlinks && !schedMap.isEmpty())
	    {
		designerBO.saveSchedulersHyperlinksToDb(schedMap);
	    }
	    if(failedReportsSb.length()>0)
	    {
		exceptionsLst = designerBO.addException(failedReportsSb, MessageCodes.REP_IMPORT_FAILED, exceptionsLst);
	    }
	    if(failedMainReportsSb.length()>0)
	    {
		exceptionsLst = designerBO.addException(failedMainReportsSb, MessageCodes.REP_MAIN_IMPORT_FAILED_DUE_TO_SUB, exceptionsLst);
	    }

	    StringBuffer sb=new StringBuffer("");
	    if(!exceptionsLst.isEmpty())
	    {
		for(int i =0;i<exceptionsLst.size();i++)
		{
		    sb.append(commonLibBO.translateErrorMessage(exceptionsLst.get(i), sessionCO.getLanguage())[0]+ConstantsCommon.NEW_LINE);
		}
		updates = sb.toString().replace("null","");
	    }
	    
	}
	catch(BaseException e)
	{
	    result = RepConstantsCommon.IMPORT_ERROR_ACTION;
	    // for automated import
	    if(upload == null)
	    {
		updates = commonLibBO.translateErrorMessage(e, sessionCO.getLanguage())[0] + ConstantsCommon.NEW_LINE;
	    }
	    else
	    {
		handleException(e, null, null);
	    }
	    return result;
	}
	catch(Exception e)
	{
	    result = RepConstantsCommon.IMPORT_ERROR_ACTION;
	    handleException(e, null, null);
	    return result;
	}
	finally 
	{
	    // automated import
	    if(upload == null)
	    {
		File zipFolder;
		try {
			zipFolder = new PathFileSecure(fileName.substring(0, fileName.length() - 4));
			if(zipFolder.exists())
			{
			    try
			    {
				FileUtil.deleteFolder(zipFolder.getPath());
			    }
			    catch(Exception e)
			    {
				log.error(e, e.getMessage());
			    }
			}
		} catch (Exception e1) {
			log.error(e1, e1.getMessage());
		}
	    }
	    else
	    {
		String reportFolder = RepConstantsCommon.IMPORT_REP + "/" + fileName;
		String unZippedRepFldr = reportFolder.substring(0, reportFolder.length() - 4);
		try
		{
		    designerBO.deleteZipFolder(reportFolder, unZippedRepFldr);
		}
		catch(BaseException e)
		{
		    log.error(e, e.getMessage());
		}
	    }
	}

	return result;
    }
    
    public String uploadReport() throws Exception
    {
	int subStrExp = 8;
	String updateAll = updates;
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String report = repositoryPath + "/upload/" + fileName;
	String result = "successJSON";
	List<ImageCO> imagesList = new ArrayList<ImageCO>();
	String sqlString="";
	try
	{
	    // save file to disk
	    FileUtils.copyFile(upload, new PathFileSecure(report));

	    JasperDesign jasperDesign = JRXmlLoader.load(report);

	    List paramsList = jasperDesign.getParametersList();
	    jasperDesign.getGroups();
	    JRDesignParameter param;
	    // check if the argument name contais a spcace then send an alert to
	    // the user
	    for(int i = 0; i < paramsList.size(); i++)
	    {
		param = (JRDesignParameter) paramsList.get(i);
		if(!param.isSystemDefined() && !param.getValueClass().equals(ReportParamsCO.class)
			&& !("SUBREPORT_DIR".equals(param.getName())) && param.getName().contains(" ")
			)// skip
		// system
		// and
		// global
		// report
		// parameters
		{

		    addDependencyMsg(getText("reporting.ArgNameSpace"), null);
		    return SUCCESS;
		}
	    }
	    // save the jrxml in the reportCO object
	    byte[] jrxml = FileUtil.readFileBytes(report);
	    
	    // reorder jrxml
	     String xslFileName = "classpath:com/path/bo/reporting/common/jasperRules/reorder-jasper-reports.xsl";
	    // this method is called to sort the elements in jrxml by x and y
	    // positions to avoid the duplication of the textfileds in the xhtml
	    // output in the editor
	    String newjrxml = designerBO.reorderJRxml(jrxml, xslFileName);
	    jrxml = newjrxml.getBytes(CommonUtil.ENCODING);

	    // create the sessionObject
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());

	    // store the old procsMap ,argsMap and procParamsMap in order to use
	    // them if the user don't want to replace them
	    List<IRP_SUB_REPORTCO> oldSubRep = repSessionCO.getReportCO().getSubreportsList();
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> oldArgsMap = (LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>) repSessionCO
		    .getReportCO().getArgumentsList().clone();
	    HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> oldProcParamsMap = (HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>>) repSessionCO
		    .getReportCO().getProcParamsMap().clone();
	    List<IRP_REP_PROCCO> oldProcsList = new ArrayList<IRP_REP_PROCCO>();
	    List<IRP_REP_PROCCO> currProcList = repSessionCO.getReportCO().getProceduresList();
	    for(int i = 0; i < currProcList.size(); i++)
	    {
		oldProcsList.add(currProcList.get(i));
	    }
	    
	    repSessionCO.setReportParams(true);
	    IRP_AD_HOC_REPORTCO newRepCO = new IRP_AD_HOC_REPORTCO();
	    newRepCO.setREPORT_NAME(repCO.getREPORT_NAME());
	    newRepCO.setPROG_REF(repCO.getPROG_REF());
	    newRepCO.setPARENT_REF(repCO.getPARENT_REF());
	    newRepCO.setPARENT_REF_STR(repCO.getPARENT_REF_STR());
	    newRepCO.setAPP_NAME(repCO.getAPP_NAME());
	    newRepCO.setJRXML_FILE(jrxml);
	    newRepCO.setFromUpDown(true);
	    newRepCO.setCONN_ID(repCO.getCONN_ID());
	    newRepCO.setDEFAULT_FORMAT(repCO.getDEFAULT_FORMAT());
	    newRepCO.setDATE_UPDATED(repCO.getDATE_UPDATED());
	    newRepCO.setMAIL_SERVER_CODE(repCO.getMAIL_SERVER_CODE());

	    
	    if("true".equals(repCO.getSHOW_HEAD_FOOT()))
	    {
		newRepCO.setSHOW_HEAD_FOOT("0");
	    }
	    else
	    {
		newRepCO.setSHOW_HEAD_FOOT("1");
	    }
	    if(RepConstantsCommon.FALSE.equals(repCO.getLANG_INDEPENDENT_YN()))
	    {
		newRepCO.setLANG_INDEPENDENT_YN("0");
	    }
	    else
	    {
		newRepCO.setLANG_INDEPENDENT_YN("1");
	    }
	    if(ConstantsCommon.CSV_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT()))
	    {
		newRepCO.setCSV_SEPARATOR(repCO.getCSV_SEPARATOR());
	    }
	    else
	    {
		newRepCO.setCSV_SEPARATOR("");
	    }

	    if(repSessionCO.getReportCO() != null && repSessionCO.getReportCO().getREPORT_ID() != null)
	    {
		newRepCO.setUpdateUpDownRep(true);
	    }
	    if(repSessionCO.getReportCO() != null)// update mode
	    {
		newRepCO.setREPORT_ID(repSessionCO.getReportCO().getREPORT_ID());
	    }
	    
	    //add the hashtbllist
	    newRepCO.setHashTblList(repSessionCO.getReportCO().getHashTblList());
	    //add the client list
	    newRepCO.setRepClientList(repSessionCO.getReportCO().getRepClientList());
	    //emptying the linkQryArsMap
	    repSessionCO.getLinkQryArsMap().clear();
	    repSessionCO.setReportCO(newRepCO);
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> localArguments = repSessionCO.getReportCO().getArgumentsList();

	    // empty the sqlObj on each upload
	    repSessionCO.setSqlObj(new SQL_OBJECT());
	    SQL_OBJECT sqlObj = repSessionCO.getSqlObj();

	    // get jasperDesign object

	    IRP_REP_ARGUMENTSCO argCO;
	    int order = 0;
	    String paramVal;
	    String paramType;

	    // fill parameters
	    if("false".equals(updateAll))
	    {
		// fill the new argumentsMap from the old one
		localArguments.putAll(oldArgsMap);
		/*
		 * fill the ArgsDBUpdate maps in order to save the updates when
		 * saving the report since the elements of the agrsMap will not
		 * be saved because the arguments have an id which is mean that
		 * it is not a new argument
		 */
		repSessionCO.getReportCO().getArgsDBUpdate().putAll(oldArgsMap);
		repSessionCO.getReportCO().setProceduresList(oldProcsList);
		repSessionCO.getReportCO().setProcParamsMap(oldProcParamsMap);
		repSessionCO.getReportCO().setSubreportsList(oldSubRep);

	    }
	    else
	    {
		// fill the ArgsDBDelee since the old args should be deleted
		// because we are replacing all the old parameters
		repSessionCO.getReportCO().setArgsDbDelete(oldArgsMap);
		for(int i = 0; i < paramsList.size(); i++)
		{
		    param = (JRDesignParameter) paramsList.get(i);
		    if(!param.isSystemDefined() && !param.getValueClass().equals(ReportParamsCO.class)
			    && !("SUBREPORT_DIR".equals(param.getName()))) // skip
		    // system
		    // and
		    // global
		    // report
		    // parameters
		    {
			argCO = new IRP_REP_ARGUMENTSCO();
			argCO.setARGUMENT_SOURCE(BigDecimal.ONE);
			argCO.setIndex(generateId() + i);
			if(StringUtil.nullToEmpty(param.getDescription()).isEmpty())
			{
			    argCO.setARGUMENT_LABEL(param.getName());
			}
			else
			{
			    argCO.setARGUMENT_LABEL(param.getDescription());
			}
			
			argCO.setARGUMENT_NAME(param.getName());

			if(param.getDefaultValueExpression() == null)
			{
			    paramType = param.getValueClassName();
			}
			else
			{
			    paramType = param.getDefaultValueExpression().getValueClassName();
			}
			
			if("java.util.Date".equals(paramType))
			{
			    paramType = "DATE";
			}
			else if("java.math.BigDecimal".equals(paramType) || "java.lang.Number".equals(paramType))
			{
			    paramType = "NUMBER";
			}
			else if(RepConstantsCommon.TIMESTAMP_TYPE_JASPER.equals(paramType))
			{
			    paramType=ConstantsCommon.datetime;
			}
			else if(RepConstantsCommon.JAVA_UTIL_COLLECTION.equals(paramType))
			{
			    argCO.setARGUMENT_SOURCE(new BigDecimal(ConstantsCommon.REP_ARG_TYPE_QRY));
			    argCO.setMULTISELECT_YN(BigDecimal.ONE);
			    if(RepConstantsCommon.REP_BIGDECIMAL.equals(param.getNestedType().getSimpleName()))
			    {
				paramType = ConstantsCommon.PARAM_TYPE_NUMBER;
			    }
			    else if(RepConstantsCommon.NESTED_TYPE_STRING.equals(param.getNestedType().getSimpleName()))
			    {
				paramType = ConstantsCommon.PARAM_TYPE_VARCHAR2;
			    }
			    else if(RepConstantsCommon.NESTED_TYPE_DATE.equals(param.getNestedType().getSimpleName()))
			    {
				paramType= ConstantsCommon.PARAM_TYPE_DATE;
			    }
			}
			else
			{
			    paramType = "VARCHAR2";
			}
			argCO.setARGUMENT_TYPE(paramType);

			if(param.getDefaultValueExpression() == null)
			{
			    paramVal = null;
			}
			else
			{
			    paramVal = param.getDefaultValueExpression().getText();
			}
			if(paramVal != null)
			{
			    // when the report is downloaded from the designer
			    // and not from ireport
			    paramVal = paramVal.replaceAll("new BigDecimal\\(\"", "");
			    paramVal = paramVal.replaceAll("new String\\(\"", "");
			    paramVal = paramVal.replaceAll("new Date\\(\"", "");
			    paramVal = paramVal.replaceAll("\"\\)", "");

			    if(paramVal.contains("new BigDecimal"))
			    {
				String[] strArray = paramVal.split("BigDecimal");
				String str = strArray[1];
				paramVal = str.substring(1, str.length() - 1);
			    }
			    if(paramVal.contains("\""))
			    {
				paramVal = paramVal.substring(1, paramVal.length() - 1);
			    }
			}

			argCO.setARGUMENT_VALUE(paramVal);
			argCO.setSESSION_ATTR_NAME(null);
			argCO.setQUERY_ID(null);
			argCO.setQUERY_NAME(null);
			argCO.setCOLUMN_NAME(null);
			argCO.setValueFlag(false);
			argCO.setARGUMENT_ORDER(new BigDecimal(++order));
			argCO.setDISPLAY_FLAG("Y"); // param.isForPrompting()
			argCO.setENABLE_FLAG("Y");
			localArguments.put(argCO.getIndex(), argCO);
		    }
		}
	    }

	    if(jasperDesign.getDatasetsList().size() == 0) // free form
	    {
		sqlString = jasperDesign.getQuery().getText();
		sqlObj.setOutputLayout(ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM);

	    }
	    else
	    {
		JRDesignDataset ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
		sqlString = ds.getQuery().getText();
		sqlObj.setOutputLayout(ConstantsCommon.OUTPUT_LAYOUT_TABULAR);

		LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> sqlObjArgMap = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();

		Map<String, JRDataset> hmDataSet = jasperDesign.getDatasetMap();// jasperDesign.getd
		
		for(Entry<String, JRDataset> entry : hmDataSet.entrySet())
		{
		    JRParameter[] argSqlObjList = entry.getValue().getParameters();
		    JRDesignParameter sqlObjparam;
		    for(int a = 0; a < argSqlObjList.length; a++)
		    {
			sqlObjparam = (JRDesignParameter) argSqlObjList[a];
			if(!sqlObjparam.isSystemDefined() && !sqlObjparam.getValueClass().equals(ReportParamsCO.class)
				&& !("SUBREPORT_DIR".equals(sqlObjparam.getName()))) // skip
			// system
			// and
			// global
			// report
			// parameters
			{
			    argCO = new IRP_REP_ARGUMENTSCO();
			    argCO.setIndex(generateId() + a);
			    if(sqlObjparam.getDescription() == null)
			    {
				argCO.setARGUMENT_LABEL(sqlObjparam.getName());
			    }
			    else
			    {
				argCO.setARGUMENT_LABEL(sqlObjparam.getDescription());
			    }
			    argCO.setARGUMENT_NAME(sqlObjparam.getName());

			    if(sqlObjparam.getDefaultValueExpression() == null)
			    {
				paramType = sqlObjparam.getValueClassName();
			    }
			    else
			    {
				paramType = sqlObjparam.getDefaultValueExpression().getValueClassName();
			    }
			    if("java.util.Date".equals(paramType))
			    {
				paramType = "DATE";
			    }
			    else if("java.math.BigDecimal".equals(paramType) || "java.lang.Number".equals(paramType))
			    {
				paramType = "NUMBER";
			    }
			    else if(RepConstantsCommon.TIMESTAMP_TYPE_JASPER.equals(paramType))
			    {
				paramType=ConstantsCommon.datetime;
			    }
			    else
			    {
				paramType = "VARCHAR2";
			    }
			    argCO.setARGUMENT_TYPE(paramType);

			    if(sqlObjparam.getDefaultValueExpression() == null)
			    {
				paramVal = null;
			    }
			    else
			    {
				paramVal = sqlObjparam.getDefaultValueExpression().getText();
			    }
			    if(paramVal != null)
			    {
				// when the report is downloaded from the
				// designer and not from ireport
				paramVal = paramVal.replaceAll("new BigDecimal\\(\"", "");
				paramVal = paramVal.replaceAll("new String\\(\"", "");
				paramVal = paramVal.replaceAll("new Date\\(\"", "");
				paramVal = paramVal.replaceAll("\"\\)", "");
			    }

			    argCO.setARGUMENT_VALUE(paramVal);
			    argCO.setARGUMENT_SOURCE(BigDecimal.ONE);
			    argCO.setSESSION_ATTR_NAME(null);
			    argCO.setQUERY_ID(null);
			    argCO.setQUERY_NAME(null);
			    argCO.setCOLUMN_NAME(null);
			    argCO.setValueFlag(false);
			    argCO.setARGUMENT_ORDER(new BigDecimal(++order));
			    argCO.setDISPLAY_FLAG("Y"); // param.isForPrompting()
			    argCO.setENABLE_FLAG("Y");
			    sqlObjArgMap.put(argCO.getIndex(), argCO);
			}
		    }
		}

		JRDesignSection desSection = (JRDesignSection) jasperDesign.getDetailSection();
		JRBand[] band = desSection.getBands();
		JRElement[] elements;
		JRElement elem;
		Component comp;
		StandardTable tbl;
		JRDesignComponentElement compElem;
		JRDatasetRun jrDsRun;
		JRDatasetParameter[] jrDsParamLst;
		JRDatasetParameter jrDsParam;
		JRExpressionChunk[] jrExprChunckLst;
		JRExpressionChunk jrExprChunck;
		if(band.length > 0)
		{
		    elements = band[0].getElements();
		    if(elements != null)
		    {
			for(int i = 0; i < elements.length; i++)
			{
			    elem = elements[i];
			    if(elem instanceof JRDesignComponentElement)
			    {
				compElem = (JRDesignComponentElement) elem;
				comp = compElem.getComponent();
				if(comp instanceof StandardTable)
				{
				    tbl = (StandardTable) comp;
				    jrDsRun = tbl.getDatasetRun();
				    jrDsParamLst = jrDsRun.getParameters();
				    String dsRepMapParamName = "";
				    String dsParamName = "";
				    int type = 0;
				    for(int j = 0; j < jrDsParamLst.length; j++)
				    {
					jrDsParam = jrDsParamLst[j];
					dsParamName = jrDsParam.getName();
					jrExprChunckLst = jrDsParam.getExpression().getChunks();
					if(jrExprChunckLst!=null)
					{
					    for(int k = 0; k < jrExprChunckLst.length; k++)
					    {
						jrExprChunck = jrExprChunckLst[k];
						dsRepMapParamName = jrExprChunck.getText();
						type = jrExprChunck.getType();
					    }
					}
					for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : sqlObjArgMap.entrySet())
					{
					    Long key = entry.getKey();
					    IRP_REP_ARGUMENTSCO dsArgs = entry.getValue();
					    if(type == 1 && dsArgs.getARGUMENT_NAME().equals(dsParamName))
					    {
						dsArgs.setARGUMENT_VALUE(dsRepMapParamName);
						break;
					    }
					    if(dsArgs.getARGUMENT_NAME().equals(dsParamName))
					    {
						// dsArgs.setARGUMENT_NAME(dsParamName);
						dsArgs.setLINK_REP_QRY_ARG(dsRepMapParamName);
						sqlObjArgMap.put(key, dsArgs);
						break;
					    }
					}

				    }

				}
			    }
			}
		    }
		}

		sqlObj.setArgumentsList(sqlObjArgMap);

		// Replace parameters with values //$P{param2}
		IRP_REP_ARGUMENTSCO argObj = null;
		String argType;
		Iterator it = sqlObj.getArgumentsList().values().iterator();
		while(it.hasNext())
		{
		    argObj = (IRP_REP_ARGUMENTSCO) it.next();
		    argType = argObj.getARGUMENT_TYPE();
		    if(argType != null && "NUMBER".equals(argType))
		    {
			sqlString = StringUtil
				.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}", "(-1)");
			sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}",
				"(-1)");
			sqlString = CommonUtil.handleMultiValParam(sqlString,argObj,argType,Integer.parseInt(RepConstantsCommon.ZERO));
		    }
		    else if(argType != null && "VARCHAR2".equals(argType))// '$P!{UnionName}'
		    {
			sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
				"'-1'");
			sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}",
				"'-1'");
			sqlString = CommonUtil.handleMultiValParam(sqlString,argObj,argType,Integer.parseInt(RepConstantsCommon.ZERO));
		    }
		    else if(argType != null && "DATE".equals(argType))
		    {
			int isSybase = commonLibBO.returnIsSybase();
			if(isSybase == 1)
			{
			    sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
				    "GETDATE()");
			    sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}",
				    "GETDATE()");
			}
			else
			{
			    sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
				    "SYSDATE");
			    sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}",
				    "SYSDATE");
			}
			sqlString = CommonUtil.handleMultiValParam(sqlString,argObj,argType,isSybase);
		    }
		}
		if(!repCO.isSKIP_QRY_VALIDATTION())
		{
		    queryBO.validateSqbQuery(sqlString);
		}

	    }

	    sqlString = jasperDesign.getQuery().getText();
	    // Replace parameters with values //$P{param2}
	    IRP_REP_ARGUMENTSCO argObj = null;
	    String argType;
	    Iterator it = localArguments.values().iterator();
	    while(it.hasNext())
	    {
		argObj = (IRP_REP_ARGUMENTSCO) it.next();
		argType = argObj.getARGUMENT_TYPE();
		if(argType != null && "NUMBER".equals(argType))
		{
		    sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}", "(-1)");
		    sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}", "(-1)");
		    sqlString = CommonUtil.handleMultiValParam(sqlString,argObj,argType,Integer.parseInt(RepConstantsCommon.ZERO));
		}
		else if(argType != null && "VARCHAR2".equals(argType))// '$P!{UnionName}'
		{
		    sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}", "'-1'");
		    sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}", "'-1'");
		    sqlString = CommonUtil.handleMultiValParam(sqlString,argObj,argType,Integer.parseInt(RepConstantsCommon.ZERO));
		}
		else if(argType != null && "DATE".equals(argType))
		{
		    int isSybase = commonLibBO.returnIsSybase();
		    if(isSybase == 1)
		    {
			sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
				"GETDATE()");
			sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}",
				"GETDATE()");
		    }
		    else
		    {
			sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
				"SYSDATE");
			sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}",
				"SYSDATE");
		    }
		    sqlString = CommonUtil.handleMultiValParam(sqlString,argObj,argType,isSybase);
		}
	    }
	    if(!repCO.isSKIP_QRY_VALIDATTION())
	    {
		if(sqlString.indexOf(RepConstantsCommon.VALID_ERROR)!=-1)
		 {
		    BOException e = new BOException();
		    e.setMsgIdent(RepConstantsCommon.VALID_ERROR);
		    throw e;
		 }
		queryBO.validateSqbQuery(sqlString);
	    }

	    // fill the fieldsMap from the jrxml
	    LinkedHashMap feMap = new LinkedHashMap();
	    List<JRField> lstFe = jasperDesign.getFieldsList();
	    if(lstFe == null || lstFe.isEmpty()) // case when we are
	    // downloading a report
	    // created in the designer
	    // section
	    {
		JRField lstDsFe[] = jasperDesign.getDatasetsList().get(0).getFields();
		lstFe = new ArrayList<JRField>();
		for(int i = 0; i < lstDsFe.length; i++)
		{
		    lstFe.add(lstDsFe[i]);
		}
	    }
	    JRField fe;
	    IRP_FIELDSCO feCO;
	    for(int i = 0; i < lstFe.size(); i++)
	    {
		fe = lstFe.get(i);

		feCO = new IRP_FIELDSCO();
		feCO.setIndex(generateId() + i);
		feCO.setFeName(fe.getName());
		if(StringUtil.nullToEmpty(fe.getDescription()).isEmpty())
		{
		    feCO.setFIELD_ALIAS(fe.getName());
		    feCO.setHasBusinessName(0); // Note : we should set it to
		    // zero after migrating all
		    // reports
		}
		else
		{
		    feCO.setFIELD_ALIAS(fe.getDescription());
		}
		
		feCO.setFIELD_DB_NAME(fe.getName());
		feCO.setFIELD_DATA_TYPE(fe.getValueClassName());
		feCO.setLabelAlias(fe.getName());
		feCO.setType("1");
		feCO.setParent("1");
		feCO.setIsLeaf("true");
		feCO.setLevel("2");
		feMap.put(feCO.getIndex(), feCO);
	    }
	    
	    // fill queries
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    StringBuffer sqbSynt = new StringBuffer(jasperDesign.getQuery().getText());
	    // LinkedHashMap feMap=queryBO.fillSqlFields(sqlString);
	    sqlObj.setFields(feMap);
	    sqlObj.setQUERY_NAME(reportCO.getREPORT_NAME() + "_0");
	    sqlObj.setSqbSyntax(sqbSynt);

	    IRP_AD_HOC_QUERYCO queryCO = new IRP_AD_HOC_QUERYCO();
	    List<IRP_AD_HOC_QUERYCO> qrysList = reportCO.getQueriesList();
	    if(qrysList != null && !qrysList.isEmpty())
	    {
		queryCO = reportCO.getQueriesList().get(0);
	    }

	    queryCO.setQUERY_NAME(reportCO.getREPORT_NAME() + "_0");
	    queryCO.setSqlObject(sqlObj);
	    queryCO.setSqlSyntax(sqbSynt);
	    queryCO.setIndex(0);
	    List<IRP_AD_HOC_QUERYCO> queriesList = new ArrayList<IRP_AD_HOC_QUERYCO>();
	    queriesList.add(queryCO);
	    reportCO.setQueriesList(queriesList);

	    // read subreports
	    if("true".equals(updateAll))
	    { 
		// get the sub report from jrxml on upload
		List<IRP_SUB_REPORTCO> subrepList = new ArrayList<IRP_SUB_REPORTCO>();
		List<IRP_SUB_REPORTCO> subrepOneList = new ArrayList<IRP_SUB_REPORTCO>();

		JRBand[] allBandList = jasperDesign.getAllBands();
		List childLstBand;
		Object childBand;
		

		for(int b = 0; b < allBandList.length; b++)
		{
		    childLstBand = allBandList[b].getChildren();
		    for(int c = 0; c < childLstBand.size(); c++)
		    {
			childBand = childLstBand.get(c);
			JRDesignSubreport zSubRep;
			if(childBand instanceof JRDesignSubreport)
			{
			    zSubRep = (JRDesignSubreport) childBand;
			    String subRepExp = zSubRep.getExpression().getText();

			    String[] subRepNameSplit = subRepExp.split(" ");
			    // String [] subRepNameSplit =
			    // subRepExp.split("+");
			    StringBuffer sbSubToSplit = new StringBuffer("");
			    for(int j = 2; j < subRepNameSplit.length; j++)
			    {
				sbSubToSplit.append(" ");
				sbSubToSplit.append(subRepNameSplit[j]);
			    }

			    String subToSplit = sbSubToSplit.toString();

			    String subRepExpression = subToSplit.substring(2, subToSplit.length() - subStrExp);
			    IRP_SUB_REPORTCO theCO = new IRP_SUB_REPORTCO();
			    theCO.setPARENT_REP_NAME(repCO.getREPORT_NAME());
			    theCO.getIrpSubReportVO().setSUB_REPORT_EXPRESSION(subRepExpression);
			    subrepList.add(theCO);
			}
		    }
		}


		boolean repeatedSubRep = false;
		for(int i = 0; i < subrepList.size(); i++)
		{
		    repeatedSubRep = false;
		    if(i == 0)
		    {
			subrepOneList.add(subrepList.get(0));
			repeatedSubRep = true;
		    }
		    else
		    {
			for(int j = 0; j < subrepOneList.size(); j++)
			{
			    if(subrepOneList.get(j).getIrpSubReportVO().getSUB_REPORT_EXPRESSION().equals(
				    subrepList.get(i).getIrpSubReportVO().getSUB_REPORT_EXPRESSION()))
			    {
				repeatedSubRep = true;
				break;
			    }

			}
		    }
		    if(!repeatedSubRep)
		    {
			subrepOneList.add(subrepList.get(i));
		    }
		}
		reportCO.setSubreportsList(subrepOneList);
	    }

	    // read images from the jrxml to update the images grid
	    JRBand[] allBandList = jasperDesign.getAllBands();
	    List childLstBand;
	    JRDesignComponentElement compElem;
	    Object childBand;
	    Component comp;
	    StandardTable tbl;
	    List columns;
	    StandardColumn col;
	    DesignCell head;
	    DesignCell foot;
	    DesignCell det;
	    JRElement[] headElems;
	    JRElement[] footElems;
	    JRElement[] detElems;
	    for(int b = 0; b < allBandList.length; b++)
	    {
		childLstBand = allBandList[b].getChildren();
		for(int c = 0; c < childLstBand.size(); c++)
		{
		    childBand = childLstBand.get(c);
		    if(childBand instanceof JRDesignImage)
		    {
			if(!((JRDesignImage) childBand).getPropertiesMap().containsProperty("blobImg"))
			{
			    // construct an images list from jrxml
			    imagesList = designerBO.retImgListFromJrxml(childBand, imagesList);
			}
		    }

		    else if(childBand instanceof JRDesignComponentElement)
		    {
			compElem = (JRDesignComponentElement) childBand;
			comp = compElem.getComponent();
			if(comp instanceof StandardTable)
			{
			    tbl = (StandardTable) comp;
			    columns = tbl.getColumns();
			    for(int k = 0; k < columns.size(); k++)
			    {
				col = (StandardColumn) columns.get(k);
				head = (DesignCell) col.getColumnHeader();
				foot = (DesignCell) col.getTableFooter();
				det = (DesignCell) col.getDetailCell();

				if(head != null)
				{
				    headElems = head.getElements();
				    if(headElems != null)
				    {
					for(int i = 0; i < headElems.length; i++)
					{
					    if(headElems[i] instanceof JRDesignImage)
					    {
						// construct an images list
						// from jrxml
						imagesList = designerBO.retImgListFromJrxml(headElems[i], imagesList);
					    }
					}
				    }
				}

				if(foot != null)
				{
				    footElems = foot.getElements();
				    if(footElems != null)
				    {
					for(int i = 0; i < footElems.length; i++)
					{
					    if(footElems[i] instanceof JRDesignImage)
					    {
						// construct an images list
						// from jrxml
						imagesList = designerBO.retImgListFromJrxml(footElems[i], imagesList);
					    }
					}
				    }
				}
				detElems = det.getElements();
				if(detElems != null)
				{
				    for(int i = 0; i < detElems.length; i++)
				    {
					if(detElems[i] instanceof JRDesignImage)
					{
					    // construct an images list from
					    // jrxml
					    imagesList = designerBO.retImgListFromJrxml(detElems[i], imagesList);
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	    reportCO.setImagesList(imagesList);
	}
	catch(BOException e)
	{
	    result = ERROR_ACTION;
	    if(RepConstantsCommon.VALID_ERROR.equals((e).getMsgIdent()))
	    {
		throw new BOException(getText("reporting.invalidMultiExpr") + "\n\n"
			+ sqlString.substring(Integer.valueOf(RepConstantsCommon.ZERO), sqlString.lastIndexOf("!") - 6),e);
	    }
	    else
	    {

		handleException(e, getText("reporting.parseJrxmlErr") + " " + repCO.getPROG_REF() + " "
			+ getText("reporting.andAppName") + " " + repCO.getAPP_NAME(), null);
	    }
	}
	catch(Exception e)
	{
	    result = ERROR_ACTION;
	    handleException(e, getText("reporting.parseJrxmlErr") + repCO.getPROG_REF() + " "
		    + getText("reporting.andAppName") + " " + repCO.getAPP_NAME(), null);
	}
	finally
	{
	    // delete saved file
	    File file = new PathFileSecure(report);
	    boolean isDel = file.delete();
	    if(!isDel)
	    {
		log.debug("The following file has not been deleted :" + report);
	    }

	}
	System.out.println("================================================= "+result);
	return result;
    }

    public Long generateId()
    {
	Calendar cal = Calendar.getInstance();
	return cal.getTimeInMillis();
    }

    public String checkProgRef() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    if("1".equals(skipPrefValidation))
	    {
		setUpdates("0");
	    }
	    else
	    {
		setUpdates(designerBO.retExistingOpts(updates, update, sessionCO.getLanguage(), getText("fcr.checkProgRefAlert")));
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "______________ error in checkProgRef ________________");
	}
	return SUCCESS;
    }

    public String saveUploadedReport() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	IRP_AD_HOC_REPORTCO reptCO = repSessionCO.getReportCO();
	try
	{
	    if(reptCO.getJRXML_FILE() == null)
	    {
		throw new BOException(getText("reportEmptyjrxml"));
	    }
	}
	catch(BOException e)
	{
	    handleException(e, null, null);
	    return SUCCESS;
	}
	reptCO.setREPORT_NAME(repCO.getREPORT_NAME());
	reptCO.setPARENT_REF(repCO.getPARENT_REF());
	reptCO.setPARENT_REF_STR(repCO.getPARENT_REF_STR());
	reptCO.setAPP_NAME(repCO.getAPP_NAME());
	reptCO.setCONN_ID(repCO.getCONN_ID());
	reptCO.setDATE_UPDATED(repCO.getDATE_UPDATED());
	reptCO.setDEFAULT_FORMAT(repCO.getDEFAULT_FORMAT());
	reptCO.setSHOW_HEAD_FOOT(repCO.getSHOW_HEAD_FOOT());
	reptCO.setLANG_INDEPENDENT_YN(repCO.getLANG_INDEPENDENT_YN());
	reptCO.setCIF_AUDIT_YN(repCO.getCIF_AUDIT_YN());
	reptCO.setSKIP_OPT_AXS(repCO.getSKIP_OPT_AXS());
	reptCO.setPROG_REF(repCO.getPROG_REF());
	reptCO.setXlsName(repCO.getXlsName());
	reptCO.setMENU_TITLE_ENG(repCO.getMENU_TITLE_ENG());
	reptCO.setCATEGORY_ID(repCO.getCATEGORY_ID());
	PTH_CTRL1VO pthCtrl1VO =commonLibBO.returnPthCtrl1();
	
	if (!StringUtil.nullToEmpty(repCO.getREPORT_VERSION()).isEmpty() && !RepConstantsCommon.ZERO.equals(repCO.getREPORT_VERSION()))
	{
	    reptCO.setREPORT_MODIFIED_YN(RepConstantsCommon.ONE);
	}

	reptCO.setCltRepFlag(pthCtrl1VO.getCLIENT_REPORT_FLAG());
	if("1".equals(reptCO.getCltRepFlag()) && reptCO.getRepClientList().isEmpty())
	{ try
	 {
	    PTH_CLIENTSCO defaultClient =designerBO.returnDefaultClient();
	    IRP_CLIENT_REPORTCO clientReportCO = new IRP_CLIENT_REPORTCO();
	    clientReportCO.getIrpClientReportVO().setREPORT_REF(reptCO.getPROG_REF());
	    clientReportCO.getIrpClientReportVO().setAPP_NAME(reptCO.getAPP_NAME());
	    clientReportCO.getIrpClientReportVO().setCLIENT_ACRONYM(defaultClient.getPthClientsVO().getCLIENT_ACRONYM());
	    clientReportCO.setCLIENT_NAME(defaultClient.getPthClientsVO().getCLIENT_NAME());
	    
	    List<IRP_CLIENT_REPORTCO> clientRep= new ArrayList<IRP_CLIENT_REPORTCO>();
	    clientRep.add(clientReportCO);
	    reptCO.setRepClientList(clientRep);
	 }
	    catch(Exception e){
		 throw new Exception(getText("reporting.exceptionDefaultClient"),e);
	    }
	}
	if("true".equals(repCO.getREPORT_TYPE())) 
	{
	    reptCO.setREPORT_TYPE("1");
	}
	else
	{
	    reptCO.setREPORT_TYPE("0");
	}
	// reptCO.setREPORT_TYPE(REPORT_TYPE)
	reptCO.setLinkQryArsMap(repSessionCO.getLinkQryArsMap());
	haveSubRep = "0";
	
	if(updatesImages != null && !updatesImages.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updatesImages, ImageCO.class, true);
	    gu.getLstAllRec();
	    ArrayList<ImageCO> imgLst = gu.getLstAllRec();
	   
	    reptCO = designerBO.writeUploadedReportfile(reptCO,imgLst);
	
	}

	if(repSessionCO.getReportCO().getSubreportsList().size() > 0)
	{
	    for(int i = 0; i < repSessionCO.getReportCO().getSubreportsList().size(); i++)
	    {
		if(repSessionCO.getReportCO().getSubreportsList().get(i).getIrpSubReportVO().getREPORT_ID() == null)
		{
		    haveSubRep = "1";
		    return SUCCESS;
		}
	    }

	}

	
	String message = "";
	SessionCO sessionCO = returnSessionObject();
	String user = sessionCO.getUserName();
	IRP_AD_HOC_REPORTVOWithBLOBs vo = new IRP_AD_HOC_REPORTVOWithBLOBs();
	try
	{
	    vo.setREPORT_NAME(reptCO.getREPORT_NAME());
	    vo.setJRXML_FILE(reptCO.getJRXML_FILE());
	    vo.setMODIFIED_BY(user);
	    vo.setDATE_MODIFIED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
	    vo.setAPP_NAME(reptCO.getAPP_NAME());
	    vo.setCONN_ID(reptCO.getCONN_ID());
	    vo.setDEFAULT_FORMAT(reptCO.getDEFAULT_FORMAT());
	    vo.setWHEN_NO_DATA(repCO.getWHEN_NO_DATA());
	    vo.setMAIL_SERVER_CODE(repCO.getMAIL_SERVER_CODE());
	    vo.setNBR_DISPLAYED_ARG_PER_ROW(repCO.getNBR_DISPLAYED_ARG_PER_ROW());
	    vo.setREPORT_MODIFIED_YN(reptCO.getREPORT_MODIFIED_YN());
	    vo.setGENERATED_FILE_NAME(repCO.getGENERATED_FILE_NAME());
	    vo.setMETADATA_REPORT_ID(repCO.getMETADATA_REPORT_ID());
	    vo.setMETADATA_REPORT_EXT(repCO.getMETADATA_REPORT_EXT());
	    vo.setMETADATA_GENERATED_LOCATION(repCO.getMETADATA_GENERATED_LOCATION());
	    vo.setCIF_AUDIT_YN(repCO.getCIF_AUDIT_YN());
	    if("true".equals(repCO.getSHOW_HEAD_FOOT()))
	    {
		vo.setSHOW_HEAD_FOOT("0");
	    }
	    else
	    {
		vo.setSHOW_HEAD_FOOT("1");
	    }
	    vo.setLANG_INDEPENDENT_YN(repCO.getLANG_INDEPENDENT_YN());
	    
	    if(ConstantsCommon.CSV_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT())
		    || ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT()))
	    {
		vo.setCSV_SEPARATOR(repCO.getCSV_SEPARATOR());
		reptCO.setCSV_SEPARATOR(repCO.getCSV_SEPARATOR());
	    }
	    else
	    {
		vo.setCSV_SEPARATOR("");
		reptCO.setCSV_SEPARATOR("");
	    }

	    if(reptCO.getREPORT_ID() == null)
	    {
		vo.setXHTML_FILE(("<HTML></HTML>").getBytes(FileUtil.DEFAULT_FILE_ENCODING));
		vo.setREP_FLAG(BigDecimal.ONE);
	    }
	    else
	    {
		IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
		repSC.setREPORT_ID(reptCO.getREPORT_ID());
		IRP_AD_HOC_REPORTCO flagsCO = designerBO.retReportFlags(repSC);

		if((flagsCO.getREP_FLAG() != null && BigDecimal.ONE.equals(flagsCO.getREP_FLAG()))
			|| reptCO.isUpdateUpDownRep())
		{
		    vo.setREP_FLAG(BigDecimal.ONE);
		    vo.setXHTML_FILE(("<HTML></HTML>").getBytes(FileUtil.DEFAULT_FILE_ENCODING));
		}
		if(flagsCO.getREP_FLAG() == null)
		{
		    vo.setXHTML_FILE(reptCO.getXHTML_FILE());
		}
	    }
	    
	    AuditRefCO refCO = initAuditRefCO();
	    BigDecimal id = null;
	    boolean updateRep = false;
	    if(reptCO.getREPORT_ID() == null)
	    {
		vo.setPROG_REF(reptCO.getPROG_REF());
		vo.setCREATED_BY(user);
		vo.setDATE_CREATED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
		// return the next report id
		id = commonRepFuncBO.retCounterValue("IRP_AD_HOC_REPORT");
		vo.setREPORT_ID(id);

		refCO.setOperationType(AuditConstant.CREATED);
		refCO.setKeyRef(AuditConstant.UPLOAD_DOWNLOAD_KEY_REF);
	    }
	    else
	    {
		vo.setREPORT_ID(reptCO.getREPORT_ID());
		updateRep = true;
		refCO.setOperationType(AuditConstant.UPDATE);
		reptCO.setAuditObj(returnAuditObject(IRP_AD_HOC_REPORTCO.class));
		refCO.setKeyRef(AuditConstant.UPLOAD_DOWNLOAD_KEY_REF);
	    }
	    

	    for(IRP_AD_HOC_QUERYCO query : reptCO.getQueriesList())
	    {
		if(query.getQUERY_ID() != null && query.getQUERY_ID().compareTo(BigDecimal.ZERO) > 0)
		{
		    query.setUpdate(true);
		}
		else
		{
		    // return next query id
		    id = commonRepFuncBO.retCounterValue("IRP_AD_HOC_QUERY");
		    query.setQUERY_ID(id);
		    query.setUpdate(false);
		}
	    }

	    reptCO.setAuditRefCO(refCO);

	    reptCO = designerBO.save(vo, reptCO, updateRep, sessionCO.getCompanyCode(), sessionCO.getBranchCode(),
		    returnUserName(),get_pageRef());
	}
	catch(BaseException e)
	{
	    //log.error(e, "Error when saving the report.");
	    if(e.getMessage() == null || e.getMessage().indexOf("2099") == -1)
	    {
		handleException(e, "Error saving the report", "saveReport.exceptionMsg._key");
		message = getText("saveReport_error_key");
	    }
	    else
	    {
		handleException(e, null, null);
		message = e.getMessage();
	    } 
	}
	catch(UnsupportedEncodingException e)
	{
	    e.printStackTrace();
	}
	catch(IOException e)
	{
	    //log.error(e, "Error serializing sql object into bytes.");
	    handleException(e, "Error serializing sql object into bytes", "saveReport.serialization.exceptionMsg._key");
	    message = getText("saveQuery_error_key");
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	finally
	{
	    designerBO.deleteFileFromRepository("updateJasperImgReport."+ConstantsCommon.JRXML_EXT); // delete saved file
	}

	if((message == null)
		|| (message != null && !message.equals(getText("saveReport_error_key")) && !message.contains("2099")))
	{
	    message = getText("saveReport_success_key");
	    reptCO.setREPORT_ID(vo.getREPORT_ID());
	    reptCO.setArgsDBUpdate(new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>());
	    reptCO.setArgsDbDelete(new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>());
	}

	repSessionCO.setReportCO(reptCO);
	setUpdate(reptCO.getREPORT_ID().toString());

	return SUCCESS;
    }

    public String retReportFlags() throws Exception
    {
	try
	{
	    BigDecimal repId = getReportId();
	    argOrder = "0";
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> argMapForSqlObj = reportCO.getArgumentsList();
	    ArrayList<IRP_REP_ARGUMENTSCO> argList = new ArrayList(argMapForSqlObj.values());
	    // BigDecimal[] arrayOrder =new BigDecimal[100];
	    int[] arrayOrder = new int[argList.size()];
	    for(int i = 0; i < argList.size(); i++)
	    {
		// BigDecimal order = argList.get(i).getARGUMENT_ORDER();
		// arrayOrder[i]=(BigDecimal) order;
		int order = argList.get(i).getARGUMENT_ORDER().intValue();
		arrayOrder[i] = order;
	    }
	    Arrays.sort(arrayOrder);
	    for(int i = 0; i < arrayOrder.length; i++)
	    {
		if((arrayOrder[i]) != i + 1)
		{
		    argOrder = "1";
		    break;
		}
	    }
	    if(repId == null || repId.intValue() == 0)
	    {
		// ReportingSessionCO repSessionCO =
		// returnReportingSessionObject(get_pageRef());
		// IRP_AD_HOC_REPORTCO reportCO=repSessionCO.getReportCO();
		if(reportCO.getREPORT_ID() == null)
		{
		    repCO = new IRP_AD_HOC_REPORTCO();
		}
		else
		{
		    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
		    repSC.setREPORT_ID(reportCO.getREPORT_ID());
		    repCO = designerBO.retReportFlags(repSC);
		    isSubRep = repCO.getIsSubRep();

		}
	    }
	    else
	    {
		IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
		repSC.setREPORT_ID(repId);
		repCO = designerBO.retReportFlags(repSC);
		isSubRep = repCO.getIsSubRep();
	    }
	    // checking if all the queries are filled for the arguments of type
	    // query
	    IRP_REP_ARGUMENTSCO argCO;
	    updates = RepConstantsCommon.ZERO;
	    for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : reportCO.getArgumentsList().entrySet())
	    {
		argCO = entry.getValue();
		if(new BigDecimal(ConstantsCommon.REP_ARG_TYPE_QRY).equals(argCO.getARGUMENT_SOURCE()) && argCO.getQUERY_ID()==null )
		{
		    updates  = RepConstantsCommon.ONE;
		    break;
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String checkIfEditReport() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    if(reportCO == null || reportCO.getREPORT_ID() == null)
	    {
		setUpdates("0");
	    }
	    else
	    {
		setUpdates("1");
	    }  
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    
    /**
     * get the subreport in recursive way for the selected reports
     * @return retExcArr[] array of string indice 0:report status (downloadble or not),indice 1:flag (true or false by default is false and turned to true if at least one report is not downloadable)
     * indice 2:concatenation of sub rep and report ids with a comma separated
     */
    public String[] fillSubRepDetRecursive(IRP_SUB_REPORTCO subCO,String[] retExcArr,int counter)
    {
	try
	{
	    BigDecimal repId=subCO.getIrpSubReportVO().getREPORT_ID();
	    List<IRP_SUB_REPORTCO> subrepChildList = designerBO.returnSubreports(repId);
	    IRP_SUB_REPORTCO childCO;
	    int ctr=counter;
	    if(subrepChildList!=null && !subrepChildList.isEmpty())
	    {
		ctr++;
		subCO.setSubRepList(subrepChildList);
		for(int i=0;i<subrepChildList.size();i++)
		{
		    StringBuffer strIds = new StringBuffer(retExcArr[2]);
		    StringBuffer strException = new StringBuffer(retExcArr[0]);
		    
		    strIds.append(subrepChildList.get(i).getIrpSubReportVO().getREPORT_ID()+",");
		    for(int j=0;j<ctr;j++)
		    {
			strException.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
			retExcArr[0] = strException.toString();
		    }
		    if(subrepChildList.get(i).getDOWNLOADABLE_FLAG().equals(BigDecimal.ZERO))
		    {
			strException.append(subrepChildList.get(i).getSUB_REPORT_NAME()).append(" ").append(getText("upDown.Down")).append(" <br>");
			retExcArr[0] = strException.toString();
		    }
		    else
		    {
			
			strException.append(subrepChildList.get(i).getSUB_REPORT_NAME()).append(" ").append(getText("upDown.notDown")).append(" <br>");
			retExcArr[0] = strException.toString();
			retExcArr[1]="true";
		    }
		    childCO=subrepChildList.get(i);
		    retExcArr[2] = strIds.toString();
		    fillSubRepDetRecursive(childCO,retExcArr,ctr);
		    
		    
		}
	    }
	}
	catch(BaseException e)
	{
	    log.error(e, "");
	    return null;
	}
	return retExcArr;
    }
    
    public String checkIfDownloadable()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    HashMap<BigDecimal, List <IRP_SUB_REPORTCO>> subRepMap = new HashMap<BigDecimal, List<IRP_SUB_REPORTCO>>(); 
	    ArrayList<BigDecimal> repIdsLst =getRepIdsLst();
	    ArrayList<BigDecimal> downladFlagLst =getDownladFlagLst();
	    StringBuffer  strException=new StringBuffer ("<html><table><tr>");
	    StringBuffer  strIds;
	    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage()))
	    {
		strException.append("<td align='right' style='width: 328px;'>").append(getText("upDown.notDownRep")).append(" :<br>");
	    }
	    else
	    {
		strException.append("<td align='left' style='width: 328px;'>").append(getText("upDown.notDownRep")).append(" :<br>");
	    }
	    String [] retExcArr = {"","false",""};// array of string indice 0:report status (downloadble or not),indice 1:flag,indice 2:sub rep ids
	    retExcArr[0]=strException.toString();
	    int cnt=1;//this counter to add a tabular for each sub rep on same level
	    for(int i=0;i<repIdsLst.size();i++)
	    {
		strIds=new StringBuffer (retExcArr[2]);
		strIds.append(repIdsLst.get(i)+",");
		retExcArr[2]=strIds.toString();
		if(downladFlagLst.get(i).equals(BigDecimal.ZERO))
		{
		    strException.append(" ").append(repNameLst.get(i)).append(" ").append(getText("upDown.Down")).append( " <br>");
		    retExcArr[0]=strException.toString();
		}
		else
		{
		    strException.append(" ").append(repNameLst.get(i)).append(" ").append(getText("upDown.notDown")).append(" <br>");
		    retExcArr[0]=strException.toString();
		    retExcArr[1] = "true";
		}
		    
		List<IRP_SUB_REPORTCO> subrepList = designerBO.returnSubreports(repIdsLst.get(i));
		subRepMap.put(repIdsLst.get(i), subrepList);
		for(int j=0;j<subrepList.size();j++)
		{
		    //retExcArr[2]=retExcArr[2] +subrepList.get(j).getIrpSubReportVO().getREPORT_ID()+",";
		    strIds=new StringBuffer (retExcArr[2]);
		    strIds.append(subrepList.get(j).getIrpSubReportVO().getREPORT_ID()+",");
		    strException.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
		    if(subrepList.get(j).getDOWNLOADABLE_FLAG().equals(BigDecimal.ZERO))
		    {
			strException.append(subrepList.get(j).getSUB_REPORT_NAME()).append(" ").append(getText("upDown.Down")+"<br>");
			retExcArr[0]=strException.toString();
		    }
		    else
		    {
			strException.append(subrepList.get(j).getSUB_REPORT_NAME()).append(" ").append(getText("upDown.notDown")).append(" <br>");
			retExcArr[0] = strException.toString();
			retExcArr[1] = "true";
		    }
		    retExcArr[2]=strIds.toString();
		    retExcArr= fillSubRepDetRecursive(subrepList.get(j),retExcArr,cnt);
		    strException= new StringBuffer(retExcArr[0]);
		}
		   cnt=1;// set cnt = 1 to initialize again the counter 
		   strException.append("<br>");
	    }
	    if(retExcArr[1].equals("true"))//false,true
	    {
		    CommonLibSC sc = new CommonLibSC();
		    sc.setAppName(sessionCO.getCurrentAppName());
		    sc.setProgRef(get_pageRef());
		    sc.setLanguage(sessionCO.getLanguage());
		    sc.setKeyLabelCode(strException.toString());
		    setUpdate(strException.toString());
		    strException.append("</td></tr></table></html>");
		    throw new BOException(commonLibBO.returnKeyLabelTrans(sc));
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null,null);
	}
	return SUCCESS;
    }
    
    public String openExportDialog() throws Exception
    {
	return "exportParam";
    } 
    
    public String openImportDialog() throws Exception
    {
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.REP_IMPORT_OPTIONS);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	importOptionsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	useExistingOptAccess = true;
	return "importParam";
    }
    
    
    /**
     * fill the subRepMap for export
     *
     */
    public void returnSubRepMap(HashMap<BigDecimal, IRP_SUB_REPORTCO> subRepMap,List<IRP_SUB_REPORTCO> subrepList)
    {
	for(int i=0;i<subrepList.size();i++)
	{
	    subRepMap.put(subrepList.get(i).getIrpSubReportVO().getREPORT_ID(), subrepList.get(i));
	    if(!subrepList.get(i).getSubRepList().isEmpty())
	    {
		returnSubRepMap( subRepMap,subrepList.get(i).getSubRepList());
	    }
	}
    }
    

    public String basicExportProcess()
    {
	    String userName="";
	    int errorCtr=0;
	try
	{

	    StringBuffer errorStr = new StringBuffer(getText("upDown.errorLog_key")+":\r\n");
	    String[] repIdsStrLst = getRepIdsStrLst().get(0).split(",");
	    String[] repRefLst= getRepRefLst().get(0).split(",");
	    String[] repAppNameLst= getRepAppNameLst().get(0).split(",");
	    
	    HashMap<BigDecimal, List<IRP_SUB_REPORTCO>> hierarchicalSubRepMap = new HashMap<BigDecimal, List<IRP_SUB_REPORTCO>>();
	    HashMap<BigDecimal, IRP_SUB_REPORTCO> subRepMap = new HashMap<BigDecimal, IRP_SUB_REPORTCO>();
	    int cnt = 0;
	    String[] retExcArr = { "", "false", "" };// array of string indice 0:report status (downloadble or not),indice 1:flag,indice 2:rep and sub rep ids
	    StringBuffer strBufferIds;
	    IRP_AD_HOC_REPORTCO report;
	    IRP_SUB_REPORTCO subRepCO = null;
	 // getting metadata reports if any
	    // below passed parameter to retMetaDataReports
	    ArrayList<BigDecimal> repIdList = new ArrayList<BigDecimal>();
	    // origList to replace values of repIdsStrLst
	    ArrayList<String> origList = new ArrayList<String>();
	    String val;
	    for(int i = 0; i < repIdsStrLst.length; i++)
	    {
		val=repIdsStrLst[i];
		origList.add(val);
		repIdList.add(new BigDecimal(val));
	    }
	    ArrayList<BigDecimal> metadataRepList = designerBO.retMetaDataReports(repIdList);
	    BigDecimal metadataRepId;
	    for(int i = 0; i < metadataRepList.size(); i++)
	    {
		metadataRepId = metadataRepList.get(i);
		if(!NumberUtil.isEmptyDecimal(metadataRepId))
		{
		    origList.add(metadataRepList.get(i).toString());
		}
	    }
	    repIdsStrLst = origList.toArray(new String[origList.size()]);
	    // end getting metadata reports
	    for(int i = 0; i < repIdsStrLst.length; i++)
	    {
		 report = null;
		 subRepCO = new IRP_SUB_REPORTCO();
		 strBufferIds=new StringBuffer (retExcArr[2]);
		 strBufferIds.append(repIdsStrLst[i]+",");
		 retExcArr[2]=strBufferIds.toString();
		try
		{ 
		  
		    report = designerBO.findReportById(new BigDecimal(repIdsStrLst[i]));
		    if(report == null)
		    {
			errorStr.append(getText("upDown.followRepDel_key")).append(":").append(repIdsStrLst[i]).append(" ").append(getText("progRef")).append(":"+repRefLst[i]).append(getText("upDown.appName_key")).append(":").append(repAppNameLst[i]).append("\r\n");//id_RepApp_progRef
			errorCtr++;
			continue;
		    }
		    // return subreports
		    List<IRP_SUB_REPORTCO> subrepList = designerBO.returnSubreports(report.getREPORT_ID());
		    report.setSubreportsList(subrepList);
		    subRepCO.getIrpSubReportVO().setREPORT_ID(report.getREPORT_ID());
		    subRepCO.setPROG_REF(report.getPROG_REF());
		    subRepCO.setAPP_NAME(report.getAPP_NAME());
		    subRepCO.setJRXML_FILE(report.getJRXML_FILE());
		    subRepCO.setSUB_REPORT_NAME(report.getREPORT_NAME());
		    subRepMap.put(report.getREPORT_ID(), subRepCO);
		}
		catch(Exception e)
		{
		   
		    errorStr.append(getText("upDown.unknownEx_key")).append(":").append(repIdsStrLst[i]).append(" ").append(getText("progRef")).append(":").append(repRefLst[i]).append(" ").append(getText("upDown.appName_key")).append(":").append(repAppNameLst[i]).append(" : ").append(e.getMessage()).append("\r\n");
		    errorCtr++;
		    continue;
		}
		//fill the sub rep map in hierarchical way
		hierarchicalSubRepMap.put(new BigDecimal(repIdsStrLst[i]), report.getSubreportsList());
		
		for(int m = 0; m < report.getSubreportsList().size(); m++)
		{
		    strBufferIds=new StringBuffer (retExcArr[2]);
		    strBufferIds.append(report.getSubreportsList().get(m).getIrpSubReportVO().getREPORT_ID()+ ",");
		    retExcArr[2]=strBufferIds.toString();
		    retExcArr = fillSubRepDetRecursive(report.getSubreportsList().get(m), retExcArr, cnt);
		}
		//this boucle to fill the subRepMap without redundancy
		for(Entry<BigDecimal, List<IRP_SUB_REPORTCO>> entry : hierarchicalSubRepMap.entrySet())
		{
		    List<IRP_SUB_REPORTCO> subrepList = entry.getValue();
		    for(int s = 0; s < subrepList.size(); s++)
		    {
			subRepMap.put(subrepList.get(s).getIrpSubReportVO().getREPORT_ID(), subrepList.get(s));
			returnSubRepMap(subRepMap, subrepList.get(s).getSubRepList());
		    }

		}

	    }

	    SessionCO sessionCO = returnSessionObject();
	    userName = sessionCO.getUserName();

	    List<ImageCO> imagesList =null;
	    JasperDesign jasperDesign;
	    HashMap<String, ImageCO> imageHm = new HashMap<String, ImageCO>();
	    for(Entry<BigDecimal, IRP_SUB_REPORTCO> entry : subRepMap.entrySet())
	    {
		 subRepCO = entry.getValue();
		 jasperDesign = null;
		try
		{
		    jasperDesign = designerBO.startBasicExportFile(userName,  subRepCO , jasperDesign);
		}
		catch(Exception e)
		{
		    if (e.getMessage().equals("UnsupportedEncoding"))
		    {
		    errorStr.append(getText("upDown.encodingEx_key")).append(":").append( subRepCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(getText("progRef")).append(":").append(subRepCO.getPROG_REF()).append(getText("upDown.appName_key")).append(":").append(subRepCO.getAPP_NAME()).append("\r\n");
		    errorCtr++;
		    continue;
		    }
		    else if (e.getMessage().equals("FileNotFound"))
		    {
			 errorStr.append(getText("upDown.fileNotFound")).append( " " ).append( "/"
			    ).append( RepConstantsCommon.EXPORT_REP ).append( "/" ).append( RepConstantsCommon.REP_EXT ).append( userName ).append( "/"
			    ).append( subRepCO.getSUB_REPORT_NAME()).append( ".jrxml").append( getText("upDown.repId")).append(":"
			    ).append( subRepCO.getIrpSubReportVO().getREPORT_ID() ).append( "\r\n");
			    errorCtr++;
			    continue;
		    }
		    else if (e.getMessage().equals("JRException"))
		    {
			  errorStr.append(getText("upDown.invalid")).append(" ").append( getText("upDown.repId")).append(" ").append(subRepCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(getText("progRef")).append(":").append(subRepCO.getPROG_REF()).append( " " ).append(getText("upDown.appName_key")).append(":").append(subRepCO.getAPP_NAME()).append( "\r\n");
			   errorCtr++;
			   continue;
		    }
		    else if (e.getMessage().equals("SAXException"))
		    {
			 errorStr.append(getText("upDown.invalid")).append(" ").append(getText("upDown.repId")).append( subRepCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(getText("progRef")).append(":").append(subRepCO.getPROG_REF()).append(getText("upDown.appName_key")).append(":").append(subRepCO.getAPP_NAME()).append("\r\n");
			    errorCtr++;
			    continue;
		    }
		    else if (e.getMessage().equals("Exception"))
		    {
			    errorStr.append(getText("upDown.unknownEx")).append(":").append( subRepCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(getText("progRef")).append(":").append(subRepCO.getPROG_REF()).append(getText("upDown.appName_key")).append(":").append(subRepCO.getAPP_NAME()).append("   : ").append(e.getMessage()).append("\r\n");
			    errorCtr++;
			    continue;
		    }
			 
		}
		
		
		
		
		
		//get the image list from all jrxml without repetition 
		//int imagesListSize=imagesList.size();
		imagesList = new ArrayList<ImageCO>();//to set empty the imagesList to get again all the image in the jrxml
		imagesList = designerBO.retImgListFromJrxmlToAllBand(jasperDesign,imagesList);
		ImageCO currImgCO;
		ImageCO foundImgCO;
		String err = null;
		for(int i=0;i<imagesList.size();i++)
		{
		    currImgCO=imagesList.get(i);
            	    foundImgCO=imageHm.get(currImgCO.getImgName());
            	    if(foundImgCO==null)
            	    {
            		imageHm.put(currImgCO.getImgName(), currImgCO);
            		boolean imgExist = designerBO.checkImgExist(currImgCO.getImgName());
            		 if(imgExist)
                  	    {
                  		errorStr.append(getText("upDown.image_key")).append(":").append(currImgCO.getImgName()).append(" ").append(getText("upDown.notFound_key")).append(" ").append(getText("upDown.repId")).append(":").append(subRepCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(getText("progRef")).append(":").append(subRepCO.getPROG_REF()).append(" ").append(getText("upDown.appName_key")).append(":").append(subRepCO.getAPP_NAME()).append("\r\n");
                  		errorCtr++;
                  		currImgCO.setErrorStatus( getText("upDown.image_key")+":");
                  	    }
            	    }
            	    else
            	    {
            		err=foundImgCO.getErrorStatus();
            		if(err!=null && !"".equals(err))
            		{
            		    errorStr.append(err+ currImgCO.getImgName()).append(" ").append(getText("upDown.notFound_key")).append(" ").append(getText("upDown.repId")).append(":").append(subRepCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(getText("progRef")).append(":").append(subRepCO.getPROG_REF()).append(" ").append(getText("upDown.appName_key")).append(":").append(subRepCO.getAPP_NAME()).append("\r\n");
            		    errorCtr++;
            		}
            		
            	    }
		}

	    }
	   

	    if(imagesList!=null)
	    {
		imagesList.clear();//to put in a clear list the imagCO without redundancy
	    }
	    
	    for(Entry<String, ImageCO> entry : imageHm.entrySet())
	    {
		ImageCO imageCO = entry.getValue();
		imagesList.add(imageCO);
	    }
	   
	    errorStr.append(getText("upDown.totalError")).append(":").append(errorCtr);
	    String zipPassword =getZipPassword();
	    if(("").equals(zipPassword)){
		zipPassword=null;
	    }
	    byte[] zipBytes=  designerBO.writeBasicExportFile(userName,imagesList,zipPassword, errorStr);
	    String zipName=RepConstantsCommon.REP_EXT + userName ;
	    if(repIdsStrLst.length==1)
	    {
		   zipName=repRefLst[0]+"_"+repAppNameLst[0];
	    }
	    response.addHeader("content-disposition", "attachment;filename=\"" + zipName +  "." + ConstantsCommon.ZIP_EXT);
	    response.setContentType("application/zip");
	    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    response.getOutputStream().write(zipBytes);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}
	catch(Exception e)
	{
	    StringBuffer msg=new StringBuffer(getText("reporting.errorOcc"));
	    if(null!=e.getMessage())
	    {
		msg.append("\n"+e.getMessage());
	    }
	    handleException(e, null, msg.toString());
	    log.error(e, "");
	}
	finally
	{
	    try
	    {
		designerBO.deleteBasicExportFile(userName);
	    }
	    catch(BaseException e)
	    {
		log.error(e,"");
	    }
	}
	return SUCCESS;
    }
  
    
    public String fullExportProcess() throws Exception
    {
	    String userName="";
	    int errorCtr=0;
	  	    
	    HashMap<String,String> translationMap = new HashMap<String,String>();
	    translationMap.put("upDown.encodingEx_key", getText("upDown.encodingEx_key"));
	    translationMap.put("upDown.appName_key", getText("upDown.appName_key"));
	    translationMap.put("upDown.fileNotFound", getText("upDown.fileNotFound"));
	    translationMap.put("upDown.repId", getText("upDown.repId"));
	    translationMap.put("upDown.invalid", getText("upDown.invalid"));
	    translationMap.put("upDown.unknownEx", getText("upDown.unknownEx"));
	    translationMap.put("upDown.image_key", getText("upDown.image_key"));
	    translationMap.put("upDown.notFound_key", getText("upDown.notFound_key"));
	    translationMap.put("upDown.totalError", getText("upDown.totalError"));
	    translationMap.put("progRef", getText("progRef"));

	try
	{

	    StringBuffer errorStr = new StringBuffer(getText("upDown.errorLog_key")+":\r\n");
	    String[] repIdsStrLst = getRepIdsStrLst().get(0).split(",");
	  //getting metadata reports if any
	    //below passed parameter to retMetaDataReports
	    ArrayList<BigDecimal> repIdList = new ArrayList<BigDecimal>();
	    //origList to replace values of repIdsStrLst
	    ArrayList<String> origList = new ArrayList<String>();
	    String val;
	    for(int i = 0; i < repIdsStrLst.length; i++)
	    {
		val=repIdsStrLst[i];
		origList.add(val);
		repIdList.add(new BigDecimal(val));
	    }
	    ArrayList<BigDecimal> metadataRepList = designerBO.retMetaDataReports(repIdList);
	    BigDecimal metadataRepId;
	    for(int i = 0; i < metadataRepList.size(); i++)
	    {
		metadataRepId = metadataRepList.get(i);
		if(!NumberUtil.isEmptyDecimal(metadataRepId))
		{
		    origList.add(metadataRepList.get(i).toString());
		}
	    }
	    repIdsStrLst = origList.toArray(new String[origList.size()]);
	    //end getting metadata reports
	    String[] repRefLst= getRepRefLst().get(0).split(",");
	    String[] repAppNameLst= getRepAppNameLst().get(0).split(",");
	    SessionCO sessionCO = returnSessionObject();
	    userName = sessionCO.getUserName();
	    String lang = sessionCO.getLanguage();
	    HashMap<BigDecimal, List<IRP_SUB_REPORTCO>> hierarchicalSubRepMap = new HashMap<BigDecimal, List<IRP_SUB_REPORTCO>>();
	    HashMap<BigDecimal, IRP_SUB_REPORTCO> exportedReportsMap = new HashMap<BigDecimal, IRP_SUB_REPORTCO>();
	    int cnt = 0;
	    String[] retExcArr = { "", "false", "" };// array of string indice 0:report status (downloadble or not),indice 1:flag,indice 2:rep and sub rep ids
	    StringBuffer strBufferIds;
	    IRP_AD_HOC_REPORTCO report;
	    IRP_SUB_REPORTCO reportCO = null;
	   
	    for(int i = 0; i < repIdsStrLst.length; i++)
	    {
		 report = null;
		 reportCO = new IRP_SUB_REPORTCO();
		 strBufferIds=new StringBuffer (retExcArr[2]);
		 strBufferIds.append(repIdsStrLst[i]+",");
		 retExcArr[2]=strBufferIds.toString();
		try
		{
		    report = designerBO.returnReportById(new BigDecimal(repIdsStrLst[i]));
		    if(report == null)
		    {
			errorStr.append(getText("upDown.followRepDel_key")).append(":").append(repIdsStrLst[i]).append(" ").append(getText("progRef")).append(":"+repRefLst[i]).append(getText("upDown.appName_key")).append(":").append(repAppNameLst[i]).append("\r\n");//id_RepApp_progRef
			errorCtr++;
			continue;
		    }
		    
		    // return subreports
		    List<IRP_SUB_REPORTCO> subrepList = designerBO.returnSubreports(report.getREPORT_ID());
		    report.setSubreportsList(subrepList);
		    reportCO.getIrpSubReportVO().setREPORT_ID(report.getREPORT_ID());
		    reportCO.setPROG_REF(report.getPROG_REF());
		    reportCO.setAPP_NAME(report.getAPP_NAME());
		    reportCO.setJRXML_FILE(report.getJRXML_FILE());
		    reportCO.setSUB_REPORT_NAME(report.getREPORT_NAME());
		    reportCO.setREPORT_VERSION(report.getREPORT_VERSION());
		    reportCO.setVERSION_DATE(report.getVERSION_DATE());
		    exportedReportsMap.put(report.getREPORT_ID(), reportCO);
		}
		catch(Exception e)
		{
		    errorStr.append(getText("upDown.unknownEx_key")).append(":").append(repIdsStrLst[i]).append(" ").append(getText("progRef")).append(":").append(repRefLst[i]).append(" ").append(getText("upDown.appName_key")).append(":").append(repAppNameLst[i]).append(" : ").append(e.getMessage()).append("\r\n");
		    errorCtr++;
		    continue;
		}
		
		//fill the sub rep map in hierarchical way
		hierarchicalSubRepMap.put(new BigDecimal(repIdsStrLst[i]), report.getSubreportsList());
		//this boucle to fill the hierarchicalSubRepMap in hierarchical way 
		for(int m = 0; m < report.getSubreportsList().size(); m++)
		{
		    strBufferIds=new StringBuffer (retExcArr[2]);
		    strBufferIds.append(report.getSubreportsList().get(m).getIrpSubReportVO().getREPORT_ID()+ ",");
		    retExcArr[2]=strBufferIds.toString();
		    retExcArr = fillSubRepDetRecursive(report.getSubreportsList().get(m), retExcArr, cnt);
		}
		//this boucle to fill the exportedReportsMap without redundancy
		for(Entry<BigDecimal, List<IRP_SUB_REPORTCO>> entry : hierarchicalSubRepMap.entrySet())
		{
		    List<IRP_SUB_REPORTCO> subrepList = entry.getValue();
		  //this boucle to fill the exportedReportsMap without redundancy
		    for(int s = 0; s < subrepList.size(); s++)
		    {
			exportedReportsMap.put(subrepList.get(s).getIrpSubReportVO().getREPORT_ID(), subrepList.get(s));
			returnSubRepMap(exportedReportsMap, subrepList.get(s).getSubRepList());
		    }
		}
	    }
	  
	    
	    // write the report content for each one in his folder
	    designerBO.writeRepContent(exportedReportsMap,userName,errorStr,errorCtr,translationMap,lang,skipTrans);
	    String [] orderedRepIds= retExcArr[2].split(",");
	    StringBuffer orderedRepIdsBuf=new StringBuffer ();
	    LinkedHashMap<BigDecimal, String> orderedMap = new LinkedHashMap<BigDecimal, String>();
	    
	    for(int c=orderedRepIds.length-1;c>=0;c--)
	    {
		//test if the exportedReportsMap contain the key
		if(exportedReportsMap.containsKey(new BigDecimal(orderedRepIds[c])))
		{
		    orderedMap.put(new BigDecimal(orderedRepIds[c]), exportedReportsMap.get(new BigDecimal(orderedRepIds[c])).getPROG_REF()+"_"+exportedReportsMap.get(new BigDecimal(orderedRepIds[c])).getAPP_NAME());
//		    orderedRepIdsBuf.append(exportedReportsMap.get(new BigDecimal(orderedRepIds[c])).getSUB_REPORT_NAME()).append("\n");
		}
	    }
	    for(Entry<BigDecimal, String> entry : orderedMap.entrySet())
	    {
		orderedRepIdsBuf.append(entry.getValue()).append("\r\n");//\r\n
	    }
	    

	    String zipPassword =getZipPassword();
	    if(("").equals(zipPassword)){
		zipPassword=null;
	    }
	    byte[] zipBytes= designerBO.writeReportExportFile(orderedRepIdsBuf,userName,zipPassword);
	    String zipName=RepConstantsCommon.REP_EXT + userName ;
	    if(repIdsStrLst.length==1)
	    {
		   zipName=repRefLst[0]+"_"+repAppNameLst[0];
	    }
	    response.addHeader("content-disposition", "attachment;filename=\"" + zipName+  "." + ConstantsCommon.ZIP_EXT);
	    response.setContentType("application/zip");
	    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    
	    response.getOutputStream().write(zipBytes);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	    
	    
	}
	catch(BaseException e)
	{
	    if(e.getMsgIdent().equals("incompatibility"))
	    {
		String detailsMsg = getText("reporting.probDbApp");
		handleException(e, null, detailsMsg);
	    }
	    log.error(e, "");
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	finally
	{
	    String fileZipLocation =RepConstantsCommon.EXPORT_REP + "/"+ RepConstantsCommon.REP_EXT + userName + "." + ConstantsCommon.ZIP_EXT;
	    String unzippedFolderLocation= RepConstantsCommon.EXPORT_REP + "/"+ RepConstantsCommon.REP_EXT + userName;
	    designerBO.deleteZipFolder(fileZipLocation,unzippedFolderLocation);
	}    
	return null;
    }
    
    public String startExportProcess() throws Exception
    {
	String result=null;
	try
	{
	    String fullBasicExp = getFullBasicExp();
	    SessionCO sessionCO = returnSessionObject();
	    String userName = sessionCO.getUserName();
	    
	    designerBO.createReportExportFile(userName, fullBasicExp);
	    
	    if(("1").equals(fullBasicExp))
	    {	
		
		try{
		    basicExportProcess();
		}
		catch(Exception e)
		{
		    result = "ERROR_ACTION";
		    StringBuffer msg =new StringBuffer(getText("reporting.errorOcc"));
		    if(null!=e.getMessage())
		    {
			msg.append("\n"+e.getMessage());
		    }
		    handleException(e, null, msg.toString());
		    log.error(e,"");
		    return result;
		}
	    }
	    else
	    {
		try
		{
		    fullExportProcess();
		}
		catch(Exception e)
		{
		    result = "ERROR_ACTION";
		    log.error(e, "");
		    StringBuffer msg = new StringBuffer(getText("reporting.errorOcc"));
		    if(null!=e.getMessage())
		    {
			msg.append("\n"+e.getMessage());
		    }
		    handleException(e, null, msg.toString());
		    return result;
		}
		
	    }

	}
	catch(Exception e)
	{
	    log.error(e,"");
	    StringBuffer msg =new StringBuffer(getText("reporting.errorOcc"));
	    if(null!=e.getMessage())
	    {
		msg.append("\n"+e.getMessage());
	    }
	    handleException(e, null,msg.toString());
	}
	return result;
    }
    public String downloadReport() throws Exception
    {
	try
	{

	    BigDecimal repId = getReportId();
	    String reportName = getUpdates();
	    reportName = reportName.replaceAll(" ", "_");

	    IRP_AD_HOC_REPORTCO report = designerBO.findReportById(repId);

	    byte[] exportBytes = report.getJRXML_FILE();
	    response.addHeader("content-disposition", "attachment;filename=\"" + reportName + ".jrxml\"");
	    response.setContentType("text/xml");
	    response.getOutputStream().write(exportBytes);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return null;
    }

    public String checkIfSkipOptAxs() throws Exception
    {
	try
	{
	    OPTVOKey optVOKey = new OPTVOKey();
	    optVOKey.setAPP_NAME(update);
	    optVOKey.setPROG_REF(updates);
	    OPTVO optVO = designerBO.checkIfSkipOptAxs(optVOKey);
	    if(optVO == null)
	    {
		setUpdate("-1");
	    }
	    else
	    {
		setUpdate("1");
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String updateReportDetails()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO currRepCO = repSessionCO.getReportCO();
	    updateRepDetFromScreen(currRepCO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public void updateRepDetFromScreen(IRP_AD_HOC_REPORTCO currRepCO)
    {
	currRepCO.setREPORT_NAME(repCO.getREPORT_NAME());
	currRepCO.setCONN_ID(repCO.getCONN_ID());
	currRepCO.setDEFAULT_FORMAT(repCO.getDEFAULT_FORMAT());
	currRepCO.setSHOW_HEAD_FOOT(repCO.getSHOW_HEAD_FOOT());
	currRepCO.setLANG_INDEPENDENT_YN(repCO.getLANG_INDEPENDENT_YN());
	currRepCO.setPARENT_REF(repCO.getPARENT_REF());
	currRepCO.setPARENT_REF_STR(repCO.getPARENT_REF_STR());
	currRepCO.setMAIL_SERVER_CODE(repCO.getMAIL_SERVER_CODE());
	currRepCO.setHOST(repCO.getHOST());
	if(ConstantsCommon.CSV_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT())
		|| ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT()))
	{
	    currRepCO.setCSV_SEPARATOR(repCO.getCSV_SEPARATOR());
	}
	else
	{
	    currRepCO.setCSV_SEPARATOR("");
	}
    }

    public String saveAsReport()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO currRepCO = repSessionCO.getReportCO();
	    String oldRepProgRef = currRepCO.getPROG_REF();
	    String oldRepAppName = currRepCO.getAPP_NAME();

	    currRepCO = designerBO.emptyRepPropsForSaveAs(currRepCO);
	    currRepCO.setOldRepAppName(oldRepAppName);
	    currRepCO.setOldRepProgRef(oldRepProgRef);
	    repSessionCO.setReportCO(currRepCO);
	    saveUploadedReport();
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String checkIfSavedReport()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    if(repSessionCO.getReportCO() == null || repSessionCO.getReportCO().getREPORT_ID() == null)
	    {
		setUpdates("0");
	    }
	    else
	    {
		setUpdates("1");
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }
    

    public String applyImportVisibility() throws JSONException
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayImport = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO overwriteTransDisplay = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(RepConstantsCommon.SKIP.equals(importOption))
	    {
	    	screenDisplayImport.setIS_VISIBLE(BigDecimal.ZERO);
	    	overwriteTransDisplay.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    else
	    {
	    	screenDisplayImport.setIS_VISIBLE(BigDecimal.ONE);
	    	overwriteTransDisplay.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    getAdditionalScreenParams().put("useExistingOptAccess", screenDisplayImport);
	    getAdditionalScreenParams().put("overwriteTrans", overwriteTransDisplay);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    
    /**
     * Method handling metadata options visiblity
     * @return
     */
    public String reportMetadataVisiblity()
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayMetaDataInfo = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(ConstantsCommon.ONE.equals(repCO.getMETADATA_REPORT_YN()) || "'1'".equals(repCO.getMETADATA_REPORT_YN()))
	    {
		screenDisplayMetaDataInfo.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayMetaDataInfo.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayMetaDataInfo.setIS_MANDATORY(BigDecimal.ONE);
		repCO.setMETADATA_REPORT_YN(ConstantsCommon.ONE);
	    }
	    else
	    {
		screenDisplayMetaDataInfo.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplayMetaDataInfo.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplayMetaDataInfo.setIS_MANDATORY(BigDecimal.ZERO);
		screenDisplayMetaDataInfo.setValue("");
	    }
	    getAdditionalScreenParams().put("lbl_metadataReport", screenDisplayMetaDataInfo);
	    getAdditionalScreenParams().put("lookuptxt_metadataReport", screenDisplayMetaDataInfo);
	    getAdditionalScreenParams().put("metadataRepName", screenDisplayMetaDataInfo);
	    getAdditionalScreenParams().put("lbl_metadataRepExt", screenDisplayMetaDataInfo);
	    getAdditionalScreenParams().put("metadataRepExt", screenDisplayMetaDataInfo);
	    getAdditionalScreenParams().put("lbl_location", screenDisplayMetaDataInfo);
	    getAdditionalScreenParams().put("location", screenDisplayMetaDataInfo);

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
}