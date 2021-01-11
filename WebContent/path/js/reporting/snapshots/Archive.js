function rep_archive_readyFunc()
{
	  if(document.getElementById('archiveCO.archiveLocation_'+_pageRef).value != 2)
	  {
	  	document.getElementById('conSelect_'+_pageRef).style.display='none';
	  }
	  document.getElementById('userGrid_'+_pageRef).style.display='none';
}

function showHideCon(archiveVal)
{
	if(archiveVal.value==2)
	{
		document.getElementById('conSelect_'+_pageRef).style.display='';	
	}
	else
	{
		document.getElementById('conSelect_'+_pageRef).style.display='none';	
	}

}	



function showHideUserGrid(archiveVal)
{
	if(archiveVal.value==2)
	{
		document.getElementById('userGrid_'+_pageRef).style.display='';	
		$('#userGridId_'+_pageRef ).jqGrid("setGridWidth",520);
	}
	else
	{
		document.getElementById('userGrid_'+_pageRef).style.display='none';	
		$("#userGridId_"+_pageRef).trigger("reloadGrid");
	}

}

function archive()
{
	var repSnap =document.getElementById("reportSnapshot_"+_pageRef).checked;
	var repSche =document.getElementById("reportScheduler_"+_pageRef).checked;
	var startDate = document.getElementById('startRangeDate_'+_pageRef).value;
	var endDate = document.getElementById('endRangeDate_'+_pageRef).value;
	
	var dbName=$("#connection_"+_pageRef).val();
	var zLocation=document.getElementById('archiveCO.archiveLocation_'+_pageRef).value;
	var dateRange = $('input:radio[name=archiveCO.dateRange]:checked').val();
	if(dateRange==2 && ( startDate=='' || endDate==''))
	{
		_showErrorMsg(startOrEndEmpty,warning_msg_title,300,100); 
		return;
	}
	else if(compareDate("startRangeDate_"+_pageRef,"endRangeDate_"+_pageRef)>0)
	{
		_showErrorMsg(wrongFromToDate,repScheSnapEmptyError,300,100); 
		return;
	}
	
	else if(repSnap ==false &&  repSche ==false)
	{
		_showErrorMsg(repScheSnapEmpty,repScheSnapEmptyError,300,100); 
	}
	else if(zLocation=="2" && dbName==null)
	{
		_showErrorMsg(missingDBName,error_msg_title,300,100); 
	}
	else
	{
		var url = jQuery.contextPath+'/path/archive/archiveAction_archivingFlow.action';
		var params=$("#archiveForm_"+_pageRef).serialize();
		var userArray;
		userArray = jQuery("#userGridId_"+_pageRef).jqGrid('getGridParam','selarrrow').valueOf();
		
		var userRadioBut =	$("input:radio[name='userRadioBut']")[1].checked;
		
		var userIdStr = "";
		for(var i=0;i<userArray.length;i++)
		{
			rowObject =  $("#userGridId_"+_pageRef).jqGrid("getRowData",userArray[i]);
			var userId = rowObject["userId"];
			userIdStr=userId+","+userIdStr;
		}
		
	    params = params+"&userIdStr="+userIdStr
		if(userRadioBut ==true)
		{
			if(userIdStr=="")
			{
				_showErrorMsg(userEmpty,userError,300,100); 
			}
			else
			{
				_showProgressBar(true);
				$.ajax({
				 url: url,
		         type:"post",
				 dataType:"json",
				 data:params,	
				 success: function(param)
				 {
					$("#logsGridId_"+_pageRef).trigger("reloadGrid");
					_showProgressBar(false);
				 }
		    	});
			}
		}
		else
		{
			 _showProgressBar(true);
			 $.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data:params,	
			 success: function(param)
			 {
				$("#logsGridId_"+_pageRef).trigger("reloadGrid");
				_showProgressBar(false);
			 }
	    });
		}
	}


}

function applySmartValidation(dateVal)
{
	if(dateVal.value==1)
	{
		  document.getElementById('startRangeDate_'+_pageRef).value='';
		  document.getElementById('endRangeDate_'+_pageRef).value='';
	}
	callDependency("dateRange_"+_pageRef+":archiveCO.dateRange",jQuery.contextPath+'/path/archive/archiveAction_applySmartValidation.action',"archiveCO.dateRange:dateRange_"+_pageRef,"dateRange","")

}
function checkDates(obj) {
	if ($("#startRangeDate_"+_pageRef).val() != "" && $("#endRangeDate_"+_pageRef).val() != "")
		{
			var dateCompare = compareDate("startRangeDate_"+_pageRef, "endRangeDate_"+_pageRef );
			if (dateCompare == 1) {
				_showErrorMsg(checkDatesAlert);
				$("#" + obj.id).val("");
			}
			}
			
}