<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>

<ps:collapsgroup id="tmplGlFrmCollapsGrp_${_pageRef}">
			<ps:collapspanel id="GLDivId"  key="gl.glDetails">		
		<ps:form id="glMaintFrmId_${_pageRef}" name="glMaintFrmId" action="validateGL"
			namespace="/path/templateMaintReport" method="POST" useHiddenProps="true">
			<ps:hidden name="glCO.glstmpltGlsDetVO.CODE" id="glTempCode_${_pageRef}"></ps:hidden>
			<table class="headerPortionContent ui-widget-content" width="100%">
				<tr>
					<td colspan="6">
						<table width="25%">
							<tr>
								<td width="32%" align="left">
									<ps:label value="%{getText('line.lineNbr')}" />
								</td>
								<td width="15%">
									<ps:textfield name="glCO.glstmpltGlsDetVO.LINE_NBR" id="lnNbr_${_pageRef}"
										mode="number" nbFormat="######" maxlength="6" readonly="true"></ps:textfield>
								</td>

								<td width="38%" align="center">
									<ps:label value="%{getText('reporting.subLineNb')}" />
								</td>
								<td width="15%">
									<ps:textfield name="glCO.glstmpltGlsDetVO.LINE_NBR_DET" id="subLineNb_${_pageRef}"
										mode="number" nbFormat="######" maxlength="6" readonly="true"></ps:textfield>
								</td>

							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" width="9%">
						<ps:label key="gl.glCompCode" for="lookuptxt_glCompCode_${_pageRef}"  />
					</td>
					<td align="left" width="19%">
						<psj:livesearch id="glCompCode_${_pageRef}"
							name="glCO.glstmpltGlsDetVO.GL_COMP" mode="number" readOnlyMode="false"
							actionName="${pageContext.request.contextPath}/path/templateMaintReport/companiesLkp"
							searchElement="" afterDepEvent="checkIfEmptyComp()"
							parameter="code:lookuptxt_glCompCode_${_pageRef},updates:calcTypeComboId_${_pageRef}"
							resultList="CODE:lookuptxt_glCompCode_${_pageRef},BRIEF_DESC_ENG:compCodeGlLookUp_${_pageRef}"
							dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/applyCompaniesDependency.action"
							dependency="compCodeGlLookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_glCompCode_${_pageRef}:commonDetVO.CODE">
						</psj:livesearch>
					</td>
					<td width="14%">
						<ps:textfield id="compCodeGlLookUp_${_pageRef}" name="glCO.GL_COMP_NAME"
							readonly="true"  tabindex="-1"></ps:textfield>
					</td>

					<td align="left" nowrap="nowrap" width="9%">
						<ps:label key="gl.branchCode" for="lookuptxt_branchCode_${_pageRef}"  />
					</td>
					<td width="19%">
						<psj:livesearch id="branchCode_${_pageRef}"
							name="glCO.glstmpltGlsDetVO.BRANCH_CODE" mode="number" readOnlyMode="false"
							actionName="${pageContext.request.contextPath}/path/templateMaintReport/branchLkp"
							searchElement=""
							paramList="code:lookuptxt_glCompCode_${_pageRef}"
							parameter="code:lookuptxt_glCompCode_${_pageRef},code1:lookuptxt_branchCode_${_pageRef}"
							resultList="CODE:lookuptxt_branchCode_${_pageRef},BRIEF_DESC_ENG:branchCodeGlLookUp_${_pageRef}"
							dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/applyBranchDependency.action"
							dependency="branchCodeGlLookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_branchCode_${_pageRef}:commonDetVO.CODE">
						</psj:livesearch>
					</td>
					<td width="14%">
						<ps:textfield id="branchCodeGlLookUp_${_pageRef}" name="glCO.BRANCH_NAME"
							readonly="true" tabindex="-1" ></ps:textfield>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<ps:label key="gl.divCode" for="lookuptxt_divCode_${_pageRef}"  />
					</td>
					<td>
						<psj:livesearch id="divCode_${_pageRef}" name="glCO.glstmpltGlsDetVO.DIV_CODE"
							mode="number" readOnlyMode="false"
							afterDepEvent="checkIfEmptyDiv()"
							actionName="${pageContext.request.contextPath}/path/templateMaintReport/divisionLkp"
							searchElement=""
							paramList="code:lookuptxt_glCompCode_${_pageRef}"
							parameter="code1:lookuptxt_glCompCode_${_pageRef},code:lookuptxt_divCode_${_pageRef}"
							resultList="CODE:lookuptxt_divCode_${_pageRef},BRIEF_DESC_ENG:divCodeLookUp_${_pageRef}"
							dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/applyDivisionDependency.action"
							dependency="divCodeLookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_divCode_${_pageRef}:commonDetVO.CODE">
						</psj:livesearch>
					</td>
					<td>
						<ps:textfield name="glCO.DIV_NAME" id="divCodeLookUp_${_pageRef}"
							readonly="true" tabindex="-1" ></ps:textfield>
					</td>
					<td align="left" nowrap="nowrap">
						<ps:label key="gl.deptCode" for="lookuptxt_deptCode_${_pageRef}"  />
					</td>
					<td>
						<psj:livesearch id="deptCode_${_pageRef}" name="glCO.glstmpltGlsDetVO.DEPT_CODE"
							mode="number" readOnlyMode="false"
							actionName="${pageContext.request.contextPath}/path/templateMaintReport/DepartmentsLkp"
							searchElement=""
							paramList="column1:lookuptxt_divCode_${_pageRef},code:lookuptxt_glCompCode_${_pageRef}"
							parameter="code1:lookuptxt_glCompCode_${_pageRef},code:lookuptxt_deptCode_${_pageRef},updates:lookuptxt_divCode_${_pageRef}"
							resultList="CODE:lookuptxt_deptCode_${_pageRef},BRIEF_DESC_ENG:deptCodeLookUp_${_pageRef}"
							dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/applyDeptDependency.action"
							dependency="deptCodeLookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_deptCode_${_pageRef}:commonDetVO.CODE">
						</psj:livesearch>
					</td>
					<td>
						<ps:textfield name="glCO.DEPT_NAME" id="deptCodeLookUp_${_pageRef}"
							readonly="true" tabindex="-1" ></ps:textfield>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<ps:label key="reporting.from" for="lookuptxt_fromGLCode_${_pageRef}"  />
					</td>
					<td>
						<psj:livesearch id="fromGLCode_${_pageRef}" name="glCO.glstmpltGlsDetVO.FROM_GL"
							mode="number" readOnlyMode="false" maxlength="6"
							afterDepEvent="checkFromToGl(1)"
							actionName="${pageContext.request.contextPath}/path/templateMaintReport/fromGLLkp"
							searchElement=""
							paramList="code:lookuptxt_glCompCode_${_pageRef},str:fromHidd_${_pageRef}"
							parameter="code1:lookuptxt_glCompCode_${_pageRef},code:lookuptxt_fromGLCode_${_pageRef}"
							resultList="CODE:lookuptxt_fromGLCode_${_pageRef},BRIEF_DESC_ENG:fromGlLookUp_${_pageRef}"
							dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/applyFromGLDependency.action"
							dependency="fromGlLookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_fromGLCode_${_pageRef}:commonDetVO.CODE">
						</psj:livesearch>
					</td>
					<td>
						<ps:textfield name="glCO.FROM_GLStr" id="fromGlLookUp_${_pageRef}"
							readonly="true" tabindex="-1" ></ps:textfield>
					</td>

					<td align="left" nowrap="nowrap">
						<ps:label key="reporting.to" for="lookuptxt_toGLCode_${_pageRef}"  />
					</td>
					<td>
						<psj:livesearch id="toGLCode_${_pageRef}" name="glCO.glstmpltGlsDetVO.TO_GL"
							mode="number" readOnlyMode="false" maxlength="6"
							afterDepEvent="checkFromToGl(2)"
							actionName="${pageContext.request.contextPath}/path/templateMaintReport/fromGLLkp"
							searchElement=""
							paramList="code:lookuptxt_glCompCode_${_pageRef},str:toHidd_${_pageRef}"
							parameter="code1:lookuptxt_glCompCode_${_pageRef},code:lookuptxt_toGLCode_${_pageRef}"
							resultList="CODE:lookuptxt_toGLCode_${_pageRef},BRIEF_DESC_ENG:toGlLookUp_${_pageRef}"
							dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/applyFromGLDependency.action"
							dependency="toGlLookUp_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_toGLCode_${_pageRef}:commonDetVO.CODE">
						</psj:livesearch>

					</td>
					<td>
						<ps:textfield name="glCO.TO_GLStr" id="toGlLookUp_${_pageRef}" readonly="true"
							tabindex="-1" ></ps:textfield>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td align="left">
						<ps:label value="%{getText('gl.percentage')}" id="percentageLabel_${_pageRef}"/>
					</td>
					<td>
						<ps:textfield name="glCO.glstmpltGlsDetVO.PERCENTAGE" id="percentage_${_pageRef}" mode="number"
							nbFormat="##.##" maxLenBeforeDec="2" ></ps:textfield>
					</td>
					<td nowrap="nowrap"  align="left">
						<ps:label key="gl.ExclClosingEntry" for="exclClosingEntry_${_pageRef}" />
						<ps:checkbox id="exclClosingEntry_${_pageRef}"
							name="glCO.EXCLUDE_CLOSING_ENTRY_CHK"></ps:checkbox>
					</td>
					<td align="right">
									<ps:label value="%{getText('reporting.calculationType')}" />
					</td>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td width="53%">
										<ps:select list="calcTypeArrList" listKey="VALUE_CODE"
											listValue="VALUE_DESC" name="glCO.glstmpltGlsDetVO.CALC_TYPE"
											id="calcTypeComboId_${_pageRef}" afterDepEvent="hideSelecAccTemplate(1)"
											parameter="updates:calcTypeComboId_${_pageRef},glCO.glstmpltGlsDetVO.CALC_TYPE:calcTypeComboId_${_pageRef}"
			                				dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyCalcTypeDependency"
			                				dependency="lookuptxt_divCode_${_pageRef}:glCO.glstmpltGlsDetVO.DIV_CODE
			                				,divCodeLookUp_${_pageRef}:glCO.DIV_NAME,lookuptxt_deptCode_${_pageRef}:glCO.glstmpltGlsDetVO.DEPT_CODE
			                				,deptCodeLookUp_${_pageRef}:glCO.DEPT_NAME,lookuptxt_fromGLCode_${_pageRef}:glCO.glstmpltGlsDetVO.FROM_GL
			                				,fromGlLookUp_${_pageRef}:glCO.FROM_GLStr,lookuptxt_toGLCode_${_pageRef}:glCO.glstmpltGlsDetVO.TO_GL
			                				,toGlLookUp_${_pageRef}:glCO.TO_GLStr,lookuptxt_glCompCode_${_pageRef}:glCO.glstmpltGlsDetVO.GL_COMP
			                				,compCodeGlLookUp_${_pageRef}:glCO.GL_COMP_NAME,lookuptxt_branchCode_${_pageRef}:glCO.glstmpltGlsDetVO.BRANCH_CODE
			                				,branchCodeGlLookUp_${_pageRef}:glCO.BRANCH_NAME"
										/>
								</td>
								<td width="47%">
									<div id="selecAccDiv_<ps:property value="_pageRef" escapeHtml="true"/>">	
										<ptt:toolBar id="selecAcc_${_pageRef}">
											<psj:a   button="true" onclick="openSelecAccScreen()" >
												<ps:text name="reporting.selectAccount"></ps:text>
											</psj:a>
				         				</ptt:toolBar> 
				         			</div>
	         					</td>
	         				</tr>
	         			</table>
					</td>
					<td>
					</td>
				</tr>
				<tr>
				<td colspan="7">
					<ptt:toolBar id="tempGL_${_pageRef}">
						<psj:submit  button="true" buttonIcon="ui-icon-disk" id="glOK_${_pageRef}">
							<ps:label key="reporting.ok"></ps:label>
						</psj:submit>
	         		</ptt:toolBar> 
				</td>
			</tr>
			</table>
		</ps:form>
</ps:collapspanel>
</ps:collapsgroup>
<script>
$("#glMaintFrmId_"+_pageRef).processAfterValid("saveGLButton");	
$("#glOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
document.getElementById("glOK_"+_pageRef).disabled=true
</script>