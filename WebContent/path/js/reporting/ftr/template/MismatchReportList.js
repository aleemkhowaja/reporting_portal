function addMismatch() {
	var tempCodeWithLineNb = $("#tempCodeWithLineNbMis_"+_pageRef).val();
	if (tempCodeWithLineNb == "0~0") {
		_showErrorMsg(selectLineAlert);
		return;
	}

	emptyMismatchForm(1,'fromAdd');
}

function saveMisButton(mode,newLineNbr)
{
	_showProgressBar(true)
	if (mode == "edit") 
	{
		var url = $("#misMaintFrmId_"+_pageRef).attr("action");
		myObject = $("#misMaintFrmId_"+_pageRef).serialize();

		var tempCodeWithLineNb = $("#tempCodeWithLineNbMis_"+_pageRef).val();
		myObject += "&tempCodeWithLineNb=" + tempCodeWithLineNb;

		
		//adjustment bbe
		$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			   if(typeof param["_error"] == "undefined" || param["_error"] == null)
			   {
					$("#mismatchReportsGrid_"+_pageRef).trigger("reloadGrid");
					emptyMismatchForm(1,'fromOK');
			   }
			 }
	    });
		//end adjustment bbe
		
	}
}


function deleteMismatch(rowid) {
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice,
			theArgs) {
		if (confirmcChoice) {
			deleteTheMismatch(theArgs.rowid)
		}
	}, {
		rowid : rowid
	});
}

function deleteTheMismatch(rowid) {

	_showProgressBar(true)
	var url = jQuery.contextPath + "/path/templateMaintReport/deleteMismatch.action?_pageRef="+_pageRef;
	myObject = $("#mismatchReportsGrid_"+_pageRef).jqGrid('getRowData', rowid);
	$.get(url, myObject, function(param) {
		$("#mismatchReportsGrid_"+_pageRef).trigger("reloadGrid");
	});

	emptyMismatchForm(0,'');
}

function emptyMismatchForm(obj,fromWhere) {
	var urlRefresh = jQuery.contextPath+ "/path/templateMaintReport/refreshMismatchForm.action?_pageRef="+_pageRef;
	$.ajax({
			 url: urlRefresh,
	         type:"post",
			 success: function(param)
			 { 
				$("#mismatchDiv_"+_pageRef).html(param);
				if(obj==1)
					{
						//expand the collapsable div
						var collapseDiv = $("#misMatchDivId > .collapsibleContainerTitle").get(0);
						if ($(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
							$(collapseDiv).trigger("click");
						}
						if(fromWhere=='fromAdd')
						{
							$("#mismatchOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
							document.getElementById("mismatchOK_"+_pageRef).disabled=false
						}
						else if(fromWhere=='fromOK')
						{
							$("#mismatchOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
							document.getElementById("mismatchOK_"+_pageRef).disabled=true;
						}
					}			
				_showProgressBar(false)
			 }
			});
}


function fillMismatchForm() {
	rowid = $("#mismatchReportsGrid_"+_pageRef).jqGrid('getGridParam', 'selrow');
	viewMismatchDetails(rowid);
}


function viewMismatchDetails(rowid) {
	var collapseDiv = $("#misMatchDivId > .collapsibleContainerTitle").get(0);
	if ($(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
		$(collapseDiv).trigger("click");
	}

	var url = jQuery.contextPath + "/path/templateMaintReport/openMismatchJson";
	url = url + "?mode=edit&pageRef="+_pageRef;
	myObject = $("#mismatchReportsGrid_"+_pageRef).jqGrid('getRowData', rowid);

	$.get(url, myObject, function(param) {
		$("#lookuptxt_existingTemplatesLook_"+_pageRef).val(param["ftrMissRepCO"].ftrMissRepVO.TMPLT_CODE1);
		$("#tmpltName_"+_pageRef).val(param["ftrMissRepCO"].TMPLT_NAME);
		$("#lookuptxt_relatedLinesLook_"+_pageRef).val(param["ftrMissRepCO"].ftrMissRepVO.SUB_LINE_NO);
		$("#lineName_"+_pageRef).val(param["ftrMissRepCO"].LINE_NAME);
		$("#concatKey_"+_pageRef).val(param["ftrMissRepCO"].concatKey);
		$("#mismatchOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
		document.getElementById("mismatchOK_"+_pageRef).disabled=false
	});
}

function emptyLineField()
{
	$("#lookuptxt_relatedLinesLook_"+_pageRef).val("");
	$("#lineName_"+_pageRef).val("");
}
