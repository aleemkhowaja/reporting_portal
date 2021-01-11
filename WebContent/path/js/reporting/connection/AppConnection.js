
function addNewAppCon()
{
	_showProgressBar(true);
	if($("#lookuptxt_conId_"+_pageRef).val()=="" || $("#lookuptxt_appName_"+_pageRef).val()=="")
	{
		_showErrorMsg(missingRequired, error_msg_title, 300, 100);
		_showProgressBar(false);
		return;
	}
	if(!$("#appConnForm").hasChanges())
	{
		//emptyAppConForm();
		 var actionSrc = jQuery.contextPath+'/path/appConnection/appConnection_loadAppConMain.action?_pageRef='+_pageRef;
					
			$.post(actionSrc,{}, function( param )
	 		{
	 			$("#appIcDiv_"+_pageRef).html(param);
	 		},"html");
		_showProgressBar(false);
		return;
	}
	
	//var params=$("#appConnForm").serialize();
	var url = jQuery.contextPath+'/path/appConnection/appConnection_updateAppCon.action';
	var params=$("#appConnForm").serialize();
	 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
		   if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   {
				//emptyAppConForm();
				reloadGrid();
	 			var actionSrc = jQuery.contextPath+'/path/appConnection/appConnection_loadAppConMain.action?_pageRef='+_pageRef;
				$.post(actionSrc,{}, function( param )
		 		{
		 			$("#appIcDiv_"+_pageRef).html(param);
		 		},"html");
				
			}
		 _showProgressBar(false)
		 }
    });

}


function emptyAppConForm()
{
	$("#lookuptxt_conId_"+_pageRef).val("");
	//liveSearch_makeReadOnly(false, "lookuptxt_appName_"+_pageRef);
	
	$("#connDesc_"+_pageRef).val("");
	$("#lookuptxt_appName_"+_pageRef).val("");
	$("#longDesc_"+_pageRef).val("");

	$("#DATE_UPDATED_"+_pageRef).val("");
	$("#actionType_"+_pageRef).val("add");

}

function reloadGrid()
{
	$("#appConnectionGridId_"+_pageRef).trigger("reloadGrid");
}


function deleteAppConRecord(sel_row_index)
{	
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteAppCon", args);
}

function deleteAppCon(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)
	{
		var appName = $("#appConnectionGridId_"+_pageRef).jqGrid('getCell', selRow, 'irpAppConnectionVO.APP_NAME');
		
		$.ajax({
				url: jQuery.contextPath+'/path/appConnection/appConnection_deleteAppConnection.action',
				type: "POST",
				data: ({appName : appName, _pageRef:_pageRef}),				
			    success: function(param){
			    	reloadGrid();
			    	emptyAppConForm();
    			}
		});
			 var actionSrc = jQuery.contextPath+'/path/appConnection/appConnection_loadAppConMain.action?_pageRef='+_pageRef;
			$.post(actionSrc,{}, function( param )//to reload the form
	 		{
	 			$("#appIcDiv_"+_pageRef).html(param);
	 		},"html");

				
	}
}