function csvItemsMaint_processSubmit()
{
  alert("Coding Needed of csvItemsMaint_onDbClickedEvent()");
}


function loadCsvItemsByRep()
{
	var reportRef =$("#lookuptxt_reportRef_"+_pageRef).val();
	var url = jQuery.contextPath+"/path/csvItems/CsvItemsListAction_loadCsvItemsByRepGrid.action?reportRef="+reportRef;
	$("#csvItemsByRepListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
}


