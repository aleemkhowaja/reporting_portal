package com.path.actions.reporting.designer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.dbmaps.vo.IRP_VERIFIED_REPORTSVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.StringUtil;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSSC;

public class SnapshotsAction extends ReportingGridBaseAction implements ServletRequestAware, ServletResponseAware
{
    IRP_SNAPSHOTSSC snapShotSC = new IRP_SNAPSHOTSSC();
    private String repName;
    private String repFormat;
    private String startDate;
    private String executedBy;
    private int isDB;
    private int snpId;
    private String fileName;
    private SnapShotBO snapShotBO;
    private BigDecimal connId;
    private ReportBO reportBO;
    
    private int snpRef;   

    public int getSnpRef()
    {
	return snpRef;
    }

    public void setSnpRef(int snpRef)
    {
	this.snpRef = snpRef;
    }
	
    public void setReportBO(ReportBO reportBO)
    {
        this.reportBO = reportBO;
    }

    public BigDecimal getConnId()
    {
	return connId;
    }

    public void setConnId(BigDecimal connId)
    {
	this.connId = connId;
    }

    public void setSnapShotBO(SnapShotBO snapShotBO)
    {
	this.snapShotBO = snapShotBO;
    }

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public String getFileName()
    {
	return fileName;
    }

    public void setFileName(String fileName)
    {
	this.fileName = fileName;
    }

    public int getSnpId()
    {
	return snpId;
    }

    public void setSnpId(int snpId)
    {
	this.snpId = snpId;
    }

    public int getIsDB()
    {
	return isDB;
    }

    public void setIsDB(int isDB)
    {
	this.isDB = isDB;
    }

    public String getRepName()
    {
	return repName;
    }

    public void setRepName(String repName)
    {
	this.repName = repName;
    }

    public String getRepFormat()
    {
	return repFormat;
    }

    public void setRepFormat(String repFormat)
    {
	this.repFormat = repFormat;
    }

    public String getStartDate()
    {
	return startDate;
    }

    public void setStartDate(String startDate)
    {
	this.startDate = startDate;
    }

    public String getExecutedBy()
    {
	return executedBy;
    }

    public void setExecutedBy(String executedBy)
    {
	this.executedBy = executedBy;
    }

    public String loadSnapshots() throws JSONException
    {
	set_showSmartInfoBtn("false");
	return "snapShots";
    }

    public Object getModel()
    {
	return snapShotSC;
    }

    public String loadSnapShotsList() throws BaseException
    {
	try
	{
	    copyproperties(snapShotSC);
	    snapShotSC.setEXECUTED_BY(returnUserName());
	    SessionCO sessionCO = returnSessionObject();
	    snapShotSC.setFILE_FORMAT_LOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	    snapShotSC.setLANG_CODE(sessionCO.getLanguage());
	    snapShotSC.setCompCode(sessionCO.getCompanyCode());
	    snapShotSC.setBranchCode(sessionCO.getBranchCode());
	    List<IRP_SNAPSHOTSCO> lst = snapShotBO.retSnapshotList(snapShotSC);
	    int lstCnt = snapShotBO.retSnapshotListCount(snapShotSC);
	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	return "success";
    }
    
    /**
     * Used to return list of snapshots based on the selected user
     */
    public String loadUserAccessSnpList()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    copyproperties(snapShotSC);
	    snapShotSC.setEXECUTED_BY(StringUtil.nullToEmpty(getExecutedBy()));
	    snapShotSC.setFILE_FORMAT_LOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	    snapShotSC.setLANG_CODE(sessionCO.getLanguage());
	    snapShotSC.setCompCode(sessionCO.getCompanyCode());
	    List<IRP_SNAPSHOTSCO> lst = snapShotBO.retUserAccessSnpList(snapShotSC);
	    int lstCnt = snapShotBO.retUserAccessSnpCount(snapShotSC);
	    setRecords(lstCnt);
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	   handleException(e, "", "") ;
	}
	return SUCCESS;
    }

    public void previewSnapshot() throws JSONException
    {
	try
	{
	    String repFormat = getRepFormat();
	    if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(repFormat))
	    {
		repFormat = ConstantsCommon.XLSX_EXT;
	    }
	    else if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(repFormat) || ConstantsCommon.CSV_REP_FORMAT.equals(repFormat))
	    {
		repFormat = ConstantsCommon.TXT_EXT;
	    }
	    int isDB = getIsDB();

	    if(isDB == 0)
	    {
		String repName = getFileName();
		Object[] obj = snapShotBO.readSnapshotFromRepository(repName, repFormat);
		if(obj[0] == null)
		{
		    throw new FileNotFoundException();
		}
		else
		{
		    byte[] reportBytes = (byte[]) obj[0];
		    repFormat = (String) obj[1];
		    response.addHeader("Content-Disposition", "attachment;filename=\"" + repName + "."
			    + repFormat + "\"");
		    response.setContentType("application/vnd.ms-word;charset=unicode");
		    response.getOutputStream().write(reportBytes);
		}
	    }
	    else
	    {
		IRP_SNAPSHOTSSC snpSC = new IRP_SNAPSHOTSSC();
		snpSC.setSNAPSHOT_ID(new BigDecimal(getSnpId()));
		snpSC.setIS_DB(new BigDecimal(isDB));
		snpSC.setCONN_ID(getConnId());
		IRP_SNAPSHOTSCO retSnpCO = snapShotBO.retReportContent(snpSC);
		byte[] reportBytes = retSnpCO.getREPORT_CONTENT();		
		
		 if(reportBytes==null)
		 {
		     reportBytes=new byte[0];
		 }
		 else
		 {
		     if(repFormat.equals(ConstantsCommon.HTML_EXT))
		     {
			 boolean isZip = FileUtil.checkIfZip(reportBytes);
			 if(isZip)
			 {
			     repFormat = "zip";
			 }
		     }
		 }
		
		response.addHeader("Content-Disposition", "attachment;filename=\"" + getFileName() + "." + repFormat
			+ "\"");
		response.setContentType("application/vnd.ms-word;charset=unicode");
		response.getOutputStream().write(reportBytes);
	    }
	}
	catch(FileNotFoundException fe)
	{
	    String errorString = "<b>The following file has been deleted</b>";
	    response.setHeader("Content-Disposition", "inline; filename=\"ReportNotFound.html\"");
	    response.setContentType("application/html");
	    try
	    {
		response.getOutputStream().write(errorString.getBytes(FileUtil.DEFAULT_FILE_ENCODING));
	    }
	    catch(IOException e)
	    {
		handleException(e, null, null);
		//log.error(e, "The following file has been deleted");
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    //log.error(e, "The following file has been deleted");
	}
	finally
	{
	    // apply audit
	    IRP_SNAPSHOTSCO snpCO = new IRP_SNAPSHOTSCO();
	    snpCO.setSNAPSHOT_ID(new BigDecimal(getSnpId()));
	    applyRetrieveAudit(AuditConstant.SNAPSHOTS_KEY_REF, snpCO, snpCO);
	}
    }

    public String retSnpTrxNb() throws Exception
    {
	try
	{
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.RETRIEVE);
	    refCO.setKeyRef(AuditConstant.SNAPSHOTS_KEY_REF);
	    IRP_SNAPSHOTSCO snpCO = new IRP_SNAPSHOTSCO();
	    snpCO.setSNAPSHOT_ID(new BigDecimal(getSnpId()));
	    setAuditTrxNbr(auditBO.checkAuditKey(snpCO, refCO));
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "success";
    }

    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response)
    {
	this.response = response;
    }
    
    public String verifySnapshot()
    {
	try
	{  
	    String programRef;
	    if(snpRef == 1)
	    {
		programRef = getRepName().split("_")[0];
	    }
	    else
	    {
		programRef = getFileName().split("_")[0];
	    }
	    SessionCO sessionCO = returnSessionObject();
	    
	    IRP_VERIFIED_REPORTSVO vo = new IRP_VERIFIED_REPORTSVO();
	    vo.setBRANCH_CODE(sessionCO.getBranchCode());
	    vo.setAPP_NAME(sessionCO.getCurrentAppName());
	    vo.setUSER_ID( sessionCO.getUserName());
	    vo.setPAGES_NUMBER( BigDecimal.ZERO);
	    vo.setREP_ID(BigDecimal.valueOf(getSnpId()));
	    vo.setREP_TITLE(RepConstantsCommon.VERIFIED_SNAPSHOTS);
	    vo.setPROG_REF(programRef);
	    reportBO.verifyReport(vo);
	} 
	catch(Exception e)
	{
	 handleException(e, getText("snapshots.errorVerify"),getText("snapshots.cannotVerify"));   
	}
	return SUCCESS;
    }
    
}
