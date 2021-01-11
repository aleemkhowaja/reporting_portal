function verifyReport()
{
		var url = jQuery.contextPath+ "/path/repCommon/reportAction_verifyReport.action";
		params={};
		params["_pageRef"] = _pageRef;
		$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: params,
			success: function(param)
			{
			   _showErrorMsg(param["updates"],info_msg_title,400,100);
			}
		});
}

function refreshRepMenuData(pageref, isFrom) {
    _showProgressBar(true);
    $.ajax({
           url: jQuery.contextPath+ "/path/dummy/DummyAction",
           type:"post",
           dataType:"json",
           success: function(data)
           {
                  _showProgressBar(false);
                  refreshRepMenuDataCommon(pageref, isFrom);
           }
    });
    
}


function refreshRepMenuDataCommon(pageref, isFrom) {
	//isFrom==1 if from menu
	if (typeof isFrom == "undefined" )
		{
		_showProgressBar(true);
		document.getElementById("tdPrint_" + pageref).style.display = "";
	}
	else 
	{
		document.getElementById("tdPrint_" + pageref).style.display = "none";
	}
   checkRTVCalcType(pageref ,false,isFrom)
}

function checkRTVCalcType(pageref,isExport,isFrom)
{
	//if _pageRef.endsWith('D00')
    var lstIndexD00=_pageRef.lastIndexOf("D00");
	if(lstIndexD00!=-1 && lstIndexD00+3==_pageRef.length)
	{
		var url = jQuery.contextPath + "/path/templateMaintReport/templMaint_checkRTVcalcType.action";
		var tmpCode = $("#lookuptxt_p_param_RA_TEMPLATE_CODE_NUMBER_"+pageref).val();
		var colTmpode = $("#lookuptxt_p_param_RA_COL_TMPLT_NUMBER_"+pageref).val();
		params={};
		params["code"]=tmpCode;
		params["code1"] = colTmpode;
		$.ajax({
		url: url,
		type:"post",
		dataType:"json",
		data: params,
		success: function(param)
		{
			if(param["updates1"]>=1)
			{
				_showErrorMsg(rtvAlert,error_msg_title,400,140);
				_showProgressBar(false);
			}
			else
			{
				checkColTempCalcTypeAndContinue(pageref,isExport,isFrom);
			}
		}
		});
		
	}
	else 
	{
		if (isExport) 
		{
		  if(_pageRef==optR0025 && (typeof isFrom == "undefined" ))	
		  {
			checkCrosscheckReport(pageref,isFrom,isExport)
		  }
		  else
		  {
			generate();
		  }
		}
		else 
		{
		  if(_pageRef==optR0025 && (typeof isFrom == "undefined" ))	
		  {
			checkCrosscheckReport(pageref,isFrom,isExport)
		  }	  
		  else
		  {
			refreshRepData(pageref, isFrom);
		  }
	   }
	}
}

function checkCrosscheckReport(pageref,isFrom,isExport)
{
	var url = jQuery.contextPath + "/path/templateMaintReport/templMaint_checkCrosscheckReport.action";
	params={};
	var asDt = $("#lookuptxt_p_param_AS_VT_VARCHAR2_"+_pageRef).val();
	var msgAppend;
	var msgDte = $("#p_param_AD_DTE_DATE_"+_pageRef).val();
	if(asDt=="T")
	{
		msgAppend = tradeDteAsOf+" ";
	}
	else
	{
		msgAppend =valueDteAsOf+" "; 
	}
	params["updates"]=asDt;
	params["updates1"]= msgDte;
	params["_pageRef"] = _pageRef;
	var msg="";
	$.ajax({
		url: url,
		type:"post",
		dataType:"json",
		data: params,
		success: function(param)
		{
		    if(param["updates"]=="Y")
		    {
		    	if (isExport) 
				{
					 generate();
				}
				else 
				{
					refreshRepData(pageref, isFrom);
				}
		    }
		    else
		    {	
				//45
				if(param["updates1"]==0)
				{
					msg+=reportRef+" R0045 - IBS 1a "+notExecuted+msgAppend+msgDte;
					_showProgressBar(false);
					_showConfirmMsg(msg,warning_msg_title,function(confirmcChoice, theArgs) {
						if (confirmcChoice) 
						{
							//23
							if(param["updates"]==0)
							{
							 	msg=reportRef+" R0023 - IBS 1b "+notExecuted+msgAppend+msgDte;
							 	_showConfirmMsg(msg,warning_msg_title,function(confirmcChoice, theArgs) {
					 				if (confirmcChoice) 
									{
					 					if (isExport) 
										{
											 generate();
										}
										else 
										{
											_showProgressBar(true);
											refreshRepData(pageref, isFrom);
										}
					 				}
							 	},null,yes_confirm,no_confirm,300,150);									 		
							}
							else
							{
								if (isExport) 
								{
									 generate();
								}
								else 
								{
									_showProgressBar(true);
									refreshRepData(pageref, isFrom);
								}
							}
						}
					}, null,  yes_confirm, no_confirm, 300, 150);
				}
				else if(param["updates"]==0)
				{
					msg=reportRef+" R0023 - IBS 1b "+notExecuted+msgAppend+msgDte;
					_showProgressBar(false);
				 	_showConfirmMsg(msg,warning_msg_title,function(confirmcChoice, theArgs) {
		 				if (confirmcChoice) 
						{
		 					if (isExport) 
							{
								 generate();
							}
							else 
							{
								_showProgressBar(true);
								refreshRepData(pageref, isFrom);
							}
		 				}
			 		},null,yes_confirm,no_confirm,300,150);					
				}
				else
				{
					if (isExport) 
					{
						 generate();
					}
					else 
					{
						refreshRepData(pageref, isFrom);
					}
				}
		    } 
		}
	});
}

function checkColTempCalcTypeAndContinue(pageref, isExport,isFrom)
{
	
	//fcr summarized
	//typeof added to prevent the popup from opening if it's not the retrieve button
	if(_pageRef==optFcrSummarized && (typeof isFrom =="undefined"))
	{
		var url = jQuery.contextPath + "/path/colTemplateMaintReport/checkColTempCalcType.action?";
		var code = $("#lookuptxt_p_param_RA_COL_TMPLT_NUMBER_"+pageref).val();
		params={};
		params["code"]=code;
		params["_pageRef"] = _pageRef;
		$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: params,
			success: function(param)
			{
				if(param["updates1"]==1)
				{
					_showErrorMsg(summarizedABD,error_msg_title,400,140);
					_showProgressBar(false);
				}
				else
				{
					if (isExport) 
					{
						generate();
					}
					else
					{
						refreshRepData(pageref, isFrom);
					}
				}
			}
		});
	}
	//fcr detailed
	//typeof added to prevent the popup from opening if it's not the retrieve button
	else if(_pageRef==optFcrDetailed && (typeof isFrom =="undefined"))
	{
		var url = jQuery.contextPath + "/path/colTemplateMaintReport/checkColTempCalcType.action?";
		var code = $("#lookuptxt_p_param_RA_COL_TMPLT_NUMBER_"+pageref).val();
		params={};
		params["code"]=code;
		params["_pageRef"] = _pageRef;
		$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: params,
			success: function(param)
			{
				//currency(R),CIF Name and Currency (K),Account No.and account name (N),CIF name branch and account no.(O)
				var groupBy = $("#lookuptxt_p_param_RA_TYPE_VARCHAR2_"+pageref).val();
				if(param["updates1"]==1 && (groupBy!='R' && groupBy!='K' && groupBy!='N' && groupBy!='O'))
				{
					_showErrorMsg(detailedABD,error_msg_title,400,160);
					_showProgressBar(false);
				}
				else
				{
					if (isExport) 
					{
							generate();
					}
					else 
					{
							refreshRepData(pageref, isFrom);
					}
				}
			}
		});
	}
	else
	{
		if (isExport)
		{
			generate();
		} 
		else
		{
			refreshRepData(pageref, isFrom);
		}
	}	
}

//proceed with report's execution
function refreshRepData(pageref, isFrom)
{
	$("#iframeLoaded_"+pageref).val("");
	var refreshUrl = jQuery.contextPath
			+ "/path/repCommon/reportAction_generateReport.action";
	var params = $("#dynParamFrmId" + pageref).serializeForm();
	var fromPage = parseInt($("#fromPage_" + pageref).val());
	var toPage = parseInt($("#toPage_" + pageref).val());
	
	if ( !isNaN(fromPage) && !isNaN(toPage) && (fromPage > toPage)) {
		_showErrorMsg(pagesRangeMsg, error_msg_title, 300, 100);
		_showProgressBar(false);
		return;
	}
	if(isNaN(fromPage))
	{
		fromPage=-9999999;
	}
	if (isNaN(toPage)) 
	{
		toPage=-9999999;
	}
	params += "&fromMenu=true&fromPage=" + fromPage + "&toPage=" + toPage;
	if (typeof isFrom != "undefined" && isFrom == 1) {
		params += "&noData=true";
	}
	if (typeof isFrom != "undefined" && isFrom == 2)
	{
		var asDtParamName=$("#snpAsOfDate_"+pageref).val();
		var aodVal;
		if(document.getElementById("p_param_"+asDtParamName+"_DATE_"+pageref)!=null)
		{
			aodVal=document.getElementById("p_param_"+asDtParamName+"_DATE_"+pageref).value;
		}
		else
		{
			aodVal=document.getElementById("snpAOD_"+pageref).value;
		}
		params += "&saveSnp=true&snp~snpFreq="
				+ $("#lookuptxt_snpFreq_" + pageref).val() + "&snp~snpDesc="
				+ $("#snpDesc_" + pageref).val() + "&snp~snpId="
				+ $("#snpId_" + pageref).val() + "&snp~repSnpId="
				+ $("#repSnpId_" + pageref).val() + "&snp~aod="
				+aodVal;
	}

	//annasuccar- 16/07/2013: load the html response inside an iframe instead of div 
	//to prevent the unresponsive script warning from poping up when the html is large
	/*$('#repMenuPrevDiv_'+pageref).load(refreshUrl, params, function(param)
	{
		$("#repMenuPrevDiv_"+pageref).html(param);
	_showProgressBar(false);
	});*/
	var themeName = (window["globalThemeName"] === undefined || window["globalThemeName"] == "") ? "Cupertino"
			: globalThemeName;
	themeName = themeName.toLowerCase();
	var progressUrl = jQuery.contextPath
			+ '/common/style/images/progressImages/' + themeName + '_Image.gif';
//	_showProgressBar(true);
	var finalUrl = refreshUrl + "?" + params;
	if(typeof isFrom == "undefined")
	{
		params += "&retrieveCall=true";
		$.ajax( {
			url : returnEncryptedUrl(refreshUrl),
			type : "post",
			dataType : "json",
			data : params,
			success : function(param) {
				if (typeof param["_error"] == "undefined"
						|| param["_error"] == null) 
				{
					if(param.reportOutputCO.hasPagination)
					{
						$("#tdPaginationPrevious_"+pageref).show();
						$("#tdPaginationNext_"+pageref).show();
						//hide the print button in case of oversize report
						$("#tdPrint_"+pageref).hide();
						//disable previous button and enable next button
						$('#btnPrevious_' + pageref).button({disabled : true});
						$('#btnNext_' + pageref).button({disabled : false});
						//initialize first page with 1
						$("#currentPage_"+pageref).val("1");
						$("#paginationCount_"+pageref).val(param.reportOutputCO.paginationCount);
					}
					else
					{
						$("#tdPaginationPrevious_"+pageref).hide();
						$("#tdPaginationNext_"+pageref).hide();
					}
//					$("#iframId_"+ pageref).contents().find('html').html(param.reportOutputCO.outputHtml);
					$("#iframId_"+ pageref).contents().find('html').find('head').empty();
					$("#iframId_"+ pageref).contents().find('html').find('body').empty().html(param.reportOutputCO.outputHtml);
					_showProgressBar(false);
				}
				else
				{
					_showProgressBar(false);
				}
			}
		});
	}
	else
	{
		jQuery(
				'<form id="submitFrmId_' + pageref + '" target="reportPrevIframe_'
				+ pageref + '"  action="' + returnEncryptedUrl(finalUrl)
				+ '" method="POST"></form>').appendTo("body").submit()
				.remove();
	}

	if (isFrom != 1) 
	{
		//check if the iframe is not yet loaded then call the dummy action in order to not loose the session	
		var interv=$("#hiddenSessionTimeOut_"+pageref).val()
		var timeoutId = setInterval(function()
		{
			 if($("#iframeLoaded_"+pageref).val()=="")
			 {
			 	callDummyAction_REP() 
			 } 
			 else
			 {
			 	clearInterval(timeoutId);
			 }
		 }, interv);
			
		
		//collapse arguements div after retrieving the report
		var collapseDiv = $("#paramsDivCollapsible_"+pageref+" > .collapsibleContainerTitle").get(0);
		if (!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) 
		{
			$(collapseDiv).trigger("click");
		}
	}
	window.setTimeout(
		function()
		{ 
			//set iframe height
			var mainHeight;
			var menuTabsHeight=0;
			var barHeight=0;
			if($("#mainTabs")==null || $("#mainTabs").height()==null)
			{
				 mainHeight = $("#portal_div").height();
			}
			else
			{
				 mainHeight = $("#mainTabs").height();
				 menuTabsHeight = $("#mainTabs ul:first-child").height();
				 barHeight = $("#infoBarCell_" + pageref).height();
			}
			
			var mainTblHeight = document.getElementById("mainTbl_" + pageref).offsetHeight;
			var frmHeight = mainHeight - menuTabsHeight - barHeight - mainTblHeight;
			document.getElementById("iframId_" + pageref).height = frmHeight;
		}, 1000);
	
	}


function openSortRep(pageref) {
	$('#orderPrevRepDlg_' + pageref).dialog('open');
}

function openFilterRep(pageref) {
	$('#filterPrevRepDlg_' + pageref).dialog('open');
}
function openGrpRep(pageref) {
	//check if the main report contains a group
	var zUrl = jQuery.contextPath
			+ "/path/repCommon/commonReporting_checkGrpUsage.action";
	var params = {};
	params["_pageRef"] = _pageRef;
	$.post(zUrl, params, function(param) {
		var isUsed = param["updates"];
		if (isUsed > 0) {
			_showErrorMsg(grpExist);
		} else {
			$('#grpPrevRepDlg_' + pageref).dialog('open');
		}
	});
}

function resetReportMenuData(pageref) {
	_showConfirmMsg(
			resetAlert,
			resetTitle,
			function(confirmcChoice, theArgs) {
				if (confirmcChoice) {
					//relaoad parameters
					//we replaced the menu with pageref cs if we open more than one tab at the same time the menu will be the value of the last opened report 
					var params1 = {};
					//params1["r_r"] = _pageRef;
					params1["menu"] = _pageRef;
					params1["htmlPageRef"]=pageref
					srcURL=jQuery.contextPath+"/path/reportsRet/dynRepParamsAction_loadDynParam.action"
					$('#paramsDiv_' + pageref).load(srcURL, params1, function()
	 				{
					//reset group,fileter and order
					var zUrl = jQuery.contextPath
							+ "/path/repCommon/commonReporting_resetPreviewOptions.action";
					var params = {};
					params["_pageRef"] = _pageRef;
					$.post(zUrl, params, function(param) {
						$("#fromPage_"+ _pageRef).val("");
						$("#toPage_"+ _pageRef).val("");
						$("#lang_id").val(null);
//						//reload data
//							_showConfirmMsg(reloadData, reloadTitle, function(
//									confirmcChoice, theArgs) {
//								if (confirmcChoice) {
									refreshRepMenuData(pageref,1);
//								}
//							}, null, null, null, 300, 100);
						});
					});
					}
			}, null, null, null, 300, 100);

}

function hideShowCsvSepartor(pageref) {
	var repFormat = document.getElementById("format_id_"+pageref).value;
	document.getElementById("chkHeadFootLblId_"+pageref).innerHTML = ""
			+ repMenuNoHeadAndFoot;
	if (repFormat == "RDTXT" || repFormat == "RDXLS") {
		document.getElementById("chkHeadFootLblId_"+pageref).innerHTML = ""
				+ repMenuNoHeaders;
	}
	if (repFormat == "CSV" || repFormat == "RDTXT" || repFormat == "CSVEXT") {
		document.getElementById("csvSeparatorId_"+pageref).style.display = "inline";
	} else {
		document.getElementById("csvSeparatorId_"+pageref).style.display = "none";
	}
}

function generate(pdff) {
	var url = jQuery.contextPath
			+ "/path/repCommon/reportAction_generateReport.action?";
	var ref = _pageRef.replace(/-/g, "_");
	var formParams = $("#dynParamFrmId" + ref).serializeForm();
	var fromPage = parseInt($("#fromPage_" + ref).val());
	var toPage = parseInt($("#toPage_" + ref).val());
	if (isNaN(fromPage))
	{
		fromPage = -9999999;
	}
	if (isNaN(toPage))
	{
		toPage = -9999999;
	}
	formParams += "&fromPage=" + fromPage + "&toPage=" + toPage;
	_showProgressBar(true);
	var isRepDone=false;
	if(typeof pdff != "undefined" && pdff=='1')
	{
		formParams+="&repPrintPdf=1";
	   //Based on jquery.fileDownload.js
	   //create an iframe to load the pdf, wait for download complete on cookie return
	   //use setTimeout instead of iframe onload method due to limitation of iframe on IE
	   //destroy the iframe and recreate it as its receiving a new pdf
	   
	   $('#tempPdfIframe').remove();
    //create a temporary iframe that is used to request the fileUrl as a GET request
    $iframe = $("<iframe id='tempPdfIframe'>")
        .hide()
        .attr("src", url+formParams)
        .appendTo("body");
    
    var cookieName= "repPdfFileDownload";
    var cookieValue= "true";
    var cookiePath= "/";
    //check if the file download has completed every checkInterval ms
    setTimeout(checkFileDownloadComplete,100);


    function checkFileDownloadComplete() {

        //has the cookie been written due to a file download occuring?
        if (document.cookie.indexOf(cookieName + "=" + cookieValue) != -1) {
     	   
		    	isRepDone=true;
		    	_showProgressBar(false);

            //remove the cookie and iframe
            var date = new Date(1000);
            document.cookie = cookieName + "=; expires=" + date.toUTCString() + "; path=" + cookiePath;

            return;
        }

        //has an error occured?
        //if neither containers exist below then the file download is occuring on the current window
        if ($iframe) {
            //has an error occured?
            try {
         	   var formDoc = $iframe[0].contentWindow || $iframe[0].contentDocument;
                if (formDoc.document) {
             	   formDoc = formDoc.document;
                }
                
                if (formDoc && formDoc.body != null && formDoc.body.innerHTML.length > 0) {
    			    		isRepDone=true;
    			    		_showErrorMsg(formDoc.body.innerHTML);
    			    		_showProgressBar(false);
    			    		return;
                }
            }
            catch (err) {
                //500 error less than IE9
			    	isRepDone=true;
			    	_showErrorMsg("");
			    	_showProgressBar(false);	
                return;
            }
        }
        //keep checking...
        setTimeout(checkFileDownloadComplete, 100);
    }
}
else
{
	  $.fileDownload(url,
				{
				    successCallback: function (url) 
				    {
				    	isRepDone=true;
				    	_showProgressBar(false);
				    },
				    failCallback: function (html, url) 
				    {
				    	isRepDone=true;
				    	_showErrorMsg(html)
				    	_showProgressBar(false);	 
				    },
				    httpMethod: "POST",
				    data:formParams,
				});	
	

	//check if the report did not return result yet then call the dummy action in order to not loose the session	
		var interv=$("#hiddenSessionTimeOut_"+ref).val()
		var timeoutId = setInterval(function()
		{
			 if(isRepDone==false)
			 {
			 	callDummyAction_REP() 
			 } 
			 else
			 {
			 	clearInterval(timeoutId);
			 }
		 }, interv);	
}
}

/*function that will only call a dummy action just to always send 
request to the server in case the report is taking time to 
retrieve data ,it is added to not face a disconnection issue*/
function callDummyAction_REP()
{
	$.ajax({
			url: jQuery.contextPath+ "/path/dummy/DummyAction",
	  		type:"post",
	  		dataType:"json",
	  		success: function(data)
	  		{
	  		}
	});
}

function fillIframeLoadedInput(pageref)
{
	$("#iframeLoaded_"+pageref).val("iframeLoaded");
}

function openSaveSnapshot(pageref)
{
	$('#saveSnpShtDlg_' + pageref).dialog('open');
}

function openArgFilters(pageref)
{
	$('#argsFilterDlg_' + pageref).dialog('open');
}

function rep_filters_saveLoadFilter(action)
{
	//insert or update
	if (action == 1)
	{
		if($("#filterName_"+htmlPageRef).val()=="")
		{
			_showErrorMsg(emptyFilterName, error_msg_title, 200, 100);
			return false;
		}
		var filterId 		 = $("#hiddenFilterId_"+htmlPageRef).val();
		var filterName 		 = $("#filterName_"+htmlPageRef).val();
		var publicYn 		 = $("#filterPublic_"+htmlPageRef).is(':checked');
		var overrideArgument = $("#overrideArgs_"+htmlPageRef).is(':checked');
		var defaultYn = $("#filterDefault_"+htmlPageRef).is(':checked');
		update=updateMode;
		if (filterId == null || filterId == "")
		{
			update=insertMode;
		}
		var myObject = $("#dynParamFrmId"+htmlPageRef).serializeForm();
		var url = jQuery.contextPath
					+"/path/repCommon/reportAction_saveLoadFilter.action?update="+update+"&filterName="
					+filterName+"&publicYn="+publicYn+"&updates="+overrideArgument+"&defaultYn="+defaultYn;
			$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				data : myObject,
				success : function(param)
				{
					if(param["update"]==0)
					{
						_showErrorMsg(filterNameExists, error_msg_title, 200, 100);
					}
					else if(param["update"]==2)
					{
						_showErrorMsg(defaultFilterExists, error_msg_title, 200, 100);
					}	
					else if(param["update"]==3)
					{
						_showErrorMsg(defaultFilterAndNameExists, error_msg_title, 200, 100);
					}
					else if(param["update"]==falseVal)
					{
						_showErrorMsg(noArgsToSave, error_msg_title, 250, 100);
					}
					else
					{
						$("#hiddenFilterId_"+htmlPageRef).val(param["filterId"]);
						$("#argsFilterDlg_"+htmlPageRef).dialog("close");
						$("#hiddenFilterTmpId_"+htmlPageRef).val("");
					}
				}
			});
	}
	//loading a filter
	else
	{
		var rowId = $("#argFiltersGrid_" +htmlPageRef).jqGrid('getGridParam','selrow');
		if (rowId == null)
		{
			_showErrorMsg(noFilterSelected, info_msg_title, 200, 100);
			return false;
		}
		var myObject 	 = $("#argFiltersGrid_"+htmlPageRef).jqGrid('getRowData',rowId);
		selectedFilterId = myObject["FILTER_ID"];
		$("#hiddenFilterId_" + htmlPageRef).val(selectedFilterId);
		myObject 	     = $("#dynParamFrmId" + _pageRef).serializeForm();
		var url = jQuery.contextPath+"/path/repCommon/reportAction_saveLoadFilter.action?filterId="+selectedFilterId+"&update=true"
			+"&var_menuId="+$("#hiddenMenuId_"+htmlPageRef).val();
		_showProgressBar(true)
		$.ajax({
			url : url,
			type : "post",
			dataType : "json",
			data : null,
			success : function(param)
			{
				$("#argsFilterDlg_"+htmlPageRef).dialog("close");
				_showProgressBar(false)
				callDependency(	"fcrId_"+_pageRef+":glstmpltCO.FCRStr",
						jQuery.contextPath+'/path/repCommon/reportAction_refreshArgumentsValues.action?_pageRef='+_pageRef+
						"&var_menuId="+$("#hiddenMenuId_"+htmlPageRef).val(),
						"",
						"",
						"");
			}
		});
	}
}

function rep_filters_deleteFilter()
{
	_showConfirmMsg(deleteConfirm, deleteTitle, function(
			confirmcChoice, theArgs) 
			{
				if (confirmcChoice) 
				{
					rowid = $("#argFiltersGrid_" + htmlPageRef).jqGrid('getGridParam', 'selrow');
					myObject = $("#argFiltersGrid_" + htmlPageRef).jqGrid('getRowData', rowid);
					selectedFilterId = myObject["FILTER_ID"];
					createdBy=myObject["CREATED_BY"];
					//empty data hashmaps	
					var url = jQuery.contextPath
							+ "/path/repCommon/reportAction_deleteFilter.action?update="+createdBy+"&filterId="
							+ selectedFilterId;
					$.ajax({
						url : url,
						type : "post",
						dataType : "json",
						data : null,
						success : function(param)
						{
							if(param["update"]==1)
							{
								_showErrorMsg(filterOtherUser, info_msg_title, 300, 100);
							}
							else
							{
								if($("#hiddenFilterId_"+htmlPageRef).val()==selectedFilterId)
								{
									$("#hiddenFilterId_"+htmlPageRef).val("");
								}
								$("#argFiltersGrid_" + htmlPageRef).jqGrid('deleteGridRow');
							}
						}
					});
				}
	}, {
	
	}, yes_confirm, no_confirm, 300, 100);
}

function rep_filters_refillIdIfAny()
{
	if($("#hiddenFilterTmpId_"+htmlPageRef).val()!="")
	{
		$("#hiddenFilterId_"+htmlPageRef).val($("#hiddenFilterTmpId_"+htmlPageRef).val());	
		$("#hiddenFilterTmpId_"+htmlPageRef).val("");
	}
	$("#argsFilterDlg_"+htmlPageRef).dialog("close");
}

function rep_filters_emptyForm()
{
	$("#filterName_"+htmlPageRef).val("");
	$("#filterPublic_"+htmlPageRef).attr('checked', false);
	$("#overrideArgs_"+htmlPageRef).attr('checked', false);
	$("#filterDefault_"+htmlPageRef).attr('checked', false);
	$("#overrideArgs_"+htmlPageRef).attr('disabled', 'disabled');
	$("#hiddenFilterTmpId_"+htmlPageRef).val($("#hiddenFilterId_"+htmlPageRef).val());
	$("#hiddenFilterId_"+htmlPageRef).val("");	
	$("#filterName_"+htmlPageRef).removeAttr("readonly");
	$("#filterPublic_"+htmlPageRef).removeAttr("disabled");
	$("#save_btn_"+htmlPageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
	$("#save_btn_"+htmlPageRef).removeAttr('disabled','disabled')
}

function rep_filters_publicReadOnly()
{
	var rowIds = $("#argFiltersGrid_"+htmlPageRef).jqGrid('getDataIDs');
    for (var i =0;i<rowIds.length;i++)
    {
        var lRowId = rowIds[i];
    	$("#argFiltersGrid_"+htmlPageRef).jqGrid("setCellReadOnly",lRowId,"PUBLIC_YN",true);
    	$("#argFiltersGrid_"+htmlPageRef).jqGrid("setCellReadOnly",lRowId,"DEFAULT_YN",true);
    }
}

function rep_filters_readyFunc()
{
	$("div#argsFilterOuterDiv_"+htmlPageRef+" .collapsibleContainer").collapsiblePanel();
	$("div#argsFilterTblDiv_"+htmlPageRef+" .collapsibleContainer").collapsiblePanel();
	$("#argFiltersGrid_"+htmlPageRef).subscribe('addLoadFilterBtn',function(event, data) {
	var pagerId = "argFiltersGrid_"+htmlPageRef+"_pager_left";
	var myGrid = $("#argFiltersGrid_"+htmlPageRef);
	myGrid.jqGrid('navButtonAdd',pagerId,{
			caption : "",
			title : loadFilter,
			id : "loadFilterButton_"+htmlPageRef,
			buttonicon : 'ui-icon-arrowthickstop-1-s',
			onClickButton : function() {
				rep_filters_saveLoadFilter(2);
			}});				
	});
filterId = $("#hiddenFilterId_"+htmlPageRef).val();
if(filterId=="")
{
	$("#overrideArgs_"+htmlPageRef).attr('disabled', 'disabled');
}
else
{
	params={};
	params["_pageRef"]=_pageRef;
	params["filterId"]=filterId;
	var url = jQuery.contextPath+"/path/repCommon/reportAction_retFilterById.action"

		$.ajax({
		url : url,
		type : "post",
		dataType : "json",
		data : params,
		success : function(param)
		{
			$("#filterName_"+htmlPageRef).val(param["irpRepFilterVO"].FILTER_NAME);
			$("#filterPublic_"+htmlPageRef).attr('checked',param["irpRepFilterVO"].PUBLIC_YN==1?true:false);
			$("#overrideArgs_"+htmlPageRef).attr('checked', true);
			$("#filterDefault_"+htmlPageRef).attr('checked',param["irpRepFilterVO"].DEFAULT_YN==1?true:false);
			if(param["updates"]=="false")
			{
				$("#filterName_"+htmlPageRef).attr("readonly","readonly");
				$("#filterPublic_"+htmlPageRef).attr("disabled","disabled");
				$("#overrideArgs_"+htmlPageRef).attr('disabled', 'disabled');
				$("#save_btn_"+htmlPageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
				$("#save_btn_"+htmlPageRef).attr('disabled','disabled');
			}
			else
			{
				$("#save_btn_"+htmlPageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
				$("#save_btn_"+htmlPageRef).removeAttr('disabled','disabled');
			}
		}
		});
}
$("#argFiltersGrid_"+htmlPageRef).subscribe('rep_filters_publicReadOnly', function(event, data) {
	rep_filters_publicReadOnly();
});
}

function rep_menu_docReadyFunc()
{
	var lstIndexD00=_pageRef.lastIndexOf(fcrExt);
	if(lstIndexD00!=-1 && lstIndexD00+3==_pageRef.length)
	{
		document.getElementById("filterRepId_"+htmlPageRef).style.display = "none";
	}
	_showProgressBar(true);
	var srcURL=jQuery.contextPath + "/path/reportsRet/dynRepParamsAction_loadDynParam.action?menu="+menu+"&htmlPageRef="+htmlPageRef;
	var params={};
	$("#paramsDiv_"+htmlPageRef).load(srcURL, params, function()
	 {
	 	refreshRepMenuData(htmlPageRef,1); //execute the report without data	  	
	  	/*the below code has been removed from above the function ready and put here because in some case the code go into the ready function
		then go to showProgressBar(false) then continue the ready , in this case the progressBar will be set to false before finishing the loading */
	  	$("#iframId_"+htmlPageRef).load(function() 
		{
			_showProgressBar(false);
		   	$("#iframId_"+htmlPageRef).show();
		});
	});
	if(menu=="RD00R")
	{
		$("#verifyBtn_"+htmlPageRef).attr("style","display:none");
	}
}

function rep_checkSnapshotExist(pgRef,isExport) {
    _showProgressBar(true);
    $.ajax({
           url: jQuery.contextPath+ "/path/dummy/DummyAction",
           type:"post",
           dataType:"json",
           success: function(data)
           {
                 _showProgressBar(false);
                 rep_checkSnapshotExistCommon(pgRef,isExport);
           }
    });
    
}

/* Author:Fares Kassab 
 * date:11/07/2016
 * desc:function that will call reportAction checkSnapshotExist method in case of "TXTFILE" format to check the existance of a snapshot parameter*/
function rep_checkSnapshotExistCommon(pgRef,isExport)
{
	var repFormat = $("#format_id_"+pgRef).val();
	
	if (repFormat == "TXTFILE" || repFormat=="DAT")
	{
	var url = jQuery.contextPath + "/path/repCommon/reportAction_checkSnapshotExist.action?";
	params={};
	params["_pageRef"] = _pageRef;
	$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: params,
			success: function(param)
			{
				if(param["updates"] == "true")
				{
					_showErrorMsg(notExistTxtFormula,warning_msg_title,300,100);
					_showProgressBar(false);
					return;
				}
				else
				{
					checkRTVCalcType(pgRef,isExport);
				}
	    	}
		   });
	}
	else
	{
	checkRTVCalcType(pgRef,isExport);
	}
}

function printIframeContents(pgRef)
{
	var myObject = $("#dynParamFrmId" + pgRef).serializeForm();
	var url=jQuery.contextPath+"/path/repCommon/reportAction_checkAuditPrintReport.action?conId="+$("#connection_id_"+pgRef).val();
	$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: myObject,
			success: function(param)
			{
				//if 1 print the report using its pdf layout based on the rep_print_pdf_yn flag
				if(param["updates"]==1)
				{  
					generate('1');
				}
				else
				{
					common_printFrameContents("iframId_"+pgRef);		
				}
			}
		});
	}

function rep_paginationPreviousNext(page_ref,PreviousOrNext)
{
	var currentPage = $("#currentPage_"+page_ref).val();
	var refreshUrl = jQuery.contextPath
			+ "/path/repCommon/reportAction_PaginationPreviousNext.action";
	var params = {};
	params["_pageRef"] = page_ref;
	_showProgressBar(true);
	$('#btnPrevious_' + page_ref).button({disabled : false});
	if(PreviousOrNext == "P")
	{
		params["currentPage"] = --currentPage;
	}
	else
	{
		params["currentPage"] = ++currentPage;
	}	
	$.ajax( {
		url : refreshUrl,
		type : "post",
		dataType : "json",
		data : params,
		success : function(param) {
			if (typeof param["_error"] == "undefined"
					|| param["_error"] == null) 
			{
				if(param.reportOutputCO.outputHtml != null)
				{
					//related to previous button
					if(currentPage == 1)
					{
						$('#btnPrevious_' + page_ref).button({disabled : true});
					}
					else
					{
						$('#btnPrevious_' + page_ref).button({disabled : false});
					}	
					//related to next button
					if($("#paginationCount_"+page_ref).val() == currentPage)
					{
						$('#btnNext_' + page_ref).button({disabled : true});
					}
					else
					{
						$('#btnNext_' + page_ref).button({disabled : false});
					}	
					$("#iframId_"+ page_ref).contents().find('html').html(param.reportOutputCO.outputHtml);
					_showProgressBar(false);
				}
				$("#currentPage_"+page_ref).val(currentPage);
				_showProgressBar(false);
			}
			else
			{
				_showProgressBar(false);
			}
		}
	});
}
