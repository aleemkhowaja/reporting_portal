<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<script>
var procParamOutTypeCmbUrl="${pageContext.request.contextPath}/path/designer/proc_loadProcParamOutTypeCmb";

	$("#procParamsGrid"+"_"+_pageRef).subscribe('checkInOut', function(event, data) {
		checkInOut();
	});
	
function checkInOut()
{
	var rowId 			= $("#procParamsGrid"+"_"+_pageRef).jqGrid('getGridParam','selrow');	
	var myObject     	= $("#procParamsGrid"+"_"+_pageRef).jqGrid('getRowData',rowId);
	//2 columns are mandatory
	paramInOut = myObject["PARAM_IN_OUT"]	
	outParamType = myObject["OUTPUT_PARAM_TYPE"]	
	if(paramInOut=="IN")
	{
		 $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"OUTPUT_PARAM_TYPE",true);	    
		 $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"SUCCESS_VALUE",true);
	  $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellValue",rowId,"SUCCESS_VALUE","");
	}
	else
	{
		
		 $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"OUTPUT_PARAM_TYPE",false);
		 if(outParamType=="1")
		 {
	      $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"SUCCESS_VALUE",false);
		 }	
		 else 
		  {
	      $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"SUCCESS_VALUE",true);
	       $("#procParamsGrid"+"_"+_pageRef).jqGrid("setCellValue",rowId,"SUCCESS_VALUE","");
		 }
		 
	}
}
</script>
	 <div>
		     <ps:url var="urlProcParams" action="proc_loadProcParamsList" namespace="/path/designer" escapeAmp="false">
	   			<ps:param  name="PROCED_ID" value="PROC_ID"></ps:param>
	   			<ps:param  name="PROCED_NAME" value="PROC_NAME"></ps:param>
	   			<ps:param  name="_pageRef" value="_pageRef"></ps:param>
   		</ps:url>
			<psjg:grid  id="procParamsGrid_${_pageRef}" 
				gridModel="gridModel"
				dataType="json"
				href="%{urlProcParams}"
				pager="false" 
				navigator="false"
				editinline="true"
  				editurl="%{urlProcParams}"
				navigatorAdd="false"
				navigatorDelete="false"
				navigatorEdit="false"
				navigatorRefresh="false"
				navigatorView="false"
				navigatorSearch="false"
				onEditInlineBeforeTopics = "checkInOut"
         		rowNum="100" 
   	  			>
			   	 <psjg:gridColumn name="PARAM_NAME" index="PARAM_NAME" id="PARAM_NAME"   title="%{getText('proc.paramName')}" colType="text"  editable="false" sortable="false" />
			   	 <psjg:gridColumn name="PARAM_TYPE" index="PARAM_TYPE" id="PARAM_TYPE"   title="%{getText('proc.paramType')}" colType="text"  editable="false" sortable="false" />
			   	 <psjg:gridColumn name="PARAM_IN_OUT" index="PARAM_IN_OUT" id="PARAM_IN_OUT"   title="%{getText('proc.paramInOut')}" colType="text"  editable="false" sortable="false" />
				 <psjg:gridColumn name="OUTPUT_PARAM_TYPE" index="OUTPUT_PARAM_TYPE" id="OUTPUT_PARAM_TYPE"   title="%{getText('proc.outParamType')}" colType="select"  edittype="select"  editable="true" sortable="false" formatter="select" editoptions="{ value:function() {  return loadCombo(procParamOutTypeCmbUrl,'procParamOutTypeCmb', 'VALUE_CODE', 'VALUE_DESC');},dataEvents: [{ type: 'change', fn: function(e) {checkInOut();} }]}"/>
				 <psjg:gridColumn name="SUCCESS_VALUE" index="SUCCESS_VALUE" id="SUCCESS_VALUE"   title="%{getText('proc.successValue')}" colType="text"  editable="true" sortable="false" />
			    <psjg:gridColumn id="QRY_PARAM_NAME" index="QRY_PARAM_NAME" name="QRY_PARAM_NAME" 
			     title="%{getText('proc.qryParamName')}" editable="true" sortable="false" colType="liveSearch"
				 dataAction="${pageContext.request.contextPath}/path/designer/proc_qryParamNameLkp.action?_pageRef=${_pageRef}"
				 editoptions="{ readonly: 'readonly'}"
				 resultList="ARGUMENT_LABEL:QRY_PARAM_NAME_lookuptxt,index:PARAM_ID" 
				 paramList="PARAMETER_TYPE:PARAM_TYPE,code1:REPORT_ID" />
			   
			   	 <psjg:gridColumn name="PARAM_ORDER" index="PARAM_ORDER" id="PARAM_ORDER"   title="%{getText('reporting.order')}" colType="number"  editable="false" sortable="false" />
				 
				 <psjg:gridColumn name="PARAM_ID" index="PARAM_ID" id="PARAM_ID"   title="paramId" colType="number"  editable="false" sortable="false" hidden="true"/>
			   	 <psjg:gridColumn name="PROC_ID" index="PROC_ID" id="PROC_ID"   title="procId" colType="number"  editable="false" sortable="false" hidden="true"/>
			   	 <psjg:gridColumn name="REPORT_ID" index="REPORT_ID" id="REPORT_ID"   title="reportId" colType="number"  editable="false" sortable="false" hidden="true"/>
			   	 <psjg:gridColumn name="PROC_NAME" index="PROC_NAME" id="PROC_NAME"   title="%{getText('proc.procName')}" colType="text"  editable="false" sortable="false" hidden="true"/>
			   	 
   		       </psjg:grid> 
		</div>

	<script type="text/javascript"> 
     $("#procParamsGrid_"+_pageRef).jqGrid("setGridWidth",780);	
     
     
	</script>
