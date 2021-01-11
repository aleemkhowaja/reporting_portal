package com.path.actions.reporting.ftr.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.lib.common.util.DateUtil;
import com.path.struts2.lib.common.base.GridBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULESC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_LOG_DETAILSCO;

public class SchedulerMailLogDetailsAction extends GridBaseAction
{

    private SchedulerBO schedulerBO;
    private String startDate;
    private String repId;
    private String schedId;
    private String schedDt;
    private final IRP_REPORT_SCHEDULESC repSchedSC = new IRP_REPORT_SCHEDULESC();


    public String getStartDate()
    {
	return startDate;
    }

    public void setStartDate(String startDate)
    {
	this.startDate = startDate;
    }
    
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

    public void setSchedulerBO(SchedulerBO schedulerBO)
    {
	this.schedulerBO = schedulerBO;
    }

    public Object getModel()
    {
	return repSchedSC;
    }

    public String loadMailLogDet()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(repSchedSC);
	    int cnt = 0;
	    if(repId == null || schedId == null || startDate == null || schedDt == null)
	    {
		setRecords(0);
		setGridModel(new ArrayList<IRP_REPORT_SCHED_LOG_DETAILSCO>());
	    }
	    else
	    {
		repSchedSC.setREPORT_ID(new BigDecimal(repId));
		repSchedSC.setSCHED_ID(new BigDecimal(schedId));
		repSchedSC.setSTART_DATE(DateUtil.parseDate(startDate, "dd/MM/yyyy hh:mm:ss aaa"));
		repSchedSC.setSCHEDULED_DATE(DateUtil.parseDate(schedDt, "dd/MM/yyyy hh:mm:ss aaa"));
		repSchedSC.setLANG_CODE(sessionCO.getLanguage());
		repSchedSC.setSCHED_MAIL_RECEIVER_TYPE_ID(RepConstantsCommon.SCHED_MAIL_RECEIVER_TYPE);
		repSchedSC.setSCHED_STATUS_LOV_TYPE_ID(RepConstantsCommon.SCHED_STATUS_LOV_TYPE);
		cnt = schedulerBO.retRepSchedMailLogDetCnt(repSchedSC);
		setRecords(cnt);
		List<IRP_REPORT_SCHED_LOG_DETAILSCO> lst = schedulerBO.retRepSchedMailLogDetLst(repSchedSC);
		setGridModel(lst);
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

	return SUCCESS;
    }
}
