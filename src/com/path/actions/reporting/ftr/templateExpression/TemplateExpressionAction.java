package com.path.actions.reporting.ftr.templateExpression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.FTR_TMPLT_EXPRCO;

public class TemplateExpressionAction extends ReportingLookupBaseAction implements ServletRequestAware,
	ServletResponseAware
{

    private List<String> operators;
    private HttpServletRequest request;
    private String updates;
    private String tempCodeLineNbr;
    private List<FTR_TMPLT_EXPRCO> cteList;
    private COLMNTMPLTVO singleColumn;
    private String tempCodeWithLineNb;
    private FTR_TMPLT_EXPRCO voExpr = new FTR_TMPLT_EXPRCO();
    private CommonLookupBO commonLookupBO;
    private String update;
    

    public String getUpdate()
    {
        return update;
    }

    public void setUpdate(String update)
    {
        this.update = update;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public List<String> getOperators()
    {
	return operators;
    }

    public void setOperators(List<String> operators)
    {
	this.operators = operators;
    }

    public COLMNTMPLTVO getSingleColumn()
    {
	return singleColumn;
    }

    public void setSingleColumn(COLMNTMPLTVO singleColumn)
    {
	this.singleColumn = singleColumn;
    }

    public List<FTR_TMPLT_EXPRCO> getCteList()
    {
	return cteList;
    }

    public void setCteList(List<FTR_TMPLT_EXPRCO> cteList)
    {
	this.cteList = cteList;
    }

    public String getTempCodeLineNbr()
    {
	return tempCodeLineNbr;
    }

    public void setTempCodeLineNbr(String tempCodeLineNbr)
    {
	this.tempCodeLineNbr = tempCodeLineNbr;
    }

    public FTR_TMPLT_EXPRCO getVoExpr()
    {
	return voExpr;
    }

    public void setVoExpr(FTR_TMPLT_EXPRCO voExpr)
    {
	this.voExpr = voExpr;
    }

    COLMNTMPLTCO singleColumnDetail = new COLMNTMPLTCO();
    private String lineNbr = "0";

    public String getLineNbr()
    {
	return lineNbr;
    }

    public void setLineNbr(String lineNbr)
    {
	this.lineNbr = lineNbr;
    }

    public COLMNTMPLTCO getSingleColumnDetail()
    {
	return singleColumnDetail;
    }

    public void setSingleColumnDetail(COLMNTMPLTCO singleColumnDetail)
    {
	this.singleColumnDetail = singleColumnDetail;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    public String getTempCodeWithLineNb()
    {
	return tempCodeWithLineNb;
    }

    public void setTempCodeWithLineNb(String tempCodeWithLineNb)
    {
	this.tempCodeWithLineNb = tempCodeWithLineNb;
    }

    public String openExpression() throws JSONException // load the grid
    {
	return SUCCESS;
    }

    // Load the Grid "exprGrid_${_pageRef}"
    public String loadExprList() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());

	if((singleColumnDetail == null) || lineNbr.equals("0") || repSessionCO.getCurrentTemplate() == null)
	{
	    setGridModel(null);
	    return SUCCESS;
	}	
	COLMNTMPLTCO currentCO = findTemplateCODetInSession(new BigDecimal(lineNbr));
	
	// check if we are deleting a line
	// update will be containing the sublinenumber (LINE_NO)
	if(update != null)
	{
	    FTR_TMPLT_EXPRCO operatorCO;
	    // store the smallest line_no and set the operator to null in order
	    // to be saved null in db
	    int smallestLine = 0;
	    int currentLine;
	    int indexToSet = 0;
	    // remove the deleted line
	    boolean loop = true;
	    List<FTR_TMPLT_EXPRCO>  currExprLst=currentCO.getExpressions();
	    for(int i = 0; i < currExprLst.size(); i++)
	    {
		operatorCO = currExprLst.get(i);
		currentLine = operatorCO.getFtrTmpltExprVO().getLINE_NO().intValue();
		if(loop)
		{
		    smallestLine = operatorCO.getFtrTmpltExprVO().getLINE_NO().intValue();
		    currentLine = smallestLine;
		    loop = false;
		    indexToSet = i;
		}
		else if(currentLine < smallestLine)
		{
		    smallestLine = currentLine;
		    indexToSet = i;
		}
		if(new BigDecimal(update).equals(
			currExprLst.get(i).getFtrTmpltExprVO().getLINE_NO()))
		{
		    currExprLst.remove(i);
		}
	    }
	    if(!currExprLst.isEmpty())
	    {
		currExprLst.get(indexToSet).getFtrTmpltExprVO().setOPERATOR(null);
	    }
	}
	if(currentCO != null)
	{
	    // fill the order
	    FTR_TMPLT_EXPRCO exprCO;
	    List<FTR_TMPLT_EXPRCO>  currExprLst=currentCO.getExpressions();
	    for(int i = 0; i < currExprLst.size(); i++)
	    {
		exprCO = currExprLst.get(i);
		exprCO.setEXP_ORDER(new BigDecimal(i + 1));

		if(exprCO.getFtrTmpltExprVO().getEXP_TYPE().equals("L"))
		{
		    if(exprCO.getFtrTmpltExprVO().getEXP_VALUE() == null)
		    {
			exprCO.getFtrTmpltExprVO().setEXP_VALUE(exprCO.getFtrTmpltExprVO().getEXP_LINE());
		    }
		    exprCO.getFtrTmpltExprVO().setEXP_LINE(null);
		}
	    }
	    setRecords(currExprLst.size());
	    setGridModel(currExprLst);
	}
	return SUCCESS;
    }

    private COLMNTMPLTCO findTemplateCODetInSession(BigDecimal line_number)
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	if(repSessionCO != null && repSessionCO.getCurrentTemplate() != null)
	{
	    for(COLMNTMPLTCO co : (repSessionCO.getCurrentTemplate()).getColumnDetails())
	    {
		if(co.getNewLineNumber().equals(line_number))
		{
		    return co;
		}
	    }
	}
	return null;
    }

    public String expressionDependency() throws Exception
    {
	try
	{
	    String expType = request.getParameter("EXP_TYPE");
	    String val = request.getParameter("VALUE");
	    String exp = request.getParameter("EXP_LINE_NO");

	    if(RepConstantsCommon.EMPTY_STRING.equals(val))
	    {
		val = "0";
	    }
	    if(RepConstantsCommon.EMPTY_STRING.equals(exp))
	    {
		exp = "0";
	    }
	    if("L".equals(expType))// line
	    {
		voExpr.setEXP_LINE_NO_READONLY("false");
		voExpr.setVALUE_READONLY("true");

		voExpr.getFtrTmpltExprVO().setEXP_VALUE(BigDecimal.ZERO);
		voExpr.getFtrTmpltExprVO().setEXP_LINE(new BigDecimal(exp));
	    }
	    else
	    // value
	    {
		voExpr.setEXP_LINE_NO_READONLY("true");
		voExpr.setVALUE_READONLY("false");

		voExpr.getFtrTmpltExprVO().setEXP_LINE(BigDecimal.ZERO);
		voExpr.getFtrTmpltExprVO().setEXP_VALUE(new BigDecimal(val));
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String lineOrValueChange() throws Exception
    {
	try
	{
	    if("L".equals(request.getParameter("EXP_TYPE")))
	    {
		String exp = request.getParameter("EXP_LINE");
		if(RepConstantsCommon.EMPTY_STRING.equals(exp) || "0".equals(exp))
		{
		    setUpdates("false");
		    String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.INVALID_LINE_NB,
			    returnSessionObject().getLanguage());
		    addDependencyMsg(msg, null);
		}

		COLMNTMPLTCO tco = findTemplateCODetInSession(new BigDecimal(exp));

		if(tco == null)
		{
		    voExpr.getFtrTmpltExprVO().setEXP_LINE(null);
		    setUpdates("false");
		    String msg = returnCommonLibBO().returnTranslErrorMessage(RepConstantsCommon.INVALID_LINE_NB,
			    returnSessionObject().getLanguage());
		    addDependencyMsg(msg, null);
		}
		else
		{
		    voExpr.getFtrTmpltExprVO().setEXP_LINE(new BigDecimal(exp));
		    voExpr.getFtrTmpltExprVO().setEXP_VALUE(null);
		    setUpdates("true");
		}
	    }
	    else
	    {
		voExpr.getFtrTmpltExprVO().setEXP_LINE(null);
		voExpr.getFtrTmpltExprVO().setEXP_VALUE(new BigDecimal(request.getParameter("VALUE")));
		setUpdates("false");
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    
    /**
     * Add a new Expression
     * @return
     */
    public String addExpression()
    {
	try
	{
	COLMNTMPLTCO currentTemplateLine = findTemplateCODetInSession(new BigDecimal(lineNbr));
	SessionCO sessionCO = returnSessionObject();
	if(updates != null && !updates.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates, FTR_TMPLT_EXPRCO.class);
	    ArrayList lstAdd = gu.getLstAdd();
		  
	    ArrayList lstEdit = gu.getLstModify();
	    FTR_TMPLT_EXPRVO oldExprVO;

	    HashMap<BigDecimal, FTR_TMPLT_EXPRVO> oldExprMap = currentTemplateLine.getOldExprMap();

	    FTR_TMPLT_EXPRCO zExprCO ;
	    FTR_TMPLT_EXPRVO zExprVO ;
	    for(int i = 0; i < lstAdd.size(); i++)
	    {
			if (((FTR_TMPLT_EXPRCO) lstAdd.get(i)).getFtrTmpltExprVO().getEXP_VALUE().compareTo(new BigDecimal(0))<0)
			{
			    throw new Exception("Can Not add a New Expression Line. Expression Value is not filled"); 
			}
		}
		 
		for(int i = 0; i < lstAdd.size(); i++)
		{

		zExprCO=(FTR_TMPLT_EXPRCO) lstAdd.get(i);
		zExprVO=zExprCO.getFtrTmpltExprVO();
		zExprVO.setLINE_NO(new BigDecimal(getMaxID().intValue() + 1));
		zExprVO.setTMPLT_LINE_NO(new BigDecimal(lineNbr));
		zExprCO.setNewLineNumber(new BigDecimal(lineNbr));
		zExprVO.setCOMP_CODE(sessionCO.getCompanyCode());
		zExprVO.setCODE(new BigDecimal(tempCodeWithLineNb.split("~")[0]));
		String exprConcatKey = sessionCO.getCompanyCode().toString() + zExprVO.getCODE() + tempCodeWithLineNb.split("~")[1] +zExprVO.getLINE_NO();
		zExprCO.setConcatKey(new BigDecimal(exprConcatKey));
		
		if(currentTemplateLine.getExpressions() == null)
		{
		    currentTemplateLine.setExpressions(new ArrayList<FTR_TMPLT_EXPRCO>());
		}

		currentTemplateLine.getExpressions().add((FTR_TMPLT_EXPRCO) lstAdd.get(i));
	    }

	    for(int i = 0; i < lstEdit.size(); i++)
	    {
		for(FTR_TMPLT_EXPRCO expression : currentTemplateLine.getExpressions())
		{
		    if(expression.getFtrTmpltExprVO().getLINE_NO().equals(
			    ((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getLINE_NO()))
		    {
			// store oldValue
			if(oldExprMap.get(expression.getFtrTmpltExprVO().getLINE_NO()) == null)
			{
			    oldExprVO = new FTR_TMPLT_EXPRVO();
			    oldExprVO.setCOMP_CODE(expression.getFtrTmpltExprVO().getCOMP_CODE());
			    oldExprVO.setTMPLT_TYPE("C");
			    oldExprVO.setCODE(expression.getFtrTmpltExprVO().getCODE());
			    oldExprVO.setTMPLT_LINE_NO(expression.getFtrTmpltExprVO().getTMPLT_LINE_NO());
			    oldExprVO.setLINE_NO(expression.getFtrTmpltExprVO().getLINE_NO());
			    oldExprVO.setEXP_TYPE(expression.getFtrTmpltExprVO().getEXP_TYPE());
			    oldExprVO.setLEFT_OPERATOR(expression.getFtrTmpltExprVO().getLEFT_OPERATOR());
			    oldExprVO.setEXP_LINE(expression.getFtrTmpltExprVO().getEXP_LINE());
			    oldExprVO.setEXP_VALUE(expression.getFtrTmpltExprVO().getEXP_VALUE());
			    oldExprVO.setRIGHT_OPERATOR(expression.getFtrTmpltExprVO().getRIGHT_OPERATOR());
			    oldExprVO.setOPERATOR(expression.getFtrTmpltExprVO().getOPERATOR());
			    oldExprMap.put(expression.getFtrTmpltExprVO().getLINE_NO(), oldExprVO);
			}

			expression.getFtrTmpltExprVO().setCOMP_CODE(sessionCO.getCompanyCode());
			//expression.getFtrTmpltExprVO().setCODE(singleColumn.getCODE());
			expression.getFtrTmpltExprVO().setTMPLT_LINE_NO(new BigDecimal(lineNbr));
			expression.getFtrTmpltExprVO().setEXP_TYPE(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getEXP_TYPE());
			expression.getFtrTmpltExprVO().setLEFT_OPERATOR(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getLEFT_OPERATOR());
			expression.getFtrTmpltExprVO().setEXP_LINE(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getEXP_LINE());
			expression.getFtrTmpltExprVO().setEXP_VALUE(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getEXP_VALUE());
			expression.getFtrTmpltExprVO().setRIGHT_OPERATOR(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getRIGHT_OPERATOR());
			expression.getFtrTmpltExprVO().setOPERATOR(
				((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getFtrTmpltExprVO().getOPERATOR());
			expression.setExprOrGLByLine(((FTR_TMPLT_EXPRCO) lstEdit.get(i)).getExprOrGLByLine());
		    }
		}
	    }
	}
	}
	catch(Exception e)
	{
	    handleException(e,null,null);
	}
	return SUCCESS;
    }

    private BigDecimal getMaxID() throws Exception
    {
	COLMNTMPLTCO currentLine = findTemplateCODetInSession(new BigDecimal(lineNbr));
	if(currentLine == null)
	{
	    throw new Exception("Template line not selected");
	}
	cteList = currentLine.getExpressions();
	if(cteList == null)
	{
	    return BigDecimal.ZERO;
	}
	BigDecimal maxID = BigDecimal.ZERO;
	for(FTR_TMPLT_EXPRCO co : cteList)
	{
	    if(co.getFtrTmpltExprVO().getLINE_NO().compareTo(maxID) >= 0)
	    {
		maxID = co.getFtrTmpltExprVO().getLINE_NO();
	    }
	}
	return maxID;
    }

    // used to fill the operator combo inside the expression grid
    public String selectOperatorsUrl()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(5));
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    operators = (ArrayList) (commonLookupBO.getLookupList(sysParamLovVO));
	}
	catch(Exception e)
	{
	    // log.error(e,
	    // "============error lin selectOperatorsUrl ===========");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
}
