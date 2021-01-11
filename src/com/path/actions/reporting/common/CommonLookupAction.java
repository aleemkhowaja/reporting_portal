package com.path.actions.reporting.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.dbmaps.vo.PTH_CTRLVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;

public class CommonLookupAction extends ReportingLookupBaseAction
{

    private IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
    private DesignerBO designerBO;
    private String reportId;
    private IRP_AD_HOC_REPORTCO report;
    private S_ADDITIONS_OPTIONSVO sAdditionsOptionsVO = new S_ADDITIONS_OPTIONSVO();// bbe
    private BigDecimal optionCode;
    private CommonLookupBO commonLookupBO;
    private BigDecimal countryCode;
    private CsvItemsSC csvItemsSC = new CsvItemsSC();
    private BigDecimal parentRepId;
    private String updates;
    
    public String getUpdates()
    {
        return updates;
    }

    public void setUpdates(String updates)
    {
        this.updates = updates;
    }


    public BigDecimal getParentRepId()
    {
        return parentRepId;
    }

    public void setParentRepId(BigDecimal parentRepId)
    {
        this.parentRepId = parentRepId;
    }

    public CsvItemsSC getCsvItemsSC()
    {
        return csvItemsSC;
    }

    public void setCsvItemsSC(CsvItemsSC csvItemsSC)
    {
        this.csvItemsSC = csvItemsSC;
    }

    public BigDecimal getCountryCode()
    {
	return countryCode;
    }

    public void setCountryCode(BigDecimal countryCode)
    {
	this.countryCode = countryCode;
    }

    public BigDecimal getOptionCode()
    {
	return optionCode;
    }

    public void setOptionCode(BigDecimal optionCode)
    {
	this.optionCode = optionCode;
    }

    public S_ADDITIONS_OPTIONSVO getsAdditionsOptionsVO()
    {
	return sAdditionsOptionsVO;
    }

    public void setsAdditionsOptionsVO(S_ADDITIONS_OPTIONSVO sAdditionsOptionsVO)
    {
	this.sAdditionsOptionsVO = sAdditionsOptionsVO;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public IRP_AD_HOC_REPORTSC getReportSC()
    {
	return reportSC;
    }

    public void setReportSC(IRP_AD_HOC_REPORTSC reportSC)
    {
	this.reportSC = reportSC;
    }

    public String getReportId()
    {
	return reportId;
    }

    public void setReportId(String reportId)
    {
	this.reportId = reportId;
    }

    public IRP_AD_HOC_REPORTCO getReport()
    {
	return report;
    }

    public void setReport(IRP_AD_HOC_REPORTCO report)
    {
	this.report = report;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public String fillReportGrid() throws BaseException
    {
	copyproperties(reportSC);
	setSearchFilter(reportSC);
	SessionCO sessionCO = returnSessionObject();
	reportSC.setFromSched("1");
	reportSC.setCOMP_CODE(sessionCO.getCompanyCode());
	reportSC.setBRANCH_CODE(sessionCO.getBranchCode());
	reportSC.setUSER_ID(sessionCO.getUserName());
	PTH_CTRLVO pthCtrl = returnCommonLibBO().returnPthCtrl();
	reportSC.setProfType(NumberUtil.nullToZero(pthCtrl.getPOP_PROF_MOD_ACCESS()));
	if(ConstantsCommon.SCHED_PROG_REF.equals(get_pageRef())) //from scheduler
	{
	    reportSC.setOPT_EXT(RepConstantsCommon.OPT_SC_SC);
	    reportSC.setFILTER_DESIGNER_REP("1");
	}
    else if(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF.equals(get_pageRef())|| RepConstantsCommon.TIME_BUCKETS.equals(get_pageRef()))//from upload download and from times bucket
	{
	    reportSC.setFILTER_DESIGNER_REP("1");
	}
	else if ( RepConstantsCommon.HYPERLINK_PROG_REF.equals(get_pageRef()))
	{
		 reportSC.setFILTER_DESIGNER_REP("1");
		 reportSC.setCalledFrom("1");
	}

	/*added because in TimeBucketsMaint.jsp (reference live search) we have a column with name REPORT_ID and the SC also has a column of name REPORT_ID, in this case
	 * the reportSC.REPORT_ID is always filled with a value when searching for a REPORT_ID in the live search grid*/  
	if(reportId == null ||  ("").equals(this.reportId.toString()))
	{
	    reportSC.setREPORT_ID(null);
	    
	}
	else
	{
	    reportSC.setREPORT_ID(new BigDecimal(reportId));
	}
	/*In case this function is called from the subReports lookup*/
	if(parentRepId!=null)
	{
	    reportSC.setPARENT_REP_ID(parentRepId);
	}
	List reportList = designerBO.getReportsList(reportSC);
	if(checkNbRec(reportSC))
	{
	    setRecords(designerBO.getReportsCount(reportSC));
	}
	setGridModel(reportList);
	return SUCCESS;
    }

    public String reportLookup()
    {
	try
	{
	    fillLookup("REPORT_ID", "/path/commonLkp/commonLkpAction_fillReportGrid", "Reports");
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "REPORT_ID", "REPORT_NAME", "PROG_REF" };
	    String[] colType = { "number", "text", "text" };
	    String[] titles = { getText("reporting.lkpCode"), getText("reportName"), getText("progRef") };

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

		if(name[i].equals("value_date"))
		{
		    gridColumn.setFormatter("date");
		    gridColumn.setEditrules("{date:true}");
		}
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, reportSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String findReportByID() throws BaseException, IOException
    {
	if(reportId == null || reportId.equals("") || reportId.equals("-9999999"))
	{
	    report = new IRP_AD_HOC_REPORTCO();
	}
	else
	{
	    report = designerBO.findReportById(new BigDecimal(reportId));
	    if(report == null)
	    {
		report = new IRP_AD_HOC_REPORTCO();
	    }
	    else
	    {
		report.setREPORT_ID(new BigDecimal(reportId));
	    }
	}

	return SUCCESS;
    }

//    public String smartLookup() throws JSONException
//    {
//	try
//	{
//	    // Design the Grid by defining the column model and column names
//	    String[] name = { "OPTION_CODE", "BRIEF_NAME_ENG", "LONG_NAME_ENG" };
//	    String[] colType = { "number", "text", "text" };
//	    String[] titles = { getText("reporting.optionCode"), getText("reporting.lkpBriefEng"),
//		    getText("reporting.lkpLongEng") };
//
//	    // Defining the Grid
//	    LookupGrid grid = new LookupGrid();
//	    grid.setCaption("SMART");
//	    grid.setRowNum("10");
//	    grid.setUrl("/path/commonLkp/commonLkpAction_loadSmartLookupGrid");
//
//	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
//
//	    int cols = name.length;
//	    for(int i = 0; i < cols; i++)
//	    {
//		// Defining each column
//		LookupGridColumn gridColumn = new LookupGridColumn();
//		gridColumn.setName(name[i]);
//		gridColumn.setIndex(name[i]);
//		gridColumn.setColType(colType[i]);
//		gridColumn.setTitle(titles[i]);
//		gridColumn.setSearch(true);
//
//		// adding column to the list
//		lsGridColumn.add(gridColumn);
//	    }
//	    lookup(grid, lsGridColumn, null, null);
//	}
//	catch(Exception e)
//	{
//	    log.error(e, "------------error in smart lookup-----------");
//	}
//	return "success";
//    }
//
//    public String loadSmartLookupGrid() throws Exception
//    {
//	try
//	{
//      S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC = new S_ADDITIONS_OPTIONSSC();
//      setSearchFilter(sAdditionsOptionsSC);
//	    copyproperties(sAdditionsOptionsSC);
//	    List addOptionsList = commonLookupBO.getAdditionsOptionsList(sAdditionsOptionsSC);
//	    int addOptionsCount = commonLookupBO.getAdditionsOptionsListCount(sAdditionsOptionsSC);
//	    setRecords(addOptionsCount);
//	    setGridModel(addOptionsList);
//	}
//	catch(Exception e)
//	{
//	    //log.error(e, "***************** error in loading the grid *************");
//	    handleException(e,null,null);
//	}
//	return "success";
//
//    }

    public String applySmartDependency() throws JSONException
    {
	try
	{
	    BigDecimal optionCode = getOptionCode();
	    if(optionCode == null || optionCode.intValue() == 0)
	    {
		sAdditionsOptionsVO.setOPTION_CODE(null);
		sAdditionsOptionsVO.setBRIEF_NAME_ENG(null);
		sAdditionsOptionsVO.setLONG_NAME_ENG(null);
	    }
	    else
	    {
		S_ADDITIONS_OPTIONSVO sAddOptVO = new S_ADDITIONS_OPTIONSVO();
		sAddOptVO.setOPTION_CODE(optionCode);
		sAdditionsOptionsVO = commonLookupBO.getAdditionsOptionsDependency(sAddOptVO);
		if(sAdditionsOptionsVO == null)
		{
		    sAdditionsOptionsVO = new S_ADDITIONS_OPTIONSVO();
		    sAdditionsOptionsVO.setOPTION_CODE(null);
		    sAdditionsOptionsVO.setBRIEF_NAME_ENG(null);
		    sAdditionsOptionsVO.setLONG_NAME_ENG(null);

		}

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method called on opening of reports lookup
     * @return
     */
    public String retReports()
    {
	try
	{
	    fillLookupCsv("REPORT_ID", "/path/commonLkp/commonLkpAction_fillReportCsvGrid.action?_pageRef="
		    + get_pageRef()+"&updates="+updates, "Reports");
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    /**
     * Method called fo fill report csv grid
     * @return
     */
    public String fillReportCsvGrid()
    {
	try
	{
        	SessionCO sessionCO = returnSessionObject();
        	csvItemsSC.setAppName(sessionCO.getCurrentAppName());
        	copyproperties(csvItemsSC);
        	setSearchFilter(csvItemsSC);
        	if(ConstantsCommon.TRUE.equals(updates))
        	{
        	    csvItemsSC.setFROM_SNP(ConstantsCommon.TRUE);
        	}
        	List<CsvItemsCO> reportList = commonLookupBO.getReportsList(csvItemsSC);
        	if(checkNbRec(csvItemsSC))
        	{
        	    setRecords(commonLookupBO.getReportsCount(csvItemsSC));
        	}
        	setGridModel(reportList);
	}
	catch(Exception e)
	{
	    	handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String fillLookupCsv(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "PROG_REF", "REPORT_NAME","FTR_REP_YN","APP_NAME" };
	    String[] colType = { "text", "text","text","text" };
	    String[] titles = { getText("progRef"), getText("reportName"),"FTR_REP_YN",getText("appName") };
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    // grid.setId(gridId);
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    // grid.setFilter(false);
	    grid.setUrl(gridUrl);
	    grid.setShrinkToFit("true");
	    List<LookupGridColumn> lsGridColumn = returnStandarColSpecs(name, colType, titles);
	    lsGridColumn.get(2).setHidden(true);
	    lookup(grid, lsGridColumn, null, csvItemsSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }
    
	public Object getModel()
	{
		return reportSC;
	}    

}
