<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<html>
	<script type="text/javascript">
	  $(document).ready(function () 
	  {
		$("#allFieldsGrid").subscribe('addDisplayField', function(event,data) 
		{
			var rowid = (event["originalEvent"])["id"];
			var url = jQuery.contextPath+ "/path/designer/queryDesign_addDisplayField";
			myObject = $("#allFieldsGrid").jqGrid('getRowData',rowid); 
			params = {};
			paramStr = JSON.stringify(myObject)
			paramStr = "{"+ "fieldCO:"+paramStr + "}";
			params["updates"] = paramStr;
			params["_pageRef"]=_pageRef;
		    $.get(url, params , function( param )
		 	{
		    	$("#displFieldsGrid").trigger("reloadGrid"); 
		 	});
 		}
 		);
	  }
	  );

	  function delDisplField(rowid)
	  {
		var url = jQuery.contextPath+ "/path/designer/queryDesign_delDisplField";
		myObject = $("#displFieldsGrid").jqGrid('getRowData',rowid);
		var feId=	myObject["index"];	
		params = {};
		params["locIndex"] = feId;
		params["_pageRef"]=_pageRef;
	    $.get(url, params , function( param )
	 	{
	    	$("#displFieldsGrid").trigger("reloadGrid"); 
	 	});
	  }
	</script>
	<body>
					<table border="0" width="100%">
					 <tr>
					   <td width="50%" valign="top">
					    <div>
				   			<ps:url var="urlAllFieldsTag" action="queryDesign_loadAllFieldsGrid" namespace="/path/designer">
				   				<ps:param name="_pageRef" value="_pageRef"></ps:param>
				   			</ps:url>
					   			
						    <psjg:grid  id="allFieldsGrid"
						    			autowidth="false" 
						    			width="300"
										gridModel="gridModel"
										dataType="json"
										href="%{urlAllFieldsTag}"
										pager="false"
					          			treeGrid="true"
					          			treeGridModel="adjacency"
					          			loadonce="false"
					          			onSelectRowTopics="addDisplayField"
					    	  			>
										 <psjg:gridColumn name="feName" index="feName" id="feNameCF"  title="%{getText('qry.allFields')}" colType="text"  editable="false" sortable="false" />
										 
										 <psjg:gridColumn name="FIELD_ALIAS" index="FIELD_ALIAS" id="fieldAliasCF"  title="%{getText('qry.selFields')}" colType="text"  editable="false" sortable="false" hidden="true"/>
										 <psjg:gridColumn name="FIELD_DB_NAME" id="fieldDbCF" index="FIELD_DB_NAME"   colType="text" title="field db name"   editable="false" sortable="false" hidden="true"/>
										 <psjg:gridColumn name="FIELD_DATA_TYPE" id="fieldTypeCF"  index="FIELD_DATA_TYPE"  colType="text" title="type"  editable="false" sortable="false"  hidden="true"/>
										 <psjg:gridColumn name="ENTITY_ALIAS" id="entFieldAliasCF" index="ENTITY_ALIAS"   colType="text" title="ent alias"  editable="false" sortable="false"  hidden="true"/>
										 <psjg:gridColumn name="labelAlias" id="labelAliasCF" index="labelAlias"   colType="text" title="label Alias"  editable="false" sortable="false"  hidden="true"/>
										 <psjg:gridColumn name="isLeaf" id="isLeafCF" index="isLeaf"   colType="text" title="isLeaf"  editable="false" sortable="false"  hidden="true"/>
										 <psjg:gridColumn name="PARENT_INDEX" id="pIdCF"  index="PARENT_INDEX"  colType="number" title="parent"  editable="false" sortable="false"  hidden="true"/>
										 <psjg:gridColumn name="index"  index="index" id="sselFeIndexCF"  title="id" colType="number"  editable="false" sortable="false"  hidden="true" key="true"/>
						    </psjg:grid> 
						 </div>
					   </td>
					   <td width="50%" valign="top">
					     <div> 
							 	<ps:url var="urlDisplFieldsTag" action="queryDesign_loadDisplayFieldsGrid" namespace="/path/designer">
							 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
							 	</ps:url>
								<psjg:grid  id="displFieldsGrid" 
									autowidth="false" 	
									width="300"								
									gridModel="gridModel"
									dataType="json"
									href="%{urlDisplFieldsTag}"
									pager="true"
									navigator="true"
									navigatorAdd="false"
									navigatorDelete="true"
									navigatorEdit="false"
									navigatorRefresh="false"
									navigatorView="false"
									navigatorSearch="false"
				          			rowNum="10" 
				    	  			rowList="5,10,15,20"
				    	  			delfunc="delDisplField"
				    	  			>
									 <psjg:gridColumn name="feName" index="feName" id="displfeName"  title="%{getText('qry.selFields')}" colType="text"  editable="false" sortable="false" />
									 <psjg:gridColumn name="labelAlias" id="displlabelAlias" index="labelAlias"   colType="text" title="label Alias"  editable="false" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="index"  index="index" id="displFeIndex"  title="id" colType="number"  editable="false" sortable="false"  hidden="true"/>
									</psjg:grid> 
					 	</div>
					   </td>
					 </tr>
					</table>	
					
					
		

	
	</body>
</html>


