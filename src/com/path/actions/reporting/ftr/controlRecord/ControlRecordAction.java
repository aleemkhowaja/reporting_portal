package com.path.actions.reporting.ftr.controlRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.controlRecord.ControlRecordBO;
import com.path.dbmaps.vo.BTR_CONTROLVO;
import com.path.dbmaps.vo.COUNTRYVO;
import com.path.dbmaps.vo.CURRENCIESVO;
import com.path.dbmaps.vo.SYS_PARAM_LANGUAGESVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.COUNTRYSC;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.common.select.SelectSC;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;

public class ControlRecordAction extends ReportingGridBaseAction
{
    private ControlRecordBO controlRecordBO;
    private BTR_CONTROLCO btrControlCO = new BTR_CONTROLCO();
    private List langList =  new ArrayList<SYS_PARAM_LANGUAGESVO>();
    private final List tradeValueList = new ArrayList<String>();
    private final List tradeList = new ArrayList<SYS_PARAM_LOV_TRANSVO>();
    private ArrayList weekDaysList = new ArrayList();
    private CommonLookupBO commonLookupBO;
    private String currencyDesc;
    private String mode;
    private COUNTRYVO countryVO = new COUNTRYVO();
    
    

    public ArrayList getWeekDaysList()
    {
	returnWeekDaysNamesList();
        return weekDaysList;
    }


    public List getTradeList()
    {
	returnTradeList();
        return tradeList;
    }

    public List getTradeValueList()
    {
        return tradeValueList;
    }


    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
        this.commonLookupBO = commonLookupBO;
    }

    public COUNTRYVO getCountryVO()
    {
        return countryVO;
    }

    public void setCountryVO(COUNTRYVO countryVO)
    {
        this.countryVO = countryVO;
    }

    public List getLangList()
    {
	 try
	 {
	        SessionCO sessionCO = returnSessionObject();
		SelectSC sc= new SelectSC();
		sc.setLanguage(sessionCO.getLanguage());
		sc.setLovTypeId(ConstantsCommon.LANGUAGES_LOV_TYPE);
		langList = returnCommonLibBO().returnLanguages(sc);
	   
	    }
	    catch(BaseException e)
	    {
		log.error(e,"");
	    }
        return langList;
    }

    public void setLangList(List langList)
    {
        this.langList = langList;
    }

    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public String getCurrencyDesc()
    {
        return currencyDesc;
    }

    public void setCurrencyDesc(String currencyDesc)
    {
        this.currencyDesc = currencyDesc;
    }



    public BTR_CONTROLCO getBtrControlCO()
    {
        return btrControlCO;
    }

    public void setBtrControlCO(BTR_CONTROLCO btrControlCO)
    {
        this.btrControlCO = btrControlCO;
    }

    public void setControlRecordBO(ControlRecordBO controlRecordBO)
    {
        this.controlRecordBO = controlRecordBO;
    }
    


    /*
     * This function loads the form object on the page's load
     */
    public String execute()
    {
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BTR_CONTROLSC btSC = new BTR_CONTROLSC();
	    btSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    //retrieve control record
	    btrControlCO =controlRecordBO.retrieveControlRecordData(btSC);
	    //audit
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.CONTROL_RECORD_KEY_REF);
	    refCO.setProgRef(get_pageRef());
	    refCO.setAppName(sessionCO.getCurrentAppName());
	    
	    
	    CURRENCIESVO currenciesVO = new CURRENCIESVO();
	    if(btrControlCO==null)
	    {
		mode = "SAVE";
		refCO.setOperationType(AuditConstant.CREATED);
	    }
	    else
	    {
		btrControlCO.getBtrControlVO().setAuditRefCO(refCO);
		mode = "UPDATE";
		refCO.setOperationType(AuditConstant.UPDATE);
		//currency lkp
		currenciesVO.setCOMP_CODE(btrControlCO.getBtrControlVO().getCOMP_CODE());
		currenciesVO.setCURRENCY_CODE(btrControlCO.getBtrControlVO().getUSD_CY_CODE());
		if(currenciesVO.getCOMP_CODE() != null && currenciesVO.getCURRENCY_CODE() != null)
		{
		    CURRENCIESVO currencyVO = returnCommonLibBO().returnCurrency(currenciesVO);
		    if(currencyVO==null)
		    {
			btrControlCO.getBtrControlVO().setUSD_CY_CODE(null);
		    }
		    else
		    {
			currencyDesc = currencyVO.getBRIEF_DESC_ENG();
		    }
		}
		
		//country lkp
		COUNTRYSC countrySC = new COUNTRYSC();
    		countrySC.setCOUNTRY_CODE(btrControlCO.getBtrControlVO().getEMP_BASE_COUNTRY());
    		countrySC.setCOMP_CODE(sessionCO.getCompanyCode());
    		if(countrySC.getCOUNTRY_CODE()!= null)
    		{
    		    countryVO.setBRIEF_DESC_ENG(commonLookupBO.getCountryDesc(countrySC));    		
    		}
    		if(btrControlCO.getBtrControlVO().getBTR_WEEKEND_DAY()== null)
    		{
    		    btrControlCO.getBtrControlVO().setBTR_WEEKEND_DAY(new BigDecimal(2));
    		}
    		  		
		if((RepConstantsCommon.FTR_LANGUAGE_ENGLISH).equals(btrControlCO.getBtrControlVO().getREPORT_LANG()))
		{
		    btrControlCO.getBtrControlVO().setREPORT_LANG(ConstantsCommon.LANGUAGE_ENGLISH);
		}
		else if(RepConstantsCommon.FTR_LANGUAGE_ARABIC.equals(btrControlCO.getBtrControlVO().getREPORT_LANG()))
		{
		    btrControlCO.getBtrControlVO().setREPORT_LANG(ConstantsCommon.LANGUAGE_ARABIC);
		}
		else if(RepConstantsCommon.FTR_LANGUAGE_FRENCH.equals(btrControlCO.getBtrControlVO().getREPORT_LANG()))
		{
		    btrControlCO.getBtrControlVO().setREPORT_LANG(ConstantsCommon.LANGUAGE_FRENCH);
		}
	    }  
	    //languages DDL
	    langList = getLangList();
	    //audit
	    if(btrControlCO!=null)
	    {
		applyRetrieveAudit(AuditConstant.CONTROL_RECORD_KEY_REF, btrControlCO.getBtrControlVO(), btrControlCO.getBtrControlVO());
	    }
	    set_showSmartInfoBtn("false");
	    }
	
	catch(Exception e)
	{
	    handleException(e, "ctrlRecord.errorInsertUpdate.exeptionMsg._key",null);
	}
	    return "successMaint";
    }
    

    /*
     *This function is called when inserting or updating for the control record form 
     */
    public String updateControlRecordData()
    {
	try
	{
	  //for the mode
	    SessionCO sessionCO = returnSessionObject();
	    BTR_CONTROLSC btSC = new BTR_CONTROLSC();
	    btSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    //retrieve control record
	    BTR_CONTROLCO existCODb =controlRecordBO.retrieveControlRecordData(btSC);
	    if(existCODb==null)
	    {
		mode="SAVE";
	    }
	    else
	    {
		mode="UPDATE";
	    }
	    btrControlCO.getBtrControlVO().setCOMP_CODE(sessionCO.getCompanyCode());
	    btrControlCO.getBtrControlVO().setREPORT_LANG(btrControlCO.getBtrControlVO().getREPORT_LANG().substring(0,1));
	    	    
	    AuditRefCO refCO = initAuditRefCO();
	    refCO.setKeyRef(AuditConstant.CONTROL_RECORD_KEY_REF);
	    refCO.setProgRef(get_pageRef());
	    refCO.setAppName(sessionCO.getCurrentAppName());
	    btrControlCO.getBtrControlVO().setAuditRefCO(refCO);
	    
	    if("UPDATE".equals(mode))
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		BTR_CONTROLVO oldVO = (BTR_CONTROLVO) returnAuditObject(BTR_CONTROLVO.class);
		refCO.setAuditObj(oldVO);
	    }
	    else
	    {
		//if inserting for the first time
		refCO.setOperationType(AuditConstant.CREATED);
	    }
	   
	    controlRecordBO.updateBtrRecord(btrControlCO,mode);
	    //added for the date_updated
	    btrControlCO.getBtrControlVO().setDATE_UPDATED(((controlRecordBO.retrieveControlRecordData(btSC)).getBtrControlVO().getDATE_UPDATED()));
	    btrControlCO.getBtrControlVO().getAuditRefCO().setAuditObj(null);
	    if((RepConstantsCommon.FTR_LANGUAGE_ENGLISH).equals(btrControlCO.getBtrControlVO().getREPORT_LANG()))
	    {
		btrControlCO.getBtrControlVO().setREPORT_LANG(ConstantsCommon.LANGUAGE_ENGLISH);
	    }
	    else if(RepConstantsCommon.FTR_LANGUAGE_ARABIC.equals(btrControlCO.getBtrControlVO().getREPORT_LANG()))
	    {
		btrControlCO.getBtrControlVO().setREPORT_LANG(ConstantsCommon.LANGUAGE_ARABIC);
	    }
	    else if(RepConstantsCommon.FTR_LANGUAGE_FRENCH.equals(btrControlCO.getBtrControlVO().getREPORT_LANG()))
	    {
		btrControlCO.getBtrControlVO().setREPORT_LANG(ConstantsCommon.LANGUAGE_FRENCH);
	    }
	    putAuditObject(btrControlCO.getBtrControlVO());
	    if(getAuditTrxNbr()==null || "".equals(getAuditTrxNbr()))
	    {
		setAuditTrxNbr("000"+sessionCO.getCompanyCode()+"0000");
	    }
	    	    
	}
	catch(BOException e)
	{	
	    //log.error(e, "ERROR in concurrent access");
	    handleException(e, "Concurrent access","reporting.concurrentAccess");
	    return "error";
	}
	catch(Exception e)
	{
	    //log.error(e, "ERROR in Control Record Action");
	    handleException(e, "ctrlRecord.errorInsertUpdate.exeptionMsg._key",null);
	}
	return "successFrm";
    }
    
    /*
     * This method is used to fill the week/days drpdwn with data
     */
    public void returnWeekDaysNamesList() 
    {
	SessionCO sessionCO = returnSessionObject();
	SYS_PARAM_LOV_TRANSVO sysParamLovVO= new SYS_PARAM_LOV_TRANSVO();
	sysParamLovVO.setLOV_TYPE_ID(new BigDecimal(79)); 
	sysParamLovVO.setLANG_CODE(sessionCO.getLanguage());
	try
	{
	    weekDaysList=(ArrayList) commonLookupBO.getLookupList(sysParamLovVO);
	}
	catch(BaseException e)
	{
	    handleException(e, "ctrlRecord.errorInsertUpdate.exeptionMsg._key",null);
	}
    }
    
    
    /*
     * This method is used to fill the trade/value list with data
     */
    public void returnTradeList() 
	{
		//fill frequency DDL
		SYS_PARAM_LOV_TRANSVO sysParamLovVO;
		String[] code = {"T","D"};
		String[] desc = {getText("ctrlRecord.tradeDate"), getText("ctrlRecord.valueDate")};
		
	    for(int i=0; i< code.length; i++)
	    {
			sysParamLovVO = new SYS_PARAM_LOV_TRANSVO();
			sysParamLovVO.setVALUE_CODE(code[i]);
			sysParamLovVO.setVALUE_DESC(desc[i]);
			tradeList.add(sysParamLovVO);
	    }
	}



}
