package com.path.actions.reporting.designer;

import java.util.ArrayList;
import java.util.List;

import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ImageCO;

public class UploadDownloadImagesAction extends ReportingLookupBaseAction
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
    
    
    
    public Object getModel()
    {
	return repSC;
    }
    
    public String openUpDownloadImages()throws Exception
    {
  	return "uploadDownloadImages";
    }
    
    public String loadImagesUD() throws Exception
    {
	try
	{

	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reptCO = repSessionCO.getReportCO();

	    List<ImageCO> imageLst = reptCO.getImagesList();

	    copyproperties(repSC);
	    // apply flipping

	    int nbRec = repSC.getNbRec();
	    int recToSkip = repSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(imageLst.size() < maxRec)
	    {
		maxRec = imageLst.size();
	    }
	    ArrayList<ImageCO> lst = new ArrayList<ImageCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((ImageCO) imageLst.get(i));
	    }

	    setRecords(imageLst.size());
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error("Error in loadImagesUD() method in UploadDownloadImagesAction");
	    handleException(e, "Error in loadImagesUD() in UploadDownloadImagesAction", null);
	}
	return SUCCESS;
    }

}
