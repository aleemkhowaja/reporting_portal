package com.path.actions.reporting.common;

import com.path.bo.reporting.common.RepMenuBO;
import com.path.reporting.struts2.lib.common.base.ReportingBaseMenuAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;

public class RepMenuAction extends ReportingBaseMenuAction {
	private RepMenuBO repMenuBO;
	private String menuId;
	private String repQryName;
	
	public String getRepQryName() {
		return repQryName;
	}

	public void setRepQryName(String repQryName) {
		this.repQryName = repQryName;
	}

	public RepMenuBO getRepMenuBO()
	{
		return repMenuBO;
	}

	public void setRepMenuBO(RepMenuBO repMenuBO)
	{
		this.repMenuBO = repMenuBO;
	}
	 
	public String generateQueriesMenu()
	{
		try
		{	
			ReportingSessionCO repSessionCO = returnReportingSessionObject();
			if(menuId==null || "".equals(menuId))
			{
					menuId = "ROOT";
					root = true;
			}			
			IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
			String[] menuTrans = {getText("query.fields"),getText("sch.parameters")};
			menuData = repMenuBO.queriesMenu(menuId, reportCO.getQueriesList(),menuTrans);
		}
		catch(Exception e)
		{
			//log.error(e, "Error in method generateQueriesMenu in RepMenuAction");
			handleException(e, "Error Loading the menu","queriesMenu.exceptionMsg._key");
		}		
		return SUCCESS;
	}	

	public String getMenuId()
	{
		return menuId;
	}

	public void setMenuId(String menuId)
	{
		this.menuId = menuId;
	}
	
    /*
     * Adding the default execute method when loading of the MenuQueries.jsp since it
     * was not exist before and we need it to set the the menu header from the
     * server side
     */
    public String execute() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	setRepQryName(reportCO.getREPORT_NAME());
	return SUCCESS;

    }
}
