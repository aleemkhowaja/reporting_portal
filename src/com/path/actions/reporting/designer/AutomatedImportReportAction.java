package com.path.actions.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.AUTOMATED_IMPORT_REPORTSCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;

public class AutomatedImportReportAction extends ReportingLookupBaseAction
{
    private ArrayList<SYS_PARAM_LOV_TRANSVO> actionCmb;
    private String updates;
    private String update;
    private CommonLookupBO commonLookupBO;
    private DesignerBO designerBO;
    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
    
    public String getUpdate()
    {
        return update;
    }

    public void setUpdate(String update)
    {
        this.update = update;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
        this.designerBO = designerBO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }



    public ArrayList<SYS_PARAM_LOV_TRANSVO> getActionCmb()
    {
        return actionCmb;
    }

    public void setActionCmb(ArrayList<SYS_PARAM_LOV_TRANSVO> actionCmb)
    {
        this.actionCmb = actionCmb;
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
     * Method called on the opening of the screen.hides smart,audit buttons
     */
    public String execute() 
    {
	try
	{
	    set_showSmartInfoBtn(ConstantsCommon.FALSE);
	    set_showRecordLogsBtn(ConstantsCommon.FALSE);
	    set_enableAudit(Boolean.valueOf(ConstantsCommon.FALSE));
	    SYS_PARAM_SCREEN_DISPLAYVO autoImpPath = new SYS_PARAM_SCREEN_DISPLAYVO();
	    autoImpPath.setIS_MANDATORY(BigDecimal.ONE);
	    getAdditionalScreenParams().put("autoImpId", autoImpPath);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return RepConstantsCommon.SUCCESS_AUTO_IMP;
    }

    /**
     * Method that loads values for grid action's drpdwn
     * 
     * @return
     * @throws Exception
     */
    public String loadAutomatedImpCmb() 
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.REP_IMPORT_OPTIONS);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    actionCmb = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }


    /**
     * Method that loads the zip files' grid's data
     * @return
     */
    public String loadAutomatedImportGrid() 
    {
	try
	{
	    ArrayList<AUTOMATED_IMPORT_REPORTSCO> autoImpRepsList;
	    // if updates is equal to one means view button clicked
	    if(RepConstantsCommon.ONE.equals(updates))
	    {
		autoImpRepsList = retAutoImpGridsRecords(update);
	    }
	    else
	    {
		// on load of the page
		autoImpRepsList = new ArrayList<AUTOMATED_IMPORT_REPORTSCO>();
	    }
	    setRecords(autoImpRepsList.size());
	    setGridModel(autoImpRepsList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    /**
     * Method that gets the list of reports from repository
     * 
     * @return
     */
    public ArrayList<AUTOMATED_IMPORT_REPORTSCO> retAutoImpGridsRecords(String path)
    {
	ArrayList<AUTOMATED_IMPORT_REPORTSCO> autoImpRepList = new ArrayList<AUTOMATED_IMPORT_REPORTSCO>();
	try
	{
	    autoImpRepList = designerBO.retAutoImpRepGridRecords(path);
	}
	catch(BaseException e)
	{
	    if(RepConstantsCommon.INVALID_PATH.equals(e.getMsgIdent()))
	    {
		handleException(null, getText(e.getMsgIdent()), null);
	    }
	    else
	    {
		handleException(e, null, null);
	    }
	}
	return autoImpRepList;
    }
    
    /**
     * grid component
     */
    public Object getModel()
    {
	return repSC;
    }
}
