package com.path.actions.reporting.ftr.template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.vo.LookupGrid;
import com.path.lib.vo.LookupGridColumn;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.template.FTR_MISMATCH_REPORTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public class TemplateMismatchAction extends ReportingLookupBaseAction implements ServletRequestAware,
	ServletResponseAware
{
    private FTR_MISMATCH_REPORTCO ftrMissRepCO = new FTR_MISMATCH_REPORTCO();
    private TemplateBO templateBO;
    private String tempCodeWithLineNb;
    private String templateCode;
    private GLSTMPLTVO glstmpltVO = new GLSTMPLTVO();
    protected HttpServletRequest request;
    private String lineNbr;
    private final TmplGridSC tmplGridSC = new TmplGridSC();
    private String currentTemplate;

    public String getCurrentTemplate()
    {
	return currentTemplate;
    }

    public void setCurrentTemplate(String currentTemplate)
    {
	this.currentTemplate = currentTemplate;
    }

    public TmplGridSC getTmplGridSC()
    {
	return tmplGridSC;
    }

    public String getLineNbr()
    {
	return lineNbr;
    }

    public void setLineNbr(String lineNbr)
    {
	this.lineNbr = lineNbr;
    }

    public GLSTMPLTVO getGlstmpltVO()
    {
	return glstmpltVO;
    }

    public void setGlstmpltVO(GLSTMPLTVO glstmpltVO)
    {
	this.glstmpltVO = glstmpltVO;
    }

    public String getTemplateCode()
    {
	return templateCode;
    }

    public void setTemplateCode(String templateCode)
    {
	this.templateCode = templateCode;
    }

    public String getTempCodeWithLineNb()
    {
	return tempCodeWithLineNb;
    }

    public void setTempCodeWithLineNb(String tempCodeWithLineNb)
    {
	this.tempCodeWithLineNb = tempCodeWithLineNb;
    }

    public FTR_MISMATCH_REPORTCO getFtrMissRepCO()
    {
	return ftrMissRepCO;
    }

    public void setFtrMissRepCO(FTR_MISMATCH_REPORTCO ftrMissRepCO)
    {
	this.ftrMissRepCO = ftrMissRepCO;
    }

    public void setTemplateBO(TemplateBO templateBO)
    {
	this.templateBO = templateBO;
    }

    public String openMismatch() throws JSONException
    {
	return SUCCESS;
    }

    /**
     * method that loads the mismatches grid
     * 
     * @return
     * @throws JSONException
     */
    public String loadMismatchGrid() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    String tempCodeWithLineNb = getTempCodeWithLineNb();
	    String zTemplCode = null;
	    String zLineNb = null;
	    if(tempCodeWithLineNb != null && !tempCodeWithLineNb.equals(""))
	    {
		zTemplCode = tempCodeWithLineNb.split("~")[0];
		zLineNb = tempCodeWithLineNb.split("~")[1];
	    }

	    if(zTemplCode != null && zLineNb != null)
	    {
		BigDecimal compCode = sessionCO.getCompanyCode();
		String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();
		// get the glstmpltCO in order to fill it with dbGL ;
		// first check in the dbLinesList;
		GLSTMPLTCO glstmpltCOO = getglstmpltCOById(lineConcatKey);
		if(glstmpltCOO != null)
		{
		    LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> dbMismatchMap = glstmpltCOO.getDbMismatchMap();
		    LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> addMismatchMap = glstmpltCOO.getAddMismatchMap();
		    LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> delMismatchMap = glstmpltCOO.getDelMismatchMap();
		    LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> finalMap = new LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO>();
		    //if false=> line is a new line from reorganize and should not be checked in db
		    //because it will retrieve criteria related to another line
		    ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
		    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
		    boolean flag=false;
		    if(allTempl.getNewLinesFromReorganize().get(glstmpltCOO.getNewLineNumber()) == null)
		    {
			flag = true;
		    }	    
		    
		    // get the data from the DB
		    /*
		     * it means when the dbLinesMap is empty and no empty
		     * because all the records are deleted
		     */
		    if((dbMismatchMap == null || (dbMismatchMap.isEmpty() && delMismatchMap.isEmpty())) && flag)

		    {
			TmplGridSC sc = new TmplGridSC();
			sc.setCOMP_CODE(compCode);
			sc.setCODE(new BigDecimal(zTemplCode));
			sc.setLINE_NBR(new BigDecimal(zLineNb));
			sc.setLANG_CODE(sessionCO.getLanguage());

			// get the db gls as list then put it in the
			// linkedHashMap
			List<FTR_MISMATCH_REPORTCO> misList = templateBO.getMismatchsList(sc);
			for(int i = 0; i < misList.size(); i++)
			{
			    FTR_MISMATCH_REPORTCO ftrMissRepCO = misList.get(i);
			    dbMismatchMap.put(ftrMissRepCO.getConcatKey(), ftrMissRepCO);
			}
			glstmpltCOO.setDbMismatchMap(dbMismatchMap);

		    }
		    finalMap.putAll(dbMismatchMap);
		    finalMap.putAll(addMismatchMap);
		    List<FTR_MISMATCH_REPORTCO> misList = new ArrayList<FTR_MISMATCH_REPORTCO>(finalMap.values());
		    setRecords(misList.size());
		    setGridModel(misList);
		}
	    }

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that gets a template by its id
     * 
     * @param lineConcatKey
     * @return
     */
    public GLSTMPLTCO getglstmpltCOById(String lineConcatKey)
    {
	ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	HashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	GLSTMPLTCO glstmpltCO = dbLinesMap.get(new BigDecimal(lineConcatKey));
	if(glstmpltCO == null)
	{
	    HashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
	    glstmpltCO = addLinesMap.get(new BigDecimal(lineConcatKey));
	}
	return glstmpltCO;
    }

    /**
     * method called to check for the existance of a template
     * 
     * @return
     * @throws JSONException
     */
    public String existingTemplatesLookup() throws JSONException
    {
	try
	{
	    // Design the Grid by defining the column model and column names
	    String[] name = { "glstmpltVO.CODE", "glstmpltVO.BRIEF_NAME_ENG" };
	    String[] colType = { "number", "text" };
	    String[] titles = { getText("reporting.code"), getText("template.relatedReports.name") };

	    // Defining the Grid
	    LookupGrid grid = new LookupGrid();
	    grid.setCaption("TEMPLATES");
	    grid.setRowNum("10");
	    grid.setUrl("/path/templateMaintReport/loadTemplatesLookupGrid");

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
	    lookup(grid, lsGridColumn, null, tmplGridSC);
	}
	catch(Exception e)
	{
	    log.error(e, "------------error in tempaltes lookup-----------");
	}
	return "success";
    }

    /**
     * method called on the dependency of the mismatch of a template
     * 
     * @return
     * @throws JSONException
     */
    public String applyExistingTemplatesDependency() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    if(templateCode == null || new BigDecimal(templateCode).intValue() == 0)
	    {
		glstmpltVO.setCODE(null);
		glstmpltVO.setBRIEF_NAME_ENG(null);
	    }
	    else
	    {
		if(currentTemplate.equals(templateCode))
		{
		    glstmpltVO = new GLSTMPLTVO();
		    glstmpltVO.setCODE(null);
		    glstmpltVO.setBRIEF_NAME_ENG(null);
		    return SUCCESS;
		}
		TmplGridSC sc = new TmplGridSC();
		sc.setCODE(new BigDecimal(templateCode));
		sc.setCOMP_CODE(sessionCO.getCompanyCode());
		String templateName = templateBO.getTemplateNameDependency(sc);
		if(templateName == null)
		{
		    glstmpltVO = new GLSTMPLTVO();
		    glstmpltVO.setCODE(null);
		    glstmpltVO.setBRIEF_NAME_ENG(null);
		}
		else
		{
		    glstmpltVO.setCODE(new BigDecimal(templateCode));
		    glstmpltVO.setBRIEF_NAME_ENG(templateName);
		}

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }




    /**
     * method called on the dependency of template lines
     * 
     * @return
     * @throws JSONException
     */
    public String applyExistingTemplateLinesDependency() throws JSONException
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    if(templateCode == null || new BigDecimal(templateCode).intValue() == 0)
	    {
		glstmpltVO.setCODE(null);
		glstmpltVO.setBRIEF_NAME_ENG(null);
	    }
	    else
	    {
		TmplGridSC sc = new TmplGridSC();
		sc.setCODE(new BigDecimal(templateCode));
		sc.setLINE_NBR(new BigDecimal(lineNbr));
		sc.setCOMP_CODE(sessionCO.getCompanyCode());
		String lineName = templateBO.getLineNameDependency(sc);
		if(lineName == null)
		{
		    glstmpltVO = new GLSTMPLTVO();
		    glstmpltVO.setCODE(null);
		    glstmpltVO.setBRIEF_NAME_ENG(null);

		}
		else
		{
		    glstmpltVO.setCODE(new BigDecimal(templateCode));
		    glstmpltVO.setBRIEF_NAME_ENG(lineName);
		    glstmpltVO.setLINE_NBR(new BigDecimal(lineNbr));
		}

	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that save's mismatch informations in memory
     * 
     * @return
     * @throws JSONException
     */
    public String validateMis() throws JSONException // submit GL form
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    String templateCodeWithLineNb = getTempCodeWithLineNb();
	    String appName = sessionCO.getCurrentAppName();
	    String lang = sessionCO.getLanguage();
	    String zTemplCode = templateCodeWithLineNb.split("~")[0];
	    String zLineNb = templateCodeWithLineNb.split("~")[1];
	    String misConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString()
		    + ftrMissRepCO.getFtrMissRepVO().getTMPLT_CODE1().toString()
		    + ftrMissRepCO.getFtrMissRepVO().getSUB_LINE_NO().toString();
	    templateBO.checkRequiredFields(ftrMissRepCO, get_pageRef(), compCode, appName, lang);
	    boolean isNew=false;
	    if(ftrMissRepCO.getConcatKey().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
	    {
		isNew=true;
		ftrMissRepCO.setConcatKey(new BigDecimal(misConcatKey));
	    }

	    String lineConcatKey = compCode.toString() + zTemplCode.toString() + zLineNb.toString();
	    // get the glstmpltCO in order to fill the mismap
	    GLSTMPLTCO glstmpltCOO = getglstmpltCOById(lineConcatKey);

	    returnReportingSessionObject(this.get_pageRef()).getAllTempl().setAnyModif(true);

	    if(glstmpltCOO == null)
	    {
		BOException e = new BOException();
		e.setMsgIdent("You must select a line");
		throw e;
	    }
	    if(glstmpltCOO.getNewLineNumber() != null)
	    {
		ftrMissRepCO.setNewLineNumber(glstmpltCOO.getNewLineNumber());
	    }
	    // check if it is a new mismatch
	    LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> addMismatchMap = glstmpltCOO.getAddMismatchMap();
	    LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> dbMismatchMap = glstmpltCOO.getDbMismatchMap();
	    ftrMissRepCO.getFtrMissRepVO().setCOMP_CODE(compCode);
	    ftrMissRepCO.getFtrMissRepVO().setLINE_NO(glstmpltCOO.getGlstmpltVO().getLINE_NBR());
	    ftrMissRepCO.getFtrMissRepVO().setTMPLT_CODE(glstmpltCOO.getGlstmpltVO().getCODE());
	    FTR_MISMATCH_REPORTCO modifMisVO = addMismatchMap.get(ftrMissRepCO.getConcatKey());

	    if(modifMisVO == null)
	    {
		if(dbMismatchMap.get(ftrMissRepCO.getConcatKey()) == null)// add
		{
		    glstmpltCOO.getAddMismatchMap().put(new BigDecimal(misConcatKey), ftrMissRepCO);
		}
		else
		{
		    modifMisVO = dbMismatchMap.get(ftrMissRepCO.getConcatKey());
		    if(glstmpltCOO.getOldMismatchMap().get(ftrMissRepCO.getConcatKey()) == null)
		    {
			glstmpltCOO.getOldMismatchMap().put(ftrMissRepCO.getConcatKey(), modifMisVO);
		    }
		    dbMismatchMap.put(ftrMissRepCO.getConcatKey(), ftrMissRepCO);
		}
	    }
	    else
	    {
		if(isNew)
		{
		    BOException e = new BOException();
		    e.setMsgIdent("Duplicated_Entry_Of_Record");
		    throw e;
		}
		else
		{
		    addMismatchMap.put(ftrMissRepCO.getConcatKey(), ftrMissRepCO);
		}
	    }

	}
	catch(BOException e)
	{
	    if(e.getMsgIdent().equals("You must select a line"))
	    {
		handleException(null, null, "gl.selectLineAlert");
	    }
	    else
	    {
		handleException(null, null, e.getMsgIdent());
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that deletes a mismatch from memory
     * 
     * @return
     * @throws JSONException
     */
    public String deleteMismatch() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal misConcatKeyNb = ftrMissRepCO.getConcatKey();
	    BigDecimal tempCode = ftrMissRepCO.getFtrMissRepVO().getTMPLT_CODE();
	    BigDecimal lineNb = ftrMissRepCO.getFtrMissRepVO().getLINE_NO();
	    String lineConcatKey = compCode.toString() + tempCode.toString() + lineNb.toString();
	    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
	    HashMap<BigDecimal, FTR_MISMATCH_REPORTCO> addMismatchMap = glstmpltCO.getAddMismatchMap();
	    HashMap<BigDecimal, FTR_MISMATCH_REPORTCO> dbMismatchMap = glstmpltCO.getDbMismatchMap();
	    HashMap<BigDecimal, FTR_MISMATCH_REPORTCO> delMismatchMap = glstmpltCO.getDelMismatchMap();
	    FTR_MISMATCH_REPORTCO delftrMismatchRepCO = addMismatchMap.get(misConcatKeyNb);
	    if(delftrMismatchRepCO == null)
	    {
		FTR_MISMATCH_REPORTCO COToDel = dbMismatchMap.get(misConcatKeyNb);
		dbMismatchMap.remove(misConcatKeyNb);
		delMismatchMap.put(misConcatKeyNb, COToDel);
	    }
	    else
	    {
		addMismatchMap.remove(misConcatKeyNb);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    public String refreshMismatchForm() throws Exception
    {

	return "refreshMismatchForm";
    }

    public String openMismatchJson() throws JSONException
    {
	return SUCCESS;
    }

    public Object getModel()
    {
	return ftrMissRepCO;
    }

    @Override
    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;

    }

}
