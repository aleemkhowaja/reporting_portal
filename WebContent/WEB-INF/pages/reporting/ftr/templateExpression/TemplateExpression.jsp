<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="tmpCodeExistAlertExpr_var" 		value="%{getEscText('template.fillTemplCode')}"/>
<ps:set name="selectLineAlertExpr_var" 			value="%{getEscText('gl.selectLineAlert')}"/>
<ps:set name="invalidLineNbAlert_var" 			value="%{getEscText('expr.invalidLineNbAlert')}"/>
<ps:set name="positiveNbAlert_var"					value="%{getEscText('col.positiveNbAlert')}"/>
<ps:set name="selectLineAlert_var"					value="%{getEscText('gl.selectLineAlert')}"/>

<script type="text/javascript">
var isAdd="false";
var tmpCodeExistAlertExpr 		= '<ps:property value="tmpCodeExistAlertExpr_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectLineAlertExpr 		= '<ps:property value="selectLineAlertExpr_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var invalidLineNbAlert 			= '<ps:property value="invalidLineNbAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var positiveNbAlert				= '<ps:property value="positiveNbAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var exprRowId="";
var selectLineAlert				='<ps:property value="selectLineAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function () 
{
	$.struts2_jquery.require("TemplateExpression.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/templateExpression/");
	rep_colTempl_ready_func();
}
);
</script>

	<ps:collapsgroup id="tmplExprCollapsGrp_${_pageRef}">
			<ps:collapspanel id="foldersDetFrm"  key="expr.exprTitle">		
				<ps:url var="urlTagOper" action="selectOperatorsUrl" namespace="/path/templateExpression"></ps:url> 
				<ps:url var="urlTag" action="loadExprList" namespace="/path/templateExpression" escapeAmp="false">
					<ps:param name="lineNbr" value="lineNbr"></ps:param>
					<ps:param name="_pageRef" value="_pageRef"></ps:param>
				</ps:url>
				<ps:url var="dependencyurl" action="expressionDependency" namespace="/path/templateExpression"></ps:url>
				<ps:url var="dependencyurl2" action="lineOrValueChange" namespace="/path/templateExpression"></ps:url>
				<psjg:grid  id="exprTemplGrid_${_pageRef}" 
				gridModel="gridModel"
				dataType="json"
				href="%{urlTag}"
				pager="true"
				navigator="true"
				navigatorSearch="false "
				viewrecords="true"
				rowNum="15" 
				rowList="5,10,15,20"
				editinline="true"
				editurl="%{urlTag}"
				navigatorEdit="false"
				addfunc="addExprLineCol"
				delfunc="deleteExprCol(rowId)"
				onEditInlineBeforeTopics="disableOperCol">
				<psjg:gridColumn name="ftrTmpltExprVO.OPERATOR" 		index="OPERATOR" 			id="operator"  	title="%{getText('operator')}" 			colType="select" editable="true" edittype="select"  formatter="select" editoptions="{value:'+:%{getText('reporting.add')};-:%{getText('reporting.substruct')};/:%{getText('reporting.divide')};*:%{getText('reporting.multiply')}; : '}" sortable="false"  />
				<psjg:gridColumn name="ftrTmpltExprVO.LEFT_OPERATOR" 	index="l_OPERATOR" 			id="lOper" 		title="%{getText('expr.lOper')}" 		colType="select" editable="true" edittype="select" formatter="select" editoptions="{value:' : ;(:('}" sortable="false" />
				<psjg:gridColumn name="ftrTmpltExprVO.EXP_TYPE" 		index="EXP_TYPE" 			id="expType" 	title="%{getText('reporting.type')}" 	colType="select" editable="true" edittype="select" formatter="select" editoptions="{dataEvents: [{ type: 'change', fn: function() { checkCellCol(this) } }],value:'L:%{getText('reporting.line')};V:%{getText('reporting.value')}'}"  sortable="false" />
				<psjg:gridColumn name="ftrTmpltExprVO.EXP_VALUE" 		index="VALUE" 				id="value" 		title="%{getText('reporting.lineNb_Val')}" colType="number"  editable="true" sortable="false" editoptions="{dataEvents: [{ type: 'change', fn: function() { checkCellCol(this) } }]}" />
				<psjg:gridColumn name="ftrTmpltExprVO.RIGHT_OPERATOR" 	index="r_OPERATOR" 			id="rOper" 		title="%{getText('expr.rOper')}" 		colType="select" editable="true" edittype="select" formatter="select" editoptions="{value:' : ;):)'}"  sortable="false" />
				<psjg:gridColumn name="concatKey" 						index="concatKey" 			id="concatKey" 	title="concat key" 						colType="number"  editable="false" sortable="false" key="true" hidden="true"/>
				<psjg:gridColumn name="ftrTmpltExprVO.COMP_CODE" 		index="COMP_CODE" 			id="compCode" 	title="%{getText('expr.companyCode')}" 	colType="number"  editable="false" sortable="false" hidden="true"/>
				<psjg:gridColumn name="ftrTmpltExprVO.LINE_NO" 			index="SUB_LINE_NO" 		id="subLineNb" 	title="%{getText('reporting.subLineNb')}" colType="number"  editable="false" sortable="false" hidden="true"/>
				<psjg:gridColumn name="ftrTmpltExprVO.CODE" 			index="TEMPLATE_CODE" 		id="templCode" 	title="%{getText('reporting.templateCode')}" colType="number"  editable="false" sortable="false" hidden="true"/>
				<psjg:gridColumn name="ftrTmpltExprVO.TMPLT_LINE_NO" 	index="TEMPLATE_LINE_NO" 	id="tempLineNb" title="%{getText('expr.templLineNb')}" colType="number"  editable="false" sortable="false" hidden="true"/>
				<psjg:gridColumn name="EXP_ORDERs" 		index="EXP_ORDER" 			id="expOrder" 	title="ORDER" 							colType="number"  editable="false" sortable="false" hidden="true"/>
				<psjg:gridColumn name="newLineNumber" 		title="tempmlate line number" colType="number" 	editable="false" sortable="false" hidden = "true"/>
				</psjg:grid> 
		</ps:collapspanel>
	</ps:collapsgroup>
	
	<ps:form id="exprTemplGridform_${_pageRef}" action="addExpression" namespace="/path/templateExpression">
			<ps:hidden name="updates" id="updatesExpr_${_pageRef}"></ps:hidden>
			<ps:hidden name="_pageRef" id="${_pageRef}"></ps:hidden>
			<ps:hidden name="lineNbr" id="lineNbr"></ps:hidden>
			<ps:hidden name="tempCodeWithLineNb" id="tempCodeWithLineNbExpr_${_pageRef}"></ps:hidden>
	</ps:form>
