<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>
<ps:set name="missingExt_var"
	value="%{getEscText('template.missingExt')}" />
<ps:set name="pathEnteredCorrect_var"
	value="%{getEscText('template.correctEnteredPath')}" />
<script>
var missingExt = '<ps:property value="missingExt_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var pathEnteredCorrect = '<ps:property value="pathEnteredCorrect_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
</script>
<ps:form  applyChangeTrack="true" id="mainFrmId" namespace="/path/templateMaintReport"
	useHiddenProps="true">
	<table class="headerPortionContent ui-widget-content" width="100%" >
		<tr>
			<td>
				<table width="100%" >
					<tr>
						<td nowrap="nowrap" width="15%" align="left">
							<ps:label key="reporting.code" for="templCode_${_pageRef}" />
						</td>
						<td nowrap="nowrap" width="40%">
							<ps:textfield size="6" name="glstmpltCO.glstmpltVO.CODE"
								id="templCode_${_pageRef}" maxlength="4" mode="number" nbFormat="####" 
								onchange="checkTemplCode(0,this)" minValue="0" required="true"></ps:textfield>
						</td>
						<td colspan="2" width="45%"></td>
					</tr>
					<tr>
						<td nowrap="nowrap" width="15%" align="left">
							<ps:label key="template.title" for="templDesc_${_pageRef}" />
						</td>
						<td nowrap="nowrap" width="40%">
							<ps:textfield name="glstmpltCO.templateTitle"
								size="50" id="templDesc_${_pageRef}" maxlength="500"
								required="true"></ps:textfield>
						</td>
						<td colspan="2" width="45%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" >
					<tr>
						<td nowrap="nowrap" width="15%" align="left">
							<ps:label value="%{getText('template.arabicTitle')}" />
						</td>
						<td nowrap="nowrap" width="38%">
							<ps:textfield size="50" onlyArabic="true"
								name="glstmpltCO.glstmpltVO.BRIEF_NAME_ARAB"
								id="templBriefArab_${_pageRef}" maxlength="500"></ps:textfield>
						</td>
						<td colspan="2" width="47%"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td nowrap="nowrap" align="left" width="15%">
							<ps:label id="forDec_${_pageRef}"
								value="%{getText('template.displayValules')}" />
						</td>
						<td nowrap="nowrap" width="40%">
							<table width="100%">
								<tr>
									<td width="50%" nowrap="nowrap">
										<ps:select list="dispValArrListt" listKey="VALUE_CODE"
											listValue="VALUE_DESC"
											name="glstmpltCO.glstmpltVO.DISPLAY_VALUES"
											id="dispValComboId_${_pageRef}" />
									</td>
									<td width="50%"></td>
								</tr>
							</table>
						</td>
						<td width="45%" colspan="2"></td>
					</tr>
				</table>
			</td>
		</tr>
		<!-- tiw -->
		<tr>
			<td>
				<table width="100%">
					<tr>
						<td width="20%" nowrap="nowrap">
							<table width="100%">
								<tr>
									<td nowrap="nowrap">
										<ps:label value="%{getText('reporting.financialCustReport')}" />
									</td>
									<td>
										<ps:checkbox parameter="glstmpltCO.FCRStr:fcrId_${_pageRef}"
											dependency="fcrId_${_pageRef}:glstmpltCO.FCRStr,dispValComboId_${_pageRef}:glstmpltCO.glstmpltVO.DISPLAY_VALUES"
											dependencySrc="${pageContext.request.contextPath}/path/templateMaintReport/fcrDependency"
											name="glstmpltCO.FCRStr" id="fcrId_${_pageRef}"></ps:checkbox>
									</td>
								</tr>
							</table>
						</td>
						<td id="showBws" width="80%" nowrap="nowrap">
							<table width="100%">
								<tr>
									<td width="15%" nowrap="nowrap" align="right">
										<ps:label value="%{getText('reporting.percLevel')}" />
									</td>
									<td width="10%" nowrap="nowrap">
										<ps:radio name="glstmpltCO.glstmpltVO.PER_LINE_GL"
											onchange="lineGlRadioChange()" id="perLineGl_${_pageRef}"
											list="#{'L':'reporting.line','G':'gl.glTitle'}"></ps:radio>
									</td>
									<td width="75%" nowrap="nowrap"></td>
								</tr>
							</table>
						</td>
						<td colspan="2" nowrap="nowrap">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4" nowrap="nowrap">

				<ptt:toolBar id="additionalButtons_${_pageRef}">
					<table width="100%">
						<tr>
							<td width="13%" nowrap="nowrap">

								<psj:submit id="openRelatedRep_${_pageRef}" type="button"
									button="true" onclick="openRelatedReportScreen()"
									cssStyle="width:100%">
									<ps:text name="template.relatedReports" />
								</psj:submit>

							</td>
							<td width="13%" nowrap="nowrap">
								<psj:submit id="createLikeButtonTmp_${_pageRef}" type="button"
									button="true" onclick="openCreateLikePopup()"
									cssStyle="width:100%">
									<ps:text name="template.createLike" />
								</psj:submit>
							</td>
							<%--<td width="13%" nowrap="nowrap">
						
								<psj:submit type="button" button="true" onclick="openOwnershipPercentageScreen()" cssStyle="width:100%">
													<ps:text name="template.ownershipScreen" />
								</psj:submit>
			
						</td>
						<td width="12%" nowrap="nowrap">
							
								<psj:submit type="button"  button="true" onclick="openHeaderOperatorScreen()" cssStyle="width:100%">
													<ps:text name="template.headerOpScreen" />
								</psj:submit>
		
						</td>
						--%>
							<td width="74%"></td>
						</tr>
					</table>
				</ptt:toolBar>
			</td>
		</tr>
	</table>
</ps:form>
<script type="text/javascript">
$("#mainFrmId").processAfterValid("saveAllTemplateInfo", {});
</script>