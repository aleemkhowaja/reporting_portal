function dynScrGridList_loadMainDiv()
{
	if($("dyn_div_dynScreen_"+ dynScreenId +"_FormId"+_pageRef).length>0)
	{
		return;
	}
	
	_showProgressBar(true);
	var loadUrl = jQuery.contextPath+"/path/dynamicScreen/dynamicScreenMainAction_fillDynamicFormAfterGridLoad?";

	var loadParams = {};
	loadParams["criteria.screenId"] = dynScreenId;
	loadParams["_pageRef"] = _pageRef;

	$.post(loadUrl, loadParams, function(param)
	{
		$("#dynamicScreen_"+ dynScreenId +"_main_div_"+_pageRef).html(param);
		if($("#"+targetDivId).length)
		{
			 var targetDivId = "newTabDivId_"+ dynScreenId +"_"+_pageRef; 
			   var _divContents = $("#"+targetDivId).find("div,input,button,select,img,textarea");
			   var _maxTopVal = 0;
			   var _maxTopElemHeight = 0;
			  	   _divContents.each( function() {
				 var _elemTopVal    = parseInt($(this).css("top"));
				 var _elemHeightVal = parseInt($(this).css("height"));
				 if(_elemTopVal != null && !isNaN(_elemTopVal) && _elemTopVal > _maxTopVal)
				 {
					 _maxTopVal = _elemTopVal;
					 _maxTopElemHeight = isNaN(_elemHeightVal)?0:_elemHeightVal;
				 }
			   });
			   var _actualDivHeight  = _maxTopVal + _maxTopElemHeight + 20 + $('#dynamicScreen_'+dynScreenId+'_GridDiv_'+_pageRef).height();
			   $("#"+targetDivId).css("height",_actualDivHeight+"px");
		}
		_showProgressBar(false);
	}, "html");
}

function dynScrGridList_onDbClickedEvent()
{
	if(_pageRef == "")
	{
		var changed = $("#dynScreen_" + dynScreenId + "_FormId").hasChanges();
	}
	else
	{
		var changed = $("#dynScreen_" + dynScreenId + "_FormId_"+_pageRef).hasChanges();
	}
	if (changed == 'true' || changed == true)
	{
		_showConfirmMsg(changes_made_confirm_msg, "", "dynScrGridListDbClicked", "yesNo");
	}
	else
	{
		dynScrGridListDbClicked(true);
	}
}
function dynScrGridListDbClicked(yesNo)
{
	if (yesNo)
	{
		_showProgressBar(true);
		var $table = $("#dynamicScreen_"+dynScreenId+"_Grid_"+ _pageRef);
		var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
		var myObject = $table.jqGrid('getRowData', selectedRowId);
		var params = {};
		
		// get the selected rowId
		var primaryKeyArr = myObject.PRIMARY_COL.split(',');
		
		for (var i = 0; i<primaryKeyArr.length; i ++)
		{
			params["scrElemHm."+primaryKeyArr[i]]= myObject[primaryKeyArr[i]];
		}
		params["criteria.screenId"] = dynScreenId;
		params["criteria.modeType"] = "load";
		params["_pageRef"] = _pageRef;

		var action = jQuery.contextPath + "/path/dynamicScreen/dynamicScreenMainAction_fillDynamicFormAfterGridLoad?";

		$.post(action, params, function(param)
		{
			$("#dynamicScreen_"+ dynScreenId +"_main_div_"+_pageRef).html(param);
			showHideSrchGrid('dynamicScreen_'+dynScreenId+'_Grid_'+ _pageRef);
			_showProgressBar(false);
		}, "html");
		

	}
}


function dynScrGridList_onAddEvent()
{
	if(_pageRef == "")
	{
		var changed = $("#dynScreen_" + dynScreenId + "_FormId").hasChanges();
	}
	else
	{
		var changed = $("#dynScreen_" + dynScreenId + "_FormId_"+_pageRef).hasChanges();
	}
	
	if (changed == 'true' || changed == true)
	{
		_showConfirmMsg(changes_made_confirm_msg, "", "dynScrGridListAdd", "yesNo");
	}
	else
	{
		dynScrGridListAdd(true);
	}
}

function dynScrGridListAdd(yesNo)
{
	if (yesNo)
	{
		_showProgressBar(true);
		
		var loadUrl = jQuery.contextPath+"/path/dynamicScreen/dynamicScreenMainAction_fillDynamicFormAfterGridLoad?";

		var reLoadParams = {};
		reLoadParams["criteria.screenId"] = dynScreenId;
		reLoadParams["_pageRef"] = _pageRef;

		$.post(loadUrl, reLoadParams, function(param)
		{
			$("#dynamicScreen_"+ dynScreenId +"_main_div_"+_pageRef).html(param);
			showHideSrchGrid('dynamicScreen_'+dynScreenId+'_Grid_'+ _pageRef);
			_showProgressBar(false);
		}, "html");
	}
}