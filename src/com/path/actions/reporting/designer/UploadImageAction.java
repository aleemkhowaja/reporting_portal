 package com.path.actions.reporting.designer;
 
 import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import com.path.bo.reporting.designer.UploadImageBO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.struts2.lib.common.base.BaseAction;
 /**
  * Show case File Upload example's action. <code>FileUploadAction</code>
 *
  */
 public class UploadImageAction extends BaseAction {

     private String contentType;
     private File upload;
     private String fileName;
    private String caption;
    private InputStream scriptStream;
    private UploadImageBO uploadImageBO;
 
     public void setUploadImageBO(UploadImageBO uploadImageBO)
    {
        this.uploadImageBO = uploadImageBO;
    }
    public InputStream getScriptStream() {
		return scriptStream;
	}
	public void setScriptStream(InputStream scriptStream) {
		this.scriptStream = scriptStream;
	}
	// since we are using <s:file name="upload" .../> the file name will be
     // obtained through getter/setter of <file-tag-name>FileName
     public String getUploadFileName() {
         return fileName;
     }
     public void setUploadFileName(String fileName) {
         this.fileName = fileName;
     }
 
 
     // since we are using <s:file name="upload" ... /> the content type will be
     // obtained through getter/setter of <file-tag-name>ContentType
     public String getUploadContentType() {
         return contentType;
     }
    public void setUploadContentType(String contentType) {
         this.contentType = contentType;
    }
 
 
     // since we are using <s:file name="upload" ... /> the File itself will be
     // obtained through getter/setter of <file-tag-name>
     public File getUpload() {
         return upload;
     }
    public void setUpload(File upload) {
         this.upload = upload;
     }
 
 
     public String getCaption() {
         return caption;
     }
     public void setCaption(String caption) {
        this.caption = caption;
    }

     public String input() {
         return SUCCESS;
     }
 
     public String upload(){
        return SUCCESS;
     }
     public String openUploadImage()
	  {
	  return "openUpload";

	  }
     
     public boolean checkImageExists(String newImageName)
     {
     	boolean imageExists=false;
	try
	{
	    imageExists = uploadImageBO.checkImageExists(newImageName);
	}
	catch(BaseException e)
	{
	    handleException(e,null,null);
	}
	 return imageExists;
     	}
     
     public String confirmUploadedImage() 
     {  
     try
     {
	 byte[] data =  FileUtil.readFileBytes(upload.getPath());
		uploadImageBO.saveUploadedImage(data, fileName);
     }
     catch(Exception e)
	{
         handleException(e, e.getMessage(),null);
	}
	return "successUpload";
     
     }
	
	public String saveUploadedImage()
	{
	          String result =ERROR_ACTION;
	          boolean overSizeLimit = false;
	          boolean imageExists = false;
		try
		{
		    scriptStream = new ByteArrayInputStream(new byte[0]);
		    imageExists = checkImageExists(fileName);
		    if (imageExists)
		    { 
			throw new BOException("Exists");
		    }
		    PTH_CTRL1VO vo= uploadImageBO.retImgParams();
		    double bytes = upload.length();
		    double kilobytes = (bytes/1024);
		    double maxbytes = vo.getREP_IMG_LIMIT_SIZE().doubleValue();
		    if ((kilobytes > maxbytes) && maxbytes>0)
		    {
			overSizeLimit = true;
		    }

		    if("1".equals(vo.getREP_IMG_LIMIT_SIZE_MAND()) && overSizeLimit)
		    {
			throw new BOException("Warning");
		    }
		    else if("2".equals(vo.getREP_IMG_LIMIT_SIZE_MAND()) && overSizeLimit)
		    {
			throw new BOException("Error");
		    }
		    else
		    { 
			byte[] data =  FileUtil.readFileBytes(upload.getPath());
			uploadImageBO.saveUploadedImage(data, fileName);
			result="successUpload";
		    }
		}
		    
		catch(Exception e)
		{
	            handleException(e, e.getMessage(),null);
		}
		return result;
	}
 }

