function bloombergRatesList_onDbClickedEvent()
{
  
  	var rowid      = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var compCode   = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('getCell', rowid, 'ftrRateUploadVO.COMP_CODE');	
	var curCode    = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('getCell', rowid, 'ftrRateUploadVO.CURRENCY_CODE');	
	var valueDate  = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('getCell', rowid, 'ftrRateUploadVO.VALUE_DATE');	
	
	
	myObject = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('getRowData', rowid);
	var url =jQuery.contextPath+'/path/bloombergRates/BloombergRatesListAction_retrieveBlbgRates.action?compCode='+compCode+'&curCode='+curCode+'&valueDate='+valueDate+'&_pageRef='+_pageRef;
	params = {};
	$.post(url, params , function( param )
 	{
		$("#auditTrxNbr_"+_pageRef).val(param["auditTrxNbr"]);
 	});
  
}

function bloombergRatesList_onAddClicked()
{
	var url =jQuery.contextPath+'/path/bloombergRates/BloombergRatesMaintAction_emptyForm?_pageRef='+_pageRef;
	var params = {};
	$.post(url, params, function(param) {
		$("#bloombergRatesListMaintDiv_id_"+_pageRef).html(param);
	}, "html");
}


function saveBlgRates() {
	
	//var hasChanges = $("#blgRatesForm_" + _pageRef).hasChanges();
	var result = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('checkRequiredCells');
	if(!result)return;
	
	//if (hasChanges == true) {
		var jsonStringUpdates = $("#bloombergRatesListGridTbl_Id_" + _pageRef).jqGrid('getChangedRowData');
		$("#updatesBlgRatesList_" + _pageRef).val(jsonStringUpdates);
		
		
		var url = $("#saveBlgRatesForm_"+_pageRef).attr("action");
		url +="?_pageRef="+_pageRef;

		myObject =  $("#saveBlgRatesForm_"+_pageRef).serialize();
		
		 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: myObject,
		 success: function(param)
		 {
		 	//$("#blgRatesForm_"+_pageRef).clearChanges();
			var url = jQuery.contextPath+"/path/bloombergRates/BloombergRatesListAction_loadBloombergRatesGrid.action";
			$("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
		 }
    });
		 
	//}

}		