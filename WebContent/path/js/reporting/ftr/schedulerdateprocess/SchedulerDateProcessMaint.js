function rep_schdDateProcess_checkDates(obj) {
	if ($("#schedFromDate").val() != "" && $("#schedToDate")
			.val() != "")
		{
			var dateCompare = compareDate("schedFromDate", "schedToDate");
			if (dateCompare == 0 || dateCompare == 1) {
				_showErrorMsg(checkDatesAlert);
				$("#" + obj.id).val("");
			}
			}
			
}
function rep_schdDateProcess_validateDates()
{
 if( $("#procTypeComboId_"+_pageRef).val()=='AO' && $("#schedAsOfDate").val()=="")
 {
 	_showErrorMsg(procValidDate);
 	return false;
 }
 if( $("#procTypeComboId_"+_pageRef).val()=='FT' && ($("#schedFromDate").val()=="" || $("#schedToDate").val()==""))
 {
 	_showErrorMsg(procValidDate);
 	return false;
 }
 if( $("#procTypeComboId_"+_pageRef).val()=='B' && ($("#schedFromDate").val()=="" || $("#schedToDate").val()=="" || $("#schedAsOfDate").val()==""))
 {
 	_showErrorMsg(procValidDate);
 	return false;
 }
  if( $("#procTypeComboId_"+_pageRef).val()=='P' && ($("schedPeriodTypeComboId").val()=="" || $("#schedPeriodicDate").val()=="" ))
 {
 	_showErrorMsg(procValidDate);
 	return false;
 }
 
 return true;
}

function rep_schdDateProcess_enableAll() {

	disableDatepicker("schedAsOfDate", false);
	disableDatepicker("schedFromDate", false);
	disableDatepicker("schedToDate", false);
}

function rep_schdDateProcess_disableDates() {
	rep_schdDateProcess_enableAll();
	var procType = $("#procTypeComboId_"+_pageRef).val();
	if (procType == "AO") {
		// $("#fromDate").attr('disabled', true);
		disableDatepicker("schedFromDate", true);
		// $("#toDate").attr('disabled', true);
		disableDatepicker("schedToDate", true);
		$("#schedFromDate").val("");
		$("#schedToDate").val("");
	} else if (procType == "FT") {
		//$("#asOfDate").attr('disabled', true);
		disableDatepicker("schedAsOfDate", true);
		$("#schedAsOfDate").val("");
	}
}


function rep_schdDateProcess_retrieveReports() {
	rep_schdDateProcess_disableDates();
 	var schedulesStr = $("#selSchedId_"+_pageRef).val(); 
	var procType = $("#procTypeComboId_"+_pageRef).val();

	if ((schedulesStr !=null) && (schedulesStr !="")) {
	
		
					$("#schedReportGrid_"+_pageRef)
								.jqGrid(
										'setGridParam',
										{
											url : jQuery.contextPath
													+"/path/schedulerDateProcess/SchedulerDateProcessMaintAction_loadSchedReportGrid.action?schedValues="
													+ schedulesStr +"&procType="+procType,
											page : 1
										}).trigger("reloadGrid");	
}
	}


function rep_schdDateProcess_runProc()
{	
	if( rep_schdDateProcess_validateDates())
		{
	var arrayLength = $("#schedReportGrid_"+_pageRef).jqGrid('getCol','REPORT_ID').length;
	var reportsArray = new Array();
	var reportsStr="";
	
	var curRep = {}
	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#schedReportGrid_"+_pageRef).jqGrid("getRowData",i);
		curRep = {};
        curRep["REPORT_ID"] = rowObject["REPORT_ID"];
        curRep["SCHED_ID"] =  rowObject["SCHED_ID"];
        var checked = rowObject["reportLineNoCheckBox"];
		if(checked=="Yes")
		{
			reportsArray.push(curRep );
		}

	}	
		

	if (reportsArray.length== 0)
		{
		_showErrorMsg (noReportToProcess, error_msg_title, 300, 100);
		return;
		}
 	_showProgressBar(true);
	var url = jQuery.contextPath +"/path/schedulerDateProcess/SchedulerDateProcessMaintAction_runProcess.action";
	var reportsArray = "{"+ "\"root\":"+JSON.stringify(reportsArray) +"}"; 
	var myObject = $("#schedulerDateProcessMaintFormId_" + _pageRef).serializeForm()+"&updates="+reportsArray;
	
	$
				.ajax( {
					url : url,
					type : "post",
					dataType : "json",
					data : myObject,
					success : function(param) {
						if (typeof param["_error"] == "undefined"
								|| param["_error"] == null) {
							_showProgressBar(false);
							_showErrorMsg(processSuccess,info_msg_title, 300, 100);
						
						}
						else
							{
								_showProgressBar(false);
							_showErrorMsg(processFailed,error_msg_title, 300, 100);
							}
						
					}
				});
	}
}
