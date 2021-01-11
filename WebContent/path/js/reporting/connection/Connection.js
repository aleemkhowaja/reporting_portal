
function addNewCon()
{
	var hasChanges = $("#filterConnForm").hasChanges();
	if(!$("#filterConnForm").hasChanges())
	{
		emptyForm();
		return;
	}
	var dbPass=$("#dbPassFake").val();
	var dbConfPass=$("#dbConfPassFake").val();
	if(dbPass!=dbConfPass)
	{
		_showErrorMsg(diffPass,dbConfirmPass,300,100); 
		return;
	}
	$("#dbPass").val(dbPass);
	$("#dbConfPass").val(dbConfPass);
	var url = jQuery.contextPath+'/path/connection/connection_testConnection.action';
	var params=$("#filterConnForm").serialize();
	
		 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
			  if(param["conStatus"]=="failed")
			  {
			  	  _showConfirmMsg(saveConf,saveTitle, function(confirmcChoice, theArgs){
		           if(confirmcChoice)
		           {
		        	  saveConnection();
		           }
			      }, {},null,null,300,100);
			  	  
			  }
			  else if(param["conStatus"]=="missingFields")
		      {
		 	  }
			  else
				  {
					saveConnection();
				  }
		 
		 }
    });

}

function saveConnection()
{
    var dbPass=$("#dbPassFake").val();
	var dbConfPass=$("#dbConfPassFake").val();

	if(dbPass!=dbConfPass)
	{
		_showErrorMsg(diffPass,dbConfirmPass,300,100); 
		return;
	}
	$("#dbPass").val(dbPass);
	$("#dbConfPass").val(dbConfPass);
	var url = jQuery.contextPath+'/path/connection/connection_update.action';
	var params=$("#filterConnForm").serialize();
	 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
		   if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   {
				emptyForm();
				reloadGrid();
			}
		 }
    });
}
	
function deleteConRecord(sel_row_index)
{	
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteCon", args);
}

function deleteCon(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)
	{
		var connId = $("#connectionGridId_"+_pageRef).jqGrid('getCell', selRow, 'CONN_ID');	
		$.ajax({
				url: jQuery.contextPath+'/path/connection/connection_deleteConnection.action',
				type: "post",
				data: ({connId : connId, _pageRef:_pageRef}),				
			    success: function(param){
				if(param['conFailedMsg']=='1')
					{
						_showErrorMsg(delLinkedAppToCon,error_msg_title,300,100); 
					}
				else
					{
				    	reloadGrid();
				    	emptyForm();
					}
    			}
		});
	}
}
		
	
function emptyForm()
{
	$("#connDesc_"+_pageRef).val("");
	$("#dbms_"+_pageRef).val("");
	$("#host_"+_pageRef).val("");
	$("#port_"+_pageRef).val("");
	$("#dbName_"+_pageRef).val("");
	$("#connId_"+_pageRef).val("");
	$("#userId_"+_pageRef).val("");
	$("#dbPassFake").val("");
	$("#dbConfPassFake").val("");
	$("#DATE_UPDATED_"+_pageRef).val("");
	$("#oldDbPass_"+_pageRef).val("");
	$("#actionType_"+_pageRef).val("add");
}

function reloadGrid()
{
	$("#connectionGridId_"+_pageRef).trigger("reloadGrid");
}