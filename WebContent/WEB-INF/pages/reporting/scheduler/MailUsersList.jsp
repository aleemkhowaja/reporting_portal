<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>

<ps:set name="allUsrsTitle_var" 		value="%{getEscText('sch.allUsrsTitle')}"/>
<ps:set name="loadAllUsrsMsg_var" 		value="%{getEscText('sch.loadAllUsrsMsg')}"/>

<script type="text/javascript">
var allUsrsTitle 		= '<ps:property value="allUsrsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var loadAllUsrsMsg 		= '<ps:property value="loadAllUsrsMsg_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document)
		.ready(
				function() {
					$("#mailUsersLstGrid_" + _pageRef + "_${updates}")
							.subscribe(
									'addLoadAllUsrsBtn',
									function(event, data) {
										var pagerId = "mailUsersLstGrid_"
												+ _pageRef
												+ "_${updates}_pager_left";
										var myGrid = $("#mailUsersLstGrid_"
												+ _pageRef + "_${updates}");
										myGrid
												.jqGrid(
														'navButtonAdd',
														pagerId,
														{
															caption : "",
															title : allUsrsTitle,
															id : "NewButton_"
																	+ _pageRef,
															buttonicon : 'ui-icon-circle-plus',
															onClickButton : function() {
																_showConfirmMsg(
																		loadAllUsrsMsg,
																		allUsrsTitle,
																		function(
																				confirmcChoice,
																				theArgs) {
																			if (confirmcChoice) {
																				var repId = $(
																						"#lookuptxt_REPORT_ID")
																						.val();
																				var schedId = $(
																						"#SCHED_ID")
																						.val();
																				var url = "${pageContext.request.contextPath}/path/scheduler/scheduler_loadAllUsers.action";
																				var myObject = {};
																				myObject["_pageRef"] = _pageRef;
																				myObject["reportId"] = repId;
																				myObject["scheduleId"] = schedId;
																				myObject["updates"] = "${updates}";
																				$
																						.post(
																								url,
																								myObject,
																								function(
																										param) {
																									//reload  grid
																									$(
																											"#mailUsersLstGrid_"
																													+ _pageRef
																													+ "_${updates}")
																											.jqGrid(
																													'setGridParam',
																													{
																														url : "${pageContext.request.contextPath}/path/scheduler/scheduler_loadSchedMailUsersList.action?reportId="
																																+ repId
																																+ "&scheduleId="
																																+ schedId
																																+ "&_pageRef="
																																+ _pageRef
																																+ "&updates=${updates}",
																														page : 1
																													})
																											.trigger(
																													"reloadGrid");
																								});

																			}
																		}, {});
															}
														});

									});

				});

function addMailSchedUser(reciepType) {
	//save previous if exists
	var repId = $("#lookuptxt_REPORT_ID").val();
	var schedId = $("#SCHED_ID").val();

	var jsonStringUpdates = $(
			"#mailUsersLstGrid_" + _pageRef + "_" + reciepType).jqGrid(
			'getAllRows');
	$("#updates1_" + _pageRef).val(jsonStringUpdates);
	var url = "${pageContext.request.contextPath}/path/scheduler/scheduler_addMailUser.action?_pageRef="
			+ _pageRef;
	myObject = $("#frmSchedMailGrid_" + _pageRef).serialize();
	myObject += "&reportId=" + repId + "&scheduleId=" + schedId + "&updates="
			+ reciepType;

	$
			.post(url,
					myObject,
					function(param) {
						//reload  grid
					$("#mailUsersLstGrid_" + _pageRef + "_" + reciepType)
							.jqGrid(
									'setGridParam',
									{
										url : "${pageContext.request.contextPath}/path/scheduler/scheduler_loadSchedMailUsersList.action?reportId="
												+ repId
												+ "&scheduleId="
												+ schedId
												+ "&_pageRef="
												+ _pageRef
												+ "&updates="
												+ reciepType,
										page : 1
									}).trigger("reloadGrid");

					window.setTimeout("delayFuncMailUsr(" + reciepType + ")",
							1000);
				});
}

function deleteMailSchedUser(reciepType) {
	_showConfirmMsg(
			deleteConfirm,
			deleteTitle,
			function(confirmcChoice, theArgs) {
				if (confirmcChoice) {
					var rowId = $(
							"#mailUsersLstGrid_" + _pageRef + "_" + reciepType)
							.jqGrid("getGridParam", 'selrow');
					var usrId = $(
							"#mailUsersLstGrid_" + _pageRef + "_" + reciepType)
							.jqGrid('getCell', rowId, 'USER_ID');
					var repId = $("#lookuptxt_REPORT_ID").val();
					var schedId = $("#SCHED_ID").val();

					var url = "${pageContext.request.contextPath}/path/scheduler/scheduler_deleteMailUser.action?";
					myObject = {};
					myObject["_pageRef"] = _pageRef;
					myObject["updates"] = usrId;
					myObject["updates1"] = reciepType;

					$
							.post(
									url,
									myObject,
									function(param) {
										$(
												"#mailUsersLstGrid_" + _pageRef
														+ "_" + reciepType)
												.jqGrid(
														'setGridParam',
														{
															url : "${pageContext.request.contextPath}/path/scheduler/scheduler_loadSchedMailUsersList.action?reportId="
																	+ repId
																	+ "&scheduleId="
																	+ schedId
																	+ "&_pageRef="
																	+ _pageRef
																	+ "&updates="
																	+ reciepType,
															page : 1
														})
												.trigger("reloadGrid");
									});
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
}

function delayFuncMailUsr(val) {
	var repId = $("#lookuptxt_REPORT_ID").val();
	var schedId = $("#SCHED_ID").val();
	$("#mailUsersLstGrid_" + _pageRef + "_" + val).jqGrid('addInlineRow', {
		SCHED_ID : schedId,
		REPORT_ID : repId,
		RECEIVER_TYPE : val
	});
}
</script>


<ps:url id="urlmailUsersLstGrid"
	action="scheduler_loadSchedMailUsersList" namespace="/path/scheduler"
	escapeAmp="false">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
	<ps:param name="updates" value="updates"></ps:param>
	<ps:param name="scheduleId" value="scheduleId"></ps:param>
	<ps:param name="reportId" value="reportId"></ps:param>
</ps:url>
<psjg:grid id="mailUsersLstGrid_${_pageRef}_${updates}" dataType="json"
	href="%{urlmailUsersLstGrid}" gridModel="gridModel" pager="true"
	rowNum="3" delfunc="deleteMailSchedUser(${updates})"
	addfunc="addMailSchedUser(${updates})" rowList="5,10,15,20"
	viewrecords="true" navigator="true" navigatorAdd="true"
	navigatorSearch="false" navigatorEdit="false" editinline="true"
	editurl="%{urlmailUsersLstGrid}"
	onGridCompleteTopics="addLoadAllUsrsBtn">
	<psjg:gridColumn id="USER_ID" index="USER_ID" name="USER_ID"
		align="center" editoptions="{ readonly: 'readonly'}"
		title="%{getText('ms.userName')}" editable="true" sortable="false"
		colType="liveSearch" searchElement="USER_ID"
		dataAction="${pageContext.request.contextPath}/path/scheduler/scheduler_mailUsersListLkp.action" />

	<psjg:gridColumn name="SCHED_ID" index="SCHED_ID" colType="number"
		title="SCHED_ID" editable="true" sortable="false" search="false"
		hidden="true" />
	<psjg:gridColumn name="REPORT_ID" index="REPORT_ID" colType="number"
		title="REPORT_ID" editable="true" sortable="false" search="false"
		hidden="true" />
	<psjg:gridColumn name="RECEIVER_TYPE" index="RECEIVER_TYPE"
		colType="text" title="RECEIVER_TYPE" editable="true" sortable="false"
		search="false" hidden="true" />
</psjg:grid>