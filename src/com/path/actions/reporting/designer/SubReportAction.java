package com.path.actions.reporting.designer;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.IRP_SUB_REPORTSC;
import com.path.vo.reporting.SQL_OBJECT;

public class SubReportAction extends ReportingLookupBaseAction
{
    IRP_AD_HOC_REPORTSC sc = new IRP_AD_HOC_REPORTSC();
    private DesignerBO designerBO;
    private IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
    private IRP_SUB_REPORTSC subrepSC = new IRP_SUB_REPORTSC();
    private String updates;
    private BigDecimal subRepId;
    private String subRepName;
    private String subRepExp;
    private String updates1;
    String[] arrSubRepImg;
    private String subRepImgIds;
    private String notUseSubRep;
    private String deleteNotUseSubRep;
    private String outputLayout;
    private  ArrayList<SYS_PARAM_LOV_TRANSVO> drpDwnCmbList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private String hideColVal;
   

    public String getHideColVal()
    {
        return hideColVal;
    }

    public void setHideColVal(String hideColVal)
    {
        this.hideColVal = hideColVal;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDrpDwnCmbList()
    {
        return drpDwnCmbList;
    }

    public void setDrpDwnCmbList(ArrayList<SYS_PARAM_LOV_TRANSVO> drpDwnCmbList)
    {
        this.drpDwnCmbList = drpDwnCmbList;
    }

    public String getOutputLayout()
    {
        return outputLayout;
    }

    public void setOutputLayout(String outputLayout)
    {
        this.outputLayout = outputLayout;
    }

    public String getDeleteNotUseSubRep()
    {
	return deleteNotUseSubRep;
    }

    public void setDeleteNotUseSubRep(String deleteNotUseSubRep)
    {
	this.deleteNotUseSubRep = deleteNotUseSubRep;
    }

    public String getNotUseSubRep()
    {
	return notUseSubRep;
    }

    public void setNotUseSubRep(String notUseSubRep)
    {
	this.notUseSubRep = notUseSubRep;
    }

    public String getSubRepImgIds()
    {
	return subRepImgIds;
    }

    public void setSubRepImgIds(String subRepImgIds)
    {
	this.subRepImgIds = subRepImgIds;
    }

    public String[] getArrSubRepImg()
    {
	return arrSubRepImg;
    }

    public void setArrSubRepImg(String... arrSubRepImg)
    {
	this.arrSubRepImg = arrSubRepImg;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public String getSubRepExp()
    {
	return subRepExp;
    }

    public void setSubRepExp(String subRepExp)
    {
	this.subRepExp = subRepExp;
    }

    public String getSubRepName()
    {
	return subRepName;
    }

    public void setSubRepName(String subRepName)
    {
	this.subRepName = subRepName;
    }

    public BigDecimal getSubRepId()
    {
	return subRepId;
    }

    public void setSubRepId(BigDecimal subRepId)
    {
	this.subRepId = subRepId;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String openSubRepList() throws Exception
    {
	return SUCCESS;
    }


    public String openSubRepMainUD() throws Exception
    {
	return "subRepList";
    }
    
    public String loadDrpDwnCombo() throws Exception
    {
	try
	{
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO1 = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO1.setVALUE_CODE("0");
	    sysParamLovVO1.setVALUE_DESC(getText("hyperLink.static"));
	    if(ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM.equals(outputLayout))
	    {
		 SYS_PARAM_LOV_TRANSVO sysParamLovVO2 = new SYS_PARAM_LOV_TRANSVO();
		 sysParamLovVO2.setVALUE_CODE("1");
		 sysParamLovVO2.setVALUE_DESC(getText("hyperLink.field"));
		 drpDwnCmbList.add(sysParamLovVO2);
	    }
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO3 = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO3.setVALUE_CODE("2");
	    sysParamLovVO3.setVALUE_DESC(getText("designer.arguments"));
	    drpDwnCmbList.add(sysParamLovVO1);
	    drpDwnCmbList.add(sysParamLovVO3);
	}
	catch(Exception e)
	{
	    //log.error(e, "");
	    handleException(e, null,null);
	}
	return "grid";
    }

    public String loadSubrepListUD() throws Exception
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO reptCO = repSessionCO.getReportCO();

	    List<IRP_SUB_REPORTCO> subRepLst = reptCO.getSubreportsList();

	    copyproperties(subrepSC);
	    setRecords(reptCO.getSubreportsList().size());
	    setGridModel(subRepLst);
	}
	catch(Exception e)
	{
	    //log.error("Error in loadSubrepListUD() method in SubReportAction");
	    handleException(e, "Error in load loadSubrepList grid", "Error in load loadSubrepList grid");
	}
	return "grid";
    }

    public String saveSubRepMainUD() throws Exception
    {
	if(updates != null && !updates.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates, IRP_SUB_REPORTCO.class, true);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.getReportCO().setSubreportsList(gu.getLstAllRec());
	}
	return "grid";
    }

    public String openSubRepParamsDialog() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SQL_OBJECT sqlObj = repSessionCO.getReportCO().getQueriesList().get(0).getSqlObject();
	setOutputLayout(sqlObj.getOutputLayout());
	if(ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM.equals(outputLayout))
	{
	    hideColVal="false";
	}
	else
	{
	    hideColVal = "true";
	}
	return "subRepParams";
    }

    public String putInSubRepList() throws Exception
    {
	if(updates != null && !updates.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates, IRP_SUB_REPORTCO.class, true);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.getReportCO().setSubreportsList(gu.getLstAllRec());
	}
	return "grid";
    }

    public String putInSubRepParamMap() throws Exception
    {
	if(updates != null && !updates.equals(""))
	{
	    GridUpdates gu = getGridUpdates(updates, IRP_REP_ARGUMENTSCO.class, true);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, List<IRP_REP_ARGUMENTSCO>> hypParamsMap1 = new HashMap<String, List<IRP_REP_ARGUMENTSCO>>();
	    hypParamsMap1.put(getSubRepId() + "_" + getSubRepExp(), gu.getLstAllRec());
	    repSessionCO.getSubRepParamsMap1().put(getSubRepId() + "_" + getSubRepExp(), gu.getLstAllRec());

	}
	return "grid";
    }

    /**
     * this method check the sub report in ckEditor
     * 
     * @return grid
     */
    public String checkUsedSubRep()
    {
	getSubRepImgIds();
	String[] arrSubRepImg = getSubRepImgIds().split(",");

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	List<IRP_SUB_REPORTCO> subreportsList = repSessionCO.getReportCO().getSubreportsList();
	List<IRP_SUB_REPORTCO> subreportsListToSave = new ArrayList<IRP_SUB_REPORTCO>();
	for(int i = 0; i < subreportsList.size(); i++)
	{
	    String subRepGridIds = subreportsList.get(i).getIrpSubReportVO().getSUB_REPORT_EXPRESSION();
	    boolean repToSave = false;
	    for(int j = 1; j < arrSubRepImg.length; j++)
	    {
		if(arrSubRepImg[j].equals(subRepGridIds))
		{
		    repToSave = true;
		    break;
		}
	    }
	    if(repToSave)
	    {
		subreportsListToSave.add(subreportsList.get(i));
	    }
	}
	if(subreportsListToSave.size() == subreportsList.size())
	{
	    notUseSubRep = "no";
	}
	else
	{
	    notUseSubRep = "yes";
	}
	if(getDeleteNotUseSubRep() != null)
	{
	    repSessionCO.getReportCO().setSubreportsList(subreportsListToSave);
	}
	return "grid";
    }

    public String loadSubRepParams() throws Exception
    {

	BigDecimal subReportId = getSubRepId();
	String subRepExp = getSubRepExp();
	IRP_SUB_REPORTSC subRepSC = new IRP_SUB_REPORTSC();
	subRepSC.setSUB_REPORT_ID(subReportId);
	subRepSC.setSUB_REPORT_EXPRESSION(subRepExp);
	boolean existKey = false;
	try
	{
	    copyproperties(subRepSC);
	    List<IRP_REP_ARGUMENTSCO> lst;
	    int lstCnt;

	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());

	    HashMap<String, List<IRP_REP_ARGUMENTSCO>> argsSubRepMap1 = repSessionCO.getSubRepParamsMap1();
	    for(Entry<String, List<IRP_REP_ARGUMENTSCO>> entry : argsSubRepMap1.entrySet())
	    {
		String key = entry.getKey();
		if(key.equals(subReportId + "_" + subRepExp))
		{
		    existKey = true;
		}

	    }
	    if(existKey)// (repSessionCO.getSubRepParamsMap().size() !=0
	    // )//&&
	    // repSessionCO.getSubRepParamsMap().keySet().equals(subReportId+"_"+subRepExp))
	    {
		ArrayList<IRP_REP_ARGUMENTSCO> list = (ArrayList<IRP_REP_ARGUMENTSCO>) argsSubRepMap1.get(subReportId
			+ "_" + subRepExp);
		setRecords(list.size());
		setGridModel(list);
	    }

	    else
	    {

		lstCnt = designerBO.getSubRepParamsCount(subRepSC);
		lst = designerBO.getSubRepParamsList(subRepSC);
		if(!NumberUtil.isEmptyDecimal(repSessionCO.getReportCO().getREPORT_ID()))
		{
		    byte[] jrxml = repSessionCO.getReportCO().getJRXML_FILE();
		    ByteArrayInputStream inputXml = new ByteArrayInputStream(jrxml);
		    JasperDesign jasperDesign = JRXmlLoader.load(inputXml);
		    
		    // get sub report from header
		    JRBand headerBand = jasperDesign.getPageHeader();
		    {
			List<JRChild> jrChild = headerBand.getChildren();
			Object child;
			JRDesignSubreport zSubRep;
			JRDesignSubreportParameter subRepParam;
			// List
			for(int k = 0; k < jrChild.size(); k++)
			{
			    child = jrChild.get(k);
			    if(child instanceof JRDesignSubreport)
			    {
				
				zSubRep = (JRDesignSubreport) child;
				Map<String, JRSubreportParameter> hmSubRepParam = zSubRep.getParametersMap();
				Iterator it = hmSubRepParam.keySet().iterator();
				
				while(it.hasNext())
				{
				    subRepParam = (JRDesignSubreportParameter) hmSubRepParam.get(it.next());
				    String subreportParameterExpression = subRepParam.getExpression().getText();
				    String subreportParameter = subRepParam.getName();
				    String[] subRepNameSplitArr = subreportParameterExpression.split("$");
				    String subRepNameSplit = subRepNameSplitArr[0];
				    String type = subRepNameSplit.substring(1, 2);
				    String colName = subRepNameSplit.substring(3, subRepNameSplit.length() - 1);
				    if(RepConstantsCommon.TYPE_FIELD.equals(type))
				    {
					for(int j = 0; j < lstCnt; j++)
					{
					    if(lst.get(j).getARGUMENT_NAME().equals(subreportParameter))
					    {
						lst.get(j).setVALUE_COLUMN(colName);
						lst.get(j).setMAP_PARAM_TYPE("1");
					    }
					}
				    }
				    else if(RepConstantsCommon.TYPE_PARAMETER.equals(type))
				    {
					for(int j = 0; j < lstCnt; j++)
					{
					    if(lst.get(j).getARGUMENT_NAME().equals(subreportParameter))
					    {
						lst.get(j).setVALUE_ARGUMENT(colName);
						lst.get(j).setMAP_PARAM_TYPE("2");
					    }
					}
				    }
				}
				
			    }
			}
			
		    }
		    
		    // JasperDesign jasperDesign = JRXmlLoader.load(report);
		    // read subreports from detail
		    {
			JRSection detailsBand = jasperDesign.getDetailSection();
			JRElement[] elements = null;
			JRBand[] details = detailsBand.getBands();
			JRDesignSubreport subRep;
			elements = details[0].getElements();
			JRDesignSubreportParameter subRepParam;
			// List
			JRElement element = null;
			for(int i = 0; i < elements.length; i++)
			{
			    element = elements[i];
			    if(element instanceof JRDesignSubreport)
			    {
				subRep = (JRDesignSubreport) element;
				Map<String, JRSubreportParameter> hmSubRepParam = subRep.getParametersMap();
				Iterator it = hmSubRepParam.keySet().iterator();
				
				while(it.hasNext())
				{
				    subRepParam = (JRDesignSubreportParameter) hmSubRepParam.get(it.next());
				    String subreportParameterExpression = subRepParam.getExpression().getText();
				    String subreportParameter = subRepParam.getName();
				    String[] subRepNameSplitArr = subreportParameterExpression.split("$");
				    String subRepNameSplit = subRepNameSplitArr[0];
				    String type = subRepNameSplit.substring(1, 2);
				    String colName = subRepNameSplit.substring(3, subRepNameSplit.length() - 1);
				    if(RepConstantsCommon.TYPE_FIELD.equals(type))
				    {
					for(int j = 0; j < lstCnt; j++)
					{
					    if(lst.get(j).getARGUMENT_NAME().equals(subreportParameter))
					    {
						lst.get(j).setVALUE_COLUMN(colName);
						lst.get(j).setMAP_PARAM_TYPE("1");
					    }
					}
				    }
				    else if( RepConstantsCommon.TYPE_PARAMETER.equals(type))
				    {
					for(int j = 0; j < lstCnt; j++)
					{
					    if(lst.get(j).getARGUMENT_NAME().equals(subreportParameter))
					    {
						lst.get(j).setVALUE_ARGUMENT(colName);
						lst.get(j).setMAP_PARAM_TYPE("2");
					    }
					}
				    }
				}
			    }
			}
			
		    }
		    
		    // get sub report from footer
		    JRBand footerBand = jasperDesign.getPageFooter();
		    {
			List<JRChild> jrChild = footerBand.getChildren();
			Object child;
			JRDesignSubreport zSubRep;
			JRDesignSubreportParameter subRepParam;
			// List
			for(int k = 0; k < jrChild.size(); k++)
			{
			    child = jrChild.get(k);
			    if(child instanceof JRDesignSubreport)
			    {
				
				zSubRep = (JRDesignSubreport) child;
				Map<String, JRSubreportParameter> hmSubRepParam = zSubRep.getParametersMap();
				Iterator it = hmSubRepParam.keySet().iterator();
				
				while(it.hasNext())
				{
				    subRepParam = (JRDesignSubreportParameter) hmSubRepParam.get(it.next());
				    String subreportParameterExpression = subRepParam.getExpression().getText();
				    String subreportParameter = subRepParam.getName();
				    String[] subRepNameSplitArr = subreportParameterExpression.split("$");
				    String subRepNameSplit = subRepNameSplitArr[0];
				    String type = subRepNameSplit.substring(1, 2);
				    String colName = subRepNameSplit.substring(3, subRepNameSplit.length() - 1);
				    if( RepConstantsCommon.TYPE_FIELD.equals(type))
				    {
					for(int j = 0; j < lstCnt; j++)
					{
					    if(lst.get(j).getARGUMENT_NAME().equals(subreportParameter))
					    {
						lst.get(j).setVALUE_COLUMN(colName);
						lst.get(j).setMAP_PARAM_TYPE("1");
					    }
					}
				    }
				    else if( RepConstantsCommon.TYPE_PARAMETER.equals(type))
				    {
					for(int j = 0; j < lstCnt; j++)
					{
					    if(lst.get(j).getARGUMENT_NAME().equals(subreportParameter))
					    {
						lst.get(j).setVALUE_ARGUMENT(colName);
						lst.get(j).setMAP_PARAM_TYPE("2");
					    }
					}
				    }
				}
				
			    }
			}
			
		    }
		    
		}

		setRecords(lstCnt);
		setGridModel(lst);

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

	return "grid";
    }

    public String retColumnsByType() throws Exception
    {
	try
	{
	    fillLookup("/path/designer/subrep_fillFieldsGrid", getText("reporting.fildsList"));
	}
	catch(Exception e)
	{
	    log.error("Error in retColumnsByType");
	}

	return "grid";
    }

    public String fillLookup(String gridUrl, String gridCaption) throws JSONException
    {
	String columnType = getUpdates();

	if(  "1".equals(columnType))
	{
	    String[] name = { "FIELD_ALIAS" };
	    String[] colType = { "text" };
	    String[] titles = { getText("reporting.fieldName") };
	    fillLookup(gridUrl, gridCaption, name, colType, titles);
	}
	else if("2".equals(columnType))
	{
	    String[] name = { "ARGUMENT_NAME" };
	    String[] colType = { "text" };
	    String[] titles = { getText("reporting.fieldName") };
	    fillLookup(gridUrl, gridCaption, name, colType, titles);
	}

	return "grid";
    }

    public String fillFieldsGrid() throws Exception
    {
	String columnType = getUpdates();
	String argType = getUpdates1();
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if("1".equals(columnType))
	{
	    ArrayList<IRP_FIELDSCO> fieldTypeList = new ArrayList<IRP_FIELDSCO>();
	    ArrayList<IRP_FIELDSCO> fieldSameTypeList = new ArrayList<IRP_FIELDSCO>();
	    SQL_OBJECT sqlObj = repSessionCO.getReportCO().getQueriesList().get(0).getSqlObject();
	    LinkedHashMap feMap = sqlObj.getFields();
	    fieldTypeList = new ArrayList(feMap.values());

	    for(int i = 0; i < fieldTypeList.size(); i++)
	    {
		if(fieldTypeList.get(i).retJavaFieldType().equals(argType)
			&& !fieldTypeList.get(i).getFeName().equals("AUTOCOMMIT"))
		{
		    fieldSameTypeList.add(fieldTypeList.get(i));
		}
	    }

	    setRecords(fieldSameTypeList.size());
	    setGridModel(fieldSameTypeList);
	}
	else if("2".equals(columnType))
	{
	    ArrayList<IRP_REP_ARGUMENTSCO> argsList = new ArrayList<IRP_REP_ARGUMENTSCO>();
	    ArrayList argsTypeList = new ArrayList();
	    LinkedHashMap argsMap = repSessionCO.getReportCO().getArgumentsList();
	    // IRP_REP_ARGUMENTSCO argObj = new IRP_REP_ARGUMENTSCO();
	    // argObj = repSessionCO.getReportCO().getArgumentsList().get(0);
	    argsList = new ArrayList(argsMap.values());
	    for(int i = 0; i < argsList.size(); i++)
	    {
		if(argsList.get(i).getARGUMENT_TYPE().equals(argType))
		{
		    argsTypeList.add(argsList.get(i));
		}
	    }
	    setRecords(argsTypeList.size());
	    setGridModel(argsTypeList);
	}

	return "grid";
    }

    public String fillLookup(String gridUrl, String gridCaption, String[] name, String[] colType, String... titles)
	    throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);

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
		if(name[i].equals("value_date"))
		{
		    gridColumn.setFormatter("date");
		    gridColumn.setEditrules("{date:true}");
		}
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, subrepSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return "grid";
    }

    public IRP_AD_HOC_REPORTSC getReportSC()
    {
	return reportSC;
    }

    public void setReportSC(IRP_AD_HOC_REPORTSC reportSC)
    {
	this.reportSC = reportSC;
    }

    public IRP_SUB_REPORTSC getSubrepSC()
    {
	return subrepSC;
    }

    public void setSubrepSC(IRP_SUB_REPORTSC subrepSC)
    {
	this.subrepSC = subrepSC;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public Object getModel()
    {
	return subrepSC;
    }
}
