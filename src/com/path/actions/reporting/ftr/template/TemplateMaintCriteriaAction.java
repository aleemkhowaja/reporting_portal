package com.path.actions.reporting.ftr.template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLT_PARAM_LINKCO;

public class TemplateMaintCriteriaAction extends ReportingLookupBaseAction implements ServletRequestAware,
	ServletResponseAware
{
    /*
     * used to fill the CRT form
     */
    private GLSTMPLT_PARAM_LINKCO crtCO = new GLSTMPLT_PARAM_LINKCO(); 
    private String updates;
    protected HttpServletRequest request;
    private String crtComp;
    private List<Object> operatorArrList; // to fill the operator combo
    private CommonLookupBO commonLookupBO;
    private TemplateBO templateBO;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> daysMonthYear;
    private ArrayList<SYS_PARAM_LOV_TRANSVO> maleFemaleList;
    
    

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getDaysMonthYear()
    {
        return daysMonthYear;
    }

    public void setDaysMonthYear(ArrayList<SYS_PARAM_LOV_TRANSVO> daysMonthYear)
    {
        this.daysMonthYear = daysMonthYear;
    }

    public ArrayList<SYS_PARAM_LOV_TRANSVO> getMaleFemaleList()
    {
        return maleFemaleList;
    }

    public void setMaleFemaleList(ArrayList<SYS_PARAM_LOV_TRANSVO> maleFemaleList)
    {
        this.maleFemaleList = maleFemaleList;
    }

    public void setTemplateBO(TemplateBO templateBO)
    {
	this.templateBO = templateBO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public List<Object> getOperatorArrList()
    {
	return operatorArrList;
    }

    public void setOperatorArrList(List<Object> operatorArrList)
    {
	this.operatorArrList = operatorArrList;
    }

    public String getCrtComp()
    {
	return crtComp;
    }

    public void setCrtComp(String crtComp)
    {
	this.crtComp = crtComp;
    }

    public GLSTMPLT_PARAM_LINKCO getCrtCO()
    {
	return crtCO;
    }

    public void setCrtCO(GLSTMPLT_PARAM_LINKCO crtCO)
    {
	this.crtCO = crtCO;
    }

    public String getUpdates()
    {
	return updates;
    }

    public void setUpdates(String updates)
    {
	this.updates = updates;
    }

    public String input() throws Exception
    {
	return SUCCESS;
    }

    public String upload() throws Exception
    {
	return SUCCESS;
    }

    public Object getModel()
    {
	return crtCO;
    }

    /**
     * method called when opening a criteria
     * 
     * @return
     * @throws JSONException
     */
    public String openCrt() throws JSONException // open line Form
    {
	try
	{

	    SessionCO sessionCO = returnSessionObject();
	    // fill the combo of the operation type
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(5)); // 5= operator
	    // (check table
	    // sys_param_lov_type)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    operatorArrList = commonLookupBO.getLookupList(sysParamLovVO);
	    SYS_PARAM_SCREEN_DISPLAYVO dispCODE1 = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.CODE1
	    SYS_PARAM_SCREEN_DISPLAYVO dispCODE2 = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.glstmpltParamLinkVO.CODE2
	    SYS_PARAM_SCREEN_DISPLAYVO dispCODE1Name = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.CODE1_NAME
	    SYS_PARAM_SCREEN_DISPLAYVO dispCODE2Name = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.CODE2_NAME
	    SYS_PARAM_SCREEN_DISPLAYVO dispValue1 = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.VALUE1
	    SYS_PARAM_SCREEN_DISPLAYVO dispValue2 = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.VALUE2
	    SYS_PARAM_SCREEN_DISPLAYVO visibilityDayMonthYear = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO visibilityMfList = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO dispCode3 = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO dispCode3Name = new SYS_PARAM_SCREEN_DISPLAYVO();
	    dispCODE1.setIS_VISIBLE(BigDecimal.ONE);
	    dispCODE2.setIS_VISIBLE(BigDecimal.ONE);
	    dispCODE1Name.setIS_VISIBLE(BigDecimal.ONE);
	    dispCODE2Name.setIS_VISIBLE(BigDecimal.ONE);
	    dispValue1.setIS_VISIBLE(BigDecimal.ONE);
	    dispValue2.setIS_VISIBLE(BigDecimal.ONE);
	    dispCode3.setIS_READONLY(BigDecimal.ONE);
	    dispCode3Name.setIS_READONLY(BigDecimal.ONE);
	    if(RepConstantsCommon.YES.equalsIgnoreCase(crtCO.getGlstmpltParamLinkVO().getINCLUDE()))
	    {
		crtCO.setINCLUDE_CHK(true);
	    }
	    if("default".equals(updates))
	    {
		dispCODE1.setIS_READONLY(BigDecimal.ONE);
		dispCODE2.setIS_READONLY(BigDecimal.ONE);
		dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		dispValue1.setIS_READONLY(BigDecimal.ONE);
		dispValue2.setIS_READONLY(BigDecimal.ONE);
		getAdditionalScreenParams().put("lookuptxt_crt_Code1", dispCODE1);
		getAdditionalScreenParams().put("lookuptxt_crt_Code2", dispCODE2);
		getAdditionalScreenParams().put("code1LookUp", dispCODE1Name);
		getAdditionalScreenParams().put("code2LookUp", dispCODE2Name);
		getAdditionalScreenParams().put("crt_value1", dispValue1);
		getAdditionalScreenParams().put("crt_value2", dispValue2);
		getAdditionalScreenParams().put("lookuptxt_crt_Code3", dispCode3);
		getAdditionalScreenParams().put("code3LookUp", dispCode3Name);
		crtCO.setCODE1(null);
		crtCO.setCODE1_NAME(null);
		crtCO.setVALUE1(null);
		crtCO.setVALUE2(null);
		crtCO.setCODE3(null);
		crtCO.setCODE3_NAME(null);
		dispCode3Name.setIS_READONLY(BigDecimal.ONE);
		dispCode3.setIS_READONLY(BigDecimal.ONE);
		setUpdates("");
	    }
	    else
	    {

		if("changedCriteria".equals(updates))
		{
		    crtCO.setCODE1(null);
		    crtCO.setCODE1_NAME(null);
		    crtCO.setVALUE1(null);
		    crtCO.setVALUE2(null);
		    crtCO.getGlstmpltParamLinkVO().setCODE2(null);
		    crtCO.setCODE2_NAME(null);
		    crtCO.setCODE3(null);
		    crtCO.setCODE3_NAME(null);
		    setUpdates("");
		}

		if(crtCO.getCOMPONENT().equals("D") || crtCO.getCOMPONENT().equals("L"))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ZERO);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ONE);
		    //for SMART criteria check if the detail1 should be mandatory or not
		    if(RepConstantsCommon.CRITERIA_SMART.equals(crtCO.getCRITERIA_TYPE_CODE()))
		    {
				GLSTMPLT_CRITERIASC crtSC= new GLSTMPLT_CRITERIASC();
				crtSC.setCODE(crtCO.getGlstmpltParamLinkVO().getCRITERIA_CODE());
				crtSC.setCOMP_CODE(sessionCO.getCompanyCode());
				S_ADDITIONS_OPTIONSVO addOptionsVO= templateBO.checkSmartMandatoryDetails(crtSC);
				if(addOptionsVO!=null && addOptionsVO.getOPTION_TYPE().intValue() > 0)
				{
				    dispCODE1.setIS_READONLY(BigDecimal.ONE);
				    dispCODE1.setIS_MANDATORY(BigDecimal.ZERO);
				}
		    }
		    dispCODE2.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue1.setIS_READONLY(BigDecimal.ONE);
		    dispValue1.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue2.setIS_READONLY(BigDecimal.ONE);
		    dispValue2.setIS_MANDATORY(BigDecimal.ZERO);
		    if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtCO.getCRITERIA_TYPE_CODE()))
		    {
			dispCODE1.setZERO_NOT_ALLOWED(BigDecimal.ZERO);
		    }
		}

		else if(crtCO.getCOMPONENT().equals("T"))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue1.setIS_READONLY(BigDecimal.ZERO);
		    dispValue1.setIS_MANDATORY(BigDecimal.ONE);
		    dispValue2.setIS_READONLY(BigDecimal.ONE);
		    dispValue2.setIS_MANDATORY(BigDecimal.ZERO);
		}

		else if(crtCO.getCOMPONENT().equals("LL"))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ZERO);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ONE);
		    dispCODE2.setIS_READONLY(BigDecimal.ZERO);
		    dispCODE2.setIS_MANDATORY(BigDecimal.ONE);
		    dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue1.setIS_READONLY(BigDecimal.ONE);
		    dispValue1.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue2.setIS_READONLY(BigDecimal.ONE);
		    dispValue2.setIS_MANDATORY(BigDecimal.ZERO);
		}

		else if(crtCO.getCOMPONENT().equals("DT"))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ZERO);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ONE);
		    dispCODE2.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue1.setIS_READONLY(BigDecimal.ZERO);
		    dispValue1.setIS_MANDATORY(BigDecimal.ONE);
		    String zOper = crtCO.getCODE1();
		    if((">=<=").equals(zOper) || ("><").equals(zOper) || ("><=").equals(zOper) || (">=<").equals(zOper))
		    {
			dispValue2.setIS_READONLY(BigDecimal.ZERO);
			dispValue2.setIS_MANDATORY(BigDecimal.ONE);
		    }
		    else
		    {
			dispValue2.setIS_READONLY(BigDecimal.ONE);
			dispValue2.setIS_MANDATORY(BigDecimal.ZERO);
			crtCO.setVALUE2(null);
		    }
		}

		else if(crtCO.getCOMPONENT().equals("TDT") || crtCO.getCOMPONENT().equals("DTT"))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ZERO);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ONE);
		    dispCODE2.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue1.setIS_READONLY(BigDecimal.ZERO);
		    dispValue1.setIS_MANDATORY(BigDecimal.ONE);
		    dispValue2.setIS_READONLY(BigDecimal.ZERO);
		    dispValue2.setIS_MANDATORY(BigDecimal.ONE);
		}

		else if(crtCO.getCOMPONENT() == null || "".equals(crtCO.getCOMPONENT().trim()))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE1Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE1Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispCODE2Name.setIS_READONLY(BigDecimal.ONE);
		    dispCODE2Name.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue1.setIS_READONLY(BigDecimal.ONE);
		    dispValue1.setIS_MANDATORY(BigDecimal.ZERO);
		    dispValue2.setIS_READONLY(BigDecimal.ONE);
		    dispValue2.setIS_MANDATORY(BigDecimal.ZERO);
		}

		if(crtCO.getFromFiltCrt() != null
			&& (crtCO.getFromFiltCrt().equals("><") || crtCO.getFromFiltCrt().equals("><=")
				|| crtCO.getFromFiltCrt().equals(">=<") || crtCO.getFromFiltCrt().equals(">=<=")))
		{
		    dispValue1.setIS_READONLY(BigDecimal.ZERO);
		    dispValue1.setIS_MANDATORY(BigDecimal.ONE);
		    dispValue2.setIS_READONLY(BigDecimal.ZERO);
		    dispValue2.setIS_MANDATORY(BigDecimal.ONE);
		}

		getAdditionalScreenParams().put("lookuptxt_crt_Code1", dispCODE1);
		getAdditionalScreenParams().put("lookuptxt_crt_Code2", dispCODE2);
		getAdditionalScreenParams().put("code1LookUp", dispCODE1Name);
		getAdditionalScreenParams().put("code2LookUp", dispCODE2Name);
		getAdditionalScreenParams().put("crt_value1", dispValue1);
		getAdditionalScreenParams().put("crt_value2", dispValue2);
		getAdditionalScreenParams().put("lookuptxt_crt_Code3", dispCode3);
		getAdditionalScreenParams().put("code3LookUp", dispCode3Name);
	    }
	    // filling the list for the dmy drp dwn
	    SYS_PARAM_LOV_TRANSVO drpDwns = new SYS_PARAM_LOV_TRANSVO();
	    drpDwns.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_CRITERIA_DMY);
	    drpDwns.setLANG_CODE(sessionCO.getLanguage());
	    daysMonthYear = (ArrayList) commonLookupBO.getLookupList(drpDwns);
	    drpDwns.setLOV_TYPE_ID(RepConstantsCommon.TEMPLATE_MALE_FEMALE);
	    maleFemaleList = (ArrayList) commonLookupBO.getLookupList(drpDwns);
	    if(RepConstantsCommon.CRITERIA_MBK.equals(crtCO.getCRITERIA_TYPE_CODE())
		    || RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY.equals(crtCO.getCRITERIA_TYPE_CODE()))
	    {
		visibilityDayMonthYear.setIS_MANDATORY(BigDecimal.ONE);
		visibilityDayMonthYear.setIS_READONLY(BigDecimal.ZERO);
		visibilityDayMonthYear.setIS_VISIBLE(BigDecimal.ONE);
		visibilityMfList.setIS_MANDATORY(BigDecimal.ZERO);
		visibilityMfList.setIS_READONLY(BigDecimal.ZERO);
		visibilityMfList.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    else if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRITERIA_TYPE_CODE()))
	    {
		visibilityMfList.setIS_MANDATORY(BigDecimal.ONE);
		visibilityMfList.setIS_READONLY(BigDecimal.ZERO);
		visibilityMfList.setIS_VISIBLE(BigDecimal.ONE);
		visibilityDayMonthYear.setIS_MANDATORY(BigDecimal.ZERO);
		visibilityDayMonthYear.setIS_READONLY(BigDecimal.ZERO);
		visibilityDayMonthYear.setIS_VISIBLE(BigDecimal.ZERO);
		//emptying desc field of livesearch
		crtCO.setCODE1_NAME("");
	    }
	    else
	    {
		visibilityMfList.setIS_MANDATORY(BigDecimal.ZERO);
		visibilityMfList.setIS_READONLY(BigDecimal.ZERO);
		visibilityMfList.setIS_VISIBLE(BigDecimal.ZERO);
		visibilityDayMonthYear.setIS_MANDATORY(BigDecimal.ZERO);
		visibilityDayMonthYear.setIS_READONLY(BigDecimal.ZERO);
		visibilityDayMonthYear.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    getAdditionalScreenParams().put("dayMonthYear", visibilityDayMonthYear);
	    getAdditionalScreenParams().put("criteriaFMId", visibilityMfList);
	    if(RepConstantsCommon.CRITERIA_CAF.equals(crtCO.getCRITERIA_TYPE_CODE()))
	    {
		dispCode3.setIS_MANDATORY(BigDecimal.ONE);
		dispCode3.setIS_READONLY(BigDecimal.ZERO);
		dispCode3Name.setIS_MANDATORY(BigDecimal.ZERO);
		dispCode3Name.setIS_READONLY(BigDecimal.ONE);
	    }
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    /**
     * method called when deleting a criteria
     * 
     * @return
     * @throws JSONException
     */
    public String deleteCrt() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal crtConcatKeyNb = crtCO.getConcatKey();
	    BigDecimal tempCode = crtCO.getGlstmpltParamLinkVO().getTEMP_CODE();
	    BigDecimal lineNb = crtCO.getGlstmpltParamLinkVO().getLINE_NO();
	    String lineConcatKey = compCode.toString() + tempCode.toString() + lineNb.toString();
	    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
	    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> addCrtMap = glstmpltCO.getAddCrtMap();
	    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> dbCrtMap = glstmpltCO.getDbCrtMap();
	    HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> delCrtMap = glstmpltCO.getDelCrtMap();
	    int maxCrtSubLineNbOld = glstmpltCO.getMaxCrtSubLineNb();
	    if(glstmpltCO.getMaxCrtSubLineNb() > 0)
	    {
		glstmpltCO.setMaxCrtSubLineNb(--maxCrtSubLineNbOld);
	    }
	    // in this case, the user didn't try to add previously any line,so
	    // get the max from db and substract 2 from it
	    else if(glstmpltCO.getMaxCrtSubLineNb() == 0)
	    {
		CommonDetailsSC crtSC = new CommonDetailsSC();
		crtSC.setTEMPLATE_CODE(tempCode);
		crtSC.setCOMP_CODE(compCode);
		crtSC.setTEMPLATE_LINE_NO(lineNb);
		CommonDetailsVO commonDetVO = templateBO.getMaxCrtSubLineNb(crtSC);
		glstmpltCO.setMaxCrtSubLineNb((commonDetVO.getSUB_LINE_NO().intValue() - 2));
	    }
	    GLSTMPLT_PARAM_LINKCO delCrtCO = addCrtMap.get(crtConcatKeyNb);
	    if(delCrtCO == null)
	    {
		GLSTMPLT_PARAM_LINKCO crtCOToDel = dbCrtMap.get(crtConcatKeyNb);
		dbCrtMap.remove(crtConcatKeyNb);
		delCrtMap.put(crtConcatKeyNb, crtCOToDel);
	    }
	    else
	    {
		addCrtMap.remove(crtConcatKeyNb);
	    }
	    // iterate over the lines and adjust the sublinenumbers
	    GLSTMPLT_PARAM_LINKCO tempCO;
	    BigDecimal subLineRemoved = crtCO.getGlstmpltParamLinkVO().getSUB_LINE_NO();
	    int newSubLineNb;
	    Iterator<Map.Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO>> itFmap = dbCrtMap.entrySet().iterator();
	    // below hashmap used when changing the sublinenumber for a record
	    // (also adjust the Key)
	    LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> temporaryAddMap = new LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();
	    String newKey;
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO> entry = itFmap.next();
		tempCO = entry.getValue();
		if(tempCO.getGlstmpltParamLinkVO().getSUB_LINE_NO().intValue() > subLineRemoved.intValue())
		{
		    newSubLineNb = tempCO.getGlstmpltParamLinkVO().getSUB_LINE_NO().intValue();
		    tempCO.getGlstmpltParamLinkVO().setSUB_LINE_NO(BigDecimal.valueOf(--newSubLineNb));
		    newKey = compCode.toString() + tempCode.toString() + lineNb.toString() + newSubLineNb;
		    tempCO.setConcatKey(new BigDecimal(newKey));
		    temporaryAddMap.put(new BigDecimal(newKey), tempCO);
		}
		else
		{
		    temporaryAddMap.put(entry.getKey(), tempCO);
		}
	    }

	    glstmpltCO.setDbCrtMap((LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>) temporaryAddMap.clone());
	    temporaryAddMap.clear();
	    itFmap = addCrtMap.entrySet().iterator();
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO> entry = itFmap.next();
		tempCO = entry.getValue();

		// since the key contains the sublinenumber, have to update also
		if(tempCO.getGlstmpltParamLinkVO().getSUB_LINE_NO().intValue() > subLineRemoved.intValue())
		{
		    newSubLineNb = tempCO.getGlstmpltParamLinkVO().getSUB_LINE_NO().intValue();
		    tempCO.getGlstmpltParamLinkVO().setSUB_LINE_NO(BigDecimal.valueOf(--newSubLineNb));
		    newKey = compCode.toString() + tempCode.toString() + lineNb.toString() + newSubLineNb;
		    tempCO.setConcatKey(new BigDecimal(newKey));
		    temporaryAddMap.put(new BigDecimal(newKey), tempCO);
		}
		else
		{
		    temporaryAddMap.put(entry.getKey(), tempCO);
		}
	    }
	    // set the addMap
	    glstmpltCO.setAddCrtMap((LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>) temporaryAddMap.clone());
	    // printHashContents(glstmpltCO.getAddCrtMap(), "addglstmpltco");
	    // printHashContents(glstmpltCO.getDbCrtMap(), "db");
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    // public static void printHashContents(HashMap<BigDecimal,
    // GLSTMPLT_PARAM_LINKCO> map, String hashName)
    // {
    // System.out.println(hashName + ":\n\n\n");
    // Iterator<Map.Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO>> itFmap =
    // map.entrySet().iterator();
    // while(itFmap.hasNext())
    // {
    // Entry<BigDecimal, GLSTMPLT_PARAM_LINKCO> entry = itFmap.next();
    // BigDecimal theKey = entry.getKey();
    // GLSTMPLT_PARAM_LINKCO gg = entry.getValue();
    // System.out.println("key hash:" + theKey + "     key: " +
    // gg.getConcatKey() + "       new Line: "
    // + gg.getNewLineNumber() + "             true line: " +
    // gg.getGlstmpltParamLinkVO().getSUB_LINE_NO()
    // + "             description: " + gg.getCRITERIA_NAME() + "\n");
    // }
    // }

    /**
     * method that gets a template by its ID
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

    @Override
    public void setServletRequest(HttpServletRequest arg0)
    {
	request = arg0;
    }

}
