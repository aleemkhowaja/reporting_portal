function addProc()
	{
		$("#procGrid_"+_pageRef).jqGrid('addInlineRow',{});
	}

	function cancelProc()
	{
		$('#procDialog').dialog('close');
	}
	
	function saveProc(isSvAs)
	{
		var grid = $("#subRepGridId_"+_pageRef);
     	var rowCount = grid.jqGrid('getDataIDs').length; 	
	    var jsonStringUpdates = $("#subRepGridId_"+_pageRef).jqGrid('getAllRows'); 
		 $("#updates_"+_pageRef).val(jsonStringUpdates); 
		 var url = $("#subRepForm_"+_pageRef).attr("action");
		 url +="?_pageRef="+_pageRef;
		 myObject =  $("#subRepForm_"+_pageRef).serialize();
		 $.post(url, myObject , function( param )
	 	 {
			rep_designer_continueSaveProc(isSvAs); 
	 	 });		
	}
	
	function rep_designer_continueSaveProc(isSvAs)
	{
		$('#constraintsMainDialog_'+_pageRef).dialog('destroy').remove();
		if(_pageRef=="RD00R")
		{
           reportHasChanged(true);
           }
		//check if the order is wrong
	  var lineCount=$("#procGrid_"+_pageRef).jqGrid('getGridParam','records');
	  var beforeStr=",";
	  var afterStr=",";
		
		var $t = $("#procGrid_"+_pageRef);
		var rows = $t.jqGrid('getDataIDs');
		var rowsLen = rows.length;

		for(i=0; i<rowsLen; i++)
		{
			  rowData = $t.jqGrid('getRowData', rows[i]);
		  	  if(rowData["EXEC_BEFORE"]=="0"|| rowData["EXEC_BEFORE"]=="Before")
		  	  {
		  		afterStr+=rowData["PROC_ORDER"]+",";
		  	  }
		  	  else
		  	  {
		  		beforeStr+=rowData["PROC_ORDER"]+",";
		  	  }
		}
		if(checkOrder(beforeStr)==1)
		{
				return;
		}
		else if(checkOrder(afterStr)==1)
		{
			return;
		}
		
		//save into session
		// var jsonStringUpdates = $("#procGrid_"+_pageRef).jqGrid('getChangedRowData');
		 var jsonStringUpdates = $("#procGrid_"+_pageRef).jqGrid('getAllRows');
		 $("#updatesProc_"+_pageRef).val(jsonStringUpdates);
		 
		var cltRepFlag = $("#cltRepFlag_" + _pageRef)
		if (cltRepFlag.val() == 0) {
		
		 	 var jsonStringUpdatesRepClient = $("#repClient_"+_pageRef).jqGrid('getAllRows');
			 $("#updatesRepClient_"+_pageRef).val(jsonStringUpdatesRepClient);
			 //update the list in session
			 var urlRepClient = $("#repClientForm_"+_pageRef).attr("action");
			 urlRepClient +="?_pageRef="+_pageRef;
			 myObject =  $("#repClientForm_"+_pageRef).serialize();
			 $.post(urlRepClient, myObject , function( param )
		 	 {
		 	 });
		}
		 
		 
		 
		 
		  
		 //same for the hashtable list to avoid adding empty line to take the modifs 
		 var isSyb = $("#isSyb_" + _pageRef)
		 if (isSyb.val() == 1) {
			
			 var jsonStringUpdatesHashTbl = $("#hashTblGridId_"+_pageRef).jqGrid('getAllRows');
			 $("#updatesHashTbl_"+_pageRef).val(jsonStringUpdatesHashTbl);
			 //update the list in session
			 var urlHahTbl = $("#hashTblForm_"+_pageRef).attr("action");
			 urlHahTbl +="?_pageRef="+_pageRef;
			 myObject =  $("#hashTblForm_"+_pageRef).serialize();
			 $.post(urlHahTbl, myObject , function( param )
		 	 {
		 	 });	
		}
			
					
	 	  
		 //end
		 var url = $("#procForm_"+_pageRef).attr("action");
		 url +="?_pageRef="+_pageRef;
		 myObject =  $("#procForm_"+_pageRef).serialize();
		 $.post(url, myObject , function( param )
	 	 {
	 	 	var err=param["update2"];
	 	 	if(err!="")
	 	 	{
	 	 		 _showErrorMsg(err, error_msg_title, 300, 100);
	 	 	}
	 	 	else
	 	 	{
			   	 if(_pageRef!="RD00UD")
			   	 {
			   	 	$('#procDialog').dialog('close');
			   	 }
			   	 else
			   	 {
			   	 	if(isSvAs!=null)
			   	 	{
			   	 		saveAsUpload();
			   	 	}
			   	 	else
			   	 	{
				   		SaveUpload()
			   	 	}
			   	 }
		   	 }
	 	 });
	}

	function checkOrder(str)
	{
		var zOrder;
		var zLength=str.split(",").length;
		for(i=0;i<zLength;i++)
		{
			zOrder=str.split(",")[i];
			if(i!=0 && i!=zLength-1 && str.indexOf(","+i+",")==-1)
			{
				_showErrorMsg(procOrderErrMsg, error_msg_title, 300, 100);
				return 1;
			}
		}
		return 0
	}

	function deleteProc(rowid)
	{
		//remove params from session
		 var url = jQuery.contextPath+ "/path/designer/proc_deleteProcParams.action?_pageRef="+_pageRef;
		 myObject = $("#procGrid_"+_pageRef).jqGrid('getRowData',rowid);
	     $.post(url, myObject , function( param )
		 {
	    	
		 });
		
		 $("#procGrid_"+_pageRef).jqGrid('deleteGridRow'); 
	}

	function saveProcParams()
	{
		
		 var paramNameArray = $("#procParamsGrid_"+_pageRef).jqGrid('getCol','PARAM_NAME');
		 if(paramNameArray.length==0)
		 {
		  	$('#procGridDialog_'+_pageRef).dialog('close');
		 }
		 else
		 {
			 var jsonStringUpdates = $("#procParamsGrid_"+_pageRef).jqGrid('getAllRows');
			 $("#update1_"+_pageRef).val(jsonStringUpdates); 
			 var url = $("#procParamsForm_"+_pageRef).attr("action");
			 url +="?_pageRef="+_pageRef;
			 myObject =  $("#procParamsForm_"+_pageRef).serialize();
			 $.post(url, myObject , function( param )
		 	 {
		 	 	var err=param["updates"];
		 	 	if(err!="")
		 	 	 {
		 	 	 _showErrorMsg(err, error_msg_title, 300, 100);
		 	 	 }
		 	 	else
		 	 	{
				 $('#procGridDialog_'+_pageRef).dialog('close');
				 }
				// $('#procGridDialog_'+_pageRef).html("");
		 	 });
		 }
	}

	function openParams(cellvalue, options, rowObject)
	{
		 return '<a href="#" onclick="openParamsList(\''+options.rowId+'\')">'+linkParamsTitle+'</a>';
	}
	function openParamsList(_rowid)
	{
		rowObject =  $("#procGrid_"+_pageRef).jqGrid("getRowData",_rowid);
		$("#procGrid_"+_pageRef).jqGrid("setSelection",_rowid,false);
		dialogUrl= jQuery.contextPath+ "/path/designer/proc_openProcParamsDialog.action?_pageRef="+_pageRef ;
		dialogOptions={ autoOpen: false,
						height:300,
						title:procParamsTitle ,
						width:800 ,
						modal: true,
						buttons: [{ text : paramsOk, click : saveProcParams},
						          { text : paramsCancel, click :function(){$(this).dialog('close');}}
				          ]
		   }
		$.post(dialogUrl, rowObject , function( param )
	 	{
    	  $('#procGridDialog_'+_pageRef).html(param) ;
    	  $('#procGridDialog_'+_pageRef).dialog(dialogOptions)
		  $('#procGridDialog_'+_pageRef).dialog('open');
		},"html");
	}