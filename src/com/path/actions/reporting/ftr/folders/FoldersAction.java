package com.path.actions.reporting.ftr.folders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.ftr.folders.FoldersBO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.AXSVOKey;
import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;

public class FoldersAction extends ReportingLookupBaseAction

{
    
    private final IRP_FOLDERSC irpFoldersc = new IRP_FOLDERSC();
    private String updates;
    private FoldersBO foldersBO;
    private IRP_FOLDERCO foldersCO;
    private IRP_FOLDERVO foldersVO;
    private CommonRepFuncBO commonRepFuncBO;
    private CommonLibBO commonLibBO;
    private String repAppName;
    
    


    
    public String getRepAppName()
    {
        return repAppName;
    }

    public void setRepAppName(String repAppName)
    {
        this.repAppName = repAppName;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO) {
		this.commonLibBO = commonLibBO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO) {
		this.commonRepFuncBO = commonRepFuncBO;
    }

    public Object getModel()
    {
	return irpFoldersc;
    }

    public void setFoldersBO(FoldersBO foldersBO)
    {
	this.foldersBO = foldersBO;
    }

    public IRP_FOLDERCO getFoldersCO()
    {
	return foldersCO;
    }

    public void setFoldersCO(IRP_FOLDERCO foldersCO)
    {
	this.foldersCO = foldersCO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    /**
     * This function loads the GridList
     * 
     * @return
     * @throws JSONException
     */
    public String loadFoldersList() throws JSONException

    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(irpFoldersc);
	    // commonDetailsSC.setCOMP_CODE(sessionCO.getCompanyCode());

	    // commonDetailsSC is needed to be passed so that we pass the
	    // GridParamsSC properties (Grid Properties)
	    irpFoldersc.setAPP_NAME(sessionCO.getCurrentAppName());
	    List lstFolders = foldersBO.loadFoldersList(irpFoldersc);
	    int foldersCount = foldersBO.retFoldersListCount(irpFoldersc);

	    setRecords(foldersCount);
	    setGridModel(lstFolders);
	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return SUCCESS;
    }

    /**
     * This function handles the creation of new folders & the update of existing folders
     * @return
     * @throws JSONException
     */
    public String validateFolders() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String pageRef = get_pageRef();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String appName = sessionCO.getCurrentAppName();
	    String lang = sessionCO.getLanguage();
	    
	    foldersVO = new IRP_FOLDERVO();
	    
	    // Setting VO values from the Getters of the CO
	    foldersVO.setBRIEF_NAME_ENG(foldersCO.getBRIEF_NAME_ENG());
	    foldersVO.setBRIEF_NAME_ARAB(foldersCO.getBRIEF_NAME_ARAB());
	    foldersVO.setFOLDER_REF(foldersCO.getFOLDER_REF());
	    foldersVO.setPARENT_REF(foldersCO.getPARENT_REF());
	    foldersVO.setDISP_ORDER(foldersCO.getDISP_ORDER());
	    foldersVO.setDATE_UPDATED(foldersCO.getDATE_UPDATED());
	    foldersVO.setAPP_NAME(foldersCO.getAPP_NAME());
	    BigDecimal fCode = foldersCO.getFOLDER_CODE();
	    AuditRefCO refCO = initAuditRefCO();
	    // Checking FolderCode to see if its a creation or an update of a folder
	    if(fCode == null || fCode.intValue() == 0)
	    {
		// Insert New Folder
	    BigDecimal folderCode=commonRepFuncBO.retCounterValue("IRP_FOLDER");
	    foldersVO.setFOLDER_CODE(folderCode);
	    
	    
	   //Give Access Privileges : save details in AXS table
	    String usrName=returnUserName();
		AXSVO axsVO=new AXSVO();
		axsVO.setCOMP_CODE(sessionCO.getCompanyCode());
		axsVO.setBRANCH_CODE(sessionCO.getBranchCode());
		axsVO.setAPP_NAME(foldersCO.getAPP_NAME());
		axsVO.setPROG_REF(foldersCO.getFOLDER_REF());
		axsVO.setUSER_ID(usrName);
		axsVO.setCREATED_BY(usrName);
		axsVO.setAUTHORIZED_BY(usrName);
		axsVO.setSTATUS("P");
		axsVO.setDIRECT_ACCESS(usrName);
		axsVO.setDATE_CREATED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
		axsVO.setDATE_AUTHORIZED(commonLibBO.addSystemTimeToDate(sessionCO.getRunningDateRET()));
		axsVO.setTO_BE_DELETED("N");
		
		//foldersCO.setAPP_NAME(sessionCO.getCurrentAppName());
		
	    //apply audit
	  
		refCO.setOperationType(AuditConstant.CREATED);
	    refCO.setKeyRef(AuditConstant.FOLDERS_KEY_REF);
	    foldersVO.setAuditRefCO(refCO);
		foldersBO.newFolder(foldersVO,axsVO,foldersCO,pageRef,compCode,appName,lang);
		
	    }
	    else
	    {
		// Update existing Folder
		foldersVO.setFOLDER_CODE(foldersCO.getFOLDER_CODE());
		
		//apply audit
		refCO.setOperationType(AuditConstant.UPDATE);
	    refCO.setKeyRef(AuditConstant.FOLDERS_KEY_REF);
	    foldersVO.setAuditRefCO(refCO);
		IRP_FOLDERVO oldFolderVO=(IRP_FOLDERVO)returnAuditObject(IRP_FOLDERVO.class);
		foldersVO.setAuditObj(oldFolderVO);
		//foldersCO.setAPP_NAME(sessionCO.getCurrentAppName());
		foldersBO.updateFolder(foldersVO,foldersCO,pageRef,compCode,appName,lang);
		
	    }

	}
	catch(Exception e)
	{
	      handleException(e, null, null);
	}

	return SUCCESS;
    }

    public String checkProgRef() throws JSONException
    {
	try
	{
	    String refStr = getUpdates().toUpperCase();
	    getRepAppName();
	    CommonDetailsSC cdSC = new CommonDetailsSC();
	    cdSC.setAppName(getRepAppName());
	    cdSC.setBRIEF_DESC_ENG(refStr);
	    setUpdates(foldersBO.checkProgRef(cdSC));

	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return SUCCESS;
    }

    public String selectFolder() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("foldersCO");
	    foldersCO = (IRP_FOLDERCO) JSONObject.toBean(jsonModel, IRP_FOLDERCO.class);
	    
	    //retrieve the selected record from the DB
	    CommonDetailsSC sc=new CommonDetailsSC();
	    SessionCO sessionCO = returnSessionObject();
	    sc.setAppName(sessionCO.getCurrentAppName());
	    IRP_FOLDERVO foldVO = new IRP_FOLDERVO();
	    foldVO.setFOLDER_REF(foldersCO.getFOLDER_REF());
	    foldVO.setAPP_NAME(foldersCO.getAPP_NAME());
	    //sc.setCODE(foldersCO.getFOLDER_CODE());
	    foldVO.setFOLDER_CODE(foldersCO.getFOLDER_CODE());
	    foldersCO=foldersBO.retrieveFolder(foldVO);
	    
	 
	    foldVO.setFOLDER_CODE(foldersCO.getFOLDER_CODE());
	    foldVO.setFOLDER_REF(foldersCO.getFOLDER_REF());
	    foldVO.setPARENT_REF(foldersCO.getPARENT_REF());
	    foldVO.setBRIEF_NAME_ENG(foldersCO.getBRIEF_NAME_ENG());
	    foldVO.setBRIEF_NAME_ARAB(foldersCO.getBRIEF_NAME_ARAB());
	    foldVO.setDISP_ORDER(foldersCO.getDISP_ORDER());
	    foldVO.setDATE_UPDATED(foldersCO.getDATE_UPDATED());
	    
	    SYS_PARAM_SCREEN_DISPLAYVO screenDisplayFolder = new SYS_PARAM_SCREEN_DISPLAYVO();
	    HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

	    screenDisplayFolder.setIS_READONLY(BigDecimal.ONE);
	    screenDisplayFolder.setIS_MANDATORY(BigDecimal.ZERO);
	    businessHm.put("foldersCO.APP_NAME", screenDisplayFolder);
	    businessHm.put("foldersCO.PARENT_REF", screenDisplayFolder);
	    businessHm.put("foldersCO.FOLDER_REF", screenDisplayFolder);
	    foldersCO.setBusinessHm(businessHm);
	    if(foldersCO != null && foldersCO.getBusinessHm() != null && foldersCO.getBusinessHm().size() > 0)
	    {
		getAdditionalScreenParams().putAll(foldersCO.getBusinessHm());
	    }
		
	    applyRetrieveAudit(AuditConstant.FOLDERS_KEY_REF, foldVO,foldVO);

	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return "successMnt";
    }
    
    public String emptyFoldersForm()
    {
	try
	{
	    foldersCO = new IRP_FOLDERCO();
	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return "successMnt";
    }
    
    public String checkDetails() throws JSONException
    {
	try
	{
	    foldersCO = new IRP_FOLDERCO();
	    //SessionCO sessionCO = returnSessionObject();
	    String progRef = getUpdates();
	    foldersCO.setAPP_NAME(getRepAppName());
	    foldersCO.setFOLDER_REF(progRef);
	    setUpdates(foldersBO.checkDetails(foldersCO));
	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return SUCCESS;
    }
    
    public String deleteFolder() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("foldersCO");
	    foldersCO = (IRP_FOLDERCO) JSONObject.toBean(jsonModel, IRP_FOLDERCO.class);
	    
	    SessionCO sessionCO = returnSessionObject();
	    foldersCO.setAPP_NAME(getRepAppName());
	    
	    foldersVO = new IRP_FOLDERVO();
	    foldersVO.setFOLDER_CODE(foldersCO.getFOLDER_CODE());
	    
	    //apply audit
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.DELETE);
	    refCO.setKeyRef(AuditConstant.FOLDERS_KEY_REF);
	    foldersVO.setAuditRefCO(refCO);
	    
	    AXSVOKey  axsVO = new AXSVO();
	    axsVO.setAPP_NAME(getRepAppName());
	    axsVO.setCOMP_CODE(sessionCO.getCompanyCode());
	    axsVO.setBRANCH_CODE(sessionCO.getBranchCode());
	    axsVO.setPROG_REF(foldersCO.getFOLDER_REF());
	    axsVO.setUSER_ID(returnUserName());
	    
	    //Delete Folder
	    foldersBO.deleteFolder(foldersVO,axsVO,foldersCO);
	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return SUCCESS;
    }

    public String loadFoldersMaint() throws Exception
    {
    	set_showSmartInfoBtn("false");
        set_searchGridId("foldersGridId");
	set_showNewInfoBtn("true");
    	return SUCCESS;
    }
}
