package com.path.actions.reporting.ftr.snapshots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_DRILLDOWN_COLUMNCO;

public class SnapshotDrilldownColumnAction extends ReportingLookupBaseAction implements ServletResponseAware
{
    private SnapshotParameterBO snapshotParameterBO;
    private final SnapshotParameterSC snapshotParameterSC = new SnapshotParameterSC();
    private String progRef;
    private String frequency;
    protected HttpServletResponse response;
    private String toDelete;
    private String repId;
    private String updateDrilColumns;
    private REP_SNAPSHOT_PARAMVO repSnapshotParamVO;

    public String getRepId()
    {
	return repId;
    }

    public void setRepId(String repId)
    {
	this.repId = repId;
    }

    public REP_SNAPSHOT_PARAMVO getRepSnapshotParamVO()
    {
	return repSnapshotParamVO;
    }

    public void setRepSnapshotParamVO(REP_SNAPSHOT_PARAMVO repSnapshotParamVO)
    {
	this.repSnapshotParamVO = repSnapshotParamVO;
    }

    public String getUpdateDrilColumns()
    {
	return updateDrilColumns;
    }

    public void setUpdateDrilColumns(String updateDrilColumns)
    {
	this.updateDrilColumns = updateDrilColumns;
    }

    public String getFrequency()
    {
	return frequency;
    }

    public void setFrequency(String frequency)
    {
	this.frequency = frequency;
    }

    public String getProgRef()
    {
	return progRef;
    }

    public String getToDelete()
    {
	return toDelete;
    }

    public void setToDelete(String toDelete)
    {
	this.toDelete = toDelete;
    }

    public void setProgRef(String progRef)
    {
	this.progRef = progRef;
    }

    public String loadSnapshotDrilColGrid() throws JSONException
    {
	try
	{
	    // on load check if hash map with key report reference + frequency
	    // exists.if not get data from database
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    copyproperties(snapshotParameterSC);
	    snapshotParameterSC.setREP_REFERENCE(repSnapshotParamVO.getREP_REFERENCE());
	    snapshotParameterSC.setSNAPSHOT_FREQUENCY(repSnapshotParamVO.getSNAPSHOT_FREQUENCY());

	    HashMap<String, Object> snParameterScreenHash = repSessionCO.getSnParameterScreenHash();
	    ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> list;
	    
	    //retrive the techNameMap(progRef , Hm(techName, lblAlias))
	    HashMap<String, HashMap<String, Object>> techNameMap =   (HashMap<String, HashMap<String, Object>>) snParameterScreenHash.get(RepConstantsCommon.MISMATCH_COL_TECH_NAME);
	    HashMap<String, Object> hm=techNameMap.get(repSnapshotParamVO.getREP_REFERENCE());
	    
	    
	    // modified columns table data not loaded

	    if(snParameterScreenHash.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN) == null
		    || ((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (snParameterScreenHash
			    .get(RepConstantsCommon.SNAPSHOT_DRILLDOWN))).get(repSnapshotParamVO.getREP_REFERENCE()
			    + "~" + repSnapshotParamVO.getSNAPSHOT_FREQUENCY()) == null)
	    {
		snapshotParameterSC.setColSearchQuery(null);
		list = snapshotParameterBO.retDrilldownColumnList(snapshotParameterSC);
		if(snParameterScreenHash.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN) == null)
		{
		    snParameterScreenHash.put(RepConstantsCommon.SNAPSHOT_DRILLDOWN,
			    new HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>());
		((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (snParameterScreenHash
			.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN))).put(repSnapshotParamVO.getREP_REFERENCE() + "~"
			+ repSnapshotParamVO.getSNAPSHOT_FREQUENCY(), list);
		}
	    }
	    else
	    {
		list = ((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (snParameterScreenHash
			.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN))).get(repSnapshotParamVO.getREP_REFERENCE() + "~"
			+ repSnapshotParamVO.getSNAPSHOT_FREQUENCY());
	    }
	    
	    
	  //fill column description from technical name since the description is not saved in the DB
	    REP_SNAPSHOT_DRILLDOWN_COLUMNCO drillColCO;
		for(int i=0;i<list.size();i++)
		{
		    drillColCO=list.get(i);
		    drillColCO.getRepSnapshotDrilColVO().setCOLUMN_DRILLDOWN(fillColDescription(hm, drillColCO.getRepSnapshotDrilColVO().getCOLUMN_DRILLDOWN()));
		}
		
	    setRecords(list.size());

	    setGridModel(list);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "successJson";
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
    
    public String fillReportGrid() throws BaseException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, REP_SNAPSHOT_DRILLDOWN_COLUMNCO> liveSearchHash = ((HashMap<String, REP_SNAPSHOT_DRILLDOWN_COLUMNCO>) (repSessionCO
		    .getSnParameterScreenHash().get(RepConstantsCommon.SNAPSHOT_HASH_LIVE_DRIL_COL)));
	    ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> ls = new ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>();
	    if(liveSearchHash != null)
	    {
		Iterator<Map.Entry<String, REP_SNAPSHOT_DRILLDOWN_COLUMNCO>> itFmap = liveSearchHash.entrySet()
			.iterator();
		Entry<String, REP_SNAPSHOT_DRILLDOWN_COLUMNCO> entry;
		while(itFmap.hasNext())
		{
		    entry = itFmap.next();
		    ls.add(entry.getValue());

		}

	    }
	    setGridModel(ls);
	    return "successJson";
	}
	catch(Exception e)
	{
	    log.error(e, "");
	    return null;
	}
    }

    public String reportLookup()
    {
	try
	{
	    fillLookup("REPORT_ID",
		    "/path/snapshotParameter/SnapshotDrilldownColumnAction_fillReportGrid.action", "Reports");
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "fillReportsLkp.exceptionMsg._key", "fillReportsLkp.exceptionMsg._key");
	}
	return "successJson";
    }

    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "repSnapshotDrilColVO.REP_ID", "repSnapshotDrilColVO.COLUMN_DRILLDOWN",
		    "repSnapshotDrilColVO.COLUMN_TYPE" ,"TECH_COL_NAME"};
	    String[] colType = { "text", "text", "text","text" };
	    String[] titles = { getText("reportId"), getText("snapshots.field"), getText("col.colType"),"TECH_COL_NAME" };
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    // grid.setId(gridId);
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setShrinkToFit("true");
	    grid.setFilter(false);
	    grid.setUrl(gridUrl);

	    List<LookupGridColumn> lstGridColumn = returnStandarColSpecs(name, colType, titles);
	    lstGridColumn.get(0).setHidden(true);
	    lstGridColumn.get(2).setHidden(true);
	     lstGridColumn.get(3).setHidden(true);
	    lstGridColumn.get(1).setSearch(false);
	    lookup(grid, lstGridColumn, null, snapshotParameterSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "fillReportsLkp.exceptionMsg._key", "fillReportsLkp.exceptionMsg._key");
	}
	return "successJson";
    }

    /**
     * Method that prints the JRXML
     * 
     * @throws Exception
     */
    // public String previewSnDrilCol() throws Exception
    // {
    // try
    // {
    // // get the reportCO in order to retrieve the jrxml
    // ReportingSessionCO repSessionCO =
    // returnReportingSessionObject(get_pageRef());
    // IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
    // repSC.setPROG_REF(progRef);
    // IRP_AD_HOC_REPORTCO repIdCO = designerBO.retRepIdByProgRef(repSC);
    // if(repIdCO == null)
    // {
    // response.setContentType("text/html");
    // response.getOutputStream().write("".getBytes(CommonUtil.ENCODING));
    // response.getOutputStream().flush();
    // return null;
    // }
    // IRP_AD_HOC_REPORTCO repCO = (IRP_AD_HOC_REPORTCO)
    // designerBO.returnReportById(repIdCO.getREPORT_ID());
    // // get the hashmap of the livesearch
    // HashMap<String, Object> hashLiveSearch = new HashMap<String, Object>();
    // Object[] obj;
    // if("".equals(repId))
    // obj = reportBO.generateReportDesign(repCO, hashLiveSearch,
    // RepConstantsCommon.SNAPSHOT_DRILLDOWN,
    // null,null);
    // else
    // obj = reportBO.generateReportDesign(repCO, hashLiveSearch,
    // RepConstantsCommon.SNAPSHOT_DRILLDOWN,
    // new BigDecimal(repId),null);
    // if(obj != null)
    // {
    // hashLiveSearch = (HashMap<String, Object>) obj[1];
    // byte[] bb = (byte[]) obj[0];
    // repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_HASH_LIVE_DRIL_COL,
    // hashLiveSearch);
    // response.setContentType("text/html");
    // response.getOutputStream().write(bb);
    // response.getOutputStream().flush();
    // }
    //
    // return null;
    // }
    // catch(Exception e)
    // {
    // handleException(e, "snapshots.errorPreview.exceptionMsg._key",
    // "snapshots.errorPreview.exceptionMsg._key");
    // log.error(e, e.getMessage() +
    // "Exception in SnapshotModifiedColumnAction");
    // return null;
    // }
    // }

    public String deleteDrilCol() throws JSONException
    {
	return "successJson";
    }

    public String saveDrilldownColumns() throws Exception
    {
	try
	{
	    if(updateDrilColumns != null && !updateDrilColumns.equals(""))
	    {// no need to get lstadd,lstupdate,lstdelete since we will always
		// delete and insert at the end
		ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
		GridUpdates gu = getGridUpdates(updateDrilColumns, REP_SNAPSHOT_DRILLDOWN_COLUMNCO.class, true);
		ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> distinct = gu.getLstAllRec();
		ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> finalList = new ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>();
		Iterator<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> it = distinct.iterator();
		REP_SNAPSHOT_DRILLDOWN_COLUMNCO co;
		Iterator<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> itt;
		boolean alreadyAdded;
		REP_SNAPSHOT_DRILLDOWN_COLUMNCO coo;
		while(it.hasNext())
		{
		    co = it.next();
		    itt = finalList.iterator();
		    alreadyAdded = false;
		    while(itt.hasNext())
		    {
			coo = itt.next();
			if(coo.getRepSnapshotDrilColVO().getCOLUMN_DRILLDOWN().equals(
				co.getRepSnapshotDrilColVO().getCOLUMN_DRILLDOWN()))
			{
			    alreadyAdded = true;
			}
		    }
		    if(!alreadyAdded && !"".equals(co.getRepSnapshotDrilColVO().getCOLUMN_DRILLDOWN()))
		    {
			if(co.getRepSnapshotDrilColVO().getREP_ID().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
			{
			    co.getRepSnapshotDrilColVO().setREP_ID(repSnapshotParamVO.getREP_ID());
			}
			finalList.add(co);
		    }
		}
		((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (repSessionCO.getSnParameterScreenHash()
			.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN))).put(repSnapshotParamVO.getREP_REFERENCE() + "~"
			+ repSnapshotParamVO.getSNAPSHOT_FREQUENCY(), finalList);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {
	this.snapshotParameterBO = snapshotParameterBO;
    }

    public Object getModel()
    {
	return snapshotParameterSC;
    }

    public void setServletResponse(HttpServletResponse response)
    {
	this.response = response;
    }

}
