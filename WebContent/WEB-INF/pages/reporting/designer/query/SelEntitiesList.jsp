<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<html>

	<body>

			
						   <div id="selEntAggrDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
						   		<ps:url var="urlSelectedEntTag" action="queryDesign_loadSelEntGrid" namespace="/path/designer">
						   			<ps:param name="_pageRef" value="_pageRef"></ps:param>
						   		</ps:url>
									<psjg:grid  id="selectedEntGrid_${_pageRef}" 
										gridModel="gridModel"
										dataType="json"
										href="%{urlSelectedEntTag}"
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
										 <psjg:gridColumn name="entityAliasWithCount" index="entityAliasWithCount" id="selEntAliasWithCount"   title="%{getText('entities')}" colType="text"  editable="false" sortable="false" />
 										   <psjg:gridColumn name="ENTITY_ALIAS" index="ENTITY_ALIAS" id="selEntAlias"   title="%{getText('entities')}" colType="text"  hidden="true"/>
										   <psjg:gridColumn name="ENTITY_DB_NAME" index="ENTITY_DB_NAME"  id="selEntDb"  title="db name" colType="text"  editable="false" sortable="false" hidden="true" />
										   <psjg:gridColumn name="index"  index="index" id="index"  title="id" colType="number"  editable="false" sortable="false" hidden="true" />
					    		       </psjg:grid> 
					    	</div>	       
						
	</body>
	
	<script type="text/javascript">
	
       $('#selectedEntGrid_'+_pageRef ).jqGrid("setColProp","entityAliasWithCount",{width:380});
       $('#selectedEntGrid_'+_pageRef ).jqGrid("setGridWidth",400);	
        
	</script>
	
</html>


