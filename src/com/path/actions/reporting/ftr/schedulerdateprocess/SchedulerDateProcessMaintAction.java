package com.path.actions.reporting.ftr.schedulerdateprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.schedulerdateprocess.SchedulerDateProcessBO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessCO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SchedulerDateProcessMaintAction.java used to
 */
public class SchedulerDateProcessMaintAction extends ReportingLookupBaseAction
{
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    private String updates;
    private String mode;
    private SchedulerDateProcessBO schedulerDateProcessBO ;
    private final SchedulerDateProcessSC schedulerDateProcessSC =  new SchedulerDateProcessSC();
    private SchedulerDateProcessCO schedulerDateProcessCO = new SchedulerDateProcessCO();
    private String schedValues;
    private String procType;
    private String updates1;
    
    public String getProcType()
    {
        return procType;
    }

    public void setProcType(String procType)
    {
        this.procType = procType;
    }

    public String getSchedValues()
        {
            return schedValues;
        }

        public void setSchedValues(String schedValues)
        {
            this.schedValues = schedValues;
        }
    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    private IRP_SCHEDULESC schedSC = new IRP_SCHEDULESC();

    public IRP_SCHEDULESC getSchedSC()
    {
	return schedSC;
    }

    public void setSchedSC(IRP_SCHEDULESC schedSC)
    {
	this.schedSC = schedSC;
    }

    @Override
    public Object getModel()
    {
	return schedSC;
    }



    public String loadSchedulerDateProcessPage()
    {
	try
	{
	    set_searchGridId("schedulerDateProcessListGridTbl_Id");
	}
	catch(Exception ex)
	{
	    handleException(ex, null, null);
	}
	return "schedulerDateProcessList";
    }

    public void setSchedulerDateProcessBO(SchedulerDateProcessBO schedulerDateProcessBO)
    {
	this.schedulerDateProcessBO = schedulerDateProcessBO;
    }
    /**
     * Shows or hides the process form elements based on the selected process type
     * @return String 
     */

    public String showHidePeriodicDates()
    {
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayTmplVisible = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayTmplNotVisible = new SYS_PARAM_SCREEN_DISPLAYVO();

	if(RepConstantsCommon.COL_TEMPLPROC_PERIODIC.equals(updates))
	{
	    screenDisplayTmplVisible.setIS_VISIBLE(BigDecimal.ONE);
	    screenDisplayTmplNotVisible.setIS_VISIBLE(BigDecimal.ZERO);
	}
	else
	{
	    screenDisplayTmplVisible.setIS_VISIBLE(BigDecimal.ZERO);
	    screenDisplayTmplNotVisible.setIS_VISIBLE(BigDecimal.ONE);
	}
	getAdditionalScreenParams().put("schedAsOfDate", screenDisplayTmplNotVisible);
	getAdditionalScreenParams().put("schedFromDate", screenDisplayTmplNotVisible);
	getAdditionalScreenParams().put("schedToDate", screenDisplayTmplNotVisible);
	getAdditionalScreenParams().put("asOfDateLabel", screenDisplayTmplNotVisible);
	getAdditionalScreenParams().put("fromDateLabel", screenDisplayTmplNotVisible);
	getAdditionalScreenParams().put("toDateLabel", screenDisplayTmplNotVisible);
	getAdditionalScreenParams().put("periodicDateLabel", screenDisplayTmplVisible);
	getAdditionalScreenParams().put("schedPeriodTypeComboId", screenDisplayTmplVisible);
	getAdditionalScreenParams().put("schedPeriodicDate", screenDisplayTmplVisible);
	return SUCCESS;
    }
    /**
     * Loads the lookup of Schedules List
     * @return String 
     */

    public String loadSchedLookup() throws JSONException
    {
	try
	{
	    fillLookup("schedSearch", "/path/schedulerDateProcess/SchedulerDateProcessMaintAction_schedLookUpGrid",
		    "Schedules List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    /**
     * fills the lookup of Schedules List
     * @return String 
     */

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "SCHED_ID", "SCHED_NAME" };
	    String[] colType = { "number", "text" };
	    String[] titles = { getText("sch.id"), getText("sch.name") };

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
	    lookup(grid, lsGridColumn, null, schedSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "json";
    }
    /**
     * Retrieve the lookup of Schedules List
     * @return String 
     */

    public String schedLookUpGrid() throws Exception
    {
	try
	{
	    copyproperties(schedSC);
	    setSearchFilter(schedSC);
	    List<IRP_SCHEDULEVO> schedList = schedulerDateProcessBO.retSchedList(schedSC);
	    int schedListCount = schedulerDateProcessBO.retSchedListCount(schedSC);
	    setRecords(schedListCount);
	    setGridModel(schedList);
	}
	catch(Exception e)
	{
	    // log.error(e,
	    // "***************** error in loading the templLookUpGrid *************");
	    handleException(e, null, null);
	}
	return "success";

    }

    /**
     * Called when the run process button is clicked to run the process
     * @return String 
     */

    public String runProcess()
    {	
	try
	{
	JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	JSONArray jsonModel = jsonFilter.getJSONArray("root");
	Object[] reportsArr = jsonModel.toArray();
	schedulerDateProcessBO.runProcess(reportsArr,schedulerDateProcessCO);

	}
	catch (Exception e)
	{
	    handleException(e, null,null);
	}
	return "success";
    }
    
    /**
     * Loads the schedules Reports grid 
     * @return String 
     */

    public String loadSchedReportGrid() throws BaseException
    {

	if(request.getParameter("schedValues") == null || request.getParameter("schedValues").equals(""))
	{
	    return SUCCESS;
	}
	if(request.getParameter("schedValues").equals("0"))
	{
	    setGridModel(null);
	    return SUCCESS;
	}
	
	JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(schedValues);
	JSONArray jsonModel = jsonFilter.getJSONArray(ConstantsCommon.REP_ROOT);
	Object[] modelArr = jsonModel.toArray();
	JSONObject obj;
	HashMap<String, Object> hm;
	
   
	List<String> lstValues = new ArrayList<String>();
		    for(int i = 0; i < modelArr.length; i++)
		    {
			obj = (JSONObject) modelArr[i];
			hm = (HashMap) JSONObject.toBean(obj, HashMap.class);
			lstValues.add((String) hm.values().toArray()[0]);
		    }
        if(lstValues.isEmpty())
        {
            setRecords(0);
            setGridModel(new ArrayList());
        }
        else
        {
    	List<String> lstDateValues = new ArrayList<String>();

	if (procType.equals(RepConstantsCommon.COL_TEMPLPROC_ASOF))
	{
	    lstDateValues.add(RepConstantsCommon.DATE_VALUE_ASOF);
	}
	if (procType.equals(RepConstantsCommon.COL_TEMPLPROC_FROMTO))
	{
	    lstDateValues.add(RepConstantsCommon.DATE_VALUE_FROM);
	    lstDateValues.add(RepConstantsCommon.DATE_VALUE_TO);
	}
	if (procType.equals(RepConstantsCommon.COL_TEMPLPROC_PERIODIC) || procType.equals(RepConstantsCommon.COL_TEMPLPROC_BOTH))
	{   lstDateValues.add(RepConstantsCommon.DATE_VALUE_ASOF);
	    lstDateValues.add(RepConstantsCommon.DATE_VALUE_FROM);
	    lstDateValues.add(RepConstantsCommon.DATE_VALUE_TO);
	}
	
	SessionCO sessionCO = returnSessionObject();
	copyproperties(schedulerDateProcessSC);
	schedulerDateProcessSC.setSchedIds(lstValues);
	schedulerDateProcessSC.setLangCode(sessionCO.getLanguage());
	schedulerDateProcessSC.setLovTypeId(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	schedulerDateProcessSC.setDateValue(lstDateValues);
	schedulerDateProcessSC.setProcType(procType);
	
	List<IRP_REPORT_SCHEDULECO> reportsList = schedulerDateProcessBO.findScheduleReports(schedulerDateProcessSC);
	if(checkNbRec(schedulerDateProcessSC))
	{
	    setRecords(schedulerDateProcessBO.findScheduleReportsCount(schedulerDateProcessSC));
	}
	setGridModel(reportsList);
        }
	return SUCCESS;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setSchedulerDateProcessCO(SchedulerDateProcessCO schedulerDateProcessCO)
    {
	this.schedulerDateProcessCO = schedulerDateProcessCO;
    }

    public SchedulerDateProcessCO getSchedulerDateProcessCO()
    {
	return schedulerDateProcessCO;
    }

  
}
