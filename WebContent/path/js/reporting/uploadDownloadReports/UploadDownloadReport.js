function continueImportProcess(zipPassword,importOption,keepSchedsHyperlinks)
{
	_showProgressBar(true);
		var options = {
			url : jQuery.contextPath
					+ "/path/designer/upDownReport_startImportProcess.action?_pageRef="
					+ _pageRef + "&zipPassword=" + zipPassword+"&importOption="+importOption+"&keepSchedsHyperlinks="+keepSchedsHyperlinks,
			type : 'post',
			success : function(param, status, xhr) {
				if (xhr.responseText.indexOf("errorMessage") != -1) 
				{			
					_showProgressBar(false);
					$("#passErrorDiv").html(param)
				}
				else 
				{
						var jsonObj = $.parseJSON($(param).html());
						if (jsonObj !=null && jsonObj["updates"]!=null)
						{
							_showErrorMsg(uploadSuccess+"\n\n"+jsonObj["updates"],warning_msg_title,400,170);
						}
						$("#passErrorDiv").html("");
						$('#importDialog_'+_pageRef).html("");
						$('#importDialog_'+_pageRef).dialog('close');
						$("#upDownGrid_"+_pageRef).trigger("reloadGrid");
						//reload menu
						ddaccordion
							.initRoot(
									"appMenu",
									"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
									false);
						_showProgressBar(false);			
				}
			}
		}
		$("#importForm_"+_pageRef).ajaxSubmit(options)		
}

function newUpload() {
	//clear session  
	var url = jQuery.contextPath+"/path/designer/upDownReport_clearUpDownSession.action?_pageRef="
			+ _pageRef;
	var myObject = {};
	$.post(url, myObject, function(param) {
		//empty inputs
			$("#repName_" + _pageRef).attr('readonly', false);
			$("#repName_" + _pageRef).val("");
		
			$("#refId_" + _pageRef).val("");
			$("#pRefStr_" + _pageRef).val("");
			$("#lookuptxt_fcrPRef_" + _pageRef).val("");
			$("#lookuptxt_appName_" + _pageRef).val("");
			$("#upload_" + _pageRef).val(""); //empty the ps file on Firefox
			$("#upload_" + _pageRef).replaceWith(
					$("#upload_" + _pageRef).clone());//empty the ps file on IE
			$("#format_" + _pageRef).val("");
			$("#connection_" + _pageRef).val("");
			$("#dateUpdated_" + _pageRef).val("");
			$("#xlsName_"+_pageRef).val("");
		    $("#lookuptxt_msCode_"+_pageRef).val("");
		    $("#msHost_"+_pageRef).val("");
		    $("#menuTitleEng_"+_pageRef).val("");
   			$("#argPerRow_"+_pageRef).val("");
			$("#reportVersion_" + _pageRef).val("");
		    $("#reportModifiedYN_" + _pageRef).val("");
			$("#generatedFileName_"+_pageRef).val("");
		      //empty checkbox
			$("#noHeadAndFoot_"+_pageRef).attr('checked', false);
			$("#skipQryValidation_"+_pageRef).attr('checked', false);
			$("#skipOptAxs_"+_pageRef).attr('checked', false);
			$("#reportType_"+_pageRef).attr('checked', false);
			$("#langIndep_"+_pageRef).attr('checked', false);	
			
			
			callDependency("whenNoData_"+_pageRef+":whenNoDataList",
			jQuery.contextPath + "/path/designer/upDownReport_reloadWhenNoDataList",
			"reportId:-1" , "whenNoData_"+_pageRef, "");
			
			callDependency("CATEGORY_DESC_"+_pageRef+":update",
					jQuery.contextPath + "/path/designer/upDownReport_applyCategoryScreenDisplay",
					"" , "lookuptxt_CATEGORY_ID_"+_pageRef, "");
			
		
		
			//disable inputs
			disableEnableInputs(0);

			//reload grids
			showHideReportDetails(0);

			fillDefaultValues();
			
			//hide csvSeparator
			checkUpdDownIfCSV("HTML");
          
		});
}

function checkProgRefs(calledFrom)
{
	if(calledFrom==undefined)
	{
		calledFrom="";
	}
	var isChecked = document.getElementById("skipOptAxs_"+_pageRef+calledFrom).checked;
	if(isChecked)
	{
		var url = jQuery.contextPath+ "/path/designer/reportDesign_depRepNameFromOPT";
		params ={};
		params["repCO.SKIP_OPT_AXS"]=$("#skipOptAxs_"+_pageRef+calledFrom).val();
		params["repCO.PROG_REF"]=$("#refId_"+_pageRef+calledFrom).val();
		params["repCO.APP_NAME"]=$("#lookuptxt_appName_"+_pageRef+calledFrom).val();
		params["repCO.PARENT_REF"]=$("#lookuptxt_fcrPRef_"+_pageRef+calledFrom).val();
	
		$.getJSON(url, params, function( data2, status, xhr ) 
		{
		   var resultVal=data2["updates"];
		   if(resultVal != "" && resultVal!=undefined)
			 { 
			 	$("#skipOptAxs_"+_pageRef+calledFrom).attr("checked",false)
				$("#refId_"+_pageRef+calledFrom).val("")
				$("#lookuptxt_appName_"+_pageRef+calledFrom)
				$("#repName_"+_pageRef+calledFrom).val("")
			   _showErrorMsg(resultVal,error_msg_title,350,120);
			 }
		   	else
			{
			 	$("#repName_"+_pageRef+calledFrom).val(data2["repCO"]["REPORT_NAME"]);
				$("#lookuptxt_fcrPRef_"+_pageRef+calledFrom).val(data2["repCO"]["PARENT_REF"]);
				$("#pRefStr_"+_pageRef+calledFrom).val(data2["repCO"]["PARENT_REF_STR"]);  
				$("#refId_"+_pageRef+calledFrom).attr("readonly",true);
				$("#repName_"+_pageRef+calledFrom).attr("readonly",true);
				$("#pRefStr_"+_pageRef+calledFrom).attr("readonly",true);
			}
		 });
	 }
	else
	{
			$("#repName_"+_pageRef+calledFrom).val("")
			$("#lookuptxt_fcrPRef_"+_pageRef+calledFrom).val("")
			$("#pRefStr_"+_pageRef+calledFrom).val("")
			$("#refId_"+_pageRef+calledFrom).attr("readonly",false);
			$("#repName_"+_pageRef+calledFrom).attr("readonly",false);
			$("#pRefStr_"+_pageRef+calledFrom).attr("readonly",false);

	}
}

function fillDefaultValues(obj) {
	var zSrc = jQuery.contextPath+"/path/fcrRep/fcr_dependencyByPRefId.action?updates=R00";//later we should send the app_name 
	//as param in order to get the real name of the R00 when the appName in not REP ,but now the other appl. does not have R00
	params = {};
	$.getJSON(zSrc, params, function(data2, status, xhr) {
		var retVal = data2['commonDetVO']['BRIEF_DESC_ENG'];
		if (retVal == "-1") {
			$("#lookuptxt_fcrPRef_" + _pageRef).val("");
			$("#pRefStr_" + _pageRef).val("");
		} else {
			$("#pRefStr_" + _pageRef).val(retVal);
			$("#lookuptxt_fcrPRef_" + _pageRef).val("R00");
			if (obj == null) {
				$("#lookuptxt_appName_" + _pageRef).val(
						data2['commonDetVO']['APPL_NAME']);
			}
		}
	});
	$("#metadataRepYn_"+_pageRef).attr('checked', false);
	$("#cifAuditYn_"+_pageRef).attr('checked', false);
	callDependency("metadataRepYn_"+_pageRef+":0",
			jQuery.contextPath + "/path/designer/upDownReport_reportMetadataVisiblity.action",
			"repCO.METADATA_REPORT_YN:'0'" , "", "");	
}

function importReport()
{
				dialogUrl= jQuery.contextPath+ "/path/designer/upDownReport_openImportDialog.action?_pageRef="+_pageRef;
					dialogOptions={ autoOpen: false,
									height:235,
									title:importTitle,
									width:450 ,
									modal: true,
									buttons: [{ text : paramsOk, click : startImportProcess},
									          { text : paramsCancel, click :function(){$(this).html("");$(this).dialog('close');}}
							          ]
					   }
					$.post(dialogUrl ,params , function( param )
				 	{
			    	  $('#importDialog_'+_pageRef).html(param) ;
			    	  $('#importDialog_'+_pageRef).dialog(dialogOptions)
					  $('#importDialog_'+_pageRef).dialog('open');
					},"html");
					
}

function fullDownloadReport()
{
	var repIdsArray = retCheckedReportIds();
	var repRefArray = retCheckedReportRef();
	var repNameArray = retCheckedReportName();
	var downloadFlagArray = retCheckedReportDownloadbleFlag();
		
	if (repIdsArray[0] == null || repIdsArray[0]=="") {

		_showErrorMsg(selectRowAlert, warning_msg_title);
		return;
	}
	
		$.ajax( {
			url : jQuery.contextPath+ '/path/designer/upDownReport_checkIfDownloadable.action',
			type : "POST",
			dataType:"json",
			data : ( {
				repIdsLst : repIdsArray,
				repRefLst : repRefArray,
				repNameLst : repNameArray,
				downladFlagLst : downloadFlagArray,
				_pageRef : _pageRef
			}),
			success : function(param, status, xhr) {
			
			//alert("param _error2=="+param["_error"])
			if (xhr.responseText.indexOf("errorMessage") == -1) {
			
					dialogUrl= jQuery.contextPath+ "/path/designer/upDownReport_openExportDialog.action?_pageRef="+_pageRef;
					dialogOptions={ autoOpen: false,
									height:180,
									title:downloadTitle,
									width:340 ,
									modal: true,
									buttons: [{ text : paramsOk, click : startExportProcess},
									          { text : paramsCancel, click :function(){$(this).html("");$(this).dialog('close');}}
							          ]
					   }
					$.post(dialogUrl ,params , function( param )
				 	{
			    	  $('#exportDialog_'+_pageRef).html(param) ;
			    	  $('#exportDialog_'+_pageRef).dialog(dialogOptions)
					  $('#exportDialog_'+_pageRef).dialog('open');
					},"html");
					
				}
		}
		});
}



function retCheckedReportIds()
{
	var arrayLength = $("#upDownGrid_"+_pageRef).jqGrid('getCol','REPORT_ID').length;
	var repIdsArray = new Array();
	var repIdsStr="";

	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#upDownGrid_"+_pageRef).jqGrid("getRowData",i);
		var repId = rowObject["REPORT_ID"];
		
		var checked = rowObject["repIdCheckBox"];
		if(checked=="Yes")
		{
			repIdsStr = repIdsStr+","+repId;
		}

	}	
	repIdsStr = repIdsStr.substring(1) 
	repIdsArray =repIdsStr.split(",") 
	return repIdsArray;
}

function retCheckedReportRef()
{
	var arrayLength = $("#upDownGrid_"+_pageRef).jqGrid('getCol','REPORT_ID').length;
	var repRefArray = new Array();
	var repRefStr="";

	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#upDownGrid_"+_pageRef).jqGrid("getRowData",i);
		var repRef = rowObject["PROG_REF"];
		
		var checked = rowObject["repIdCheckBox"];
		if(checked=="Yes")
		{
			repRefStr = repRefStr+","+repRef;
		}

	}	
	repRefStr = repRefStr.substring(1) 
	repRefArray =repRefStr.split(",") 
	return repRefArray;
}

function retCheckedReportName()
{
	var arrayLength = $("#upDownGrid_"+_pageRef).jqGrid('getCol','REPORT_ID').length;
	var repNameArray = new Array();
	var repNameStr="";

	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#upDownGrid_"+_pageRef).jqGrid("getRowData",i);
		var repName = rowObject["REPORT_NAME"];
		
		var checked = rowObject["repIdCheckBox"];
		if(checked=="Yes")
		{
			repNameStr = repNameStr+","+repName;
		}

	}	
	repNameStr = repNameStr.substring(1) 
	repNameArray =repNameStr.split(",") 
	return repNameArray;
}

function retCheckedReportDownloadbleFlag()
{
	var arrayLength = $("#upDownGrid_"+_pageRef).jqGrid('getCol','REPORT_ID').length;
	var downloadFlagArray = new Array();
	var downloadFlagStr="";

	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#upDownGrid_"+_pageRef).jqGrid("getRowData",i);
		var downloadFlag = rowObject["DOWNLOADABLE_FLAG"];
		
		var checked = rowObject["repIdCheckBox"];
		if(checked=="Yes")
		{
			downloadFlagStr = downloadFlagStr+","+downloadFlag;
		}

	}	
	downloadFlagStr = downloadFlagStr.substring(1) 
	downloadFlagArray =downloadFlagStr.split(",") 
	return downloadFlagArray;
}

function retCheckedReportAppName()
{
	var arrayLength = $("#upDownGrid_"+_pageRef).jqGrid('getCol','REPORT_ID').length;
	var repAppNameArray = new Array();
	var repAppNameStr="";
	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#upDownGrid_"+_pageRef).jqGrid("getRowData",i);
		var repAppName = rowObject["APP_NAME"];
		var checked = rowObject["repIdCheckBox"];
		if(checked=="Yes")
		{
			repAppNameStr = repAppNameStr+","+repAppName;
		}

	}
	repAppNameStr = repAppNameStr.substring(1) 
	repAppNameArray =repAppNameStr.split(",")
	return repAppNameArray;
}

function visibilitySkip(hideDocReady)
{
	if($('input:radio[name=basicFullExport]:checked').val()==2)
	{
		document.getElementById("lblSkip_"+_pageRef).style.display = "inline";
		document.getElementById("skipTrans_"+_pageRef).style.display = "inline";
	}
	else if (hideDocReady || $('input:radio[name=basicFullExport]:checked').val()==1)
	{
		$("#skipTrans_"+_pageRef).attr('checked', false);
		document.getElementById("lblSkip_"+_pageRef).style.display = "none";
		document.getElementById("skipTrans_"+_pageRef).style.display = "none";
	}
}

function startExportProcess()
{
	var fullBasicExp = $('input:radio[name=basicFullExport]:checked').val();//basic 1,full 2
	var skipTrans = $("#skipTrans_"+_pageRef).is(':checked');
	var repIdsArray = retCheckedReportIds();
	var repRefArray = retCheckedReportRef();
	var repNameArray = retCheckedReportName();
	var downloadFlagArray = retCheckedReportDownloadbleFlag();
	var repAppNameArray = retCheckedReportAppName();
	var zipPassword = $("#zipPassword_" +_pageRef).val();
	
	var objData = {};
		
	$('#exportDialog_'+_pageRef).html("");
	$('#exportDialog_'+_pageRef).dialog('close');
	_showProgressBar(true)
     $.fileDownload(jQuery.contextPath+ "/path/designer/upDownReport_startExportProcess.action?_pageRef="+ _pageRef +"&skipTrans="+skipTrans+"&repRefLst="+repRefArray+ "&repNameLst="+encodeURIComponent(repNameArray)+ "&repIdsStrLst="+repIdsArray+ "&repAppNameLst="+repAppNameArray+ "&fullBasicExp="+fullBasicExp+ "&zipPassword="+zipPassword,
     {
	   successCallback: function (url) {
       _showProgressBar(false)
   },
   failCallback: function (html, url) {
        _showProgressBar(false)
       	_showErrorMsg(html);
   },
   data:objData
     });
	//reinitialize the url
	document.getElementById("fullDownloadFormIds_"+ _pageRef).action=jQuery.contextPath+'/path/designer/upDownReport_startExportProcess.action'
}



function downloadReport() {
	var selRowId = $("#upDownGrid_" + _pageRef)
			.jqGrid('getGridParam', 'selrow');
	if (selRowId == null) {

		_showErrorMsg(selectRowAlert, warning_msg_title);
		return;
	}
	var reportId = $("#upDownGrid_" + _pageRef).jqGrid('getCell', selRowId,
			'REPORT_ID');
	var reportName = $("#upDownGrid_" + _pageRef).jqGrid('getCell', selRowId,
			'REPORT_NAME');
	var url = jQuery.contextPath+"/path/designer/upDownReport_retReportFlags.action?_pageRef="
			+ _pageRef + "&reportId=" + reportId;
	var myObject = {};
	$
			.post(
					url,
					myObject,
					function(param) {
						var isDownld = param['repCO']['DOWNLOADABLE_FLAG'];
						if (isDownld == 1) {
							_showErrorMsg(nonDownloadableReport, info_msg_title);
							return;
						} else {

							var brs = navigator.userAgent.toLowerCase();
							//WIN XP
							if (brs.search(/nt\s5\.1/) != -1) {
								document.getElementById("downloadFormIds_"
										+ _pageRef).target = "_top";
							} else {
								document.getElementById("downloadFormIds_"
										+ _pageRef).target = "hiddenFrm";
							}

							var urlDownload = document
									.getElementById("downloadFormIds_"
											+ _pageRef).action;
							if (urlDownload.indexOf("?reportId=") == -1) {
								document.getElementById("downloadFormIds_"
										+ _pageRef).action += "?reportId="
										+ reportId + "&updates="
										+ encodeURIComponent(reportName);
							} else {
								urlDownload = urlDownload.substring(0,
										urlDownload.indexOf("?reportId="));
								document.getElementById("downloadFormIds_"
										+ _pageRef).action = urlDownload
										+ "?reportId=" + reportId + "&updates="
										+ encodeURIComponent(reportName);
							}
//							document.getElementById(
//									"downloadFormIds_" + _pageRef).submit();
							submitEncryptedData("downloadFormIds_" + _pageRef);
						}
					});

}


function disableEnableInputs(flag) {
	if (flag == "1") {
		$("#refId_" + _pageRef).attr('readonly', true);
		liveSearch_makeReadOnly(true, "appName_" + _pageRef);
		$("#lookuptxt_appName_" + _pageRef).attr('readonly', true);
	} else if (flag == "0") {
		$("#refId_" + _pageRef).attr('readonly', false);
		liveSearch_makeReadOnly(false, "appName_" + _pageRef);
		$("#lookuptxt_appName_" + _pageRef).attr('readonly', true);
	}

}


function deleteUpDownReport(rowid) 
{
	var url = jQuery.contextPath+"/path/designer/upDownReport_retReportFlags.action?_pageRef="
			+ _pageRef;
	var myObject = {};
	myObject["reportId"] = $("#upDownGrid_" + _pageRef).jqGrid('getCell',
			rowid, 'REPORT_ID');
	$.post(url, myObject, function(param) {
		var isEditable = param['repCO']['EDITABLE_FLAG'];
		if (isEditable == 1) {
			_showErrorMsg(nonDeletedReport, info_msg_title);
			return;
		} else if (param["isSubRep"] != 0) {
			_showErrorMsg(nonDelSubRep, info_msg_title, 350, 120);
			return;
		} else {
			_showConfirmMsg(deleteConfirm, deleteTitle, function(
					confirmcChoice, theArgs) {
				if (confirmcChoice) {
					delUpDownReport(theArgs.rowid)
				}
			}, {
				rowid : rowid
			}, yes_confirm, no_confirm, 300, 100);
		}
	});
}



function delUpDownReport(rowid) {
	//check if editable or not 

	var reportId = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'REPORT_ID');
	var progRef = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'PROG_REF');
	var oldRepId = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'OLD_REPORT_ID');
	var appName = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'APP_NAME');

	var arrId = new Array(1);
	var arrOldId = new Array(1);
	var arrProgRef = new Array(1);
	var arrAppName = new Array(1);

	arrId[0] = reportId;
	arrOldId[0] = oldRepId;
	arrProgRef[0] = progRef + "," + progRef + "D," + progRef + "M," + progRef
			+ "SV," + progRef + "SA," + progRef + "SM," + progRef + "SC,"
			+ progRef + "PR";
	arrAppName[0] = appName;
	var url = jQuery.contextPath+"/path/designer/reportsList_checkIfDeleteReport.action"
	var params = {};
	params["reportsId"] = arrId;
	params["appsName"] = arrAppName;
	params["progRefs"] = arrProgRef;
	$
			.post(
					url,
					params,
					function(param) {
						var isUsed = param["updates"];
						var isSched = param["updates2"];
						var isAccess = param["accessStr"];
						var metadataRep = param["update"];
						if (metadataRep != "")
						{
							_showErrorMsg(repBeingMetadata + "  "
									+ metadataRep, info_msg_title, 350, 120);
						}
						else if (isAccess != "") {
							_showErrorMsg(delHypRepAccessAlert + "  "
									+ isAccess, info_msg_title, 350, 120);
						} else {
							if (isUsed == "" && isSched == "") {
								$
										.ajax( {
											url : jQuery.contextPath+'/path/designer/reportsList_delete.action',
											type : "POST",
											data : ( {
												reportsId : arrId,
												progRefs : arrProgRef,
												_pageRef : _pageRef,
												oldReportsId : arrOldId,
												appsName : arrAppName
											}),
											success : function(xml) {
												//relaod grid
											$("#upDownGrid_" + _pageRef)
													.trigger("reloadGrid");
											//reload the menu
											ddaccordion
													.initRoot(
															"appMenu",
															"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
															false);
											//empty form
											newUpload();
										}
										});
							} else {
								if (isUsed != "") {
									_showErrorMsg(repUplHypUsage + "  "
											+ isUsed);
								}
								if (isSched != "") {
									_showErrorMsg(schedUplRepUsage + "  "
											+ isSched);
								}
							}
						}
					});
}


function uploadReport() {
	//check if editable or not 
	var url = jQuery.contextPath+"/path/designer/upDownReport_retReportFlags.action?_pageRef="
			+ _pageRef;
	var myObject = {};
	$
			.post(
					url,
					myObject,
					function(param) {
						var isEditable = param['repCO']['EDITABLE_FLAG'];
						var isRep = param['repCO']['REP_FLAG'];
						if (isEditable == 1) {
							_showErrorMsg(nonEditableReport, info_msg_title);
							return;
						} else {

							//check empty fields
							var repName = $("#repName_" + _pageRef).val();
							var progRef = $("#refId_" + _pageRef).val();
							var parentRef = $("#pRefStr_" + _pageRef).val();
							var appName = $("#lookuptxt_appName_" + _pageRef)
									.val();
							var fileName = document.getElementById("upload_"
									+ _pageRef).value;
							var ext = fileName.split(".")[1];

							var format = $("#format_" + _pageRef).val();
							var connection = $("#connection_" + _pageRef).val();

							var isWebYn= $("#APP_IS_WEB_YN_" + _pageRef).val();
							var categ= $("#lookuptxt_CATEGORY_ID_" + _pageRef).val();
							if (repName == "" || progRef == ""
									|| parentRef == "" || appName == ""
									|| format == "" || (isWebYn==2 && parentRef=="ROOT" && categ=="")) {
								_showErrorMsg(missingFeReq);
								return;
							} else if (typeof ext == 'undefined') {
								_showErrorMsg(notAllowed);
								return;
							} else {
								//check if edit mode
								var url = jQuery.contextPath+"/path/designer/upDownReport_checkIfEditReport.action?_pageRef="
										+ _pageRef;
								var myObject = {};
								$
										.post(
												url,
												myObject,
												function(param) {
													var repMode = param['updates'];
													if (repMode == "1")//update mode
													{
														_showConfirmMsg(
																replaceRepByUpload,
																warning_msg_title,
																function(
																		confirmcChoice,
																		theArgs) {
																	if (confirmcChoice) {
																		if (isRep == "0") {
																			_showConfirmMsg(
																					showInDesignerAlert,
																					warning_msg_title,
																					function(
																							confirmcChoice,
																							theArgs) {
																						if (confirmcChoice) {
																							checkForSchedUsage()
																						}
																					},
																					{},
																					yes_confirm,
																					no_confirm,
																					350,
																					100);
																		} else {
																			checkForSchedUsage()
																		}
																	}
																}, {},
																yes_confirm,
																no_confirm,
																350, 100);
													} else //new
													{
														var skipOptAxsChk = document
																.getElementById("skipOptAxs_"
																		+ _pageRef).checked;
														if (skipOptAxsChk == true) {
															var zSrc = jQuery.contextPath+"/path/designer/upDownReport_checkIfSkipOptAxs.action";
															var params = {};
															params["updates"] = progRef;
															params["update"] = appName;
															$
																	.post(
																			zSrc,
																			params,
																			function(
																					param) {
																				var isOptExist = param["update"];
																				if (isOptExist == "-1") {
																					_showErrorMsg(
																							checkOptAxsAlert,
																							error_msg_title,
																							350,
																							120);
																				} else {
																					doUpload("true");
																				}
																			});

														} else {
															doUpload("true");
														}
													}
												});
							}
						}
					});
}



function checkForSchedUsage(isSave)
{
	var url = jQuery.contextPath+"/path/designer/reportsList_retSchedUsage.action?_pageRef="+_pageRef;
	var myObject = {};
	$.post(url, myObject, function(param1) 
	{
		var isSchedRep = param1['isSchedRep'];
		if (isSchedRep == "1")//if the report used in sched
		{
				_showConfirmMsg(repUsedInSched, warning_msg_title, function(confirmeChoice, theArgss)
				{
					if (confirmeChoice) 
					{
						if(isSave =="1")
						{
							doSaveUpload()
						}
						else
						{
							checkForHyperlink()
						}
					}
				}, {}, yes_confirm, no_confirm, 400, 100);
		}
		else
		{
			if(isSave =="1")
			{
				doSaveUpload()
			}
			else
			{
				checkForHyperlink()
			}
		}
	});
			
}


function checkForHyperlink() {
	//check if the report has hyperlinks
	var url1 = jQuery.contextPath+"/path/designer/reportsList_checkIfHasHyperlink.action?_pageRef="
			+ _pageRef;
	var myObject1 = {};
	$.post(url1, myObject1, function(param1) {
		var hasHyp = param1['updates'];
		if (hasHyp == "1")//if the report has hyperlink
			{
				_showConfirmMsg(hasHyperlinkAlert, info_msg_title, function(
						confirmeChoice, theArgss) {
					if (confirmeChoice) {
						checkForUpdateAll();
					}
				}, {}, yes_confirm, no_confirm, 400, 100);
			} else //the report does not have a hyperlink
			{
				checkForUpdateAll();
			}
		});
}


function checkForUpdateAll() {
	//ask if the user wants to override the args,procs and subrReps
	_showConfirmMsg(updateAllAlert, info_msg_title, function(confirmeChoice,
			theArgss) {
		if (confirmeChoice) {
			doUpload("true")
		} else {
			doUpload("false")
		}
	}, {}, yes_confirm, no_confirm, 300, 100);
}

function emptyUpDownPRefLkp() {
	$("#pRefStr_" + _pageRef).val("");
	$("#lookuptxt_fcrPRef_" + _pageRef).val("");
	$("#refId_" + _pageRef).val("");
}

 function startImportProcess()
{
var fileName=document.getElementById("uploadImport_"+_pageRef).value;
//var ext=fileName.split(".")[1]
var ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length); 
if(fileName=="" || ext.toLowerCase()!="zip")
{
	_showErrorMsg(fillZipFileAlert, error_msg_title, 300, 100);
	return;
}

var zipPassword = $("#zipPassword_" +_pageRef).val();
var importOption = $("#importOptionsListId_"+_pageRef).val();

	if(importOption=='REPLACE')
	{
		_showConfirmMsg(replaceConfirm,info_msg_title, function(
						confirmcChoice, theArgs) {
					if (confirmcChoice) 
					{
						continueImportProcess(zipPassword,importOption,false);
					}
					else
					{
						continueImportProcess(zipPassword,importOption,true);
					}
				}, {}, yes_confirm, no_confirm, 300, 100);
	}	
	else
	{
		continueImportProcess(zipPassword,importOption,false);
	}
		
}


 function doUpload(updateAll) {
	var fileName = document.getElementById("upload_" + _pageRef).value;
	var ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length);
	ext = ext.toUpperCase();
	if (ext == "JRXML") {
		var options = {
			url : jQuery.contextPath
					+ "/path/designer/upDownReport_uploadReport.action?_pageRef="
					+ _pageRef + "&updates=" + updateAll,
			type : 'post',
			success : function(param, status, xhr) {
				if (xhr.responseText.indexOf("errorMessage") != -1) {
					$("#actionErrorDiv").html(param)
					showHideReportDetails(0);
				} else {
					var jsonObj = $.parseJSON($(param).html())
					if (jsonObj != null && jsonObj._dependencyMsg != null
							&& jsonObj._dependencyMsg != "") {
						_showErrorMsg(jsonObj._dependencyMsg, error_msg_title,
								350, 120);
					} else {
						$("#actionErrorDiv").html("")
						
						
						//reload the when no data combo by checking if the jrxml is a freeForm or tabular
						var whenNoDataVal=$("#whenNoData_"+_pageRef).val();
						callDependency("whenNoData_"+_pageRef+":whenNoDataList",
						jQuery.contextPath + "/path/designer/upDownReport_reloadWhenNoDataList.action?_pageRef="+_pageRef,
						"" , "whenNoData_"+_pageRef, "putSelValAfterReload('"+whenNoDataVal+"')");
						
						//show notification to reset the when no data after upload
						_showErrorMsg(checkWhenNoDataAlert,info_msg_title, 350, 120);
						showHideReportDetails(1);
					}
				}
				_showProgressBar(false)
			}
		}
		_showProgressBar(true)
		$("#downUpFrm_" + _pageRef).ajaxSubmit(options)

	} else {
		_showErrorMsg(notAllowed);
	}
}

 
 function putSelValAfterReload(prevVal)
{
	$("#whenNoData_"+_pageRef).val(prevVal);
}
function collapseAllPanel(idDiv) {
	var toggleElem = $("#idDiv").children(":first").find('span .ui-icon');
	//if it is collapsed open it then load content to allow grid resize correctly
	if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
		$(".collapsibleContainerTitle", "div#idDiv").click();
	}
}



function showHideReportDetails(flag) {
	if (document.getElementById("saveAsBtn_" + _pageRef)) {
		document.getElementById("saveAsBtn_" + _pageRef).style.display = "inline";
		document.getElementById("translateBtn_" + _pageRef).style.display = "inline";
		
	}
	if (flag == "1") {
		var toggleElem = $("#paramsDiv_" + _pageRef).children(":first").find(
				'span .ui-icon');
		//if it is collapsed open it then load content to allow grid resize correctly
		if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
			$(".collapsibleContainerTitle", "div#paramsDiv_" + _pageRef)
					.click();
		}
		//collapseAllPanel("paramsDiv_"+_pageRef)
		var loadParamsSrc = jQuery.contextPath
				+ "/path/designer/queryDesign_openParameters.action?_pageRef="
				+ _pageRef;
		$("#paramsInnerDiv_" + _pageRef).load(loadParamsSrc);

		var toggleElemProc = $("#procsDiv_" + _pageRef).children(":first")
				.find('span .ui-icon');
		//alert("toggleElem =="+toggleElem)
		//if it is collapsed open it then load content to allow grid resize correctly
		if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
			$(".collapsibleContainerTitle", "div#procsDiv_" + _pageRef).click();
		}

		var loadProcsSrc = jQuery.contextPath
				+ "/path/designer/proc_openProcList.action?_pageRef="
				+ _pageRef;
		$("#procsInnerDiv_" + _pageRef).load(loadProcsSrc);

		var toggleElemSub = $("#uploadDownloadSubRep_" + _pageRef).children(
				":first").find('span .ui-icon');
		if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
			$(".collapsibleContainerTitle",
					"div#uploadDownloadSubRep_" + _pageRef).click();
		}
		var loadSubRepSrc = jQuery.contextPath
				+ "/path/designer/subrep_openSubRepMainUD.action?_pageRef="
				+ _pageRef;
		$("#uploadDownloadSubRepInnerDiv_" + _pageRef).load(loadSubRepSrc);

		var toggleElemHashTbl = $("#uploadDownloadHashTbl_" + _pageRef)
				.children(":first").find('span .ui-icon');
		if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
			$(".collapsibleContainerTitle",
					"div#uploadDownloadHashTbl_" + _pageRef).click();
		}
		var loadHashTblSrc = jQuery.contextPath
				+ "/path/designer/hashTbl_openHashTbl.action?_pageRef="
				+ _pageRef;
		$("#uploadDownloadHashTblInnerDiv_" + _pageRef).load(loadHashTblSrc);
		
		
		
		var toggleElemImages = $("#imagesDiv_" + _pageRef).children(
				":first").find('span .ui-icon');
		if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
			$(".collapsibleContainerTitle",
					"div#imagesDiv_" + _pageRef).click();
		}
		var loadImagesSrc = jQuery.contextPath +"/path/designer/uploadDownloadImages_openUpDownloadImages.action?_pageRef="+_pageRef;
		$("#imagesInnerDiv_"+_pageRef).load(loadImagesSrc);
		
		
		var toggleElemRepClient = $("#uploadDownloadRepClient_" + _pageRef).children(
		":first").find('span .ui-icon');
		if ($(toggleElem).hasClass('ui-icon-circle-triangle-s')) {
			$(".collapsibleContainerTitle",
					"div#uploadDownloadRepClient_" + _pageRef).click();
		}
		var loadRepClientSrc = jQuery.contextPath
				+ "/path/designer/repClient_openRepClient.action?_pageRef="
				+ _pageRef;
		$("#uploadDownloadRepClientInnerDiv_" + _pageRef).load(loadRepClientSrc);
		

		document.getElementById("upDownToolBarDivId").style.display = "inline";
		//check if the reportId==null then hide the saveAs btn
		var zSrc = jQuery.contextPath +"/path/designer/upDownReport_checkIfSavedReport.action";
		var params = {};
		params["_pageRef"] = _pageRef;
		$
				.post(
						zSrc,
						params,
						function(param) {
							var isSaved = param["updates"];
							if (isSaved == "0") {
								document
										.getElementById("saveAsBtn_" + _pageRef).style.display = "none";
								document.getElementById("translateBtn_" + _pageRef).style.display = "none";
							}

						});

	} else if (flag == "0") {
		document.getElementById("menuTitleEngLbl_"+_pageRef).style.display = "none";
		document.getElementById("menuTitleEngTxt_"+_pageRef).style.display = "none";
		document.getElementById("paramsInnerDiv_" + _pageRef).innerHTML = "";
		document.getElementById("procsInnerDiv_" + _pageRef).innerHTML = "";
		document.getElementById("uploadDownloadSubRepInnerDiv_" + _pageRef).innerHTML = "";
		if (document
				.getElementById("uploadDownloadHashTblInnerDiv_" + _pageRef)) {
			document
					.getElementById("uploadDownloadHashTblInnerDiv_" + _pageRef).innerHTML = "";
		}
		document.getElementById("upDownToolBarDivId").style.display = "none";
		document.getElementById("imagesInnerDiv_" + _pageRef).innerHTML = "";
		if(document.getElementById("uploadDownloadRepClientInnerDiv_" + _pageRef))
		{
			document.getElementById("uploadDownloadRepClientInnerDiv_" + _pageRef).innerHTML = "";
		}
	}
}


function upperProgRef() {
	var refStr = $("#refId_" + _pageRef).val();
	//Regular expression that not allow the user to insert arabic characters
	var re = new RegExp("[\u0600-\u06ff\ufb50-\ufdff\ufe70-\ufeff]", "g");
	refStr = refStr.replace(re, "")
	refStr = refStr.replace(" ", "");
	if($.browser.msie )
       document.getElementById("refId_" + _pageRef).innerText = refStr.toUpperCase()
    else
       $("#refId_" + _pageRef).val(refStr.toUpperCase());
	
}


function saveAsUpload() {
	var url = jQuery.contextPath +"/path/designer/reportsList_checkUpdateReportAccess.action"
	var params = {};
	params["_pageRef"] = _pageRef;
	//saveAs
	params["updates2"] = "SA";
	//check for saveAs access
	$
			.post(url,
					params,
					function(param) {
						var isAccess = param["accessStr"];
						var isEditable = param["editableStr"];
						
						if (isAccess == "N") {
							_showErrorMsg(repSaveAsAccessAlert,
									error_msg_title, 350, 120);
							return;
						}
						else if(isEditable == "N")
						{
							_showErrorMsg(repCantSaveUnmodifiable,error_msg_title, 350, 120);
									return;
						}
						else {
							//check for arguments order			 
					var url = jQuery.contextPath +"/path/designer/upDownReport_retReportFlags.action";
					var myObject = {};
					myObject["_pageRef"] = _pageRef;
					$
							.post(
									url,
									myObject,
									function(param) {
										var argOrder = param["argOrder"];
										if (argOrder == "1") {
											_showErrorMsg(wrongArgsOrder,
													error_msg_title, 350, 120);
											return;
										} else {
											//check for user existing opt axs
											//check if skip opt axs is clicked
											var skipOptAxsChk = document
													.getElementById("skipOptAxs_"
															+ _pageRef).checked;
											var repRef = $("#refId_" + _pageRef)
													.val();
											var appName = $(
													"#lookuptxt_appName_"
															+ _pageRef).val();
											if (skipOptAxsChk == true) {
												var zSrc = jQuery.contextPath +"/path/designer/upDownReport_checkIfSkipOptAxs.action";
												var params = {};
												params["updates"] = repRef;
												params["update"] = appName;
												$
														.post(
																zSrc,
																params,
																function(param) {
																	var isOptExist = param["update"];
																	if (isOptExist == "-1") {
																		$(
																				"#refId_"
																						+ _pageRef)
																				.val(
																						"");
																		_showErrorMsg(
																				checkOptAxsAlert,
																				error_msg_title,
																				350,
																				120);
																		return;
																	} else {
																		//show dialog
																		openSaveAsDlg();
																	}
																});
											} else {
												//show dialog
												openSaveAsDlg();
											}
										}
									});
				}
			});

}

function openSaveAsDlg() 
{
	var url = jQuery.contextPath +"/path/designer/upDownReport_updateReportDetails.action";
	var myObject = $("#downUpFrm_" + _pageRef).serializeForm();
	$.post(url, myObject, function(param) {
		$('#saveAsDialog_' + _pageRef).dialog('open');
	});

}


function SaveUpload() 
{
//check progRef validation
	var skipPrefValidation="0";
	if ($("#refId_" + _pageRef).attr('readonly') == "readonly") //if update or use existing opt
	{
		skipPrefValidation="1";
	}

		var refStr = $("#refId_"+_pageRef).val();
		var repAppName = $("#lookuptxt_appName_" + _pageRef).val();
		if(refStr=="")
		{
			_showErrorMsg(missingFeReq, error_msg_title, 300, 100);
			return;
		}
		
		var isVersioned = $("#reportVersion_"+_pageRef).val(); //param['repCO']['REPORT_VERSION'];
		var isModified =  $("#reportModifiedYN_"+_pageRef).val();//param['repCO']['REPORT_MODIFIED_YN'];
		if (isVersioned !="0" && isModified =="0")
		{
				
		_showConfirmMsg(updateVersionedReport, info_msg_title, function(confirmcChoice, theArgs) {
				if (confirmcChoice) 
					{
						SaveReport(skipPrefValidation,refStr);
					}
				else
				{
					return;
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
			
		}
		else
			{
			SaveReport(skipPrefValidation,refStr);
			}

} 
function SaveReport(skipPrefValidation,refStr)
	
{	
		//var refStr = $("#refId_"+_pageRef).val();
		var repAppName = $("#lookuptxt_appName_" + _pageRef).val();

		
		var zSrc = jQuery.contextPath +"/path/designer/upDownReport_checkProgRef.action";
		params = {};
		params["updates"] = refStr;
		params["update"] = repAppName;
		params["skipPrefValidation"]=skipPrefValidation;
		$.getJSON(zSrc, params, function(data2, status, xhr) {
			var retVal = data2['updates'];
			//check if the progRef exists in the irp_ad_hoc_report
				if (retVal != "0") // if the reference exist  
				{
					$("#refId_"+_pageRef).val("");
					_showErrorMsg(retVal);
				}
				else
				{
				var url = jQuery.contextPath +"/path/designer/reportsList_checkUpdateReportAccess.action"
				var params = {};
				params["_pageRef"] = _pageRef;
				params["updates2"] = "M";//updates
				$
				.post(
					url,
					params,
					function(param) {
						var isAccess = param["accessStr"];
						var isEditable = param["editableStr"];
						if (isAccess == "N") {
							_showErrorMsg(repHypUpdateAccessAlert,
									info_msg_title, 350, 120);

						} 
						else if(isEditable == "N")
						{
							_showErrorMsg(repCantSaveUnmodifiable,error_msg_title, 350, 120);
									return;
						}
						else {
								var url = jQuery.contextPath +"/path/designer/upDownReport_retReportFlags.action?_pageRef="
										+ _pageRef;
								var myObject = {};
								$
										.post(
												url,
												myObject,
												function(param) {
													var isEditable = param['repCO']['EDITABLE_FLAG'];
													if(param["updates"]==1)
													{
														_showErrorMsg(queriesNotFilled,error_msg_title,350, 120);
														return;
													}
													var argOrder = param["argOrder"];
													if (argOrder == "1") {
														_showErrorMsg(
																wrongArgsOrder,
																info_msg_title,
																350, 120);
														return;
													}
													if (isEditable == 1) {
														_showErrorMsg(
																nonEditableReport,
																info_msg_title);
														return;
													} else {
														var repName = $(
																"#repName_"
																		+ _pageRef)
																.val();
														var pRef = $(
																"#lookuptxt_fcrPRef_"
																		+ _pageRef)
																.val();
														var repRef = $(
																"#refId_"
																		+ _pageRef)
																.val();
														var appName = $(
																"#lookuptxt_appName_"
																		+ _pageRef)
																.val();
																
														var imgNameLst = $("#imagesGridId_"+_pageRef).jqGrid('getCol','mappedImgName');	
														
														
														for(var l=0;l<imgNameLst.length;l++)
														{
															if(imgNameLst[l]=="")
															{
																_showErrorMsg(missingColReq);
																return;
															}
														}
														var isWebYn= $("#APP_IS_WEB_YN_" + _pageRef).val();
														var categ= $("#lookuptxt_CATEGORY_ID_" + _pageRef).val();
														if($("#metadataRepYn_"+_pageRef).is(':checked')==true)
														{
															var metadataRep = $("#lookuptxt_metadataReport_"+_pageRef).val();
															var metadataRepExt = $("#metadataRepExt_"+_pageRef).val();
															var metadataLocation = $("#location_"+_pageRef).val();
															if(metadataRep=="" || metadataRepExt=="" || metadataLocation=="")
															{
																_showErrorMsg(missingFeReq);
																return;
															}
														}	
														if (repName == ""
																|| pRef == ""
																|| repRef == ""
																|| (isWebYn==2 && pRef=="ROOT" && categ=="")) {
															_showErrorMsg(missingFeReq);
															return;
														}
														
														var xlsName = $("#xlsName_"+_pageRef).val();
														if(xlsName!="" && (xlsName.endsWith('.xls')==false))
														{
															_showErrorMsg(missingExt);
															return;
														}
	
														//check if skip opt axs is clicked
														var skipOptAxsChk = document
																.getElementById("skipOptAxs_"
																		+ _pageRef).checked;
														if (skipOptAxsChk == true) {
															var zSrc = jQuery.contextPath +"/path/designer/upDownReport_checkIfSkipOptAxs.action";
															var params = {};
															params["updates"] = repRef;
															params["update"] = appName;
															$
																	.post(
																			zSrc,
																			params,
																			function(
																					param) {
																				var isOptExist = param["update"];
																				if (isOptExist == "-1") {
																					$(
																							"#refId_"
																									+ _pageRef)
																							.val(
																									"");
																					_showErrorMsg(
																							checkOptAxsAlert,
																							error_msg_title,
																							350,
																							120);
																					return;
																				} else {
																					checkForSchedUsage("1");
																				}
																			});
														} else {
															checkForSchedUsage("1");
														}
													}
												});
							}
	
						});
				}
			});
}


function doSaveUpload() 
{
	var zRowId = $("#upDownGrid_" + _pageRef).jqGrid('getGridParam', 'selrow');
	myObj = $("#upDownGrid_" + _pageRef).jqGrid('getRowData', zRowId);
	
	var repCltArray = $("#repClient_"+_pageRef).jqGrid('getCol','irpClientReportVO.CLIENT_ACRONYM');
	var cltRepFlag = $("#cltRepFlag_" + _pageRef)
	
	if (cltRepFlag.val() == 0)
	{
		var result = $("#repClient_"+_pageRef).jqGrid('checkRequiredCells');
		if(!result)return;
	}
	
	if (cltRepFlag.val() == 0 && repCltArray.length==0) {
		_showErrorMsg(empRepClt, info_msg_title, 300, 100);
		return;
	}
	
	repCltArray.sort();
	var repeatedExpr=false;
	for(var i=0;i<repCltArray.length-1;i++)
	{
		if(repCltArray[i].toUpperCase()==repCltArray[i+1].toUpperCase())
		{
			repeatedExpr=true;
		}
		break;
	}
	if(repeatedExpr==true)
	{
		_showErrorMsg(cltRepeated,info_msg_title,350,120);
		return;
	}
	
	
	
	var jsonStringImgUpdates = $("#imagesGridId_"+_pageRef).jqGrid('getAllRows');
	$("#updatesImages_"+_pageRef).val(jsonStringImgUpdates);
	
	var url = jQuery.contextPath +"/path/designer/upDownReport_saveUploadedReport.action";
		url +="?cltRepFlag="+cltRepFlag.val();
	var myObject = $("#downUpFrm_" + _pageRef).serializeForm();
	_showProgressBar(true);
	$
			.ajax( {
				url : url,
				type : "post",
				dataType : "json",
				data : myObject,
				success : function(param) {
					if (typeof param["_error"] == "undefined"
							|| param["_error"] == null) {
						if (param["haveSubRep"] == 1) {

							_showErrorMsg(empSubRep, info_msg_title, 300, 100);
						} else {

							_showErrorMsg(argProcSaved, info_msg_title, 300,
									100);

							//disable inputs
							disableEnableInputs(1);
							//reload grids
							$("#upDownGrid_" + _pageRef).trigger("reloadGrid");
							$("#argumentsGrid_" + _pageRef).trigger(
									"reloadGrid");
							$("#hashTblGridId_" + _pageRef).trigger(
									"reloadGrid");
							//empty form
							newUpload();
							//reload menu
							ddaccordion
									.initRoot(
											"appMenu",
											"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
											false);
						}
					}
					_showProgressBar(false);
				}
			});
}

function checkUpdDownIfCSV(repFormat, calledFrom)
{
	//if not called from saveAs
	if (calledFrom == null) {
		calledFrom = "";
	}
	document.getElementById("noHeadFootSpan_"+_pageRef+ calledFrom).style.display="inline";
	document.getElementById("noHeadAndFootLbl_"+_pageRef+ calledFrom).innerHTML=""+repNoHeadAndFoot;
	if(repFormat == "RDTXT" || repFormat == "RDXLS")
	{
		document.getElementById("noHeadAndFootLbl_"+_pageRef+ calledFrom).innerHTML=""+repNoHeaders;
	}
	if (repFormat == "CSV" || repFormat=="RDTXT") {
		document.getElementById("sepLblTd_" + _pageRef + calledFrom).style.display = "inline";
		document.getElementById("sepValTd_" + _pageRef + calledFrom).style.display = "inline";
	} else {
		document.getElementById("sepLblTd_" + _pageRef + calledFrom).style.display = "none";
		document.getElementById("sepValTd_" + _pageRef + calledFrom).style.display = "none";
	}
	
	if(repFormat=="SQL")
	{
		document.getElementById("noHeadFootSpan_"+_pageRef+ calledFrom).style.display="none";
	}
}

function openTranslate()
{
	rowid = $("#upDownGrid_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var appName = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,'APP_NAME');
	//repCO.APP_NAME repAppName refId_
	var repRef = $("#refId_" +_pageRef).val(); 
	var repAppName = $("#lookuptxt_appName_" +_pageRef).val(); 
	loadTransByAppPageRef(repAppName, repRef);//to open the translation screen
}	

function loadReportAndForm() {
	rowid = $("#upDownGrid_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var reportId = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'REPORT_ID');
	//check if the user has access to update the report
	var progRef = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'PROG_REF');
	var appName = $("#upDownGrid_" + _pageRef).jqGrid('getCell', rowid,
			'APP_NAME');

	loadTheReportAndForm(reportId, progRef, appName)	
}



function loadTheReportAndForm(reportId, progRef, appName) {

	var url = jQuery.contextPath+"/path/designer/reportsList_checkUpdateReportAccess.action"
	var params = {};
	params["updates"] = appName;
	params["updates1"] = progRef;
	params["updates2"] = "M";//update
	params["repCO.REPORT_ID"]=reportId;
	$
			.post(
					url,
					params,
					function(param) {
						var isAccess = param["accessStr"];
						var isEditable = param["editableStr"];
						if (isAccess == "N") {
							_showErrorMsg(repHypUpdateAccessAlert,
									info_msg_title, 350, 120);
						}
						if (isEditable == "N")
						{
							_showErrorMsg(repNotEditable,
									info_msg_title, 350, 120);
						}
						var url = jQuery.contextPath+"/path/designer/reportDesign_loadReport.action"
						var params = {};
						params["reportId"] = reportId;
						params["_pageRef"] = _pageRef;
						$.post(url, params,
								function(param) {
									if (typeof param == "object") {

										if (param._error != "")
											_showErrorMsg(param._error,
													error_msg_title)
									} else {
										$("#upDownMaintDiv_" + _pageRef).html(
												param);
										//disable inputs
								disableEnableInputs(1);

								//reload grids
								showHideReportDetails(1);


								//show hide separtor combo
								checkUpdDownIfCSV($("#format_" + _pageRef)
										.val());
								var metadataRep=0;
								if($("#metadataRepYn_"+_pageRef).is(':checked')==true)
									{
									metadataRep=1;
									}
								callDependency("metadataRepYn_"+_pageRef+":repCO.METADATA_REPORT_YN",
										jQuery.contextPath + "/path/designer/upDownReport_reportMetadataVisiblity.action",
										"repCO.METADATA_REPORT_YN:'"+metadataRep+"'" , "metadataRepYn_", "");								
								
							}
						});
					});
					

	$("#actionErrorDiv").html("");
}

function UploadDwnReadyFunc()
{
	$("#lookuptxt_appName_" + _pageRef).attr('readonly', true);

	//fill default values
	fillDefaultValues();

	checkUpdDownIfCSV("HTML");

	var isSyb = $("#isSyb_" + _pageRef)
	if (isSyb.val() == 0) {
	
		document.getElementById("uploadDownloadHashTbl_"
				+ _pageRef).innerHTML = "";
	}
	
	var cltRepFlag = $("#cltRepFlag_" + _pageRef)
	{
		if (cltRepFlag.val() == 1) {
		
			document.getElementById("uploadDownloadRepClient_"
					+ _pageRef).innerHTML = "";
		}
	}
	
	
	

	$("#upDownGrid_" + _pageRef).subscribe(
			'emptyUpDownTrx',
			function(event, data) {
				$(
						"#downloadFormIds_" + _pageRef
								+ " #auditTrxNbr_" + _pageRef)
						.val("");
			});
	$("#upDownGrid_" + _pageRef)
			.subscribe(
					'addDownloadBtn',
					function(event, data) {
						var pagerId = "upDownGrid_" + _pageRef
								+ "_pager_left";

						var myGrid = $("#upDownGrid_"
								+ _pageRef);


										
							myGrid.jqGrid(
										'navButtonAdd',
										pagerId,
										{
											caption : "",
											title : downloadTitle,
											id : "fullDownloadButton_"
													+ _pageRef,
											buttonicon : 'ui-icon-arrowthickstop-1-s',
											onClickButton : function() {
												fullDownloadReport();
											}
										});	
										
										
							myGrid
								.jqGrid(
										'navButtonAdd',
										pagerId,
										{
											caption : "",
											title : importTitle,
											id : "importButton_"
													+ _pageRef,
											buttonicon : 'ui-icon-arrowthickstop-1-n',
											onClickButton : function() {
												importReport();
											}
										});				

					});
			preventSpecialCharacters("refId_"+_pageRef)		
			
			$("#saveAsDialog_" + _pageRef).subscribe('closeSaveAsDlg',
				function(event, data) {
								//remove dialog content
								$('#saveAsDialog_' + _pageRef).empty();
							});
			
			callDependency("metadataRepYn_"+_pageRef+":0",
					jQuery.contextPath + "/path/designer/upDownReport_reportMetadataVisiblity.action",
					"reportId:-1" , "whenNoData_"+_pageRef, "");					
}
