function resetSnInformation() 
{
	var srcURL = jQuery.contextPath
			+ "/path/snapshotParameter/SnapshotInformationMaintAction_reloadFrm.action";
	var params = {};
	params["_pageRef"] = _pageRef;
	//TP 451133 need to load the content of page reload into for parent Div, not to loose the collapsible pannel
	$("#snapshotInformationMaintFormId_"+_pageRef).parent().load(srcURL, params, function()
	 {
		var gridUrl = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_loadSnapshotInformationGrid";
		var params = $("#snapshotInformationMaintFormId_" + _pageRef).serialize();
		$("#snapshotInformationListGridTbl_Id_" + _pageRef).jqGrid('setGridParam',
			{
				url : gridUrl,
				datatype : 'json',
				postData : params
			}).trigger("reloadGrid");
	});
}


function searchSnInformation() {
	var gridUrl = jQuery.contextPath+ "/path/snapshotParameter/SnapshotInformationListAction_loadSnapshotInformationGrid";
	var params = $("#snapshotInformationMaintFormId_" + _pageRef).serializeArray();
	$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setGridParam',
			{
				url : gridUrl,
				datatype : 'json',
				postData : params
			}).trigger("reloadGrid");
			
}

function snapshotInformationMaint_processSubmit()
{
	var myObject = $("#snapshotInformationMaintFormId_" + _pageRef).serialize();
	url = jQuery.contextPath	+ "/path/snapshotParameter/SnapshotInformationMaintAction_saveAllSnInfo.action";
	
	
	$.post(url, myObject , function( param )
 	{
		  if(typeof param["_error"] == "undefined" || param["_error"] == null)
	      {
				var srcURL = jQuery.contextPath+ "/path/snapshotParameter/SnapshotInformationMaintAction_reloadFrm.action";
				var params = {};
				params["_pageRef"] = _pageRef;
				$("#snapshotInformationMaintFormId_"+_pageRef).parent().load(srcURL, params, function()
				{	

				});
 			 	
 			 	var srcURL=jQuery.contextPath + "/path/snapshotParameter/SnapshotInformationListAction_reloadDivGrid.action";
				var params={};
				params["_pageRef"]      =_pageRef;
				$("div#listInf_"+_pageRef).load(srcURL, params, function()
				 {
		 			$("#gview_snapshotInformationListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
				 });		
	 	  }
	 	  else
	 	  {
	 	   	_showErrorMsg(param["_error"],error_msg_title,300,150);
	 	  } 			 	 	
 	});
}

function viewSnapshot() 
{
	var row = $("#snapshotInformationListGridTbl_Id_" + _pageRef).jqGrid(
			'getGridParam', 'selrow');
	if (row == null) {
		_showErrorMsg(selectSnpRowAlert, warning_msg_title, 300, 100);
	} else {
		rowObject = $("#snapshotInformationListGridTbl_Id_" + _pageRef).jqGrid(
				"getRowData", row);
		var snpRepId = rowObject["repSnapshotInfoVO.REP_SNAPSHOT_ID"];

		var params = {};
		params["code"] = snpRepId;
		params["repSnapshotInformationCO.SNP_FORMAT"] = $("#format_"+_pageRef).val();
		params["repSnapshotInformationCO.CSV_SEPARATOR"] = $("#csvSeparatorId_"+_pageRef).val();
		if ($("#noHeadAndFoot_" + _pageRef).attr("checked") != null) {
			params["repSnapshotInformationCO.SHOW_HEAD_FOOT"] = "Y";

		} else {
			params["repSnapshotInformationCO.SHOW_HEAD_FOOT"] = "N";
		}
		
		viewSnpUrl = jQuery.contextPath
				+ "/path/snapshotParameter/SnapshotInformationMaintAction_loadSnapshot.action";

		$.download(viewSnpUrl, params, "POST");
	}

}

function checkSnpIfCSV(repFormat)
{
	document.getElementById("noHeadFootSpan_"+_pageRef).style.display="inline";
	document.getElementById("noHeadAndFootLbl_"+_pageRef).innerHTML=""+snpNoHeadAndFoot;
	if(repFormat == "RDTXT" || repFormat == "RDXLS")
	{
		document.getElementById("noHeadAndFootLbl_"+_pageRef).innerHTML=""+snpNoHeaders;
	}
	if (repFormat == "CSV" || repFormat=="RDTXT") {
		document.getElementById("sepLblTd_" + _pageRef).style.display = "inline";
		document.getElementById("sepValTd_" + _pageRef).style.display = "inline";
	} 
	else
	{
		document.getElementById("sepLblTd_" + _pageRef).style.display = "none";
		document.getElementById("sepValTd_" + _pageRef).style.display = "none";
	}
	if(repFormat=="SQL")
	{
		document.getElementById("noHeadFootSpan_"+_pageRef).style.display="none";
	}
}

function viewFile(from)
{ 
	if(from==1)
	{
		nbRecords = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','records');
		if (nbRecords==0)
		{
			_showErrorMsg(noFileSelPerFreq,error_msg_title,300,100);
		}
		else if($("#snpFilePeriod_"+_pageRef).val()=="")
		{
			_showErrorMsg(missingPeriod,error_msg_title,300,100);
		}
		else if($("#reportFrequencyListId_"+_pageRef).val()=="")
		{
			_showErrorMsg(missingFrequency,error_msg_title,300,100);
		}
		else
		{
				var brs = navigator.userAgent.toLowerCase();
				//get all records
				var jsonStringUpdates = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getAllRows');
				$("#updateSnInfo_"+_pageRef).val(jsonStringUpdates);
				//WIN XP
				if (brs.search(/nt\s5\.1/) != -1) 
				{
					document.getElementById("snapshotInformationMaintFormId_"+_pageRef).target = "_top";
				} 
				var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_viewSitcomFile.action?_pageRef="+_pageRef
				document.getElementById("snapshotInformationMaintFormId_"+_pageRef).setAttribute("action",url);
				$("#snapshotInformationMaintFormId_"+_pageRef).clearChanges();
				//document.getElementById("snapshotInformationMaintFormId_"+_pageRef).submit();	
				submitEncryptedData("snapshotInformationMaintFormId_"+_pageRef);
		}
	}
	else
	{
		 jQuery('#snapshotInformationListGridTbl_Id_'+_pageRef).jqGrid('setSelection', from.substring(4,from.length));
		 //check if enable/dis is checked
		 rowid = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
		 myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
		 //check the enable/disable in parameter screen
		 var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_checkIncludeFileConditions?_pageRef="+_pageRef
				$.ajax({
			 	url: url,
		        type:"post",
			 	dataType:"json",
			 	async : false,
			 	data: myObject,
			 	success: function(param)
			 	{
			 		if(param["updates"]=="4")
			 		{
						_showErrorMsg(notEnabledFileGen,error_msg_title,300,100);
						return false;
			 		}
			 		else
			 		{
						 var rowId 			= $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');	
						 var myObject     	= $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowId);
						 $("#repSnapshotId_"+_pageRef).val(myObject["repSnapshotInfoVO.REP_SNAPSHOT_ID"]);
						 $("#repId_"+_pageRef).val(myObject["repSnapshotParamVO.REP_ID"]);
						 $("#descriptionSnInfo_"+_pageRef).val(myObject["repSnapshotInfoVO.DESCRIPTION"]); 
						 $("#retDateId_"+_pageRef).val(myObject["repSnapshotInfoVO.RETREIVE_DATE"]);
						 $("#snpFrqId_"+_pageRef).val(myObject["repSnapshotParamVO.SNAPSHOT_FREQUENCY"]);
						 $("#reportRef_"+_pageRef).val(myObject["repSnapshotParamVO.REP_REFERENCE"]);
						 var brs = navigator.userAgent.toLowerCase();
						 //WIN XP
						 if (brs.search(/nt\s5\.1/) != -1) 
						 {
					 		document.getElementById("snapshotInformationMaintFormId_"+_pageRef).target = "_top";
					  	 } 
						 var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_viewSitcomFileOfColumn.action?_pageRef="+_pageRef
						 document.getElementById("snapshotInformationMaintFormId_"+_pageRef).setAttribute("action",url);
						 $("#snapshotInformationMaintFormId_"+_pageRef).clearChanges();
						 //document.getElementById("snapshotInformationMaintFormId_"+_pageRef).submit();	
						 submitEncryptedData("snapshotInformationMaintFormId_"+_pageRef);
			 		}
			 	}
			 	}); 	
	}
}

function genFile()
{
	//check for empty grid
	nbRecords = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','records');
	if (nbRecords==0)
	{
		_showErrorMsg(noSnSelFileGen,error_msg_title,300,100);
		return;
	}
    //check if at least one record selected
	 var allRowIds = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
	 var atLeastOneCheck=0;
	 for (var i =0;i<allRowIds.length;i++)
	 {
	 	llRowId=allRowIds[i];
	    var myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',llRowId);
	 	if(myObject["ENABLE_SITCOM_YN"]=='true')
	 	{
	 		atLeastOneCheck=1;
	 		break;
	 	}
	 }
	 if(atLeastOneCheck==1)
	 {
	 	//adding checking for the period	
   		var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_generateFile?_pageRef="+_pageRef
		var jsonStringUpdates = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getAllRows');
		$("#updateSnInfo_"+_pageRef).val(jsonStringUpdates);
		$("#shouldCheckPerFreq_"+_pageRef).val("true");
		myObject = $("#snapshotInformationMaintFormId_"+_pageRef).serialize();
		$.ajax({
	 	url: url,
        type:"post",
	 	dataType:"json",
	 	async : false,
	 	data: myObject,
	 	success: function(param)
		 	{
		 		if(param["updates"]=="1")
		 		{
		 			_showErrorMsg(samePeriodError,error_msg_title,300,100);
		 		}
		 		else if(param["updates"]=="2")
		 		{
		 			_showErrorMsg(sameFreqPeriodCheckError,error_msg_title,300,100);
		 		/*All selected snapshots should have same period and frequency*/
		 		}
		 		else
		 		{
			 		//added to show the file
			 		$("#shouldCheckPerFreq_"+_pageRef).val("false");
			 		myObject = $("#snapshotInformationMaintFormId_"+_pageRef).serialize();
					var brs = navigator.userAgent.toLowerCase();
					//WIN XP
					if (brs.search(/nt\s5\.1/) != -1) 
					{
						document.getElementById("snapshotInformationMaintFormId_"+_pageRef).target = "_top";
					} 
					var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_generateFile.action?_pageRef="+_pageRef
					document.getElementById("snapshotInformationMaintFormId_"+_pageRef).setAttribute("action",url);
					$("#snapshotInformationMaintFormId_"+_pageRef).clearChanges();
					//document.getElementById("snapshotInformationMaintFormId_"+_pageRef).submit();
					submitEncryptedData("snapshotInformationMaintFormId_"+_pageRef);
			 		//end added to show the file
		 		}
		 	}
	 	}); 
	 }
	 else
	 {
	 	_showErrorMsg(noSnSelFileGen,error_msg_title,300,100);
	 }
}
