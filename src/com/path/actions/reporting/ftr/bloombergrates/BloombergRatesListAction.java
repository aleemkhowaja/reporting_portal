package com.path.actions.reporting.ftr.bloombergrates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.core.currency.CurrencyBO;
import com.path.bo.reporting.ftr.bloombergrates.BloombergRatesBO;
import com.path.dbmaps.vo.CURRENCIESVO;
import com.path.dbmaps.vo.FTR_RATE_UPLOADVO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesCO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesSC;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.vo.GridUpdates;
import com.path.struts2.lib.common.base.GridBaseAction;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.core.currency.CurrencySC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesListAction.java used to
 */
public class BloombergRatesListAction extends GridBaseAction
{
    private BloombergRatesBO bloombergRatesBO;
    private File upload;
    BloombergRatesSC bloombergRatesSC = new BloombergRatesSC();
    private String updatesBlgRatesList;
    private BigDecimal compCode;
    private BigDecimal curCode;
    private Date valueDate;
    private CommonLibBO commonLibBO;
    private CurrencyBO currencyBO;
    
    

    public void setCurrencyBO(CurrencyBO currencyBO)
    {
        this.currencyBO = currencyBO;
    }

    public BigDecimal getCompCode()
    {
        return compCode;
    }

    public void setCompCode(BigDecimal compCode)
    {
        this.compCode = compCode;
    }

    public BigDecimal getCurCode()
    {
        return curCode;
    }

    public void setCurCode(BigDecimal curCode)
    {
        this.curCode = curCode;
    }

    public Date getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(Date valueDate)
    {
        this.valueDate = valueDate;
    }

    public String getUpdatesBlgRatesList()
    {
        return updatesBlgRatesList;
    }

    public void setUpdatesBlgRatesList(String updatesBlgRatesList)
    {
        this.updatesBlgRatesList = updatesBlgRatesList;
    }

    public BloombergRatesSC getBloombergRatesSC()
    {
        return bloombergRatesSC;
    }

    public void setBloombergRatesSC(BloombergRatesSC bloombergRatesSC)
    {
        this.bloombergRatesSC = bloombergRatesSC;
    }

    public File getUpload()
    {
        return upload;
    }

    public void setUpload(File upload)
    {
        this.upload = upload;
    }

    public void setBloombergRatesBO(BloombergRatesBO bloombergRatesBO)
    {
	this.bloombergRatesBO = bloombergRatesBO;
    }
    

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
        this.commonLibBO = commonLibBO;
    }

    /**
     * load bloomberg rate grid
     */
    public String loadBloombergRatesGrid() throws BaseException
    {
	try
	{
	    HashMap<String,String> hmDate = new HashMap<String,String>();
	    hmDate.put("VALUE_DATE", "VALUE_DATE");
	    bloombergRatesSC.setDateSearchCols(hmDate);
	    copyproperties(bloombergRatesSC);
	    SessionCO sessionCO = returnSessionObject();
	    bloombergRatesSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    List<BloombergRatesCO> lst = bloombergRatesBO.retBloombergRatesList(bloombergRatesSC);
	    int lstCnt = bloombergRatesBO.retBloombergRatesCount(bloombergRatesSC);
	    setRecords(lstCnt);
	    setGridModel(lst);

	}
	catch(Exception e)
	{
	    handleException(e, "Error in load bloomberg rates grid", "");
	}
	return SUCCESS;
    }  
    
    
    public String saveBlgRates() throws Exception
    {
	if(updatesBlgRatesList != null && !updatesBlgRatesList.equals(""))
	{
	    try
	    {
		GridUpdates gu = getGridUpdates(updatesBlgRatesList, BloombergRatesCO.class, true);
		SessionCO sessionCO = returnSessionObject();
		AuditRefCO refCO = initAuditRefCO();
		 
		String appName   	= sessionCO.getCurrentAppName();
		String lang		= sessionCO.getLanguage();
		
		//apply audit
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.BLG_RATES_KEY_REF);
		bloombergRatesBO.saveBlgRates(gu.getLstAllRec(),refCO, get_pageRef(), sessionCO.getCompanyCode(),appName,lang);
	    }

	    catch(Exception e)
	    {
		handleException(e, null, null);
	    }
	}
	return SUCCESS;
    }
    
    public String uploadBloombergRates()
    {
	String result  =null;
	try
	{
	    SessionCO sessionCO = returnSessionObject();
	    BloombergRatesSC blRtSC = new BloombergRatesSC();
	    //BufferedReader in = new BufferedReader(new FileReader(upload));
	    
	    InputStreamReader isr = new InputStreamReader(new FileInputStream(new PathFileSecure(upload.toString())), FileUtil.DEFAULT_FILE_ENCODING);
	    BufferedReader in = new BufferedReader (isr);
	    String str;
	    //limit the size of the line to be below of 200 MB = 200000000 bytes 
	    str = PathFileSecure.readLine(in, 200000000);
	  
	    blRtSC.setCOMP_CODE(sessionCO.getCompanyCode());
	    CurrencySC currencySC = new CurrencySC();
	    currencySC.setCompCode(sessionCO.getCompanyCode());
	    //List<BloombergRatesCO> lstCurrency = bloombergRatesBO.retCurrencyLst(blRtSC);
	    currencySC.setNbRec(-1);
	    List<CURRENCIESVO> lstCurrency = currencyBO.currencyList(currencySC);
	    HashMap<String,BigDecimal> currencyHm = new HashMap<String,BigDecimal>();
	   
	    for(int i=0;i<lstCurrency.size();i++)
	    {
		currencyHm.put(lstCurrency.get(i).getBRIEF_DESC_ENG(),lstCurrency.get(i).getCURRENCY_CODE());
	    }
	    
	    if(str == null)
	    {
		result = ERROR_ACTION;
		CommonLibSC sc = new CommonLibSC();
		sc.setAppName(sessionCO.getCurrentAppName());
		sc.setProgRef(get_pageRef());
		sc.setLanguage(sessionCO.getLanguage());
		sc.setKeyLabelCode("blrgRates.exceptionMsgEmpty._key");
		throw new BOException(commonLibBO.returnKeyLabelTrans(sc));
		
	    }
	    else
	    {
		String[] ar=str.split("\\t");
		if(ar.length!=6 || !ar[0].equals("CCY") || !ar[1].equals("VALUE") || !ar[2].equals("DF") || !ar[3].equals("RATE") || !ar[4].equals("ADJ") || !ar[5].equals("NET"))
		{
		    result = ERROR_ACTION;
		    CommonLibSC sc = new CommonLibSC();
		    sc.setAppName(sessionCO.getCurrentAppName());
		    sc.setProgRef(get_pageRef());
		    sc.setLanguage(sessionCO.getLanguage());
		    sc.setKeyLabelCode("blrgRates.exceptionMsgHeader._key");
		    throw new BOException(commonLibBO.returnKeyLabelTrans(sc));
		}
	    }
	    
	    HashMap<String, BloombergRatesCO> bloombergHm = new HashMap<String, BloombergRatesCO>();
	    //limit the size of the line to be below of 200 MB = 200000000 bytes
	    while((str = PathFileSecure.readLine(in, 200000000)) != null)
	    {
		String[] ar=str.split("\\t");
		BloombergRatesCO bloombergRatesCO = new BloombergRatesCO();
		if(currencyHm.containsKey(ar[0]))
		{
		    bloombergRatesCO.setCurrencyDesc(ar[0]);
		    bloombergRatesCO.getFtrRateUploadVO().setCURRENCY_CODE(currencyHm.get(ar[0]));
		    try{
			bloombergRatesCO.getFtrRateUploadVO().setVALUE_DATE(DateUtil.parseDate(ar[1], "dd/MM/yyyy"));//DateUtil.parseDate(newDateUpStr, "dd/MM/yyyy hh:mm:ss");
		    }
		    catch(Exception e)
		    {
			result = ERROR_ACTION;
			CommonLibSC sc = new CommonLibSC();
			sc.setAppName(sessionCO.getCurrentAppName());
			sc.setProgRef(get_pageRef());
			sc.setLanguage(sessionCO.getLanguage());
			sc.setKeyLabelCode("blrgRates.exceptionMsgForDate._key");
			throw new BOException(commonLibBO.returnKeyLabelTrans(sc), e);
		    }
		    try{
			
			bloombergRatesCO.getFtrRateUploadVO().setDISC_FACTOR(new BigDecimal(ar[2]));
		    }
		    catch(Exception e)
		    {
			result = ERROR_ACTION;
			CommonLibSC sc = new CommonLibSC();
			sc.setAppName(sessionCO.getCurrentAppName());
			sc.setProgRef(get_pageRef());
			sc.setLanguage(sessionCO.getLanguage());
			sc.setKeyLabelCode("blrgRates.exceptionMsgForDiscFactor._key");
			throw new BOException(commonLibBO.returnKeyLabelTrans(sc), e);
		    }
		    
		    
		    try
		    {
			if(ar[3].contains("%"))
			{
			    bloombergRatesCO.getFtrRateUploadVO().setRATE( new BigDecimal(ar[3].substring(0, ar[3].length() - 1)));
			}
			else
			{
			    bloombergRatesCO.getFtrRateUploadVO().setRATE(new BigDecimal(ar[3]));
			}

		    }
		    catch(Exception e)
		    {
			result = ERROR_ACTION;
			CommonLibSC sc = new CommonLibSC();
			sc.setAppName(sessionCO.getCurrentAppName());
			sc.setProgRef(get_pageRef());
			sc.setLanguage(sessionCO.getLanguage());
			sc.setKeyLabelCode("blrgRates.exceptionMsgForRate._key");
			throw new BOException(commonLibBO.returnKeyLabelTrans(sc), e);
		    }

		    try
		    {
			if(ar[4].contains("%"))
			{
			    bloombergRatesCO.getFtrRateUploadVO().setADJUST_RATE(new BigDecimal(ar[4].substring(0, ar[4].length() - 1)));
			}
			else
			{
			    bloombergRatesCO.getFtrRateUploadVO().setADJUST_RATE(new BigDecimal(ar[4]));
			}
		    }
		    catch(Exception e)
		    {
			result = ERROR_ACTION;
			CommonLibSC sc = new CommonLibSC();
			sc.setAppName(sessionCO.getCurrentAppName());
			sc.setProgRef(get_pageRef());
			sc.setLanguage(sessionCO.getLanguage());
			sc.setKeyLabelCode("blrgRates.exceptionMsgForAdjustRate._key");
			throw new BOException(commonLibBO.returnKeyLabelTrans(sc), e);
		    }

		    try
		    {
			if(ar[5].contains("%"))
			{
			    bloombergRatesCO.getFtrRateUploadVO().setNET_RATE(new BigDecimal(ar[5].substring(0, ar[5].length() - 1)));
			}
			else
			{
			    bloombergRatesCO.getFtrRateUploadVO().setNET_RATE(new BigDecimal(ar[5]));
			}
		    }
		    catch(Exception e)
		    {
			result = ERROR_ACTION;
			CommonLibSC sc = new CommonLibSC();
			sc.setAppName(sessionCO.getCurrentAppName());
			sc.setProgRef(get_pageRef());
			sc.setLanguage(sessionCO.getLanguage());
			sc.setKeyLabelCode("blrgRates.exceptionMsgForNetRate._key");
			throw new BOException(commonLibBO.returnKeyLabelTrans(sc), e);
		    }
		    bloombergRatesCO.getFtrRateUploadVO().setCOMP_CODE(sessionCO.getCompanyCode());
		    bloombergHm.put(bloombergRatesCO.getCurrencyDesc()+bloombergRatesCO.getFtrRateUploadVO().getCOMP_CODE()+bloombergRatesCO.getFtrRateUploadVO().getVALUE_DATE(), bloombergRatesCO);
		}
	    }
	    List<BloombergRatesCO> bloombergList = new ArrayList<BloombergRatesCO>(bloombergHm.values());
	    in.close();
	    if(!bloombergList.isEmpty())
	    {
		try{
		    bloombergRatesBO.saveUploadBloombergRates(bloombergList);
		}
		catch(BaseException e)
		{
		    result = ERROR_ACTION;
		    CommonLibSC sc = new CommonLibSC();
		    sc.setAppName(sessionCO.getCurrentAppName());
		    sc.setProgRef(get_pageRef());
		    sc.setLanguage(sessionCO.getLanguage());
		    sc.setKeyLabelCode("blrgRates.exceptionMsgForSQL._key");
		    throw new BOException(commonLibBO.returnKeyLabelTrans(sc), e);
		}
	    }
	    
	}
	catch(Exception e)
	{
	    handleException(e, null,null);
	    return ERROR_ACTION;
	}

	return result;
    }
    

    public String retrieveBlbgRates() throws Exception
    {
	try
	{
	    FTR_RATE_UPLOADVO ftrRateUpVO = new FTR_RATE_UPLOADVO();
	    SessionCO sessionCO = returnSessionObject();
	    ftrRateUpVO.setCOMP_CODE(sessionCO.getCompanyCode());
	    ftrRateUpVO.setCURRENCY_CODE(curCode);
	    String dateStr = DateUtil.format(getValueDate(),"dd/MM/yyyy");
	    ftrRateUpVO.setVALUE_DATE(DateUtil.parseDate(dateStr,"dd/MM/yyyy"));
	    
	    applyRetrieveAudit(AuditConstant.BLG_RATES_KEY_REF, ftrRateUpVO, ftrRateUpVO);
	}
	catch(Exception e)
	{
	    handleException(e, null, null);
	}
	return SUCCESS;

    }
 
    public Object getModel()
    {
	return bloombergRatesSC;
    }

	
}
