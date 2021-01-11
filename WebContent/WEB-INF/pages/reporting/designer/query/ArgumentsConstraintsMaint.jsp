<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>
<style>
				.ui-autocomplete {
					max-height: 180px;
					overflow-y: auto;
					overflow-x: hidden;
				}
</style>
<ps:form   id="argConstrFrmId_${_pageRef}" namespace="/path/designer" 	useHiddenProps="true">
	<table class="headerPortionContent ui-widget-content" width="100%" >
		<tr>
			<td>
				<ps:label key="reporting.caseSensitivity" for="caseSenstDrp_${_pageRef}"/>	
			</td>
			<td>
				<ps:select cssStyle="width:25%" emptyOption="true" list="caseSensList" listKey="VALUE_CODE" listValue="VALUE_DESC" 
					name="argConstrtCO.CASE_SENSITIVITY" id="caseSenstDrp_${_pageRef}"/>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.maxLength" for="maxLength_${_pageRef}"/>	
			</td>
			<td>
				<ps:textfield name="argConstrtCO.MAX_LENGTH" mode="number" id="maxLength_${_pageRef}" size="50"/>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.maxVal" for="maxVal_${_pageRef}" />	
			</td>
			<td>
				<ps:textfield name="argConstrtCO.MAX_VAL" mode="number" 	id="maxVal_${_pageRef}" size="50"/>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.minVal" for="minVal_${_pageRef}"/>	
			</td>
			<td>
				<ps:textfield name="argConstrtCO.MIN_VAL" mode="number" id="minVal_${_pageRef}" size="50"/>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.format" for="formatConstr_${_pageRef}"/>	
			</td>
			<td>
				<ps:textfield name="argConstrtCO.FORMAT" 	id="formatConstr_${_pageRef}" size="50"/>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.comparison" for="comparisonId_${_pageRef}"/>	
			</td>
			<td>
				<ps:textarea id="comparisonId_${_pageRef}" name="argConstrtCO.CONDITION" maxlength="1000" cols="85" rows="5"/>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.showExpr" for="showExprId_${_pageRef}"/>	
			</td>
			<td>
				<ps:textarea id="showExprId_${_pageRef}" name="argConstrtCO.SHOW_EXPR" maxlength="1000" cols="85" rows="5"/>                              
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.hideExpr" for="hideExprId_${_pageRef}"/>	
			</td>
			<td>
				<ps:textarea id="hideExprId_${_pageRef}" name="argConstrtCO.HIDE_EXPR" maxlength="1000" cols="85" rows="5"/>                               
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting.useBtrCtrlDisp" for="btrCntrlDisp_${_pageRef}"/>	
			</td>
			<td>
				<ps:checkbox id="btrCntrlDisp_${_pageRef}" name="argConstrtCO.BTR_CONTROL_DISP"></ps:checkbox>
			</td>
		</tr>
	</table>
</ps:form>