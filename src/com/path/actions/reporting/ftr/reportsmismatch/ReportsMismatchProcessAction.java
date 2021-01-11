package com.path.actions.reporting.ftr.reportsmismatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchProcessBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

public class ReportsMismatchProcessAction extends ReportingLookupBaseAction
{
    private ReportsMismatchProcessBO reportsMismatchProcessBO;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> misTypeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private CommonLookupBO commonLookupBO;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> reportFrequencyList;
    private REP_MISMATCH_PROCESSCO misProcCO;
    private REP_MISMATCH_PROCESSSC misProcSC = new REP_MISMATCH_PROCESSSC();
    HttpServletResponse response = ServletActionContext.getResponse();
    private String update;

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public REP_MISMATCH_PROCESSSC getMisProcSC()
    {
	return misProcSC;
    }

    public void setMisProcSC(REP_MISMATCH_PROCESSSC misProcSC)
    {
	this.misProcSC = misProcSC;
    }

    public REP_MISMATCH_PROCESSCO getMisProcCO()
    {
	return misProcCO;
    }

    public void setMisProcCO(REP_MISMATCH_PROCESSCO misProcCO)
    {
	this.misProcCO = misProcCO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getReportFrequencyList()
    {
	return reportFrequencyList;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getMisTypeList()
    {
	return misTypeList;
    }

    public void setMisTypeList(ArrayList<SYS_PARAM_LOV_TRANSVO> misTypeList)
    {
	this.misTypeList = misTypeList;
    }

    public void setReportsMismatchProcessBO(ReportsMismatchProcessBO reportsMismatchProcessBO)
    {
	this.reportsMismatchProcessBO = reportsMismatchProcessBO;
    }

    public Object getModel()
    {
	return misProcSC;
    }

    public String execute() throws Exception
    {
	set_showSmartInfoBtn("false");
	fillMismatchProcessCombos();
	return "repMisProcess";
    }

    public void fillMismatchProcessCombos()
    {
	try
	{
	    // fill mismatch type combo
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.REPORTS_MISMATCH_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    misTypeList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	    // fill frequency lkp
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.FREQUENCY_SNP_LOV_TYPE);
	    reportFrequencyList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public String loadCrtProgRefLkp()
    {
	try
	{
	    fillRepRefLookup("/path/reportsMismatch/repMismProctAction_fillCrtProgRefLkp.action?_pageRef="
		    + get_pageRef());
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillRepRefLookup(String gridUrl) throws JSONException
    {
	try
	{
	    String[] name = { "CRT_PROGREF", "PROG_REF", "CRITERIA", "REP_MISMATCH_ID" };
	    String[] colType = { "text", "text", "text", "number" };
	    String[] titles = { "CRT_ProgREF", getText("progRef"), getText("criteria.criteriaTitle"),
		    "REP_MISMATCH_ID" };
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("");
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    grid.setShrinkToFit("true");
	    List<LookupGridColumn> lstGridColumn = returnStandarColSpecs(name, colType, titles);
	     lstGridColumn.get(0).setHidden(true);
	     lstGridColumn.get(3).setHidden(true);
	    lookup(grid, lstGridColumn, null, misProcSC);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillCrtProgRefLkp()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(misProcSC);
	    setSearchFilter(misProcSC);
	    BigDecimal misType = misProcCO.getTYPE();
	    misProcSC.setMISMATCH_TYPE(misType);
	    misProcSC.setCompCode(sessionCO.getCompanyCode());

	    ArrayList<REP_MISMATCH_PROCESSCO> lst = reportsMismatchProcessBO.retCrtProgRefLkpLst(misProcSC);
	    int size = reportsMismatchProcessBO.retCrtProgRefLkpCnt(misProcSC);

	    setRecords(size);
	    setGridModel(lst);
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    //log.error(e, "");
	    handleException(e, null, null);
	    return null;
	}
    }

    public String resetMismatchProcess()
    {
	try
	{
	    misProcCO = new REP_MISMATCH_PROCESSCO();
	    fillMismatchProcessCombos();
	    return "repMisProcessMaint";
	}
	catch(Exception e)
	{
	    //log.error(e, "");
	    handleException(e, null, null);
	    return null;
	}

    }

    public String loadMismatchProcessGrid()
    {
	try
	{
	    copyproperties(misProcSC);
	    int cnt;
	    List<REP_MISMATCH_PROCESSCO> lst;
	    // value already selected in Maint screen
	    if(misProcCO == null || misProcCO.getREP_MISMATCH_ID() == null
		    || NumberUtil.isEmptyDecimal(misProcCO.getREP_MISMATCH_ID()))
	    {
		cnt = 0;
		lst = new ArrayList<REP_MISMATCH_PROCESSCO>();
	    }
	    // on load or reset
	    else
	    {
		misProcSC.setREP_MIS_ID(misProcCO.getREP_MISMATCH_ID());
		// inter
		if(misProcCO.getTYPE().equals(BigDecimal.ONE))
		{
		    SessionCO sessionCO = returnSessionObject();
		    misProcSC.setCompCode(sessionCO.getCompanyCode());
		    misProcSC.setMISMATCH_TYPE(misProcCO.getTYPE());
		    misProcSC.setCRITERIA_VAL(misProcCO.getCRITERIA());
		    cnt = reportsMismatchProcessBO.retMisProcGridInterCnt(misProcSC);
		    lst = reportsMismatchProcessBO.retMisProcGridInterLst(misProcSC);
		}
		else
		{
		    cnt = reportsMismatchProcessBO.retMisProcGridIntraCnt(misProcSC);
		    lst = reportsMismatchProcessBO.retMisProcGridIntraLst(misProcSC);
		}
	    }

	    setRecords(cnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e, "");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String getHideCrt()
    {
	if(misProcCO == null || misProcCO.getTYPE().equals(BigDecimal.ONE))
	{
	    return "true";
	}
	else
	{
	    return "false";
	}

    }

    public String getHideProgRef()
    {
	if(misProcCO == null || misProcCO.getTYPE().equals(BigDecimal.ONE))
	{
	    return "false";
	}
	else
	{
	    return "true";
	}
    }

    public String reloadMisProcGrid()
    {
	return "repMisProcessGrid";
    }

    public String goProcess()
    {
	try
	{
	    if(misProcCO == null)
	    {
		response.setContentType("text/html");
		response.getOutputStream().write("".getBytes(CommonUtil.ENCODING));
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	    }
	    // reset
	    else
	    {

		StringBuffer sb;
		REP_MISMATCH_PROCESSSC sc = new REP_MISMATCH_PROCESSSC();
		// if inter
		if(BigDecimal.ONE.equals(misProcCO.getTYPE()))
		{
		    String[] progRefsArr = misProcCO.getCrtsProgRefs().split(",");
		    sc.setProgRefs(progRefsArr);
		}
		// if intra
		else
		{
		    sc.setProgRefs(misProcCO.getCRT_PROGREF().split(","));
		}
		sc.setMISMATCH_FREQ(misProcCO.getFREQUENCY());
		List<REP_SNAPSHOT_INFORMATIONCO> snpLst = reportsMismatchProcessBO.retMisProcSnpInfoList(sc);
		misProcCO.setTRANS_MSG(getText("snp.missingSnp"));
		HashMap<String,String> hm = new HashMap();
		hm.put("1",getText("progRef"));
		hm.put("2",getText("criteria.criteriaTitle"));
		hm.put("3",getText("line.lineDetails"));
		hm.put("4",getText("reporting.sum"));
		
		sb = reportsMismatchProcessBO.retProcessHtml(snpLst, misProcCO,hm);

		response.setContentType("text/html");
		response.getOutputStream().write(sb.toString().getBytes(CommonUtil.ENCODING));
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    //log.error(e, "");
	    return null;
	}
    }
}
