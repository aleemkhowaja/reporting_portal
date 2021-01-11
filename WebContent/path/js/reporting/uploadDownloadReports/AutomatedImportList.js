function rep_autoImp_findRepFiles()
{
	var params={};
	params["_pageRef"] =_pageRef;
	params["updates"] = 1;//flag to open empty grid on load or load it with data when clicking on view
	params["update"]=$("#autoImpId_"+_pageRef).val();//path
	params["password"] = $("#passwordAutoImpRep_"+_pageRef).val();	
	$("#autoImportRepGrid_"+_pageRef).jqGrid('setGridParam',
		{
			url :jQuery.contextPath+"/path/automatedImport/AutomatedImportReportAction_loadAutomatedImportGrid",
			datatype : 'json',
			postData : params
		}).trigger("reloadGrid");
}

function rep_autoImp_startImportProcess()
{
	var rows = $("#autoImportRepGrid_"+_pageRef).jqGrid('getGridParam', 'selarrrow');
	var selectedRepsList =  new Object();
    var selectedReps = null;
    var replaceIfExist = false;
    $.each(	$("#autoImportRepGrid_"+_pageRef).jqGrid('getGridParam','selarrrow'), function(index, rowId) {		    	
			selectedReps = $("#autoImportRepGrid_"+_pageRef).jqGrid('getRowData',rowId)		 
			selectedRepsList["autoImportCOList["+index+"].ZIP_FILE_NAME"] 	= 	selectedReps["ZIP_FILE_NAME"];
			selectedRepsList["autoImportCOList["+index+"].ACTION"] 			= 	selectedReps["ACTION"];
			selectedRepsList["autoImportCOList["+index+"].USE_EXISTING_OPT"]= 	selectedReps["USE_EXISTING_OPT"];
			selectedRepsList["autoImportCOList["+index+"].SKIP_TRANSLATION"]= 	selectedReps["SKIP_TRANSLATION"];
			selectedRepsList["autoImportCOList["+index+"].UPDATE_VERSION_IF_EQUAL"]= 	selectedReps["UPDATE_VERSION_IF_EQUAL"];
			if(selectedReps["ACTION"]=='REPLACE')
			{
				replaceIfExist=true;
			}
	}); 
	
    if(rows.length>0)
	{
			 	var detailsParmeters;
			 	if(replaceIfExist)
			 	{
				   _showConfirmMsg(replaceConfirmAuto,info_msg_title, function(
								confirmcChoice, theArgs) {
					   		_showProgressBar(true);
							if (confirmcChoice) 
							{
								rep_autoImp_continueImportProcess(selectedRepsList,detailsParmeters,false)
							}
							else
							{
								rep_autoImp_continueImportProcess(selectedRepsList,detailsParmeters,true)
							}
						}, {}, yes_confirm, no_confirm, 300, 100);							 	
				 	
			 	}
			 	else
			 	{
			 		_showProgressBar(true);
			 		rep_autoImp_continueImportProcess(selectedRepsList,detailsParmeters,false)
			 	}
	}
     else
    {
    	_showProgressBar(false);
    }
}
    
function rep_autoImp_continueImportProcess(selectedRepsList,detailsParmeters,keepSchedsHyperlinks)
{
	var zipPassword = $("#passwordAutoImpRep_"+_pageRef).val();
	var update = $("#autoImpId_"+_pageRef).val();
	var zurl=jQuery.contextPath+"/path/designer/upDownReport_startAutomatedImportProcess.action"
	document.getElementById("automatedImportFormId_"+_pageRef).setAttribute("action",zurl);	
	detailsParmeters= {'zipPassword':zipPassword,'update':update,'keepSchedsHyperlinks':keepSchedsHyperlinks,'_pageRef':_pageRef};
	$.extend(selectedRepsList, detailsParmeters);
	$.post(zurl, selectedRepsList , function(param)
	 	{		
	 		_showProgressBar(false);	
	 		if(param["update"]==1)
	 		{
	 			var msg;
	 			var widthMsg;
	 			if(param["caption"]=="false")
	 			{
	 				msg=autoImpUnsuccess;
	 				widthMsg=250;
	 			}
	 			else
	 			{
	 				msg=autoImpWarnings;
	 				widthMsg=350;
	 			}
				_showConfirmMsg(msg, info_msg_title,function(confirmcChoice, theArgs) {
						if (confirmcChoice) 
						{ 
							  _showProgressBar(true);
							  var params={};
							  params["_pageRef"] = _pageRef;
							  $.fileDownload(jQuery.contextPath+"/path/designer/upDownReport_showWarningsAutomatedImport.action?caption="+param["caption"],
								{
								    successCallback: function (url) 
								    {
								    	_showProgressBar(false);
								    },
								    failCallback: function (html, url) 
								    {
								    	_showProgressBar(false);	 
								    },
								    data:params,
								});			  
						}
						//clear session
						else
						{
							var zurl=jQuery.contextPath+"/path/designer/upDownReport_removeWarningsFromSession.action";
							var params={};
							params["_pageRef"] = _pageRef;
							$.post(zurl, params , function(param)
			 				{
			 					
			 				});
						}
				}, {}, yes_confirm, no_confirm, widthMsg, 100);
	 		}
	 		else
	 		{
	 			_showErrorMsg(autoImpSuccess,info_msg_title,200,100);
	 		}
	  rep_autoImp_reloadMenu();	 	
	 });
}

function rep_autoImp_reloadMenu()
{
	//reload menu
	ddaccordion
	.initRoot(
			"appMenu",
			"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
			false);
} 

function automatedImportReadyFunc()
{
		$("#autoImportRepGrid_"+_pageRef).subscribe('checkAllUseOptAxs',
				function(event, data) 
				{
					var pagerId = "autoImportRepGrid_"+_pageRef+"_pager_left";
					var myGrid = $("#autoImportRepGrid_"+_pageRef);									
					myGrid.jqGrid('navButtonAdd',pagerId,
										{
											caption : "",
											title : checkUncheckUseExtOpt,
											id : "checkAllUseOptAxs_"+_pageRef,
											buttonicon : 'ui-icon-check',
											onClickButton : function() {checkAllUseOptAccess();}
										}
								  );										
				 }
			);
}

function checkAllUseOptAccess()
{
	var allRowIds =  $("#autoImportRepGrid_"+_pageRef).jqGrid('getDataIDs');
	var status = $("#statusCheckAllOpt_"+_pageRef).val()=="true"?false:true;
	if(allRowIds)
	{
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	myObject =$("#autoImportRepGrid_"+_pageRef).jqGrid('getRowData',llRowId);
	    	$("#autoImportRepGrid_"+_pageRef).jqGrid('setCellValue',llRowId,'USE_EXISTING_OPT',status);	    	
	    }
	  	$("#statusCheckAllOpt_"+_pageRef).val(status);
	}
}