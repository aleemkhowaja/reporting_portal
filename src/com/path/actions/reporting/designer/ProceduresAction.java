package com.path.actions.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.ProceduresBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;

public class ProceduresAction extends ReportingLookupBaseAction
{
    private final IRP_REP_PROCSC repProcSC = new IRP_REP_PROCSC();
    private ProceduresBO procBO;
    private String updates;
    private BigDecimal code1;
    private BigDecimal REPORT_ID;
    private BigDecimal PROC_ID;
    private String PROC_NAME;
    private String PROCED_NAME;
    private String PROC_DESC;
    private BigDecimal PROCED_ID;
    private String update1;
    private String update2;
    private String PARAMETER_TYPE;
    private CommonLookupBO commonLookupBO;    
    private ArrayList<SYS_PARAM_LOV_TRANSVO> procParamOutTypeCmb = new ArrayList<SYS_PARAM_LOV_TRANSVO>();

    public String getPARAMETER_TYPE()
    {
	return PARAMETER_TYPE;
    }

    public void setPARAMETER_TYPE(String pARAMETERTYPE)
    {
	PARAMETER_TYPE = pARAMETERTYPE;
    }

    public String getPROCED_NAME()
    {
	return PROCED_NAME;
    }

    public void setPROCED_NAME(String pROCEDNAME)
    {
	PROCED_NAME = pROCEDNAME;
    }

    public BigDecimal getPROCED_ID()
    {
	return PROCED_ID;
    }

    public void setPROCED_ID(BigDecimal pROCEDID)
    {
	PROCED_ID = pROCEDID;
    }

    public String getUpdate1()
    {
	return update1;
    }

    public String getUpdate2()
    {
        return update2;
    }

    public void setUpdate2(String update2)
    {
        this.update2 = update2;
    }

    public void setUpdate1(String update1)
    {
	this.update1 = update1;
    }

    public String getPROC_DESC()
    {
	return PROC_DESC;
    }

    public void setPROC_DESC(String pROCDESC)
    {
	PROC_DESC = pROCDESC;
    }

    public BigDecimal getPROC_ID()
    {
	return PROC_ID;
    }

    public void setPROC_ID(BigDecimal pROCID)
    {
	PROC_ID = pROCID;
    }

    public String getPROC_NAME()
    {
	return PROC_NAME;
    }

    public void setPROC_NAME(String pROCNAME)
    {
	PROC_NAME = pROCNAME;
    }

    public BigDecimal getREPORT_ID()
    {
	return REPORT_ID;
    }

    public void setREPORT_ID(BigDecimal rEPORTID)
    {
	REPORT_ID = rEPORTID;
    }

    public BigDecimal getCode1()
    {
	return code1;
    }

    public void setCode1(BigDecimal code1)
    {
	this.code1 = code1;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public void setProcBO(ProceduresBO procBO)
    {
	this.procBO = procBO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getProcParamOutTypeCmb()
    {
	return procParamOutTypeCmb;
    }

    public void setProcParamOutTypeCmb(ArrayList<SYS_PARAM_LOV_TRANSVO> procParamOutTypeCmb)
    {
	this.procParamOutTypeCmb = procParamOutTypeCmb;
    }

    public Object getModel()
    {
	return repProcSC;
    }

    public String openProcList() throws Exception
    {
	return SUCCESS;
    }

    public String loadProcList() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO report = repSessionCO.getReportCO();
	    List<IRP_REP_PROCCO> lstProc = report.getProceduresList();
	    int procCount = lstProc.size();
	    if(lstProc.isEmpty() && report.getREPORT_ID() != null && !report.isFromUpDown() )
	    {
		repProcSC.setREP_ID(report.getREPORT_ID());
		copyproperties(repProcSC);
		lstProc = procBO.getProceduresList(repProcSC);
		procCount = procBO.getProceduresCount(repProcSC);
		report.setProceduresList(lstProc);
	    }
	    setRecords(procCount);
	    setGridModel(lstProc);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public String procNameLkp() throws Exception
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "PROC_NAME", "PROC_DESC" };
	    String[] colType = { "text", "text" };
	    String[] titles = { getText("proc.procName"), getText("proc.procDesc") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(getText("proc.ProcNameLkpTitle"));
	    grid.setRowNum("10");
	    grid.setUrl("/path/designer/proc_retSysProcList.action?_pageRef=" + get_pageRef());

	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(true);
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, repProcSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public String retSysProcList() throws Exception
    {
	try
	{
	    copyproperties(repProcSC);
	    setSearchFilter(repProcSC);

	    List<IRP_REP_PROCCO> procList = procBO.getSysProcList(repProcSC);
	    int procCount = procBO.getSysProcCount(repProcSC);

	    setRecords(procCount);
	    setGridModel(procList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";

    }

    public String saveProcedures() throws Exception
    {
	try
	{
	    if(updates != null && !updates.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates, IRP_REP_PROCCO.class, true);
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
		reportCO.getProceduresList().clear();
		reportCO.getProceduresList().addAll(gu.getLstAllRec());
		
		List<IRP_REP_PROCCO> lstProc = reportCO.getProceduresList();
		HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> procParamsMap = reportCO.getProcParamsMap();
		
		
		int paramsCnt;
		setUpdate2("");
		for(IRP_REP_PROCCO proc : lstProc)
		{
		    repProcSC.setREP_ID(BigDecimal.valueOf(-1));
		    repProcSC.setPROCEDURE_NAME(proc.getPROC_NAME());
		    repProcSC.setPROCEDURE_ID(BigDecimal.valueOf(-1));
		    paramsCnt = procBO.getSysProcParamsCount(repProcSC);
		    if(paramsCnt>0)
		    {
			ArrayList<IRP_REP_PROC_PARAMSCO> paramsList = procParamsMap.get(proc.getPROC_NAME());
			if(paramsList == null || paramsList.isEmpty())
			{
			    setUpdate2(getText("proc.emptyValErr"));
			    break;
			}
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public String openProcParamsDialog() throws Exception
    {
	return "procParams";
    }

    public String loadProcParamsList() throws Exception
    {
	try
	{
	    BigDecimal procId = getPROCED_ID();
	    String procName = getPROCED_NAME();

	    copyproperties(repProcSC);
	    repProcSC.setIsGrid(false);// added since the grid is without navigator and in DAO if nbRec is zero and nbRec is -1 then the grid will be empty
	    List<IRP_REP_PROC_PARAMSCO> lstParams;
	    int paramsCnt;

	    if(procName != null && !procName.equals(""))
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> procParamsMap = repSessionCO.getReportCO()
			.getProcParamsMap();
		ArrayList<IRP_REP_PROC_PARAMSCO> paramsList = procParamsMap.get(procName);

		if(paramsList == null)
		{
		    BigDecimal repId = repSessionCO.getReportCO().getREPORT_ID();
		    if(repId == null)
		    {
			repId = new BigDecimal(-1);
		    }
		    repProcSC.setREP_ID(repId);
		    repProcSC.setPROCEDURE_NAME(procName);
		    repProcSC.setPROCEDURE_ID(procId);
		    if(NumberUtil.isEmptyDecimal(procId))
		    {
			// retrieve data from system table
			paramsCnt = procBO.getSysProcParamsCount(repProcSC);
			lstParams = procBO.getSysProcParamsList(repProcSC);
		    }
		    else
		    {
			// retrieve COUNT from irp_rep_proc_params
			paramsCnt = procBO.getProceduresParamsCount(repProcSC);
			if(paramsCnt > 0)
			{
			    // retrieve data from IRP_REP_PROC_PARAMS
			    lstParams = procBO.getProceduresParamsList(repProcSC);
			}
			else
			{
			    // retrieve data from system table
			    paramsCnt = procBO.getSysProcParamsCount(repProcSC);
			    lstParams = procBO.getSysProcParamsList(repProcSC);
			}
		    }
		    setRecords(paramsCnt);
		    setGridModel(lstParams);
		}
		else
		{
		    setRecords(paramsList.size());
		    setGridModel(paramsList);
		}
		
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public String saveProcParams()
    {
	try
	{
	    if(update1 != null && !update1.equals(""))
		{
		    GridUpdates gu = getGridUpdates(update1, IRP_REP_PROC_PARAMSCO.class, true);
		    
		    ArrayList<IRP_REP_PROC_PARAMSCO>ar=gu.getLstAllRec();
		    int countError=0;
		    int countMessage=0;
		    boolean procVal = false;
		    IRP_REP_PROC_PARAMSCO paramCO;
    		    for(int i = 0; i < ar.size(); i++)
    		    {
    		       paramCO = ar.get(i);
    		      if(paramCO.getOUTPUT_PARAM_TYPE() != null)
    		      {
    			if(paramCO.getOUTPUT_PARAM_TYPE().equals("1"))
    			{
    			    countError = countError + 1;
    			}
    			else if(paramCO.getOUTPUT_PARAM_TYPE().equals("2"))
    			{
    			    countMessage = countMessage + 1;
    			}
    		     }
    		     if (paramCO.getQRY_PARAM_NAME().isEmpty())
    		     {
    			 procVal = true;
    			 break;
    		     }
    		      
    		   }
    		    if (procVal)
    		    {
    			setUpdates(getText("proc.emptyValErr"));
    		    }
    		    else if ((countError > 1) &&  (countMessage > 1))
		    {
			setUpdates(getText("proc.outCntErrMsg"));
		    }
		    else if (countError > 1)
		    {
			setUpdates(getText("proc.outCntErr"));
		    }
		    
		    else if (countMessage > 1)
		    {
			setUpdates(getText("proc.outCntMsg"));
		    }
		    else
		    {
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		    HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> procMap = repSessionCO.getReportCO().getProcParamsMap();
		    paramCO = ar.get(0);
		    procMap.put(paramCO.getPROC_NAME(), gu.getLstAllRec());
		    setUpdates("");
		    }
		}
	   
	}
	catch(Exception e)
	{
	    //log.error(e,"");
	    handleException(e, null,null);
	}
	 
	 return "grid";
	
    }

    public String qryParamNameLkp() throws Exception
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "ARGUMENT_NAME", "ARGUMENT_LABEL", "index" };
	    String[] colType = { "text", "text", "number" };
	    String[] titles = { getText("reporting.name"), getText("reporting.label"), getText("arguments.ARGUMENT_ID") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(getText("proc.QryParamNameLkpTitle"));
	    grid.setRowNum("10");
	    grid.setUrl("/path/designer/proc_retQryParamList.action");

	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();

	    int cols = name.length;
	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(name[i]);
		gridColumn.setIndex(name[i]);
		gridColumn.setColType(colType[i]);
		gridColumn.setTitle(titles[i]);
		gridColumn.setSearch(false);
		// adding column to the list
		if(name[i].equals("index"))
		{
		    gridColumn.setHidden(true);
		}
		lsGridColumn.add(gridColumn);

	    }
	    lookup(grid, lsGridColumn, null, repProcSC);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public String retQryParamList() throws Exception
    {
	try
	{
	    int isSyb=returnCommonLibBO().returnIsSybase();
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments = retQryArgsMap(get_pageRef());
	    String paramType = getPARAMETER_TYPE();
	    if((paramType.equalsIgnoreCase(RepConstantsCommon.charact))
		    || (paramType.equalsIgnoreCase(RepConstantsCommon.varchar)))
	    {
		paramType = ConstantsCommon.PARAM_TYPE_VARCHAR2;
	    }
	    copyproperties(repProcSC);
	    setSearchFilter(repProcSC);
	    int nbRec = repProcSC.getNbRec();
	    int recToSkip = repProcSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    IRP_REP_ARGUMENTSCO argCO;
	    if(arguments.size() < maxRec)
	    {
		maxRec = arguments.size();
	    }
	    ArrayList<IRP_REP_ARGUMENTSCO> lst = new ArrayList<IRP_REP_ARGUMENTSCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		argCO = (IRP_REP_ARGUMENTSCO) arguments.values().toArray()[i];
		if((argCO.getARGUMENT_TYPE().equalsIgnoreCase(paramType))
			|| (ConstantsCommon.PARAM_TYPE_DATE.equalsIgnoreCase(paramType) && isSyb == 0 && ConstantsCommon.datetime
				.equalsIgnoreCase(argCO.getARGUMENT_TYPE()))
			|| (isSyb == 1 && ConstantsCommon.datetime.equals(paramType) && ConstantsCommon.PARAM_TYPE_DATE
				.equalsIgnoreCase(argCO.getARGUMENT_TYPE())))
		{
		    if(argCO.getARGUMENT_ID() != null)
		    {
			argCO.setIndex(argCO.getARGUMENT_ID().longValue());
		    }
		    lst.add(argCO);
		}
	    }

	    setRecords(arguments.size());
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return "grid";
    }

    public LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> retQryArgsMap(String pageRef) throws Exception
    {
	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments = null;
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(pageRef);
	    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();

	    arguments = reportCO.getArgumentsList();
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return (LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>) arguments.clone();
    }

    public HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> retProcParamsMap(String pageRef) throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(pageRef);
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	return reportCO.getProcParamsMap();
    }

    public String deleteProcParams() throws Exception
    {
	HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> paramsMap = retProcParamsMap(get_pageRef());
	paramsMap.remove(getPROC_NAME());
	return "grid";
    }
    
    public String loadProcParamOutTypeCmb() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.PROC_PARAM_OUT_TYPE_LOV_TYPE);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    SYS_PARAM_LOV_TRANSVO emptyLine = new SYS_PARAM_LOV_TRANSVO();
	    emptyLine.setVALUE_CODE("");
	    emptyLine.setVALUE_DESC("");
	    procParamOutTypeCmb = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    procParamOutTypeCmb.add(emptyLine);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return "grid";
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
        this.commonLookupBO = commonLookupBO;
    }
}
