//Used when adding a new Time Bucket Header
function addNewTimeBuckets()
{
	emptyFormAndGrid();
}

//Used to empty the Form and Grid of Time Buckets Details
function emptyFormAndGrid()
{
	_showProgressBar(true);
	var url=jQuery.contextPath+'/path/timeBuckets/timeBucketsAction_emptyTimeBucketsFrm.action';
	params = {};
	params["_pageRef"]=_pageRef;
	//Empty form
	$.post(url, params , function( param )
 	{
 		$("#tbDiv_"+_pageRef).html(param);
 		//Empty grid of Time Buckets Details
 		 $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('setGridParam',
   		 {    
   			url : jQuery.contextPath+"/path/timeBuckets/timeBucketsAction_emptyTimeBucketsListDetails.action",
   			page : 1
   		 }).trigger("reloadGrid");
 		
 	},"html");
 	_showProgressBar(false);
}

//Used to show the selected Time Bucket header in the form with the lines related to it in the Grid of Time Buckets Details
function editTimeBuckets()
{
	_showProgressBar(true);
	rowid =$("#timeBucketsTable_"+_pageRef).jqGrid('getGridParam','selrow');
	var url=jQuery.contextPath+'/path/timeBuckets/timeBucketsAction_retrieveTimeBuckets.action';
	params = {};
	var repRef=$("#timeBucketsTable_"+_pageRef).jqGrid('getCell', rowid,'ftrtimebucketsVO.REP_REF');
	var currCode=$("#timeBucketsTable_"+_pageRef).jqGrid('getCell', rowid,'ftrtimebucketsVO.CURRENCY_CODE');
	params["_pageRef"]=_pageRef;
	params["repRef"]=repRef;
	params["currencyCode"]=currCode;
	//fill form
	$.post(url, params , function( param )
 	{
 		$("#tbDiv_"+_pageRef).html(param);
 		//$("repName_"+_pageRef).attr("disabled", true);
 		//$("lookuptxt_currencyCodeTimeBuckets_"+_pageRef).attr("disabled", true);
 		
 		//reload grid
 		 $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('setGridParam',
   		 {    
   			url : jQuery.contextPath+"/path/timeBuckets/timeBucketsAction_retrieveTimeBucketsListDetails.action?repRef="+repRef+"&currencyCode="+currCode+"&_pageRef="+_pageRef,
   			page : 1
   		 }).trigger("reloadGrid");
 		
 	},"html");
 	_showProgressBar(false);
}

function addTimeBucketsDetails()
{
	var repRefVal = $("#lookuptxt_repRefTimeBuckets_"+_pageRef).val();
	var curCodeVal = $("#lookuptxt_currencyCodeTimeBuckets_"+_pageRef).val();
	
	//checks number of fields in grid
	var result = $("#timeBucketsDetailsGrid_" + _pageRef).jqGrid('checkRequiredCells');
	if (!result) 
	{
		_showProgressBar(false);
		return;
	}
	
	$("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('addInlineRow',{'ftrtimebucketsVO.REP_REF':repRefVal, 'ftrtimebucketsVO.CURRENCY_CODE':curCodeVal});

	

}

function deleteTimeBuckets(sel_row_index)
{	
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm , deleteTitle, "deleteBucket", args);
}

function emptyForm()
{
	$("#tbDiv_"+_pageRef).load(jQuery.contextPath+ "/path/timeBuckets/timeBucketsAction_emptyTimeBucketsFrm.action?_pageRef="+_pageRef);
}


function deleteBucket(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)      
	{
		var repRef = $("#timeBucketsTable_"+_pageRef).jqGrid('getCell', selRow,'ftrtimebucketsVO.REP_REF');
		var currencyCode = $("#timeBucketsTable_"+_pageRef).jqGrid('getCell', selRow,'ftrtimebucketsVO.CURRENCY_CODE');
		var zSrc=jQuery.contextPath+'/path/timeBuckets/timeBucketsAction_deleteTimeBuckets.action?repRef='+repRef+'&currencyCode='+currencyCode;
		params ={};
 					$.ajax({
 						url: zSrc,
 						type: "POST",
 						data: ({_pageRef:_pageRef}),				
 					    success: function(xml){
 					    	reloadGridTimeBuckets();
 					    	reloadGridTimeBucketsDetails();
 					        emptyForm();
 					     //  $("#timeBucketsDetailsGrid_"+_pageRef).trigger("reloadGrid");

 		    			}
 					});
	}
}

function deleteTimeBucketsDetails(sel_row_index)
{	
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm , deleteTitle, "deleteBucketDetails", args);
}

function deleteBucketDetails(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)      
	{
		$("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('deleteGridRow');
	}
}
		
//		var repRef = $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('getCell', selRow,'ftrtimebucketsVO.REP_REF');
//		var currencyCode = $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('getCell', selRow,'ftrtimebucketsVO.CURRENCY_CODE');
//		var lineNo = $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('getCell', selRow,'ftrtimebucketsVO.LINE_NO');
//		var zSrc=jQuery.contextPath+'/path/timeBuckets/timeBucketsAction_deleteTimeBucketsDetails.action?repRef='+repRef+'&currencyCode='+currencyCode+'&lineNo='+lineNo;
//	
//		
// 					$.ajax({
// 						url: zSrc,
// 						type: "POST",
// 						data: ({_pageRef:_pageRef}),				
// 						success: function(xml){
// 						
// 						var gridUrl = jQuery.contextPath+"/path/timeBuckets/timeBucketsAction_retrieveTimeBucketsListDetails"; 						
// 						params= {};
// 						params["repRef"]= repRef;
// 						params["currencyCode"]= currencyCode;
// 						alert("repRef"+params["repRef"]);
// 						alert("currencyCode"+params["currencyCode"]);
//
// 						$("#timeBucketsDetailsGrid_" + _pageRef).jqGrid('setGridParam',
// 							{
// 							url : gridUrl,
// 							datatype : 'json',
// 							postData : params
// 							}).trigger("reloadGrid");
// 					}
// 					});
	


//Reload the Grid 'timeBucketsTable_pageRef'
function reloadGridTimeBuckets()
{
	$("#timeBucketsTable_"+_pageRef).trigger("reloadGrid");
}

//Reload the Grid 'timeBucketsDetailsGrid_pageRef'
function reloadGridTimeBucketsDetails()
{
	$("#timeBucketsDetailsGrid_"+_pageRef).trigger("reloadGrid");
}

//Used to save any update or addition of new row in both Grids

function saveTimeBuckets()
{
	// _showProgressBar(true);
	//Check if any change is applied on the form
     
	//check password
	var repRef = $("#lookuptxt_repRefTimeBuckets_" + _pageRef).val();
	var currCode = $("#lookuptxt_currencyCodeTimeBuckets_" + _pageRef).val();

	if (repRef == "" && currCode == "") {
		_showErrorMsg(missingReportAndCurrency, error_msg_title, 300, 100);
		 _showProgressBar(false);
		return;
	}

	else if (repRef == "") {
		_showErrorMsg(missingReportReference, error_msg_title, 300, 100);
	    _showProgressBar(false);
		return;

	}

	else if (currCode == "")

	{
		_showErrorMsg(missingCurrecnyCode, error_msg_title, 300, 100);
		_showProgressBar(false);
		return;
	}
	

    //checks number of fields in grid
	var rowCount = $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('getGridParam', 'records');
	
	
	if(rowCount==0 ){
	   _showErrorMsg(missingLine, error_msg_title, 300, 100);
	   _showProgressBar(false);
	   return;	
	}

   else {
		var result = $("#timeBucketsDetailsGrid_" + _pageRef).jqGrid('checkRequiredCells');
		if (!result) {
		    _showProgressBar(false);
			return;		
		   }
		}
	
		
		 
	//Get the updated and new rows in the grid "timeBucketsDetailsGrid"
	var jsonStringUpdates = $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('getChangedRowData');
	var jsonStringUpdates_1 = $("#timeBucketsDetailsGrid_"+_pageRef).jqGrid('getAllRows');

			
	//post the "jsonStringUpdates" to the page "TimeBucketsAction"
    $("#updatesExpr_"+_pageRef).val(jsonStringUpdates);
    	//post the "jsonStringUpdates" to the page "TimeBucketsAction"
    $("#updatesExpr_1"+_pageRef).val(jsonStringUpdates_1);
    
    
    //Set the url of the "saveTimeBuckets" method in "timeBucketsAction"
    var url=jQuery.contextPath+'/path/timeBuckets/timeBucketsAction_saveTimeBuckets.action';
    //Serialize the form "timeBucketsForm" to post it to page "timeBucketsAction"
    myObject =  $("#timeBucketsForm_"+_pageRef).serialize();
    //call the method "saveTimeBuckets" in "timeBucketsAction"
  
    _showProgressBar(true);
	$.post(url, myObject , function( param){	
				
	   if(param["errorMsg"]!=null){		 
		 _showErrorMsg(duplicateline, error_msg_title, 300, 100);
        _showProgressBar(false);
        }
        else {        	
        	$("#timeBucketsTable_"+_pageRef).trigger("reloadGrid");
   		     addNewTimeBuckets();  
        	}
       	});
	
	}

