<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<ps:form id="crtMaintFrmIdColTemp" useHiddenProps="true" name="crtMaintFrmId" action="validateCrt" namespace="/path/colTemplateMaintReport"  method="POST" >
<ps:param  name="_pageRef" value="_pageRef"></ps:param>
<ps:hidden name="crtCO.columntmpltParamLinkVO.TEMP_CODE" id="crtTempCode" ></ps:hidden>
<ps:hidden name="crtCO.TABLE_NAME1" id="crtTblName1" ></ps:hidden>
<ps:hidden name="crtCO.TABLE_NAME2" id="crtTblName2" ></ps:hidden>
<ps:hidden name="crtCO.CRITERIA_TYPE_CODE" id="crt_type_Code" ></ps:hidden>
<ps:hidden name="crtCO.COMPONENT" id="crtComponent" ></ps:hidden>
<table width="100%"  class="headerPortionContent ui-widget-content" border="0">	
	<tr>
		<td colspan="6">
			<table width="50%" border="0">
				<tr>
				 <td width="95px"><ps:label value="%{getText('criteria.lineNb')}"/></td>
				 <td width="50px"><ps:textfield name="crtCO.columntmpltParamLinkVO.LINE_NO" id="crt_tempLnNbr" mode="number" emptyValue="0" nbFormat="######" maxlength="6" readonly="true"></ps:textfield></td>
				 <td align="right"><ps:label value="%{getText('reporting.subLineNb')}"/></td>
				 <td width="50px"><ps:textfield name="crtCO.columntmpltParamLinkVO.SUB_LINE_NO" id="crt_subLnNbr" mode="number" emptyValue="0" nbFormat="######" maxlength="6" readonly="true"></ps:textfield></td>
				 <td align="right"><ps:label value="%{getText('criteria.Include')}"/></td>
				 <td > <ps:checkbox id="crt_include"  name="crtCO.INCLUDE_CHK"></ps:checkbox></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100px"><ps:label key="criteria.filterCrt" for="lookuptxt_crt_Code" /></td>
		<td width="80px">
 				  <psj:livesearch 
    		  id            ="crt_Code"
                    name          ="crtCO.columntmpltParamLinkVO.CRITERIA_CODE" 
                    mode          ="number"
                    readOnlyMode  ="false"
                    actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/filterCrtLkp"
                    searchElement ="" 
                    afterDepEvent="changeDisplayColTemp();"
                    parameter     ="code:lookuptxt_crt_Code"
                    resultList="CRITERIA_CODE:lookuptxt_crt_Code,CRITERIA_DESCRIPTION:crtLookUp,CRITERIA_TYPE_CODE:crt_type_Code,TABLE_NAME1:crtTblName1,TABLE_NAME2:crtTblName2,COMPONENT:crtComponent"
                    dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyFilterCrtDependency.action"
                    dependency    ="lookuptxt_crt_Code:commonDetVO.CRITERIA_CODE,crtLookUp:commonDetVO.CRITERIA_DESCRIPTION,crt_type_Code:commonDetVO.CRITERIA_TYPE_CODE,crtTblName1:commonDetVO.TABLE_NAME1,crtTblName2:commonDetVO.TABLE_NAME2,crtComponent:commonDetVO.COMPONENT" 
                    >
      		      </psj:livesearch>
		</td>
		<td>
			<ps:textfield name="crtCO.CRITERIA_NAME" id="crtLookUp" readonly="true" tabindex="-1"></ps:textfield>
		</td>
		<td style="display: none;" align="right"><ps:label value="%{getText('operator')}"/></td>
		<td style="display: none;"  colspan="2">	
			<ps:select
			list="operatorArrList"
			listKey="VALUE_CODE"
			listValue="VALUE_DESC"
			name="crtCO.columntmpltParamLinkVO.OPERATOR" 
			id="operComboId" 
			/> 
		</td>
	</tr>
	<tr id="criteriaFMColTrId_<ps:property value="_pageRef" escapeHtml="true"/>">
		 <td>
		 	<ps:label key="template.fm" for="criteriaFMColId_${_pageRef}"/>
		 </td>
		 <td>
		 	<ps:select 	list="maleFemaleList" listKey="VALUE_CODE" listValue="VALUE_DESC" name="crtCO.columntmpltParamLinkVO.GENDER" 
				id="criteriaFMColId_${_pageRef}"/>
		 </td>
	 	 <td colspan="4"></td>
	</tr>	
	<tr>
		<td width="100px"><ps:label key="criteria.details1" for="lookuptxt_crt_Code1"/></td>
		<td width="80px">
 				  <psj:livesearch 
    		  id            ="crt_Code1"
                    name          ="crtCO.CODE1" 
                    mode          ="text"
                    readOnlyMode  ="false"
                    actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/filterCrtDetLkp"
                    searchElement ="" 
                    beforeDepEvent="befDepColTemp();"
                    afterDepEvent="det1DependencyColTemp()"
                    parameter     ="str:lookuptxt_crt_Code1,str1:crtTblName1,updates:crt_type_Code,updates1:code1LookUp,code:lookuptxt_crt_Code"
                    paramList="str:crtTblName1,str1:crt_type_Code,code:lookuptxt_crt_Code"
                    resultList="CODE_STR:lookuptxt_crt_Code1,BRIEF_DESC_ENG:code1LookUp"
                    dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyFilterCrtDetDependency.action"
                    dependency    ="code1LookUp:commonDetVO.BRIEF_DESC_ENG,lookuptxt_crt_Code1:commonDetVO.CODE_STR"						                      >
      		      </psj:livesearch>
		</td>
		<td>
			<ps:textfield name="crtCO.CODE1_NAME" id="code1LookUp" readonly="true" tabindex="-1"></ps:textfield>
		</td>
		<td width="100px" align="right"><ps:label key="criteria.details2" for="lookuptxt_crt_Code2"/></td>
		 	<td width="80px">
 				  <psj:livesearch 
    		  id            ="crt_Code2"
                    name          ="crtCO.columntmpltParamLinkVO.CODE2" 
                    mode          ="text"
                    readOnlyMode  ="false"
                    actionName="${pageContext.request.contextPath}/path/colTemplateMaintReport/filterCrtDetLkp"
                    searchElement ="" 
                    afterDepEvent="afterDepDetails2ColTemp();"
                    parameter     ="str:lookuptxt_crt_Code2,str1:crtTblName2,updates:crt_type_Code,VALUE:lookuptxt_crt_Code1"
                    paramList="str:crtTblName2,str1:lookuptxt_crt_Code1,str2:crt_type_Code"
                    resultList="CODE_STR:lookuptxt_crt_Code2,BRIEF_DESC_ENG:code2LookUp" 
                    dependencySrc ="${pageContext.request.contextPath}/path/colTemplateMaintReport/applyFilterCrtDetDependency.action"
                    dependency    ="code2LookUp:commonDetVO.BRIEF_DESC_ENG,lookuptxt_crt_Code2:commonDetVO.CODE_STR"						                      >
      		      </psj:livesearch>
		</td>
		<td>
			<ps:textfield name="crtCO.CODE2_NAME" id="code2LookUp" readonly="true" tabindex="-1"></ps:textfield>
		</td>
	</tr>
	<tr>
		<td align="left"><ps:label value="%{getText('criteria.value')}"/></td> 
		<td colspan="2"><ps:textfield name="crtCO.VALUE1" id="crt_value1" mode="number" emptyValue="0" nbFormat="######" maxlength="13" onblur="checkValue1()"></ps:textfield></td>
		<td align="right"><ps:label value="%{getText('criteria.value2')}"/></td> 
		<td  colspan="2"><ps:textfield name="crtCO.VALUE2" id="crt_value2" mode="number" emptyValue="0" nbFormat="######" maxlength="13"></ps:textfield></td>
	</tr>
	<tr>
	<tr id="dayMonthYearColTr_<ps:property value="_pageRef" escapeHtml="true"/>">
		<td align="left"><ps:label key="template.period" for ="dayMonthYear_${_pageRef}"/></td> 
		<td >	
			<ps:select  list="daysMonthYear" listKey="VALUE_CODE" listValue="VALUE_DESC" 
				name="crtCO.columntmpltParamLinkVO.DATE_TYPE" id="dayMonthYear_${_pageRef}"/>
		</td>	
		<td colspan="4"></td>	
	</tr>
		<td colspan="6">
			<ptt:toolBar id="tmpltCritt_${_pageRef}">
				<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="addCriteriaColTemp();">
					<ps:label key="reporting.ok" ></ps:label>
				</psj:submit>
	   		</ptt:toolBar>
		</td>
	</tr>
</table>
</ps:form>
<script>
//$("#crtMaintFrmId_"+_pageRef).processAfterValid("addCriteriaColTemp");	
</script>