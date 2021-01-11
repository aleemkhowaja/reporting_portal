
function dynScreenParamMapGrid_addMapGrid()
{
	var thCustTbl =	$("#dynScreenParamMapGrid_Id_"+currentPageRef);
	// add new row
	var rId = thCustTbl.jqGrid('addInlineRow', {});
}

function dynScreenParamMapGrid_deleteMapGrid(theRowId)
{
	_showConfirmMsg((typeof Confirm_Delete_Process_key != undefined)?Confirm_Delete_Process_key:"Delete Selected Row?",confirm_msg_title,function(yesNo)
	    {
			if(yesNo) 
			{
				var $table = $("#dynScreenParamMapGrid_Id_" + currentPageRef);
				var selectedRowObject = $table.jqGrid('getRowData', theRowId);
				var fldMapId = selectedRowObject["sysParamElmDynScrnMapDet.FLD_MAP_ID"];
				var elementMapId = selectedRowObject["sysParamElmDynScrnMapDet.ELEMENT_MAP_ID"];
				
				if(fldMapId == '')
				{
					$("#dynScreenParamMapGrid_Id_"+currentPageRef).jqGrid('deleteGridRow',theRowId);	
				}
				else
				{
					
					_showProgressBar(true);
					var deleteAction = jQuery.contextPath+"/path/dynamicScreen/dynamicScreenMainAction_deleteDynScreenFieldMapping.action";
					var deleteParam = { 
										"dynamicScreenParamsMapCO.sysParamElmDynScrnMapDet.FLD_MAP_ID": fldMapId,
										"dynamicScreenParamsMapCO.sysParamElmDynScrnMapDet.ELEMENT_MAP_ID": elementMapId
									  };
					
					$.ajax({
						 url: deleteAction,
				         type:"post",
						 dataType:"json",
						 data: deleteParam,
						 success: function(data){
						     
							 if(typeof data["_error"] == "undefined" || data["_error"] == null)
						     {	 
								   if($("#dynScreenParamMapGrid_Id_"+currentPageRef).html()!=null && $("#dynScreenParamMapGrid_Id_"+currentPageRef).html()!="")
						           {
						            	 $("#dynScreenParamMapGrid_Id_"+currentPageRef).trigger("reloadGrid");
						           }
				             } 
							 
							 _showProgressBar(false);
							 
						 }
				    });
					
				}
			}
		},"yesNo");
}

function dynScreenParamMapGrid_mapTypeChanged()
{
	var $table = $("#dynScreenParamMapGrid_Id_" + currentPageRef);
	var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
	var selectedRowObject = $table.jqGrid('getRowData', selectedRowId);
	var mapTypeDesc = selectedRowObject["mapTypeDesc"];
	$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.MAP_TYPE",mapTypeDesc);
	
	//constant map type
	if(mapTypeDesc == '3')
	{
		$table.jqGrid('setCellReadOnly', selectedRowId, "mapValueConstant","false");
		$table.jqGrid('setCellReadOnly', selectedRowId, "sysParamElmDynScrnMapDet.MAP_VALUE","true");
	}//screen or session map type
	else if(mapTypeDesc == '1' || mapTypeDesc == '2')
	{
		$table.jqGrid('setCellReadOnly', selectedRowId, "sysParamElmDynScrnMapDet.MAP_VALUE","false");
		$table.jqGrid('setCellReadOnly', selectedRowId, "mapValueConstant","true");
	}
	$table.jqGrid('setCellValue', selectedRowId,"mapValueConstant",'');
	$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.MAP_VALUE",'');
	$table.jqGrid('setCellValue', selectedRowId,"mapValueDesc",'');
	
	adjustMapValueResultList(mapTypeDesc,$('#dynScreenParamMapGrid_MAP_ELEMENT_TYPE_' + currentPageRef).val());
	
}

function adjustMapValueResultList(mapTypeDesc,mapElementType)
{
	if(mapTypeDesc == '1')
	{
		jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".resultList = 'FLD_IDENTIFIER:sysParamElmDynScrnMapDet.MAP_VALUE_lookuptxt,FIELD_KEY_LABEL_CODE:mapValueDesc'");
		if(mapElementType == '4' || mapElementType == '5')
		{
			jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".paramList = 'screenId:screenId'");
			jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".actionName = '"+ jQuery.contextPath + "/path/dynamicScreen/dynScreenLookupAction_drawingDynScreenElementsGrid" +"'");
			jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".resultList = 'elementIdValue:sysParamElmDynScrnMapDet.MAP_VALUE_lookuptxt'");
		}
		else
		{
			jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".paramList = 'criteria.mapType:sysParamElmDynScrnMapDet.MAP_TYPE,criteria.currAppName:dynScreenParamMapGrid_APP_NAME_" + currentPageRef + ",criteria.progRef:dynScreenParamMapGrid_currentPageRef_" + currentPageRef + "'");
			jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".actionName = '"+ jQuery.contextPath + "/path/buttoncustomization/ButtonCustomizationLookupAction_constructScreenElementsOrSessionLookup" +"'");
			jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".resultList = 'FLD_IDENTIFIER:sysParamElmDynScrnMapDet.MAP_VALUE_lookuptxt,FIELD_KEY_LABEL_CODE:mapValueDesc'");
		}
	}
	else if( mapTypeDesc == '2')
	{
		jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".paramList = 'criteria.mapType:sysParamElmDynScrnMapDet.MAP_TYPE,criteria.currAppName:dynScreenParamMapGrid_APP_NAME_" + currentPageRef + ",criteria.progRef:dynScreenParamMapGrid_currentPageRef_" + currentPageRef + "'");
		jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".actionName = '"+ jQuery.contextPath + "/path/buttoncustomization/ButtonCustomizationLookupAction_constructScreenElementsOrSessionLookup" +"'");
		jQuery.globalEval("options_liveSearch_sysParamElmDynScrnMapDet_MAP_VALUE_dynScreenParamMapGrid_Id_"  + currentPageRef + ".resultList = 'propertyName:sysParamElmDynScrnMapDet.MAP_VALUE_lookuptxt,description:mapValueDesc'");
	}
}

function dynScreenParamMapGrid_onRowSelTopic(rowObj)
{
	var $table = $("#dynScreenParamMapGrid_Id_" + currentPageRef);
	var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
	var selectedRowObject = $table.jqGrid('getRowData', selectedRowId);
	var mapTypeDesc = selectedRowObject["sysParamElmDynScrnMapDet.MAP_TYPE"];
	var dynParamTypeDesc = selectedRowObject["sysParamElmDynScrnMapDet.DYN_PARAM_TYPE"];

	if(dynParamTypeDesc == '')
	{
		dynParamTypeDesc = returnHtmlEltValue( selectedRowId + '_dynParamTypeDesc');
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.DYN_PARAM_TYPE",dynParamTypeDesc);

	}
	
	if(mapTypeDesc == '')
	{
		mapTypeDesc = returnHtmlEltValue( selectedRowId + '_mapTypeDesc');
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.MAP_TYPE",mapTypeDesc);

	}
	if(mapTypeDesc == '3')
	{
		$table.jqGrid('setCellReadOnly', selectedRowId, "mapValueConstant","false");
		$table.jqGrid('setCellReadOnly', selectedRowId, "sysParamElmDynScrnMapDet.MAP_VALUE","true");
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.MAP_VALUE",'');
	}
	else if(mapTypeDesc == '1' || mapTypeDesc == '2')
	{
		$table.jqGrid('setCellReadOnly', selectedRowId, "sysParamElmDynScrnMapDet.MAP_VALUE","false");
		$table.jqGrid('setCellReadOnly', selectedRowId, "mapValueConstant","true");
		$table.jqGrid('setCellValue', selectedRowId,"mapValueConstant",'');
	}
	
	var disableScreenLookup = $('#dynScreenParamMapGrid_DisableScreenLookup_' + currentPageRef).val();
	if(disableScreenLookup != undefined && disableScreenLookup == 'true')
	{
		$table.jqGrid('setCellReadOnly', selectedRowId, "sysDynScreenDef.DYN_SCREEN_ID","true");
		
		var defaultScreenId = $('#dynScreenParamMapGrid_DefaultScreenId_' + currentPageRef).val();
		var defaultScreenDesc = $('#dynScreenParamMapGrid_DefaultScreenDesc_' + currentPageRef).val();
		
		if(defaultScreenId != undefined && defaultScreenId != null && defaultScreenId >= 0 
			&& defaultScreenDesc != undefined && defaultScreenDesc != null )
		{
			$table.jqGrid('setCellValue', selectedRowId,"sysDynScreenDef.DYN_SCREEN_ID",defaultScreenId);
			$table.jqGrid('setCellValue', selectedRowId,"sysDynScreenDef.DYN_SCREEN_DESC",defaultScreenDesc);
		}
	}
	
	adjustMapValueResultList(mapTypeDesc,$('#dynScreenParamMapGrid_MAP_ELEMENT_TYPE_' + currentPageRef).val());
		
	//Initialize rows default values
	var progRef = selectedRowObject["sysParamElmDynScrnMap.PROG_REF"];
	if(progRef == '')
	{
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMap.PROG_REF", $('#dynScreenParamMapGrid_PROG_REF_' + currentPageRef).val() );
	}
	
	var appName = selectedRowObject["sysParamElmDynScrnMap.APP_NAME"];
	if(appName == '')
	{
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMap.APP_NAME", $('#dynScreenParamMapGrid_APP_NAME_' + currentPageRef).val() );
	}
	
	var compCode = selectedRowObject["sysParamElmDynScrnMap.COMP_CODE"];
	if(compCode == '')
	{
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMap.COMP_CODE", $('#dynScreenParamMapGrid_COMP_CODE_' + currentPageRef).val() );
	}
	
	var mapElementType = selectedRowObject["sysParamElmDynScrnMap.MAP_ELEMENT_TYPE"];
	if(mapElementType == '')
	{
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMap.MAP_ELEMENT_TYPE", $('#dynScreenParamMapGrid_MAP_ELEMENT_TYPE_' + currentPageRef).val() );
	}
	
	var elementIdentifier = selectedRowObject["sysParamElmDynScrnMap.ELEMENT_IDENTIFIER"];
	if(elementIdentifier == '')
	{
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMap.ELEMENT_IDENTIFIER", $('#dynScreenParamMapGrid_ELEMENT_IDENTIFIER_' + currentPageRef).val() );
	}
	
	var elementOpId = selectedRowObject["sysParamElmDynScrnMap.ELEMENT_OP_ID"];
	if(elementOpId == '')
	{
		$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMap.ELEMENT_OP_ID", $('#dynScreenParamMapGrid_ELEMENT_OP_ID_' + currentPageRef).val() );
	}
	

}

function dynScreenParamMapGrid_dynParamTypeChanged()
{
	var $table = $("#dynScreenParamMapGrid_Id_" + currentPageRef);
	var selectedRowId = $table.jqGrid('getGridParam', 'selrow');
	var selectedRowObject = $table.jqGrid('getRowData', selectedRowId);
	var mapTypeDesc = selectedRowObject["dynParamTypeDesc"];
	$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.DYN_PARAM_TYPE",mapTypeDesc);
	$table.jqGrid('setCellValue', selectedRowId,"sysParamElmDynScrnMapDet.TO_ELEMENT_ID",'');
	$table.jqGrid('setCellValue', selectedRowId,"elementHtmlId",'');
}