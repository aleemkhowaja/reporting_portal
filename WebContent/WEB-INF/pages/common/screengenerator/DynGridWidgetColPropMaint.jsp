<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<ps:hidden name="screenId"  value="${criteria.screenId}"></ps:hidden>
<ps:hidden name="elementId" value="${criteria.elementId}"></ps:hidden>
<ps:hidden name="elementType" value="${criteria.elementType}"></ps:hidden>
<ps:hidden name="criteria.colProperties" value="${criteria.colProperties}"></ps:hidden>
<ps:hidden name="elemTableName" value="${criteria.tableName}"></ps:hidden>

<ps:set name="queryColKey" value="%{getEscText('query_key')}" />

<ps:url id="dynScrTablesURL"
	action="dynScrTablesListAction_loadGridWigetColProperties" escapeAmp="false"
	namespace="/path/screenGenerator">
	<ps:param name="criteria.tableName" value="%{criteria.tableName}" />
</ps:url>
<div style="width: 100%">
	<psjg:grid id="dynScrGridWidgetColPropsId" altRows="false" 
		dataType= "json" serializeGridData="screenGeneratorProp_serializeGridWidgetColPropsData"
		editurl="abc" editinline="true" filter="true" gridModel="gridModel"
		href="%{dynScrTablesURL}" navigator="true" navigatorAdd="false"
		navigatorEdit="false" navigatorDelete="false" navigatorRefresh="false"
		navigatorSearch="false" sortable="false" shrinkToFit="false" viewrecords="true"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		hiddengrid="false" multiselect="false" rownumbers="false"
		onEditInlineBeforeTopics="ondynScrGridWidgetColPropsEditInline">

		<psjg:gridColumn id="COL_ID" colType="number" name="COL_ID" index="COL_ID" title="" hidden="true" />
		<psjg:gridColumn id="TABLE_ID" colType="number" name="TABLE_ID" index="TABLE_ID" title="" hidden="true" />
		<psjg:gridColumn id="COL_TYPE" colType="text" name="COL_TYPE" index="COL_TYPE" title="" hidden="true" />
		<psjg:gridColumn id="PRIMARY_KEY" colType="number" name="PRIMARY_KEY" index="PRIMARY_KEY" title="" hidden="true" />
		<psjg:gridColumn id="TABLE_TECH_NAME" colType="text" name="TABLE_TECH_NAME" index="TABLE_TECH_NAME" autoSearch="true"
			title="%{getText('table_tech_name_key')}" editable="false" sortable="true" search="true"    width="120"/>
		<psjg:gridColumn id="COL_TECH_NAME" colType="text" name="COL_TECH_NAME" index="COL_TECH_NAME" autoSearch="true"
			title="%{getText('col_tech_name_key')}" editable="false" sortable="true" search="true"   width="120" />
		<psjg:gridColumn id="COL_DESC" colType="text" name="COL_DESC" index="COL_DESC" autoSearch="true" title="%{getText('col_desc_key')}"
			editable="false" sortable="true" search="true"   width="120" />
		<psjg:gridColumn id="COL_TYPE_DESC" colType="text" name="COL_TYPE_DESC" index="COL_TYPE_DESC" autoSearch="true" title="%{getText('col_desc_key')}"
			editable="false" sortable="true" search="true"  width="120" />
			<psjg:gridColumn id="COL_IS_LIVESEARCH" colType="checkbox" editable="true" sortable="true"
			editoptions="{value:'1:0',dataEvents: [{ type: 'change', fn: function(e) { screenGeneratorProp_onChangeIsLiveSearch(e)  } }]}" 
			edittype="checkbox" formatter="checkbox" formatoptions="{disabled : true}" name="COL_IS_LIVESEARCH"
			index="COL_IS_LIVESEARCH" title="%{getText('is_livesearch_key')}" width="100" align="center" search="false"/>
		<psjg:gridColumn name="QUERY_BTN" index="QUERY_BTN" title="%{getText('liveSearchParams_key')}" align="center" colType="button"
			editable="false"  sortable="false" search="false"  width="120"  formatter="screenGeneratorProp_openLiveSearchQueryFormatter"/>
		<psjg:gridColumn id="QUERY_DATA" colType="text" name="QUERY_DATA" index="QUERY_DATA" title="" hidden="true" />
		<psjg:gridColumn id="LOOKUP_DESC" name="LOOKUP_DESC" title="%{getText('lookup_desc')}" 
			index="LOOKUP_DESC" colType="liveSearch" sortable="true"
			dataAction="${pageContext.request.contextPath}/path/screenGenerator/generatorLookupAction_drawingScrColsGrid"
			resultList="COL_TECH_NAME:LOOKUP_DESC_lookuptxt"
			editable="true"
			paramList="criteria.tableName:elemTableName" />
	</psjg:grid>
</div>
<script type="text/javascript">
var queryColKey= '<ps:property value="queryColKey" escapeHtml="false" escapeJavaScript="true"/>';
$("#dynScrGridWidgetColPropsId").unsubscribe("ondynScrGridWidgetColPropsEditInline");
$("#dynScrGridWidgetColPropsId").subscribe("ondynScrGridWidgetColPropsEditInline",function(){
	screenGeneratorProp_ondynScrGridWidgetColPropsEditInline();
	});
</script>