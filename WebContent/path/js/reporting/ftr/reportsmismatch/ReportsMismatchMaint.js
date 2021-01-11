function submitFormRepMis()
{
	var jsonStringUpdates = $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getChangedRowData');
	$("#updateRepMis_"+_pageRef).val(jsonStringUpdates); 
	$("#reportsMismatchMaintFormId_"+_pageRef).trigger('submit');
}

function reportsMismatchMaint_processSubmit()
{
	//checking for missing informations
	var allRowIds =  $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getDataIDs');
	 if(allRowIds)
	 {		
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	myObject =$("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getRowData',llRowId);
	    	var enteredInACondition=false;
	    	if(myObject["repMismatchParamVO.CRITERIA_CODE"] == "" )
	    	{
	    		 _showErrorMsg(invalidCrtCode,error_msg_title,300,100);
	    		 enteredInACondition=true;
	    	}
	    	if(myObject["repMismatchParamVO.REP_REFERENCE"] == "")
	    	{
	    		_showErrorMsg(invalidRepRef,error_msg_title,300,100);
	    		enteredInACondition=true
	    	}
	    	if(myObject["repMismatchParamVO.CRITERIA_COLUMN"]=="")
	    	{
	    		_showErrorMsg(missingCrtCol,error_msg_title,300,100);
	    		enteredInACondition=true
	    	}
	    	if(enteredInACondition)
	    	{
	    		return;
	    	}
	    }
	  }
	
	var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_saveAllReportsMismatch";
	myObject =  $("#reportsMismatchMaintFormId_"+_pageRef).serialize();
	$.post(url, myObject , function(param)
	{ 
		if(typeof param["_error"] == "undefined" || param["_error"] == null)
	    {
			$("#reportsMismatchListGridTbl_Id_1_"+_pageRef).trigger("reloadGrid");
		}
		else
		{
			_showErrorMsg(param["_error"],error_msg_title,300,150);
		}
	});
}

function loadRepMisGrid()
{
	adjustMisType();
	var srcURL=jQuery.contextPath + "/path/reportsMismatch/ReportsMismatchListAction_reloadDivMis.action";
	var params={};
	//1=> main grid
	params["update"]		=1;
	//0=>intra
	params["mismatchType"]	=$("#mismatchType_"+_pageRef).val();
	params["_pageRef"]      =_pageRef;
	$("#sort6mismatch_"+_pageRef).load(srcURL, params, function()
	 {

	});
}

function adjustMisType()
{
	$("#mismatchType_"+_pageRef).val($("#misTypeListComboId_"+_pageRef).val());
}
