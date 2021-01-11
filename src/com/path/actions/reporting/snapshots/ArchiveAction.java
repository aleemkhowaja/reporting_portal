package com.path.actions.reporting.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONException;

import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.snapshots.ARCHIVECO;
import com.path.vo.reporting.snapshots.ARCHIVESC;
import com.path.vo.reporting.snapshots.IRP_SNAPSHOTS_LOGSCO;

public class ArchiveAction extends ReportingLookupBaseAction
{
    private CommonLookupBO commonLookupBO;
    private ARCHIVECO archiveCO = new ARCHIVECO();
    private SnapShotBO snapShotBO;
    private ARCHIVESC archiveSC = new ARCHIVESC();
    private String userIdStr;

    private ArrayList<SYS_PARAM_LOV_TRANSVO> datePeriod = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to
    // fill
    // select
    // in
    // jsp
    private ArrayList<SYS_PARAM_LOV_TRANSVO> archiveLocation = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to
    // fill
    // select
    // in
    // jsp
    private ArrayList<SYS_PARAM_LOV_TRANSVO> dateRange = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to
    // fill
    // select
    // in
    // jsp
    private ArrayList<SYS_PARAM_LOV_TRANSVO> usersList = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to

    // fill
    // select
    // in
    // jsp

    
    public ArrayList<SYS_PARAM_LOV_TRANSVO> getUsersList()
    {
	return usersList;
    }

    public void setUsersList(ArrayList<SYS_PARAM_LOV_TRANSVO> usersList)
    {
	this.usersList = usersList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDateRange()
    {
	return dateRange;
    }

    public void setDateRange(ArrayList<SYS_PARAM_LOV_TRANSVO> dateRange)
    {
	this.dateRange = dateRange;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getArchiveLocation()
    {
	return archiveLocation;
    }

    public void setArchiveLocation(ArrayList<SYS_PARAM_LOV_TRANSVO> archiveLocation)
    {
	this.archiveLocation = archiveLocation;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDatePeriod()
    {
	return datePeriod;
    }

    public void setDatePeriod(ArrayList<SYS_PARAM_LOV_TRANSVO> datePeriod)
    {
	this.datePeriod = datePeriod;
    }

    public String getUserIdStr()
    {
	return userIdStr;
    }

    public void setUserIdStr(String userIdStr)
    {
	this.userIdStr = userIdStr;
    }

    public ARCHIVESC getArchiveSC()
    {
	return archiveSC;
    }

    public void setArchiveSC(ARCHIVESC archiveSC)
    {
	this.archiveSC = archiveSC;
    }

    public ARCHIVECO getArchiveCO()
    {
	return archiveCO;
    }

    public Object getModel()
    {
 	return archiveSC;
    }
    
    public void setSnapShotBO(SnapShotBO snapShotBO)
    {
	this.snapShotBO = snapShotBO;
    }

    public void setArchiveCO(ARCHIVECO archiveCO)
    {
	this.archiveCO = archiveCO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public String loadArchive() throws BaseException
    {
	set_showSmartInfoBtn("false");
	set_showRecordLogsBtn("false");

	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(325));
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	datePeriod = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	SYS_PARAM_LOV_TRANSVO sysParamLovArchiveVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovArchiveVO.setLOV_TYPE_ID(new BigDecimal(326));
	sysParamLovArchiveVO.setLANG_CODE(sessionCO.getLanguage());
	archiveLocation = (ArrayList) commonLookupBO.getLookupList(sysParamLovArchiveVO);

	sysParamLovArchiveVO.setLOV_TYPE_ID(new BigDecimal(512));
	sysParamLovArchiveVO.setLANG_CODE(sessionCO.getLanguage());
	dateRange = (ArrayList) commonLookupBO.getLookupList(sysParamLovArchiveVO);

	sysParamLovArchiveVO.setLOV_TYPE_ID(new BigDecimal(335));
	sysParamLovArchiveVO.setLANG_CODE(sessionCO.getLanguage());
	usersList = (ArrayList) commonLookupBO.getLookupList(sysParamLovArchiveVO);

	return "archive";
    }

    public List<IRP_CONNECTIONSVO> getConnectionsList()
    {
	try
	{
	    IRP_CONNECTIONSSC conSC = new IRP_CONNECTIONSSC();
	    return commonLookupBO.returnConnectionsListByApp(conSC);   
//	    return commonLookupBO.returnConnectionsList();
	}
	catch(BaseException e)
	{
	    log.error(e, "Error in getConnectionsList");
	    return null;
	}
    }

    public String archivingFlow() throws Exception
    {
	archiveCO.getArchiveLocation();
	archiveCO.getEndRangeDate();
	
	if(userIdStr.endsWith(","))
	{
	    userIdStr = userIdStr.substring(0, userIdStr.length() - 1);
	    List<String> userList = new ArrayList<String>(Arrays.asList(userIdStr.split(",")));
	    archiveCO.setUserList(userList);
	}
	archiveCO.setUserId(userIdStr);

	try
	{
	    snapShotBO.archivingFlow(archiveCO);
	}
	catch(Exception e)
	{
	    if(e.getMessage().equals("tmplProc.checkDatesAlert"))
	    {
		 handleException(null,getText("tmplProc.checkDatesAlert"),null);
	    }
	    else
	    {
		//log.error(e.getMessage());
		handleException(e, "", "");
	    }
	    return SUCCESS;
	}

	return null;
    }

    /**
     * Construct the grid of application name lookup.
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadUserLkpGrid() throws BaseException
    {

	try
	{
	    copyproperties(archiveSC);
	    List<ARCHIVECO> lst = snapShotBO.retUserList(archiveSC);
	    int lstCnt = snapShotBO.retUserListCount(archiveSC);
	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in loadUserLkpGrid  AppConnectionAction");
	    handleException(e, "Error in load application name lookup grid", "loadUserLkpGrid .exceptionMsg._key");
	}
	return SUCCESS;
    }

    /**
     * Construct the grid of application name lookup.
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadArchiveLogsGrid() throws BaseException
    {

	try
	{
	    copyproperties(archiveSC);
	    List<IRP_SNAPSHOTS_LOGSCO> lst = snapShotBO.retArchiveLogsList(archiveSC);
	    int lstCnt = snapShotBO.retArchiveLogsListCount(archiveSC);
	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in loadUserLkpGrid  AppConnectionAction");
	    handleException(e, "Error in load application name lookup grid", "loadUserLkpGrid .exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String applySmartValidation() throws JSONException
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayStartRangeDate = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayEndRangeDate = new SYS_PARAM_SCREEN_DISPLAYVO();// archiveCO.endRangeDate
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySelDate = new SYS_PARAM_SCREEN_DISPLAYVO();

	    if("2".equals(archiveCO.getDateRange()))
	    {
		screenDisplayStartRangeDate.setIS_READONLY(BigDecimal.ZERO);
		screenDisplayEndRangeDate.setIS_READONLY(BigDecimal.ZERO);
		screenDisplaySelDate.setIS_READONLY(BigDecimal.ONE);
	    }
	    else
	    {
		screenDisplayStartRangeDate.setIS_READONLY(BigDecimal.ONE);
		screenDisplayEndRangeDate.setIS_READONLY(BigDecimal.ONE);
		screenDisplaySelDate.setIS_READONLY(BigDecimal.ZERO);
	    }

	    getAdditionalScreenParams().put("startRangeDate", screenDisplayStartRangeDate);
	    getAdditionalScreenParams().put("endRangeDate", screenDisplayStartRangeDate);
	    getAdditionalScreenParams().put("selDate", screenDisplaySelDate);

	}
	catch(Exception e)
	{
	    //log.error(e, "");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

}
