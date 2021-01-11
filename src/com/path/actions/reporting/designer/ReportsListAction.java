package com.path.actions.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.json.JSONException;

import com.path.actions.ReportAction.SepartorFormat;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.HyperlinksBO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.PTH_CTRLVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ftr.template.AXSCO;

public class ReportsListAction extends ReportingGridBaseAction
{

    /**
	 * 
	 */
    //private static final long serialVersionUID = 4303701322609374433L;
    private IRP_AD_HOC_REPORTCO repCO;
    private List<IRP_AD_HOC_REPORTCO> reportsList;
    private DesignerBO designerBO;
    private String updates;
    IRP_AD_HOC_REPORTVOWithBLOBs vo = new IRP_AD_HOC_REPORTVOWithBLOBs();
    IRP_AD_HOC_REPORTSC sc = new IRP_AD_HOC_REPORTSC();
    BigDecimal[] reportsId;
    BigDecimal[] oldReportsId;
    private HyperlinksBO hyperlinksBO;
    String[] appsName;
    String[] progRefs;
    String accessStr;
    String editableStr;
    private String updates1;
    private String updates2;
    private int isSyb;
    ArrayList<SYS_PARAM_LOV_TRANSVO> reportFormats = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private CommonLookupBO commonLookupBO;
    private ArrayList<SepartorFormat> csvSeparators;
    List<IRP_CONNECTIONSVO> connectionsList;
    private String calledFrom;
    private String isSchedRep;
    private String haveClt;
    private String update;

    public String getUpdate()
    {
        return update;
    }

    public void setUpdate(String update)
    {
        this.update = update;
    }
    
    
    
    
  

    public String getHaveClt()
    {
        return haveClt;
    }

    public void setHaveClt(String haveClt)
    {
        this.haveClt = haveClt;
    }

    public String getIsSchedRep()
     {
         return isSchedRep;
     }

     public void setIsSchedRep(String isSchedRep)
     {
         this.isSchedRep = isSchedRep;
     }
    public String getEditableStr()
    {
        return editableStr;
    }

    public void setEditableStr(String editableStr)
    {
        this.editableStr = editableStr;
    }

    public String getCalledFrom()
    {
	return calledFrom;
    }

    public void setCalledFrom(String calledFrom)
    {
	this.calledFrom = calledFrom;
    }

    public List<IRP_CONNECTIONSVO> getConnectionsList()
    {
	return connectionsList;
    }

    public void setConnectionsList(List<IRP_CONNECTIONSVO> connectionsList)
    {
	this.connectionsList = connectionsList;
    }

    public ArrayList<SepartorFormat> getCsvSeparators()
    {
	return csvSeparators;
    }

    public void setCsvSeparators(ArrayList<SepartorFormat> csvSeparators)
    {
	this.csvSeparators = csvSeparators;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getReportFormats()
    {
	return reportFormats;
    }

    public void setReportFormats(ArrayList<SYS_PARAM_LOV_TRANSVO> reportFormats)
    {
	this.reportFormats = reportFormats;
    }

    public String getUpdates2()
    {
	return updates2;
    }

    public void setUpdates2(String updates2)
    {
	this.updates2 = updates2;
    }

    public int getIsSyb()
    {
	return isSyb;
    }

    public void setIsSyb(int isSyb)
    {
	this.isSyb = isSyb;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public String getAccessStr()
    {
	return accessStr;
    }

    public void setAccessStr(String accessStr)
    {
	this.accessStr = accessStr;
    }

    public String[] getAppsName()
    {
	return appsName;
    }

    public void setAppsName(String[] appsName)
    {
	this.appsName = appsName;
    }

    public void setHyperlinksBO(HyperlinksBO hyperlinksBO)
    {
	this.hyperlinksBO = hyperlinksBO;
    }

    public BigDecimal[] getOldReportsId()
    {
	return oldReportsId;
    }

    public void setOldReportsId(BigDecimal[] oldReportsId)
    {
	this.oldReportsId = oldReportsId;
    }

    public List<IRP_AD_HOC_REPORTCO> getReportsList()
    {
	return reportsList;
    }

    public void setReportsList(List<IRP_AD_HOC_REPORTCO> reportsList)
    {
	this.reportsList = reportsList;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public IRP_AD_HOC_REPORTCO getRepCO()
    {
	return repCO;
    }

    public void setRepCO(IRP_AD_HOC_REPORTCO repCO)
    {
	this.repCO = repCO;
    }

    @Override
    public Object getModel()
    {
	return sc;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public BigDecimal[] getReportsId()
    {
	return reportsId;
    }

    public void setReportsId(BigDecimal[] reportsId)
    {
	this.reportsId = reportsId;
    }

    public String[] getProgRefs()
    {
	return progRefs;
    }

    public void setProgRefs(String[] progRefs)
    {
	this.progRefs = progRefs;
    }

    public String openReportsList()
    {
	set_showSmartInfoBtn("false");
	return SUCCESS;
    }

    public String retTrxNb() throws Exception
    {

	JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	JSONObject jsonModel = jsonFilter.getJSONObject("repCO");
	repCO = (IRP_AD_HOC_REPORTCO) JSONObject.toBean(jsonModel, IRP_AD_HOC_REPORTCO.class);

	AuditRefCO refCO = initAuditRefCO();
	refCO.setOperationType(AuditConstant.RETRIEVE);
	refCO.setKeyRef(AuditConstant.REPORTS_KEY_REF);
	setAuditTrxNbr(auditBO.checkAuditKey(repCO, refCO));
	return "grid";
    }

    public String loadGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(sc);
	    if(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF.equals(get_pageRef()))
	    {
		sc.setFILTER_DESIGNER_REP("1");
		sc.setAPP_NAME(sessionCO.getCurrentAppName());
	    }

	    PTH_CTRLVO pthCtrl = returnCommonLibBO().returnPthCtrl();
	    sc.setProfType(NumberUtil.nullToZero(pthCtrl.getPOP_PROF_MOD_ACCESS()));
	    sc.setAPP_NAME(sessionCO.getCurrentAppName());
	    sc.setBRANCH_CODE(sessionCO.getBranchCode());
	    sc.setCOMP_CODE(sessionCO.getCompanyCode());
	    sc.setUSER_ID(sessionCO.getUserName());
	    if(checkNbRec(sc))
	    {
		setRecords(designerBO.getReportsCount(sc));
	    }
	    reportsList = designerBO.getReportsList(sc);
	    setGridModel(reportsList);
	    isSyb = returnCommonLibBO().returnIsSybase();
	}
	catch(Exception e)
	{
	    //log.error(e, "Error in method loadGrid in ReportsListAction");
	    handleException(e, "Error Loading Reports list", "loadReports.exceptionMsg._key");
	}
	return "grid";
    }

    public void delete()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String pRefs = Arrays.asList(progRefs).toString();
	    pRefs = pRefs.substring(1, pRefs.length() - 1);
	    pRefs = pRefs.replaceAll(" ", "");
	    String[] progRefsStr = pRefs.split(",");
	    List<String> progRefList = Arrays.asList(progRefsStr);

	    // delete from table AXS
	    AXSCO axsCO = new AXSCO();
	    axsCO.setCOMP_CODE(sessionCO.getCompanyCode());
	    axsCO.setBRANCH_CODE(sessionCO.getBranchCode());
	    axsCO.setUSER_ID(returnUserName());

	    String pgRef = get_pageRef();
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.DELETE);
	    if(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF.equals(pgRef))
	    {
		refCO.setKeyRef(AuditConstant.UPLOAD_DOWNLOAD_KEY_REF);
	    }
	    else
	    {
		refCO.setKeyRef(AuditConstant.REPORTS_KEY_REF);
	    }

	    designerBO.deleteReport(Arrays.asList(reportsId), axsCO, refCO, Arrays.asList(oldReportsId), Arrays
		    .asList(appsName), progRefList,false,sessionCO.getUserName(),get_pageRef());
	}
	catch(Exception e)
	{
	    handleException(e, "Error deleting a report", "deleteReport.exceptionMsg._key");
	    //log.error(e, "Error in method delete in ReportsListAction");
	}
    }

    // check if the user has access to delete the report and check if the report
    // is not used as hyperlink report or as scheduled report
    public String checkIfDeleteReport() throws Exception
    {
	try
	{
	    List<BigDecimal> repIdsLst = Arrays.asList(reportsId);
	    List<String> appsNameLst = Arrays.asList(appsName);

	    String pRefs = Arrays.asList(progRefs).toString();
	    pRefs = pRefs.substring(1, pRefs.length() - 1);
	    pRefs = pRefs.replaceAll(" ", "");
	    String[] progRefsStr = pRefs.split(",");
	    List<String> progRefsLst = Arrays.asList(progRefsStr);
	    String hasDelAxs;
	    List<BigDecimal> accessRepIds = new ArrayList<BigDecimal>();
	   
	    String appName;
	    // check user access
	    for(int i = 0; i < repIdsLst.size(); i++)
	    {
		appName = appsNameLst.get(i);
		hasDelAxs=returnAccessRightByProgRef(progRefsLst.get(i * 8) + "D",appName);
		if(hasDelAxs ==null)
		{
		    accessRepIds.add(repIdsLst.get(i));
		}
	    }
	    // check hyperlinks usage,subReports usage and scheduler usage
	    if(accessRepIds.isEmpty())
	    {
		setAccessStr("");
		List<BigDecimal> hypRepIds = hyperlinksBO.retHypReportUsage(repIdsLst);
		if(hypRepIds == null || hypRepIds.isEmpty())
		{
		    setUpdates("");		    
		}
		else
		{
		    setUpdates(hypRepIds.toString());
		}
		// check if subreports
		List<BigDecimal> subRepIds = designerBO.retSubReportUsage(repIdsLst);
		if(subRepIds == null || subRepIds.isEmpty())
		{
		    setUpdates1("");
		}
		else
		{
		    setUpdates1(subRepIds.toString());
		}
		// check scheduler
		List<BigDecimal> schedRepIds = designerBO.retSchedUsage(repIdsLst);
		if(schedRepIds == null || schedRepIds.isEmpty())
		{
		    setUpdates2("");
		}
		else
		{
		    setUpdates2(schedRepIds.toString());
		}
		
	    }
	    else
	    {
		setAccessStr(accessRepIds.toString());	
	    }
	    ArrayList<IRP_AD_HOC_REPORTVO> metadataRepList = designerBO.retReportsBeingMetadata(repIdsLst);
	    // "" to not add condition in js
	    update = "";
	    IRP_AD_HOC_REPORTVO metaVO;
	    if(!metadataRepList.isEmpty())
	    {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < metadataRepList.size(); i++)
		{
		    metaVO = metadataRepList.get(i);
		    sb.append(metaVO.getMETADATA_REPORT_ID() + " "+getText("metadata_of")+ " : " + metaVO.getREPORT_ID());
		    if(i != metadataRepList.size() - 1)
		    {
			sb.append(",");
		    }
		}
		update = sb.toString();
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "grid";
    }

    public String checkUpdateReportAccess() throws Exception
    {
	try
	{
	    List<String> progRefLst = new ArrayList<String>();
	    String progRef = getUpdates1();
	    String appName = getUpdates();
	    boolean updateMode = true;
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    // check if we are in update mode
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO lRepCO = repSessionCO.getReportCO();
	    boolean haveNoClt = false;
	    String cltRepFlag = "1";
	    if(lRepCO!=null)
	    {
		cltRepFlag = lRepCO.getCltRepFlag();//cltRepFlag 0:visible 1:hidden
		haveNoClt = lRepCO.getRepClientList().isEmpty();
	    }
	    if(progRef == null) // called when saving a report
	    {
		if(lRepCO.getREPORT_ID() == null)
		{
		    updateMode = false;
		}
		else
		{
		    progRef = lRepCO.getPROG_REF();
		    appName = lRepCO.getAPP_NAME();
		}
		
		setEditableStr(ConstantsCommon.YES);
		if(lRepCO.getREPORT_ID() != null)
		{
		    repSC.setREPORT_ID(lRepCO.getREPORT_ID());
		    lRepCO = designerBO.retReportFlags(repSC);
		    if(BigDecimal.ONE.equals((lRepCO.getEDITABLE_FLAG())))
		    {
			setEditableStr(ConstantsCommon.NO);
		    }
		}
	    }

	    if(updateMode)
	    {
		progRefLst.add(progRef + getUpdates2());
		String accessRight = returnAccessRightByProgRef(progRef + getUpdates2(),appName);
		if(accessRight ==null)
		{
		    setAccessStr("N");
		}
		else
		{
		    setAccessStr("Y");
		}
		if(repCO==null)
		{
		    repSC.setREPORT_ID(lRepCO.getREPORT_ID());
		}
		else
		{
		    repSC.setREPORT_ID(repCO.getREPORT_ID());
		}
		repCO = designerBO.retReportFlags(repSC);
		if(BigDecimal.ONE.equals((repCO.getEDITABLE_FLAG())))
		{
		    setEditableStr(ConstantsCommon.NO);
		}
		else
		{
		    setEditableStr(ConstantsCommon.YES);
		}
	    }
	    	
		if(lRepCO !=null && "0".equals(cltRepFlag) && haveNoClt )
		{
		    setHaveClt("0");
		}
	    
	    

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "grid";
    }

    // check if the user has a hyperlink
    public String checkIfHasHyperlink() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO repCO = repSessionCO.getReportCO();
	    int hypCnt = hyperlinksBO.retIfRepHasHyp(repCO.getREPORT_ID());
	    if(hypCnt > 0)
	    {
		setUpdates("1");
	    }
	    else
	    {
		setUpdates("0");
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "grid";
    }

    public String openSaveAsDlg()
    {
	try
	{
	    // fill combos
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    reportFormats = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    csvSeparators = new ArrayList<SepartorFormat>();
	    connectionsList = new ArrayList<IRP_CONNECTIONSVO>();
	    
	    csvSeparators.add(new SepartorFormat(",", ","));
	    csvSeparators.add(new SepartorFormat(getText("reporting.tab"), "\\t"));
	    
	    connectionsList = commonLookupBO.returnConnectionsList();
	    // hide some inputs when calling the save as from the designer
	    if("0".equals(calledFrom))
	    {
		SYS_PARAM_SCREEN_DISPLAYVO buisnessElement = new SYS_PARAM_SCREEN_DISPLAYVO();
		buisnessElement.setIS_VISIBLE(BigDecimal.ZERO);
		HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

		hm.put("skipOptAxs_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("skipOptAxsLbl_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("format_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("formatLbl_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("sepValTd_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("sepValTdLbl_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("csvSeparatorId_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("noHeadAndFoot_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("noHeadAndFootLbl_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("connection_" + get_pageRef() + "_" + calledFrom, buisnessElement);
		hm.put("connectionLbl_" + get_pageRef() + "_" + calledFrom, buisnessElement);

		setAdditionalScreenParams(hm);
	    }

	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repCO = repSessionCO.getReportCO();
	}
	catch(BaseException e)
	{
	    log.error(e, "");
	}

	return "successSA";
    }
    
    public String retSchedUsage()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	    BigDecimal repId = reportCO.getREPORT_ID();
	    if(repId != null)
	    {
		List<BigDecimal> reportIdsLst = Arrays.asList(repId);
		List<BigDecimal> schedRepIds = designerBO.retSchedUsage(reportIdsLst);
		if(schedRepIds != null && !schedRepIds.isEmpty())
		{
		    isSchedRep = "1";
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "grid";
    }
}
