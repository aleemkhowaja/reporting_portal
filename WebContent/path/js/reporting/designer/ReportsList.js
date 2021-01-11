function deleteReport() {
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteReports", {},
			yes_confirm, no_confirm, 300, 100);
}

function deleteReports(confirmation) {
	if (confirmation) {
		var rows = $("#reportsGrid").jqGrid('getGridParam', 'selarrrow');
		var arrId;
		var arrProgRef;
		var arrOldId;
		var arrAppsName;
		var found = false;
		if (rows) {
			arrId = new Array(rows.length);
			arrProgRef = new Array(rows.length);
			arrOldId = new Array(rows.length);
			arrAppsName = new Array(rows.length);

			var j = 0;
			jQuery.each(rows, function(i, val) {
				var data = $("#reportsGrid").jqGrid('getRowData', val);
				if (data.REPORT_ID == $('#reportId').val()) {
					_showErrorMsg("<ps:text name='delReport_msg_key'/>");
					found = true;
					return false;
				}

				arrAppsName[j] = data.APP_NAME;
				arrOldId[j] = data.OLD_REPORT_ID;
				arrId[j] = data.REPORT_ID;
				arrProgRef[j] = data.PROG_REF + "," + data.PROG_REF + "D,"
						+ data.PROG_REF + "M," + data.PROG_REF + "SV,"
						+ data.PROG_REF + "SA," + data.PROG_REF + "SM,"
						+ data.PROG_REF + "SC," + data.PROG_REF + "PR";
				j++;
			});

			if (found)
				return;
			else {
				//check if the report is used in the hyperlinks section then return a message to the user or does not have entry in opt
				var url = jQuery.contextPath+"/path/designer/reportsList_checkIfDeleteReport.action"
				var params = {};
				params["reportsId"] = arrId;
				params["appsName"] = arrAppsName;
				params["progRefs"] = arrProgRef;
				$
						.post(
								url,
								params,
								function(param) {
									var isUsed = param["updates"];
									var isSubRep = param["updates1"];
									var isSched = param["updates2"];
									var isAccess = param["accessStr"];
									var metadataRep = param["update"];
									if (isAccess != "") {
										_showErrorMsg(delRepAccessAlert + "  "
												+ isAccess, info_msg_title,
												350, 120);
									} else {
										if (isUsed == "" && isSubRep == ""
												&& isSched == ""  && metadataRep=="") {
											$
													.ajax( {
														url : jQuery.contextPath+'/path/designer/reportsList_delete.action?_pageRef=' + _pageRef,
														type : "POST",
														data : ( {
															reportsId : arrId,
															progRefs : arrProgRef,
															oldReportsId : arrOldId,
															appsName : arrAppsName
														}),
														success : function(xml) {
															$("#reportsGrid")
																	.trigger(
																			"reloadGrid");
															//empty trx input
															$(
																	"#repListFrm_"
																			+ _pageRef
																			+ " #auditTrxNbr_"
																			+ _pageRef)
																	.val("")
															//reload the menu
															ddaccordion
																	.initRoot(
																			"appMenu",
																			"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
																			false);
														}
													});
										} else {
											if (metadataRep != "")
											{
												_showErrorMsg(repBeingMetadata + "  "
														+ metadataRep, info_msg_title, 350, 120);
											}
											if (isUsed != "") {
												_showErrorMsg(repHypUsage
														+ "  " + isUsed);
											}
											if (isSubRep != "") {
												_showErrorMsg(subRepUsage
														+ "  " + isSubRep);
											}
											if (isSched != "") {
												_showErrorMsg(schedRepUsage
														+ "  " + isSched);
											}
										}
									}
								});
			}
		}
	}
}

var xhtml;

function checkEditorLoading()
{
	if($("#editorDiv").html() == null)
	{
		$("#editorLoadedFrom_"+_pageRef).val("1");
		$("#imgDiv").load(jQuery.contextPath+'/path/designer/reportDesign_loadEditor.action?_pageRef=' + _pageRef)
	}
	else
	{
		loadReport();
	}
}

function loadReport() 
{
	_showProgressBar(true);
	var row = $("#reportsGrid").jqGrid('getGridParam', 'selarrrow');

	if (row.length == 0) {
		_showErrorMsg("<ps:text name='selReport_msg_key'/>");_showProgressBar(false);
	} else if (row.length > 1) {
		_showErrorMsg("<ps:text name='multiRepSel_msg_key'/>");_showProgressBar(false);
	} else {
		//check if the user has access to update the report
		var progRef = $("#reportsGrid").jqGrid('getCell', row, 'PROG_REF');
		var appName = $("#reportsGrid").jqGrid('getCell', row, 'APP_NAME');

		var url = jQuery.contextPath+"/path/designer/reportsList_checkUpdateReportAccess.action"
		var params = {};
		params["updates"] = appName;
		params["updates1"] = progRef;
		params["updates2"] = "M";//update
		var reportId = $("#reportsGrid").jqGrid('getCell',row,'REPORT_ID');
		params["repCO.REPORT_ID"]=reportId;
		$
				.post(
						url,
						params,
						function(param) {
							var isAccess = param["accessStr"];
							var isEditable = param["editableStr"];
							if (isAccess == "N") {
								_showErrorMsg(repUpdateAccessAlert,
										info_msg_title, 350, 120);
							}
							
	
							$
									.ajax( {
										url : jQuery.contextPath+'/path/designer/reportDesign_loadReport.action?_pageRef=' + _pageRef,
										type : "POST",
										data : "reportId=" + reportId,
										success : function(json) {
											xhtml = json['XHTML'];
											if (typeof param["_error"] == "undefined"
													|| param["_error"] == null) {
												/*$("#imgDiv").css("display",
														"none");
												$("#designerDiv").css(
														"display", "inline");
												*/
												$('#queriesDiv')
														.load(
																jQuery.contextPath+"/loadQueriesMenu.action");

												setTimeout(
														function() {
															$("#reportId")
																	.attr(
																			"value",
																			json['reportCO']['REPORT_ID']);
															
												CKEDITOR.instances.editor1.setData(xhtml);
												//CKEDITOR.instances.editor1.resize('95%', 625);
												cancelEvents();
												configCommands();
												
												$('#zDialog').dialog('close');
														}, 500);
											}
											if (isEditable == "N")
											{
												_showErrorMsg(repNotEditable, info_msg_title, 350, 120);
											}
											_showProgressBar(false);
										}
									});
						});			
	}
	return false;
}

function repPreAuditCall() {
	var row = $("#reportsGrid").jqGrid('getGridParam', 'selarrrow');
	if (row.length > 1 || row.length == 0) {
		_showErrorMsg('<ps:text name="selReport_msg_key"/>');
		return false;
	} else {
		var zRowId = $("#reportsGrid").jqGrid('getGridParam', 'selrow');
		var url = jQuery.contextPath + "/path/designer/reportsList_retTrxNb";
		myObject = $("#reportsGrid").jqGrid('getRowData', zRowId);
		params = {};
		paramStr = JSON.stringify(myObject)
		paramStr = "{" + "repCO:" + paramStr + "}";
		params["updates"] = paramStr;
		params["_pageRef"] = _pageRef
		$.post(url, params, function(param) {
			//setTrxAudit
				var auditTrxNbr_val = param["auditTrxNbr"];
				$("#repListFrm_" + _pageRef + " #auditTrxNbr_" + _pageRef).val(
						auditTrxNbr_val)
				//open audit dialog
				$.__overlaybox( {
					height : 600,
					href : jQuery.contextPath
							+ "/path/audit/audit_showAuditReport?progRef="
							+ _pageRef + "&trxNbr="
							+ $("#auditTrxNbr_" + _pageRef).val()
				});

			});
		return false;
	}
}