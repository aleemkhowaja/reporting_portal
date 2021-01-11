<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<script type="text/javascript">


$(document).ready(function() 
{

	$.struts2_jquery.require("TemplateMain.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/template/");
	$.struts2_jquery.require("MismatchReportList.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/template/");
	var isDisabledExprTab=$.inArray(2, $("#tabs").tabs("option", "disabled"));
	$("#mismatchReportsGrid_"+_pageRef).subscribe('disEnExprTab', function(event,data) {
		var lineCount=$("#mismatchReportsGrid_"+_pageRef).jqGrid('getGridParam','records');	
		var tempCodeWithLineNb=$("#tempCodeWithLineNbMis_"+_pageRef).val();
			if(tempCodeWithLineNb!="0~0")
			{
				var zSrc="${pageContext.request.contextPath}/path/templateMaintReport/checkIfExprLine.action?_pageRef="+_pageRef+"&templateCode="+tempCodeWithLineNb.split("~")[0]+"&lineNumber="+tempCodeWithLineNb.split("~")[1];
				params ={};
				
				$.getJSON(zSrc, params, function( data2, status, xhr ) 
				{
					var retVal=data2['exprOrGLByLineCO']['exprOrGLByLine'];
					allLineCount=0;
					
					for (var i=0;i<retVal.length;i++)
					{
						if(retVal[i]=="1")
						{
							allLineCount=1;	
							break;
						}	
					}
					if(lineCount ==0 && allLineCount==0 && isDisabledExprTab=="0") //when deleting the last record
					{ 
						$("#tabs").tabs('enable', 2);
					}
					else if(allLineCount==1)
					{
					  $("#tabs").tabs( {disabled : [ 2 ]});
					}
				});
			}
	});
	
});


</script>
<ps:textfield name="tempCodeWithLineNb" cssStyle="display:none"  id="tempCodeWithLineNbMis_${_pageRef}"></ps:textfield>
   <ps:url id="urlMismatchGrid" action="loadMismatchGrid?_pageRef=${_pageRef}&tempCodeWithLineNb=${tempCodeWithLineNb}" namespace="/path/templateMaintReport">
   </ps:url> 
	<ps:collapsgroup id="sort15">
		<ps:collapspanel id="misRepGridDiv_${_pageRef}"  key="template.mismatch">
    <psjg:grid
    	id="mismatchReportsGrid_${_pageRef}"
    	dataType="json" 
    	href="%{urlMismatchGrid}" 
    	gridModel="gridModel"
		pager="true" navigator="true" navigatorSearch="false"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		navigatorEdit="false" navigatorAdd="true" navigatorDelete="true"
		caption =" "
		navigatorRefresh="true" viewrecords="true" rowNum="5"
		rowList="5,10,15,20"   	
    	delfunc="deleteMismatch"
    	editinline="false"
    	ondblclick="fillMismatchForm()"
    	addfunc="addMismatch"
    	editurl="%{urlMismatchGrid}"
    	onCompleteTopics="disEnExprTab"
		sortable="true" 
		shrinkToFit="true">
      	<psjg:gridColumn name="ftrMissRepVO.TMPLT_CODE1" 	index="ftrMissRepVO.TMPLT_CODE1"	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="%{getText('reporting.templateCode')}" 			editable="false"  sortable="true" search="false"/>  
      	<psjg:gridColumn name="ftrMissRepVO.TMPLT_CODE" 	index="ftrMissRepVO.TMPLT_CODE"	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="TMPLT_CODE" hidden="true"			editable="false"  sortable="true" search="false"/>  
      	<psjg:gridColumn name="TMPLT_NAME"					index="TMPLT_NAME"  				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text" 	title="%{getText('reporting.description')}"   sortable="true"   hidden="false"   search="false" />	
      	<psjg:gridColumn name="newLineNumber" 				title="tempmlate line number" colType="number" 	editable="false" sortable="false" hidden = "true"/>
      	<psjg:gridColumn name="ftrMissRepVO.SUB_LINE_NO"	index="ftrMissRepVO.SUB_LINE_NO"	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="%{getText('reporting.subLineNb')}"			editable="true"   hidden="false"   sortable="true" search="false"/>  
      	<psjg:gridColumn name="LINE_NAME"					index="LINE_NAME"					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"   title="%{getText('expr.expLineNb')}"			editable="false"  sortable="true" search="false"/>  
      	<psjg:gridColumn name="ftrMissRepVO.LINE_NO"		index="ftrMissRepVO.LINE_NO"					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="LINE_NO" 			editable="false"  hidden="true" search="false"/>  
      	<psjg:gridColumn name="concatKey"					index="concatKey"					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="Name" 			editable="false"  hidden="true" search="false"/>  
      </psjg:grid>
</ps:collapspanel>


<div id="mismatchDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="headerPortion">
	<%@include file="MismatchReportFrm.jsp"%>
</div>
</ps:collapsgroup>
<script>
$("#gview_mismatchReportsGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide(); 
</script>