var cancelQry="true";
var sqbQry="false";

function checkSelectedQryTab(selectedTabId, event)
{	
    $( "#tabs_"+_pageRef).tabs({
    		cache: true});	
			
			 	var url = $.data(event.originalEvent.ui.tab, 'load.tabs');
			 	var cached = $.data(event.originalEvent.ui.tab, 'cache.tabs');
			 	var frstLoad;
			 	if(typeof cached == "undefined" && typeof url != "undefined")
			 	{
			 		var zIndex;
			 		newUrl = jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?_pageRef="+_pageRef+"&tab=";
			 		if(openSqb!="true")
			 		{
			 			if(selectedTabId=="tab1")
	                    {
	                     	newUrl += "newquery";
							zIndex= 0;
	                    }
	                    else if(selectedTabId=="tab2")
	                    {
	                    	frstLoad="true";
	                     	newUrl += "expressions";
							zIndex= 1;
	                    }
	                    else if(selectedTabId=="tab3")
	                    {
	                        frstLoad="true";
	                        newUrl += "joins";
							zIndex= 2;
	                    }
	                    else if(selectedTabId=="tab4")
	                    {
	                        newUrl += "arguments";
							zIndex= 3;
	                    }
						else if(selectedTabId=="tab5")
	                    {
	                        newUrl += "aggregate";
							zIndex= 4;
	                    }
	                    else if(selectedTabId=="tab6")
	                    {
	                    	frstLoad="true";
	                        newUrl += "order";
							zIndex= 5;
	                    }
	                    else if(selectedTabId=="tab7")
	                    {
	                    	frstLoad="true";
	                        newUrl += "conditions";
							zIndex= 6;
	                    }
	                    else if(selectedTabId=="tab8")
	                    {
	                    	frstLoad="true";
	                        newUrl += "having";
							zIndex= 7;
	                    }
	                    else if(selectedTabId=="tab9")
	                    {
	                    	frstLoad="true";
	                        newUrl += "syntax";
							zIndex= 8;
	                    }
			 		}
			 		else
			 		{
			 			if(selectedTabId=="tab4")
	                    {
			 			   newUrl += "arguments";
						   zIndex=0;
	                    }
	                    else if(selectedTabId=="tab9")
	                    {
	                    	 newUrl += "syntax";
							 zIndex=1;
	                    }
			 		}
			 		
       				$("#tabs_"+_pageRef).tabs("url",zIndex,newUrl);
			 	}	
			 	if(selectedTabId!="tab1" && openSqb!="true")
			 	{
			 		//check if the queryName has been filled
					var qryName=document.getElementById("qryName_"+_pageRef).value;
					if(qryName=="")
					{
						_showErrorMsg(qryNameAlert);
						document.getElementById("qryName_"+_pageRef).focus();						
						event.originalEvent.event.preventDefault();
					}
					else
					{
						//save the qryName
						var url = jQuery.contextPath+ "/path/designer/queryDesign_saveQryName";
						var params={};
					    params['updates']=qryName;
					    params["_pageRef"]=_pageRef;
						$.post(url, params , function( param )
					 	{
						 	
					 	});	
						if(selectedTabId=="tab6" && frstLoad!="true")
					 	{
				    		//$("#Order").load(jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?tab=order");
							 $("#orderGrid_"+_pageRef).trigger("reloadGrid");
					 	}
						if(selectedTabId=="tab3" && frstLoad!="true")
						{
							//$("#Join").load(jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?tab=joins&_pageRef="+_pageRef);
							$("#tabs_"+_pageRef).tabs("url", 2, jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?tab=joins&_pageRef="+_pageRef);
						}
						if(selectedTabId=="tab7" && frstLoad!="true")
						{
								$("#tabs_"+_pageRef).tabs("url", 6, jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?tab=conditions&_pageRef="+_pageRef); 
						}
						if(selectedTabId=="tab8" && frstLoad!="true")
						{
							$("#tabs_"+_pageRef).tabs("url", 7, jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?tab=having&_pageRef="+_pageRef);
						}
						if(selectedTabId=="tab9" && frstLoad!="true")
						{
							$("#tabs_"+_pageRef).tabs("url", 8, jQuery.contextPath+ "/path/designer/queryDesign_loadTabContent.action?tab=syntax&_pageRef="+_pageRef);
						}
						if(selectedTabId=="tab2" && frstLoad!="true")
					 	{
						 	 $("#selEntExprGrid_"+_pageRef).trigger("reloadGrid");
							 $("#selFieldsExprGrid_"+_pageRef).jqGrid('setGridParam',
			   			  	 {    
			   					url : jQuery.contextPath+"/path/designer/queryDesign_loadSelFieldsGrid.action?locIndex=-1&_pageRef="+_pageRef,
			   					page : 1
			   				 }).trigger("reloadGrid");
						 	 
						 	
					 	}
					}
			 	}
			 	else if(selectedTabId=="tab9" && openSqb=="true" && frstLoad!="true")
			 	{
			 		 $("#syntaxArgumentsGrid_"+_pageRef).trigger("reloadGrid");
			 	}
			 
	}
	

function okQry()
{
		if(_pageRef=="RD00R")
		{
         reportHasChanged(true);
         }
    	if(openSqb==null || openSqb=="null")
    	{
			//check queryName
			var qryName=document.getElementById("qryName_"+_pageRef).value;
			if(qryName=="")
			{
				_showErrorMsg(qryNameAlert);
				document.getElementById("qryName_"+_pageRef).focus();
				return false;
			}
			else
			{
				//save the qryName
				var url = jQuery.contextPath+ "/path/designer/queryDesign_saveQryName";
				var params={};
				params['updates']=qryName;
				params["_pageRef"]=_pageRef;
				 $.post(url, params , function( param )
			 	{
			    	//check if we selected a fields 
				 	var isFieldSel=param['updates'];
				 	if(isFieldSel=="false")
				 	{
				 		_showErrorMsg(selectFieldAlert);
						return;
				 	}
				 	else
				 	{
					 	cancelQry="false";	
					 	//close the dialog
					 	if(fromQry=="true")
					 	{
					 		$("#queriesDialg").dialog('close');
					 		closeQryDlgFunc();
					 	}
					 	else
					 	{
					 		$('#constraintsMainDialog_'+_pageRef).dialog('destroy').remove();
					 		$("#qryDialg").dialog('close');
					 	} 			 	
				 	}
			 	});	
			}
			return false;
    	}
    	else
    	{
			if(document.getElementById("sqbSyntax_"+_pageRef)==null) //the sqbSyntax is not loaded ==> sql is not updated
			{
				cancelQry="false";
				if(fromQry=="true")
			 	{
			 		$("#queriesDialg").dialog('close');
			 		closeQryDlgFunc();
			 	}
			 	else
			 	{
			 		$("#qryDialg").dialog('close');
			 	} 		
			}
			else
			{
				var sqbName=document.getElementById("sqbQryName_"+_pageRef).value;
				if(sqbName=="" && document.getElementById("qryNameTr_"+_pageRef).style.display=="inline")
				{
					_showErrorMsg(qryNameAlert)
					return;
				}
				else
				{
					_showConfirmMsg(queryValConfrm, info_msg_title,"confirmOkQuery", {}, yes_confirm, no_confirm, 300, 150);
				}	    
			}
    	}
}

function confirmOkQuery(confirmcChoice, theArgs)
{
      if (confirmcChoice) 
      {
            var sqbName=document.getElementById("sqbQryName_"+_pageRef).value;
            return proceedWithQueryVal(sqbName);      
      }
}


function proceedWithQueryVal(sqbName)
{			
	_showProgressBar(true);
	var url = jQuery.contextPath+ "/path/designer/queryDesign_validateSqbQuery.action";
	var params={};
	if(sqbName!=null)
	{
		params["updates"]=sqbName
	}
	params["sqbSyntax"]=document.getElementById("sqbSyntax_"+_pageRef).value;
	params["_pageRef"]=_pageRef;
	params["update1"] = $("#connectionSyntax_"+_pageRef).val();
	document.getElementById("queryBeingVal_"+_pageRef).innerHTML="<font color='red'>"+procTakeTime+"</font>";
    $.post(url, params, function( data2, status, xhr )
 	{
   		if(data2['updates']=="")
       	{
       		$("#statuss_"+_pageRef).html("");
       		sqbQry="true";
       		if(document.getElementById("syntax_"+_pageRef)!=null) //called from repots section
       		{
        		//refresh chooseFields grids
        		$("#allFieldsGrid").trigger("reloadGrid");
				$("#displFieldsGrid").trigger("reloadGrid"); 
				document.getElementById("syntax_"+_pageRef).value=document.getElementById("sqbSyntax_"+_pageRef).value;
       		}
       		else
       		{
       			cancelQry="false";	
       		}
       		if(fromQry=="true")
		 	{
		 		$("#queriesDialg").dialog('close');
		 	}
		 	else
		 	{
		 		$("#qryDialg").dialog('close');
		 	} 		
		 	
		 	 _showProgressBar(false);
		 	 document.getElementById("queryBeingVal_"+_pageRef).innerHTML="";
		 	 closeQryDlgFunc();
		 	 return true;
       	}
       	else
        {
       		$("#statuss_"+_pageRef).html(data2['updates']);
       		_showProgressBar(false);
       		document.getElementById("queryBeingVal_"+_pageRef).innerHTML="";
	    	return false;
       	}
 	});		
}
function closeQryDlgFunc()
{
	var sqbName=document.getElementById("sqbQryName_"+_pageRef);
	if(typeof templateQryId == "undefined")
	{
		templateQryId="";
	}
	if(cancelQry=="true" || (cancelQry =="false"&& sqbName == null))
	{
		$.ajax({
			url: jQuery.contextPath+"/path/designer/queriesList_clearSession.action"
		});
	}
	else 
	{
		cancelQry="true";				
		$.ajax({
			url: jQuery.contextPath+"/path/designer/queriesList_saveTemplateQuery.action?queryId="+templateQryId,
			type: "POST",	
			dataType:"json",
			data: ({_pageRef : _pageRef}),											
		    success: function(xml){
		     	$("#qryListFrm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("")
				$("#queriesTempGrid").trigger("reloadGrid");
			}
		});
	}	
	$("#qryTypesDialg").dialog('close'); 
	//called from iFrame external opt Not from reporting module
	if(window.name && window.name.indexOf("extScreenFrame") == 0)
	{   
		$.postMessage({ confirmCallBack: 'closeRepQueryIframe' ,confirmValue:"true",confirmArgs:'null'}, 
				window.originalUrl ,window.top);
		return;
	}
}

function cancelTheQry()
{
	cancelQry="true";
	$.ajax({
		url:jQuery.contextPath+"/path/designer/queryDesign_cancelQry"
	});	
	if(fromQry=="true")
 	{
 		$("#queriesDialg").dialog('close');
 		closeQryDlgFunc();
 	}
 	else
 	{
 		$("#qryDialg").dialog('close');
 	} 		
	//called from iFrame external opt Not from reporting module
	if(window.name && window.name.indexOf("extScreenFrame") == 0)
	{   
		$.postMessage({ confirmCallBack: 'closeRepQueryIframe' ,confirmValue:"true",confirmArgs:'null'}, 
				window.originalUrl ,window.top);
		return;
	}
	return false; 
}

function rep_designer_hideSyntGrid()
{
	document.getElementById("syntaxArgumentsGrid_"+_pageRef+"_pager").style.display="none";
}