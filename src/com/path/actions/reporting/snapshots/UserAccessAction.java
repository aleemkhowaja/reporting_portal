package com.path.actions.reporting.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.admin.user.UsrSC;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.snapshots.USER_ACCESSCO;

public class UserAccessAction extends ReportingLookupBaseAction
{

    private SnapShotBO snapShotBO;
    private USER_ACCESSCO userAccessCO = new USER_ACCESSCO();
    private UsrSC usrSC = new UsrSC();
    private String userId;
    private String viewedUserIdStr;
    private String branchIdStr;
    private String updatesUserViewedList = "";
    private String updatesBranchList = "";
    private String trxNumber;
    private String dateUp;

    public String getDateUp()
    {
	return dateUp;
    }

    public void setDateUp(String dateUp)
    {
	this.dateUp = dateUp;
    }

    public USER_ACCESSCO getUserAccessCO()
    {
	return userAccessCO;
    }

    public void setUserAccessCO(USER_ACCESSCO userAccessCO)
    {
	this.userAccessCO = userAccessCO;
    }

    public String getTrxNumber()
    {
	return trxNumber;
    }

    public void setTrxNumber(String trxNumber)
    {
	this.trxNumber = trxNumber;
    }

    public String getUpdatesBranchList()
    {
	return updatesBranchList;
    }

    public void setUpdatesBranchList(String updatesBranchList)
    {
	this.updatesBranchList = updatesBranchList;
    }

    public String getBranchIdStr()
    {
	return branchIdStr;
    }

    public void setBranchIdStr(String branchIdStr)
    {
	this.branchIdStr = branchIdStr;
    }

    public String getUpdatesUserViewedList()
    {
	return updatesUserViewedList;
    }

    public void setUpdatesUserViewedList(String updatesUserViewedList)
    {
	this.updatesUserViewedList = updatesUserViewedList;
    }

    public String getViewedUserIdStr()
    {
	return viewedUserIdStr;
    }

    public void setViewedUserIdStr(String viewedUserIdStr)
    {
	this.viewedUserIdStr = viewedUserIdStr;
    }

    public String getUserId()
    {
	return userId;
    }

    public void setUserId(String userId)
    {
	this.userId = userId;
    }

    public UsrSC getUsrSC()
    {
	return usrSC;
    }

    public void setUsrSC(UsrSC usrSC)
    {
	this.usrSC = usrSC;
    }

    public void setSnapShotBO(SnapShotBO snapShotBO)
    {
	this.snapShotBO = snapShotBO;
    }

    public String loadUserAccess() throws BaseException
    {
	set_showSmartInfoBtn("false");
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SessionCO sessionCO = (SessionCO) session.get(ConstantsCommon.SESSION_VO_ATTR);
	userAccessCO.setEMP_COMP_CODE(sessionCO.getCompanyCode());
	userAccessCO.setUSER_ID(sessionCO.getUserName());
	BigDecimal eodViewer = snapShotBO.retEodViewer(userAccessCO);
	// eodViewer=new BigDecimal(1);
	userAccessCO.setEodViewer(eodViewer);
	repSessionCO.getViewedUserAccessMap().clear();
	repSessionCO.getViewedBranchMap().clear();
	return "userAccess";
    }

    /**
     * load user access grid.
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadUserAccessGrid() throws BaseException
    {

	try
	{
	    copyproperties(usrSC);
	    List<USER_ACCESSCO> lst = snapShotBO.retUserAccessList(usrSC);
	    int lstCnt = snapShotBO.retUserAccessListCount(usrSC);
	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	  //  log.error(e, "ERROR in loadUserAccessGrid ");
	    handleException(e, "Error in loadUserAccessGrid grid", "");
	}
	return SUCCESS;
    }

    /**
     * Load viewed user access grid
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String fillUserAccessHm() throws BaseException
    {

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	usrSC.setUser_id(userId);
	List<USER_ACCESSCO> lstViewedUsers = null;
	List<USER_ACCESSCO> lstBranch = null;
	if(userId != null)
	{
	    repSessionCO.getViewedUserAccessMap().clear();
	    lstViewedUsers = snapShotBO.retAllViewedUserList(usrSC);
	    lstBranch = snapShotBO.retSelectedBranchList(usrSC);
	    for(int i = 0; i < lstViewedUsers.size(); i++)
	    {
		dateUp = DateUtil.format(lstViewedUsers.get(0).getDATE_UPDATED(), "dd/MM/yyyy hh:mm:ss");
		repSessionCO.getViewedUserAccessMap().put(userId + "_" + lstViewedUsers.get(i).getUSER_ID(),
			lstViewedUsers.get(i));
	    }
	    repSessionCO.getViewedBranchMap().clear();
	    repSessionCO.getViewedBranchMap().put(userId, lstBranch);

	    trxNumber = StringUtils.leftPad(userId, 8, "0");
	    trxNumber = "00000000" + trxNumber;
	    setAuditTrxNbr(trxNumber);

	}
	return SUCCESS;
    }

    /**
     * Load viewed user access grid
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadViewedUserGrid() throws BaseException
    {
	try
	{

	    if(userId != null)
	    {
		if("-1".equals(userId))
		{
		    usrSC.setUser_id(null);
		}
		else
		{
		    usrSC.setUserId(userId);
		}
	    }

	    // usrSC.setUserId(userId);
	    copyproperties(usrSC);
	    List<USER_ACCESSCO> lst = snapShotBO.retViewedUserList(usrSC);
	    int lstCnt = snapShotBO.retViewedUserListCount(usrSC);

	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in loadViewedUserGrid ");
	    handleException(e, "Error in loadViewedUserGrid grid", "");
	}
	return SUCCESS;
    }

    public String putInAccessUserHashMap() throws Exception
    {
	if(updatesUserViewedList != null && !updatesUserViewedList.equals(""))
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    GridUpdates gu = getGridUpdates(updatesUserViewedList, USER_ACCESSCO.class, true);
	    gu.getLstAllRec();
	    for(int i = 0; i < gu.getLstAllRec().size(); i++)
	    {
		//USER_ACCESSCO usrCO = new USER_ACCESSCO();
		USER_ACCESSCO usrCO  = (USER_ACCESSCO) gu.getLstAllRec().get(i);
		String usrCOKey = getUserId() + "_" + usrCO.getUSER_ID();
		//USER_ACCESSCO hmUserCO = new USER_ACCESSCO();
		String hmKey = null;
		boolean existKey = false;
		for(Entry<String, USER_ACCESSCO> entry : repSessionCO.getViewedUserAccessMap().entrySet())
		{
		    hmKey = entry.getKey();
		    //hmUserCO = entry.getValue();
		    if(usrCOKey.equals(hmKey))
		    {
			existKey = true;
			break;
		    }

		}
		if(existKey)
		{
		    if(!usrCO.getIntCheckBox())
		    {
			repSessionCO.getViewedUserAccessMap().remove(hmKey);
			if(repSessionCO.getViewedUserAccessMap().size() == 0)
			{
			    USER_ACCESSCO emptyUserCO = new USER_ACCESSCO();
			    repSessionCO.getViewedUserAccessMap().put(hmKey, emptyUserCO);
			    break;
			}
		    }
		}
		else
		{
		    if(usrCO.getIntCheckBox())
		    {
			repSessionCO.getViewedUserAccessMap().put(usrCOKey, usrCO);
		    }
		}

	    }
	}
	return SUCCESS;
    }

    public String putInBranchHashMap() throws Exception
    {
	if(updatesBranchList != null && !updatesBranchList.equals(""))
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    GridUpdates gu = getGridUpdates(updatesBranchList, USER_ACCESSCO.class, true);
	    List<USER_ACCESSCO> lstSelectedBranch = new ArrayList<USER_ACCESSCO>();
	    for(int i = 0; i < gu.getLstAllRec().size(); i++)
	    {
		USER_ACCESSCO usrCO = (USER_ACCESSCO) gu.getLstAllRec().get(i);
		boolean existKey = false;

		for(Entry<String, List<USER_ACCESSCO>> entry : repSessionCO.getViewedBranchMap().entrySet())
		{
		    lstSelectedBranch = entry.getValue();
		}

		for(int k = 0; k < lstSelectedBranch.size(); k++)
		{

		    if(lstSelectedBranch.get(k).getEMP_BRANCH_CODE().equals(usrCO.getEMP_BRANCH_CODE()))
		    {
			if(!usrCO.getBranchCheckBox())
			{
			    lstSelectedBranch.remove(k);
			}
			existKey = true;
			break;
		    }

		}
		if(!existKey && usrCO.getBranchCheckBox())
		{
			lstSelectedBranch.add(usrCO);
		}

	    }
	    repSessionCO.getViewedBranchMap().put(getUserId(), lstSelectedBranch);
	}
	return SUCCESS;
    }

    public String retCheckboxStr() throws BaseException
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	usrSC.setUser_id(userId);
	String[] oldUserId = null;
	String key = null;
	if(repSessionCO.getViewedUserAccessMap().size() > 0)
	{

	    for(Entry<String, USER_ACCESSCO> entry : repSessionCO.getViewedUserAccessMap().entrySet())
	    {
		key = entry.getKey();
		oldUserId = key.split("_");
		USER_ACCESSCO myCO = entry.getValue();
		if(myCO.getIntCheckBox() != null)
		{
		    viewedUserIdStr = oldUserId[1] + "," + viewedUserIdStr;
		}
	    }
	    if(viewedUserIdStr != null && viewedUserIdStr.endsWith(",null"))
	    {
		viewedUserIdStr = viewedUserIdStr.substring(0, viewedUserIdStr.length() - 5);
	    }
	}

	for(Entry<String, List<USER_ACCESSCO>> entry : repSessionCO.getViewedBranchMap().entrySet())
	{
	    key = entry.getKey();
	    if(entry.getValue() != null)
	    {
		List<USER_ACCESSCO> lstSelectedBranch = entry.getValue();
		for(int i = 0; i < lstSelectedBranch.size(); i++)
		{
		    if(lstSelectedBranch.get(i).getEMP_BRANCH_CODE() != null)
		    {
			branchIdStr = lstSelectedBranch.get(i).getEMP_BRANCH_CODE() + "," + branchIdStr;
		    }
		}
	    }
	}
	if(branchIdStr != null && branchIdStr.endsWith(",null"))
	{
	    branchIdStr = branchIdStr.substring(0, branchIdStr.length() - 5);
	}

	return SUCCESS;
    }

    /**
     * Save user access grid
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String saveUserAccess() throws BaseException
    {

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.getViewedUserAccessMap();
	List<USER_ACCESSCO> lstViewedUsers = new ArrayList<USER_ACCESSCO>();
	List<USER_ACCESSCO> lstSelectedBranch = new ArrayList<USER_ACCESSCO>();
	SessionCO sessionCO = (SessionCO) session.get(ConstantsCommon.SESSION_VO_ATTR);

	AuditRefCO refCO = initAuditRefCO();

	for(Entry<String, USER_ACCESSCO> entry : repSessionCO.getViewedUserAccessMap().entrySet())
	{
	    //USER_ACCESSCO userCO = new USER_ACCESSCO();

	    // dateUp =
	    // DateUtil.format(lstViewedUsers.get(0).getDATE_UPDATED(),"dd/MM/yyyy hh:mm:ss");
	    // DateUtil.parseDate(getDateUp(),"dd/MM/yyyy hh:mm:ss");
	    USER_ACCESSCO userCO = entry.getValue();
	    userCO.setViewerUserId(userId);
	    String strDateUp = getDateUp();
	    if(!("").equals(strDateUp))// && !strDateUp.equals(null)
	    {
		userCO.setDATE_UPDATED(DateUtil.parseDate(getDateUp(), "dd/MM/yyyy hh:mm:ss"));
	    }
	    userCO.setEMP_COMP_CODE(sessionCO.getCompanyCode());
	    if(userAccessCO.getEodViewer().equals(BigDecimal.ONE))
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.USER_ACCESS_KEY_REF);
		userCO.setEodViewer(userAccessCO.getEodViewer());
		userCO.setAuditRefCO(refCO);
		lstViewedUsers.add(userCO);
	    }
	    else
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.USER_ACCESS_BRANCH_KEY_REF);
		userCO.setEodViewer(userAccessCO.getEodViewer());
		userCO.setAuditRefCO(refCO);
		lstViewedUsers.add(userCO);
	    }

	}

	for(Entry<String, List<USER_ACCESSCO>> entry : repSessionCO.getViewedBranchMap().entrySet())
	{
	    lstSelectedBranch = entry.getValue();
	}

	try
	{
	    if(lstViewedUsers.isEmpty())
	    {
		USER_ACCESSCO userCO = new USER_ACCESSCO();
		userCO.setViewerUserId(userId);
		snapShotBO.deleteUserAccess(userCO);
	    }
	    else
	    {
		snapShotBO.saveUserAccess(lstViewedUsers, lstSelectedBranch);
	    }
	    
	    repSessionCO.getViewedUserAccessMap().clear();
	    repSessionCO.getViewedBranchMap().clear();
	}
	catch(BaseException e)
	{
	    if(e.getMessage().equals("emptyBr_key"))
	    {
		 handleException(null,getText("userAccess.emptyBr_key"),null);
	    }
	    else
	    {
		handleException(e,null,null);
	    }
	}

	return SUCCESS;
    }

    /**
     * Load branch user access grid
     * 
     * @return SUCCESS
     * @throws BaseException
     */
    public String loadBranchGrid() throws BaseException
    {
	try
	{

	    SessionCO sessionCO = (SessionCO) session.get(ConstantsCommon.SESSION_VO_ATTR);
	    usrSC.setCompCode(sessionCO.getCompanyCode());

	    if(userId != null)
	    {
		if("-1".equals(userId))
		{
		    usrSC.setUser_id(null);
		}
		else
		{
		    usrSC.setUserId(userId);
		}
	    }

	    // usrSC.setUserId(userId);
	    copyproperties(usrSC);

	    usrSC.setIsRTLDir(sessionCO.getIsRTLDir());
	    List<USER_ACCESSCO> lst = snapShotBO.retBranchGridList(usrSC);
	    int lstCnt = snapShotBO.retBranchGridListCount(usrSC);
	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in loadBranchGrid ");
	    handleException(e, "Error in loadBranchGrid grid", "");
	}
	return SUCCESS;
    }

}
