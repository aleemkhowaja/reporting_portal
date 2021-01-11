<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="runningStr_var" 	value="%{getEscText('reporting.running')}"/>

<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>
<script type="text/javascript">
var runningStr 		= '<ps:property value="runningStr_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document)
		.ready(
				function() {
					$("#lblCurrentStatus").attr('readonly', true);

					$("#schedEngGrid").subscribe(
							'emptySchedEngTrx',
							function(event, data) {
								$(
										"#frmSchedulerEngine #auditTrxNbr_"
												+ _pageRef).val("");
							});
					$("#schedEngGrid")
							.subscribe(
									'retSchedEngTrx',
									function(event, data) {
										var rowid = (event["originalEvent"])["id"];
										var url = jQuery.contextPath
												+ "/path/scheduler/schedulerEngine_retSchedEngTrx";
										myObject = $("#schedEngGrid").jqGrid(
												'getRowData', rowid);
										params = {};
										paramStr = JSON.stringify(myObject)
										paramStr = "{" + "schedLogCO:"
												+ paramStr + "}";
										params["updates"] = paramStr;
										params["_pageRef"] = _pageRef
										$
												.get(
														url,
														params,
														function(param) {
															var trxAudit = param["auditTrxNbr"];
															$(
																	"#frmSchedulerEngine #auditTrxNbr_"
																			+ _pageRef)
																	.val(
																			trxAudit)
														});
									});

				});
function startEngine() {
	var url = '${pageContext.request.contextPath}/path/scheduler/schedulerEngine_startEngine.action';
	params = {};
	$.ajax( {
		url : url,
		type : "post",
		dataType : "json",
		data : params,
		success : function(param) 
		{
			if (typeof param["_error"] == "undefined"
					|| param["_error"] == null) {
				$("#lblCurrentStatus").val(param['lblCurrentStatus']);
				$("#strtBtn_" + _pageRef).addClass('ui-state-disabled');
			}
		}
	});
	return false;
}
function stopEngine() {
	var url = '${pageContext.request.contextPath}/path/scheduler/schedulerEngine_stopEngine.action';
	params = {};
	$.getJSON(url, params, function(data2, status, xhr) {
		$("#lblCurrentStatus").val(data2['lblCurrentStatus']);
		$("#strtBtn_" + _pageRef).removeClass('ui-state-disabled');
	});
	return false;
}

function viewReport(param) {
	var sel_row_index = param.substring(4, param.length);
	//added the connection id
	var connId = $("#schedEngGrid").jqGrid('getCell', sel_row_index, 'CONN_ID');
	var startDate = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'START_DATE_STR1');
	var repFormat = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'REP_FORMAT');
	var schedDate = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'SCHED_DATE_STR1');
	var repId = $("#schedEngGrid")
			.jqGrid('getCell', sel_row_index, 'REPORT_ID');
	var schedId = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'SCHED_ID');
	var progRef = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'PROG_REF');
	var IS_DB = $("#schedEngGrid").jqGrid('getCell', sel_row_index, 'IS_DB');
	var snpId = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'SNAPSHOT_ID');
	var FILE_NAME = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'FILE_NAME');
	var SAVE_DATA_TYPE = $("#schedEngGrid").jqGrid('getCell', sel_row_index,
			'SAVE_DATA_TYPE');
	if (SAVE_DATA_TYPE == "3") {
		//show and fill the mailLogDetails grid
		document.getElementById("mailLogDetDiv_" + _pageRef).style.display = "inline";
		$("#mailLogDetGridId_" + _pageRef)
				.jqGrid(
						'setGridParam',
						{
							url : "${pageContext.request.contextPath}/path/scheduler/schedulerMailLogDet_loadMailLogDet.action?repId="
									+ repId
									+ "&schedId="
									+ schedId
									+ "&startDate="
									+ startDate
									+ "&schedDt="
									+ schedDate,
							page : 1
						}).trigger("reloadGrid");

	} else {

		//hide and empty  the mailLogDetails grid
		document.getElementById("mailLogDetDiv_" + _pageRef).style.display = "none";
		$("#mailLogDetGridId_" + _pageRef)
				.jqGrid(
						'setGridParam',
						{
							url : "${pageContext.request.contextPath}/path/scheduler/schedulerMailLogDet_loadMailLogDet.action",
							page : 1
						}).trigger("reloadGrid");

		var urlSched = document.getElementById("previewSchedRep").action;
		if (urlSched.indexOf("&repFormat=") == -1) {
			document.getElementById("previewSchedRep").action += "?progRef="
					+ progRef + "&repFormat=" + repFormat + "&startDate="
					+ startDate + "&_pageRef=" + _pageRef + "&repId=" + repId
					+ "&schedId=" + schedId + "&schedDt=" + schedDate
					+ "&IS_DB=" + IS_DB + "&snpId=" + snpId + "&FILE_NAME="
					+ FILE_NAME+ "&CONN_ID="+connId;
		} else {
			urlSched = urlSched.substring(0, urlSched.indexOf("?progRef="));
			document.getElementById("previewSchedRep").action = urlSched
					+ "?progRef=" + progRef + "&repFormat=" + repFormat
					+ "&startDate=" + startDate + "&_pageRef=" + _pageRef
					+ "&repId=" + repId + "&schedId=" + schedId + "&schedDt="
					+ schedDate + "&IS_DB=" + IS_DB + "&snpId=" + snpId
					+ "&FILE_NAME=" + FILE_NAME+ "&CONN_ID="+connId;
		}
		//document.getElementById("previewSchedRep").submit();
		submitEncryptedData("previewSchedRep");
	}
}
</script>


<br />
<br />
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<div id="title"
	style="font-family: Tahoma; font-size: 14pt; text-align: center">
</div>
<br>

<ps:form id="frmSchedulerEngine" useHiddenProps="true">
	<ps:hidden name="reportName" value="%{jasperFileName}" />


	<table width="100%">
		<tr>

			<td>
				<div style="text-align: center;">
					<table>
						<tr>
							<td>
								<ps:label value="%{getText('reporting.currentStatus')}"
									cssStyle="text-align:center;font: bold 8pt Tahoma !important" />
							</td>
							<td>
								<ps:textfield id="lblCurrentStatus" name="lblCurrentStatus"
									size="95"
									cssStyle="text-align:center;font: bold 8pt Tahoma !important">
								</ps:textfield>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<div>
		<ps:url var="urlGrid" action="schedulerEngine_loadRunningScheds"
			namespace="/path/scheduler"></ps:url>
		<psjg:grid id="schedEngGrid" gridModel="gridModel"
			caption="%{getText('sch.runningSchedules')}" dataType="json"
			href="%{urlGrid}" pager="true" navigator="true"
			navigatorSearch="true"
			navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
			navigatorRefresh="true" viewrecords="true" rowNum="20"
			onSelectRowTopics="retSchedEngTrx" rowList="5,10,15,20"
			onCompleteTopics="emptySchedEngTrx" sortable="true">

			<psjg:gridColumn name="IS_DB" id="IS_DB" title="IS_DB"
				colType="number" editable="false" sortable="true" index="IS_DB"
				hidden="true" />
			<psjg:gridColumn name="SNAPSHOT_ID" id="SNAPSHOT_ID"
				title="SNAPSHOT_ID" colType="number" editable="false"
				sortable="true" index="SNAPSHOT_ID" hidden="true" />				
			<psjg:gridColumn name="SCHED_NAME" id="SCHED_NAME"
				title="%{getText('sch.name')}" colType="text" editable="false"
				sortable="true" index="SCHED_NAME"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
			<psjg:gridColumn name="REPORT_NAME" id="REPORT_NAME" width="70"
				title="%{getText('reportName')}" colType="text" editable="false"
				sortable="true" index="REPORT_NAME"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
			<psjg:gridColumn name="START_DATE_STR" id="START_DATE_STR"
				width="100" title="%{getText('sch.startDate')}" colType="text"
				editable="false" sortable="true" index="START_DATE_STR"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
			<psjg:gridColumn name="STATUS_DESC" id="STATUS_DESC" width="70"
				title="%{getText('sch.status')}" colType="text" editable="false"
				sortable="true" index="STATUS_DESC"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
			<psjg:gridColumn name="REMARKS" id="REMARKS" width="250"
				title="%{getText('sch.remarks')}" colType="text" editable="false"
				sortable="true" index="REMARKS"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
			<psjg:gridColumn name="REPORT_FORMAT_TRANS" id="REP_FORMAT" width="70"
				title="%{getText('reportFormat')}" colType="text" editable="false" sortable="true"
				index="REPORT_FORMAT_TRANS" hidden="false" />
				
			<psjg:gridColumn name="PROG_REF" id="PROG_REF" title="PROG_REF"
				colType="text" editable="false" sortable="true" index="PROG_REF"
				hidden="true" />
			<psjg:gridColumn name="START_DATE_STR1" id="START_DATE_STR1"
				title="START_DATE_STR1" colType="text" editable="false"
				sortable="true" index="START_DATE_STR1" hidden="true" />

			<psjg:gridColumn name="SAVE_DATA_TYPE_STR" id="SAVE_DATA_TYPE_STR"
				width="100" title="%{getText('sch.saveDataType')}" colType="text"
				editable="false" sortable="true" index="SAVE_DATA_TYPE_STR" />

			<psjg:gridColumn name="PREVIEW" index="PREVIEW"
				title="%{getText('snapShot.preview')}" colType="text"
				editable="false" sortable="false" search="false"
				formatter="showlink"
				formatoptions="{baseLinkUrl: 'javascript:',showAction:'viewReport(\"',addParam:'\");'}" />


			<psjg:gridColumn name="SAVE_DATA_TYPE" id="SAVE_DATA_TYPE"
				title="SAVE_DATA_TYPE" colType="number" editable="false"
				sortable="false" index="SAVE_DATA_TYPE" hidden="true" />
			<psjg:gridColumn name="REPORT_ID" id="REPORT_ID" title="REPORT_ID"
				colType="number" editable="false" sortable="false" index="REPORT_ID"
				hidden="true" />
			<psjg:gridColumn name="SCHED_ID" id="SCHED_ID" title="SCHED_ID"
				colType="number" editable="false" sortable="false" index="SCHED_ID"
				hidden="true" />
			<psjg:gridColumn name="START_DATE" id="START_DATE" title="START_DATE"
				colType="date" editable="false" sortable="false" index="START_DATE"
				hidden="true" />
			<psjg:gridColumn name="SCHED_DATE_STR1" id="SCHED_DATE_STR1"
				title="SCHED_DATE_STR1" colType="text" editable="false"
				sortable="false" index="SCHED_DATE_STR1" hidden="true" />
			<psjg:gridColumn name="SCHEDULED_DATE" id="SCHEDULED_DATE"
				title="SCHEDULED_DATE" colType="date" editable="false"
				sortable="false" index="SCHEDULED_DATE" hidden="true" />
			<psjg:gridColumn name="FILE_NAME" id="FILE_NAME" title="FILE_NAME"
				colType="text" editable="false" sortable="false" index="FILE_NAME"
				hidden="true" />
			<psjg:gridColumn name="REP_FORMAT" id="REP_FORMAT" width="50"
				title="Format" colType="text" editable="false" sortable="true"
				index="REP_FORMAT" hidden="true" />
			<psjg:gridColumn name="CONN_ID" id="CONN_ID" title="CONN_ID"
				colType="text" editable="false" sortable="false" index="CONN_ID"
				hidden="true" />
		</psjg:grid>
	</div>
	<br></br>
	<div>
		<table>
			<tr>
				<td style="text-align: center;">
					<psj:submit type="button" onclick="return startEngine()"
						button="true" id="strtBtn_${_pageRef}">
						<ps:text name="reporting.startEngine"></ps:text>
					</psj:submit>
				</td>
				<td style="text-align: center;">
					<psj:submit type="button" onclick="return stopEngine()"
						button="true">
						<ps:text name="reporting.stopEngine"></ps:text>
					</psj:submit>
				</td>
			</tr>
		</table>
	</div>
	<br></br>
	<div id="mailLogDetDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
		<ps:url id="urlMailLogDetGrid"
			action="schedulerMailLogDet_loadMailLogDet.action"
			namespace="/path/scheduler" />
		<psjg:grid id="mailLogDetGridId_${_pageRef}" dataType="json"
			href="%{urlMailLogDetGrid}" gridModel="gridModel" pager="true"
			rowNum="5" rowList="5,10,15,20" viewrecords="true" navigator="true"
			navigatorAdd="false" navigatorEdit="false" navigatorDelete="false"
			navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}">

			<psjg:gridColumn name="logDetVO.FROM_EMAIL_VAL" id="FROM_EMAIL_VAL"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="FROM_EMAIL_VAL" colType="text" title="%{getText('ms.from')}"
				sortable="true" width="100" />

			<psjg:gridColumn name="RECEIVER_TYPE_STR" id="RECEIVER_TYPE_STR"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="RECEIVER_TYPE_STR" colType="text"
				title="%{getText('sch.receiverType')}" sortable="true" width="50" />

			<psjg:gridColumn name="logDetVO.START_DATE" id="logDetVO.START_DATE"
				title="%{getText('sentdatekey')}" colType="datetime" editable="false" formatoptions="{srcformat:'Y-m-d H:i:s.u',newformat:'d/m/Y H:i:s' }"
				sortable="false" index="logDetVO.START_DATE" />

			<psjg:gridColumn name="logDetVO.RECEIVER_EMAIL_VAL"
				id="RECEIVER_EMAIL_VAL"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="RECEIVER_EMAIL_VAL" colType="text"
				title="%{getText('sch.receiver')}" sortable="true" width="100" />

			<psjg:gridColumn name="STATUS_DESC" id="STATUS_DESC"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="STATUS_DESC" colType="text" title="%{getText('sch.status')}"
				sortable="true" width="50" />
				
			<psjg:gridColumn name="logDetVO.EMAIL_SUBJECT" id="EMAIL_SUBJECT"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="EMAIL_SUBJECT" colType="text" title="%{getText('email_subject')}"
				sortable="true" width="170" />
				
			<psjg:gridColumn name="logDetVO.CIF_NO" id="CIF_NO"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="CIF_NO" colType="number" title="%{getText('arg_to_cifno')}"
				sortable="true" width="170" />

			<psjg:gridColumn name="logDetVO.REMARKS" id="REMARKS"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="REMARKS" colType="text" title="%{getText('sch.remarks')}"
				sortable="true" width="170" />

			<psjg:gridColumn name="logDetVO.STATUS" id="STATUS"
				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
				index="STATUS" colType="text" title="%{getText('sch.status')}"
				sortable="true" hidden="true" />

		</psjg:grid>

	</div>
</ps:form>
<ps:form id="previewSchedRep" namespace="/path/scheduler"
	action="schedulerEngine_previewSchedReport.action" target="_blank">
</ps:form>
<script type="text/javascript">
if ($("#lblCurrentStatus").val() == runningStr) {
	$("#strtBtn_" + _pageRef).addClass('ui-state-disabled');
}

document.getElementById("mailLogDetDiv_" + _pageRef).style.display = "none";

$("#schedEngGrid").jqGrid('filterToolbar', {
	searchOnEnter : true
});
$("#mailLogDetGridId_" + _pageRef).jqGrid('filterToolbar', {
	searchOnEnter : true
});
</script>


