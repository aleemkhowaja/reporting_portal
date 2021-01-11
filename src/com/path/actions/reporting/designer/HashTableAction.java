package com.path.actions.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.designer.DesignerBO;
import com.path.dbmaps.vo.IRP_HASH_TABLEVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_HASH_TABLESC;

public class HashTableAction extends ReportingLookupBaseAction
{

    private DesignerBO designerBO;
    private IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
    IRP_HASH_TABLESC irpHashTblSC = new IRP_HASH_TABLESC();
    public String updates;
    public BigDecimal hashTblId;
    
    
    
    
    public BigDecimal getHashTblId()
    {
        return hashTblId;
    }

    public void setHashTblId(BigDecimal hashTblId)
    {
        this.hashTblId = hashTblId;
    }

    public String getUpdates()
    {
        return updates;
    }

    public void setUpdates(String updates)
    {
        this.updates = updates;
    }

    public IRP_HASH_TABLESC getIrpHashTblSC()
    {
        return irpHashTblSC;
    }

    public void setIrpHashTblSC(IRP_HASH_TABLESC irpHashTblSC)
    {
        this.irpHashTblSC = irpHashTblSC;
    }

    public IRP_AD_HOC_REPORTSC getReportSC()
    {
        return reportSC;
    }

    public void setReportSC(IRP_AD_HOC_REPORTSC reportSC)
    {
        this.reportSC = reportSC;
    }


    public void setDesignerBO(DesignerBO designerBO)
    {
        this.designerBO = designerBO;
    }
    
 
    public String openHashTbl()throws Exception
    {
  	return "hashTbl";
    }
    
    
    public String loadHashTblList()throws Exception
    {
  	try
  	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reptCO=repSessionCO.getReportCO();
	    
	    List<IRP_HASH_TABLECO> hashTblLst=reptCO.getHashTblList();
	    IRP_HASH_TABLESC irpHashTblSC = new IRP_HASH_TABLESC();
	    copyproperties(irpHashTblSC);
		int nbRec=irpHashTblSC.getNbRec();
		int recToSkip=irpHashTblSC.getRecToskip();
		int maxRec=nbRec+recToSkip;
		if(hashTblLst.size()<maxRec)
		{
			maxRec=hashTblLst.size();
		}
		ArrayList<IRP_HASH_TABLECO> lst=new ArrayList<IRP_HASH_TABLECO>();
		for(int i=recToSkip;i<maxRec;i++)
		{
			lst.add(hashTblLst.get(i));
		}
		
		setRecords(reptCO.getHashTblList().size());
		setGridModel(lst); 

  	}
  	catch(Exception e)
  	{
  	    //log.error("Error in loadHashTblList() method in HashTableAction");
  	    handleException(e, "Error in load loadHashTblList grid","Error in load loadHashTblList grid");
  	}
  	return SUCCESS;
    }
    
    
    public String hashTblLookup()throws Exception
    {
	try
	{
	    fillLookup("/path/designer/hashTbl_fillHashTblLookupGrid", "hash table");
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup","fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }
    
	public String fillLookup(String gridUrl,String gridCaption) throws JSONException
	{
		try
		{
			// Design the Grid by defining the column model and column names
			String[] name = {"HASH_TABLE_ID","HASH_TABLE_NAME"};
			String[] colType = {"number","text"};
			String[] titles = {"HASH_TABLE_ID","HASH_TABLE_NAME"};
			
			//Defining the Grid
			LookupGrid grid = new LookupGrid();
		//	grid.setId(gridId);
			grid.setCaption(gridCaption);
			grid.setRowNum("10");
		//	grid.setFilter(false);
			grid.setUrl(gridUrl);
			
			List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
			
			int cols = name.length;
			for(int i=0;i<cols;i++){
				//Defining each column
				LookupGridColumn gridColumn = new LookupGridColumn();
				gridColumn.setName(name[i]);
				gridColumn.setIndex(name[i]);
				gridColumn.setColType(colType[i]);
				gridColumn.setTitle(titles[i]);
				gridColumn.setSearch(true);
				
				if(name[i].equals("value_date")){
					gridColumn.setFormatter("date");
					gridColumn.setEditrules("{date:true}");
				}
				//adding column to the list
				lsGridColumn.add(gridColumn);
			}
			lookup(grid, lsGridColumn,null,irpHashTblSC);
		}
		catch(Exception e)
		{
			//log.error("Error filling reports lookup");
			handleException(e, "Error filling reports lookup","fillReportsLkp.exceptionMsg._key");
		}
		return SUCCESS;
	}
	    
	
	/**
	 * Construct the grid of hash tbl lookup.
	 * @return SUCCESS
	 * @throws BaseException
	*/
	public String fillHashTblLookupGrid()throws BaseException
	{
	
	    try
	    {
		copyproperties(irpHashTblSC);
		setSearchFilter(irpHashTblSC);
		int lstCnt=designerBO.retHashTablListCount(irpHashTblSC);
		List<IRP_HASH_TABLEVO>lst=designerBO.retHashTablList(irpHashTblSC);
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		List<IRP_HASH_TABLECO> hashTableList = repSessionCO.getReportCO().getHashTblList();
		for(int i=0;i<hashTableList.size();i++)
		{
		    for(int j=0;j<lst.size();j++)
		    {
			if(lst.get(j).getHASH_TABLE_ID().intValue()==hashTableList.get(i).getIrpHashTableVO().getHASH_TABLE_ID().intValue())
			{
    			      lst.remove(j);
			}
		    }
		}
		
		setRecords(lstCnt);
		setGridModel(lst);	
		}
		catch(Exception e)
		{
		    //log.error(e, "ERROR in fillHashTblLookupGrid AppConnectionAction");
		    handleException(e, "Error in load fillHashTblLookupGrid lookup grid","fillHashTblLookupGrid.exceptionMsg._key");
		}
		return SUCCESS;
	    } 
	
	
	public String putInHashTblMap()throws Exception
	{
		if(updates!=null && !updates.equals(""))
		{
			GridUpdates gu = getGridUpdates(updates, IRP_HASH_TABLECO.class,true);
			ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
			repSessionCO.getReportCO().setHashTblList(gu.getLstAllRec());
		}
		return SUCCESS;
	}
	
	public String delFrmHashTblMap()throws Exception
	{
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		for(int i=0;i<repSessionCO.getReportCO().getHashTblList().size();i++)
		{
		    if(repSessionCO.getReportCO().getHashTblList().get(i).getIrpHashTableVO().getHASH_TABLE_ID().intValue()==hashTblId.intValue())
		    {
			repSessionCO.getReportCO().getHashTblList().remove(i);
		    }
		}
		return SUCCESS;
	}
	
	
}
