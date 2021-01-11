package com.path.actions.reporting.ftr.reportsmismatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchBO;
import com.path.dbmaps.vo.REP_MISMATCH_PARAMVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.reportsmismatch.ReportsMismatchSC;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_INTRA_CRITERIACO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * ReportsMismatchMaintAction.java used to
 */
public class ReportsMismatchMaintAction extends ReportingLookupBaseAction
{
    private ArrayList<SYS_PARAM_LOV_TRANSVO> misTypeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private CommonLookupBO commonLookupBO;
    private String update; // 1:main grid ; 2 :related reports
    private BigDecimal mismatchType;
    private REP_MISMATCH_PARAMCO repMismatchParamCO;
    private REP_MISMATCH_PARAMVO repMismatchParamVO;
    private REP_MISMATCH_PARAMVO repMisGridParamVO = new REP_MISMATCH_PARAMVO();
    private String updates;
    private ReportsMismatchBO reportsMismatchBO;

    public void setReportsMismatchBO(ReportsMismatchBO reportsMismatchBO)
    {
	this.reportsMismatchBO = reportsMismatchBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public REP_MISMATCH_PARAMVO getRepMisGridParamVO()
    {
	return repMisGridParamVO;
    }

    public void setRepMisGridParamVO(REP_MISMATCH_PARAMVO repMisGridParamVO)
    {
	this.repMisGridParamVO = repMisGridParamVO;
    }

    public REP_MISMATCH_PARAMVO getRepMismatchParamVO()
    {
	return repMismatchParamVO;
    }

    public void setRepMismatchParamVO(REP_MISMATCH_PARAMVO repMismatchParamVO)
    {
	this.repMismatchParamVO = repMismatchParamVO;
    }

    public REP_MISMATCH_PARAMCO getRepMismatchParamCO()
    {
	return repMismatchParamCO;
    }

    public void setRepMismatchParamCO(REP_MISMATCH_PARAMCO repMismatchParamCO)
    {
	this.repMismatchParamCO = repMismatchParamCO;
    }

    public String getUpdate()
    {
	return update;
    }

    public BigDecimal getMismatchType()
    {
	return mismatchType;
    }

    public void setMismatchType(BigDecimal mismatchType)
    {
	this.mismatchType = mismatchType;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public String execute() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.REPORTS_MISMATCH_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    misTypeList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    set_showSmartInfoBtn(RepConstantsCommon.FALSE);
	    setUpdate("1"); // 1: main grid ; 2 : related report grid
	    setMismatchType(BigDecimal.ONE);// by default the inter reports will
	    // be
	    // loaded

	    repSessionCO.setRepMisParameterScreenHash(new HashMap<String, Object>());
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_REL_COLS,
		    new HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>());
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_REL_REPORTS,
		    new HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "reportsMismatchList";
    }

    public String cleanSnSession() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.setRepMisParameterScreenHash(new HashMap<String, Object>());
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_REL_COLS,
		    new HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>());
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_REL_CRITERIA,
		    new HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>());
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_REL_REPORTS,
		    new HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>());
	    ReportsMismatchSC sc = new ReportsMismatchSC();
	    sc.setMisType(mismatchType);
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_INTER_INTRA,
		    reportsMismatchBO.fillHashInterIntraInit(sc));

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getMisTypeList()
    {
	return misTypeList;
    }

    public void setMisTypeList(ArrayList<SYS_PARAM_LOV_TRANSVO> misTypeList)
    {
	this.misTypeList = misTypeList;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public String getHideRelCrt()
    {
	BigDecimal misType = getMismatchType();
	if(misType != null && misType.intValue() == 1 ||  "2".equals(update))
	{
	    return "true";
	}
	else
	{
	    return "false";
	}
    }

    public String getHideRelReports()
    {
	BigDecimal misType = getMismatchType();
	if(misType != null && misType.intValue() == 0 || "2".equals(update))
	{
	    return "true";
	}
	else
	{
	    return "false";
	}
    }

    public String getHideCrt()
    {
	if("2".equals(update))
	{
	    return "true";
	}
	else
	{
	    return "false";
	}
    }

    public String openRelRepMis() throws JSONException
    {
	setUpdate("2");
	repMisGridParamVO.setCRITERIA_CODE(repMismatchParamVO.getCRITERIA_CODE());
	return "mismatchsGrid";
    }

}
