package com.path.actions.reporting.ftr.fcr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.dbmaps.vo.PTH_CTRLVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;

public class FcrAction extends ReportingLookupBaseAction
{
    private FcrBO fcrBO;
    private FTR_OPTCO fcrCO;
    private CommonDetailsSC commonDetailsSC = new CommonDetailsSC();
    private CommonDetailsVO commonDetVO;
    private String updates;
    private CommonRepFuncBO commonRepFuncBO;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> reportTypesList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> groupByList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private String pRefLkp;
    private String applName;
    private BigDecimal code;
    private SchedulerBO schedulerBO;
    private TemplateBO templateBO;
    

    public void setTemplateBO(TemplateBO templateBO)
    {
        this.templateBO = templateBO;
    }

    public void setSchedulerBO(SchedulerBO schedulerBO)
    {
        this.schedulerBO = schedulerBO;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    private CommonLookupBO commonLookupBO;

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getGroupByList()
    {
	return groupByList;
    }

    public void setGroupByList(ArrayList<SYS_PARAM_LOV_TRANSVO> groupByList)
    {
	this.groupByList = groupByList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getReportTypesList()
    {
	return reportTypesList;
    }

    public void setReportTypesList(ArrayList<SYS_PARAM_LOV_TRANSVO> reportTypesList)
    {
	this.reportTypesList = reportTypesList;
    }

    public String getApplName()
    {
	return applName;
    }

    public void setApplName(String applName)
    {
	this.applName = applName;
    }

    public String getPRefLkp()
    {
	return pRefLkp;
    }

    public void setPRefLkp(String pRefLkp)
    {
	this.pRefLkp = pRefLkp;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public Object getModel()
    {
	return commonDetailsSC;
    }

    /**
     * Method that open the dynamic report screen
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String openDynamicRep()
    {
	try
	{
	    // change the display of the group by to display none
	    SYS_PARAM_SCREEN_DISPLAYVO repType = new SYS_PARAM_SCREEN_DISPLAYVO();
	    repType.setIS_VISIBLE(BigDecimal.ZERO);
	    getAdditionalScreenParams().put("groupBy", repType);
	    getAdditionalScreenParams().put("groupByDetails", repType);

	    // add the search button when clicking shows the grid
	    set_searchGridId("fcrGridId");

	    // new button empty the form
	    set_showNewInfoBtn("true");

	    // remove the smart button
	    set_showSmartInfoBtn("false");

	    // empty the form
	    cleanForm();
	}
	catch(Exception e)
	{
	    //log.error(e, "______________ error in openDynamicRep ________________");
	    handleException(e, null, null);
	}
	return "successLst";

    }

    /**
     * Method that load the grid of fcr
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String loadFcrList() // load fcr list grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(commonDetailsSC);
	    commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    commonDetailsSC.setAppName(sessionCO.getCurrentAppName());
	    List<FTR_OPTCO> lstFcr = fcrBO.loadFcrList(commonDetailsSC);
	    int fcrCount = fcrBO.retFcrListCount(commonDetailsSC);

	    setRecords(fcrCount);
	    setGridModel(lstFcr);

	}
	catch(Exception e)
	{
	    //log.error(e, "__________ error in loadFcrList()");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method that validate and either insert new row or update the row
     * 
     * @return successMnt
     * @throws Exception
     */
    public String validateFcr() // submit GL form
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    FTR_OPTVO fcrVO = fcrCO.getFtrOptVO();// new FTR_OPTVO();
	    if(ConstantsCommon.GRP_BY_SEC_BRIEF_NAME.equals(fcrVO.getGROUP_BY())
		    && templateBO.checkTemplateGLRecords(fcrVO.getTMPLT_CODE(), sessionCO.getCompanyCode()) == 0)
	    {
		handleException(null, getText("reporting.calcTypeAssetHolding"), null);
		return ERROR;
	    }
	    fcrVO.setAPP_NAME(sessionCO.getCurrentAppName());
	    String pageRef = get_pageRef();
	    String lang = sessionCO.getLanguage();
	    // apply audit
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.DYNAMIC_REP_KEY_REF);
	    fcrVO.setAuditRefCO(refCO);

	    // mode to know if this is update or inserting a new row
	    String mode;
	    if(fcrVO.getCODE() == null || fcrVO.getCODE().intValue() == 0)
	    {
		BigDecimal code = commonRepFuncBO.retCounterValue("FTR_OPT");
		fcrVO.setCODE(code);
		mode = ConstantsCommon.CREATE_MODE;
	    }
	    else
	    {
		FTR_OPTVO oldOptCO = (FTR_OPTVO) returnAuditObject(FTR_OPTVO.class);
		fcrVO.setAuditObj(oldOptCO);
		mode = ConstantsCommon.UPDATE_MODE;
	    }
	    // fill the trans. of the ext of the opts that we will save in the
	    // prog_desc col since we are not able to get the trans. from the BO
	    HashMap<String, String> optTrans = new HashMap<String, String>();
	    optTrans.put("D", getText("opt.delete"));
	    optTrans.put("M", getText("opt.modify"));
	    optTrans.put("SV", getText("opt.Save"));
	    optTrans.put("SA", getText("opt.SaveAs"));
	    optTrans.put("SM", getText("opt.sendMail"));
	    optTrans.put("SC", getText("opt.sched"));
	    optTrans.put("PR", getText("opt.print"));
	    fcrCO.setRUNNING_DATE(sessionCO.getRunningDateRET());

	    fcrBO.saveFCR(fcrCO, sessionCO.getCompanyCode(), sessionCO.getBranchCode(), sessionCO.getCurrentAppName(),
		    returnUserName(), optTrans, mode, pageRef, lang);
	}
	catch(Exception e)
	{
	   // log.error(e, "______________ error in validateFcr ________________");
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    /**
     * Method that load the parent Ref lookup
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String loadPRefLkp()
    {
	try
	{
		 String[] name = { "PROG_REF", "PROG_DESC", "MENU_TITLE_ENG" };
		 String[] colType = { "text", "text", "text" };
		 String[] titles = { getText("progRef"), getText("reporting.description"), getText("reporting.lkpMenuTitle") };
	    fillLookup("pRefGridId", "/path/fcrRep/fcr_pRefLookUpGrid", getText("reporting.pRefList"),name,colType,titles);
	}
	catch(Exception e)
	{
	    //log.error(e, "______________ error in loadPRefLkp ________________");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method that load the grid of parent Ref lookup
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String pRefLookUpGrid()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(commonDetailsSC);
	    setSearchFilter(commonDetailsSC);
	    commonDetailsSC.setGrid(true);
	    commonDetailsSC.setPROG_REF(null);// because the prog_ref is filled
	    // on liveSearch
	    if(applName == null)
	    {
		commonDetailsSC.setAppName(sessionCO.getCurrentAppName());
	    }
	    else
	    {
		commonDetailsSC.setAppName(applName);
	    }
	    PTH_CTRLVO pthCtrl = returnCommonLibBO().returnPthCtrl();
	    PTH_CTRL1VO pthCtrl1 = returnCommonLibBO().returnPthCtrl1();
	    commonDetailsSC.setCORE_IMAL_YN(pthCtrl1.getCORE_IMAL_YN());
	    commonDetailsSC.setUserId(sessionCO.getUserName());
	    commonDetailsSC.setCompCode(sessionCO.getCompanyCode());
	    commonDetailsSC.setBranchCode(sessionCO.getBranchCode());
	    if("0".equals(pthCtrl1.getREP_SHOW_PARENT_OPTS()))
	    {
		commonDetailsSC.setProfType(NumberUtil.nullToZero(pthCtrl.getPOP_PROF_MOD_ACCESS()));
	    }
	    List<CommonDetailsVO> pRefList = fcrBO.retParentRefList(commonDetailsSC);
	    int pRefListCount = fcrBO.retParentRefListCount(commonDetailsSC);
	    setRecords(pRefListCount);
	    setGridModel(pRefList);
	}
	catch(Exception e)
	{
	   // log.error(e, "********* error in loading the pRefLookUpGrid ****");
	    handleException(e, null, null);
	}
	return "success";

    }

    /**
     * Method that construct the categories lookup where is_web_yn=2
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String loadCategoriesLkp()
    {
		try
		{
			// Design the Grid by defining the column model and column names
		    String[] name = { "CODE", "BRIEF_DESC_ENG", "CATEGORY","BRIEF_DESC_ARAB"};
		    String[] colType = { "number", "text", "text","text" };
		    String[] titles = { getText("code"), getText("reporting.description"), getText("desc_fr_key"),getText("desc_arab_key") };
		    
		    fillLookup("categGridId", "/path/fcrRep/fcr_categoriesLookUpGrid", getText("reporting.lkpCategory"),name,colType,titles);
		}
		catch(Exception e)
		{
		    handleException(e, null, null);
		}
		return SUCCESS;
    }
    
    /**
     * Method that load the grid of the categories lookup
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String categoriesLookUpGrid()
    {
    	try
    	{
    		    copyproperties(commonDetailsSC);
    		    setSearchFilter(commonDetailsSC);
    		    commonDetailsSC.setGrid(true);
    		    List<CommonDetailsVO> categList = fcrBO.retCategoriesLkpList(commonDetailsSC);
    		    int categListCount = fcrBO.retCategoriesLkpCount(commonDetailsSC);
    		    setRecords(categListCount);
    		    setGridModel(categList);
    	}
    	catch(Exception e)
    	{
    		handleException(e, null, null);
    	}
    	return SUCCESS;
    }

    /**
     * Method for the dependency of parent ref id
     * 
     * @return SUCCESS
     * @throws Exception
     */
	public String dependencyByCategoryId() 
	{
		try
		{
			commonDetVO = new CommonDetailsVO();
			commonDetailsSC = new CommonDetailsSC();
			commonDetailsSC.setGrid(false);
			commonDetailsSC.setCODE(code);
			List<CommonDetailsVO> categList = fcrBO.retCategoriesLkpList(commonDetailsSC);
			if(!categList.isEmpty())
			{
				commonDetVO=categList.get(0);
			}
		}
		catch (Exception e) 
		{
			handleException(e, null, null);
		}
		return SUCCESS;
	}
    
    /**
     * Method that fill the lookup
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String fillLookup(String gridId, String gridUrl, String gridCaption, String[] name,String[] colType,String[] titles)
    {
	try
	{
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

		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, commonDetailsSC);
	}
	catch(Exception e)
	{
	    //log.error(e, "______________ error in fillLookup ________________");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method for the dependency of parent ref id
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String dependencyByPRefId()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    if(updates == null || updates.equals("") || updates.equals("-9999999"))
	    {
		commonDetVO = new CommonDetailsVO();
	    }
	    else if(updates != null && updates.equals("ROOT"))
	    {
		commonDetVO = new CommonDetailsVO();
		commonDetVO.setBRIEF_DESC_ENG("ROOT");
		commonDetVO.setCODE_STR("ROOT");
	    }
	    else
	    {
		commonDetailsSC = new CommonDetailsSC();
		commonDetailsSC.setPROG_REF(updates);
		commonDetailsSC.setGrid(false);
		if(applName == null)
		{
		    commonDetailsSC.setAppName(sessionCO.getCurrentAppName());
		}
		else
		{
		    commonDetailsSC.setAppName(applName);
		}
		PTH_CTRLVO pthCtrl = returnCommonLibBO().returnPthCtrl();
		PTH_CTRL1VO pthCtrl1 = returnCommonLibBO().returnPthCtrl1();
		commonDetailsSC.setCORE_IMAL_YN(pthCtrl1.getCORE_IMAL_YN());
		commonDetailsSC.setUserId(sessionCO.getUserName());
		commonDetailsSC.setCompCode(sessionCO.getCompanyCode());
		commonDetailsSC.setBranchCode(sessionCO.getBranchCode());
		if("0".equals(pthCtrl1.getREP_SHOW_PARENT_OPTS()))
		{
		    commonDetailsSC.setProfType(NumberUtil.nullToZero(pthCtrl.getPOP_PROF_MOD_ACCESS()));
		}
		List<CommonDetailsVO> templList = fcrBO.retParentRefList(commonDetailsSC);
		if(templList == null || templList.isEmpty())
		{
		    commonDetVO = new CommonDetailsVO();
		}
		else
		{
		    CommonDetailsVO retVal = templList.get(0);
		    commonDetVO = new CommonDetailsVO();
		    commonDetVO.setCODE_STR(updates);
		    commonDetVO.setBRIEF_DESC_ENG(retVal.getPROG_DESC());
		    commonDetVO.setAPPL_NAME(sessionCO.getCurrentAppName());
		}
	    }
	    
	    //empty  and show/hide the category inputs
  		SYS_PARAM_SCREEN_DISPLAYVO categ = new SYS_PARAM_SCREEN_DISPLAYVO();
  		
  		if(new BigDecimal(2).equals(code) && RepConstantsCommon.ROOT.equals(updates))
  		{
  			categ.setIS_VISIBLE(BigDecimal.ONE);
  			categ.setIS_MANDATORY(BigDecimal.ONE);
  		}
  		else if(!RepConstantsCommon.ROOT.equals(updates))
  		{
  			commonDetVO.setCATEGORY("");
  			commonDetVO.setCODE(null);
  			categ.setIS_VISIBLE(BigDecimal.ZERO);
  		}
  		getAdditionalScreenParams().put("lookuptxt_CATEGORY_ID", categ);
	  	getAdditionalScreenParams().put("CATEGORY_DESC", categ);
  		
  		
	}
	catch(Exception e)
	{
	    //log.error(e, "______________ error in dependencyByPRefId ________________");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method that check if the prog ref is already used in same application we
     * can use the same prog ref in different application
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String checkProgRef()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String refStr = getUpdates();
	    CommonDetailsSC sc = new CommonDetailsSC();
	    sc.setAppName(sessionCO.getCurrentAppName());
	    sc.setBRIEF_DESC_ENG(refStr);
	    int refCount = fcrBO.checkProgRef(sc);
	    setUpdates(String.valueOf(refCount));
	}
	catch(Exception e)
	{
	   // log.error(e, "______________ error in checkProgRef ________________");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * Method to get the data for the grid
     * 
     * @return successMnt
     * @throws Exception
     */
    public String openFcr()
    {
	try
	{
	    // apply audit

	    SessionCO sessionCO = returnSessionObject();

	    cleanForm();

	    CommonDetailsSC sc = new CommonDetailsSC();
	    sc.setCOMP_CODE(sessionCO.getCompanyCode());
	    sc.setAppName(sessionCO.getCurrentAppName());
	    sc.setCODE(getCode());
	    fcrCO = fcrBO.retrieveFcr(sc);

	    // clear before the group by if it was summarized if it was detailed
	    // shows the group by
	    SYS_PARAM_SCREEN_DISPLAYVO dispVO = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(fcrCO.getFtrOptVO().getREP_TYPE().equals("S"))
	    {
		dispVO.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    else
	    {
		dispVO.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    getAdditionalScreenParams().put("groupBy", dispVO);
	    getAdditionalScreenParams().put("groupByDetails", dispVO);

	    dispVO = new SYS_PARAM_SCREEN_DISPLAYVO();
	    dispVO.setIS_READONLY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("fcrProgRef", dispVO);

	    applyRetrieveAudit(AuditConstant.DYNAMIC_REP_KEY_REF, fcrCO.getFtrOptVO(), fcrCO.getFtrOptVO());

	}
	catch(Exception e)
	{
	    //log.error(e, "______________ error in openFcr ________________");
	    handleException(e, null, null);
	}
	return "successMnt";
    }

    /**
     * Method that delete a selected row in the grid
     * 
     * @return successMnt
     * @throws Exception
     */
    public String deleteFcr()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    // apply audit
	    fcrCO = new FTR_OPTCO();
	    fcrCO.getFtrOptVO().setCODE(getCode());
	    fcrCO.getFtrOptVO().setAPP_NAME(getApplName());
	    fcrCO.getFtrOptVO().setPROG_REF(getPRefLkp());

	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.DELETE);
	    refCO.setKeyRef(AuditConstant.DYNAMIC_REP_KEY_REF);
	    fcrCO.setAuditRefCO(refCO);

	    fcrBO.deleteFCR(sessionCO.getCompanyCode(), sessionCO.getBranchCode(), sessionCO.getCurrentAppName(),
		    returnUserName(), fcrCO,get_pageRef());
	}
	catch(Exception e)
	{
	   // log.error(e, "______________ error in deleteFcr ________________");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String cleanForm()
    {
	try
	{
	    // declare the select summarized
	    SessionCO sessionCO = returnSessionObject();

	    // change the display of the group by
	    SYS_PARAM_SCREEN_DISPLAYVO dispVO = new SYS_PARAM_SCREEN_DISPLAYVO();
	    dispVO.setIS_VISIBLE(BigDecimal.ZERO);
	    getAdditionalScreenParams().put("groupBy", dispVO);
	    getAdditionalScreenParams().put("groupByDetails", dispVO);

	    // change the prog ref to read only
	    dispVO = new SYS_PARAM_SCREEN_DISPLAYVO();
	    dispVO.setIS_READONLY(BigDecimal.ZERO);

	    getAdditionalScreenParams().put("fcrProgRef", dispVO);

	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.FCR_REPORT_TYPE_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    reportTypesList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.FCR_REPORT_GROUPING_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    groupByList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	    fcrCO = new FTR_OPTCO();
	    fcrCO.getFtrOptVO().setREP_TYPE("S");
	}
	catch(Exception e)
	{
	    //log.error(e, "______________ error in deleteFcr ________________");
	    handleException(e, null, null);
	}
	return "successMnt";
    }
    
    /**
     * Method that checks if a given report is binded to a scheduler
     * @return
     */
    public String checkSchedHasReport()
    {
	try
	{
	    updates = String.valueOf(schedulerBO.checkReportHasSched(updates));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public void setFcrBO(FcrBO fcrBO)
    {
	this.fcrBO = fcrBO;
    }

    public FTR_OPTCO getFcrCO()
    {
	return fcrCO;
    }

    public void setFcrCO(FTR_OPTCO fcrCO)
    {
	this.fcrCO = fcrCO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public CommonDetailsVO getCommonDetVO()
    {
	return commonDetVO;
    }

    public void setCommonDetVO(CommonDetailsVO commonDetVO)
    {
	this.commonDetVO = commonDetVO;
    }

}
