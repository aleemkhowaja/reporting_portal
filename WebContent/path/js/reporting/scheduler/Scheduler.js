autoCompleteEmailBody = function(theInputId,expression_cust_tags)
			{ 
				 var theInput = $("#"+theInputId);
				 // don't navigate away from the field on tab when selecting an item
			     theInput.bind( "keydown", function( event )
			     {
				   	if( event.keyCode === $.ui.keyCode.DOWN && !theInput.isopened)
				   	{
				       	theInput.autocomplete( "search", "" );
				   	}
			     })
			     .autocomplete({
			       minLength: 0,
			       inputId:theInputId,
			       source: expression_cust_tags,
			       open: function( event, ui )
			       {
			       	theInput.isopened = true;
			       },
			       close: function( event, ui )
			       {
			       	theInput.isopened = false;
			       },
			       focus: function()
			       {
			         // prevent value inserted on focus
			         return false;
			       },
			       select: function( event, ui )
			       {
			          var cursPosition   = returnCursorPosStart(document.getElementById(theInputId));
			    	  var strTillCurrPos = this.value.substring(0,cursPosition)
			    	  /**
			    	   * [MarwanMaddah]: this pattern will catch all the words 
			    	   * that are exists in the input from index 0 untill the current cursor position
			    	   * then the last word will be replaced by the selected value from the Search result
			    	   */
			    	  var patt       = /\w+/g;
			          var result     = strTillCurrPos.match(patt);
			          var firstPart  = "";
			          if(result!= null && result.length > 0)
			          {        	  
			             var resultLgth = result.length;
			    	     firstPart  = strTillCurrPos.substring(0,strTillCurrPos.lastIndexOf(result[resultLgth-1])); 
			          }
			          else
			          {
			        	 firstPart = strTillCurrPos; 
			          }
			          this.value = firstPart + " " +ui.item.value +" "+ this.value.substring(cursPosition);
			          return false;
			       }
			     });
};	


function detGridClicked() {

	rowid = $("#schedReportGrid").jqGrid('getGridParam', 'selrow');
	myObject = $("#schedReportGrid").jqGrid('getRowData', rowid);
	showRepConfig(1);	
	//fill the combos 
	callDependency(
			"updates1_"+_pageRef+
			":updates1,updates2_"+_pageRef+":updates2,FROM_EMAIL_FE_VAL_"+_pageRef+":emailFeLst,TO_EMAIL_FE_VAL_"+_pageRef+":emailFeLst,CC_EMAIL_FE_VAL_"+_pageRef+":emailFeLst,BCC_EMAIL_FE_VAL_"+_pageRef+":emailFeLst,TO_EMAIL_CIF_VAL_"+_pageRef+":emailCIFLst,ATTACH_FILE_NAME_"+_pageRef+":emailFileNameLst,TO_EMAIL_FECOMP_VAL_"+_pageRef+":emailComputeFeLst,CC_EMAIL_FECOMP_VAL_"+_pageRef+":emailComputeFeLst,BCC_EMAIL_FECOMP_VAL_"+_pageRef+":emailComputeFeLst,SECURED_PWD_FIELD_NAME_"+_pageRef+":passwordFeLst",
			jQuery.contextPath + "/path/scheduler/scheduler_loadMailCombos",
			"reportId:" + myObject["REPORT_ID"], "", "fillRepSchedForm()");

}//end detGridClicked

function fillRepSchedForm() {
	rowid = $("#schedReportGrid").jqGrid('getGridParam', 'selrow');
	var isFcrYn = false;
	myObject = $("#schedReportGrid").jqGrid('getRowData', rowid);
	var zRepId = myObject["REPORT_ID"];
	var zSchedId = myObject["SCHED_ID"];
	var zRepRef = myObject["REPORT_REF"];
	var zSrc = jQuery.contextPath+"/path/scheduler/scheduler_findSingleReportSched.action?SCHED_ID="
			+ zSchedId + "&REPORT_ID=" + zRepId + "&REPORT_REF="+zRepRef;
	params = {};
	$
			.getJSON(
					zSrc,
					params,
					function(data2, status, xhr) {
						if (data2['reportSchedCO'] != null) {
							$("#lookuptxt_REPORT_ID").val(
									data2['reportSchedCO']['REPORT_ID']);
							$("#REPORT_NAME").val(
									data2['reportSchedCO']['REPORT_NAME']);
							$("#REPORT_FORMAT").val(
									data2['reportSchedCO']['REPORT_FORMAT']);
							$("#REPORT_LANGUAGE").val(
									data2['reportSchedCO']['REPORT_LANGUAGE']);
							$("#SAVE_DATA_TYPE_" + _pageRef).val(
									data2['reportSchedCO']['SAVE_DATA_TYPE']);
							
							//reload the autocomplete in the body textarea based on the new report id 
							 var zUrl =jQuery.contextPath+"/path/scheduler/scheduler_retEmailBodyAutoCompleteFields.action?reportId="+$("#lookuptxt_REPORT_ID").val();
							 $.ajax({
							 url: zUrl,
					         type:"post",
							 dataType:"json",
							 data: params,
							 success: function(param)
								{
									autoCompleteEmailBody("EMAIL_BODY_"+_pageRef,(param["updates"]).split(","));
								}
					    	 });
							showHideMailConfig($("#SAVE_DATA_TYPE_" + _pageRef)
									.val(), 0)

							if (data2['reportSchedCO']['SHOW_HEAD_FOOT'] == '0') {
								$("#noHeadAndFoot_" + _pageRef).attr('checked',
										true);
							} else {
								$("#noHeadAndFoot_" + _pageRef).attr('checked',
										false);
							}

							checkSchedIfCSV($("#REPORT_FORMAT").val());

							$("#csvSeparatorId_" + _pageRef).val(
									data2['reportSchedCO']['CSV_SEPARATOR']);

							if (data2['reportSchedCO']['IS_ACTIVE'] == 'Y') {
								$("#IS_ACTIVE").attr('checked', true);
							} else {
								$("#IS_ACTIVE").attr('checked', false);
							}

							if (data2['reportSchedCO']['PRINTER_NAME'] == null
									|| data2['reportSchedCO']['PRINTER_NAME'] == "") {
								$("#IS_PRINT").attr('checked', false);
								$("#printers").hide();
							} else {
								$("#IS_PRINT").attr('checked', true);
								$("#printers").show();
								$("#PRINTER_NAME").val(
										data2['reportSchedCO']['PRINTER_NAME']);

							}

							//fill data related to the mail section  

							$("#lookuptxt_msHost_" + _pageRef).val(
									data2['reportSchedCO']['MAIL_SERVER_HOST']);
							$("#msId_" + _pageRef).val(
									data2['reportSchedCO']['MAIL_SERVER_CODE']);
							$("#DEFAULT_FROM_MS_" + _pageRef).val(
									data2['reportSchedCO']['DEFAULT_FROM_MS']);

							$("#FROM_EMAIL_TYPE_" + _pageRef).val(
									data2['reportSchedCO']['FROM_EMAIL_TYPE']);
							fromEmailType = $("#FROM_EMAIL_TYPE_" + _pageRef)
									.val();
							hideShowFromVal(fromEmailType);
							$("#FROM_EMAIL_VAL_" + _pageRef).val(
									data2['reportSchedCO']['FROM_EMAIL_VAL']);
							if (fromEmailType == "3") {
								$("#FROM_EMAIL_FE_VAL_" + _pageRef).val(
										$("#FROM_EMAIL_VAL_" + _pageRef).val());
							}

							$("#TO_EMAIL_TYPE_" + _pageRef).val(
									data2['reportSchedCO']['TO_EMAIL_TYPE']);
							toEmailType = $("#TO_EMAIL_TYPE_" + _pageRef).val();
							hideShowToVal(toEmailType)
							$("#TO_EMAIL_VAL_" + _pageRef).val(
									data2['reportSchedCO']['TO_EMAIL_VAL']);
							if (toEmailType == "2") {
								$("#TO_EMAIL_FE_VAL_" + _pageRef).val(
										$("#TO_EMAIL_VAL_" + _pageRef).val());
							} else if (toEmailType == "3") {
								$("#TO_EMAIL_CIF_VAL_" + _pageRef).val(
										$("#TO_EMAIL_VAL_" + _pageRef).val());
							}

							$("#CC_EMAIL_TYPE_" + _pageRef).val(
									data2['reportSchedCO']['CC_EMAIL_TYPE']);
							ccEmailType = $("#CC_EMAIL_TYPE_" + _pageRef).val();
							hideShowCcVal(ccEmailType)
							$("#CC_EMAIL_VAL_" + _pageRef).val(
									data2['reportSchedCO']['CC_EMAIL_VAL']);
							if (ccEmailType == "2") {
								$("#CC_EMAIL_FE_VAL_" + _pageRef).val(
										$("#CC_EMAIL_VAL_" + _pageRef).val());
							}

							$("#BCC_EMAIL_TYPE_" + _pageRef).val(
									data2['reportSchedCO']['BCC_EMAIL_TYPE']);
							bccEmailType = $("#BCC_EMAIL_TYPE_" + _pageRef)
									.val();
							hideShowBccVal(bccEmailType)
							$("#BCC_EMAIL_VAL_" + _pageRef).val(
									data2['reportSchedCO']['BCC_EMAIL_VAL']);
							if (bccEmailType == "2") {
								$("#BCC_EMAIL_FE_VAL_" + _pageRef).val(
										$("#BCC_EMAIL_VAL_" + _pageRef).val());
							}

							$("#EMAIL_SUBJECT_" + _pageRef).val(
									data2['reportSchedCO']['EMAIL_SUBJECT']);
							$("#EMAIL_BODY_" + _pageRef).val(
									data2['reportSchedCO']['EMAIL_BODY']);

							$("#ATTACH_FILE_NAME_" + _pageRef).val(
									data2['reportSchedCO']['ATTACH_FILE_NAME']);

							hideShowDateDB();
							$("#DATE_TYPE_" + _pageRef).val(
									data2['reportSchedCO']['DATE_TYPE']);

							if (data2['reportSchedCO']['SEND_IF_NO_DATA_YN'] == '0') {
								$("#SEND_IF_NO_DATA_YN_" + _pageRef).attr(
										'checked', true);
							} else {
								$("#SEND_IF_NO_DATA_YN_" + _pageRef).attr(
										'checked', false);
							}

							if (data2['reportSchedCO']['PRINT_IF_NO_DATA_YN'] == '0') {
								$("#PRINT_IF_NO_DATA_YN_" + _pageRef).attr(
										'checked', true);
							} else {
								$("#PRINT_IF_NO_DATA_YN_" + _pageRef).attr(
										'checked', false);
							}
							if (data2['reportSchedCO']['SECURED_FILE_YN'] == '1') {
								$("#SECURED_FILE_YN_" + _pageRef).attr(
										'checked', true);
								$("#password").show();
								$("#passwordLabel").show();
								$("#SECURED_PWD_FIELD_NAME_"+_pageRef).val(data2['reportSchedCO']['SECURED_PWD_FIELD_NAME']);
							} else {
								$("#SECURED_FILE_YN_" + _pageRef).attr(
										'checked', false);
								$("#password").hide();
								$("#passwordLabel").hide();
								$("#SECURED_PWD_FIELD_NAME_"+_pageRef).val("");
								
							}
							if(data2['reportSchedCO']['IS_FCR_YN']=='1')
							{
								isFcrYn=true;
								$("#isFcrRep_"+_pageRef).attr('checked', true);
								$("#lookuptxt_fcrRepId_"+_pageRef).val(data2['reportSchedCO']['REPORT_REF']);
								$("#fcrRepName_"+_pageRef).val(data2['reportSchedCO']['FCR_REPORT_NAME']);
							}
							else
							{
								isFcrYn=false;
								$("#isFcrRep_"+_pageRef).attr('checked', false);
								$("#lookuptxt_fcrRepId_"+_pageRef).val("");
								$("#fcrRepName_"+_pageRef).val("");
							}
							

							$("#updates1_" + _pageRef).val("");
							$("#updates2_" + _pageRef).val(
									$("#TO_EMAIL_TYPE_" + _pageRef).val());

							//reload groupByFeList grid
							repSchedule_reConstructGroupByDiv(zRepId,zSchedId,"true",null)

							//hide usersList grids
							hideUsersLstGrids();
							//reload usersList grids
							emptyUsersLstGrids();

							//end fill data related to the mail section

							$("#detMode").val("edit");
							liveSearch_makeReadOnly(true, "REPORT_ID");
							liveSearch_makeReadOnly(true, "fcrRepId_"+_pageRef);
							$("#REPORT_NAME").attr('readonly', true);
							$("#fcrRepName_"+_pageRef).attr('readonly',true);
							$("#isFcrRep_"+_pageRef).attr('disabled','disabled');

							//empty params form
							var loadSrc;
							if(isFcrYn)
							{
								 loadSrc = jQuery.contextPath
									+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?updates="
									+$("#lookuptxt_fcrRepId_"+_pageRef).val()+"D00,"
									+$("#lookuptxt_REPORT_ID").val()+"&code=-2&update=true&schedId="+zSchedId+"&report_ref="+zRepRef;
							}
							else
							{
								 loadSrc = jQuery.contextPath
										+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code="
										+ zRepId + "&update=true&schedId="
										+ zSchedId+"&report_ref="+zRepRef;
							}
							$("#embedDiv").load(loadSrc,fcrReadOnly);

						}
						
							if (data2['reportSchedCO']['USE_DEFAULT_MS_YN'] == 1) 
							{
								$("#USE_DEFAULT_MS_YN_"+_pageRef).attr('checked', true);
								liveSearch_makeReadOnly(true, "msHost_"+_pageRef);
							}
							else 
							{
								$("#USE_DEFAULT_MS_YN_"+_pageRef).attr('checked',  false);
								liveSearch_makeReadOnly(false, "msHost_"+_pageRef);
							}
					//to hide show the livesearches when double clicking on the grid	
					if($("#isFcrRep_"+_pageRef).is(':checked')==true)
					{
						chkBoxVal=1;
					}
					else
					{
						chkBoxVal=0;	
					}
					callDependency(	"isFcrRep_"+_pageRef+":reportSchedCO.IS_FCR_YN",
					jQuery.contextPath+'/path/scheduler/scheduler_showHideFcr.action',
					"reportSchedCO.IS_FCR_YN:'"+chkBoxVal+"',updates:'1',reportSchedCO.USE_DEFAULT_MS_YN:'"+data2['reportSchedCO']['USE_DEFAULT_MS_YN']+"'",
					"isFcrRep_"+_pageRef,
					"")				
					});
}

function fcrReadOnly()
{
	if($("#isFcrRep_"+_pageRef).is(':checked')==true)
	{
		$("input[id^= lookuptxt_p_param_]").attr('readonly', true);
	}
}
//clickmain
function mainGridClicked() {
	//hide the mail div
	showHideMailConfig("1", 0)
	showRepConfig(0);
	rowid = $("#schedGrid").jqGrid('getGridParam', 'selrow');
	$('#previousValue_' + _pageRef).val("");
	myObject = $("#schedGrid").jqGrid('getRowData', rowid);

	params = {};
	params["_pageRef"] = _pageRef;
	params["SCHED_ID"] = myObject["SCHED_VO.SCHED_ID"];
	var zSrc = jQuery.contextPath+"/path/scheduler/findSingleSchedule.action";
	$
			.post(
					zSrc,
					params,
					function(param) {
						$("#schedMaintDiv_" + _pageRef).html(param);
						frequencyChanged(0);
						schedTypeChanged($("#SCHED_TYPE_" + _pageRef).val(), 1);
						$("#modeSched").val("edit");
						$("#schedReportGrid")
								.jqGrid(
										'setGridParam',
										{
											url : jQuery.contextPath
													+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID="
													+ $("#SCHED_ID").val(),
											page : 1
										}).trigger("reloadGrid");
						emptyDetForm();
						//empty params form
						var loadSrc = jQuery.contextPath
								+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
						$("#embedDiv").load(loadSrc,$('#lookuptxt_BATCH_CODE_'+_pageRef).attr('readonly',true));

						//Remove Below Grid incase of Event Alert
						var eventId = $('#lookuptxt_EVT_ID_'+_pageRef).val();
						if(eventId !=null && eventId != "")
						{
							$("#allReportsGridDiv").attr("style","display:none");
							$("#lookuptxt_EVT_ID_"+_pageRef).attr("readonly",true);
						}
						else
						{
							$("#allReportsGridDiv").attr("style","display:inline");
						}
					}, "html");
}//end mainGridClicked

function mainSchedRetrieved(schedId) {
	//hide the mail div
	showHideMailConfig("1", 0)
	showRepConfig(0);

	params = {};
	params["_pageRef"] = _pageRef;
	params["SCHED_ID"] = schedId
	var zSrc = jQuery.contextPath+"/path/scheduler/findSingleSchedule.action";
	$
			.post(
					zSrc,
					params,
					function(param) {
						$("#schedMaintDiv_" + _pageRef).html(param);
						//Remove Below Grid incase of Event Alert
						var eventId = $('#lookuptxt_EVT_ID_'+_pageRef).val();
						if(eventId !=null && eventId != "")
						{
							$("#allReportsGridDiv").attr("style","display:none");
							$("#lookuptxt_EVT_ID_"+_pageRef).attr("readonly",true);
						}
						else
						{
							$("#allReportsGridDiv").attr("style","display:inline");
						}
						frequencyChanged(0);
						schedTypeChanged($("#SCHED_TYPE_" + _pageRef).val(), 1);
						$("#modeSched").val("edit");
						$("#schedReportGrid")
								.jqGrid(
										'setGridParam',
										{
											url : jQuery.contextPath
													+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID="
													+ $("#SCHED_ID").val(),
											page : 1
										}).trigger("reloadGrid");
						emptyDetForm();
						//empty params form
						var loadSrc = jQuery.contextPath
								+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
						$("#embedDiv").load(loadSrc,$('#lookuptxt_BATCH_CODE_'+_pageRef).attr('readonly',true));

					}, "html");
}

function addReportRec() {
	if ( !schedIdFilled() ) {
	_showErrorMsg(selectSched);
	return;
	}
showRepConfig(1);
emptyDetForm();

//empty params form
var loadSrc = jQuery.contextPath
		+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
$("#embedDiv").load(loadSrc);
//bbe
	var chkBoxVal;
	if($("#isFcrRep_"+_pageRef).is(':checked')==true)
	{
		chkBoxVal=1;	
	}
	else
	{
		chkBoxVal=0;	
	}
	callDependency(	"isFcrRep_"+_pageRef+":reportSchedCO.IS_FCR_YN",
					jQuery.contextPath+'/path/scheduler/scheduler_showHideFcr.action',
					"reportSchedCO.IS_FCR_YN:'"+chkBoxVal+"',updates:'1'",
					"isFcrRep_"+_pageRef,
					"")		
}
/*
  function addDetail() {
	var url = jQuery.contextPath + "/path/scheduler/scheduler_retUsrsLstCnt";
	var params = {};
	params["_pageRef"] = _pageRef;
	params["scheduleId"] = $("#SCHED_ID").val();
	params["reportId"] = $("#lookuptxt_REPORT_ID").val();
	$.post(url, params, function(param) {
		var usrsLstCnt = param["updates"];
		addRepSchedDetail(usrsLstCnt)

	});
}
*/
function addRepSchedDetail(usrsLstCnt) {

	if ($("#lookuptxt_REPORT_ID").val() == ""
			|| $("#lookuptxt_REPORT_ID").val() == "0") {
		_showErrorMsg(fillSchedReport);
		return;
	}
	if ($("SCHED_ID").val() == "" || $("#SCHED_ID").val() == "0") {
		_showErrorMsg(selectSched);
		return;
	}
	
	//if mail
	if ($("#SAVE_DATA_TYPE_" + _pageRef).val() == "3") {	
		//subject ,and body should not be empty
		if ($("#EMAIL_SUBJECT_" + _pageRef).val() == ""
				|| $("#EMAIL_BODY_" + _pageRef).val() == ""
				|| ($("#lookuptxt_msHost_" + _pageRef).val() == "" && $("#USE_DEFAULT_MS_YN_"+_pageRef).is(':checked')==false)
				|| $("#FROM_EMAIL_VAL_" + _pageRef).val() == "") {
			_showErrorMsg(missingBodySubj);
			return;
		}
		//to , cc or bcc should be filled
		if ($("#TO_EMAIL_VAL_" + _pageRef).val() == ""
				&& $("#CC_EMAIL_VAL_" + _pageRef).val() == ""
				&& $("#BCC_EMAIL_VAL_" + _pageRef).val() == ""
				&& usrsLstCnt == "0") {
			_showErrorMsg(missingToCcBcc);
			return;
		}
	}
	if ($("#SECURED_FILE_YN_" + _pageRef).val() == true) {
				if ($("#SECURED_PWD_FIELD_NAME_" + _pageRef).val() == "") {
			_showErrorMsg(missingPassword);
			return;
		}
	}
	//check if all the parameters are not empty
	var url = jQuery.contextPath+'/path/scheduler/scheduler_retParamIds';
	params = {};
	params["reportId"] = $("#lookuptxt_REPORT_ID").val();
	$
			.post(
					url,
					params,
					function(param) {
						var paramIds = param["updates"];
						var isEmpty = false;
						if (paramIds != null && paramIds != "") {
							var paramId;
							for ( var i = 0; i < paramIds.split(",").length; i++) {
								paramId = paramIds.split(",")[i];
								if (document.getElementById(paramId).value == "" || 
									   (document.getElementById(paramId).name.substring(0,6)=="paramH"
										&& document.getElementById(paramId).value=="{\"root\":[]}")) {
									isEmpty = true;
								}
							}
						}
						if (isEmpty == true) {
							_showErrorMsg(fillParamAlert)
							return;
						} else {

							if ($("#detMode").val() == "edit") {
								var repId = $("#lookuptxt_REPORT_ID").val()
								var reportRef=$("#lookuptxt_fcrRepId_"+_pageRef).val();
								var url1 = jQuery.contextPath
													+'/path/scheduler/scheduler_addReport.action?SCHED_ID='
										+ $("#SCHED_ID").val()
										+ "&_pageRef="
										+ _pageRef;
								$
										.post(
												url1,
												$("#frmReportsControls")
														.serialize()
														+ "&"
														+ $(
																"#frmSchedMail_"
																		+ _pageRef)
																.serialize(),
												function(param) {

													$("#schedReportGrid")
															.jqGrid(
																	'setGridParam',
																	{
																		url : jQuery.contextPath
													+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID="
																				+ $(
																						"#SCHED_ID")
																						.val(),
																		page : 1
																	})
															.trigger(
																	"reloadGrid");
													emptyDetForm();

													//get the repMenu and save the parameters
													var url = jQuery.contextPath
													+'/path/scheduler/scheduler_retProgRefByRepId';
													params = {};
													params["reportId"] = repId;
													$
															.post(
																	url,
																	params,
																	function(
																			param) {
																		var repMenu = param["updates"];
																		//update the parameters  
																		var url = jQuery.contextPath
																				+ "/path/scheduler/scheduler_updateSchedParams.action?schedId="
																				+ $(
																						"#SCHED_ID")
																						.val()
																				+ "&reportId="
																				+ repId;
																		myObject = $(
																				"#dynParamFrmId"+schedConstId
																						+ repMenu
																								.replace(
																										"-",
																										"_"))
																				.serialize();
																		myObject +="&updates="+reportRef;
																		$
																				.post(
																						url,
																						myObject,
																						function(
																								param) {
																							//empty params form
																							var loadSrc = jQuery.contextPath
																									+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
																							$(
																									"#embedDiv")
																									.load(
																											loadSrc);
																							var url1 = jQuery.contextPath
													+'/path/scheduler/scheduler_addSchedule.action';
     _showProgressBar(true);
     	$
			.ajax( {
				url : url1,
				type : "post",
				dataType : "json",
				data : $("#frmSchedulerControls").serialize(),
				success : function(param) {
					if (typeof param["_error"] == "undefined"
							|| param["_error"] == null ) {
						emptyDetForm();
						var schedId =$("#SCHED_ID").val();
						emptyMainForm();
						showRepConfig(0);
						$("#schedGrid").trigger("reloadGrid");
						$("#schedReportGrid")
								.jqGrid(
										'setGridParam',
										{
											url : jQuery.contextPath
													+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID=0",
											page : 1
										}).trigger("reloadGrid");
						mainSchedRetrieved(schedId);
										_showProgressBar(false);
											
					}
					

				}
			});
																						});
																	});
												});
							}//edit mode
							else {

								var zSrc = jQuery.contextPath
													+"/path/scheduler/scheduler_retRepSchedCount.action?SCHED_ID="
										+ $("#SCHED_ID").val()
										+ "&reportId="
										+ $("#lookuptxt_REPORT_ID").val()
										+ "&updates="+ $("#lookuptxt_fcrRepId_"+_pageRef).val();
								params = {};
								$
										.getJSON(
												zSrc,
												params,
												function(data2, status, xhr) {
													if (data2['reportId'] == "-1") {
														_showErrorMsg(schedRepExists);
														return;
													}

													//update the checkboxes values that are 'true' to 0 or 1 since their properties are of type BigDecimal

													var repId = $(
															"#lookuptxt_REPORT_ID")
															.val();
													var reportRef=$("#lookuptxt_fcrRepId_"+_pageRef).val();
													var url1 = jQuery.contextPath
													+'/path/scheduler/scheduler_addReport.action?SCHED_ID='
															+ $("#SCHED_ID")
																	.val()
															+ "&_pageRef="
															+ _pageRef;
													$
															.post(
																	url1,
																	$(
																			"#frmReportsControls")
																			.serializeForm()
																			+ "&"
																			+ $(
																					"#frmSchedMail_"
																							+ _pageRef)
																					.serialize(),
																	function(
																			param) {

																		$(
																				"#schedReportGrid")
																				.jqGrid(
																						'setGridParam',
																						{
																							url : jQuery.contextPath
													+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID="
																									+ $(
																											"#SCHED_ID")
																											.val(),
																							page : 1
																						})
																				.trigger(
																						"reloadGrid");
																		emptyDetForm();

																		//get the repMenu and save the parameters
																		var url = jQuery.contextPath
													+'/path/scheduler/scheduler_retProgRefByRepId';
																		params = {};
																		params["reportId"] = repId;
																		$
																				.post(
																						url,
																						params,
																						function(
																								param) {
																							var repMenu = param["updates"];
																							//save the parameters   
																							var url = jQuery.contextPath
																									+ "/path/scheduler/scheduler_saveSchedParams.action?schedId="
																									+ $(
																											"#SCHED_ID")
																											.val()
																									+ "&reportId="
																									+ repId;
																							myObject = $(
																									"#dynParamFrmId"+schedConstId
																											+ repMenu
																													.replace(
																															"-",
																															"_"))
																									.serialize();
																							myObject+="&updates="+reportRef;
																							$
																									.post(
																											url,
																											myObject,
																											function(
																													param) {
																												//empty params form
																												var loadSrc = jQuery.contextPath
																														+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
																												$(
																														"#embedDiv")
																														.load(
																																loadSrc);
																												var url1 = jQuery.contextPath
													+'/path/scheduler/scheduler_addSchedule.action';
     _showProgressBar(true);
     	$
			.ajax( {
				url : url1,
				type : "post",
				dataType : "json",
				data : $("#frmSchedulerControls").serialize(),
				success : function(param) {
					if (typeof param["_error"] == "undefined"
							|| param["_error"] == null ) {
						var schedId =$("#SCHED_ID").val();
						emptyMainForm();
						mainSchedRetrieved(schedId);				
						_showProgressBar(false);
											
					}
					

				}
			});
																											});

																						});

																	});
												});

							}//else (add new mode)

						}
		
					});
					
}
function onDetDelClicked(sel_row_index) {
	var args = {
		selRow : sel_row_index
	};
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteSchedReport", args);
}
function deleteSchedReport(confirmation, args) {
	var selRow = args.selRow;
	if (confirmation) {
		var repID = $("#schedReportGrid")
				.jqGrid('getCell', selRow, 'REPORT_ID');
		var schedID = $("#schedReportGrid").jqGrid('getCell', selRow,
				'SCHED_ID');
		var repRef=$("#schedReportGrid").jqGrid('getCell', selRow,
				'REPORT_REF');
		var url = jQuery.contextPath+'/path/scheduler/scheduler_deleteReportSchedule.action';
		var param = {};
		param["SCHED_ID"] = schedID;
		param["reportId"] = repID;
		param["_pageRef"] = _pageRef;
		param["REPORT_REF"]=repRef;
		$
				.get(
						url,
						param,
						function(param) {
							$("#schedReportGrid")
									.jqGrid(
											'setGridParam',
											{
												url : jQuery.contextPath+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID="
														+ schedID,
												page : 1
											}).trigger("reloadGrid");
							emptyDetForm();
							//empty params form
							var loadSrc = jQuery.contextPath
									+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
							$("#embedDiv").load(loadSrc);
						});
	}
}
function onMainDelClicked(sel_row_index) {
	var args = {
		selRow : sel_row_index
	};
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteSched", args);
}

function deleteSched(confirmation, args) {
	var selRow = args.selRow;
	if (confirmation) {
		var schedID = $("#schedGrid").jqGrid('getCell', selRow, 'SCHED_VO.SCHED_ID');
		var zSrc = jQuery.contextPath+"/path/scheduler/scheduler_checkSchedIsRunning.action?SCHED_ID="
				+ schedID;
		params = {};
		$
				.getJSON(
						zSrc,
						params,
						function(data2, status, xhr) {
							if (data2['reportId'] == "-1") {
								_showErrorMsg("<ps:text name='delSchec_msg_key' />");
								return;
							}
							var url = jQuery.contextPath+'/path/scheduler/scheduler_deleteSchedule.action?SCHED_ID=' + schedID;
							var param = {};
							param["_pageRef"] = _pageRef;
							$.get(url, param, function(param) {

								$("#schedGrid").trigger("reloadGrid");
								$("#schedReportGrid").trigger("reloadGrid");
								emptyDetForm();
								emptyMainForm();
								showRepConfig(0);	
							});
						});
	}
}
function requiredFieldsFilled() {
	return !($("#SCHED_NAME").val() == ""
			|| (($("#SCHED_EXPIRY_DATE").val() == "" || $("#FIRST_RUN_DATE")
			.val() == "")&& $("#SCHED_TYPE_"+_pageRef).val()==1));
}
function repIdFilled() {
 	return ($("#lookuptxt_REPORT_ID").val() !="" );
}
function schedIdFilled() {
 	return ( $("#SCHED_ID").val() != ""
			&& $("#SCHED_ID").val() != "0" );
}

//addmain
function addSchedule() {
	$('#previousValue_' + _pageRef).val("");
	$('#schedule_Id_' + _pageRef).val("");
emptyDetForm();
emptyMainForm();
showRepConfig(0);	
$("#schedReportGrid").jqGrid(
'setGridParam',
 {
 url : jQuery.contextPath+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID=-1",
 page : 1
 }).trigger("reloadGrid");
}
function saveSchedule() {
	setTimeout(function(){ $("#saveSchdBut_"+_pageRef).removeAttr("disabled"); }, 500);
	if (!requiredFieldsFilled()) {
		_showErrorMsg(fillSchedRequFe);
		return;
	}
	if (($("#REPETITION").val() == "E" || $("#REPETITION").val() == "M")
			&& !atLeastMonthSelected()) {
		_showErrorMsg(selectMonth);
		return;
	}

	if (($("#REPETITION").val() == "H" ) && !MissingHourMinutes()) {
		_showErrorMsg(fillhourminutes);
		return;
	}
	
	if ($("#REPETITION").val() == "D" && !atLeastDaySelected()) {
		_showErrorMsg(selectDay);
		return;
	}

	var dateCompare = compareDate("FIRST_RUN_DATE", "SCHED_EXPIRY_DATE");
	if ((dateCompare == 0 || dateCompare == 1)&&$("#SCHED_TYPE_"+_pageRef).val()==1) {
		_showErrorMsg(dateChecking);
		return;
	}
	
	//if the save data type is DB then empty the split grid
	if ($("#SAVE_DATA_TYPE_" + _pageRef).val() == "1")
		{
		//reload groupByFeList grid
		repSchedule_reConstructGroupByDiv();
		
		}
	

	var schedId = $("#SCHED_ID").val();
	if ( schedIdFilled() && repIdFilled()) 
	{ 
	var url = jQuery.contextPath + "/path/scheduler/scheduler_retUsrsLstCnt";
	var params = {};
	params["_pageRef"] = _pageRef;
	params["scheduleId"] = $("#SCHED_ID").val();
	params["reportId"] = $("#lookuptxt_REPORT_ID").val();
	$.post(url, params, function(param) {
		var usrsLstCnt = param["updates"];
		
		addRepSchedDetail(usrsLstCnt);
		return;

	});
	
		}
		else 
		{
			var url1 = jQuery.contextPath+'/path/scheduler/scheduler_addSchedule.action';

    _showProgressBar(true);
	$
			.ajax( {
				url : url1,
				type : "post",
				dataType : "json",
				data : $("#frmSchedulerControls").serialize(),
				success : function(param) {
					if (typeof param["_error"] == "undefined"
							|| param["_error"] == null ) {
						emptyDetForm();
						emptyMainForm();
						$("#schedGrid").trigger("reloadGrid");
						if (schedId != "" && schedId != "0" )
							{
							mainSchedRetrieved(schedId);
}
						else 
							{	
								$("#schedReportGrid")
								.jqGrid(
										'setGridParam',
										{
											url : jQuery.contextPath+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID=0",
											page : 1
										}).trigger("reloadGrid");
							}
					_showProgressBar(false);										
					}
				}
			});
		}	
}
function isPrintChanged() {
	if ($("#IS_PRINT").is(':checked') == true) {
		$("#printers").show();
	} else {
		$("#printers").hide();
	}
}
function securedChanged() {
	if ($("#SECURED_FILE_YN_" + _pageRef).is(':checked') == true) {
		$("#password").show();
		$("#passwordLabel").show();
	} else {
		$("#password").hide();
		$("#passwordLabel").hide();
	}
}
function frequencyChanged(caller) {
	if ($("#REPETITION").val() == "M" || $("#REPETITION").val() == "E") {
		$("#months").show();
		if (caller == 1)
			selectMonths();
		$("#days").hide();
		hideHours();
		clearDays();
   		clearHours();
	} else if ($("#REPETITION").val() == "D") {
		$("#days").show();
		if (caller == 1)
			selectDays();
		$("#months").hide();
		hideHours();
		clearMonths();
		clearHours();
	} 
	else if ($("#REPETITION").val() == "H")
	 {
		showHours();
		$("#months").hide();
		$("#days").hide();
		clearMonths();
		clearDays();
	}
	 else
	  {
		$("#days").hide();
		$("#months").hide();
		hideHours();
		clearDays();
		clearMonths();
		clearHours();
	}
}
function reportChanged() {
	if (!isNaN($("#lookuptxt_REPORT_ID").val()) && $("#SCHED_ID").val() != ""
			&& $("#SCHED_ID").val() != "0") {
		var zSrc = jQuery.contextPath+"/path/scheduler/scheduler_findReportByID.action?reportId="
				+ $("#lookuptxt_REPORT_ID").val();
		params = {};
		$
				.getJSON(zSrc,
						params,
						function(data2, status, xhr) {
							if (data2['report'] != null) {
								$("#REPORT_NAME").val(
										data2['report']['REPORT_NAME']);
								//load the parameter form
						var repId = $("#lookuptxt_REPORT_ID").val();
						var loadSrc = jQuery.contextPath
								+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code="
								+ repId;
						$("#embedDiv").load(loadSrc);
					} else {
						$("#lookuptxt_REPORT_ID").val("");
						$("#REPORT_NAME").val("");
						//empty params form
						var loadSrc = jQuery.contextPath
								+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
						$("#embedDiv").load(loadSrc);
					}

				});
	} else {
		_showErrorMsg(selectRepId);
		$("#lookuptxt_REPORT_ID").val("");
		$("#REPORT_NAME").val("");
	}
}
function selectMonths() {
	$("#chkJAN").attr('checked', true);
	$("#chkFEB").attr('checked', true);
	$("#chkMAR").attr('checked', true);
	$("#chkAPR").attr('checked', true);
	$("#chkMAY").attr('checked', true);
	$("#chkJUN").attr('checked', true);
	$("#chkJUL").attr('checked', true);
	$("#chkAUG").attr('checked', true);
	$("#chkSEP").attr('checked', true);
	$("#chkOCT").attr('checked', true);
	$("#chkNOV").attr('checked', true);
	$("#chkDEC").attr('checked', true);
}
function selectDays() {
	$("#chkMON").attr('checked', true);
	$("#chkTUE").attr('checked', true);
	$("#chkWED").attr('checked', true);
	$("#chkTHU").attr('checked', true);
	$("#chkFRI").attr('checked', true);
	$("#chkSAT").attr('checked', true);
	$("#chkSUN").attr('checked', true);
}
function clearMonths() {
	$("#chkJAN").attr('checked', false);
	$("#chkFEB").attr('checked', false);
	$("#chkMAR").attr('checked', false);
	$("#chkAPR").attr('checked', false);
	$("#chkMAY").attr('checked', false);
	$("#chkJUN").attr('checked', false);
	$("#chkJUL").attr('checked', false);
	$("#chkAUG").attr('checked', false);
	$("#chkSEP").attr('checked', false);
	$("#chkOCT").attr('checked', false);
	$("#chkNOV").attr('checked', false);
	$("#chkDEC").attr('checked', false);
}
function clearDays() {
	$("#chkMON").attr('checked', false);
	$("#chkTUE").attr('checked', false);
	$("#chkWED").attr('checked', false);
	$("#chkTHU").attr('checked', false);
	$("#chkFRI").attr('checked', false);
	$("#chkSAT").attr('checked', false);
	$("#chkSUN").attr('checked', false);
}

function clearHours(){
$("#FREQUENCY_HOUR").val("");
$("#FREQUENCY_MINUTE").val("");
}
function hideHours(){
document.getElementById("hoursLbl_" + _pageRef).style.display = "none";
document.getElementById("hoursVal_" + _pageRef).style.display = "none";
document.getElementById("minLbl_" + _pageRef).style.display = "none";
document.getElementById("minVal_" + _pageRef).style.display = "none";
}

function showHours()
{
document.getElementById("hoursLbl_" + _pageRef).style.display = "";
document.getElementById("hoursVal_" + _pageRef).style.display = "";
document.getElementById("minLbl_" + _pageRef).style.display = "";
document.getElementById("minVal_" + _pageRef).style.display = "";
}
function emptyDetForm() {
	$("#lookuptxt_REPORT_ID").val("");
	$("#REPORT_NAME").val("");
	$("#REPORT_FORMAT").val("N");
	$("#IS_ACTIVE").attr('checked', false);
	$("#IS_PRINT").attr('checked', false);
	$("#PRINTER_NAME").val($("#PRINTER_NAME option:first").val());
	$("#detMode").val("add");
	liveSearch_makeReadOnly(false, "REPORT_ID");
	liveSearch_makeReadOnly(false, "msHost_"+_pageRef);
	$("#USE_DEFAULT_MS_YN_"+_pageRef).attr('checked', false);
	$("#REPORT_NAME").attr('readonly', false);
	liveSearch_makeReadOnly(false, "fcrRepId_"+_pageRef);
	$("#fcrRepName_"+_pageRef).attr('readonly',false);
	$("#isFcrRep_"+_pageRef).removeAttr('disabled');
	$("#printers").hide();
	$("#SAVE_DATA_TYPE_" + _pageRef).val("1");
	$("#DATE_TYPE_" + _pageRef).val("0");
	$("#lookuptxt_fcrRepId_"+_pageRef).val("");
	$("#fcrRepName_"+_pageRef).val("");
	$("#isFcrRep_"+_pageRef).attr('checked',false);
	$("#SECURED_FILE_YN_" + _pageRef).attr('checked', false);
	$("#password").hide();
	$("#passwordLabel").hide();
	$("#splitFeDiv_"+_pageRef).css("visibility","hidden");
	applyDefaultSett();
	emptyDetMailForm();
}

function emptyDetMailForm(isEmptyCombos) {
	//empty inputs related to the sending mail
	$("#lookuptxt_msHost_" + _pageRef).val("");
	$("#msId_" + _pageRef).val("");
	$("#DEFAULT_FROM_MS_" + _pageRef).val("");
	$("#FROM_EMAIL_TYPE_" + _pageRef).val("1");

	$("#FROM_EMAIL_VAL_" + _pageRef).val("");
	$("#FROM_EMAIL_VAL_" + _pageRef).attr('readonly', true);
	document.getElementById("fromFeVal_" + _pageRef).style.display = "none";
	document.getElementById("fromVal_" + _pageRef).style.display = "inline";

	$("#TO_EMAIL_TYPE_" + _pageRef).val("1");
	$("#TO_EMAIL_VAL_" + _pageRef).val("");
	document.getElementById("toVal_" + _pageRef).style.display = "inline";
	document.getElementById("toFeVal_" + _pageRef).style.display = "none";
	document.getElementById("toCIFVal_" + _pageRef).style.display = "none";
	document.getElementById("toFeCompVal_" + _pageRef).style.display = "none";

	$("#CC_EMAIL_TYPE_" + _pageRef).val("1");
	$("#CC_EMAIL_VAL_" + _pageRef).val("");
	document.getElementById("ccVal_" + _pageRef).style.display = "inline";
	document.getElementById("ccFeVal_" + _pageRef).style.display = "none";
	document.getElementById("ccFeCompVal_" + _pageRef).style.display = "none";

	$("#BCC_EMAIL_TYPE_" + _pageRef).val("1");
	$("#BCC_EMAIL_VAL_" + _pageRef).val("");
	document.getElementById("bccVal_" + _pageRef).style.display = "inline";
	document.getElementById("bccFeVal_" + _pageRef).style.display = "none";
	document.getElementById("bccFeVal_" + _pageRef).style.display = "none";

	$("#EMAIL_SUBJECT_" + _pageRef).val("");
	$("#EMAIL_BODY_" + _pageRef).val("");
	$("#ATTACH_FILE_NAME_" + _pageRef).val("0");
	$("#SEND_IF_NO_DATA_YN_" + _pageRef).attr('checked', false);
	$("#PRINT_IF_NO_DATA_YN_" + _pageRef).attr('checked', false);
	$("#SECURED_FILE_YN_" + _pageRef).attr('checked', false);
	$("#SECURED_PWD_FIELD_NAME_" + _pageRef).val("");	
	$("#updates1_" + _pageRef).val("");
	$("#updates2_" + _pageRef).val("1");

	if (isEmptyCombos != 1) {
		//empty fields combos
		$('#FROM_EMAIL_FE_VAL_'+_pageRef+' option').each(
				function(index, option) {
					$(option).remove();
				});
		$('#TO_EMAIL_FE_VAL_'+_pageRef+' option').each(function(index, option) {
			$(option).remove();
		});
		$('#TO_EMAIL_CIF_VAL_'+_pageRef+' option').each(function(index, option) {
			$(option).remove();
		});
		$('#CC_EMAIL_FE_VAL_'+_pageRef+' option').each(function(index, option) {
			$(option).remove();
		});
		$('#BCC_EMAIL_FE_VAL_'+_pageRef+' option').each(function(index, option) {
			$(option).remove();
		});
		$('#ATTACH_FILE_NAME_'+_pageRef+' option').each(function(index, option) {
			$(option).remove();
		});
		
		//reload groupByFeList grid
		repSchedule_reConstructGroupByDiv();
	}

	//hide usersList grids
	hideUsersLstGrids();
	//reload usersList grids
	emptyUsersLstGrids();
	//hide mail div
	document.getElementById("mailConfigDiv_" + _pageRef).style.display = "none";
}

function applyDefaultSett() {
	//hide the csv separator
	checkSchedIfCSV("HTML");
	//set the html format as default
	$("#REPORT_FORMAT").val("HTML");
	document.getElementById("noHeadAndFoot_" + _pageRef).checked = false;
}

function atLeastDaySelected() {
	if (document.getElementById("chkMON").checked
			|| document.getElementById("chkTUE").checked
			|| document.getElementById("chkWED").checked
			|| document.getElementById("chkTHU").checked
			|| document.getElementById("chkFRI").checked
			|| document.getElementById("chkSAT").checked
			|| document.getElementById("chkSUN").checked)
		return true;
	return false;
}
function atLeastMonthSelected() {
	if (document.getElementById("chkJAN").checked
			|| document.getElementById("chkFEB").checked
			|| document.getElementById("chkMAR").checked
			|| document.getElementById("chkAPR").checked
			|| document.getElementById("chkMAY").checked
			|| document.getElementById("chkJUN").checked
			|| document.getElementById("chkJUL").checked
			|| document.getElementById("chkAUG").checked
			|| document.getElementById("chkSEP").checked
			|| document.getElementById("chkOCT").checked
			|| document.getElementById("chkNOV").checked
			|| document.getElementById("chkDEC").checked)
		return true;
	return false;
}

function MissingHourMinutes()
{
	if ($.trim($('#FREQUENCY_HOUR').val()) == ''
			|| $.trim($('#FREQUENCY_MINUTE').val()) == '') 
	{
		return false;
	}
	return true;
}

function emptyMainForm() {
	$("#SCHED_NAME").val("");
	$("#REPETITION").val("O");
	$("#SCHED_ID").val("0");
	$("#SCHED_EXPIRY_DATE").val("");
	$("#NEXT_RUN_DATE").val("");
	$("#FIRST_RUN_DATE").val("");
	$("#modeSched").val("add");
	$("#SCHED_TYPE_" + _pageRef).val("1");
	schedTypeChanged($("#SCHED_TYPE_" + _pageRef).val(), 0);
	clearDays();
	clearMonths();
	clearHours();
	$("#months").hide();
	$("#days").hide();
	hideHours();	
}

function loadParams(from) {
	//stop the loading if the fcr livesearch is visible
	if($("#lookuptxt_fcrRepId_"+_pageRef).val()!="" && from==1)
	{
		return;
	}
	var repId = $("#lookuptxt_REPORT_ID").val();
	if (repId != null && repId != "") {
	var loadSrc;
		if(from==1)
		{
			loadSrc = jQuery.contextPath
					+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code="
					+ repId;
		}
		else
		{
			loadSrc = jQuery.contextPath
					+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?updates="+$("#lookuptxt_fcrRepId_"+_pageRef).val()+"D00,"
					+$("#lookuptxt_REPORT_ID").val()+"&code=-2";
		}		
		$("#embedDiv").load(loadSrc,fcrReadOnly);
		//update related email inputs
		if ($("#FROM_EMAIL_TYPE_" + _pageRef).val() == "3") {
			$("#FROM_EMAIL_VAL_" + _pageRef).val(
					$("#FROM_EMAIL_FE_VAL_" + _pageRef).val());
		}
		if ($("#TO_EMAIL_TYPE_" + _pageRef).val() == "2") {
			$("#TO_EMAIL_VAL_" + _pageRef).val(
					$("#TO_EMAIL_FE_VAL_" + _pageRef).val());
		}
		if ($("#TO_EMAIL_TYPE_" + _pageRef).val() == "3") {
			$("#TO_EMAIL_VAL_" + _pageRef).val(
					$("#TO_EMAIL_CIF_VAL_" + _pageRef).val());
		}
		if ($("#CC_EMAIL_TYPE_" + _pageRef).val() == "2") {
			$("#CC_EMAIL_VAL_" + _pageRef).val(
					$("#CC_EMAIL_FE_VAL_" + _pageRef).val());
		}
		if ($("#BCC_EMAIL_TYPE_" + _pageRef).val() == "2") {
			$("#BCC_EMAIL_VAL_" + _pageRef).val(
					$("#BCC_EMAIL_FE_VAL_" + _pageRef).val());
		}
		//reload groupByFeList grid
		repSchedule_reConstructGroupByDiv($("#lookuptxt_REPORT_ID").val(),$("#SCHED_ID").val(),null,null);
		
		//reload the autocomplete in the body textarea based on the new report id 
		var url =jQuery.contextPath+"/path/scheduler/scheduler_retEmailBodyAutoCompleteFields.action?reportId="+$("#lookuptxt_REPORT_ID").val();
		$.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 	{
		 		autoCompleteEmailBody("EMAIL_BODY_"+_pageRef,(param["updates"]).split(","));
		 	}
   	    });		 
		
		
	} else {
		var loadSrc = jQuery.contextPath
				+ "/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1";
		$("#embedDiv").load(loadSrc);
		//update related email inputs
		if ($("#FROM_EMAIL_TYPE_" + _pageRef).val() == "3") {
			$("#FROM_EMAIL_VAL_" + _pageRef).val("");
		}
		if ($("#TO_EMAIL_TYPE_" + _pageRef).val() == "2"
				|| $("#TO_EMAIL_TYPE_" + _pageRef).val() == "3") {
			$("#TO_EMAIL_VAL_" + _pageRef).val("");
		}
		if ($("#CC_EMAIL_TYPE_" + _pageRef).val() == "2") {
			$("#CC_EMAIL_VAL_" + _pageRef).val("");
		}
		if ($("#BCC_EMAIL_TYPE_" + _pageRef).val() == "2") {
			$("#BCC_EMAIL_VAL_" + _pageRef).val("");
		}

		//reload groupByFeList grid
		repSchedule_reConstructGroupByDiv();
		
		//reload the autocomplete in the body textarea based on the new report id 
		autoCompleteEmailBody("EMAIL_BODY_"+_pageRef,"");
	}

}

function checkSchedIfCSV(repFormat) {
	document.getElementById("noHeadAndFootLbl_"+_pageRef).innerHTML=""+repSchedNoHeadAndFoot;
	document.getElementById("noHeadAndFootLblSpan_"+_pageRef).style.display="inline";
	document.getElementById("noHeadAndFootSpan_"+_pageRef).style.display="inline";	
	if(repFormat == "RDTXT" || repFormat == "RDXLS")
	{
		document.getElementById("noHeadAndFootLbl_"+_pageRef).innerHTML=""+repSchedNoHeaders;
	}
	if (repFormat == "CSV" || repFormat=="RDTXT") {
		document.getElementById("sepLblTd_" + _pageRef).style.display = "inline";
		document.getElementById("sepValTd_" + _pageRef).style.display = "inline";
	} else {
		document.getElementById("sepLblTd_" + _pageRef).style.display = "none";
		document.getElementById("sepValTd_" + _pageRef).style.display = "none";
	}
	if(repFormat=="SQL")
	{
		document.getElementById("noHeadAndFootLblSpan_"+_pageRef).style.display="none";
		document.getElementById("noHeadAndFootSpan_"+_pageRef).style.display="none";
	}
	if(repFormat=="PDF")
	{	$("#SECURED_FILE_YN_" + _pageRef).show();
								$("#password").show();
								$("#passwordLabel").show();
								$("#securedLabel").show();
	}
	else
		{$("#SECURED_FILE_YN_" + _pageRef).hide();
			$("#password").hide();
			$("#passwordLabel").hide();
			$("#securedLabel").hide();
			$("#SECURED_PWD_FIELD_NAME_"+_pageRef).val("");
			$("#SECURED_FILE_YN_" + _pageRef).attr('checked', false);
		
		}
}

function showHideMailConfig(val, isEmptyMailForm) {
	if (val == "3") {
		document.getElementById("mailConfigDiv_" + _pageRef).style.display = "inline";
		$("#splitFeDiv_"+_pageRef).css("visibility","visible");
	}
	else {
		if(val=="2")
		{
			$("#splitFeDiv_"+_pageRef).css("visibility","visible");
		}
		else
		{
			$("#splitFeDiv_"+_pageRef).css("visibility","hidden");
		}
		document.getElementById("mailConfigDiv_" + _pageRef).style.display = "none";
		//emptyDetMail form
		if (isEmptyMailForm == 1) {
			emptyDetMailForm(1);
		}
	}
}

function showRepConfig(val) {
	if (val == "1") {
		document.getElementById("allReportsGridDiv_" + _pageRef).style.display = "inline";
	} else {
		document.getElementById("allReportsGridDiv_" + _pageRef).style.display = "none";
	}
}

function hideShowFromVal(val) {
	document.getElementById("fromVal_" + _pageRef).style.display = "inline";
	document.getElementById("fromFeVal_" + _pageRef).style.display = "inline";
	$("#FROM_EMAIL_VAL_" + _pageRef).val("");
	$("#FROM_EMAIL_VAL_" + _pageRef).attr('readonly', false);
	if (val == "1") {
		document.getElementById("fromFeVal_" + _pageRef).style.display = "none";
		$("#FROM_EMAIL_VAL_" + _pageRef).attr('readonly', true);
		$("#FROM_EMAIL_VAL_" + _pageRef).val(
				$("#DEFAULT_FROM_MS_" + _pageRef).val());
	} else if (val == "2") {
		document.getElementById("fromFeVal_" + _pageRef).style.display = "none";
	} else if (val == "3") {
		document.getElementById("fromVal_" + _pageRef).style.display = "none";
		$("#FROM_EMAIL_VAL_" + _pageRef).val(
				$("#FROM_EMAIL_FE_VAL_" + _pageRef).val());
	}
}

function fillFromMailVal() {
	$("#FROM_EMAIL_VAL_" + _pageRef).val(
			$("#FROM_EMAIL_FE_VAL_" + _pageRef).val());
}

function fillDefaultMS() {
	var fromType = $("#FROM_EMAIL_TYPE_" + _pageRef).val();
	if (fromType == "1") {
		$("#FROM_EMAIL_VAL_" + _pageRef).val(
				$("#DEFAULT_FROM_MS_" + _pageRef).val());
	}
}

function hideShowToVal(val) {
	var prevToType = $("#updates2_" + _pageRef).val();
	var prevToVal = $("#TO_EMAIL_VAL_" + _pageRef).val();

	document.getElementById("toVal_" + _pageRef).style.display = "inline";
	document.getElementById("toFeVal_" + _pageRef).style.display = "inline";
	document.getElementById("toCIFVal_" + _pageRef).style.display = "inline";
	document.getElementById("toFeCompVal_" + _pageRef).style.display = "inline";
	$("#TO_EMAIL_VAL_" + _pageRef).val("");
	if (val == "1") {
		document.getElementById("toFeVal_" + _pageRef).style.display = "none";
		document.getElementById("toCIFVal_" + _pageRef).style.display = "none";
		document.getElementById("toFeCompVal_" + _pageRef).style.display = "none";
		//remove cif field from the grpByList
		if (prevToType == "3") {
			deleteGrpByCifField(prevToVal)
		}
	} else if (val == "2") {
		document.getElementById("toVal_" + _pageRef).style.display = "none";
		document.getElementById("toCIFVal_" + _pageRef).style.display = "none";
		document.getElementById("toFeCompVal_" + _pageRef).style.display = "none";
		$("#TO_EMAIL_VAL_" + _pageRef).val(
				$("#TO_EMAIL_FE_VAL_" + _pageRef).val());
		//remove cif field from the grpByList
		if (prevToType == "3") {
			deleteGrpByCifField(prevToVal)
		}
	} else if (val == "3") {
		document.getElementById("toVal_" + _pageRef).style.display = "none";
		document.getElementById("toFeVal_" + _pageRef).style.display = "none";
		document.getElementById("toFeCompVal_" + _pageRef).style.display = "none";
		$("#TO_EMAIL_VAL_" + _pageRef).val(
				$("#TO_EMAIL_CIF_VAL_" + _pageRef).val());

		//add cif to grpBy
		addGrpByCifField();
	}
	else if(val=="4")
	{
		document.getElementById("toFeVal_" + _pageRef).style.display = "none";
		document.getElementById("toCIFVal_" + _pageRef).style.display = "none";
		document.getElementById("toVal_" + _pageRef).style.display = "none";
		$("#TO_EMAIL_VAL_" + _pageRef).val($("#TO_EMAIL_FECOMP_VAL_" + _pageRef).val());
		if (prevToType == "3")
		{
			deleteGrpByCifField(prevToVal)
		}
	}

	$("#updates2_" + _pageRef).val(val)

}

function addGrpByCifField() {
	if (document.getElementById('TO_EMAIL_CIF_VAL_' + _pageRef).value != "") {
		var toCifVal = document.getElementById('TO_EMAIL_CIF_VAL_' + _pageRef).options[document
				.getElementById('TO_EMAIL_CIF_VAL_' + _pageRef).selectedIndex].text;
		var url = jQuery.contextPath+"/path/scheduler/scheduler_addCIFMailFeGroupBy.action";
		myObject = {};
		myObject["updates"] = $("#TO_EMAIL_VAL_" + _pageRef).val();
		myObject["updates2"] = toCifVal;
		myObject["_pageRef"] = _pageRef;
		myObject["scheduleId"] = $("#SCHED_ID").val();
		myObject["reportId"] = $("#lookuptxt_REPORT_ID").val();
		$
				.post(url,
						myObject,
						function(param) {
							//reload groupByFeList grid
					repSchedule_reConstructGroupByDiv( $("#lookuptxt_REPORT_ID").val(), $("#SCHED_ID").val(),null,"1");
					});
	}

}

function deleteGrpByCifField(prevToVal) {
	var url = jQuery.contextPath+"/path/scheduler/scheduler_deleteMailFeGroupBy.action";
	myObject = {};
	myObject["updates"] = prevToVal;
	myObject["_pageRef"] = _pageRef;
	$
			.post(url,
					myObject,
					function(param) {
						//reload groupByFeList grid
						repSchedule_reConstructGroupByDiv( $("#lookuptxt_REPORT_ID").val(), $("#SCHED_ID").val(),null,"1");
				});
}

function fillToMailVal() {
	$("#TO_EMAIL_VAL_" + _pageRef).val($("#TO_EMAIL_FE_VAL_" + _pageRef).val());
}

function fillCompToMailVal() {
	$("#TO_EMAIL_VAL_" + _pageRef).val($("#TO_EMAIL_FECOMP_VAL_" + _pageRef).val());
}

function fillToCIFVal() {

	prevToVal = $("#TO_EMAIL_VAL_" + _pageRef).val();
	$("#TO_EMAIL_VAL_" + _pageRef)
			.val($("#TO_EMAIL_CIF_VAL_" + _pageRef).val());
	//delete preview cif field from the grpByFields
	deleteGrpByCifField(prevToVal);
	//add the new field
	addGrpByCifField();
}

function hideShowCcVal(val) {
	document.getElementById("ccVal_" + _pageRef).style.display = "inline";
	document.getElementById("ccFeVal_" + _pageRef).style.display = "inline";
	document.getElementById("ccFeCompVal_" + _pageRef).style.display = "inline";
	$("#CC_EMAIL_VAL_" + _pageRef).val("");
	if (val == "1") 
	{
		document.getElementById("ccFeVal_" + _pageRef).style.display = "none";
		document.getElementById("ccFeCompVal_" + _pageRef).style.display = "none";
	}
	else if (val == "2") 
	{
		document.getElementById("ccVal_" + _pageRef).style.display = "none";
		document.getElementById("ccFeCompVal_" + _pageRef).style.display = "none";
		$("#CC_EMAIL_VAL_" + _pageRef).val($("#CC_EMAIL_FE_VAL_" + _pageRef).val());
	}
	else if (val == "3")
	{
		document.getElementById("ccVal_" + _pageRef).style.display = "none";
		document.getElementById("ccFeVal_" + _pageRef).style.display = "none";
		$("#CC_EMAIL_VAL_" + _pageRef).val($("#CC_EMAIL_FECOMP_VAL_" + _pageRef).val());
	}
}

function fillCCMailVal() {
	$("#CC_EMAIL_VAL_" + _pageRef).val($("#CC_EMAIL_FE_VAL_" + _pageRef).val());
}

function fillCompCCMailVal() {
	$("#CC_EMAIL_VAL_" + _pageRef).val($("#CC_EMAIL_FECOMP_VAL_" + _pageRef).val());
}

function hideShowBccVal(val) {
	document.getElementById("bccVal_" + _pageRef).style.display = "inline";
	document.getElementById("bccFeVal_" + _pageRef).style.display = "inline";
	document.getElementById("bccFeCompVal_" + _pageRef).style.display = "inline";
	$("#BCC_EMAIL_VAL_" + _pageRef).val("");
	if (val == "1") {
		document.getElementById("bccFeVal_" + _pageRef).style.display = "none";
		document.getElementById("bccFeCompVal_" + _pageRef).style.display = "none";
	} else if (val == "2") {
		document.getElementById("bccVal_" + _pageRef).style.display = "none";
		document.getElementById("bccFeCompVal_" + _pageRef).style.display = "none";
		$("#BCC_EMAIL_VAL_" + _pageRef).val(
				$("#BCC_EMAIL_FE_VAL_" + _pageRef).val());
	}
	else if (val == "3")
	{
		document.getElementById("bccVal_" + _pageRef).style.display = "none";
		document.getElementById("bccFeVal_" + _pageRef).style.display = "none";
		$("#BCC_EMAIL_VAL_" + _pageRef).val($("#BCC_EMAIL_FECOMP_VAL_" + _pageRef).val());
	}
	$("#SECURED_PWD_FIELD_NAME_" + _pageRef).val(
				$("#BCC_EMAIL_FE_VAL_" + _pageRef).val());
}

function fillBCCMailVal() {
	$("#BCC_EMAIL_VAL_" + _pageRef).val(
			$("#BCC_EMAIL_FE_VAL_" + _pageRef).val());
}

function fillBCCCompMailVal() {
	$("#BCC_EMAIL_VAL_" + _pageRef).val(
			$("#BCC_EMAIL_FECOMP_VAL_" + _pageRef).val());
}

function addMailFeGrpBy() {
	var repId = $("#lookuptxt_REPORT_ID").val();
	var schedId = $("#SCHED_ID").val();
	if (repId == "" || schedId == "") {
		_showErrorMsg(selectRepId,error_msg_title, 300, 100);
	} else {
		//save previous if exists
		var jsonStringUpdates = $("#mailFeGrpByGrid_" + _pageRef).jqGrid(
				'getAllRows');
		$("#updates1_" + _pageRef).val(jsonStringUpdates);
		var url = jQuery.contextPath+"/path/scheduler/scheduler_addMailFeGroupBy.action?_pageRef="
				+ _pageRef;
		myObject = $("#frmSchedMailGrid_" + _pageRef).serialize();
		myObject += "&reportId=" + repId + "&scheduleId=" + schedId;
		$
				.post(url,
						myObject,
						function(param) {
							//reload groupByFeList grid
							repSchedule_reConstructGroupByDiv( $("#lookuptxt_REPORT_ID").val(), $("#SCHED_ID").val(),null,"1");

						window.setTimeout("delayFuncMailGrp()", 1000);
					});

	}
}

function delayFuncMailGrp() {
	var repId = $("#lookuptxt_REPORT_ID").val();
	//add new row
	grpCmbUrl = jQuery.contextPath+"/path/scheduler/scheduler_loadMailFeGroupByCmb.action?_pageRef="
			+ _pageRef + "&reportId=" + repId;
	$("#mailFeGrpByGrid_" + _pageRef).jqGrid('addInlineRow', {});
}

function fillGrpByFeAlias() {
	var rowId = $("#mailFeGrpByGrid_" + _pageRef).jqGrid("getGridParam",
			'selrow');
	var feAlias = $("#mailFeGrpByGrid_" + _pageRef).jqGrid('getCell', rowId,
			'FIELD_DESC');
	$("#mailFeGrpByGrid_" + _pageRef).jqGrid('setCell', rowId,
			"mailGrpVO.FIELD_ALIAS", feAlias)
}

function deleteMailFeGrpBy() {

	_showConfirmMsg(
			deleteConfirm,
			deleteTitle,
			function(confirmcChoice, theArgs) {
				if (confirmcChoice) {
					var rowId = $("#mailFeGrpByGrid_" + _pageRef).jqGrid(
							"getGridParam", 'selrow');
					var feAlias = $("#mailFeGrpByGrid_" + _pageRef).jqGrid(
							'getCell', rowId, 'mailGrpVO.FIELD_ALIAS');

					var url = jQuery.contextPath+"/path/scheduler/scheduler_deleteMailFeGroupBy.action?";
					myObject = {};
					myObject["_pageRef"] = _pageRef;
					myObject["updates"] = feAlias;
					$
							.post(
									url,
									myObject,
									function(param) {
										repSchedule_reConstructGroupByDiv( $("#lookuptxt_REPORT_ID").val(), $("#SCHED_ID").val(),null,"1");
									});
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
}

function showToUsersList() {
	if ($("#toUsersLisGridtDiv_" + _pageRef).is(":empty")) {
		$('#toUsersLisGridtDiv_' + _pageRef)
				.load(
						jQuery.contextPath+"/path/scheduler/scheduler_loadSchedMailUsers.action?_pageRef="
								+ _pageRef
								+ "&updates=1&reportId="
								+ $("#lookuptxt_REPORT_ID").val()
								+ "&scheduleId=" + $("#SCHED_ID").val());
	}
}

function showCcUsersList() {
	if ($("#ccUsersLisGridtDiv_" + _pageRef).is(":empty")) {
		$('#ccUsersLisGridtDiv_' + _pageRef)
				.load(
						jQuery.contextPath+"/path/scheduler/scheduler_loadSchedMailUsers.action?_pageRef="
								+ _pageRef
								+ "&updates=2&reportId="
								+ $("#lookuptxt_REPORT_ID").val()
								+ "&scheduleId=" + $("#SCHED_ID").val());
	}
}

function showBccUsersList() {
	if ($("#bccUsersLisGridtDiv_" + _pageRef).is(":empty")) {
		$('#bccUsersLisGridtDiv_' + _pageRef)
				.load(
						jQuery.contextPath+"/path/scheduler/scheduler_loadSchedMailUsers.action?_pageRef="
								+ _pageRef
								+ "&updates=3&reportId="
								+ $("#lookuptxt_REPORT_ID").val()
								+ "&scheduleId=" + $("#SCHED_ID").val());
	}
}

function emptyUsersLstGrids() {
	$('#toUsersLisGridtDiv_' + _pageRef).html("");
	$('#ccUsersLisGridtDiv_' + _pageRef).html("");
	$('#bccUsersLisGridtDiv_' + _pageRef).html("");

}

function hideUsersLstGrids() {
	var collapseDiv = $(
			"#toUsersListDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
	if (!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
		$(collapseDiv).trigger("click");
	}

	var collapseDiv = $(
			"#ccUsersListDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
	if (!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
		$(collapseDiv).trigger("click");
	}
	var collapseDiv = $(
			"#bccUsersListDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
	if (!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
		$(collapseDiv).trigger("click");
	}

}

function hideShowDateDB() {
	var fileName = $("#ATTACH_FILE_NAME_" + _pageRef).val();
	if (fileName == "0") {
		document.getElementById("dateTypeLbl_" + _pageRef).style.display = "inline";
		document.getElementById("dateTypeVal_" + _pageRef).style.display = "inline";
	} else {
		document.getElementById("dateTypeLbl_" + _pageRef).style.display = "none";
		document.getElementById("dateTypeVal_" + _pageRef).style.display = "none";
	}
}

function schedTypeChanged(val, emptyDates) 
{
	var SCHED_ID = $('#schedule_Id_'+ _pageRef).val();
	var previousValue =  $('#previousValue_' + _pageRef).val();
	if(SCHED_ID != null && SCHED_ID != "" && val=="6" && previousValue != "6")
	{
		$('#SCHED_TYPE_' + _pageRef).val(previousValue);
		_showErrorMsg(cannotModifySchedule, error_msg_title, 300,100);
		return;
	}
	else
	{
		$('#previousValue_' + _pageRef).val(val);
	}
	document.getElementById("frdLlb_" + _pageRef).style.display = "inline";
	document.getElementById("frdVal_" + _pageRef).style.display = "inline";
	document.getElementById("edLbl_" + _pageRef).style.display = "inline";
	document.getElementById("edVal_" + _pageRef).style.display = "inline";
	document.getElementById("nrdLbl_" + _pageRef).style.display = "inline";
	document.getElementById("nrdVal_" + _pageRef).style.display = "inline";
	document.getElementById("execButton_"+ _pageRef).style.display = "none" ;
	/*if (val == "2" || val == "3") {
		document.getElementById("REPETITION").style.display = "none" ;
		document.getElementById("repLbl_" + _pageRef).style.display = "none";
		$("#lookupdiv_BATCH_CODE_"+_pageRef).attr("style","display:inline;");
		$("#BATCH_BRIEF_NAME_"+_pageRef).attr("style","display:inline");
		$("#batchCodeLbl_"+_pageRef).attr("style","display:inline");		
		$("#FIRST_RUN_DATE").val("31/12/2099 12:00 AM");
		$("#SCHED_EXPIRY_DATE").val("31/12/2099 12:00 AM");
		$("#NEXT_RUN_DATE").val("31/12/2099 12:00 AM");
		$("#REPETITION").val("O");
		hideDateFields();
		clearDays();
		clearMonths();
		clearHours();
		$("#months").hide();
		$("#days").hide();
		hideHours();	
	} 
	else*/ if ((val == "1" || val=="5" || val=="6") && emptyDates == 0) 
	{
		document.getElementById("REPETITION").style.display = "inline";
		document.getElementById("repLbl_" + _pageRef).style.display = "inline";
		$("#lookupdiv_BATCH_CODE_"+_pageRef).attr("style","display:none");
		$("#BATCH_BRIEF_NAME_"+_pageRef).attr("style","display:none");
		$("#batchCodeLbl_"+_pageRef).attr("style","display:none");	
		$("#SCHED_EXPIRY_DATE").val("");
		$("#NEXT_RUN_DATE").val("");
		$("#FIRST_RUN_DATE").val("");
		$("#REPETITION").val("O");
		$("#lookuptxt_BATCH_CODE_"+_pageRef).attr("required",false);
		$("#lookuptxt_BATCH_CODE_"+_pageRef).val("");
		$("#BATCH_BRIEF_NAME_"+_pageRef).val("");
		//Added for Alert Event
		$("#lookupdiv_EVT_ID_"+_pageRef).attr("style","display:none");
		$("#EVT_BRIEF_NAME_"+_pageRef).attr("style","display:none");
		$("#eventIdLbl_"+_pageRef).attr("style","display:none");
		$("#lookuptxt_EVT_ID_"+_pageRef).attr("required",false);
		$("#lookuptxt_EVT_ID_"+_pageRef).val("");
		$("#EVT_BRIEF_NAME_"+_pageRef).val("");
		$("#allReportsGridDiv").attr("style","display:inline");
		
		if(val=="5")
		{
			$("#lookupdiv_BATCH_CODE_"+_pageRef).attr("style","display:inline;");
			$("#lookuptxt_BATCH_CODE_"+_pageRef).val("");
			$("#lookuptxt_BATCH_CODE_"+_pageRef).attr("required",true);
			$("#BATCH_BRIEF_NAME_"+_pageRef).attr("style","display:inline");
			$("#BATCH_BRIEF_NAME_"+_pageRef).val("");
			$("#batchCodeLbl_"+_pageRef).attr("style","display:inline");
			$("#lookuptxt_BATCH_CODE_"+_pageRef).attr("readonly",true);
			//Added for Alert Event
			$("#lookupdiv_EVT_ID_"+_pageRef).attr("style","display:none");
			$("#EVT_BRIEF_NAME_"+_pageRef).attr("style","display:none");
			$("#eventIdLbl_"+_pageRef).attr("style","display:none");	
			$("#lookuptxt_EVT_ID_"+_pageRef).attr("required",false);
			$("#lookuptxt_EVT_ID_"+_pageRef).val("");
			$("#EVT_BRIEF_NAME_"+_pageRef).val("");
			
			$("#allReportsGridDiv").attr("style","display:inline");
		}
		else if(val=="6")
		{
			//Added for Alert Event
			$("#lookupdiv_EVT_ID_"+_pageRef).attr("style","display:inline;");
			$("#lookuptxt_EVT_ID_"+_pageRef).val("");
			$("#lookuptxt_EVT_ID_"+_pageRef).attr("required",true);
			$("#EVT_BRIEF_NAME_"+_pageRef).attr("style","display:inline");
			$("#EVT_BRIEF_NAME_"+_pageRef).val("");
			$("#eventIdLbl_"+_pageRef).attr("style","display:inline");
			$("#lookuptxt_EVT_ID_"+_pageRef).attr("readonly",true);
			
			$("#lookupdiv_BATCH_CODE_"+_pageRef).attr("style","display:none");
			$("#BATCH_BRIEF_NAME_"+_pageRef).attr("style","display:none");
			$("#batchCodeLbl_"+_pageRef).attr("style","display:none");	
			$("#lookuptxt_BATCH_CODE_"+_pageRef).attr("required",false);
			$("#lookuptxt_BATCH_CODE_"+_pageRef).val("");
			$("#BATCH_BRIEF_NAME_"+_pageRef).val("");
			
			$("#allReportsGridDiv").attr("style","display:none");
		}
	}
	else if(val=="4")
	{	
		document.getElementById("REPETITION").style.display = "none" ;
		document.getElementById("repLbl_" + _pageRef).style.display = "none";
		document.getElementById("nrdLbl_" + _pageRef).style.display = "none";
		document.getElementById("nrdVal_" + _pageRef).style.display = "none";
		document.getElementById("execButton_"+ _pageRef).style.display = "inline" ;
		$("#FIRST_RUN_DATE").val("31/12/2099 12:00 AM");
		$("#SCHED_EXPIRY_DATE").val("");
		$("#NEXT_RUN_DATE").val("");
		$("#REPETITION").val("O");
		hideDateFields();
		clearDays();
		clearMonths();
		clearHours();
		$("#months").hide();
		$("#days").hide();
		hideHours();
		$("#lookupdiv_BATCH_CODE_"+_pageRef).attr("style","display:none");
		$("#BATCH_BRIEF_NAME_"+_pageRef).attr("style","display:none");
		$("#batchCodeLbl_"+_pageRef).attr("style","display:none");	
		$("#lookuptxt_BATCH_CODE_"+_pageRef).attr("required",false);
		$("#lookuptxt_BATCH_CODE_"+_pageRef).val("");
		$("#BATCH_BRIEF_NAME_"+_pageRef).val("");
		//Added for Alert Event
		$("#lookupdiv_EVT_ID_"+_pageRef).attr("style","display:none");
		$("#EVT_BRIEF_NAME_"+_pageRef).attr("style","display:none");
		$("#eventIdLbl_"+_pageRef).attr("style","display:none");	
		$("#lookuptxt_EVT_ID_"+_pageRef).attr("required",false);
		$("#lookuptxt_EVT_ID_"+_pageRef).val("");
		$("#EVT_BRIEF_NAME_"+_pageRef).val("");
		
		$("#allReportsGridDiv").attr("style","display:inline");
	}

	else if(val=="")
	{	document.getElementById("REPETITION").style.display = "none" ;
		document.getElementById("repLbl_" + _pageRef).style.display = "none";
		clearDays();
		clearMonths();
		clearHours();
		$("#months").hide();
		$("#days").hide();
		hideHours();	
	}

}
function hideDateFields()
{
		document.getElementById("frdLlb_" + _pageRef).style.display = "none";
		document.getElementById("frdVal_" + _pageRef).style.display = "none";
		document.getElementById("edLbl_" + _pageRef).style.display = "none";
		document.getElementById("edVal_" + _pageRef).style.display = "none";
}
function checkMinuteValue(val)
{
		if(val>=60)
		{
			$("#FREQUENCY_MINUTE").val("");
			_showErrorMsg(sched.checkminvalue,error_msg_title,250,100);
		}
}

function schedReadyFunc()
{	$("#mailFeGrpByGrid_" + _pageRef).subscribe('constructGrpComboUrl',function(event, data) {
			var repId = $("#lookuptxt_REPORT_ID").val();
			rowid = (event["originalEvent"])["id"];
			var rowObject = $("#mailFeGrpByGrid_" + _pageRef).jqGrid("getRowData", rowid);
			grpCmbUrl = jQuery.contextPath+"/path/scheduler/scheduler_loadMailFeGroupByCmb.action?_pageRef="+_pageRef + "&reportId="+repId;
			});	
		$("#schedGrid").subscribe('emptySchedTrx',function(event, data) {
							$("#frmSchedulerControls #auditTrxNbr_" + _pageRef).val("");
							$("#frmSchedulerControls #auditObj_" + _pageRef).val("");
							emptyDetForm();
							 emptyMainForm();
							 showRepConfig(0);	
$("#schedReportGrid").jqGrid(
'setGridParam',
 {
 url : jQuery.contextPath+"/path/scheduler/scheduler_loadSchedReportGrid.action?SCHED_ID=-1",
 page : 1
 }).trigger("reloadGrid");
						});
	
	$("#updates2_" + _pageRef).val("1");
	hideDateFields();
	$("#NEXT_RUN_DATE").val("31/12/2099 12:00 AM");
	$("#FIRST_RUN_DATE").val("31/12/2099 12:00 AM");
	$("#SCHED_EXPIRY_DATE").val("31/12/2099 12:00 AM");
	document.getElementById("execButton_"+ _pageRef).style.display = "none" ;
	//hide the mail div
	document.getElementById("mailConfigDiv_" + _pageRef).style.display = "none";
	document.getElementById("allReportsGridDiv_" + _pageRef).style.display = "none";				
	$("#toUsersListDiv_" + _pageRef + " .collapsibleContainerTitle")
			.click(showToUsersList);
	
	$("#ccUsersListDiv_" + _pageRef + " .collapsibleContainerTitle")
			.click(showCcUsersList);
	$(
			"#bccUsersListDiv_" + _pageRef
	+ " .collapsibleContainerTitle").click(
			showBccUsersList);
	applyDefaultSett();
	$("#lookuptxt_msHost_" + _pageRef).attr('readonly', true);
	$("#months").hide();
	$("#days").hide();
	hideHours();
	document.getElementById("REPETITION").style.display = "none" ;
	document.getElementById("repLbl_" + _pageRef).style.display = "none";	
	$("#printers").hide();
	$("#password").hide();
	$("#passwordLabel").hide();
	callDependency(	"isFcrRep_"+_pageRef+":reportSchedCO.IS_FCR_YN",
	jQuery.contextPath+'/path/scheduler/scheduler_showHideFcr.action',
	"reportSchedCO.IS_FCR_YN:0",
	"isFcrRep_"+_pageRef,
	"")		
	$("#lookuptxt_BATCH_CODE_"+_pageRef).attr("readonly",true);
	$("#lookuptxt_EVT_ID_"+_pageRef).attr("readonly",true);
} 

function executeSchedule()
{
	_showProgressBar(true);
	var url = jQuery.contextPath +"/path/scheduler/scheduler_executeSchedule.action"
	params = {};
	params["schedId"] =  $("#SCHED_ID").val();
	params["_pageRef"]=_pageRef;
	//Empty form
	$.post(url, params , function( param )
 	{
			if (typeof param["_error"] == "undefined"|| param["_error"] == null) 
			{	_showErrorMsg(executeSuccess,info_msg_title, 300, 100);
				_showProgressBar(false)
			}
			else 
			{	
				_showErrorMsg(executeFailed,error_msg_title, 300, 100);
				_showProgressBar(false)
			}
		
 	},"html");
 	_showProgressBar(false);

}


/**this function will reconstuct the group by list gird in order to reload the combo box of the fields which is only reconstructed on grid construction**/
function repSchedule_reConstructGroupByDiv(zRepId,zSchedId,updates, updates2)
{
	grpCmbUrl = jQuery.contextPath+"/path/scheduler/scheduler_loadMailFeGroupByCmb.action?_pageRef="
	+ _pageRef + "&reportId=" + zRepId;
	var url = jQuery.contextPath+ "/path/scheduler/scheduler_loadSchedGroupBy";
	var params = {};
	params["_pageRef"] = _pageRef;
	params["reportId"] = zRepId;
	params["scheduleId"] = zSchedId;
	params["updates"] = updates;
	params["updates2"] = updates2;
	$.post(url, params, function(param) {
		$("#splitFeDiv_" + _pageRef).html(param);
	}, "html");
	
}