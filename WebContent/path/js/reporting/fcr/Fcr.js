function rep_fcr_selectRowFn() {
	rowid = $("#fcrGridId_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var url = jQuery.contextPath + "/path/fcrRep/fcr_openFcr";
	myObject = $("#fcrGridId_" + _pageRef).jqGrid('getRowData', rowid);
	params = {};

	params["code"] = myObject["ftrOptVO.CODE"];
	params["_pageRef"] = _pageRef;
	$.post(url, params, function(param) {
		$("#fcrDiv_" + _pageRef).html(param);
	}, "html");
}
function newFcr() {
	//save 
	var url = $("#fcrDetFormId_" + _pageRef).attr("action");
	parseNumbers();
	var fcrCode = $("#fcrCode_" + _pageRef).val();
	if (fcrCode == "") {
		$("#fcrCode_" + _pageRef).val("0");
	}
	myObject = $("#fcrDetFormId_" + _pageRef).serialize();

	$.ajax( {
		url : url,
		type : "post",
		dataType : "json",
		data : myObject,
		success : function(param) {
			if (typeof param["_error"] == "undefined"
					|| param["_error"] == null) {
				$("#fcrGridId_" + _pageRef).trigger("reloadGrid");
				//empty form
				emptyFcrForm();
				refreshMenu();
			}
		}
	});
}
function emptyFcrForm() {
	var url = jQuery.contextPath + "/path/fcrRep/fcr_cleanForm";
	var params = {};
	params["_pageRef"] = _pageRef;
	$.post(url, params, function(param) {
		$("#fcrDiv_" + _pageRef).html(param);
	}, "html");

}
function deleteFcr(rowid) {
	myObject = $("#fcrGridId_" + _pageRef).jqGrid('getRowData', rowid);
	params={};
	url=jQuery.contextPath+'/path/fcrRep/fcr_checkSchedHasReport.action';
	params["updates"] = myObject["ftrOptVO.PROG_REF"];
		$.ajax( {
		url : url,
		type : "post",
		dataType : "json",
		data : params,
		success : function(param) {
			if(param["updates"]>0)
			{
				 _showErrorMsg(existingSched,error_msg_title,300,100);
				 return;
			}
			else
			{
				_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice,
						theArgs) {
					if (confirmcChoice) {
						deleteTheFcr(theArgs.rowid)
					}
				}, {
					rowid : rowid
				});
			}
			
		}
	});
}
function deleteTheFcr(rowid) {
	var url = jQuery.contextPath + "/path/fcrRep/fcr_deleteFcr";
	myObject = $("#fcrGridId_" + _pageRef).jqGrid('getRowData', rowid);
	params = {};

	params["code"] = myObject["ftrOptVO.CODE"];
	params["applName"] = myObject["ftrOptVO.APP_NAME"];
	params["pRefLkp"] = myObject["ftrOptVO.PROG_REF"];
	params["_pageRef"] = _pageRef;
	$.get(url, params, function(param) {
		$("#fcrGridId_" + _pageRef).trigger("reloadGrid");
		emptyFcrForm();
		refreshMenu();
	});
}

function checkProgRef(obj) {
	var refStr = $("#" + obj.id).val();
	if (refStr == "") {
		return;
	} else {
		var zSrc = jQuery.contextPath
				+ "/path/fcrRep/fcr_checkProgRef.action?updates=" + refStr;
		params = {};
		$.getJSON(zSrc, params, function(data2, status, xhr) {
			var retVal = data2['updates'];
			if (retVal != "0") // if the reference exist  
				{
					$("#" + obj.id).val("");
					_showErrorMsg(checkProgRefAlert);
				}
			});
	}
}
function refreshMenu() {
	ddaccordion
			.initRoot(
					"appMenu",
					"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
					false);
}

function checkInputVal() {
	var refStr = $("#fcrProgRef_" + _pageRef).val();
	var re = new RegExp("[\u0600-\u06ff\ufb50-\ufdff\ufe70-\ufeff]", "g");
	refStr = refStr.replace(re, "")
	refStr = refStr.replace(" ", "");
	$("#fcrProgRef_" + _pageRef).val(refStr.toUpperCase());
}

function showHideElements(report_Type) {
	if (report_Type == "D") {
		$('#groupBy_' + _pageRef).show('fast');
		$('#groupByDetails_' + _pageRef).show('fast');
	} else {
		$('#groupBy_' + _pageRef).hide();
		$('#groupByDetails_' + _pageRef).hide();
	}
}