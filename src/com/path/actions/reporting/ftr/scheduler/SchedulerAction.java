package com.path.actions.reporting.ftr.scheduler;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.springframework.remoting.RemoteLookupFailureException;

import com.path.actions.ReportAction.SepartorFormat;
import com.path.bo.admin.user.UsrBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.bo.reporting.ftr.scheduler.TimerScheduleBO;
import com.path.dbmaps.vo.ALRT_EVTVO;
import com.path.dbmaps.vo.EOD_BATCH_MASTERVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_PARAMSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.SYS_PARAM_LANGUAGESVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.USRVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.admin.user.UsrSC;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.common.select.SelectSC;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.scheduler.Days;
import com.path.vo.reporting.scheduler.IRP_BATCHCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULESC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSSC;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYCO;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYSC;
import com.path.vo.reporting.scheduler.IRP_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;
import com.path.vo.reporting.scheduler.IRP_SCHED_DETAILSCO;
import com.path.vo.reporting.scheduler.Months;

public class SchedulerAction extends ReportingLookupBaseAction
{
    private TimerScheduleBO timerScheduleBO;
    public void setTimerScheduleBO(TimerScheduleBO timerScheduleBO)
    {
        this.timerScheduleBO = timerScheduleBO;
    }

    HttpServletRequest request = ServletActionContext.getRequest();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> fileFormatList = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to
    // fill
    // select
    // in
    // jsp
    private ArrayList<SYS_PARAM_LOV_TRANSVO> printerNameList = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to
    // fill
    // select
    // in
    // jsp
  
     private CommonLookupBO commonLookupBO;
    private DesignerBO designerBO;
    private IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
    private SchedulerBO schedulerBO;
    private IRP_SCHEDULECO schedule;
    private IRP_SCHEDULECO schedCO;
    private Months months;
    private Days days;
    private String mode;
    private String detMode;
    private String reportId;
    private IRP_AD_HOC_REPORTCO report;
    private IRP_SCHEDULESC schedulerSC = new IRP_SCHEDULESC();
    private final IRP_REPORT_SCHEDULESC reportSchedSC = new IRP_REPORT_SCHEDULESC();
    private IRP_REPORT_SCHEDULECO reportSchedCO = new IRP_REPORT_SCHEDULECO();
    private BigDecimal schedId;
    private String hiddenMenuId;
    private String updates;
    private CommonRepFuncBO commonRepFuncBO;
    private ArrayList<SYS_PARAM_LANGUAGESVO> langList = new ArrayList<SYS_PARAM_LANGUAGESVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> saveAsRepList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> fromMailList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> toMailList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> ccMailList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> bccMailList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> emailFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> passwordFeLst =  new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> emailComputeFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> emailCIFLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> emailFileNameLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> mailFeGroupByCmbLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> dateTypeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> schedTypeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private BigDecimal scheduleId;
    private String updates1;
    private String updates2;
    private UsrBO usrBO;
    private final UsrSC usrSC = new UsrSC();
    private FcrBO fcrBO;
    private CommonReportingBO commonReportingBO;
    
    
    
    public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
        this.commonReportingBO = commonReportingBO;
    }

    public void setFcrBO(FcrBO fcrBO)
    {
        this.fcrBO = fcrBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getEmailComputeFeLst()
    {
        return emailComputeFeLst;
    }

    public void setEmailComputeFeLst(ArrayList<SYS_PARAM_LOV_TRANSVO> emailComputeFeLst)
    {
        this.emailComputeFeLst = emailComputeFeLst;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getSchedTypeList()
    {
	return schedTypeList;
    }

    public void setSchedTypeList(ArrayList<SYS_PARAM_LOV_TRANSVO> schedTypeList)
    {
	this.schedTypeList = schedTypeList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDateTypeList()
    {
	return dateTypeList;
    }

    public void setDateTypeList(ArrayList<SYS_PARAM_LOV_TRANSVO> dateTypeList)
    {
	this.dateTypeList = dateTypeList;
    }

    public void setUsrBO(UsrBO usrBO)
    {
	this.usrBO = usrBO;
    }

    public String getUpdates2()
    {
	return updates2;
    }

    public void setUpdates2(String updates2)
    {
	this.updates2 = updates2;
    }

    private final IRP_REP_SCHED_MAIL_GROUP_BYSC mailGrpSC = new IRP_REP_SCHED_MAIL_GROUP_BYSC();

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getMailFeGroupByCmbLst()
    {
	return mailFeGroupByCmbLst;
    }

    public void setMailFeGroupByCmbLst(ArrayList<SYS_PARAM_LOV_TRANSVO> mailFeGroupByCmbLst)
    {
	this.mailFeGroupByCmbLst = mailFeGroupByCmbLst;
    }

    public BigDecimal getScheduleId()
    {
	return scheduleId;
    }

    public void setScheduleId(BigDecimal scheduleId)
    {
	this.scheduleId = scheduleId;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getEmailFileNameLst()
    {
	return emailFileNameLst;
    }

    public void setEmailFileNameLst(ArrayList<SYS_PARAM_LOV_TRANSVO> emailFileNameLst)
    {
	this.emailFileNameLst = emailFileNameLst;
    }

    public void setPasswordFeLst(ArrayList<SYS_PARAM_LOV_TRANSVO> passwordFeLst)
    {
	this.passwordFeLst = passwordFeLst;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getPasswordFeLst()
    {
	return passwordFeLst;
    }
    public ArrayList<SYS_PARAM_LOV_TRANSVO> getEmailFeLst()
    {
	return emailFeLst;
    }

    public void setEmailFeLst(ArrayList<SYS_PARAM_LOV_TRANSVO> emailFeLst)
    {
	this.emailFeLst = emailFeLst;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getEmailCIFLst()
    {
	return emailCIFLst;
    }

    public void setEmailCIFLst(ArrayList<SYS_PARAM_LOV_TRANSVO> emailCIFLst)
    {
	this.emailCIFLst = emailCIFLst;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getToMailList()
    {
	return toMailList;
    }

    public void setToMailList(ArrayList<SYS_PARAM_LOV_TRANSVO> toMailList)
    {
	this.toMailList = toMailList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getCcMailList()
    {
	return ccMailList;
    }

    public void setCcMailList(ArrayList<SYS_PARAM_LOV_TRANSVO> ccMailList)
    {
	this.ccMailList = ccMailList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getBccMailList()
    {
	return bccMailList;
    }

    public void setBccMailList(ArrayList<SYS_PARAM_LOV_TRANSVO> bccMailList)
    {
	this.bccMailList = bccMailList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getFromMailList()
    {
	return fromMailList;
    }

    public void setFromMailList(ArrayList<SYS_PARAM_LOV_TRANSVO> fromMailList)
    {
	this.fromMailList = fromMailList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getSaveAsRepList()
    {
	return saveAsRepList;
    }

    public void setSaveAsRepList(ArrayList<SYS_PARAM_LOV_TRANSVO> saveAsRepList)
    {
	this.saveAsRepList = saveAsRepList;
    }

    public ArrayList<SYS_PARAM_LANGUAGESVO> getLangList()
    {
	return langList;
    }

    public void setLangList(ArrayList<SYS_PARAM_LANGUAGESVO> langList)
    {
	this.langList = langList;
    }

    public IRP_SCHEDULECO getSchedCO()
    {
	return schedCO;
    }

    public void setSchedCO(IRP_SCHEDULECO schedCO)
    {
	this.schedCO = schedCO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public IRP_AD_HOC_REPORTSC getReportSC()
    {
	return reportSC;
    }

    public void setReportSC(IRP_AD_HOC_REPORTSC reportSC)
    {
	this.reportSC = reportSC;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public IRP_SCHEDULECO getSchedule()
    {
	return schedule;
    }

    public void setSchedule(IRP_SCHEDULECO schedule)
    {
	this.schedule = schedule;
    }

    public IRP_SCHEDULESC getSchedulerSC()
    {
	return schedulerSC;
    }

    public void setSchedulerSC(IRP_SCHEDULESC schedulerSC)
    {
	this.schedulerSC = schedulerSC;
    }

    public void setSchedulerBO(SchedulerBO schedulerBO)
    {
	this.schedulerBO = schedulerBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getFileFormatList()
    {
	return fileFormatList;
    }

    public void setFileFormatList(ArrayList<SYS_PARAM_LOV_TRANSVO> fileFormatList)
    {
	this.fileFormatList = fileFormatList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getPrinterNameList()
    {
	return printerNameList;
    }

    public void setPrinterNameList(ArrayList<SYS_PARAM_LOV_TRANSVO> printerNameList)
    {
	this.printerNameList = printerNameList;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getFrequencyList()
    {
	 ArrayList<SYS_PARAM_LOV_TRANSVO> frequencyList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	// fill frequency DDL
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_FREQUENCY_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    frequencyList=  (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return frequencyList;
    }

    public Months getMonths()
    {
	return months;
    }

    public void setMonths(Months months)
    {
	this.months = months;
    }

    public Days getDays()
    {
	return days;
    }

    public void setDays(Days days)
    {
	this.days = days;
    }

    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public String getDetMode()
    {
	return detMode;
    }

    public void setDetMode(String detMode)
    {
	this.detMode = detMode;
    }

    public IRP_AD_HOC_REPORTCO getReport()
    {
	return report;
    }

    public void setReport(IRP_AD_HOC_REPORTCO report)
    {
	this.report = report;
    }

    public String getReportId()
    {
	return reportId;
    }

    public void setReportId(String reportId)
    {
	this.reportId = reportId;
    }

    public IRP_REPORT_SCHEDULECO getReportSchedCO()
    {
	return reportSchedCO;
    }

    public void setReportSchedCO(IRP_REPORT_SCHEDULECO reportSchedCO)
    {
	this.reportSchedCO = reportSchedCO;
    }

    public Object getModel()
    {
	return schedulerSC;
    }

    public String execute() throws BaseException
    {
	set_showSmartInfoBtn("false");
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	fileFormatList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	SelectSC sc= new SelectSC();
	sc.setLanguage(sessionCO.getLanguage());
	sc.setLovTypeId(ConstantsCommon.LANGUAGES_LOV_TYPE);
	langList = (ArrayList)returnCommonLibBO().returnLanguages(sc);
	PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
	for(PrintService printer : printServices)
	{
	    SYS_PARAM_LOV_TRANSVO singlePrinter = new SYS_PARAM_LOV_TRANSVO();
	    singlePrinter.setVALUE_CODE(printer.getName());
	    singlePrinter.setVALUE_DESC(printer.getName());
	    printerNameList.add(singlePrinter);
	}

	// fill the saveAsReport list
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SAVE_SCHED_REPORT_AS);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	saveAsRepList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	// fill from email type
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_REPORT_FROM_EMAIL_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	fromMailList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	// fill to email type
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_REPORT_TO_EMAIL_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	toMailList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	// fill cc email type
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_REPORT_CC_EMAIL_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	ccMailList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	// fill bcc email type
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_REPORT_CC_EMAIL_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	bccMailList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	// fill date type combo
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_REPORT_DATE_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	dateTypeList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	// fill schedule type combo
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	schedTypeList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	applyMainSchedScreenDisplay();
	reportSchedScreenDisplay();
	return SUCCESS;
    }

    /**this method will set the mandatory fileds at the level of the main sched form*/
    public void applyMainSchedScreenDisplay()
    {
	    SYS_PARAM_SCREEN_DISPLAYVO SCHED_NAME = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SCHED_NAME.setIS_MANDATORY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("SCHED_NAME", SCHED_NAME);
	    
	    SYS_PARAM_SCREEN_DISPLAYVO FIRST_RUN_DATE = new SYS_PARAM_SCREEN_DISPLAYVO();
	    FIRST_RUN_DATE.setIS_MANDATORY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("FIRST_RUN_DATE", FIRST_RUN_DATE);
	    
	    SYS_PARAM_SCREEN_DISPLAYVO SCHED_EXPIRY_DATE = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SCHED_EXPIRY_DATE.setIS_MANDATORY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("SCHED_EXPIRY_DATE", SCHED_EXPIRY_DATE);
	    
	    SYS_PARAM_SCREEN_DISPLAYVO REPETITION = new SYS_PARAM_SCREEN_DISPLAYVO();
	    REPETITION.setIS_MANDATORY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("REPETITION", REPETITION);
	    
	    SYS_PARAM_SCREEN_DISPLAYVO SCHED_TYPE = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SCHED_TYPE.setIS_MANDATORY(BigDecimal.ONE);
	    
	    SYS_PARAM_SCREEN_DISPLAYVO BATCH_CODE = new SYS_PARAM_SCREEN_DISPLAYVO();
    	if(schedule == null || NumberUtil.isEmptyDecimal(schedule.getSCHED_VO().getBATCH_CODE()))
    	{
    	    BATCH_CODE.setIS_MANDATORY(BigDecimal.ZERO);
    	    BATCH_CODE.setIS_VISIBLE(BigDecimal.ZERO);
    	    getAdditionalScreenParams().put("lookuptxt_BATCH_CODE", BATCH_CODE);
    	    getAdditionalScreenParams().put("BATCH_BRIEF_NAME", BATCH_CODE);
    	}
    	else
    	{
    	    BATCH_CODE.setIS_MANDATORY(BigDecimal.ZERO);
    	    BATCH_CODE.setIS_VISIBLE(BigDecimal.ONE);
    	    BATCH_CODE.setIS_MANDATORY(BigDecimal.ONE);
    	    getAdditionalScreenParams().put("lookuptxt_BATCH_CODE", BATCH_CODE);
    	    getAdditionalScreenParams().put("BATCH_BRIEF_NAME", BATCH_CODE);
    	}
    	//Added for Alert Event
    	SYS_PARAM_SCREEN_DISPLAYVO EVT_ID = new SYS_PARAM_SCREEN_DISPLAYVO();
    	if(schedule == null || NumberUtil.isEmptyDecimal(schedule.getSCHED_VO().getEVT_ID()))
    	{
    		EVT_ID.setIS_MANDATORY(BigDecimal.ZERO);
    		EVT_ID.setIS_VISIBLE(BigDecimal.ZERO);
    	    getAdditionalScreenParams().put("lookuptxt_EVT_ID", EVT_ID);
    	    getAdditionalScreenParams().put("EVT_BRIEF_NAME", EVT_ID);
    	    getAdditionalScreenParams().put("allReportsGridDiv", EVT_ID);
    	}
    	else
    	{
    		EVT_ID.setIS_VISIBLE(BigDecimal.ONE);
    		EVT_ID.setIS_MANDATORY(BigDecimal.ONE);
    	    getAdditionalScreenParams().put("lookuptxt_EVT_ID", EVT_ID);
    	    getAdditionalScreenParams().put("EVT_BRIEF_NAME", EVT_ID);
    	    getAdditionalScreenParams().put("allReportsGridDiv", EVT_ID);
    	}
	    getAdditionalScreenParams().put("SCHED_TYPE", SCHED_TYPE);
	    getAdditionalScreenParams().put("FREQUENCY_HOUR", SCHED_TYPE);
	    getAdditionalScreenParams().put("FREQUENCY_MINUTE", SCHED_TYPE);
	
    }
    
    /**this method will set the mandatory fileds at the level of the report sched form*/
    public void reportSchedScreenDisplay()
    {
	 SYS_PARAM_SCREEN_DISPLAYVO REPORT_ID = new SYS_PARAM_SCREEN_DISPLAYVO();
	 REPORT_ID.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("lookuptxt_REPORT_ID", REPORT_ID);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO fcrRepId = new SYS_PARAM_SCREEN_DISPLAYVO();
	 fcrRepId.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("lookuptxt_fcrRepId", fcrRepId);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO REPORT_NAME = new SYS_PARAM_SCREEN_DISPLAYVO();
	 REPORT_NAME.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("REPORT_NAME", REPORT_NAME);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO fcrRepName = new SYS_PARAM_SCREEN_DISPLAYVO();
	 fcrRepName.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("fcrRepName", fcrRepName);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO REPORT_FORMAT = new SYS_PARAM_SCREEN_DISPLAYVO();
	 REPORT_FORMAT.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("REPORT_FORMAT", REPORT_FORMAT);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO csvSeparatorId = new SYS_PARAM_SCREEN_DISPLAYVO();
	 csvSeparatorId.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("csvSeparatorId", csvSeparatorId);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO REPORT_LANGUAGE = new SYS_PARAM_SCREEN_DISPLAYVO();
	 REPORT_LANGUAGE.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("REPORT_LANGUAGE", REPORT_LANGUAGE);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO ATTACH_FILE_NAME = new SYS_PARAM_SCREEN_DISPLAYVO();
	 ATTACH_FILE_NAME.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("ATTACH_FILE_NAME", ATTACH_FILE_NAME);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO DATE_TYPE = new SYS_PARAM_SCREEN_DISPLAYVO();
	 DATE_TYPE.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("DATE_TYPE", DATE_TYPE);
	 
	 SYS_PARAM_SCREEN_DISPLAYVO SAVE_DATA_TYPE = new SYS_PARAM_SCREEN_DISPLAYVO();
	 SAVE_DATA_TYPE.setIS_MANDATORY(BigDecimal.ONE);
	 getAdditionalScreenParams().put("SAVE_DATA_TYPE", SAVE_DATA_TYPE);
	 applyMailConfigScreenDisplay();
    }
    
    /**this method will set the mandatory fileds at the level of the mail config form*/
    public void applyMailConfigScreenDisplay()
    {
	SYS_PARAM_SCREEN_DISPLAYVO msHost = new SYS_PARAM_SCREEN_DISPLAYVO();
	msHost.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("lookuptxt_msHost", msHost);
	
	SYS_PARAM_SCREEN_DISPLAYVO FROM_EMAIL_TYPE = new SYS_PARAM_SCREEN_DISPLAYVO();
	FROM_EMAIL_TYPE.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("FROM_EMAIL_TYPE", FROM_EMAIL_TYPE);
	
	SYS_PARAM_SCREEN_DISPLAYVO FROM_EMAIL_VAL = new SYS_PARAM_SCREEN_DISPLAYVO();
	FROM_EMAIL_VAL.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("FROM_EMAIL_VAL", FROM_EMAIL_VAL);
	
	SYS_PARAM_SCREEN_DISPLAYVO FROM_EMAIL_FE_VAL = new SYS_PARAM_SCREEN_DISPLAYVO();
	FROM_EMAIL_FE_VAL.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("FROM_EMAIL_FE_VAL", FROM_EMAIL_FE_VAL);
	
	SYS_PARAM_SCREEN_DISPLAYVO EMAIL_SUBJECT = new SYS_PARAM_SCREEN_DISPLAYVO();
	EMAIL_SUBJECT.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("EMAIL_SUBJECT", EMAIL_SUBJECT);
	
	SYS_PARAM_SCREEN_DISPLAYVO EMAIL_BODY = new SYS_PARAM_SCREEN_DISPLAYVO();
	EMAIL_BODY.setIS_MANDATORY(BigDecimal.ONE);
	getAdditionalScreenParams().put("EMAIL_BODY", EMAIL_BODY);
    }
    
    public String loadGrid() throws Exception
    {   SessionCO sessionCO = returnSessionObject();
	copyproperties(schedulerSC);
	schedulerSC.setSCHED_TYPE_LOV_TYPE_ID(RepConstantsCommon.SCHED_TYPE);
	schedulerSC.setLANG_CODE(sessionCO.getLanguage());
	List<IRP_SCHEDULECO> schedulesList = schedulerBO.findSchedules(schedulerSC);

	if(checkNbRec(schedulerSC))
	{
	    setRecords(schedulerBO.findSchedulesCount(schedulerSC));
	}
	setGridModel(schedulesList);

	return SUCCESS;
    }

    public String findSingleSchedule() throws Exception
    {
	try
	{
		SessionCO sessionCO = returnSessionObject();
		schedulerSC.setPreferredLanguage(sessionCO.getLanguage());
	    copyproperties(schedulerSC);
	    schedule = schedulerBO.findSingleSchedule(schedulerSC);
	    months = new Months();
	    days = new Days();
	    if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH) || schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ENDOFMONTH))
	    {
		findSelectedMonths(schedule.getScheduleDetails());
	    }
	    else if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_DAILY))
	    {
		findSelectedDays(schedule.getScheduleDetails());
	    }

	    // apply audit
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa",Locale.ENGLISH);
	    IRP_SCHEDULEVO schedVO = new IRP_SCHEDULEVO();
	    schedVO.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    schedVO.setEND_OF_MONTH(schedule.getSCHED_VO().getEND_OF_MONTH());
	    schedVO.setSCHED_NAME(schedule.getSCHED_VO().getSCHED_NAME());
	    schedVO.setFIRST_RUN_DATE(sdf.parse(schedule.getFIRST_RUN_DATE_STR()));
	    schedVO.setNEXT_RUN_DATE(schedule.getSCHED_VO().getNEXT_RUN_DATE());
	    schedVO.setFREQUENCY_HOUR(schedule.getSCHED_VO().getFREQUENCY_HOUR());
	    schedVO.setFREQUENCY_MINUTE(schedule.getSCHED_VO().getFREQUENCY_MINUTE());	  
	    if (!StringUtil.nullToEmpty(schedule.getSCHED_EXPIRY_DATE_STR()).isEmpty()) 
	    {
	    schedVO.setSCHED_EXPIRY_DATE(sdf.parse(schedule.getSCHED_EXPIRY_DATE_STR()));
	    }
	    schedVO.setSCHED_FREQUENCY(schedule.getSCHED_VO().getSCHED_FREQUENCY());
	    schedVO.setDATE_UPDATED(schedule.getSCHED_VO().getDATE_UPDATED());
	    applyRetrieveAudit(AuditConstant.SCHED_KEY_REF, schedVO, schedVO);
	    // fill schedule type combo
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    schedTypeList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    applyMainSchedScreenDisplay();

	}
	catch(Exception e)
	{
	    //log.error("Error retrieving a schedule");
	    handleException(e, "Error retrieving a schedule", "returnSched.exceptionMsg._key");
	}
	return "successMnt";
    }

    public String findSingleReportSched() throws Exception
    {
	// here
	if(request.getParameter("SCHED_ID") == null || request.getParameter("REPORT_ID") == null)
	{
	    return SUCCESS;
	}

	SYS_PARAM_LOV_TRANSVO sysVO;

	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("0");
	sysVO.setVALUE_DESC(getText("sch.default"));
	emailFileNameLst.add(sysVO);

	reportSchedSC.setSCHED_ID(new BigDecimal(request.getParameter("SCHED_ID")));
	reportSchedSC.setREPORT_ID(new BigDecimal(request.getParameter("REPORT_ID")));
	reportSchedSC.setREPORT_REF(request.getParameter(RepConstantsCommon.SCHED_REPORT_REF));
	reportSchedCO = schedulerBO.findSingleReportSchedule(reportSchedSC);
	if(BigDecimal.valueOf(3).equals(reportSchedCO.getSAVE_DATA_TYPE()) && reportSchedCO.getMAIL_SERVER_CODE()==null)
	{
	    reportSchedCO.setUSE_DEFAULT_MS_YN("1");
	}
	return SUCCESS;
    }

    private void fillDays()
    {
	if(schedule.getScheduleDetails() == null)
	{
	    schedule.setScheduleDetails(new ArrayList<IRP_SCHED_DETAILSCO>());
	}
	if(days.isMON())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("2"));
	    schedule.getScheduleDetails().add(key);
	}
	if(days.isTUE())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("3"));
	    schedule.getScheduleDetails().add(key);
	}
	if(days.isWED())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("4"));
	    schedule.getScheduleDetails().add(key);
	}
	if(days.isTHU())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("5"));
	    schedule.getScheduleDetails().add(key);
	}
	if(days.isFRI())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("6"));
	    schedule.getScheduleDetails().add(key);
	}
	if(days.isSAT())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("7"));
	    schedule.getScheduleDetails().add(key);
	}
	if(days.isSUN())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(BigDecimal.ONE);
	    schedule.getScheduleDetails().add(key);
	}

    }

    private void fillMonths()
    {
	if(schedule.getScheduleDetails() == null)
	{
	    schedule.setScheduleDetails(new ArrayList<IRP_SCHED_DETAILSCO>());
	}
	if(months.isJAN())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(BigDecimal.ONE);
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isFEB())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("2"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isMAR())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("3"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isAPR())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("4"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isMAY())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("5"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isJUN())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("6"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isJUL())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("7"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isAUG())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("8"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isSEP())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("9"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isOCT())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(BigDecimal.TEN);
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isNOV())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("11"));
	    schedule.getScheduleDetails().add(key);
	}
	if(months.isDEC())
	{
	    IRP_SCHED_DETAILSCO key = new IRP_SCHED_DETAILSCO();
	    key.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    key.setFREQUENCY_OCCURENCE(new BigDecimal("12"));
	    schedule.getScheduleDetails().add(key);
	}
    }

    public String findReportByID() throws BaseException, IOException
    {
	try
	{
	    if(reportId == null || reportId.equals("") || reportId.equals("-9999999"))
	    {
		report = new IRP_AD_HOC_REPORTCO();
		// empty related combos
		SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
		sysVO.setVALUE_CODE("");
		sysVO.setVALUE_DESC("");
		emailFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		emailFeLst.add(sysVO);
		emailCIFLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		emailCIFLst.add(sysVO);
		emailFileNameLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		emailFileNameLst.add(sysVO);
		emailComputeFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		emailComputeFeLst.add(sysVO);
		passwordFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		passwordFeLst.add(sysVO);
	    }
	    else
	    {
		
		report = designerBO.returnReportById(new BigDecimal(reportId));

		if(report == null)
		{
		    report = new IRP_AD_HOC_REPORTCO();
		    // empty related combos
		    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
		    sysVO.setVALUE_CODE("");
		    sysVO.setVALUE_DESC("");
		    emailFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		    emailFeLst.add(sysVO);
		    emailCIFLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		    emailCIFLst.add(sysVO);
		    emailFileNameLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		    emailFileNameLst.add(sysVO);
		    emailComputeFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		    emailComputeFeLst.add(sysVO);
		    passwordFeLst = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
		    passwordFeLst.add(sysVO);

		}
		else
		{
		    report.setREPORT_ID(new BigDecimal(reportId));
		    
		    IRP_AD_HOC_QUERYCO qryObj = report.getQueriesList().get(0);
		    HashMap<Long, IRP_FIELDSCO> feMap = qryObj.getSqlObject().getDisplayFields();
		    IRP_FIELDSCO feCO;
		    SYS_PARAM_LOV_TRANSVO sysVO;

		    sysVO = new SYS_PARAM_LOV_TRANSVO();
		    sysVO.setVALUE_CODE("0");
		    sysVO.setVALUE_DESC(getText("sch.default"));
		    emailFileNameLst.add(sysVO);

		    SessionCO sessionCO = returnSessionObject();
		    HashMap<Long, IRP_FIELDSCO> varMap = designerBO.returnVariablesMapByReportId(new BigDecimal(reportId),sessionCO.getUserName());
		    Iterator itVar=varMap.values().iterator();
		    while(itVar.hasNext())
		    {
			feCO=(IRP_FIELDSCO)itVar.next();
			sysVO = new SYS_PARAM_LOV_TRANSVO();
			sysVO.setVALUE_CODE(feCO.getLabelAlias());
			sysVO.setVALUE_DESC(feCO.getFIELD_ALIAS());
			emailComputeFeLst.add(sysVO);
		    }
		    
		    // fill related combos
		    String entName;
		    Iterator it = feMap.values().iterator();
		    while(it.hasNext())
		    {
			feCO = (IRP_FIELDSCO) it.next();
			sysVO = new SYS_PARAM_LOV_TRANSVO();
			sysVO.setVALUE_CODE(feCO.getLabelAlias());
			entName = (feCO.getENTITY_ALIAS()) == null ? "" : feCO.getENTITY_ALIAS() + "."; 
			sysVO.setVALUE_DESC(entName + feCO.getFIELD_ALIAS());
			if("java.lang.String".equals(feCO.getFIELD_DATA_TYPE()))
			{
			    emailFeLst.add(sysVO);
			    passwordFeLst.add(sysVO);
			    emailFileNameLst.add(sysVO);
			}
			else if("java.math.BigDecimal".equals(feCO.getFIELD_DATA_TYPE()))
			{
			    emailCIFLst.add(sysVO);
			}
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String loadSchedReportGrid() throws BaseException
    {
	if(request.getParameter("SCHED_ID") == null || request.getParameter("SCHED_ID").equals(""))
	{
	    return SUCCESS;
	}
	if(request.getParameter("SCHED_ID").equals("0"))
	{
	    setGridModel(null);
	    return SUCCESS;
	}
	SessionCO sessionCO = returnSessionObject();
	copyproperties(reportSchedSC);
	reportSchedSC.setSCHED_ID(new BigDecimal(request.getParameter("SCHED_ID")));
	reportSchedSC.setLANG_CODE(sessionCO.getLanguage());
	reportSchedSC.setLovTypeId(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	List<IRP_REPORT_SCHEDULECO> reportsList = schedulerBO.findReportSchedules(reportSchedSC);
	if(checkNbRec(reportSchedSC))
	{
	    setRecords(schedulerBO.retReportSchedulesCount(reportSchedSC));
	}
	setGridModel(reportsList);

	return SUCCESS;
    }

    public String addSchedule() throws BaseException
    {
	try
	{
	    // convert the dates from string to date because the serialize does
	    // not take into consideration the Date that is why we put the dates
	    // as String
	    Date frstRunDateStr = schedule.getSCHED_VO().getFIRST_RUN_DATE();
	    schedule.getSCHED_VO().setFIRST_RUN_DATE(frstRunDateStr);
	    Date expiryDateStr = schedule.getSCHED_VO().getSCHED_EXPIRY_DATE();
	    schedule.getSCHED_VO().setSCHED_EXPIRY_DATE(expiryDateStr);

	    if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH) || schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ENDOFMONTH))
	    {
		fillMonths();
	    }
	    else if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_DAILY))
	    {
		fillDays();
	    }

	    BigDecimal schedId = commonRepFuncBO.retCounterValue("IRP_SCHEDULE");

	    // apply audit
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.SCHED_KEY_REF);
	    if(RepConstantsCommon.MODE_EDIT.equals(mode) && !BigDecimal.ZERO.equals(schedule.getSCHED_VO().getSCHED_ID()))
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		IRP_SCHEDULEVO oldSchedVO = (IRP_SCHEDULEVO) returnAuditObject(IRP_SCHEDULEVO.class);
		refCO.setAuditObj(oldSchedVO);
	    }
	    else
	    {
		refCO.setOperationType(AuditConstant.CREATED);
	    }
	    schedule.setAuditRefCO(refCO);

	    SessionCO sessionCO = returnSessionObject();
		schedulerSC.setPreferredLanguage(sessionCO.getLanguage());
	    schedulerBO.addSchedule(mode, schedule, schedulerSC, schedId);
	    // schedulerBO.retNextRunningDate(schedule);
	}
	catch(Exception e)
	{
	    //log.error("Error adding schedule");
	    if(e.getMessage() == null || e.getMessage().indexOf("2099") == -1)
	    {
		handleException(e, "Error adding schedule", "addSched.exceptionMsg._key");	
	    }
	    else
	    {
		handleException(e, null, null);
	    }
	}
	return SUCCESS;
    }
    
    public String retRepSchedCount() throws BaseException
    {
	if(request.getParameter("SCHED_ID").equals("") || request.getParameter("SCHED_ID") == null)
	{
	    return SUCCESS;
	}
	reportSchedSC.setSCHED_ID(new BigDecimal(request.getParameter("SCHED_ID")));
	if(request.getParameter("reportId") != null && !request.getParameter("reportId").equals(""))
	{
	    reportSchedSC.setREPORT_ID(new BigDecimal(request.getParameter("reportId")));
	}
	reportSchedSC.setREPORT_REF(updates);
	int cnt = schedulerBO.retReportSchedulesCount(reportSchedSC);
	if(cnt > 0)
	{
	    reportId = "-1";
	}
	return SUCCESS;
    }

    public String checkSchedIsRunning() throws BaseException
    {
	if(request.getParameter("SCHED_ID").equals("") || request.getParameter("SCHED_ID") == null)
	{
	    return SUCCESS;
	}

	reportSchedSC.setSCHED_ID(new BigDecimal(request.getParameter("SCHED_ID")));
	if(request.getParameter("reportId") != null && !request.getParameter("reportId").equals(""))
	{
	    reportSchedSC.setREPORT_ID(new BigDecimal(request.getParameter("reportId")));
	}

	int cnt = schedulerBO.checkSchedIsRunning(reportSchedSC);
	if(cnt > 0)
	{
	    reportId = "-1";
	}
	return SUCCESS;
    }

    public String deleteSchedule() throws BaseException
    {
	if(request.getParameter("SCHED_ID").equals("") || request.getParameter("SCHED_ID") == null)
	{
	    return SUCCESS;
	}
	// apply audit
	AuditRefCO refCO = initAuditRefCO();
	refCO.setOperationType(AuditConstant.DELETE);
	refCO.setKeyRef(AuditConstant.SCHED_KEY_REF);
	
	BigDecimal schedId = new BigDecimal(request.getParameter("SCHED_ID"));

	schedulerBO.deleteSchedule(schedId, refCO);
	return SUCCESS;
    }

    public String deleteReportSchedule() throws BaseException
    {
	if(request.getParameter("reportId").equals("") || request.getParameter("reportId") == null
		|| request.getParameter("SCHED_ID").equals("") || request.getParameter("SCHED_ID") == null)
	{
	    return SUCCESS;
	}
	BigDecimal repId = new BigDecimal(request.getParameter("reportId"));
	BigDecimal schedId = new BigDecimal(request.getParameter("SCHED_ID"));
	String repRef= request.getParameter(RepConstantsCommon.SCHED_REPORT_REF);
	IRP_REPORT_SCHEDULECO reSchedCO = new IRP_REPORT_SCHEDULECO();
	reSchedCO.setREPORT_ID(repId);
	reSchedCO.setSCHED_ID(schedId);
	reSchedCO.setREPORT_REF(repRef);
	// apply audit
	AuditRefCO refCO = initAuditRefCO();
	refCO.setKeyRef(AuditConstant.SCHED_KEY_REF);
	refCO.setOperationType(AuditConstant.UPDATE);

	schedulerBO.deleteReportSchedule(reSchedCO, refCO);
	return SUCCESS;
    }

    public String addReport() throws BaseException
    {
	if(request.getParameter("SCHED_ID") == null || request.getParameter("SCHED_ID").equals("")
		|| request.getParameter("SCHED_ID").equals("0"))
	{
	    return SUCCESS;
	}
	reportSchedCO.setSCHED_ID(new BigDecimal(request.getParameter("SCHED_ID")));

	reportSchedSC.setSCHED_ID(reportSchedCO.getSCHED_ID());
	reportSchedSC.setREPORT_ID(reportSchedCO.getREPORT_ID());

	reportSchedCO.setDATA_ONLY("N");
	if(reportSchedCO.getIS_PRINT().equals("false"))
	{
	    reportSchedCO.setPRINTER_NAME("");
	}
	if(reportSchedCO.getIS_ACTIVE().equals("true"))
	{
	    reportSchedCO.setIS_ACTIVE("Y");
	}
	else
	{
	    reportSchedCO.setIS_ACTIVE("N");
	}
	if("true".equals(reportSchedCO.getSHOW_HEAD_FOOT()))
	{
	    reportSchedCO.setSHOW_HEAD_FOOT("0");
	}
	else
	{
	    reportSchedCO.setSHOW_HEAD_FOOT("1");
	}

	if("true".equals(reportSchedCO.getSEND_IF_NO_DATA_YN()))
	{
	    reportSchedCO.setSEND_IF_NO_DATA_YN("0");
	}
	else
	{
	    reportSchedCO.setSEND_IF_NO_DATA_YN("1");
	}
	if("true".equals(reportSchedCO.getPRINT_IF_NO_DATA_YN()))
	{
	    reportSchedCO.setPRINT_IF_NO_DATA_YN("0");
	}
	else
	{
	    reportSchedCO.setPRINT_IF_NO_DATA_YN("1");
	}
	if("true".equals(reportSchedCO.getSECURED_FILE_YN()))
	{
	    reportSchedCO.setSECURED_FILE_YN("1");
	}
	else
	{
	    reportSchedCO.setSECURED_FILE_YN("0");
	    reportSchedCO.setSECURED_PWD_FIELD_NAME("");
	}

	if(!ConstantsCommon.CSV_REP_FORMAT.equals(reportSchedCO.getREPORT_FORMAT()) && !ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(reportSchedCO.getREPORT_FORMAT()))
	{
	    reportSchedCO.setCSV_SEPARATOR("");
	}
	// apply audit
	AuditRefCO refCO = initAuditRefCO();
	refCO.setKeyRef(AuditConstant.SCHED_KEY_REF);
	refCO.setOperationType(AuditConstant.UPDATE);
	reportSchedCO.setAuditRefCO(refCO);

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList = repSessionCO
		.getRepScheMailGrpByList();

	HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = repSessionCO
		.getRepSchedMailUsrsMap();

	// to be set from the jsp later
	reportSchedCO.setEMAIL_CONSOLIDATED_YN("0"); // not consolidated
	IRP_REPORT_SCHEDULEVO repSchedVO = convertRepSchedCOToVO(reportSchedCO);
	if( RepConstantsCommon.MODE_ADD.equals(detMode))
	{
	    int cnt = schedulerBO.retReportSchedulesCount(reportSchedSC);
	    if(cnt > 0)
	    {
		return SUCCESS;
	    }
	    schedulerBO.insertReportSchedule(repSchedVO, repScheMailGrpByList, repSchedMailUsrsMap);
	}
	else
	{
	    schedulerBO.updateReportSchedule(repSchedVO, repScheMailGrpByList, repSchedMailUsrsMap);
	}

	return SUCCESS;
    }

    /**
    This method will convert the co into vo in order to use the genericDAO
    */
    private IRP_REPORT_SCHEDULEVO convertRepSchedCOToVO(IRP_REPORT_SCHEDULECO co)
    {
	IRP_REPORT_SCHEDULEVO vo= new IRP_REPORT_SCHEDULEVO();
	try
	{
	    vo.setSCHED_ID(co.getSCHED_ID());
	    vo.setREPORT_ID(co.getREPORT_ID());
	    vo.setREPORT_FORMAT(co.getREPORT_FORMAT());
	    vo.setIS_ACTIVE(co.getIS_ACTIVE());
	    vo.setPRINTER_NAME(co.getPRINTER_NAME());
	    vo.setDATA_ONLY(co.getDATA_ONLY());
	    vo.setCSV_SEPARATOR(co.getCSV_SEPARATOR());
	    vo.setSHOW_HEAD_FOOT(co.getSHOW_HEAD_FOOT());
	    vo.setSAVE_DATA_TYPE(co.getSAVE_DATA_TYPE());
	    vo.setREPORT_LANGUAGE(co.getREPORT_LANGUAGE());
	    vo.setMAIL_SERVER_CODE(co.getMAIL_SERVER_CODE());
	    vo.setFROM_EMAIL_TYPE(co.getFROM_EMAIL_TYPE());
	    vo.setFROM_EMAIL_VAL(co.getFROM_EMAIL_VAL());
	    vo.setTO_EMAIL_TYPE(co.getTO_EMAIL_TYPE());
	    vo.setTO_EMAIL_VAL(co.getTO_EMAIL_VAL());
	    vo.setCC_EMAIL_TYPE(co.getCC_EMAIL_TYPE());
	    vo.setCC_EMAIL_VAL(co.getCC_EMAIL_VAL());
	    vo.setBCC_EMAIL_TYPE(co.getBCC_EMAIL_TYPE());
	    vo.setBCC_EMAIL_VAL(co.getBCC_EMAIL_VAL());
	    vo.setEMAIL_SUBJECT(co.getEMAIL_SUBJECT());
	    vo.setEMAIL_BODY(co.getEMAIL_BODY());
	    vo.setATTACH_FILE_NAME(co.getATTACH_FILE_NAME());
	    vo.setSEND_IF_NO_DATA_YN(co.getSEND_IF_NO_DATA_YN());
	    vo.setPRINT_IF_NO_DATA_YN(co.getPRINT_IF_NO_DATA_YN());
	    vo.setEMAIL_CONSOLIDATED_YN(co.getEMAIL_CONSOLIDATED_YN());
	    vo.setDATE_TYPE(co.getDATE_TYPE());
	    vo.setREPORT_REF(co.getREPORT_REF());
	    vo.setIS_FCR_YN(co.getIS_FCR_YN());
	    vo.setSECURED_PWD_FIELD_NAME(co.getSECURED_PWD_FIELD_NAME());
	    vo.setSECURED_FILE_YN(co.getSECURED_FILE_YN());
	    vo.setAuditRefCO(co.getAuditRefCO());
	}
	catch(Exception e)
	{
	    handleException(e, "", "");
	}
	return vo;
    }
    
    private void findSelectedDays(List<IRP_SCHED_DETAILSCO> details)
    {
	for(IRP_SCHED_DETAILSCO schedDet : details)
	{
	    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(2)))
	    {
		days.setMON(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(3)))
	    {
		days.setTUE(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(4)))
	    {
		days.setWED(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(5)))
	    {
		days.setTHU(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(6)))
	    {
		days.setFRI(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(7)))
	    {
		days.setSAT(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(BigDecimal.ONE))
	    {
		days.setSUN(true);
	    }
	}
    }

    private void findSelectedMonths(List<IRP_SCHED_DETAILSCO> details)
    {
	for(IRP_SCHED_DETAILSCO schedDet : details)
	{
	    if(schedDet.getFREQUENCY_OCCURENCE().equals(BigDecimal.ONE))
	    {
		months.setJAN(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(2)))
	    {
		months.setFEB(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(3)))
	    {
		months.setMAR(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(4)))
	    {
		months.setAPR(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(5)))
	    {
		months.setMAY(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(6)))
	    {
		months.setJUN(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(7)))
	    {
		months.setJUL(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(8)))
	    {
		months.setAUG(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(9)))
	    {
		months.setSEP(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(BigDecimal.TEN))
	    {
		months.setOCT(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(11)))
	    {
		months.setNOV(true);
	    }
	    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(12)))
	    {
		months.setDEC(true);
	    }
	}
    }

    public ArrayList<IRP_REPORT_SCHED_PARAMSVO> retSchedParams()  
    {
	ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList = new ArrayList<IRP_REPORT_SCHED_PARAMSVO>();
	try
	{
	    BigDecimal schedId = getSchedId();
	    BigDecimal repId = new BigDecimal(getReportId());
	    //return the id of each sched param from the table irp_rep_arguments
	    IRP_REPORT_SCHED_PARAMSSC paramSC = new IRP_REPORT_SCHED_PARAMSSC();
	    paramSC.setREPORT_ID(repId);
	    HashMap<String ,IRP_REP_ARGUMENTSCO> repArgsMap=schedulerBO.retRepMainArgsMap(paramSC);
	   
	    String flag;
	    String valueOn;
	    String valueOff;
	    Enumeration enu = request.getParameterNames();
	    IRP_REP_ARGUMENTSCO argCO;
	    String paramName;
	    IRP_REPORT_SCHED_PARAMSVO paramVO;
	    String reportRef=getUpdates();
	    if(reportRef.isEmpty())
	    {
		reportRef=ConstantsCommon.ZERO;
	    }
	    while(enu.hasMoreElements())
	    {
		String itemName = (String) enu.nextElement();
		String itemValue = request.getParameter(itemName);
		if(itemName != null && itemName.indexOf("param~") == 0 && request.getParameter("__checkbox_"+itemName)==null)
		{
		    paramName=itemName.split("~")[1];
		    argCO=repArgsMap.get(paramName);
		    // if argumentName  has not been changed
		    if(argCO != null)
		    {
			paramVO = new IRP_REPORT_SCHED_PARAMSVO();
			paramVO.setPARAM_ID(argCO.getARGUMENT_ID());
			paramVO.setSCHED_ID(schedId);
			paramVO.setREPORT_ID(repId);
			paramVO.setPARAM_NAME(paramName);
			paramVO.setPARAM_TYPE(itemName.split("~")[2]);
			paramVO.setPARAM_VALUE(itemValue);
			paramVO.setREPORT_REF(reportRef);
			paramsList.add(paramVO);
		    }
		}
		else if(itemName.startsWith("__checkbox_paramsFlag"))
		{
		    flag = itemName.substring(11);

		    itemName = flag.substring(11, flag.length() - 4);
		    // if argumentName has not been changed
		    argCO = repArgsMap.get(itemName);
		    if(argCO != null)
		    {
			valueOn = flag.substring(flag.length() - 3, flag.length() - 2);
			valueOff = flag.substring(flag.length() - 1, flag.length());

			paramVO = new IRP_REPORT_SCHED_PARAMSVO();
			paramVO.setPARAM_ID(argCO.getARGUMENT_ID());
			paramVO.setSCHED_ID(schedId);
			paramVO.setREPORT_ID(repId);
			paramVO.setPARAM_NAME(itemName);
			paramVO.setPARAM_TYPE(ConstantsCommon.PARAM_TYPE_VARCHAR2);
			paramVO.setREPORT_REF(reportRef);
			if(request.getParameter(flag) == null)
			{
			    paramVO.setPARAM_VALUE(valueOff);
			}
			else
			{
			    paramVO.setPARAM_VALUE(valueOn);
			}
			paramsList.add(paramVO);
		    }
		}//__multiselect_param~RA_CONTRIBUTOR~VARCHAR2
		else if(itemName.startsWith("__checkbox_param"))
		{
		    // if argumentName has not been changed
		    paramName = itemName.split("~")[1];
		    argCO = repArgsMap.get(paramName);
		    if(argCO != null)
		    {
			paramVO = new IRP_REPORT_SCHED_PARAMSVO();
			paramVO.setPARAM_ID(argCO.getARGUMENT_ID());
			paramVO.setSCHED_ID(schedId);
			paramVO.setREPORT_ID(repId);
			paramVO.setPARAM_NAME(paramName);
			paramVO.setPARAM_TYPE(itemName.split("~")[2]);
			paramVO.setREPORT_REF(reportRef);
			flag = itemName.substring(11);
			if(request.getParameter(flag) == null)
			{
			    paramVO.setPARAM_VALUE(ConstantsCommon.NO);
			}
			else
			{
			    paramVO.setPARAM_VALUE(ConstantsCommon.YES);
			}
			paramsList.add(paramVO);
		    }
		}
		else if(itemName.startsWith(ConstantsCommon.PARAM_H))
		{
		    paramName = itemName.split("~")[1];
		    argCO = repArgsMap.get(paramName);
		    if(argCO != null)
		    {
			String argType = itemName.split("~")[2];
			Map parameters = new HashMap<String, Object>();
			parameters = commonReportingBO.fillParametersWithCollection(itemName, itemValue, parameters,
				argType, 1);
			paramVO = new IRP_REPORT_SCHED_PARAMSVO();
			paramVO.setPARAM_ID(argCO.getARGUMENT_ID());
			paramVO.setSCHED_ID(schedId);
			paramVO.setREPORT_ID(repId);
			paramVO.setPARAM_NAME(paramName);
			paramVO.setPARAM_TYPE(argType);
			paramVO.setREPORT_REF(reportRef);
			StringBuffer sb = new StringBuffer();
			ArrayList<Object> lstValues = (ArrayList<Object>) parameters.get(itemName);
			String val;
			for(int i = 0; i < lstValues.size(); i++)
			{
			    val = lstValues.get(i).toString();
			    if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(argType))
			    {
				val = val.replace("\"", "\"\"");
				sb.append("\"" + val + "\"");
			    }
			    else
			    {
				sb.append(val);
			    }
			    if(i < lstValues.size() - 1)
			    {
				sb.append(",");
			    }
			}
			paramVO.setPARAM_VALUE(sb.toString());
			paramsList.add(paramVO);
		    }
		}
		else if(itemName.startsWith("combosValues"))
		{
		    paramName=itemName.substring(itemName.indexOf(".")+1,itemName.length());
		    argCO=repArgsMap.get(paramName);
		    // if argumentName  has not been changed
		    if(argCO != null)
		    {
			paramVO = new IRP_REPORT_SCHED_PARAMSVO();
			paramVO.setPARAM_ID(argCO.getARGUMENT_ID());
			paramVO.setSCHED_ID(schedId);
			paramVO.setREPORT_ID(repId);
			paramVO.setPARAM_NAME(paramName);
			paramVO.setPARAM_TYPE(argCO.getARGUMENT_TYPE());
			paramVO.setPARAM_VALUE(itemValue);
			paramVO.setREPORT_REF(reportRef);
			paramsList.add(paramVO);
		    }		
		}
	    }}
	catch(Exception e)
	{
	    //log.error(e, "============error in retSchedParams ===========");
	    handleException(e, null, null);
	}
	return paramsList;
    }

    public String saveSchedParams()
    {
	try
	{
	    ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList = retSchedParams();
	    schedulerBO.saveSchedParams(paramsList);
	}
	catch(Exception e)
	{
	   // log.error(e, "============error in saveSchedParams ===========");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String updateSchedParams() 
    {
	try
	{
	    // delete reportSchedParam
	    ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList = retSchedParams();
	    BigDecimal repId = new BigDecimal(getReportId());
	    IRP_REPORT_SCHED_PARAMSSC paramSC = new IRP_REPORT_SCHED_PARAMSSC();
	    paramSC.setREPORT_ID(repId);
	    paramSC.setSCHED_ID(schedId);
	    String reportRef=getUpdates();
	    if(reportRef.isEmpty())
	    {
		reportRef=ConstantsCommon.ZERO;
	    }
	    paramSC.setREPORT_REF(reportRef);
	    schedulerBO.updateSchedParams(paramSC, paramsList);
	}
	catch(Exception e)
	{
	    //log.error("Error updating scheduler parameters(updateSchedParams)");
	    handleException(e, "Error updating scheduler parameters", "updateSchedParams.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public BigDecimal getSchedId()
    {
	return schedId;
    }

    public void setSchedId(BigDecimal schedId)
    {
	this.schedId = schedId;
    }

    public String getHiddenMenuId()
    {
	return hiddenMenuId;
    }

    public void setHiddenMenuId(String hiddenMenuId)
    {
	this.hiddenMenuId = hiddenMenuId;
    }

    /**
     * Method that returns the report's parameters
     * @return
     */
    public String retParamIds()
    {
	try
	{
	    BigDecimal repId = new BigDecimal(getReportId());
	    IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	    reportSC.setREPORT_ID(repId);
	    IRP_AD_HOC_REPORTCO repCO = designerBO.retProgRefByRepId(reportSC);

	    StringBuffer sbRepParamIds = new StringBuffer("");
	    IRP_REP_ARGUMENTSCO argCO;
	    List<IRP_REP_ARGUMENTSCO> argumentsList = designerBO.retArgsByReport(reportSC);
	    for(int i = 0; i < argumentsList.size(); i++)
	    {
		argCO = argumentsList.get(i);
		//if central bank report and hidden don't check if filled
		if(RepConstantsCommon.ONE.equals(repCO.getREPORT_TYPE())
			&& RepConstantsCommon.N.equals(argCO.getDISPLAY_FLAG()))
		{
		    continue;
		}
		if(argumentsList.get(i).getARGUMENT_SOURCE().equals(new BigDecimal(3)) || argumentsList.get(i).getARGUMENT_SOURCE().equals(new BigDecimal(10)))
		{
		    //handling multi livesearch
		    if(new BigDecimal(RepConstantsCommon.ONE).equals(argCO.getMULTISELECT_YN())
			    && !RepConstantsCommon.N.equals(argCO.getDISPLAY_FLAG())/*SCHED_ROOT_SYNTAX*/)
		    {
			sbRepParamIds
				.append(ConstantsCommon.P_PARAM_UNDERSCORE + RepConstantsCommon.SCHED_CONST_ID
					+ argCO.getARGUMENT_NAME() + "_" + argCO.getARGUMENT_TYPE() + "_"
					+ repCO.getPROG_REF()+",");

		    }
		    else if(ConstantsCommon.ARG_QRY_COMBO.equals(argCO.getARG_QUERY_OPTIONS()))
		    {
			sbRepParamIds.append(ConstantsCommon.P_PARAM_UNDERSCORE + RepConstantsCommon.SCHED_CONST_ID
				+ argCO.getARGUMENT_NAME() + "_" + argCO.getARGUMENT_TYPE() + "_" + repCO.getPROG_REF()
				+ ",");
		    }
		    else
		    {
			if(!RepConstantsCommon.N.equals(argCO.getDISPLAY_FLAG()))
			{
			    sbRepParamIds.append(RepConstantsCommon.LS_LOOKUPTXT);
			}
			sbRepParamIds.append(ConstantsCommon.P_PARAM_UNDERSCORE + RepConstantsCommon.SCHED_CONST_ID);
			sbRepParamIds.append(argCO.getARGUMENT_NAME());
			sbRepParamIds.append("_");
			sbRepParamIds.append(argCO.getARGUMENT_TYPE());
			sbRepParamIds.append("_");
			sbRepParamIds.append(StringUtil.replaceInString(repCO.getPROG_REF(), "-", "_"));
			sbRepParamIds.append(",");
		    }
		}
		else
		{
		    if(!argumentsList.get(i).getARGUMENT_SOURCE().equals(new BigDecimal(4)))
		    {
			
			sbRepParamIds.append("p_param_"+RepConstantsCommon.SCHED_CONST_ID);
			sbRepParamIds.append(argCO.getARGUMENT_NAME());
			sbRepParamIds.append("_" );
			sbRepParamIds.append(argCO.getARGUMENT_TYPE());
			sbRepParamIds.append("_");
			sbRepParamIds.append(StringUtil.replaceInString(repCO.getPROG_REF(), "-", "_"));
			sbRepParamIds.append(",");// updated following the
			// dynamicRepParmsAction.java
			// changes
		    }
		}
	    }
	    
	    if(sbRepParamIds.indexOf(RepConstantsCommon.PARAM_OL_ERROR_FCR) != -1)
	    {
		sbRepParamIds = removeParamsFromSb(sbRepParamIds, RepConstantsCommon.PARAM_OL_ERROR_FCR);
	    }
	    if(sbRepParamIds.indexOf(RepConstantsCommon.PARAM_OS_MESSAGE_FCR) != -1)
	    {
		sbRepParamIds = removeParamsFromSb(sbRepParamIds, RepConstantsCommon.PARAM_OS_MESSAGE_FCR);
	    }
	    if(sbRepParamIds.indexOf(RepConstantsCommon.PARAM_BASED_ON_REP_CURRENCY) != -1)
	    {
		sbRepParamIds = removeParamsFromSb(sbRepParamIds, RepConstantsCommon.PARAM_BASED_ON_REP_CURRENCY);
	    }
	    
	    String repParamIds = sbRepParamIds.toString();

	    if(repParamIds.length() > 0)
	    {
		repParamIds = repParamIds.substring(0, repParamIds.length() - 1);
	    }
	    setUpdates(repParamIds);
	}
	catch(Exception e)
	{
	    //log.error("Error returning parameters ids");
	    handleException(e, "Error returning parameters id", "returnParamsId.exceptionMsg._key");
	}

	return SUCCESS;
    }
    
    /***
     * Method that remove a parameter from the list of parameters
     * @param sbRepParamIds
     * @param removeStr
     * @return
     * @throws Exception
     */
    private StringBuffer removeParamsFromSb(StringBuffer sbRepParamIds, String removeStr) throws Exception
    {
	String firstPart = sbRepParamIds.substring(0, sbRepParamIds.indexOf(removeStr));
	String tmpStr = sbRepParamIds.substring(sbRepParamIds.indexOf(removeStr), sbRepParamIds.length());
	String secondPart = tmpStr.substring(tmpStr.indexOf(",") + 1, tmpStr.length());
	StringBuffer sbParamIds = new StringBuffer();
	sbParamIds.append(firstPart + secondPart);
	return sbParamIds;
    }

    public String retProgRefByRepId() throws JSONException
    {
	try
	{
	    IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	    reportSC.setREPORT_ID(new BigDecimal(reportId));
	    IRP_AD_HOC_REPORTCO repCO = designerBO.retProgRefByRepId(reportSC);
	    if(repCO == null)
	    {
		setUpdates("wronggggg");
	    }
	    else
	    {
		setUpdates(repCO.getPROG_REF());
	    }
	}
	catch(Exception e)
	{
	    //log.error("Error returning report reference");
	    handleException(e, "Error returning report reference", "returnReportRef.exceptionMsg._key");
	    setUpdates("wronggggg");
	}
	return SUCCESS;
    }

    public List<SepartorFormat> getCsvSeparators()
    {
	ArrayList<SepartorFormat> csvSeparatorsList = new ArrayList<SepartorFormat>();
	csvSeparatorsList.add(new SepartorFormat(",", ","));
	csvSeparatorsList.add(new SepartorFormat(getText("reporting.tab"), "\\t"));
	return csvSeparatorsList;
    }

    public String loadMailFeGroupByList() throws Exception // load the grid
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    if("true".equals(updates)) // when dbl click a new report
	    {
		repSessionCO.setRepScheMailGrpByList(new LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO>());
		repSessionCO
			.setRepSchedMailUsrsMap(new HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>>());
	    }
	    if(getReportId() == null || NumberUtil.isEmptyDecimal(new BigDecimal(getReportId())) || scheduleId == null
		    || NumberUtil.isEmptyDecimal(scheduleId))
	    {
		setRecords(0);
		setGridModel(new ArrayList<IRP_REP_SCHED_MAIL_GROUP_BYCO>());
		repSessionCO.setRepScheMailGrpByList(new LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO>());
		repSessionCO
			.setRepSchedMailUsrsMap(new HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>>());
	    }
	    else
	    {
		List<IRP_REP_SCHED_MAIL_GROUP_BYCO> lst;
		int cnt;
		LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList = repSessionCO
			.getRepScheMailGrpByList();
		LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> newGrpMap = new LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO>();
		if(repScheMailGrpByList.isEmpty() && updates2 == null)
		{
		    BigDecimal repId = new BigDecimal(getReportId());
		    copyproperties(mailGrpSC);
		    mailGrpSC.setSCHED_ID(scheduleId);
		    mailGrpSC.setREPORT_ID(repId);
		    // return the saved grps
		    lst = schedulerBO.retMailFeGroupByList(mailGrpSC);
		    cnt = schedulerBO.retMailFeGroupByCnt(mailGrpSC);

		    // get the reportCO in order to retrieve the field
		    // description from it and set it the grpby grid
		    if(!lst.isEmpty())
		    {
			HashMap<String, String> feDetMap = new HashMap<String, String>();
			HashMap<Long, IRP_FIELDSCO> feMap = returnFieldsMapByReportId(repId);
			Iterator it = feMap.values().iterator();
			IRP_FIELDSCO feCO;
			String entName;
			while(it.hasNext())
			{
			    feCO = (IRP_FIELDSCO) it.next();
			    entName = (feCO.getENTITY_ALIAS()) == null ? feCO.getFIELD_ALIAS() : feCO.getENTITY_ALIAS()
				    + "." + feCO.getFIELD_ALIAS();
			    feDetMap.put(feCO.getLabelAlias(), entName);
			}

			IRP_REP_SCHED_MAIL_GROUP_BYCO grpCO;
			for(int i = 0; i < lst.size(); i++)
			{
			    grpCO = lst.get(i);
			    grpCO.setFIELD_DESC(feDetMap.get(grpCO.getMailGrpVO().getFIELD_ALIAS()));
			    newGrpMap.put(grpCO.getMailGrpVO().getFIELD_ALIAS(), grpCO);
			}
			repSessionCO.setRepScheMailGrpByList(newGrpMap);
		    }
		}
		else
		{
		    lst = new ArrayList(repScheMailGrpByList.values());
		    cnt = repScheMailGrpByList.size();
		}
		setRecords(cnt);
		setGridModel(lst);
	    }

	}
	catch(Exception e)
	{
	    //log.error(e, "============error loading the grid ===========");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadMailFeGroupByCmb() throws Exception
    {
	try
	{
	    SYS_PARAM_LOV_TRANSVO sysVO;
	    BigDecimal repId = new BigDecimal(reportId);
	    HashMap<Long, IRP_FIELDSCO> feMap = returnFieldsMapByReportId(repId);
	    Iterator it = feMap.values().iterator();
	    IRP_FIELDSCO feCO;
	    String entName;
	    // fill empty value at the begining since when the combo is filled
	    // ,the first element will be selected by default
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("");
	    sysVO.setVALUE_DESC("");
	    mailFeGroupByCmbLst.add(sysVO);
	    while(it.hasNext())
	    {
		feCO = (IRP_FIELDSCO) it.next();
		sysVO = new SYS_PARAM_LOV_TRANSVO();
		sysVO.setVALUE_CODE(feCO.getLabelAlias());
		entName = (feCO.getENTITY_ALIAS()) == null ? "" : feCO.getENTITY_ALIAS() + ".";
		sysVO.setVALUE_DESC(entName + feCO.getFIELD_ALIAS());
		mailFeGroupByCmbLst.add(sysVO);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String addMailFeGroupBy() throws Exception
    {
	try
	{
	    if(updates1 != null && !updates1.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates1, IRP_REP_SCHED_MAIL_GROUP_BYCO.class, true);
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList = repSessionCO
			.getRepScheMailGrpByList();
		repScheMailGrpByList.clear();
		List<IRP_REP_SCHED_MAIL_GROUP_BYCO> allRec = gu.getLstAllRec();
		IRP_REP_SCHED_MAIL_GROUP_BYCO grpCO;
		BigDecimal repId = new BigDecimal(reportId);
		int cnt = 0;
		String feAlias;
		for(int i = 0; i < allRec.size(); i++)
		{
		    grpCO = allRec.get(i);
		    grpCO.getMailGrpVO().setREPORT_ID(repId);
		    grpCO.getMailGrpVO().setSCHED_ID(scheduleId);
		    grpCO.getMailGrpVO().setGRP_ORDER(new BigDecimal(++cnt));
		    feAlias = grpCO.getMailGrpVO().getFIELD_ALIAS();
		    if(!"".equals(feAlias) && repScheMailGrpByList.get(feAlias) == null)
		    {
			repScheMailGrpByList.put(grpCO.getMailGrpVO().getFIELD_ALIAS(), grpCO);
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String deleteMailFeGroupBy() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList = repSessionCO
		    .getRepScheMailGrpByList();
	    repScheMailGrpByList.remove(updates);
	    // update orders
	    int cnt = 0;
	    IRP_REP_SCHED_MAIL_GROUP_BYCO grpCO;
	    Iterator it = repScheMailGrpByList.values().iterator();
	    while(it.hasNext())
	    {
		grpCO = (IRP_REP_SCHED_MAIL_GROUP_BYCO) it.next();
		grpCO.getMailGrpVO().setGRP_ORDER(new BigDecimal(++cnt));
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public HashMap<Long, IRP_FIELDSCO> returnFieldsMapByReportId(BigDecimal reportId)
    {
	HashMap<Long, IRP_FIELDSCO> feMap = new HashMap<Long, IRP_FIELDSCO>();
	try
	{
	    IRP_AD_HOC_REPORTCO repCO = designerBO.returnReportById(reportId);
	    if(repCO != null)
	    {
		IRP_AD_HOC_QUERYCO qryObj = repCO.getQueriesList().get(0);
		feMap = qryObj.getSqlObject().getDisplayFields();
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return feMap;
    }

    public String loadMailCombos() throws Exception
    {
	try
	{
	    setUpdates1("delInFile");// only added cs the callDependency
	    // function is not take into consideration
	    // the dependency in the first two params
	    setUpdates2("delInFile2");
	    IRP_FIELDSCO feCO;
	    SYS_PARAM_LOV_TRANSVO sysVO;
	    HashMap<Long, IRP_FIELDSCO> feMap = returnFieldsMapByReportId(new BigDecimal(reportId));
	    SessionCO sessionCO = returnSessionObject();
	    HashMap<Long, IRP_FIELDSCO> varMap = designerBO.returnVariablesMapByReportId(new BigDecimal(reportId),sessionCO.getUserName());
	    Iterator itVar=varMap.values().iterator();
	    while(itVar.hasNext())
	    {
		feCO=(IRP_FIELDSCO)itVar.next();
		sysVO = new SYS_PARAM_LOV_TRANSVO();
		sysVO.setVALUE_CODE(feCO.getLabelAlias());
		sysVO.setVALUE_DESC(feCO.getFIELD_ALIAS());
		emailComputeFeLst.add(sysVO);
	    }
	    
	  

	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("0");
	    sysVO.setVALUE_DESC(getText("sch.default"));
	    emailFileNameLst.add(sysVO);

	    // fill related combos
	    String entName;
	    Iterator it = feMap.values().iterator();
	    while(it.hasNext())
	    {
		feCO = (IRP_FIELDSCO) it.next();
		sysVO = new SYS_PARAM_LOV_TRANSVO();
		sysVO.setVALUE_CODE(feCO.getLabelAlias());
		entName = (feCO.getENTITY_ALIAS()) == null ? "" : feCO.getENTITY_ALIAS() + "."; // check
		sysVO.setVALUE_DESC(entName + feCO.getFIELD_ALIAS());
		if("java.lang.String".equals(feCO.getFIELD_DATA_TYPE()))
		{
		    emailFeLst.add(sysVO);
		    passwordFeLst.add(sysVO);
		    emailFileNameLst.add(sysVO);
		}
		else if("java.math.BigDecimal".equals(feCO.getFIELD_DATA_TYPE()))
		{
		    emailCIFLst.add(sysVO);
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String addCIFMailFeGroupBy() throws Exception
    {
	try
	{
	    IRP_REP_SCHED_MAIL_GROUP_BYCO grpCO;
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList = repSessionCO
		    .getRepScheMailGrpByList();

	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> prevMap = new LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO>();
	    prevMap.putAll(repScheMailGrpByList);

	    repScheMailGrpByList.clear();

	    grpCO = new IRP_REP_SCHED_MAIL_GROUP_BYCO();
	    grpCO.getMailGrpVO().setFIELD_ALIAS(updates);
	    grpCO.getMailGrpVO().setSCHED_ID(scheduleId);
	    grpCO.getMailGrpVO().setREPORT_ID(new BigDecimal(reportId));
	    grpCO.setFIELD_DESC(updates2.replace("\n", ""));
	    repScheMailGrpByList.put(updates, grpCO);

	    repScheMailGrpByList.putAll(prevMap);
	    // update orders
	    int cnt = 0;

	    Iterator it = repScheMailGrpByList.values().iterator();
	    while(it.hasNext())
	    {
		grpCO = (IRP_REP_SCHED_MAIL_GROUP_BYCO) it.next();
		grpCO.getMailGrpVO().setGRP_ORDER(new BigDecimal(++cnt));
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String loadSchedMailUsers()
    {
	return "successUsrsLst";
    }

    public String loadSchedMailUsersList() // load the grid
    {
	try
	{
	    // 1 : to ; 2:cc ; 3:bcc
	    List<IRP_REP_SCHED_MAIL_RECEIVERSVO> lstUsrs;
	    if(reportId == null || "".equals(reportId) || NumberUtil.isEmptyDecimal(scheduleId))
	    {
		lstUsrs = new ArrayList<IRP_REP_SCHED_MAIL_RECEIVERSVO>();
	    }
	    else
	    {

		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = repSessionCO
			.getRepSchedMailUsrsMap();
		if(repSchedMailUsrsMap.get(updates) == null)
		{
		    IRP_REP_SCHED_MAIL_RECEIVERSVO recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
		    recVO.setRECEIVER_TYPE(new BigDecimal(updates));
		    recVO.setSCHED_ID(scheduleId);
		    recVO.setREPORT_ID(new BigDecimal(reportId));
		    lstUsrs = schedulerBO.retSchedMailUsersList(recVO);
//		    cnt = schedulerBO.retSchedMailUsersCnt(recVO);

		    repSchedMailUsrsMap.put(updates, fillSessionUsrMap(lstUsrs));
		}
		else
		{
		    lstUsrs = new ArrayList(repSchedMailUsrsMap.get(updates).values());
//		    cnt = lstUsrs.size();
		}

	    }

	    copyproperties(mailGrpSC);

	    int nbRec = mailGrpSC.getNbRec();
	    int recToSkip = mailGrpSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(lstUsrs.size() < maxRec)
	    {
		maxRec = lstUsrs.size();
	    }
	    ArrayList<IRP_REP_SCHED_MAIL_RECEIVERSVO> lst = new ArrayList<IRP_REP_SCHED_MAIL_RECEIVERSVO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add(lstUsrs.get(i));
	    }

	    setRecords(lstUsrs.size());
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> fillSessionUsrMap(
	    List<IRP_REP_SCHED_MAIL_RECEIVERSVO> lstUsrs)
    {
	LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> usrMap = new LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>();
	IRP_REP_SCHED_MAIL_RECEIVERSVO usrVO;
	for(int i = 0; i < lstUsrs.size(); i++)
	{
	    usrVO = lstUsrs.get(i);
	    if(usrVO.getUSER_ID() != null && !"".equals(usrVO.getUSER_ID()))
	    {
		usrMap.put(usrVO.getUSER_ID(), usrVO);
	    }
	}
	return usrMap;
    }

    public String mailUsersListLkp()
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "USER_ID" };
	    String[] colType = { "text" };
	    String[] titles = { getText("ms.userName") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("");
	    grid.setRowNum("10");
	    grid.setUrl("/path/scheduler/scheduler_mailUsersList.action");

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
	    lookup(grid, lsGridColumn, null, usrSC);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String mailUsersList()
    {
	try
	{
	    copyproperties(usrSC);
	    setSearchFilter(usrSC);

	    List<USRVO> usrsList = usrBO.getUserList(usrSC);
	    int usrCnt = usrBO.getUserCount(usrSC);
	    setRecords(usrCnt);
	    setGridModel(usrsList);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;

    }

    public String addMailUser()
    {
	try
	{
	    if(updates1 != null && !updates1.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates1, IRP_REP_SCHED_MAIL_RECEIVERSVO.class, true);
		List<IRP_REP_SCHED_MAIL_RECEIVERSVO> lstAll = gu.getLstAllRec();
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = repSessionCO
			.getRepSchedMailUsrsMap();
		repSchedMailUsrsMap.remove(updates);
		repSchedMailUsrsMap.put(updates, fillSessionUsrMap(lstAll));
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String deleteMailUser()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = repSessionCO
		    .getRepSchedMailUsrsMap();
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> usrMap = repSchedMailUsrsMap.get(updates1);
	    if(usrMap != null)
	    {
		usrMap.remove(updates);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String loadAllUsers()
    {

	try
	{
	    BigDecimal repId = new BigDecimal(getReportId());
	    BigDecimal schedId = getScheduleId();
	    BigDecimal reciepType = new BigDecimal(getUpdates());
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = repSessionCO
		    .getRepSchedMailUsrsMap();
	    // empty recipientType
	    repSchedMailUsrsMap.remove(getUpdates());

	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> reciepMap = new LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>();
	    UsrSC userSC = new UsrSC();
	    userSC.setNbRec(-1);
	    List<USRVO> usrsList = usrBO.getUserList(userSC);
	    USRVO usrVO;
	    IRP_REP_SCHED_MAIL_RECEIVERSVO recVO;
	    for(int i = 0; i < usrsList.size(); i++)
	    {
		usrVO = usrsList.get(i);
		recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
		recVO.setUSER_ID(usrVO.getUSER_ID());
		recVO.setRECEIVER_TYPE(reciepType);
		recVO.setREPORT_ID(repId);
		recVO.setSCHED_ID(schedId);
		reciepMap.put(recVO.getUSER_ID(), recVO);
	    }
	    repSchedMailUsrsMap.put(getUpdates(), reciepMap);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String retUsrsLstCnt()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = repSessionCO
		    .getRepSchedMailUsrsMap();
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> toUsrMap = repSchedMailUsrsMap.get("1");
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> ccUsrMap = repSchedMailUsrsMap.get("2");
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> bccUsrMap = repSchedMailUsrsMap.get("3");

	    if((toUsrMap == null || toUsrMap.isEmpty()) && (ccUsrMap == null || ccUsrMap.isEmpty())
		    && (bccUsrMap == null || bccUsrMap.isEmpty()))
	    {
		// check if in the DB no user is saved // in case of the
		// repSchedMailUsrsMap is not filled yet
		IRP_REP_SCHED_MAIL_RECEIVERSVO recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
		recVO.setSCHED_ID(scheduleId);
		recVO.setREPORT_ID(new BigDecimal(reportId));
		int cnt = schedulerBO.retSchedMailUsersCnt(recVO);
		if(cnt == 0)
		{
		    setUpdates("0");
		}
		else
		{
		    setUpdates("1");
		}
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
     * method that shows/hide the livesearches related to reports (normal or
     * dynamic fcr)
     * 
     * @return
     * @throws Exception
     */
    public String showHideFcr()
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayFcr = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayRep = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayRepName = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayFcrRepName = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(reportSchedCO.getUSE_DEFAULT_MS_YN() != null)
	    {
		SYS_PARAM_SCREEN_DISPLAYVO msHost = new SYS_PARAM_SCREEN_DISPLAYVO();
		if(ConstantsCommon.ONE.equals(reportSchedCO.getUSE_DEFAULT_MS_YN()))
		{
		    msHost.setIS_MANDATORY(BigDecimal.ZERO);
		}
		else
		{
		    msHost.setIS_MANDATORY(BigDecimal.ONE);
		}
		getAdditionalScreenParams().put("lookuptxt_msHost", msHost);
	    }
	  
	    
	    if(BigDecimal.ONE.equals(reportSchedCO.getIS_FCR_YN()))
	    {
		screenDisplayFcr.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayRep.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplayRepName.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplayFcrRepName.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    else
	    {
		screenDisplayFcr.setIS_VISIBLE(BigDecimal.ZERO);
		screenDisplayRep.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayRepName.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplayFcrRepName.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    if(updates == null)
	    {
		screenDisplayFcr.setValue("");
		screenDisplayRep.setValue("");
		screenDisplayFcrRepName.setValue("");
		screenDisplayRepName.setValue("");
	    }
	    screenDisplayFcr.setIS_MANDATORY(BigDecimal.ONE);
	    screenDisplayFcrRepName.setIS_MANDATORY(BigDecimal.ONE);
	    screenDisplayRep.setIS_MANDATORY(BigDecimal.ONE);
	    screenDisplayRepName.setIS_MANDATORY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("lookuptxt_REPORT_ID", screenDisplayRep);
	    getAdditionalScreenParams().put("lookuptxt_fcrRepId", screenDisplayFcr);
	    getAdditionalScreenParams().put("REPORT_NAME", screenDisplayRepName);
	    getAdditionalScreenParams().put("fcrRepName", screenDisplayFcrRepName);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method that fills the fcr livesearch
     * @return
     * @throws BaseException
     */
    public String fillLiveSearchFcrLookup() 
    {
	try
	{
	    reportSC = new IRP_AD_HOC_REPORTSC();
	    SessionCO sessionCO = returnSessionObject();
	    CommonDetailsSC commonDetailsSC = new CommonDetailsSC();
	    copyproperties(commonDetailsSC);
	    commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    commonDetailsSC.setAppName(sessionCO.getCurrentAppName());
	    //get the list of dynamic reports
	    List<FTR_OPTCO> lstDynReports = schedulerBO.retListDynReports(commonDetailsSC);
	    int size = fcrBO.retFcrListCount(commonDetailsSC);
	    setRecords(size);
	    setGridModel(lstDynReports);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method called for fcr livesearch
     * @return
     */
    public String repFcrLookup()
    {
	try
	{
	    fillRepFcrLookup("/path/scheduler/scheduler_fillLiveSearchFcrLookup.action", "");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method for fcr livesearch
     * @param gridUrl
     * @param gridCaption
     * @return
     * @throws JSONException
     */
    public String fillRepFcrLookup(String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    String[] name = { "ftrOptVO.PROG_REF", "ftrOptVO.BRIEF_NAME_ENG",
		    "REPORT_ID" };
	    String[] colType = { "text", "text", "number" };
	    String[] titles = { getText("repRef_key"), getText("reporting.description"),
		    getText("reporting.description") };
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    grid.setShrinkToFit(ConstantsCommon.TRUE);
	    List<LookupGridColumn> lstGridColumn = returnStandarColSpecs(name, colType, titles);
	    lstGridColumn.get(2).setHidden(Boolean.valueOf(ConstantsCommon.TRUE));
	    lookup(grid, lstGridColumn, null, schedulerSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    

    
    /**
     * Method that gets fcr report's desc
     * @return
     * @throws JSONException
     */
    public String findReportEngDesc()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    reportSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    updates1 = updates;
	    reportSC.setPROG_REF(ConstantsCommon.FCR_MAIN_REPORT_REF);
	    reportSC.setAPP_NAME(sessionCO.getCurrentAppName());
	    report=schedulerBO.retFcrRepById(reportSC);
	    if(report == null)
	    {
		updates = "";
		updates1 = "";
	    }
	    else
	    {
		updates = fcrBO.retFcrReportEngDesc(updates);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * method that shows/hide the mail server live search based on the check box Default ms
     * 
     * @return
     * @throws Exception
     */
    public String defaultMsChecked()
    {
	try
	{

	    String useDefMs = reportSchedCO.getUSE_DEFAULT_MS_YN();
	    // fill from email type
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SCHED_REPORT_FROM_EMAIL_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    fromMailList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	    SYS_PARAM_SCREEN_DISPLAYVO msHost = new SYS_PARAM_SCREEN_DISPLAYVO();
	    msHost.setValue("");

	    SYS_PARAM_SCREEN_DISPLAYVO FROM_EMAIL_TYPE = new SYS_PARAM_SCREEN_DISPLAYVO();
	    FROM_EMAIL_TYPE.setIS_MANDATORY(BigDecimal.ONE);

	    SYS_PARAM_SCREEN_DISPLAYVO FROM_EMAIL_VAL = new SYS_PARAM_SCREEN_DISPLAYVO();
	    FROM_EMAIL_VAL.setIS_MANDATORY(BigDecimal.ONE);
	    FROM_EMAIL_VAL.setValue("");

	    SYS_PARAM_SCREEN_DISPLAYVO fromVal = new SYS_PARAM_SCREEN_DISPLAYVO();
	    fromVal.setIS_VISIBLE(BigDecimal.ONE);

	    SYS_PARAM_SCREEN_DISPLAYVO fromFeVal = new SYS_PARAM_SCREEN_DISPLAYVO();
	    fromFeVal.setIS_VISIBLE(BigDecimal.ZERO);
	    
	    SYS_PARAM_SCREEN_DISPLAYVO msId = new SYS_PARAM_SCREEN_DISPLAYVO();
	    msId.setValue("");
	    
	    SYS_PARAM_SCREEN_DISPLAYVO DEFAULT_FROM_MS = new SYS_PARAM_SCREEN_DISPLAYVO();
	    DEFAULT_FROM_MS.setValue("");

	    if("1".equals(useDefMs))
	    {
		msHost.setIS_READONLY(BigDecimal.ONE);
		msHost.setIS_MANDATORY(BigDecimal.ZERO);
		fromMailList.remove(0);
		FROM_EMAIL_TYPE.setValue(BigDecimal.valueOf(2));
		FROM_EMAIL_VAL.setIS_READONLY(BigDecimal.ZERO);
	    }
	    else
	    {
		msHost.setIS_READONLY(BigDecimal.ZERO);
		msHost.setIS_MANDATORY(BigDecimal.ONE);
		FROM_EMAIL_TYPE.setValue(BigDecimal.valueOf(1));
		FROM_EMAIL_VAL.setIS_READONLY(BigDecimal.ONE);
	    }
	    getAdditionalScreenParams().put("lookuptxt_msHost", msHost);
	    getAdditionalScreenParams().put("FROM_EMAIL_TYPE", FROM_EMAIL_TYPE);
	    getAdditionalScreenParams().put("FROM_EMAIL_VAL", FROM_EMAIL_VAL);
	    getAdditionalScreenParams().put("fromVal", fromVal);
	    getAdditionalScreenParams().put("fromFeVal", fromFeVal);
	    getAdditionalScreenParams().put("msId", msId);
	    getAdditionalScreenParams().put("DEFAULT_FROM_MS", DEFAULT_FROM_MS);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
 
    
    public String executeSchedule() 
    {

	SessionCO sessionCO = returnSessionObject();
	ReportParamsCO repParamsCO = new ReportParamsCO();
	// put all session values in ReportParamsCO object to be sent to
	// jasperReport api
	String[] properties = new String[] { "baseCurrDecPoint", "baseCurrencyCode", "baseCurrencyName", "branchCode",
		"clientType", "companyArabName", "companyCode", "companyName", "currentAppName", "exposCurCode",
		"exposCurName", "fiscalYear", "runningDateRET", "userName", "isRTLDir","language","branchName","employeeId" };
	try
	{
	    PathPropertyUtil.copyProperties(sessionCO, repParamsCO, properties);
	    timerScheduleBO.executeImmediate(repParamsCO,schedId);
	}
	catch(Exception e)
	{
	    handleException(e, null,null);
	}

	return SUCCESS;
    }
    
    /**
     * method that will return the fields of the report query to be used in the autocomplete function of the email body
     * 
     * @return
     * @throws Exception
     */
    public String retEmailBodyAutoCompleteFields()
    {
	try
	{
	    if(StringUtil.nullToEmpty(reportId).isEmpty())
	    {
		updates = "";
	    }
	    else
	    {
		IRP_AD_HOC_REPORTCO repCO = designerBO.returnReportById(new BigDecimal(reportId));
		if(repCO == null)
		{
		    updates = "";
		}
		else
		{
		    StringBuffer sb=new StringBuffer();
		    IRP_AD_HOC_QUERYCO qryObj = repCO.getQueriesList().get(0);
		    HashMap<Long, IRP_FIELDSCO> feMap = qryObj.getSqlObject().getDisplayFields();
		    Iterator it = feMap.values().iterator();
		    IRP_FIELDSCO feCO;
		    while(it.hasNext())
		    {
			feCO = (IRP_FIELDSCO) it.next();
			sb.append("["+feCO.getLabelAlias()+"]").append(",");
		    }
		    updates=sb.toString();
		    if(!updates.isEmpty())
		    {
			updates=updates.substring(0, updates.length()-1);
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e,"","");
	}
	return SUCCESS;
    }

    
    /**
     * method that load the grid of the groupBy fields in the report schedule section
     * 
     * @return
     * @throws 
     */
    public String loadSchedGroupBy()
    {
    	return "successSchedGroupBy";
    }
    
    public String batchesLookup()
    {
	try
	{
	    fillBatchesLookup("", "/path/scheduler/scheduler_fillBatchesGrid", "Batches");
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling batches lookup", "Error filling batches lookup");
	}
	return SUCCESS;
    }
    
    /**
     * Method to Populate Lookup
     * @return
     * @throws BaseException
     */
    public String individualEventsLookup()
    {
		try
		{
		    fillAlertEventLookup("", "/path/scheduler/scheduler_fillAlertEventsGrid", "Events");
		}
		catch(Exception e)
		{
		    //log.error("Error filling reports lookup");
		    handleException(e, "Error filling Alert Events lookup", "Error filling Alert Events lookup");
		}
		return SUCCESS;
    }
    
    /**
     * Method to Construct Lookup 
     * @return
     * @throws BaseException
     */
    public String fillAlertEventLookup(String gridId, String gridUrl, String gridCaption) 
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "EVT_ID", "DESC_ENG"};
	    String[] colType = { "number", "text"};
	    String[] titles = { getText("event_id_key"), getText("desceng_key")};

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
	    lookup(grid, lsGridColumn, null, schedulerSC);
	}
	catch(Exception e)
	{
	    handleException(e, "Error filling Alert Events lookup", "Error filling Alert Events lookup");
	}
	return SUCCESS;
    }
    
    /**
     * Method to Fill Lookup from Events
     * @return
     * @throws BaseException
     */
    public String fillAlertEventsGrid()
    {
	try
	{
		SessionCO sessionCO = returnSessionObject();
		schedulerSC.setCompCode(sessionCO.getCompanyCode());
	    
	    copyproperties(schedulerSC);
	    setSearchFilter(schedulerSC);
	    schedulerSC.setPreferredLanguage(ConstantsCommon.LANGUAGE_ENGLISH);
	    List<Map<String, Object>> eventsList = schedulerBO.retEventList(schedulerSC);
	    ArrayList<ALRT_EVTVO> listEvents = new ArrayList<ALRT_EVTVO>();
	    ALRT_EVTVO eventCO;
	    BigDecimal eventCode;
	    String eventBriefName;
	    for(int i = 0; i < eventsList.size(); i++)
	    {
	    	eventCO = new ALRT_EVTVO();
	    	eventCode = new BigDecimal((String) eventsList.get(i).get("evt_ID"));
	    	eventBriefName = (String) eventsList.get(i).get("desc_ENG");
			eventCO.setEVT_ID(eventCode);
			eventCO.setDESC_ENG(eventBriefName);
			listEvents.add(eventCO);
	    }
	    if(checkNbRec(schedulerSC))
	    {
	    	setRecords(schedulerBO.retEventListCount(schedulerSC));
	    }
	    setGridModel(listEvents);
	}
	catch(RemoteLookupFailureException e)
	{
	    handleException(null,"",getText("no_alert_found_key"));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }  
    
    public String fillBatchesLookup(String gridId, String gridUrl, String gridCaption) 
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "eodBatchMasterVO.BATCH_CODE", "eodBatchMasterVO.BATCH_BRIEF_NAME"};
	    String[] colType = { "number", "text"};
	    String[] titles = { getText("Batch_Code_Key"), getText("BATCH_NAME_key")};

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
	    lookup(grid, lsGridColumn, null, schedulerSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling batches lookup", "Error filling batches lookup");
	}
	return SUCCESS;
    }
    
    /**
     * 
     * @return
     * @throws BaseException
     */
    public String fillBatchesGrid()
    {
	try
	{
	    copyproperties(reportSC);
	    setSearchFilter(reportSC);
	    reportSC.setPreferredLanguage(ConstantsCommon.LANGUAGE_ENGLISH);
	    List<HashMap<String, Object>> batchesList = schedulerBO.retBatchesList(reportSC);
	    ArrayList<IRP_BATCHCO> listBatches = new ArrayList<IRP_BATCHCO>();
	    IRP_BATCHCO batchCO;
	    EOD_BATCH_MASTERVO eodVO;
	    BigDecimal batchCode;
	    String batchBriefName;
	    for(int i = 0; i < batchesList.size(); i++)
	    {
		batchCO = new IRP_BATCHCO();
		eodVO = new EOD_BATCH_MASTERVO();
		batchCO.setEodBatchMasterVO(eodVO);
		batchCode = new BigDecimal(
			(String) ((HashMap<String, Object>) batchesList.get(i).get("eodBatchMasterVO"))
				.get("batch_CODE"));
		batchBriefName = (String) ((HashMap<String, Object>) batchesList.get(i).get("eodBatchMasterVO"))
			.get("batch_BRIEF_NAME");
		eodVO.setBATCH_CODE(batchCode);
		eodVO.setBATCH_BRIEF_NAME(batchBriefName);
		listBatches.add(batchCO);
	    }
	    if(checkNbRec(reportSC))
	    {
		setRecords(schedulerBO.retBatchesListCount(reportSC));
	    }
	    setGridModel(listBatches);
	}
	catch(RemoteLookupFailureException e)
	{
	    handleException(null,"",getText("mtsappnotavailable_key"));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }    
}
