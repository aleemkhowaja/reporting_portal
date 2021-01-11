function rep_templProcess_readyFunc()
{
	$("#fromTemplStr").attr('readonly', true);
	$("#toTemplStr").attr('readonly', true);
	$("#fromColTemplStr").attr('readonly', true);
	$("#toColTemplStr").attr('readonly', true);
}


function checkTemplDiff(isFrom) {
	var fromTempl = $("#lookuptxt_fromTempl").val();
	var toTempl = $("#lookuptxt_toTempl").val();
	var diffTempl = toTempl - fromTempl;
	//check if from <to
	if (isFrom == 1) {
		if (toTempl != "" && diffTempl < 0) {
			$("#lookuptxt_fromTempl").val("");
			$("#fromTemplStr").val("");
			_showErrorMsg(fromToCompareAlert);
		}
	}
	if (isFrom == 2) {
		if (fromTempl != "" && toTempl != "" && diffTempl < 0) {
			$("#lookuptxt_toTempl").val("");
			$("#toTemplStr").val("");
			_showErrorMsg(fromToCompareAlert);
		}
	}
}

function checkColTemplDiff(isFrom) {
	var fromColTempl = $("#lookuptxt_fromColTempl").val();
	var toColTempl = $("#lookuptxt_toColTempl").val();
	var diffColTempl = toColTempl - fromColTempl;

	//check if from <to
	if (isFrom == 1) {
		if (toColTempl != "" && diffColTempl < 0) {
			$("#lookuptxt_fromColTempl").val("");
			$("#fromColTemplStr").val("");
			_showErrorMsg(fromToCompareAlert);
			return;
		}

	}
	if (isFrom == 2) {
		if (fromColTempl != "" && toColTempl != "" && diffColTempl < 0) {
			$("#lookuptxt_toColTempl").val("");
			$("#toColTemplStr").val("");
			_showErrorMsg(fromToCompareAlert);
			return;
		}
	}
}

function runTemplProc() {
	if ($("#lookuptxt_fromTempl").val() == ""
			|| $("#lookuptxt_toTempl").val() == "") {
		_showErrorMsg(requiredFieldsAlert);
		return;
	} else {
		_showProgressBar(true);
		var url = jQuery.contextPath
				+ "/path/templateProcess/proc_runTemplProcess";
		myObject = $("#tmplProcFormId").serialize();
		$.getJSON(url, myObject, function(data2, status, xhr) {
			var toggleElem = $("#tmplProccLogsDiv_" + _pageRef).children(
					":first").find('span .ui-icon');
			//if it is collapsed open it then load content to allow grid resize correctly
				if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
					$(".collapsibleContainerTitle",
							"div#tmplProccLogsDiv_" + _pageRef).click();
				}
				$("#tmplProccLogsGrid_" + _pageRef).trigger("reloadGrid");
				_showProgressBar(false);
			});
	}
}

function validateDates()
{
 if($("#procTypeComboId_"+_pageRef).val()=='AO' && $("#asOfDate").val()=="")
 {
 	_showErrorMsg(colTmpProcValidDate);
 	return false;
 }
 if($("#procTypeComboId_"+_pageRef).val()=='FT' && ($("#fromDate").val()=="" || $("#toDate").val()==""))
 {
 	_showErrorMsg(colTmpProcValidDate);
 	return false;
 }
 if($("#procTypeComboId_"+_pageRef).val()=='B' && ($("#fromDate").val()=="" || $("#toDate").val()=="" || $("#asOfDate").val()==""))
 {
 	_showErrorMsg(colTmpProcValidDate);
 	return false;
 }
 if($("#procTypeComboId_"+_pageRef).val()=='P' && ($("#procTypeComboId_"+_pageRef).val()=="" || $("#periodDate").val()=="" ))
 {
 	_showErrorMsg(colTmpProcValidDate);
 	return false;
 }
 
 return true;
}

function runColTemplProc() {
	if (($("#lookuptxt_fromColTempl").val() == "" || $("#lookuptxt_toColTempl").val() == "")
			|| ( $("#procTypeComboId_"+_pageRef).val()=='P' && $("#periodicDate").val()=="")) 
		{
			_showErrorMsg(requiredFieldsAlert);
			return;
		} 
		else if(validateDates())
		{
			_showProgressBar(true);
			var url = jQuery.contextPath
					+ "/path/templateProcess/proc_runColTemplProcess";
			myObject = $("#colTmplProcFormId").serialize();
			$.getJSON(url, myObject, function(data2, status, xhr) {
				var toggleElem = $("#colTmplProcLogsDiv_" + _pageRef).children(
						":first").find('span .ui-icon');
				//if it is collapsed open it then load content to allow grid resize correctly
					if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
						$(".collapsibleContainerTitle",
								"div#colTmplProcLogsDiv_" + _pageRef).click();
					}
	
					$("#colTmplProcLogsGrid_" + _pageRef).trigger("reloadGrid");
					_showProgressBar(false);
				});
		}
}

function runAllProc() {
	if ($("#lookuptxt_fromTempl").val() == ""
			|| $("#lookuptxt_toTempl").val() == ""
			|| $("#lookuptxt_fromColTempl").val() == ""
			|| $("#lookuptxt_toColTempl").val() == "") {
		_showErrorMsg(requiredFieldsAlert);
		return;
	} else {
		_showProgressBar(true);
		var url = jQuery.contextPath
				+ "/path/templateProcess/proc_runTemplProcess";
		myObject = $("#tmplProcFormId").serialize();
		$.getJSON(url, myObject, function(data2, status, xhr) {
			var toggleElem = $("#tmplProccLogsDiv_" + _pageRef).children(
					":first").find('span .ui-icon');
			//if it is collapsed open it then load content to allow grid resize correctly
				if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
					$(".collapsibleContainerTitle",
							"div#tmplProccLogsDiv_" + _pageRef).click();
				}

				$("#tmplProccLogsGrid_" + _pageRef).trigger("reloadGrid");
				runColTemplProc();
				_showProgressBar(false);
			});
	}
}

function disableDates() {
	enableAll();
	var procType = $("#procTypeComboId_"+_pageRef).val();
	if (procType == "AO") {
		disableDatepicker("fromDate", true);
		disableDatepicker("toDate", true);
		$("#fromDate").val("");
		$("#toDate").val("");
	} else if (procType == "FT") {
		disableDatepicker("asOfDate", true);
		$("#asOfDate").val("");
	}
}

function enableAll() {
	disableDatepicker("asOfDate", false);
	disableDatepicker("fromDate", false);
	disableDatepicker("toDate", false);
}

function checkDates(obj) {
	if ($("#fromDate").val() != "" && $("#toDate")
			.val() != "")
		{
			var dateCompare = compareDate("fromDate", "toDate");
			if (dateCompare == 1) {
				_showErrorMsg(checkDatesAlert);
				$("#" + obj.id).val("");
			}
			}
			
}