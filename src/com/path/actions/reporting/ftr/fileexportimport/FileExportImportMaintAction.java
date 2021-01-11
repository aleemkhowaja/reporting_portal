package com.path.actions.reporting.ftr.fileexportimport;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.fileexportimport.FileExportImportBO;
import com.path.dbmaps.vo.IRP_FILE_DETVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * FileExportImportMaintAction.java used to
 */

public class FileExportImportMaintAction extends ReportingLookupBaseAction implements ServletRequestAware
{	private FileExportImportBO fileExportImportBO;
	private IRP_FILE_MAINSC irp_file_mainSC = new IRP_FILE_MAINSC();
	private IRP_FILE_MAINCO irp_file_mainCO = new IRP_FILE_MAINCO();
	private String updates;	
	private String updates_1;
	private String mainFileName;
	private BigDecimal fileId;
	private File upload;
	private String fileName;
	private String oldFileName;
	private String mode;
	HttpServletRequest request = ServletActionContext.getRequest();
	private String PROG_REF_D00;
        private String htmlPageRef;
        private HashMap noHeadFootMap;
        private IRP_FILE_DETVO irp_file_detVO;
        private CommonLookupBO commonLookupBO;
        private HashMap paramsFlag;

	/**
	 * @return the paramsFlag
	 */
	public HashMap getParamsFlag()
	{
	    return paramsFlag;
	}

	/**
	 * @param paramsFlag the paramsFlag to set
	 */
	public void setParamsFlag(HashMap paramsFlag)
	{
	    this.paramsFlag = paramsFlag;
	}

	/**
	 * @param commonLookupBO the commonLookupBO to set
	 */
	public void setCommonLookupBO(CommonLookupBO commonLookupBO)
	{
	    this.commonLookupBO = commonLookupBO;
	}

	/**
	 * @return the irp_file_detVO
	 */
	public IRP_FILE_DETVO getIrp_file_detVO()
	{
	    return irp_file_detVO;
	}

	/**
	 * @param irpFileDetVO the irp_file_detVO to set
	 */
	public void setIrp_file_detVO(IRP_FILE_DETVO irpFileDetVO)
	{
	    irp_file_detVO = irpFileDetVO;
	}

	public HashMap getNoHeadFootMap()
        {
    	return noHeadFootMap;
        }
    
        public void setNoHeadFootMap(HashMap noHeadFootMap)
        {
    	this.noHeadFootMap = noHeadFootMap;
        }
	  /**
	 * @return the htmlPageRef
	 */
	public String getHtmlPageRef()
	{
	    return htmlPageRef;
	}

	/**
	 * @param htmlPageRef the htmlPageRef to set
	 */
	public void setHtmlPageRef(String htmlPageRef)
	{
	    this.htmlPageRef = htmlPageRef;
	}

	/**
	 * @return the pROG_REF_D00
	 */
	public String getPROG_REF_D00()
	{
	    return PROG_REF_D00;
	}

	/**
	 * @param pROGREFD00 the pROG_REF_D00 to set
	 */
	public void setPROG_REF_D00(String pROGREFD00)
	{
	    PROG_REF_D00 = pROGREFD00;
	}

	@Override
	    public void setServletRequest(HttpServletRequest request)
	    {
		// TODO Auto-generated method stub
		this.request = request;

	    }
	private CommonRepFuncBO commonRepFuncBO;
	    public String getMode()
	    {
		return mode;
	    }

	    public void setMode(String mode)
	    {
		this.mode = mode;
	    }
	public String getMainFileName()
	{
	    return mainFileName;
	}

	public void setMainFileName(String mainFileName)
	{
	    this.mainFileName = mainFileName;
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
	    public IRP_FILE_MAINSC getIrp_file_mainSC()
	    {
		return irp_file_mainSC;
	    }

	    public void setIrp_file_mainSC(IRP_FILE_MAINSC irp_file_mainSC)
	    {
		this.irp_file_mainSC = irp_file_mainSC;
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
	   
	 public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
	    {
		this.commonRepFuncBO = commonRepFuncBO;
	    }


	
	public String saveFiles()
	{	
	 try
	 { SessionCO sessionCO = returnSessionObject();
	 String pageRef		= get_pageRef();
	String appName   	= sessionCO.getCurrentAppName();
	AuditRefCO refCO = initAuditRefCO();

	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	boolean isCreatedDirectory = new PathFileSecure(repositoryPath + "/" +RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION).mkdir();
	if(!isCreatedDirectory)
	{
	    log.debug("The directory " + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + " has not been created");
	}
	
	String folderName = fileName;
	
	fileName =   StringUtil.nullToEmpty(fileName);
	if (!fileName.isEmpty()) 
	{
	int extIndex = fileName.lastIndexOf(".");
	    if (extIndex != 0 ) 
	    {
		folderName = fileName.substring(0,extIndex);
	    }
	    boolean isCreatedFolder =  new PathFileSecure(repositoryPath + "/" +RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION+"/"+folderName).mkdir(); 
	    if(!isCreatedFolder)
	    {
	    log.debug("The folder " + fileName + " has not been created");
	    }
	    
	}
	
	  fileExportImportBO.createMainFolder(folderName);
		    
	  BigDecimal id = fileId;

	    if ("add".equals(mode))
	    {
	    
	    int fileNameExist = fileExportImportBO.checkIfFileUploaded(fileName);
	    if( fileNameExist>0)
	    {   
		throw new BOException(getText("fileexpimp.fileexists"));
	    }
	    String report = repositoryPath + "/" +RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION+"/"+folderName+"/"+fileName;
	    FileUtils.copyFile(upload, new PathFileSecure(report));
	    byte[] fileContent = FileUtil.readFileBytes(report);
	    IRP_FILE_MAINVO newFileVO = new IRP_FILE_MAINVO();
	    id = commonRepFuncBO.retCounterValue("IRP_FILE_MAIN");
	    newFileVO.setFILE_ID(id);
	  
	    newFileVO.setFILE_NAME(fileName);
	    newFileVO.setFILE_CONTENT(fileContent);
	    // Construct the Audit Reference  
	    refCO.setOperationType(AuditConstant.CREATED);
	    refCO.setKeyRef(AuditConstant.IRP_FILE_MAIN_KEY_REF);
	    refCO.setProgRef(pageRef);
	    refCO.setAppName(appName);
	    newFileVO.setAuditRefCO(refCO);
	    fileExportImportBO.insertFileMain(newFileVO);
	   
	    //deleting the report that was uploaded to the repository
	    File file = new PathFileSecure(report);
	    boolean isDel = file.delete();
	    if(!isDel)
	    {
		log.debug("The uploaded file has not been deleted from the repository:" + file);
	    }
	    }
	    IRP_FILE_MAINVO fileVO = new IRP_FILE_MAINVO();
	    if ("update".equals(mode)) 
		{
		id = fileId;
		fileName = StringUtil.nullToEmpty(fileName);
		if(!fileName.isEmpty() )
		{
		    int fileNameExist = fileExportImportBO.checkIfFileUploaded(fileName);
		    if(fileNameExist > 0 && oldFileName.compareTo(fileName)!=0)
		    {
			throw new BOException(getText("fileexpimp.fileexists"));
		    }
		    String report = repositoryPath + "/" +RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION+"/"+folderName+"/"+fileName;

		    FileUtils.copyFile(upload, new PathFileSecure(report));
		    byte[] fileContent = FileUtil.readFileBytes(report);
		    fileVO.setFILE_ID(id);
		  
		    fileVO.setFILE_NAME(fileName);
		    fileVO.setFILE_CONTENT(fileContent);
		    
		    // Construct the Audit Reference  

		    refCO.setOperationType(AuditConstant.UPDATE);
		    refCO.setKeyRef(AuditConstant.IRP_FILE_MAIN_KEY_REF);
		    refCO.setProgRef(pageRef);
		    refCO.setAppName(appName);
		    fileVO.setAuditRefCO(refCO);
		    IRP_FILE_MAINVO oldFileVO=(IRP_FILE_MAINVO)returnAuditObject(IRP_FILE_MAINVO.class);
		 
		    fileExportImportBO.updateMainFile(fileVO,oldFileVO);
		 }
		}
	    
	    GridUpdates gu;

	    if(updates != null && !updates.equals("") && !updates.equals("{\"root\":[]}"))
	    {
		// Get the updated record in the grid that sent from the page
		// "fileExportImportList.js"
		gu = getGridUpdates(updates, IRP_FILE_DETCO.class);
		// Set the array of added records
		ArrayList lstAdd = gu.getLstAdd();
		// Set the array of updated records
		ArrayList lstMod = gu.getLstModify();
		// Set the array of deleted records
		ArrayList lstDel = gu.getLstDelete();

		// Check the sizes of the arrays to know if there are deleted
		// records
		if(!lstDel.isEmpty())
		{
		    refCO.setOperationType(AuditConstant.DELETE);
		    refCO.setKeyRef(AuditConstant.IRP_FILE_MAIN_KEY_REF);
		    refCO.setProgRef(pageRef);
		    refCO.setAppName(appName);
		    fileVO.setAuditRefCO(refCO);
		    fileExportImportBO.deleteDetailFiles(lstDel,fileId,fileVO);

		}
		// Check the sizes of the arrays to know if there are records
		// added
		if(!lstAdd.isEmpty())
		{
		    refCO.setOperationType(AuditConstant.CREATED);
		    refCO.setKeyRef(AuditConstant.IRP_FILE_MAIN_KEY_REF);
		    refCO.setProgRef(pageRef);
		    refCO.setAppName(appName);
		    fileVO.setAuditRefCO(refCO);
		    fileExportImportBO.insertDetailFiles(lstAdd, id,fileVO, sessionCO.getCompanyCode());
		}

		// Check the sizes of the arrays to know if there are records
		// updated
		if(!lstMod.isEmpty())
		{
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		    HashMap oldAuditRefVOList = repSessionCO.getFileDetailsOld();
		    refCO.setOperationType(AuditConstant.UPDATE);
		    refCO.setKeyRef(AuditConstant.IRP_FILE_MAIN_KEY_REF);
		    refCO.setProgRef(pageRef);
		    refCO.setAppName(appName);
		    fileVO.setAuditRefCO(refCO);
		    fileExportImportBO.updateDetailFiles(lstMod,fileId,fileVO,oldAuditRefVOList);
		}
	    }
	}
	catch(Exception e)
	{ 
	handleException(e, null,null);
	}
	 return "fileSuccess";
	}
	
	    
	    /**
	     * Method called to empty the form 'fileExportImportMaint.jsp'
	     */
		public String emptyFileMainFrm()throws Exception
		{   setIrp_file_mainCO(new IRP_FILE_MAINCO());
		    mode = "add";
		    return "successFrm";
		}

	    public void setIrp_file_mainCO(IRP_FILE_MAINCO irp_file_mainCO)
	    {
		this.irp_file_mainCO = irp_file_mainCO;
	    }

	    public IRP_FILE_MAINCO getIrp_file_mainCO()
	    {
		return irp_file_mainCO;
	    }

	    public void setOldFileName(String oldFileName)
	    {
		this.oldFileName = oldFileName;
	    }

	    public String getOldFileName()
	    {
		return oldFileName;
	    }
	    
    /**
     * 
     * This method will open the dynamic parameter screen
     * 
     * @param
     * @return
     * @throws Exception
     */	    
    public String openDynParam()
    {
	htmlPageRef=PROG_REF_D00.replace("-", "_");
	return "dynParamSuccess";
    }
    
    /**
     * 
     * This method is added to store the parameters values of each report in the
     * fileExportImport
     * 
     * @param
     * @return
     * @throws Exception
     */
    public void saveDynParamsVal()
    {
	try
	{
	    ReportingSessionCO fileExpImpSessionCO = returnReportingSessionObject(ConstantsCommon.FILE_EXP_IMP_PROG_REF);
	    SessionCO sessionCO = returnSessionObject();
	    HashMap<String, Object> paramMap = new HashMap<String, Object>();

	    Enumeration enu = request.getParameterNames();
	    String itemName;
	    String itemValue;
	    String paramType;
	    String[] arr;
	    String dateFrmt;
	    Date dt;
	    String flag;
	    String valueOn;
	    String valueOff;
	    while(enu.hasMoreElements())
	    {
		itemName = (String) enu.nextElement();
		itemValue = request.getParameter(itemName);
		if(itemName.startsWith(ConstantsCommon.PARAM_TILDA))
		{
		    arr = itemName.split("~");
		    itemName = arr[1];
		    paramType = arr[2];
		    if(!(StringUtil.nullToEmpty(itemValue)).isEmpty())
		    {
			if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(paramType))
			{
			    paramMap.put(itemName, new BigDecimal(itemValue));
			}
			else if(ConstantsCommon.PARAM_TYPE_DATE.equals(paramType))
			{
			    paramMap.put(itemName, DateUtil.parseDate(itemValue, DateUtil.DEFAULT_DATE_FORMAT));
			}
			else if(ConstantsCommon.datetime.equals(paramType))
			{
			    itemValue = itemValue.replace("+", " ");
			    dateFrmt = DateUtil.getDatePattern(itemValue);
			    
			    if(commonLookupBO.retSysDateParamLovVal(sessionCO.getLanguage()).equals(itemValue))
			    {
				 paramMap.put(itemName,itemValue);
			    }
			    else
			    {
				 dt = DateUtil.parseDate(itemValue, dateFrmt);
				    paramMap.put(itemName, new java.sql.Timestamp(dt.getTime()));
			    }
			   
			}
			else
			{
			    paramMap.put(itemName, itemValue);
			}
		    }

		}
		else if(itemName.startsWith(RepConstantsCommon.CHECKBOX_PARAM_FLAG))
		{
		    flag = itemName.substring(11);

		    itemName = flag.substring(11, flag.length() - 4);
		    valueOn = flag.substring(flag.length() - 3, flag.length() - 2);
		    valueOff = flag.substring(flag.length() - 1, flag.length());

		    if(request.getParameter(flag) == null)
		    {
			paramMap.put(itemName, valueOff);
		    }
		    else
		    {
			paramMap.put(itemName, valueOn);
		    }
		}
		if(noHeadFootMap != null)
		{
		    String val[] = (String[]) noHeadFootMap.get(ConstantsCommon.HEAD_FOOT_PARAM);
		    if(ConstantsCommon.TRUE.equals(val[0]))
		    {
			paramMap.put(RepConstantsCommon.NO_HEAD_FOOT, ConstantsCommon.TRUE);
		    }
		    else
		    {
			paramMap.put(RepConstantsCommon.NO_HEAD_FOOT, ConstantsCommon.FALSE);
		    }
		}
	    }
	    paramMap.put(ConstantsCommon.LANG_PARAM, request.getParameter(ConstantsCommon.LANG_PARAM));
	    paramMap.put(ConstantsCommon.FORMAT_PARAM, request.getParameter(ConstantsCommon.FORMAT_PARAM));
	    paramMap.put(ConstantsCommon.DB_PARAM, request.getParameter(ConstantsCommon.DB_PARAM));
	    paramMap.put(ConstantsCommon.MENU_ID_PARAM, request.getParameter(ConstantsCommon.MENU_ID_PARAM));
	    paramMap.put(ConstantsCommon.REPORT_ID_PARAM, request.getParameter(ConstantsCommon.REPORT_ID_PARAM));
	    paramMap.put(ConstantsCommon.APP_NAME_PARAM, request.getParameter(ConstantsCommon.APP_NAME_PARAM));
	    paramMap.put(ConstantsCommon.DYNAMIC_SCREEN_PARAM, request.getParameter(ConstantsCommon.DYNAMIC_SCREEN_PARAM));
	    

	   fileExpImpSessionCO.getFileExpImpParamsMap().remove(PROG_REF_D00);
	   fileExpImpSessionCO.getFileExpImpParamsMap().put(PROG_REF_D00, paramMap);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

    }
}
