function linkDynScreen_onDbClickedEvent()
{
	var $table = $("#linkDynScreenListGridTbl_Id_"+_pageRef);
	var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
	var myObject = $table.jqGrid('getRowData', selectedRowId);
	/**
	 * get the selected rowId
	 */
	var progRef  = myObject["PROG_REF"];
	var screenId = myObject["DYN_SCREEN_ID"];
	var changes = $("#linkDynScreenMaintFormId_"+_pageRef).hasChanges();
	if(changes == 'true' || changes == true)
	{
		_showConfirmMsg(changes_made_confirm_msg,"","linkDynScreen_onDbClickedAfterConfirm");// Confirmation call if changes made
	}
	else
	{
		linkDynScreen_onDbClickedAfterConfirm(true);
	}	
}
function linkDynScreen_onDbClickedAfterConfirm(yesNo)
{
	if(yesNo)
	{
		var $table = $("#linkDynScreenListGridTbl_Id_"+_pageRef);
		var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
		var myObject = $table.jqGrid('getRowData', selectedRowId);
		
		/**
		 * get the selected rowId
		 */
	    linkDynScreen_loadDataInTheForm(myObject["PROG_REF"],myObject["DYN_SCREEN_ID"]);
	    showHideSrchGrid('linkDynScreenListGridTbl_Id_'+_pageRef);
	}
}
function linkDynScreen_onAddClicked()
{
	var changes = $("#linkDynScreenMaintFormId_"+_pageRef).hasChanges();
	if(changes == 'true' || changes == true)
	{
		_showConfirmMsg(changes_made_confirm_msg,"","linkDynScreen_onAddClickedAfterConfirm");// Confirmation call if changes made
	}
	else
	{
		linkDynScreen_onAddClickedAfterConfirm(true);
	}	
	
}
function linkDynScreen_onAddClickedAfterConfirm()
{
	var actionSrc  = jQuery.contextPath+"/path/dynamicScreen/linkDynScreenMainAction_initialize?_pageRef="+_pageRef;
	$.post(actionSrc
		  ,{}
	      ,function(param) {
				$("#linkDynScreenMainInfoDiv_id_"+_pageRef).html(param);
		  }
	      ,"html");
	
}
function linkDynScreen_loadDataInTheForm(selProfRef,selScreenId)
{
	var actionSrc   = jQuery.contextPath+"/path/dynamicScreen/linkDynScreenMainAction_edit?_pageRef="+_pageRef;
	_showProgressBar(true); 
	$.post(actionSrc
		   ,{"criteria.screenId":selScreenId,"criteria.progRef":selProfRef}
	       ,function( param )
 	        {
	    	   if(typeof param == "object")
		    	 {
		    		 if(param._error != "")
		    			 _showErrorMsg(param._error,error_msg_title)
		    	 }
		    	 else
		    	 {		    		 
		            $("#linkDynScreenMainInfoDiv_id_"+_pageRef).html(param);		            
		    	 }
		         _showProgressBar(false);
	        }
	       );
	
}
/**
 * 
 * @param theFormId
 */
function linkDynScreen_openMenuTree(theFormId)
{
	var	treeMenuDiv = $("<div id='treeMenu_div'/>");
	treeMenuDiv.css("padding","0");
    var theForm = $("#"+theFormId);

    var theParams = {};
		theParams.saveAsParent = '1';
    $("body").append(treeMenuDiv);    
    var curParams = theParams;
    if(!curParams)
    {
    	curParams = {};
    }
    _showProgressBar(true);
    var srcURL = jQuery.contextPath+"/path/loadTreeMenu";
    var _dialogOptions = {modal:true, 
                          title:choose_parent_key,
                       autoOpen:false,
                           show:'slide',
                       position:'center', 
                          width:returnMaxWidth(370),
                         height:returnMaxHeight(600),
                        buttons:{
	                             ok:function(){
	                                     if(linkDynScreen_onTreeMenuSelection())
	                                     {    	                                    	 
	                                        $(this).dialog("close");
	                                        $(this).dialog("destroy");
	                                        $(this).remove();
	                    					
	                                     }}
	                	    	 ,cancel: function(){
	                	    		 $(this).dialog("close");
	                	    		 $(this).dialog("destroy");
	                	    		 $(this).remove();}
                                },
	                      close: function (){
	 								           var theDialog = $(this);
	 								           theDialog.remove();
								}};
    $("#treeMenu_div").load(srcURL, curParams,function(){_showProgressBar(false);});
    $("#treeMenu_div").dialog(_dialogOptions);
    $("#treeMenu_div").dialog("open");
}
/**
 * 
 */
function linkDynScreen_onTreeMenuSelection()
{
	var selectedObj = selectScreenMenuFromTree();
	if(!selectedObj)
	{
		return false;
	}
	$("#linkDynScreen_parentRef_"+_pageRef).val(selectedObj["progRef"]);
	$("#screen_parent_name_"+_pageRef).val(selectedObj["desc"]);
	return true;
}
function linkDynScreen_setMethodName(methodName)
{
	$("#linkDynScreen_sbmtMethodName_"+_pageRef).val(methodName);
}
function linkDynScreen_submitProcess()
{
	var methodName   = $("#linkDynScreen_sbmtMethodName_"+_pageRef).val();
	if(methodName != 'delete')
    {
		linkDynScreen_submit(true,{methodName:methodName})
    }
	else
	{
		_showConfirmMsg(Confirm_Delete_Process_key, Warning_key,linkDynScreen_submit, {methodName : methodName});
	}
}
function linkDynScreen_submit(confirm,args)	
{
	if(confirm)
	{
		_showProgressBar(true);
		var methodName   = args.methodName;
		var theForm      = $("#linkDynScreenMaintFormId_"+_pageRef).serializeForm();
		var parentRef    = $("#linkDynScreen_parentRef_"+_pageRef).val();
		/**
		 * to reload on delete from after save 
		 */
		var reloadPath   = jQuery.contextPath+"/path/dynamicScreen/linkDynScreenMainAction_edit?_pageRef="+_pageRef;
		var selProgRef   = $("#linkDynScreen_screen_ref_"+_pageRef).val();
		var selScreenId  = $("#lookuptxt_linkDynScreen_ScreenId").val();
		var reloadParams = {};/*Fix Bug #824261 we should reset the form after saving new record {"criteria.screenId":selScreenId,"criteria.progRef":selProgRef};*/
		/**
		 * 
		 */
		$.ajax({
			 url: jQuery.contextPath+"/path/dynamicScreen/linkDynScreenMainAction_"+methodName,
	         type:"post",
			 dataType:"json",
			 data: theForm,
			 success: function(data)
			 		{
				 		if(typeof data["_error"] == "undefined" || data["_error"] == null)
				 		{
				 			if(typeof parentRef !="undefined" && parentRef != null && parentRef!="")
				 			{			 				
				 				accordionReloadMenuItem(parentRef);
				 			}
				 			else
				 			{
				 				accordionReloadMenuItem("ROOT");
				 			}
				 			_showErrorMsg(record_was_Updated_Successfully_key,info_msg_title);
				 			$("#linkDynScreenListGridTbl_Id_"+_pageRef).trigger("reloadGrid");
				 			$("#linkDynScreenMainInfoDiv_id_"+_pageRef).load(reloadPath,reloadParams);
				 		}
				 		_showProgressBar(false);
				    }
		});		
	}
}