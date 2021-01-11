package com.path.actions.reporting.designer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.path.bo.reporting.designer.ImageBO;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ImageCO;



public class ImageAction extends ReportingGridBaseAction 
{
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
    private String updates;
    private ImageBO imageBO;
    

    public void setImageBO(ImageBO imageBO)
    {
        this.imageBO = imageBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String openImagesList() throws Exception
    {
	return "success";
    }

    public Object getModel()
    {
	return repSC;
    }

    /**
     * Method called on load of the images grid
     * @return
     */
    public String loadImagesList() 
    {
	try
	{
	    copyproperties(repSC);
	    if(checkNbRec(repSC))
	    {
		setRecords(imageBO.retImagesGridRecordsCount(repSC));
	    }
	    setGridModel(imageBO.retImagesGridsRecords(repSC));
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }
    
    /**
     * Method for loading an image object from db
     */
    public void loadImage()
    {
	String imageName = getUpdates();
	try
	{
	    byte[] data = imageBO.retImgObject(imageName);
	    response.reset();
	    response.getOutputStream().write(data);
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }

    /**
     * Method called on image deletion
     */
    public void deleteImage()
    {
	try
	{
	    String fileName = getUpdates();
	    ImageCO imgCO = new ImageCO();
	    imgCO.setFileName(fileName);
	    imageBO.deleteImg(imgCO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }
}
