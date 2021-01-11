package com.path.actions.reporting.ftr.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.common.login.LoginBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dbmaps.vo.REP_SITCOM_FILEVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.USRVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterListAction.java used to
 */
public class SnapshotInformationListAction extends ReportingLookupBaseAction
{
    private SnapshotParameterBO snapshotParameterBO;
    private SnapshotParameterSC snapshotParameterSC = new SnapshotParameterSC();
    private REP_SNAPSHOT_INFORMATIONCO repSnapshotInformationCO;
    private REP_SNAPSHOT_INFOVO repSnapshotInfoVO;
    private REP_SNAPSHOT_PARAMVO repSnapshotParamVO;
    private String updates;
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    private String filePeriod;
    private String shouldCheckPerFreq;
    private LoginBO loginBO;
    private CommonRepFuncBO commonRepFuncBO;


    public CommonRepFuncBO getCommonRepFuncBO()
    {
        return commonRepFuncBO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
        this.commonRepFuncBO = commonRepFuncBO;
    }

    public String getShouldCheckPerFreq()
    {
	return shouldCheckPerFreq;
    }

    public void setShouldCheckPerFreq(String shouldCheckPerFreq)
    {
	this.shouldCheckPerFreq = shouldCheckPerFreq;
    }

    public void setLoginBO(LoginBO loginBO)
    {
	this.loginBO = loginBO;
    }

    public String getFilePeriod()
    {
	return filePeriod;
    }

    public void setFilePeriod(String filePeriod)
    {
	this.filePeriod = filePeriod;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public REP_SNAPSHOT_PARAMVO getRepSnapshotParamVO()
    {
	return repSnapshotParamVO;
    }

    public void setRepSnapshotParamVO(REP_SNAPSHOT_PARAMVO repSnapshotParamVO)
    {
	this.repSnapshotParamVO = repSnapshotParamVO;
    }

    public REP_SNAPSHOT_INFOVO getRepSnapshotInfoVO()
    {
	return repSnapshotInfoVO;
    }

    public void setRepSnapshotInfoVO(REP_SNAPSHOT_INFOVO repSnapshotInfoVO)
    {
	this.repSnapshotInfoVO = repSnapshotInfoVO;
    }

    public REP_SNAPSHOT_INFORMATIONCO getRepSnapshotInformationCO()
    {
	return repSnapshotInformationCO;
    }

    public void setRepSnapshotInformationCO(REP_SNAPSHOT_INFORMATIONCO repSnapshotInformationCO)
    {
	this.repSnapshotInformationCO = repSnapshotInformationCO;
    }

    public SnapshotParameterSC getSnapshotParameterSC()
    {
	return snapshotParameterSC;
    }

    public void setSnapshotParameterSC(SnapshotParameterSC snapshotParameterSC)
    {
	this.snapshotParameterSC = snapshotParameterSC;
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {
	this.snapshotParameterBO = snapshotParameterBO;
    }

    public String loadSnapshotInformationGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    copyproperties(snapshotParameterSC);
	    snapshotParameterSC.setCOMP_CODE(compCode);
	    snapshotParameterSC.setLanguageCode(sessionCO.getLanguage());
	    snapshotParameterSC.setCurrAppName(sessionCO.getCurrentAppName());
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> list = snapshotParameterBO.retSnapshotInformationList(
		    snapshotParameterSC, repSnapshotInformationCO);
	    int snpCount = snapshotParameterBO.retSnapshotInformationListCount(snapshotParameterSC);
	    setRecords(snpCount);
	    setGridModel(list);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillLiveSearchRepRefLookup() throws BaseException
    {
	try
	{
	    copyproperties(snapshotParameterSC);
	    setSearchFilter(snapshotParameterSC);
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> ls = snapshotParameterBO
		    .selectSnapshotParameterReports(snapshotParameterSC);
	    int size = snapshotParameterBO.selectSnapshotParameterReportsCount(snapshotParameterSC);
	    setRecords(size);
	    setGridModel(ls);
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }

    public String repRefLookup()
    {
	try
	{
	    fillRepRefLookup(
		    "/path/snapshotParameter/SnapshotInformationListAction_fillLiveSearchRepRefLookup.action?_pageRef="
			    + get_pageRef(), "");
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String fillRepRefLookup(String gridUrl, String gridCaption) throws JSONException
    {
	try
	{
	    String[] name = { "APPL_NAME", "repSnapshotParamVO.REP_REFERENCE", "repSnapshotInfoVO.DESCRIPTION" };
	    String[] colType = { "text", "text", "text" };
	    String[] titles = { getText("app_name_key"), getText("repRef_key"), getText("reporting.description") };
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    grid.setShrinkToFit("true");
	    List<LookupGridColumn> lstGridColumn = returnStandarColSpecs(name, colType, titles);
	    lookup(grid, lstGridColumn, null, snapshotParameterSC);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling reports lookup");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String reloadDivGrid() throws JSONException
    {
	return "reloadDivGrid";
    }

    public Object getModel()
    {
	return snapshotParameterSC;
    }

    public String applyMisInfoAudit() throws JSONException
    {
	try
	{
	    applyRetrieveAudit(AuditConstant.SNAPSHOT_INFO_KEY_REF, repSnapshotInfoVO, repSnapshotInfoVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String checkIncludeFileConditions()
    {
	try
	{
	    snapshotParameterSC.setREP_SNAPSHOT_ID(repSnapshotInfoVO.getREP_SNAPSHOT_ID());
	    updates = String.valueOf(snapshotParameterBO.checkIncludeFileConditions(snapshotParameterSC));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public boolean checkIfSamePeriodFrequency(Date period, String frequency, REP_SNAPSHOT_INFORMATIONCO infoCO)
    {
	try
	{
	    // compare the month of the file period if same month as the
	    // retrieve date in vo same for the year
	    REP_SNAPSHOT_INFOVO infoVO = infoCO.getRepSnapshotInfoVO();
	    Date retrieveDate = infoVO.getRETREIVE_DATE();
	    int retrieveMonth = DateUtil.pathMonth(retrieveDate);
	    int retrieveYear = DateUtil.getDayYearMonth(retrieveDate, DateUtil.YEAR);
	    int periodMonth = DateUtil.pathMonth(period);
	    int periodYear = DateUtil.getDayYearMonth(period, DateUtil.YEAR);
	    if(periodYear == retrieveYear && infoCO.getSNP_FRQ().equals(frequency))
	    {
		if(RepConstantsCommon.SNP_FREQUENCY_YEARLY.equals(frequency))
		{
		    return true;
		}
		else if(RepConstantsCommon.SNP_FREQUENCY_SEMI_ANNUALLY.equals(frequency))
		{
		    if((periodMonth <= 6 && retrieveMonth <= 6) || (periodMonth > 6 && retrieveMonth > 6))
		    {
			return true;
		    }
		}
		else if(RepConstantsCommon.SNP_FREQUENCY_QUARTERLY.equals(frequency))
		{
		    if((periodMonth <= 3 && retrieveMonth <= 3)
			    || (periodMonth > 3 && periodMonth <= 6 && retrieveMonth > 3 && retrieveMonth <= 6)
			    || (periodMonth > 6 && periodMonth <= 9 && retrieveMonth > 6 && retrieveMonth <= 9)
			    || (periodMonth > 9 && periodMonth <= 12 && retrieveMonth > 9 && retrieveMonth <= 12))
		    {
			return true;
		    }
		}
		else if(RepConstantsCommon.SNP_FREQUENCY_MONTHLY.equals(frequency) && periodMonth == retrieveMonth)
		{
		    return true;
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return false;
    }

    public String viewSitcomFile() throws JSONException
    {
	try
	{
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> allRec;
	    REP_SNAPSHOT_INFOVO snpInfoVO = null;
	    REP_SITCOM_FILEVO fileVO = null;
	    // adding code to load from data from db
	    SessionCO sessionCO = returnSessionObject();
	    snapshotParameterSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    snapshotParameterSC.setLanguageCode(sessionCO.getLanguage());
	    allRec = snapshotParameterBO.retSnapshotInformationList(snapshotParameterSC, null);
	    // end adding code to load data from db
	    Date period = DateUtil.parseDate(filePeriod, "MM-yyyy");
	    REP_SNAPSHOT_INFORMATIONCO co;
	    /*
	     * read the data from db because of sitcom file id not being loaded
	     * in grid after creation of a new sitcom file(clicked on generate
	     * file).
	     */
	    for(int i = 0; i < allRec.size(); i++)
	    {
		co = allRec.get(i);
		snpInfoVO = co.getRepSnapshotInfoVO();
		// if same frequency then continue below
		if(checkIfSamePeriodFrequency(period, repSnapshotInformationCO.getRepSnapshotParamVO()
			.getSNAPSHOT_FREQUENCY(), co)
			&& snpInfoVO.getSITCOM_FILE_ID() != null
			&& !(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE).equals(snpInfoVO.getSITCOM_FILE_ID()))
		{
		    // get the sitcom file from db
		    snapshotParameterSC.setSITCOM_FILE_ID(snpInfoVO.getSITCOM_FILE_ID());
		    fileVO = snapshotParameterBO.retSitcomFile(snapshotParameterSC);
		    break;
		}

	    }
	    if(fileVO == null)
	    {
		response.addHeader("content-disposition", "attachment;filename=\""
			+ RepConstantsCommon.SNAPSHOT_FTR_SITCOM + (new Date()).toString() + "\"");
		response.getOutputStream().flush();
		response.getOutputStream().close();
	    }
	    else
	    {
		response.addHeader("content-disposition", "attachment;filename=\""
			+ RepConstantsCommon.SNAPSHOT_FTR_SITCOM + (new Date()).toString() + "."
			+ ConstantsCommon.TXT_EXT + "\"");
		response.setContentType("application/txt");
		if(fileVO.getSITCOM_FILE() != null)
		{
		    response.getOutputStream().write(fileVO.getSITCOM_FILE());
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return null;
    }

    /**
     * Method called when viewing already created sitcom
     * @return
     */
    public String viewSitcomFileOfColumn()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    StringBuffer sb = commonRepFuncBO.generateSitcom(repSnapshotInfoVO.getREP_SNAPSHOT_ID(), sessionCO.getCurrentAppName(),
		    repSnapshotParamVO.getREP_ID(), repSnapshotParamVO.getREP_REFERENCE(), sessionCO.getCompanyCode(),
		    repSnapshotInfoVO.getRETREIVE_DATE(), repSnapshotParamVO.getSNAPSHOT_FREQUENCY(), null,null);
	    response.addHeader("content-disposition", "attachment;filename=\"" + repSnapshotInfoVO.getDESCRIPTION()
		    + "." + ConstantsCommon.TXT_EXT + "\"");
	    response.setContentType("application/txt");
	    response.getOutputStream().write(sb.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING));
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return null;
    }

    public String generateFile() throws JSONException
    {
	StringBuffer sb = new StringBuffer();
	try
	{
	    if(updates != null && !updates.equals(""))
	    {
		ArrayList<REP_SNAPSHOT_INFORMATIONCO> lstAllRec;
		ArrayList<REP_SNAPSHOT_INFORMATIONCO> lstAllRecGrid;
		REP_SNAPSHOT_INFORMATIONCO repCO;
		REP_SNAPSHOT_INFORMATIONCO infoCO;
		Date period = null;
		String frequency = null;
		GridUpdates gu = getGridUpdates(updates, REP_SNAPSHOT_INFORMATIONCO.class, true);
		lstAllRecGrid = gu.getLstAllRec();
		SessionCO sessionCO = returnSessionObject();
		snapshotParameterSC.setCOMP_CODE(sessionCO.getCompanyCode());
		snapshotParameterSC.setLanguageCode(sessionCO.getLanguage());
		lstAllRec = snapshotParameterBO.retSnapshotInformationList(snapshotParameterSC, null);
		// before continuing with lstAllRec,update the include file
		// values for its data
		Iterator<REP_SNAPSHOT_INFORMATIONCO> it = lstAllRecGrid.iterator();
		/*
		 * records are being read from db because of issues with the
		 * reload of the grid when a sitcom file has been inserted .when
		 * adding a new file the new added sitcom id is not loaded in
		 * the grid (javascript issue).To adjust this inconvenience load
		 * the data directly from db.The below loop is to set the check
		 * include checkbox to the same value as the sent value from the
		 * grid
		 */
		while(it.hasNext())
		{
		    repCO = it.next();
		    for(int i = 0; i < lstAllRec.size(); i++)
		    {
			infoCO = lstAllRec.get(i);
			if(infoCO.getRepSnapshotInfoVO().getREP_SNAPSHOT_ID().equals(
				repCO.getRepSnapshotInfoVO().getREP_SNAPSHOT_ID())
				&& repCO.getENABLE_SITCOM_YN().equals(ConstantsCommon.TRUE))
			{
			    infoCO.setENABLE_SITCOM_YN(ConstantsCommon.TRUE);
			    break;
			}
		    }
		}
		// end updating include file info
		/*
		 * to store the first fileSitcomId found in order to update it
		 * later in the DB
		 */
		BigDecimal sitcomFileId = null;
		/*
		 * to store the list of snpId that will be looped to generate
		 * the sitcom file
		 */
		it = lstAllRec.iterator();
		ArrayList<REP_SNAPSHOT_INFORMATIONCO> coToGenerateList = new ArrayList<REP_SNAPSHOT_INFORMATIONCO>();
		if(ConstantsCommon.TRUE.equals(shouldCheckPerFreq))
		{
		    while(it.hasNext())
		    {
			repCO = it.next();
			if(repCO.getENABLE_SITCOM_YN().equals(ConstantsCommon.TRUE))
			{
			    period = repCO.getRepSnapshotInfoVO().getRETREIVE_DATE();
			    frequency = repCO.getSNP_FRQ();
			    break;
			}
		    }
		    it = lstAllRec.iterator();
		    while(it.hasNext())
		    {
			repCO = it.next();
			if(repCO.getENABLE_SITCOM_YN().equals(ConstantsCommon.TRUE)
				&& (!checkIfSamePeriodFrequency(period, frequency, repCO)))
			{
			    // Show message that selected snapshots must have
			    // same
			    // period and frequency
			    updates = "1";
			    return SUCCESS;
			}
			/*
			 * if period is the same for the selected
			 * snapshots,check now if all the records for the same
			 * frequency and period are checked
			 */
			else if(checkIfSamePeriodFrequency(period, frequency, repCO)
				&& repCO.getENABLE_SITCOM_YN().equals(RepConstantsCommon.FALSE))
			{
			    updates = "2";
			    return SUCCESS;
			}
		    }
		    return SUCCESS;
		}
		else
		{
		    /*
		     * reached this point => no validation issues encountered
		     * previously.now generate the sitcom for every co in the
		     * list
		     */
		    it = lstAllRec.iterator();
		    while(it.hasNext())
		    {
			repCO = it.next();
			if(repCO.getENABLE_SITCOM_YN().equals(ConstantsCommon.TRUE))
			{
			    coToGenerateList.add(repCO);
			    if(frequency == null)
			    {
				frequency = repCO.getSNP_FRQ();
			    }
			    if(sitcomFileId == null && repCO.getRepSnapshotInfoVO().getSITCOM_FILE_ID() != null)
			    {
				sitcomFileId = repCO.getRepSnapshotInfoVO().getSITCOM_FILE_ID();
			    }
			}
		    }

		    ArrayList<BigDecimal> repSnpIdList = new ArrayList<BigDecimal>();
		    for(int i = 0; i < coToGenerateList.size(); i++)
		    {
			repCO = coToGenerateList.get(i);
			sb.append(
				commonRepFuncBO.generateSitcom(repCO.getRepSnapshotInfoVO().getREP_SNAPSHOT_ID(),
					sessionCO.getCurrentAppName(), repCO.getRepSnapshotParamVO().getREP_ID(), repCO
						.getRepSnapshotParamVO().getREP_REFERENCE(),
					sessionCO.getCompanyCode(), repCO.getRepSnapshotInfoVO().getRETREIVE_DATE(),
					repCO.getRepSnapshotParamVO().getSNAPSHOT_FREQUENCY(), null, null)).toString();
			repSnpIdList.add(repCO.getRepSnapshotInfoVO().getREP_SNAPSHOT_ID());
		    }
		    // save the sitcom in db and show it to the user
		    // if the sitcomFileId is still null => there's not a
		    // pre-existing fileId=>perform an insert
		    CommonLibSC criteria = new CommonLibSC();
		    criteria.setCompanyCode(sessionCO.getCompanyCode());
		    criteria.setBranchCode(sessionCO.getBranchCode());
		    Date runningDate = loginBO.returnRunningDate(criteria.getCompanyCode(), criteria.getBranchCode(),
			    ConstantsCommon.ACC_APP_NAME);
		    REP_SITCOM_FILEVO fileVO = new REP_SITCOM_FILEVO();
		    fileVO.setSITCOM_FILE(sb.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING));
		    fileVO.setCOMP_CODE(sessionCO.getCompanyCode());
		    fileVO.setSITCOM_FREQUENCY(frequency);
		    fileVO.setSITCOM_DATE(runningDate);
		    if(sitcomFileId == null || sitcomFileId.equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
		    {
			snapshotParameterBO.insertSitcomFile(fileVO, coToGenerateList);
		    }
		    // perform an udpate
		    else
		    {
			fileVO.setSITCOM_FILE_ID(sitcomFileId);
			snapshotParameterBO.updateSitcomFile(fileVO, coToGenerateList);
		    }
		    if(fileVO != null)
		    {
			response.addHeader("content-disposition", "attachment;filename=\""
				+ RepConstantsCommon.SNAPSHOT_FTR_SITCOM + (new Date()).toString() + "."
				+ ConstantsCommon.TXT_EXT + "\"");
			response.setContentType("application/txt");
			response.getOutputStream().write(fileVO.getSITCOM_FILE());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		    }

		    return null;
		}

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return null;
    }
     public String snapshotUserLookup()
    {
	try
	{
	    fillSnapshotUserLookup(
		    "/path/snapshotParameter/SnapshotInformationListAction_fillLiveSearchSnapshotUserLookup.action", "");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
     public String fillLiveSearchSnapshotUserLookup() throws BaseException
    {
	try
	{
	    copyproperties(snapshotParameterSC);
	    setSearchFilter(snapshotParameterSC);
	    ArrayList<USRVO> ls = snapshotParameterBO.selectSnapshotUser(snapshotParameterSC);
	    int size = snapshotParameterBO.retSnapshotUserCount(snapshotParameterSC);
	    setRecords(size);
	    setGridModel(ls);
	    return SUCCESS;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }
    
    public String fillSnapshotUserLookup(String gridUrl, String gridCaption) throws JSONException
    {
	try
	{	
	    String[] name    =  { "USER_ID","USER_GRP_ID","USER_GRP_DESC","USER_VALID_DT","USER_STS","RESIGNED" };
	    String[] colType =  { "text","text","text","date" ,"text","text" };
	    String[] titles  =  { getText("reporting.lkpUserId"), getText("reporting.lkpGroupId"),getText("reporting.lkpGroupDescription"),
				    getText("reporting.lkpUserValidityDate"),getText("reporting.lkpStatus"),getText("reporting.lkpResigned") };
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
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
	    lookup(grid, lsGridColumn, null, snapshotParameterSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
        
}
