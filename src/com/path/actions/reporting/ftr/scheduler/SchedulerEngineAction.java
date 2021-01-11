package com.path.actions.reporting.ftr.scheduler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.struts2.lib.common.base.LookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSSC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_LOGCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSSC;

public class SchedulerEngineAction extends LookupBaseAction
{

    Queue<IRP_AD_HOC_REPORTCO> reports;
    private SchedulerBO schedulerBO;
    private String lblCurrentStatus;
    private String repFormat;
    private String startDate;
    private String updates;
    private String repId;
    private String schedId;
    private String schedDt;
    private String progRef;
    private int IS_DB;
    private BigDecimal snpId;
    private SnapShotBO snapShotBO;
    private final IRP_REPORT_SCHED_PARAMSSC sc = new IRP_REPORT_SCHED_PARAMSSC();
    private String FILE_NAME;
    private String CONN_ID;
    public String getCONN_ID()
    {
        return CONN_ID;
    }

    public void setCONN_ID(String cONNID)
    {
        CONN_ID = cONNID;
    }

    public String getFILE_NAME()
    {
	return FILE_NAME;
    }

    public void setFILE_NAME(String fILENAME)
    {
	FILE_NAME = fILENAME;
    }

    public void setSnapShotBO(SnapShotBO snapShotBO)
    {
	this.snapShotBO = snapShotBO;
    }

    public BigDecimal getSnpId()
    {
	return snpId;
    }

    public void setSnpId(BigDecimal snpId)
    {
	this.snpId = snpId;
    }

    public int getIS_DB()
    {
	return IS_DB;
    }

    public void setIS_DB(int iSDB)
    {
	IS_DB = iSDB;
    }

    public String getProgRef()
    {
	return progRef;
    }

    public void setProgRef(String progRef)
    {
	this.progRef = progRef;
    }

    private IRP_REPORT_SCHED_LOGCO schedLogCO;

    public String getRepId()
    {
	return repId;
    }

    public void setRepId(String repId)
    {
	this.repId = repId;
    }

    public String getSchedId()
    {
	return schedId;
    }

    public void setSchedId(String schedId)
    {
	this.schedId = schedId;
    }

    public String getSchedDt()
    {
	return schedDt;
    }

    public void setSchedDt(String schedDt)
    {
	this.schedDt = schedDt;
    }

    public IRP_REPORT_SCHED_LOGCO getSchedLogCO()
    {
	return schedLogCO;
    }

    public void setSchedLogCO(IRP_REPORT_SCHED_LOGCO schedLogCO)
    {
	this.schedLogCO = schedLogCO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String getRepFormat()
    {
	return repFormat;
    }

    public void setRepFormat(String repFormat)
    {
	this.repFormat = repFormat;
    }

    public String getStartDate()
    {
	return startDate;
    }

    public void setStartDate(String startDate)
    {
	this.startDate = startDate;
    }

    private List<IRP_REPORT_SCHED_LOGCO> runningReports;

    public List<IRP_REPORT_SCHED_LOGCO> getRunningReports()
    {
	return runningReports;
    }

    public void setRunningReports(List<IRP_REPORT_SCHED_LOGCO> runningReports)
    {
	this.runningReports = runningReports;
    }

    public String getLblCurrentStatus()
    {
	return lblCurrentStatus;
    }

    public void setLblCurrentStatus(String lblCurrentStatus)
    {
	this.lblCurrentStatus = lblCurrentStatus;
    }

    public void setSchedulerBO(SchedulerBO schedulerBO)
    {
	this.schedulerBO = schedulerBO;
    }

    public String stopEngine()
    {
	schedulerBO.stopTimer();
	lblCurrentStatus = getText("reporting.stopped");
	return SUCCESS;
    }

    public String startEngine()
    {
	try
	{
	    schedulerBO.startEngine();
	    lblCurrentStatus = getText("reporting.running");
	    if(schedulerBO.isTimerRunning())
	    {
		 lblCurrentStatus = getText("reporting.running");
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null,null);
	}
	return SUCCESS;
    }

    public String loadRunningScheds() throws BaseException
    {

	copyproperties(sc);
	SessionCO sessionCO = returnSessionObject();
	sc.setLANG_CODE(sessionCO.getLanguage());
	sc.setLOV_DATA_TYPE(RepConstantsCommon.SAVE_DATA_TYPE_LOV_TYPE);
	sc.setSCHED_STATUS_LOV_TYPE_ID(RepConstantsCommon.SCHED_STATUS_LOV_TYPE);
	sc.setFILE_FORMAT_LOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	if(checkNbRec(sc))
	{
	    setRecords(schedulerBO.findReportByRunningStatusCount(sc));
	}
	runningReports = schedulerBO.findReportByRunningStatus(sc);
	setGridModel(runningReports);
	return SUCCESS;
    }

    public String execute() throws BaseException, ParseException
    {
	if(schedulerBO.isTimerRunning())
	{
	    lblCurrentStatus = getText("reporting.running");
	}
	else
	{
	    lblCurrentStatus = getText("reporting.stopped");
	}
	set_showSmartInfoBtn("false");
	return SUCCESS;
    }

    // annasuccar: remove it and remove the hidden field from jsp
    public String getJasperFileName()
    {
	SessionCO sessionCO = returnSessionObject();
	return "JrxmlFile_" + sessionCO.getUserName();
    }

    public void previewSchedReport()
    {
	try
	{
	    HttpServletResponse response = ServletActionContext.getResponse();
	    int isDb = getIS_DB();
	    BigDecimal repId = new BigDecimal(getRepId());
	    BigDecimal schedId = new BigDecimal(getSchedId());
	    String schedDateStr = getSchedDt();
	    String repFormat = getRepFormat();
	    String repName = getFILE_NAME();
	    log.info("++++++++++repFormat===" + repFormat + "                repName== " + repName);

	    String ext = "";
	    if((ConstantsCommon.DOC_REP_FORMAT).equals(repFormat))
	    {
		ext = ConstantsCommon.DOC_EXT;
		// response.setContentType("application/vnd.ms-word");
	    }
	    else if((ConstantsCommon.PDF_REP_FORMAT).equals(repFormat))
	    {
		ext = ConstantsCommon.PDF_EXT;
		// response.setContentType("application/pdf");
	    }
	    else if((ConstantsCommon.XLS_REP_FORMAT).equals(repFormat))
	    {
		ext = ConstantsCommon.XLSX_EXT;
		// response.setContentType("application/vnd.ms-excel");
	    }
	    else if((ConstantsCommon.HTML_REP_FORMAT).equals(repFormat))
	    {
		ext = ConstantsCommon.HTML_EXT;
		// response.setContentType("text/html");
	    }
	    else if(ConstantsCommon.CSV_REP_FORMAT.equalsIgnoreCase(repFormat))
	    {
		ext = ConstantsCommon.TXT_EXT;
		// response.setContentType("application/txt");
	    }
	    else if(ConstantsCommon.CSV_EXT_REP_FORMAT.equalsIgnoreCase(repFormat))
	    {
	    	ext = ConstantsCommon.CSV_REP_FORMAT.toLowerCase();
	    }
	    else if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equalsIgnoreCase(repFormat))
	    {
		ext = ConstantsCommon.TXT_EXT;
		// response.setContentType("application/txt");
	    }
	    else if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equalsIgnoreCase(repFormat))
	    {
		ext = ConstantsCommon.XLSX_EXT;
		// response.setContentType("application/vnd.ms-excel");
	    }
	    else if(ConstantsCommon.ODS_REP_FORMAT.equalsIgnoreCase(repFormat))
	    {
	    	ext = ConstantsCommon.ODS_REP_FORMAT.toLowerCase();
	    }
	    else if(ConstantsCommon.SQL_REP_FORMAT.equalsIgnoreCase(repFormat))
	    {
		ext = ConstantsCommon.SQL_EXT;
	    }
	    if(isDb == 0) // saved under repository
	    {
		Object[] retObj=snapShotBO.readSnapshotFromRepository(repName, ext);
		if(retObj[0]==null)
		{
		    String errorString = "<b>The following file is not found, it might be deleted</b>";
		    response.setHeader("Content-Disposition", "inline; filename=\"ReportNotFound.html\"");
		    response.setContentType("application/html");
		    response.getOutputStream().write(errorString.getBytes(FileUtil.DEFAULT_FILE_ENCODING));
		}
		else
		{
		    byte[] reportBytes = (byte[]) retObj[0];
		    ext=(String) retObj[1];
		    // apply audit
		    IRP_REPORT_SCHED_LOGCO zSchedLogCO = new IRP_REPORT_SCHED_LOGCO();
		    zSchedLogCO.setREPORT_ID(repId);
		    zSchedLogCO.setSCHED_ID(schedId);
		    zSchedLogCO.setSTART_DATE_STR(getStartDate().replaceAll(" ", ""));
		    zSchedLogCO.setSCHEDULED_DATE_STR(schedDateStr.replaceAll(" ", ""));
		    zSchedLogCO.setSTATUS("");
		    applyRetrieveAudit(AuditConstant.SCHED_ENG_KEY_REF, zSchedLogCO, zSchedLogCO);

		    // add the below code in order to fix an xls issue and to be
		    // the same as the snapshot section
		    response.addHeader("Content-Disposition", "attachment;filename=\"" + repName + "." + ext
			    + "\"");
		    response.setContentType("application/vnd.ms-word;charset=unicode");
		    response.getOutputStream().write(reportBytes);
		}
	    }
	    else
	    // saved under the DB
	    {
		IRP_SNAPSHOTSSC snpSC = new IRP_SNAPSHOTSSC();
		snpSC.setSNAPSHOT_ID(getSnpId());
		snpSC.setIS_DB(new BigDecimal(getIS_DB()));
		if(!"".equals(getCONN_ID()))
		{
		    snpSC.setCONN_ID(new BigDecimal(getCONN_ID()));
		}
		IRP_SNAPSHOTSCO retSnpCO = snapShotBO.retReportContent(snpSC);

		if(retSnpCO == null)
		{
		    String errorString = "<b>The following file is not found, it might be deleted</b>";
		    response.setHeader("Content-Disposition", "inline; filename=\"ReportNotFound.html\"");
		    response.setContentType("application/html");
		    response.getOutputStream().write(errorString.getBytes(FileUtil.DEFAULT_FILE_ENCODING));
		}
		else
		{
		    byte[] reportBytes = retSnpCO.getREPORT_CONTENT();

		    if(reportBytes==null)
		    {
			reportBytes=new byte[0];
		    }
		    else
		    {
			if(ext.equals(ConstantsCommon.HTML_EXT))
			{
			    boolean isZip = FileUtil.checkIfZip(reportBytes);
			    if(isZip)
			    {
				ext = ConstantsCommon.ZIP_EXT;
			    }
			}
		    }
		    response.addHeader("Content-Disposition", "attachment;filename=\"" + repName + "." + ext
			    + "\"");
		    response.setContentType("application/vnd.ms-word;charset=unicode");
		    response.getOutputStream().write(reportBytes);
		}
	    }
	}
	catch(Exception e)
	{
	    //log.error("Error previewing report");
	    handleException(e,getText("previewReport.exceptionMsg._key"),null);
	}
    }

    public String retSchedEngTrx() throws Exception
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("schedLogCO");
	    schedLogCO = (IRP_REPORT_SCHED_LOGCO) JSONObject.toBean(jsonModel, IRP_REPORT_SCHED_LOGCO.class);

	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.RETRIEVE);
	    refCO.setKeyRef(AuditConstant.SCHED_ENG_KEY_REF);

	    schedLogCO.setSCHEDULED_DATE_STR((schedLogCO.getSCHED_DATE_STR1().replaceAll(" ", "")));
	    schedLogCO.setSTART_DATE_STR(schedLogCO.getSTART_DATE_STR1().replaceAll(" ", ""));

	    schedLogCO.setSTATUS("");

	    setAuditTrxNbr(auditBO.checkAuditKey(schedLogCO, refCO));
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public Object getModel()
    {
	return sc;
    }
}
