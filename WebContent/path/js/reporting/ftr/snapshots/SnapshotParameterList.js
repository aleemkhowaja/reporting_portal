function addSnParam()
{
	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('addInlineRow',{});	
}

function deleteSnCol(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteSnColFunc(theArgs.rowid)
           }
	      }, {rowid : rowid}, yes_confirm, no_confirm, 300, 100);	
	
}
function changeAsOfDateFunc()
{	
	rowid = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject 			= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	var currentRepReference = myObject["repSnapshotParamVO.REP_ID"];
	if(currentRepReference!=null && currentRepReference!="")
	{
		$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setGridRowStatus',rowid,2);
	}
}



	

				


function checkUniquenessProgRefFreq()
{
	rowid = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject 			= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	var currentRepReference = myObject["repSnapshotParamVO.REP_REFERENCE"];
	var currentFrequency    = myObject["repSnapshotParamVO.SNAPSHOT_FREQUENCY"];
    var rowIds = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
    var nbOccur =0;
    for (var i =0;i<rowIds.length;i++)
    {
        var lRowId = rowIds[i];
    	lObject = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',lRowId);
    	if(lObject["repSnapshotParamVO.REP_REFERENCE"]== currentRepReference && lObject["repSnapshotParamVO.SNAPSHOT_FREQUENCY"]==currentFrequency)
    	nbOccur++;
    }
    if(nbOccur>1)
    {
    	_showErrorMsg(repFreqDup,snapshotsDuplication,300,100)
    	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotParamVO.REP_REFERENCE'," ");
    	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotParamVO.SNAPSHOT_FREQUENCY'," ");
    	return "1";
    }
    else
    {
    	return "0";
    }
}

function submitFormSn()
{
	var jsonStringUpdates = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getChangedRowData');
	$("#updateSnParameter_"+_pageRef).val(jsonStringUpdates); 
	$("#snapshotParameterMaintFormId_"+_pageRef).trigger('submit');
	$.data(document.getElementById($("#snpFrmId12_"+_pageRef).attr("id")),"changeTrack",false); 
	$.data(document.getElementById($("#snapshotParameterMaintFormId_"+_pageRef).attr("id")),"changeTrack",false); 
}

function changeSnFunc(from)
{
	//begin checking if any modifications in the prog ref and frequency
	var rowidModifs = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var myObject 	= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowidModifs);
	var progRefOld 	= myObject["progRefOld"];
	var freqOld		= myObject["freqOld"];
	var newProgRef  = myObject["repSnapshotParamVO.REP_REFERENCE"];
	var newFreq		= myObject["repSnapshotParamVO.SNAPSHOT_FREQUENCY"];

	
	
	myObject["repSnapshotParamCO.progRefOld"]=progRefOld;
	myObject["repSnapshotParamCO.freqOld"]=freqOld;
	myObject["repSnapshotParamCO.repSnapshotParamVO.REP_REFERENCE"]=newProgRef;
	myObject["repSnapshotParamCO.repSnapshotParamVO.SNAPSHOT_FREQUENCY"]=newFreq;
	//end checking
	if(from=="fileOrder")
	{
	var sitcomOrderArr = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getCol','repSnapshotParamVO.SITCOM_ORDER');
	for (var i=0;i<sitcomOrderArr.length;i++)
	{
		var cur = sitcomOrderArr[i];
		for (var j=i+1;j<sitcomOrderArr.length;j++)
		{
				var next = sitcomOrderArr[j];
				if(cur!="" && next!="" && cur==next)
				{	
					_showErrorMsg(orderDupSn,snapshotsDuplication,300,100);
					rowid = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
					$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotParamVO.SITCOM_ORDER'," ");			
					return;
				}
		}
	}
		return;
	}
	else
	{
	var tst= checkUniquenessProgRefFreq()
	if(tst=="0")
	{
			if(progRefOld!="" && freqOld!="" && (progRefOld!=newProgRef || freqOld!=newFreq))
			{
				var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_adjustHashRefFreq?_pageRef="+_pageRef
					$.ajax({
					 url: url,
			         type:"post",
					 dataType:"json",
					 data: myObject,
					 success: function(param)
					 {
							$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCell',rowid,'freqOld',newFreq);
							$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCell',rowid,'progRefOld',newProgRef);		
					 }
			    });
			}
	}
	}
}

function cleanSnSession()
{
	var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterMaintAction_cleanSnSession?_pageRef="+_pageRef
					$.ajax({
					 url: url,
			         type:"post",
					 dataType:"json",
					 data: "",
					 success: function(param)
					 {

					 }
			    });
}


function deleteSnColFunc(rowid)
{
	 	var rowidModifs = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
		var myObject 	= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowidModifs);
		var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_checkSnInformation?_pageRef="+_pageRef
			$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
				if(param["updates2"]=="0")	
				{
					$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow'); 
				}
				else
				{
					_showErrorMsg(rowHasSavedSn,error_msg_title,300,100);
				}
			 }
			});
}

function fillTextArea(from)
{
	var gridObj;
		if(from==1)
		{
			gridObj = $("#reportColumnGrid_"+_pageRef);
		}
		else if(from==2)
		{
			gridObj = $("#functionsGridId_"+_pageRef);
		}
		else if(from==3)
		{
			gridObj = $("#expressionGridId_"+_pageRef);
		}
		else
		{
			gridObj = $("#reportParamsGrid_"+_pageRef);
		}
		
		
		rowid = gridObj.jqGrid('getGridParam','selrow');
		myObject = gridObj.jqGrid('getRowData',rowid);
		var idTxtArea;
		if($("#selectedTxtArea_"+_pageRef).val()==1)
		{
			idTxtArea="sitcomFormulaHeader_"+_pageRef;
		}
		else if($("#selectedTxtArea_"+_pageRef).val()==2)
		{
			idTxtArea="sitcomFormula_"+_pageRef;
		}
		else
		{
			idTxtArea="sitcomFormulaFooter_"+_pageRef;
		}
		var cursPosition   = returnCursorPosStart(document.getElementById(idTxtArea));
		var startingString = ($("#"+idTxtArea).val()).substring(0,cursPosition);
		var endString = ($("#"+idTxtArea).val()).substring(cursPosition,($("#"+idTxtArea).val()).length);
		
		if(from==1)
		{
			$("#"+idTxtArea).val(startingString+myObject["repSnapshotDrilColVO.COLUMN_DRILLDOWN"]+endString);
		}
		else if(from==4)
		{
			$("#"+idTxtArea).val(startingString+"["+myObject["ARGUMENT_NAME"]+"]"+endString);
		}		
		else
		{
			$("#"+idTxtArea).val(startingString+myObject["VALUE_DESC"]+endString);
		}
}

function saveFormulaVal ()
{
	rowid = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotParamVO.SITCOM_FORMULA',$("#sitcomFormula_"+_pageRef).val());
	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotParamVO.SITCOM_FORMULA_HEADER',$("#sitcomFormulaHeader_"+_pageRef).val());
	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotParamVO.SITCOM_FORMULA_FOOTER',$("#sitcomFormulaFooter_"+_pageRef).val());
	
	if(myObject["repSnapshotParamVO.REP_ID"]!="")
	{
		$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setGridRowStatus',rowid,2);
	}
	$.data(document.getElementById($("#snpFrmId12_"+_pageRef).attr("id")),"changeTrack",true); 
}

function formula_ready_func()
{
	$("div#sortSnColsFrm .collapsibleContainer").collapsiblePanel();	
	$("div#headerDiv_"+_pageRef+" .collapsibleContainer").collapsiblePanel();	
	$("div#footerDiv_"+_pageRef+" .collapsibleContainer").collapsiblePanel();
	$("div#detailsDiv_"+_pageRef+" .collapsibleContainer").collapsiblePanel();
	$("#selectedTxtArea_"+_pageRef).val(1);
	
	params ={};
	params["progRef"]	=$("#repFormula_"+_pageRef).val();
	params["repId"]		=$("#repIdFormula_"+_pageRef).val();
	params["from"]		=fromDrllDwn;
	params["_pageRef"]	=_pageRef;
	params["appName"]	=$("#appName_"+_pageRef).val();

	var refreshUrl=jQuery.contextPath+'/path/ftrCommon/FtrCommonAction_previewReportDesign.action?'
	$('#ColDivPreviewFormula_'+_pageRef).load(refreshUrl, params, function(param, textStatus, req)
	 {
		if(textStatus == "error") 
		{
		}
		else
		{
			$("#ColDivPreviewFormula_"+_pageRef).html(param);
			$("#reportColumnGrid_"+_pageRef).trigger("reloadGrid");
		}
	});
}

function hideTitleBars_formula()
{
	$("#gview_functionsGridId_"+_pageRef+" div.ui-jqgrid-titlebar").hide();   
	$("#gview_expressionGridId_"+_pageRef+" div.ui-jqgrid-titlebar").hide();   
	$("#gview_reportColumnGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide();   
	$("#gview_reportParamsGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide();   
}