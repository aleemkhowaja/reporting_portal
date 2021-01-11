package com.path.actions.reporting.ftr.columntemplate;

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
import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateBO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.util.NumberUtil;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;

public class ColumnTemplateMaintCriteriaAction extends ReportingLookupBaseAction implements ServletRequestAware,ServletResponseAware
{

    private COLMNTMPLT_PARAM_LINKCO crtCO = new COLMNTMPLT_PARAM_LINKCO(); // used to fill the CRT form
    private String updates;
    protected HttpServletRequest request;
    private List<Object> operatorArrList; // to fill the operator combo    
    private CommonLookupBO commonLookupBO;
    private ColumnTemplateBO columnTemplateBO;
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

    public void setColumnTemplateBO(ColumnTemplateBO columnTemplateBO)
    {
        this.columnTemplateBO = columnTemplateBO;
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

    public COLMNTMPLT_PARAM_LINKCO getCrtCO()
	{
	    return crtCO;
	}
    
	public void setCrtCO(COLMNTMPLT_PARAM_LINKCO crtCO)
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
	    SYS_PARAM_SCREEN_DISPLAYVO visibilityDayMonthYear = new SYS_PARAM_SCREEN_DISPLAYVO();// crtCO.VALUE2
	    SYS_PARAM_SCREEN_DISPLAYVO visibilityMfList = new SYS_PARAM_SCREEN_DISPLAYVO();
	    SYS_PARAM_SCREEN_DISPLAYVO visibilityCrtCode = new SYS_PARAM_SCREEN_DISPLAYVO();//crt_Code
	    dispCODE1.setIS_VISIBLE(BigDecimal.ONE);
	    dispCODE2.setIS_VISIBLE(BigDecimal.ONE);
	    dispCODE1Name.setIS_VISIBLE(BigDecimal.ONE);
	    dispCODE2Name.setIS_VISIBLE(BigDecimal.ONE);
	    dispValue1.setIS_VISIBLE(BigDecimal.ONE);
	    dispValue2.setIS_VISIBLE(BigDecimal.ONE);
	    if(RepConstantsCommon.YES.equalsIgnoreCase(crtCO.getColumntmpltParamLinkVO().getINCLUDE()))
	    {
		crtCO.setINCLUDE_CHK(true);
	    }
		if(crtCO.getCOMPONENT().equals("D") || crtCO.getCOMPONENT().equals("L"))
		{
		    dispCODE1.setIS_READONLY(BigDecimal.ZERO);
		    dispCODE1.setIS_MANDATORY(BigDecimal.ONE);
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
		visibilityCrtCode.setIS_MANDATORY(BigDecimal.ONE);
		visibilityCrtCode.setIS_VISIBLE(BigDecimal.ONE);
		visibilityCrtCode.setIS_READONLY(BigDecimal.ZERO);		
		getAdditionalScreenParams().put("lookuptxt_crt_Code",visibilityCrtCode);
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
	    getAdditionalScreenParams().put("criteriaFMColId", visibilityMfList);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}

	return SUCCESS;
    }

    public String deleteCrt() throws JSONException //load the grid
    {
    	try
    	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal crtConcatKeyNb = crtCO.getConcatKey();
	    BigDecimal tempCode = crtCO.getColumntmpltParamLinkVO().getTEMP_CODE();
	    BigDecimal lineNb = crtCO.getColumntmpltParamLinkVO().getLINE_NO();
	    if(!NumberUtil.isEmptyDecimal(crtCO.getNewLineNumber()))
	    {
		lineNb = crtCO.getNewLineNumber();
	    }
	    String lineConcatKey = compCode.toString() + tempCode.toString() + lineNb.toString();
	    COLMNTMPLTCO glstmpltCO = getColmnTmpltCOById(lineConcatKey);
	    HashMap addCrtMap = glstmpltCO.getAddCrtMap();
	    HashMap dbCrtMap = glstmpltCO.getDbCrtMap();
	    HashMap delCrtMap = glstmpltCO.getDelCrtMap();
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
		if(crtCO.getNewLineNumber()!=null)
		{
		    crtSC.setTEMPLATE_LINE_NO(crtCO.getColumntmpltParamLinkVO().getLINE_NO());
		}
		CommonDetailsVO commonDetVO = columnTemplateBO.getMaxCrtSubLineNb(crtSC);
		glstmpltCO.setMaxCrtSubLineNb((commonDetVO.getSUB_LINE_NO().intValue() - 2));
	    }

	    COLMNTMPLT_PARAM_LINKCO delCrtCO = (COLMNTMPLT_PARAM_LINKCO) addCrtMap.get(crtConcatKeyNb);
	    if(delCrtCO == null)
	    {
		COLMNTMPLT_PARAM_LINKCO crtCOToDel = (COLMNTMPLT_PARAM_LINKCO) dbCrtMap.get(crtConcatKeyNb);
		dbCrtMap.remove(crtConcatKeyNb);
		delCrtMap.put(crtConcatKeyNb, crtCOToDel);
	    }
	    else
	    {
		addCrtMap.remove(crtConcatKeyNb);
	    }
	    // iterate over the lines and adjust the sublinenumbers
	    COLMNTMPLT_PARAM_LINKCO tempCO;
	    BigDecimal subLineRemoved = crtCO.getColumntmpltParamLinkVO().getSUB_LINE_NO();
	    int newSubLineNb;
	    Iterator<Map.Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO>> itFmap = dbCrtMap.entrySet().iterator();
	    // below hashmap used when changing the sublinenumber for a record
	    // (also adjust the Key)
	    LinkedHashMap<BigDecimal, COLMNTMPLT_PARAM_LINKCO> temporaryAddMap = new LinkedHashMap<BigDecimal, COLMNTMPLT_PARAM_LINKCO>();
	    String newKey;
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itFmap.next();
		tempCO = entry.getValue();
		if(tempCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().intValue() > subLineRemoved.intValue())
		{
		    newSubLineNb = tempCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().intValue();
		    tempCO.getColumntmpltParamLinkVO().setSUB_LINE_NO(BigDecimal.valueOf(--newSubLineNb));
		    newKey = compCode.toString() + tempCode.toString() + lineNb.toString() + newSubLineNb;
		    tempCO.setConcatKey(new BigDecimal(newKey));
		    temporaryAddMap.put(new BigDecimal(newKey), tempCO);
		}
		else
		{
		    temporaryAddMap.put(entry.getKey(), tempCO);
		}
	    }

	    glstmpltCO.setDbCrtMap((LinkedHashMap<BigDecimal, COLMNTMPLT_PARAM_LINKCO>) temporaryAddMap.clone());
	    temporaryAddMap.clear();
	    itFmap = addCrtMap.entrySet().iterator();
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, COLMNTMPLT_PARAM_LINKCO> entry = itFmap.next();
		tempCO = entry.getValue();

		// since the key contains the sublinenumber, have to update also
		if(tempCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().intValue() > subLineRemoved.intValue())
		{
		    newSubLineNb = tempCO.getColumntmpltParamLinkVO().getSUB_LINE_NO().intValue();
		    tempCO.getColumntmpltParamLinkVO().setSUB_LINE_NO(BigDecimal.valueOf(--newSubLineNb));
		    newKey = compCode.toString() + tempCode.toString() + lineNb.toString() + newSubLineNb;
		    tempCO.setConcatKey(new BigDecimal(newKey));
		    temporaryAddMap.put(new BigDecimal(newKey), tempCO);
		}
		else
		{
		    temporaryAddMap.put(entry.getKey(), tempCO);
		}
	    }
	    glstmpltCO.setAddCrtMap((LinkedHashMap<BigDecimal, COLMNTMPLT_PARAM_LINKCO>) temporaryAddMap.clone());
    	}
    	catch(Exception e)
    	{
    		handleException(e,null , null);
    	}
    	return SUCCESS;
    }
        	
    public COLMNTMPLTCO getColmnTmpltCOById(String lineConcatKey)
	{
		ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	 	COLMNTMPLTCO allTempl=repSessionCO.getCurrentTemplate(); 
	 	
	 	//Get Column lines of the current template
		List<COLMNTMPLTCO> colmnTempLinesList = allTempl.getColumnDetails();
		LinkedHashMap<BigDecimal, COLMNTMPLTCO> dbLinesMap = new LinkedHashMap<BigDecimal, COLMNTMPLTCO>();
		
		for(int i=0; i<colmnTempLinesList.size(); i++)
		{
			dbLinesMap.put(colmnTempLinesList.get(i).getConcatKey() , colmnTempLinesList.get(i));
		}
		
	    COLMNTMPLTCO glstmpltCO=dbLinesMap.get(new BigDecimal(lineConcatKey));
	    
	    return glstmpltCO;
	}
    
    @Override
	public void setServletRequest(HttpServletRequest arg0)
	{
	    request=arg0;
	}
}

