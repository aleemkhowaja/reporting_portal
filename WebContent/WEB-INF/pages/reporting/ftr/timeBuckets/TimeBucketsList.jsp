<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<ps:set name="Missing_report_reference" 	value="%{getEscText('timeBucket.missingReference')}"/>
<ps:set name="Missing_cuurency_code" 	value="%{getEscText('timeBucket.missingCurrency')}"/>
<ps:set name="Missing_report_reference_currency_code" 	value="%{getEscText('timeBucket.missingReferenceAndCurrency')}"/>
<ps:set name="fill_Line" 	value="%{getEscText('timeBuckets.fillLine')}"/>
<ps:set name="duplicate_Line" 	value="%{getEscText('timeBuckets.duplicateLine')}"/>

<html>
<head>
<title><ps:text name="reporting.timeBuckets"/></title>

	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
	
  
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
	
<style>
.ui-jqgrid tr.jqgrow td {white-space: normal}
</style>
<script type="text/javascript">

var missingReportReference		= '<ps:property value="Missing_report_reference"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingCurrecnyCode		    = '<ps:property value="Missing_cuurency_code"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingReportAndCurrency 	= '<ps:property value="Missing_report_reference_currency_code"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingLine   =           	'<ps:property value="fill_Line"  escapeHtml="false" escapeJavaScript="true"/>'; 
var duplicateline =    '<ps:property value="duplicate_Line"  escapeHtml="false" escapeJavaScript="true"/>'; 
 
$(document).ready(function() 
{
	$.struts2_jquery.require("TimeBuckets.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/timeBuckets/");
	$("#timeBucketsTable_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	{
			 $("#timeBucketsForm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("")
	});
});

</script>
</head>

<body> 
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

<div>
		<table width="100%" cellpadding="0">
			<tr>
				<td  width="100%" >
				 	<ps:url id="urlGrid" action="timeBucketsAction_loadGrid" namespace="/path/timeBuckets">
				 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
				 	</ps:url>   
					<psjg:grid
						id="timeBucketsTable_${_pageRef}"
						dataType="json" 
						href="%{urlGrid}" 
						gridModel="gridModel"
						pager="true" 
						navigator="true" 
						navigatorSearch="true"
						navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
						navigatorEdit="false" 
						navigatorAdd="false" 
						navigatorDelete="true"
						caption =" "
						navigatorRefresh="true" 
						viewrecords="true" 
						rowNum="5"
						rowList="5,10,15,20"   	
						delfunc="deleteTimeBuckets"
						addfunc="addNewTimeBuckets"
						ondblclick="editTimeBuckets()"
						sortable="true">
						
						<psjg:gridColumn name="ftrtimebucketsVO.COMP_CODE" index="ftrtimebucketsVO.COMP_CODE" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" colType="number" title="%{getText('reporting.companyCode')}" editable="false" sortable="true" search="true" hidden="true"/>
						<psjg:gridColumn name="ftrtimebucketsVO.REP_REF" index="ftrtimebucketsVO.REP_REF" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" colType="text" title="%{getText('designer.reportReference')}" editable="false" sortable="true" search="true"/>
						<psjg:gridColumn name="ftrtimebucketsVO.CURRENCY_CODE" index="ftrtimebucketsVO.CURRENCY_CODE" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" colType="number" title="%{getText('reporting.lkpCurrCode')}" editable="false" sortable="true" search="true"/>
					</psjg:grid>
				</td>
			</tr>
		</table>
	</div>

<div>
	<h1 class="headerPortionContent ui-widget ui-state-default">
		<ps:label value="%{getText('timeBuckets.timeBucketsForm')}"/>
	</h1>
</div>

<div id="tbDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="headerPortion">
	<%@include file="TimeBucketsMaint.jsp"%>
</div> 

	<div>
				<ps:url id="urlTag" action="timeBucketsAction_retrieveTimeBucketsListDetails"
					namespace="/path/timeBuckets">
					<ps:param name="_pageRef" value="_pageRef"></ps:param>
				</ps:url>

				<psjg:grid  id="timeBucketsDetailsGrid_${_pageRef}" 
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
		        	addfunc="addTimeBucketsDetails" 
		        	delfunc="deleteTimeBucketsDetails">
         	
         	<psjg:gridColumn name="ftrtimebucketsVO.COMP_CODE" index="ftrtimebucketsVO.COMP_CODE" id="comp_code" title="%{getText('reporting.companyCode')}" colType="number" editable="true" sortable="true" hidden="true"/>
			<psjg:gridColumn name="ftrtimebucketsVO.REP_REF" index="ftrtimebucketsVO.REP_REF" id="rep_ref" title="%{getText('designer.reportReference')}" colType="text" editable="true" sortable="true" hidden="true"/>
			<psjg:gridColumn name="ftrtimebucketsVO.CURRENCY_CODE" index="ftrtimebucketsVO.CURRENCY_CODE" id="currency_code" title="%{getText('reporting.lkpCurrCode')}" colType="number" editable="true" sortable="true" hidden="true"/>
	        <psjg:gridColumn name="ftrtimebucketsVO.LINE_NO" index="ftrtimebucketsVO.LINE_NO" id="line_no" title="%{getText('line.lineNbr')}" colType="number"  editable="false" sortable="true" hidden="true"/>
	        <psjg:gridColumn name="ftrtimebucketsVO.RATE_DESC" index="ftrtimebucketsVO.RATE_DESC" id="rate_desc" title="%{getText('reporting.description')}" colType="text"  editable="true" sortable="true" required="true" editoptions="{maxlength:'40'}"/>
	        <psjg:gridColumn name="ftrtimebucketsVO.NO_OF_DAYS" index="ftrtimebucketsVO.NO_OF_DAYS" id="no_of_days" title="%{getText('reporting.noOfDays')}" minValue="0" colType="number"  editable="true" sortable="false" required="true" editoptions="{maxlength:'10'}"/>
	     	<psjg:gridColumn name="ftrtimebucketsVO.LIMIT" index="ftrtimebucketsVO.LIMIT" id="limit" title="%{getText('reporting.limit')}" colType="number"  minValue="0" editable="true" sortable="false" required="true" editoptions="{maxlength:'21'}" nbFormat="#.###" maxLenBeforeDec="17"/>
    	</psjg:grid> 
	</div>
<div>
	<table border="0" width="100%">
		<tr>
			<td>
				<ptt:toolBar  id="timeBucketsBar"> 
					<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="saveTimeBuckets()" >
						<ps:label key="reporting.save"></ps:label>
					</psj:submit>
				</ptt:toolBar>	
			</td>
		</tr>
	</table>
</div>
</body>

<script type="text/javascript">
$("#timeBucketsTable_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>
 
</html>