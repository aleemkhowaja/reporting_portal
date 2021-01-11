package com.path.actions.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.designer.HyperlinksBO;
import com.path.dbmaps.vo.IRP_REP_HYPERLINKSVO;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.DesignerGridSC;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;

public class HyperlinksAction extends ReportingGridBaseAction
{
    private HyperlinksBO hyperlinksBO;
    private final DesignerGridSC designerGridSC = new DesignerGridSC();
    private String updates;
    private String colName;
    private String repId;
    private String linkRepId;
    private String feIndex;

    public String getFeIndex()
    {
	return feIndex;
    }

    public void setFeIndex(String feIndex)
    {
	this.feIndex = feIndex;
    }

    public String getLinkRepId()
    {
	return linkRepId;
    }

    public void setLinkRepId(String linkRepId)
    {
	this.linkRepId = linkRepId;
    }

    public String getColName()
    {
	return colName;
    }

    public void setColName(String colName)
    {
	this.colName = colName;
    }

    public String getRepId()
    {
	return repId;
    }

    public void setRepId(String repId)
    {
	this.repId = repId;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public void setHyperlinksBO(HyperlinksBO hyperlinksBO)
    {
	this.hyperlinksBO = hyperlinksBO;
    }

    public Object getModel()
    {
	return designerGridSC;
    }

    public String loadHyperlinks() throws Exception
    {
	set_showSmartInfoBtn("false");
	// empty session
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setHyperlinkParamsMap(new HashMap<String, List<IRP_REP_HYPERLINKSCO>>());
	repSessionCO.setOldHyperlinkParamsMap(new HashMap<String, List<IRP_REP_HYPERLINKSCO>>());
	return "hyperlink";
    }

    public String loadHyperlinksGrid() throws Exception // load the grid
    {
	try
	{
	    copyproperties(designerGridSC);
	    List<IRP_REP_HYPERLINKSCO> lst = hyperlinksBO.retHyperlinksList(designerGridSC);
	    int cnt = hyperlinksBO.retHyperlinksCnt(designerGridSC);
	    setRecords(cnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e, "============error loading the grid ===========");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method called on hyperlink deletion
     * @return
     */
    public String deleteHyperlink()
    {
	try
	{

	    if(StringUtil.isNotEmpty(getFeIndex()) && StringUtil.isNotEmpty(getRepId()))
	    {
		IRP_REP_HYPERLINKSCO hypCO = new IRP_REP_HYPERLINKSCO();
		hypCO.getHyperlinkVO().setFIELD_INDEX(new BigDecimal(getFeIndex()));
		hypCO.getHyperlinkVO().setREPORT_ID(new BigDecimal(getRepId()));

		// apply audit
		AuditRefCO refCO = initAuditRefCO();
		refCO.setOperationType(AuditConstant.DELETE);
		refCO.setKeyRef(AuditConstant.HYPERLINKS_KEY_REF);
		hypCO.setAuditRefCO(refCO);
		hyperlinksBO.deleteHyperlink(hypCO);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String openHyperlinkParamsDialog() throws Exception
    {

	// add audit if it is not a new record
	if(getUpdates() != null && getUpdates().equals("U"))
	{
	    IRP_REP_HYPERLINKSVO hypVO = new IRP_REP_HYPERLINKSVO();
	    hypVO.setREPORT_ID(new BigDecimal(getRepId()));
	    hypVO.setFIELD_INDEX(new BigDecimal(getFeIndex()));
	    applyRetrieveAudit(AuditConstant.HYPERLINKS_KEY_REF, hypVO, hypVO);
	}

	return "hypParams";
    }

    public String loadHypParamsList() throws Exception
    {
	try
	{
	    copyproperties(designerGridSC);
	    List<IRP_REP_HYPERLINKSCO> lst;
	    int cnt;
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, List<IRP_REP_HYPERLINKSCO>> hyperlinkParamsMap = repSessionCO.getHyperlinkParamsMap();
	    List<IRP_REP_HYPERLINKSCO> list = hyperlinkParamsMap.get(getRepId() + "_" + getFeIndex());
	    if(hyperlinkParamsMap.isEmpty() || list == null)
	    {
		designerGridSC.setRep_Id(new BigDecimal(getRepId()));
		designerGridSC.setLink_rep_Id(new BigDecimal(getLinkRepId()));
		designerGridSC.setField_index(new BigDecimal(getFeIndex()));
		designerGridSC.setCol_name(getColName());

		lst = hyperlinksBO.retHyperlinkParamsList(designerGridSC);
		cnt = hyperlinksBO.retHyperlinkParamsCnt(designerGridSC);
		setRecords(cnt);
		setGridModel(lst);

		// Store the list in order to use it in audit
		HashMap<String, List<IRP_REP_HYPERLINKSCO>> oldHyperlinkParamsMap = repSessionCO
			.getOldHyperlinkParamsMap();
		oldHyperlinkParamsMap.put(getRepId() + "_" + getFeIndex(), lst);
	    }
	    else
	    {

		int nbRec = designerGridSC.getNbRec();
		int recToSkip = designerGridSC.getRecToskip();
		int maxRec = nbRec + recToSkip;
		if(list.size() < maxRec)
		{
		    maxRec = list.size();
		}
		lst = new ArrayList<IRP_REP_HYPERLINKSCO>();
		for(int i = recToSkip; i < maxRec; i++)
		{
		    lst.add(list.get(i));
		}
		setRecords(list.size());
		setGridModel(lst);
	    }

	}
	catch(Exception e)
	{
	    //log.error(e, "============error loading the grid ===========");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String saveHypParams() throws Exception
    {
	if(updates != null && !updates.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates, IRP_REP_HYPERLINKSCO.class, true);
	    if(!gu.getLstAllRec().isEmpty())
	    {
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		    HashMap<String, List<IRP_REP_HYPERLINKSCO>> hypParamsMap = repSessionCO.getHyperlinkParamsMap();
	
		    IRP_REP_HYPERLINKSCO hypCO = (IRP_REP_HYPERLINKSCO) gu.getLstAllRec().get(0);
		    hypParamsMap.put(hypCO.getHyperlinkVO().getREPORT_ID() + "_" + hypCO.getHyperlinkVO().getFIELD_INDEX(), gu
			    .getLstAllRec());
	    }
	}
	return SUCCESS;
    }

    public String saveHyperlinks() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, List<IRP_REP_HYPERLINKSCO>> hypParamsMap = repSessionCO.getHyperlinkParamsMap();
	    if((updates != null && !updates.equals("")) || !(hypParamsMap.isEmpty()))
	    {
		ArrayList<IRP_REP_HYPERLINKSCO> lstAdd;
		ArrayList<IRP_REP_HYPERLINKSCO> lstMod;
		if(updates == null || updates.equals(""))
		{
		    lstAdd = new ArrayList<IRP_REP_HYPERLINKSCO>();
		    lstMod = new ArrayList<IRP_REP_HYPERLINKSCO>();
		}
		else
		{
		    GridUpdates gu = getGridUpdates(updates, IRP_REP_HYPERLINKSCO.class);
		    lstAdd = gu.getLstAdd();
		    lstMod = gu.getLstModify();
		}
		AuditRefCO refCO = initAuditRefCO();
		refCO.setKeyRef(AuditConstant.HYPERLINKS_KEY_REF);

		hyperlinksBO.saveHyperlinks(hypParamsMap, lstAdd, lstMod, refCO, repSessionCO
			.getOldHyperlinkParamsMap());

	    }
	}
	catch(Exception e)
	{
	    if(e.getMessage() == null || e.getMessage().indexOf("2099") == -1)
	    {
		handleException(e, "", "");
	    }
	    else
	    {
		handleException(e, null, null);
	    }
	}

	return SUCCESS;
    }

    public String clearHypParamsMap() throws Exception
    {
	// empty session
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setHyperlinkParamsMap(new HashMap<String, List<IRP_REP_HYPERLINKSCO>>());
	repSessionCO.setOldHyperlinkParamsMap(new HashMap<String, List<IRP_REP_HYPERLINKSCO>>());
	return SUCCESS;
    }

    public String retTrxNb() throws Exception
    {
	IRP_REP_HYPERLINKSVO hypVO = new IRP_REP_HYPERLINKSVO();
	if (!StringUtil.nullToEmpty(getRepId()).isEmpty())
	{
	hypVO.setREPORT_ID(new BigDecimal(getRepId()));
	}
	if (!StringUtil.nullToEmpty(getFeIndex()).isEmpty())
	{
	hypVO.setFIELD_INDEX(new BigDecimal(getFeIndex()));
	}

	AuditRefCO refCO = initAuditRefCO();
	refCO.setOperationType(AuditConstant.RETRIEVE);
	refCO.setKeyRef(AuditConstant.HYPERLINKS_KEY_REF);
	setAuditTrxNbr(auditBO.checkAuditKey(hypVO, refCO));
	return SUCCESS;
    }
}
