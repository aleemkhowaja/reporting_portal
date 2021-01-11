package com.path.actions.reporting.designer;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.path.actions.ReportAction.SepartorFormat;
import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPTVOKey;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.S_APPVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.StringUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_CLIENT_REPORTCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.PTH_CLIENTSCO;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.VARIABLE_OBJECT;

public class EditorAction extends ReportingBaseAction implements ServletRequestAware, ServletResponseAware
{
    private HttpServletRequest request;
    private DesignerBO designerBO;
    private QueryBO queryBO;
    private CommonRepFuncBO commonRepFuncBO;
    private CommonReportingBO commonReportingBO;
    private IRP_AD_HOC_REPORTCO reportCO;
    private CommonLibBO commonLibBO;
    private String content;
    String xhtml;
    String jrxml;
    String webAppCtxRoot;
    String user;
    String reportName;
    String message;
    private String reportId;
    String queryName;
    private String fieldInfo;
    private VARIABLE_OBJECT variable;
    private String reference;
    private StringBuffer sqbSyntax;
    private String fieldClass;
    private String pageWidth;
    private String updates;
    ArrayList<SYS_PARAM_LOV_TRANSVO> reportFormatsList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private CommonLookupBO commonLookupBO;
    private IRP_AD_HOC_REPORTCO repCO;
    private String htmlPageRef; // page reference (- replaced by _)
    private String menu;
    private int isSyb;
    private String repAppName;
    private String repProgRef;
    private String fromWhere;
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

    
    
    
    
    
    public String getCltRepFlag()
    {
        return cltRepFlag;
    }

    public void setCltRepFlag(String cltRepFlag)
    {
        this.cltRepFlag = cltRepFlag;
    }

    public String getFromWhere()
    {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere)
    {
        this.fromWhere = fromWhere;
    }


    public String getRepProgRef()
    {
        return repProgRef;
    }

    public void setRepProgRef(String repProgRef)
    {
        this.repProgRef = repProgRef;
    }

    public String getRepAppName()
    {
        return repAppName;
    }

    public void setRepAppName(String repAppName)
    {
        this.repAppName = repAppName;
    }

    public int getIsSyb()
    {
	return isSyb;
    }

    public void setIsSyb(int isSyb)
    {
	this.isSyb = isSyb;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
	this.commonLibBO = commonLibBO;
    }

    public String getMenu()
    {
	return menu;
    }

    public void setMenu(String menu)
    {
	this.menu = menu;
    }

    public String getHtmlPageRef()
    {
	return htmlPageRef;
    }

    public void setHtmlPageRef(String htmlPageRef)
    {
	this.htmlPageRef = htmlPageRef;
    }

    public List getCsvSeparators()
    {
	ArrayList<SepartorFormat> csvSeparatorsList = new ArrayList<SepartorFormat>();
	csvSeparatorsList.add(new SepartorFormat(",", ","));
	csvSeparatorsList.add(new SepartorFormat(getText("reporting.tab"), "\\t"));
	return csvSeparatorsList;
    }

    public IRP_AD_HOC_REPORTCO getRepCO()
    {
	return repCO;
    }

    public void setRepCO(IRP_AD_HOC_REPORTCO repCO)
    {
	this.repCO = repCO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public String openReportDesigner() throws BaseException
    {

	isSyb = commonLibBO.returnIsSybase();
	return "reportDesigner";
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public StringBuffer getSqbSyntax()
    {
	return sqbSyntax;
    }

    public void setSqbSyntax(StringBuffer sqbSyntax)
    {
	this.sqbSyntax = sqbSyntax;
    }

    public String getReference()
    {
	return reference;
    }

    public void setReference(String reference)
    {
	this.reference = reference;
    }

    public String getContent()
    {
	return content;
    }

    public void setContent(String content)
    {
	this.content = content;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public String getMessage()
    {
	return message;
    }

    public String getReportId()
    {
	return reportId;
    }

    public void setReportId(String reportId)
    {
	this.reportId = reportId;
    }

    public String getQueryName()
    {
	return queryName;
    }

    public void setQueryName(String queryName)
    {
	this.queryName = queryName;
    }

    public IRP_AD_HOC_REPORTCO getReportCO()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	return repSessionCO.getReportCO();
    }

    public String loadReport()
    {
	String pageRef = get_pageRef();
	ReportingSessionCO repSessionCO = returnReportingSessionObject(pageRef);
	repSessionCO.getSubRepParamsMap().clear();// to clear the hashmap of sub
	// report param
	repSessionCO.getSubRepParamsMap1().clear();// to clear the hashmap of
	// sub report param
	repSessionCO.getLinkQryArsMap().clear();// to clear the hashmap of link
	// qry in updown
	    
	try
	{
	    reportCO = designerBO.returnReportById(new BigDecimal(reportId));
	    if(reportCO.getCltRepFlag()==null)
	    {
		PTH_CTRL1VO pthCtrl1VO = returnCommonLibBO().returnPthCtrl1();
		String cltRepFlag = pthCtrl1VO.getCLIENT_REPORT_FLAG();
		reportCO.setCltRepFlag(cltRepFlag);
	    }
	    if(ConstantsCommon.PROGREF_ROOT.equals(reportCO.getPARENT_REF()))
	    {
		reportCO.setPARENT_REF_STR(ConstantsCommon.PROGREF_ROOT);
	    }
	    repSessionCO.setLinkQryArsMap(reportCO.getLinkQryArsMap());

	    // if(("2").equals(repSessionCO.getSqlObj().getOutputLayout()))
	    // {
	    // repSessionCO.getSqlObj().setArgumentsList(reportCO.getArgumentsList());
	    // }

	    if(BigDecimal.ONE.toString().equals(reportCO.getSHOW_HEAD_FOOT()))
	    {
		reportCO.setSHOW_HEAD_FOOT("false");
	    }
	    else
	    {
		reportCO.setSHOW_HEAD_FOOT("true");
	    }
	    

	    if(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF.equals(pageRef))
	    {
		retWhenNoDataList();
		reportCO.setFromUpDown(true);
		// set audit
		// get the row from db
		repCO = designerBO.retrieveReportVO(new BigDecimal(reportId));
		repCO.setSKIP_OPT_AXS("true");
		depRepNameFromOPT();
		
		S_APPVO appVO=new S_APPVO();
		appVO.setAPP_NAME(repCO.getAPP_NAME());
		appVO=returnCommonLibBO().returnApplication(appVO);
		repCO.setAPP_IS_WEB_YN(appVO.getIS_WEB_YN());

		if(BigDecimal.ONE.toString().equals(reportCO.getREPORT_TYPE()))
		{
		    repCO.setREPORT_TYPE("true");
		}
		else
		{
		    repCO.setREPORT_TYPE("false");
		}
		applyScreenDisplayParam(appVO.getIS_WEB_YN(),repCO);
		applyRetrieveAudit(AuditConstant.UPLOAD_DOWNLOAD_KEY_REF, repCO, repCO);
	    }
	    else
	    {
		// set audit
		applyRetrieveAudit(AuditConstant.REPORTS_KEY_REF, reportCO, reportCO);
		// call the reverse engineer process to convert the report from
		// jrxml format to xhtml
		reportCO = createXHTMLFile(reportCO);
		//annasuccar- 20 jan 2014: commented out the below to unify HTML and HTM
//		if(("HTML").equals(reportCO.getDEFAULT_FORMAT()))
//		{
//		    reportCO.setDEFAULT_FORMAT("HTM");
//		}
	    }

	    // set orientation and titleRepeated
	    reportCO.setOrientation(returnOrientationFromHTML());
	    reportCO.setTitleRepeated(returnTitleRepeatedFromHTML());

	    repSessionCO.setReportCO(reportCO);
	    if(pageRef == null || !pageRef.equals(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF))
	    {
		repSessionCO.setOldReportCO(reportCO);
	    }

	    List<IRP_SUB_REPORTCO> subreportsList = repSessionCO.getReportCO().getSubreportsList();
	    HashMap<String, List<IRP_REP_ARGUMENTSCO>> subRepParamsMap1 = new HashMap<String, List<IRP_REP_ARGUMENTSCO>>();
	    for(int i = 0; i < subreportsList.size(); i++)
	    {
		subRepParamsMap1.put(subreportsList.get(i).getIrpSubReportVO().getREPORT_ID() + "_"
			+ subreportsList.get(i).getIrpSubReportVO().getSUB_REPORT_EXPRESSION(), subreportsList.get(i)
			.getSubRepArgsList());
		repSessionCO.setSubRepParamsMap1(subRepParamsMap1);

	    }
	    if(repCO!=null && StringUtil.nullToEmpty(repCO.getMENU_TITLE_ENG()).isEmpty())
	    {
		repCO.setMENU_TITLE_ENG(repCO.getREPORT_NAME());
	    }
	}
	catch(Exception e)
	{
//	    log.error(e, "Error returning the report info and queries for report id " + reportId);
//	    handleException(e, "Error returning the report details and queries",
//		    "loadReport.repDetails.exceptionMsg._key");
	    //log.error(e, "Error retrieving the report having id " + reportId + " from db: " + e.getMessage());
	    handleException(e, "Error retrieving the report from db: " + e.getMessage(),
		    e.getMessage());
	    return "error";
	}
	if(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF.equals(pageRef))
	{
	    return "successMnt";
	}
	else
	{
	    return "editorSuccess";
	}

    }
    
    
    
    /**
     * method that shows/hide the template excel file
     * 
     * @return
     * @throws Exception
     */
    public String depByRepType() throws Exception
    {
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> screenDisplay = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	if(getFromWhere()==null)
	{
	    try
	    {
		
		if(repCO.getREPORT_TYPE().equals("true"))
		{
		    screenDisplay = returnCommonLibBO().applyDynScreenDisplay("xlsName", "repCO.xlsName", ConstantsCommon.ACTION_TYPE_VISIBLE, "1", screenDisplay, null);
		}
		else
		{
		    screenDisplay = returnCommonLibBO().applyDynScreenDisplay("xlsName", "repCO.xlsName", ConstantsCommon.ACTION_TYPE_VISIBLE, "0", screenDisplay, null);
		}
		 getAdditionalScreenParams().putAll(screenDisplay);
		return "editorSuccess";
	    }
	    catch(Exception e)
	    {
		 log.error(e,e.getMessage());
		return null;
	    }
	}
	else
	{
	    if("nu".equals(getFromWhere()))
	    {
		screenDisplay = returnCommonLibBO().applyDynScreenDisplay("xlsName", "repCO.xlsName", ConstantsCommon.ACTION_TYPE_VISIBLE, "0", screenDisplay, null);
		getAdditionalScreenParams().putAll(screenDisplay);
		return "editorSuccess";
	    }
	    else
	    {
		
		ReportingSessionCO repSessionCO = returnReportingSessionObject(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF);
		reportCO = repSessionCO.getReportCO();
		if("1".equals(reportCO.getREPORT_TYPE()))
		{
		    screenDisplay = returnCommonLibBO().applyDynScreenDisplay("xlsName", "repCO.xlsName", ConstantsCommon.ACTION_TYPE_VISIBLE, "1", screenDisplay, null);
		}
		else
		{
		    screenDisplay = returnCommonLibBO().applyDynScreenDisplay("xlsName", "repCO.xlsName", ConstantsCommon.ACTION_TYPE_VISIBLE, "0", screenDisplay, null);
		}
		getAdditionalScreenParams().putAll(screenDisplay);
		return "editorSuccess";
	    }
	}
	
	
    }
      /**
     * Method that hides fields when opt exist
     * 
     * @return
     * @throws Exception
     */ 
    public String depRepNameFromOPT() throws Exception
    {
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> checkBoxReadOnly = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> reportRefReadOnly = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> appNameReadOnly = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	if( "true".equals(repCO.getSKIP_OPT_AXS()) )
	{
	     
	    if(NumberUtil.isEmptyDecimal(repCO.getREPORT_ID())) // Creating new report with existing opt 
	    {
		OPTVOKey voExists = new OPTVOKey();
		voExists.setAPP_NAME(repCO.getAPP_NAME());
		voExists.setPROG_REF(repCO.getPROG_REF());
		OPTVO  optVO = designerBO.checkIfSkipOptAxs(voExists);
		
		reportRefReadOnly = returnCommonLibBO().applyDynScreenDisplay("refId", "repCO.PROG_REF", ConstantsCommon.ACTION_TYPE_READONLY, "1", reportRefReadOnly, null);
		appNameReadOnly = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_appName", "repCO.APP_NAME", ConstantsCommon.ACTION_TYPE_READONLY, "1", appNameReadOnly, null);
		
		if(optVO == null)
		{
			updates=getText("upDown.checkOptAxsAlert");
		}
		else if(designerBO.checkIfOptInAdHocReport(optVO))
	    	{
	    	    	updates = getText("upDown.alreadyExistingRep");
	    	}
		else
		{
			//return parent opt (MENU TITLE ENGLISH )
			OPTVOKey parentVO = new OPTVOKey();
			parentVO.setAPP_NAME(optVO.getAPP_NAME());
			parentVO.setPROG_REF(optVO.getPARENT_REF());
			if(optVO.getPARENT_REF()==null)
			{
				repCO.setPARENT_REF("");
				repCO.setPARENT_REF_STR("");
			}
			else
			{
        			OPTVO voBb = designerBO.checkIfSkipOptAxs(parentVO);
        				
        			repCO.setPARENT_REF(optVO.getPARENT_REF());
        			if(voBb != null)
        			{
        				repCO.setPARENT_REF_STR(voBb.getMENU_TITLE_ENG());
        			}
			}
			repCO.setREPORT_NAME(optVO.getPROG_DESC());
		}
	    }
	    else //Already Uploaded report 
	    {
		checkBoxReadOnly = returnCommonLibBO().applyDynScreenDisplay("skipOptAxs", "repCO.SKIP_OPT_AXS", ConstantsCommon.ACTION_TYPE_READONLY, "1", checkBoxReadOnly, null);
		getAdditionalScreenParams().putAll(checkBoxReadOnly);
	    }
	}
	else
	{
	    reportRefReadOnly = returnCommonLibBO().applyDynScreenDisplay("refId", "repCO.PROG_REF", ConstantsCommon.ACTION_TYPE_READONLY, "0", reportRefReadOnly, null);	
	    appNameReadOnly = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_appName", "repCO.APP_NAME", ConstantsCommon.ACTION_TYPE_READONLY, "0", appNameReadOnly, null);
	}
	getAdditionalScreenParams().putAll(reportRefReadOnly);
	getAdditionalScreenParams().putAll(appNameReadOnly);

	return "editorSuccess";
    }
     /**
     * Method that hide Checkbox if the reference and app_name fields are not filled 
     * 
     * @return
     * @throws Exception
     */ 
    public String depCheckBoxVisiblity() throws Exception
    {
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> checkBoxReadOnly = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	if(("").equals(StringUtil.nullToEmpty(repCO.getAPP_NAME()))  || ("").equals(StringUtil.nullToEmpty(repCO.getPROG_REF())))
	{
	    checkBoxReadOnly = returnCommonLibBO().applyDynScreenDisplay("skipOptAxs", "repCO.SKIP_OPT_AXS", ConstantsCommon.ACTION_TYPE_READONLY, "1", checkBoxReadOnly, null);	   
	}
	else
	{
	    checkBoxReadOnly = returnCommonLibBO().applyDynScreenDisplay("skipOptAxs", "repCO.SKIP_OPT_AXS", ConstantsCommon.ACTION_TYPE_READONLY, "0", checkBoxReadOnly, null);
	}
	
	if(!StringUtil.nullToEmpty(repCO.getAPP_NAME()).isEmpty())
	{
		S_APPVO appVO=new S_APPVO();
		appVO.setAPP_NAME(repCO.getAPP_NAME());
		appVO=returnCommonLibBO().returnApplication(appVO);
		repCO.setAPP_IS_WEB_YN(appVO.getIS_WEB_YN());
	}
	

	getAdditionalScreenParams().putAll(checkBoxReadOnly);
	return "editorSuccess";
    }
    

    public Element returnRootFromXML() throws UnsupportedEncodingException, DocumentException
    {
	String xml = new String(reportCO.getXHTML_FILE(), CommonUtil.ENCODING);
	Document document = new SAXReader().read(new StringReader(xml));
	return document.getRootElement();
    }

    public String getXHTML() throws UnsupportedEncodingException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    reportCO = repSessionCO.getReportCO();
	    if(reportCO != null && reportCO.getXHTML_FILE() != null)
	    {
		Element root = returnRootFromXML();
		List<Element> elements = root.elements();
		Element table = null;
		for(Element element : elements)
		{
		    if(element.getName().toUpperCase().equals("TABLE"))
		    {
			table = element;
			break;
		    }
		}
		if(table == null)
		{
		    return "";		    
		}
		else
		{
		    return table.asXML();
		}
	    }

	}
	catch(DocumentException e)
	{
	    //log.error(e, "XHTML_ERROR");
	    handleException(null, "", "reporting.xhtmlError");
	    return "error";
	}
	return null;
    }

    public int returnOrientationFromHTML()
    {
	try
	{
	    if(reportCO.getXHTML_FILE() != null)
	    {
		Element root = returnRootFromXML();
		Attribute attribute = root.attribute("orientation");
		if(attribute != null && attribute.getValue().equals("Landscape"))
		{
		    return 1;
		}
	    }
	}
	catch(DocumentException e)
	{
	    log.error(e, "");
	}
	catch(UnsupportedEncodingException e)
	{
	    log.error(e, "");
	}
	return 2;
    }

    public boolean returnTitleRepeatedFromHTML()
    {
	try
	{
	    if(reportCO.getXHTML_FILE() != null)
	    {
		Element root = returnRootFromXML();
		Attribute attribute = root.attribute("titleRepeated");
		if(attribute != null && attribute.getValue().equals("true"))
		{
		    return true;
		}
	    }

	}
	catch(DocumentException e)
	{
	    log.error(e, "");
	}
	catch(UnsupportedEncodingException e)
	{
	    log.error(e, "");
	}
	return false;
    }

    public String preview()
    {
	try
	{
	    createJasperReportFile();
	    String fileName=reportCO.getPROG_REF() + "_" + reportCO.getAPP_NAME() + "_" + user.replace(".", "");
	    designerBO.writeFileToRepository(fileName,jrxml,ConstantsCommon.JRXML_EXT);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error creating jrxml file.");
	    handleException(e, "Error creating jasperReport file to preview the report",
		    "previewReport.createJRfile.exceptionMsg._key");
	}
	return "editorSuccess";
    }

    public String save()
    {
	IRP_AD_HOC_REPORTVOWithBLOBs vo = new IRP_AD_HOC_REPORTVOWithBLOBs();
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    createJasperReportFile();

	    vo.setREPORT_NAME(reportName);
	    vo.setXHTML_FILE(xhtml.getBytes(CommonUtil.ENCODING));
	    vo.setJRXML_FILE(jrxml.getBytes(CommonUtil.ENCODING));
	    vo.setDEFAULT_FORMAT(reportCO.getDEFAULT_FORMAT());
	    vo.setCONN_ID(reportCO.getCONN_ID());
	    vo.setMODIFIED_BY(user);
	    vo.setDATE_MODIFIED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
	    vo.setAPP_NAME(reportCO.getAPP_NAME());
	    vo.setWHEN_NO_DATA(reportCO.getWHEN_NO_DATA());
	    vo.setMAIL_SERVER_CODE(reportCO.getMAIL_SERVER_CODE());
	    PTH_CTRL1VO pthCtrl1VO = commonLibBO.returnPthCtrl1();
	    cltRepFlag = pthCtrl1VO.getCLIENT_REPORT_FLAG();
	    reportCO.setCltRepFlag(cltRepFlag);
	    try
	    {
		if("0".equals(cltRepFlag) && reportCO.getRepClientList().isEmpty())
		{
		    throw new BOException(getText("upDown.empRepClt"));
		}
		else if ("1".equals(cltRepFlag) && reportCO.getRepClientList().isEmpty())
		{ try
		   {
		    PTH_CLIENTSCO defaultClient =designerBO.returnDefaultClient();
		    IRP_CLIENT_REPORTCO clientReportCO = new IRP_CLIENT_REPORTCO();
		    clientReportCO.getIrpClientReportVO().setREPORT_REF(reportCO.getPROG_REF());
		    clientReportCO.getIrpClientReportVO().setAPP_NAME(reportCO.getAPP_NAME());
		    clientReportCO.getIrpClientReportVO().setCLIENT_ACRONYM(defaultClient.getPthClientsVO().getCLIENT_ACRONYM());
		    clientReportCO.setCLIENT_NAME(defaultClient.getPthClientsVO().getCLIENT_NAME());
		  
		    
		    List<IRP_CLIENT_REPORTCO> clientRep= new ArrayList<IRP_CLIENT_REPORTCO>();
		    clientRep.add(clientReportCO);
		    reportCO.setRepClientList(clientRep);
		   }
		 catch(Exception e){
		     throw new BaseException(getText("reporting.exceptionDefaultClient"),e);
			}
		}
	    }
	    catch(Exception e)
	    {
		handleException(e, null, null);
		//return "editorSuccess"; 
		//return "error";
		return "error";
	    }
	    
	    
	    if("true".equals(reportCO.getSHOW_HEAD_FOOT()))
	    {
		vo.setSHOW_HEAD_FOOT("0");
	    }
	    else
	    {
		vo.setSHOW_HEAD_FOOT("1");
	    }
	    
	    vo.setLANG_INDEPENDENT_YN(reportCO.getLANG_INDEPENDENT_YN());	    
	    if(ConstantsCommon.CSV_REP_FORMAT.equals(reportCO.getDEFAULT_FORMAT()))
	    {
		vo.setCSV_SEPARATOR(reportCO.getCSV_SEPARATOR());
	    }
	    else
	    {
		vo.setCSV_SEPARATOR("");
	    }
	    BigDecimal id = null;
	    boolean updateRep = false;
	    AuditRefCO refCO = initAuditRefCO();
	    if(reportCO.getREPORT_ID() == null)
	    {
		vo.setPROG_REF(reportCO.getPROG_REF());
		vo.setCREATED_BY(user);
		vo.setDATE_CREATED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
		// return the next report id
		id = commonRepFuncBO.retCounterValue("IRP_AD_HOC_REPORT");
		vo.setREPORT_ID(id);
		refCO.setOperationType(AuditConstant.CREATED);
		refCO.setKeyRef(AuditConstant.REPORTS_KEY_REF);
	    }
	    else
	    {
		vo.setREPORT_ID(reportCO.getREPORT_ID());
		updateRep = true;
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.REPORTS_KEY_REF);
	    }

	    for(IRP_AD_HOC_QUERYCO query : reportCO.getQueriesList())
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

	    reportCO.setAuditRefCO(refCO);

	    reportCO = designerBO.save(vo, reportCO, updateRep, sessionCO.getCompanyCode(), sessionCO.getBranchCode(),
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
	    log.error(e, "");
	}
	catch(IOException e)
	{
	    //log.error(e, "Error serializing sql object into bytes.");
	    handleException(e, "Error serializing sql object into bytes", "saveReport.serialization.exceptionMsg._key");
	    //log.error(e, "");
	    message = getText("saveQuery_error_key");
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

	if((message == null)
		|| (message != null && !message.equals(getText("saveReport_error_key"))
			 && !message.contains("2099")))
	{
	    message = getText("saveReport_success_key");
	    reportCO.setREPORT_ID(vo.getREPORT_ID());
	    reportCO.setArgsDBUpdate(new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>());
	    reportCO.setArgsDbDelete(new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>());
	}

	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setReportCO(reportCO);
	return "editorSuccess";
    }

    public void createJasperReportFile()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    reportCO = repSessionCO.getReportCO();
	    reportName = reportCO.getREPORT_NAME();
	    String orient = "Portrait";
	    String pageHeight = "842";
	    String columnWidth = "555";

	    if(reportCO.getOrientation() == 1)
	    {
		orient = "Landscape";
		pageHeight = "595";
		columnWidth = "802";
	    }

	    StringBuffer startStr = new StringBuffer();
	    startStr.append("<report name=\"" + reportName + "\" " + "orientation=\"" + orient + "\" "
		    + "titleRepeated=\"" + reportCO.getTitleRepeated() + "\" " + "pageWidth=\"" + pageWidth + "\" "
		    + "pageHeight=\"" + pageHeight + "\" " + "columnWidth=\"" + columnWidth + "\" " + ">");
	    StringBuffer groupStr  = new StringBuffer();
	    StringBuffer paramStr  = new StringBuffer();
	    StringBuffer dataSetParam  = new StringBuffer();

	    Collection<IRP_REP_ARGUMENTSCO> params = reportCO.getArgumentsList().values();
	    for(IRP_REP_ARGUMENTSCO param : params)
	    {
		if(BigDecimal.ONE.equals(param.getMULTISELECT_YN()))
		{
		    startStr.append(" <parameter name=\"" + param.getARGUMENT_NAME()
			    + "\" class=\"java.util.Collection\" nestedType=\"" + param.getJrxmlType() + "\"> "
			    + "<parameterDescription><![CDATA[]]></parameterDescription>"
			    + " <defaultValueExpression><![CDATA[]]></defaultValueExpression> " + " </parameter> ");
		}
		else
		{
		    startStr.append(" <parameter name=\"" + param.getARGUMENT_NAME() + "\" class=\"" + param.getJrxmlType()
			+ "\"> " + " <defaultValueExpression><![CDATA[" + param.getJrxmlValue()
			+ "]]></defaultValueExpression> " + " </parameter> ");
		}
	    }

	    // if(startStr.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")==-1)
	    List<IRP_SUB_REPORTCO> subreportsList = repSessionCO.getReportCO().getSubreportsList();
	    if(!subreportsList.isEmpty() && (startStr.indexOf("<parameter name=\"SUBREPORT_DIR") == -1))
            {
		    startStr.append(" <parameter name=\"SUBREPORT_DIR\" class=\"java.lang.String" + "\">  "
			    + " </parameter> ");

	    }

	    // HashMap<String, List<IRP_SUB_REPORTCO>>
	    // argsSubRepMap=repSessionCO.getSubRepParamsMap();
	    HashMap<String, List<IRP_REP_ARGUMENTSCO>> argsSubRepMap = repSessionCO.getSubRepParamsMap1();

	    for(int i = 0; i < subreportsList.size(); i++)
	    {
		String subRepGridIds = subreportsList.get(i).getIrpSubReportVO().getREPORT_ID() + "_"
			+ subreportsList.get(i).getIrpSubReportVO().getSUB_REPORT_EXPRESSION();

		startStr.append(" <subreport expression=\""
			+ subreportsList.get(i).getIrpSubReportVO().getSUB_REPORT_EXPRESSION() + "\">");

		for(Entry<String, List<IRP_REP_ARGUMENTSCO>> entry : argsSubRepMap.entrySet())
		{
		    String key = entry.getKey();
		    if(key.equals(subRepGridIds))
		    {
			ArrayList<IRP_REP_ARGUMENTSCO> argsList = (ArrayList<IRP_REP_ARGUMENTSCO>) argsSubRepMap
				.get(subRepGridIds);
			ArrayList<IRP_REP_ARGUMENTSCO> argsMapList = new ArrayList<IRP_REP_ARGUMENTSCO>();
			argsMapList = new ArrayList(argsList);
			argsMapList.size();
			for(int k = 0; k < argsMapList.size(); k++)
			{
			    argsMapList.get(k).getARGUMENT_NAME();
			    startStr.append(" <subreportParameter name=\"" + argsMapList.get(k).getARGUMENT_NAME() + "\">");
			    if(argsMapList.get(k).getMAP_PARAM_TYPE().equals("1"))// 1==F
			    {
				startStr.append(" <subreportParameterExpression><![CDATA[$F{"
					+ argsMapList.get(k).getVALUE_COLUMN() + "}]]>");
			    }
			    else if(argsMapList.get(k).getMAP_PARAM_TYPE().equals("2"))// 2==P
			    {
				startStr.append(" <subreportParameterExpression><![CDATA[$P{"
					+ argsMapList.get(k).getVALUE_ARGUMENT() + "}]]>");
			    }
			    else
			    {
				//startStr.append(" <subreportParameterExpression><![CDATA[${"+ argsMapList.get(k).getVALUE_STATIC() + "}]]>");
				if(argsMapList.get(k).getARGUMENT_TYPE().equals("NUMBER"))
				{
				    startStr.append(" <subreportParameterExpression><![CDATA[new BigDecimal("+ argsMapList.get(k).getVALUE_STATIC() + ")]]>"); 
				}
				else //(argsMapList.get(k).getARGUMENT_TYPE().equals("VARCHAR2"))
				{
				    startStr.append(" <subreportParameterExpression><![CDATA[new String("+'"'+ argsMapList.get(k).getVALUE_STATIC() +'"'+ ")]]>");
				}
			    }

			    startStr.append(" </subreportParameterExpression> </subreportParameter>");

			}

		    }

		}
		startStr.append(" </subreport>");
	    }

	    for(IRP_AD_HOC_QUERYCO queryco : reportCO.getQueriesList())
	    {
		SQL_OBJECT sqlObj = queryco.getSqlObject();

		if(sqlObj.getOutputLayout().equals(ConstantsCommon.OUTPUT_LAYOUT_TABULAR))
		{ // tabular
		    Collection<IRP_REP_ARGUMENTSCO> arguments = sqlObj.getArgumentsList().values();
		    if(arguments != null && !arguments.isEmpty())
		    {
			for(IRP_REP_ARGUMENTSCO arg : arguments)
			{
			    if(arg.getLINK_REP_QRY_ARG() == null || arg.getLINK_REP_QRY_ARG().equals(""))// added
			    // by
			    // h.khoury
			    // to
			    // set
			    // the
			    // arguments
			    // of
			    // dataset.
			    {
				if(BigDecimal.ONE.equals(arg.getMULTISELECT_YN()))
				{
				    dataSetParam.append(" <datasetParameter name=\"" + arg.getARGUMENT_NAME() + "\"> "
					    + " <datasetParameterExpression><![CDATA[$P{"+arg.getARGUMENT_NAME()+"}]]></datasetParameterExpression> " + " </datasetParameter> ");
				}
				else
				{
				    dataSetParam.append(" <datasetParameter name=\"" + arg.getARGUMENT_NAME() + "\"> "
					    + " <datasetParameterExpression><![CDATA[" + arg.getARGUMENT_VALUE()
					    + "]]></datasetParameterExpression> " + " </datasetParameter> ");
				}
			    }
			 
			    else
			    {
				if(BigDecimal.ONE.equals(arg.getMULTISELECT_YN()))
				{
				    dataSetParam.append(" <datasetParameter name=\"" + arg.getARGUMENT_NAME() + "\"> "
					    + " <datasetParameterExpression><![CDATA[$P{"+arg.getARGUMENT_NAME()+"}]]></datasetParameterExpression> "
					    + " </datasetParameter> ");
				}
				else
				{
				    dataSetParam.append(" <datasetParameter name=\"" + arg.getARGUMENT_NAME() + "\"> "
					    + " <datasetParameterExpression><![CDATA[$P{" + arg.getLINK_REP_QRY_ARG()
					    + "}]]></datasetParameterExpression> " + " </datasetParameter> ");
				}
			    } 
			    if(BigDecimal.ONE.equals(arg.getMULTISELECT_YN()))
			    {
				paramStr.append(" <parameter name=\"" + arg.getARGUMENT_NAME()
					    + "\" class=\"java.util.Collection\" nestedType=\"" + arg.getJrxmlType() + "\"> "
					    + "<parameterDescription><![CDATA[]]></parameterDescription>"
					    + " <defaultValueExpression><![CDATA[]]></defaultValueExpression> " + " </parameter> ");
			    }
			    else
			    {
				paramStr.append(" <parameter name=\"" + arg.getARGUMENT_NAME() + "\" class=\""
					+ arg.getJrxmlType() + "\"> " + " <defaultValueExpression><![CDATA["
					+ arg.getJrxmlValue() + "]]></defaultValueExpression> " + " </parameter> ");
			    }
			}
		    }
		    startStr.append(" <subDataset name=\"" + queryco.getQUERY_NAME() + "\">");
		    startStr.append(paramStr);
		}
		//				
		startStr.append(" <queryString> <![CDATA[" + queryco.getSqlSyntax() + "]]></queryString> ");

		ArrayList<IRP_FIELDSCO> fields = sqlObj.getAllFields();
		LinkedHashMap<Long, IRP_FIELDSCO> groupByHM = sqlObj.getGroupByHM();

		groupStr.append("");
		for(IRP_FIELDSCO field : fields)
		{
		    startStr.append("	<field name=\"" + field.getLabelAlias().replaceAll(" ", "") + "\" class=\""
			    + field.getFIELD_DATA_TYPE() + "\"><fieldDescription><![CDATA[" + field.getFIELD_ALIAS()
			    + "]]></fieldDescription></field>");
		    if(groupByHM.containsKey(field.getIndex()))
		    {
			groupStr.append("<group name=\"" + field.getGroupName() + "\">" + "<groupExpression><![CDATA[$F{"
				+ field.getLabelAlias() + "}]]></groupExpression>" + "</group>");
		    }
		}

		startStr.append(groupStr);

		List<VARIABLE_OBJECT> variableList = queryco.getVariablesList();
		if(!variableList.isEmpty())
		{
		    for(VARIABLE_OBJECT variable : variableList)
		    {
			startStr.append(" <variable name=\"" + variable.getVarName() + "\" class=\""
				+ variable.getVarClass() + "\" calculation=\"" + variable.getCalculation() + "\"> "
				+ " <variableExpression><![CDATA[$F{" + variable.getExpression()
				+ "}]]></variableExpression> " + " </variable> ");
		    }
		}
		if(sqlObj.getOutputLayout().equals(ConstantsCommon.OUTPUT_LAYOUT_TABULAR)) // tabular
		{
		    startStr.append(dataSetParam);
		    startStr.append(" </subDataset>");
		}
	    }

	    content = content.replaceAll("&nbsp;", "&#160;");

	    // annasuccar- Firefox is breaking background-image:url() with
	    // &quot;
	    // which is causing the html to be completely garbled
	    // workaround to remove the double quotation until the fix is
	    // released by CKeditor
	    content = content.replaceAll("background: url\\(&quot;", "background: url(");
	    content = content.replaceAll(".JPG&quot;", ".JPG");
	    content = content.replaceAll("<!--", "");
	    content = content.replaceAll("-->", "");
	    String contentStr = content;
	    String endStr = "</report>";

	    xhtml = startStr.toString() + contentStr + endStr;

	    SessionCO sessionCO = returnSessionObject();
	    user = sessionCO.getUserName();
	    String xslFileName ="classpath:com/path/bo/reporting/common/jasperRules/JrxmlFile.xsl";

	    jrxml = designerBO.generateJrxml(xhtml.replaceAll("&#160;", " "), xslFileName);

	    log.debug("xhtml:: " + xhtml);
	    log.debug("jrxml:: " + jrxml);

	}
	catch(BaseException e1)
	{
	    log.error(e1, "Error in creating jrxml file.");
	}
    }

    public IRP_AD_HOC_REPORTCO createXHTMLFile(IRP_AD_HOC_REPORTCO reportCO) throws Exception
    {
	String xslFileName ="classpath:com/path/bo/reporting/common/jasperRules/XhtmlFile.xsl";
	xhtml = designerBO.generateXhtml(reportCO.getJRXML_FILE(), xslFileName);
	reportCO.setXHTML_FILE(xhtml.getBytes(CommonUtil.ENCODING));
	return reportCO;
    }

    public void deleteHtmlFile()
    {

	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    user = sessionCO.getUserName();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_AD_HOC_REPORTCO repCO = repSessionCO.getReportCO();
	    reportName = repSessionCO.getReportCO().getREPORT_NAME();
	    commonReportingBO.deleteTempFiles(repCO.getPROG_REF() + "_" + repCO.getAPP_NAME() + "_" + user);

	    // empty previewOrderLst
	    repSessionCO.getReportCO().setPrevOrderList(new ArrayList<IRP_FIELDSCO>());
	    repSessionCO.getReportCO().setPrevFilterMap(new LinkedHashMap<Long, CONDITION_OBJECT>());
	    repSessionCO.getReportCO().setPrevGrpMap(new LinkedHashMap<Long, IRP_FIELDSCO>());
	}
	catch(Exception e)
	{
	    log.error(e, "Error deleting the JasperReport files");
	}
    }

    public String closeRepDesigner()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setReportCO(null);
	return null;
    }

    public String openQueryDesigner() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	List<IRP_AD_HOC_QUERYCO> queries = reportCO.getQueriesList();

	if(getUpdates() != null && getUpdates().equals("true"))
	{
	    queryName = repSessionCO.getMenuQryName();
	    repSessionCO.setMenuQryName(null);
	}

	for(IRP_AD_HOC_QUERYCO query : queries)
	{
		//the below is removed since the report only has one query
//	    if(query.getQUERY_NAME().equals(queryName))
//	    {
		SQL_OBJECT sqlObj = (SQL_OBJECT) CommonUtil.getCopyObject(query.getSqlObject());
		repSessionCO.setSqlObj(sqlObj);
		SQL_OBJECT oldSqlObj = (SQL_OBJECT) CommonUtil.getCopyObject(query.getSqlObject());
		repSessionCO.setOldSqlObj(oldSqlObj);
		break;
//	    }
	}

	// //check if sqb then open the qryEditor else open the queryDesigner
	if(repSessionCO.getSqlObj().getEntities().size() == 0)
	{
	    request.setAttribute("openSqb", "true");
	}
	else
	{
	    request.setAttribute("openSqb", null);
	}
	// else
	// {
	return "queryDesigner";
	// }
    }

    public String updateQuery() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	    for(IRP_AD_HOC_QUERYCO query : reportCO.getQueriesList())
	    {
		if(query.getQUERY_NAME().equals(queryName))
		{
		    query.setSqlObject(sqlObj);
		    if(sqlObj != null
			    && (sqlObj.getSqbSyntax() == null || ("").equals(sqlObj.getSqbSyntax().toString())))
		    {
			query.setQUERY_NAME(sqlObj.getQUERY_NAME());
			StringBuffer sql = queryBO.generateSql(sqlObj);
			query.setSqlSyntax(sql);
		    }
		    break;
		}
	    }
	    repSessionCO.setSqlObj(null);
	}
	catch(Exception e)
	{
	    //log.error("Error updating the query.");
	    handleException(e, "Error updating the query", "updateQuery.exceptionMsg._key");
	}
	return "editorSuccess";
    }

    public String chooseValueType()
    {
	return "valueTypeSelection";
    }

    public List<Type> getValueTypes()
    {
	List<Type> list = new ArrayList<Type>();
	list.add(new Type("0", "The field value"));
	list.add(new Type("1", "The result of an aggregation function"));
	return list;
    }

    public List<CalcType> getCalculationTypes()
    {
	List<CalcType> list = new ArrayList<CalcType>();
	list.add(new CalcType("Count", "java.math.BigDecimal"));
	if(("java.math.BigDecimal").equals(this.getFieldClass()))
	{
	    list.add(new CalcType("Sum", "java.math.BigDecimal"));
	    list.add(new CalcType("Average", "java.math.BigDecimal"));
	    list.add(new CalcType("Lowest", "java.math.BigDecimal"));
	    list.add(new CalcType("Highest", "java.math.BigDecimal"));
	}
	else
	{
	    list.add(new CalcType("Lowest", "java.lang.String"));
	    list.add(new CalcType("Highest", "java.lang.String"));
	}

	return list;
    }

    public static class Type
    {
	public Type(String code, String description)
	{
	    this.code = code;
	    this.description = description;
	}

	private String code;
	private String description;

	public String getCode()
	{
	    return code;
	}

	public void setCode(String code)
	{
	    this.code = code;
	}

	public String getDescription()
	{
	    return description;
	}

	public void setDescription(String description)
	{
	    this.description = description;
	}
    }

    public static class CalcType
    {
	public CalcType(String description, String value)
	{
	    this.description = description;
	    this.value = value;
	}

	private String description;
	private String value;

	public String getDescription()
	{
	    return description;
	}

	public void setDescription(String description)
	{
	    this.description = description;
	}

	public String getValue()
	{
	    return value;
	}

	public void setValue(String value)
	{
	    this.value = value;
	}
    }

    public String createVariable()
    {
	JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(fieldInfo);
	JSONObject jsonModel = jsonFilter.getJSONObject("obj");
	variable = (VARIABLE_OBJECT) JSONObject.toBean(jsonModel, VARIABLE_OBJECT.class);
	IRP_AD_HOC_QUERYCO queryCO = this.getReportCO().getQueriesList().get(variable.getQryIndex());
	int num = queryCO.getVariablesList().size() + 1;
	variable.setVarName(variable.getExpression() + "_" + num);
	variable.setVarClass(this.getFieldClass());
	queryCO.getVariablesList().add(variable);
	return "editorSuccess";
    }

    public String submitRepProperties()
    {
	IRP_AD_HOC_REPORTCO reprotCO = getReportCO();
	reprotCO.setOrientation(repCO.getOrientation());
	reprotCO.setTitleRepeated(repCO.getTitleRepeated());
	reprotCO.setDEFAULT_FORMAT(repCO.getDEFAULT_FORMAT());
	reprotCO.setCONN_ID(repCO.getCONN_ID());
	reprotCO.setSHOW_HEAD_FOOT(repCO.getSHOW_HEAD_FOOT());
	reprotCO.setLANG_INDEPENDENT_YN(repCO.getLANG_INDEPENDENT_YN());
	reprotCO.setWHEN_NO_DATA(repCO.getWHEN_NO_DATA());
	reprotCO.setMAIL_SERVER_CODE(repCO.getMAIL_SERVER_CODE());
	reprotCO.setHOST(repCO.getHOST());
	String csvSep=repCO.getCSV_SEPARATOR();
	if("\t".equals(csvSep))
	{
	    csvSep = "\\t";
	}
	reprotCO.setCSV_SEPARATOR(csvSep);
	return "editorSuccess";
    }

    public String getFieldInfo()
    {
	return fieldInfo;
    }

    public void setFieldInfo(String fieldInfo)
    {
	this.fieldInfo = fieldInfo;
    }

    public VARIABLE_OBJECT getVariable()
    {
	return variable;
    }

    public void setVariable(VARIABLE_OBJECT variable)
    {
	this.variable = variable;
    }

    public String openProperties()
    {
	try
	{
	    retWhenNoDataList();
	    repCO=getReportCO();
	    PTH_CTRL1VO pthCtrl1VO = commonLibBO.returnPthCtrl1();
	    cltRepFlag = pthCtrl1VO.getCLIENT_REPORT_FLAG();
	}
	catch(BaseException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return "properties";
    }

    public String getFieldClass()
    {
	return fieldClass;
    }

    public void setFieldClass(String fieldClass)
    {
	this.fieldClass = fieldClass;
    }

    public String getPageWidth()
    {
	return pageWidth;
    }

    public void setPageWidth(String pageWidth)
    {
	this.pageWidth = pageWidth;
    }

    @Override
    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public String clearQrySession() throws JSONException
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setSqlObj(null);
	return "editorSuccess";
    }

    public List<SYS_PARAM_LOV_TRANSVO> getOrientationsList()
    {
	List<SYS_PARAM_LOV_TRANSVO> orientations = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("2");
	sysVO.setVALUE_DESC(getText("orientation.portrait_key"));
	orientations.add(sysVO);
	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("1");
	sysVO.setVALUE_DESC(getText("orientation.landscape_key"));
	orientations.add(sysVO);
	return orientations;
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
    
    
    public List retWhenNoDataList() throws BaseException
    {
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.WHEN_NO_DATA_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	whenNoDataList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	String outputLayout ;
	if("RD00R".equals(get_pageRef()))
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    outputLayout = repSessionCO.getReportCO().getQueriesList().get(0).getSqlObject().getOutputLayout();
	    
	}
	else
	{
	    outputLayout = reportCO.getQueriesList().get(0).getSqlObject().getOutputLayout();
	}
	if(ConstantsCommon.OUTPUT_LAYOUT_TABULAR.equals(outputLayout))
	{
	  //only show the common tpyes between tabular and freeform
	    hideTabWhenNoDataTypes();
	}
	return whenNoDataList;
    }

    public void hideTabWhenNoDataTypes()
    {
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
	    log.error("Error returning the list of db connections.");
	}
	return null;
    }


    public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
	this.commonReportingBO = commonReportingBO;
    }

    public String openDynPreview() //throws Exception
    {
	try
	{
	    set_showSmartInfoBtn("false");
	    htmlPageRef = get_pageRef().replace("-", "_");
	    menu = get_pageRef();
	    PTH_CTRL1VO pthCtrl1VO;
	    pthCtrl1VO = commonLibBO.returnPthCtrl1();
	    cltRepFlag = pthCtrl1VO.getCLIENT_REPORT_FLAG();
	    return "successDynPrev";
	}
	catch(Exception e)
	{
	    handleException(e, "Error opening the preview screen: " + e.getMessage(),
		    e.getMessage());
	    return null;
	}
    }

    public String checkIfSavedReport()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    if(repSessionCO.getReportCO() == null || repSessionCO.getReportCO().getREPORT_ID() == null)
	    {
		setUpdates("0");
	    }
	    else
	    {
		setUpdates("1");
	    }
	    setRepAppName(repSessionCO.getReportCO().getAPP_NAME());
	    setRepProgRef(repSessionCO.getReportCO().getPROG_REF());
	    
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "editorSuccess";
    }

    public String fillSaveAsDetails()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_AD_HOC_REPORTCO currRepCO = repSessionCO.getReportCO();
	    currRepCO.setREPORT_NAME(repCO.getREPORT_NAME());
	    currRepCO.setAPP_NAME(repCO.getAPP_NAME());
	    currRepCO.setPROG_REF(repCO.getPROG_REF());
	    currRepCO.setPARENT_REF(repCO.getPARENT_REF());
	    currRepCO.setPARENT_REF_STR(repCO.getPARENT_REF_STR());
	    currRepCO.setLANG_INDEPENDENT_YN(repCO.getLANG_INDEPENDENT_YN());
	    currRepCO = designerBO.emptyRepPropsForSaveAs(currRepCO);
	    repSessionCO.setReportCO(currRepCO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "editorSuccess";
    }
    
    public String loadEditor()
    {
	return "editor";
    }
    

    /**
     * set mandatory fields at the level of the upload download screen
     * 
     * @return
     */
    
    public void applyScreenDisplayParam(BigDecimal isWebYn,IRP_AD_HOC_REPORTCO repco) 
    {
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
	
	SYS_PARAM_SCREEN_DISPLAYVO upload = new SYS_PARAM_SCREEN_DISPLAYVO();
	upload.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("upload", upload);
	
	
	SYS_PARAM_SCREEN_DISPLAYVO categ = new SYS_PARAM_SCREEN_DISPLAYVO();
	if(new BigDecimal(2).equals(isWebYn) && RepConstantsCommon.ROOT.equals(repco.getPARENT_REF()))
	{
		categ.setIS_MANDATORY(BigDecimal.ONE);
		categ.setIS_VISIBLE(BigDecimal.ONE);
	}
	else
	{
		categ.setIS_MANDATORY(BigDecimal.ZERO);
		categ.setIS_VISIBLE(BigDecimal.ZERO);
	}
	getAdditionalScreenParams().put("lookuptxt_CATEGORY_ID", categ);
	getAdditionalScreenParams().put("CATEGORY_DESC", categ);

    }
}
