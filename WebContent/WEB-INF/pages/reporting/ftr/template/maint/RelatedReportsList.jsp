<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>
<ps:set name="checkProgRefAlert_var" 	value="%{getEscText('fcr.checkProgRefAlert')}"/>
<ps:set name="orderAlreadyInUse_var" 	value="%{getEscText('template.orderAlreadyInUse')}"/>


<script type="text/javascript">
checkProgRefAlert = '<ps:property value="checkProgRefAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
orderAlreadyInUse = '<ps:property value="orderAlreadyInUse_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
$(document).ready(
		function() {

			$.struts2_jquery.require("TemplateMain.js", null,
					jQuery.contextPath + "/path/js/reporting/ftr/template/");
		});
		
var oldProgRef;	
var oldTemplateOrder;
var oldReportName;

	
$("#relatedReportsGrid_"+_pageRef).subscribe('saveOldValues', function(event, data) {
	saveOldValues();
});

function saveOldValues()
{
	var currentRowId 	= $("#relatedReportsGrid_"+_pageRef).jqGrid('getGridParam','selrow');
    oldProgRef 			= $("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',currentRowId)["ftrTmplRefVO.REPORT_REF"]; 
    oldTemplateOrder 	= $("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',currentRowId)["ftrTmplRefVO.TEMPLATE_ORDER"]; 
    oldReportName  		= $("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',currentRowId)["reportName"]; 
}

function checkDuplicationProgRef()
{
		var currentRowId = $("#relatedReportsGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		var currentProgRef = $("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',currentRowId)["ftrTmplRefVO.REPORT_REF"]; 
	  	var allRowIds =  $("#relatedReportsGrid_"+_pageRef).jqGrid('getDataIDs');
	  	var nbOccurences = 0;
	  	if(allRowIds)
	  	{
	  		
		  	for (var i =0;i<allRowIds.length;i++)
		    {
		    	llRowId=allRowIds[i];
		    	myObject =$("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',llRowId);
		    	if(myObject["ftrTmplRefVO.REPORT_REF"]==currentProgRef)
		    	{
			    	 nbOccurences++;
		    	}	    	
		    }
		    if(nbOccurences>1)
		    {
		    	_showErrorMsg(checkProgRefAlert,error_msg_title,300,100);
				 $("#relatedReportsGrid_"+_pageRef).jqGrid("setCellValue",currentRowId,"reportName",oldReportName);  
			     $("#relatedReportsGrid_"+_pageRef).jqGrid("setCellValue",currentRowId,"ftrTmplRefVO.REPORT_REF",oldProgRef);  
				 $("#relatedReportsGrid_"+_pageRef).jqGrid("setCellValue",currentRowId,"ftrTmplRefVO.TEMPLATE_ORDER",oldTemplateOrder); 
				 $("#relatedReportsGrid_"+_pageRef).jqGrid("getCell",currentRowId,"reportName")
				 return;
		    }
	    }
}		

	

</script>
<ps:url id="urlGrid"
	action="relatedRepGridUrl?_pageRef=${_pageRef}&relatedRepCode=${relatedRepCode}"
	namespace="/path/templateMaintReport">
	<ps:param name="relatedRepCode" value="glstmpltCO.glstmpltVO.CODE"></ps:param>
</ps:url>
<div>
  <table width="100%" cellpadding="0">
  <tr>
  <td  width="100%" >  
    <psjg:grid
    	id="relatedReportsGrid_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
		pager="true" navigator="true" navigatorSearch="true"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		navigatorEdit="false" navigatorAdd="true" navigatorDelete="true"
		caption =" "
		navigatorRefresh="true" viewrecords="true" rowNum="5"
		rowList="5,10,15,20"   	
    	delfunc="deleteRelRepLine"
    	editinline="true"
    	addfunc="addRelRep"
    	editurl="%{urlGrid}"
    	ondblclick=""
    	onCompleteTopics="emptyCrtTrx"
    	onEditInlineBeforeTopics="saveOldValues"
		sortable="true" 
		shrinkToFit="true"
		>
      	<psjg:gridColumn name="ftrTmplRefVO.REPORT_REF" required="true"
      		index="ftrTmplRefVO.REPORT_REF" 
		  	title="%{getText('template.relatedReports.reference')}" id="ftrTmplRefVO.REPORT_REF"
		  	editable="true" 
		  	sortable="true" 
		  	colType="liveSearch" 
		  	searchElement="REPORT_NAME"
		  	editoptions="{dataEvents: [{ type: 'change', fn: function(e) { checkDuplicationProgRef();onChangeTempOrder();}}],readonly: 'readonly'}"		
		  	dataAction="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_retReports.action?_pageRef=${_pageRef}"
		  	resultList="PROG_REF:ftrTmplRefVO.REPORT_REF_lookuptxt,REPORT_NAME:reportName"/>
		<psjg:gridColumn name="reportName" 						index="reportName"	id="reportName"					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"  title="%{getText('template.relatedReports.name')}" editable="false"  sortable="true" search="false" />  
      	<psjg:gridColumn name="ftrTmplRefVO.TEMPLATE_ORDER" 	index="ftrTmplRefVO.TEMPLATE_ORDER"  id="ftrTmplRefVO.TEMPLATE_ORDER"	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="select" edittype="select"  formatter="select"  title="%{getText('template.relatedReports.templateOrder')}"  editoptions="{value:function(){return loadTemplateOrderCombo();},dataEvents: [{ type: 'change', fn: function(e) { onChangeTempOrder();}}]}"  	editable="true"  sortable="true" search="false" />
      	<psjg:gridColumn name="ftrTmplRefVO.TEMPLATE_CODE" 		index="ftrTmplRefVO.TEMPLATE_CODE"  id="ftrTmplRefVO.TEMPLATE_CODE"	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"  title="%{getText('reporting.templateCode')}" sortable="true" hidden="true" search="false" />
  		<psjg:gridColumn name="ftrTmplRefVO.APP_NAME" 			index="ftrTmplRefVO.APP_NAME"  		id="ftrTmplRefVO.APP_NAME"	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"  title="Template Order"   editoptions="{value:function(){return loadTemplateOrderCombo();}}"  	editable="false" hidden="true"  sortable="true" search="false" /> 
      	<psjg:gridColumn name="concatKey" 						index="concatKey"	id="concatKey"					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="Name" 	editable="true" hidden="true"  sortable="true" search="false"/>  
      	<psjg:gridColumn name="oldReportRef" 					index="oldReportRef"		id="oldReportRef"			searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"  title="Name" 	editable="true" hidden="true"  sortable="true" search="false"/>  
      </psjg:grid>
    </td>
    </tr>
    </table>
</div>
    <ps:form id="RelRepForm_${_pageRef}" action="saveRelatedReports" namespace="/path/templateMaintReport">
    	<ps:textfield style="display:none"  name="glstmpltCO.glstmpltVO.CODE" id="tmplCode_${_pageRef}"></ps:textfield>
		<ps:hidden name="updateRelReports" id="updateRelReports_${_pageRef}"></ps:hidden>
	</ps:form>