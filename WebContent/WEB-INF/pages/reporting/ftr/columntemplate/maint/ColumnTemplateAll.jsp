<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>

<ps:set name="runColTemplProcAlert_var" 		value="%{getEscText('tmplProc.runColTemplProcAlert')}"/>
<ps:set name="colTmplProcFailedAlert_var" 		value="%{getEscText('tmplProc.colTmplProcFailedAlert')}"/>
<ps:set name="colTmplProcDoneAlert_var" 		value="%{getEscText('tmplProc.colTmplProcDoneAlert')}"/>
<ps:set name="invalidLnNbAlert_var" 			value="%{getEscText('expr.invalidLineNbAlert')}"/>

<ps:set name="exprColTypeAlert_var" 			value="%{getEscText('col.exprColTypeAlert')}"/>
<ps:set name="positiveNbAlert_var" 				value="%{getEscText('col.positiveNbAlert')}"/>
<ps:set name="codeExistAlert_var" 				value="%{getEscText('codeExistAlert')}"/>
<ps:set name="posNbAlert_var" 					value="%{getEscText('col.posNbAlert')}"/>
<ps:set name="missingTmplCodeAlert_var" 		value="%{getEscText('col.missingTmplCodeAlert')}"/>
<ps:set name="colTemplCodeExistAlert_var" 		value="%{getEscText('codeExistAlert')}"/>
<ps:set name="compCodeAlert_var" 				value="%{getEscText('col.compCodeAlert')}"/>
<ps:set name="dateFromAlert_var" 				value="%{getEscText('col.dateFromAlert')}"/>
<ps:set name="lineNbAlert_var" 					value="%{getEscText('col.lineNbAlert')}"/>
<ps:set name="dataRequAlert_var" 				value="%{getEscText('col.dataRequAlert')}"/>
<ps:set name="runProcTitle_var" 				value="%{getEscText('template.runProcTitle')}"/>
<ps:set name="needReorganize_var" 				value="%{getEscText('template.needReorganize')}"/>
<ps:set name="reorganizeTitle_var" 				value="%{getEscText('template.reorganizeTitle')}"/>
<ps:set name="lineNumberExistAlert_var" 		value="%{getEscText('lineNumberExistAlert')}"/>
<ps:set name="invalidToDate_var" 				value="%{getEscText('expr.invalidToDate')}"/>
<ps:set name="specifyCodeCol_var" 				value="%{getEscText('template.specifyCode')}"/>
<ps:set name="curAlreadyDefined_var" 			value="%{getEscText('col.curAlreadyDefined')}"/>
<ps:set name="allowedCurrencyDaily_var" 		value="%{getEscText('col.allowedCurrencyDaily')}"/>
<ps:set name="allowedCurrencyMonthly_var" 		value="%{getEscText('col.allowedCurrencyMonthly')}"/>
<ps:set name="sureRowCurType_var" 				value="%{getEscText('col.sureRowCurType')}"/>
<ps:set name="compareToFromAlert_var" 			value="%{getEscText('gl.compareToFromAlert')}"/>
<ps:set name="missingElements_var" 				value="%{getEscText('reporting.missingElements')}"/>
<ps:set name="startingValue_var" 				value="%{getEscText('reporting.startingValue')}"/>
<ps:set name="interval_var"		 				value="%{getEscText('reporting.interval')}"/>
<ps:set name="warningengcurbriefname_var"		value="%{getEscText('reporting_warningengcurbriefname')}"/>





<html>
<head>
<title><ps:text name="reporting.timeBuckets"/></title>

	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery.wizard-tabs-0.1.js"></script> <%--Wizard tabs --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/common/js/path-toolbar.js"></script>
      	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery.wizard-tabs-0.1.js"></script> <%--Wizard tabs --%>

<script type="text/javascript">

	var runColTemplProcAlert 		= '<ps:property value="runColTemplProcAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var colTmplProcFailedAlert 		= '<ps:property value="colTmplProcFailedAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var colTmplProcDoneAlert 		= '<ps:property value="colTmplProcDoneAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var invalidLnNbAlert 			= '<ps:property value="invalidLnNbAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var exprColTypeAlert 			= '<ps:property value="exprColTypeAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var positiveNbAlert 			= '<ps:property value="positiveNbAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var codeExistAlert 				= '<ps:property value="codeExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var posNbAlert 					= '<ps:property value="posNbAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var missingTmplCodeAlert 		= '<ps:property value="missingTmplCodeAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var colTemplCodeExistAlert 		= '<ps:property value="colTemplCodeExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var compCodeAlert 				= '<ps:property value="compCodeAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var dateFromAlert 				= '<ps:property value="dateFromAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var lineNbAlert 				= '<ps:property value="lineNbAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var dataRequAlert 				= '<ps:property value="dataRequAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var runProcTitle 				= '<ps:property value="runProcTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var needReorganize 				= '<ps:property value="needReorganize_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var reorganizeTitle 			= '<ps:property value="reorganizeTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var lineNumberExistAlert 		= '<ps:property value="lineNumberExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var invalidToDate 				= '<ps:property value="invalidToDate_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var colExprRowId="";
    var specifyCodeCol    			= '<ps:property value="specifyCodeCol_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var curAlreadyDefined			= '<ps:property value="curAlreadyDefined_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var allowedCurrencyDaily		= '<ps:property value="allowedCurrencyDaily_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var allowedCurrencyMonthly		= '<ps:property value="allowedCurrencyMonthly_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var sureRowCurType				= '<ps:property value="sureRowCurType_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var compareToFromAlert			= '<ps:property value="compareToFromAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var missingElements				= '<ps:property value="missingElements_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var startingValue				= '<ps:property value="startingValue_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var interval					= '<ps:property value="interval_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var calcTypePer					="<%=RepConstantsCommon.COL_CALC_PERCENTAGE%>";
    var warningEngCurrBriefName 	= '<ps:property value="warningengcurbriefname_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


</script>

</head>

<body>	    
	<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:url id="urlGridCol" action="loadGrid" namespace="/path/colTemplateMaintReport"> 
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>
	<psjg:grid
			id="colGrid_${_pageRef}"
			gridModel="gridModel" 
			dataType="json"
			href="%{urlGridCol}"
			pager="true" 
			navigator="true" 
			navigatorSearch="true"
			navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			navigatorEdit="false" 
			navigatorAdd="false" 
			navigatorDelete="true"
			navigatorRefresh="true" 
			viewrecords="true" 
			rowNum="3" 
			caption=" "
			rowList="3,5,10,15,20"
			ondblclick="MainGridClicked()" 
			hiddengrid="true"
			sortable="true" 
			addfunc="emptyMainForm" 
			delfunc="onMainDelClicked"
			onCompleteTopics="emptyColTmplTrx"
			onGridCompleteTopics="emptyMainForm">
		
		<psjg:gridColumn name="colmnTmpltVO.CODE" index="colmnTmpltVO.CODE"
			id="CODE" width="3" title="%{getText('reporting.code')}"
			colType="number" editable="false" sortable="true" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" />
		<psjg:gridColumn name="colmnTmpltVO.BRIEF_NAME_ENG" index="colmnTmpltVO.BRIEF_NAME_ENG"
			id="BRIEF_NAME_ENG" width="7" title="%{getText('reporting.briefNameEnglish')}"
			colType="text" editable="false" sortable="true"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"/>
		<psjg:gridColumn name="colmnTmpltVO.BRIEF_NAME_ARAB" index="colmnTmpltVO.BRIEF_NAME_ARAB"
			id="BRIEF_NAME_ARAB" width="7" title="%{getText('reporting.briefNameArab')}"
			colType="text" editable="false" sortable="true"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"/>
		<psjg:gridColumn name="colmnTmpltVO.PRINT_PAPER_SIZE" index="colmnTmpltVO.PRINT_PAPER_SIZE"
			id="PRINT_PAPER_SIZE" width="7" title="PRINT_PAPER_SIZE"
			colType="text" editable="false" sortable="true" hidden="true"/>
		<psjg:gridColumn name="colmnTmpltVO.PRINT_PAPER_ORIENTATION"
			index="colmnTmpltVO.PRINT_PAPER_ORIENTATION" id="PRINT_PAPER_ORIENTATION"
			width="7" title="Orientation" colType="text" hidden="true"
			editable="false" sortable="true" />
		<psjg:gridColumn name="colmnTmpltVO.COMP_CODE" index="colmnTmpltVO.COMP_CODE"
			id="COMP_CODE" width="3" title="COMP_CODE"
			colType="number" editable="false" sortable="true" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" hidden="true" />		
	</psjg:grid>
	
	<ps:collapsgroup id='colTmplCollapsGrp_${_pageRef}'>
	
		<ps:collapspanel id='inEditColTemplateDiv_${_pageRef}'  key="reporting.colTemplDet">
				 <%@include file="ColTemplateMaint.jsp"%>
		</ps:collapspanel>
	
	<div id="reorganizeLinesDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="clearfix">
		<ps:url id="reorganizeLn"  namespace="/path/colTemplateMaintReport" action="reorganizeLines">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psj:dialog
			id="reorganizeLines_${_pageRef}" 
			href="%{reorganizeLn}"
		    autoOpen="false"
		    dataType="html"
		    title="%{getText('template.reorganizeTitle')}"
		    modal="true" 
		    width="300"
		    height="160"
		    buttons="{Cancel:function(){$( this ).dialog( 'close' );return null;},OK: function(){rep_col_submitLinesOrder();}}"/>    
	</div> 
	
	<div id="createLikeDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="clearfix">
		<ps:url id="createLikeUrlCol"  namespace="/path/colTemplateMaintReport" action="createLikeUrl">
			<ps:param name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psj:dialog
			id="createLikeDialog_${_pageRef}" 
			href="%{createLikeUrlCol}"
		    autoOpen="false"
		    dataType="html"
		    title="%{getText('template.createLike')}"
		    modal="true" 
		    width="300"
		    height="150"
		    buttons="{%{getText('reporting.cancel')}:function(){$( this ).dialog( 'close' );return null;},%{getText('reporting.ok')}: function(){saveCreateLikeCol(this);}}"
		/>    
	</div>
	
			<ps:collapspanel id='inColTemplateDetailsDiv_${_pageRef}'  key="designer.templDetLst">
			<ps:url id="gdUrl" var="urlLineTag" action="fillDetailsGrid" namespace="/path/colTemplateMaintReport">
				<ps:param name="newCode" value="0"></ps:param>
			</ps:url> 
			<psjg:grid id="detColGrid_${_pageRef}" 
				gridModel="gridModel" 
				dataType="json" href="%{urlLineTag}" pager="true" navigator="true"
				navigatorSearch="false" rowNum="15" rowList="5,10,15,20"
				viewrecords="true" navigatorEdit="false"
				navigatorRefresh="false"
				addfunc="addTemplateDetCols" 
				ondblclick="detailGridClicked()"
				delfunc="onDetDelClicked"
				onCompleteTopics="colLineGrdComplete">
				
				<psjg:gridColumn name="newLineNumber" id="newLineNumber" width="30" title="%{getText('line.lineNbr')}" 
					colType="number"  editable="false" sortable="false" key="true" hidden="false"  /> 
				<psjg:gridColumn name="colmnTmpltVO.COMP_CODE" id="COMP_CODE" width="70"
					title="COMP_CODE" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.CODE" id="CODE" width="70"
					title="CODE" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.LINE_NBR" id="LINE_NBR" width="70"
					title="%{getText('col.lineNbr')}" colType="number" editable="false" sortable="false" key="true" hidden="true"/>
				<psjg:gridColumn name="COL_TYPE_STR" id="COL_TYPE_STR" width="70"
					title="%{getText('col.colType')}" colType="text" editable="false" sortable="false" hidden="false" />
				<psjg:gridColumn name="colmnTmpltVO.COL_TYPE" id="COL_TYPE" width="70"
					title="%{getText('col.colType')}" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.BRIEF_NAME_ENG" id="BRIEF_NAME_ENG" width="70"
					title="%{getText('reporting.briefNameEnglish')}" colType="text" editable="false" sortable="false" />
				<psjg:gridColumn name="colmnTmpltVO.BRIEF_NAME_ARAB" id="BRIEF_NAME_ARAB" width="70"
					title="%{getText('reporting.briefNameArab')}" colType="text" editable="false" sortable="false" />
				<psjg:gridColumn name="colmnTmpltVO.FROM_DATE" id="FROM_DATE" width="70"
					title="%{getText('reporting.fromAsOfDate')}" colType="text" editable="false" sortable="false" hidden="false" 
					formatter="date" />
				<psjg:gridColumn name="colmnTmpltVO.TO_DATE" id="TO_DATE" width="70" formatter="date"
					title="%{getText('col.toDate')}" colType="text" editable="false" sortable="false" hidden="false" />
				<psjg:gridColumn name="colmnTmpltVO.FROM_BRANCH" id="FROM_BRANCH"
					title="%{getText('line.addDesc1')}" colType="text" hidden="true" />
				<psjg:gridColumn name="FROM_BRANCH_STR" id="FROM_BRANCH_STR" title=""
					width="70" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.TO_BRANCH" id="TO_BRANCH"
					title="%{getText('line.addDesc2')}" colType="text" hidden="true" />
				<psjg:gridColumn name="TO_BRANCH_STR" id="TO_BRANCH_STR" width="0"
					title="" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="COMP_STR" id="COMP_STR"
					width="70" title="Company Code" colType="text" editable="false" sortable="false" hidden="true"/>
				<psjg:gridColumn name="colmnTmpltVO.COMP" id="COMP"
					title="%{getText('line.round')}" colType="text" hidden="true" />
				<psjg:gridColumn name="FROM_REGION_STR" id="FROM_REGION_STR" width="90"
					title="FROM_REGION_STR" colType="text" hidden="true"/>
				<psjg:gridColumn name="colmnTmpltVO.FROM_REGION" id="FROM_REGION" width="90"
					title="colmnTmpltVO.FROM_REGION" colType="text" hidden="true" />
				<psjg:gridColumn name="TO_REGION_STR" id="TO_REGION_STR"
					title="TO_REGION_STR" colType="text" hidden="true"/>
				<psjg:gridColumn name="colmnTmpltVO.TO_REGION" id="TO_REGION"
					title="colmnTmpltVO.TO_REGION" colType="text" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.FROM_DIV" id="FROM_DIV" width="70"
					title="%{getText('line.printed')}" colType="number" hidden="true" />
				<psjg:gridColumn name="FROM_DIV_STR" id="FROM_DIV_STR" width="0"
					title="" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.TO_DIV" id="TO_DIV" width="90"
					title="colmnTmpltVO.TO_DIV" colType="number" hidden="true" />
				<psjg:gridColumn name="TO_DIV_STR" id="TO_DIV_STR" width="0" title=""
					colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.FROM_DEPT" id="FROM_DEPT"
					title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
				<psjg:gridColumn name="FROM_DEPT_STR" id="FROM_DEPT_STR" width="0"
					title="" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.TO_DEPT" id="TO_DEPT"
					title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
				<psjg:gridColumn name="TO_DEPT_STR" id="TO_DEPT_STR" width="0"
					title="" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.FROM_UNIT" id="FROM_UNIT"
					title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
				<psjg:gridColumn name="FROM_UNIT_STR" id="FROM_UNIT_STR" width="0"
					title="" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.TO_UNIT" id="TO_UNIT" width="70"
					title="%{getText('line.format')}" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="TO_UNIT_STR" id="TO_UNIT_STR" width="0"
					title="" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.BOLD" id="BOLD" width="70" title="Bold"
					colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.DISP_COL_TOT_ZERO" id="DISP_COL_TOT_ZERO"
					width="70" title="%{getText('line.format')}" colType="text" editable="false" sortable="false" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.ALL_CRITERIA" id="ALL_CRITERIA" width="70"
					title="%{getText('line.displayLineTotalZero')}" colType="text" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.SPECIFY_DAY" id="ALL_CRITERIA" width="70"
					title="%{getText('line.displayLineTotalZero')}" colType="text" hidden="true" />	
				<psjg:gridColumn name="colmnTmpltVO.SPECIFY_MONTH" id="ALL_CRITERIA" width="70"
					title="%{getText('line.displayLineTotalZero')}" colType="text" hidden="true" />
				<psjg:gridColumn name="colmnTmpltVO.FROM_CY" id="FROM_CY" width="70"
					title="%{getText('line.displayLineTotalZero')}" colType="number" hidden="true" />		
				<psjg:gridColumn name="CURRENCY_STR" id="CURRENCY_STR" width="70"
					title="%{getText('line.displayLineTotalZero')}" colType="text" hidden="true" />		
				</psjg:grid>
		 </ps:collapspanel>
	
		 <ps:collapspanel id='inEditColTemplateDetailsDiv_${_pageRef}'  key="template.templateDetails">
		  <ps:form id="frmEditColTemplateDetails_${_pageRef}" useHiddenProps="true">	
			<ps:hidden name="singleColumnDetail.colmnTmpltVO.CODE" id="HIDDEN_CODE" value="0"></ps:hidden>
			<ps:hidden name="detailColGridMode" id="detailColGridMode" value="add"></ps:hidden>
			<ps:hidden id="COL_TYPE_STR" name="singleColumnDetail.COL_TYPE_STR"></ps:hidden>
			<table class="headerPortionContent ui-widget-content" width="100%">
				<tr>
					<td nowrap>
						<ps:label key="line.lineNbr" for="LINE_NBR_${_pageRef}" />
					</td>
					<td colspan="2" nowrap>
						<div style="width: 50px;">
							<ps:textfield name="singleColumnDetail.colmnTmpltVO.LINE_NBR" id="LINE_NBR_${_pageRef}" mode="number" nbFormat="######" 
								maxlength="6" minValue="0" onchange="DetLineNumberChanged(this)">
							</ps:textfield>
						</div>
					</td>
					<td nowrap>
						<ps:label key="col.colType" for="COL_TYPE_${_pageRef}" />
					</td>
					<td colspan="2" nowrap>
						<ps:select list="colTypeArrList" listKey="VALUE_CODE" name="singleColumnDetail.colmnTmpltVO.COL_TYPE" id="COL_TYPE_${_pageRef}"
						listValue="VALUE_DESC"  onchange="columnTypeChanged()" 
						parameter="colType:COL_TYPE_${_pageRef}" 
		                dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/col_applyRequiredFields.action"
		                dependency="BRIEF_NAME_ENG_${_pageRef}:singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ENG"
						/>
						
					</td>
				</tr>
				<tr>
					<td nowrap><ps:label key="reporting.briefNameEnglish" for="BRIEF_NAME_ENG_${_pageRef}" /></td>
					<td nowrap colspan="2">
						<table width="89%">
							<tr>
								<td>
									<ps:textfield name="singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ENG" id="BRIEF_NAME_ENG_${_pageRef}" maxlength="500">
									</ps:textfield>
								</td>
							</tr>
						</table>
					</td>	
					<td nowrap><ps:label key="reporting.briefNameArab" for="BRIEF_NAME_ARAB_${_pageRef}" /></td>
					<td nowrap colspan="2">
						<ps:textfield name="singleColumnDetail.colmnTmpltVO.BRIEF_NAME_ARAB" id="BRIEF_NAME_ARAB_${_pageRef}" maxlength="500"  onlyArabic="true"></ps:textfield>
					</td>	
				</tr>
				<tr>
					<td nowrap> <ps:label key="reporting.fromAsOfDate" for="FROM_DATE_${_pageRef}" /></td>
					<td nowrap colspan="2">
					<psj:datepicker name="singleColumnDetail.colmnTmpltVO.FROM_DATE"
						id="FROM_DATE_${_pageRef}"  onchange="fromDateChanged()" buttonImageOnly="true"></psj:datepicker>
						</td>
						
					<td nowrap> <ps:label key="col.toDate" for="TO_DATE_${_pageRef}" /></td>
					<td nowrap colspan="2">
					<psj:datepicker name="singleColumnDetail.colmnTmpltVO.TO_DATE"
						id="TO_DATE_${_pageRef}"  onchange="toDateChanged()" buttonImageOnly="true"></psj:datepicker>
					</td>
				</tr>
				<tr>
					<td>
						<ps:label key="col.days" for="colDaysOfWeekId_${_pageRef}" />
					</td>
					<td>
						<ps:select list="colDaysOFWeek" listKey="VALUE_CODE" name="singleColumnDetail.colmnTmpltVO.SPECIFY_DAY" 
						id="colDaysOfWeekId_${_pageRef}" listValue="VALUE_DESC"/>
					</td>
					 <td colspan="4"></td>
				</tr>
				<tr>
					 <td>
						<ps:label key="col.months" for="colListMonthsId_${_pageRef}" />
					 </td>
					 <td>
					 	<ps:select list="colListMonths" listKey="VALUE_CODE" name="singleColumnDetail.colmnTmpltVO.SPECIFY_MONTH" 
					 	id="colListMonthsId_${_pageRef}" listValue="VALUE_DESC"/>
					 </td>
					 <td colspan="4"></td>
				</tr>
				<tr>
					<td nowrap>
						<ps:label key="reporting.companyCode" for="lookuptxt_compID_${_pageRef}" />
					</td>
					<td width="80px"> 
				   		  <psj:livesearch 
			    		  	 id            ="compID_${_pageRef}"
		                     name          ="singleColumnDetail.colmnTmpltVO.COMP" 
		                     mode          ="number"
		                     readOnlyMode  ="false"
		                     actionName="${pageContext.request.contextPath}/path/templateMaintReport/companiesLkp"
		                     searchElement =""
		                     afterDepEvent="afterCompDependency()"
		                     parameter     ="code:lookuptxt_compID_${_pageRef},code1:1"
		                     resultList="CODE:lookuptxt_compID_${_pageRef},BRIEF_DESC_ENG:compDesc_${_pageRef}" 
		                     dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyCompaniesDependency.action"
		                     dependency    ="compDesc_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_compID_${_pageRef}:commonDetVO.CODE" 
		                     >
		        		     </psj:livesearch>
					 </td>
					 <td>
		   		   		 <ps:textfield id="compDesc_${_pageRef}" name="singleColumnDetail.COMP_STR" readonly="true" tabindex="-1"></ps:textfield>	  
					 </td>
					<td nowrap >
						<ps:label value="%{getText('col.allCriteria')}" />
					</td>
					<td nowrap colspan="2">
						<ps:checkbox id="chkAllCriteria" name="allCriteria" onchange="allCriteriaClicked()"></ps:checkbox>
					</td>
				</tr>
				<tr>
					<td  nowrap><ps:label key="reporting.fromBranch" for="lookuptxt_brnchCode_${_pageRef}" /></td>
					 <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="brnchCode_${_pageRef}" 
		                      name          ="singleColumnDetail.colmnTmpltVO.FROM_BRANCH" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/branchLkp"
		                      searchElement =""
		                      paramList="column1:lookuptxt_compID_${_pageRef}"
		                      resultList="CODE:lookuptxt_brnchCode_${_pageRef},BRIEF_DESC_ENG:brnchDesc_${_pageRef}"
		                      parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_brnchCode_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyBranchDependency.action"
		                      dependency    ="brnchDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_brnchCode_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		      </psj:livesearch>
					</td>
					<td>
						<ps:textfield id="brnchDesc_${_pageRef}" name="singleColumnDetail.FROM_BRANCH_STR" readonly="true" tabindex="-1"></ps:textfield>
					</td>
					<td nowrap><ps:label key="reporting.toBrach" for="lookuptxt_brnchCodeTo_${_pageRef}" /></td>
					 <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="brnchCodeTo_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.TO_BRANCH" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/branchLkp"
		                      searchElement =""
		                      paramList="column1:lookuptxt_compID_${_pageRef}"
		                      resultList="CODE:lookuptxt_brnchCodeTo_${_pageRef},BRIEF_DESC_ENG:brnchToDesc_${_pageRef}"
		                      parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_brnchCodeTo_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyBranchDependency.action"
		                      dependency    ="brnchToDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_brnchCodeTo_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		      </psj:livesearch>
					</td>
					<td>
						<ps:textfield id="brnchToDesc_${_pageRef}" name="singleColumnDetail.TO_BRANCH_STR" readonly="true" tabindex="-1"></ps:textfield>
					</td>
				</tr>
				<tr>
					  <td nowrap><ps:label key="reporting.fromRegion" for="lookuptxt_FROM_REGION_${_pageRef}" /></td>
					  <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="FROM_REGION_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.FROM_REGION" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/regionLkp"
		                      searchElement =""
		                      resultList="CODE:lookuptxt_FROM_REGION_${_pageRef},BRIEF_DESC_ENG:region1Str_${_pageRef}"
							  parameter     ="column1:lookuptxt_FROM_REGION_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyRegionDependency.action"
		                      dependency    ="regionFromStr_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_FROM_REGION_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		  </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="regionFromStr_${_pageRef}" name="singleColumnDetail.FROM_REGION_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
						
					  <td nowrap><ps:label key="reporting.toRegion" for="lookuptxt_TO_REGION_${_pageRef}" /></td>
					  <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="TO_REGION_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.TO_REGION" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/regionLkp"
		                      searchElement =""
		                      resultList="CODE:lookuptxt_TO_REGION_${_pageRef},BRIEF_DESC_ENG:region1Str_${_pageRef}"
							  parameter     ="column1:lookuptxt_TO_REGION_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyRegionDependency.action"
		                      dependency    ="regionToStr_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_TO_REGION_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		  </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="regionToStr_${_pageRef}" name="singleColumnDetail.TO_REGION_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
				</tr>
		
				<tr>
					<td nowrap><ps:label key="reporting.fromDiv" for="lookuptxt_divCodeFrom_${_pageRef}" /></td>
					 <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="divCodeFrom_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.FROM_DIV" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      afterDepEvent="checkDiv(1)"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/fillLookupDivision"
		                      searchElement =""
		                   	  paramList="column1:lookuptxt_compID_${_pageRef}"
		                      resultList="CODE:lookuptxt_divCodeFrom_${_pageRef},BRIEF_DESC_ENG:divCodeFromDesc_${_pageRef}"
							  parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:isFrom_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyDivisionDependency.action"
		                      dependency    ="divCodeFromDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_divCodeFrom_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		 </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="divCodeFromDesc_${_pageRef}" name="singleColumnDetail.FROM_DIV_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
					<td nowrap><ps:label key="reporting.toDiv" for="lookuptxt_divCodeTo_${_pageRef}" /></td>
						 <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="divCodeTo_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.TO_DIV" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      afterDepEvent="checkDiv(2)"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/fillLookupDivision"
		                      searchElement =""
		                   	  paramList="column1:lookuptxt_compID_${_pageRef}"
		                      resultList="CODE:lookuptxt_divCodeTo_${_pageRef},BRIEF_DESC_ENG:divCodeToDesc_${_pageRef}"
							  parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeTo_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyDivisionDependency.action"
		                      dependency    ="divCodeToDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_divCodeTo_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		  </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="divCodeToDesc_${_pageRef}" name="singleColumnDetail.TO_DIV_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
				</tr>
				<tr>
					<td nowrap><ps:label key="reporting.fromDept" for="lookuptxt_depCodeFrom_${_pageRef}" /></td>
					 <td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="depCodeFrom_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.FROM_DEPT" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/DepartmentsLkp"
		                      searchElement =""
		                   	  paramList="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:lookuptxt_divCodeTo_${_pageRef}"
		                      resultList="CODE:lookuptxt_depCodeFrom_${_pageRef},BRIEF_DESC_ENG:depCodeFromDesc_${_pageRef}"
		                      afterDepEvent="checkDept(1)"
							  parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:lookuptxt_depCodeFrom_${_pageRef},
							  					updates:isFrom_${_pageRef},TO_DIV:lookuptxt_divCodeTo_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyDeptDependency.action"
		                      dependency    ="depCodeFromDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_depCodeFrom_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		  </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="depCodeFromDesc_${_pageRef}" name="singleColumnDetail.FROM_DEPT_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
					
					<td nowrap><ps:label key="reporting.toDept" for="lookuptxt_TO_DEPT_${_pageRef}" /></td>
						<td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="TO_DEPT_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.TO_DEPT" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/DepartmentsLkp"
		                      searchElement =""
		                   	  paramList="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:lookuptxt_divCodeTo_${_pageRef}"
		                      resultList="CODE:lookuptxt_TO_DEPT_${_pageRef},BRIEF_DESC_ENG:depCodeToDesc_${_pageRef}"
		                      afterDepEvent="checkDept(2)"
							   parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:lookuptxt_TO_DEPT_${_pageRef},
							  				 TO_DIV:lookuptxt_divCodeTo_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyDeptDependency.action"
		                      dependency    ="depCodeToDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_TO_DEPT_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		      </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="depCodeToDesc_${_pageRef}" name="singleColumnDetail.TO_DEPT_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
				</tr>
				<tr>
					<td nowrap> <ps:label key="reporting.fromUnit" for="lookuptxt_FROM_UNIT_${_pageRef}" /></td>
					<td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="FROM_UNIT_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.FROM_UNIT" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/UnitsLkp"
		                      searchElement =""
		                   	  paramList="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:lookuptxt_depCodeFrom_${_pageRef}"
		                      resultList="CODE:lookuptxt_FROM_UNIT_${_pageRef},BRIEF_DESC_ENG:unitFromDesc_${_pageRef}"
							  parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeFrom_${_pageRef},column3:lookuptxt_depCodeFrom_${_pageRef},FROM_UNIT:lookuptxt_FROM_UNIT_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyUnitDependency.action"
		                      dependency    ="unitFromDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_FROM_UNIT_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		   </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="unitFromDesc_${_pageRef}" name="singleColumnDetail.FROM_UNIT_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
					<td nowrap><ps:label key="reporting.toUnit" for="lookuptxt_TO_UNIT_${_pageRef}" /></td>
					<td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="TO_UNIT_${_pageRef}"
		                      name          ="singleColumnDetail.colmnTmpltVO.TO_UNIT" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/UnitsLkp"
		                      searchElement =""
		                      paramList="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeTo_${_pageRef},column3:lookuptxt_TO_DEPT_${_pageRef}"
		                      resultList="CODE:lookuptxt_TO_UNIT_${_pageRef},BRIEF_DESC_ENG:unitToDesc_${_pageRef}"
							  parameter     ="column1:lookuptxt_compID_${_pageRef},column2:lookuptxt_divCodeTo_${_pageRef},column3:lookuptxt_TO_DEPT_${_pageRef},FROM_UNIT:lookuptxt_TO_UNIT_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyUnitDependency.action"
		                      dependency    ="unitToDesc_${_pageRef}:lkpDependencyVO.BRIEF_DESC_ENG,lookuptxt_TO_UNIT_${_pageRef}:lkpDependencyVO.CODE" 
		                      >
		        		      </psj:livesearch>
					  </td>
					  <td>
						<ps:textfield id="unitToDesc_${_pageRef}" name="singleColumnDetail.TO_UNIT_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
				</tr>
				<tr>
						<td align="left"><ps:label value="%{getText('line.currency')}"/></td>
						<td>
							<psj:livesearch
								id="csvLookUp_${_pageRef}" 
								name="singleColumnDetail.colmnTmpltVO.FROM_CY"
								readOnlyMode  ="false"
								actionName="${pageContext.request.contextPath}/pathdesktop/currencyLookup_constructLookup"
								searchElement="CURRENCY_CODE" 
								resultList="CURRENCY_CODE:lookuptxt_csvLookUp_${_pageRef},BRIEF_DESC_ENG:currencyStr_${_pageRef}"
								mode="number" maxlength="3"
								parameter="CURRENCY_CODE:lookuptxt_csvLookUp_${_pageRef}"
					            dependencySrc="${pageContext.request.contextPath}/pathdesktop/CurrencyDependencyAction_dependencyByCurrencyCode.action?displayMsg=1"
					            dependency="lookuptxt_csvLookUp_${_pageRef}:currency.CURRENCY_CODE,currencyStr_${_pageRef}:currency.BRIEF_DESC_ENG">
							</psj:livesearch>	
					    </td>
						<td>
							<ps:textfield id="currencyStr_${_pageRef}" name="singleColumnDetail.CURRENCY_STR"  readonly="true"/>
						</td>
						<td colspan="3"></td>
				</tr>						 
				<tr>
					<td nowrap><ps:label value="%{getText('line.bold')}" /></td>
					<td nowrap><ps:checkbox id="BOLD" name="det_IS_BOLD"></ps:checkbox></td>
					<td nowrap></td>
					<td nowrap><ps:label value="%{getText('reporting.displayColWithZeroVal')}" /></td>
					<td nowrap><ps:checkbox id="DISP_COL_TOT_ZERO" name="det_DISP_COL_TOT_ZERO"></ps:checkbox></td>
					<td nowrap></td>
				</tr>
				<tr>
					<td colspan="6">
		         		<ptt:toolBar id="colTemplTlBar_${_pageRef}">
							<psj:submit id="linesOK_${_pageRef}" button="true" buttonIcon="ui-icon-disk">
								<ps:label key="reporting.ok"></ps:label>
							</psj:submit>
		         		</ptt:toolBar> 
					</td>
				</tr>
			 </table>
			 <ps:hidden name="singleColumnDetail.numberAfter" id="numberAfter_${_pageRef}"></ps:hidden>
		   </ps:form>	
		</ps:collapspanel>

	<div > 

 	   <psj:tabbedpanel id="tabs_${_pageRef}" onselect="checkSelectedColTmplTab">
	 	 	
				<psj:tab id="custTab1" href="${pageContext.request.contextPath}/path/colTemplateMaintReport/openCriteria.action?tempCodeWithLineNb=0~0&_pageRef=${_pageRef}" key="criteria.criteriaTitle"/>
				<psj:tab id="custTab2" href="${pageContext.request.contextPath}/path/templateExpression/openExpression.action?lineNbr=0&_pageRef=${_pageRef}" key="designer.expressions" />
				
            </psj:tabbedpanel>	
 		
	</div>
</ps:collapsgroup>
<ptt:toolBar  id="tlbSave"> 
	<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="return saveAll()" >
		<ps:text name="reporting.save"></ps:text>
	</psj:submit>
</ptt:toolBar>

<ps:hidden value="1" id="isFrom_${_pageRef}"></ps:hidden>
<ps:hidden name="linesGridMode" id="linesGridMode"></ps:hidden>

</body>
<script  type="text/javascript">

	$(document).ready(function()
	 {
  		$.struts2_jquery.require("ColumnTemplate.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/columntemplate/");
		rep_colTempl_ready();
	});
</script>
 
</html>