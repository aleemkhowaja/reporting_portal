package com.path.actions.reporting.designer;

import java.util.ArrayList;
import java.util.List;

import com.path.bo.reporting.designer.DesignerBO;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_CLIENT_REPORTCO;
import com.path.vo.reporting.PTH_CLIENTSCO;
import com.path.vo.reporting.PTH_CLIENTSSC;

public class ReportClientAction extends ReportingLookupBaseAction
{
    private PTH_CLIENTSSC pthClientSC = new PTH_CLIENTSSC();
    private DesignerBO designerBO;
    public String updatesRepClient;
    




    public String openRepClient() throws Exception
    {
  	return "repClient";
    }
    
    
    public String loadClientRepList() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reptCO = repSessionCO.getReportCO();

	    List<IRP_CLIENT_REPORTCO> repClientLst = reptCO.getRepClientList();
	    copyproperties(pthClientSC);
	    int nbRec = pthClientSC.getNbRec();
	    int recToSkip = pthClientSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(repClientLst.size() < maxRec)
	    {
		maxRec = repClientLst.size();
	    }
	    ArrayList<IRP_CLIENT_REPORTCO> lst = new ArrayList<IRP_CLIENT_REPORTCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add(repClientLst.get(i));
	    }

	    setRecords(reptCO.getRepClientList().size());
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error("Error in loadClientRepList() method in ReportClientAction");
	    handleException(e, "Error in load loadClientRepList grid", "Error in load loadClientRepList grid");
	}
	return "grid";
    }
    
    
    public String clientRepLkp() throws Exception
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "pthClientsVO.CLIENT_ACRONYM", "pthClientsVO.CLIENT_NAME" };
	    String[] colType = { "text", "text" };
	    String[] titles = { getText("upDown.cltAcro"), getText("upDown.cltName") };
	    //String[] titles = { getText("proc.procName"), getText("proc.procDesc") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(getText("upDown.cltAcro"));
	    grid.setRowNum("10");
	    grid.setUrl("/path/designer/repClient_retRepClientLkpList.action?_pageRef=" + get_pageRef());

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
	    lookup(grid, lsGridColumn, null, pthClientSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public String retRepClientLkpList() throws Exception
    {
	try
	{
	    copyproperties(pthClientSC);
	    setSearchFilter(pthClientSC);

	    List<PTH_CLIENTSCO> repClientList = designerBO.retRepClientLst(pthClientSC);
	    int repClientCount = designerBO.retRepClientLstCount(pthClientSC);

	    setRecords(repClientCount);
	    setGridModel(repClientList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";

    }
    
    
    public String putInRepList() throws Exception
    {
	if(updatesRepClient != null && !updatesRepClient.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updatesRepClient, IRP_CLIENT_REPORTCO.class, true);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.getReportCO().setRepClientList(gu.getLstAllRec());
	}
	return "grid";
    }
	
    
    public void setDesignerBO(DesignerBO designerBO)
    {
        this.designerBO = designerBO;
    }


    public PTH_CLIENTSSC getPthClientSC()
    {
        return pthClientSC;
    }


    public void setPthClientSC(PTH_CLIENTSSC pthClientSC)
    {
        this.pthClientSC = pthClientSC;
    }
    
    public Object getModel()
    {
	return pthClientSC;
    }


    public String getUpdatesRepClient()
    {
        return updatesRepClient;
    }


    public void setUpdatesRepClient(String updatesRepClient)
    {
        this.updatesRepClient = updatesRepClient;
    }
    

}
