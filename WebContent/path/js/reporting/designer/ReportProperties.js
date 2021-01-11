function closePropDlg()
{
	$('#propDialog').dialog('close');
	return false;
}



function checkPropIfCSV(repFormat)
{
	if(repFormat=="CSV")
	{
		document.getElementById("sepLblTd_"+_pageRef).style.display="inline";
		document.getElementById("sepValTd_"+_pageRef).style.display="inline";
	}
	else
	{
		document.getElementById("sepLblTd_"+_pageRef).style.display="none";
		document.getElementById("sepValTd_"+_pageRef).style.display="none";
	}
}


function submitProperties()
{
    reportHasChanged(true);
	var url = jQuery.contextPath+ "/path/designer/reportDesign_submitRepProperties";

	
	var cltRepFlag = $("#cltRepFlag_" + _pageRef)
	var repCltArray = $("#repClient_"+_pageRef).jqGrid('getCol','irpClientReportVO.CLIENT_ACRONYM');
	
	if (cltRepFlag.val() == 0 && repCltArray.length==0) {
		_showErrorMsg(empRepClt, info_msg_title, 300, 100);
		return;
	}
	
	repCltArray.sort();
	var repeatedExpr=false;
	for(var i=0;i<repCltArray.length-1;i++)
	{
		if(repCltArray[i].toUpperCase()==repCltArray[i+1].toUpperCase())
		{
			repeatedExpr=true;
		}
		break;
	}
	if(repeatedExpr==true)
	{
		_showErrorMsg(cltRepeated,info_msg_title,350,120);
		return;
	}
			
	if (cltRepFlag.val() == 0) {
	  	var jsonStringUpdatesRepClient = $("#repClient_"+_pageRef).jqGrid('getAllRows');
		$("#updatesRepClient_"+_pageRef).val(jsonStringUpdatesRepClient);
		//var urlRepClient = $("#repClientForm_"+_pageRef).attr("action");
		var urlRepClient = jQuery.contextPath+'/path/designer/repClient_putInRepList';
		urlRepClient +="?_pageRef="+_pageRef;
		urlRepClient +="&updatesRepClient="+$("#updatesRepClient_"+_pageRef).val();
		myObject =  $("#repClientForm_"+_pageRef).serialize();
		$.post(urlRepClient, myObject , function( param )
	 	{
	 	});
 	}

	$.post(url, $("#propertiesFrm_"+_pageRef).serializeForm() , function( param )
	{
		var editor = CKEDITOR.instances['editor1'];
		var firstTbl=editor.document.getById('root');
		var editorOrientation= $('#orientation').val();
		if(editorOrientation==1)
		{
			firstTbl.setAttribute('width','842');
		}
		else
		{
			firstTbl.setAttribute('width','595');
		}
  		$('#propDialog').dialog('close');
	});
}