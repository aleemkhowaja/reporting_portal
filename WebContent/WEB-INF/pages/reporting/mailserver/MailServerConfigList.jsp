<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="diffMSPass_var" 			value="%{getEscText('connection.diffPass')}"/>
<ps:set name="mailSentMsg_var" 			value="%{getEscText('ms.mailSentMsg')}"/>
<ps:set name="invalidMailAlert_var" 	value="%{getEscText('ms.invalidMail')}"/>
<ps:set name="usedMailServer_var" 	value="%{getEscText('ms.usedMailServer')}"/>

<script type="text/javascript">
var diffMSPass 			= '<ps:property value="diffMSPass_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var mailSentMsg 		= '<ps:property value="mailSentMsg_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var invalidMailAlert 	= '<ps:property value="invalidMailAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var usedMailServer 	= '<ps:property value="usedMailServer_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(
		function() {
			$.struts2_jquery.require("MailServerConfig.js", null,
					jQuery.contextPath + "/path/js/reporting/mailserver/");
					rep_mailserver_listReadyFunc();
		});
</script>


<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<ps:url id="urlMsGrid" action="mailServerConfig_loadMailServerGrid"
	namespace="/path/mailServerConfig">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
<psjg:grid id="msGridId_${_pageRef}" dataType="json" href="%{urlMsGrid}"
	gridModel="gridModel" pager="true" rowNum="5" rowList="5,10,15,20"
	viewrecords="true" navigator="true" navigatorAdd="true"
	navigatorEdit="false"
	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
	addfunc="emptySmForm" ondblclick="loadMailServerForm()"
	onCompleteTopics="emptyMailServerTrx" delfunc="deleteMailServer">
	<psjg:gridColumn name="mailServerVO.MAIL_SERVER_CODE"
		id="MAIL_SERVER_CODE"
		searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
		index="MAIL_SERVER_CODE" colType="number" title="MAIL_SERVER_CODE"
		sortable="true" search="true" key="true" hidden="true" />
	<psjg:gridColumn name="mailServerVO.HOST" id="HOST"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		index="HOST" colType="text" title="%{getText('ms.host')}"
		sortable="true" />
	<psjg:gridColumn name="mailServerVO.PORT" id="PORT"
		searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" index="PORT"
		colType="number" title="%{getText('ms.port')}" sortable="true" />
	<psjg:gridColumn name="mailServerVO.SERVER_USER_NAME"
		id="SERVER_USER_NAME"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		index="SERVER_USER_NAME" colType="text"
		title="%{getText('ms.userName')}" sortable="true" />
	<psjg:gridColumn name="mailServerVO.MAIL_FROM" id="MAIL_FROM"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		index="MAIL_FROM" colType="text" title="%{getText('ms.from')}"
		sortable="true" />
</psjg:grid>


<div id="msDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="connectedSortable ui-helper-reset">
	<div id="msMaintDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="collapsibleContainer"
		title="Details">
		<%@include file="MailServerConfigMaint.jsp"%>
	</div>
</div>



<script type="text/javascript">
$("#msGridId_" + _pageRef).jqGrid('filterToolbar', {
	searchOnEnter : true
});
</script>
