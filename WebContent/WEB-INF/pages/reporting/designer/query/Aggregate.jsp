<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="missingLabelAlert_var" 		value="%{getEscText('aggr.missingLabel')}"/>

<html>
	<script type="text/javascript">
	
	 var missingLabelAlert 		= '<ps:property value="missingLabelAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'

	 function addAggrLine()
	 {

	  //check if the label is missing
	     var jsonStringUpdates = $("#aggrGrid_"+_pageRef).jqGrid('getChangedRowData','feName'); 
	     var feNameVal=jsonStringUpdates.substring(jsonStringUpdates.indexOf("\"feName\":\"")+10,jsonStringUpdates.indexOf("\",\"ENTITY_ALIAS\""))
		 if(feNameVal=="" && jsonStringUpdates!="")
		 {
			_showErrorMsg(missingLabelAlert)
			return;
		 }

	     //save previous row
	     if(jsonStringUpdates!="")
	     {
	    	 $("#updatesAggr_"+_pageRef).val(jsonStringUpdates); 
	 	    var url = $("#aggrGridform_"+_pageRef).attr("action");
	 		 myObject =  $("#aggrGridform_"+_pageRef).serialize();
	 		 $.post(url, myObject , function( param )
	 	 	{
	 	   	  $("#aggrGrid_"+_pageRef).trigger("reloadGrid");
	 	   	  window.setTimeout("delayFunc()" ,1000);
	 	 	});
	     }
	     else
	     {
	    	 delayFunc();
	     }
	   
	 }
	 
	function delayFunc()
	{
		$("#aggrGrid_"+_pageRef).jqGrid('addInlineRow',{});
	}

	  function okButton(obj)
	  {

		  
		parentRowId = $("#aggrGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	   	rowid = $("#selectedEntGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	   	myObject = $("#selectedEntGrid_"+_pageRef).jqGrid('getRowData',rowid);
		var previousPId=$("#"+parentRowId+"_PARENT_INDEX").val();
		$("#inpt"+parentRowId+"_entityAliasWithCount").val(myObject["entityAliasWithCount"]);
		$("#"+parentRowId+"_ENTITY_ALIAS").val(myObject["ENTITY_ALIAS"]);
		$("#"+parentRowId+"_ENTITY_DB_NAME").val(myObject["ENTITY_DB_NAME"]);
		$("#"+parentRowId+"_PARENT_INDEX").val(myObject["index"]);
		$("#parId_"+_pageRef).val(myObject["index"]);

		if(previousPId!=myObject["index"])
		{
			$("#inpt"+parentRowId+"_FIELD_ALIAS").val("");
			$("#"+parentRowId+"_FIELD_DB_NAME").val("");
			$("#"+parentRowId+"_FIELD_DATA_TYPE").val("");
			$("#"+parentRowId+"_type").val("");
			//$("#"+parentRowId+"_aggrFeId").val("");
		} 	
			 $(obj).dialog('close');
	 }

	 function addField(obj)
	 {
		 parentRowId = $("#aggrGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		   	rowid = $("#selectedFieldsGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		   	myObject = $("#selectedFieldsGrid_"+_pageRef).jqGrid('getRowData',rowid);
			$("#inpt"+parentRowId+"_FIELD_ALIAS").val(myObject["FIELD_ALIAS"]);
			$("#"+parentRowId+"_FIELD_DB_NAME").val(myObject["FIELD_DB_NAME"]);
			//$("#"+parentRowId+"_aggrFeId").val(myObject["index"]);
			$("#"+parentRowId+"_FIELD_DATA_TYPE").val(myObject["FIELD_DATA_TYPE"]);
		//	$("#inpt"+parentRowId+"_ENTITY_ALIAS").val(myObject["ENTITY_ALIAS"]);
			$("#"+parentRowId+"_type").val("1");
		    $(obj).dialog('close');
	 }

	</script>
	<body>

			
						
						<div id="aggrGridDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
						<ps:url var="urlTag" action="queryDesign_loadAggrGrid" namespace="/path/designer">
							<ps:param name="_pageRef" value="_pageRef"></ps:param>
						</ps:url>
						<psjg:grid  id="aggrGrid_${_pageRef}" 
							gridModel="gridModel"
							dataType="json"
							href="%{urlTag}"
							pager="true"
							navigator="true"
							navigatorSearch="false "
							navigatorRefresh="false"
		          			viewrecords="true"
		          			rowNum="15" 
		    	  			rowList="5,10,15,20"
		    	  			editinline="true"
		    	  			editurl="%{urlTag}"
		          			navigatorEdit="false"
		          			addfunc="addAggrLine" 
		          			 >
				      				 <psjg:gridColumn name="aggregate" index="aggregate" id="aggr" title="%{getText('aggr.aggrFunct')}" colType="select" editable="true" edittype="select" formatter="select" editoptions="{value:'Max:%{getText('reporting.max')};Min:%{getText('reporting.min')};Avg:%{getText('reporting.avg')};Sum:%{getText('reporting.sum')};Count:%{getText('reporting.count')}'}"  sortable="false" />
									 <psjg:gridColumn name="entityAliasWithCount" dialogUrl="/path/designer/queryDesign_openSelEntDialog.action?_pageRef=${_pageRef}" dialogOptions="{ autoOpen: false,close: function(){$(this).remove()}, height:300,title:'%{getText('entities.entList')}' , width:450 ,modal: true, buttons: { '%{getText('reporting.ok')}': function() {okButton(this);},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$(this).remove()} } }" index="entityAliasWithCount" colType="dialog" title="%{getText('entities')}"  editable="true" width="150" align="center" sortable="true"/>
									<psjg:gridColumn  name="FIELD_ALIAS" dialogUrl="/path/designer/queryDesign_openSelFieldsDialog.action?_pageRef=${_pageRef}" dialogOptions="{ autoOpen: false,close: function(){$(this).remove()}, height:300,title:'%{getText('reporting.fildsList')}' , width:450 ,modal: true, buttons: { '%{getText('reporting.ok')}': function() {addField(this);},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$(this).remove()} } }" index="FIELD_ALIAS" colType="dialog" title="%{getText('query.fields')}"  editable="true" width="150" align="center" sortable="true"/>
									<psjg:gridColumn name="feName" index="feName" id="feNameAggr" title="%{getText('reporting.label')}"  colType="text"  editable="true" sortable="false" />
									
									 <psjg:gridColumn name="ENTITY_ALIAS" index="ENTITY_ALIAS" id="entAliasAggr" title="ent Alias"  colType="text"  editable="true" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="ENTITY_DB_NAME" id="entFieldDbAggr"  index="ENTITY_DB_NAME"  colType="text" title="ent db name"  editable="true" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="FIELD_DB_NAME" id="fieldDbAggr" index="FIELD_DB_NAME"   colType="text" title="field db name"   editable="true" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="type" id="typeAggr"  index="type"  colType="text" title="type"  editable="true" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="PARENT_INDEX" id="parentIdAggr"  index="PARENT_INDEX"  colType="number" title="parent Id"  editable="true" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="index" id="indexAggr"  index="index"  colType="number" title="ID"  editable="true" sortable="false" key="true" hidden="true"/>
									 <psjg:gridColumn name="aggrFeId" id="FeIdAggr"  index="aggrFeId"  colType="number" title="Field Id"  editable="true" sortable="false" hidden="true"/>
									 <psjg:gridColumn name="FIELD_DATA_TYPE" id="feDataTypeAggr"  index="FIELD_DATA_TYPE"  colType="text" title="Data type"  editable="true" sortable="false" hidden="true"/>
				      
		    		       </psjg:grid> 
		       
						</div>
		
		<ps:form id="aggrGridform_${_pageRef}" action="queryDesign_validateAggr" namespace="/path/designer">
			<ps:hidden name="updates" id="updatesAggr_${_pageRef}"></ps:hidden>
			<ps:hidden name="parId" id="parId_${_pageRef}"></ps:hidden>
			<ps:hidden name="_pageRef" id="${_pageRef}"></ps:hidden>
	   </ps:form>
	</body>
</html>


