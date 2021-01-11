<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="havingFeRequAlert_var" 		value="%{getEscText('having.havingFeRequired')}"/>

<html>
<head> 
<script type="text/javascript">

var havingFeRequAlert 		= "${havingFeRequAlert_var}";

$(document).ready(function () 
{
	$("#havingGrid_"+_pageRef).subscribe('applyConjunctionHaving', function(event,data) 
	{
		var condCount=$("#havingGrid_"+_pageRef).jqGrid('getGridParam','records');
		if(condCount>0)
		{
				$("#conjunctionh_"+_pageRef).val("And");
		}
	});		
}
);


		function havingRowClicked()
		{
			rowid = $('#havingGrid_'+_pageRef).jqGrid('getGridParam','selrow');
			myObject = $("#havingGrid_"+_pageRef).jqGrid('getRowData', rowid);

			var zSrc="${pageContext.request.contextPath}/path/designer/queryDesign_findSingleHaving.action?locIndex="+ myObject["index"]+"&_pageRef="+_pageRef;
			params ={};
			$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{
				$("#lparh_"+_pageRef).val(data2['havingObject']['lparenthesis']);
				$("#rparh_"+_pageRef).val(data2['havingObject']['rparenthesis']);
				if(data2['havingObject']['argument2']!=null)
				{
					$("#ARGUMENT2_"+_pageRef).val(data2['havingObject']['argument2']['index']);
				}
				if(data2['havingObject']['argument1']!=null)
				{
					$("#ARGUMENT1_"+_pageRef).val(data2['havingObject']['argument1']['index']);
				}
				$("#conjunctionh_"+_pageRef).val(data2['havingObject']['conjunction']);
				
				$("#customArgumenth_"+_pageRef).val(data2['havingObject']['value']);
				$("#operatorh_"+_pageRef).val(data2['havingObject']['operator']);
				$("#havingIndex_"+_pageRef).val(data2['havingObject']['index']);
				$("#havingField1_"+_pageRef).val(data2['havingObject']['field1']['index']);

				$("#havingEntity_"+_pageRef).val(data2['havingObject']['entity2']['index']);
				for (var i = 0; i < data2['fields2'].length; i++)
				{
					$("#havingField2_"+_pageRef).append(new Option(data2['fields2'][i]['FIELD_ALIAS'],data2['fields2'][i]['FIELD_DB_NAME']));
				}
				$("#havingField2_"+_pageRef).val(data2['havingObject']['field2']['FIELD_DB_NAME']);
			});
}
function emptyHavingForm()
{
	$("#havingIndex_"+_pageRef).val("");
	$("#conjunctionh_"+_pageRef).val("And");
	$("#rparh_"+_pageRef).val("");
	$("#ARGUMENT2_"+_pageRef).val("");
	$("#ARGUMENT1_"+_pageRef).val("");
	$("#operatorh_"+_pageRef).val("");
	$("#havingField1_"+_pageRef).val("");
	$("#havingField2_"+_pageRef).val("");
	$("#havingEntity_"+_pageRef).val("");
	$("#lparh_"+_pageRef).val("");
	$("#customArgumenth_"+_pageRef).val("");
	$('#havingField2_${_pageRef} option').each(function(index, option) {
	    $(option).remove();
	});

}
function requiredHavingFieldsFilled()
{
	if($("#havingField1_"+_pageRef).val()==null || $("#havingField1_"+_pageRef).val()=="" || $("#operatorh_"+_pageRef).val()=="" || 
			($("#havingField2_"+_pageRef).val()=="" && $("#ARGUMENT1_"+_pageRef).val()==""	&& $("#ARGUMENT2_"+_pageRef).val() == "" 
				&& $("#customArgumenth_"+_pageRef).val() == ""))
	{
		_showErrorMsg(havingFeRequAlert);
		return 0;
	}
	return 1;
}
function addHaving()
{
	if(!requiredHavingFieldsFilled())
		return;
	parseNumbers();
	
	var url1 = '${pageContext.request.contextPath}/path/designer/queryDesign_addUpdateHaving.action';
	$.post(	url1,$("#havingForm_"+_pageRef).serialize(),
			function(param)
			{
				$("#havingGrid_"+_pageRef).trigger("reloadGrid");
				$("#conjunctionh_"+_pageRef).attr("disabled",false);
			}
		);//post
		emptyHavingForm();
}
function deleteHaving(rowid) 
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
         if(confirmcChoice)
         {
        	 deleteTheHaving(theArgs.rowid)
         }
	      }, {rowid : rowid});	
}

function deleteTheHaving(rowid)
{
	parseNumbers();
	var lineNumber = $("#havingGrid_"+_pageRef).jqGrid('getCell', rowid,'index');
	var url1 = '${pageContext.request.contextPath}/path/designer/queryDesign_deleteHaving.action?locIndex=' + lineNumber+'&_pageRef='+_pageRef;
	var param={};
	$.get(url1,param,function(param) 
			{
				$("#havingGrid_"+_pageRef).trigger("reloadGrid");
				emptyHavingForm();
				if(param['conditionRecordsCount'] > 0)
					$("#conjunctionh_"+_pageRef).attr("disabled",false);
				else{
					$("#conjunctionh_"+_pageRef).attr("disabled",true);
					$("#conjunctionh_"+_pageRef).val("");
				}
			}
	);//get
	
}
</script>
</head>
<style type="text/css">
	 	.headerPortion 
		{
			padding: 0 0 5px 0;
		}
		.headerPortionContent 
		{
			padding: 0 10px 0 10px;
		}
		form div,table,ul,li 
		{
			font: 8pt Tahoma;	 
		}
</style> 
<body>
<div>
<form id="havingForm_${_pageRef}">
	<table width="100%" id="havingTbl_${_pageRef}">
<tr>
<td colspan="9">
<ps:url id="urlTag" action="queryDesign_havingGridUrl" namespace="/path/designer">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
	
<psjg:grid id="havingGrid_${_pageRef}" gridModel="gridModel" dataType="json"
	href="%{urlTag}" pager="true" navigator="true" navigatorSearch="false "
	viewrecords="true" rowNum="15" rowList="5,10,15,20" editinline="true"
	navigatorEdit="false" addfunc="addHaving"
	delfunc="deleteHaving"  onGridCompleteTopics="applyConjunctionHaving"
	ondblclick="havingRowClicked()"
	>

<psjg:gridColumn name="index" index="index"
		id="index" title="none"
		colType="text" editable="false" sortable="true" hidden="true" />

	<psjg:gridColumn name="conjunction" index="conjunction"
		id="conjunction" width="7"
		title="%{getText('cnd.Conjunction')}" colType="text"
		editable="false" sortable="true" />
	<psjg:gridColumn name="lparenthesis" index="lparenthesis"
		id="lparenthesis" width="7" title="("
		colType="text" editable="false" sortable="true" />
	<psjg:gridColumn name="field1.aggregateAlias" index="field1"
		id="field1" width="7"
		title="%{getText('jn.field1')}" colType="text"
		editable="false" sortable="true" hidden="false"/>
	<psjg:gridColumn name="operator" index="operator"
		id="operator" width="7"
		title="%{getText('operator')}" colType="text"
		editable="false" sortable="true" hidden="true"/>
		
	<psjg:gridColumn name="operatorName" index="operatorName"
		id="operatorName" width="7"
		title="%{getText('operator')}" colType="text"
		editable="false" sortable="true" />
	
	<psjg:gridColumn name="entity2.ENTITY_ALIAS" index="entity2"
		id="entity2" width="7"
		title="%{getText('jn.entity2')}" colType="text"
		editable="false" sortable="true" hidden="true"/>
	
	<psjg:gridColumn name="entity2.entityAliasWithCount" index="entityAliasWithCount"
		id="entityAliasWithCount" width="7"
		title="%{getText('jn.entity2')}" colType="text"
		editable="false" sortable="true" />
	
	<psjg:gridColumn name="field2.FIELD_DB_NAME" index="field2"
		id="field2" width="7"
		title="%{getText('jn.field2')}" colType="text"
		editable="false" sortable="true" />
	<psjg:gridColumn name="argument1.ARGUMENT_NAME" index="argument1.ARGUMENT_NAME"
		id="argument1_argumentName" width="7"
		title="%{getText('cnd.Argument1')}" colType="text"
		editable="false" sortable="true" />

	<psjg:gridColumn name="argument2.ARGUMENT_NAME" index="argument2.ARGUMENT_NAME"
		id="argument2_argumentName" width="7"
		title="%{getText('cnd.Argument2')}" colType="text"
		editable="false" sortable="true" hidden="false"/>

	<psjg:gridColumn name="value" index="value"
		id="value" width="7"
		title="%{getText('cnd.Value')}" colType="text"
		editable="false" sortable="true" hidden="false"/>
		
	<psjg:gridColumn name="rparenthesis" index="rparenthesis"
		id="rparenthesis" width="7"
		title=")" colType="text"
		editable="false" sortable="true" />


		</psjg:grid>
</td>
</tr>
	<tr>
		<td>
			<ps:label value="%{getText('cnd.Conjunction')}"></ps:label>
		</td>
		<td>
			<ps:label value="("></ps:label>
		</td>
		<td>
			<ps:label value="%{getText('cnd.Field')}"></ps:label>
		</td>
		<td>
			<ps:label value="%{getText('operator')}"></ps:label>
		</td>
		<td>
			<ps:label value="%{getText('jn.field2')}"></ps:label>
		</td>
		<td>
			<ps:label value="%{getText('cnd.Argument1')}"></ps:label>
		</td>
		<td>
			<ps:label value="%{getText('cnd.Argument2')}"></ps:label>
		</td>
		<td>
			<ps:label value="%{getText('cnd.Value')}"></ps:label>
		</td>
		<td>
			<ps:label value=")"></ps:label>
		</td>

	</tr>
	<tr>
		<td>
			<ps:select id="conjunctionh_${_pageRef}" list="conjunctionsList" listKey="VALUE_CODE" listValue="VALUE_DESC" emptyOption="true" name="havingObject.conjunction" disabled="%{havConjunctionDisabled}"></ps:select>
		</td>
		<td>
			<ps:hidden id="havingIndex_${_pageRef}" name="havingIndex" value=""></ps:hidden>
			<ps:select id="lparh_${_pageRef}" list="#{'(':'('}" emptyOption="true" name="havingObject.lparenthesis"></ps:select>
		</td>
		<td>
			<ps:select name="havingField1" id="havingField1_${_pageRef}" list="aggFields"
				listKey="index" listValue="aggregateAlias" emptyOption="true">
			</ps:select>
		</td>
		<td>
			<ps:select name="havingObject.operator" id="operatorh_${_pageRef}" list="operatorsList"
				listKey="VALUE_CODE" listValue="VALUE_DESC" emptyOption="true">
			</ps:select>
		</td>
		<td>
			<ps:select name="havingEntity" id="havingEntity_${_pageRef}" list="entitiesList"
				listKey="index" listValue="entityAliasWithCount" emptyOption="true"
				dependency="havingField2_${_pageRef}:fields2"
				dependencySrc="${pageContext.request.contextPath}/path/designer/queryDesign_havingEntityChange"
				parameter="havingEntity:havingEntity_${_pageRef},_pageRef:pageRef_${_pageRef}">
			</ps:select>
			<ps:select name="havingField2" id="havingField2_${_pageRef}" list="fields2"
				listKey="FIELD_DB_NAME" listValue="FIELD_ALIAS" emptyOption="true">
			</ps:select>
		</td>
		<td>
			<ps:select name="ARGUMENT1" id="ARGUMENT1_${_pageRef}" list="argumentsList"
				listKey="index" listValue="ARGUMENT_NAME" emptyOption="true">
			</ps:select>
		</td>
		<td>
			<ps:select name="ARGUMENT2" id="ARGUMENT2_${_pageRef}" list="argumentsList"
				listKey="index" listValue="ARGUMENT_NAME" emptyOption="true">
			</ps:select>
		</td>
		<td>
			<ps:textfield name="havingObject.value"
				id="customArgumenth_${_pageRef}"></ps:textfield>
		</td>
		<td>
			<ps:select id="rparh_${_pageRef}" list="#{')':')'}" emptyOption="true" name="havingObject.rparenthesis"></ps:select>
		</td>

	</tr>
</table>
<ps:hidden name="_pageRef" value="${_pageRef}"/>
</form>
</div>
</body>