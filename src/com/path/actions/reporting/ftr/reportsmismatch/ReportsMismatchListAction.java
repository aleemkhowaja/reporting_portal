package com.path.actions.reporting.ftr.reportsmismatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchBO;
import com.path.dbmaps.vo.REP_MISMATCH_PARAMVO;
import com.path.dbmaps.vo.reportsmismatch.ReportsMismatchSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_INTRA_CRITERIACO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PARAMCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * ReportsMismatchListAction.java used to
 */
public class ReportsMismatchListAction extends ReportingLookupBaseAction
{
    private ReportsMismatchBO reportsMismatchBO;
    private ReportsMismatchSC reportsMismatchSC = new ReportsMismatchSC();
    private String update; // 1:main grid ; 2 :related reports
    private BigDecimal mismatchType;
    private REP_MISMATCH_PARAMVO repMismatchParamVO;
    private REP_MISMATCH_PARAMVO repMisGridParamVO = new REP_MISMATCH_PARAMVO();
    private String crtColOrRelCol; // 1:from crt col ; 2 :from related cols
    private String CRT_COL;
    // storing the updates of the grid in related columns/deleted rows related
    // reports
    private String updates;
    private REP_MISMATCH_PARAMCO repMismatchParamCO;
    // related reports rows in the screen
    private String updates1;
    // updates2=="" because it's being used in the screen!
    private String updates2 = "";
    // private CommonLibBO commonLibBO;

    // public void setCommonLibBO(CommonLibBO commonLibBO)
    // {
    // this.commonLibBO = commonLibBO;
    // }

    public REP_MISMATCH_PARAMVO getRepMisGridParamVO()
    {
	return repMisGridParamVO;
    }

    public void setRepMisGridParamVO(REP_MISMATCH_PARAMVO repMisGridParamVO)
    {
	this.repMisGridParamVO = repMisGridParamVO;
    }

    public String getUpdates2()
    {
	return updates2;
    }

    public void setUpdates2(String updates2)
    {
	this.updates2 = updates2;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public REP_MISMATCH_PARAMCO getRepMismatchParamCO()
    {
	return repMismatchParamCO;
    }

    public void setRepMismatchParamCO(REP_MISMATCH_PARAMCO repMismatchParamCO)
    {
	this.repMismatchParamCO = repMismatchParamCO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public BigDecimal getMismatchType()
    {
	return mismatchType;
    }

    public void setMismatchType(BigDecimal mismatchType)
    {
	this.mismatchType = mismatchType;
    }

    public String getCRT_COL()
    {
	return CRT_COL;
    }

    public void setCRT_COL(String cRTCOL)
    {
	CRT_COL = cRTCOL;
    }

    public String getCrtColOrRelCol()
    {
	return crtColOrRelCol;
    }

    public void setCrtColOrRelCol(String crtColOrRelCol)
    {
	this.crtColOrRelCol = crtColOrRelCol;
    }

    public REP_MISMATCH_PARAMVO getRepMismatchParamVO()
    {
	return repMismatchParamVO;
    }

    public void setRepMismatchParamVO(REP_MISMATCH_PARAMVO repMismatchParamVO)
    {
	this.repMismatchParamVO = repMismatchParamVO;
    }

    public ReportsMismatchSC getReportsMismatchSC()
    {
	return reportsMismatchSC;
    }

    public void setReportsMismatchSC(ReportsMismatchSC reportsMismatchSC)
    {
	this.reportsMismatchSC = reportsMismatchSC;
    }

    public void setReportsMismatchBO(ReportsMismatchBO reportsMismatchBO)
    {
	this.reportsMismatchBO = reportsMismatchBO;
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
	    //colTechMap(progRef, HashMap(techName, lblAlias))
	    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_COL_TECH_NAME,new HashMap<String,HashMap<String ,Object>>());
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

    public String loadReportsMismatchGrid() throws JSONException
    {
	try
	{
	    if("1".equals(update)) // called from the main grid
	    {
		SessionCO sessionCO = returnSessionObject();
		BigDecimal compCode = sessionCO.getCompanyCode();
		copyproperties(reportsMismatchSC);
		reportsMismatchSC.setCompCode(compCode);
		reportsMismatchSC.setMisType(mismatchType);
		ArrayList<REP_MISMATCH_PARAMCO> list = reportsMismatchBO.retReportsMismatchList(reportsMismatchSC);
		int repMisCnt = reportsMismatchBO.retReportsMismatchListCount(reportsMismatchSC);
		setRecords(repMisCnt);
		setGridModel(list);
	    }
	    else
	    // called from the related reports
	    {
		SessionCO sessionCO = returnSessionObject();
		BigDecimal compCode = sessionCO.getCompanyCode();
		copyproperties(reportsMismatchSC);
		reportsMismatchSC.setCompCode(compCode);
		reportsMismatchSC.setRepReference(repMisGridParamVO.getREP_REFERENCE());
		reportsMismatchSC.setCriteriaCode(repMisGridParamVO.getCRITERIA_CODE());
		ArrayList<REP_MISMATCH_PARAMCO> list;
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
		HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>> relRepHash = (HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) repMisParameterScreenHash
			.get(RepConstantsCommon.MISMATCH_REL_REPORTS);

		if(relRepHash == null || relRepHash.get(repMisGridParamVO.getCRITERIA_CODE()) == null)
		{
		    if(relRepHash == null)
		    {
			repMisParameterScreenHash.put(RepConstantsCommon.MISMATCH_REL_REPORTS,
				new HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>());
			relRepHash = new HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>();
			repMisParameterScreenHash.put(RepConstantsCommon.MISMATCH_REL_REPORTS, relRepHash);
		    }
		    if(repMisGridParamVO.getREP_MISMATCH_ID() == null
			    || ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(repMisGridParamVO.getREP_MISMATCH_ID()))
		    {
			list = new ArrayList<REP_MISMATCH_PARAMCO>();			
		    }
		    else
		    {
			list = reportsMismatchBO.retRelatedReportsMismatchList(reportsMismatchSC);
		    }

		    HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> listHash = new HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>();
		    listHash.put(RepConstantsCommon.MISMATCH_ALL_REC, list);
		    listHash.put(RepConstantsCommon.MISMATCH_LST_DEL, new ArrayList<REP_MISMATCH_PARAMCO>());
		    relRepHash.put(repMisGridParamVO.getCRITERIA_CODE(), listHash);

		}
		else
		{
		    list = relRepHash.get(repMisGridParamVO.getCRITERIA_CODE())
			    .get(RepConstantsCommon.MISMATCH_ALL_REC);

		}
		int repMisCnt = reportsMismatchBO.retRelatedReportsMismatchListCount(reportsMismatchSC);
		setRecords(repMisCnt);
		setGridModel(list);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String loadReportsMismatchRelatedColsList() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		HashMap<String, HashMap<String, Object>> techNameMap = (HashMap<String, HashMap<String,Object>>) repSessionCO.getRepMisParameterScreenHash()
			.get(RepConstantsCommon.MISMATCH_COL_TECH_NAME);
		 HashMap<String, Object> hm=techNameMap.get(repMismatchParamVO.getREP_REFERENCE());
	    if( "1".equals(crtColOrRelCol)) // called from criteria column
	    {
		ArrayList<REP_MISMATCH_COLUMNCO> list = new ArrayList<REP_MISMATCH_COLUMNCO>();
		if(!"".equals(repMismatchParamVO.getCOLUMN_TYPE())
			&& !"".equals(repMismatchParamVO.getCRITERIA_COLUMN()))
		{
		    REP_MISMATCH_COLUMNCO co = new REP_MISMATCH_COLUMNCO();
		    co.getRepMismatchColumnVO().setREP_MISMATCH_ID(repMismatchParamVO.getREP_MISMATCH_ID());
		    co.getRepMismatchColumnVO().setCOLUMN_TYPE(repMismatchParamVO.getCOLUMN_TYPE());
		    co.getRepMismatchColumnVO().setRELATED_COLUMN(fillColDescription(hm, repMismatchParamVO.getCRITERIA_COLUMN()));
		    co.setTECH_COL_NAME(repMismatchParamVO.getCRITERIA_COLUMN());
		    list.add(co);
		}

		setRecords(list.size());
		setGridModel(list);
	    }
	    else
	    // called from related columns
	    {
		ArrayList<REP_MISMATCH_COLUMNCO> list;
		HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
		if((repMisParameterScreenHash.get(RepConstantsCommon.MISMATCH_REL_COLS) == null)
			|| ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
				.get(RepConstantsCommon.MISMATCH_REL_COLS)).get(repMismatchParamVO.getREP_REFERENCE()
				+ "~" + repMismatchParamVO.getCRITERIA_CODE()) == null)
		{
		    if(repMisParameterScreenHash.get(RepConstantsCommon.MISMATCH_REL_COLS) == null)
		    {
			repMisParameterScreenHash.put(RepConstantsCommon.MISMATCH_REL_COLS,
				new HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>());
		    }
		    if(repMismatchParamVO.getREP_MISMATCH_ID() == null
			    || ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(repMismatchParamVO.getREP_MISMATCH_ID()))
		    {
			list = new ArrayList<REP_MISMATCH_COLUMNCO>();
		    }
		    else
		    {
			reportsMismatchSC.setColSearchQuery(null);
			reportsMismatchSC.setREP_MISMATCH_ID(repMismatchParamVO.getREP_MISMATCH_ID());
			list = reportsMismatchBO.retReportsMismatchRelColsList(reportsMismatchSC);
			// it's a db record and if the list is empty , remove it
			// from the session to avoid adding an empty entry on
			// the save because this will cause an unecessary delete
			// from the db
			((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repSessionCO
				.getRepMisParameterScreenHash().get(RepConstantsCommon.MISMATCH_REL_COLS)))
				.remove(repMismatchParamVO.getREP_REFERENCE() + "~"
					+ repMismatchParamVO.getCRITERIA_CODE());
		    }
		    if(!list.isEmpty())
		    {
			((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
				.get(RepConstantsCommon.MISMATCH_REL_COLS))).put(repMismatchParamVO.getREP_REFERENCE()
				+ "~" + repMismatchParamVO.getCRITERIA_CODE(), list);
		    }
		}
		else
		{
		    list = ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_COLS))).get(repMismatchParamVO.getREP_REFERENCE()
			    + "~" + repMismatchParamVO.getCRITERIA_CODE());
		}
		
		//fill column description from technical name since the description is not saved in the DB
		REP_MISMATCH_COLUMNCO repMisCO;
		for(int i=0;i<list.size();i++)
		{
		    repMisCO=list.get(i);
		    repMisCO.getRepMismatchColumnVO().setRELATED_COLUMN(fillColDescription(hm, repMisCO.getRepMismatchColumnVO().getRELATED_COLUMN()));
		}
		setRecords(list.size());
		setGridModel(list);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    public String fillColDescription(HashMap<String, Object> hm, String colTechName)
    {
	String lblAlias = colTechName;
	if(hm != null)
	{
	    lblAlias = (String) hm.get(colTechName);
	    if(lblAlias == null)
	    {
		lblAlias = colTechName;
	    }

	}
	return lblAlias;
    }

    public String reportLookup()
    {
	try
	{
	    fillLookup("", "/path/reportsMismatch/ReportsMismatchListAction_fillReportGrid", "");
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup reports mismatch");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    String[] name = { "PROG_DESC", "repSnapshotParamVO.REP_REFERENCE" };
	    String[] colType = { "text", "text" };
	    String[] titles = { getText("reportName"), getText("progRef") };
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    lookup(grid, reportsMismatchSC, name, colType, titles);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup reports mismatch");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillReportGrid() throws BaseException
    {
	copyproperties(reportsMismatchSC);
	setSearchFilter(reportsMismatchSC);
	SessionCO sessionCO = returnSessionObject();
	reportsMismatchSC.setAppName(sessionCO.getCurrentAppName());
	ArrayList<REP_SNAPSHOT_PARAMCO> reportList = reportsMismatchBO.retReportsMismatchLookupList(reportsMismatchSC);
	int recordsCount = reportsMismatchBO.retReportsMismatchLookupListCount(reportsMismatchSC);
	setRecords(recordsCount);
	setGridModel(reportList);
	return SUCCESS;
    }

    public String fillLiveSearchCriteriaColumnGrid() throws BaseException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    // added to flip
	    copyproperties(reportsMismatchSC);
	    setSearchFilter(reportsMismatchSC);
	    int nbRec = reportsMismatchSC.getNbRec();
	    int recToSkip = reportsMismatchSC.getRecToskip();
	    HashMap<String, REP_MISMATCH_COLUMNCO> liveSearchHash = ((HashMap<String, REP_MISMATCH_COLUMNCO>) (repSessionCO
		    .getRepMisParameterScreenHash().get(RepConstantsCommon.MISMATCH_CRITERIA_COLUMN_HASH)));
	    int maxRec = nbRec + recToSkip;
	    ArrayList<REP_MISMATCH_COLUMNCO> ls = new ArrayList<REP_MISMATCH_COLUMNCO>();
	    if(liveSearchHash == null)
	    {
		liveSearchHash = new LinkedHashMap<String, REP_MISMATCH_COLUMNCO>();
	    }
	    if(liveSearchHash.size() < maxRec)
	    {
		maxRec = liveSearchHash.size();
	    }

	    for(int i = recToSkip; i < maxRec; i++)
	    {
		ls.add((REP_MISMATCH_COLUMNCO) liveSearchHash.values().toArray()[i]);
	    }
	    setRecords(liveSearchHash.size());
	    setGridModel(ls);
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    log.error(e, "");
	    return null;
	}
    }

    public String criteriaColumnLookup()
    {
	try
	{
	    fillCriteriaColumnLookup("",
		    "/path/reportsMismatch/ReportsMismatchListAction_fillLiveSearchCriteriaColumnGrid.action", "");
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillCriteriaColumnLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    String[] name = { "repMismatchColumnVO.REP_MISMATCH_ID", "repMismatchColumnVO.RELATED_COLUMN",
		    "repMismatchColumnVO.COLUMN_TYPE","TECH_COL_NAME" };
	    String[] colType = { "number", "text", "text","text" };
	    String[] titles = { getText("reportId"), getText("reportsMismatch.criteriaColumn"), getText("col.colType"),"TECH_COL_NAME" };
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    grid.setShrinkToFit("true");
	    grid.setFilter(false);
	    List<LookupGridColumn> lstGridColumn = returnStandarColSpecs(name, colType, titles);
	    lstGridColumn.get(0).setHidden(true);
	    lstGridColumn.get(2).setHidden(true);
	    lstGridColumn.get(3).setHidden(true);
	    lstGridColumn.get(1).setSearch(false);
	    lookup(grid, lstGridColumn, null, reportsMismatchSC);
	}
	catch(Exception e)
	{
	   // log.error(e, "Error filling reports lookup");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String saveRelatedColumnsMismatch() throws Exception
    {
	try
	{
	    if(updates != null && !updates.equals(""))
	    {// no need to get lstadd,lstupdate,lstdelete since we will always
		// delete and insert at the end
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		GridUpdates gu = getGridUpdates(updates, REP_MISMATCH_COLUMNCO.class, true);
		ArrayList<REP_MISMATCH_COLUMNCO> distinct = gu.getLstAllRec();
		ArrayList<REP_MISMATCH_COLUMNCO> finalList = new ArrayList<REP_MISMATCH_COLUMNCO>();
		Iterator<REP_MISMATCH_COLUMNCO> it = distinct.iterator();
		REP_MISMATCH_COLUMNCO co;
		Iterator<REP_MISMATCH_COLUMNCO> itt;
		boolean alreadyAdded;
		REP_MISMATCH_COLUMNCO coo;
		while(it.hasNext())
		{
		    co = it.next();
		    itt = finalList.iterator();
		    alreadyAdded = false;
		    while(itt.hasNext())
		    {
			coo = itt.next();
			if(coo.getRepMismatchColumnVO().getRELATED_COLUMN().equals(
				co.getRepMismatchColumnVO().getRELATED_COLUMN()))
			{
			    alreadyAdded = true;
			}
		    }
		    if(!alreadyAdded && !"".equals(co.getRepMismatchColumnVO().getRELATED_COLUMN()))
		    {
			if(co.getRepMismatchColumnVO().getREP_MISMATCH_ID().equals(
				ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
			{
			    co.getRepMismatchColumnVO().setREP_MISMATCH_ID(repMismatchParamVO.getREP_MISMATCH_ID());
			}
			finalList.add(co);
		    }
		}

		HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>> relColsMap = (HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repSessionCO
			.getRepMisParameterScreenHash().get(RepConstantsCommon.MISMATCH_REL_COLS));

		relColsMap.put(repMismatchParamVO.getREP_REFERENCE() + "~" + repMismatchParamVO.getCRITERIA_CODE(),
			finalList);

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String adjustHashRefCrt() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    String oldProgRef = repMismatchParamCO.getOldProgRef();
	    String newProgRef = repMismatchParamCO.getRepMismatchParamVO().getREP_REFERENCE();
	    String oldCrt = repMismatchParamCO.getOldCrt();
	    String newCrt = repMismatchParamCO.getRepMismatchParamVO().getCRITERIA_CODE();
	    BigDecimal repMismatchId = repMismatchParamCO.getRepMismatchParamVO().getREP_MISMATCH_ID();
	    HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
	    // adjusting in the hash of related columns
	    HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>> relColHash = ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_COLS)));
	    HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>> relRepHash = (HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_REPORTS));
	    HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>> relatedCriteriaHash = (HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA);
	    HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap = (HashMap<String, REP_MISMATCH_PARAMCO>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_INTER_INTRA));
	    // criteria changed
	    // criteria can be changed only in the main grid
	    if(oldProgRef.equals(newProgRef))
	    {
		// change keys in interIntra hash
		REP_MISMATCH_PARAMCO interCO = interIntraMap.get(oldProgRef + "~" + oldCrt);
		if(interCO != null)
		{
		    interIntraMap.remove(oldProgRef + "~" + oldCrt);
		    interIntraMap.put(oldProgRef + "~" + newCrt, interCO);
		    /*
		     * now has to check all the reports having same criteria
		     * loaded and update their entries with the new criteria
		     */
		    HashMap<String, REP_MISMATCH_PARAMCO> tempInterIntra = (HashMap<String, REP_MISMATCH_PARAMCO>) interIntraMap
			    .clone();
		    Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itTempInterIntra = tempInterIntra.entrySet()
			    .iterator();
		    Entry<String, REP_MISMATCH_PARAMCO> entry;
		    while(itTempInterIntra.hasNext())
		    {
			entry = itTempInterIntra.next();
			String theKey = entry.getKey();
			if(theKey.substring(theKey.indexOf("~") + 1, theKey.length()).equals(oldCrt))
			{
			    interCO = entry.getValue();
			    interIntraMap.remove(theKey);
			    interIntraMap.put(theKey.substring(0, theKey.indexOf("~") + 1) + newCrt, interCO);
			}
		    }
		}
		// change the keys in the cols hash of the record of the main
		// grid
		if(relColHash != null)
		{

		    ArrayList<REP_MISMATCH_COLUMNCO> oldRelCols = relColHash.get(oldProgRef + "~" + oldCrt);
		    if(oldRelCols != null)
		    {
			relColHash.remove(oldProgRef + "~" + oldCrt);
			relColHash.put(oldProgRef + "~" + newCrt, oldRelCols);
		    }
		}
		// change the key in the related report hash
		// 2-Change the crt value inside each vo of the related
		// reports
		// ,Note that if the rel. reports is not loaded then we should
		// load it
		// 3- change the key in the related cols of the related
		// reports
		// already in session
		// mismatchType ==1 => inter reports
		if(BigDecimal.valueOf(1).equals(mismatchType))
		{
		    HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> currRelRepMap = relRepHash.get(oldCrt);
		    if(currRelRepMap == null)
		    // load it from the database
		    {
			reportsMismatchSC.setCompCode(sessionCO.getCompanyCode());
			reportsMismatchSC.setRepReference(oldProgRef);
			reportsMismatchSC.setCriteriaCode(oldCrt);
			ArrayList<REP_MISMATCH_PARAMCO> list = reportsMismatchBO
				.retRelatedReportsMismatchList(reportsMismatchSC);
			REP_MISMATCH_PARAMCO co;
			// ArrayList<REP_MISMATCH_COLUMNCO> listColCO;
			for(int i = 0; i < list.size(); i++)
			{
			    co = list.get(i);
			    co.getRepMismatchParamVO().setCRITERIA_CODE(newCrt);
			}
			HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> newRelRepHash = new HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>();
			newRelRepHash.put(RepConstantsCommon.MISMATCH_LST_DEL, new ArrayList<REP_MISMATCH_PARAMCO>());
			newRelRepHash.put(RepConstantsCommon.MISMATCH_ALL_REC, list);
			relRepHash.put(newCrt, newRelRepHash);
		    }
		    else
		    {
			ArrayList<REP_MISMATCH_PARAMCO> lstAll = (currRelRepMap)
				.get(RepConstantsCommon.MISMATCH_ALL_REC);
			REP_MISMATCH_PARAMCO co;
			ArrayList<REP_MISMATCH_COLUMNCO> listColCO;
			for(int i = 0; i < lstAll.size(); i++)
			{
			    co = lstAll.get(i);
			    co.getRepMismatchParamVO().setCRITERIA_CODE(newCrt);
			    co.setOldCrt(newCrt);
			    listColCO = relColHash.get(co.getRepMismatchParamVO().getREP_REFERENCE() + "~" + oldCrt);
			    relColHash.remove(co.getRepMismatchParamVO().getREP_REFERENCE() + "~" + oldCrt);
			    relColHash.put(co.getRepMismatchParamVO().getREP_REFERENCE() + "~" + newCrt, listColCO);
			}
			relRepHash.put(newCrt, currRelRepMap);
			relRepHash.remove(oldCrt);
		    }

		}
		/*
		 * prog ref did not change in the intra reports but changed the
		 * criteria
		 */
		else
		{
		    /*
		     * check if the entered new criteria does not exist among
		     * the list of related criterias
		     */
		    REP_MISMATCH_INTRA_CRITERIACO co;
		    ArrayList<REP_MISMATCH_INTRA_CRITERIACO> relatedCriteriatList = new ArrayList<REP_MISMATCH_INTRA_CRITERIACO>();
		    if(relatedCriteriaHash.get(oldProgRef + "~" + oldCrt) != null)
		    {
			relatedCriteriatList = relatedCriteriaHash.get(oldProgRef + "~" + oldCrt);
		    }
		    /*
		     * load the related criteria from db to check if any
		     * duplication
		     */
		    if(relatedCriteriaHash.get(oldProgRef + "~" + oldCrt) == null)
		    {
			reportsMismatchSC.setREP_MISMATCH_ID(repMismatchId);
			relatedCriteriatList = reportsMismatchBO.retReportsMismatchRelCriteriaList(reportsMismatchSC);
			relatedCriteriaHash.put(oldProgRef + "~" + oldCrt, relatedCriteriatList);
		    }
		    for(int i = 0; i < relatedCriteriatList.size(); i++)
		    {
			co = relatedCriteriatList.get(i);
			if(co.getRepMismatchIntraCriteriaVO().getRELATED_CRITERIA().equals(newCrt))
			{
			    setUpdates2(newProgRef);
			    return SUCCESS;
			}
		    }
		    relatedCriteriaHash.remove(oldProgRef + "~" + oldCrt);
		    relatedCriteriaHash.put(oldProgRef + "~" + newCrt, relatedCriteriatList);

		}
	    }
	    /*
	     * progRef changed.it can be changed in the main grid and also in
	     * the related reports grid
	     */
	    else
	    {
		// change keys in interIntra hash
		REP_MISMATCH_PARAMCO parentCO = interIntraMap.get(oldProgRef + "~" + oldCrt);
		if(parentCO != null)
		{
		    interIntraMap.remove(oldProgRef + "~" + oldCrt);
		    interIntraMap.put(newProgRef + "~" + oldCrt, parentCO);
		}
		// if progRef changed ,check if the new one does not
		// exist in the related reports
		if( "1".equals(update) && BigDecimal.valueOf(1).equals(mismatchType))
		{
		    if(relRepHash.get(oldCrt) == null)
		    /*
		     * will load the related reports from the db and check if
		     * anyone has same progref
		     */
		    {
			reportsMismatchSC.setCompCode(sessionCO.getCompanyCode());
			reportsMismatchSC.setRepReference(oldProgRef);
			reportsMismatchSC.setCriteriaCode(oldCrt);
			ArrayList<REP_MISMATCH_PARAMCO> list = reportsMismatchBO
				.retRelatedReportsMismatchList(reportsMismatchSC);
			REP_MISMATCH_PARAMCO co;
			for(int i = 0; i < list.size(); i++)
			{
			    co = list.get(i);
			    if(co.getRepMismatchParamVO().getREP_REFERENCE().equals(newProgRef))
			    {
				setUpdates2(newProgRef);
				return SUCCESS;
			    }

			}
		    }
		    else
		    {
			ArrayList<REP_MISMATCH_PARAMCO> lstAll = (relRepHash.get(oldCrt))
				.get(RepConstantsCommon.MISMATCH_ALL_REC);
			REP_MISMATCH_PARAMCO co;
			for(int i = 0; i < lstAll.size(); i++)
			{
			    co = lstAll.get(i);
			    if(co.getRepMismatchParamVO().getREP_REFERENCE().equals(newProgRef))
			    {
				setUpdates2(newProgRef);
			    }
			}
		    }

		}
		/* Prog Ref changed in the related reports screen */
		else if("2".equals(update) && BigDecimal.valueOf(1).equals(mismatchType))
		{
		    // get listAll using oldCrt
		    ArrayList<REP_MISMATCH_PARAMCO> listAll = relRepHash.get(oldCrt).get(
			    RepConstantsCommon.MISMATCH_ALL_REC);
		    for(int i = 0; i < listAll.size(); i++)
		    {
			REP_MISMATCH_PARAMCO co = listAll.get(i);
			// if found the co,if has a mismatch id=>put new
			// ArrayList in the hash.else remove it from the
			// session
			if(co.getRepMismatchParamVO().getREP_REFERENCE().equals(oldProgRef))
			{
			    ArrayList<REP_MISMATCH_COLUMNCO> listCols = relColHash.get(oldProgRef + "~" + oldCrt);
			    if(listCols != null && !listCols.isEmpty())
			    {

				relColHash.remove(oldProgRef + "~" + oldCrt);
				relColHash.put(newProgRef + "~" + oldCrt, new ArrayList<REP_MISMATCH_COLUMNCO>());
			    }
			    co.getRepMismatchParamVO().setREP_REFERENCE(newProgRef);
			}
		    }
		}

		/*
		 * if changed progRef in intra reports adjust the related
		 * criteria hash also
		 */
		else if( BigDecimal.valueOf(0).equals(mismatchType) &&(relatedCriteriaHash.get(oldProgRef + "~" + oldCrt) != null) )
		{
			ArrayList<REP_MISMATCH_INTRA_CRITERIACO> list = relatedCriteriaHash.get(oldProgRef + "~"
				+ oldCrt);
			relatedCriteriaHash.remove(oldProgRef + "~" + oldCrt);
			relatedCriteriaHash.put(newProgRef + "~" + oldCrt, list);
		}
		/*
		 * if changed progRef in main grid and same progRef exists in
		 * related reports,below code will not be reached since there's
		 * a return above.if below code is reached , related columns of
		 * the report in related reports will be affected.wrong.related
		 * columns of the related report should stay the same
		 */
		if(relColHash != null)
		{
		    /* empty the related columns hash */
		    relColHash.remove(oldProgRef + "~" + oldCrt);
		    relColHash.put(newProgRef + "~" + newCrt, new ArrayList<REP_MISMATCH_COLUMNCO>());
		}

	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return ERROR;
	}
	return SUCCESS;
    }

    public String saveRelatedReports() throws Exception
    {
	try
	{
	    // put allRec in the relRepHash of the crt
	    if(updates1 != null && !updates1.equals("") && !updates1.equals("{\"root\":[]}"))
	    {
		String crtCode = repMismatchParamVO.getCRITERIA_CODE();
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
		GridUpdates gu = getGridUpdates(updates1, REP_MISMATCH_PARAMCO.class, true);
		// all records in the grid APPEARING in the screen
		ArrayList<REP_MISMATCH_PARAMCO> listAllRelRep = gu.getLstAllRec();
		// get(MISMATCH_REL_REPORTS)==>relRepHash hash of the related
		// reports
		HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>> relRepHash = (HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) repMisParameterScreenHash
			.get(RepConstantsCommon.MISMATCH_REL_REPORTS);
		// getting all the related reports related to crtCode
		HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> hashAllByCrtCode = relRepHash.get(crtCode);
		if(!listAllRelRep.isEmpty())
		{
		    hashAllByCrtCode.put(RepConstantsCommon.MISMATCH_ALL_REC, listAllRelRep);
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /*
     * public void printContentHash() {
     * 
     * try { ReportingSessionCO repSessionCO =
     * returnReportingSessionObject(get_pageRef()); StringBuffer sb = new
     * StringBuffer(); sb.append(
     * "#########################################################################################################################\n\n\n\n"
     * );sb.append(
     * "**********************************RELATED REPORTS**********************************\n\n\n"
     * ); HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>
     * hashRelRep = (HashMap<String, HashMap<String,
     * ArrayList<REP_MISMATCH_PARAMCO>>>) (repSessionCO
     * .getRepMisParameterScreenHash
     * ().get(RepConstantsCommon.MISMATCH_REL_REPORTS)); Set keySet =
     * hashRelRep.keySet(); Iterator it = keySet.iterator(); while(it.hasNext())
     * { String key = (String) it.next(); HashMap<String,
     * ArrayList<REP_MISMATCH_PARAMCO>> loc = (HashMap<String,
     * ArrayList<REP_MISMATCH_PARAMCO>>) hashRelRep .get(key); Set
     * keySetOtherSet = loc.keySet(); Iterator itt = keySetOtherSet.iterator();
     * while(itt.hasNext()) { String otherKey = (String) itt.next();
     * ArrayList<REP_MISMATCH_PARAMCO> list = (ArrayList<REP_MISMATCH_PARAMCO>)
     * loc.get(otherKey); for(int i = 0; i < list.size(); i++) {
     * sb.append("key of the hash: " + key + " Internal key: " + otherKey +
     * "   REP REFERENCE: " +
     * (list.get(i)).getRepMismatchParamVO().getREP_REFERENCE() +
     * "    CRITERIA CODE   " +
     * (list.get(i)).getRepMismatchParamVO().getCRITERIA_CODE() + "\n"); } }
     * sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); }
     * 
     * sb.append(
     * "**********************************RELATED COLUMNS**********************************\n\n\n"
     * ); HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>> hashRelCols =
     * (HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repSessionCO
     * .getRepMisParameterScreenHash
     * ().get(RepConstantsCommon.MISMATCH_REL_COLS)); Set relSet =
     * hashRelCols.keySet(); Iterator id = relSet.iterator();
     * while(id.hasNext()) { String key = (String) id.next();
     * ArrayList<REP_MISMATCH_COLUMNCO> ll = (ArrayList<REP_MISMATCH_COLUMNCO>)
     * hashRelCols.get(key); if(ll != null) { if(ll.size() == 0) {
     * sb.append("the key : " + key + "      RELATED COLUMN      \n"); } else {
     * for(int i = 0; i < ll.size(); i++) { sb.append("the key: " + key +
     * " RELATED COLUMN: " +
     * (ll.get(i)).getRepMismatchColumnVO().getRELATED_COLUMN() +
     * "  MISMATCH ID: " +
     * (ll.get(i)).getRepMismatchColumnVO().getREP_MISMATCH_ID() + "\n"); } } }
     * }sb.append(
     * "**********************************RELATED CRITERIA**********************************\n\n\n"
     * ); HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>
     * hashRelatedCriteria = (HashMap<String,
     * ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) (repSessionCO
     * .getRepMisParameterScreenHash
     * ().get(RepConstantsCommon.MISMATCH_REL_CRITERIA)); Set relatedCrtSet =
     * hashRelatedCriteria.keySet(); Iterator idrelatedCrtSet =
     * relatedCrtSet.iterator(); while(idrelatedCrtSet.hasNext()) { String key =
     * (String) idrelatedCrtSet.next(); ArrayList<REP_MISMATCH_INTRA_CRITERIACO>
     * ll = (ArrayList<REP_MISMATCH_INTRA_CRITERIACO>) hashRelatedCriteria
     * .get(key); if(ll != null) { if(ll.size() == 0) { sb.append("the key : " +
     * key + "      RELATED CRITERIA      \n"); } else { for(int i = 0; i <
     * ll.size(); i++) { sb.append("the key: " + key + " RELATED CRITERIA: " +
     * (ll.get(i)).getRepMismatchIntraCriteriaVO().getRELATED_CRITERIA() +
     * "  MISMATCH ID: " +
     * (ll.get(i)).getRepMismatchIntraCriteriaVO().getREP_MISMATCH_ID() + "\n");
     * } } } } sb.append(
     * "#########################################################################################################################\n\n\n\n"
     * );
     * 
     * File f = new
     * File("C:\\Users\\bassambechara\\Desktop\\scenarios_mismatch.txt");
     * FileOutputStream ff = new FileOutputStream(f, true);
     * ff.write(String.valueOf(sb.toString()).getBytes()); ff.close(); }
     * catch(Exception e) { e.printStackTrace(); } }
     */

    public String deleteRepMismatchRow() throws JSONException
    {
	return deleteRepMisDetails(repMismatchParamVO);
    }

    public String deleteRepMisDetails(REP_MISMATCH_PARAMVO repMismatchParamVO) throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
	    HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> listHash = new HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>();
	    // case where the record already exists in the db and deleting it in
	    // the main grid
	    if(repMismatchParamVO.getREP_MISMATCH_ID().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
	    {
		// if newly added record in the related reports screen or in the
		// main grid (not
		// previously in the db)
		/*
		 * if from related reports,find the record in the session and
		 * remove it from the hashmap of related reports of the main
		 * grid record.don't put it in listDelete because not in the db
		 * to be deleted
		 */
		// >> if related report
		if("2".equals(update))
		{
		    listHash = (((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).get(repMismatchParamVO.getCRITERIA_CODE()));
		    ArrayList<REP_MISMATCH_PARAMCO> arr = listHash.get(RepConstantsCommon.MISMATCH_ALL_REC);
		    ArrayList<REP_MISMATCH_PARAMCO> newList = new ArrayList<REP_MISMATCH_PARAMCO>();
		    Iterator<REP_MISMATCH_PARAMCO> it = arr.iterator();
		    REP_MISMATCH_PARAMCO co;
		    while(it.hasNext())
		    {
			co = it.next();
			if(!(co.getRepMismatchParamVO().getREP_REFERENCE()
				.equals(repMismatchParamVO.getREP_REFERENCE()) && co.getRepMismatchParamVO()
				.getCRITERIA_CODE().equals(repMismatchParamVO.getCRITERIA_CODE())))
			{
			    newList.add(co);
			}
		    }
		    listHash.put(RepConstantsCommon.MISMATCH_ALL_REC, newList);
		    // remove the hashmap of related columns from the session
		    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_COLS))).remove((repMismatchParamVO.getREP_REFERENCE()
			    + "~" + repMismatchParamVO.getCRITERIA_CODE()));
		}
		// if in the main grid and deleting a newly added record
		else
		{
		    // >> if inter
		    if(BigDecimal.valueOf(1).equals(mismatchType))
		    {
			// remove the hashmap of related reports from the
			// session
			HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> relRepMap = ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
				.get(RepConstantsCommon.MISMATCH_REL_REPORTS))).get((repMismatchParamVO
				.getCRITERIA_CODE()));

			// case when the user added a record in the main grid
			// not
			// having related reports.relRepMap will be null
			if(relRepMap != null)
			{
			    // empty related cols of related reports
			    ArrayList<REP_MISMATCH_PARAMCO> lstAll = relRepMap.get(RepConstantsCommon.MISMATCH_ALL_REC);
			    if(lstAll != null && !lstAll.isEmpty())
			    {
				REP_MISMATCH_PARAMCO repCO;
				for(int i = 0; i < lstAll.size(); i++)
				{
				    repCO = lstAll.get(i);
				    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
					    .get(RepConstantsCommon.MISMATCH_REL_COLS))).remove(repCO
					    .getRepMismatchParamVO().getREP_REFERENCE()
					    + "~" + repCO.getRepMismatchParamVO().getCRITERIA_CODE());
				}
			    }

			    // empty related reports
			    ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
				    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).remove((repMismatchParamVO
				    .getCRITERIA_CODE()));
			}
		    }
		    // >> if intra
		    else if(BigDecimal.valueOf(0).equals(mismatchType))
		    {
			// if intra reports remove the related criteria hash
			// from the session
			((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) (repMisParameterScreenHash
				.get(RepConstantsCommon.MISMATCH_REL_CRITERIA))).remove(repMismatchParamVO
				.getREP_REFERENCE()
				+ "~" + repMismatchParamVO.getCRITERIA_CODE());
		    }
		    // empty related cols of the main grid report for both inter
		    // and intra
		    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_COLS))).remove(repMismatchParamVO.getREP_REFERENCE()
			    + "~" + repMismatchParamVO.getCRITERIA_CODE());
		}

	    }
	    else
	    {
		// only for main grid get the list of related reports and set
		// all records to be deleted
		// >> if mainGrid + inter
		if("1".equals(update) && BigDecimal.valueOf(1).equals(mismatchType))
		{
		    reportsMismatchSC.setCompCode(sessionCO.getCompanyCode());
		    reportsMismatchSC.setRepReference(repMismatchParamVO.getREP_REFERENCE());
		    reportsMismatchSC.setCriteriaCode(repMismatchParamVO.getCRITERIA_CODE());
		    ArrayList<REP_MISMATCH_PARAMCO> list = reportsMismatchBO
			    .retRelatedReportsMismatchList(reportsMismatchSC);
		    // empty the related reports hash
		    listHash.put(RepConstantsCommon.MISMATCH_ALL_REC, new ArrayList<REP_MISMATCH_PARAMCO>());
		    listHash.put(RepConstantsCommon.MISMATCH_LST_DEL, list);
		    ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).put(repMismatchParamVO.getCRITERIA_CODE(),
			    listHash);

		    // empty related columns of related reports

		    if(list != null && !list.isEmpty())
		    {
			REP_MISMATCH_PARAMCO repCO;
			for(int i = 0; i < list.size(); i++)
			{
			    repCO = list.get(i);
			    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
				    .get(RepConstantsCommon.MISMATCH_REL_COLS))).put(repCO.getRepMismatchParamVO()
				    .getREP_REFERENCE()
				    + "~" + repCO.getRepMismatchParamVO().getCRITERIA_CODE(),
				    new ArrayList<REP_MISMATCH_COLUMNCO>());
			}
		    }

		}
		// if deleting a record in the related reports,and already in
		// the db, modify the main grid record listDelete
		// >> if related reports
		else if("2".equals(update))
		{
		    listHash = (((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).get(repMismatchParamVO.getCRITERIA_CODE()));
		    ArrayList<REP_MISMATCH_PARAMCO> arr = listHash.get(RepConstantsCommon.MISMATCH_ALL_REC);
		    ArrayList<REP_MISMATCH_PARAMCO> newlistDel = listHash.get(RepConstantsCommon.MISMATCH_LST_DEL);
		    ArrayList<REP_MISMATCH_PARAMCO> newList = new ArrayList<REP_MISMATCH_PARAMCO>();
		    Iterator<REP_MISMATCH_PARAMCO> it = arr.iterator();
		    while(it.hasNext())
		    {
			REP_MISMATCH_PARAMCO co = it.next();
			if((co.getRepMismatchParamVO().getREP_REFERENCE()
				.equals(repMismatchParamVO.getREP_REFERENCE()) && co.getRepMismatchParamVO()
				.getCRITERIA_CODE().equals(repMismatchParamVO.getCRITERIA_CODE())))
			{
			    newlistDel.add(co);			    
			}
			else
			{
			    newList.add(co);
			}
		    }
		    listHash.put(RepConstantsCommon.MISMATCH_ALL_REC, newList);
		    // listHash.put(RepConstantsCommon.MISMATCH_LST_DEL,
		    // newlistDel);
		}
		// for intra reports, empty the hash of related criteria
		// >> if intra
		else if(BigDecimal.valueOf(0).equals(mismatchType))
		{
		    ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) (repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA))).put(repMismatchParamVO.getREP_REFERENCE()
			    + "~" + repMismatchParamVO.getCRITERIA_CODE(),
			    new ArrayList<REP_MISMATCH_INTRA_CRITERIACO>());
		}

		// empty the related columns hash for both if main grid or a row
		// is selected in the related reports screen
		((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
			.get(RepConstantsCommon.MISMATCH_REL_COLS))).put(repMismatchParamVO.getREP_REFERENCE() + "~"
			+ repMismatchParamVO.getCRITERIA_CODE(), new ArrayList<REP_MISMATCH_COLUMNCO>());
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String deleteAllRepMismatch() throws JSONException
    {
	if(updates1 != null && !updates1.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates1, REP_MISMATCH_PARAMCO.class, true);
	    // all records in the grid APPEARING in the screen
	    ArrayList<REP_MISMATCH_PARAMCO> listAllRecords = gu.getLstAllRec();
	    Iterator<REP_MISMATCH_PARAMCO> it = listAllRecords.iterator();
	    REP_MISMATCH_PARAMCO co;
	    while(it.hasNext())
	    {
		co = it.next();
		deleteRepMisDetails(co.getRepMismatchParamVO());
	    }
	}
	return SUCCESS;
    }

    public String loadReportsMismatchRelCriteriaList() throws JSONException
    {
	try
	{
	    ArrayList<REP_MISMATCH_INTRA_CRITERIACO> list;
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
	    HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>> relCrtHm = ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA));
	    if(relCrtHm == null
		    || relCrtHm
			    .get(repMismatchParamVO.getREP_REFERENCE() + "~" + repMismatchParamVO.getCRITERIA_CODE()) == null)
	    {
		if(relCrtHm == null)
		{
		    repMisParameterScreenHash.put(RepConstantsCommon.MISMATCH_REL_CRITERIA,
			    new HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>());
		    relCrtHm = ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA));
		}
		if(repMismatchParamVO.getREP_MISMATCH_ID() == null
			 || ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(repMismatchParamVO.getREP_MISMATCH_ID()))
		{
		    list = new ArrayList<REP_MISMATCH_INTRA_CRITERIACO>();
		}
		else

		{
		    reportsMismatchSC.setColSearchQuery(null);
		    reportsMismatchSC.setREP_MISMATCH_ID(repMismatchParamVO.getREP_MISMATCH_ID());
		    list = reportsMismatchBO.retReportsMismatchRelCriteriaList(reportsMismatchSC);
		}
		relCrtHm.put(repMismatchParamVO.getREP_REFERENCE() + "~" + repMismatchParamVO.getCRITERIA_CODE(), list);
	    }
	    else
	    {
		list = relCrtHm
			.get(repMismatchParamVO.getREP_REFERENCE() + "~" + repMismatchParamVO.getCRITERIA_CODE());
	    }
	    setRecords(list.size());
	    setGridModel(list);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String saveAllReportsMismatch() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    ArrayList lstAdd;
	    ArrayList lstMod;
	    ArrayList lstDel;
	    GridUpdates gu = new GridUpdates();
	    HashMap<String, Object> repMisParameterScreenHash = repSessionCO.getRepMisParameterScreenHash();
	    if(updates != null && !updates.equals(""))
	    {
		gu = getGridUpdates(updates, REP_MISMATCH_PARAMCO.class);
	    }
	    lstAdd = gu.getLstAdd();
	    lstMod = gu.getLstModify();
	    lstDel = gu.getLstDelete();
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.MISMATCH_PARAM_KEY_REF);
	    if(mismatchType.intValue() == 1)
	    {
		reportsMismatchBO.saveAllInterReportsMismatch(lstAdd, lstMod, lstDel, repMisParameterScreenHash,
			sessionCO.getCompanyCode(), refCO);
	    }
	    else
	    {
		reportsMismatchBO.saveAllIntraReportsMismatch(lstAdd, lstMod, lstDel, repMisParameterScreenHash,
			sessionCO.getCompanyCode(), refCO);
	    }
	    /*
	     * Map<String, Object> row = new LinkedHashMap<String, Object>();
	     * row.put("trs_no", "222"); row.put("branch_code", "1");
	     * row.put("test", "test_value"); row.put("ddd", "");
	     * row.put("kkkkk", null); row.put("another",
	     * BigDecimal.valueOf(1000.64349)); row.put("trs_date",
	     * DateUtil.parseDate("15/07/2010", "dd/MM/yyyy"));
	     * row.put("the_null", null); row.put("the_1", null);
	     * row.put("the_2", "A"); row.put("the_3", null); row.put("the_9",
	     * BigDecimal.valueOf(656)); row.put("the_5", null);
	     * row.put("the_11", BigDecimal.valueOf(1235));
	     * 
	     * List<Map<String, Object>> rowData = new ArrayList<Map<String,
	     * Object>>(); rowData.add(row);
	     * 
	     * Object result =commonLibBO.executeExpression(
	     * "if(String(the_2) == 'A'  ,'Active','Inactive')+ 'AAAAAA'", 0,
	     * rowData);
	     */
	    // Object result =
	    // commonLibBO.executeExpression("abs(trs_no)asc(the_2)", 0,
	    // rowData);
	    // System.out.println(result);
	    // end for testing
	}
	catch(Exception e)
	{
	    cleanSnSession();
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String saveRelatedCriteriaMismatch() throws JSONException
    {

	try
	{
	    if(updates != null && !updates.equals(""))
	    {// no need to get lstadd,lstupdate,lstdelete since we will always
		// delete and insert at the end
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		GridUpdates gu = getGridUpdates(updates, REP_MISMATCH_INTRA_CRITERIACO.class, true);
		ArrayList<REP_MISMATCH_INTRA_CRITERIACO> distinct = gu.getLstAllRec();
		ArrayList<REP_MISMATCH_INTRA_CRITERIACO> finalList = new ArrayList<REP_MISMATCH_INTRA_CRITERIACO>();
		Iterator<REP_MISMATCH_INTRA_CRITERIACO> it = distinct.iterator();
		REP_MISMATCH_INTRA_CRITERIACO co;
		Iterator<REP_MISMATCH_INTRA_CRITERIACO> itt;
		boolean alreadyAdded;
		REP_MISMATCH_INTRA_CRITERIACO coo;
		while(it.hasNext())
		{
		    co = it.next();
		    itt = finalList.iterator();
		    alreadyAdded = false;
		    while(itt.hasNext())
		    {
			coo = itt.next();
			if(coo.getRepMismatchIntraCriteriaVO().getRELATED_CRITERIA().equals(
				co.getRepMismatchIntraCriteriaVO().getRELATED_CRITERIA()))
			{
			    alreadyAdded = true;
			}
		    }
		    if(!alreadyAdded && !"".equals(co.getRepMismatchIntraCriteriaVO().getRELATED_CRITERIA()))
		    {
			finalList.add(co);
		    }
		}

		((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) (repSessionCO
			.getRepMisParameterScreenHash().get(RepConstantsCommon.MISMATCH_REL_CRITERIA))).put(
			repMismatchParamVO.getREP_REFERENCE() + "~" + repMismatchParamVO.getCRITERIA_CODE(), finalList);

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String applyMismatchParamAudit() throws JSONException
    {
	try
	{
	    applyRetrieveAudit(AuditConstant.MISMATCH_PARAM_KEY_REF, repMismatchParamVO, repMismatchParamVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String openCriteriaColumn() throws JSONException
    {
	setCrtColOrRelCol("1"); // called from criteria cols
	fillColsTechNameMap();
	return "criteriaColumn";

    }

    public void fillColsTechNameMap()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    String progRef = repMismatchParamVO.getREP_REFERENCE();
	    /*
	     * fill for each progRef a hashMap(technicalColName, lblField) in
	     * order to show the lblField in the grids of crt col and rel. cols
	     */

	    HashMap<String, HashMap<String, Object>> techNameMap = (HashMap<String, HashMap<String,Object>>) repSessionCO
		    .getRepMisParameterScreenHash().get(RepConstantsCommon.MISMATCH_COL_TECH_NAME);
	    if(techNameMap == null)
	    {
		repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_COL_TECH_NAME,
			new HashMap<String, HashMap<Object, String>>());
		techNameMap = (HashMap<String, HashMap<String,Object>>) repSessionCO.getRepMisParameterScreenHash()
			.get(RepConstantsCommon.MISMATCH_COL_TECH_NAME);

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
    public String openRelatedCriteria() throws JSONException
    {
	return "relatedCrt";

    }

    public String openRelatedColumns() throws JSONException
    {
	setCrtColOrRelCol("2"); // called from related cols
	fillColsTechNameMap();
	return "criteriaColumn";
    }

    public Object getModel()
    {
	return reportsMismatchSC;
    }

    public String reloadDivMis() throws JSONException
    {

	return "reloadDivMis";
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

    public String getHideRelCrt()
    {
	BigDecimal misType = getMismatchType();
	if(misType != null && misType.intValue() == 1 || "2".equals(update))
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
}
