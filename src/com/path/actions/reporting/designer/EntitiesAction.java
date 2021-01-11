package com.path.actions.reporting.designer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.designer.EntitiesBO;
import com.path.dbmaps.vo.IRP_ENTITIESVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.GridUpdates;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.struts2.lib.common.base.PathActionContextMethods;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.designer.IRP_ENTITIESSC;
import com.path.vo.reporting.designer.IRP_TABLE_VIEWCO;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class EntitiesAction extends ReportingLookupBaseAction
{
    private IRP_ENTITIESVO entitiesVO;
    private IRP_FIELDSCO entFieldsVO;
    private EntitiesBO entitiesBO;
    private IRP_ENTITIESSC entitiesSC = new IRP_ENTITIESSC();
    private String entityDBName;
    private String entityAlias;
    private String updates;
    private String updates1;

    private LinkedHashMap allFields = new LinkedHashMap();
    private LinkedHashMap addedFields = new LinkedHashMap();
    private HashMap deletedFields = new HashMap();
    private HashMap updatedFields = new HashMap();

    public String getEntityDBName()
    {
	return entityDBName;
    }

    public void setEntityDBName(String entityDBName)
    {
	this.entityDBName = entityDBName;
    }

    public String getEntityAlias()
    {
	return entityAlias;
    }

    public void setEntityAlias(String entityAlias)
    {
	this.entityAlias = entityAlias;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public IRP_ENTITIESVO getEntitiesVO()
    {
	return entitiesVO;
    }

    public void setEntitiesVO(IRP_ENTITIESVO entitiesVO)
    {
	this.entitiesVO = entitiesVO;
    }

    public IRP_FIELDSCO getEntFieldsVO()
    {
	return entFieldsVO;
    }

    public void setEntFieldsVO(IRP_FIELDSCO entFieldsVO)
    {
	this.entFieldsVO = entFieldsVO;
    }

    public void setEntitiesBO(EntitiesBO entitiesBO)
    {
	this.entitiesBO = entitiesBO;
    }

    public String getUpdates1()
    {
	return updates1;
    }

    public void setUpdates1(String updates1)
    {
	this.updates1 = updates1;
    }

    public Object getModel()
    {
	return entitiesSC;
    }

    public String loadFieldsList()
    {
	try
	{
	    // entitiesSC.setSearchType("F");
	    // copy the details related to search criteria to the SC
	    String entityName = getEntityDBName();
	    if(entityName == null || entityName.equals(""))
	    {
		setRecords(0);
		setGridModel(null);
	    }
	    else
	    {
		entitiesSC.setENTITY_DB_NAME(entityName);
		copyproperties(entitiesSC);

		List<IRP_FIELDSCO> lstEntFields;

		int fieldsCount;
		ReportingSessionCO repSessionCO = returnReportingSessionObject();
		IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();

		String upd = getUpdates();
		if(upd != null && upd.equals("-1"))
		{
		    // delFieldsList = null;
		    lstEntFields = entitiesBO.loadFieldsList(entitiesSC);
		    fieldsCount = entitiesBO.retFieldsListCount(entitiesSC);

		    for(IRP_FIELDSCO fieldCO : lstEntFields)
		    {
			allFields.put(fieldCO.getFIELD_DB_NAME(), fieldCO);
		    }
		    entityFields.setAllFields(allFields);
		    entityFields.setAddedFields(new LinkedHashMap());
		    entityFields.setUpdatedFields(new HashMap());
		    entityFields.setDeletedFields(new HashMap());

		}
		else
		{

		    allFields = entityFields.getAllFields();
		    addedFields = entityFields.getAddedFields();

		    if(allFields == null)
		    {
			allFields = new LinkedHashMap();
		    }
		    if(addedFields == null)
		    {
			addedFields = new LinkedHashMap();
		    }

		    // Converting from LinkedHashMap to ArrayList so that it is
		    // used in the setGridModel()
		    LinkedHashMap finalMap = new LinkedHashMap();
		    finalMap.putAll(allFields);
		    finalMap.putAll(addedFields);
		    lstEntFields = new ArrayList(finalMap.values());
		    fieldsCount = lstEntFields.size();

		}

		int nbRec = entitiesSC.getNbRec();
		int recToSkip = entitiesSC.getRecToskip();
		int maxRec = nbRec + recToSkip;
		if(fieldsCount < maxRec)
		{
		    maxRec = fieldsCount;
		}
		ArrayList<IRP_FIELDSCO> lst = new ArrayList<IRP_FIELDSCO>();

		for(int i = recToSkip; i < maxRec; i++)
		{
		    lst.add((IRP_FIELDSCO) lstEntFields.toArray()[i]);
		}

		setRecords(fieldsCount);
		setGridModel(lst);

	    }

	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in loadFieldsList()");
	}

	return SUCCESS;
    }

    public String deleteEntity() throws Exception
    {
	try
	{
	    String entDBName = getEntityDBName();
	    IRP_ENTITIESSC entSC = new IRP_ENTITIESSC();
	    entSC.setENTITY_DB_NAME(entDBName);
	    entSC.setDelFieldsList(null);

	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setOperationType(AuditConstant.DELETE);
	    refCO.setKeyRef(AuditConstant.ENTITIES_KEY_REF);
	    entitiesBO.deleteEntity(entSC, refCO);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	return SUCCESS;
    }

    public String loadEntitiesList()
    {
	try
	{
	    // entitiesSC.setSearchType("E");
	    copyproperties(entitiesSC);
	    List lstEntities = entitiesBO.loadEntitiesList(entitiesSC);

	    int entitiesCount = entitiesBO.retEntitiesListCount(entitiesSC);

	    setRecords(entitiesCount);
	    setGridModel(lstEntities);
	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in loadEntitiesList()");
	}

	return SUCCESS;
    }

    public String constructEntLkp()
    {

	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "OBJECT_DB_NAME", "OBJECT_TYPE" };
	    String[] colType = { "text", "text" };
	    String[] titles = { getText("entities.tableViewName"), getText("entities.tableViewType") };

	    fillLookup(name, colType, titles, "entitiesGridList", "/path/entitiesMaint/entities_fillEntLkp",
		    getText("entities.entList"));
	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in constructEntLkp()");
	}
	return SUCCESS;

    }

    public String fillLookup(String[] colNames, String[] colTypes, String[] colTitles, String gridId, String gridUrl,
	    String gridCaption)
    {
	try
	{
	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    List<LookupGridColumn> lsGridColumn = new ArrayList<LookupGridColumn>();
	    grid.setId(gridId);
	    grid.setCaption(gridCaption);
	    grid.setRowNum("10");
	    grid.setUrl(gridUrl);
	    int cols = colNames.length;

	    for(int i = 0; i < cols; i++)
	    {
		// Defining each column
		LookupGridColumn gridColumn = new LookupGridColumn();
		gridColumn.setName(colNames[i]);
		gridColumn.setIndex(colNames[i]);
		gridColumn.setColType(colTypes[i]);
		gridColumn.setTitle(colTitles[i]);
		gridColumn.setSearch(true);
		// adding column to the list
		lsGridColumn.add(gridColumn);
	    }
	    lookup(grid, lsGridColumn, null, entitiesSC);
	    // lookup(grid, lsGridColumn, null, commonDetailsSC);

	}
	catch(Exception e)
	{
	    log.error(e, "Error in constructEntLkp of EntitiesAction");
	}
	return SUCCESS;
    }

    public String fillEntLkp() throws JSONException
    {
	try
	{
	    entitiesSC = new IRP_ENTITIESSC("T");
	    setSearchFilter(entitiesSC);

	    copyproperties(entitiesSC);

	    entitiesSC.setGrid(true);

	    if(getRecords() == 0)
	    {
		int tableViewCount = entitiesBO.retTableViewListCount(entitiesSC);
		setRecords(tableViewCount);
	    }
	    List<IRP_TABLE_VIEWCO> tableViewList = entitiesBO.loadTableViewList(entitiesSC);
	    // set the collection into gridModel attribute defined at JSP grid
	    setGridModel(tableViewList);

	}
	catch(Exception e)
	{
	    //log.error(e, "********* error in constructEntLkp of EntitiesAction (fillEntLkp )****");
	    handleException(e,null,null);
	}

	return SUCCESS;
    }

    public String openSelFieldsDialog()
    {
	return "successFields";
    }

    public String loadFieldLkpList()
    {

	try
	{
	    entitiesSC.setSearchType("FL");
	    copyproperties(entitiesSC);
	    entitiesSC.setENTITY_DB_NAME(getEntityDBName());

	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();

	    addedFields = entityFields.getAddedFields();

	    // entitiesSC.setAddedFields(addedFields);
	    // if (addedFields!=null){
	    // entitiesSC.setAddFieldsListCount(addedFields.size());
	    // }
	    // else{
	    // entitiesSC.setAddFieldsListCount(0);
	    // }

	    List<String> addedListStr = new ArrayList<String>();
	    Iterator it = addedFields.values().iterator();
	    IRP_FIELDSCO fe;
	    while(it.hasNext())
	    {
		fe = (IRP_FIELDSCO) it.next();
		addedListStr.add(fe.getFIELD_DB_NAME());
	    }

	    if(addedListStr.isEmpty())
	    {
		entitiesSC.setAddedFieldsListStr(null);
	    }
	    else
	    {
		entitiesSC.setAddedFieldsListStr(addedListStr);
	    }

	    List<String> delFeListStr = new ArrayList<String>();
	    HashMap delFeMap = entityFields.getDeletedFields();
	    it = delFeMap.values().iterator();
	    while(it.hasNext())
	    {
		fe = (IRP_FIELDSCO) it.next();
		if(addedFields.get(fe.getFIELD_DB_NAME()) == null)
		{
		    delFeListStr.add(fe.getFIELD_DB_NAME());
		}
	    }
	    if(delFeListStr.isEmpty())
	    {
		entitiesSC.setDelFieldsList(null);
	    }
	    else
	    {
		entitiesSC.setDelFieldsList(delFeListStr);
	    }

	    // check if a fe exists in delete and add at the same time , then
	    // remove it from delete

	    List fieldLkpList = entitiesBO.loadFieldLkpList(entitiesSC);
	    int fieldsCount = entitiesBO.retFieldLkpListCount(entitiesSC);
	    
	    //add flipping
	    int nbRec = entitiesSC.getNbRec();
	    int recToSkip = entitiesSC.getRecToskip();
	    int maxRec = nbRec + recToSkip;
	    if(fieldLkpList.size() < maxRec)
	    {
		maxRec = fieldLkpList.size();
	    }
	    ArrayList<IRP_ENTITIESSC> lst = new ArrayList<IRP_ENTITIESSC>();
	    for(int i = recToSkip; i < maxRec; i++)
	    {
		lst.add((IRP_ENTITIESSC)fieldLkpList.get(i));
	    }

	    setRecords(fieldsCount);
	    setGridModel(lst);

	}
	catch(BaseException e)
	{
	    log.error(e, "__________ error in loadFieldLkpList()");
	}
	return SUCCESS;
    }

    public String selectEntity() throws JSONException
    {
	try
	{
	    JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(updates);
	    JSONObject jsonModel = jsonFilter.getJSONObject("entitiesVO");
	    entitiesVO = (IRP_ENTITIESVO) JSONObject.toBean(jsonModel, IRP_ENTITIESVO.class);

	    // empty old fieldsMap
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();
	    if(entityFields != null)
	    {
		entityFields.setOldFieldsMap(new HashMap<String, IRP_FIELDSCO>());
	    }

	    // get row data from DB with date_updated
	    entitiesVO = entitiesBO.retrieveEntity(entitiesVO);

	    applyRetrieveAudit(AuditConstant.ENTITIES_KEY_REF, entitiesVO, entitiesVO);

	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in selectEntity()");
	}
	return "successMnt";
    }

    public String validateEntFields()
    {
	try
	{
	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();

	    allFields = entityFields.getAllFields();
	    addedFields = entityFields.getAddedFields();
	    updatedFields = entityFields.getUpdatedFields();
	    deletedFields = entityFields.getDeletedFields();

	    LinkedHashMap<String, IRP_FIELDSCO> finalMap = new LinkedHashMap<String, IRP_FIELDSCO>();
	    finalMap.putAll(allFields);
	    finalMap.putAll(addedFields);

	    int fieldsCount = 0;

	    if(finalMap != null)
	    {
		fieldsCount = finalMap.size();
	    }

	    if(fieldsCount != 0)
	    {
		String columnVal;
		boolean missingCol = false;

		for(IRP_FIELDSCO fieldsCO : finalMap.values())
		{
		    columnVal = fieldsCO.getFIELD_DB_NAME();
		    if(columnVal == null || columnVal.equals(""))
		    {
			missingCol = true;
			break;
		    }
		    columnVal = fieldsCO.getFIELD_ALIAS();
		    if(columnVal == null || columnVal.equals(""))
		    {
			missingCol = true;
			break;
		    }
		}

		if(missingCol)
		{
		    setUpdates("-1");
		    return SUCCESS;
		}
	    }

	    entityFields.setENTITY_DB_NAME(entitiesVO.getENTITY_DB_NAME());
	    entityFields.setENTITY_ALIAS(entitiesVO.getENTITY_ALIAS());

	    String newEntity = getUpdates();

	    // apply audit
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.ENTITIES_KEY_REF);

	    if(newEntity != null && newEntity.equals("1"))
	    {
		entityFields.setNewEntity(true);

	    }
	    else
	    {
		entityFields.setNewEntity(false);
		refCO.setAuditObj(returnAuditObject(IRP_ENTITIESVO.class));
	    }

	    // Sending the object to BO where the business logic should be
	    // handled
	    entitiesBO.saveEntFields(entityFields, refCO);

	}
	catch(Exception e)
	{
	    if(e.getMessage() == null || e.getMessage().indexOf("2099") == -1)
	    {
		log.error(e, "__________ error in validateEntFields()");
	    }
	    else
	    {
		handleException(e, null, null);
	    }
	}
	return SUCCESS;
    }

    public String deleteFields()
    {
	try
	{

	    String[] delFieldsList = (String[]) PathActionContextMethods.returnParameters().get("delFieldsList");

	    List<IRP_FIELDSCO> delList = new ArrayList<IRP_FIELDSCO>();

	    for(int i = 0; i < delFieldsList.length; i++)
	    {
		IRP_FIELDSCO delElement = new IRP_FIELDSCO();
		delElement.setFIELD_DB_NAME(delFieldsList[i]);
		delList.add(delElement);
	    }

	    ReportingSessionCO repSessionCO = returnReportingSessionObject();
	    IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();

	    allFields = entityFields.getAllFields();
	    addedFields = entityFields.getAddedFields();
	    updatedFields = entityFields.getUpdatedFields();
	    deletedFields = entityFields.getDeletedFields();

	    if(allFields == null)
	    {
		allFields = new LinkedHashMap();
	    }
	    if(addedFields == null)
	    {
		addedFields = new LinkedHashMap();
	    }
	    if(deletedFields == null)
	    {
		deletedFields = new HashMap();
	    }
	    String fieldName;
	    for(IRP_FIELDSCO fieldsCO : delList)
	    {
		fieldName = fieldsCO.getFIELD_DB_NAME();
		if(addedFields.containsKey(fieldName))
		{
		    addedFields.remove(fieldName);
		}
		else
		{
		    if(updatedFields.containsKey(fieldsCO.getFIELD_DB_NAME()))
		    {
			updatedFields.remove(fieldName);
		    }
		    allFields.remove(fieldName);
		    deletedFields.put(fieldsCO.getFIELD_DB_NAME(), fieldsCO);
		}
	    }

	    entityFields.setAllFields(allFields);
	    entityFields.setUpdatedFields(updatedFields);
	    entityFields.setAddedFields(addedFields);
	    entityFields.setDeletedFields(deletedFields);

	    // Saving the values into the Session
	    repSessionCO.setEntityFields(entityFields);

	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in deleteFields()");
	}
	return SUCCESS;
    }

    public String loadAllFieldsList()
    {
	try
	{
	    String entityName = getEntityDBName();
	    if(entityName != null && !entityName.equals(""))
	    {
		entitiesSC.setENTITY_DB_NAME(entityName);
		copyproperties(entitiesSC);
		List lstAllFields = entitiesBO.loadAllFieldsList(entitiesSC);
		int allFieldsCount = entitiesBO.retAllFieldsListCount(entitiesSC);

		entitiesSC.setGrid(false);
		List lstAllFe = entitiesBO.loadAllFieldsList(entitiesSC);

		ReportingSessionCO repSessionCO = returnReportingSessionObject();
		IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();
		entityFields.setLoadAll(true);
		entityFields.setAddedFields(new LinkedHashMap<String, IRP_FIELDSCO>());
		entityFields.setUpdatedFields(new HashMap<String, IRP_FIELDSCO>());
		LinkedHashMap addedMap = entityFields.getAddedFields();
		IRP_FIELDSCO feCO;
		for(int i = 0; i < lstAllFe.size(); i++)
		{
		    feCO = (IRP_FIELDSCO) lstAllFe.get(i);
		    addedMap.put(feCO.getFIELD_DB_NAME(), feCO);
		}

		entityFields.getDeletedFields().putAll(entityFields.getAllFields());
		entityFields.setAllFields(new LinkedHashMap<String, IRP_FIELDSCO>());

		setRecords(allFieldsCount);
		setGridModel(lstAllFields);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in loadAllFieldsList()");
	}
	return SUCCESS;
    }

    public String changeFieldList()
    {
	try
	{

	    if(updates != null && !updates.equals(""))
	    {
		GridUpdates gu = getGridUpdates(updates, IRP_FIELDSCO.class);
		ArrayList lstAdd = gu.getLstAdd();
		ArrayList lstMod = gu.getLstModify();
		if(!lstAdd.isEmpty() || !lstMod.isEmpty())
		{
		    ReportingSessionCO repSessionCO = returnReportingSessionObject();
		    IRP_ENTITIESSC entityFields = repSessionCO.getEntityFields();

		    HashMap<String, IRP_FIELDSCO> oldFieldsMap = entityFields.getOldFieldsMap();
		    HashMap<String, IRP_FIELDSCO> oldAllFields = new HashMap<String, IRP_FIELDSCO>();
		    Iterator it = entityFields.getAllFields().keySet().iterator();
		    String feName;
		    IRP_FIELDSCO feCOO;
		    while(it.hasNext())
		    {
			feName = (String) it.next();
			feCOO = (IRP_FIELDSCO) entityFields.getAllFields().get(feName);
			oldAllFields.put(feName, feCOO);
		    }

		    allFields = entityFields.getAllFields();
		    if(allFields == null)
		    {
			allFields = new LinkedHashMap();
		    }
		    addedFields = entityFields.getAddedFields();
		    if(addedFields == null)
		    {
			addedFields = new LinkedHashMap();
		    }
		    updatedFields = entityFields.getUpdatedFields();
		    if(updatedFields == null)
		    {
			updatedFields = new HashMap();
		    }
		    String fieldName;

		    if(entityFields.isLoadAll())
		    {
			entityFields.setLoadAll(false);
			for(Object o : lstMod)
			{
			    IRP_FIELDSCO fieldCO = (IRP_FIELDSCO) o;
			    addedFields.put(fieldCO.getFIELD_DB_NAME(), fieldCO);
			}
			updatedFields = new LinkedHashMap<String, IRP_FIELDSCO>();
			allFields = new LinkedHashMap<String, IRP_FIELDSCO>();
		    }

		    for(Object o : lstMod)
		    {
			IRP_FIELDSCO fieldCO = (IRP_FIELDSCO) o;
			fieldName = fieldCO.getFIELD_DB_NAME();
			if(allFields.containsKey(fieldName))
			{
			    allFields.put(fieldName, fieldCO);
			    updatedFields.put(fieldName, fieldCO);
			    // fill the oldFieldsMap in order to use it in audit
			    if(oldFieldsMap.get(fieldName) == null)
			    {
				oldFieldsMap.put(fieldName, oldAllFields.get(fieldName));
			    }
			}
			else
			{
			    fieldCO.setOldField("1");
			    addedFields.put(fieldName, fieldCO);
			}
		    }
		    for(Object o : lstAdd)
		    {
			IRP_FIELDSCO fieldCO = (IRP_FIELDSCO) o;
			fieldName = fieldCO.getFIELD_DB_NAME();
			if(fieldName != null && !fieldName.equals(""))
			{
			    fieldCO.setOldField("1");
			    addedFields.put(fieldCO.getFIELD_DB_NAME(), fieldCO);
			}
		    }
		    entityFields.setAddedFields(addedFields);
		    entityFields.setUpdatedFields(updatedFields);
		    entityFields.setAllFields(allFields);

		    repSessionCO.setEntityFields(entityFields);
		}
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in changeFieldList()");
	}
	return SUCCESS;
    }

    public String execute() throws Exception
    {
	set_showSmartInfoBtn("false");
	return SUCCESS;
    }
}
