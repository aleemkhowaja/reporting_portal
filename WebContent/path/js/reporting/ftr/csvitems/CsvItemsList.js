function csvItemsList_onDbClickedEvent()
{
	var rowid = 	$("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var repRef = $("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('getCell', rowid, 'cbkRptLineVO.REP_REF');	
	
	myObject = $("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('getRowData', rowid);
	var url =jQuery.contextPath+'/path/csvItems/CsvItemsMaintAction_retrieveRepName.action?reportRef='+repRef;
	params = {};
	paramStr = JSON.stringify(myObject);
	paramStr = "{"+ "csvItemsCO:"+paramStr + "}";
	params["updates"] = paramStr;
	params["_pageRef"]=_pageRef;
		$.post(url, params , function( param )
 		{
 			$("#csvItemsListMaintDiv_id_"+_pageRef).html(param);
 			//$("#actionType_"+_pageRef).val("edit");
 					
			var url = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsByRepGrid.action?reportRef="+repRef;
			$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
		
 		},"html");
		
		

  
}

function csvItemsList_onAddClicked()
{
	emptyForm()
}

/*
This Function empties the form's fields
	*/
function emptyForm()
{
	var url = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsByRepGrid.action";
	$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
	//empty form
	
//	$("#lookuptxt_reportRef_"+_pageRef).val('');
//	$("#reportName_"+_pageRef).val('');
	
	var url = jQuery.contextPath+"/path/csvItems/CsvItemsMaintAction_emptyForm?_pageRef="+_pageRef;
	var params = {};
	$.post(url, params, function(param) {
		$("#csvItemsListMaintDiv_id_"+_pageRef).html(param);
	}, "html");

}

function addCsvItem()
{
	var reportRef =$("#lookuptxt_reportRef_"+_pageRef).val();
	if(reportRef=="")
	{
		_showErrorMsg(missingRepAlert)
		return;
	}
	
	//checks number of fields in grid
	var result = $("#csvItemsByRepListGridTbl_Id_" + _pageRef).jqGrid('checkRequiredCells');
	if (!result)
	{
	    _showProgressBar(false);
		return;		
	}
	
	var lineNbr = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getCol','cbkRptLineVO.LINE_NBR');
	$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('addInlineRow',{'cbkRptLineVO.LINE_NBR':lineNbr.length+1});
	
}

function deleteCsvItem()
{
	var rowid = 	$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var index = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getInd',rowid)
	$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow');
	
	
	var lineNbr = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getCol','cbkRptLineVO.LINE_NBR');
	for(var i=index;i<=lineNbr.length;i++)
	{
		var rowId =  $("#csvItemsByRepListGridTbl_Id_"+_pageRef)[0].rows[i].id
		$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('setCellValue', rowId, 'cbkRptLineVO.LINE_NBR',i);
	}
}

function saveCsvItems()
{
	//checks number of fields in grid
	var rowCount = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getGridParam', 'records');
	
	
	if(rowCount==0 ){
	   _showErrorMsg(missingLine, error_msg_title, 300, 100);
	   _showProgressBar(false);
	   return;	
	}

   else {
		var result = $("#csvItemsByRepListGridTbl_Id_" + _pageRef).jqGrid('checkRequiredCells');
		if (!result) {
		    _showProgressBar(false);
			return;		
		   }
		}
	
	var hasChanges = $("#csvItemsByRepForm_"+_pageRef).hasChanges();
	if(hasChanges==true)
	{
		var jsonStringUpdates = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getAllRows');
		$("#updatesCsvList_"+_pageRef).val(jsonStringUpdates);
		
		var jsonStringDelete = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('getChangedRowData');
		$("#deletedCsvList_"+_pageRef).val(jsonStringDelete); 
		
		var result = $("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('checkRequiredCells');
		if(!result)return;
		
		var url = $("#saveCsvItemsForm_"+_pageRef).attr("action");
		var reportRef = $("#lookuptxt_reportRef_"+_pageRef).val();
		var dateUpdated =  $("#DATE_UPDATED_"+_pageRef).val();
		url +="?_pageRef="+_pageRef;
		url +="&reportRef="+reportRef;
		url +="&dateUpdated="+dateUpdated;
		myObject =  $("#saveCsvItemsForm_"+_pageRef).serialize();
		
		
		
		 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: myObject,
		 success: function(param)
		 {
		 	$("#csvItemsByRepForm_"+_pageRef).clearChanges();
			var urlCsv = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsGrid.action";
			$("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: urlCsv }).trigger("reloadGrid");
			
			var urlCsvByRep = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsByRepGrid.action";
			$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: urlCsvByRep }).trigger("reloadGrid");
	
			emptyForm();
		 }
    });
		
		$("#updatesCsvList_"+_pageRef).val('');
		$("#deletedCsvList_"+_pageRef).val('');
	}
}

function deleteAllCsvItemByRep()
{
	var rowid = 	$("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var repRef = $("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('getCell', rowid, 'cbkRptLineVO.REP_REF');	
	
	var url =jQuery.contextPath+'/path/csvItems/CsvItemsListAction_deleteAllCsvItemByRep.action?reportRef='+repRef;
	params = {};
	$.post(url, params , function( param )
 	{
 		var urlCsv = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsGrid.action";
		$("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: urlCsv }).trigger("reloadGrid");
			
		var urlCsvByRep = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsByRepGrid.action";
		$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: urlCsvByRep }).trigger("reloadGrid");
	
		emptyForm();
		
 	});
}



