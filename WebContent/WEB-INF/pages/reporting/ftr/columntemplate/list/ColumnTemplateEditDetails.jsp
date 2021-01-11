<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<!-- created by zahi, column template, 04/10/2011  -->
<html>


<script type="text/javascript">
$(document).ready(function () 
  {
  		$.struts2_jquery.require("ColumnTemplateEditDetails.js", null,jQuery.contextPath + "/path/js/reporting/ftr/columntemplate/");
		  
 });
</script>
<body>
<div id="detailDiv">
<ps:form id="mainForm">
	<ps:hidden name="singleColumnDetail.LINE_NBR" id="LINE_NBR" value="0"></ps:hidden>
	<ps:hidden name="singleColumnDetail.TEMPLATE_CODE" id="TEMPLATE_CODE" value="0"></ps:hidden>

	<table>
		<tr>

			<td><ps:label value="Line Number" /></td>
			<td><ps:textfield name="newLineNumber" id="newLineNumber"
				mode="number" nbFormat="######" maxlength="6"></ps:textfield></td>
		</tr>

		<tr>

			<td><ps:label value="Column Type" /></td>
			<td><ps:select list="colTypeArrList" listKey="VALUE_CODE"
				listValue="VALUE_DESCRIPTION" name="singleColumnDetail.COL_TYPE"
				id="COL_TYPE" headerValue="calcution type"
				headerKey="calculation type" /></td>
		</tr>
		<tr>
			<td><ps:label value="From Date" /></td>
			<td><psj:datepicker name="singleColumnDetail.FROM_DATE"
				id="FROM_DATE" buttonImageOnly="true"></psj:datepicker></td>
		</tr>
		<tr>
			<td><ps:label value="To Date" /></td>
			<td><psj:datepicker name="singleColumnDetail.TO_DATE"
				id="TO_DATE" buttonImageOnly="true"></psj:datepicker></td>
		</tr>
		<tr>
			<td><ps:label value="Company Code" /></td>
			<td><ps:textfield name="singleColumnDetail.COL_COMP_CODE"
				id="compID" mode="number" nbFormat="######" maxlength="6">
			</ps:textfield> <psj:lookup
				actionName="${pageContext.request.contextPath}/path/templateMaintReport/companiesLkp"
				id="compDesc" modal="true"
				name="singleColumnDetail.COL_COMP_CODE_STR"
				resultList="CODE:compID,BRIEF_DESC_ENG:compDesc"
				title="Company Lookup" autoOpen="false" position="center">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="All Criteria" /></td>
			<td><ps:textfield name="singleColumnDetail.ALL_CRITERIA"
				id="ALL_CRITERIA"></ps:textfield></td>
		</tr>

		<tr>
			<td><ps:label value="From Branch" /></td>
			<td><ps:textfield name="singleColumnDetail.FROM_BRANCH"
				id="brnchCode" mode="number" nbFormat="######" maxlength="6"></ps:textfield>
			<psj:lookup
				actionName="${pageContext.request.contextPath}/path/templateMaintReport/branchLkp"
				id="brnchDesc" modal="true"
				name="singleColumnDetail.FROM_BRANCH_STR"
				resultList="CODE:brnchCode,BRIEF_DESC_ENG:brnchDesc"
				title="Branch Lookup" autoOpen="false" position="center">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="To Branch" /></td>
			<td><ps:textfield name="singleColumnDetail.TO_BRANCH"
				id="brnchCodeTo" mode="number" nbFormat="######" maxlength="6"></ps:textfield>
			<psj:lookup
				actionName="${pageContext.request.contextPath}/path/templateMaintReport/branchLkp"
				id="brnchToDesc" modal="true"
				name="singleColumnDetail.TO_BRANCH_STR"
				resultList="CODE:brnchCodeTo,BRIEF_DESC_ENG:brnchToDesc"
				title="Branch Lookup" autoOpen="false" position="center">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="From Currency" /></td>
			<td><ps:textfield name="singleColumnDetail.FROM_CY" id="FROM_CY"
				mode="number" nbFormat="######" maxlength="6"></ps:textfield></td>
		</tr>
		<tr>
			<td><ps:label value="To Currency" /></td>
			<td><ps:textfield name="singleColumnDetail.TO_CY" id="TO_CY" mode="number" nbFormat="######" maxlength="6"></ps:textfield></td>
		</tr>
		<tr>
			<td><ps:label value="From Division" /></td>
			<td><ps:textfield name="singleColumnDetail.FROM_DIV"
				id="divCodeFrom" mode="number" nbFormat="######" maxlength="6"></ps:textfield>
			<psj:lookup
				actionName="${pageContext.request.contextPath}/path/templateMaintReport/divisionLkp"
				id="divCodeFromDesc" modal="true"
				name="singleColumnDetail.FROM_DIV_STR"
				resultList="CODE:divCodeFrom,BRIEF_DESC_ENG:divCodeFromDesc"
				title="Division Lookup" autoOpen="false" position="center">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="To Division" /></td>
			<td><ps:textfield name="singleColumnDetail.TO_DIV"
				id="divCodeTo" mode="number" nbFormat="######" maxlength="6"></ps:textfield>
			<psj:lookup
				actionName="${pageContext.request.contextPath}/path/templateMaintReport/divisionLkp"
				id="divCodeToDesc" modal="true" name="singleColumnDetail.TO_DIV_STR"
				resultList="CODE:divCodeTo,BRIEF_DESC_ENG:divCodeToDesc"
				title="Division Lookup" autoOpen="false" position="center">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="From Department" /></td>
			<td><ps:textfield name="singleColumnDetail.FROM_DEPT"
				id="depCodeFrom" mode="number" nbFormat="######" maxlength="6"></ps:textfield>
			<psj:lookup
				actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/DepartmentsLkp"
				id="depCodeFromDesc" modal="true"
				resultList="CODE:depCodeFrom,BRIEF_DESC_ENG:depCodeFromDesc"
				paramList="column1:divCodeFrom,column2:divCodeTo"
				title="Department Lookup" autoOpen="false" position="center"
				name="singleColumnDetail.FROM_DEPT_STR">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="To Department" /></td>
			<td><ps:textfield name="singleColumnDetail.TO_DEPT" id="TO_DEPT"
				mode="number" nbFormat="######" maxlength="6"></ps:textfield> <psj:lookup
				actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/DepartmentsLkp"
				id="depCodeToDesc" modal="true"
				resultList="CODE:depCodeTo,BRIEF_DESC_ENG:depCodeToDesc"
				title="To Department" autoOpen="false" position="center"
				name="singleColumnDetail.TO_DEPT_STR">
			</psj:lookup></td>
		</tr>
		<tr>
			<td><ps:label value="From Unit" /></td>
			<td><ps:textfield name="singleColumnDetail.FROM_UNIT"
				id="FROM_UNIT"></ps:textfield></td>
		</tr>
		<tr>
			<td><ps:label value="To Unit" /></td>
			<td><ps:textfield name="singleColumnDetail.TO_UNIT" id="TO_UNIT"
				mode="number" nbFormat="######" maxlength="6"></ps:textfield></td>
		</tr>
		<tr>
			<td><ps:label value="%{getText('line.bold')}" /></td>
			<td><ps:checkbox id="BOLD" name="det_IS_BOLD"></ps:checkbox></td>
		</tr>
		<tr>
			<td><ps:label value="Display Total Zero" /></td>
			<td><ps:checkbox id="DISP_COL_TOT_ZERO"
				name="det_DISP_COL_TOT_ZERO"></ps:checkbox></td>
		</tr>
		<tr>
			<td></td>
			<td><ps:submit id="submitBtn" onclick="return openColT();"
				value="Save"></ps:submit></td>
		</tr>
	</table>

</ps:form></div>
</body>
</html>