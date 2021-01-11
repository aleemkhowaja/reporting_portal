package com.path.actions.reporting.ftr.templateProcess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.templateProcess.TemplateProcessBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.templateProcess.TEMPLATE_PROCESSCO;
import com.path.vo.reporting.ftr.templateProcess.TEMPLATE_PROCESSSC;

public class TemplateProcessAction extends ReportingLookupBaseAction
{

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();

    private TEMPLATE_PROCESSCO tmplProcCO;
    private CommonDetailsSC commonDetailsSC = new CommonDetailsSC();

    private TemplateProcessBO templateProcBO;

    private String updates;
    private BigDecimal code;
    private ArrayList procTypeArrList; // to fill the calculationType combo
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
	set_showSmartInfoBtn("false");
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	// //clear the allTempl session
	// repSessionCO.setAllTempl(null);
	//	

	// fill the combo of process type
	procTypeArrList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();

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
	
	//SYS_PARAM_LOV_TRANSVO weeksVO = new SYS_PARAM_LOV_TRANSVO();
	//weeksVO.setVALUE_CODE("W");
	//weeksVO.setVALUE_DESC(getText("reporting.weeks"));
	//periodTypeArrList.add(weeksVO);
	
	SYS_PARAM_LOV_TRANSVO monthsVO = new SYS_PARAM_LOV_TRANSVO();
	monthsVO.setVALUE_CODE("M");
	monthsVO.setVALUE_DESC(getText("reporting.months"));
	periodTypeArrList.add(monthsVO);

	SYS_PARAM_LOV_TRANSVO yearsVO = new SYS_PARAM_LOV_TRANSVO();
	yearsVO.setVALUE_CODE("Y");
	yearsVO.setVALUE_DESC(getText("reporting.years"));
	periodTypeArrList.add(yearsVO);
	
	repSessionCO.setTmplProcMap(new HashMap<String, List<GLSTMPLTCO>>());// added
	

	// to
	// clear
	// session
	// when
	// opening
	// the
	// screen
	repSessionCO.setColTmpProcMap(new HashMap<String, List<COLMNTMPLTCO>>());// added
	// to
	// clear
	// session
	// when
	// opening
	// the
	// screen
	
	 SYS_PARAM_SCREEN_DISPLAYVO screenDisplayTmplNotVisible = new SYS_PARAM_SCREEN_DISPLAYVO();
		 
         screenDisplayTmplNotVisible.setIS_VISIBLE(BigDecimal.ZERO);

	 getAdditionalScreenParams().put("periodTypeComboId", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("periodicDate", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("periodicDateLbl", screenDisplayTmplNotVisible);
	return SUCCESS;
    }
    
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
	 getAdditionalScreenParams().put("asOfDate", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("fromDate", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("toDate", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("asOfDateLbl", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("fromDateLbl", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("toDateLbl", screenDisplayTmplNotVisible);
	 getAdditionalScreenParams().put("periodicDateLbl", screenDisplayTmplVisible);
	 getAdditionalScreenParams().put("periodTypeComboId", screenDisplayTmplVisible);
	 getAdditionalScreenParams().put("periodicDate", screenDisplayTmplVisible);
	return SUCCESS;
    }
    

    public Object getModel()
    {
	return commonDetailsSC;
    }

    // public String runTempl() throws JSONException
    // {
    // return SUCCESS;
    // }

    public String loadTemplLkp() throws JSONException
    {
	try
	{
	    fillLookup("templatesGridId", "/path/templateProcess/proc_templLookUpGrid", "Templates List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String templLookUpGrid() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(commonDetailsSC);
	    setSearchFilter(commonDetailsSC);
	    commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    List<CommonDetailsVO> tempList = templateProcBO.retTemplatesList(commonDetailsSC);
	    int tempListCount = templateProcBO.retTemplatesListCount(commonDetailsSC);
	    setRecords(tempListCount);
	    setGridModel(tempList);
	}
	catch(Exception e)
	{
	   // log.error(e, "***************** error in loading the templLookUpGrid *************");
	    handleException(e, null, null);
	}
	return "success";

    }

    public String loadColTemplLkp() throws JSONException
    {
	try
	{
	    fillLookup("colTemplatesGridId", "/path/templateProcess/proc_colTemplLookUpGrid", "Column Templates List");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String colTemplLookUpGrid() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(commonDetailsSC);
	    setSearchFilter(commonDetailsSC);
	    commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    List<CommonDetailsVO> tempList = templateProcBO.retColTemplatesList(commonDetailsSC);
	    int tempListCount = templateProcBO.retColTemplatesListCount(commonDetailsSC);
	    setRecords(tempListCount);
	    setGridModel(tempList);
	}
	catch(Exception e)
	{
	    //log.error(e, "***************** error in loading the colTemplLookUpGrid *************");
	    handleException(e, null, null);
	}
	return "success";

    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "CODE", "BRIEF_DESC_ENG" };
	    String[] colType = { "number", "text" };
	    String[] titles = { getText("reporting.code"), getText("reporting.description") };

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

    public String applyTemplDependency() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal templCode = getCode();
	    if(templCode == null || templCode.intValue() == 0 || templCode.intValue() == -9999999)
	    {
		tmplProcCO = new TEMPLATE_PROCESSCO();
	    }
	    else
	    {
		commonDetailsSC = new CommonDetailsSC();
		commonDetailsSC.setTEMPLATE_CODE(templCode);
		commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());
		commonDetailsSC.setGrid(false);
		List templList = templateProcBO.retTemplatesList(commonDetailsSC);
		if(templList == null || templList.isEmpty())
		{
		    tmplProcCO = new TEMPLATE_PROCESSCO();
		}
		else
		{
		    CommonDetailsVO retVal = (CommonDetailsVO) templList.get(0);
		    tmplProcCO = new TEMPLATE_PROCESSCO();
		    tmplProcCO.setFromTempl(templCode);
		    tmplProcCO.setFromTemplStr(retVal.getBRIEF_DESC_ENG());
		}
	    }
	}
	catch(Exception e)
	{
	   // log.error(e, "***************** error in applyTemplDependency *************");
	    handleException(e, null, null);
	}
	return "success";

    }

    public String applyColTemplDependency() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal templCode = getCode();
	    if(templCode == null || templCode.intValue() == 0 || templCode.intValue() == -9999999)
	    {
		tmplProcCO = new TEMPLATE_PROCESSCO();
	    }
	    else
	    {
		commonDetailsSC = new CommonDetailsSC();
		commonDetailsSC.setTEMPLATE_CODE(templCode);
		commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());
		commonDetailsSC.setGrid(false);
		List templList = templateProcBO.retColTemplatesList(commonDetailsSC);
		if(templList == null || templList.isEmpty())
		{
		    tmplProcCO = new TEMPLATE_PROCESSCO();
		}
		else
		{
		    CommonDetailsVO retVal = (CommonDetailsVO) templList.get(0);
		    tmplProcCO = new TEMPLATE_PROCESSCO();
		    tmplProcCO.setFromTempl(templCode);
		    tmplProcCO.setFromTemplStr(retVal.getBRIEF_DESC_ENG());
		}
	    }
	}
	catch(Exception e)
	{
	    //log.error(e, "***************** error in applyColTemplDependency *************");
	    handleException(e, null, null);
	}
	return "success";

    }

    public String runTemplProcess() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal tmpCode = getCode();
	    TEMPLATE_PROCESSSC tmplProcSC = new TEMPLATE_PROCESSSC();
	    tmplProcSC.setCompCode(compCode);
	    if(tmpCode == null) // called from the template process section
	    {
		tmplProcSC.setFromTempl(tmplProcCO.getFromTempl());
		tmplProcSC.setToTempl(tmplProcCO.getToTempl());
	    }
	    else
	    // called after saving a template
	    {
		tmplProcSC.setFromTempl(tmpCode);
		tmplProcSC.setToTempl(tmpCode);
	    }
	    tmplProcSC.setUserName(returnUserName());

	    // String
	    // procName="P_TEMPLATE_PROCESS("+compCode+","+tmplProcCO.getFromTempl()+","+tmplProcCO.getToTempl()+",'"+getUserName()+"')";
//	    log.error("\n\n procedure params====(" + tmplProcSC.getCompCode() + "," + tmplProcSC.getFromTempl() + ","
//		    + tmplProcSC.getToTempl() + ",'" + tmplProcSC.getUserName() + "')\n\n");

	    tmplProcSC.setIsRTL(sessionCO.getIsRTLDir());// for the translation
	    // get the column in
	    // arabic
	    HashMap<String, String> procStatTransMap = new HashMap<String, String>();
	    if(tmpCode == null) // called from the template process section
	    {
		procStatTransMap.put("s", getText("tmplProc.success"));
		procStatTransMap.put("f", getText("tmplProc.failed"));
	    }
	    // used hashmap to send the data to the session so we can use it in
	    // our grid
	    List<GLSTMPLTCO> allTmpl = templateProcBO.runTemplProcess(tmplProcSC, procStatTransMap);

	    if(tmpCode == null) // called from the template process section
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		repSessionCO.getTmplProcMap().put("0", allTmpl);// 0:Templ ; 1 :
		// column
		// template
	    }

	    setUpdates("true");
	}
	catch(Exception e)
	{
	    setUpdates("wrong");
	    handleException(e, null, null);
	    // throw new
	    // JSONException("***************** error in runTemplProcess *************");
	}
	return "success";
    }

    /**
     * Method called from template process run button and save on column template screens
     * @return
     */
    public String runColTemplProcess()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String aoDStr = "";
	    String fr_dateDStr = "";
	    String to_dateDStr = "";
	    BigDecimal periodicDate = BigDecimal.ZERO;
	    String periodType = "";
	    TEMPLATE_PROCESSSC tmplProcSC = new TEMPLATE_PROCESSSC();
	    tmplProcSC.setCompCode(compCode);

	    BigDecimal tmpCode = getCode();
	    if(tmpCode == null) // called from the template process section
	    {
		// Start added by Khaled on 21/12/2011 to send the dates as
		// string to the procedure
		Date aod = tmplProcCO.getAsOfDate();
		Date fr_date = tmplProcCO.getFromDate();
		Date to_date = tmplProcCO.getToDate();
		BigDecimal pr_date =tmplProcCO.getPeriodicDate();
		String pr_type = tmplProcCO.getPeriodType();
		//

		if(aod != null)
		{
		    aoDStr = DateUtil.format(aod,"dd/MM/yyyy");
		}

		if(fr_date != null)
		{
		    fr_dateDStr = DateUtil.format(fr_date,"dd/MM/yyyy");
		}

		if(to_date != null)
		{
		    to_dateDStr = DateUtil.format(to_date,"dd/MM/yyyy");
		}
		if(pr_date != null)
		{
		periodicDate = pr_date ;
		}
		if (pr_type !=null)
		{
		periodType = pr_type;
		}
		// End added by Khaled on 21/12/2011 to send the dates as
		// string to the procedure

		tmplProcSC.setFromTempl(tmplProcCO.getFromTempl());
		tmplProcSC.setToTempl(tmplProcCO.getToTempl());
		tmplProcSC.setProcType(tmplProcCO.getProcType());
	    }
	    else
	    // called after saving a column template
	    {
		tmplProcSC.setFromTempl(tmpCode);
		tmplProcSC.setToTempl(tmpCode);
		tmplProcSC.setProcType("");
	    }
	    tmplProcSC.setAsOfDateStr(aoDStr);
	    tmplProcSC.setFromDateStr(fr_dateDStr);
	    tmplProcSC.setToDateStr(to_dateDStr);
	    tmplProcSC.setPeriodicDate(periodicDate);
	    tmplProcSC.setPeriodType(periodType);
	    
	    tmplProcSC.setIsRTL(sessionCO.getIsRTLDir());
	    // get the translation of the process status
	    HashMap<String, String> procStatTransMap = new HashMap<String, String>();
	    if(tmpCode == null) // called from the template process section
	    {
		procStatTransMap.put("s", getText("tmplProc.success"));
		procStatTransMap.put("f", getText("tmplProc.failed"));
	    }
	    // used hashmap to send the data to the session so we can use it in
	    // our grid

	    List<COLMNTMPLTCO> allTmpl = templateProcBO.runColTemplProcess(tmplProcSC, procStatTransMap,tmpCode==null?true:false);
	    if(tmpCode == null) // called from the template process section
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		repSessionCO.getColTmpProcMap().put("1", allTmpl);// 0:Templ ; 1
		// : column
		// template
	    }
	    setUpdates("true");
	}
	catch(Exception e)
	{
	    setUpdates("wrong");
	    try
	    {
		throw new BOException(RepConstantsCommon.PROCEDURE_FAILED,e);
	    }
	    catch(BOException e1)
	    {
		handleException(e, null, null);
	    }
	}
	return "success";
    }

    public String loadTmplProcLogsGrid() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());// get
	    // all
	    // the
	    // data
	    // needed
	    // from
	    // session
	    List<GLSTMPLTCO> tmplProcLst = repSessionCO.getTmplProcMap().get("0");

	    copyproperties(commonDetailsSC);
	    if(tmplProcLst == null)// check if their is no data to make the grid
	    // empty
	    {
		setRecords(0);
		setGridModel(new ArrayList<GLSTMPLTCO>());
	    }
	    else
	    {

		int nbRec = commonDetailsSC.getNbRec();
		int recToSkip = commonDetailsSC.getRecToskip();
		int maxRec = nbRec + recToSkip;
		if(tmplProcLst.size() < maxRec)
		{
		    maxRec = tmplProcLst.size();
		}
		ArrayList<GLSTMPLTCO> lst = new ArrayList<GLSTMPLTCO>();
		for(int i = recToSkip; i < maxRec; i++)
		{
		    lst.add(tmplProcLst.get(i));
		}
		setRecords(tmplProcLst.size());
		setGridModel(lst);
	    }
	}
	catch(Exception e)
	{
	    //log.error(e, "Error in method loadTmplProcLogsGrid in MailServerAction");
	    handleException(e, "Error Loading template process list", "error loading template process  grid");
	}
	return SUCCESS;
    }

    public String loadColumnTmplProcLogsGrid() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());// get
	    // all
	    // the
	    // data
	    // needed
	    // from
	    // session
	    List<COLMNTMPLTCO> colTmplProcLst = repSessionCO.getColTmpProcMap().get("1");
	    copyproperties(commonDetailsSC);
	    if(colTmplProcLst == null)// check if their is no data to make the
	    // grid empty
	    {
		setRecords(0);
		setGridModel(new ArrayList<COLMNTMPLTCO>());
	    }
	    else
	    {
		int nbRec = commonDetailsSC.getNbRec();
		int recToSkip = commonDetailsSC.getRecToskip();
		int maxRec = nbRec + recToSkip;
		if(colTmplProcLst.size() < maxRec)
		{
		    maxRec = colTmplProcLst.size();
		}
		ArrayList<COLMNTMPLTCO> lst = new ArrayList<COLMNTMPLTCO>();
		for(int i = recToSkip; i < maxRec; i++)
		{
		    lst.add(colTmplProcLst.get(i));
		}
		setRecords(colTmplProcLst.size());
		setGridModel(lst);
	    }
	}
	catch(Exception e)
	{
	    //log.error(e, "Error in method loadTmplProcLogsGrid in MailServerAction");
	    handleException(e, "Error Loading template process list", "error loading template process  grid");
	}
	return SUCCESS;
    }


    public TEMPLATE_PROCESSCO getTmplProcCO()
    {
	return tmplProcCO;
    }

    public void setTmplProcCO(TEMPLATE_PROCESSCO tmplProcCO)
    {
	this.tmplProcCO = tmplProcCO;
    }

    public void setTemplateProcBO(TemplateProcessBO templateProcBO)
    {
	this.templateProcBO = templateProcBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    public ArrayList getProcTypeArrList()
    {
	return procTypeArrList;
    }

    public void setProcTypeArrList(ArrayList procTypeArrList)
    {
	this.procTypeArrList = procTypeArrList;
    }

}
