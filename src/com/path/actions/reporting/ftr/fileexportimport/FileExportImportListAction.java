package com.path.actions.reporting.ftr.fileexportimport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.fileexportimport.FileExportImportBO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.vo.LookupGrid;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;

/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * FileExportImportListAction.java used to
 */
public class FileExportImportListAction extends ReportingLookupBaseAction implements ServletRequestAware
{
    	private IRP_FILE_MAINSC irp_file_mainSC = new IRP_FILE_MAINSC();
    	private IRP_FILE_DETSC irp_file_detSC = new IRP_FILE_DETSC();
    	private final IRP_FILE_MAINVO irp_file_mainVO = new IRP_FILE_MAINVO();
    	private FileExportImportBO fileExportImportBO;
	private List<IRP_FILE_MAINCO> irp_file_mainCOList;
	private IRP_FILE_MAINCO irp_file_mainCO = new IRP_FILE_MAINCO();
	private IRP_FILE_DETCO irp_file_detCO = new IRP_FILE_DETCO();
	private List<IRP_FILE_DETCO> irp_file_detCOList;
	//private String concatKey;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	private String updates;	
	private String updates_1;	
	private String mode;
	private String fileName;
	private String mainFileName;
	private String concatKey;
	private String downloadFileName;


	    public String getDownloadFileName()
	    {
		return downloadFileName;
	    }

	    public void setDownloadFileName(String downloadFileName)
	    {
		this.downloadFileName = downloadFileName;
	    }

	public String getMainFileName()
	{
	    return mainFileName;
	}

	public void setMainFileName(String mainFileName)
	{
	    this.mainFileName = mainFileName;
	}


	private String subFileName;
	public String getSubFileName()
	{
	    return subFileName;
	}

	public void setSubFileName(String subFileName)
	{
	    this.subFileName = subFileName;
	}


	private BigDecimal fileId;
	private File upload;
	private String contentHeader;
	    private InputStream fileStream;

	    public InputStream getFileStream()
	    {
		return fileStream;
	    }

	    public void setFileStream(InputStream fileStream)
	    {
		this.fileStream = fileStream;
	    }
	  public String getContentHeader()
	    {
		return contentHeader;
	    }

	    public void setContentHeader(String contentHeader)
	    {
		this.contentHeader = contentHeader;
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

    @Override
    public void setServletRequest(HttpServletRequest request)
    {
	// TODO Auto-generated method stub
	this.request = request;

    }

    @Override
    public void setServletResponse(HttpServletResponse response)
    {
	this.response = response;
    }

    public BigDecimal getFileId()
    {
	return fileId;
    }

    public void setFileId(BigDecimal fileId)
    {
	this.fileId = fileId;
    }

    public String getUploadFileName()
    {
	return fileName;
    }

    public void setUploadFileName(String fileName)
    {
	this.fileName = fileName;
    }
    
    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
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

    public String getUpdates_1()
    {
	return updates_1;
    }

    public void setUpdates_1(String updates_1)
    {
	this.updates_1 = updates_1;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public void setFileExportImportBO(FileExportImportBO fileExportImportBO)
    {
	this.fileExportImportBO = fileExportImportBO;
    }

    public Object getModel()
    {
	return irp_file_mainSC;
    }
    // since we are using <s:file name="upload" ... /> the File itself will be
    // obtained through getter/setter of <file-tag-name>
    public File getUpload() {
        return upload;
    }
   public void setUpload(File upload) {
        this.upload = upload;
    }


	/**
	 * Execute Method
	 */
    	public String execute() throws Exception
        {   
    	    try
    	    {
    	    String pageRef = get_pageRef();
    	    if ("FEI00MT".equals(pageRef))
    	    {
    		   set_showNewInfoBtn("true");
    	    }
    	    else
    	    {
 		   set_showNewInfoBtn("false");
 	    }
            set_showSmartInfoBtn("false");
            set_searchGridId("fileMainGrid");
         
            mode="add";
           
           clearDynParamSession();
               
            return SUCCESS;
    	    }
    	    catch (Exception e)
    	    {
    		handleException(e, null,null);
    		return ERROR_ACTION;
    	    }
        }
    	

	/**
	     * Method called when loading the grid 'MainGrid_${_pageRef}'
	     */
		public String loadMainGrid() throws JSONException{
			try
			{
				SessionCO sessionCO = returnSessionObject();
				copyproperties(irp_file_mainSC);
				irp_file_mainSC.setPreferredLanguage(sessionCO.getLanguage());
				
				if(checkNbRec(irp_file_mainSC))
				{
					setRecords(fileExportImportBO.getFileMainCount(irp_file_mainSC));
				}
				irp_file_mainCOList = fileExportImportBO.getFileMainList(irp_file_mainSC);			
				setGridModel(irp_file_mainCOList);
			}
			catch(Exception e)
			{
			    handleException(e, "fileExportImport.errorGrid.exceptionMsg._key",null);
			}
			return "json";
	    }
		
		public String retrieveMainGrid()throws Exception
		{
			try
			{
				IRP_FILE_MAINSC irp_file_mainSC = new IRP_FILE_MAINSC();
			    	irp_file_mainSC.setFILE_ID(fileId);
			    	IRP_FILE_MAINVO vo=fileExportImportBO.retrieveMainFiles(irp_file_mainSC);
			    	irp_file_mainCO.setIrp_file_mainVO(vo);
			    	vo.setFILE_CONTENT(null);
			    	applyRetrieveAudit(AuditConstant.IRP_FILE_MAIN_KEY_REF, vo, vo);
				    
			    	// Audit on Retrieve
			    	mode="update";
			    	clearDynParamSession();
			}
			catch(Exception e)
			{
			    handleException(e, "fileExportImport.errorGrid.exceptionMsg._key",null);
			}
			return "successFrm";
		}
		
		/**
		 * Method called when deleting the Main File row from the grid 'MainGrid_${_pageRef}'
		 */
		public String deleteMainFile() {
			try
			{   SessionCO sessionCO = returnSessionObject();
			    String pageRef	= get_pageRef();
			    String appName   	= sessionCO.getCurrentAppName();
			    AuditRefCO refCO = initAuditRefCO();
			    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
			    String folderName = null;
			    irp_file_mainVO.setFILE_ID(fileId);
			    irp_file_mainVO.setFILE_NAME(mainFileName);

			    irp_file_mainCO.setIrp_file_mainVO(irp_file_mainVO);
			    refCO.setOperationType(AuditConstant.DELETE);
			    refCO.setKeyRef(AuditConstant.IRP_FILE_MAIN_KEY_REF);
			    refCO.setProgRef(pageRef);
			    refCO.setAppName(appName);
			    irp_file_mainVO.setAuditRefCO(refCO);
			    fileExportImportBO.deleteMainFile(irp_file_mainVO);
			     if (!mainFileName.isEmpty())
				{
				    int extIndex = mainFileName.lastIndexOf(".");
				    if (extIndex != 0 ) 
				    {
					folderName = mainFileName.substring(0,extIndex);
				    }
				    String repository = repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/" + folderName ;
				    try
				    {
					FileUtil.deleteFolder(repository);
				    }
				    catch(Exception e)
				    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    }
				 
				}
			    
			}
			catch(Exception e)
			{
			    handleException(e, "fileExportImport.errorDeleting.exceptionMsg._key",null);
			}
			return "json";
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
		
		
		/**
		     * Method called to retrieve the File Details grid of selected row in the grid Main Files grid'
		     */ 
		
		
			public String retrieveDetailFileList()throws Exception
			{
				try
				{
					//Declare and set the 'ReportingSessionCO' 
					ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
					//Use the HashMap to save the list of File when retrieved from Database,
					//Then do any change and save it, we use the HashMap to get the old values of the modified
					//Files to save it in the audit
					HashMap dbFileDetails = new HashMap();
					
					//Declare and initialize the 'sessionCO'
					//SessionCO sessionCO = returnSessionObject();
					
					IRP_FILE_DETSC irpfiledetSC = new IRP_FILE_DETSC();
					copyproperties(irpfiledetSC);		
					
					//If File is not null, then execute the save
					if(fileId==null)
					{
						setRecords(0);
						setGridModel(new ArrayList<IRP_FILE_DETCO>());
					}
					else
					{
					//Retrieve the list of File Details
					irpfiledetSC.setFILE_ID(fileId);
					setRecords(fileExportImportBO.getDetailFilesCount(irpfiledetSC));
					irp_file_detCOList = fileExportImportBO.retrieveDetailFiles(irpfiledetSC);
					
					//Loop through the retrieved Time Buckets Details and set the concatKey of each one,
				    	//Then save the list in the HashMap 'dbTimeBucketsDetails'
				    	for(int i=0;i<irp_file_detCOList.size();i++)
				    	{
				    	    	irp_file_detCO = irp_file_detCOList.get(i);
				    	
						concatKey = irp_file_detCO.getIrp_file_detVO().getFILE_ID().toString();
				    		concatKey += '_' + irp_file_detCO.getIrp_file_detVO().getLINE_NO().toString();
				    				    		
				    		irp_file_detCO.setConcatKey(concatKey);
				    		dbFileDetails.put(concatKey, irp_file_detCO.getIrp_file_detVO());
				    	}
				    	
				    	//Set the HashMap list of old 'File Details' in the session  'ReportingSessionCO'
				    	//So we can use it when saving the audit of the updated 'File Details' in 'FileExportImportBOImpl'
				    	repSessionCO.setFileDetailsOld(dbFileDetails);
			
				    	//Set the Model of the grid 'FileDetailsGrid_${_pageRef}'
				    	setGridModel(irp_file_detCOList);
					}
					
			    	
			    	mode="update";
				}
				catch(Exception e)
				{
				    handleException(e, "fileExportImport.errorRetrievingDetails.exceptionMsg._key", null);
				}
				return "json";
			}
			
			  public String reportLookup()
			    {
				try
				{
				    fillLookup("", "/path/fileExportImport/fileExportImportListAction_loadReportReferenceLkpGrid", "");
				}
				catch(Exception e)
				{
				    //log.error(e, "Error filling reports lookup ");
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

			
			
			  
			
    /**
     * Method called to empty the grid 'fileDetGrid_${_pageRef}'
     */
    public String emptyDetailFileList() throws Exception
    {
	setRecords(0);
	setGridModel(new ArrayList<IRP_FILE_DETCO>());
	mode = "add";
	return "json";
    }
    
    
    public void exportFile() throws Exception
    {	
	
	    try {
	   
	    IRP_FILE_MAINSC irp_file_mainSC = new IRP_FILE_MAINSC();
	    // Get the VO of the file
	    irp_file_mainSC.setFILE_ID(fileId);
	    IRP_FILE_MAINVO vo = fileExportImportBO.retrieveMainFiles(irp_file_mainSC);
	
	    String folderName = "";
	    byte[] fileBytes = null;
	    fileBytes = vo.getFILE_CONTENT();
	    String fileName = vo.getFILE_NAME();
	    int extIndex = fileName.lastIndexOf(".");
	    if(extIndex != 0)
	    {
		folderName = fileName.substring(0, extIndex);
	    }
	 
	    try
		{		 
	    byte[] zipBytes = null;
	    zipBytes = fileExportImportBO.writeFileBytes(folderName,fileName,fileBytes);
	    response.addHeader("content-disposition", "attachment;filename=\"" + folderName +  "." + ConstantsCommon.ZIP_EXT);
	    response.setContentType("application/zip");
	    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    response.getOutputStream().write(zipBytes);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	  

        	}
        	catch(IOException e)
        	{
        	    log.error(e, "");
        	}
	    }
	    catch(Exception e)
	    {
		throw new BOException("Error when retrieving the file for the file Id: "
			    + fileId + "\n " + e.getMessage(), e);
	    }
	    
    }
    
    
    public String viewFile() throws BOException 
    {
	try{
	    IRP_FILE_MAINSC irp_file_mainSC = new IRP_FILE_MAINSC();
	    // Get the VO of the file
	    irp_file_mainSC.setFILE_ID(fileId);
	    IRP_FILE_MAINVO vo = fileExportImportBO.retrieveMainFiles(irp_file_mainSC);	 
	    String folderName = vo.getFILE_NAME();
	    int extIndex = folderName.lastIndexOf(".");
	    if(extIndex != 0)
	    {
		folderName = folderName.substring(0, extIndex);
	    }
	    
	    byte[] fileBytes = null;
	    fileBytes= fileExportImportBO.readFileBytes(folderName,subFileName);	   

	    downloadFileName =subFileName + "."+ ConstantsCommon.XLSX_EXT;
	    if (fileBytes != null)
	    {
		 fileStream = new ByteArrayInputStream(fileBytes);
		    ServletActionContext.getResponse().setHeader("Set-Cookie", "fileDownload=true; path=/");
	    }

	}
	
	catch(Exception e)
	{
	    handleException(e, null, null);
	    try
	    {
		setFileStream(new ByteArrayInputStream(get_error().getBytes(FileUtil.DEFAULT_FILE_ENCODING)));
	    }
	    catch(UnsupportedEncodingException e1)
	    {
		log.error(e1, "Error in reading the error message");
	    }
	    return "fileError";
	}
	return "saveFile";
    }
    
    /**
     * 
     * This method will clear the session data related to the dynamic parameters value of each main report
     * @param
     * @return
     * @throws Exception
     */
    public void clearDynParamSession()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(ConstantsCommon.FILE_EXP_IMP_PROG_REF);
	    repSessionCO.setFileExpImpParamsMap(new HashMap<String, HashMap<String,Object>>());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }
    
 }
