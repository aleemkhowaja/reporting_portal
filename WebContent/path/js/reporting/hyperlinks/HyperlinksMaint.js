function rep_hypMaint_ready()
{
	$("#hypLinkGrid_"+_pageRef).subscribe('retTrxNb', function(event,data) 
	{
		var url = jQuery.contextPath+ "/path/designer/hyperLinks_retTrxNb";
		rowid = (event["originalEvent"])["id"];
		var rowObject =  $("#hypLinkGrid_"+_pageRef).jqGrid("getRowData",rowid);
		var params={};
		params["repId"]=rowObject["hyperlinkVO.REPORT_ID"];
		params["feIndex"]=rowObject["hyperlinkVO.FIELD_INDEX"];
		params["_pageRef"]=_pageRef;
		$.post(url, params , function( param )
		{
			 //setTrxAudit
			 var auditTrxNbr_val=param["auditTrxNbr"];
 			 $("#hypForm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val(auditTrxNbr_val)
 		});
	
	});
	
	
	$("#hypLinkGrid_"+_pageRef).subscribe('emptyParamsMap', function(event,data) 
	{
		var url = jQuery.contextPath+ "/path/designer/hyperLinks_clearHypParamsMap.action";
		params = {};
		params["_pageRef"]=_pageRef;
		$.post(url, params , function( param )
		{
				//empty trx
				 $("#hypForm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("");
		}); 
	});
	
	$("#hypLinkGrid_"+_pageRef).subscribe('disableLiveSearch', function(event,data) 
	{
		rowId = (event["originalEvent"])["id"];
		myObject = $("#hypLinkGrid_"+_pageRef).jqGrid('getRowData',rowId);
		if(myObject["SAVE_FLAG"]=="U")
		{
			$("#hypLinkGrid_"+_pageRef).jqGrid('setCellReadOnly', rowId, 'REPORT_NAME','true',myObject["REPORT_NAME"]);
			$("#hypLinkGrid_"+_pageRef).jqGrid('setCellReadOnly', rowId, 'hyperlinkVO.COLUMN_NAME','true',myObject["hyperlinkVO.COLUMN_NAME"]);
			$("#hypLinkGrid_"+_pageRef).jqGrid('setCellReadOnly', rowId, 'LINKED_REP_NAME','true',myObject["LINKED_REP_NAME"]);
			}
	});
}

  	
  function deleteHyperlink(rowid)
  {
  	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
	    if(confirmcChoice)
	    {
	 	   confirmDeleteHyperlink(theArgs.rowid)
	    }
		}, {rowid : rowid},null,null,300,100);	
	
   }
  
  function confirmDeleteHyperlink(rowid)
  {
		var url = jQuery.contextPath+ "/path/designer/hyperLinks_deleteHyperlink.action";
		myObject = $("#hypLinkGrid_"+_pageRef).jqGrid('getRowData',rowid);
		params = {};
		
		params["feIndex"] =myObject["hyperlinkVO.FIELD_INDEX"] ;
		params["repId"] =myObject["hyperlinkVO.REPORT_ID"] ;
		params["_pageRef"]=_pageRef;
		$.post(url, params , function( param )
		{
	    	$("#hypLinkGrid_"+_pageRef).trigger("reloadGrid");
		});
  }
  
  function addNewHyperlink()
  {
  		$("#hypLinkGrid_"+_pageRef).jqGrid('addInlineRow',{});
  }
  
  function openHypLnkParams(cellvalue, options, rowObject)
	{
		 return '<a href="#" onclick="openHypLnkParamsList(\''+options.rowId+'\')">'+linkParamsTitle+'</a>';
	}
	
	function saveHypParams()
	{
		//check required fields
		var rows = $("#hypParamsGrid_"+_pageRef).jqGrid('getGridParam','records');	
		var argType;
		if(rows)
		{		
			for(var i=1;i<=rows;i++)
			{
				var data =$("#hypParamsGrid_"+_pageRef).jqGrid('getRowData',i);
				argType=data["hyperlinkVO.ARGUMENT_TYPE"];
				if(argType==0)
				{
					if(data["hyperlinkVO.VALUE_STATIC"]=="" || data["hyperlinkVO.VALUE_STATIC"]==null)
					{
						alert(missingFe);
						return;
					}
				}
				else if(argType==1)
				{
					if(data["hyperlinkVO.VALUE_COLUMN"]=="" || data["hyperlinkVO.VALUE_COLUMN"]==null)
					{
						alert(missingFe);
						return;
					}
				}
				else if(argType==2)
				{
					if(data["hyperlinkVO.VALUE_SESSION"]=="" || data["hyperlinkVO.VALUE_SESSION"]==null)
					{
						alert(missingFe);
						return;
					}
				}
			}
		}
		var jsonStringUpdates = $("#hypParamsGrid_"+_pageRef).jqGrid('getAllRows');
		 $("#updates_"+_pageRef).val(jsonStringUpdates); 
		 var url = $("#hypParamsForm_"+_pageRef).attr("action");
		 url +="?_pageRef="+_pageRef;
		 myObject =  $("#hypParamsForm_"+_pageRef).serialize();
		 $.post(url, myObject , function( param )
	 	 {
			 $('#hypParamsGridDialog_'+_pageRef).dialog('close');
			 //$('#hypParamsGridDialog_'+_pageRef).html(""); stoped to prevent open empty grid in link param
	 	 });
	}
	
	function openHypLnkParamsList(_rowid)
	{
	
	
		var rowObject =  $("#hypLinkGrid_"+_pageRef).jqGrid("getRowData",_rowid);
		var params={};
		params["repId"]=rowObject["hyperlinkVO.REPORT_ID"];
		params["linkRepId"]=rowObject["hyperlinkVO.LINKED_REPORT_ID"];
		params["feIndex"]=rowObject["hyperlinkVO.FIELD_INDEX"];
		params["colName"]=rowObject["hyperlinkVO.COLUMN_NAME"];
		params["updates"]=rowObject["SAVE_FLAG"];
		
		$("#hypLinkGrid_"+_pageRef).jqGrid("setSelection",_rowid,true);
		
		if(params["repId"]=="" || params["linkRepId"]=="" || params["colName"]=="")
		{
			_showErrorMsg(missingFe, error_msg_title, 300, 100);
		}
		else
			{
			dialogUrl= jQuery.contextPath+ "/path/designer/hyperLinks_openHyperlinkParamsDialog.action?_pageRef="+_pageRef ;
		dialogOptions={ autoOpen: false,
						height:300,
						title:paramsTitle ,
						width:800 ,
						modal: true,
						buttons: [{ text : paramsOk, click : saveHypParams}]
		   }
		$.post(dialogUrl, params , function( param )
	 	{
    	  $('#hypParamsGridDialog_'+_pageRef).html(param) ;
    	  $('#hypParamsGridDialog_'+_pageRef).dialog(dialogOptions)
		  $('#hypParamsGridDialog_'+_pageRef).dialog('open');
		},"html")
			}
		
		
	}
	
	function emptyRepDetails()
	{
			var rowid = $("#hypLinkGrid_"+_pageRef).jqGrid('getGridParam','selrow');
			document.getElementById(rowid+"_hyperlinkVO.FIELD_INDEX").value="";
			document.getElementById(rowid+"_hyperlinkVO.COLUMN_NAME_lookuptxt_hypLinkGrid_"+_pageRef).value="";
			//to empty parameters later
	}
  
  function saveHyperlinks()
  {
  		var jsonStringUpdates = $("#hypLinkGrid_"+_pageRef).jqGrid('getChangedRowData'); 
		$("#updateHyp_"+_pageRef).val(jsonStringUpdates); 
		 var url = $("#hypForm_"+_pageRef).attr("action");
		 myObject =  $("#hypForm_"+_pageRef).serialize();
		 
		 
	 	  $.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
				 if(typeof param["_error"] == "undefined" || param["_error"] == null)
			  	 {
					$("#hypLinkGrid_"+_pageRef).trigger("reloadGrid");
				}
				if(param["_error"].indexOf("DAOException")!=-1)
				{
				$("#hypLinkGrid_"+_pageRef).trigger("reloadGrid");
				}
			}
	 	 });
  }