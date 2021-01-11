<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<ps:collapsgroup id="sort7CritTemp_${_pageRef}">
	<ps:collapspanel id="critTempId"  key="criteria.crieteriaDetails">
<ps:form  useHiddenProps="true"  id="crtMaintFrmId"  action="validateCrt" validate="true"
namespace="/path/templateMaintReport"  method="POST" >
							  <ps:hidden name="crtCO.glstmpltParamLinkVO.TEMP_CODE" id="crtTempCode_${_pageRef}" ></ps:hidden>
						   	  <ps:hidden name="crtCO.TABLE_NAME1" 					id="crtTblName1_${_pageRef}" ></ps:hidden>
						 	  <ps:hidden name="crtCO.TABLE_NAME2" 					id="crtTblName2_${_pageRef}" ></ps:hidden>
						 	  <ps:hidden name="crtCO.CRITERIA_TYPE_CODE" 			id="crt_type_Code_${_pageRef}" ></ps:hidden>
						 	  <ps:hidden name="crtCO.COMPONENT"   					id="crtComponent_${_pageRef}" ></ps:hidden>
						 	  <ps:hidden name="crtCO.fromFiltCrt" 					id="fromFiltCrt_${_pageRef}" ></ps:hidden>
							  <table width="100%"  class="headerPortionContent ui-widget-content" border="0">
									
									<tr>
										<td colspan="6">
											<table width="50%" border="0">
												<tr>
												 <td width="95px"><ps:label value="%{getText('criteria.lineNb')}"/></td>
												 <td width="50px"><ps:textfield name="crtCO.glstmpltParamLinkVO.LINE_NO" id="crt_tempLnNbr_${_pageRef}" mode="number" emptyValue="0" nbFormat="######" maxlength="6" readonly="true"></ps:textfield></td>
												 <td align="right"><ps:label value="%{getText('reporting.subLineNb')}"/></td>
												 <td width="50px"><ps:textfield name="crtCO.glstmpltParamLinkVO.SUB_LINE_NO" id="crt_subLnNbr_${_pageRef}" mode="number" emptyValue="0" nbFormat="######" maxlength="6" readonly="true"></ps:textfield></td>
												 <td align="right"><ps:label value="%{getText('criteria.Include')}"/></td>
												 <td > <ps:checkbox id="crt_include_${_pageRef}"  name="crtCO.INCLUDE_CHK"></ps:checkbox></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td width="100px"><ps:label key="criteria.filterCrt" for="lookuptxt_crt_Code_${_pageRef}"/></td>
										<td width="80px">
						   				  <psj:livesearch 
								    		  id            ="crt_Code_${_pageRef}"
						                      name          ="crtCO.glstmpltParamLinkVO.CRITERIA_CODE" 
						                      mode          ="number"
						                      readOnlyMode  ="false"
						                      actionName="${pageContext.request.contextPath}/path/templateMaintReport/filterCrtLkp"
						                      searchElement ="" 
						                      beforeDepEvent="_showProgressBar(true)"
						                      afterDepEvent="changeDisplay(false);_showProgressBar(false)"
						                      parameter     ="code:lookuptxt_crt_Code_${_pageRef}"
						                      resultList="CRITERIA_CODE:lookuptxt_crt_Code_${_pageRef},CRITERIA_DESCRIPTION:crtLookUp_${_pageRef},CRITERIA_TYPE_CODE:crt_type_Code_${_pageRef},TABLE_NAME1:crtTblName1_${_pageRef},TABLE_NAME2:crtTblName2_${_pageRef},COMPONENT:crtComponent_${_pageRef}"
						                      dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyFilterCrtDependency.action"
						                      dependency    ="lookuptxt_crt_Code_${_pageRef}:commonDetVO.CRITERIA_CODE,crtLookUp_${_pageRef}:commonDetVO.CRITERIA_DESCRIPTION,crt_type_Code_${_pageRef}:commonDetVO.CRITERIA_TYPE_CODE,crtTblName1_${_pageRef}:commonDetVO.TABLE_NAME1,crtTblName2_${_pageRef}:commonDetVO.TABLE_NAME2,crtComponent_${_pageRef}:commonDetVO.COMPONENT" 
						                      >
					         		      </psj:livesearch>
										</td>
										<td>
											<ps:textfield name="crtCO.CRITERIA_NAME" id="crtLookUp_${_pageRef}" readonly="true" tabindex="-1"></ps:textfield>
										</td>
										
										
										<td style="display: none;" align="right"><ps:label value="%{getText('operator')}"/></td>
										<td style="display: none;"  colspan="2">	
											<ps:select
											list="operatorArrList"
											listKey="VALUE_CODE"
											listValue="VALUE_DESC"
											name="crtCO.glstmpltParamLinkVO.OPERATOR" 
											id="operComboId_${_pageRef}" 
											/>
										</td>
									</tr>
									<tr id="criteriaFMTrId_<ps:property value="_pageRef" escapeHtml="true"/>">
										 <td>
										 	<ps:label key="template.fm" for="criteriaFMId_${_pageRef}"/>
										 </td>
										 <td>
										 	<ps:select
												list="maleFemaleList"
												listKey="VALUE_CODE"
												listValue="VALUE_DESC"
												name="crtCO.glstmpltParamLinkVO.GENDER" 
												id="criteriaFMId_${_pageRef}" 
												/>
										 </td>
									 	 <td colspan="4"></td>
									</tr>
									<tr>
										<td width="100px"><ps:label key="criteria.details1" for="lookuptxt_crt_Code1_${_pageRef}"/></td>
										<td width="80px">
						   				  <psj:livesearch 
								    		  id            ="crt_Code1_${_pageRef}"
						                      name          ="crtCO.CODE1" 
						                      mode          ="text"
						                      actionName="${pageContext.request.contextPath}/path/templateMaintReport/filterCrtDetLkp"
						                      searchElement ="" 
						                      beforeDepEvent="befDep();"
						                      afterDepEvent="det1Dependency();"
						                      parameter     ="str:lookuptxt_crt_Code1_${_pageRef},str1:crtTblName1_${_pageRef},updates:crt_type_Code_${_pageRef},updates1:code1LookUp_${_pageRef},code:lookuptxt_crt_Code_${_pageRef}"
						                      paramList="str:crtTblName1_${_pageRef},str1:crt_type_Code_${_pageRef},code:lookuptxt_crt_Code_${_pageRef}"
						                      resultList="CODE_STR:lookuptxt_crt_Code1_${_pageRef},BRIEF_DESC_ENG:code1LookUp_${_pageRef}"
						                      dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyFilterCrtDetDependency.action"
						                      dependency    ="code1LookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_crt_Code1_${_pageRef}:commonDetVO.CODE_STR">
					         		      </psj:livesearch>
										</td>
										<td>
											<ps:textfield name="crtCO.CODE1_NAME" id="code1LookUp_${_pageRef}" readonly="true" tabindex="-1"></ps:textfield>
										</td>								
										
										<td width="100px" align="right"><ps:label key="criteria.details2" for="lookuptxt_crt_Code2_${_pageRef}"/></td>
										 	<td width="80px">
						   				  <psj:livesearch 
								    		  id            ="crt_Code2_${_pageRef}"
						                      name          ="crtCO.glstmpltParamLinkVO.CODE2" 
						                      mode          ="text"
						                      beforeDepEvent="_showProgressBar(true)"
						                      afterDepEvent="afterDepDetails2();"
						                      actionName="${pageContext.request.contextPath}/path/templateMaintReport/filterCrtDetLkp"
						                      searchElement ="" 
						                      parameter     ="str:lookuptxt_crt_Code2_${_pageRef},str1:crtTblName2_${_pageRef},updates:crt_type_Code_${_pageRef},VALUE:lookuptxt_crt_Code1_${_pageRef}"
						                      paramList="str:crtTblName2_${_pageRef},str1:lookuptxt_crt_Code1_${_pageRef},str2:crt_type_Code_${_pageRef}"
						                      resultList="CODE_STR:lookuptxt_crt_Code2_${_pageRef},BRIEF_DESC_ENG:code2LookUp_${_pageRef}" 
						                      dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyFilterCrtDetDependency.action"
						                      dependency    ="code2LookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_crt_Code2_${_pageRef}:commonDetVO.CODE_STR">
					         		      </psj:livesearch>
										</td>
										<td>
											<ps:textfield name="crtCO.CODE2_NAME" id="code2LookUp_${_pageRef}" readonly="true" tabindex="-1"></ps:textfield>
										</td> 									
									<td width="100px" align="right"><ps:label key="criteria.details3" for="lookuptxt_crt_Code3_${_pageRef}"/></td>
									   <td width="80px">
						   				  <psj:livesearch 
								    		  id            ="crt_Code3_${_pageRef}"
						                      name          ="crtCO.CODE3" 
						                      mode          ="text"
						                      beforeDepEvent="_showProgressBar(true)"
						                      afterDepEvent="rep_template_afterDepDetails();"
						                      actionName="${pageContext.request.contextPath}/path/templateMaintReport/filterCrtDetLkp"
						                      searchElement ="" 
						                      parameter     ="str:lookuptxt_crt_Code3_${_pageRef},updates1:code3LookUp_${_pageRef},str1:lookuptxt_crt_Code1_${_pageRef},updates:crt_type_Code_${_pageRef},VALUE:lookuptxt_crt_Code2_${_pageRef},str3:lookuptxt_crt_Code2_${_pageRef}"
						                      paramList="str:crtTblName2_${_pageRef},str1:lookuptxt_crt_Code1_${_pageRef},str2:crt_type_Code_${_pageRef},str3:lookuptxt_crt_Code2_${_pageRef}"
						                      resultList="CODE_STR:lookuptxt_crt_Code3_${_pageRef},BRIEF_DESC_ENG:code3LookUp_${_pageRef}" 
						                      dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyFilterCrtDetDependency.action"
						                      dependency    ="code3LookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_crt_Code3_${_pageRef}:commonDetVO.CODE_STR">
					         		      </psj:livesearch>
										</td>
										<td>
											<ps:textfield name="crtCO.CODE3_NAME" id="code3LookUp_${_pageRef}" readonly="true" tabindex="-1"></ps:textfield>
										</td> 													
									</tr>
									<tr>
									<td align="left"><ps:label key="criteria.value" for ="crt_value1_${_pageRef}"/></td> 
									<td colspan="2"><ps:textfield minValue="0" mode="number" name="crtCO.VALUE1" id="crt_value1_${_pageRef}" maxlength="18" maxLenBeforeDec="12" nbFormat="#,##0.000" ></ps:textfield></td>
									<td align="right"><ps:label key="criteria.value2" for="crt_value2_${_pageRef}"/></td> 
									<td colspan="2"><ps:textfield minValue="0" mode="number" name="crtCO.VALUE2" id="crt_value2_${_pageRef}" emptyValue="0" maxlength="18" maxLenBeforeDec="12" nbFormat="#,##0.000" ></ps:textfield></td>
									</tr>
									<tr id="dayMonthYearTr_<ps:property value="_pageRef" escapeHtml="true"/>">
									<td align="left"><ps:label key="template.period" for ="dayMonthYear_${_pageRef}"/></td> 
									<td >	
										<ps:select  list="daysMonthYear" listKey="VALUE_CODE" listValue="VALUE_DESC" 
										name="crtCO.glstmpltParamLinkVO.DATE_TYPE" id="dayMonthYear_${_pageRef}"/>
									</td>	
									<td colspan="4"></td>	
									</tr>
									<tr>
									 <td colspan="9">
										<ptt:toolBar id="tmpltCritt_${_pageRef}">
											<psj:submit type="button"  button="true" buttonIcon="ui-icon-disk" onclick="addCriteria();" id="criteriaOK_${_pageRef}">
												<ps:text name="reporting.ok"></ps:text>
											</psj:submit>
						         		</ptt:toolBar> 
									 </td>
									</tr>
								</table>
								<ps:hidden id="updtCrtList_${_pageRef}" name="updates"/>
							</ps:form>
</ps:collapspanel>
</ps:collapsgroup>