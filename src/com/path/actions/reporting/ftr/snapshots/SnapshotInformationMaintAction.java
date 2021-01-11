package com.path.actions.reporting.ftr.snapshots;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.path.actions.ReportAction.SepartorFormat;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.ftr.controlRecord.ControlRecordBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ReportOutputCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterMaintAction.java used to
 */
public class SnapshotInformationMaintAction extends ReportingLookupBaseAction
{
    private SnapshotParameterBO snapshotParameterBO;
    private REP_SNAPSHOT_INFORMATIONCO repSnapshotInformationCO;
    private CommonLookupBO commonLookupBO;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> infStatusList;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> reportFrequencyList;
    private String updates;
    private String snpChanges;
    private BigDecimal code;
    private String filename;
    private InputStream fileStream;
    HttpServletResponse response = ServletActionContext.getResponse();
    private ReportOutputCO reportOutputCO;
    private ReportBO reportBO;
    private String contentType;
    private String contentHeader;
    ArrayList<SYS_PARAM_LOV_TRANSVO> reportFormatsList;
    ArrayList<SepartorFormat> csvSeparatorsList;
    private ControlRecordBO controlRecordBO;
    private String hideCol;
    private CommonReportingBO commonReportingBO;
    private REP_SNAPSHOT_INFOVO repSnapshotInfoVO;
    private REP_SNAPSHOT_PARAMVO repSnapshotParamVO;
    
    
    public REP_SNAPSHOT_INFOVO getRepSnapshotInfoVO()
    {
        return repSnapshotInfoVO;
    }
    public void setRepSnapshotInfoVO(REP_SNAPSHOT_INFOVO repSnapshotInfoVO)
    {
        this.repSnapshotInfoVO = repSnapshotInfoVO;
    }
    public REP_SNAPSHOT_PARAMVO getRepSnapshotParamVO()
    {
        return repSnapshotParamVO;
    }
    public void setRepSnapshotParamVO(REP_SNAPSHOT_PARAMVO repSnapshotParamVO)
    {
        this.repSnapshotParamVO = repSnapshotParamVO;
    }
    public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
	this.commonReportingBO = commonReportingBO;
    }
    public ArrayList<SepartorFormat> getCsvSeparatorsList()
    {
	return csvSeparatorsList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getReportFormatsList()
    {
	return reportFormatsList;
    }

    public String getSnpChanges()
    {
	return snpChanges;
    }

    public void setSnpChanges(String snpChanges)
    {
	this.snpChanges = snpChanges;
    }

    public String getContentHeader()
    {
	return contentHeader;
    }

    public void setContentHeader(String contentHeader)
    {
	this.contentHeader = contentHeader;
    }

    public String getContentType()
    {
	return contentType;
    }

    public void setContentType(String contentType)
    {
	this.contentType = contentType;
    }

    public void setReportBO(ReportBO reportBO)
    {
	this.reportBO = reportBO;
    }

    public ReportOutputCO getReportOutputCO()
    {
	return reportOutputCO;
    }

    public void setReportOutputCO(ReportOutputCO reportOutputCO)
    {
	this.reportOutputCO = reportOutputCO;
    }

    public InputStream getFileStream()
    {
	return fileStream;
    }

    public void setFileStream(InputStream fileStream)
    {
	this.fileStream = fileStream;
    }

    public String getFilename()
    {
	return filename;
    }

    public void setFilename(String filename)
    {
	this.filename = filename;
    }

    public BigDecimal getCode()
    {
	return code;
    }

    public void setCode(BigDecimal code)
    {
	this.code = code;
    }

    public String getHideCol()
    {
	return hideCol;
    }

    public void setHideCol(String hideCol)
    {
	this.hideCol = hideCol;
    }

    public void setControlRecordBO(ControlRecordBO controlRecordBO)
    {
	this.controlRecordBO = controlRecordBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getReportFrequencyList()
    {
	return reportFrequencyList;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getInfStatusList()
    {
	return infStatusList;
    }

    public void setInfStatusList(ArrayList<SYS_PARAM_LOV_TRANSVO> infStatusList)
    {
	this.infStatusList = infStatusList;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public REP_SNAPSHOT_INFORMATIONCO getRepSnapshotInformationCO()
    {
	return repSnapshotInformationCO;
    }

    public void setRepSnapshotInformationCO(REP_SNAPSHOT_INFORMATIONCO repSnapshotInformationCO)
    {
	this.repSnapshotInformationCO = repSnapshotInformationCO;
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {
	this.snapshotParameterBO = snapshotParameterBO;
    }

    public String execute() throws Exception
    {
	SessionCO sessionCO = returnSessionObject();
	BTR_CONTROLCO btrControlCO;
	ReportingSessionCO repCO = returnReportingSessionObject(get_pageRef());
	SnapshotParameterSC sc = new SnapshotParameterSC();
	sc.setCOMP_CODE(sessionCO.getCompanyCode());
	repCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_INFO_INIT_MAP,
		snapshotParameterBO.fillHashInfoInit(sc));
	// added to check the check box in snapshot parameter screen
	BigDecimal compCode = sessionCO.getCompanyCode();
	BTR_CONTROLSC btSC = new BTR_CONTROLSC();
	btSC.setCOMP_CODE(compCode);
	btrControlCO = controlRecordBO.retrieveControlRecordData(btSC);
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplaySnValPath = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO nameGenerate = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO nameView = new SYS_PARAM_SCREEN_DISPLAYVO();
	if(RepConstantsCommon.Y.equals(btrControlCO.getBtrControlVO().getSITCOM_ENABLE_YN()))
	{
	    screenDisplaySnValPath.setIS_READONLY(BigDecimal.ONE);
	    screenDisplaySnValPath.setIS_VISIBLE(BigDecimal.ONE);
	    setHideCol(RepConstantsCommon.FALSE);
	    nameGenerate.setLABEL_KEY(getText("reporting.launch") + " "
		    + btrControlCO.getBtrControlVO().getSITCOM_TEXT());
	    nameGenerate.setIS_VISIBLE(BigDecimal.ONE);
	    nameView.setLABEL_KEY(getText("snapshots.view") + " " + btrControlCO.getBtrControlVO().getSITCOM_TEXT());
	    nameView.setIS_VISIBLE(BigDecimal.ONE);
	}
	else
	{
	    screenDisplaySnValPath.setIS_READONLY(BigDecimal.ZERO);
	    screenDisplaySnValPath.setIS_VISIBLE(BigDecimal.ZERO);
	    setHideCol(ConstantsCommon.TRUE);
	    nameGenerate.setIS_VISIBLE(BigDecimal.ZERO);
	    nameView.setIS_VISIBLE(BigDecimal.ZERO);
	}
	getAdditionalScreenParams().put("genFileLabel_" + get_pageRef(), nameGenerate);
	getAdditionalScreenParams().put("viewFileLabel_" + get_pageRef(), nameView);
	getAdditionalScreenParams().put("genFile_" + get_pageRef(), screenDisplaySnValPath);
	getAdditionalScreenParams().put("viewFile_" + get_pageRef(), screenDisplaySnValPath);
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SNAPSHOTS_INF_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	infStatusList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.FREQUENCY_SNP_LOV_TYPE);
	reportFrequencyList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	SYS_PARAM_LOV_TRANSVO vo = new SYS_PARAM_LOV_TRANSVO();
	// to add empty option to the frequency combo
	vo.setVALUE_CODE("");
	vo.setVALUE_DESC("");
	reportFrequencyList.add(0, vo);
	set_showSmartInfoBtn(RepConstantsCommon.FALSE);

	// fill format lkp
	sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(ConstantsCommon.FILE_FORMAT_LOV_TYPE);
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	reportFormatsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);

	csvSeparatorsList = new ArrayList<SepartorFormat>();
	csvSeparatorsList.add(new SepartorFormat(",", ","));
	csvSeparatorsList.add(new SepartorFormat(getText("reporting.tab"), "\\t"));

	return "snapshotInformationList";
    }

    public String reloadFrm() throws Exception
    {
	execute();
	return SUCCESS;
    }

    public String saveAllSnInfo() throws Exception
    {
	try
	{
	    ArrayList lstMod = new ArrayList();
	    ArrayList lstDel = new ArrayList();
	    if(updates != null && !updates.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates, REP_SNAPSHOT_INFORMATIONCO.class);
		lstMod = gu.getLstModify();
		lstDel = gu.getLstDelete();
	    }
	    ReportingSessionCO repCO = returnReportingSessionObject(get_pageRef());
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.SNAPSHOT_INFO_KEY_REF);
	    snapshotParameterBO.saveAllSnInfoDetails(lstMod, lstDel, repCO.getSnParameterScreenHash(), refCO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    execute();
	    return ERROR;
	}
	execute();
	return SUCCESS;
    }

    public String viewSnapshot() throws Exception
    {
	return "viewSnapshot";
    }

    public String loadSnapshot()
    {
	String var_reportName = "";
	try
	{
	    String var_format;
	    String modifySnp = getUpdates();
	    boolean isModify = false;
	    if(RepConstantsCommon.Y.equals(modifySnp))
	    {
		isModify = true;
		var_format = ConstantsCommon.HTML_REP_FORMAT;
	    }
	    else
	    {
		var_format = repSnapshotInformationCO.getSNP_FORMAT();
	    }
	    SessionCO sessionCO = returnSessionObject();
	    REP_SNAPSHOT_INFOVO infoVO = new REP_SNAPSHOT_INFOVO();
	    infoVO.setREP_SNAPSHOT_ID(getCode());
	    REP_SNAPSHOT_INFOVO retInfoVO = snapshotParameterBO.retSnpInfoVOByRepSnpId(infoVO);

	    HashMap parameters = (HashMap) CommonUtil.objectFromBytes(retInfoVO.getSNAPSHOT_PARAMS());
	  
	    //return the generated file name from irp_ad_hoc_report or ftr_opt
	    REP_SNAPSHOT_INFORMATIONCO mainRepDetCO=snapshotParameterBO.retSnapshotMainReportDetails(retInfoVO);

	    Date executionDate = returnCommonLibBO().returnSystemDateWithTime();
	    String startExecution = DateUtil.format(executionDate, "yyyy-MM-dd HH-mm-ss aa");
	    var_reportName = retInfoVO.getREP_SNAPSHOT_ID() + "_" + sessionCO.getUserName() + "_" + startExecution;

	    parameters.put("isNew", RepConstantsCommon.N);
	    parameters.put("pathCnt", Integer.valueOf(-1));
	    ArrayList<HashMap<String, Object>> pathArr = (ArrayList<HashMap<String, Object>>) CommonUtil
		    .objectFromBytes(retInfoVO.getREP_BLOB());
	    parameters.put("pathArr", pathArr);
	    boolean showHeadFoot = false;
	    if(RepConstantsCommon.Y.equals(repSnapshotInformationCO.getSHOW_HEAD_FOOT()))
	    {
		showHeadFoot = true;
	    }
	    
	   parameters.put(RepConstantsCommon.COMP_CODE_COL+"~1", sessionCO.getCompanyCode());
	    
	    reportOutputCO = reportBO.printSnapshot(var_reportName, parameters, var_format, retInfoVO, sessionCO
		    .getCurrentAppName(), sessionCO.getUserName(), isModify, repSnapshotInformationCO
		    .getCSV_SEPARATOR(), showHeadFoot);

	    if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(var_format))
	    {
		var_format = ConstantsCommon.XLS_REP_FORMAT;
	    }
	    else if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(var_format))
	    {
		var_format = ConstantsCommon.CSV_REP_FORMAT;
	    }
	    byte[] reportBytes = reportOutputCO.getReportBytes();
	    String ext = ConstantsCommon.HTML_EXT;
	    contentType = "text/html";
	    if(RepConstantsCommon.Y.equals(modifySnp))
	    {
		response.setContentType(contentType);
		response.getOutputStream().write(reportBytes);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	    }
	    else
	    {
		if(var_format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT))
		{
		    ext = ConstantsCommon.PDF_EXT;
		    contentType = "application/pdf";
		}
		else if(var_format.equalsIgnoreCase(ConstantsCommon.DOC_REP_FORMAT))
		{
		    ext = ConstantsCommon.DOC_EXT;
		    contentType = "application/vnd.ms-word";
		}
		else if(var_format.equalsIgnoreCase(ConstantsCommon.XLS_REP_FORMAT))
		{

		    ext = ConstantsCommon.XLS_EXT;
		    contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}
		else if(var_format.equalsIgnoreCase(ConstantsCommon.CSV_REP_FORMAT) || var_format.equalsIgnoreCase(ConstantsCommon.TXT_FILE))
		{
		    ext = ConstantsCommon.TXT_EXT;
		    contentType = "application/txt";
		}
		else if(var_format.equalsIgnoreCase(ConstantsCommon.DAT_EXT))
		{
			 ext = ConstantsCommon.DAT_EXT.toLowerCase();
			    contentType = "application/txt";
		}
		else if(var_format.equalsIgnoreCase(ConstantsCommon.SQL_REP_FORMAT))
		{
		    ext = ConstantsCommon.SQL_EXT;
		    contentType = "application/txt";
		}
		else if(ConstantsCommon.ODS_REP_FORMAT.equalsIgnoreCase(var_format))
		{
		    ext = ConstantsCommon.ODS_REP_FORMAT.toLowerCase();
		    contentType = "application/vnd.oasis.opendocument.spreadsheet";
		}
		String fileNameDisplay = retInfoVO.getDESCRIPTION();
		if(mainRepDetCO!=null && !StringUtil.nullToEmpty(mainRepDetCO.getRepSnapshotInfoVO().getDESCRIPTION()).isEmpty())
		{
		    fileNameDisplay=commonReportingBO.returnGeneratedFileName(mainRepDetCO.getRepSnapshotInfoVO().getDESCRIPTION(),parameters);
		}
		fileStream = new ByteArrayInputStream(reportBytes);
		filename = fileNameDisplay + "." + ext;
		contentHeader = "Content-Disposition:attachment;filename=\"" + fileNameDisplay + "." + ext + "\"";
		// setting cookie in order to let file download function
		// on client side to go to success callback
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	finally
	{ 
	    try
	    {
		// delete the jrxml
		commonReportingBO.deleteTempFiles(var_reportName);
	    }
	    catch(Exception e)
	    {
		log.error(e.getMessage());
	    }

	}
	return "successLoadSnp";
	// return "successJson";
    }

    public String saveSnapshotDataChanges()
    {
	try
	{
	    REP_SNAPSHOT_INFOVO infoVO = new REP_SNAPSHOT_INFOVO();
	    infoVO.setREP_SNAPSHOT_ID(getCode());
	    REP_SNAPSHOT_INFOVO retInfoVO = snapshotParameterBO.retSnpInfoVOByRepSnpId(infoVO);
	    ArrayList<HashMap<String, Object>> pathArr = (ArrayList<HashMap<String, Object>>) CommonUtil
		    .objectFromBytes(retInfoVO.getREP_BLOB());
	    String obj;
	    String feKey;
	    String newFeVal;
	    String rowNb;
	    String feName;
	    String feType;
	    Object parsedNewFeVal;
	    HashMap<String, Object> tempHm;
	    if(snpChanges.length() > 0)
	    {
		snpChanges = snpChanges.substring(1, snpChanges.length());
		String[] snpChangesArr = snpChanges.split("&");
		for(int i = 0; i < snpChangesArr.length; i++)
		{
		    obj = snpChangesArr[i];
		    feKey = obj.split("=")[0];
		    newFeVal = obj.split("=")[1];
		    rowNb = feKey.split("~")[0];
		    feName = feKey.split("~")[1];
		    feType = feKey.split("~")[2];
		    tempHm = pathArr.get(Integer.parseInt(rowNb));
		    if(RepConstantsCommon.NUMBER_N.equals(feType))
		    {
			parsedNewFeVal = new BigDecimal(newFeVal);
		    }
		    else if(RepConstantsCommon.DATE_D.equals(feType))
		    {
			parsedNewFeVal = DateUtil.parseDate(newFeVal, "dd/MM/yyyy");
		    }
		    else if(RepConstantsCommon.VARCHAR_V.equals(feType))
		    {
			parsedNewFeVal = newFeVal;
		    }
		    else
		    {
			parsedNewFeVal = newFeVal;
			log.debug(">>>>>>>>>>>>>>>   saveSnapshotDataChanges >>>>>  feType==" + feType
				+ " not supported");
		    }
		    tempHm.put(feName, parsedNewFeVal);
		}
	    }
	    // save changes into DB
	    infoVO.setREP_BLOB(CommonUtil.objectToBytes(pathArr));
	    snapshotParameterBO.updateSnpInfo(infoVO);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "successJson";
    }

    public String retSnpFormatDetails()
    {
	try
	{
	    SnapshotParameterSC sc = new SnapshotParameterSC();
	    sc.setPROG_REF(repSnapshotInformationCO.getRepSnapshotParamVO().getREP_REFERENCE());
	    sc.setSNP_REPORT_TYPE(repSnapshotInformationCO.getREPORT_TYPE());
	    sc.setCOMP_CODE(returnSessionObject().getCompanyCode());
	    repSnapshotInformationCO = snapshotParameterBO.retSnpFormatDetails(sc);
	    // if related data has been deleted
	    if(repSnapshotInformationCO == null)
	    {
		repSnapshotInformationCO = new REP_SNAPSHOT_INFORMATIONCO();
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "successJson";
    }
}
