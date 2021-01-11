package com.path.actions.reporting.designer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.SQL_OBJECT;

public class WizardAction extends ReportingBaseAction
{

    /**
	 * 
	 */

    // private static final long serialVersionUID = -5052618609590001474L;
    HttpServletRequest request = ServletActionContext.getRequest();
    private IRP_AD_HOC_QUERYCO queryCO = new IRP_AD_HOC_QUERYCO();
    private String reportName;
    private String reference;
    private String PARENT_REF;
    private String PARENT_REF_STR;
    private String reportLayout;
    private boolean repNameExists;
    private boolean refExists;
    private boolean missingQry;
    private String syntax;
    private DesignerBO designerBO;
    private QueryBO queryBO;
    private String queryId;
    IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
    private String tableHTML;
    private String openSqb;
    private String applicationName;
    private String update;

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public String getApplicationName()
    {
	return applicationName;
    }

    public void setApplicationName(String applicationName)
    {
	this.applicationName = applicationName;
    }

    public String getOpenSqb()
    {
	return openSqb;
    }

    public void setOpenSqb(String openSqb)
    {
	this.openSqb = openSqb;
    }

    public String execute() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setReportCO(new IRP_AD_HOC_REPORTCO());
	repSessionCO.setSqlObj(new SQL_OBJECT());
	return SUCCESS;
    }

    public String checkReportInfo()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    repSessionCO.getReportCO().setREPORT_NAME(reportName);
	    repSessionCO.getReportCO().setAPP_NAME(applicationName);

	    repNameExists = false;
	    SessionCO sessionCO = returnSessionObject();
	    String retVal = "0";
	    if(reference != null && !reference.isEmpty())
	    {
		retVal = designerBO.retExistingOpts(reference, applicationName, sessionCO.getLanguage(),
			getText("fcr.checkProgRefAlert"));
	    }
	    setUpdate(retVal);
	    if("0".equals(retVal))
	    {
		refExists = false;
	    }
	    else
	    {
		refExists = true;
	    }
	}
	catch(BaseException e)
	{
	    //log.error(e, "Error checking report information values against db.");
	    handleException(e, "Error checking report information values against db",
		    "checkReportInfo.exceptionMsg._key");
	}

	return "successWizard";
    }

    public String checkQuery()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	if(repSessionCO.getReportCO().getQueriesList().size() == 0)
	{
	    missingQry = true;
	}
	else
	{
	    missingQry = false;
	}

	return "successWizard";
    }

    public String submitWizard()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    SQL_OBJECT sqlObj = repSessionCO.getSqlObj();// /////////////////
	    reportCO.setREPORT_NAME(reportName);
	    reportCO.setPROG_REF(reference);
	    reportCO.setPARENT_REF(PARENT_REF);
	    reportCO.setPARENT_REF_STR(PARENT_REF_STR);
	    reportCO.setDEFAULT_FORMAT(ConstantsCommon.HTML_REP_FORMAT);
	    
	    PTH_CTRL1VO pthCtrl1VO = returnCommonLibBO().returnPthCtrl1();
	    String cltRepFlag = pthCtrl1VO.getCLIENT_REPORT_FLAG();
	    reportCO.setCltRepFlag(cltRepFlag);
	    
	    // annasuccar- 20 jan 2014: commented out the below to unify HTML
	    // and HTM
	    // if(("HTML").equals(reportCO.getDEFAULT_FORMAT()))
	    // {
	    // reportCO.setDEFAULT_FORMAT("HTM");
	    // }
	    if(queryId != null && !("").equals(queryId))
	    {
		reportCO.getQueriesList().get(0).setQUERY_ID(new BigDecimal(queryId));
	    }
	    if(reportLayout == null)
	    {
		reportLayout = "1";
	    }
	    reportCO.getQueriesList().get(0).getSqlObject().setOutputLayout(reportLayout);
	    // //override reportCO.ArgumentsList by
	    // queryCO.getSqlObject().getArgumentsList() in case the report is
	    // freeForm
	    // // since the sql and the report will have the same args
	    if(ConstantsCommon.OUTPUT_LAYOUT_TABULAR.equals(repSessionCO.getSqlObj().getOutputLayout()))
	    // copy the args from qry to rep
	    {
		LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> argMap = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();

		for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : sqlObj.getArgumentsList().entrySet())
		{
		    Long key = entry.getKey();
		    IRP_REP_ARGUMENTSCO args = entry.getValue();
		    argMap.put(key, args);
		}

		reportCO.setArgumentsList(argMap);
	    }
	    else
	    // same args on qry and rep
	    {
		reportCO.setArgumentsList(sqlObj.getArgumentsList());
	    }
	    repSessionCO.setOldReportCO(reportCO);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error submitting the wizard.");
	    handleException(e, "Error submitting the wizard", "submitWizard.exceptionMsg._key");
	}
	return "successWizard";
    }

    public String showQueryDesigner() throws Exception
    {
	String openSqb = getOpenSqb();
	if(openSqb != null && openSqb.equals("true"))
	{
	    request.setAttribute("openSqb", null);
	}
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	SQL_OBJECT oldSqlObj = (SQL_OBJECT) CommonUtil.getCopyObject(sqlObj);
	repSessionCO.setOldSqlObj(oldSqlObj);
	return "queryDesigner";
    }

    public String showQueriesList()
    {
	return "queriesList";
    }

    public String getReportName()
    {
	return reportName;
    }

    public void setReportName(String reportName)
    {
	this.reportName = reportName;
    }

    public String getReference()
    {
	return reference;
    }

    public void setReference(String reference)
    {
	this.reference = reference;
    }

    public boolean isRepNameExists()
    {
	return repNameExists;
    }

    public void setRepNameExists(boolean repNameExists)
    {
	this.repNameExists = repNameExists;
    }

    public boolean isRefExists()
    {
	return refExists;
    }

    public void setRefExists(boolean refExists)
    {
	this.refExists = refExists;
    }

    public boolean isMissingQry()
    {
	return missingQry;
    }

    public String getSyntax()
    {
	return syntax;
    }

    public void setSyntax(String syntax)
    {
	this.syntax = syntax;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public String getQueryId()
    {
	return queryId;
    }

    public void setQueryId(String queryId)
    {
	this.queryId = queryId;
    }

    public String openQuery()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    queryCO = queryBO.returnQueryById(new BigDecimal(queryId), true);
	    queryCO.setQUERY_ID(null);
	    queryCO.setIndex(0);
	    if(queryCO.getSqlObject().getSqbSyntax() != null && queryCO.getSqlObject().getSqbSyntax().length() > 0)
	    {
		syntax = queryCO.getSqlObject().getSqbSyntax().toString();
		queryCO.setSqlSyntax(queryCO.getSqlObject().getSqbSyntax());
	    }
	    else
	    {
		syntax = (queryCO.getSqlSyntax()).toString();
	    }
	    repSessionCO.setSqlObj(queryCO.getSqlObject());
	    List<IRP_AD_HOC_QUERYCO> queriesList = new ArrayList<IRP_AD_HOC_QUERYCO>();
	    queriesList.add(queryCO);
	    reportCO.setQueriesList(queriesList);
	    // to not override reportCO.ArgumentsList by
	    // queryCO.getSqlObject().getArgumentsList() to cover the case when
	    // the report is tabular
	    // in case the report is freeForm, we will override later when
	    // submitting the report
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> hmArgs = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();

	    // this loop is used to set an argument label and order and argument
	    // value from query to the arguments report.
	    int i = 0;
	    for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : queryCO.getSqlObject().getArgumentsList().entrySet())
	    {
		Long key = entry.getKey();
		IRP_REP_ARGUMENTSCO args = entry.getValue();
		args.setARGUMENT_LABEL(args.getARGUMENT_NAME());
		i++;
		args.setARGUMENT_ORDER(new BigDecimal(i));
		hmArgs.put(key, args);

	    }
	    reportCO.setArgumentsList(hmArgs);

	    String repName = reportCO.getREPORT_NAME() + "_0";
	    queryCO.setQUERY_NAME(repName);
	    repSessionCO.getSqlObj().setQUERY_NAME(repName);
	}
	catch(BaseException e)
	{
	    //log.error(e, "Error returning the query.");
	    handleException(e, "Error returning the query", "openQuery.exceptionMsg._key");
	}
	catch(IOException e)
	{
	    //log.error(e, "Error deserialising query byte[] to query object.");
	    handleException(e, "Error deserialising query bytes to query object",
		    "openQuery.deserialising.exceptionMsg._key");
	}
	catch(ClassNotFoundException e)
	{
	    //log.error(e, "Error deserialising query byte[] to query object.");
	    handleException(e, "Error deserialising query bytes to query object",
		    "openQuery.deserialising.exceptionMsg._key");
	}
	return "successWizard";
    }

    public String getTableHTML()
    {
	return tableHTML;
    }

    public void setTableHTML(String tableHTML)
    {
	this.tableHTML = tableHTML;
    }

    public String generateHtml() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	    StringBuffer html = new StringBuffer();
	    StringBuffer tHead = new StringBuffer();
	    StringBuffer tFoot = new StringBuffer();
	    StringBuffer tBody = new StringBuffer();

	    if(sqlObj != null && sqlObj.getDisplayFields().size() > 0)
	    {
		log.debug("sqlObj.getOutputLayout(): " + sqlObj.getOutputLayout());
		String qryName = sqlObj.getQUERY_NAME();
		LinkedHashMap<Long, IRP_FIELDSCO> feMap = sqlObj.getDisplayFields();
		Iterator it = feMap.values().iterator();
		IRP_FIELDSCO feCO;
		String pattern;

		if(sqlObj.getOutputLayout().equals(ConstantsCommon.OUTPUT_LAYOUT_TABULAR))
		{
		    while(it.hasNext())
		    {
			feCO = (IRP_FIELDSCO) it.next();
			if(feCO.getFIELD_DATA_TYPE().equals("java.util.Date"))
			{
			    pattern = "pattern=\"dd/MM/yyyy\"";
			}
			else
			{
			    pattern = "";
			}
			tHead.append("<td scope=\"col\" style=\"text-align: center;\">").append(
				"<input type=\"text\" label=\"true\" value=\"" + feCO.getFeName()
					+ "\" style=\"font-size: 9px;\" />").append("</td>");
			tFoot
				.append("<td style=\"background: url(&quot;/imal_reporting_portal/ckeditor/images/total.JPG&quot;) no-repeat scroll center center transparent; text-align: center;\">&nbsp;</td>");
			tBody.append("<td style=\"text-align: center;\">").append(
				"<input type=\"text\" field=\"true\" name=\"" + feCO.getFeName() + "\" value=\"$F{"
					+ feCO.getLabelAlias().replaceAll(" ", "") + "}\" class=\""
					+ feCO.getFIELD_DATA_TYPE() + "\" " + pattern
					+ " readOnly=\"true\" style=\"font-size: 9px;\"/>").append("</td>");
		    }

		    html.append("<table id=\"" + qryName + "\" subdataset=\"" + qryName
			    + "\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\">"
			    + "<tbody>" + "<tr id=\"header\">" + tHead.toString() + "</tr> " + " <tr id=\"" + qryName
			    + "_detail\">" + tBody.toString() + "</tr>" + "<tr id=\"footer\">" + tFoot.toString()
			    + "</tr>" + "</tbody>" + "</table>" + "<p>&nbsp;</p>");
		}
		else
		{
		    while(it.hasNext())
		    {
			feCO = (IRP_FIELDSCO) it.next();
			if(feCO.getFIELD_DATA_TYPE().equals("java.util.Date"))
			{
			    pattern = "pattern=\"dd/MM/yyyy\"";
			}
			else
			{
			    pattern = "";
			}
			html.append("<p>").append(
				"<strong><input type=\"text\" label=\"true\" value=\"" + feCO.getFeName()
					+ "\" style=\"font-size: 9px;\" /></strong>")
				.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(
					"<input type=\"text\" field=\"true\" name=\"" + feCO.getFeName()
						+ "\" value=\"$F{" + feCO.getLabelAlias().replaceAll(" ", "")
						+ "}\" class=\"" + feCO.getFIELD_DATA_TYPE() + "\" " + pattern
						+ " readOnly=\"true\" style=\"font-size: 9px;\"/>").append("</p>")
				.append("<p>&nbsp;</p>");
		    }
		}

		setTableHTML(html.toString());
	    }
	    else
	    {
		setTableHTML("");
	    }
	}
	catch(Exception x)
	{
	    //log.error(x, "Error generating the report html output");
	    handleException(x, "Error generating the report html output", "generateRepHtml.exceptionMsg._key");
	    setTableHTML("Wrong tblHtml");
	}
	return "successWizard";
    }

    public String generateSql() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	    StringBuffer sql = queryBO.generateSql(sqlObj);
	    syntax = sql.toString();
	    queryCO.setQUERY_NAME(sqlObj.getQUERY_NAME());
	    queryCO.setSqlObject(sqlObj);
	    queryCO.setSqlSyntax(sql);
	    queryCO.setIndex(0);
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    List<IRP_AD_HOC_QUERYCO> queriesList = new ArrayList<IRP_AD_HOC_QUERYCO>();
	    queriesList.add(queryCO);
	    reportCO.setQueriesList(queriesList);

	    // case when qry is designer
	    // to not override reportCO.ArgumentsList by
	    // queryCO.getSqlObject().getArgumentsList() to cover the case when
	    // the report is tabular
	    // in case the report is freeForm, we will override later when
	    // submitting the report
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> hmArgs = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();

	    // this loop is used to set an argument label and order and argument
	    // value from query to the arguments report.
	    int i = 0;
	    for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : sqlObj.getArgumentsList().entrySet())
	    {
		Long key = entry.getKey();
		IRP_REP_ARGUMENTSCO args = entry.getValue();
		args.setARGUMENT_LABEL(args.getARGUMENT_NAME());
		args.setLINK_REP_QRY_ARG(args.getARGUMENT_LABEL());
		i++;
		args.setARGUMENT_ORDER(new BigDecimal(i));
		hmArgs.put(key, args);

	    }

	    reportCO.setArgumentsList(hmArgs);

	}
	catch(Exception x)
	{
	    //log.error(x, "Error generating the sql syntax.");
	    handleException(x, "Error generating query syntax", "generateSqlSyntax.exceptionMsg._key");
	    syntax = "Wrong SQL";
	}
	return "successWizard";
    }

    public void resetReport()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setReportCO(repSessionCO.getOldReportCO());
    }

    public void resetQry()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setSqlObj(new SQL_OBJECT());
	List<IRP_AD_HOC_QUERYCO> list = repSessionCO.getReportCO().getQueriesList();
	list.clear();
    }

    public String getPARENT_REF()
    {
	return PARENT_REF;
    }

    public void setPARENT_REF(String pARENTREF)
    {
	PARENT_REF = pARENTREF;
    }

    public String getPARENT_REF_STR()
    {
	return PARENT_REF_STR;
    }

    public void setPARENT_REF_STR(String pARENTREFSTR)
    {
	PARENT_REF_STR = pARENTREFSTR;
    }

    public String getReportLayout()
    {
	return reportLayout;
    }

    public void setReportLayout(String reportLayout)
    {
	this.reportLayout = reportLayout;
    }

    public List<SYS_PARAM_LOV_TRANSVO> getRepLayout()
    {
	List<SYS_PARAM_LOV_TRANSVO> layouts = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("1");
	sysVO.setVALUE_DESC(getText("layout.table_key"));
	layouts.add(sysVO);
	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("2");
	sysVO.setVALUE_DESC(getText("layout.freeform_key"));
	layouts.add(sysVO);
	return layouts;
    }
}
