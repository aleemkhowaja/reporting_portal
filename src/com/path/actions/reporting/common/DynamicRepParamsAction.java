package com.path.actions.reporting.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.path.actions.ReportAction.ReportFormat;
import com.path.actions.ReportAction.SepartorFormat;
import com.path.actions.admin.dynamicparams.ReportingDynamicParamsAction;
import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.bo.reporting.ftr.controlRecord.ControlRecordBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dbmaps.vo.BRANCHESVO;
import com.path.dbmaps.vo.BRANCHESVOKey;
import com.path.dbmaps.vo.BTR_CONTROLVO;
import com.path.dbmaps.vo.CURRENCIESVO;
import com.path.dbmaps.vo.CURRENCIESVOKey;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTS_FILTERSVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.vo.admin.dynamicparams.DynamicParamsVO;
import com.path.vo.admin.dynamicparams.ListParamVO;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.select.SelectSC;
import com.path.vo.reporting.DynLookupSC;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_ARG_CONSTRAINTSCO;
import com.path.vo.reporting.IRP_REP_FILTERSSC;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSSC;

public class DynamicRepParamsAction extends ReportingDynamicParamsAction implements ServletRequestAware
{
    protected HttpServletRequest request;
    private String menu;
    private DesignerBO designerBO;
    private List formatList;
    private List langList;
    private BigDecimal code;
    private String update;
    private BigDecimal schedId;
    private SchedulerBO schedulerBO;
    private CommonLookupBO commonLookupBO;
    private List chkSaveList;
    private List chkFlagList;
    HashMap paramsFlag = new HashMap();
    HashMap noHeadFootMap = new HashMap();
    private List chkNoHeadAndFootLst;
    private List csvSeparatorsList;
    private List<IRP_CONNECTIONSVO> connectionList;
    private String var_format;
    private String var_separator;
    private String var_noHeadAndFoot;
    private BigDecimal var_db;
    private String htmlPageRef; // page reference (- replaced by _)
    private String l;
    private QueryBO queryBO;
    private CommonLibBO commonLibBO;
    private FcrBO fcrBO;
    private SnapshotParameterBO snapshotParameterBO;
    private String appName;
    private String updates;
    private ControlRecordBO controlRecordBO;
    private BigDecimal fromFileExpImp;
    private CommonReportingBO commonReportingBO;
    private String report_ref; // used in scheduler in case of FCR reports
    private  HashMap<String, ArrayList<LinkedHashMap>> combosLists = new HashMap<String, ArrayList<LinkedHashMap>>();
    private HashMap<String, String> combosValues = new HashMap<String, String>();
    
    

    public HashMap<String, String> getCombosValues()
    {
        return combosValues;
    }

    public void setCombosValues(HashMap<String, String> combosValues)
    {
        this.combosValues = combosValues;
    }

    public void setCombosLists(HashMap<String, ArrayList<LinkedHashMap>> combosLists)
    {
        this.combosLists = combosLists;
    }

    public HashMap<String, ArrayList<LinkedHashMap>> getCombosLists()
    {
        return combosLists;
    }

    public String getReport_ref()
    {
        return report_ref;
    }

    public void setReport_ref(String reportRef)
    {
        report_ref = reportRef;
    }

    public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
        this.commonReportingBO = commonReportingBO;
    }
    /**
     * @return the fromFileExpImp
     */
    public BigDecimal getFromFileExpImp()
    {
        return fromFileExpImp;
    }

    /**
     * @param fromFileExpImp the fromFileExpImp to set
     */
    public void setFromFileExpImp(BigDecimal fromFileExpImp)
    {
        this.fromFileExpImp = fromFileExpImp;
    }

    public String getUpdates()
    {
        return updates;
    }

    public void setUpdates(String updates)
    {
        this.updates = updates;
    }

    public void setControlRecordBO(ControlRecordBO controlRecordBO)
    {
        this.controlRecordBO = controlRecordBO;
    }
    
    public String getAppName()
    {
	return appName;
    }

    public void setAppName(String appName)
    {
	this.appName = appName;
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {
	this.snapshotParameterBO = snapshotParameterBO;
    }

    public BigDecimal getVar_db()
    {
	return var_db;
    }

    public void setVar_db(BigDecimal varDb)
    {
	var_db = varDb;
    }

    public List<IRP_CONNECTIONSVO> getConnectionList()
    {
	return connectionList;
    }

    public void setConnectionList(List<IRP_CONNECTIONSVO> connectionList)
    {
	this.connectionList = connectionList;
    }

    public void setFcrBO(FcrBO fcrBO)
    {
	this.fcrBO = fcrBO;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
	this.commonLibBO = commonLibBO;
    }

    public String getHtmlPageRef()
    {
	return htmlPageRef;
    }

    public String getL()
    {
	return l;
    }

    public void setL(String l)
    {
	this.l = l;
    }


    public void setHtmlPageRef(String htmlPageRef)
    {
	this.htmlPageRef = htmlPageRef;
    }

    public String getVar_noHeadAndFoot()
    {
	return var_noHeadAndFoot;
    }

    public void setVar_noHeadAndFoot(String varNoHeadAndFoot)
    {
	var_noHeadAndFoot = varNoHeadAndFoot;
    }

    public String getVar_separator()
    {
	return var_separator;
    }

    public void setVar_separator(String varSeparator)
    {
	var_separator = varSeparator;
    }

    public String getVar_format()
    {
	return var_format;
    }

    public void setVar_format(String varFormat)
    {
	var_format = varFormat;
    }

    public List getCsvSeparatorsList()
    {
	return csvSeparatorsList;
    }

    public void setCsvSeparatorsList(List csvSeparatorsList)
    {
	this.csvSeparatorsList = csvSeparatorsList;
    }

    public List getChkNoHeadAndFootLst()
    {
	return chkNoHeadAndFootLst;
    }

    public void setChkNoHeadAndFootLst(List chkNoHeadAndFootLst)
    {
	this.chkNoHeadAndFootLst = chkNoHeadAndFootLst;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public List getChkSaveList()
    {
	return chkSaveList;
    }

    public void setChkSaveList(List chkSaveList)
    {
	this.chkSaveList = chkSaveList;
    }

    public List getChkFlagList()
    {
	return chkFlagList;
    }

    public void setChkFlagList(List chkFlagList)
    {
	this.chkFlagList = chkFlagList;
    }

    public HashMap getParamsFlag()
    {
	return paramsFlag;
    }

    public HashMap getNoHeadFootMap()
    {
	return noHeadFootMap;
    }

    public void setSchedulerBO(SchedulerBO schedulerBO)
    {
	this.schedulerBO = schedulerBO;
    }

    public BigDecimal getSchedId()
    {
	return schedId;
    }

    public void setSchedId(BigDecimal schedId)
    {
	this.schedId = schedId;
    }

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public String getMenu()
    {
	return menu;
    }

    public void setMenu(String menu)
    {
	this.menu = menu;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public List getFormatList()
    {
	return formatList;
    }

    public void setFormatList(List formatList)
    {
	this.formatList = formatList;
    }

    public List getLangList()
    {
	return langList;
    }

    public void setLangList(List langList)
    {
	this.langList = langList;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public String loadFileExpImpDynParam() throws Exception
    {
	fillReportByProgRef();
	fillFormElement();
	return "SUCCESS_DYNAMIC_PARAM";
    }
    
    public String loadDynParam() throws Exception
    {
	fillFormElement();
	return "SUCCESS_DYNAMIC_PARAM";
    }

    private IRP_AD_HOC_REPORTCO  fillReportByProgRef()throws Exception
    {
	IRP_FIELDSCO irpFieldCO;
	ArrayList<IRP_FIELDSCO> lst=new ArrayList<IRP_FIELDSCO>();
	// stoped by haytham.k for fcr reports
	// if (!menu.endsWith("DY0"))
	// {
	SessionCO sessionCO = returnSessionObject();
	htmlPageRef = get_pageRef().replace("-", "_");

	IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	repSC.setPROG_REF(menu);
	repSC.setCOMP_CODE(sessionCO.getCompanyCode());
	if(appName == null)
	{
	    appName = sessionCO.getCurrentAppName();
	}
	repSC.setAPP_NAME(appName);
	String standardFlagYN = null;
	String fcrProgRef=null;
	String isRowToCol="0";
	String generatedFileName="";
	if(menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	{
	    // to set the new prog_ref without the D00
	    repSC.setPROG_REF(menu.substring(0, menu.length() - 3));
	    IRP_FCR_REPORTSCO irpFcrRepCO = fcrBO.retFcrRep(repSC);
	    if(irpFcrRepCO==null)
	    {
		throw new BOException(getText("rephavingreference_key")+" "+repSC.getPROG_REF()+" "+getText("apprepnotexist_key"));
	    }
	    standardFlagYN = irpFcrRepCO.getSTANDARD_FLAG_YN();
	    isRowToCol=irpFcrRepCO.getROW_COLMN().toString();
	    generatedFileName=StringUtil.nullToEmpty(irpFcrRepCO.getGENERATED_FILE_NAME());
	    if(irpFcrRepCO.getProgRef() != null)
	    {
		    //set the report reference to the fcr_main_report
		    repSC.setPROG_REF(ConstantsCommon.FCR_MAIN_REPORT_REF); 
		    fcrProgRef=irpFcrRepCO.getProgRef();
	    }
	}

	HashMap<String, Object> repSCMap = new HashMap<String, Object>();
	String[] propsArr = ConstantsCommon.retRepIdByProgRef_PROPS
		.toArray(new String[ConstantsCommon.retRepIdByProgRef_PROPS.size()]);
	PathPropertyUtil.copyProperties(repSC, repSCMap, false, propsArr);
	HashMap<String, Object> retHm = commonReportingBO.retRepIdByProgRef(repSCMap);
	IRP_AD_HOC_REPORTCO repIdCO;
	if(retHm.isEmpty())
	{
	    throw new BOException("Report having reference " + menu + " and application " + appName + " doesn't exist.");
	}
	else
	{
	    repIdCO = (IRP_AD_HOC_REPORTCO) PathPropertyUtil.convertToObject(retHm, IRP_AD_HOC_REPORTCO.class);
	    if(NumberUtil.isEmptyDecimal(repIdCO.getREPORT_ID()))
	    {
		  throw new Exception("Report having reference " + menu + " and application " + appName + " doesn't exist.");
	    }
	}

	IRP_AD_HOC_REPORTCO reportCO = designerBO.returnReportById(repIdCO.getREPORT_ID());
	if(menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	{
		reportCO.setGENERATED_FILE_NAME(generatedFileName);
	    reportCO.setFTR_OPT_PROG_REF(menu.substring(0,menu.lastIndexOf(ConstantsCommon.OPT_FCR_EXTENSION)));
	    reportCO.setSTANDARD_FLAG_YN(standardFlagYN);
	    // if dynamic report
	    if(fcrProgRef != null)
	    {
		LinkedHashMap argsMap = reportCO.getArgumentsList();
		Iterator it = argsMap.keySet().iterator();
		Long key;
		IRP_REP_ARGUMENTSCO argObj;
		while(it.hasNext())
		{
		    key = (Long) it.next();
		    argObj = (IRP_REP_ARGUMENTSCO) argsMap.get(key);
		    // if fcr summurazied then set the default value of the
		    // paramter FCR_REF to R0020
		    if(fcrProgRef.indexOf(RepConstantsCommon.FCRSUM_REF) == 0
			    && ConstantsCommon.FCR_FCR_REF.equals(argObj.getARGUMENT_NAME()))
		    {
			argObj.setARGUMENT_VALUE(ConstantsCommon.FCR_SUMMARIZED_OPT);
		    }
		    // if fcr summurazied then set the default value of the
		    // paramter RA_TYPE to N
		    else if(fcrProgRef.indexOf(RepConstantsCommon.FCRSUM_REF) == 0
			    && ConstantsCommon.ARG_RA_TYPE.endsWith(argObj.getARGUMENT_NAME()))
		    {
			argObj.setARGUMENT_VALUE(RepConstantsCommon.N);
			argObj.setDISPLAY_FLAG("N");
		    }
		    // disable the row_to_col and set the value from the the
		    // dynamic screen parametrization
		    else if(ConstantsCommon.ARG_ROW_TO_COL.equals(argObj.getARGUMENT_NAME()))
		    {
		    	if(ConstantsCommon.NO.equals(standardFlagYN))
		    	{
		    		argObj.setENABLE_FLAG(ConstantsCommon.NO);
		    	}
		    	argObj.setARGUMENT_VALUE(isRowToCol);
		    }

		}
	    }
	}
	// fill the reportingSessionCO with the reportCO
	ReportingSessionCO repSessionCO = returnReportingSessionObject(menu);

	/* start fares */
	IRP_FIELDSCO feCO;
	Boolean addedToSortedList;
	// check if sorting exist in IRP_REP_SORT table
	HashMap<String, Object> lstSortingMap = new HashMap<String, Object>();
	lstSortingMap.put("reportId", repIdCO.getREPORT_ID());
	lstSortingMap.put("userId", sessionCO.getUserName());
	lstSortingMap = commonReportingBO.retSortingListFromIrpRepSort(lstSortingMap);

	// fill the prevOrder list by looping through all the sorting field
	SQL_OBJECT sqlObj = reportCO.getQueriesList().get(0).getSqlObject();
	LinkedHashMap<Long, IRP_FIELDSCO> feMap = sqlObj.getFields();
	Iterator it = feMap.values().iterator();
	iter1: while(it.hasNext())
	{
	    addedToSortedList = false;
	    feCO = (IRP_FIELDSCO) it.next();
	    ArrayList<HashMap<String, Object>> SortingMapList = new ArrayList<HashMap<String, Object>>();
	    SortingMapList = (ArrayList<HashMap<String, Object>>) lstSortingMap.get("irpFieldsCOList");
	    if(SortingMapList != null && !SortingMapList.isEmpty())
	    {
		HashMap<String, Object> SortMap;
		Iterator<HashMap<String, Object>> itSort = SortingMapList.iterator();
		iter2: while(itSort.hasNext())
		{
		    SortMap = new HashMap<String, Object>();
		    SortMap = itSort.next();
		    irpFieldCO = new IRP_FIELDSCO();
		    irpFieldCO = (IRP_FIELDSCO) PathPropertyUtil.convertToObject(SortMap, IRP_FIELDSCO.class);
		    if(feCO.getFIELD_ALIAS().equals(irpFieldCO.getFIELD_ALIAS()))
		    {
			irpFieldCO.setIndex(feCO.getIndex());
			irpFieldCO.setFIELD_DB_NAME(feCO.getFIELD_DB_NAME());
			irpFieldCO.setEntityAliasWithCount(feCO.getEntityAliasWithCount());
			irpFieldCO.setENTITY_ALIAS(feCO.getENTITY_ALIAS());
			irpFieldCO.setLabelAlias(feCO.getLabelAlias());
			lst.add(irpFieldCO);
			addedToSortedList = true;
			break iter2;
		    }
		}
	    }
	    if(addedToSortedList)
	    {
		continue iter1;
	    }
	    // in case not all the fields exist in IRP_REP_SORT Table
	    irpFieldCO = new IRP_FIELDSCO();
	    irpFieldCO.setIndex(feCO.getIndex());
	    irpFieldCO.setFIELD_DB_NAME(feCO.getFIELD_DB_NAME());
	    irpFieldCO.setEntityAliasWithCount(feCO.getEntityAliasWithCount());
	    irpFieldCO.setENTITY_ALIAS(feCO.getENTITY_ALIAS());
	    irpFieldCO.setLabelAlias(feCO.getLabelAlias());
	    irpFieldCO.setFIELD_ALIAS(feCO.getFIELD_ALIAS());
	    irpFieldCO.setOrder(null);
	    lst.add(irpFieldCO);
	}
	reportCO.setPrevOrderList(lst);
	/* end fares */
	    
	repSessionCO.setReportCO(reportCO);
	
	return reportCO;
    }
    
    public String loadReportFromMenu() throws Exception
    {
	
	IRP_AD_HOC_REPORTCO reportCO =fillReportByProgRef();
	SessionCO sessionCO = returnSessionObject();
	// translate the field_alias
	CommonLibSC sc = new CommonLibSC();
	sc.setAppName(reportCO.getAPP_NAME());
	sc.setProgRef(reportCO.getPROG_REF());
	sc.setLanguage(sessionCO.getLanguage());

	SQL_OBJECT sqlObj = reportCO.getQueriesList().get(0).getSqlObject();
	IRP_FIELDSCO feCO;
	Iterator it = sqlObj.getFields().values().iterator();
	String feTrans;
	while(it.hasNext())
	{
	    feCO = (IRP_FIELDSCO) it.next();
	    sc.setKeyLabelCode(feCO.getFIELD_ALIAS());
	    feTrans = commonLibBO.returnKeyLabelTrans(sc);
	    feCO.setFIELD_ALIAS(feTrans);
	    feCO.setFeName(feTrans);
	}

	set_showSmartInfoBtn("false");
	// }

	// fcr : ends with D00 ; ftr : reportType=1
	if( menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION) || "1".equals(reportCO.getREPORT_TYPE()))
	{
	    // check if has record in snpParam
	    SnapshotParameterSC snpParamSC = new SnapshotParameterSC();
	    if(menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	    {
		snpParamSC.setREP_REFERENCE(menu.substring(0, menu.length() - 3));
	    }
	    else
	    {
		snpParamSC.setREP_REFERENCE(menu);
	    }
	    snpParamSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    snpParamSC.setSAVE_REP_YN("Y");
	    BigDecimal hasSnpParam = snapshotParameterBO.checkRepHasEnableSnpParam(snpParamSC);
	    if(hasSnpParam != null && hasSnpParam.intValue() > 0)
	    {
		// check if has opt axs
		String hasSnpAxs = returnAccessRightByProgRef(RepConstantsCommon.SNP_BTN_AXS_REF, sessionCO
			.getCurrentAppName());
		if(hasSnpAxs == null)
		{
		    showHideBtn("0", "saveSnpBtn_");
		}
		else
		{
		    showHideBtn("1", "saveSnpBtn_");
		}
	    }
	    else
	    {
		showHideBtn("0", "saveSnpBtn_");
	    }
	}
	else
	{
	    showHideBtn("0", "saveSnpBtn_");
	}

	String hasVerifyAccess=returnAccessRightByProgRef(ConstantsCommon.OPT_VERIFY_BTN);
	if(hasVerifyAccess ==null)
	{
	    showHideBtn("0","verifyBtn_");
	}
	else
	{
	    showHideBtn("1","verifyBtn_");
	}

	return "SUCCESS_REP_PREVIEW";
    }

    public void showHideBtn(String visibleVal, String elemId)
    {
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> button = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	try
	{
	    button = returnCommonLibBO().applyDynScreenDisplay(elemId + htmlPageRef, null,
		    ConstantsCommon.ACTION_TYPE_VISIBLE, visibleVal, button, null);
	    getAdditionalScreenParams().putAll(button);
	}
	catch(BaseException e)
	{
	    log.error(e, e.getMessage());
	}
    }

    /**
     * Method that gets default value of an argument
     * @param repCO
     * @param argObj
     * @param paramVal
     * @return
     */
    public ArrayList<LinkedHashMap> retQryDfltVal(IRP_AD_HOC_REPORTCO repCO, IRP_REP_ARGUMENTSCO argObj,String paramVal)
    {
	try
	{
	LinkedHashMap argsMap = repCO.getArgumentsList();
	ArrayList<IRP_REP_ARGUMENTSCO> argsList = new ArrayList(argsMap.values());
	Object argSessionValue;
	IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	SessionCO sessionCO = returnSessionObject();
	String sessionParamName;
	for(int i = 0; i < argsList.size(); i++)
	{

	    if((!NumberUtil.isEmptyDecimal(argsList.get(i).getQUERY_ID_DEFAULT()) || StringUtil.isNotEmpty(paramVal))
		    && argsList.get(i).getARGUMENT_NAME().equals(argObj.getARGUMENT_NAME()))
	    {

		DynLookupSC dynLookupSC = new DynLookupSC();
		HashMap<String, String> hmQryParam = new HashMap<String, String>();

		reportSC.setREPORT_ID(repCO.getREPORT_ID());
		reportSC.setReportArgumentId(argsList.get(i).getARGUMENT_ID());
		reportSC.setDefaultSrc(StringUtil.isNotEmpty(paramVal) ? BigDecimal.ONE : new BigDecimal(2));
		List<IRP_QUERY_ARG_MAPPINGCO> listDfltSrc = designerBO.retQryArgMapping(reportSC);


		    IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(StringUtil.isNotEmpty(paramVal) ? argsList.get(i).getQUERY_ID()
			    : argsList.get(i).getQUERY_ID_DEFAULT(), true);
		    for(int j = 0; j < query.getSqlObject().getArgumentsList().size(); j++)
		    {
			LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments = query.getSqlObject().getArgumentsList();
			ArrayList<IRP_REP_ARGUMENTSCO> argsQryList = new ArrayList(arguments.values());

			for(int m = 0; m < listDfltSrc.size(); m++)
			{
			    for(int n = 0; n < listDfltSrc.size(); n++)
			    {
				if(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME().equals(
					argsQryList.get(n).getARGUMENT_NAME()))
				{
				    listDfltSrc.get(m).setARGUMENT_TYPE(argsQryList.get(n).getARGUMENT_TYPE());
				    if(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getSTATIC_VALUE() == null
					    || ("").equals(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						    .getSTATIC_VALUE()))
				    {
					for(int a = 0; a < argsList.size(); a++)
					{
					    if(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						    .getREPORT_MAPPED_ARG_NAME().equals(
							    argsList.get(a).getARGUMENT_NAME()))
					    {
						if(argsList.get(a).getARGUMENT_SOURCE().equals(ConstantsCommon.SESSION_ARG_SOURCE)
							|| argsList.get(a).getARGUMENT_SOURCE().equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE))
						{
						    sessionParamName=argsList.get(a).getSESSION_ATTR_NAME();
						    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage())
							    && ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName) )
						    {
							sessionParamName=ConstantsCommon.COMP_AR_NAME_SESSION;
						    }
						    argSessionValue = PathPropertyUtil.returnProperty(sessionCO,sessionParamName);
						    hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
							    .getQUERY_ARG_NAME(), argSessionValue.toString());
						}
						else if(argsList.get(a).getARGUMENT_SOURCE().equals(new BigDecimal(5)))
						{
						    hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
							    .getQUERY_ARG_NAME(), menu);
						}
						else
						{
						    hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
							    .getQUERY_ARG_NAME(), argsList.get(a).getARGUMENT_VALUE());
						}
					    }
					}
				    }
				    else
				    {
					hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						.getQUERY_ARG_NAME(), listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						.getSTATIC_VALUE());
				    }
				}
			    }
			}

		    }
		    query.getSqlSyntax();
		dynLookupSC.setIsSybase(commonLibBO.returnIsSybase());
		dynLookupSC.setQryId(StringUtil.isNotEmpty(paramVal) ? argsList.get(i).getQUERY_ID().toString() : argsList.get(i)
			.getQUERY_ID_DEFAULT().toString());
		dynLookupSC.setConnId(repCO.getCONN_ID());
		if(StringUtil.isNotEmpty(paramVal))
		{
		   dynLookupSC.setArgId(argObj.getARGUMENT_ID());
		   dynLookupSC.setReportId(argObj.getREPORT_ID());
		   dynLookupSC.setArgVal(paramVal);
		}
		dynLookupSC.setHmQryParam(hmQryParam);
		dynLookupSC.setNbRec(1);
		dynLookupSC.setRecToskip(0);
		dynLookupSC.setCompCode(sessionCO.getCompanyCode());
	    dynLookupSC.setBranchCode(sessionCO.getBranchCode());
	    dynLookupSC.setUserId(sessionCO.getUserName());
	    dynLookupSC.setCurrAppName(sessionCO.getCurrentAppName());
		    ArrayList<LinkedHashMap> dfltValqry = commonLookupBO.returnQryResult(dynLookupSC);
		   
		    return dfltValqry;
	    }
	}
	}
	catch(Exception e)
	{
	 handleException(e,null,null);   
	}
	return null;
    }
    
    /**
     * Method that fills additionnal properties for paramVO
     * 
     * @param argType
     * @param paramVO
     * @param constrCO
     * @return
     */
    public DynamicParamsVO fillAdditionalProps(String argType, DynamicParamsVO paramVO,
	    IRP_REP_ARG_CONSTRAINTSCO constrCO, HashMap<String, HashMap<Integer, Object>> argFunctionMap,
	    String argName, String idSched)
    {
	DynamicParamsVO lParamVO = paramVO;
	if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argType))
	{
	    if(!StringUtil.nullToEmpty(constrCO.getMAX_LENGTH()).isEmpty())
	    {
		lParamVO.setMaxLength(Integer.valueOf(constrCO.getMAX_LENGTH()));
	    }
	    if(!StringUtil.nullToEmpty(constrCO.getMAX_VAL()).isEmpty())
	    {
		lParamVO.setMaxValue(new BigDecimal(constrCO.getMAX_VAL()));
	    }
	    if(!StringUtil.nullToEmpty(constrCO.getMIN_VAL()).isEmpty())
	    {
		lParamVO.setMinValue(new BigDecimal(constrCO.getMIN_VAL()));
	    }
	    if(StringUtil.nullToEmpty(constrCO.getFORMAT()).isEmpty())
	    {
		lParamVO.setNbFormat("0.####");
	    }
	    else
	    {
		lParamVO.setNbFormat(constrCO.getFORMAT());
	    }
	}
	else if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(argType))
	{
	    if(!StringUtil.nullToEmpty(constrCO.getCASE_SENSITIVITY()).isEmpty())
	    {
		lParamVO.setLowerCase((constrCO.getCASE_SENSITIVITY().equals("1")) ? false : true);
		lParamVO.setUpperCase((constrCO.getCASE_SENSITIVITY().equals("1")) ? true : false);
	    }
	    if(!StringUtil.nullToEmpty(constrCO.getMAX_LENGTH()).isEmpty())
	    {
		lParamVO.setMaxLength(Integer.valueOf(constrCO.getMAX_LENGTH()));
	    }
	    if(!StringUtil.nullToEmpty(constrCO.getFORMAT()).isEmpty())
	    {
		lParamVO.setTxtFormat(constrCO.getFORMAT());
	    }
	}
	if(argFunctionMap.get(argName) != null)
	{
	    StringBuffer onChangeContent = new StringBuffer();
	    // comparison
	    if(argFunctionMap.get(argName).get(0) != null)
	    {
		onChangeContent.append("onChangeComparisonRep('" + lParamVO.getName() + "','"+idSched+"','"+htmlPageRef+"')");
	    }
	    // has hide or show expressions
	    if(argFunctionMap.get(argName).get(1) != null || argFunctionMap.get(argName).get(2) != null)
	    {
		onChangeContent.append(";onChangeShowHideRep('" + lParamVO.getName() + "','"+idSched+"','"+htmlPageRef+"')");
	    }
	    lParamVO.setOnchange(onChangeContent.toString());
	}
	return lParamVO;
    }

    public void fillFormElement()
    {
	IRP_AD_HOC_REPORTCO repCO = new IRP_AD_HOC_REPORTCO();
	try
	{
	    //Map containing the hidden elements related to the multi liveSearch and will be looped 
	    HashMap <String,DynamicParamsVO> multiHiddenMap = new HashMap <String, DynamicParamsVO>();
	    SessionCO sessionCO = returnSessionObject();
	    IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	    BigDecimal repId = getCode();
	    String isSelRow = getUpdate();
	    HashMap schedParamValMap = new HashMap();

	    BigDecimal db = BigDecimal.ZERO;

	    boolean fromSched = false;
	    String idSched="";
	    List<IRP_REP_ARGUMENTSCO> argumentsList = new ArrayList<IRP_REP_ARGUMENTSCO>();

	    if(menu == null)// called from scheduler section
	    {
		fromSched = true;
		idSched=RepConstantsCommon.SCHED_CONST_ID;
		reportSC.setREPORT_ID(repId);
		db = BigDecimal.ZERO;

		if(repId.equals(new BigDecimal(-1)))
		{
		    repCO = null;
		    menu = "relooadD00";
		}
		else
		{
		    try
		    {		
			String[] arr=null;
			if(updates!=null)
			{
			    arr = updates.split(",");
			}
			//added for scheduler fcr dynamic reports
			if(arr!=null && arr[0].endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
			{
			    menu=arr[0];
			    //fill the repId to get the sched params values //if(isSelRow != null)
			    repId=new BigDecimal(arr[1]);
			    repCO = designerBO.returnReportById(repId);
			    htmlPageRef=repCO.getPROG_REF();
			    
			}
			//end added	
			else
			{
			    repCO = designerBO.returnReportById(repId);			    
			    menu = repCO.getPROG_REF();
			}
			argumentsList = new ArrayList(repCO.getArgumentsList().values());
			//stored to be used onChageComparison and onChangeShowHide
			ReportingSessionCO repSessionCO = returnReportingSessionObject(ConstantsCommon.SCHED_PROG_REF);
			repSessionCO.setReportCO(repCO);
		    }
		    catch(Exception e)
		    {
			log.error(e, "Error retrieving the report having id " +repId +" from db: " + e.getMessage());
		    }
		}

		// get the values of each row(when selecting a row in the
		// 'schedule report' grid)
		if(isSelRow != null)
		{
		    IRP_REPORT_SCHED_PARAMSSC paramSC = new IRP_REPORT_SCHED_PARAMSSC();
		    paramSC.setREPORT_ID(repId);
		    paramSC.setSCHED_ID(getSchedId());
		    if(report_ref.isEmpty())
		    {
			setReport_ref(ConstantsCommon.ZERO);
		    }
		    paramSC.setREPORT_REF(report_ref);

		    schedParamValMap = schedulerBO.retSchedParamValues(paramSC);
		}

	    }
	    // stoped by haytham.k for fcr reports
	    // else if (!menu.endsWith("DY0")){
	    else
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(menu);
		repCO = repSessionCO.getReportCO();
		db = repCO.getCONN_ID() == null ? BigDecimal.ZERO : repCO.getCONN_ID();

		argumentsList = new ArrayList(repCO.getArgumentsList().values());
	    }
	    
	    int argPerRow=4;
	    if(!fromSched)
	    {
		BigDecimal globalArgPerRow = commonLibBO.returnPthCtrl1().getNBR_DISPLAYED_REP_ARG_PER_ROW();
		BigDecimal repArgPerRow = repCO.getNBR_DISPLAYED_ARG_PER_ROW();
		if(NumberUtil.nullToZero(repArgPerRow).intValue() > 0)
		{
		    argPerRow = repArgPerRow.intValue();
		}
		else if(NumberUtil.nullToZero(globalArgPerRow).intValue() > 0)
		{
		    argPerRow = globalArgPerRow.intValue();
		}
	    }

	    ReportingSessionCO fileExpImpSessionCO = returnReportingSessionObject(ConstantsCommon.FILE_EXP_IMP_PROG_REF);
	    HashMap<String, Object> fileExpImpParamsMap = new HashMap<String, Object>();
	    boolean expImpParamsExist = false;
	    if(BigDecimal.ONE.equals(fromFileExpImp) && fileExpImpSessionCO.getFileExpImpParamsMap().get(menu) != null)
	    {
		fileExpImpParamsMap = fileExpImpSessionCO.getFileExpImpParamsMap().get(menu);
		expImpParamsExist = true;
	    }
	    if(htmlPageRef == null)
	    {
		htmlPageRef = menu.replace("-", "_");
	    }
	    int count = 0;
	    String argType;
	    BigDecimal argSource;
	    Object argValue = new Object();

	    ArrayList<DynamicParamsVO> formLst = new ArrayList<DynamicParamsVO>();
	    DynamicParamsVO paramVO;
	    IRP_REPORT_SCHED_PARAMSCO schedCO;
	    int paramCnt = 0;
	    String valStr;
	    String defaultColDescValue ="";
	    List<DynamicParamsVO> hiddenSchedParam = new ArrayList<DynamicParamsVO>();
	    
	    //to store the arguments that are dependent on a specific argument
	    ArrayList<IRP_REP_ARGUMENTSVO> argDepList;
	    IRP_REP_ARGUMENTSVO argDep;
	    StringBuffer argDepId;
	    StringBuffer dependency;
	    String sessionParamName;
	    IRP_REP_ARG_CONSTRAINTSCO constrCO;
	    //below map has argument name as key and value hashmap with 3 possible entries
	    //0=>comparison,1=>show,2=>hide
	    HashMap<String,HashMap<Integer,Object>> argFunctionMap = new HashMap<String, HashMap<Integer,Object>>(); 
	    fillArgFunctionMap(argFunctionMap,argumentsList);
	    	    
	    BTR_CONTROLVO btrCtrl= new BTR_CONTROLVO();
	    btrCtrl.setCOMP_CODE(sessionCO.getCompanyCode());
	    btrCtrl= controlRecordBO.retBtrCtrVO(btrCtrl);
	    StringBuffer sessTransOnChangeSb=new StringBuffer();
	    StringBuffer repLangParamsIdSb = new StringBuffer();
	    StringBuffer repLangParamsNameSb = new StringBuffer();
	    String sessTransAr="";
	    String sessTransEn="";
	    CURRENCIESVOKey currVOKey;
	    CURRENCIESVO curVO=null;
	    BRANCHESVOKey brVOKey;
	    BRANCHESVO brVO=null;
	    int sessTransCnt=0;
	    HashMap<String,String> refreshHm = new HashMap<String, String>();
	    List<IRP_QUERY_ARG_MAPPINGCO> argsMapCOList;
	    if(repCO != null)
	    {
		for(Entry<String, List<IRP_QUERY_ARG_MAPPINGCO>> entry : repCO.getLinkQryArsMap().entrySet())
		{
		    argsMapCOList = entry.getValue();
		    for(int i = 0; i < argsMapCOList.size(); i++)
		    {
			refreshHm.put(argsMapCOList.get(i).getIrpQueryArgsMappingVO().getREPORT_MAPPED_ARG_NAME(),
				ConstantsCommon.TRUE);
		    }
		}
	    }
	    HashMap<BigDecimal, String> ArgsFilter = new HashMap<BigDecimal, String>();
	    BigDecimal defaultFilter = null;
	    if(!fromSched)
	    {
		IRP_REP_FILTERSSC filterSC = new IRP_REP_FILTERSSC();
		filterSC.setREPORT_ID(repCO.getREPORT_ID());
		HashMap<String, Object> filtersSCMap = PathPropertyUtil.convertToMap(filterSC);

		IRP_REP_ARGUMENTS_FILTERSVO argFilterVO;
		try
		{
		    defaultFilter = commonReportingBO.selectDefaultFilter(filtersSCMap);
		}
		catch(Exception e)
		{
		    log.error(e, "Error retrieving the default filter " + e.getMessage());

		}

		if(defaultFilter != null)
		{
		    filterSC.setFILTER_ID(defaultFilter);
		    filtersSCMap = PathPropertyUtil.convertToMap(filterSC);
		    List<HashMap<String, Object>> filterArgslistMap = null;
		    try
		    {
			filterArgslistMap = commonReportingBO.retFilterArgumentsValues(filtersSCMap);
		    }
		    catch(Exception e)
		    {
			log.error(e, "Error retrieving the filter arguments " + e.getMessage());

		    }
		    String[] propsArr = (String[]) ConstantsCommon.retFiltersArgsListMap_PROPS.toArray();
		    HashMap<String, Object> retMap;
		    for(int i = 0; i < filterArgslistMap.size(); i++)
		    {
			retMap = filterArgslistMap.get(i);
			argFilterVO = new IRP_REP_ARGUMENTS_FILTERSVO();
			PathPropertyUtil.copyProperties(retMap, argFilterVO, false, propsArr);
			ArgsFilter.put(argFilterVO.getARGUMENT_ID(), argFilterVO.getARGUMENT_VALUE());
		    }
		}
	    }
	    for(IRP_REP_ARGUMENTSCO argObj : argumentsList)
	    {
		argSource = argObj.getARGUMENT_SOURCE();
		String valFromFilter = null;
		if(!fromSched)
		{
		    valFromFilter = ArgsFilter.get(argObj.getARGUMENT_ID());
		    if(valFromFilter != null)
		    {
			// if liveSearch with multi select
			if((new BigDecimal("3").equals(argSource) || new BigDecimal("10").equals(argSource)) && BigDecimal.ONE.equals(argObj.getMULTISELECT_YN()))
			{
			    argObj.setARGUMENT_VALUE(valFromFilter.split(",").length + ConstantsCommon.P_SELECTED);
			}
			else
			{
			    argObj.setARGUMENT_VALUE(valFromFilter);
			}
		    }
		}
		constrCO = argObj.getIrpRepArgConstraintCO();
		// later - check if timesUsed >0 then create it
		paramVO = new DynamicParamsVO();
		//removed min-width:120px;max-width:120px because the label was not well displayed under IE9
		paramVO.setCssStyle("width:120px;");
		String paramLblTrans = returnKeyTrans(argObj.getARGUMENT_NAME(), repCO.getAPP_NAME(),repCO.getPROG_REF());
		if(paramLblTrans != null && paramLblTrans.equals(argObj.getARGUMENT_NAME()))
		{
		    paramVO.setLabel(returnKeyTrans(argObj.getARGUMENT_LABEL(), repCO.getAPP_NAME(),repCO.getPROG_REF()));
		}
		else
		{
		    paramVO.setLabel(paramLblTrans);
		}
		paramVO.setLabelKeyCode(argObj.getARGUMENT_NAME());
		paramVO.setName(ConstantsCommon.PARAM_TILDA + argObj.getARGUMENT_NAME() + "~" + argObj.getARGUMENT_TYPE());
		if(refreshHm.get(argObj.getARGUMENT_NAME()) != null
			&& RepConstantsCommon.Y.equals(argObj.getDISPLAY_FLAG())
			&& !(ConstantsCommon.REP_ARG_TYPE_QRY.equals(argObj.getARGUMENT_SOURCE()) && (ConstantsCommon.ARG_QRY_LIVESEARCH_WITHOUT_DESC
				.equals(argObj.getARG_QUERY_OPTIONS()) || ConstantsCommon.ARG_QRY_LIVESEARCH_WITH_DESC
				.equals(argObj.getARG_QUERY_OPTIONS()))))
		{
		    paramVO.setOnchange("onChangeRefreshRep('" + paramVO.getName() + "','"+idSched+"','"
			    +htmlPageRef+"')");
		}
		// paramVO.setId("p_"+paramVO.getName()); //changed in order to
		// catch the id in the preview section of the report designer
		// asuccar- 23/05/2013: change the id value to show the date
		// picker
		paramVO.setId("p_" + "param_"+idSched + argObj.getARGUMENT_NAME() + "_" + argObj.getARGUMENT_TYPE() + "_"
			+ htmlPageRef);
		HashMap<BigDecimal, String> localMap = new HashMap<BigDecimal, String>();
		localMap.put(BigDecimal.valueOf(0), paramVO.getId());
		repCO.getArgShowHideMap().put(paramVO.getName(), localMap);
		argType = argObj.getARGUMENT_TYPE();

		//if display_flag=No
		if(argObj.getDISPLAY_FLAG().equals("N"))
		{
		    paramVO.setElement_type(HIDDEN_ELEMENT);
		    if(argSource.equals(ConstantsCommon.REP_LANG_ARG_SOURCE))
		    {
			if(repLangParamsIdSb.length() > 0)
			{
			    repLangParamsIdSb.append("," + paramVO.getId());
			    repLangParamsNameSb.append("," + paramVO.getName());
			}
			else
			{
			    repLangParamsIdSb.append(paramVO.getId());
			    repLangParamsNameSb.append(paramVO.getName());
			}
		    }

		}
		//5=>report reference no constraints
		else if(argSource.equals(new BigDecimal("5")))
		{
		    paramVO.setElement_dataType(DT_STRING);
		    paramVO.setElement_type(TEXTFIELD_ELEMENT);
		}
		//4=>flag txtarea in constraints
		else if(argSource.equals(new BigDecimal("4")))
		{
		    //changing the name of the argument if checkbox
		    repCO.getArgShowHideMap().remove(paramVO.getName());
		    HashMap<BigDecimal, String> lMap = new HashMap<BigDecimal, String>();
		    lMap.put(BigDecimal.valueOf(0), paramVO.getId());
		    repCO.getArgShowHideMap().put("__checkbox_paramsFlag."+argObj.getARGUMENT_NAME()+"_Y_N", lMap);
		    
		    //.get(paramVO.getName()).put(BigDecimal.valueOf(0),
			//    "lookuptxt_" + paramVO.getId());
		    paramVO.setElement_dataType(DT_STRING);
		    paramVO.setElement_type(CHKBOX_ELEMENT);
		    paramVO = fillAdditionalProps(argType, paramVO, constrCO, argFunctionMap,
			    argObj.getARGUMENT_NAME(), idSched);
		}
		//3=>query txtarea in constraints
		else if(argSource.equals(new BigDecimal("3")) || argSource.equals(new BigDecimal("10")))
		{
		    if (ConstantsCommon.ARG_QRY_COMBO.equals(argObj.getARG_QUERY_OPTIONS()))
		    {
			paramVO.setElement_type(COMBO_ELEMENT);
			ListParamVO lstCmbParamVO = new ListParamVO();
			if(NumberUtil.isEmptyDecimal(argObj.getQUERY_ID_DEFAULT()))
			{
			    combosLists.put(argObj.getARGUMENT_NAME(), (ArrayList<LinkedHashMap>) retQryCombo(argObj
				    .getQUERY_ID(), repCO, argObj));
			}
			else
			{
			    combosLists.put(argObj.getARGUMENT_NAME(), (ArrayList<LinkedHashMap>) retQryCombo(argObj
				    .getQUERY_ID_DEFAULT(), repCO, argObj));
			}
			lstCmbParamVO.setValueList("combosLists." + argObj.getARGUMENT_NAME());
			lstCmbParamVO.setKey(argObj.getCOLUMN_NAME());
			lstCmbParamVO.setValue(argObj.getCOLUMN_DESC());
			paramVO.setElement_dataType(DT_STRING);
			paramVO.setListParamVO(lstCmbParamVO);
			paramVO.setLabelId(argObj.getARGUMENT_LABEL() + "_" + menu);	
			repCO.getArgComboMap().put(paramVO.getId(),"");
		    }
		    else
		    {
		    paramVO.setElement_type(LIVESEARCH_ELEMENT);
		    //checking if multiselect livesearch
		    if(BigDecimal.ONE.equals(argObj.getMULTISELECT_YN()))
		    {
			// adding a hidden to the form next to the livesearch
			DynamicParamsVO multiHiddenVO = new DynamicParamsVO();
			multiHiddenVO.setElement_type(HIDDEN_ELEMENT);
			multiHiddenVO.setName(ConstantsCommon.PARAM_H+"~" + argObj.getARGUMENT_NAME() + "~" + argObj.getARGUMENT_TYPE());
			multiHiddenVO.setId(ConstantsCommon.P_PARAM_UNDERSCORE + idSched + argObj.getARGUMENT_NAME() + "_" + argObj.getARGUMENT_TYPE()
				+ "_" + htmlPageRef);
			multiHiddenVO.setColumn(1);
			multiHiddenVO.setReadOnly(ConstantsCommon.TRUE);
			StringBuffer sb = new StringBuffer();
			if(!fromSched)
			{
			    valFromFilter = StringUtil.nullToEmpty(ArgsFilter.get(argObj.getARGUMENT_ID()));
			    if(!valFromFilter.isEmpty())
			    {
				String[] valArray = valFromFilter.split(",");
				for(int j = 0; j < valArray.length; j++)
				{
				    if(sb.length() > 0)
				    {
					sb.append(",");
				    }
				    if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(argObj.getARGUMENT_TYPE()))
				    {
					// if string contains ""
					valArray[j] = valArray[j].replace("\"\"", "\\\"");
					sb.append("{\"" + argObj.getCOLUMN_NAME() + "\":" + valArray[j].trim() + "}");
				    }
				    else
				    {
					    sb.append("{\"" + argObj.getCOLUMN_NAME() + "\":\"" + valArray[j].trim() + "\"}");
				    }
				}
				multiHiddenVO.setValue(ConstantsCommon.MULTI_P_ROOT + sb.toString() + "]}");
			    }
			}
			multiHiddenMap.put(multiHiddenVO.getName(),multiHiddenVO);
			paramVO.setMultiSelect(ConstantsCommon.TRUE);
			paramVO.setMultiResultInput(multiHiddenVO.getId());
			paramVO.setSelectColumn(argObj.getCOLUMN_NAME());
			String name = paramVO.getName();
			HashMap<BigDecimal, String> map = repCO.getArgShowHideMap().get(name);
			repCO.getArgShowHideMap().remove(name);
			//removing param~ to not take it into consideration on retrieve
			paramVO.setName(name.substring(name.indexOf("~") + 1, name.length()));
			repCO.getArgShowHideMap().put(paramVO.getName(), map);
		    }
		    // if it's livesearch adjust the id (lookuptxt_) in
		    // ArgShowHideMap
		    repCO.getArgShowHideMap().get(paramVO.getName()).put(BigDecimal.valueOf(0),
			    "lookuptxt_" + paramVO.getId());
		    if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argType)&&!BigDecimal.ONE.equals(argObj.getMULTISELECT_YN()))
		    {
			paramVO.setMode(ConstantsCommon.COLUMN_NUMBER_TYPE);
		    }
		    // paramVO.setId(""+paramCnt);

		    // if the type = 3(arg type = query)
		    ArrayList<IRP_REP_ARGUMENTSCO> argsList = new ArrayList<IRP_REP_ARGUMENTSCO>();
		    LinkedHashMap argsMap = repCO.getArgumentsList();
		    argsList = new ArrayList(argsMap.values());
		    List<IRP_QUERY_ARG_MAPPINGCO> list = new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
		    StringBuffer queryParam = new StringBuffer("");
		    // return the arguments of the query linked to the report's
		    // arguments
		    // to set the type of the report's arguments
		    for(int i = 0; i < argsList.size(); i++)
		    {
			reportSC.setREPORT_ID(repCO.getREPORT_ID());
			if(!NumberUtil.isEmptyDecimal(argsList.get(i).getQUERY_ID())&&(argsList.get(i).getARGUMENT_ID().equals(argObj.getARGUMENT_ID())))
			{
			    reportSC.setReportArgumentId(argsList.get(i).getARGUMENT_ID());
			    reportSC.setDefaultSrc(BigDecimal.ONE);
			    list = designerBO.retQryArgMapping(reportSC);
			    try
			    {
				IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(argsList.get(i).getQUERY_ID(), true);

				for(int j = 0; j < query.getSqlObject().getArgumentsList().size(); j++)
				{
				    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();
				    arguments = query.getSqlObject().getArgumentsList();
				    ArrayList<IRP_REP_ARGUMENTSCO> argsQryList = new ArrayList<IRP_REP_ARGUMENTSCO>();
				    argsQryList = new ArrayList(arguments.values());

				    for(int m = 0; m < list.size(); m++)
				    {
					for(int n = 0; n < argsQryList.size(); n++)
					{
					    if(list.get(m).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME().equals(
						    argsQryList.get(n).getARGUMENT_NAME()))
					    {
						list.get(m).setARGUMENT_TYPE(argsQryList.get(n).getARGUMENT_TYPE());
					    }
					}
				    }

				}

			    }
			    catch(ClassNotFoundException e)
			    {
				log.error(e, "error in mapping to live search on report having id: "+repCO.getREPORT_ID() +", reference: "+repCO.getPROG_REF() + " and application: "+repCO.getAPP_NAME());
			    }
			    catch(IOException e)
			    {
				log.error(e, "error in mapping to live search on report having id: "+repCO.getREPORT_ID() +", reference: "+repCO.getPROG_REF() + " and application: "+repCO.getAPP_NAME());
			    }

			    // construct the paramList (query arguments) to send
			    // it to the livesearch action
			    StringBuffer buf = new StringBuffer();
			    for(int s = 0; s < list.size(); s++)
			    {
				// add an additional parameter for each argument that includes the type of the argument.
				buf.append(list.get(s).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME())
				   .append("@TYPE:")
				   .append(list.get(s).getARGUMENT_TYPE()).append(",");
				 
				if(list.get(s).getIrpQueryArgsMappingVO().getSTATIC_VALUE() == null
					|| ("").equals(list.get(s).getIrpQueryArgsMappingVO().getSTATIC_VALUE()))
				{
				    // if not static then append the value of
				    // the linked report's arg
				    buf.append(list.get(s).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME())
					    .append(
						    (new BigDecimal(3).equals(list.get(s).getArgumentCO()
							    .getARGUMENT_SOURCE()) || new BigDecimal(10).equals(list.get(s).getArgumentCO()
									    .getARGUMENT_SOURCE())) ? ":lookuptxt_p_" : ":p_").append(
						    "param_"+idSched).append(
						    list.get(s).getIrpQueryArgsMappingVO().getREPORT_MAPPED_ARG_NAME())
					    .append("_").append(list.get(s).getARGUMENT_TYPE()).append("_")
					    .append(htmlPageRef).append(",");
				}
				else
				{ // if query's arg value is static
				    buf.append(list.get(s).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME()).append(":")
					    .append(list.get(s).getIrpQueryArgsMappingVO().getSTATIC_VALUE() + "-")
					    .append(list.get(s).getARGUMENT_TYPE()).append(",");
				}
			    }

			    queryParam.append(buf);

			}
		    }
		    
		    //connection DDL does not exist in scheduler
		    if(!fromSched) 
		    {
			queryParam.append("conId:connection_id_" + htmlPageRef);
		    }

		    if(queryParam.toString().endsWith(","))
		    {
			queryParam = new StringBuffer(queryParam.substring(0, queryParam.length() - 1));
		    }
		    paramVO.setParamList(queryParam.toString());
		    paramVO.setActionName("/path/repCommon/dynLkupAction_constructQryLookup?qryId="
			    + argObj.getQUERY_ID() + "&repApp=" + repCO.getAPP_NAME() + "&repRef="
			    + repCO.getPROG_REF());// +"&queryStr="+queryStr
		    
		    paramVO.setSearchElement(argObj.getCOLUMN_NAME());
		    
		    //annasuccar-05/08/2014: return the report arguments dependent on this one
		    reportSC.setReportArgumentId(argObj.getARGUMENT_ID());
		    reportSC.setReportArgumentName(argObj.getARGUMENT_NAME());
		    argDepList = (ArrayList<IRP_REP_ARGUMENTSVO>) designerBO.retRepArgDepList(reportSC);
		    dependency = new StringBuffer();
		    argDepId = new StringBuffer();
		    for( int a = 0; a < argDepList.size(); a++ )
		    {
			argDep = argDepList.get(a);
			if( new BigDecimal(3).equals(argDep.getARGUMENT_SOURCE()) || new BigDecimal(10).equals(argDep.getARGUMENT_SOURCE()) )
			{
			    argDepId.append("lookuptxt_");
			}
			argDepId.append("p_").append("param_"+idSched).append(argDep.getARGUMENT_NAME()).append("_").append(argDep.getARGUMENT_TYPE()).append("_")
			.append(htmlPageRef);
			
			if (a > 0)
			{
			    dependency.append(",");
			}
			dependency.append(argDepId.toString()).append(": ''");
		    }
		
		    // remove the dependency conditions because there should be
		    // always dependency
		    if(!BigDecimal.ONE.equals(argObj.getMULTISELECT_YN()))
		    {
			paramVO = addParamsLiveSearch(argumentsList, idSched, paramVO);
			paramVO.setDependency(dependency.toString());
			if((StringUtil.nullToEmpty(paramVO.getDependency())).isEmpty())
			{
			    paramVO.setDependency("lookuptxt_" + paramVO.getId() + ":" + paramVO.getName());
			}
			StringBuffer dependencySrc = new StringBuffer(
				"/path/repCommon/reportAction_applyArgsDependency?qryId=" + argObj.getQUERY_ID()
				+ "&argId=" + argObj.getARGUMENT_ID());
			if(StringUtil.isNotEmpty(idSched))
			{
			    dependencySrc.append("&fromMenu=" + ConstantsCommon.NO);
			}
			paramVO.setDependencySrc(dependencySrc.toString());
		    }
		    if (RepConstantsCommon.ARG_QRY_LIVESEARCH_WITH_DESC.equals(argObj.getARG_QUERY_OPTIONS()))
		    {
			paramVO.setResultList(argObj.getCOLUMN_DESC()+":"+"p_" + "desc_"+idSched+ argObj.getARGUMENT_NAME()+"_" + htmlPageRef);
		    }
		    }
		}
		else if(argSource.equals(ConstantsCommon.REP_LANG_ARG_SOURCE))
		{
		    paramVO.setElement_dataType(DT_STRING);
		    paramVO.setElement_type(TEXTFIELD_ELEMENT);
		    paramVO.setReadOnly(ConstantsCommon.TRUE);
		    if(repLangParamsIdSb.length()>0)
		    {
			repLangParamsIdSb.append(","+paramVO.getId());
			repLangParamsNameSb.append(","+paramVO.getName());
		    }
		    else
		    {
			repLangParamsIdSb.append(paramVO.getId());
			repLangParamsNameSb.append(paramVO.getName());
		    }
		}
		else if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argType))
		{
		    paramVO.setElement_dataType(DT_NUMBER);
		    paramVO.setElement_type(TEXTFIELD_ELEMENT);
		    paramVO = fillAdditionalProps(argType, paramVO, constrCO,argFunctionMap,argObj.getARGUMENT_NAME(),idSched);
		}
		else if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(argType))
		{
		    paramVO.setElement_dataType(DT_STRING);
		    paramVO.setElement_type(TEXTFIELD_ELEMENT);
		    paramVO = fillAdditionalProps(argType, paramVO, constrCO,argFunctionMap,argObj.getARGUMENT_NAME(),idSched);
		}
		else if(ConstantsCommon.PARAM_TYPE_DATE.equals(argType))
		{
		    paramVO.setElement_dataType(DT_DATE);
		    paramVO.setElement_type(DATE_ELEMENT);
		    paramVO = fillAdditionalProps(argType, paramVO, constrCO,argFunctionMap,argObj.getARGUMENT_NAME(),idSched);
		}
		else if(ConstantsCommon.datetime.equals(argType))
		{
		    /*if schedDate parameter or system date - if it is called from
		     designer or menu , the schedDate will be shown as datePicker*/
		    if((BigDecimal.valueOf(6).equals(argObj.getARGUMENT_SOURCE()) &&  fromSched)
			    || BigDecimal.valueOf(7).equals(argObj.getARGUMENT_SOURCE()))
		    {
			paramVO.setElement_dataType(DT_STRING);
			paramVO.setElement_type(TEXTFIELD_ELEMENT);
			paramVO.setReadOnly(ConstantsCommon.TRUE);
		    }
		    else
		    {
			paramVO.setElement_dataType(DT_DATE);
			paramVO.setElement_type(DATE_ELEMENT);
			paramVO.setDateWithTime("true");
			paramVO.setDateTimeAmPm("true");
		    }
		    paramVO = fillAdditionalProps(argType, paramVO, constrCO,argFunctionMap,argObj.getARGUMENT_NAME(),idSched);
		}

		if(isSelRow == null)
		{
		     Object fileExpImpParamVal=null;
		     if(expImpParamsExist)
		     {
			 fileExpImpParamVal=fileExpImpParamsMap.get(argObj.getARGUMENT_NAME());
		     }
		    
		    if(argSource.equals(ConstantsCommon.REP_LANG_ARG_SOURCE))
		    {
			 if(expImpParamsExist)
			 {
			     valStr=StringUtil.nullToEmpty(fileExpImpParamVal);
			 }
			 else
			 {
			     valStr = sessionCO.getLanguage();
			 }
		    }
		    else
		    {
			valStr = argObj.getARGUMENT_VALUE();
		    }
		    if(argSource.equals(ConstantsCommon.SESSION_ARG_SOURCE) || argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE))
		    {// value from
			// session
			sessionParamName=argObj.getSESSION_ATTR_NAME();
			if(expImpParamsExist)
			{
			    if(ConstantsCommon.PARAM_TYPE_DATE.equals(argObj.getARGUMENT_TYPE()))
			    {
				valStr = DateUtil.format((Date) fileExpImpParamVal, DateUtil.DEFAULT_DATE_FORMAT);
			    }
			    else
			    {
				valStr = StringUtil.nullToEmpty(fileExpImpParamVal);
			    }
			}
			
			if(!expImpParamsExist && "".equals(StringUtil.nullToEmpty(valStr)))
			{
			    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage())
				    && ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName) )
			    {
				argValue = PathPropertyUtil.returnProperty(sessionCO, ConstantsCommon.COMP_AR_NAME_SESSION); 
			    }
			    else if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage())
				    && ConstantsCommon.BASE_CURR_NAME_EXP_VAR.equals(sessionParamName))
			    {
				currVOKey= new CURRENCIESVOKey();
				currVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
				currVOKey.setCURRENCY_CODE(sessionCO.getBaseCurrencyCode());
				curVO=commonLibBO. returnCurrency(currVOKey);
				argValue=curVO.getBRIEF_DESC_ARAB();
			    }
			    else if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage())
				    && ConstantsCommon.BRANCH_NAME_EXP_VAR.equals(sessionParamName))
			    {
				brVOKey= new BRANCHESVOKey();
				brVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
				brVOKey.setBRANCH_CODE(sessionCO.getBranchCode());
				brVO=commonLibBO. returnBranch(brVOKey);
				argValue=brVO.getBRIEF_DESC_ARAB();
			    }
			    else
			    {
				argValue = PathPropertyUtil.returnProperty(sessionCO, sessionParamName); 
			    }
				// if the sessionValue is null then use the default argument value
				if(argValue == null)
				{
				    argValue = StringUtil.nullToEmpty(valStr);
				}
				valStr=argValue.toString();
				if(ConstantsCommon.PARAM_TYPE_DATE.equals(argObj.getARGUMENT_TYPE()))
				{
				    try
				    {
					valStr = DateUtil.format(DateUtil.parseDate(valStr,
						"EEE MMM dd HH:mm:ss Z yyyy"), DateUtil.DEFAULT_DATE_FORMAT);
				    }
				    catch(Exception e)
				    {
					//log.error("Error parsing the value of a parameter of type date");
					handleException(e, "Error parsing the value of a parameter of type date on report having id: "+repCO.getREPORT_ID() +", reference: "+repCO.getPROG_REF() + " and application: "+repCO.getAPP_NAME(),
						"loadDynForm.parseDate.exceptionMsg._key");
				    }
				}
			}
			if(!fromSched && argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE) && "0".equals(repCO.getLANG_INDEPENDENT_YN()) 
				&& "".equals(StringUtil.nullToEmpty(argObj.getARGUMENT_VALUE())))
			{
			    if(ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName))
			    {
				sessTransEn=(String)PathPropertyUtil.returnProperty(sessionCO, sessionParamName); 
				sessTransAr=(String)PathPropertyUtil.returnProperty(sessionCO, ConstantsCommon.COMP_AR_NAME_SESSION); 
			    }
			    else if(ConstantsCommon.BASE_CURR_NAME_EXP_VAR.equals(sessionParamName))
			    {
				if(curVO == null)
				{
				    currVOKey = new CURRENCIESVOKey();
				    currVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
				    currVOKey.setCURRENCY_CODE(sessionCO.getBaseCurrencyCode());
				    curVO = commonLibBO.returnCurrency(currVOKey);
				}
				sessTransEn=curVO.getBRIEF_DESC_ENG();
				sessTransAr=curVO.getBRIEF_DESC_ARAB();
			    }
			    else if(ConstantsCommon.BRANCH_NAME_EXP_VAR.equals(sessionParamName))
			    {
				if(brVO == null)
				{
				    brVOKey = new BRANCHESVOKey();
				    brVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
				    brVOKey.setBRANCH_CODE(sessionCO.getBranchCode());
				    brVO = commonLibBO.returnBranch(brVOKey);
				}
				sessTransEn=brVO.getBRIEF_DESC_ENG();
				sessTransAr=brVO.getBRIEF_DESC_ARAB();
			    }
			    sessTransCnt++;
			    sessTransAr=StringUtil.nullToEmpty(sessTransAr);
			    sessTransEn=StringUtil.nullToEmpty(sessTransEn);
			    sessTransOnChangeSb.append("elt"+sessTransCnt+":{id:'"+paramVO.getId()+"',transAr:'"+sessTransAr+"',transEn:'"+sessTransEn+"'},");
			}
		    }
		    else if(argSource.equals(new BigDecimal(4))) // based on
		    // flag
		    {
			if(expImpParamsExist)
			{
			    argValue=StringUtil.nullToEmpty(fileExpImpParamVal);
			}
			else
			{
			    if(NumberUtil.isEmptyDecimal(argObj.getQUERY_ID_DEFAULT()))
				{
				    argValue = argObj.getARGUMENT_VALUE();
				}
				else
				{   ArrayList<LinkedHashMap> argValueRes = retQryDfltVal(repCO, argObj,null);
				    if (argValueRes != null && !argValueRes.isEmpty())
				    {
                			Object obj=argValueRes.get(0).get(argObj.getDEFAULT_VALUE_COL_NAME());
                			if(obj instanceof Timestamp)
                			{
                			    Timestamp ts=(Timestamp) obj;
                			    Date dt= new Date(ts.getTime());
                			    argValue=DateUtil.format(dt,DateUtil.DEFAULT_DATE_FORMAT);
                			}
                			else
                			{
                			    argValue = obj.toString();
                			}
                			if (!StringUtil.nullToEmpty(argObj.getDEFAULT_VALUE_COL_DESC()).isEmpty())
					{
					defaultColDescValue = argValueRes.get(0).get(argObj.getDEFAULT_VALUE_COL_DESC()).toString();
					}
				    }
				}
			}

			if(argValue.equals(argObj.getFLAG_VALUE_ON()))
			{
			    paramsFlag.put(argObj.getARGUMENT_NAME() + "_" + argObj.getFLAG_VALUE_ON() + "_"
				    + argObj.getFLAG_VALUE_OFF(), ConstantsCommon.TRUE);
			    valStr=ConstantsCommon.TRUE;
			    paramVO.setValue(valStr);
			}
			else
			{
			    paramsFlag.put(argObj.getARGUMENT_NAME() + "_" + argObj.getFLAG_VALUE_ON() + "_"
				    + argObj.getFLAG_VALUE_OFF(), ConstantsCommon.FALSE);
			    valStr = ConstantsCommon.FALSE;
				paramVO.setValue(valStr);
			}
			paramVO.setName("paramsFlag." + argObj.getARGUMENT_NAME() + "_" + argObj.getFLAG_VALUE_ON()
				+ "_" + argObj.getFLAG_VALUE_OFF());
		    }
		    else
		    {
			if(argSource.equals(new BigDecimal("5")))
			{
			    paramVO.setValue(menu);
			    valStr = menu;
			    // repCO.setPROG_REF(menu);
			}
			else
			{
			    if(expImpParamsExist)
			    {
				valStr=StringUtil.nullToEmpty(fileExpImpParamVal);
			    }
			    else
			    {
				    ArrayList<LinkedHashMap> argValueRes= retQryDfltVal(repCO, argObj,null);
				    String dynVal ="";
				    if (argValueRes != null && !argValueRes.isEmpty())
				    {	Object obj=argValueRes.get(0).get(argObj.getDEFAULT_VALUE_COL_NAME());
                			if(obj instanceof Timestamp)
                			{
                			    Timestamp ts=(Timestamp) obj;
                			    Date dt= new Date(ts.getTime());
                			    dynVal=DateUtil.format(dt,DateUtil.DEFAULT_DATE_FORMAT);
                			}
                			else
                			{
                			    dynVal = obj.toString();
                			}
				    
				    
                			if (!StringUtil.nullToEmpty(argObj.getDEFAULT_VALUE_COL_DESC()).isEmpty())
					{
					defaultColDescValue = argValueRes.get(0).get(argObj.getDEFAULT_VALUE_COL_DESC()).toString();
					}
				    }
				    if(!RepConstantsCommon.EMPTY_STRING.equals(dynVal))
				    {
					if(ConstantsCommon.PARAM_TYPE_DATE.equals(argType))
					{
					    dynVal = StringUtil.replaceInString(dynVal, "-", "/");
					}
					valStr = dynVal;
				    } 
			    }
			   
			}
		    }
		    if(valStr == null)
		    {
			valStr = "";
		    }

		    if(ConstantsCommon.PARAM_TYPE_DATE.equals(argType) && !valStr.isEmpty()
			    && "Y".equals(argObj.getDISPLAY_FLAG()))
		    {
			if(expImpParamsExist)
			{
			    if((BigDecimal.ONE).equals(argObj.getARGUMENT_SRC_DEFAULT()))
			    {
				valStr = DateUtil.format((Date)fileExpImpParamVal, "yyyy-MM-dd");
			    }
			    else
			    {
				valStr = DateUtil.format((Date)fileExpImpParamVal, "yyyy-MM-dd");
			    }
			}
			else
			{
			    valStr = StringUtil.replaceInString(valStr, "-", "/");
			    if((BigDecimal.ONE).equals(argObj.getARGUMENT_SRC_DEFAULT()))
			    {
				valStr = DateUtil.format(DateUtil.parseDate(valStr, DateUtil.DEFAULT_DATE_FORMAT), "yyyy-MM-dd");
			    }
			    else
			    {
				valStr = DateUtil.format(DateUtil.parseDate(valStr, DateUtil.getDatePattern(valStr)), "yyyy-MM-dd");
			    }
			}

		    }
		    if(ConstantsCommon.datetime.equals(argType) && !valStr.isEmpty()
			    && "Y".equals(argObj.getDISPLAY_FLAG()))
		    {
			if(expImpParamsExist)
			{
			    if(fileExpImpParamVal instanceof String
				    && commonLookupBO.retSysDateParamLovVal(sessionCO.getLanguage()).equals(fileExpImpParamVal))
			    {
				valStr=fileExpImpParamVal.toString();
			    }
			    else
			    {
				valStr = DateUtil.format((Timestamp)fileExpImpParamVal,"yyyy-MM-dd'T'hh:mm:ss");
			    }
			}
			else
			{
			    valStr = DateUtil.format(DateUtil.parseDate(valStr, DateUtil.getDatePattern(valStr)),
				"yyyy-MM-dd'T'HH:mm:ss");
			}
		    }
		    if(ConstantsCommon.datetime.equals(argType)
			    && BigDecimal.valueOf(6).equals(argObj.getARGUMENT_SOURCE()) && fromSched)
		    {
			valStr = commonLookupBO.retSchedDateParamLovVal(sessionCO.getLanguage());
		    }
		    else if(ConstantsCommon.datetime.equals(argType)
			    && BigDecimal.valueOf(7).equals(argObj.getARGUMENT_SOURCE()))
		    {
			valStr = commonLookupBO.retSysDateParamLovVal(sessionCO.getLanguage());
		    }

		    paramVO.setValue(valStr);

		    if(ConstantsCommon.TRUE.equals(argObj.getIrpRepArgConstraintCO().getBTR_CONTROL_DISP()) && ConstantsCommon.YES.equals(btrCtrl.getARGUMENTS_ENABLE_YN()))
		    {
		    		paramVO.setReadOnly(ConstantsCommon.TRUE);		    		
		    	
		    }
		    else if (!ConstantsCommon.TRUE.equals(argObj.getIrpRepArgConstraintCO().getBTR_CONTROL_DISP()))
		    {
		    	if(ConstantsCommon.NO.equals(argObj.getENABLE_FLAG()))	 
		    	{
		    		paramVO.setReadOnly(ConstantsCommon.TRUE);
		    	}		    	
		    	if(menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION) && ConstantsCommon.YES.equals(repCO.getSTANDARD_FLAG_YN()))
		    	{
		    		paramVO.setReadOnly(RepConstantsCommon.FALSE);
		    	}
		    }		
		}
		else
		// called from scheduler
		{
		    schedCO = (IRP_REPORT_SCHED_PARAMSCO) schedParamValMap.get(argObj.getARGUMENT_NAME());
		    if(schedCO != null)
		    {
			if(BigDecimal.ONE.equals(argObj.getMULTISELECT_YN()))
			{
			    // constructing the json string from db values
			    String valArray[] = schedCO.getPARAM_VALUE().split(",");
			    paramVO.setValue(valArray.length + ConstantsCommon.P_SELECTED);
			    StringBuffer sb;
			    DynamicParamsVO hiddenVO = multiHiddenMap.get(ConstantsCommon.PARAM_H + "~"
				    + argObj.getARGUMENT_NAME() + "~" + argObj.getARGUMENT_TYPE());
			    sb = new StringBuffer();
			    for(int j = 0; j < valArray.length; j++)
			    {
				if(sb.length() > 0)
				{
				    sb.append(",");
				}
				if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(argObj.getARGUMENT_TYPE()))
				{
				    // if string contains ""
				    valArray[j] = valArray[j].replace("\"\"", "\\\"");
				    sb.append("{\"" + paramVO.getSelectColumn() + "\":" + valArray[j].trim() + "}");
				}
				else
				{
				    sb.append("{\"" + paramVO.getSelectColumn() + "\":\"" + valArray[j].trim() + "\"}");
				}
			    }
			    hiddenVO.setValue(ConstantsCommon.MULTI_P_ROOT + sb.toString() + "]}");
			}
			else
			{
			if(ConstantsCommon.PARAM_TYPE_DATE.equals(argType))
			{
			    try
			    {
				paramVO.setValue(DateUtil.format(DateUtil.parseDate(schedCO.getPARAM_VALUE(),
					DateUtil.DEFAULT_DATE_FORMAT), "MM/dd/yyyy"));
			    }
			    catch(Exception e)
			    {
				handleException(e, "Error parsing the value of a parameter of type date on report having id: "+repCO.getREPORT_ID() +", reference: "+repCO.getPROG_REF() + " and application: "+repCO.getAPP_NAME(),
					"loadDynForm.parseDate.exceptionMsg._key");
			    }
			}
			else if(ConstantsCommon.datetime.equals(argType))
			{
			    if(BigDecimal.valueOf(6).equals(argObj.getARGUMENT_SOURCE()))
			    {
				paramVO.setValue(commonLookupBO.retSchedDateParamLovVal(sessionCO.getLanguage()));
			    }
			    else if(BigDecimal.valueOf(7).equals(argObj.getARGUMENT_SOURCE()))
			    {
				paramVO.setValue(commonLookupBO.retSysDateParamLovVal(sessionCO.getLanguage()));
			    }
			    else
			    {
				paramVO.setValue(DateUtil.format(DateUtil.parseDate(schedCO.getPARAM_VALUE(), DateUtil
					.getDatePattern(schedCO.getPARAM_VALUE())), "yyyy-MM-dd'T'HH:mm:ss"));
			    }
			}
			else if(argSource.equals(new BigDecimal(4))) // based on
			// flag
			{

			    if(NumberUtil.isEmptyDecimal(argObj.getQUERY_ID_DEFAULT()))
			    {
				argValue = schedCO.getPARAM_VALUE();
			    }
			    else
			    {
				  ArrayList<LinkedHashMap> argValueRes = retQryDfltVal(repCO, argObj,null);
				  if (argValueRes != null && !argValueRes.isEmpty())
				    {
					Object obj=argValueRes.get(0).get(argObj.getDEFAULT_VALUE_COL_NAME());
                			if(obj instanceof Timestamp)
                			{
                			    Timestamp ts=(Timestamp) obj;
                			    Date dt= new Date(ts.getTime());
                			    argValue=DateUtil.format(dt,DateUtil.DEFAULT_DATE_FORMAT);
                			}
                			else
                			{
                			    argValue = obj.toString();
                			}
                			if (!StringUtil.nullToEmpty(argObj.getDEFAULT_VALUE_COL_DESC()).isEmpty())
					{
					defaultColDescValue = argValueRes.get(0).get(argObj.getDEFAULT_VALUE_COL_DESC()).toString();
					}
				    }
			    }

			    if(argValue.equals(argObj.getFLAG_VALUE_ON()))
			    {
				paramsFlag.put(argObj.getARGUMENT_NAME() + "_" + argObj.getFLAG_VALUE_ON() + "_"
					+ argObj.getFLAG_VALUE_OFF(), "true");
				valStr = ConstantsCommon.TRUE;
				 paramVO.setValue(valStr);
			    }
			    else
			    {
				paramsFlag.put(argObj.getARGUMENT_NAME() + "_" + argObj.getFLAG_VALUE_ON() + "_"
					+ argObj.getFLAG_VALUE_OFF(), "false");
				valStr = ConstantsCommon.FALSE;
				 paramVO.setValue(valStr);
			    }
			    paramVO.setName("paramsFlag." + argObj.getARGUMENT_NAME() + "_" + argObj.getFLAG_VALUE_ON()
				    + "_" + argObj.getFLAG_VALUE_OFF());
			}
			    else if(argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE))
			    {
				sessionParamName = argObj.getSESSION_ATTR_NAME();
				if(ConstantsCommon.BASE_CURR_NAME_EXP_VAR.equals(sessionParamName))
				{
				    // get the currency details
				    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage()))
				    {
					currVOKey = new CURRENCIESVOKey();
					currVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
					currVOKey.setCURRENCY_CODE(sessionCO.getBaseCurrencyCode());
					curVO = commonLibBO.returnCurrency(currVOKey);
					sessTransAr = curVO.getBRIEF_DESC_ARAB();
					paramVO.setValue(sessTransAr);
				    }
				    else
				    {
					sessTransEn = (String) PathPropertyUtil.returnProperty(sessionCO,
						sessionParamName);
					paramVO.setValue(sessTransEn);
				    }
				}
				else if(ConstantsCommon.BRANCH_NAME_EXP_VAR.equals(sessionParamName))
				{
				    // get the branch details
				    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage()))
				    {
					brVOKey = new BRANCHESVOKey();
					brVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
					brVOKey.setBRANCH_CODE(sessionCO.getBranchCode());
					brVO = commonLibBO.returnBranch(brVOKey);
					sessTransAr = brVO.getBRIEF_DESC_ARAB();
					paramVO.setValue(sessTransAr);
				    }
				    else
				    {
					sessTransEn = (String) PathPropertyUtil.returnProperty(sessionCO,
						sessionParamName);
					paramVO.setValue(sessTransEn);
				    }
				}
				else if(ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName))
				{
				    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage()))
				    {
					sessTransAr = (String) PathPropertyUtil.returnProperty(sessionCO,
						ConstantsCommon.COMP_AR_NAME_SESSION);
					paramVO.setValue(sessTransAr);
				    }
				    else
				    {
					sessTransEn = (String) PathPropertyUtil.returnProperty(sessionCO,
						sessionParamName);
					paramVO.setValue(sessTransEn);
				    }
				}
			    }
			    else
			    {
				if(ConstantsCommon.ARG_QRY_COMBO.equals(argObj.getARG_QUERY_OPTIONS()))
				{
				    combosValues.put(argObj.getARGUMENT_NAME(), schedCO.getPARAM_VALUE());
				    paramVO.setName("combosValues." + argObj.getARGUMENT_NAME());
				}
				else
				{
				    paramVO.setValue(schedCO.getPARAM_VALUE());
				}
			}
			}
			if(ConstantsCommon.TRUE.equals(argObj.getIrpRepArgConstraintCO().getBTR_CONTROL_DISP())
				&& ConstantsCommon.YES.equals(btrCtrl.getARGUMENTS_ENABLE_YN()))
			{
			    paramVO.setReadOnly(ConstantsCommon.TRUE);

			}
			else if(!ConstantsCommon.TRUE.equals(argObj.getIrpRepArgConstraintCO().getBTR_CONTROL_DISP()))
			{
			    if(ConstantsCommon.NO.equals(argObj.getENABLE_FLAG()))
			    {
				paramVO.setReadOnly(ConstantsCommon.TRUE);
			    }
			    if(menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION)
				    && ConstantsCommon.YES.equals(repCO.getSTANDARD_FLAG_YN()))
			    {
				paramVO.setReadOnly(RepConstantsCommon.FALSE);
			    }
			}
		    }
		}

		paramVO.setColumn(1);
		if(repId == null)
		{
		    if(paramCnt == 0 || paramCnt == argPerRow)
		    {
			paramVO.setRow(++count);
			if(paramCnt == argPerRow)
			{
			    paramCnt = 0;
			}
		    }
		    else
		    {
			paramVO.setRow(count);
		    }
		    paramCnt++;
		    formLst.add(paramVO);
			if (argObj.getARG_QUERY_OPTIONS().equals(RepConstantsCommon.ARG_QRY_LIVESEARCH_WITH_DESC))
			{
			    DynamicParamsVO colDescVO = new DynamicParamsVO();
			    colDescVO.setCssStyle("width:240px;");
			    colDescVO.setElement_dataType(DT_STRING);
			    colDescVO.setElement_type(TEXTFIELD_ELEMENT);
			    colDescVO.setId("p_" + "desc_"+idSched + argObj.getARGUMENT_NAME()+"_" + htmlPageRef);
			    colDescVO.setReadOnly(ConstantsCommon.TRUE);
			    colDescVO.setColumn(1);
			    if( !NumberUtil.isEmptyDecimal(argObj.getQUERY_ID_DEFAULT()))
			    {
			    colDescVO.setValue(defaultColDescValue);
			    }
			    if(paramCnt == 0 || paramCnt == 4)
			    {
				colDescVO.setRow(++count);
				if(paramCnt == 4)
				{
				    paramCnt = 0;
				}
			    }
			    else
			    {
				colDescVO.setRow(count);
			    }
			    paramCnt++;
			    formLst.add(colDescVO);
			}
		}
		else
		// from sched
		{
		    // show the arg. with displayFlag='Y' first ,then fill the
		    // hidden args. in order to have a well displayed form
		    if("Y".equalsIgnoreCase(argObj.getDISPLAY_FLAG()))
		    {
			if(paramCnt == 0 || paramCnt == 2)
			{
			    paramVO.setRow(++count);
			    if(paramCnt == 2)
			    {
				paramCnt = 0;
			    }
			}
			else
			{
			    paramVO.setRow(count);
			}
			paramCnt++;
			formLst.add(paramVO);
			if (argObj.getARG_QUERY_OPTIONS().equals(RepConstantsCommon.ARG_QRY_LIVESEARCH_WITH_DESC))
			{
			    DynamicParamsVO colDescVO = new DynamicParamsVO();
			    colDescVO.setElement_dataType(DT_STRING);
			    colDescVO.setElement_type(TEXTFIELD_ELEMENT);
			    colDescVO.setId("p_" + "desc_"+idSched + argObj.getARGUMENT_NAME()+"_" + htmlPageRef);
			    colDescVO.setReadOnly(ConstantsCommon.TRUE);
			    colDescVO.setColumn(1);
			    if(!(paramVO.getValue() == null && NumberUtil.isEmptyDecimal(argObj.getQUERY_ID_DEFAULT())))
			    {
				if(paramVO.getValue() == null)
				{
				    colDescVO.setValue(defaultColDescValue);
				}
				else
				{
				    ArrayList<LinkedHashMap> result = retQryDfltVal(repCO, argObj, paramVO.getValue());
				    if(result != null && !result.isEmpty())
				    {
					colDescVO.setValue((String) result.get(0).get(argObj.getCOLUMN_DESC()));
				    }
				}
			    }
			    if(paramCnt == 0 || paramCnt == 2)
			    {
				colDescVO.setRow(++count);
				if(paramCnt == 2)
				{
				    paramCnt = 0;
				}
			    }
			    else
			    {
				colDescVO.setRow(count);
			    }
			    paramCnt++;
			    formLst.add(colDescVO);
			}
		    }
		    else
		    {
			hiddenSchedParam.add(paramVO);
		    }

		}

	    }
	    
	    DynamicParamsVO hiddenVO;
	    //skip one row
	    count++;
	    for(Entry <String,DynamicParamsVO> entry : multiHiddenMap.entrySet())
	    {
		hiddenVO = entry.getValue();
		hiddenVO.setRow(count);
		formLst.add(hiddenVO);
	    }
	    
	    // if sched , fill the hidden arguments at the end
	    DynamicParamsVO dpVO;
	    if(repId != null && !hiddenSchedParam.isEmpty())
	    {
		count++;
		for(int k = 0; k < hiddenSchedParam.size(); k++)
		{
		    dpVO = hiddenSchedParam.get(k);
		    dpVO.setRow(count);
		    formLst.add(dpVO);
		}

	    }
	    if(!fromSched) // if not scheduler then add spaces at the end of
	    // the parameters
	    {
		count = count + 10; // to add spaces between the parameters and
		// the below
	    }

	    // create the combo of choose output format
	    // if(repId==null && fromDesigner==null )
	    // {
	    if(!fromSched)// called from scheduler section
	    {

		// language DDL
		ListParamVO lstParamVO = new ListParamVO();
		DynamicParamsVO paramVO1 = new DynamicParamsVO();
		langList = setLangCombo();
		lstParamVO.setValueList("langList");
		lstParamVO.setKey("LANG_CODE");
		lstParamVO.setValue("LANG_NAME");
		paramVO1.setElement_dataType(DT_STRING);
		paramVO1.setElement_type(COMBO_ELEMENT);
		paramVO1.setId("lang_id_"+htmlPageRef);
		paramVO1.setName(ConstantsCommon.LANG_PARAM); // language
		paramVO1.setLabel("language");
		paramVO1.setLabelId("langLblId_"+menu);
		StringBuffer onChangeFuncSb = new StringBuffer();
		if(sessTransOnChangeSb.length()>0)
		{
		    String sessTransOnChangeStr=sessTransOnChangeSb.toString();
		    sessTransOnChangeStr="{"+sessTransOnChangeStr.substring(0,sessTransOnChangeStr.length()-1)+"}";
		    onChangeFuncSb.append("updateTransSessionArgsRep("+sessTransCnt+",'lang_id_"+htmlPageRef+"',"+sessTransOnChangeStr+");");
		}
		if(repLangParamsIdSb.length()>0)
		{
		    String repLangParamsIds = repLangParamsIdSb.toString();
		    String repLangParamsNames = repLangParamsNameSb.toString();
		    onChangeFuncSb.append("updateRepLangParamsRep('"+htmlPageRef+"','"+ repLangParamsIds + "','"+repLangParamsNames+"','"+idSched+"')");
		}
		if(onChangeFuncSb.length()>0)
		{
		    paramVO1.setOnchange(onChangeFuncSb.toString());
		}
		
		if("1".equals(repCO.getLANG_INDEPENDENT_YN()))
		{
		    paramVO1.setVisible("false");
		    paramVO1.setValue(ConstantsCommon.LANGUAGE_ENGLISH);
		    paramVO1.setConsiderChoiceValue(ConstantsCommon.TRUE);

		}
		else if (expImpParamsExist)
		{
		    paramVO1.setValue((String)fileExpImpParamsMap.get(ConstantsCommon.LANG_PARAM));
		    paramVO1.setConsiderChoiceValue(ConstantsCommon.TRUE);
		}
		else
		{
		    paramVO1.setValue(sessionCO.getLanguage());
		    paramVO1.setConsiderChoiceValue(ConstantsCommon.TRUE);
		}
		paramVO1.setRow(++count);
		paramVO1.setColumn(1);
		paramVO1.setListParamVO(lstParamVO);
		if("0".equals(repCO.getLANG_INDEPENDENT_YN()))
		{
		    formLst.add(paramVO1);
		}
		// report format DDL
		lstParamVO = new ListParamVO();
		paramVO = new DynamicParamsVO();
		formatList = setCombo();
		lstParamVO.setValueList("formatList");
		lstParamVO.setKey("VALUE_CODE");
		lstParamVO.setValue("VALUE_DESC");
		// paramVO.setLabel("Export To");
		paramVO.setElement_dataType(DT_STRING);
		paramVO.setElement_type(COMBO_ELEMENT);
		paramVO.setId("format_id_" + htmlPageRef);
		paramVO.setName(ConstantsCommon.FORMAT_PARAM);
		paramVO.setLabel(getText("designer.defaultFormat"));
		paramVO.setRow(count);
		paramVO.setColumn(1);
		paramVO.setValue(repCO.getDEFAULT_FORMAT());
		paramVO.setConsiderChoiceValue(ConstantsCommon.TRUE);
		paramVO.setOnchange("hideShowCsvSepartor('" + htmlPageRef + "')");
		paramVO.setListParamVO(lstParamVO);
		if(BigDecimal.ONE.equals(fromFileExpImp))
		{
		    paramVO.setReadOnly("true");
		    paramVO.setValue("XLS");
		    paramVO.setConsiderChoiceValue(ConstantsCommon.TRUE);
		}
		formLst.add(paramVO);

		// report connection DDL
		lstParamVO = new ListParamVO();
		paramVO = new DynamicParamsVO();
		connectionList = setConnectionList();
		lstParamVO.setValueList("connectionList");
		lstParamVO.setKey("CONN_ID");
		lstParamVO.setValue("CONN_DESC");
		paramVO.setElement_dataType(DT_STRING);
		paramVO.setElement_type(COMBO_ELEMENT);
		paramVO.setId("connection_id_" + htmlPageRef);
		paramVO.setName(ConstantsCommon.DB_PARAM);
		paramVO.setLabel(getText("connection.conDescription"));
		if(expImpParamsExist)
		{
		    String dbValObj=StringUtil.nullToEmpty(fileExpImpParamsMap.get(ConstantsCommon.DB_PARAM));
		    db =dbValObj.isEmpty()?BigDecimal.ZERO:new BigDecimal(dbValObj);
		}
		paramVO.setValue(db.toString());
		paramVO.setConsiderChoiceValue(ConstantsCommon.TRUE);
		paramVO.setRow(count);
		paramVO.setColumn(1);
		paramVO.setListParamVO(lstParamVO);
		formLst.add(paramVO);

		if(fromFileExpImp==null)
		{
		    	// csv separators
			lstParamVO = new ListParamVO();
			paramVO = new DynamicParamsVO();
			csvSeparatorsList = setSepartor();
			lstParamVO.setValueList("csvSeparatorsList");
			lstParamVO.setKey("code");
			lstParamVO.setValue("description");
			paramVO.setElement_dataType(DT_STRING);
			paramVO.setElement_type(COMBO_ELEMENT);
			paramVO.setId("csvSeparatorId_" + htmlPageRef);
			paramVO.setName("var_separator");
			paramVO.setRow(count);
			paramVO.setColumn(1);
			if(ConstantsCommon.CSV_REP_FORMAT.equals(getVar_format())
				|| ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT()))
			{
			    paramVO.setVisible("true");
			}
			else
			{
			    paramVO.setVisible("false");
			}
			paramVO.setListParamVO(lstParamVO);
			String csvSeparator = repCO.getCSV_SEPARATOR();
			if("\\t".equals(csvSeparator))
			{
			    csvSeparator = "\t";
			}
			paramVO.setValue(StringUtil.nullToEmpty(csvSeparator));
			paramVO.setConsiderChoiceValue(ConstantsCommon.TRUE);
			formLst.add(paramVO);
		}
		
		//put the language combo as hidden at the end of the row just for display purpose
		if("1".equals(repCO.getLANG_INDEPENDENT_YN()))
		{
		formLst.add(paramVO1);
		}

		// hide/show header and footer
		lstParamVO = new ListParamVO();
		paramVO = new DynamicParamsVO();
		chkNoHeadAndFootLst = setChkBox();
		lstParamVO.setValueList("chkNoHeadAndFootLst");
		lstParamVO.setKey("code");
		lstParamVO.setValue("description");
		if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT())
			|| ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(repCO.getDEFAULT_FORMAT()))
		{
		    paramVO.setLabel(getText("reporting.noHeaders"));
		}
		else
		{
		    paramVO.setLabel(getText("reporting.noHeadAndFoot"));
		}
		paramVO.setElement_dataType(DT_STRING);
		paramVO.setElement_type(CHKBOX_ELEMENT);
		paramVO.setId("chkHeadFootId_" + htmlPageRef);
		paramVO.setRow(++count);
		paramVO.setColumn(1);
		paramVO.setListParamVO(lstParamVO);
		
		String noHeadFootVal;
		if (expImpParamsExist)
		{
		    noHeadFootVal=(String)fileExpImpParamsMap.get(RepConstantsCommon.NO_HEAD_FOOT);
		}
		else if(BigDecimal.ZERO.toString().equals(repCO.getSHOW_HEAD_FOOT())
			|| ConstantsCommon.TRUE.equals(repCO.getSHOW_HEAD_FOOT()))
		{
		    noHeadFootVal=ConstantsCommon.TRUE;
		}
		else
		{
		    noHeadFootVal=ConstantsCommon.FALSE;
		}
		noHeadFootMap.put(ConstantsCommon.HEAD_FOOT_PARAM, noHeadFootVal);
		paramVO.setValue(noHeadFootVal);
		paramVO.setName("noHeadFootMap."+ConstantsCommon.HEAD_FOOT_PARAM);
		paramVO.setLabelId("chkHeadFootLblId_" + htmlPageRef);
		if(ConstantsCommon.SQL_REP_FORMAT.equals(getVar_format()))
		{
		    paramVO.setVisible(ConstantsCommon.FALSE);
		}
		else
		{
		    paramVO.setVisible(ConstantsCommon.TRUE);
		}

		formLst.add(paramVO);
		// }

		// create the 'Save Copy' checkBox
		// if not called from designer and not fcr or ftr report
		if((!("RD00R").equals(menu) && fromFileExpImp==null)
			&& (!"1".equals(repCO.getREPORT_TYPE()) && !menu.endsWith(
				ConstantsCommon.OPT_FCR_EXTENSION)))
		{
		    paramVO = new DynamicParamsVO();
		    lstParamVO = new ListParamVO();
		    chkSaveList = setSnapshotCombo();
		    lstParamVO.setValueList("chkSaveList");
		    lstParamVO.setKey("code");
		    lstParamVO.setValue("description");
		    paramVO.setLabel(getText("saveSnapshot_key"));
		    paramVO.setElement_dataType(DT_STRING);
		    paramVO.setElement_type(COMBO_ELEMENT);
		    paramVO.setId("chkId_" + htmlPageRef);
		    paramVO.setName("var_chkName");
		    paramVO.setRow(count);
		    paramVO.setColumn(1);
		    paramVO.setListParamVO(lstParamVO);
		    formLst.add(paramVO);
		}
		
		int maxInactInterv = ServletActionContext.getRequest().getSession().getMaxInactiveInterval();
		int interv=1200000;
		if(maxInactInterv > 0)
		{
			// get the 20% of the session timeout value
			BigDecimal intervPerc = new BigDecimal((maxInactInterv / 5)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			interv = (maxInactInterv - intervPerc.intValue()) * 1000;
		}
		paramVO = new DynamicParamsVO();
		paramVO.setElement_dataType(DT_NUMBER);
		 paramVO.setElement_type(HIDDEN_ELEMENT);
		paramVO.setId("hiddenSessionTimeOut_" + htmlPageRef);
		paramVO.setName(ConstantsCommon.SESSION_TIMEOUT_PARAM);
		paramVO.setValue(String.valueOf(interv));
		paramVO.setRow(count);
		paramVO.setColumn(1);
		formLst.add(paramVO);
	    }

	    // set the filter id hidden
	    DynamicParamsVO filterParamVO = new DynamicParamsVO();
	    filterParamVO.setElement_dataType(DT_STRING);
	    filterParamVO.setElement_type(HIDDEN_ELEMENT);
	    filterParamVO.setId("hiddenFilterId_" + htmlPageRef);
	    filterParamVO.setName(ConstantsCommon.FILTER_ID_PARAM);
	    filterParamVO.setRow(++count);
	    filterParamVO.setColumn(1);
	    if(defaultFilter != null)
	    {
		filterParamVO.setValue(defaultFilter.toString());
	    }
	    formLst.add(filterParamVO);

	    // set the filter id hidden
	    DynamicParamsVO filterParamTmpVO = new DynamicParamsVO();
	    filterParamTmpVO.setElement_dataType(DT_STRING);
	    filterParamTmpVO.setElement_type(HIDDEN_ELEMENT);
	    filterParamTmpVO.setId("hiddenFilterTmpId_" + htmlPageRef);
	    filterParamTmpVO.setRow(++count);
	    filterParamTmpVO.setColumn(1);
	    formLst.add(filterParamTmpVO);
	    // set the menuId as hidden
	    paramVO = new DynamicParamsVO();
	    paramVO.setElement_dataType(DT_STRING);
	    paramVO.setElement_type(HIDDEN_ELEMENT);
	    paramVO.setId("hiddenMenuId_"+htmlPageRef);
	    paramVO.setName(ConstantsCommon.MENU_ID_PARAM);
	    paramVO.setValue(menu);
	    paramVO.setRow(count);
	    paramVO.setColumn(1);
	    formLst.add(paramVO);

	    // set report id as hidden
	    paramVO = new DynamicParamsVO();
	    paramVO.setElement_dataType(DT_STRING);
	    paramVO.setElement_type(HIDDEN_ELEMENT);
	    paramVO.setId("hiddenRepId");
	    paramVO.setName(ConstantsCommon.REPORT_ID_PARAM);

	    if(repCO == null || repCO.getREPORT_ID() == null)
	    {
		paramVO.setValue("0");
	    }
	    else
	    {
		paramVO.setValue((repCO.getREPORT_ID()).toString());
	    }

	    paramVO.setRow(count);
	    paramVO.setColumn(1);
	    formLst.add(paramVO);

	    if(repCO != null && repCO.getSTANDARD_FLAG_YN() != null)
	    {
		paramVO = new DynamicParamsVO();
		paramVO.setElement_dataType(DT_STRING);
		paramVO.setElement_type(HIDDEN_ELEMENT);
		paramVO.setId("STANDARD_FLAG_YN");
		paramVO.setName("param~STANDARD_FLAG_YN~VARCHAR2");
		paramVO.setValue(repCO.getSTANDARD_FLAG_YN());
		paramVO.setRow(count);
		paramVO.setColumn(1);
		formLst.add(paramVO);
	    }

	    // set the app name as hidden
	    paramVO = new DynamicParamsVO();
	    paramVO.setElement_dataType(DT_STRING);
	    paramVO.setElement_type(HIDDEN_ELEMENT);
	    paramVO.setId("hiddenAppName");
	    paramVO.setName(ConstantsCommon.APP_NAME_PARAM);
	    paramVO.setValue(ConstantsCommon.REP_APP_NAME);
	    paramVO.setRow(count);
	    paramVO.setColumn(1);
	    formLst.add(paramVO);

	    // set d_p param as hidden
	    paramVO = new DynamicParamsVO();
	    paramVO.setElement_dataType(DT_STRING);
	    paramVO.setElement_type(HIDDEN_ELEMENT);
	    paramVO.setId("hiddenDP");
	    paramVO.setName(ConstantsCommon.DYNAMIC_SCREEN_PARAM);
	    paramVO.setValue("1");
	    paramVO.setRow(count);
	    paramVO.setColumn(1);
	    formLst.add(paramVO);

	    if(fromSched && argumentsList.isEmpty())
	    {
		dpVO = new DynamicParamsVO();
		dpVO.setElement_type(LABEL_ELEMENT);
		dpVO.setLabel(getText("sch.repNoParams"));
		dpVO.setId("noParamsId_" + htmlPageRef);
		dpVO.setRow(count++);
		dpVO.setColumn(1);
		formLst.add(dpVO);
	    }
	    String actionName = "";
	    super.fillFormElements(formLst, actionName, "dynParamFrmId"+idSched + htmlPageRef, "_blank");
	}
	catch(BaseException e)
	{
	    handleException(e, "Error in constructing the report retrieval screen for report having id: "+repCO.getREPORT_ID() +", reference: "+repCO.getPROG_REF() + " and application: "+repCO.getAPP_NAME(),
		    "loadDynForm.fillForm.exceptionMsg._key");
	}
    }
    
    /**
     * Method that dynamically adds the livesearch parameters
     * @param argumentsList
     * @param idSched
     * @param paramVO
     * @return
     */
    public DynamicParamsVO addParamsLiveSearch(List<IRP_REP_ARGUMENTSCO> argumentsList, String idSched,
	    DynamicParamsVO paramVO)
    {
	DynamicParamsVO lParamVO = paramVO;
	// adding the parameters
	StringBuffer parameters = new StringBuffer();
	IRP_REP_ARGUMENTSCO arg;
	for(int i = 0; i < argumentsList.size(); i++)
	{
	    arg = argumentsList.get(i);
	    parameters.append(ConstantsCommon.PARAM_TILDA + arg.getARGUMENT_NAME() + "~" + arg.getARGUMENT_TYPE() + ":");
	    if(new BigDecimal(3).equals(arg.getARGUMENT_SOURCE()) || new BigDecimal(10).equals(arg.getARGUMENT_SOURCE()))
	    {
		parameters.append("lookuptxt_");
	    }
	    parameters.append("p_").append("param_" + idSched).append(arg.getARGUMENT_NAME()).append("_").append(
		    arg.getARGUMENT_TYPE()).append("_").append(htmlPageRef);
	    parameters.append(",");
	}
	parameters.append("var_menuId:~CONST_" + menu + ",updates:'" + lParamVO.getName() + "',conId:connection_id_"+htmlPageRef);
	lParamVO.setParameters(parameters.toString());
	return lParamVO;
    }
    
    
    /**
     * Method that fills the argFunctionMap with data related to comparison and show/hide functions 
     * 0=> add function onChange = comparision
     * 1 or 2 => add function onChange = show/hide
     * @param argFunctionMap
     * @param argumentsList
     */
    public void fillArgFunctionMap(HashMap<String, HashMap<Integer, Object>> argFunctionMap,
	    List<IRP_REP_ARGUMENTSCO> argumentsList)
    {
	IRP_REP_ARG_CONSTRAINTSCO constrCO;
	String argName;

	for(IRP_REP_ARGUMENTSCO argObj : argumentsList)
	{
	    argName = argObj.getARGUMENT_NAME();
	    constrCO = argObj.getIrpRepArgConstraintCO();
	    if(constrCO.getCONDITION() != null)
	    {
		if(argFunctionMap.get(argName) == null)
		{
		    HashMap<Integer, Object> internalMap = new HashMap<Integer, Object>();
		    internalMap.put(0, "");
		    argFunctionMap.put(argName, internalMap);
		}
		else if(argFunctionMap.get(argName).get(0) == null)
		{
		    HashMap<Integer, Object> internalMap = argFunctionMap.get(argName);
		    internalMap.put(0, "");
		    argFunctionMap.put(argName, internalMap);
		}
		checkArgsUsedInExpr(argName, constrCO.getCONDITION(), argumentsList, argFunctionMap, 0);
	    }
	    if(constrCO.getSHOW_EXPR() != null)
	    {

		if(argFunctionMap.get(argName) == null)
		{
		    HashMap<Integer, Object> internalMap = new HashMap<Integer, Object>();
		    internalMap.put(1, "");
		    argFunctionMap.put(argName, internalMap);
		}
		else if(argFunctionMap.get(argName).get(1) == null)
		{
		    HashMap<Integer, Object> internalMap = argFunctionMap.get(argName);
		    internalMap.put(1, "");
		    argFunctionMap.put(argName, internalMap);
		}
		checkArgsUsedInExpr(argName, constrCO.getSHOW_EXPR(), argumentsList, argFunctionMap, 1);
	    }
	    if(constrCO.getHIDE_EXPR() != null)
	    {
		if(argFunctionMap.get(argName) == null)
		{
		    HashMap<Integer, Object> internalMap = new HashMap<Integer, Object>();
		    internalMap.put(2, "");
		    argFunctionMap.put(argName, internalMap);
		}
		else if(argFunctionMap.get(argName).get(2) == null)
		{
		    HashMap<Integer, Object> internalMap = argFunctionMap.get(argName);
		    internalMap.put(2, "");
		    argFunctionMap.put(argName, internalMap);
		}
		checkArgsUsedInExpr(argName, constrCO.getHIDE_EXPR(), argumentsList, argFunctionMap, 2);
	    }
	}
    }

    /**
     * Method that checks the other arguments used in current argument expression in order
     * to add js function (change,show/hide) to them
     * 
     * @param argName
     * @param expression
     * @param argumentsList
     * @param argFunctionMap
     * @param option
     */
    public void checkArgsUsedInExpr(String argName, String expression, List<IRP_REP_ARGUMENTSCO> argumentsList,
	    HashMap<String, HashMap<Integer, Object>> argFunctionMap, int option)
    {
	HashMap<Integer, Object> internalMap ;
	for(IRP_REP_ARGUMENTSCO argObj : argumentsList)
	{
	    if(expression.indexOf(argObj.getARGUMENT_NAME()) != -1 && !argName.equals(argObj.getARGUMENT_NAME()))
	    {
		if(argFunctionMap.get(argObj.getARGUMENT_NAME()) == null)
		{
		    internalMap = new HashMap<Integer, Object>();
		    internalMap.put(option, "");
		    argFunctionMap.put(argObj.getARGUMENT_NAME(), internalMap);
		}
		else if(argFunctionMap.get(argObj.getARGUMENT_NAME()).get(option) == null)
		{
		    internalMap = argFunctionMap.get(argObj.getARGUMENT_NAME());
		    internalMap.put(option, "");
		    argFunctionMap.put(argObj.getARGUMENT_NAME(), internalMap);
		}
	    }
	}
    }


    public List setCombo() throws BaseException
    {
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	return commonLookupBO.getLookupList(sysParamLovVO);
    }

    /**
     * Method that fills combo element with related records
     * @param queryId
     * @param repCO
     * @param argumentId
     * @return
     */
    public List retQryCombo(BigDecimal queryId, IRP_AD_HOC_REPORTCO repCO, IRP_REP_ARGUMENTSCO argObj)
    {
	try
	{
	    HashMap<String, String> hmQryParam = new HashMap<String, String>();
	    Object argSessionValue;
	    BigDecimal argumentId = argObj.getARGUMENT_ID();
	    LinkedHashMap argsMap = repCO.getArgumentsList();
	    SessionCO sessionCO = returnSessionObject();
	    String sessionParamName;
	    ArrayList<IRP_REP_ARGUMENTSCO> argsList = new ArrayList(argsMap.values());
	    DynLookupSC dynLookupSC = new DynLookupSC();
	    dynLookupSC.setIsSybase(commonLibBO.returnIsSybase());
	    dynLookupSC.setQryId(queryId.toString());
	    dynLookupSC.setConnId(repCO.getCONN_ID());
	    IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	    reportSC.setREPORT_ID(repCO.getREPORT_ID());
	    reportSC.setReportArgumentId(argumentId);
	    reportSC.setDefaultSrc(argObj.getQUERY_ID_DEFAULT()==null?BigDecimal.ONE:new BigDecimal(2));
	    List<IRP_QUERY_ARG_MAPPINGCO> listDfltSrc = designerBO.retQryArgMapping(reportSC);

	    IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(queryId, true);
	    for(int j = 0; j < query.getSqlObject().getArgumentsList().size(); j++)
	    {
		LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments = query.getSqlObject().getArgumentsList();
		ArrayList<IRP_REP_ARGUMENTSCO> argsQryList = new ArrayList(arguments.values());

		for(int m = 0; m < listDfltSrc.size(); m++)
		{
		    for(int n = 0; n < (argsQryList.size() < listDfltSrc.size() ? argsQryList.size() : listDfltSrc
			    .size()); n++)
		    {
			if(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME().equals(
				argsQryList.get(n).getARGUMENT_NAME()))
			{
			    listDfltSrc.get(m).setARGUMENT_TYPE(argsQryList.get(n).getARGUMENT_TYPE());
			    if(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getSTATIC_VALUE() == null
				    || ("").equals(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getSTATIC_VALUE()))
			    {
				for(int a = 0; a < argsList.size(); a++)
				{
				    if(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getREPORT_MAPPED_ARG_NAME()
					    .equals(argsList.get(a).getARGUMENT_NAME()))
				    {
					if(argsList.get(a).getARGUMENT_SOURCE().equals(
						ConstantsCommon.SESSION_ARG_SOURCE)
						|| argsList.get(a).getARGUMENT_SOURCE().equals(
							ConstantsCommon.TRANS_SESSION_ARG_SOURCE))
					{
					    sessionParamName = argsList.get(a).getSESSION_ATTR_NAME();
					    if(ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage())
						    && ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName))
					    {
						sessionParamName = ConstantsCommon.COMP_AR_NAME_SESSION;
					    }
					    argSessionValue = PathPropertyUtil.returnProperty(sessionCO,
						    sessionParamName);
					    hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						    .getQUERY_ARG_NAME(), argSessionValue.toString());
					}
					else if(argsList.get(a).getARGUMENT_SOURCE().equals(new BigDecimal(5)))
					{
					    hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						    .getQUERY_ARG_NAME(), menu);
					}
					else
					{
					    hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO()
						    .getQUERY_ARG_NAME(), argsList.get(a).getARGUMENT_VALUE());
					}
				    }
				}
			    }
			    else
			    {
				hmQryParam.put(listDfltSrc.get(m).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME(),
					listDfltSrc.get(m).getIrpQueryArgsMappingVO().getSTATIC_VALUE());
			    }
			}
		    }
		}

	    }
	    dynLookupSC.setHmQryParam(hmQryParam);
	    dynLookupSC.setCompCode(sessionCO.getCompanyCode());
	    dynLookupSC.setBranchCode(sessionCO.getBranchCode());
	    dynLookupSC.setUserId(sessionCO.getUserName());
	    dynLookupSC.setCurrAppName(sessionCO.getCurrentAppName());
	    dynLookupSC.setNbRec(commonLookupBO.returnQryResultCnt(dynLookupSC));
	    dynLookupSC.setRecToskip(0);
	    return commonLookupBO.returnQryResult(dynLookupSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return null;
    }
    
    
    public List setSepartor()
    {
	ArrayList<SepartorFormat> separatorsList = new ArrayList<SepartorFormat>();
	separatorsList.add(new SepartorFormat(",", ","));
	separatorsList.add(new SepartorFormat(getText("reporting.tab"), "\t"));
	return separatorsList;
    }

    public List setLangCombo() throws BaseException
    {
	SessionCO sessionCO = returnSessionObject();
	SelectSC sc= new SelectSC();
	sc.setLanguage(sessionCO.getLanguage());
	sc.setLovTypeId(ConstantsCommon.LANGUAGES_LOV_TYPE);
	return commonLibBO.returnLanguages(sc);
    }

    public List setSnapshotCombo()
    {
	ArrayList<ReportFormat> snapShotList = new ArrayList<ReportFormat>();
	snapShotList.add(new ReportFormat("", ""));
	snapShotList.add(new ReportFormat(getText("reporting.db"), RepConstantsCommon.DB));
	snapShotList.add(new ReportFormat(getText("reporting.repository"), RepConstantsCommon.Repository));
	return snapShotList;
    }

    public List<IRP_CONNECTIONSVO> setConnectionList()
    {
	List<IRP_CONNECTIONSVO> conList = new ArrayList<IRP_CONNECTIONSVO>();
	conList.add(new IRP_CONNECTIONSVO());
	try
	{
	    conList.addAll(commonLookupBO.returnConnectionsList());
	}
	catch(BaseException e)
	{
	    log.error(e.getMessage());
	}
	return conList;

    }

    private List setChkBox()
    {
	ArrayList<ReportFormat> repChkList = new ArrayList<ReportFormat>();
	repChkList.add(new ReportFormat("", ConstantsCommon.TRUE));
	return repChkList;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    @Override
    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

}
