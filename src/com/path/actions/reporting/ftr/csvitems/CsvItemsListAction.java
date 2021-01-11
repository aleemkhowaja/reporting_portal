package com.path.actions.reporting.ftr.csvitems;

import java.util.Date;
import java.util.List;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ftr.csvitems.CsvItemsBO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.GridUpdates;
import com.path.struts2.lib.common.base.GridBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsListAction.java used to
 */
public class CsvItemsListAction extends GridBaseAction
{

    private CsvItemsBO csvItemsBO;

    CsvItemsSC csvItemsSC = new CsvItemsSC();
    private String reportRef;
    private String updates;
    private Date dateUpdated;
    private String deletedCsvRow;
    

    
    public String getDeletedCsvRow()
    {
        return deletedCsvRow;
    }

    public void setDeletedCsvRow(String deletedCsvRow)
    {
        this.deletedCsvRow = deletedCsvRow;
    }

    public Date getDateUpdated()
    {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdates()
    {
        return updates;
    }

    public void setUpdates(String updates)
    {
        this.updates = updates;
    }

    public String getReportRef()
    {
        return reportRef;
    }

    public void setReportRef(String reportRef)
    {
        this.reportRef = reportRef;
    }

    public CsvItemsSC getCsvItemsSC()
    {
	return csvItemsSC;
    }

    public void setCsvItemsSC(CsvItemsSC csvItemsSC)
    {
	this.csvItemsSC = csvItemsSC;
    }

    public void setCsvItemsBO(CsvItemsBO csvItemsBO)
    {
	this.csvItemsBO = csvItemsBO;
    }

    public Object getModel()
    {
	return csvItemsSC;
    }
    /**
     * load csv Items grid
     */
    public String loadCsvItemsGrid() throws BaseException
    {
	try
	{
	    copyproperties(csvItemsSC);
	    SessionCO sessionCO = returnSessionObject();
	    csvItemsSC.setCompCode(sessionCO.getCompanyCode());
	    List<CsvItemsCO> lst = csvItemsBO.retCsvItemsList(csvItemsSC);
	    int lstCnt = csvItemsBO.retCsvItemsCount(csvItemsSC);
	    setRecords(lstCnt);
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    handleException(e, "Error in load csv Items grid", "");
	}
	return SUCCESS;
    }

    /**
     * load csv Items grid by report ref
     */
    public String loadCsvItemsByRepGrid() throws BaseException
    {
	try
	{
	    copyproperties(csvItemsSC);
	    List<CsvItemsCO> lst = null;
	    int lstCnt = 0; 
	    if(reportRef!=null)
	    {
		csvItemsSC.setREP_REF(reportRef);
		SessionCO sessionCO = returnSessionObject();
		csvItemsSC.setCompCode(sessionCO.getCompanyCode());
		lst= csvItemsBO.retCsvItemsByRepList(csvItemsSC);
		lstCnt =csvItemsBO.retCsvItemsByRepCount(csvItemsSC);
	    }
	    setRecords(lstCnt);
	    setGridModel(lst);
	    

	}
	catch(Exception e)
	{
	    handleException(e, "Error in load csv Items grid", "");
	}
	return SUCCESS;
    }
    
    
    public String saveCsvItems() throws Exception
    {
	if(updates != null && !updates.equals(""))
	{
	    try
	    {
		GridUpdates gu = getGridUpdates(updates, CsvItemsCO.class, true);
		List<CsvItemsCO> lstCsvItemsDeleted = null;
		SessionCO sessionCO = returnSessionObject();
		AuditRefCO refCO = initAuditRefCO();
		 
		String appName   	= sessionCO.getCurrentAppName();
		String lang		= sessionCO.getLanguage();
		
		//apply audit
		//refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.CSV_ITEMS_KEY_REF);
		
		if(deletedCsvRow != null && !deletedCsvRow.equals(""))
		{
		    GridUpdates guDel = getGridUpdates(deletedCsvRow, CsvItemsCO.class, false);
		    lstCsvItemsDeleted =guDel.getLstDelete();
		}
		
		List<CsvItemsCO> lstCsvItems = gu.getLstAllRec(); 
		csvItemsBO.saveCsvItems(lstCsvItems,lstCsvItemsDeleted,refCO,get_pageRef(), getReportRef(), sessionCO.getCompanyCode(), getDateUpdated(),appName,lang);
	    }

	    catch(Exception e)
	    {
		handleException(e, null, null);
	    }
	}
	return SUCCESS;
    }
   

    public String deleteAllCsvItemByRep() throws Exception
    {
	SessionCO sessionCO = returnSessionObject();
	csvItemsBO.deleteAllCsvItemByRep(getReportRef(), sessionCO.getCompanyCode());
	return SUCCESS;
    }
}
