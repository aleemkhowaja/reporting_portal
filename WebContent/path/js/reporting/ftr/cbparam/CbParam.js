function rep_cbParam_readyFunc()
{
	$("#cbParamGrid_"+_pageRef).subscribe('retrieveAudit', function(event,data) 
	{
		rowid = $("#cbParamGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		var ENTITY_CODE = $("#cbParamGrid_"+_pageRef).jqGrid('getCell', rowid,'ftr_cb_codeVO.ENTITY_CODE');
		var SUB_ENTITY_CODE = $("#cbParamGrid_"+_pageRef).jqGrid('getCell', rowid,'ftr_cb_codeVO.SUB_ENTITY_CODE');
		var ENTITY_CB_CODE = $("#cbParamGrid_"+_pageRef).jqGrid('getCell', rowid,'ftr_cb_codeVO.ENTITY_CB_CODE');
		var COMP_CODE = $("#cbParamGrid_"+_pageRef).jqGrid('getCell', rowid,'ftr_cb_codeVO.COMP_CODE');
		var ENTITY_TYPE = $("#cbParamGrid_"+_pageRef).jqGrid('getCell', rowid,'ftr_cb_codeVO.ENTITY_TYPE');
		var zSrc=jQuery.contextPath+'/path/cbParam/cbParamList_applyAudit.action?entityCode='+ENTITY_CODE+'&subEntityCode='+SUB_ENTITY_CODE+'&entityCbCode='+ENTITY_CB_CODE+'&compCode='+COMP_CODE+'&updates='+ENTITY_TYPE;
		         $.ajax({
		          url: zSrc,
		          type: "POST",
		          data: ({_pageRef:_pageRef}),    
		             success: function(xml){      
		   				$("#auditTrxNbr_"+_pageRef).val(xml["auditTrxNbr"]) 
		             }
		         });
		});
}

function saveCbParam(){
	
	var rowIds = $("#cbParamGrid_"+_pageRef).jqGrid('getDataIDs');
	nbMaxBigger=0;
    for (var i =0;i<rowIds.length;i++)
    {
        var lRowId = rowIds[i];
    	lObject = $("#cbParamGrid_"+_pageRef).jqGrid('getRowData',lRowId);
    	if(lObject["ftr_cb_codeVO.ENTITY_CB_CODE"] > 9999)
    	{	
    		$("#cbParamGrid_"+_pageRef).jqGrid('setCellValue',lRowId,'ftr_cb_codeVO.ENTITY_CB_CODE'," ");
    		nbMaxBigger=1;
    	}
    }
    if(nbMaxBigger>0)
	{
		_showErrorMsg(maxValCbParm+" 9999",error_msg_title,300 ,150);
		return;
	}
	 var jsonStringUpdates = $("#cbParamGrid_"+_pageRef).jqGrid('getChangedRowData');
	 $("#updatesCbParamList_"+_pageRef).val(jsonStringUpdates); 
	 var url = $("#cbParamForm_"+_pageRef).attr("action");
	 myObject =  $("#cbParamForm_"+_pageRef).serialize();
	 _showProgressBar(true);
 
 	 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: myObject,
		 success: function(param)
		 {
		   if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   {
				//reload grid
				$("#cbParamGrid_"+_pageRef).trigger("reloadGrid");
		   }
		    _showProgressBar(false);
		}
 	
	});
 	 
 	 	
}