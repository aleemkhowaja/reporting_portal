package com.path.actions.reporting.designer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;

import com.path.actions.CommonReportingAction;
import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_SESSION_ATTRIBUTESVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_ARG_CONSTRAINTSCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;

public class QueryDesignerAction extends CommonReportingAction 
{

    /**
	 * 
	 */
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    private String havingIndex;
    private IRP_AD_HOC_QUERYCO queryCO = new IRP_AD_HOC_QUERYCO();
    private final QRY_GRIDSC qryGridSC = new QRY_GRIDSC();
    private QueryBO queryBO;
    private DesignerBO designerBO;
    private CommonLookupBO commonLookupBO;
    private CommonLibBO commonLibBO;
    private IRP_ENTITIESCO entCO;
    private IRP_FIELDSCO fieldCO;
    private String updates;
    private String customArgument;
    private CONDITION_OBJECT joinObject;
    private List<SYS_PARAM_LOV_TRANSVO> joinTypesList;
    private String argJoinType;
    private String argEntity1;
    private String argEntity2;
    private String joinMode;
    private String oper;
    private String parId;// parentId
    private String havingEntity;
    private String havingField1;
    private String havingField2;
    private CONDITION_OBJECT havingObject;
    private IRP_REP_ARGUMENTSCO argumentCO;
    private String argumentsMode;
    private String argField1;
    private String argField2;
    private Long PARENT_INDEX;

    private String sqbSyntax;
    private String openSqb;
    private final IRP_AD_HOC_QUERYSC querySC = new IRP_AD_HOC_QUERYSC();
    private BigDecimal queryIdLkp;
    private String queryNameLkp;
    private List<String> qryColumnsList = new ArrayList<String>();
    private String argumentType;
    private String sessionAttr;
    private String columnName;
    private String columnDesc;
    private String argQueryOptions;
    public String getArgQueryOptions()
    {
        return argQueryOptions;
    }

    public void setArgQueryOptions(String argQueryOptions)
    {
        this.argQueryOptions = argQueryOptions;
    }

    private Long paramIdx;
    private IRP_REP_ARGUMENTSCO parameter;
    private HashMap<Long, String> parametersList;
    private String updates1;
    private String update1;
    private String updatesLinkQryArgs;

    private String repArgsName;
    private String qryName;
    private String oldRepArgsName;
    private String forSrcOrDfltVal;
    private String isQryArg;
    private String srcOrDflt;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> defaultSourceType = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    
    private BigDecimal queryDfltValIdLkp;
    private String queryNameDftlValLkp;
    private String qryDfltValColName;
    private String defaultValueColDesc;
    private List<String> qryColumnsDfltValList = new ArrayList<String>();
    private ArrayList<SYS_PARAM_LOV_TRANSVO> caseSensList;
    private IRP_REP_ARG_CONSTRAINTSCO argConstrtCO;
    private String oldArgName;
    private List<String> sessionAttrList;
    private List<IRP_CONNECTIONSVO> connectionList;
    private BigDecimal queryNoDefSyntax;
    
    
    

    public BigDecimal getQueryNoDefSyntax()
    {
        return queryNoDefSyntax;
    }

    public void setQueryNoDefSyntax(BigDecimal queryNoDefSyntax)
    {
        this.queryNoDefSyntax = queryNoDefSyntax;
    }

    private ArrayList<SYS_PARAM_LOV_TRANSVO> argsDateValueList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();

    private ArrayList<SYS_PARAM_LOV_TRANSVO> qryOprionsList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();


    public void setQryOprionsList(ArrayList<SYS_PARAM_LOV_TRANSVO> qryOprionsList)
    {
        this.qryOprionsList = qryOprionsList;
    }

    public void setArgsDateValueList(ArrayList<SYS_PARAM_LOV_TRANSVO> argsDateValueList)
    {
        this.argsDateValueList = argsDateValueList;
    }

    public List<IRP_CONNECTIONSVO> getConnectionList()
    {
        return connectionList;
    }

    public void setConnectionList(List<IRP_CONNECTIONSVO> connectionList)
    {
        this.connectionList = connectionList;
    }

    /**
     * @param sessionAttrList the sessionAttrList to set
     */
    public void setSessionAttrList(List<String> sessionAttrList)
    {
        this.sessionAttrList = sessionAttrList;
    }
    
    /**
     * @return the sessionAttrList
     */
    public List<String> getSessionAttrList()
    {
        return sessionAttrList;
    }

    public String getOldArgName()
    {
        return oldArgName;
    }

    public void setOldArgName(String oldArgName)
    {
        this.oldArgName = oldArgName;
    }

    public IRP_REP_ARG_CONSTRAINTSCO getArgConstrtCO()
    {
        return argConstrtCO;
    }

    public void setArgConstrtCO(IRP_REP_ARG_CONSTRAINTSCO argConstrtCO)
    {
        this.argConstrtCO = argConstrtCO;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getCaseSensList()
    {
        return caseSensList;
    }

    public void setCaseSensList(ArrayList<SYS_PARAM_LOV_TRANSVO> caseSensList)
    {
        this.caseSensList = caseSensList;
    }

    public String getQryDfltValColName()
    {
        return qryDfltValColName;
    }

    public void setQryDfltValColName(String qryDfltValColName)
    {
        this.qryDfltValColName = qryDfltValColName;
    }

    public String getQueryNameDftlValLkp()
    {
        return queryNameDftlValLkp;
    }

    public void setQueryNameDftlValLkp(String queryNameDftlValLkp)
    {
        this.queryNameDftlValLkp = queryNameDftlValLkp;
    }

    public List<String> getQryColumnsDfltValList()
    {
        return qryColumnsDfltValList;
    }

    public void setQryColumnsDfltValList(List<String> qryColumnsDfltValList)
    {
        this.qryColumnsDfltValList = qryColumnsDfltValList;
    }

    public BigDecimal getQueryDfltValIdLkp()
    {
        return queryDfltValIdLkp;
    }

    public void setQueryDfltValIdLkp(BigDecimal queryDfltValIdLkp)
    {
        this.queryDfltValIdLkp = queryDfltValIdLkp;
    }

    public String getSrcOrDflt()
    {
        return srcOrDflt;
    }

    public void setSrcOrDflt(String srcOrDflt)
    {
        this.srcOrDflt = srcOrDflt;
    }

    public String getForSrcOrDfltVal()
    {
        return forSrcOrDfltVal;
    }

    public void setForSrcOrDfltVal(String forSrcOrDfltVal)
    {
        this.forSrcOrDfltVal = forSrcOrDfltVal;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDefaultSourceType()
    {
        return defaultSourceType;
    }

    public void setDefaultSourceType(ArrayList<SYS_PARAM_LOV_TRANSVO> defaultSourceType)
    {
        this.defaultSourceType = defaultSourceType;
    }

    public String getIsQryArg()
    {
	return isQryArg;
    }

    public void setIsQryArg(String isQryArg)
    {
	this.isQryArg = isQryArg;
    }

    public String getOldRepArgsName()
    {
	return oldRepArgsName;
    }

    public void setOldRepArgsName(String oldRepArgsName)
    {
	this.oldRepArgsName = oldRepArgsName;
    }

    public String getQryName()
    {
	return qryName;
    }

    public void setQryName(String qryName)
    {
	this.qryName = qryName;
    }

    public String getRepArgsName()
    {
	return repArgsName;
    }

    public void setRepArgsName(String repArgsName)
    {
	this.repArgsName = repArgsName;
    }

    public String getUpdatesLinkQryArgs()
    {
	return updatesLinkQryArgs;
    }

    public void setUpdatesLinkQryArgs(String updatesLinkQryArgs)
    {
	this.updatesLinkQryArgs = updatesLinkQryArgs;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
        this.commonLibBO = commonLibBO;
    }

    public String getUpdate1()
    {
	return update1;
    }

    public void setUpdate1(String update1)
    {
	this.update1 = update1;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public String openParameters()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setReportParams(true);
	
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(380));
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	try
	{
	    defaultSourceType = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    fillSessionArgsList();
	}
	catch(BaseException e)
	{
	   log.error(e.getMessage());
	}
	
	return "parameters";
    }

    public void clearRepParamsSession()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	repSessionCO.setReportParams(false);
    }

    public String getOpenSqb()
    {
	return openSqb;
    }

    public void setOpenSqb(String openSqb)
    {
	this.openSqb = openSqb;
    }

    public String getSqbSyntax()
    {
	return sqbSyntax;
    }

    public void setSqbSyntax(String sqbSyntax)
    {
	this.sqbSyntax = sqbSyntax;
    }

    public String getHavingIndex()
    {
	return havingIndex;
    }

    public void setHavingIndex(String havingIndex)
    {
	this.havingIndex = havingIndex;
    }

    public Long getPARENT_INDEX()
    {
	return PARENT_INDEX;
    }

    public void setPARENT_INDEX(Long pARENTINDEX)
    {
	PARENT_INDEX = pARENTINDEX;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public String getCustomArgument()
    {
	return customArgument;
    }

    public void setCustomArgument(String customArgument)
    {
	this.customArgument = customArgument;
    }

    public CONDITION_OBJECT getJoinObject()
    {
	return joinObject;
    }

    public void setJoinObject(CONDITION_OBJECT joinObject)
    {
	this.joinObject = joinObject;
    }

    public List<SYS_PARAM_LOV_TRANSVO> getJoinTypesList()
    {
	if(tab != null && tab.equals("joins"))
	{
	    joinTypesList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("Inner");
	    sysVO.setVALUE_DESC(getText("reporting.inner"));
	    joinTypesList.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("Left");
	    sysVO.setVALUE_DESC(getText("reporting.left"));
	    joinTypesList.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("Right");
	    sysVO.setVALUE_DESC(getText("reporting.right"));
	    joinTypesList.add(sysVO);
	}
	return joinTypesList;
    }

    public void setJoinTypesList(List<SYS_PARAM_LOV_TRANSVO> joinTypesList)
    {
	this.joinTypesList = joinTypesList;
    }

    public String getArgJoinType()
    {
	return argJoinType;
    }

    public void setArgJoinType(String argJoinType)
    {
	this.argJoinType = argJoinType;
    }

    public String getArgEntity1()
    {
	return argEntity1;
    }

    public void setArgEntity1(String argEntity1)
    {
	this.argEntity1 = argEntity1;
    }

    public String getArgEntity2()
    {
	return argEntity2;
    }

    public void setArgEntity2(String argEntity2)
    {
	this.argEntity2 = argEntity2;
    }

    public String getJoinMode()
    {
	return joinMode;
    }

    public void setJoinMode(String joinMode)
    {
	this.joinMode = joinMode;
    }

    public String getParId()
    {
	return parId;
    }

    public void setParId(String parId)
    {
	this.parId = parId;
    }

    public CONDITION_OBJECT getHavingObject()
    {
	return havingObject;
    }

    public void setHavingObject(CONDITION_OBJECT havingObject)
    {
	this.havingObject = havingObject;
    }

    public String getHavingField2()
    {
	return havingField2;
    }

    public void setHavingField2(String havingField2)
    {
	this.havingField2 = havingField2;
    }

    public String getHavingField1()
    {
	return havingField1;
    }

    public void setHavingField1(String havingField1)
    {
	this.havingField1 = havingField1;
    }

    public String getHavingEntity()
    {
	return havingEntity;
    }

    public void setHavingEntity(String havingEntity)
    {
	this.havingEntity = havingEntity;
    }

    public List<IRP_FIELDSCO> getAggFields()
    {
	if(tab != null && tab.equals("having"))
	{
	    HashMap<Long, IRP_FIELDSCO> aggregateMaps = retSqlObj().getAggregateFields();
	    List<IRP_FIELDSCO> aggregateList = new ArrayList<IRP_FIELDSCO>();
	    for(int i = 0; i < aggregateMaps.size(); i++)
	    {
		aggregateList.add((IRP_FIELDSCO) aggregateMaps.values().toArray()[i]);
	    }
	    return aggregateList;
	}
	return null;
    }

    public String getArgumentsMode()
    {
	return argumentsMode;
    }

    public void setArgumentsMode(String argumentsMode)
    {
	this.argumentsMode = argumentsMode;
    }

    public IRP_REP_ARGUMENTSCO getArgumentCO()
    {
	return argumentCO;
    }

    public void setArgumentCO(IRP_REP_ARGUMENTSCO argumentCO)
    {
	this.argumentCO = argumentCO;
    }

    public String getArgField2()
    {
	return argField2;
    }

    public void setArgField2(String argField2)
    {
	this.argField2 = argField2;
    }

    public String getArgField1()
    {
	return argField1;
    }

    public void setArgField1(String argField1)
    {
	this.argField1 = argField1;
    }

    public Object getModel()
    {
	return qryGridSC;
    }

    public String loadTabContent()
    {
	SQL_OBJECT sqlObj = retSqlObj();
	setHtmlPageRef(get_pageRef());
	
	if(tab != null && tab.equals("newquery"))
	{

	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    IRP_AD_HOC_REPORTCO report = repSessionCO.getReportCO();
	    if(report == null)
	    {
		queryCO.setQUERY_NAME(sqlObj.getQUERY_NAME());
	    }
	    else
	    {
		String name = sqlObj.getQUERY_NAME() == null ? report.getREPORT_NAME() + "_"
			+ (report.getQueriesList().size()) : sqlObj.getQUERY_NAME();
		queryCO.setQUERY_NAME(name);
	    }
	}
	if(!ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(sqlObj.getCONN_ID()))
	{
		queryCO.setCONN_ID(sqlObj.getCONN_ID());
	}
	if(tab != null && tab.equals("syntax") && sqlObj!=null && sqlObj.getSqbSyntax() != null)
	{
	    setSqbSyntax(sqlObj.getSqbSyntax().toString());
	    queryCO.setQUERY_NAME(sqlObj.getQUERY_NAME());
	}
	if(RepConstantsCommon.ARGUMENTS_TAB.equals(tab))
	{
	    fillSessionArgsList();
	}
	connectionList = setConnectionList();
	return tab;
    }
    
    /**
     * Method returning list of all available connections
     * @return
     */
    public List<IRP_CONNECTIONSVO> setConnectionList()
    {
	List<IRP_CONNECTIONSVO> conList = new ArrayList<IRP_CONNECTIONSVO>();
	conList.add(new IRP_CONNECTIONSVO());
	try
	{
	    conList.addAll(commonLookupBO.returnConnectionsList());
	}
	catch(BaseException e)
	{
	    handleException(e,null,null);
	}
	return conList;
    }

    public String getTab()
    {
	return tab;
    }

    public void setTab(String tab)
    {
	this.tab = tab;
    }

    public String loadDbEntGrid() throws JSONException
    {
	try
	{
	    copyproperties(qryGridSC);
	    List<IRP_ENTITIESCO> allEntList = queryBO.retDBEntities(qryGridSC);
	    // set the index of each obj since Sybase does not have a conversion
	    IRP_ENTITIESCO entCO;
	    for(int i = 0; i < allEntList.size(); i++)
	    {
		entCO = allEntList.get(i);
		entCO.setIndex(Long.valueOf(i));
	    }

	    int allEntCnt = queryBO.retDBEntitiesCount(qryGridSC);
	    setRecords(allEntCnt);
	    setGridModel(allEntList);
	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading entities from database.(loadDbEntGrid)");
	    handleException(e, "Error loading entities from database", "loadDbEntities.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadSelEntGrid() throws JSONException
    {
	try
	{
	    copyproperties(qryGridSC);
	    SQL_OBJECT sqlObj = retSqlObj();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    sqlObj = repSessionCO.getSqlObj();
	    LinkedHashMap<Long, IRP_ENTITIESCO> entMap = sqlObj.getEntities();

	    int nbRec = qryGridSC.getNbRec();
	    int recToSkip = qryGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(entMap.size() < maxRec)
	    {
		maxRec = entMap.size();
	    }
	    ArrayList<IRP_ENTITIESCO> lst = new ArrayList<IRP_ENTITIESCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((IRP_ENTITIESCO) entMap.values().toArray()[i]);
	    }

	    setRecords(entMap.size());
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading selected entities.(loadSelEntGrid)");
	    handleException(e, "Error loading selected entities", "loadSelEntities.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadExprEntGrid() throws JSONException
    {
	try
	{
	    copyproperties(qryGridSC);
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_ENTITIESCO> initEntMap = sqlObj.getEntities();
	    LinkedHashMap<Long, IRP_ENTITIESCO> entMap = (LinkedHashMap) initEntMap.clone();
	    // Add the Aggregate option
	    IRP_ENTITIESCO aggrEnt = new IRP_ENTITIESCO();
	    aggrEnt.setEntityAliasWithCount("AGGREGATES");
	    aggrEnt.setENTITY_DB_NAME("Aggregates");
	    aggrEnt.setENTITY_ALIAS("Aggregates");
	    aggrEnt.setIndex(generateId());
	    entMap.put(aggrEnt.getIndex(), aggrEnt);

	    int nbRec = qryGridSC.getNbRec();
	    int recToSkip = qryGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(entMap.size() < maxRec)
	    {
		maxRec = entMap.size();
	    }
	    ArrayList<IRP_ENTITIESCO> lst = new ArrayList<IRP_ENTITIESCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((IRP_ENTITIESCO) entMap.values().toArray()[i]);
	    }

	    setRecords(entMap.size());
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading expressions grid.(loadExprEntGrid)");
	    handleException(e, "Error loading expressions", "loadExpressions.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadDbFieldsGrid() throws JSONException
    {
	try
	{
	    String dbTblName = getUpdates();
	    Long parentId = getLocIndex();
	    if(dbTblName == null || dbTblName.equals("") || parentId == null || parentId.intValue() == -1)
	    {
		setRecords(0);
		setGridModel(new ArrayList<IRP_FIELDSCO>());
	    }
	    else
	    {
		copyproperties(qryGridSC);
		qryGridSC.setENTITY_DB_NAME(dbTblName);
		qryGridSC.setPARENT_ID(parentId==null?Long.valueOf("0"):parentId);
		List<IRP_FIELDSCO> allFieldsList = queryBO.retDBFields(qryGridSC);
		addObjsIndex(allFieldsList);
		int allFieldsCnt = queryBO.retDBFieldsCount(qryGridSC);
		setRecords(allFieldsCnt);
		setGridModel(allFieldsList);
	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading fields from database.(loadDbFieldsGrid)");
	    handleException(e, "Error loading fields from database", "loadDbFields.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadSelFieldsGrid() throws JSONException
    {
	try
	{
	    copyproperties(qryGridSC);
	    Long parentId = getLocIndex();
	    if(parentId == null)
	    {
		parentId = getPARENT_INDEX();
	    }
	    if(parentId == null || parentId.intValue() == 0 || parentId.intValue() == -1)
	    {
		setRecords(0);
		setGridModel(new ArrayList<IRP_FIELDSCO>());
	    }
	    else
	    {
		SQL_OBJECT sqlObj = retSqlObj();
		ArrayList<IRP_FIELDSCO> lst = new ArrayList<IRP_FIELDSCO>();
		if(parentId.toString().equals("1000001111222")) // fill the
		// aggregates
		// inside the
		// expression
		// section
		{
		    LinkedHashMap aggrMap = new LinkedHashMap<Long, IRP_FIELDSCO>();
		    IRP_FIELDSCO aggrObj;
		    IRP_FIELDSCO expAggrObj;
		    Iterator it = sqlObj.getAggregateFields().values().iterator();
		    while(it.hasNext())
		    {
			aggrObj = (IRP_FIELDSCO) it.next();
			expAggrObj = new IRP_FIELDSCO();
			expAggrObj.setIndex(aggrObj.getIndex());
			expAggrObj.setFIELD_ALIAS(aggrObj.getLabelAlias());
			expAggrObj.setFIELD_DATA_TYPE(aggrObj.getFIELD_DATA_TYPE());
			aggrMap.put(expAggrObj.getIndex(), expAggrObj);

		    }
		    setRecords(aggrMap.size());
		    setGridModel(new ArrayList(aggrMap.values()));
		}
		else
		{
		    IRP_ENTITIESCO selEntCO = sqlObj.getEntities().get(parentId);
		    LinkedHashMap<Long, IRP_FIELDSCO> fieldsMap;
		    if(selEntCO == null)
		    {
			fieldsMap = new LinkedHashMap<Long, IRP_FIELDSCO>();
			setRecords(fieldsMap.size());
			setGridModel(lst);
		    }
		    else
		    {
			if(getPARENT_INDEX() == null)
			{
			    fieldsMap = selEntCO.getSelectedFields();
			    int nbRec = qryGridSC.getNbRec();
			    int recToSkip = qryGridSC.getRecToskip();
			    int maxRec = nbRec + recToSkip;
			    if(fieldsMap.size() < maxRec)
			    {
				maxRec = fieldsMap.size();
			    }

			    for(int i = recToSkip; i < maxRec; i++)
			    {
				lst.add((IRP_FIELDSCO) fieldsMap.values().toArray()[i]);
			    }
			    setRecords(fieldsMap.size());
			    setGridModel(lst);
			}
			else // get all DB fields -aggregate section
			{
			    String entDBName = selEntCO.getENTITY_DB_NAME();
			    qryGridSC.setENTITY_DB_NAME(entDBName);
			    qryGridSC.setPARENT_ID(getPARENT_INDEX());
			    List<IRP_FIELDSCO> allFieldsList = queryBO.retDBFields(qryGridSC);
			    addObjsIndex(allFieldsList);
			    int allFieldsCnt = queryBO.retDBFieldsCount(qryGridSC);
			    setRecords(allFieldsCnt);
			    setGridModel(allFieldsList);
			}

		    }
		}

	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading selected fields. (loadSelFieldsGrid)");
	    handleException(e, "Error loading selected fields", "loadSelFields.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String addEntity() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("entCO");
	    entCO = (IRP_ENTITIESCO) JSONObject.toBean(jsonModel, IRP_ENTITIESCO.class);
	    Long entId = generateId();
	    entCO.setIndex(entId);
	    entCO.setType("1");
	    entCO.setSyntaxAlias(entCO.getENTITY_DB_NAME() + "_" + entCO.getSequenceNextVal());
	    SQL_OBJECT sqlObj = retSqlObj();

	    // check how many time this entity is selected
	    int count = 0;
	    Iterator it = sqlObj.getEntities().values().iterator();
	    while(it.hasNext())
	    {
		IRP_ENTITIESCO zEnt = (IRP_ENTITIESCO) it.next();
		if(zEnt.getENTITY_DB_NAME().equals(entCO.getENTITY_DB_NAME()))
		{
		    count++;
		}
	    }
	    if(count == 0)
	    {
		entCO.setEntityAliasWithCount(entCO.getENTITY_ALIAS());
	    }
	    else
	    {
		entCO.setEntityAliasWithCount(entCO.getENTITY_ALIAS() + "(" + count + ")");
	    }
	    sqlObj.getEntities().put(entId, entCO);

	}
	catch(Exception e)
	{
	    //log.error(e,"Error adding an entity. (addEntity)");
	    handleException(e, "Error adding an entity", "addEntity.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String addField() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("fieldCO");
	    fieldCO = (IRP_FIELDSCO) JSONObject.toBean(jsonModel, IRP_FIELDSCO.class);
	    IRP_ENTITIESCO ent = sqlObj.getEntities().get(fieldCO.getPARENT_INDEX());
	    Long fieldId = generateId();
	    fieldCO.setIndex(fieldId);
	    fieldCO.setType("1");
	    fieldCO.setLabelAlias(fieldCO.getFIELD_DB_NAME() + "_" + fieldCO.getSequenceNextVal());
	    fieldCO.setFieldSyntax(ent.getSyntaxAlias() + "." + fieldCO.getFIELD_DB_NAME());// entityAlias.fieldDbName
	    // for grid tree
	    fieldCO.setParent(fieldCO.getPARENT_INDEX().toString());
	    fieldCO.setIsLeaf("true");
	    fieldCO.setLevel("3");
	    fieldCO.setFeName(fieldCO.getENTITY_ALIAS() + "." + fieldCO.getFIELD_ALIAS());
	    fieldCO.setEntityAliasWithCount(ent.getEntityAliasWithCount());

	    // check if the selected fields was not selected before than add it
	    boolean found = false;
	    Iterator it = ent.getSelectedFields().values().iterator();
	    while(it.hasNext())
	    {
		IRP_FIELDSCO feCO = (IRP_FIELDSCO) it.next();
		if(feCO.getFIELD_DB_NAME().endsWith(fieldCO.getFIELD_DB_NAME()))
		{
		    found = true;
		    break;
		}

	    }
	    if(!found)
	    {
		// add the selected field to the list of entity's field in order
		// to use it when displaying the selected fields of each entity
		ent.getSelectedFields().put(fieldId, fieldCO);
		// add the selected field to the list of fields ,in order to use
		// it when generating the sql string
		sqlObj.getFields().put(fieldId, fieldCO);
	    }

	}
	catch(Exception e)
	{
	    //log.error(e,"Error adding a field. (addField)");
	    handleException(e, "Error adding a field", "addField.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String delEntity() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("entCO");
	    entCO = (IRP_ENTITIESCO) JSONObject.toBean(jsonModel, IRP_ENTITIESCO.class);
	    Long entId = entCO.getIndex();
	    SQL_OBJECT sqlObj = retSqlObj();

	    // remove all related fields from the following maps :fields and
	    // displayedFields(ent.SelectedFields will be removed when del. the
	    // ent)
	    IRP_ENTITIESCO irpEntCO = sqlObj.getEntities().get(entId);
	    HashMap selFe = irpEntCO.getSelectedFields();

	    // put all the sel field ids that are related to the entity inside
	    // an array in order to use it when deleting from the the Fields Map
	    Iterator it = selFe.keySet().iterator();
	    ArrayList<Long> feIdsArr = new ArrayList<Long>();
	    while(it.hasNext())
	    {
		feIdsArr.add((Long) it.next());
	    }
	    sqlObj.getEntities().remove(entId);

	    // delete from fields
	    HashMap feMap = sqlObj.getFields();
	    for(int i = 0; i < feIdsArr.size(); i++)
	    {
		feMap.remove(feIdsArr.get(i));
	    }

	    // delete from displayedFields
	    HashMap displFe = sqlObj.getDisplayFields();
	    for(int i = 0; i < feIdsArr.size(); i++)
	    {
		displFe.remove(feIdsArr.get(i));
	    }

	}
	catch(Exception e)
	{
	    //log.error(e,"Error removing an entity.(delEntity)");
	    handleException(e, "Error removing an entity", "removeEntity.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String delField() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("fieldCO");
	    fieldCO = (IRP_FIELDSCO) JSONObject.toBean(jsonModel, IRP_FIELDSCO.class);
	    Long fieldId = fieldCO.getIndex();
	    SQL_OBJECT sqlObj = retSqlObj();
	    sqlObj.getFields().remove(fieldId);
	    IRP_ENTITIESCO irpEntCO = sqlObj.getEntities().get(fieldCO.getPARENT_INDEX());
	    irpEntCO.getSelectedFields().remove(fieldId);
	}
	catch(Exception e)
	{
	   // log.error(e,"Error removing a field.(delField)");
	    handleException(e, "Error removing a field", "removeField.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String saveQryName() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    String qryName = getUpdates();
	    sqlObj.setQUERY_NAME(qryName);

	    // return if we already select a field
	    if(sqlObj.getFields().size() > 0)
	    {
		setUpdates("true");
	    }
	    else
	    {
		setUpdates("false");
	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"Error setting the query name in sql object.(saveQryName)");
	    handleException(e, "Error setting the query name in sql object", "setQueryName.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String storeMenuQryName() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    String qryName = getUpdates();
	    repSessionCO.setMenuQryName(qryName);
	}
	catch(Exception e)
	{
	    //log.error(e,"Error setting the query name in session.(storeMenuQryName)");
	    handleException(e, "Error setting the query name in session", "setQueryNameInSession.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadAggrGrid() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_FIELDSCO> aggrMap = sqlObj.getAggregateFields();
	    String oper = getOper();
	    String aggrId = getId();
	    if(aggrId != null && aggrId.indexOf("new_") == -1 && oper != null && oper.equals("del"))
	    {
		aggrMap.remove(Long.valueOf(aggrId));
	    }

	    setRecords(aggrMap.size());
	    setGridModel(new ArrayList(aggrMap.values()));

	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading aggregates grid. (loadAggrGrid)");
	    handleException(e, "Error loading aggregates", "loadAggr.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String validateAggr() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_FIELDSCO> aggrMap = sqlObj.getAggregateFields();
	    IRP_ENTITIESCO entCO;
	    if(updates != null && !updates.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates, IRP_FIELDSCO.class);
		ArrayList lstAdd = gu.getLstAdd();
		ArrayList lstMod = gu.getLstModify();
		IRP_FIELDSCO feCO;
		IRP_FIELDSCO oldFeCO;

		if(!lstAdd.isEmpty() || !lstMod.isEmpty())
		{
		    for(int i = 0; i < lstAdd.size(); i++)
		    {
			feCO = (IRP_FIELDSCO) lstAdd.get(i);
			entCO = sqlObj.getEntities().get(feCO.getPARENT_INDEX());
			if(!feCO.getAggregate().equals("") && !feCO.getENTITY_ALIAS().equals("")
				&& !feCO.getFIELD_ALIAS().equals(""))
			{
			    feCO.setIndex(generateId());

			    // for grid tree
			    feCO.setParent("3");
			    feCO.setLevel("2");
			    feCO.setIsLeaf("true");
			    // feCO.setLabelAlias("aggr_"+feCO.getSequenceNextVal());
			    feCO.setLabelAlias(feCO.getFeName());
			    feCO.setFieldSyntax(feCO.getAggregate() + "(" + entCO.getSyntaxAlias() + "."
				    + feCO.getFIELD_DB_NAME() + ")"); //
			    aggrMap.put(feCO.getIndex(), feCO);
			}
		    }
		    for(int j = 0; j < lstMod.size(); j++)
		    {
			feCO = (IRP_FIELDSCO) lstMod.get(j);
			entCO = sqlObj.getEntities().get(feCO.getPARENT_INDEX());
			oldFeCO = sqlObj.getAggregateFields().get(feCO.getIndex());

			feCO.setParent("3");
			feCO.setLevel("2");
			feCO.setIsLeaf("true");
			feCO.setLabelAlias(oldFeCO.getLabelAlias());
			feCO.setFieldSyntax(feCO.getAggregate() + "(" + entCO.getSyntaxAlias() + "."
				+ feCO.getFIELD_DB_NAME() + ")");

			aggrMap.put(feCO.getIndex(), feCO);

		    }
		}
	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"Error validating aggregates. (validateAggr)");
	    handleException(e, "Error validating aggregates", "validateAggr.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadExprGrid() throws JSONException
    {
	try
	{
	    copyproperties(qryGridSC);
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_FIELDSCO> exprMap = sqlObj.getExpressionFields();

	    int nbRec = qryGridSC.getNbRec();
	    int recToSkip = qryGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(exprMap.size() < maxRec)
	    {
		maxRec = exprMap.size();
	    }
	    ArrayList<IRP_FIELDSCO> lst = new ArrayList<IRP_FIELDSCO>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((IRP_FIELDSCO) exprMap.values().toArray()[i]);
	    }

	    setRecords(exprMap.size());
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	    //log.error(e,"Error loading expressions grid. (loadExprGrid)");
	    handleException(e, "Error loading expressions", "loadExpressions.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String validateExpr() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    String exprType = getUpdates();
	    fieldCO.setFIELD_DATA_TYPE(exprType);

	    LinkedHashMap<Long, IRP_FIELDSCO> exprMap = sqlObj.getExpressionFields();
	    if(fieldCO.getIndex() == null)// new expression
	    {
		Long exprId = generateId();
		fieldCO.setIndex(exprId);
		fieldCO.setType("1");

		// for grid tree
		fieldCO.setParent("2");
		fieldCO.setLevel("2");
		fieldCO.setIsLeaf("true");
		fieldCO.setFeName(fieldCO.getLabelAlias());

		String parsedExpr = queryBO.parseExpression(fieldCO.getHtml(), sqlObj);
		fieldCO.setFieldSyntax(parsedExpr);

		exprMap.put(exprId, fieldCO);
	    }
	    else
	    // update expression
	    {
		fieldCO.setType("1");
		String parsedExpr = queryBO.parseExpression(fieldCO.getHtml(), sqlObj);
		fieldCO.setFieldSyntax(parsedExpr);

		// for grid tree
		fieldCO.setParent("2");
		fieldCO.setLevel("2");
		fieldCO.setIsLeaf("true");
		fieldCO.setFeName(fieldCO.getLabelAlias());

		exprMap.put(fieldCO.getIndex(), fieldCO);
	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"Error validating expression. (validateExpr)");
	    handleException(e, "Error validating expression", "validateExp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String openExpr() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("fieldCO");
	    fieldCO = (IRP_FIELDSCO) JSONObject.toBean(jsonModel, IRP_FIELDSCO.class);
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_FIELDSCO> exprMap = sqlObj.getExpressionFields();
	    IRP_FIELDSCO exprCO = exprMap.get(fieldCO.getIndex());
	    fieldCO.setHtml(exprCO.getHtml());

	}
	catch(Exception e)
	{
	    //log.error(e,"Error opening expression. (openExpr)");
	    handleException(e, "Error opening expression", "openExpr.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String deleteExpr() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_FIELDSCO> exprMap = sqlObj.getExpressionFields();
	    Long exprId = getLocIndex();
	    exprMap.remove(exprId);
	}
	catch(Exception e)
	{
	    //log.error(e,"Error deleting expression.(deleteExpr)");
	    handleException(e, "Error deleting expression", "deleteExpr.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String checkExpValidity() throws JSONException
    {
	try
	{
	    String exprHtml = getUpdates();
	    String exprValidity = queryBO.checkExpressionValidity(exprHtml);
	    setUpdates(exprValidity);
	}
	catch(Exception x)
	{
	    log.error("Error checking expression validity");
	    // handleException(x,
	    // "Error validating expression","validateExp.exceptionMsg._key");
	    setUpdates(x.getMessage());
	}
	return SUCCESS;
    }

    public String checkUsage() throws JSONException
    {
	try
	{
	    Long id = getLocIndex();
	    String isFe = getUpdates();
	    SQL_OBJECT sqlObj = retSqlObj();
	    // check in Aggr
	    Iterator it = sqlObj.getAggregateFields().values().iterator();
	    IRP_FIELDSCO feCO = null;
	    IRP_FIELDSCO mainFeCO = sqlObj.getFields().get(id);
	    StringBuffer retVal = new StringBuffer();
	    while(it.hasNext())
	    {
		feCO = (IRP_FIELDSCO) it.next();

		// feId is not saved in aggr since we get all the fe from the
		// DB,so we have to check usage following the ent_db_name &
		// fe_DB_name
		if(isFe != null && feCO.getFIELD_DB_NAME().equals(mainFeCO.getFIELD_DB_NAME())) // check
		// field
		// usage
		{
		    retVal.append(getText("reporting.aggregates"));
		    break;
		}
		else
		// check entity usage
		{
		    if(feCO.getPARENT_INDEX().equals(id))
		    {
			retVal.append(getText("reporting.aggregates"));
			break;
		    }
		}
	    }
	    // check in Expessions
	    it = sqlObj.getExpressionFields().values().iterator();
	    while(it.hasNext())
	    {
		feCO = (IRP_FIELDSCO) it.next();
		if(feCO.getHtml().indexOf(id.toString()) != -1)
		{
		    if(RepConstantsCommon.EMPTY_STRING.equals(retVal.toString()))
		    {
			retVal.append(getText("designer.expressions"));			
		    }
		    else
		    {
			retVal.append(" , ").append(getText("designer.expressions"));
		    }
		    break;
		}
	    }

	    setUpdates(retVal.toString());
	}
	catch(Exception e)
	{
	   // log.error(e,"Error in checking the usage of aggregates.(checkUsage)");
	    handleException(e, "Error checking the usage of aggregates", "checkAggrUsage.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadAllFieldsGrid() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();

	    String qryType = ConstantsCommon.SQB_QRY_TYPE;
	    if(sqlObj != null && sqlObj.getEntities().size() > 0)
	    {
		qryType = ConstantsCommon.QBE_QRY_TYPE;
	    }

	    copyproperties(qryGridSC);
	    String nodeId = getNodeid();
	    ArrayList<IRP_FIELDSCO> gridTreeList = new ArrayList<IRP_FIELDSCO>();

	    IRP_FIELDSCO rootCO;
	    if(nodeId == null || nodeId.isEmpty())
	    {
		nodeId = "0";
		rootCO = new IRP_FIELDSCO();
		rootCO.setIndex(Long.valueOf(0));
		rootCO.setFeName(getText("qry.allFields"));
		rootCO.setParent("-1");
		rootCO.setLevel("0");
		rootCO.setIsLeaf("false");
		gridTreeList.add(rootCO);
	    }
	    else if("0".equals(nodeId))
	    {
		rootCO = new IRP_FIELDSCO();
		rootCO.setIndex(Long.valueOf(1));
		rootCO.setFeName(getText("query.fields"));
		rootCO.setParent(nodeId);
		rootCO.setLevel("1");
		rootCO.setIsLeaf("false");
		gridTreeList.add(rootCO);

		if((ConstantsCommon.QBE_QRY_TYPE).equals(qryType))
		{
		    rootCO = new IRP_FIELDSCO();
		    rootCO.setIndex(Long.valueOf(2));
		    rootCO.setFeName(getText("expr.ExpressionList"));
		    rootCO.setParent(nodeId);
		    rootCO.setLevel("1");
		    rootCO.setIsLeaf("false");
		    gridTreeList.add(rootCO);

		    rootCO = new IRP_FIELDSCO();
		    rootCO.setIndex(Long.valueOf(3));
		    rootCO.setFeName(getText("reporting.aggregates"));
		    rootCO.setParent(nodeId);
		    rootCO.setLevel("1");
		    rootCO.setIsLeaf("false");
		    gridTreeList.add(rootCO);
		}
	    }
	    else if("1".equals(nodeId))// display entities
	    {
		if(ConstantsCommon.QBE_QRY_TYPE.equals(qryType))
		{
		    LinkedHashMap entMap = sqlObj.getEntities();
		    Iterator it = entMap.values().iterator();
		    IRP_ENTITIESCO entCO;
		    while(it.hasNext())
		    {
			entCO = (IRP_ENTITIESCO) it.next();
			rootCO = new IRP_FIELDSCO();
			rootCO.setIndex(entCO.getIndex());
			rootCO.setFeName(entCO.getEntityAliasWithCount());
			rootCO.setParent("1");
			rootCO.setLevel("2");
			rootCO.setIsLeaf("false");
			gridTreeList.add(rootCO);

		    }
		}
		else
		{
		    LinkedHashMap feMap = sqlObj.getFields();
		    gridTreeList = new ArrayList(feMap.values());
		}
		// LinkedHashMap<Long,IRP_FIELDSCO> feMap=sqlObj.getFields();
		// gridTreeList=new ArrayList(feMap.values());
	    }
	    else if("2".equals(nodeId))// Expressions
	    {
		LinkedHashMap<Long, IRP_FIELDSCO> exprMap = sqlObj.getExpressionFields();
		gridTreeList = new ArrayList(exprMap.values());
	    }
	    else if( "3".equals(nodeId))// Aggregates
	    {
		LinkedHashMap<Long, IRP_FIELDSCO> aggrMap = sqlObj.getAggregateFields();
		gridTreeList = new ArrayList(aggrMap.values());
	    }
	    else
	    // show selected fields of the entity
	    {
		LinkedHashMap feMap = sqlObj.getEntities().get(Long.valueOf(nodeId)).getSelectedFields();
		gridTreeList = new ArrayList(feMap.values());
	    }

	    // set the collection into gridModel attribute defined at JSP grid
	    // tag
	    setGridModel(gridTreeList);

	}
	catch(Exception e)
	{
	    //log.error(e,"Error in loading all fields");
	    handleException(e, "Error loading all fields", "loadAllFields.exceptionMsg._key");

	}
	return SUCCESS;
    }

    public String addDisplayField() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("fieldCO");
	    fieldCO = (IRP_FIELDSCO) JSONObject.toBean(jsonModel, IRP_FIELDSCO.class);
	    if(fieldCO.getIsLeaf() != null && fieldCO.getIsLeaf().equals("true"))
	    {
		sqlObj.getDisplayFields().put(fieldCO.getIndex(), fieldCO);
	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"Error in adding a field to be displayed on the report.(addDisplayField )");
	    handleException(e, "Error adding a field to be displayed on the report", "addRepField.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String loadDisplayFieldsGrid() throws JSONException
    {
	try
	{
	    copyproperties(qryGridSC);
	    ArrayList<IRP_FIELDSCO> lst = new ArrayList<IRP_FIELDSCO>();
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, IRP_FIELDSCO> fieldsMap;

	    fieldsMap = sqlObj.getDisplayFields();
	    int nbRec = qryGridSC.getNbRec();
	    int recToSkip = qryGridSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(fieldsMap.size() < maxRec)
	    {
		maxRec = fieldsMap.size();
	    }

	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((IRP_FIELDSCO) fieldsMap.values().toArray()[i]);
	    }
	    setRecords(fieldsMap.size());
	    setGridModel(lst);
	}
	catch(Exception e)
	{
	   // log.error(e,"Error loading the fields to be displayed on the report.(loadDisplayFieldsGrid)");
	    handleException(e, "Error loading the fields to be displayed on the report",
		    "loadRepFields.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String delDisplField() throws JSONException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    sqlObj.getDisplayFields().remove(getLocIndex());

	}
	catch(Exception e)
	{
	    //log.error(e,"Error removing field from being displayed on the report.(delDisplField)");
	    handleException(e, "Error removing the field from being displayed on the report",
		    "removeRepField.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String checkQryName() throws JSONException
    {
	try
	{
	    String qryName = getUpdates();
	    setUpdates("false");
	    String nameExist = queryBO.checkQryName(qryName);
	    if(nameExist != null)
	    {
		setUpdates("true");
	    }
	}
	catch(Exception e)
	{
	   // log.error(e,"Error checking the query name.(checkQryName)");
	    handleException(e, "Error checking the query name", "checkQueryName.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String openSelEntDialog() throws JSONException
    {
	return "successEntities";
    }

    public String openSelFieldsDialog() throws JSONException
    {
	return "successFields";
    }

    public IRP_AD_HOC_QUERYCO getQueryCO()
    {
	return queryCO;
    }

    public void setQueryCO(IRP_AD_HOC_QUERYCO queryCO)
    {
	this.queryCO = queryCO;
    }

    public IRP_ENTITIESCO getEntCO()
    {
	return entCO;
    }

    public void setEntCO(IRP_ENTITIESCO entCO)
    {
	this.entCO = entCO;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public IRP_FIELDSCO getFieldCO()
    {
	return fieldCO;
    }

    public void setFieldCO(IRP_FIELDSCO fieldCO)
    {
	this.fieldCO = fieldCO;
    }

    public String getOper()
    {
	return oper;
    }

    public void setOper(String oper)
    {
	this.oper = oper;
    }

    public String argumenGridUrl()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());// from
	// rep
	// arg
	// RD00R
	SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	if(sqlObj != null)
	{
	    isQryArg = sqlObj.getIsQryArg();
	}
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();

	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();

	if(repSessionCO.isReportParams() || (repSessionCO.getSqlObj() != null
		&& ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM.equals(repSessionCO.getSqlObj().getOutputLayout())))
	{
	    arguments = reportCO.getArgumentsList();
	}
	else if(sqlObj!=null)
	{
	    arguments = sqlObj.getArgumentsList();
	}

	copyproperties(qryGridSC);
	int nbRec = qryGridSC.getNbRec();
	int recToSkip = qryGridSC.getRecToskip();
	int maxRec = nbRec + recToSkip;
	if(arguments.size() < maxRec)
	{
	    maxRec = arguments.size();
	}
	ArrayList<IRP_REP_ARGUMENTSCO> lst = new ArrayList<IRP_REP_ARGUMENTSCO>();
	if(arguments.values().size() > 0)
	{
	    List<IRP_REP_ARGUMENTSCO> args = new ArrayList<IRP_REP_ARGUMENTSCO>(arguments.values());
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add(args.get(i));
	    }
	}
	setRecords(arguments.size());
	setGridModel(lst);

	return SUCCESS;
    }

    public String openLinkQueryMainDialog()
    {
	try
	{
	String forSrcOrDfltVal = getForSrcOrDfltVal();
	if( "2".equals(forSrcOrDfltVal))
	{
	    queryIdLkp =getQueryDfltValIdLkp();
	}
	if(!NumberUtil.isEmptyDecimal(queryIdLkp))
	{
	    IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(queryIdLkp, true);
	    if(query.getQUERY_OBJECT() == null)
	    {
		BOException e = new BOException("");
		e.setMsgIdent(ConstantsCommon.QUERY_NO_DEFINED_SYNTAX);
		throw e;
	    }
	    this.queryNameLkp = query.getQUERY_NAME();
	    // HashMap<String,String> hm = queryBO.returnQryColumns(qrySynt);
	    // qryColumnsList.add("");

	    HashMap<String, String> hm = returnFeAliasAndTypeMap(query);

	    for(Entry<String, String> entry : hm.entrySet())
	    {
		qryColumnsList.add(entry.getKey());
	    }
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    if("2".equals(forSrcOrDfltVal))
	    {
		repSessionCO.setQryDfltValColumnsList(hm);
	    }
	    else
	    {
		repSessionCO.setQryColumnsList(hm);
	    }
	}
	set_showSmartInfoBtn("false");
	}
	catch(BOException e)
	{
	    if(ConstantsCommon.QUERY_NO_DEFINED_SYNTAX.equals(e.getMsgIdent()))
	    {
		queryNoDefSyntax = BigDecimal.ONE;
	    }
	    handleException(e,null,null);
	}
	catch(Exception e)
	{
	    handleException(e,null,null);
	}
	return "linkQuery";
    }

    public String loadLinkQryGrid() throws ClassNotFoundException, BaseException, IOException
    {
	// if(NumberUtil.isEmptyDecimal(queryId))
	// {
	// setRecords(0);
	// setGridModel(null);
	// }
	// queryIdLkp=100549;
	getForSrcOrDfltVal();
	String qryName = getQryName();
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.getLinkQryArsMap().size();
	String repArgName = getOldRepArgsName();// getRepArgsName();
	String oldRepArgsName = getRepArgsName();// getOldRepArgsName();
	String forSrcOrDfltVal = getForSrcOrDfltVal();
	boolean existKey = false;
	String[] arrayKey =null;
// by h.khoury to check all repArgName and oldRepArgsName if i need to add to rep name ~1 or ~2
	if(repSessionCO.getLinkQryArsMap().size() > 0)
	{
	    HashMap<String, List<IRP_QUERY_ARG_MAPPINGCO>> linkQryArsMap = repSessionCO.getLinkQryArsMap();
	    for(Entry<String, List<IRP_QUERY_ARG_MAPPINGCO>> entry : linkQryArsMap.entrySet())
	    {
		arrayKey = entry.getKey().split("~");
		//String dsYN =arrayKey[1];
		String key = entry.getKey(); //key.split("_");
		if(key.equals(repArgName+"~"+forSrcOrDfltVal))
		{
		    existKey = true;
		    break;
		}

	    }
	    if(existKey)
	    {
		if(getForSrcOrDfltVal().equals("2"))
		{
		    qryName =  getQueryNameDftlValLkp();
		}
		if(qryName == null || qryName.equals(""))
		{
		    existKey = false;
		    repSessionCO.getLinkQryArsMap().remove(repArgName);
		}
		else
		{

		    ArrayList<IRP_QUERY_ARG_MAPPINGCO> list = (ArrayList<IRP_QUERY_ARG_MAPPINGCO>) linkQryArsMap
			    .get(repArgName+"~"+arrayKey[1]);

		    if(!repArgName.equals(oldRepArgsName))
		    {
			repSessionCO.getLinkQryArsMap().put(oldRepArgsName, list);
			repSessionCO.getLinkQryArsMap().remove(repArgName);
		    }

		    /*
		     * not used now
		     * 
		     * for(int l=0;l<list.size();l++) { IRP_QUERY_ARG_MAPPINGCO
		     * irpQryArgMapCO =new IRP_QUERY_ARG_MAPPINGCO();
		     * IRP_QUERY_ARG_MAPPINGCO irpQryArgMapForLstCO =new
		     * IRP_QUERY_ARG_MAPPINGCO(); irpQryArgMapCO = list.get(l);
		     * irpQryArgMapForLstCO
		     * .setQRY_ARG_NAME(irpQryArgMapCO.getIrpQueryArgsMappingVO
		     * ().getQUERY_ARG_NAME());
		     * //irpQryArgMapForLstCO.setQRY_ARG_TYPE("NUMBER");
		     * if(irpQryArgMapCO
		     * .getIrpQueryArgsMappingVO().getSTATIC_VALUE()==null) {
		     * irpQryArgMapForLstCO.setMAP_PARAM_TYPE("2");
		     * irpQryArgMapForLstCO
		     * .getIrpQueryArgsMappingVO().setREPORT_MAPPED_ARG_NAME
		     * (irpQryArgMapCO
		     * .getIrpQueryArgsMappingVO().getREPORT_MAPPED_ARG_NAME());
		     * } else { irpQryArgMapForLstCO.setMAP_PARAM_TYPE("0");
		     * irpQryArgMapForLstCO
		     * .getIrpQueryArgsMappingVO().setSTATIC_VALUE
		     * (irpQryArgMapCO
		     * .getIrpQueryArgsMappingVO().getSTATIC_VALUE()); }
		     * 
		     * qryArgMaplst.add(irpQryArgMapForLstCO); }
		     */
		    setRecords(list.size());
		    setGridModel(list);
		}
	    }
	}

	if(!existKey)
	{
	    if(getForSrcOrDfltVal().equals("2"))
	    {
		queryIdLkp =  getQueryDfltValIdLkp();
	    }
	    if(NumberUtil.isEmptyDecimal(this.queryIdLkp))
	    {
		setRecords(0);
		setGridModel(null);
	    }
	    else
	    {

		IRP_AD_HOC_QUERYCO queryCO = queryBO.returnQueryById(queryIdLkp, false);
		LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments;
		arguments = queryCO.getSqlObject().getArgumentsList();

		ArrayList<IRP_REP_ARGUMENTSCO> lst = new ArrayList(arguments.values());
		ArrayList<IRP_QUERY_ARG_MAPPINGCO> qryArgMaplst = new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();

		if(arguments.values().size() > 0)
		{
		    for(int i = 0; i < lst.size(); i++)
		    {
			IRP_QUERY_ARG_MAPPINGCO irpQryMapCO = new IRP_QUERY_ARG_MAPPINGCO();
			irpQryMapCO.setQRY_ARG_NAME(lst.get(i).getARGUMENT_NAME());
			irpQryMapCO.setQRY_ARG_TYPE(lst.get(i).getARGUMENT_TYPE());
			// if(lst.get(i).getIrpQryArgMapgLst().get(i).getIrpQueryArgsMappingVO().getSTATIC_VALUE().equals(null))
			// {
			// lst.get(i).setMAP_PARAM_TYPE("0");
			// }
			// else
			// {
			// lst.get(i).setMAP_PARAM_TYPE("2");
			// }
			qryArgMaplst.add(irpQryMapCO);
		    }
		}
		setRecords(qryArgMaplst.size());
		setGridModel(qryArgMaplst);
	    }
	}

	return SUCCESS;
    }

    public String putInLinkQryMap() throws Exception
    {
	if(updatesLinkQryArgs != null && !updatesLinkQryArgs.equals(""))
	{
	    
	    GridUpdates gu = getGridUpdates(updatesLinkQryArgs, IRP_QUERY_ARG_MAPPINGCO.class, true);
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    String repArgName = getRepArgsName();
	    repSessionCO.getLinkQryArsMap().put(repArgName+"~"+getSrcOrDflt(), gu.getLstAllRec());

	}
	return SUCCESS;
    }

    public String retColumnsByType() throws Exception
    {
	try
	{
	    get_pageRef();
	    getUpdates();
	    fillLookupArgs("fffff", "/path/designer/queryDesign_fillArgsFieldsGrid.action",
		    getText("reporting.fildsList"));
	}
	catch(Exception e)
	{
	    log.error("Error in retColumnsByType");
	}

	return SUCCESS;
    }

    public String fillLookupArgs(String p, String gridUrl, String gridCaption) throws JSONException
    {
	String columnType = getUpdates();

	if("1".equals(columnType))
	{
	    String[] name = { "FIELD_ALIAS" };
	    String[] colType = { "text" };
	    String[] titles = { getText("reporting.fieldName") };
	    fillLkp(gridUrl, gridCaption, name, colType, titles);
	}
	else if("2".equals(columnType))
	{
	    String[] name = { "ARGUMENT_NAME" };
	    String[] colType = { "text" };
	    String[] titles = { getText("reporting.fieldName") };
	    fillLkp(gridUrl, gridCaption, name, colType, titles);
	}

	return SUCCESS;
    }

    public String retReportName() throws JSONException
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject();
	updates=repSessionCO.getReportCO().getREPORT_NAME();
	return SUCCESS;
    }
    
    public String fillArgsFieldsGrid() throws Exception
    {
	String columnType = getUpdates();
	String argType = getUpdates1();
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if( "1".equals(columnType))
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

	return SUCCESS;
    }

    public String fillLkp(String gridUrl, String gridCaption, String[] name, String[] colType, String... titles)
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
	    lookup(grid, lsGridColumn, null, qryGridSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String getHideArgsColGrid()
    {

	if(get_pageRef() != null && (get_pageRef().equals("RD00Q")))
	{
	    return "true";
	}
	else if(get_pageRef() != null && get_pageRef().equals("RD00R"))
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    repSessionCO.isReportParams();
	    if(repSessionCO.isReportParams())
	    {
		return "false";		
	    }
	    else
	    {
		return "true";
	    }
	}
	else
	{
	    return "false";
	}

    }

    public String addUpdateArgument()
    {

	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	//added for null query object in db and editing the already existing query
	if(RepConstantsCommon.MODE_ADD.equals(argumentsMode) && repSessionCO.getSqlObj() == null)
	    repSessionCO.setSqlObj(new SQL_OBJECT());
	SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	IRP_AD_HOC_REPORTCO report = repSessionCO.getReportCO();
	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> localArguments = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();
	if(ConstantsCommon.TRUE.equals(argumentCO.getMULTISELECT_YN_STR()))
	{
	    argumentCO.setMULTISELECT_YN(BigDecimal.ONE);
	}
	else
	{
	    argumentCO.setMULTISELECT_YN(BigDecimal.ZERO);
	}
	if(ConstantsCommon.TRUE.equals(argumentCO.getTO_SAVE_YN_STR()))
	{
	    argumentCO.setTO_SAVE_YN(BigDecimal.ONE);
	}
	else
	{
	    argumentCO.setTO_SAVE_YN(BigDecimal.ZERO);
	}	
	if(ConstantsCommon.TRUE.equals(argumentCO.getCIF_AUDIT_YN_STR()))
	{
	    argumentCO.setCIF_AUDIT_YN(BigDecimal.ONE);
	}
	else
	{
	    argumentCO.setCIF_AUDIT_YN(BigDecimal.ZERO);
	}
	if(repSessionCO.isReportParams())
	{
	    localArguments = report.getArgumentsList();
	}
	else
	{
	    localArguments = sqlObj.getArgumentsList();
	}
	IRP_REP_ARGUMENTSCO argument = new IRP_REP_ARGUMENTSCO();
	if(RepConstantsCommon.MODE_ADD.equals(argumentsMode))
	{
	    argument.setIndex(generateId());
	}
	else
	{
	    argument = localArguments.get(argumentCO.getIndex());
	}

	if(BigDecimal.valueOf(6).equals(argumentCO.getARGUMENT_SOURCE())
		|| BigDecimal.valueOf(7).equals(argumentCO.getARGUMENT_SOURCE()))
	{
	    argument.setARGUMENT_TYPE(RepConstantsCommon.dateTime);
	}
	else if(ConstantsCommon.REP_LANG_ARG_SOURCE.equals(argumentCO.getARGUMENT_SOURCE()))
	{
	    argument.setARGUMENT_TYPE(ConstantsCommon.PARAM_TYPE_VARCHAR2);
	}
	else
	{
	    argument.setARGUMENT_TYPE(argumentCO.getARGUMENT_TYPE());
	}
	argument.setARGUMENT_LABEL(argumentCO.getARGUMENT_LABEL());
	argument.setARGUMENT_NAME(argumentCO.getARGUMENT_NAME());
	argument.setARGUMENT_VALUE(argumentCO.getARGUMENT_VALUE());
	argument.setARGUMENT_SOURCE(argumentCO.getARGUMENT_SOURCE());
	argument.setARGUMENT_DATE_VALUE(argumentCO.getARGUMENT_DATE_VALUE());
	argument.setTO_SAVE_YN(argumentCO.getTO_SAVE_YN());
	argument.setCIF_AUDIT_YN(argumentCO.getCIF_AUDIT_YN());
	
	if(ConstantsCommon.SESSION_ARG_SOURCE.equals(argumentCO.getARGUMENT_SOURCE())
		|| ConstantsCommon.TRANS_SESSION_ARG_SOURCE.equals(argumentCO.getARGUMENT_SOURCE()))
	{
	    argument.setSESSION_ATTR_NAME(argumentCO.getSESSION_ATTR_NAME());
	}

	if(("2").equals(getSrcOrDflt()))
	{
	    argument.setQUERY_ID_DEFAULT(getQueryDfltValIdLkp());
	    argument.setARGUMENT_SRC_DEFAULT(new BigDecimal(2));
	    argument.setDEFAULT_VAL_QRY_NAME(getQueryNameDftlValLkp());
	    argument.setDEFAULT_VALUE_COL_NAME(getQryDfltValColName());
	    argument.setDEFAULT_VALUE_COL_DESC(getDefaultValueColDesc());
	    if(!(new BigDecimal(ConstantsCommon.REP_ARG_TYPE_QRY).equals(argumentCO.getARGUMENT_SOURCE())))
	    {
		argument.setARG_QUERY_OPTIONS(RepConstantsCommon.ONE);
		argument.setDEFAULT_VALUE_COL_DESC("");
	    }
	}
	else
	{
	    argument.setQUERY_ID_DEFAULT(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE);
	    argument.setARGUMENT_SRC_DEFAULT(BigDecimal.ONE);
	    argument.setDEFAULT_VAL_QRY_NAME("");
	    argument.setDEFAULT_VALUE_COL_NAME("");  
	    argument.setDEFAULT_VALUE_COL_DESC("");  
	}
	    if(new BigDecimal(3).equals(argumentCO.getARGUMENT_SOURCE()) || new BigDecimal(10).equals(argumentCO.getARGUMENT_SOURCE()))
	    {
		argument.setMULTISELECT_YN(argumentCO.getMULTISELECT_YN());
        	    if(BigDecimal.ONE.equals(argument.getMULTISELECT_YN()))
        	    {
        		argument.setCOLUMN_DESC("");
        		argument.setDEFAULT_VALUE_COL_DESC("");
        		argument.setARG_QUERY_OPTIONS(RepConstantsCommon.ONE);
        	    }
        	    else
        	    {
        		argument.setARG_QUERY_OPTIONS(getArgQueryOptions());
        		if(RepConstantsCommon.ONE.equals(getArgQueryOptions()))
        		{
        		    argument.setCOLUMN_DESC("");
        		    argument.setDEFAULT_VALUE_COL_DESC("");
        		}
        		else
        		{
        		    argument.setCOLUMN_DESC(getColumnDesc());
        		}
        	    }
		argument.setQUERY_NAME(getQryName());
		argument.setCOLUMN_NAME(getColumnName());
		argument.setQUERY_ID(getQueryIdLkp());
	    }
	    else
	    {
		argument.setQUERY_NAME("");
		argument.setCOLUMN_NAME("");
		argument.setCOLUMN_DESC("");
		
		argument.setQUERY_ID(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE);
	    }

	argument.setValueFlag(argumentCO.isValueFlag());
	argument.setFLAG_VALUE_ON(argumentCO.getFLAG_VALUE_ON());
	argument.setFLAG_VALUE_OFF(argumentCO.getFLAG_VALUE_OFF());
	argument.setARGUMENT_ORDER(argumentCO.getARGUMENT_ORDER());
	argument.setDISPLAY_FLAG(argumentCO.getDISPLAY_FLAG());
	argument.setENABLE_FLAG(argumentCO.getENABLE_FLAG());
	argument.setLINK_REP_QRY_ARG(argumentCO.getLINK_REP_QRY_ARG());
	//checking if constraints has been added to the argument
	if(report != null)
	{
	    IRP_REP_ARG_CONSTRAINTSCO constrCO = report.getArgConstraintsMap().get(argument.getARGUMENT_NAME());
	    if(constrCO != null)
	    {
		argument.getIrpRepArgConstraintCO().setCASE_SENSITIVITY(constrCO.getCASE_SENSITIVITY());
		argument.getIrpRepArgConstraintCO().setCONDITION(constrCO.getCONDITION());
		argument.getIrpRepArgConstraintCO().setFORMAT(constrCO.getFORMAT());
		argument.getIrpRepArgConstraintCO().setHIDE_EXPR(constrCO.getHIDE_EXPR());
		argument.getIrpRepArgConstraintCO().setMAX_LENGTH(constrCO.getMAX_LENGTH());
		argument.getIrpRepArgConstraintCO().setMAX_VAL(constrCO.getMAX_VAL());
		argument.getIrpRepArgConstraintCO().setMIN_VAL(constrCO.getMIN_VAL());
		argument.getIrpRepArgConstraintCO().setSHOW_EXPR(constrCO.getSHOW_EXPR());
		report.getArgConstraintsMap().remove(argument.getARGUMENT_NAME());
	    }
	}
	if(RepConstantsCommon.MODE_ADD.equals(argumentsMode))
	{
	    localArguments.put(argument.getIndex(), argument);
//	    if(!repSessionCO.isReportParams() && this.paramIdx != null && this.paramIdx == 0)
//	    {
//		// report.getArgumentsList().put(argument.getIndex(),
//		// argument);// stoped by h.khoury to prevent
//		// this.setParametersList(fillParametersList());
//	    }
	}
	else
	{
	    if(repSessionCO.isReportParams() && argument.getARGUMENT_ID() != null)
	    {
		report.getArgsDBUpdate().remove(argument.getIndex());
		report.getArgsDBUpdate().put(argument.getIndex(), argument);
	    }
	}
	return SUCCESS;
    }

    public void deleteArgument()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	IRP_AD_HOC_REPORTCO report = repSessionCO.getReportCO();
	if(repSessionCO.isReportParams())
	{
	    report.getArgumentsList().remove(argumentCO.getIndex());
	    //empty the entry in report's map for the constraints for the case any data exists
	    //report.getArgConstraintsMap().remove(argumentCO.getARGUMENT_NAME());

	    if(argumentCO.getARGUMENT_ID() != null)
	    {
		argumentCO.setREPORT_ID(report.getREPORT_ID());
		report.getArgsDbDelete().put(argumentCO.getIndex(), argumentCO);
	    }
	}
	else
	{
	    sqlObj.getArgumentsList().remove(argumentCO.getIndex());
	}

    }

    public String entity1Change() throws BaseException
    {
	if(argEntity1 == null || argEntity1.isEmpty())
	{
	    this.setFields1(new ArrayList<IRP_FIELDSCO>());	  
	}
	else
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    if(sqlObj != null && sqlObj.getEntities() != null && sqlObj.getEntities().get(Long.valueOf(argEntity1)) != null)
	    {
		IRP_ENTITIESCO currentEntity = sqlObj.getEntities().get(Long.valueOf(argEntity1));

		copyproperties(qryGridSC);
		qryGridSC.setRecToskip(0);
		qryGridSC.setNbRec(32000);
		qryGridSC.setFIELD_NAME(null);
		qryGridSC.setENTITY_DB_NAME(currentEntity.getENTITY_DB_NAME());
		qryGridSC.setPARENT_ID(Long.valueOf("0"));
		fields1 = queryBO.retDBFields(qryGridSC);
		addObjsIndex(fields1);
	    }
	}
	return "input";
    }

//    public List<IRP_FIELDSCO> getFields1()
//    {
//	return super.getFields1();
//    }
//
//    public List<IRP_FIELDSCO> getFields2()
//    {
//	return super.getFields2();
//    }

    public String entity2Change() throws BaseException
    {
	if(argEntity2 == null || argEntity2.isEmpty())
	{
	    this.setFields2(new ArrayList<IRP_FIELDSCO>());
	}
	else
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    if(sqlObj != null && sqlObj.getEntities() != null && sqlObj.getEntities().get(Long.valueOf(argEntity2)) != null)
	    {
		IRP_ENTITIESCO currentEntity = sqlObj.getEntities().get(Long.valueOf(argEntity2));
		copyproperties(qryGridSC);
		qryGridSC.setRecToskip(0);
		qryGridSC.setNbRec(32000);
		qryGridSC.setFIELD_NAME(null);
		qryGridSC.setENTITY_DB_NAME(currentEntity.getENTITY_DB_NAME());
		qryGridSC.setPARENT_ID(Long.valueOf("0"));
		fields2 = queryBO.retDBFields(qryGridSC);
		if(fields2!=null)
		{
		    addObjsIndex(fields2);
		}
	    }
	}
	return "input";
    }

    public String joinGridUrl()
    {
	copyproperties(qryGridSC);
	SQL_OBJECT sqlObj = retSqlObj();
	// if(retSqlObj()==null)
	// {
	// repSessionCO.setSqlObj(new SQL_OBJECT());
	// }
	if(sqlObj.getJoinsList() == null)
	{
	    sqlObj.setJoinsList(new LinkedHashMap<Long, CONDITION_OBJECT>());
	}
	LinkedHashMap<Long, CONDITION_OBJECT> joins = sqlObj.getJoinsList();

	int nbRec = qryGridSC.getNbRec();
	int recToSkip = qryGridSC.getRecToskip();
	int maxRec = nbRec + recToSkip;
	if(joins.size() < maxRec)
	{
	    maxRec = joins.size();
	}
	ArrayList<CONDITION_OBJECT> lst = new ArrayList<CONDITION_OBJECT>();
	for(int i = recToSkip; i < maxRec; i++)
	{
	    lst.add((CONDITION_OBJECT) joins.values().toArray()[i]);
	}

	setRecords(joins.size());
	setGridModel(lst);

	return SUCCESS;
    }

    public void addUpdateJoin() throws BaseException
    {
	SQL_OBJECT sqlObj = retSqlObj();
	LinkedHashMap<Long, CONDITION_OBJECT> localJoins = sqlObj.getJoinsList();
	qryGridSC.setRecToskip(0);
	qryGridSC.setNbRec(32000);
	qryGridSC.setPARENT_ID(Long.valueOf("0"));
	if( RepConstantsCommon.MODE_ADD.equals(joinMode) )
	{
	    CONDITION_OBJECT condition = new CONDITION_OBJECT();
	    condition.setIndex(generateId());
	    condition.setEntity1(sqlObj.getEntities().get(Long.valueOf(argEntity1)));
	    condition.setEntity2(sqlObj.getEntities().get(Long.valueOf(argEntity2)));
	    condition.setJoin(argJoinType);

	    qryGridSC.setENTITY_DB_NAME(condition.getEntity1().getENTITY_DB_NAME());
	    qryGridSC.setFIELD_NAME(argField1);
	    List<IRP_FIELDSCO> field1 = queryBO.retDBFields(qryGridSC);
	    addObjsIndex(field1);
	    if(field1 == null || field1.isEmpty())
	    {
		condition.setField1(null);		
	    }
	    else
	    {
		condition.setField1(field1.get(0));
	    }
	    qryGridSC.setENTITY_DB_NAME(condition.getEntity2().getENTITY_DB_NAME());
	    qryGridSC.setFIELD_NAME(argField2);
	    List<IRP_FIELDSCO> field2 = queryBO.retDBFields(qryGridSC);
	    addObjsIndex(field2);
	    if(field2 == null || field2.isEmpty())
	    {
		condition.setField2(null);
	    }
	    else
	    {
		condition.setField2(field2.get(0));
	    }

	    localJoins.put(condition.getIndex(), condition);
	}
	else
	{
	    CONDITION_OBJECT condition = localJoins.get(getLocIndex());
	    if(condition != null)
	    {
		condition.setEntity1(sqlObj.getEntities().get(Long.valueOf(argEntity1)));
		condition.setEntity2(sqlObj.getEntities().get(Long.valueOf(argEntity2)));
		condition.setJoin(argJoinType);
		qryGridSC.setENTITY_DB_NAME(condition.getEntity1().getENTITY_DB_NAME());
		qryGridSC.setFIELD_NAME(argField1);
		List<IRP_FIELDSCO> field1 = queryBO.retDBFields(qryGridSC);
		addObjsIndex(field1);
		if(field1 == null || field1.isEmpty())
		{
		    condition.setField1(null);		   
		}
		else
		{
		    condition.setField1(field1.get(0));
		}
		qryGridSC.setENTITY_DB_NAME(condition.getEntity2().getENTITY_DB_NAME());
		qryGridSC.setFIELD_NAME(argField2);
		List<IRP_FIELDSCO> field2 = queryBO.retDBFields(qryGridSC);
		addObjsIndex(field2);
		if(field2 == null || field2.isEmpty())
		{
		    condition.setField2(null);
		}
		else
		{
		    condition.setField2(field2.get(0));
		}
	    }
	}
    }

    public void deleteJoin()
    {
	(this.retSqlObj()).getJoinsList().remove(locIndex);
    }

    public String findSingleHaving() throws BaseException
    {
	try
	{
	    if(!locIndex.equals(Long.valueOf(0)))
	    {
		havingObject = retSqlObj().getHavingList().get(locIndex);
		// havingObject.getEntity2().getENTITY_ALIAS();
		if(havingObject.getEntity2() != null)
		{
		    copyproperties(qryGridSC);
		    qryGridSC.setRecToskip(0);
		    qryGridSC.setNbRec(32000);
		    qryGridSC.setFIELD_NAME(null);
		    qryGridSC.setENTITY_DB_NAME(havingObject.getEntity2().getENTITY_DB_NAME());
		    qryGridSC.setPARENT_ID(Long.valueOf("0"));
		    fields2 = queryBO.retDBFields(qryGridSC);
		    addObjsIndex(fields2);
		}
	    }
	}
	catch(Exception e)
	{
	    //log.error("Error in findSingleHaving() method");
	    handleException(e, "Error filling fields", "fillFields.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String findSingleJoin() throws BaseException
    {
	try
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    LinkedHashMap<Long, CONDITION_OBJECT> joins = sqlObj.getJoinsList();
	    Long jnIndex = getLocIndex();
	    CONDITION_OBJECT joinObj = joins.get(jnIndex);
	    if(joinObj != null)
	    {
		String ent1DB = joinObj.getEntity1().getENTITY_DB_NAME();
		String ent2DB = joinObj.getEntity2().getENTITY_DB_NAME();

		copyproperties(qryGridSC);
		qryGridSC.setRecToskip(0);
		qryGridSC.setNbRec(32000);
		qryGridSC.setFIELD_NAME(null);
		qryGridSC.setPARENT_ID(Long.valueOf("0"));

		// fill fields1
		qryGridSC.setENTITY_DB_NAME(ent1DB);
		fields1 = queryBO.retDBFields(qryGridSC);
		addObjsIndex(fields1);

		// fill fields2

		qryGridSC.setENTITY_DB_NAME(ent2DB);
		fields2 = queryBO.retDBFields(qryGridSC);
		addObjsIndex(fields2);
	    }
	}
	catch(Exception e)
	{
	    //log.error("Error in findSingleJoin() method");
	    handleException(e, "Error filling fields", "fillFields.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String havingGridUrl()
    {
	copyproperties(qryGridSC);
	SQL_OBJECT sqlObj = retSqlObj();
	// if(sqlObj==null)
	// {
	// repSessionCO.setSqlObj(new SQL_OBJECT());
	// }
	if(sqlObj.getHavingList() == null)
	{
	    sqlObj.setHavingList(new LinkedHashMap<Long, CONDITION_OBJECT>());
	}

	LinkedHashMap<Long, CONDITION_OBJECT> havings = sqlObj.getHavingList();

	int nbRec = qryGridSC.getNbRec();
	int recToSkip = qryGridSC.getRecToskip();
	int maxRec = nbRec + recToSkip;
	if(havings.size() < maxRec)
	{
	    maxRec = havings.size();
	}
	ArrayList<CONDITION_OBJECT> lst = new ArrayList<CONDITION_OBJECT>();
	for(int i = recToSkip; i < maxRec; i++)
	{
	    lst.add((CONDITION_OBJECT) havings.values().toArray()[i]);
	}

	setRecords(havings.size());
	setGridModel(lst);
	return SUCCESS;
    }

    public String havingEntityChange() throws BaseException
    {
	if(havingEntity == null || havingEntity.isEmpty())
	{
	    fields2 = new ArrayList<IRP_FIELDSCO>();
	}
	else
	{
	    SQL_OBJECT sqlObj = retSqlObj();
	    if(sqlObj != null && sqlObj.getEntities() != null
		    && sqlObj.getEntities().get(Long.valueOf(havingEntity)) != null)
	    {
		IRP_ENTITIESCO currentEntity = sqlObj.getEntities().get(Long.valueOf(havingEntity));

		copyproperties(qryGridSC);
		qryGridSC.setRecToskip(0);
		qryGridSC.setNbRec(32000);
		qryGridSC.setFIELD_NAME(null);
		qryGridSC.setENTITY_DB_NAME(currentEntity.getENTITY_DB_NAME());
		qryGridSC.setPARENT_ID(Long.valueOf("0"));
		fields2 = queryBO.retDBFields(qryGridSC);
		addObjsIndex(fields2);
	    }
	}
	return "input";
    }

    public void addUpdateHaving() throws BaseException
    {
	SQL_OBJECT sqlObj = retSqlObj();
	if(sqlObj == null)
	{
	    return;
	}
	if(sqlObj.getHavingList() == null)
	{
	    sqlObj.setHavingList(new LinkedHashMap<Long, CONDITION_OBJECT>());
	}

	IRP_REP_ARGUMENTSCO argument1 = null;
	IRP_REP_ARGUMENTSCO argument2 = null;

	if(ARGUMENT1 != null && !ARGUMENT1.equals(""))
	{
	    argument1 = sqlObj.getArgumentsList().get(Long.valueOf(ARGUMENT1));
	}
	if(ARGUMENT2 != null && !ARGUMENT2.equals(""))
	{
	    argument2 = sqlObj.getArgumentsList().get(Long.valueOf(ARGUMENT2));
	}
	IRP_ENTITIESCO hvngEntity = null;
	if(havingEntity != null && !havingEntity.equals(""))
	{
	    hvngEntity = sqlObj.getEntities().get(Long.valueOf(havingEntity));
	}
	IRP_FIELDSCO field2 = null;
	if(havingField2 != null && !havingField2.equals("") && hvngEntity != null)
	{
	    copyproperties(qryGridSC);
	    qryGridSC.setRecToskip(0);
	    qryGridSC.setNbRec(32000);
	    qryGridSC.setPARENT_ID(Long.valueOf("0"));

	    qryGridSC.setENTITY_DB_NAME(hvngEntity.getENTITY_DB_NAME());
	    qryGridSC.setFIELD_NAME(havingField2);
	    field2 = queryBO.retDBFields(qryGridSC).get(0);
	    addObjsIndex(fields2);
	}
	IRP_FIELDSCO field1 = null;
	if(havingField1 != null && !havingField1.equals(""))
	{
	    field1 = sqlObj.getAggregateFields().get(Long.valueOf(havingField1));
	}

	CONDITION_OBJECT having;
	if( "".equals(havingIndex) || "0".equals(havingIndex))
	{
	    having = new CONDITION_OBJECT();
	    having.setIndex(generateId());
	}
	else
	{
	    having = sqlObj.getHavingList().get(Long.valueOf(havingIndex));
	}

	having.setArgument1(argument1);
	having.setArgument2(argument2);
	having.setConjunction(havingObject.getConjunction());

	having.setLparenthesis(havingObject.getLparenthesis());
	having.setOperator(havingObject.getOperator());
	having.setRparenthesis(havingObject.getRparenthesis());
	having.setValue(havingObject.getValue());
	having.setField1(field1);
	having.setEntity2(hvngEntity);
	having.setField2(field2);
	having.setOperatorName(havingObject.getOperator());
	if(havingObject.getOperator() != null && havingObject.getOperator().equals("bet"))
	{
	    having.setOperatorName("BETWEEN");
	}
	sqlObj.getHavingList().put(having.getIndex(), having);
    }

    public String deleteHaving()
    {
	SQL_OBJECT sqlObj = this.retSqlObj();
	sqlObj.getHavingList().remove(locIndex);
	conditionRecordsCount = String.valueOf(sqlObj.getHavingList().size());
	return "input";
    }

    public String createQuery()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setSqlObj(new SQL_OBJECT());
	queryCO.setQUERY_NAME("");
	return "queryDesigner";
    }

    public String createStaticQuery()
    {
	String openSqb = getOpenSqb();
	if(openSqb != null && openSqb.equals("true"))
	{
	    request.setAttribute("openSqb", null);
	}
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	repSessionCO.setSqlObj(new SQL_OBJECT());
	return "queryDesigner";
    }

    public String chooseQryType() throws JSONException
    {
	return "qryTypes";
    }

    public String cancelQry() throws JSONException
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	SQL_OBJECT oldSqlObj = repSessionCO.getOldSqlObj();
	repSessionCO.setSqlObj(oldSqlObj);
	return "input";
    }

    public String getQrySyntax() throws JSONException
    {
	String syntax = "";
	try
	{
	    if(tab != null && tab.equals("syntax"))
	    {
		SQL_OBJECT sqlObj = retSqlObj();
		if(sqlObj != null)
		{
		    StringBuffer sql = queryBO.generateSql(sqlObj);
		    syntax = sql.toString();
		}
	    }

	}
	catch(Exception x)
	{
	    //log.error(x, "Error generating query syntax");
	    handleException(x, "Error generating query syntax", "generateSqlSyntax.exceptionMsg._key");
	    syntax = "Wrong SQL";
	}
	return syntax;
    }

    public String getQueryNameReadOnly()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	return (repSessionCO.getReportCO() == null) ? "false" : "true";
    }

    public String getHavConjunctionDisabled()
    {
	if(tab != null && tab.equals("having"))
	{
	    LinkedHashMap<Long, CONDITION_OBJECT> list = retSqlObj().getHavingList();
	    return (list == null || list.isEmpty()) ? "true" : "false";
	}
	return null;
    }

    public String argumentUsage() throws JSONException
    {
	try
	{
	    //String result = "";
	    StringBuffer resultSB= new StringBuffer("");
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    SQL_OBJECT sqlObj = repSessionCO.getSqlObj();
	    Iterator it;
	    IRP_REP_ARGUMENTSCO argObj = null;
	    if(sqlObj == null || repSessionCO.isReportParams())
	    {
		if(repSessionCO.isReportParams())
		{
		    argObj = repSessionCO.getReportCO().getArgumentsList().get(Long.valueOf(updates));
		}
	    }
	    else
	    {
		argObj = sqlObj.getArgumentsList().get(Long.valueOf(updates));
	    }
	    if(sqlObj != null && !repSessionCO.isReportParams())
	    {
		HashMap<Long, CONDITION_OBJECT> condMap = sqlObj.getConditionsList();
		HashMap<Long, CONDITION_OBJECT> havingMap = sqlObj.getHavingList();
		CONDITION_OBJECT myObj;
		it = condMap.values().iterator();
		while(it.hasNext())
		{
		    myObj = (CONDITION_OBJECT) it.next();
		    if((myObj.getArgument1() != null && myObj.getArgument1().equals(argObj))
			    || (myObj.getArgument2() != null && myObj.getArgument2().equals(argObj)))
		    {
			//result = getText("designer.conditions");
			resultSB= new StringBuffer(getText("designer.conditions"));
			break;
		    }
		}
		
		it = havingMap.values().iterator();
		while(it.hasNext())
		{
		    myObj = (CONDITION_OBJECT) it.next();
		    if((myObj.getArgument1() != null && myObj.getArgument1().equals(argObj))
			    || (myObj.getArgument2() != null && myObj.getArgument2().equals(argObj)))
		    {
			if(resultSB.length() > 0)
			{
			    //result += " , " + getText("designer.having");
			    resultSB.append(" , ").append(getText("designer.having"));
			}
			else
			{
			    //result = getText("designer.having");
			    resultSB = new StringBuffer(getText("designer.having"));
			}
			break;
		    }
		}
	    }

	    if(argObj != null && repSessionCO.getReportCO() != null) // if it is
	    // not
	    // called
	    // from the
	    // query
	    // section
	    {
		// check if the parameter is linked to a procedure
		HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> procMap = repSessionCO.getReportCO()
			.getProcParamsMap();
		it = procMap.values().iterator();
		ArrayList<IRP_REP_PROC_PARAMSCO> paramsLst;
		IRP_REP_PROC_PARAMSCO paramCO;
		while(it.hasNext())
		{
		    paramsLst = (ArrayList<IRP_REP_PROC_PARAMSCO>) it.next();
		    for(int i = 0; i < paramsLst.size(); i++)
		    {
			paramCO = paramsLst.get(i);
			if(paramCO.getPARAM_ID() != null
				&& ((argObj.getARGUMENT_ID() != null && paramCO.getPARAM_ID().intValue() == argObj
					.getARGUMENT_ID().intValue()) || (argObj.getIndex() != 0 && paramCO
					.getPARAM_ID().longValue() == argObj.getIndex())))
			{
			   // result = getText("upDown.Procs");
			    resultSB = new StringBuffer(getText("upDown.Procs"));
			    break;
			}
		    }
		}
	    }
	    
	    if(repSessionCO.getReportCO()!=null)
	    {
		resultSB.append(checkArgUsedInComparisonExpressions(argObj,repSessionCO.getReportCO().getArgumentsList()));
	    }
	    setUpdates(resultSB.toString());
	}
	catch(Exception e)
	{
	    //log.error("Error in checking argument usage");
	    handleException(e, "Error checking argument usage", "checkArgUsage.exceptionMsg._key");
	}
	return SUCCESS;
    }

    /**
     * function that checks if the argument is used in the comparison or show/hide of other arguments
     * @param argObj to be deleted
     * @param argsList list of arguments
     * @return
     * @throws JSONException
     */
    public String checkArgUsedInComparisonExpressions(IRP_REP_ARGUMENTSCO argObj,
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> argsList) throws JSONException
    {
	IRP_REP_ARG_CONSTRAINTSCO constrCO;
	String argName = argObj.getARGUMENT_NAME();
	for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : argsList.entrySet())
	{
	    constrCO = entry.getValue().getIrpRepArgConstraintCO();
	    //it's the same argument
	    if(entry.getKey().equals(argObj.getIndex()))
	    {
		continue;
	    }
	    if((constrCO.getCONDITION() != null && constrCO.getCONDITION().indexOf(argName) != -1)
		    || (constrCO.getSHOW_EXPR() != null && constrCO.getSHOW_EXPR().indexOf(argName) != -1)
		    || (constrCO.getHIDE_EXPR() != null && constrCO.getHIDE_EXPR().indexOf(argName) != -1))
	    {
		return getText("reporting.argConstraints");
	    }
	}
	return RepConstantsCommon.EMPTY_STRING;
    }
    
    public String retQueryType() throws JSONException
    {
	try
	{
	    setUpdates(retQryTypeStr());
	}
	catch(Exception e)
	{
	    //log.error("Error in returning query type");
	    handleException(e, "Error returning query type", "returnQueryType.exceptionMsg._key");
	}
	return SUCCESS;
    }
    
    public String retQryTypeStr()
    {
	  String result = ConstantsCommon.SQB_QRY_TYPE;
	    SQL_OBJECT sqlObj = this.retSqlObj();
	    if(sqlObj != null && sqlObj.getSqbSyntax() == null && sqlObj.getEntities().size() > 0)
	    {
		result = ConstantsCommon.QBE_QRY_TYPE;
	    }
	    else if(sqlObj != null && sqlObj.getSqbSyntax() == null && sqlObj.getEntities().size() == 0)
	    {
		result = RepConstantsCommon.NEW_QRY_TYPE;
	    }
	    return result;
    }

    /**
     * Method called on query's validation
     * @return
     */
    public String validateSqbQuery() 
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    String sqlString = new String(getSqbSyntax().getBytes());
	    
	    log.debug("\n\n\n" + sqlString + "\n\n\n");
	    SQL_OBJECT sqlObj = retSqlObj();
	    // Replace parameters with values //$P{param2}
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> argMap = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();
	    // test if freeForm or tabular
	    if(sqlObj != null && ConstantsCommon.OUTPUT_LAYOUT_TABULAR.equals(sqlObj.getOutputLayout()))
	    {
		argMap = sqlObj.getArgumentsList();
	    }
	    else if(sqlObj != null && ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM.equals(sqlObj.getOutputLayout()))
	    {
		IRP_AD_HOC_REPORTCO report = repSessionCO.getReportCO();
		argMap = report.getArgumentsList();
	    }
	    else
	    {
		argMap = sqlObj.getArgumentsList();
	    }

	    // to
	    // LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> qryArg = new
	    // LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();
	    // LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> hmArg = new
	    // LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();
	    // int j=0;
	    // for (Entry<Long, IRP_REP_ARGUMENTSCO> entry : argMap.entrySet())
	    // {
	    // Long key = entry.getKey();
	    // IRP_REP_ARGUMENTSCO args = entry.getValue();
	    // args.setARGUMENT_LABEL(args.getARGUMENT_NAME());
	    // j++;
	    // args.setARGUMENT_ORDER(new BigDecimal(j));
	    // args.setLINK_REP_QRY_ARG(args.getARGUMENT_LABEL());
	    // hmArg.put(key, args);
	    //				
	    // }
	    IRP_REP_ARGUMENTSCO argObj = null;
	    String argType;
	    Iterator it = argMap.values().iterator();
	    while(it.hasNext())
	    {
		argObj = (IRP_REP_ARGUMENTSCO) it.next();
		argType = argObj.getARGUMENT_TYPE();
		if(argType != null && argType.equals("NUMBER"))
		{
		    sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}", "(-1)");
		}
		else if(argType != null && argType.equals("VARCHAR2"))// '$P!{UnionName}'
		{
		    sqlString = StringUtil.replaceInString(sqlString, "$P!{" + argObj.getARGUMENT_NAME() + "}", "-1");
		}
		else if(argType != null && (argType.equals("DATE") || argType.equals(ConstantsCommon.datetime)))
		{
		    if(commonLibBO.returnIsSybase() == 1)
		    {
			sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
			"GETDATE()");
		    }
		    else
		    {
			sqlString = StringUtil.replaceInString(sqlString, "$P{" + argObj.getARGUMENT_NAME() + "}",
				"SYSDATE");
		    }
		}
		 sqlString = CommonUtil.handleMultiValParam(sqlString, argObj, argType,0);
		 if(sqlString.indexOf(RepConstantsCommon.VALID_ERROR)!=-1)
		 {
		    throw new BOException(":"
			    + getText("reporting.invalidMultiExpr")
			    + sqlString.substring(Integer.parseInt(RepConstantsCommon.ZERO), sqlString.lastIndexOf("!")
				    - Integer.parseInt(RepConstantsCommon.MULTISELECT_ERROR_SIZE)));
		}
	    }
	    String qryType = ConstantsCommon.SQB_QRY_TYPE;
	    if(sqlObj != null && sqlObj.getEntities().size() > 0)
	    {
		qryType = ConstantsCommon.QBE_QRY_TYPE;
	    }
	    setQryType(qryType);

	    StringBuffer sqbSynt = new StringBuffer(sqbSyntax);
	    if(ConstantsCommon.SQB_QRY_TYPE.equals(qryType))
	    {
		//annasuccar and bassambechara 21may2014:change the query validation method to
		//get the same test result as in preview report
		BigDecimal conId=null;
		if(StringUtil.isNotEmpty(update1))
		{
		    conId = new BigDecimal(update1);
		}
		queryBO.validateSqbQueryDesigner(sqbSynt, new ArrayList<IRP_REP_ARGUMENTSCO> (argMap.values()),conId);
		// fill the fieldsMap
		LinkedHashMap feMap = queryBO.fillSqlFields(sqlString,conId);
		sqlObj.setFields(feMap);
		
		// fill queriesList
		String qryName;
		IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
		if(reportCO == null)// called from the 'queries' section
		{
		    qryName = getUpdates();
		}
		else
		{
		    qryName = sqlObj.getQUERY_NAME();
		    if(qryName == null)
		    {
			qryName = reportCO.getREPORT_NAME() + "_0";
		    }
		}

		sqlObj.setQUERY_NAME(qryName);
		sqlObj.setSqbSyntax(sqbSynt);
		sqlObj.setCONN_ID(conId);
		
		if(reportCO != null) // called from reports section and not from
		// queries section
		{
		    IRP_AD_HOC_QUERYCO queryCO = new IRP_AD_HOC_QUERYCO();
		    List<IRP_AD_HOC_QUERYCO> qrysList = reportCO.getQueriesList();
		    if(qrysList != null && !qrysList.isEmpty())
		    {
			// BigDecimal qryId=oldQryCO.getQUERY_ID();
			// queryCO.setQUERY_ID(qryId);
			// queryCO.setReportQueryVO(oldQryCO.getReportQueryVO());
			queryCO = reportCO.getQueriesList().get(0);
		    }

		    queryCO.setQUERY_NAME(qryName);
		    queryCO.setSqlObject(sqlObj);
		    queryCO.setSqlSyntax(sqbSynt);
		    queryCO.setIndex(0);
		    List<IRP_AD_HOC_QUERYCO> queriesList = new ArrayList<IRP_AD_HOC_QUERYCO>();
		    queriesList.add(queryCO);
		    reportCO.setQueriesList(queriesList);
		}
	    }
	    setUpdates("");
//stop by h.khoury to prevent copying the qry param to rep param on every changes of qry param
//	    if(reportCO != null)
//	    {
//		LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> hmArgs = new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>();
//
//		// this loop is used to set an argument label and order and
//		// argument value from query to the arguments report.
//		int i = 0;
//		for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : argMap.entrySet())
//		{
//		    Long key = entry.getKey();
//		    IRP_REP_ARGUMENTSCO args = entry.getValue();
//		    args.setARGUMENT_LABEL(args.getARGUMENT_NAME());
//		    args.setLINK_REP_QRY_ARG(args.getARGUMENT_LABEL());
//		    i++;
//		    args.setARGUMENT_ORDER(new BigDecimal(i));
//		    hmArgs.put(key, args);
//		}
//		reportCO.setArgumentsList(hmArgs);
//	    }
	}
	catch(Exception e)
	{
	    log.info("Error in validating query syntax");
	    // handleException(e,
	    // "Error validating query syntax","validateQuerySyntax.exceptionMsg._key");
	    String msg = e.getMessage();
	    setUpdates(msg.substring(msg.indexOf(":") + 1, msg.length()));
	}
	return SUCCESS;
    }

    /*This method will fill the session combo box based on the selected argument source*/
    public String fillSessionArgBySource()
    {

	try
	{
	    IRP_SESSION_ATTRIBUTESVO sessionAttrVO = new IRP_SESSION_ATTRIBUTESVO();
	    if(ConstantsCommon.TRANS_SESSION_ARG_SOURCE.toString().equals(getUpdate1()))
	    {
		sessionAttrVO.setLANG_INDEPENDENT_YN(ConstantsCommon.NO);

		sessionAttrList = new ArrayList<String>();
		List<IRP_SESSION_ATTRIBUTESVO> list = commonLibBO.returnSessionAttrList(sessionAttrVO);
		for(IRP_SESSION_ATTRIBUTESVO vo : list)
		{
		    sessionAttrList.add(vo.getATTRIBUTE_NAME());
		}
	    }
	    else
	    {
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		Set<String> set = (repSessionCO.getSessionAttrList()).keySet();
		sessionAttrList = new ArrayList<String>(set);

	    }
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, String> hm = repSessionCO.getSessionAttrList();
	    this.argumentType = hm.get(sessionAttr);

	}
	catch(BaseException e)
	{
	    handleException(e, "Error returning session attributes list", "returnSessAttrList.exceptionMsg._key");
	}
	
	return SUCCESS;
    }
    

    /*On load of the arguments screen fill the repSessionCO.setSessionAttrList*/
    public void fillSessionArgsList()
    {
	HashMap<String, String> hm = new HashMap<String, String>();
	sessionAttrList = new ArrayList<String>();
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    try
	    {
		    IRP_SESSION_ATTRIBUTESVO sessionAttrVO = new IRP_SESSION_ATTRIBUTESVO();
		    List<IRP_SESSION_ATTRIBUTESVO> list = commonLibBO.returnSessionAttrList(sessionAttrVO);
		    for(IRP_SESSION_ATTRIBUTESVO vo : list)
		    {
			hm.put(vo.getATTRIBUTE_NAME(), vo.getATTRIBUTE_TYPE());
			sessionAttrList.add(vo.getATTRIBUTE_NAME());
		    }
		    repSessionCO.setSessionAttrList(hm);
		}
	    catch(BaseException e)
	    {
		handleException(e, "Error returning session attributes list", "returnSessAttrList.exceptionMsg._key");
	    }
    }

    public String constructQryLookup()
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "QUERY_ID", "QUERY_NAME" };
	    String[] colType = { "number", "text" };
	    String[] titles = { getText("query.id"), getText("query_name_key") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption(getText("queries.list_key"));
	    grid.setRowNum("10");
	    grid.setUrl("/path/designer/queryDesign_fillQryLookup.action?_pageRef=" + get_pageRef());
	    int cols = name.length;
	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
	    
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
	    lookup(grid, lsGridColumn, null, querySC);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error constructing the query lookup");
	    handleException(e, "Error constructing the query lookup", "constructingQryLkp.exceptionMsg._key");
	}

	return SUCCESS;
    }

    public String fillQryLookup() throws JSONException
    {
	try
	{
	    setSearchFilter(querySC);
	    copyproperties(querySC);

	    if(getRecords() == 0)
	    {
		setRecords(queryBO.getQueriesCount(querySC));
	    }

	    List<IRP_AD_HOC_QUERYVO> list = queryBO.getQueriesList(querySC);
	    setGridModel(list);
	}
	catch(Exception e)
	{
	    //log.error(e, "Error filling query lookup");
	    handleException(e, "Error filling query lookup", "fillQryLookup.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String dependencyByQryId() throws JSONException
    {
	try
	{
	    if(NumberUtil.isEmptyDecimal(this.queryIdLkp))
	    {
		this.queryNameLkp = null;
		this.qryColumnsList = new ArrayList<String>();
		this.queryIdLkp = null;
	    }
	    else
	    {
		IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(queryIdLkp, true);
		if(query.getQUERY_OBJECT() == null)
		{
		    BOException e = new BOException("");
		    e.setMsgIdent(ConstantsCommon.QUERY_NO_DEFINED_SYNTAX);
		    throw e;
		}
		this.queryNameLkp = query.getQUERY_NAME();
		// HashMap<String,String> hm =
		// queryBO.returnQryColumns(qrySynt);
		qryColumnsList.add("");

		HashMap<String, String> hm = returnFeAliasAndTypeMap(query);

		for(Entry<String, String> entry : hm.entrySet())
		{
		    qryColumnsList.add(entry.getKey());
		}
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		repSessionCO.setQryColumnsList(hm);
		// loadLinkQryGrid(queryIdLkp);
	    }
	}
	catch(BOException e)
	{
	    if(ConstantsCommon.QUERY_NO_DEFINED_SYNTAX.equals(e.getMsgIdent()))
	    {
		handleException(e, getText("queryIdkey") + " " + queryIdLkp + " " + getText("hasnodefinedsyntaxkey"),null);
		queryIdLkp=null;
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    public String dependencyByQryDfltValId() throws JSONException
    {
	try
	{
	    if(NumberUtil.isEmptyDecimal(this.queryDfltValIdLkp))
	    {
		this.queryNameDftlValLkp = null;
		this.qryColumnsDfltValList = new ArrayList<String>();
		this.queryDfltValIdLkp = null;
	    }
	    else
	    {
		IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(queryDfltValIdLkp, true);
		this.queryNameDftlValLkp = query.getQUERY_NAME();
		// HashMap<String,String> hm =
		// queryBO.returnQryColumns(qrySynt);
		qryColumnsDfltValList.add("");

		HashMap<String, String> hm = returnFeAliasAndTypeMap(query);

		for(Entry<String, String> entry : hm.entrySet())
		{
		    qryColumnsDfltValList.add(entry.getKey());
		}
		ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		repSessionCO.setQryDfltValColumnsList(hm);
		// loadLinkQryGrid(queryIdLkp);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    
    public String dependencyBySessionAttr() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, String> hm = repSessionCO.getSessionAttrList();
	    this.argumentType = hm.get(this.getSessionAttr());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String dependencyByColName() throws JSONException
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    HashMap<String, String> hm = repSessionCO.getQryColumnsList();
	    this.argumentType = hm.get(this.getColumnName());
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public LinkedHashMap<String, String> getReportParam()
    {
	LinkedHashMap<String, String> list = new LinkedHashMap<String, String>();
	if(tab != null && tab.equals("arguments"))
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	    if(repSessionCO.getReportCO() != null)
	    {
		Collection<IRP_REP_ARGUMENTSCO> collection = repSessionCO.getReportCO().getArgumentsList().values();

		list.put("", "");
		for(IRP_REP_ARGUMENTSCO arg : collection)
		{
		    list.put(arg.getARGUMENT_NAME(), arg.getARGUMENT_NAME());
		}
	    }

	}
	return list;
    }

    public LinkedHashMap<Long, String> getReportParameters()
    {
	LinkedHashMap<Long, String> ret = new LinkedHashMap<Long, String>();
	if(tab != null && tab.equals("arguments"))
	{
	    ret = fillParametersList();
	}
	return ret;
    }

    public LinkedHashMap<Long, String> fillParametersList()
    {
	LinkedHashMap<Long, String> list = new LinkedHashMap<Long, String>();
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	if(repSessionCO.getReportCO() != null)
	{
	    Collection<IRP_REP_ARGUMENTSCO> collection = repSessionCO.getReportCO().getArgumentsList().values();

	    Long key = Long.valueOf(0);
	    list.put(key, "");
	    for(IRP_REP_ARGUMENTSCO arg : collection)
	    {
		list.put(arg.getIndex(), arg.getARGUMENT_NAME());
	    }
	}

	return list;
    }

    public String returnRepParamByIdx()
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	parameter = repSessionCO.getReportCO().getArgumentsList().get(paramIdx);
	return SUCCESS;
    }

    public List<String> getQryColumnsList()
    {
	return qryColumnsList;
    }

    public BigDecimal getQueryIdLkp()
    {
	return queryIdLkp;
    }

    public void setQueryIdLkp(BigDecimal queryIdLkp)
    {
	this.queryIdLkp = queryIdLkp;
    }

    public String getQueryNameLkp()
    {
	return queryNameLkp;
    }

    public void setQueryNameLkp(String queryNameLkp)
    {
	this.queryNameLkp = queryNameLkp;
    }

    public String getArgumentType()
    {
	return argumentType;
    }

    public void setArgumentType(String argumentType)
    {
	this.argumentType = argumentType;
    }

    public String getSessionAttr()
    {
	return sessionAttr;
    }

    public void setSessionAttr(String sessionAttr)
    {
	this.sessionAttr = sessionAttr;
    }

    public String getColumnName()
    {
	return columnName;
    }

    public void setColumnName(String columnName)
    {
	this.columnName = columnName;
    }

    public String getArgDefaultVal()
    {
	return null;
    }

    public Long getParamIdx()
    {
	return paramIdx;
    }

    public void setParamIdx(Long paramIdx)
    {
	this.paramIdx = paramIdx;
    }

    public IRP_REP_ARGUMENTSCO getParameter()
    {
	return parameter;
    }

    public HashMap<Long, String> getParametersList()
    {
	return parametersList;
    }

    public void setParametersList(HashMap<Long, String> parametersList)
    {
	this.parametersList = parametersList;
    }

    public List<SYS_PARAM_LOV_TRANSVO> getArgsSourceList()
    {
	List<SYS_PARAM_LOV_TRANSVO> sources = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	try
	{
	    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("1");
	    sysVO.setVALUE_DESC(getText("fixed_key"));
	    sources.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("2");
	    sysVO.setVALUE_DESC(getText("session_key"));
	    sources.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("3");
	    sysVO.setVALUE_DESC(getText("query_key"));
	    sources.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("4");
	    sysVO.setVALUE_DESC(getText("flag_key"));
	    sources.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("5");
	    sysVO.setVALUE_DESC(getText("repRef_key"));
	    sources.add(sysVO);
	    SessionCO sessionCO = returnSessionObject();
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.REP_SCHEDULE_DATE_PARAM);
	    sysVO.setLANG_CODE(sessionCO.getLanguage());
	    ArrayList<SYS_PARAM_LOV_TRANSVO> lstSchedParamType=(ArrayList) commonLookupBO.getLookupList(sysVO);
	    //update ids since the id returned by the function getLookupList contains ids that already used
	    if(!lstSchedParamType.isEmpty())
	    {
		SYS_PARAM_LOV_TRANSVO schedParamVO=lstSchedParamType.get(0);
		schedParamVO.setVALUE_CODE("6");
		sources.add(schedParamVO);
	    }
	   
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.REP_SYSTEM_DATE_PARAM);
	    sysVO.setLANG_CODE(sessionCO.getLanguage());
	    ArrayList<SYS_PARAM_LOV_TRANSVO> lstSysDateParamType=(ArrayList) commonLookupBO.getLookupList(sysVO);
	    //update ids since the id returned by the function getLookupList contains ids that already used
	    if(!lstSysDateParamType.isEmpty())
	    {
		SYS_PARAM_LOV_TRANSVO sysDateParamVO=lstSysDateParamType.get(0);
		sysDateParamVO.setVALUE_CODE("7");
		sources.add(sysDateParamVO);
	    }
	    
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE(ConstantsCommon.TRANS_SESSION_ARG_SOURCE.toString());
	    sysVO.setVALUE_DESC(getText("reporting.transSession_key"));
	    sources.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE(ConstantsCommon.REP_LANG_ARG_SOURCE.toString());
	    sysVO.setVALUE_DESC(getText("reporting.reportLanguage"));
	    sources.add(sysVO);
	    sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setVALUE_CODE("10");
	    sysVO.setVALUE_DESC(getText("db_connection"));
	    sources.add(sysVO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

	return sources;
    }
    public ArrayList<SYS_PARAM_LOV_TRANSVO> getQryOprionsList()
    {
	try
	{
	  SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.ARG_QUERY_OPTIONS_LOV);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    qryOprionsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return qryOprionsList;
    }


    public List<SYS_PARAM_LOV_TRANSVO> getArgsTypeList()
    {
	List<SYS_PARAM_LOV_TRANSVO> types = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
	SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("NUMBER");
	sysVO.setVALUE_DESC(getText("type.number_key"));
	types.add(sysVO);
	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("VARCHAR2");
	sysVO.setVALUE_DESC(getText("type.character_key"));
	types.add(sysVO);
	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE("DATE");
	sysVO.setVALUE_DESC(getText("type.date_key"));
	types.add(sysVO);
	
	sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setVALUE_CODE(ConstantsCommon.datetime);
	sysVO.setVALUE_DESC(getText("type.dateTime_key"));
	types.add(sysVO);

	return types;
    }
    
    public List<SYS_PARAM_LOV_TRANSVO> getArgsDateValueList() throws Exception
    {
	try
	{
	  SessionCO sessionCO = returnSessionObject();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.ARG_DATE_VALUE_LOV);
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    SYS_PARAM_LOV_TRANSVO emptyLine = new SYS_PARAM_LOV_TRANSVO();
	    emptyLine.setVALUE_CODE("");
	    emptyLine.setVALUE_DESC("");
	    argsDateValueList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	    argsDateValueList.add(emptyLine);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return argsDateValueList;
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
		if(name[i].equals("index"))
		{
		    gridColumn.setHidden(true);
		}
		if(name[i].equals("value_date"))
		{
		    gridColumn.setFormatter("date");
		    gridColumn.setEditrules("{date:true}");
		}
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, qryGridSC);
	}
	catch(Exception e)
	{
	    //log.error("Error filling reports lookup");
	    handleException(e, "Error filling reports lookup", "fillReportsLkp.exceptionMsg._key");
	}
	return SUCCESS;
    }

    public String retColumnsByRepId() throws Exception
    {
	try
	{
	    fillLookupCol("/path/designer/queryDesign_fillFieldsGrid", getText("reporting.fildsList"));
	}
	catch(Exception e)
	{
	    log.error("Error in retColumnsByRepId");
	}

	return SUCCESS;
    }

    public String fillLookupCol(String gridUrl, String gridCaption) throws JSONException
    {
	String[] name = { "index", "FIELD_ALIAS" };
	String[] colType = { "number", "text" };
	String[] titles = { getText("reporting.lkpCode"), getText("reporting.fieldName") };
	fillLookup(gridUrl, gridCaption, name, colType, titles);
	return SUCCESS;
    }

    public String fillFieldsGrid() throws Exception
    {
	if (!StringUtil.nullToEmpty(getUpdates()).isEmpty())
	{
	BigDecimal repId = new BigDecimal(getUpdates());
	IRP_AD_HOC_REPORTCO repCO = designerBO.returnReportById(repId);

	copyproperties(qryGridSC);
	setSearchFilter(qryGridSC);
	List<IRP_FIELDSCO> reportList = new ArrayList<IRP_FIELDSCO>(repCO.getQueriesList().get(0).getSqlObject()
		.getFields().values());

	int nbRec = qryGridSC.getNbRec();
	int recToSkip = qryGridSC.getRecToskip();
	int maxRec = nbRec + recToSkip;
	if(reportList.size() < maxRec)
	{
	    maxRec = reportList.size();
	}
	ArrayList<IRP_FIELDSCO> lst = new ArrayList<IRP_FIELDSCO>();
	for(int i = recToSkip; i < maxRec; i++)
	{
	    lst.add(reportList.get(i));
	}

	setRecords(reportList.size());
	setGridModel(lst);
	}
	return SUCCESS;
    }

    public String fillSessionLst() throws Exception
    {
	try
	{
	    fillLookupSession("/path/designer/queryDesign_fillSessionGrid", getText("hyperLink.session"));
	}
	catch(Exception e)
	{
	    log.error("Error in retColumnsByRepId");
	}

	return SUCCESS;
    }

    public String fillLookupSession(String gridUrl, String gridCaption) throws JSONException
    {
	String[] name = { "ATTRIBUTE_NAME", "ATTRIBUTE_TYPE" };
	String[] colType = { "text", "text" };
	String[] titles = { getText("reporting.description"), getText("reporting.type") };
	fillLookup(gridUrl, gridCaption, name, colType, titles);
	return SUCCESS;
    }

    public String fillSessionGrid() throws Exception
    {

	copyproperties(qryGridSC);
	setSearchFilter(qryGridSC);
	IRP_SESSION_ATTRIBUTESVO sessionAttrVO = new IRP_SESSION_ATTRIBUTESVO();
	List<IRP_SESSION_ATTRIBUTESVO> list = commonLibBO.returnSessionAttrList(sessionAttrVO);

	int nbRec = qryGridSC.getNbRec();
	int recToSkip = qryGridSC.getRecToskip();
	int maxRec = nbRec + recToSkip;
	if(list.size() < maxRec)
	{
	    maxRec = list.size();
	}
	ArrayList<IRP_SESSION_ATTRIBUTESVO> lst = new ArrayList<IRP_SESSION_ATTRIBUTESVO>();
	for(int i = recToSkip; i < maxRec; i++)
	{
	    lst.add(list.get(i));
	}

	setRecords(list.size());
	setGridModel(lst);

	return SUCCESS;
    }

    public ReportingSessionCO returnReportingSessionObject()
    {
	return returnReportingSessionObject("RD00R");
    }

    public ReportingSessionCO returnReportingSessionObject(String ref)
    {
	String pageRef=ref;
	if(pageRef == null)
	{
	    pageRef = "RD00R";
	}
	SessionCO sessionCO = (SessionCO) session.get(ConstantsCommon.SESSION_VO_ATTR);
	HashMap<String, ReportingSessionCO> sessionMap = (HashMap<String, ReportingSessionCO>) sessionCO
		.getReportingAppDet();
	if(sessionMap == null)
	{
	    sessionCO.setReportingAppDet(new HashMap<String, ReportingSessionCO>());
	}
	sessionMap = (HashMap<String, ReportingSessionCO>) sessionCO.getReportingAppDet();
	if(sessionMap.get(pageRef) == null)
	{
	    sessionMap.put(pageRef, new ReportingSessionCO());
	}
	return sessionMap.get(pageRef);
    }

    public HashMap<String, String> returnFeAliasAndTypeMap(IRP_AD_HOC_QUERYCO query)
    {
	HashMap<String, String> hm = new HashMap<String, String>();
	LinkedHashMap<Long, IRP_FIELDSCO> fieldsMap;
	for(int i = 0; i < query.getSqlObject().getFields().size(); i++)
	{
	    fieldsMap = query.getSqlObject().getFields();

	    for(Entry<Long, IRP_FIELDSCO> entry : fieldsMap.entrySet())
	    {
		String fType = CommonUtil.returnJavaFieldType(entry.getValue().getFIELD_DATA_TYPE());
		hm.put(entry.getValue().getLabelAlias(), fType);
	    }

	}
	return hm;
    }
    
    public String checkIfArgInSyntax()
    {
	try
	{
	    setUpdates("0");
	    String syntax=getSqbSyntax();
	    //not called from wizard
	    if(syntax==null)
	    {
		String qryType =retQryTypeStr();
		if(ConstantsCommon.SQB_QRY_TYPE.equals(qryType))
		{
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		    IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
		    //called from 'Report Designer - Queries' section
		    if(reportCO==null)
		    {
			syntax=repSessionCO.getSqlObj().getSqbSyntax().toString();
		    }
		    else
		    {
			 syntax=reportCO.getQueriesList().get(0).getSqlSyntax().toString();
		    }
		    if(checkIfArgUsedInSyntax(syntax,getRepArgsName()))
		    {
			setUpdates("1");
		    }
		    
		}
	    }
	    else
	    {
		if(checkIfArgUsedInSyntax(syntax,getRepArgsName()))
		    {
			setUpdates("1");
		    }
	    }
	}
	catch(Exception e)
	{
	    //log.error(e,"");
	    handleException(e, null, null);
	}
	return SUCCESS;
    }
    
    public boolean checkIfArgUsedInSyntax(String syntax,String argName) throws Exception
    {
	boolean found=false;
	if(syntax!=null && (syntax.indexOf("$P{"+argName+"}")>=0)|| syntax.indexOf("$P!{"+argName+"}")>=0)
	{
	    found=true;
	}
	else
	{
	    IRP_REP_ARGUMENTSCO co = new IRP_REP_ARGUMENTSCO();
	    co.setARGUMENT_NAME(argName);
	    String newSyntax = CommonUtil.handleMultiValParam(syntax, co, ConstantsCommon.PARAM_TYPE_NUMBER,0);
	    if(!newSyntax.equals(syntax))
	    {
		found = true;
	    }
	}
	return found;
    }
    
    /**
     * Method that opens the constraint dialog and shows the necessary fields
     * @return
     * @throws Exception
     */
    public String openArgConstraintsDialog() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	BigDecimal source;
	IRP_REP_ARGUMENTSCO repArgCO = (reportCO.getArgumentsList().get(Long.valueOf(updates)));
	//new argument being added
	source = new BigDecimal(updates1) ;
	//checking if it's a new argument
	if(repArgCO==null)
	{
	    argConstrtCO = reportCO.getArgConstraintsMap().get(repArgsName);
	}
	else
	{
	    argConstrtCO = repArgCO.getIrpRepArgConstraintCO();
	}
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	sysVO.setLOV_TYPE_ID(RepConstantsCommon.ARG_CONSTRAINT_LOV);
	sysVO.setLANG_CODE(sessionCO.getLanguage());
	caseSensList = (ArrayList) commonLookupBO.getLookupList(sysVO);
	SYS_PARAM_SCREEN_DISPLAYVO constraintDispHide = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO constraintDispShow = new SYS_PARAM_SCREEN_DISPLAYVO();
	constraintDispHide.setIS_VISIBLE(BigDecimal.ZERO);
	constraintDispShow.setIS_VISIBLE(BigDecimal.ONE);
	if(BigDecimal.ONE.equals(source))
	{
	    if(argumentType.equals(ConstantsCommon.PARAM_TYPE_NUMBER))
	    {
		getAdditionalScreenParams().put("caseSenstDrp", constraintDispHide);
		getAdditionalScreenParams().put("minVal", constraintDispShow);
		getAdditionalScreenParams().put("maxVal", constraintDispShow);
	    }
	    else if(argumentType.equals(ConstantsCommon.PARAM_TYPE_VARCHAR2))
	    {
		getAdditionalScreenParams().put("caseSenstDrp", constraintDispShow);
		getAdditionalScreenParams().put("minVal", constraintDispHide);
		getAdditionalScreenParams().put("maxVal", constraintDispHide);
	    }
	    else if(argumentType.equals(ConstantsCommon.PARAM_TYPE_DATE)
		    || argumentType.equals(RepConstantsCommon.dateTime))
	    {
		getAdditionalScreenParams().put("caseSenstDrp", constraintDispHide);
		getAdditionalScreenParams().put("minVal", constraintDispHide);
		getAdditionalScreenParams().put("maxVal", constraintDispHide);
		getAdditionalScreenParams().put("maxLength", constraintDispHide);
		getAdditionalScreenParams().put("formatConstr", constraintDispHide);
		getAdditionalScreenParams().put("maxVal", constraintDispHide);
	    }
	}
	// source = 3,4 or 6
	else
	{
	    getAdditionalScreenParams().put("caseSenstDrp", constraintDispHide);
	    getAdditionalScreenParams().put("minVal", constraintDispHide);
	    getAdditionalScreenParams().put("maxVal", constraintDispHide);
	    getAdditionalScreenParams().put("maxLength", constraintDispHide);
	    getAdditionalScreenParams().put("formatConstr", constraintDispHide);
	    getAdditionalScreenParams().put("maxVal", constraintDispHide);
	}
	return "constraintsArgs";
    }
    
    /**
     * Method that retrieves the data to be showed in the autocomplete (txtarea)
     * @return
     * @throws Exception
     */
    public String retTextAreaData() throws Exception
    {
	StringBuffer comparison = new StringBuffer("");
	StringBuffer showHide = new StringBuffer("");
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	// < <= > >=
	sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(7));
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	ArrayList<SYS_PARAM_LOV_TRANSVO> operatorsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	for(int i = 0; i < operatorsList.size(); i++)
	{
	    comparison.append(operatorsList.get(i).getVALUE_DESC() + ",");
	    showHide.append(operatorsList.get(i).getVALUE_DESC() + ",");
	}
	sysParamLovVO.setLOV_TYPE_ID(RepConstantsCommon.SNAPSHOTS_FORMULA_PB_LOV_TYPE);
	operatorsList = (ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	for(int i = 0; i < operatorsList.size(); i++)
	{
	    comparison.append(operatorsList.get(i).getVALUE_DESC() + ",");
	    showHide.append(operatorsList.get(i).getVALUE_DESC() + ",");
	}
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	List<IRP_REP_ARGUMENTSCO> args = new ArrayList<IRP_REP_ARGUMENTSCO>(reportCO.getArgumentsList().values());
	// IRP_REP_ARGUMENTSCO repArgCO = (reportCO.getArgumentsList().get(new
	// Long(updates)));
	for(int i = 0; i < args.size(); i++)
	{
	    showHide.append("[" + args.get(i).getARGUMENT_NAME() + "],");
	    // checking if the arguments of same type in order to added the
	    // label for the comparison txtarea
	    if(argumentType.equals(args.get(i).getARGUMENT_TYPE()))
	    {
		comparison.append("[" + args.get(i).getARGUMENT_NAME() + "],");
	    }
	}
	update1 = showHide.toString();
	// checking added for case where creating a new argument in order to
	// have its label in the autocomplete
	if(comparison.indexOf(repArgsName) == -1)
	{
	    comparison.append("[" + repArgsName + "],");
	}
	updates1 = comparison.toString();
	return SUCCESS;
    }
    
    /**
     * Method that saves the argument constraints 
     * @return
     * @throws Exception
     */
    public String saveArgConstraints() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	IRP_REP_ARGUMENTSCO repArgCO = (reportCO.getArgumentsList().get(Long.valueOf(updates)));
	// repArgCO null=> new argument that hasn't been added yet.put the new
	// constrCO in reportCO dedicated map
	// and later will be added to the new argument object
	if(repArgCO == null)
	{
	    if(executeCompShowHide(argConstrtCO, reportCO.getArgumentsList(),updates1))
	    {
		IRP_REP_ARG_CONSTRAINTSCO repArgConstraintsCO = new IRP_REP_ARG_CONSTRAINTSCO();
		repArgConstraintsCO.setCASE_SENSITIVITY(argConstrtCO.getCASE_SENSITIVITY());
		repArgConstraintsCO.setCONDITION(argConstrtCO.getCONDITION());
		repArgConstraintsCO.setFORMAT(argConstrtCO.getFORMAT());
		repArgConstraintsCO.setHIDE_EXPR(argConstrtCO.getHIDE_EXPR());
		repArgConstraintsCO.setMAX_LENGTH(argConstrtCO.getMAX_LENGTH());
		repArgConstraintsCO.setMAX_VAL(argConstrtCO.getMAX_VAL());
		repArgConstraintsCO.setMIN_VAL(argConstrtCO.getMIN_VAL());
		repArgConstraintsCO.setSHOW_EXPR(argConstrtCO.getSHOW_EXPR());
		repArgConstraintsCO.setBTR_CONTROL_DISP(argConstrtCO.getBTR_CONTROL_DISP());
		// Before put initialize the map because it will always only
		// contain one entry temporarily until the user presses the +
		//button of the grid and adds the arg + its constraints
		reportCO.setArgConstraintsMap(new LinkedHashMap<String, IRP_REP_ARG_CONSTRAINTSCO>());
		reportCO.getArgConstraintsMap().put(updates1, repArgConstraintsCO);
	    }
	    else
	    {
		return SUCCESS;
	    }
	}
	else
	{
	    if(executeCompShowHide(argConstrtCO, reportCO.getArgumentsList(),repArgCO.getARGUMENT_NAME()))
	    {
		IRP_REP_ARG_CONSTRAINTSCO lCO = repArgCO.getIrpRepArgConstraintCO();
		lCO.setCASE_SENSITIVITY(argConstrtCO.getCASE_SENSITIVITY());
		lCO.setCONDITION(argConstrtCO.getCONDITION());
		lCO.setFORMAT(argConstrtCO.getFORMAT());
		lCO.setHIDE_EXPR(argConstrtCO.getHIDE_EXPR());
		lCO.setMAX_LENGTH(argConstrtCO.getMAX_LENGTH());
		lCO.setMAX_VAL(argConstrtCO.getMAX_VAL());
		lCO.setMIN_VAL(argConstrtCO.getMIN_VAL());
		lCO.setSHOW_EXPR(argConstrtCO.getSHOW_EXPR());
		lCO.setBTR_CONTROL_DISP(argConstrtCO.getBTR_CONTROL_DISP());
	    }
	    else
	    {
		return SUCCESS;
	    }
	}
	return SUCCESS;
    }
    /**
     * Method that replaces old argument name with the newer one on change of the argument name
     * @return
     * @throws Exception
     */
    public String adjustArgMap() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	IRP_REP_ARG_CONSTRAINTSCO constrCO = reportCO.getArgConstraintsMap().get(oldArgName);
	// if there was already constraint in session
	if(constrCO != null)
	{
	    reportCO.getArgConstraintsMap().remove(oldArgName);
	    reportCO.getArgConstraintsMap().put(repArgsName, constrCO);
	}
	return SUCCESS;
    }

    /**
     * Method that executes the comparison,show,hide expressions and return boolean
     * @param constrCO
     * @param mapArgs
     * @return
     * @throws Exception
     */
    public boolean executeCompShowHide(IRP_REP_ARG_CONSTRAINTSCO constrCO,
	    LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> mapArgs,String argumentName) throws Exception
    {
	Map<String, Object> row;
	List<Map<String, Object>> rowData = new ArrayList<Map<String, Object>>();
	row = new LinkedHashMap<String, Object>();
	row.put("", "");
	String conditionExpr = constrCO.getCONDITION();
	String showExpr = constrCO.getSHOW_EXPR();
	String hideExpr = constrCO.getHIDE_EXPR();
	IRP_REP_ARGUMENTSCO argCO;
	String argName;
	String argType;
	String value = "";
	StringBuffer sb = new StringBuffer();
	if(constrCO.getCONDITION().indexOf(argumentName) == -1 && constrCO.getCONDITION().trim().length() > 0)
	{
	    sb.append(getText("reporting.constraintOnce") + ConstantsCommon.NEW_LINE);
	}
	for(Entry<Long, IRP_REP_ARGUMENTSCO> entry : mapArgs.entrySet())
	{
	    argCO = entry.getValue();
	    argName = argCO.getARGUMENT_NAME();
	    argType = argCO.getARGUMENT_TYPE();
	    if(argType.equals(ConstantsCommon.PARAM_TYPE_NUMBER))
	    {
		value = "1";
	    }
	    else if(argType.equals(ConstantsCommon.PARAM_TYPE_VARCHAR2))
	    {
		value = "a";
	    }
	    else if(argType.equals(ConstantsCommon.PARAM_TYPE_DATE))
	    {
		value = "2006-04-23";
	    }
	    conditionExpr = conditionExpr.replace("[" + argName + "]", value);
	    showExpr = showExpr.replace("[" + argName + "]", value);
	    hideExpr = hideExpr.replace("[" + argName + "]", value);
	}
	rowData.add(row);
	try
	{
	    returnCommonLibBO().executeExpression(conditionExpr, 0, rowData);
	}
	catch(Exception e)
	{
	    sb.append(getText("reporting.conditionUnverified") + ConstantsCommon.NEW_LINE);
	}
	try
	{
	    returnCommonLibBO().executeExpression(showExpr, 0, rowData);
	}
	catch(Exception e)
	{
	    sb.append(getText("reporting.showUnverified") + ConstantsCommon.NEW_LINE);
	}
	try
	{
	    returnCommonLibBO().executeExpression(hideExpr, 0, rowData);
	}
	catch(Exception e)
	{
	    sb.append(getText("reporting.hideUnverified") + ConstantsCommon.NEW_LINE);
	}
	if(sb.length() > 0)
	{
	    updates1 = sb.toString();
	    return false;
	}
	return true;
    }
    
    /**
     * Method that empties the argument constraint related data
     * @return
     * @throws Exception
     */
    public String emptyArgConstraintData() throws Exception
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
	IRP_AD_HOC_REPORTCO reportCO = repSessionCO.getReportCO();
	IRP_REP_ARGUMENTSCO repArgCO = (reportCO.getArgumentsList().get(Long.valueOf(updates)));
	if(repArgCO==null)
	{
	    reportCO.getArgConstraintsMap().remove(updates1);
	}
	else
	{
	    repArgCO.setIrpRepArgConstraintCO(new IRP_REP_ARG_CONSTRAINTSCO());	   
	}
	return SUCCESS;
    }

    public void setColumnDesc(String columnDesc)
    {
	this.columnDesc = columnDesc;
    }

    public String getColumnDesc()
    {
	return columnDesc;
    }

    public void setDefaultValueColDesc(String defaultValueColDesc)
    {
	this.defaultValueColDesc = defaultValueColDesc;
    }

    public String getDefaultValueColDesc()
    {
	return defaultValueColDesc;
    }

}
