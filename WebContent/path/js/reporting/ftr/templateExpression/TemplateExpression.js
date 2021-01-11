function rep_colTempl_ready_func()
{
	$("#exprTemplGrid_"+_pageRef).subscribe("disableGLTab",function(event,data)
			{
				var lineCount=$("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam','records');
				var isDisabledTab=$.inArray(0, $("#tabs").tabs("option", "disabled"))
				if(lineCount >0 && isAdd=="false"  && isDisabledTab=="-1")//when adding the fist record to the grid 
				{
					$("#tabs").tabs({disabled: [0,1]});
				}
				if(lineCount ==0 && isAdd=="false"  && isDisabledTab=="0") //when deleting the last record
				{
					$("#tabs").tabs('enable', 0);
					$("#tabs").tabs('enable', 1);
				}
				if(isAdd=="true")
				{
					isAdd="false";
				}
			});

			$("#exprTemplGrid_"+_pageRef).subscribe('selectExprLine', function(event,data) {
				exprRowId = (event["originalEvent"])["id"];
			});

			$("#exprTemplGrid_"+_pageRef).subscribe('disableOperCol', function(event,data) 
			{
				rowId = (event["originalEvent"])["id"];
				myObject = $("#exprTemplGrid_"+_pageRef).jqGrid('getRowData',rowId);
				var expOrder=myObject["EXP_ORDER"];
				var rowCount = $("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam', 'records');		
				var ind = $("#exprTemplGrid_"+_pageRef).jqGrid("getInd",rowId)
				var currPageNb=$("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam','page')
				if(ind==1 && currPageNb==1)
				{	
					$("#exprTemplGrid_"+_pageRef).jqGrid("setCellReadOnly",rowId,"ftrTmpltExprVO.OPERATOR",true);	  
				}
				else if(myObject["ftrTmpltExprVO.OPERATOR"]=="")
				{
					$("#exprTemplGrid_"+_pageRef).jqGrid('setCellValue',rowId,'ftrTmpltExprVO.OPERATOR',"+");
				}
			});
			$("#lineNbr").val(globalLineNbr);
			if($("#tempCodeWithLineNbExpr_"+_pageRef).val()=="")
			{
				$("#tempCodeWithLineNbExpr_"+_pageRef).val(globTmpCodeWithLnNb);
			}
}

function checkCellCol(obj)
{	
	var rowidModifs = $("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#exprTemplGrid_"+_pageRef).jqGrid('getRowData',rowidModifs);
	var expType=myObject["ftrTmpltExprVO.EXP_TYPE"];
	   var expLine=myObject["ftrTmpltExprVO.EXP_VALUE"];   	
	if(expType=="L")
	{
		if (expLine <0 ) 
		{ 
		   _showErrorMsg(positiveNbAlert, error_msg_title, 300, 100);
		   	$("#exprTemplGrid_"+_pageRef).jqGrid('setCellValue', rowidModifs, 'ftrTmpltExprVO.EXP_VALUE','');
		   	return;
		}
		var url = jQuery.contextPath+ "/path/templateExpression/lineOrValueChange";
		params ={};
		params["EXP_TYPE"]=expType;
		params["EXP_LINE"] =expLine;
		params["_pageRef"]=_pageRef;
		params["tempCode_LineNb"]=$("#tempCodeWithLineNbExpr_"+_pageRef).val();
		
		$.getJSON(url, params, function( data2, status, xhr ) 
		{
		   var retVal=data2['updates'];
		   if(retVal=="false")
		   {
			   _showErrorMsg(invalidLineNbAlert, error_msg_title, 300, 100);
			 	$("#exprTemplGrid_"+_pageRef).jqGrid('setCellValue', rowidModifs, 'ftrTmpltExprVO.EXP_VALUE','');
		   }
		 		});
	}
}

function addExprLineCol()
{
	var tempCodeWithLineNb = $("#tempCodeWithLineNbExpr_"+_pageRef).val();
	if (tempCodeWithLineNb == "0~0") 
	{
		_showErrorMsg(selectLineAlertExpr);
		return;
	}
	if($("#lineNbr").val() == "")
	{
		_showErrorMsg(selectLineAlert, error_msg_title, 300, 100);
		return;
	}
	//check if we didn't select a line
	if($("#templCode").val()=="")
	{
		_showErrorMsg(tmpCodeExistAlertExpr);
		return;
	}
	
	var tempCodeWithLineNb=$("#tempCodeWithLineNbExpr_"+_pageRef).val();
//	
//	if(tempCodeWithLineNb=="0~0")
//	{
//		_showErrorMsg(selectLineAlertExpr);
//		return;
//	}
	
	//check if all operators are filled
	var rowCount = $("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam', 'records');
	var datafromgrid = $("#exprTemplGrid_"+_pageRef).jqGrid('getRowData');
	for (var i = 0; i < rowCount; i++)
	{
		var zRow=datafromgrid[i];
		if(zRow!=null)
		{
			if((i!=0 && (zRow["ftrTmpltExprVO.OPERATOR"]=="" || zRow["ftrTmpltExprVO.EXP_VALUE"]=="")) || 
		    		(i==0 && zRow["ftrTmpltExprVO.EXP_VALUE"]==""))
			{
			_showErrorMsg(missingReqInputs, error_msg_title, 300, 100);
			_showProgressBar(false)
			return;
			}
		}
	}
	
	//save previous row
	var jsonStringUpdates = $("#exprTemplGrid_"+_pageRef).jqGrid('getChangedRowData'); 
	$("#updatesExpr_"+_pageRef).val(jsonStringUpdates); 
	var url = $("#exprTemplGridform_"+_pageRef).attr("action");
	myObject =  $("#exprTemplGridform_"+_pageRef).serialize();
	
//	$.post(url, myObject , function( param )
// 	{
//   	  $("#exprTemplGrid_"+_pageRef).trigger("reloadGrid");
//   	  isAdd="true";
//   	  window.setTimeout("delayFunc()" ,1000);
// 	});
	
	$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			   if(typeof param["_error"] == "undefined" || param["_error"] == null)
			   {
					$("#exprTemplGrid_"+_pageRef).trigger("reloadGrid");
				   	  isAdd="true";
				   	  window.setTimeout("delayFunc()" ,1000);
			   }
			 }
	    });
	
}
 
function delayFunc()
{
	var rowCount = $("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam', 'records');
	if(rowCount==0)
	{
		$("#exprTemplGrid_"+_pageRef).jqGrid('addInlineRow',{OPERATOR:''});
	}
	else
	{
		$("#exprTemplGrid_"+_pageRef).jqGrid('addInlineRow',{OPERATOR:'+'});
	}	
}

function deleteExprCol(rowId)
{
	var myObject     	= $("#exprTemplGrid_"+_pageRef).jqGrid('getRowData',rowId);
	var subLineNbr 		= myObject["ftrTmpltExprVO.LINE_NO"];
	var lineNbr 		= myObject["newLineNumber"];
	if(subLineNbr=="" && lineNbr=="")
	{
		 $("#exprTemplGrid_"+_pageRef).jqGrid('deleteGridRow');
	}
	else
	{
	
		 var allRowIds =  $("#exprTemplGrid_"+_pageRef).jqGrid('getDataIDs');
		 var indexRowBeingDeleted = $("#exprTemplGrid_"+_pageRef).jqGrid("getInd",rowId);
		 if(allRowIds)
		 {		
		  	for (var i =0;i<allRowIds.length+1;i++)
		    {
		    	llRowId=allRowIds[i];
		    	var ind = $("#exprTemplGrid_"+_pageRef).jqGrid("getInd",llRowId)
		    	if(ind==2 && indexRowBeingDeleted==1)
		    	{
		    		$("#exprTemplGrid_"+_pageRef).jqGrid('setCellValue',llRowId,'ftrTmpltExprVO.OPERATOR',"");
		    		break;
		    	}
		    }
		  } 
		params={};
		params["lineNbr"]=lineNbr;
		params["update"]=subLineNbr
		params["_pageRef"]=_pageRef;
		var url = jQuery.contextPath+"/path/templateExpression/loadExprList.action?";
		$.ajax({
			 url: url,
		     type:"post",
			 dataType:"json",
			 data: params,
			 success: function(param)
			 {
					$("#exprTemplGrid_"+_pageRef).jqGrid('deleteGridRow');
			 }
		 });  
	}
}



		