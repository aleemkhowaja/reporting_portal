<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="joinFeRequired_var" 		value="%{getEscText('group.missingFe')}"/>

<html>
<script type="text/javascript">

	var joinFeRequired 		= '<ps:property value="joinFeRequired_var"  escapeHtml="false" escapeJavaScript="true"/>'

	
		function joinRowClicked()
		{
			rowid = $("#joinGrid_"+_pageRef).jqGrid('getGridParam','selrow');
			myObject = $("#joinGrid_"+_pageRef).jqGrid('getRowData', rowid);
			var zSrc="${pageContext.request.contextPath}/path/designer/queryDesign_findSingleJoin.action?locIndex="+ myObject["index"]+"&_pageRef="+_pageRef;
			params ={};
			$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{
				//fill field1 combo
				for (var i = 0; i < data2['fields1'].length; i++)
				{
					$("#field1_"+_pageRef).append(new Option(data2['fields1'][i]['FIELD_ALIAS'],data2['fields1'][i]['FIELD_DB_NAME']));
				}
				//fill field2 combo
				for (var i = 0; i < data2['fields2'].length; i++)
				{
					$("#argField2_"+_pageRef).append(new Option(data2['fields2'][i]['FIELD_ALIAS'],data2['fields2'][i]['FIELD_DB_NAME']));
				}
				
				$("#argEntity1_"+_pageRef).val(myObject["entity1.index"]);
				$("#field1_"+_pageRef).val(myObject["field1.FIELD_DB_NAME"]);
				$("#join_"+_pageRef).val(myObject["join"]);
				$("#argEntity2_"+_pageRef).val(myObject["entity2.index"]);
				$("#argField2_"+_pageRef).val(myObject["field2.FIELD_DB_NAME"]);
				$("#joinIndex_"+_pageRef).val(myObject["index"]);
				$("#joinMode_"+_pageRef).val("edit");

			});
	}

	function emptyForm()
	{
		$('#field1_${_pageRef} option').each(function(index, option) {
		    $(option).remove();
		});
		$('#argField2_${_pageRef} option').each(function(index, option) {
		    $(option).remove();
		});
		$("#argEntity1_"+_pageRef).val("");
		$("#field1_"+_pageRef).val("");
		$("#join_"+_pageRef).val("Inner");
		$("#argEntity2_"+_pageRef).val("");
		$("#argField2_"+_pageRef).val("");
		$("#joinMode_"+_pageRef).val("add");
	}
	function requiredJoinFieldsFilled()
	{
		if($("#argEntity1_"+_pageRef).val()=="" || $("#field1_"+_pageRef).val()=="" || $("#argEntity2_"+_pageRef).val()==""
			|| $("#argField2_"+_pageRef).val() == "" )
		{
			_showErrorMsg(joinFeRequired);
			return 0;
		}
		return 1;
	}
	function addJoin()
	{
		if(!requiredJoinFieldsFilled())
			return;
		parseNumbers();
		
		var url1 = '${pageContext.request.contextPath}/path/designer/queryDesign_addUpdateJoin.action?_pageRef='+_pageRef;
		$.post(	url1,$("#joinForm_${_pageRef}").serialize(),
				function(param)
				{
					$("#joinGrid_"+_pageRef).trigger("reloadGrid");
					
				}
			);//post
			emptyForm();
	}
	function deleteJoin(rowid) 
	{
		 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteTheJoin(theArgs.rowid)
           }
	      }, {rowid : rowid});	
	}

	function deleteTheJoin(rowid)
	{
		$("#joinIndex_"+_pageRef).val("0");
		parseNumbers();
		var lineNumber = $("#joinGrid_"+_pageRef).jqGrid('getCell', rowid,'index');
		
		var url1 = '${pageContext.request.contextPath}/path/designer/queryDesign_deleteJoin.action?locIndex='+lineNumber+"&_pageRef="+_pageRef;
		var param={};
		$.get(url1,param,function(param) 
				{
					$("#joinGrid_"+_pageRef).trigger("reloadGrid");
				}
		);//get
	}
</script>
<head>
<script type="text/javascript">
	
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
<form id="joinForm_${_pageRef}" useHiddenProps="true"> 
<ps:url id="urlTag" action="queryDesign_joinGridUrl"
	namespace="/path/designer">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
	
<psjg:grid id="joinGrid_${_pageRef}" gridModel="gridModel" dataType="json"
	href="%{urlTag}" pager="true" navigator="true" navigatorSearch="false "
	viewrecords="true" rowNum="15" rowList="5,10,15,20" editinline="true"
	navigatorEdit="false" addfunc="addJoin"
	delfunc="deleteJoin"
	ondblclick="joinRowClicked()"
	>
	<psjg:gridColumn name="index" index="index"
		id="index" width="7" title="none"
		colType="text" editable="false" sortable="true" hidden="true" />
	<psjg:gridColumn name="entity1.index" index="entity1"
		id="entity1" width="7" title="%{getText('jn.entity1')}"
		colType="text" editable="false" sortable="true" hidden="true"/>

	<psjg:gridColumn name="field1.FIELD_DB_NAME" index="field1"
		id="field1" width="7"
		title="%{getText('jn.field1')}" colType="text"
		editable="false" sortable="true" hidden="true"/>
		
	<psjg:gridColumn name="entity1.ENTITY_ALIAS" index="entity1"
		id="entity1" width="7"
		title="%{getText('jn.entity1')}" colType="text"
		editable="false" sortable="true" hidden="true"/>
		
	<psjg:gridColumn name="entity1.entityAliasWithCount" index="entityWithCount1"
		id="entityWithCount1" width="7"
		title="%{getText('jn.entity1')}" colType="text"
		editable="false" sortable="true" />
		
	<psjg:gridColumn name="field1.FIELD_ALIAS" index="field1"
		id="field1" width="7"
		title="%{getText('jn.field1')}" colType="text"
		editable="false" sortable="true" />
		
	<psjg:gridColumn name="join" index="join"
		id="join" width="7"
		title="%{getText('jn.join')}" colType="text"
		editable="false" sortable="true" />

	<psjg:gridColumn name="entity2.index" index="entity2"
		id="entity2" width="7"
		title="%{getText('jn.entity2')}" colType="text"
		editable="false" sortable="true" hidden="true"/>

	<psjg:gridColumn name="field2.FIELD_DB_NAME" index="field2"
		id="field2" width="7"
		title="%{getText('jn.field2')}" colType="text"
		editable="false" sortable="true" hidden="true"/>
		
	<psjg:gridColumn name="entity2.ENTITY_ALIAS" index="entity2"
		id="entity2" width="7"
		title="%{getText('jn.entity2')}" colType="text"
		editable="false" sortable="true" hidden="true"/>
		
	<psjg:gridColumn name="entity2.entityAliasWithCount" index="entityAliasWithCount2"
		id="entityAliasWithCount2" width="7"
		title="%{getText('jn.entity2')}" colType="text"
		editable="false" sortable="true" />

	<psjg:gridColumn name="field2.FIELD_ALIAS" index="field2"
		id="field2" width="7"
		title="%{getText('jn.field2')}" colType="text"
		editable="false" sortable="true" />
</psjg:grid>
<table>
	<tr>
		<td><ps:label value="%{getText('jn.entity1')}"></ps:label></td>
		<td><ps:label value="%{getText('jn.field1')}"></ps:label></td>
		<td><ps:label value="%{getText('jn.join')}"></ps:label></td>
		<td><ps:label value="%{getText('jn.entity2')}"></ps:label></td>
		<td><ps:label value="%{getText('jn.field2')}"></ps:label></td>
	</tr>
	<tr>
		<td>
			<ps:select name="argEntity1" id="argEntity1_${_pageRef}" list="entitiesList" 
			listKey="index" listValue="entityAliasWithCount"
			dependency="field1_${_pageRef}:fields1"
			dependencySrc="${pageContext.request.contextPath}/path/designer/queryDesign_entity1Change"
			parameter="argEntity1:argEntity1_${_pageRef},_pageRef:pageRef_${_pageRef}" emptyOption="true">
			</ps:select>
		</td>
		<td>
			<ps:select name="argField1" id="field1_${_pageRef}" list="fields1" emptyOption="true"
			listKey="FIELD_DB_NAME" listValue="FIELD_ALIAS"></ps:select>
		</td>
		<td><ps:select name="argJoinType" id="join_${_pageRef}"
			list="joinTypesList" listKey="VALUE_CODE" listValue="VALUE_DESC"></ps:select>
		</td>
		<td>
			<ps:select name="argEntity2" id="argEntity2_${_pageRef}" list="entitiesList"
			listKey="index" listValue="entityAliasWithCount" emptyOption="true"
			dependency="argField2_${_pageRef}:fields2"
			dependencySrc="${pageContext.request.contextPath}/path/designer/queryDesign_entity2Change"
			parameter="argEntity2:argEntity2_${_pageRef},_pageRef:pageRef_${_pageRef}"></ps:select>
		</td>
		<td>
			<ps:select name="argField2" id="argField2_${_pageRef}" list="fields2"
			listKey="FIELD_DB_NAME" listValue="FIELD_ALIAS" emptyOption="true"></ps:select>
		</td>
		<td></td>
	</tr>
</table>
<ps:hidden name="locIndex" id="joinIndex_${_pageRef}" value="0"></ps:hidden>
<ps:hidden id="joinMode_${_pageRef}" name="joinMode" value="add"></ps:hidden>

</form>
</div>
</body>
</html>