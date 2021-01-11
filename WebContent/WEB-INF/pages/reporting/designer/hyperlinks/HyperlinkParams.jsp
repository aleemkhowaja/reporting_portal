<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>


<script type="text/javascript">

		$("#hypParamsGrid_"+_pageRef).subscribe('enableDisableParamsCells', function(event,data) 
		{
			rowId = (event["originalEvent"])["id"];
			applyEnableDisableParamsCells(rowId)
		});
	

    	
    function applyEnableDisableParamsCells(rowId)
    {
    	var $tbl = $("#hypParamsGrid_"+_pageRef);
   		var VALUE_COLUMN = $tbl.jqGrid('getCellInputElt',rowId,'hyperlinkVO.VALUE_COLUMN');
   		var VALUE_SESSION = $tbl.jqGrid('getCellInputElt',rowId,'hyperlinkVO.VALUE_SESSION');
   		var argType=$tbl.jqGrid('getCell', rowId, 'hyperlinkVO.ARGUMENT_TYPE');
    	if(argType==0) //static
    	{
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_COLUMN','','true');
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_SESSION','','true');
    		document.getElementById(rowId+"_hyperlinkVO.LINKED_FIELD_INDEX").value="";
   			var VALUE_STATIC = $tbl.jqGrid('getCell',rowId,'hyperlinkVO.VALUE_STATIC');
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_STATIC',VALUE_STATIC ,'false');
    		
    		
    	}
    	else if(argType==1) //field
    	{
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_STATIC','','true');
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_SESSION','','true');
    		var VALUE_COLUMN = $tbl.jqGrid('getCell',rowId,'hyperlinkVO.VALUE_COLUMN');
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_COLUMN',VALUE_COLUMN,'false');
    		$(document.getElementById(rowId + "_hyperlinkVO.VALUE_COLUMN_lookuptxt_hypParamsGrid_"+_pageRef)).attr('readonly','readonly');
    	}
    	else if(argType==2)//session
    	{
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_STATIC','','true');
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_COLUMN','','true');
    		var VALUE_SESSION = $tbl.jqGrid('getCell',rowId,'hyperlinkVO.VALUE_SESSION');
    		document.getElementById(rowId+"_hyperlinkVO.LINKED_FIELD_INDEX").value="";
    		$("#hypParamsGrid_"+_pageRef).jqGrid('setCellValue', rowId, 'hyperlinkVO.VALUE_SESSION',VALUE_SESSION,'false');
    		$(document.getElementById(rowId + "_hyperlinkVO.VALUE_SESSION_lookuptxt_hypParamsGrid_"+_pageRef)).attr('readonly','readonly');
    	}
    	
    }	
    
  
  function checkHypParamCell(obj)
  {
  console.log("checkHypParamCell ")
 		var rowId = $("#hypParamsGrid_"+_pageRef).jqGrid('getGridParam','selrow');
  		applyEnableDisableParamsCells(rowId)
  }


</script>

		  <div> 
		     <ps:url var="urlHypParams" action="hyperLinks_loadHypParamsList" namespace="/path/designer" escapeAmp="false">
	   			<ps:param  name="linkRepId" value="linkRepId"></ps:param>
	   			<ps:param  name="repId" value="repId"></ps:param>
	   			<ps:param  name="_pageRef" value="_pageRef"></ps:param>
	   			<ps:param  name="feIndex" value="feIndex"></ps:param>
	   			<ps:param  name="colName" value="colName"></ps:param>
	   		</ps:url>
			<psjg:grid  id="hypParamsGrid_${_pageRef}" 
				gridModel="gridModel"
				dataType="json"
				href="%{urlHypParams}"
				pager="false" 
				navigator="false"
				editinline="true"
	 			editurl="%{urlHypParams}"
				navigatorAdd="false"
				navigatorDelete="false"
				navigatorEdit="false"
				navigatorRefresh="false"
				navigatorView="false"
				navigatorSearch="false"
	        	rowNum="100" 
	  	  		rowList="5,10,15,20"
	  	  		onEditInlineBeforeTopics="enableDisableParamsCells"
	  	  	>
			   	<psjg:gridColumn name="ARGUMENT_NAME" index="ARGUMENT_NAME" id="ARGUMENT_NAME"   title="%{getText('proc.paramName')}" colType="text"  editable="false" sortable="false" />
			   	<psjg:gridColumn name="PARAM_TYPE" index="PARAM_TYPE" id="PARAM_TYPE"   title="%{getText('proc.paramType')}" colType="text"  editable="false" sortable="false" />
			    <psjg:gridColumn name="hyperlinkVO.ARGUMENT_TYPE" index="ARGUMENT_TYPE" id="ARGUMENT_TYPE" title="%{getText('reporting.type')}" colType="select" editable="true" edittype="select" formatter="select" editoptions="{value:'0:%{getText('hyperLink.static')};1:%{getText('hyperLink.field')};2:%{getText('hyperLink.session')}',dataEvents: [{ type: 'change', fn: function() { checkHypParamCell(this) } }]}" sortable="false" />
			    <psjg:gridColumn name="hyperlinkVO.VALUE_STATIC" index="VALUE_STATIC" id="VALUE_STATIC"   title="%{getText('hyperLink.static')}" colType="text"  editable="true" sortable="false" />
				<psjg:gridColumn  name="hyperlinkVO.VALUE_COLUMN" id="VALUE_COLUMN" index="VALUE_COLUMN"
								  editoptions="{ readonly: 'readonly'}"
								  title="%{getText('hyperLink.colName')}" editable="true" sortable="false" 
								  colType="liveSearch" searchElement="FIELD_ALIAS" paramList="updates:hyperlinkVO.REPORT_ID"
								  dataAction="${pageContext.request.contextPath}/path/designer/queryDesign_retColumnsByRepId.action" 
								  resultList="index:hyperlinkVO.LINKED_FIELD_INDEX" />  
				<psjg:gridColumn  name="hyperlinkVO.VALUE_SESSION" id="VALUE_SESSION" index="VALUE_SESSION"
								  editoptions="{ readonly: 'readonly'}"
								  title="%{getText('hyperLink.session')}" editable="true" sortable="false" 
								  colType="liveSearch" searchElement="ATTRIBUTE_NAME"
								  dataAction="${pageContext.request.contextPath}/path/designer/queryDesign_fillSessionLst.action"/> 			  
	  		  
	  		    <psjg:gridColumn name="hyperlinkVO.COLUMN_NAME"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="COLUMN_NAME"      colType="text"  title="COLUMN_NAME"      sortable="true" hidden="true"/>
	  	  		<psjg:gridColumn name="hyperlinkVO.FIELD_INDEX"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="FIELD_INDEX"      colType="number"  title="FIELD_INDEX"      sortable="true" hidden="true"/>
	  	  		<psjg:gridColumn name="hyperlinkVO.REPORT_ID"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="REPORT_ID"      colType="number"  title="REPORT_ID"      sortable="true" hidden="true"/>
	  	  		<psjg:gridColumn name="hyperlinkVO.LINKED_REPORT_ID"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="LINKED_REPORT_ID"      colType="number"  title="LINKED_REPORT_ID"      sortable="true" hidden="true"/>
	  	  		<psjg:gridColumn name="hyperlinkVO.ARGUMENT_ID" index="ARGUMENT_ID" id="ARGUMENT_ID"   title="ARGUMENT ID" colType="number"  editable="true" sortable="false"  hidden="true"/>
	  		    <psjg:gridColumn name="hyperlinkVO.LINKED_FIELD_INDEX"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="LINKED_FIELD_INDEX"      colType="number"  title="LINKED_FIELD_INDEX"      sortable="true" hidden="true"/>
	  		  <psjg:gridColumn name="DATE_UPDATED_STR"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="false"  index="DATE_UPDATED_STR"      colType="text"  title="DATE_UPDATED_STR"      sortable="true" hidden="true"/>
	  		  </psjg:grid> 
		</div>


	<script type="text/javascript"> 
     $("#hypParamsGrid_"+_pageRef).jqGrid("setGridWidth",750);	
	</script>
