
package com.path.actions.reporting.ftr.fileexportimport;

import java.util.List;

import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.ftr.fileexportimport.FileExportImportBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.LookupGrid;
import com.path.struts2.lib.common.base.LookupBaseAction;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;
/**
 * Used to load Account lookup...
 */
public class FileExportImportLookupAction extends LookupBaseAction
{
	private  IRP_FILE_MAINSC irp_file_mainSC = new IRP_FILE_MAINSC();
    	private  IRP_FILE_DETSC irp_file_detSC = new IRP_FILE_DETSC();

    	private FileExportImportBO fileExportImportBO;
	private List<IRP_FILE_MAINCO> irp_file_mainCOList;
	private  IRP_FILE_MAINCO irp_file_mainCO = new IRP_FILE_MAINCO();
	private IRP_FILE_DETCO irp_file_detCO = new IRP_FILE_DETCO();
	private List<IRP_FILE_DETCO> irp_file_detCOList;
	private String PROG_REF;
	public String getPROG_REF()
	{
	    return PROG_REF;
	}

	public void setPROG_REF(String pROGREF)
	{
	    PROG_REF = pROGREF;
	}

	private String REPORT_NAME;

	  public String getREPORT_NAME()
	{
	    return REPORT_NAME;
	}

	public void setREPORT_NAME(String rEPORTNAME)
	{
	    REPORT_NAME = rEPORTNAME;
	}

	public void setIrp_file_detSC(IRP_FILE_DETSC irp_file_detSC)
	    {
		this.irp_file_detSC = irp_file_detSC;
	    }

	    public IRP_FILE_DETSC getIrp_file_detSC()
	    {
		return irp_file_detSC;
	    }

	    public List<IRP_FILE_DETCO> getIrp_file_detCOList()
	    {
		return irp_file_detCOList;
	    }

	    public void setIrp_file_detCOList(List<IRP_FILE_DETCO> irpFileDetCOList)
	    {
		irp_file_detCOList = irpFileDetCOList;
	    }

	    public IRP_FILE_DETCO getIrp_file_detCO()
	    {
		return irp_file_detCO;
	    }

	    public void setIrp_file_detCO(IRP_FILE_DETCO irpFileDetCO)
	    {
		irp_file_detCO = irpFileDetCO;
	    }

	    public IRP_FILE_MAINSC getIrp_file_mainSC()
	    {
		return irp_file_mainSC;
	    }

	    public void setIrp_file_mainSC(IRP_FILE_MAINSC irp_file_mainSC)
	    {
		this.irp_file_mainSC = irp_file_mainSC;
	    }

	    public List<IRP_FILE_MAINCO> getIrp_file_mainCOList()
	    {
		return irp_file_mainCOList;
	    }

	    public void setIrp_file_mainCOList(List<IRP_FILE_MAINCO> irp_file_mainCOList)
	    {
		this.irp_file_mainCOList = irp_file_mainCOList;
	    }

	    public IRP_FILE_MAINCO getIrp_file_mainCO()
	    {
		return irp_file_mainCO;
	    }

	    public void setIrp_file_mainCO(IRP_FILE_MAINCO irp_file_mainCO)
	    {
		this.irp_file_mainCO = irp_file_mainCO;
	    }

	
	public void setFileExportImportBO(FileExportImportBO fileExportImportBO)
	{
		this.fileExportImportBO = fileExportImportBO;
	}
	
	public String loadReportReferenceLkpGrid()throws BaseException
	{
	
	  	try
	    	{irp_file_detSC.setAPP_NAME("REP");
	    	    	copyproperties(irp_file_detSC);
	    		List<IRP_FILE_DETCO>lst=fileExportImportBO.retRepRefList(irp_file_detSC);
	    		int lstCnt=fileExportImportBO.retRepRefListCount(irp_file_detSC);
	    		setRecords(lstCnt);
	    		setGridModel(lst);	
	    	}
	    	catch(Exception e)
	    	{
		    //log.error(e, "ERROR in loadAppNameLkpGrid AppConnectionAction");
		    handleException(e, "Error in load report reference lookup grid","loadReportReferenceLkpGrid.exceptionMsg._key");
	    	}
	    	return "grid";
	    } 
	
	  public String reportLookup()
	    {
		try
		{
		    fillLookup("", "/path/fileExportImport/fileExportImportLookupAction_loadReportReferenceLkpGrid", "");
		}
		catch(Exception e)
		{
		    //log.error(e, "Error filling reports lookup reports mismatch");
		    handleException(e, null, null);
		}
		return "grid";
	    }

	    public String fillLookup(String gridId, String gridUrl, String gridCaption) throws JSONException
	    {
		try
		{
		    String[] name = { "PROG_REF","REPORT_NAME"};
		    String[] colType = { "text", "text" };
		    String[] titles = { getText("progRef"), getText("reportName") };

		    LookupGrid grid = new LookupGrid();
		    grid.setCaption(gridCaption);
		    grid.setRowNum("10");
		    grid.setUrl(gridUrl);
		    irp_file_detSC.setAPP_NAME("REP");
		    lookup(grid, irp_file_detSC, name, colType, titles);
		}
		catch(Exception e)
		{
		    handleException(e, null, null);
		}
		return "grid";
	    }
}
