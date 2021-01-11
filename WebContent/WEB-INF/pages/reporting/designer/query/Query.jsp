<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="delObjAlert_var" 			value="%{getEscText('reporting.delObjAlert')}"/>
<ps:set name="qryNameExistAlert_var" 	value="%{getEscText('qry.qryNameExistAlert')}"/>

<html>
		<script type="text/javascript">
		var delObjAlert 			= '<ps:property value="delObjAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
		var qryNameExistAlert 		= '<ps:property value="qryNameExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
		var queryNameReadOnly 		= '<ps:property value="queryNameReadOnly"  escapeHtml="false" escapeJavaScript="true"/>'

		  $(document).ready(function () 
		  {
		  		$.struts2_jquery.require("Query.js", null,jQuery.contextPath + "/path/js/reporting/designer/");
				rep_query_readyFunc();
				  
		 });
		 
		</script>
		<body>
			<table border="0"  width="100%" id="qryTbl_<ps:property value="_pageRef" escapeHtml="true"/>">
				<tr>
				   <td nowrap="nowrap">
				   <ps:label value="%{getText('queryName')}"/>
				   <ps:textfield name="queryCO.QUERY_NAME" maxlength="50" size="75" id="qryName_${_pageRef}"    onchange="checkQryName(this.value)"></ps:textfield></td>
				   <td></td>
				 </tr>
				 <tr>  
					<td width="50%" valign="top">
					 	
					 	
		 					 <div id="dbEntDiv_<ps:property value="_pageRef" escapeHtml="true"/>"> 
							 	<ps:url var="urlDbEntTag" action="queryDesign_loadDbEntGrid" namespace="/path/designer"></ps:url>
								<psjg:grid  id="dbEntGrid_${_pageRef}" 
									gridModel="gridModel"
									dataType="json"
									href="%{urlDbEntTag}"
									pager="true"
									navigatorAdd="false"
									navigatorDelete="false"
									navigatorEdit="false"
									navigatorRefresh="false"
									navigatorView="false"
									navigator="true"
									navigatorSearch="true"
									navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
				          			rowNum="7" 
				    	  			rowList="5,10,15,20"
				    	  			ondblclick="addEntity()" >
									 <psjg:gridColumn name="ENTITY_ALIAS" id="dBEntAlias"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="ENTITY_ALIAS"   title="%{getText('qry.allEnt')}" colType="text" search="true" editable="false" sortable="true" />
									
									 <psjg:gridColumn name="ENTITY_DB_NAME" id="dBEnt"  index="ENTITY_DB_NAME" title="dbName" colType="text"  editable="false" sortable="true"  hidden="true"/>
				    		       </psjg:grid> 
					 	</div>
					 	
					 	
					</td>
					<td valign="top">
					 <div id="selEntDiv_<ps:property value="_pageRef" escapeHtml="true"/>"> 
							 	<ps:url var="urlSelEntTag" action="queryDesign_loadSelEntGrid" namespace="/path/designer">
							 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
							 	</ps:url>
								<psjg:grid  id="selEntGrid_${_pageRef}" 
									gridModel="gridModel"
									dataType="json"
									href="%{urlSelEntTag}"
									pager="true"
									navigator="true"
									navigatorAdd="false"
									navigatorDelete="true"
									navigatorEdit="false"
									navigatorRefresh="false"
									navigatorView="false"
									navigatorSearch="false"
				          			rowNum="7" 
				    	  			rowList="5,10,15,20"
				    	  			delfunc="delEntity"
				    	  			ondblclick="showDBFields()"
				    	  			>
									 <psjg:gridColumn name="entityAliasWithCount" index="entityAliasWithCount" id="selEntityAliasWithCount"   title="%{getText('qry.SelEnt')}" colType="text"  editable="false" sortable="false" />
									
									   <psjg:gridColumn name="ENTITY_ALIAS" index="ENTITY_ALIAS" id="selEntAlias"   title="ent alias" colType="text" hidden="true" />
									   <psjg:gridColumn name="ENTITY_DB_NAME" index="ENTITY_DB_NAME"  id="selEntDb"  title="db name" colType="text"  editable="false" sortable="false" hidden="true"/>
									   <psjg:gridColumn name="index"  index="index" id="index"  title="id" colType="number"  editable="false" sortable="false" hidden="true"/>
				    		       </psjg:grid> 
					 	</div>
					 	
					</td>
				</tr>
				<tr>
					<td width="50%" valign="top">
						<div id="dbFeDiv_<ps:property value="_pageRef" escapeHtml="true"/>"> 
							 	<ps:url var="urlDBFieldsTag" action="queryDesign_loadDbFieldsGrid" namespace="/path/designer"></ps:url>
								<psjg:grid  id="dBFieldsGrid_${_pageRef}" 
									gridModel="gridModel"
									dataType="json"
									href="%{urlDBFieldsTag}"
									pager="true"
									navigator="true"
									navigatorAdd="false"
									navigatorDelete="false"
									navigatorEdit="false"
									navigatorRefresh="false"
									navigatorView="false"
									navigatorSearch="true"
									navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
				          			rowNum="7" 
				    	  			rowList="5,10,15,20"
				    	  			ondblclick="addField()"
				    	  			>
									 <psjg:gridColumn name="FIELD_ALIAS" id="dbFieldAlias"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="FIELD_ALIAS" title="%{getText('qry.allFields')}" colType="text"  editable="false" sortable="true" />
									
									 <psjg:gridColumn name="ENTITY_DB_NAME" id="entFieldDb"  index="ENTITY_DB_NAME"  colType="text" title="ent db name"  editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="ENTITY_ALIAS" id="entFieldAlias" index="ENTITY_ALIAS"   colType="text" title="ent alias"  editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="FIELD_DB_NAME" id="fieldDb" index="FIELD_DB_NAME"   colType="text" title="field db name"   editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="FIELD_DATA_TYPE" id="fieldType"  index="FIELD_DATA_TYPE"  colType="text" title="type"  editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="PARENT_INDEX" id="parentId"  index="PARENT_INDEX"  colType="number" title="parent"  editable="false" sortable="false" hidden="true"/>
				    		       </psjg:grid> 
					 	</div>
					 	
					</td>
					<td valign="top">
					
					<div id="selFeDiv_<ps:property value="_pageRef" escapeHtml="true"/>"> 
							 	<ps:url var="urlSelFieldsTag" action="queryDesign_loadSelFieldsGrid" namespace="/path/designer">
							 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
							 	</ps:url>
								<psjg:grid  id="selFieldsGrid_${_pageRef}" 
									gridModel="gridModel"
									dataType="json"
									href="%{urlSelFieldsTag}"
									pager="true"
									navigator="true"
									navigatorAdd="false"
									navigatorDelete="true"
									navigatorEdit="false"
									navigatorRefresh="false"
									navigatorView="false"
									navigatorSearch="false"
				          			rowNum="7" 
				    	  			rowList="5,10,15,20"
				    	  			delfunc="delField"
				    	  			>
									 <psjg:gridColumn name="FIELD_ALIAS" index="FIELD_ALIAS" id="selFieldAlias"  title="%{getText('qry.selFields')}" colType="text"  editable="false" sortable="false" />
									 
									 <psjg:gridColumn name="PARENT_INDEX" id="pId"  index="PARENT_INDEX"  colType="number" title="parent"  editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="index"  index="index" id="selFeIndex"  title="id" colType="number"  editable="false" sortable="false"  hidden="true"/>
									</psjg:grid> 
					 	</div>
					 	
					</td>
				</tr>
			</table>	
			<ps:hidden name="updates" id="updates_${_pageRef}"/>
	</body>
</html>

