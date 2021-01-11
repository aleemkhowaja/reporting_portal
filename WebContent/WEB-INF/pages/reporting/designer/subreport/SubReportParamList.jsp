<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 

<script type="text/javascript">
drpDwnCmbUrl="${pageContext.request.contextPath}/path/designer/subrep_loadDrpDwnCombo.action?_pageRef="+_pageRef+"&outputLayout="+$("#outLayout_"+_pageRef).val();
$(document).ready(function() 
{

});
	 $("#subRepParamGridId_"+_pageRef).subscribe('enableDisableParamsCells', function(event,data) 
	 {
		rowId = (event["originalEvent"])["id"];
		myObject = $("#subRepParamGridId_"+_pageRef).jqGrid('getRowData',rowId);
		var argType=myObject["MAP_PARAM_TYPE"];
		applyEnableDisableParamsCells(argType,rowId)
	 });
		
	  function checkHypParamCell(obj)
	  {
	  //console.log("checkHypParamCell ")
	 		var rowId = $("#subRepParamGridId_"+_pageRef).jqGrid('getGridParam','selrow');
	  		applyEnableDisableParamsCells(obj.value,rowId)
	  }
  
    function applyEnableDisableParamsCells(argType,rowId)
    {
    	var $tbl = $("#subRepParamGridId_"+_pageRef);
   		var VALUE_COLUMN = $tbl.jqGrid('getCellInputElt',rowId,'VALUE_COLUMN');
   		var VALUE_ARGUMENT = $tbl.jqGrid('getCellInputElt',rowId,'VALUE_ARGUMENT');
    	if(argType==0) //static
    	{
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_COLUMN','','true');
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_ARGUMENT','','true');
    		//document.getElementById(rowId+"_LINKED_FIELD_INDEX").value="";
   			var VALUE_STATIC = $tbl.jqGrid('getCell',rowId,'VALUE_STATIC');
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_STATIC',VALUE_STATIC ,'false');
    		
    		
    	}
    	else if(argType==1) //field
    	{
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_STATIC','','true');
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_ARGUMENT','','true');
    		var VALUE_COLUMN = $tbl.jqGrid('getCell',rowId,'VALUE_COLUMN');
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_COLUMN',VALUE_COLUMN,'false');
    		$(document.getElementById(rowId + "_VALUE_COLUMN_lookuptxt_subRepParamGridId_"+_pageRef)).attr('readonly','readonly');
    	}
    	else if(argType==2)//session
    	{
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_STATIC','','true');
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_COLUMN','','true');
    		var VALUE_ARGUMENT = $tbl.jqGrid('getCell',rowId,'VALUE_ARGUMENT');
    		//document.getElementById(rowId+"_LINKED_FIELD_INDEX").value="";
    		$("#subRepParamGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'VALUE_ARGUMENT',VALUE_ARGUMENT,'false');
    		$(document.getElementById(rowId + "_VALUE_ARGUMENT_lookuptxt_subRepParamGridId_"+_pageRef)).attr('readonly','readonly');
    	}
    	
    }
      
</script>
  <ps:hidden id="outLayout_${_pageRef}" name="outputLayout"/>
	<ps:url id="urlGrid" action="subrep_loadSubRepParams" namespace="/path/designer" escapeAmp="false">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		<ps:param name="subRepName" value="subRepName"></ps:param>
		<ps:param name="subRepId" value="subRepId"></ps:param>
		<ps:param name="subRepExp" value="subRepExp"></ps:param>
		
	</ps:url>   
    <psjg:grid
    	id="subRepParamGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	editurl="%{urlGrid}"
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="5"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	navigator="true"	
    	navigatorAdd="false" 
    	navigatorEdit="false"
    	editinline="true"
    	navigatorRefresh="false"
    	navigatorView="false"
    	navigatorSearch="false"
    	navigatorDelete="false"
    	onEditInlineBeforeTopics="enableDisableParamsCells"
     >
     
     <psjg:gridColumn name="ARGUMENT_NAME" id="ARGUMENT_NAME" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="ARGUMENT_NAME"  colType="text"  title="Argument Name" sortable="true"  width="50"  hidden="false" editable="false"/>
     <psjg:gridColumn name="ARGUMENT_TYPE" id="ARGUMENT_TYPE" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="MAP_PARAM_TYPE"  colType="text"  title="ARGUMENT_TYPE" sortable="true"  width="50"  hidden="false" editable="false"/>
     <psjg:gridColumn name="MAP_PARAM_TYPE" index="MAP_PARAM_TYPE" id="MAP_PARAM_TYPE" title="%{getText('reporting.type')}" colType="select" editable="true" edittype="select" formatter="select" 
     editoptions="{ value:function() {  return loadCombo(drpDwnCmbUrl,'drpDwnCmbList', 'VALUE_CODE', 'VALUE_DESC');}
     ,dataEvents: [{ type: 'change', fn: function() { checkHypParamCell(this) } }]}"          sortable="false" width="50" />
	 <psjg:gridColumn name="VALUE_STATIC" index="VALUE_STATIC" id="VALUE_STATIC"   title="%{getText('hyperLink.static')}" colType="text"  editable="true" sortable="false" width="50" />
	 <psjg:gridColumn name="VALUE_COLUMN" index="VALUE_COLUMN" id="VALUE_COLUMN"  
		     	 	  editoptions="{ readonly: 'readonly'}"
		     	 	  title="%{getText('hyperLink.colName')}" 
		     	 	  editable="true" 
		     	 	  sortable="false" 
		     	 	  colType="liveSearch" searchElement="FIELD_ALIAS" paramList="updates:MAP_PARAM_TYPE,updates1:ARGUMENT_TYPE"
		     	 	  dataAction="${pageContext.request.contextPath}/path/designer/subrep_retColumnsByType.action"
		     	 	  resultList="FIELD_ALIAS:VALUE_COLUMN_lookuptxt" 
		     	 	  width="50"
		     	 	  hidden="%{hideColVal}"/>
	 <psjg:gridColumn name="VALUE_ARGUMENT" index="VALUE_ARGUMENT" id="VALUE_ARGUMENT"  
		     	 	  editoptions="{ readonly: 'readonly'}"
		     	 	  title="%{getText('designer.arguments')}" 
		     	 	  editable="true" 
		     	 	  sortable="false" 
		     	 	  colType="liveSearch" searchElement="ARGUMENT_NAME" paramList="updates:MAP_PARAM_TYPE,updates1:ARGUMENT_TYPE"
		     	 	  dataAction="${pageContext.request.contextPath}/path/designer/subrep_retColumnsByType.action"
		     	 	  resultList="ARGUMENT_NAME:VALUE_ARGUMENT_lookuptxt" 
		     	 	  width="50"/> 	     	 	   				
     
    </psjg:grid>
    
  <div id="sort1FltrCrt">   </div>
  
		<script type="text/javascript">
	
       $('#subRepParamGridId_'+_pageRef ).jqGrid("setColProp","FIELD_ALIAS",{width:380});
       $('#subRepParamGridId_'+_pageRef ).jqGrid("setGridWidth",960);	
        
	</script>  

