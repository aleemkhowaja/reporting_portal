package com.path.actions.reporting.ftr.schedulerdateprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;

/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * SchedulerDateProcessListAction.java used to
 */
public class SchedulerDateProcessListAction  extends ReportingLookupBaseAction
{      HttpServletRequest request = ServletActionContext.getRequest();

    private ArrayList<SYS_PARAM_LOV_TRANSVO> procTypeArrList = new ArrayList<SYS_PARAM_LOV_TRANSVO>(); // to
// fill
// the
// calculationType
// combo


public ArrayList<SYS_PARAM_LOV_TRANSVO> getProcTypeArrList()
{
return procTypeArrList;
}

public void setProcTypeArrList(ArrayList<SYS_PARAM_LOV_TRANSVO> procTypeArrList)
{
this.procTypeArrList = procTypeArrList;
}

private ArrayList periodTypeArrList; // to fill the calculationType combo

public ArrayList getPeriodTypeArrList()
{
return periodTypeArrList;
}

public void setPeriodTypeArrList(ArrayList periodTypeArrList)
{
this.periodTypeArrList = periodTypeArrList;
}

	    public String execute() throws Exception
	    {
		try
		{
		set_showSmartInfoBtn("false");
		ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
		// //clear the allTempl session
		// repSessionCO.setAllTempl(null);
		//	

		// fill the combo of process type

		SYS_PARAM_LOV_TRANSVO asOfDateVO = new SYS_PARAM_LOV_TRANSVO();
		asOfDateVO.setVALUE_CODE("AO");
		asOfDateVO.setVALUE_DESC(getText("reporting.asOfDate"));
		procTypeArrList.add(asOfDateVO);

		SYS_PARAM_LOV_TRANSVO ftDateVO = new SYS_PARAM_LOV_TRANSVO();
		ftDateVO.setVALUE_CODE("FT");
		ftDateVO.setVALUE_DESC(getText("reporting.fromToDate"));
		procTypeArrList.add(ftDateVO);

		SYS_PARAM_LOV_TRANSVO bDateVO = new SYS_PARAM_LOV_TRANSVO();
		bDateVO.setVALUE_CODE("B");
		bDateVO.setVALUE_DESC(getText("reporting.bothDates"));
		procTypeArrList.add(bDateVO);
		repSessionCO.setTmplProcMap(new HashMap<String, List<GLSTMPLTCO>>());// added

		SYS_PARAM_LOV_TRANSVO pDateVO = new SYS_PARAM_LOV_TRANSVO();
		pDateVO.setVALUE_CODE("P");
		pDateVO.setVALUE_DESC(getText("reporting.periodicDates"));
		procTypeArrList.add(pDateVO);
		repSessionCO.setTmplProcMap(new HashMap<String, List<GLSTMPLTCO>>());// added

		periodTypeArrList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();

		SYS_PARAM_LOV_TRANSVO daysVO = new SYS_PARAM_LOV_TRANSVO();
		daysVO.setVALUE_CODE("D");
		daysVO.setVALUE_DESC(getText("reporting.days"));
		periodTypeArrList.add(daysVO);

		// SYS_PARAM_LOV_TRANSVO weeksVO = new SYS_PARAM_LOV_TRANSVO();
		// weeksVO.setVALUE_CODE("W");
		// weeksVO.setVALUE_DESC(getText("reporting.weeks"));
		// periodTypeArrList.add(weeksVO);

		SYS_PARAM_LOV_TRANSVO monthsVO = new SYS_PARAM_LOV_TRANSVO();
		monthsVO.setVALUE_CODE("M");
		monthsVO.setVALUE_DESC(getText("reporting.months"));
		periodTypeArrList.add(monthsVO);

		SYS_PARAM_LOV_TRANSVO yearsVO = new SYS_PARAM_LOV_TRANSVO();
		yearsVO.setVALUE_CODE("Y");
		yearsVO.setVALUE_DESC(getText("reporting.years"));
		periodTypeArrList.add(yearsVO);

		// repSessionCO.setTmplProcMap(new HashMap<String,
		// List<GLSTMPLTCO>>());// added

		// repSessionCO.setColTmpProcMap(new HashMap<String,
		// List<COLMNTMPLTCO>>());// added

		SYS_PARAM_SCREEN_DISPLAYVO screenDisplayTmplNotVisible = new SYS_PARAM_SCREEN_DISPLAYVO();

		screenDisplayTmplNotVisible.setIS_VISIBLE(BigDecimal.ZERO);

		getAdditionalScreenParams().put("schedPeriodTypeComboId", screenDisplayTmplNotVisible);
		getAdditionalScreenParams().put("schedPeriodicDate", screenDisplayTmplNotVisible);
		getAdditionalScreenParams().put("periodicDateLabel", screenDisplayTmplNotVisible);
		return SUCCESS;
		}
		catch(Exception e)
		{
		    log.error(e,"");
		    return ERROR;
		}
	    }

	  
	  
	    
	    
}
