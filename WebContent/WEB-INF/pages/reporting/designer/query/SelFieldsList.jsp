<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<html>
	<script type="text/javascript">

	 $(document).ready(function () 
			  {
				  
					
			 });
	</script>
	<body>
					<table border="0" width="100%" id="selFeAggrTbl_<ps:property value="_pageRef" escapeHtml="true"/>">
					 <tr>
					   <td width="100%">
					   			<ps:url var="urlSelectedFieldsTag" action="queryDesign_loadSelFieldsGrid" namespace="/path/designer" escapeAmp="false">
					   				<ps:param  name="PARENT_INDEX" value="PARENT_INDEX"></ps:param>
					   				<ps:param name="_pageRef" value="_pageRef"></ps:param>
					   			</ps:url>
					   			
								<psjg:grid  id="selectedFieldsGrid_${_pageRef}" 
									gridModel="gridModel"
									dataType="json"
									href="%{urlSelectedFieldsTag}"
									pager="true"
									navigator="true"
									navigatorAdd="false"
									navigatorDelete="false"
									navigatorEdit="false"
									navigatorRefresh="false"
									navigatorView="false"
									navigatorSearch="false"
				          			rowNum="7" 
				    	  			rowList="5,10,15,20"
				    	  			>
									 <psjg:gridColumn name="FIELD_ALIAS" index="FIELD_ALIAS" id="sselFieldAlias"  title="%{getText('query.fields')}" colType="text"  editable="false" sortable="false" />
									 
									  <psjg:gridColumn name="FIELD_DB_NAME" id="ffieldDb" index="FIELD_DB_NAME"   colType="text" title="field db name"   editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="FIELD_DATA_TYPE" id="ffieldType"  index="FIELD_DATA_TYPE"  colType="text" title="type"  editable="false" sortable="false"  hidden="true"/>
									  <psjg:gridColumn name="ENTITY_ALIAS" id="eentFieldAlias" index="ENTITY_ALIAS"   colType="text" title="ent alias"  editable="false" sortable="false"  hidden="true"/>
									 <psjg:gridColumn name="PARENT_INDEX" id="ppId"  index="PARENT_INDEX"  colType="number" title="parent"  editable="false" sortable="false"  hidden="true"/>
									 <psjg:gridColumn name="index"  index="index" id="sselFeIndex"  title="id" colType="number"  editable="false" sortable="false"  hidden="true"/>
									</psjg:grid> 
					   </td>
					 </tr>
					</table>	
					
		<ps:hidden id="PARENT_INDEX" name="PARENT_INDEX"></ps:hidden>
	
	</body>
		<script type="text/javascript">
	
       $('#selectedFieldsGrid_'+_pageRef ).jqGrid("setColProp","FIELD_ALIAS",{width:380});
       $('#selectedFieldsGrid_'+_pageRef ).jqGrid("setGridWidth",400);	
        
	</script>
</html>


