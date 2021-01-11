<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<script type="text/javascript">

</script>

<table style="width: 50%">
	<tr>
		<td><ps:label value="%{getText('sch.splitFe')}" /></td>
	</tr>
	<tr>
		<td>
		<ps:url id="urlMailFeGrpGrid" action="scheduler_loadMailFeGroupByList"
		namespace="/path/scheduler" escapeAmp="false">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		<ps:param name="reportId" value="reportId"></ps:param>
		<ps:param name="scheduleId" value="scheduleId"></ps:param>
		<ps:param name="updates" value="updates"></ps:param>
	</ps:url>
	<psjg:grid id="mailFeGrpByGrid_${_pageRef}" dataType="json"
		href="%{urlMailFeGrpGrid}" gridModel="gridModel" pager="true" 
		rowNum="10" delfunc="deleteMailFeGrpBy" addfunc="addMailFeGrpBy"
		rowList="5,10,15,20" viewrecords="true" navigator="true"
		navigatorAdd="true" navigatorSearch="false" navigatorEdit="false"
		editinline="true" editurl="%{urlMailFeGrpGrid}"
		onSelectRowTopics="constructGrpComboUrl">
		<psjg:gridColumn name="mailGrpVO.SCHED_ID" index="SCHED_ID"
			colType="number" title="SCHED_ID" editable="true" sortable="false"
			search="false" hidden="true" />
		<psjg:gridColumn name="mailGrpVO.REPORT_ID" index="REPORT_ID"
			colType="number" title="REPORT_ID" editable="true" sortable="false"
			search="false" hidden="true" />
		<psjg:gridColumn name="mailGrpVO.GRP_ORDER" index="GRP_ORDER"
			colType="number" title="GRP_ORDER" editable="true" sortable="false"
			search="false" hidden="true" />
		<psjg:gridColumn name="mailGrpVO.FIELD_ALIAS" index="FIELD_ALIAS"
			colType="text" title="FIELD_ALIAS" editable="true" sortable="false"
			search="false" hidden="true" />
		<psjg:gridColumn id="FIELD_DESC" index="FIELD_DESC" name="FIELD_DESC"
			title="%{getText('entities.Alias')}" colType="select" hidden="false"
			editable="true" sortable="false" search="false"
			editoptions="{ value:function() {  return loadCombo(grpCmbUrl,'mailFeGroupByCmbLst', 'VALUE_CODE', 'VALUE_DESC');}
								,dataEvents: [{ type: 'change', fn: function(e) { fillGrpByFeAlias() } }]}"
			edittype="select" />
	</psjg:grid>
		</td>
	</tr>
</table>