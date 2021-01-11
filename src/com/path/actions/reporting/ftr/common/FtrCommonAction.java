package com.path.actions.reporting.ftr.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.reporting.struts2.lib.common.base.ReportingBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;

public class FtrCommonAction extends ReportingBaseAction
{
    HttpServletResponse response = ServletActionContext.getResponse();
    private String from;
    private ReportBO reportBO;
    private DesignerBO designerBO;
    private FcrBO fcrBO;
    private String repId;
    private String updates;
    private String progRef;
    private BigDecimal mismatchId;
    // used to check if we should allow String or just numbers in the selection
    // of criteria columns/related columns
    private BigDecimal crtColOrRelCol;
    private String appName;
    
    public void setFcrBO(FcrBO fcrBO)
    {
        this.fcrBO = fcrBO;
    }


    public BigDecimal getCrtColOrRelCol()
    {
	return crtColOrRelCol;
    }

    public void setCrtColOrRelCol(BigDecimal crtColOrRelCol)
    {
	this.crtColOrRelCol = crtColOrRelCol;
    }

    public BigDecimal getMismatchId()
    {
	return mismatchId;
    }

    public void setMismatchId(BigDecimal mismatchId)
    {
	this.mismatchId = mismatchId;
    }

    public String getProgRef()
    {
	return progRef;
    }

    public void setProgRef(String progRef)
    {
	this.progRef = progRef;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String getRepId()
    {
	return repId;
    }

    public void setRepId(String repId)
    {
	this.repId = repId;
    }

    public void setReportBO(ReportBO reportBO)
    {
	this.reportBO = reportBO;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public String getFrom()
    {
	return from;
    }

    public void setFrom(String from)
    {
	this.from = from;
    }

	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}
	
    /**
     * Method to preview the report's design
     * @return
     */
    public String previewReportDesign()
    {
	try
	{
	    /*
	     * from can have only 3 values:
	     * RepConstantsCommon.MISMATCH_FROM_MAIN_GRID
	     * ,RepConstantsCommon.SNAPSHOT_MODIFIED
	     * ,RepConstantsCommon.SNAPSHOT_DRILLDOWN
	     */
	    BigDecimal id = null;
	    Map<Object, Object> parameters = new HashMap<Object, Object>();
	    ReportParamsCO repParamsCO = new ReportParamsCO();
	    parameters.put("repParamsCO", repParamsCO);
	    SessionCO sessionCO = returnSessionObject();
	    // put all parameters having values from session in repParamsCO
	    String[] properties = new String[] { "baseCurrDecPoint", "baseCurrencyCode", "baseCurrencyName",
		    "branchCode", "clientType", "companyArabName", "companyCode", "companyName", "currentAppName",
		    "exposCurCode", "exposCurName", "fiscalYear", "runningDateRET", "language", "userName",
		    "branchName", "isRTLDir" };

	    PathPropertyUtil.copyProperties(sessionCO, repParamsCO, properties);
	    // setting additional properties requiered for translation
	    repParamsCO.setRepAppName(sessionCO.getCurrentAppName());
	    repParamsCO.setProgRef(get_pageRef());
	    repParamsCO.setRepLanguage(sessionCO.getLanguage());
	    if(from.equals(RepConstantsCommon.SNAPSHOT_DRILLDOWN) || from.equals(RepConstantsCommon.SNAPSHOT_MODIFIED))
	    {
		if(!"".equals(repId))
		{
		    id = new BigDecimal(repId);
		}
	    }
	    else if(from.equals(RepConstantsCommon.MISMATCH_CRITERIA_COLUMN))
	    {
		id = mismatchId;
	    }
	    // get the reportCO in order to retrieve the jrxml
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setPROG_REF(progRef);
	    repSC.setAPP_NAME(appName);
	    IRP_AD_HOC_REPORTCO repIdCO = designerBO.retRepIdByProgRef(repSC);
	    if(repIdCO == null)
	    {
		FTR_OPTCO ftrOptCO = new FTR_OPTCO();
		ftrOptCO.getFtrOptVO().setPROG_REF(progRef);
		ftrOptCO.getFtrOptVO().setAPP_NAME(ConstantsCommon.REP_APP_NAME);
		if(fcrBO.returnDynamicReportByRef(ftrOptCO) == null || returnCommonLibBO().returnIsSybase() == 1)
		{
		    response.setContentType("text/html");
		    response.getOutputStream().write("".getBytes(CommonUtil.ENCODING));
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		    return null;
		}
		else
		{
		    repSC.setPROG_REF(ConstantsCommon.FCR_MAIN_REPORT_REF);
		    repIdCO = designerBO.retRepIdByProgRef(repSC);
		}
	    }

	    IRP_AD_HOC_REPORTCO repCO = designerBO.returnReportById(repIdCO.getREPORT_ID());
	    HashMap<String, Object> hashLiveSearch = new HashMap<String, Object>();
	    String castType = from;
	    Object[] obj;
	    if(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(id))
	    {
		obj = reportBO.generateReportDesign(repCO, hashLiveSearch, castType, null, crtColOrRelCol, parameters);
	    }
	    else
	    {
		obj = reportBO.generateReportDesign(repCO, hashLiveSearch, castType, id, crtColOrRelCol, parameters);
	    }
	    if(obj != null)
	    {
		hashLiveSearch = (HashMap<String, Object>) obj[1];
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		byte[] bb = (byte[]) obj[0];
		if(from.equals(RepConstantsCommon.MISMATCH_CRITERIA_COLUMN))
		{
		    repSessionCO.getRepMisParameterScreenHash().put(RepConstantsCommon.MISMATCH_CRITERIA_COLUMN_HASH,
			    hashLiveSearch);
		}
		else if(from.equals(RepConstantsCommon.SNAPSHOT_DRILLDOWN))
		{
		    repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_HASH_LIVE_DRIL_COL,
			    hashLiveSearch);
		}
		else if(from.equals(RepConstantsCommon.SNAPSHOT_MODIFIED))
		{
		    repSessionCO.getSnParameterScreenHash().put(RepConstantsCommon.SNAPSHOT_HASH_LIVE_MOD_COL,
			    hashLiveSearch);
		}
		if(bb!=null)
		{
		    response.setContentType("text/html");
		    response.getOutputStream().write(bb);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	    }
	    return null;
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	    return null;
	}
    }
}
