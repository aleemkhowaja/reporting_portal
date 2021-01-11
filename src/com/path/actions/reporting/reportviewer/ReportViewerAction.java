package com.path.actions.reporting.reportviewer;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.reportviewer.ReportViewerBO;
import com.path.lib.common.util.StringUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.struts2.lib.common.base.BaseAction;
import com.path.vo.reporting.reportviewer.ReportViewerCO;

public class ReportViewerAction extends BaseAction
{
    private String folder;
    private ReportViewerBO reportViewerBO;
    private ReportViewerCO reportViewerCO;
    List<ReportViewerCO> LinkPathList;
    List<ReportViewerCO> LeftMenuList;
    HttpServletResponse response = ServletActionContext.getResponse();
    private String elementPath;
    private String elementName;
    private String elementType;
    byte[] PreviewByte;
    Integer countPath;
    private String update;
    private String folderNotExist;

    public String getFolderNotExist()
    {
        return folderNotExist;
    }

    public void setFolderNotExist(String folderNotExist)
    {
        this.folderNotExist = folderNotExist;
    }

    public String getUpdate()
    {
	return update;
    }

    public void setUpdate(String update)
    {
	this.update = update;
    }

    public String getFolder()
    {
	return folder;
    }

    public void setFolder(String folder)
    {
	this.folder = folder;
    }

    public ReportViewerBO getReportViewerBO()
    {
	return reportViewerBO;
    }

    public void setReportViewerBO(ReportViewerBO reportViewerBO)
    {
	this.reportViewerBO = reportViewerBO;
    }

    public ReportViewerCO getReportViewerCO()
    {
	return reportViewerCO;
    }

    public void setReportViewerCO(ReportViewerCO reportViewerCO)
    {
	this.reportViewerCO = reportViewerCO;
    }

    public List<ReportViewerCO> getLinkPathList()
    {
	return LinkPathList;
    }

    public void setLinkPathList(List<ReportViewerCO> linkPathList)
    {
	LinkPathList = linkPathList;
    }

    public List<ReportViewerCO> getLeftMenuList()
    {
	return LeftMenuList;
    }

    public void setLeftMenuList(List<ReportViewerCO> leftMenuList)
    {
	LeftMenuList = leftMenuList;
    }

    public byte[] getPreviewByte()
    {
	return PreviewByte;
    }

    public void setPreviewByte(byte[] previewByte)
    {
	PreviewByte = previewByte;
    }

    public String getElementPath()
    {
	return elementPath;
    }

    public void setElementPath(String elementPath)
    {
	this.elementPath = elementPath;
    }

    public String getElementName()
    {
	return elementName;
    }

    public void setElementName(String elementName)
    {
	this.elementName = elementName;
    }

    public String getElementType()
    {
	return elementType;
    }

    public void setElementType(String elementType)
    {
	this.elementType = elementType;
    }

    public Integer getCountPath()
    {
	return countPath;
    }

    public void setCountPath(Integer countPath)
    {
	this.countPath = countPath;
    }

    public String execute()
    {
	try
	{

	    // set the parent path with "PROC" in case was empty in the
	    // OPT_EXTENDED Url
	    if(reportViewerCO == null)
	    {
		set_showSmartInfoBtn("false");
		set_showRecordLogsBtn("false");
		set_enableAudit(false);
		reportViewerCO = new ReportViewerCO();
		folder = StringUtil.nullEmptyToValue(folder, ConstantsCommon.PROC_APP_NAME);
		reportViewerCO.setElementPath(folder);
		reportViewerCO.setElementType(RepConstantsCommon.FOLDER);
	    }
	    // get the path list related to the header
	    retLinkPathList(reportViewerCO);
	    // get the path list related to the left menu
	    retLeftMenuList(reportViewerCO);

	    String[] linkPathFolder;
	    linkPathFolder = reportViewerCO.getElementPath().split("/");
	    // if call from opt extended
	    if(folder != null && !LinkPathList.isEmpty() && linkPathFolder.length > 1)
	    {
		reportViewerCO = LinkPathList.get(LinkPathList.size() - 1);
		countPath = LinkPathList.size();
		LinkPathList.clear();
		LinkPathList.add(reportViewerCO);
	    }
	    else
	    {
		if(countPath == null)
		{
		    countPath = 1;
		    if(LinkPathList.isEmpty())
		    {
			folderNotExist = linkPathFolder[linkPathFolder.length-1] + " " + getText("not_exist");
		    }
		}
		// remove the object that should not appear in the param link in
		// case the parameter folder in OPT extended contains more than
		// one level
		for(int i = 0; i < countPath - 1; i++)
		{
		    LinkPathList.remove(0);
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return ERROR_ACTION;
	}
	if(folder != null)
	{
	    return "reportViewer";
	}
	else if("1".equals(update))
	{
	    return "northViewer";
	}
	else if("2".equals(update))
	{
	    return "westViewer";
	}
	else
	{
	    return null;
	}
    }

    /**
     * Method that will get the path list related to the header
     */
    public ReportViewerCO retLinkPathList(ReportViewerCO reportViewerCO)
    {
	try
	{
	    LinkPathList = reportViewerBO.retLinkPathList(reportViewerCO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return reportViewerCO;
    }

    /**
     * Method that will get the path list related to the left menu
     */
    public ReportViewerCO retLeftMenuList(ReportViewerCO reportViewerCO)
    {
	try
	{
	    LeftMenuList = reportViewerBO.retLeftMenuList(reportViewerCO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return reportViewerCO;
    }

    /**
     * Method that will load the report in the right div
     */
    public String loadDiv()
    {
	try
	{
	    PreviewByte = reportViewerBO.retReportBytes(reportViewerCO);
	    response.setContentType("text/html");
	    if(PreviewByte != null)
	    {
		if(PreviewByte.length<3145728)
		{
		    response.getOutputStream().write(PreviewByte);
		}
		else
		{
		    response.getOutputStream().write("".getBytes(CommonUtil.ENCODING));  
		}
	    }
	    else
	    {
		response.getOutputStream().write("File not found".getBytes(CommonUtil.ENCODING));
	    }
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}
	catch(Exception ex)
	{
	    handleException(ex, null, null);
	}
	return null;
    }

    /**
     * Method that will open the browser upload bar in case the user needs to
     * open or save it
     */
    public void previewReportViewer()
    {
	try
	{
	    reportViewerCO = new ReportViewerCO();
	    reportViewerCO.setElementPath(elementPath);
	    reportViewerCO.setElementName(elementName);
	    reportViewerCO.setElementType(elementType);
	    byte[] reportBytes = reportViewerBO.retReportBytes(reportViewerCO);
	    response.addHeader("Content-Disposition", "attachment;filename=\"" + elementName + "\"");
	    if(reportBytes != null)
	    {
		if(elementName.toUpperCase().endsWith(".".concat(ConstantsCommon.PDF_REP_FORMAT)))
		{
		    response.setContentType("application/pdf");
		}
		else if(elementName.toUpperCase().endsWith(".".concat(ConstantsCommon.DOC_REP_FORMAT)))
		{
		    response.setContentType("application/vnd.ms-word");
		}
		else if(elementName.toUpperCase().endsWith(".".concat(ConstantsCommon.XLS_REP_FORMAT)))
		{
		    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		}
		else if(elementName.toUpperCase().endsWith(".".concat(ConstantsCommon.CSV_REP_FORMAT))
			|| elementName.toLowerCase().endsWith(".".concat(ConstantsCommon.TXT_EXT)))
		{
		    response.setContentType("application/txt");
		}
		else if(elementName.toUpperCase().endsWith(".".concat(ConstantsCommon.ODS_REP_FORMAT)))
		{
		    response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
		}
	    }
	    else
	    {
		response.getOutputStream().write("File not found".getBytes(CommonUtil.ENCODING));
	    }
	    response.getOutputStream().write(reportBytes);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
    }
}
