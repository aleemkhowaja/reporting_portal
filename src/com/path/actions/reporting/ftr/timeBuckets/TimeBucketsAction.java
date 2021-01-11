package com.path.actions.reporting.ftr.timeBuckets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.ftr.timeBuckets.TimeBucketsBO;
import com.path.dbmaps.vo.FTR_TIME_BUCKETSVO;
import com.path.lib.vo.GridUpdates;
import com.path.reporting.struts2.lib.common.base.ReportingGridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSSC;

public class TimeBucketsAction extends ReportingGridBaseAction implements  ServletResponseAware
{
	private FTR_TIME_BUCKETSSC ftrTimeBucketsSC = new FTR_TIME_BUCKETSSC();
	private final FTR_TIME_BUCKETSVO ftrTimeBucketsVO = new FTR_TIME_BUCKETSVO();
	private TimeBucketsBO timeBucketsBO;
	private List<FTR_TIME_BUCKETSCO> ftrTimeBucketsCOList;
	private FTR_TIME_BUCKETSCO ftrTimeBucketsCO = new FTR_TIME_BUCKETSCO();
	private String repRef;
	private BigDecimal currencyCode;
	private BigDecimal lineNo;
	private String mode;
	private String updates;
	private String concatKey;
	
	private String updates_1;
	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUpdates_1() {
		return updates_1;
	}

	public void setUpdates_1(String updates_1) {
		this.updates_1 = updates_1;
	}

	public String getUpdates() {
		return updates;
	}

	public void setUpdates(String updates) {
		this.updates = updates;
	}

	public FTR_TIME_BUCKETSSC getFtrTimeBucketsSC() {
		return ftrTimeBucketsSC;
	}

	public void setFtrTimeBucketsSC(FTR_TIME_BUCKETSSC ftrTimeBucketsSC) {
		this.ftrTimeBucketsSC = ftrTimeBucketsSC;
	}

	public void setTimeBucketsBO(TimeBucketsBO timeBucketsBO) {
		this.timeBucketsBO = timeBucketsBO;
	}

	public List<FTR_TIME_BUCKETSCO> getFtrTimeBucketsCOList() {
		return ftrTimeBucketsCOList;
	}

	public void setFtrTimeBucketsCOList(
			List<FTR_TIME_BUCKETSCO> ftrTimeBucketsCOList) {
		this.ftrTimeBucketsCOList = ftrTimeBucketsCOList;
	}

	public FTR_TIME_BUCKETSCO getFtrTimeBucketsCO() {
		return ftrTimeBucketsCO;
	}

	public void setFtrTimeBucketsCO(FTR_TIME_BUCKETSCO ftrTimeBucketsCO) {
		this.ftrTimeBucketsCO = ftrTimeBucketsCO;
	}

	public String getRepRef() {
		return repRef;
	}

	public void setRepRef(String repRef) {
		this.repRef = repRef;
	}

	public BigDecimal getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(BigDecimal currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
     * Method called when loading the grid 'timeBucketsTable_${_pageRef}'
     */
	public String loadGrid() throws JSONException{
		try
		{
			SessionCO sessionCO = returnSessionObject();
			copyproperties(ftrTimeBucketsSC);
			ftrTimeBucketsSC.setPreferredLanguage(sessionCO.getLanguage());
			
			if(checkNbRec(ftrTimeBucketsSC))
			{
				setRecords(timeBucketsBO.getTimeBucketsCount(ftrTimeBucketsSC));
			}
			ftrTimeBucketsCOList = timeBucketsBO.getTimeBucketsList(ftrTimeBucketsSC);			
			setGridModel(ftrTimeBucketsCOList);
		}
		catch(Exception e)
		{
		    //log.error(e, "Error in method loadGrid in TimeBucketsAction");
		    handleException(e, "timeBuckets.errorGrid.exceptionMsg._key",null);
		}
		return "json";
    }
	
	/**
     * Method called when retrieving the grid 'timeBucketsTable_${_pageRef}'
     */
	public String retrieveTimeBuckets()throws Exception
	{
		try
		{
			  	SessionCO sessionCO = returnSessionObject();
		    	FTR_TIME_BUCKETSSC ftrTimeBucketsSC = new FTR_TIME_BUCKETSSC();
		    	ftrTimeBucketsSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    	ftrTimeBucketsSC.setREP_REF(repRef);
		    	ftrTimeBucketsSC.setCURRENCY_CODE(currencyCode);
		    	
		    	//FTR_TIME_BUCKETSVO vo=timeBucketsBO.retrieveTimeBuckets(ftrTimeBucketsSC);//Commented by LBedrane - 19/03/2018 638947 - Currency description field issue 
		    	//ftrTimeBucketsCO.setFtrtimebucketsVO(vo);//Commented by LBedrane - 19/03/2018 638947 - Currency description field issue 
		    	ftrTimeBucketsCO = timeBucketsBO.retrieveTimeBuckets(ftrTimeBucketsSC);//LBedrane - 19/03/2018 638947 - Currency description field issue
		    	mode="update";
		}
		catch(Exception e)
		{
			//log.error(e,"Exception in TimeBucketsAction");
		    handleException(e, "timeBuckets.errorRetrieving.exceptionMsg._key", null);
		}
		return "successFrm";
	}
	
	/**
     * Method called to retrieve the 'timeBucketsDetailsGrid_${_pageRef}' of selected row in the grid 'timeBucketsTable_${_pageRef}'
     */ 
	public String retrieveTimeBucketsListDetails()throws Exception
	{
		try
		{
			//Declare and set the 'ReportingSessionCO' 
			ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
			//Use the HashMap to save the list of Time Buckets when retrieved from Database,
			//Then do any change and save it, we use the HashMap to get the old values of the modified
			//Time Buckets to save it in the audit
			HashMap dbTimeBucketsDetails = new HashMap();
			
			//Declare and initialize the 'sessionCO'
			SessionCO sessionCO = returnSessionObject();
			
			FTR_TIME_BUCKETSSC ftrTimeBucketsSC = new FTR_TIME_BUCKETSSC();
			copyproperties(ftrTimeBucketsSC);		
			
			//If Report Reference and Currency Code in the form are not null, then execute the save
			if(repRef==null || currencyCode==null)
			{
				setRecords(0);
				setGridModel(new ArrayList<FTR_TIME_BUCKETSCO>());
			}
			else
			{
				//Retrieve the list of Time Buckets Details
		    	ftrTimeBucketsSC.setCOMP_CODE(sessionCO.getCompanyCode());
		    	ftrTimeBucketsSC.setREP_REF(repRef);
		    	ftrTimeBucketsSC.setCURRENCY_CODE(currencyCode);
		    	setRecords(timeBucketsBO.getTimeBucketsDetailsCount(ftrTimeBucketsSC));
		    	ftrTimeBucketsCOList = timeBucketsBO.retTimeBucketsListDetails(ftrTimeBucketsSC);
		    	
		    	//Loop through the retrieved Time Buckets Details and set the concatKey of each one,
		    	//Then save the list in the HashMap 'dbTimeBucketsDetails'
		    	for(int i=0;i<ftrTimeBucketsCOList.size();i++)
		    	{
		    		ftrTimeBucketsCO = ftrTimeBucketsCOList.get(i);
		    		
		    		concatKey = ftrTimeBucketsCO.getFtrtimebucketsVO().getCOMP_CODE().toString();
		    		concatKey += '_' + ftrTimeBucketsCO.getFtrtimebucketsVO().getREP_REF();
		    		concatKey += '_' + ftrTimeBucketsCO.getFtrtimebucketsVO().getCURRENCY_CODE().toString();
		    		concatKey += '_' + ftrTimeBucketsCO.getFtrtimebucketsVO().getLINE_NO().toString();
		    		concatKey += '_' + ftrTimeBucketsCO.getBRIEF_DESC_ENG().toString();//LBedrane - 19/03/2018 638947 - Currency description field issue
		    				    		
		    		ftrTimeBucketsCO.setConcatKey(concatKey);
		    		dbTimeBucketsDetails.put(concatKey, ftrTimeBucketsCO.getFtrtimebucketsVO());
		    	}
		    	
		    	//Set the HashMap list of old 'Time Buckets Details' in the session  'ReportingSessionCO'
		    	//So we can use it when saving the audit of the updated 'Time Buckets Details' in 'TimeBucketsBOImpl'
		    	repSessionCO.setTimeBucketsOld(dbTimeBucketsDetails);
		    	//Set the Model of the grid 'timeBucketsDetailsGrid_${_pageRef}'
		    	setGridModel(ftrTimeBucketsCOList);
			}
			
	    	
	    	mode="update";
		}
		catch(Exception e)
		{
			//log.error(e,"Exception in TimeBucketsAction");
		    handleException(e, "timeBuckets.errorRetrievingDetails.exceptionMsg._key", null);
		}
		return "json";
	}
	
	/**
     * Method called to empty the form 'TimeBucketsMaint.jsp'
     */
	public String emptyTimeBucketsFrm()throws Exception
	{
		ftrTimeBucketsCO = new FTR_TIME_BUCKETSCO();
	    mode = "add";
	    return "successFrm";
	}
	
	/**
     * Method called to empty the gird 'timeBucketsDetailsGrid_${_pageRef}'
     */
	public String emptyTimeBucketsListDetails()throws Exception
	{
		setRecords(0);
		setGridModel(new ArrayList<FTR_TIME_BUCKETSCO>());
    	mode="add";
		return "json";
	}
	
	/**
	 * Method called when deleting the Time Buckets row from the grid 'timeBucketsTable_${_pageRef}'
	 * @return
	 */
	public String deleteTimeBuckets() {
		try
		{
		    SessionCO sessionCO = returnSessionObject();
		    ftrTimeBucketsVO.setCOMP_CODE(sessionCO.getCompanyCode());
		    ftrTimeBucketsVO.setREP_REF(repRef);
		    ftrTimeBucketsVO.setCURRENCY_CODE(currencyCode);
		    
		   //  AuditRefCO refCO = initAuditRefCO();
		   // refCO.setOperationType(AuditConstant.DELETE);
		   // refCO.setKeyRef(AuditConstant.TIME_BUCKETS_KEY_REF);
		   // refCO.setProgRef(pageRef);
		   // refCO.setAppName(appName);
		   // ftrTimeBucketsVO.setAuditRefCO(refCO);
		    ftrTimeBucketsCO.setFtrtimebucketsVO(ftrTimeBucketsVO);
		    
		    timeBucketsBO.deleteTimeBuckets(ftrTimeBucketsVO);
		}
		catch(Exception e)
		{
		    //log.error(e, "Error in method delete in TimeBucketsAction");
		    handleException(e, "timeBuckets.errorDeleting.exceptionMsg._key",null);
		}
		return "json";
	}
	
	//Reem and Anna 26/09/2014 
	//The delete is included in the save method.
	/**
	 * Method called when deleting the Time Buckets row from the grid 'timeBucketsDetailsGrid_${_pageRef}'
	 */
//	public String deleteTimeBucketsDetails() {
//		try
//		{
//		    SessionCO sessionCO = returnSessionObject();
//		    String pageRef    = get_pageRef();
//		    String appName     = sessionCO.getCurrentAppName();
//		    
//		    ftrTimeBucketsVO.setCOMP_CODE(sessionCO.getCompanyCode());
//		    ftrTimeBucketsVO.setREP_REF(repRef);
//		    ftrTimeBucketsVO.setCURRENCY_CODE(currencyCode);
//		    ftrTimeBucketsVO.setLINE_NO(lineNo);
//		    
//		    AuditRefCO refCO = initAuditRefCO();
//		    String lang = sessionCO.getLanguage();
//		    refCO.setOperationType(AuditConstant.DELETE);
//		    refCO.setKeyRef(AuditConstant.TIME_BUCKETS_LINE_KEY_REF);
//		    refCO.setProgRef(pageRef);
//		    refCO.setAppName(appName);
//		    ftrTimeBucketsVO.setAuditRefCO(refCO);
//		    ftrTimeBucketsCO.setFtrtimebucketsVO(ftrTimeBucketsVO);
//		    
//		    timeBucketsBO.deleteTimeBucketsDetails(ftrTimeBucketsVO, pageRef, sessionCO.getCompanyCode(), appName, lang);
//		}
//		catch(Exception e)
//		{
//		    //log.error(e, "Error in method delete in TimeBucketsAction");
//		    handleException(e, "timeBuckets.errorDeletingDetails.exceptionMsg._key",null);
//		}
//		return "json";
//	}
	
	/**
	 * Method to get the model of the grid 'timeBucketsTable_${_pageRef}'
	 */
	public Object getModel()
    {
    	return ftrTimeBucketsSC;
    }
	
	/**
	 * Execute Method
	 */
	public String execute() throws Exception
    {      
        set_showSmartInfoBtn("false");
        set_searchGridId("timeBucketsTable");
        set_showNewInfoBtn("true");
        mode="add";
        return SUCCESS;
    }
	
	/**
	 * Method to save the updated and added rows in both Grids. 
	 * @throws JSONException 
	 */
	public String saveTimeBuckets() throws JSONException
	{
		try
		{
			
			
			//String pageRef		= get_pageRef();
			//String appName   	= sessionCO.getCurrentAppName();
			//AuditRefCO refCO = initAuditRefCO();
			
			GridUpdates gu;
						
			if(updates_1 != null && !updates_1.equals("")&& !updates_1.equals("{\"root\":[]}"))
			{				
				//Get the updated record in the grid that sent from the page "TimeBuckets.js"
				gu = getGridUpdates(updates_1, FTR_TIME_BUCKETSCO.class,true);
				//Set the array of all records
				ArrayList lstAllRecords = gu.getLstAllRec();
				
				FTR_TIME_BUCKETSCO  ftrTimeBucketsCO;
				BigDecimal numOfDays;
				FTR_TIME_BUCKETSCO ftrTimeBucketsCO_;
				BigDecimal numOfDays_;
				
                for (int i=0; i<lstAllRecords.size(); i++){
					
                	ftrTimeBucketsCO= (FTR_TIME_BUCKETSCO) lstAllRecords.get(i);
                	numOfDays= ftrTimeBucketsCO.getFtrtimebucketsVO().getNO_OF_DAYS();
										
					for (int j=i+1; j<lstAllRecords.size(); j++){
						ftrTimeBucketsCO_ = (FTR_TIME_BUCKETSCO) lstAllRecords.get(j);
						numOfDays_= ftrTimeBucketsCO_.getFtrtimebucketsVO().getNO_OF_DAYS();
						
						if (numOfDays.equals(numOfDays_)){							
							errorMsg = "1";
							return "json";
						}
					}
					
				}	
			}
			
			//If updates != null, that means if the variable "jsonStringUpdates" 
			//that sent from "TimeBuckets.js" is not null, so there are updates 
			if(updates != null && !updates.equals("")&& !updates.equals("{\"root\":[]}"))
			{
				//Get the updated record in the grid that sent from the page "TimeBuckets.js"
				gu = getGridUpdates(updates, FTR_TIME_BUCKETSCO.class);
				//Set the array of added records
				ArrayList lstAdd = gu.getLstAdd();
				//Set the array of updated records
				ArrayList lstMod = gu.getLstModify();
				//Set the array of deleted records
				ArrayList lstDel = gu.getLstDelete();
			    //String lang = sessionCO.getLanguage();

				//Create a new instance of SessionCO
				SessionCO sessionCO = returnSessionObject();
				
			    // Check the sizes of the arrays to know if there are deleted records
			    if(!lstDel.isEmpty())
			    {	
			    	//refCO.setOperationType(AuditConstant.DELETE);
			    	//refCO.setKeyRef(AuditConstant.TIME_BUCKETS_LINE_KEY_REF);
			    	//refCO.setProgRef(pageRef);
			    	//refCO.setAppName(appName);
			    	
			    	timeBucketsBO.deleteTimeBucketsDetails(lstDel, sessionCO.getCompanyCode());

			    }

				BigDecimal compCode = sessionCO.getCompanyCode();
				//Check the sizes of the arrays to know if there are records added
				if(!lstAdd.isEmpty())
				{
					//refCO.setOperationType(AuditConstant.CREATED);
					//refCO.setKeyRef(AuditConstant.TIME_BUCKETS_LINE_KEY_REF);
					//refCO.setProgRef(pageRef);
					//refCO.setAppName(appName);
					
					timeBucketsBO.insertTimeBuckets(lstAdd, compCode);
				}
				
				//Check the sizes of the arrays to know if there are records updated
				if(!lstMod.isEmpty())
				{
					//ReportingSessionCO repSessionCO = returnReportingSessionObject(get_pageRef());
					//HashMap oldAuditRefVOList = repSessionCO.getTimeBucketsOld();
					timeBucketsBO.updateTimeBuckets(lstMod, compCode);
				}
			}
		}
		catch(Exception e)
		{
			//log.error(e, "Error in method save in TimeBucketsAction");
		    handleException(e, "timeBuckets.errorsaving.exceptionMsg._key",null);
		}
		return "json";
	}
	
	public String retReportRef()
	{
	    try
	    {
		if(repRef==null || repRef.isEmpty())
		{
		    ftrTimeBucketsCO=new FTR_TIME_BUCKETSCO();
		}
		else
		{
		    FTR_TIME_BUCKETSSC sc = new FTR_TIME_BUCKETSSC();
		    sc.setAPP_NAME(ConstantsCommon.REP_APP_NAME);
		    sc.setREP_REF(repRef);
		    ftrTimeBucketsCO = timeBucketsBO.getRepRef(sc);
		    if(ftrTimeBucketsCO==null)
		    {
			SessionCO sessionCO = returnSessionObject();
			String errMsg = returnCommonLibBO().returnTranslErrorMessage(
				MessageCodes.INVALID_MISSING_REFERENCE, sessionCO.getLanguage());
			addDependencyMsg(errMsg, null);
		    }
		}
	    
	    }
	    catch(Exception e)
		{
		  //  log.error(e, "Error in method getReportRef in TimeBucketsAction");
		    handleException(e, "timeBuckets.errorgetrepref.exceptionMsg._key",null);
		}
	    return "json";
	}


}