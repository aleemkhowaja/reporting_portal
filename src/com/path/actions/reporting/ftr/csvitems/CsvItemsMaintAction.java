package com.path.actions.reporting.ftr.csvitems;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ftr.csvitems.CsvItemsBO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.SessionCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsMaintAction.java used to
 */
public class CsvItemsMaintAction extends ReportingLookupBaseAction
{
    private CsvItemsBO csvItemsBO;
    private CsvItemsSC csvItemsSC = new CsvItemsSC();
    private String reportRef;
    CsvItemsCO csvItemsCO = new CsvItemsCO();
    
    
    
    

    public CsvItemsCO getCsvItemsCO()
    {
        return csvItemsCO;
    }

    public void setCsvItemsCO(CsvItemsCO csvItemsCO)
    {
        this.csvItemsCO = csvItemsCO;
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

    /**
     * open the jsp page
     * 
     * @return
     */

    public String loadCsvItemsPage()
    {
	try
	{
	    set_showSmartInfoBtn("false");
	    set_searchGridId("csvItemsListGridTbl_Id");
	    set_showNewInfoBtn("true");
	}
	catch(Exception ex)
	{
	    handleException(ex, null, null);
	}
	return "csvItemsList";
    }
    
    public String emptyForm()
    {
	try
	{
	    csvItemsCO = new CsvItemsCO();
	}
	catch(Exception e)
	{
	    handleException(e,null , null);
	}
	return "csvItemsMaint";
    }

    /**
     * apply the dependency on the application name lkp
     * 
     * @return SUCCESS
     * @throws Exception
     */
    public String applyDependencyByRepRef() throws Exception
    {
	try
	{
	    CsvItemsSC csvItemsSC = new CsvItemsSC();
	    csvItemsSC.setREP_REF(reportRef);
	    SessionCO sessionCO = returnSessionObject();
	    csvItemsSC.setCompCode(sessionCO.getCompanyCode());
	    csvItemsCO = csvItemsBO.applyDependencyByRepRef(csvItemsSC);
	    
	    if(csvItemsCO!=null)
	    {
		csvItemsCO.getCbkRptLineVO().setREP_REF(reportRef);
		csvItemsCO.getCbkRptLineVO().setCOMP_CODE(sessionCO.getCompanyCode());
		applyRetrieveAudit(AuditConstant.CSV_ITEMS_KEY_REF, csvItemsCO.getCbkRptLineVO(), csvItemsCO.getCbkRptLineVO());
	    }

	}
	catch(Exception e)
	{
	    //log.error(e, "***************** error in applyDependencyByRepRef *************");
	    handleException(e, null, null);
	}
	return SUCCESS;

    }
    
    
    public String retrieveRepName() throws Exception
    {
	csvItemsSC.setREP_REF(reportRef);
	SessionCO sessionCO = returnSessionObject();
	csvItemsSC.setCompCode(sessionCO.getCompanyCode());
	try
	{
	    csvItemsCO =csvItemsBO.retrieveRepName(csvItemsSC);
	    csvItemsCO.getCbkRptLineVO().setREP_REF(reportRef);
	    csvItemsCO.getCbkRptLineVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    applyRetrieveAudit(AuditConstant.CSV_ITEMS_KEY_REF, csvItemsCO.getCbkRptLineVO(), csvItemsCO.getCbkRptLineVO());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	
	return "csvItemsMaint";
    }

}
