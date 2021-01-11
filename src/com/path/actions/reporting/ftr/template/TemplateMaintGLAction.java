package com.path.actions.reporting.ftr.template;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.reporting.struts2.lib.common.base.ReportingLookupBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTGLSDETCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;

public class TemplateMaintGLAction extends ReportingLookupBaseAction
{
    private GLSTMPLTCO glstmpltCO = new GLSTMPLTCO();
    private GLSTMPLTVO glstmpltVO = new GLSTMPLTVO();
    private String mode;
    private CommonLookupBO commonLookupBO;
    private File upload;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    private String contentType;
    private String fileName;
    private List<Object> calcTypeArrList; // to fill the calculationType combo
    private String caption;
    private String updates;
    private TemplateBO templateBO;

    public void setTemplateBO(TemplateBO templateBO)
    {
	this.templateBO = templateBO;
    }

    public List<Object> getCalcTypeArrList()
    {
	return calcTypeArrList;
    }

    public void setCalcTypeArrList(List<Object> calcTypeArrList)
    {
	this.calcTypeArrList = calcTypeArrList;
    }

    public GLSTMPLTGLSDETCO getGlCO()
    {
	return glCO;
    }

    public void setGlCO(GLSTMPLTGLSDETCO glCO)
    {
	this.glCO = glCO;
    }

    private GLSTMPLTGLSDETCO glCO = new GLSTMPLTGLSDETCO();

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

    public String getContentType()
    {

	return contentType;
    }

    public void setContentType(String contentType)
    {
	this.contentType = contentType;
    }

    public String getFileName()
    {
	return fileName;
    }

    public void setFileName(String fileName)
    {
	this.fileName = fileName;
    }

    public String getCaption()
    {
	return caption;
    }

    public void setCaption(String caption)
    {
	this.caption = caption;
    }

    public String upload() throws Exception
    {
	return SUCCESS;
    }

    public void setRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    public HttpServletResponse getResponse()
    {
	return response;
    }

    public void setResponse(HttpServletResponse response)
    {
	this.response = response;
    }

    public File getUpload()
    {
	return upload;
    }

    public void setUpload(File upload)
    {
	this.upload = upload;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public GLSTMPLTVO getGlstmpltVO()
    {
	return glstmpltVO;
    }

    public void setGlstmpltVO(GLSTMPLTVO glstmpltVO)
    {
	this.glstmpltVO = glstmpltVO;
    }

    public GLSTMPLTCO getGlstmpltCO()
    {
	return glstmpltCO;
    }

    public void setGlstmpltCO(GLSTMPLTCO glstmpltCO)
    {
	this.glstmpltCO = glstmpltCO;
    }

    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    /***
     * Method called on opening of GL record
     * @return
     */
    public String openGL() // open line Form
    {
	try
	{
	    HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> hm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	    SYS_PARAM_SCREEN_DISPLAYVO dispChkClosingEntry = new SYS_PARAM_SCREEN_DISPLAYVO();
	    if(glCO.getGlstmpltGlsDetVO().getPERCENTAGE().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
	    {
		glCO.getGlstmpltGlsDetVO().setPERCENTAGE(null);
	    }
	    String calcType = glCO.getGlstmpltGlsDetVO().getCALC_TYPE();
	    if(RepConstantsCommon.CALC_CNT_CIF.equals(calcType) || (RepConstantsCommon.CALC_TYPE_RTV).equals(calcType)
	    	|| RepConstantsCommon.CALC_INDEP_CNT_CIF.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_IBC.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_ILC.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_LG.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_OBC.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_OLC.equals(calcType)
		    || RepConstantsCommon.CALC_TYPE_LIST.contains(calcType))
	    {
		SYS_PARAM_SCREEN_DISPLAYVO buisnessElement = new SYS_PARAM_SCREEN_DISPLAYVO();
		buisnessElement.setIS_MANDATORY(BigDecimal.ZERO);
		buisnessElement.setIS_READONLY(BigDecimal.ONE);
		dispChkClosingEntry.setIS_VISIBLE(BigDecimal.ZERO);
		glCO.getGlstmpltGlsDetVO().setDIV_CODE(null);
		glCO.setDIV_NAME(null);
		glCO.getGlstmpltGlsDetVO().setDEPT_CODE(null);
		glCO.setDEPT_NAME(null);
		glCO.getGlstmpltGlsDetVO().setFROM_GL(null);
		glCO.setFROM_GLStr(null);
		glCO.getGlstmpltGlsDetVO().setTO_GL(null);
		glCO.setTO_GLStr(null);
		if((RepConstantsCommon.CALC_TYPE_RTV).equals(calcType))
		{
		    SessionCO sessionCO = returnSessionObject();
		    BigDecimal compCode = sessionCO.getCompanyCode();
		    String compDesc = sessionCO.getCompanyName();
		    glCO.getGlstmpltGlsDetVO().setGL_COMP(compCode);
		    glCO.setGL_COMP_NAME(compDesc);
		    glCO.getGlstmpltGlsDetVO().setBRANCH_CODE(null);
		    glCO.setBRANCH_NAME(null);
		    hm.put("lookuptxt_glCompCode", buisnessElement);
		    hm.put("lookuptxt_branchCode", buisnessElement);
		}
		hm.put("lookuptxt_divCode", buisnessElement);
		hm.put("lookuptxt_deptCode", buisnessElement);
		hm.put("lookuptxt_fromGLCode", buisnessElement);
		hm.put("lookuptxt_toGLCode", buisnessElement);
	    }
	    else if(RepConstantsCommon.CALC_TYPE_BALANCE.equals(glCO.getGlstmpltGlsDetVO().getCALC_TYPE()))
	    {
		dispChkClosingEntry.setIS_VISIBLE(BigDecimal.ONE);
	    }
	    else
	    {
		dispChkClosingEntry.setIS_VISIBLE(BigDecimal.ZERO);
	    }
	    hm.put("exclClosingEntry", dispChkClosingEntry);
	    setAdditionalScreenParams(hm);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	 return SUCCESS;
    }

    /**
     * method called when deleting a GL
     * 
     * @return
     * @throws JSONException
     */
    public String deleteGL() throws JSONException // load the grid
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BigDecimal compCode = sessionCO.getCompanyCode();
	    BigDecimal glConcatKeyNb = glCO.getConcatKey();
	    BigDecimal tempCode = glCO.getGlstmpltGlsDetVO().getCODE();
	    BigDecimal lineNb = glCO.getGlstmpltGlsDetVO().getLINE_NBR();
	    String lineConcatKey = compCode.toString() + tempCode.toString() + lineNb.toString();
	    GLSTMPLTCO glstmpltCO = getglstmpltCOById(lineConcatKey);
	    LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> addGLMap = glstmpltCO.getAddGLMap();
	    LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> dbGLMap = glstmpltCO.getDbGLMap();
	    HashMap<BigDecimal, GLSTMPLTGLSDETCO> delGLMap = glstmpltCO.getDelGLMap();
	    GLSTMPLTGLSDETCO delGLCO = addGLMap.get(glConcatKeyNb);
	    int maxGlSubLineNbOld = glstmpltCO.getMaxGLSubLineNb();
	    if(glstmpltCO.getMaxGLSubLineNb() > 0)
	    {
		glstmpltCO.setMaxGLSubLineNb(--maxGlSubLineNbOld);
	    }
	    // in this case, the user didn't try to add previously any line,so
	    // get the max from db and substract 1 from it
	    else if(glstmpltCO.getMaxGLSubLineNb() == 0)
	    {
		GLSTMPLTSC lineSC = new GLSTMPLTSC();
		lineSC.setCODE(tempCode);
		lineSC.setCOMP_CODE(compCode);
		lineSC.setLINE_NBR(lineNb);
		GLSTMPLTGLSDETCO checkSubglstmpltCO = templateBO.getMaxGLSubLineNb(lineSC);
		// -2 because only the first time it will substract 2 then it
		// will be -1.-2 because when adding there will be a +1
		glstmpltCO
			.setMaxGLSubLineNb((checkSubglstmpltCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue() - 2));
	    }
	    if(delGLCO == null)
	    {
		GLSTMPLTGLSDETCO glCOToDel = dbGLMap.get(glConcatKeyNb);
		dbGLMap.remove(glConcatKeyNb);
		delGLMap.put(glConcatKeyNb, glCOToDel);

	    }
	    else
	    {
		addGLMap.remove(glConcatKeyNb);
	    }
	    // iterate over the lines and adjust the sublinenumbers
	    int subLineNbRemoved = glCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue();
	    LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> temporaryAddMap = new LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>();
	    String newKey;
	    GLSTMPLTGLSDETCO tempCO;
	    Iterator<Map.Entry<BigDecimal, GLSTMPLTGLSDETCO>> itFmap = dbGLMap.entrySet().iterator();
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, GLSTMPLTGLSDETCO> entry = itFmap.next();
		tempCO = entry.getValue();
		if(tempCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue() > subLineNbRemoved)
		{
		    int currentNumber = tempCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue();
		    tempCO.getGlstmpltGlsDetVO().setLINE_NBR_DET(new BigDecimal(currentNumber - 1));
		    newKey = compCode.toString() + tempCode.toString() + lineNb.toString() + (currentNumber - 1);
		    tempCO.setConcatKey(new BigDecimal(newKey));
		    temporaryAddMap.put(new BigDecimal(newKey), tempCO);
		}
		else
		{
		    temporaryAddMap.put(entry.getKey(), tempCO);
		}
	    }
	    glstmpltCO.setDbGLMap((LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>) temporaryAddMap.clone());
	    temporaryAddMap.clear();
	    itFmap = addGLMap.entrySet().iterator();
	    int newSubLineNb;
	    while(itFmap.hasNext())
	    {
		Entry<BigDecimal, GLSTMPLTGLSDETCO> entry = itFmap.next();
		tempCO = entry.getValue();

		// since the key contains the sublinenumber, have to update also
		if(tempCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue() > subLineNbRemoved)
		{
		    newSubLineNb = tempCO.getGlstmpltGlsDetVO().getLINE_NBR_DET().intValue();
		    tempCO.getGlstmpltGlsDetVO().setLINE_NBR_DET(BigDecimal.valueOf(--newSubLineNb));
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
	    glstmpltCO.setAddGLMap((LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>) temporaryAddMap.clone());

	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;
    }

    /**
     * method that gets a gl by its id
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
     * method that refreshes a gl form
     * 
     * @return
     * @throws Exception
     */
    public String refreshGlForm() throws Exception
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    ReportingSessionCO repSessionCO = returnReportingSessionObject(this.get_pageRef());
	    GLSTMPLTCO allTempl = repSessionCO.getAllTempl();
	    SYS_PARAM_LOV_TRANSVO sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
	    sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(4)); // 4= calculation
	    // type (check
	    // table
	    // sys_param_lov_type
	    // in the xls file)
	    sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	    calcTypeArrList = commonLookupBO.getLookupList(sysParamLovVO);
	    // fill a map with key and value of the calcultion type in order to
	    // get the value of the selected key when needed
	    if(allTempl != null && allTempl.getCalcTypeMap().size() == 0)
	    {
		HashMap<String, String> calcTypeMap = allTempl.getCalcTypeMap();
		for(int i = 0; i < calcTypeArrList.size(); i++)
		{
		    SYS_PARAM_LOV_TRANSVO calcTypeVO = (SYS_PARAM_LOV_TRANSVO) calcTypeArrList.get(i);
		    calcTypeMap.put(calcTypeVO.getVALUE_CODE(), calcTypeVO.getVALUE_DESC());
		}
	    }
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}

	return "successGLFrm";
    }

    public Object getModel()
    {
	return glCO;
    }
}
