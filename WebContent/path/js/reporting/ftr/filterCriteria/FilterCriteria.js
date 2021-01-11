$(document).ready(function()
		{
			if(!$("#typeChk").attr('checked'))
				$("#detailsSel").css("display", "none"); 
		}
);

function checkIDAvailability()
{
	var crtCode= $("#codeField_"+_pageRef).val();
	if(crtCode!="")
	{
		var zSrc=jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_countOfFilterCriteria.action?code='+crtCode;
		params ={};
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{		var currVal=data2['code'];
			if(currVal=="1") 
			{
				_showErrorMsg(codeExistAlert);
				$("#codeField_"+_pageRef).val("");
				document.getElementById("codeField_"+_pageRef).focus();
			}
			});
	}
}

function reloadGrid()
{
	$("#filterCritTable").trigger("reloadGrid");		
}
	
function closeDialog() 
{
	$('#filterCritDialog').dialog('close');
	return false;
}

function showHideDetails(isChecked)
{
	if(isChecked)
		$("#detailsSel").css("display", "");
	else
		$("#detailsSel").css("display", "none");
}

function doSubmit() 
{
	$("#codeField").attr('disabled', false);
}

function addNew()
{ 	
	if($("#codeField_"+_pageRef).attr('readonly')=="readonly" && $("#codeField_"+_pageRef).val()=="")
	{
		emptyForm();
	}
	_showProgressBar(true);
		var parentRowId = $("#filterCritTable_"+_pageRef).jqGrid('getGridParam','selrow');
		selRowObj = $("#filterCritTable_"+_pageRef).jqGrid('getRowData', parentRowId);
		var url = jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_update.action';
		var params=$("#filterCritForm_"+_pageRef).serialize();
		params+="&oldDesc="+selRowObj["BRIEF_DESC_ENG"]+"&oldCrtType="+selRowObj["CRI_TYPE"];
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
					callDependency(	"criteriaType_"+_pageRef+":fcCO.glstmpltCriteriaVO.CRI_TYPE",
					jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_applySmartValidation.action',
					"fcCO.glstmpltCriteriaVO.CRI_TYPE:1",
					"criteriaType_",
					"")
				}
			   _showProgressBar(false);
			 }
	    });
		$("#filterCritForm_"+_pageRef+" #lookuptxt_smartLookupSearch_"+_pageRef).val("");
		$("#filterCritForm_"+_pageRef+" #smartCode_"+_pageRef).val("");
}
function reloadGrid()
{
	$("#filterCritTable_"+_pageRef).trigger("reloadGrid");
}

function emptyForm()
{

	$("#codeField_"+_pageRef).val("");
	$("#criteriaDesc_"+_pageRef).val("");
	$("#criteriaType_"+_pageRef).val("");
	$("#codeField_"+_pageRef).attr("readonly",false);
	$("#actionType_"+_pageRef).val("add");
	
	$("#longDescArab_"+_pageRef).val("");
	$("#briefDescArab_"+_pageRef).val("");
	$("#longDescEng_"+_pageRef).val("");
	$("input:radio[name='fcCO.glstmpltCriteriaVO.CRI_LINE_GL']")[0].checked = true;
	callDependency(	"criteriaType_"+_pageRef+":fcCO.glstmpltCriteriaVO.CRI_TYPE",
					jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_applySmartValidation.action',
					"fcCO.glstmpltCriteriaVO.CRI_TYPE:1",
					"criteriaType_",
					"")

}

function deleteFilterCrit(sel_row_index)
{	
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteCrit", args);
}

function deleteCrit(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)      
	{
		_showProgressBar(true);
		var criteriaCode = $("#filterCritTable_"+_pageRef).jqGrid('getCell', selRow,'glstmpltCriteriaVO.CODE');
		var zSrc=jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_countOfUsedFilterCriteria.action?code='+criteriaCode;
		params ={};
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
				{
						var usageCount=data2['usageCount'];
			 			if(usageCount > 0) 
			 			{
			 				_showErrorMsg(critDel_msg_key);
			 				_showProgressBar(false);
							return;
				 		}
			 			else
			 			{
			 					$.ajax({
			 						url: jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_delete.action',
			 						type: "POST",
			 						data: ({code : criteriaCode, _pageRef:_pageRef}),				
			 					    success: function(xml){
			 					    	reloadGrid();
			 							$("#codeField_"+_pageRef).val("");
			 							$("#criteriaDesc_"+_pageRef).val("");
			 							$("#criteriaType_"+_pageRef).val("");
			 							$("#longDescArab_"+_pageRef).val("");
			 							$("#briefDescArab_"+_pageRef).val("");
			 							$("#longDescEng_"+_pageRef).val("");
			 							$("#lookuptxt_smartLookupSearch_"+_pageRef).val("");
			 							$("#smartCode_"+_pageRef).val("");
			 							$("#codeField_"+_pageRef).removeAttr("readonly");
			 							$("#actionType_"+_pageRef).val("add");
			 							callDependency(	"criteriaType_"+_pageRef+":fcCO.glstmpltCriteriaVO.CRI_TYPE",
										jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_applySmartValidation.action',
										"fcCO.glstmpltCriteriaVO.CRI_TYPE:1",
										"criteriaType_",
										"")
			 							_showProgressBar(false);
			 		    			}
			 					});
			 			}
				});
	}
}

function editFilterCrit() 
			{
			_showProgressBar(true);
			rowid = 	$("#filterCritTable_"+_pageRef).jqGrid('getGridParam','selrow');			
			myObject = $("#filterCritTable_"+_pageRef).jqGrid('getRowData', rowid);
			var code = $("#filterCritTable_"+_pageRef).jqGrid('getCell', rowid, 'glstmpltCriteriaVO.CODE');
			var url =jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_retrieveFitlCrt.action?code='+code;
				params = {};
				paramStr = JSON.stringify(myObject);
				paramStr = "{"+ "fcCO:"+paramStr + "}";
				params["updates"] = paramStr;
				params["_pageRef"]=_pageRef;
				$.post(url, params , function( param )
		 		{
		 		
		 			$("#fcDiv_"+_pageRef).html(param);
		 			$("#codeField_"+_pageRef).attr("readonly","true");
		 			$("#actionType_"+_pageRef).val("edit");
		 			_showProgressBar(false);
		 		},"html");


			}

function filterCrtReadyFunc()
{
	$("#filterCritTable_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	{
		 		 $("#filterCritForm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("")
		 		 emptyForm();
		 		 callDependency(	"criteriaType_"+_pageRef+":fcCO.glstmpltCriteriaVO.CRI_TYPE",
					jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_applySmartValidation.action',
					"fcCO.glstmpltCriteriaVO.CRI_TYPE:1",
					"criteriaType_",
					"")
					$("#gview_filterCritTable_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
					
	});

	
	if(!$("#typeChk").attr('checked'))
		$("#detailsSel").css("display", "none"); 
	
	$("#filterCritTable_"+_pageRef).subscribe('dialogclosetopic', function(event,ui) {
        $.ajax({
			url: '${pageContext.request.contextPath}/path/filterCriteria/filterCriteriaList_refresh.action',
			success: function(xml){
		    	reloadGrid();
			}
		});
	});
		 $("#filterCritForm_"+_pageRef+" #smartLookupSearch_"+_pageRef).val("")
		 $("#filterCritForm_"+_pageRef+" #smartCode_"+_pageRef).val("")
		 callDependency(	"criteriaType_"+_pageRef+":fcCO.glstmpltCriteriaVO.CRI_TYPE",
					jQuery.contextPath+'/path/filterCriteria/filterCriteriaList_applySmartValidation.action',
					"fcCO.glstmpltCriteriaVO.CRI_TYPE:1",
					"criteriaType_",
					"")
					
}
