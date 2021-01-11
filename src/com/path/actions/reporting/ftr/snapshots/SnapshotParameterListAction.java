package com.path.actions.reporting.ftr.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dbmaps.vo.IRP_FCR_FIXED_COLSVO;
import com.path.dbmaps.vo.IRP_SNAPSHOT_PARAM_MAPPINGVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.select.SelectCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_DRILLDOWN_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_MODIFY_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterListAction.java used to
 */
public class SnapshotParameterListAction extends ReportingLookupBaseAction
{
    private SnapshotParameterBO snapshotParameterBO;
    private final SnapshotParameterSC snapshotParameterSC = new SnapshotParameterSC();
    private CommonLookupBO commonLookupBO;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> snapshotFreqCmb = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SelectCO> snapshotArgCmbList;
    private REP_SNAPSHOT_PARAMVO repSnapshotParamVO;
    private IRP_SNAPSHOT_PARAM_MAPPINGVO repSnapshotParamMappingVO;
    private String updates2;
    // added the new in order to get the value in this way of sending otherwise
    // object always null
    private REP_SNAPSHOT_PARAMCO repSnapshotParamCO = new REP_SNAPSHOT_PARAMCO();
    private DesignerBO designerBO;
    private String code;
    private ReportsMismatchBO reportsMismatchBO;
    private FcrBO fcrBO;
    private String appName;
    
    
    public void setFcrBO(FcrBO fcrBO)
    {
        this.fcrBO = fcrBO;
    }

    
    public void setReportsMismatchBO(ReportsMismatchBO reportsMismatchBO)
    {
        this.reportsMismatchBO = reportsMismatchBO;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public IRP_SNAPSHOT_PARAM_MAPPINGVO getRepSnapshotParamMappingVO()
    {
	return repSnapshotParamMappingVO;
    }

    public void setRepSnapshotParamMappingVO(IRP_SNAPSHOT_PARAM_MAPPINGVO repSnapshotParamMappingVO)
    {
	this.repSnapshotParamMappingVO = repSnapshotParamMappingVO;
    }

    public ArrayList<SelectCO> getSnapshotArgCmbList()
    {
	return snapshotArgCmbList;
    }

    public void setSnapshotArgCmbList(ArrayList<SelectCO> snapshotArgCmbList)
    {
	this.snapshotArgCmbList = snapshotArgCmbList;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public String getUpdates2()
    {
	return updates2;
    }

    public void setUpdates2(String updates2)
    {
	this.updates2 = updates2;
    }

    public REP_SNAPSHOT_PARAMCO getRepSnapshotParamCO()
    {
	return repSnapshotParamCO;
    }

    public void setRepSnapshotParamCO(REP_SNAPSHOT_PARAMCO repSnapshotParamCO)
    {
	this.repSnapshotParamCO = repSnapshotParamCO;
    }

    public REP_SNAPSHOT_PARAMVO getRepSnapshotParamVO()
    {
	return repSnapshotParamVO;
    }

    public void setRepSnapshotParamVO(REP_SNAPSHOT_PARAMVO repSnapshotParamVO)
    {
	this.repSnapshotParamVO = repSnapshotParamVO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getSnapshotFreqCmb()
    {
	return snapshotFreqCmb;
    }

    public void setSnapshotFreqCmb(ArrayList<SYS_PARAM_LOV_TRANSVO> snapshotFreqCmb)
    {
	this.snapshotFreqCmb = snapshotFreqCmb;
    }

    public String execute() throws Exception
    {
	return SUCCESS;
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {

	this.snapshotParameterBO = snapshotParameterBO;
    }

    public String loadSnapshotfrequencyCmb() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.FREQUENCY_SNP_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    snapshotFreqCmb = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return SUCCESS;
    }

    public String repParamLookup()
    {
	try
	{
	    fillLookup("", "/path/snapshotParameter/SnapshotParameterListAction_fillParamGrid", "");
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup reports params");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    String[] name = { "PARAM_NAME" };
	    String[] colType = { "text" };
	    String[] titles = { getText("sch.parameters") };
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    lookup(grid, snapshotParameterSC, name, colType, titles);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup reports mismatch");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillParamGrid() throws BaseException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(snapshotParameterSC);
	    setSearchFilter(snapshotParameterSC);
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setPROG_REF(repSnapshotParamVO.getREP_REFERENCE());
	    repSC.setAPP_NAME(sessionCO.getCurrentAppName());
	    IRP_AD_HOC_REPORTCO repIdCO = designerBO.retRepIdByProgRef(repSC);
	    if(repIdCO != null)
	    {
		IRP_AD_HOC_REPORTCO repCO = designerBO.returnReportById(repIdCO.getREPORT_ID());
		ArrayList<IRP_SNAPSHOT_PARAM_MAPPINGVO> listParams = new ArrayList<IRP_SNAPSHOT_PARAM_MAPPINGVO>();
		Iterator<Map.Entry<Long, IRP_REP_ARGUMENTSCO>> itFmap = repCO.getArgumentsList().entrySet().iterator();
		IRP_SNAPSHOT_PARAM_MAPPINGVO repSnapshotParamMappingVO;
		while(itFmap.hasNext())
		{
		    Entry<Long, IRP_REP_ARGUMENTSCO> entry = itFmap.next();
		    if(entry.getValue().getARGUMENT_TYPE().equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_DATE))
		    {
			repSnapshotParamMappingVO = new IRP_SNAPSHOT_PARAM_MAPPINGVO();
			repSnapshotParamMappingVO.setPARAM_NAME(entry.getValue().getARGUMENT_LABEL());
			listParams.add(repSnapshotParamMappingVO);
		    }

		}
		setRecords(listParams.size());
		setGridModel(listParams);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadSnapshotParameterGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    copyproperties(snapshotParameterSC);
	    snapshotParameterSC.setCOMP_CODE(compCode);
	    ArrayList<REP_SNAPSHOT_PARAMCO> list = snapshotParameterBO.retSnapshotsParametersList(snapshotParameterSC);
	    IRP_SNAPSHOT_PARAM_MAPPINGVO vo = new IRP_SNAPSHOT_PARAM_MAPPINGVO();
	    REP_SNAPSHOT_PARAMCO repCO;
	    for(int i = 0; i < list.size(); i++)
	    {
		repCO = list.get(i);
		vo.setPARAM_NAME(repCO.getPARAM_NAME());
		changeMappingToName(vo, repCO);
		repCO.setPARAM_NAME(vo.getPARAM_NAME());
	    }
	    int snpCount = snapshotParameterBO.retSnapshotsParametersListCount(snapshotParameterSC);
	    setRecords(snpCount);
	    setGridModel(list);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadFormulaFuncGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    copyproperties(snapshotParameterSC);
	    snapshotParameterSC.setCOMP_CODE(compCode);
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SNAPSHOTS_FORMULA_PB_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    ArrayList<SYS_PARAM_LOV_TRANSVO> pbFunctionsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    setRecords(pbFunctionsList.size());
	    setGridModel(pbFunctionsList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method for loading reports columns
     * @return
     */
    public String loadFormulaRepColGrid()
    {
	try
	{
	    copyproperties(snapshotParameterSC);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> gridList = new ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>();
	    FTR_OPTCO ftrOptCO = new FTR_OPTCO();
	    ftrOptCO.getFtrOptVO().setPROG_REF(updates2);
	    ftrOptCO.getFtrOptVO().setAPP_NAME(getAppName());
	    ftrOptCO = fcrBO.returnDynamicReportByRef(ftrOptCO);
	    if(ftrOptCO == null)
	    {
		HashMap<String, REP_SNAPSHOT_DRILLDOWN_COLUMNCO> liveSearchHash = ((HashMap<String, REP_SNAPSHOT_DRILLDOWN_COLUMNCO>) (repSessionCO
			.getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_HASH_LIVE_DRIL_COL)));
		if(liveSearchHash != null)
		{
		    Iterator<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> it = (liveSearchHash.values().iterator());
		    while(it.hasNext())
		    {
			gridList.add(it.next());
		    }
		}
	    }
	    else
	    {
		List<COLMNTMPLTCO> dynCols;
		if(ftrOptCO.getFtrOptVO().getROW_COLMN().intValue() == 0)
		{
		    COLMNTMPLTSC colTmplSC = new COLMNTMPLTSC();
		    colTmplSC.setCODE(ftrOptCO.getFtrOptVO().getCOLUMN_CODE());
		    colTmplSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    colTmplSC.setLANG_CODE(sessionCO.getLanguage());
		    dynCols = fcrBO.retDynamicFcrColsFromColTempl(colTmplSC);
		}
		else
		{
		    GLSTMPLTSC tmplSC = new GLSTMPLTSC();
		    tmplSC.setCODE(ftrOptCO.getFtrOptVO().getTMPLT_CODE());
		    tmplSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    dynCols = fcrBO.retDynamicFcrColsFromTempl(tmplSC);
		}
		List<IRP_FCR_FIXED_COLSVO> fixedCols = new ArrayList<IRP_FCR_FIXED_COLSVO>();
		IRP_FCR_FIXED_COLSVO firstFixedCol = new IRP_FCR_FIXED_COLSVO();
		firstFixedCol.setCOL_NAME(RepConstantsCommon.TMPLT_DESC_COL);
		firstFixedCol.setCOL_HEADER("");
		firstFixedCol.setCOL_TYPE(RepConstantsCommon.VARCHAR_TYPE_JASPER);
		fixedCols.add(firstFixedCol);
		BigDecimal compCode = sessionCO.getCompanyCode();
		IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
		repSC.setPROG_REF(updates2);
		repSC.setCOMP_CODE(compCode);
		repSC.setAPP_NAME(ConstantsCommon.REP_APP_NAME);
		IRP_FCR_REPORTSCO irpFcrRepCO = fcrBO.retFcrRep(repSC);
		String ftrOptRef = irpFcrRepCO.getProgRef();
		List<IRP_FCR_FIXED_COLSVO> fixedColsDB = fcrBO.retFixedFcrColsByRef(ftrOptRef);
		fixedCols.addAll(fixedColsDB);
		REP_SNAPSHOT_DRILLDOWN_COLUMNCO repSnpCO;
		for(int i = 0; i < fixedCols.size(); i++)
		{
		    repSnpCO = new REP_SNAPSHOT_DRILLDOWN_COLUMNCO();
		    repSnpCO.getRepSnapshotDrilColVO().setCOLUMN_DRILLDOWN(fixedCols.get(i).getCOL_NAME());
		    gridList.add(repSnpCO);
		}
		for(int i = 0; i < dynCols.size(); i++)
		{
		    repSnpCO = new REP_SNAPSHOT_DRILLDOWN_COLUMNCO();
		    repSnpCO.getRepSnapshotDrilColVO().setCOLUMN_DRILLDOWN(RepConstantsCommon.DYNCOL_FIELD + i);
		    gridList.add(repSnpCO);
		}
	    }
	    setRecords(gridList.size());
	    setGridModel(gridList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadFormulaExprGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    copyproperties(snapshotParameterSC);
	    snapshotParameterSC.setCOMP_CODE(compCode);
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SNAPSHOTS_EXPRESSIONS_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    ArrayList<SYS_PARAM_LOV_TRANSVO> pbFunctionsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    setRecords(pbFunctionsList.size());
	    setGridModel(pbFunctionsList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method to get argument label instead of name
     * @param repSnapshotParamMappingVO
     * @param repCO
     */
    public void changeMappingToName(IRP_SNAPSHOT_PARAM_MAPPINGVO repSnapshotParamMappingVO, REP_SNAPSHOT_PARAMCO repCO)
    {
	try
	{
	    // put the value of param name instead of the label
	    SessionCO sessionCO = returnSessionObject();
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setPROG_REF(repCO.getRepSnapshotParamVO().getREP_REFERENCE());
	    repSC.setAPP_NAME(sessionCO.getCurrentAppName());
	    IRP_AD_HOC_REPORTCO repIdCO = null;
	    if(repSC.getPROG_REF() != null)
	    {
		repIdCO = designerBO.retRepIdByProgRef(repSC);
	    }
	    if(repIdCO != null)
	    {
		repSC = new IRP_AD_HOC_REPORTSC();
		repSC.setREPORT_ID(repIdCO.getREPORT_ID());
		List<IRP_REP_ARGUMENTSCO> argsList = designerBO.retArgsByReport(repSC);
		IRP_REP_ARGUMENTSCO argCO;
		for(int i = 0; i < argsList.size(); i++)
		{
		    argCO = argsList.get(i);
		    if(argCO.getARGUMENT_TYPE().equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_DATE)
			    && argCO.getARGUMENT_NAME().equals(repCO.getPARAM_NAME()))
		    {
			repSnapshotParamMappingVO.setPARAM_NAME(argCO.getARGUMENT_LABEL());
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }
    
    
    /**
     * Method that loads a report params visible in retrieval screen
     * @return
     */
    public String loadFormulaParamGrid()
    {
	try
	{
	    IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	    reportSC.setPROG_REF(updates2);
	    reportSC.setAPP_NAME(getAppName());
	    IRP_AD_HOC_REPORTCO repCO = designerBO.retRepIdByProgRef(reportSC);
	    ArrayList<IRP_REP_ARGUMENTSCO> theModel = new ArrayList<IRP_REP_ARGUMENTSCO>();
	    if(repCO == null)
	    {
		FTR_OPTCO ftrOptCO = new FTR_OPTCO();
		ftrOptCO.getFtrOptVO().setPROG_REF(updates2);
		ftrOptCO.getFtrOptVO().setAPP_NAME(ConstantsCommon.REP_APP_NAME);
		ftrOptCO = fcrBO.returnDynamicReportByRef(ftrOptCO);
		if(ftrOptCO != null)
		{
		    theModel = snapshotParameterBO.loadDynamicParams(ConstantsCommon.FCR_MAIN_REPORT_REF);
		}
	    }
	    else
	    {
		reportSC = new IRP_AD_HOC_REPORTSC();
		reportSC.setREPORT_ID(repCO.getREPORT_ID());
		List<IRP_REP_ARGUMENTSCO> argumentsList = designerBO.retArgsByReport(reportSC);
		IRP_REP_ARGUMENTSCO argCO;
		for(int i = 0; i < argumentsList.size(); i++)
		{
		    argCO = argumentsList.get(i);
		    if(RepConstantsCommon.Y.equals(argCO.getDISPLAY_FLAG()))
		    {
			theModel.add(argCO);
		    }
		}
	    }
	    setRecords(theModel.size());
	    setGridModel(theModel);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String adjustHashRefFreq() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    String progRefOld = repSnapshotParamCO.getProgRefOld();
	    String newProgRef = repSnapshotParamCO.getRepSnapshotParamVO().getREP_REFERENCE();
	    String freqOld = repSnapshotParamCO.getFreqOld();
	    String newFreq = repSnapshotParamCO.getRepSnapshotParamVO().getSNAPSHOT_FREQUENCY();

	    HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>> liveSearchHashDrilCol = ((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (repSessionCO
		    .getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_DRILLDOWN)));
	    HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>> liveSearchHashModCol = ((HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) (repSessionCO
		    .getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_MODIFIED)));
	    HashMap<String, REP_SNAPSHOT_PARAMCO> initMap = ((HashMap<String, REP_SNAPSHOT_PARAMCO>) (repSessionCO
		    .getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_INIT_MAP)));
	    /* updating progref and frequency in the initMap */
	    REP_SNAPSHOT_PARAMCO co;
	    co = initMap.get(progRefOld + "~" + freqOld);
	    initMap.remove(progRefOld + "~" + freqOld);
	    initMap.put(newProgRef + "~" + newFreq, co);
	    if(liveSearchHashDrilCol == null)
	    {
		SnapshotParameterSC sc = new SnapshotParameterSC();
		sc.setREP_REFERENCE(progRefOld);
		sc.setSNAPSHOT_FREQUENCY(freqOld);
		sc.setGrid(false);
		ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> list = snapshotParameterBO.retDrilldownColumnList(sc);
		repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_DRILLDOWN,
			new HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>());
		HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>> drillHm = (HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) repSessionCO
			.getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_DRILLDOWN);
		drillHm.put(progRefOld + "~" + freqOld, list);
		liveSearchHashDrilCol = ((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (repSessionCO
			.getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_DRILLDOWN)));
	    }

	    ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> oldDrillCols = liveSearchHashDrilCol.get(progRefOld + "~"
		    + freqOld);
	    // added to handle case where no data found when retrieving from db
	    // because an empty entry will exist in session
	    // and will cause an nullPointer on db save
	    if(oldDrillCols == null || oldDrillCols.isEmpty())
	    {
		((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) repSessionCO.getSnParameterScreenHash()
			.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN)).remove(progRefOld + "~" + freqOld);
	    }

	    else
	    {
		liveSearchHashDrilCol.remove(progRefOld + "~" + freqOld);
		liveSearchHashDrilCol.put(newProgRef + "~" + newFreq, oldDrillCols);
	    }

	    if(liveSearchHashModCol == null)
	    {
		// fill the hashMap from the DB
		SnapshotParameterSC sc = new SnapshotParameterSC();
		sc.setREP_REFERENCE(progRefOld);
		sc.setSNAPSHOT_FREQUENCY(freqOld);
		sc.setGrid(false);
		ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> list = snapshotParameterBO.retModifiedColumnList(sc);
		repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_MODIFIED,
			new HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>());
		HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>> colMap = (HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) repSessionCO
			.getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_MODIFIED);
		colMap.put(progRefOld + "~" + freqOld, list);

		liveSearchHashModCol = ((HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) (repSessionCO
			.getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_MODIFIED)));
	    }

	    ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> oldCols = liveSearchHashModCol.get(progRefOld + "~" + freqOld);
	    // added to handle case where no data found when retrieving from db
	    // because an empty entry will exist in session
	    // and will cause an nullPointer on db save
	    if(oldCols == null || oldCols.isEmpty())
	    {
		((HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) repSessionCO.getSnParameterScreenHash()
			.get(RepConstantsCommon.SNAPSHOT_MODIFIED)).remove(progRefOld + "~" + freqOld);
	    }
	    else
	    {
		liveSearchHashModCol.remove(progRefOld + "~" + freqOld);
		liveSearchHashModCol.put(newProgRef + "~" + newFreq, oldCols);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String checkSnInformation()
    {
	try
	{
	    snapshotParameterSC.setREP_ID(repSnapshotParamVO.getREP_ID());
	    if(snapshotParameterBO.checkSnInformation(snapshotParameterSC) > 0)
	    {
		setUpdates2("5");
	    }
	    else
	    {
		setUpdates2("0");
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applySnParamAudit() throws JSONException
    {
	try
	{
	    applyRetrieveAudit(AuditConstant.SNAPSHOT_PARAM_KEY_REF, repSnapshotParamVO, repSnapshotParamVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadSnapshotModColGrid() throws JSONException
    {
	return SUCCESS;
    }

    public String openModifColDialog() throws JSONException
    {
	fillColsTechNameMap();	
	return "successModifiedColumns";
    }
    
    
    public void fillColsTechNameMap()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    String progRef = repSnapshotParamVO.getREP_REFERENCE();
	    /*
	     * fill for each progRef a hashMap(technicalColName, lblField) in
	     * order to show the lblField in the grids of crt col and rel. cols
	     */

	    HashMap<String, HashMap<String, Object>> techNameMap =   (HashMap<String, HashMap<String, Object>>) repSessionCO.getSnParameterScreenHash().get(RepConstantsCommon.MISMATCH_COL_TECH_NAME);
	    if(techNameMap == null)
	    {
		repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.MISMATCH_COL_TECH_NAME,
			new HashMap<String, HashMap<Object, String>>());
		techNameMap =  (HashMap<String, HashMap<String, Object>>) repSessionCO.getSnParameterScreenHash().get(RepConstantsCommon.MISMATCH_COL_TECH_NAME);

	    }
	    if(techNameMap.get(progRef) == null)
	    {
		// return reportCO from progRef
		SessionCO sessionCO = returnSessionObject();
		IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
		repSC.setPROG_REF(progRef);
		repSC.setAPP_NAME(sessionCO.getCurrentAppName());
		HashMap<String, Object> colTechMap = reportsMismatchBO.fillColTechNameMap(repSC);
		if(colTechMap != null)
		{
		    techNameMap.put(progRef, colTechMap);
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	
    }

    public String openFormulaDialog() throws Exception
    {
	return "formulaDialog";
    }

    public String openDrilColDialog() throws JSONException
    {
	fillColsTechNameMap();
	return "successDrilldownColumns";
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public Object getModel()
    {
	return snapshotParameterSC;
    }
    
    public String snpFreqLookup()
    {
	try
	{
	    fillLookup("/path/snapshotParameter/SnapshotParameterListAction_loadSnpFreqGrid.action?_pageRef="
		    + get_pageRef());
	}
	catch(Exception e)
	{
	   // log.error("Error filling snpashot frequency lookup");
	    handleException(e, "fillReportsLkp.exceptionMsg._key", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadSnpFreqGrid() throws JSONException
    {
	try
	{
	    copyproperties(snapshotParameterSC);
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String repRef=getCode();
	    if(repRef.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	    {
		repRef=repRef.substring(0,repRef.length()-3);
	    }
	    snapshotParameterSC.setREP_REFERENCE(repRef);
	    snapshotParameterSC.setCOMP_CODE(compCode);
	    snapshotParameterSC.setAOD_TYPE(BigDecimal.ONE);
	    List<REP_SNAPSHOT_PARAMCO> snpFreqList = snapshotParameterBO.retSnpFrequenciesLst(snapshotParameterSC);
	    setGridModel(snpFreqList);
	    //no need for flipping here since the max number of records can be 4
	    setRecords(snpFreqList.size());
	}
	catch(Exception e)
	{
	    //log.error(e, "Error in method loadMailServerGrid in MailServerAction");
	    handleException(e, "Error Loading mail server list", "error loading the mail server grid");
	}
	return SUCCESS;
    }

    public String fillLookup(String gridUrl) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "repSnapshotParamVO.REP_ID", "repSnapshotParamVO.REP_REFERENCE",
		    "repSnapshotParamVO.SNAPSHOT_FREQUENCY", "AS_OF_DATE_PARAM_NAME" };
	    String[] colType = { "number", "text", "text", "text" };
	    String[] titles = { getText("reportId"), getText("template.relatedReports.reference"),
		    getText("snapshots.frequency"),  getText("As_Of_Date_key") };
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setRowNum("10");
	    grid.setShrinkToFit("true");
	    grid.setUrl(gridUrl);
	    
	    List<LookupGridColumn> lstGridColumn = returnStandarColSpecs(name, colType, titles);
	    // lstGridColumn.get(5).setHidden(true);
	    lookup(grid, lstGridColumn, null, snapshotParameterSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling snapshot frequency lookup");
	    handleException(e, "fillReportsLkp.exceptionMsg._key", "fillReportsLkp.exceptionMsg._key");
	}
	return "successJson";
    }


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}

}
