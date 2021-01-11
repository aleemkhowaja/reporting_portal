function snapshotParameterMaint_processSubmit()
{
	//check if all the livesearch of parameter has been filled
	var allRowIds = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
	 if(allRowIds)
	 {		
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	myObject =$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',llRowId);
	    	var enteredInACondition=false;
	    	if(myObject["PARAM_NAME"] == "" && myObject["IS_FTR_FCR"]==1 )
	    	{
	        	jQuery("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setSelection', llRowId);
	    		 _showErrorMsg(missingAsOf,error_msg_title,300,100);
	    		 return;
	    	}
	    }
	  }

 	var url = jQuery.contextPath+ "/path/snapshotParameter/SnapshotParameterMaintAction_saveAllSn";
	myObject =  $("#snapshotParameterMaintFormId_"+_pageRef).serialize();
	 	$.post(url, myObject , function( param )
	 	{
	 	  if(typeof param["_error"] == "undefined" || param["_error"] == null)
	      {
	 	 	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setGridParam',{url :jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_loadSnapshotParameterGrid?_pageRef="+_pageRef}).trigger("reloadGrid");	
	 	  }
	 	  else
	 	  {
	 	   	_showErrorMsg(param["_error"],error_msg_title,300,150);
	 	  }
	 	 });
}	
	
function markChanged()
{
	$.data(document.getElementById($("#snpFrmId12_"+_pageRef).attr("id")),"changeTrack",true); 
}