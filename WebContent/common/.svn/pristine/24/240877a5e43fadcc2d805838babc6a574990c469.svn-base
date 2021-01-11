function linkDynScrTab_onDbClickedEvent()
{
	var $table = $("#linkDynScrTabListGridTbl_Id_"+_pageRef);
	var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
	var myObject = $table.jqGrid('getRowData', selectedRowId);
	/**
	 * get the selected rowId
	 */
	var changes = $("#linkDynScrTabMaintFormId_"+_pageRef).hasChanges();
	if(changes == 'true' || changes == true)
	{
		_showConfirmMsg(changes_made_confirm_msg,"","linkDynScrTab_onDbClickedAfterConfirm");// Confirmation call if changes made
	}
	else
	{
		linkDynScrTab_onDbClickedAfterConfirm(true);
	}	
}
function linkDynScrTab_onDbClickedAfterConfirm(yesNo)
{
	if(yesNo)
	{
		var $table = $("#linkDynScrTabListGridTbl_Id_"+_pageRef);
		var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
		var myObject    = $table.jqGrid('getRowData', selectedRowId);
		var screenId    = myObject["DYN_SCREEN_ID"];
		var objectCode  = myObject["OBJECT_CODE"];
		var subObjectId = myObject["SUB_OBJECT_ID"];
		var params = {"criteria.screenId":screenId,"criteria.objectCode":objectCode,"criteria.subObjectId":subObjectId};
		
		/**
		 * get the selected rowId
		 */
	    linkDynScrTab_loadDataInTheForm(params);
	    showHideSrchGrid('linkDynScrTabListGridTbl_Id_'+_pageRef);
	}
}
function linkDynScrTab_onAddClicked()
{
	var changes = $("#linkDynScrTabMaintFormId_"+_pageRef).hasChanges();
	if(changes == 'true' || changes == true)
	{
		_showConfirmMsg(changes_made_confirm_msg,"","linkDynScrTab_onAddClickedAfterConfirm");// Confirmation call if changes made
	}
	else
	{
		linkDynScrTab_onAddClickedAfterConfirm(true);
	}	
	
}
function linkDynScrTab_onAddClickedAfterConfirm()
{
	var actionSrc  = jQuery.contextPath+"/path/dynamicScreen/linkDynScrTabMainAction_initialize?_pageRef="+_pageRef;
	var params = {};
	params["criteria.objectCode"] = $("#linkDynScrTab_objectCode_"+_pageRef).val();
	params["criteria.objectType"] = $("#linkDynScrTab_objectType_"+_pageRef).val();
	$.post(actionSrc
		  ,params
	      ,function(param) {
				$("#linkDynScreenTabMainInfoDiv_id_"+_pageRef).html(param);
		  }
	      ,"html");
	
}
function linkDynScrTab_loadDataInTheForm(params)
{
	var actionSrc   = jQuery.contextPath+"/path/dynamicScreen/linkDynScrTabMainAction_edit?_pageRef="+_pageRef;
	_showProgressBar(true); 
	$.post(actionSrc
		   ,params
	       ,function( param )
 	        {
	    	   if(typeof param == "object")
		    	 {
		    		 if(param._error != "")
		    			 _showErrorMsg(param._error,error_msg_title)
		    	 }
		    	 else
		    	 {		    		 
		            $("#linkDynScreenTabMainInfoDiv_id_"+_pageRef).html(param);		            
		    	 }
		         _showProgressBar(false);
	        }
	       );
	
}
/**
 * 
 */
function linkDynScrTab_onTreeMenuSelection()
{
	var selectedObj = selectScreenMenuFromTree();
	if(!selectedObj)
	{
		return false;
	}
	$("#linkDynScrTab_parentRef_"+_pageRef).val(selectedObj["progRef"]);
	$("#screen_parent_name_"+_pageRef).val(selectedObj["desc"]);
	return true;
}
function linkDynScrTab_setMethodName(methodName)
{
	$("#linkDynScrTab_sbmtMethodName_"+_pageRef).val(methodName);
}
function linkDynScrTab_submitProcess()
{
	var methodName   = $("#linkDynScrTab_sbmtMethodName_"+_pageRef).val();
	if(methodName != 'delete')
    {
		linkDynScrTab_submit(true,{methodName:methodName})
    }
	else
	{
		linkDynScrTab_onDelClicked();
	}
}
function linkDynScrTab_submit(confirm,args)	
{
	if(confirm)
	{
		_showProgressBar(true);
		var methodName   = args.methodName;
		var theForm      = $("#linkDynScrTabMaintFormId_"+_pageRef).serializeForm();
		var objectId  = $("#linkDynScrTab_objectCode_"+_pageRef).val();
		var menuTitle = $("#lookuptxt_labelEdit").val();
		var currScreenId = $("#lookuptxt_linkDynScrTab_screenId").val();
		var tabUrl    = jQuery.contextPath+"/path/dynamicScreen/dynamicScreenMainAction_loadDynamicScreen?criteria.screenId="+currScreenId+"&_pageRef="+_pageRef;
		/**
		 * to reload on delete from after save 
		 */
		var reloadPath   = jQuery.contextPath+"/path/dynamicScreen/linkDynScrTabMainAction_edit?_pageRef="+_pageRef;
		var selScreenId  = $("#lookuptxt_linkDynScrTab_ScreenId").val();
		var reloadParams = {};/*Fix #824261 reset the form after saving {"criteria.screenId":selScreenId};*/
		/**
		 * 
		 */
		$.ajax({
			 url: jQuery.contextPath+"/path/dynamicScreen/linkDynScrTabMainAction_"+methodName,
	         type:"post",
			 dataType:"json",
			 data: theForm,
			 success: function(data)
			 		{
				 		if(typeof data["_error"] == "undefined" || data["_error"] == null)
				 		{
				 			_showErrorMsg(record_was_Updated_Successfully_key,info_msg_title);
				 			$("#linkDynScrTabListGridTbl_Id_"+_pageRef).trigger("reloadGrid");
				 			$("#linkDynScreenTabMainInfoDiv_id_"+_pageRef).load(reloadPath,reloadParams);
				 			var newTabDivId = "newTabDivId_"+data.linkDynScrTabCO.SUB_OBJECT_ID+"_"+_pageRef;
				 			var newTabId    = "newTabId_"+data.linkDynScrTabCO.SUB_OBJECT_ID+"_"+data.linkDynScrTabCO.DYN_SCREEN_ID+"_"+_pageRef;
				 			var tabs = $("#"+objectId).data('taboptions');
				 			$("#"+objectId).tabs( "option", "tabTemplate", "<li class='path-screen-openned' id='"+newTabId+"'><a href='#"+newTabDivId+"'>"+menuTitle+"</a></li>" );
				 			$("#"+objectId).tabs("add",newTabId,menuTitle,tabs.length);
				 			$("#"+objectId).append("<div class='ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide' id='"+newTabDivId+"'></div>");
//				 			linkDynScrTab_onAddClickedAfterConfirm();

				 		}
				 		_showProgressBar(false);
				    }
		});		
	}
}
function linkDynScrTab_onDelClicked()
{
	var subObjId = $("#linkDynScrTab_subObjectId_"+_pageRef).val();
	var objId    = $("#linkDynScrTab_objectCode_"+_pageRef).val();
	var screenId = $("#lookuptxt_linkDynScrTab_screenId").val();
	if(subObjId!=null)
	{
		_showConfirmMsg(Confirm_Delete_Process_key, Warning_key,
				linkDynScrTab_delAfterConfirm, {subObjId:subObjId,objId:objId,screenId:screenId});		
	}
	else
	{
		_showErrorMsg("No Screen Selected to Delete",info_msg_title);
	}	
}
function linkDynScrTab_delAfterConfirm(confirm,args)
{
	var theParam ={};
	theParam["linkDynScrTabCO.SUB_OBJECT_ID"] = parseInt(args.subObjId);
	theParam["linkDynScrTabCO.OBJECT_CODE"]   = args.objId;
	theParam["linkDynScrTabCO.DYN_SCREEN_ID"] = parseInt(args.screenId);
	var _tabbedPanelId = $("#linkDynScrTab_objectCode_"+_pageRef).val();
	var _deletedTabId  = "newTabId_"+args.subObjId+"_"+args.screenId+"_"+_pageRef
	if(confirm)
	{
		$.ajax({
			 url: jQuery.contextPath+"/path/dynamicScreen/linkDynScrTabMainAction_delete",
	         type:"post",
			 dataType:"json",
			 data: theParam,
			 success: function(data){
			 
			     if(typeof data["_error"] == "undefined" || data["_error"] == null)
			     {
	                if($("#linkDynScrTabListGridTbl_Id_"+_pageRef).html()!=null && $("#linkDynScrTabListGridTbl_Id_"+_pageRef).html()!="")
	                {
	                	$("#linkDynScrTabListGridTbl_Id_"+_pageRef).trigger("reloadGrid");
	                	$("#"+_tabbedPanelId).tabs("remove",_deletedTabId);
	                	linkDynScrTab_onAddClickedAfterConfirm();
	                	_showProgressBar(false);
	                }
	             } 
			 }
	    });
	}
}