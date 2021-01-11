package com.path.actions.reporting.ftr.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.controlRecord.ControlRecordBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterMaintAction.java used to
 */
public class SnapshotParameterMaintAction extends ReportingLookupBaseAction
{
    private SnapshotParameterBO snapshotParameterBO;
    private BTR_CONTROLCO btrControlCO;
    private ControlRecordBO controlRecordBO;
    private String updates;
    private String htmlPageRef;
    private String repSnpId;
    private Date aod;
    private BigDecimal snpId;
    private String updates1;

    public String getUpdates1()
    {
        return updates1;
    }

    public void setUpdates1(String updates1)
    {
        this.updates1 = updates1;
    }

    public BigDecimal getSnpId()
    {
        return snpId;
    }

    public void setSnpId(BigDecimal snpId)
    {
        this.snpId = snpId;
    }

    public Date getAod()
    {
        return aod;
    }

    public void setAod(Date aod)
    {
        this.aod = aod;
    }

    public String getRepSnpId()
    {
        return repSnpId;
    }

    public void setRepSnpId(String repSnpId)
    {
        this.repSnpId = repSnpId;
    }

    public String getHtmlPageRef()
    {
        return htmlPageRef;
    }

    public void setHtmlPageRef(String htmlPageRef)
    {
        this.htmlPageRef = htmlPageRef;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public BTR_CONTROLCO getBtrControlCO()
    {
	return btrControlCO;
    }

    public void setBtrControlCO(BTR_CONTROLCO btrControlCO)
    {
	this.btrControlCO = btrControlCO;
    }

    public String execute() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SessionCO sessionCO = returnSessionObject();
	BigDecimal compCode = sessionCO.getCompanyCode();
	BTR_CONTROLSC btSC = new BTR_CONTROLSC();
	btSC.setCOMP_CODE(compCode);
	btrControlCO = controlRecordBO.retrieveControlRecordData(btSC);
	if(RepConstantsCommon.Y.equals(btrControlCO.getBtrControlVO().getSITCOM_ENABLE_YN()))
	{
	    btrControlCO.setSitcomEnableYn(ConstantsCommon.TRUE);
	}
	else
	{
	    btrControlCO.setSitcomEnableYn(RepConstantsCommon.FALSE);
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySn = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySnValPath = new SYS_PARAM_SCREEN_DISPLAYVO();
	    screenDisplaySnValPath.setIS_READONLY(BigDecimal.ONE);
	    screenDisplaySnValPath.setIS_VISIBLE(BigDecimal.ZERO);
	    screenDisplaySn.setIS_READONLY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("snapshotPath", screenDisplaySn);
	    getAdditionalScreenParams().put("sitcomTxt", screenDisplaySn);
	    getAdditionalScreenParams().put("valPathS", screenDisplaySnValPath);
	}
	set_showSmartInfoBtn(RepConstantsCommon.FALSE);
	repSessionCO.setSnParameterScreenHash(new HashMap<String, Object>());
	repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_INIT_MAP, fillInitMap(compCode));
	return "snapshotParameterList";
    }

    public HashMap<String, REP_SNAPSHOT_PARAMCO> fillInitMap(BigDecimal compCode) throws Exception
    {
	SnapshotParameterSC sc = new SnapshotParameterSC();
	sc.setCOMP_CODE(compCode);
	return snapshotParameterBO.fillInitMap(sc);
    }

    public String loadSnapshotParameterPage()
    {
	try
	{
	    set_searchGridId("snapshotParameterListGridTbl_Id");
	}
	catch(Exception ex)
	{
	    handleException(ex, null, null);
	}
	return "snapshotParameterList";
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {
	this.snapshotParameterBO = snapshotParameterBO;
    }

    public String snEnFileGenDependency() throws Exception
    {
	try
	{
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySn = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySnValPath = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(ConstantsCommon.TRUE.equals(btrControlCO.getSitcomEnableYn()))
	    {
		screenDisplaySn.setIS_READONLY(BigDecimal.ZERO);
		screenDisplaySnValPath.setIS_VISIBLE(BigDecimal.ONE);
		screenDisplaySnValPath.setIS_READONLY(BigDecimal.ZERO);
	    }
	    else
	    {
		screenDisplaySn.setIS_READONLY(BigDecimal.ONE);
		btrControlCO.getBtrControlVO().setSITCOM_TEXT("");
		screenDisplaySnValPath.setIS_READONLY(BigDecimal.ONE);
		screenDisplaySnValPath.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    getAdditionalScreenParams().put("snapshotPath", screenDisplaySn);
	    getAdditionalScreenParams().put("sitcomTxt", screenDisplaySn);
	    getAdditionalScreenParams().put("valPathS", screenDisplaySnValPath);

	    return "successJson";
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }

    public String saveAllSn() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SessionCO sessionCO = returnSessionObject();
	    btrControlCO.getBtrControlVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    ArrayList lstAdd;
	    ArrayList lstMod = new ArrayList();
	    ArrayList lstDel = new ArrayList();
	    ArrayList<REP_SNAPSHOT_PARAMCO> lstAddFinal = new ArrayList<REP_SNAPSHOT_PARAMCO>();
	    HashMap<String, Object> snParameterScreenHash = repSessionCO.getSnParameterScreenHash();
	    if(updates != null && !updates.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates, REP_SNAPSHOT_PARAMCO.class);
		lstAdd = gu.getLstAdd();
		Iterator it = lstAdd.iterator();

		while(it.hasNext())
		{
		    REP_SNAPSHOT_PARAMCO repCO = (REP_SNAPSHOT_PARAMCO) it.next();
		    if(!"".equals(repCO.getRepSnapshotParamVO().getREP_REFERENCE()))
		    {
			repCO.getRepSnapshotParamVO().setCOMP_CODE(sessionCO.getCompanyCode());
			repCO.getRepSnapshotParamVO().setCREATED_BY(sessionCO.getUserName());
			lstAddFinal.add(repCO);
		    }
		}
		lstMod = gu.getLstModify();
		// changing the created_by for modified records
		Iterator itt = lstMod.iterator();
		while(itt.hasNext())
		{
		    ((REP_SNAPSHOT_PARAMCO) itt.next()).getRepSnapshotParamVO().setCREATED_BY(sessionCO.getUserName());
		}
		lstDel = gu.getLstDelete();
	    }
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.SNAPSHOT_PARAM_KEY_REF);
	    snapshotParameterBO.saveAllSnDetails(lstAddFinal, lstMod, lstDel, snParameterScreenHash, btrControlCO,
		    refCO, sessionCO);
	    repSessionCO.setSnParameterScreenHash(new HashMap<String, Object>());

	}
	catch(BOException e)
	{
	    if(null != e.getMsgIdent() && e.getMsgIdent().equals("regExp"))
	    {
		handleException(null, null, "reporting.pathValidation");
	    }
	    else
	    {
		cleanSnSession();
		handleException(e, null, null);
	    }

	}

	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "successJson";
    }

    public String cleanSnSession() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.setSnParameterScreenHash(new HashMap<String, Object>());
	    repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_INIT_MAP,
		    fillInitMap(returnSessionObject().getCompanyCode()));
	    //colTechMap(progRef, HashMap(techName, lblAlias))
	    repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.MISMATCH_COL_TECH_NAME,new HashMap<String,HashMap<String ,Object>>());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "successJson";
    }

    public void setControlRecordBO(ControlRecordBO controlRecordBO)
    {
	this.controlRecordBO = controlRecordBO;
    }

    public String getHideFields()
    {
	if(btrControlCO == null || ConstantsCommon.TRUE.equals(btrControlCO.getSitcomEnableYn()))
	{
	    return RepConstantsCommon.FALSE;
	}
	else
	{
	    return ConstantsCommon.TRUE;
	}
    }

    public String reloadDivGrid() throws JSONException
    {
	if(ConstantsCommon.TRUE.equals(btrControlCO.getSitcomEnableYn()))
	{
	    btrControlCO.setSitcomEnableYn(RepConstantsCommon.FALSE);
	}
	else
	{
	    btrControlCO.setSitcomEnableYn(ConstantsCommon.TRUE);
	}
	return "reloadDivGrid";
    }
    public String openSaveSnapshot()throws Exception
    {
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> snpFreq = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	snpFreq = returnCommonLibBO().applyDynScreenDisplay("lookuptxt_snpFreq"  , "snpInfoCO.SNP_FRQ",
		ConstantsCommon.ACTION_TYPE_MANDATORY, "1", snpFreq, null);
	getAdditionalScreenParams().putAll(snpFreq);

	
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> snpDesc  = returnCommonLibBO().applyDynScreenDisplay("snpDesc", "snpInfoCO.snpInfoVO.DESCRIPTION",
		ConstantsCommon.ACTION_TYPE_MANDATORY, "1", snpFreq, null);
	getAdditionalScreenParams().putAll(snpDesc);

	
	
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> aodInput = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> aodInputMand = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> aodLbl = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	//if fcr , show the oad as parameter in the screen
	if(get_pageRef().endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	{
	    aodInput = returnCommonLibBO().applyDynScreenDisplay("snpAOD", null, ConstantsCommon.ACTION_TYPE_VISIBLE,
		    "1", aodInput, null);

	    aodLbl = returnCommonLibBO().applyDynScreenDisplay("snpAodLbl", null, ConstantsCommon.ACTION_TYPE_VISIBLE,
		    "1", aodLbl, null);

	    aodInputMand = returnCommonLibBO().applyDynScreenDisplay("snpAOD", null,
		    ConstantsCommon.ACTION_TYPE_MANDATORY, "1", aodInputMand, null);
	}
	else
	{
	    aodInput = returnCommonLibBO().applyDynScreenDisplay("snpAOD", null, ConstantsCommon.ACTION_TYPE_VISIBLE,
		    "0", aodInput, null);

	    aodLbl = returnCommonLibBO().applyDynScreenDisplay("snpAodLbl", null, ConstantsCommon.ACTION_TYPE_VISIBLE,
		    "0", aodLbl, null);
	}
	getAdditionalScreenParams().putAll(aodInput);
	getAdditionalScreenParams().putAll(aodLbl);
	getAdditionalScreenParams().putAll(aodInputMand);

	return "saveSnpSht";
    } 
    
    public String checkSnpStatusBeforeSave()
    {
	
	return "successJson";
    }
    
   /* public String checkIfOverrideSnp() throws Exception
    {
	try
	{
	    String freq = getUpdates();
	    Date snpAOD = getAod();
	    SnapshotParameterSC snpInfoSC = new SnapshotParameterSC();
	    snpInfoSC.setREP_ID(getSnpId());
	    snpInfoSC.setSNAPSHOT_FREQUENCY(freq);
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> snpLst = snapshotParameterBO.retSnpListByRepIdAndFreq(snpInfoSC);

	    int aodMonth = DateUtil.pathMonth(snpAOD);
	    int aodYear = DateUtil.getDayYearMonth(snpAOD, DateUtil.YEAR);

	    REP_SNAPSHOT_INFORMATIONCO co;
	    Date retrieveDate;
	    int retrieveMonth;
	    int retrieveYear;

	    boolean found = false;
	    for(int i = 0; i < snpLst.size(); i++)
	    {
		co = snpLst.get(i);
		retrieveDate = co.getRepSnapshotInfoVO().getRETREIVE_DATE();
		retrieveMonth = DateUtil.pathMonth(retrieveDate);
		retrieveYear = DateUtil.getDayYearMonth(retrieveDate, DateUtil.YEAR);
		if(aodYear == retrieveYear)
		{
		    if(RepConstantsCommon.SNP_FREQUENCY_YEARLY.equals(freq))
		    {
			retSnpIdAndIsDeclared(co, found);
			 found=true;
			 setUpdates1(co.getRepSnapshotInfoVO().getDESCRIPTION());
			break;
		    }
		    else if(RepConstantsCommon.SNP_FREQUENCY_SEMI_ANNUALLY.equals(freq))
		    {
			if((aodMonth <= 6 && retrieveMonth <= 6) || (aodMonth > 6 && retrieveMonth > 6))
			{
			    retSnpIdAndIsDeclared(co, found);
			    found=true;
			    setUpdates1(co.getRepSnapshotInfoVO().getDESCRIPTION());
			    break;
			}
		    }
		    else if(RepConstantsCommon.SNP_FREQUENCY_QUARTERLY.equals(freq))
		    {
			if((aodMonth <= 3 && retrieveMonth <= 3)
				|| (aodMonth > 3 && aodMonth <= 6 && retrieveMonth > 3 && retrieveMonth <= 6)
				|| (aodMonth > 6 && aodMonth <= 9 && retrieveMonth > 6 && retrieveMonth <= 9)
				|| (aodMonth > 9 && aodMonth <= 12 && retrieveMonth > 9 && retrieveMonth <= 12))
			{
			    retSnpIdAndIsDeclared(co, found);
			    found=true;
			    setUpdates1(co.getRepSnapshotInfoVO().getDESCRIPTION());
			    break;
			}
		    }
		    else if(RepConstantsCommon.SNP_FREQUENCY_MONTHLY.equals(freq) && aodMonth == retrieveMonth)
		    {
			    retSnpIdAndIsDeclared(co, found);
			    found=true;
			    setUpdates1(co.getRepSnapshotInfoVO().getDESCRIPTION());
			    break;
		    }
		}
	    }
	    if(!found)
	    {
		setRepSnpId(null);
		setUpdates(RepConstantsCommon.N);
		 setUpdates1("");
	    }
	}
	catch(Exception e)
	{
	    //log.error(e, "");
	    handleException(e, "", "");
	}

	return "successJson";
    }

    public void retSnpIdAndIsDeclared(REP_SNAPSHOT_INFORMATIONCO co, boolean found)
    {
	try
	{
	    setRepSnpId(co.getRepSnapshotInfoVO().getREP_SNAPSHOT_ID().toString());
	    if(RepConstantsCommon.Y.equals(co.getRepSnapshotInfoVO().getDECLARED_YN()))
	    {
		setUpdates(RepConstantsCommon.Y);
	    }
	    else
	    {
		setUpdates(RepConstantsCommon.N);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }  */  

}
