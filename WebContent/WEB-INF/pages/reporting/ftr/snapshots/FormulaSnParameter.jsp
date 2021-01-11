<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>
<html>
<script>
   // $.struts2_jquery.require("SnapshotParameterList.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/snapshots/");
	var fromDrllDwn= "<%=RepConstantsCommon.SNAPSHOT_DRILLDOWN%>";
	$(document).ready(function(){	
		formula_ready_func();
	});
</script>
<body>

<ps:hidden  name="repSnapshotParamVO.REP_REFERENCE"		id="repFormula_${_pageRef}"/>
<ps:hidden  name="repSnapshotParamVO.REP_REFERENCE"		id="colFormula2_${_pageRef}"/>
<ps:hidden  name="repSnapshotParamVO.REP_ID"			id="repIdFormula_${_pageRef}"/>
<ps:hidden  name="appName"			id="appName_${_pageRef}"/>

<ps:hidden  id="selectedTxtArea_${_pageRef}"/>
<ps:set name="repRef_${_pageRef}" value="repSnapshotParamVO.REP_REFERENCE"/>
<ps:set name="currAppName_${_pageRef}" value="appName"/>
<ps:url id="urlFunctionsGrid" escapeAmp="false" action="SnapshotParameterListAction_loadFormulaFuncGrid" namespace="/path/snapshotParameter">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
<ps:url id="urlExpressionGrid" escapeAmp="false" action="SnapshotParameterListAction_loadFormulaExprGrid" namespace="/path/snapshotParameter">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>	
<ps:url id="urlColumnsGrid" escapeAmp="false" action="SnapshotParameterListAction_loadFormulaRepColGrid" namespace="/path/snapshotParameter">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
   <ps:param name="updates2" value="repRef_${_pageRef}"></ps:param>
   <ps:param name="appName" value="currAppName_${_pageRef}"></ps:param>
</ps:url>	
<ps:url id="urlParamsGrid" escapeAmp="false" action="SnapshotParameterListAction_loadFormulaParamGrid" namespace="/path/snapshotParameter">
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
   <ps:param name="updates2" value="repRef_${_pageRef}"></ps:param>
   <ps:param name="appName" value="currAppName_${_pageRef}"></ps:param>
</ps:url>

		<div class="collapsibleContainer" title="<ps:text name='Expression'/>">
			<table  width="100%" border ="1">
				<tr>
					<td width="80%" rowspan="4">
						<table  width="100%">
							<tr>
								<td>
									<div id="headerDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="connectedSortable ui-helper-reset"
										title="<ps:text name="Header_key"/>">
										<div class="collapsibleContainer"
											title="<ps:text name="Header_key"/>">
											<ps:textarea name="repSnapshotParamVO.SITCOM_FORMULA_HEADER"
												id="sitcomFormulaHeader_${_pageRef}"
												cssClass="textCompSize ui-state-focus ui-corner-all"
												onfocus="$('#selectedTxtArea_'+_pageRef).val(1)"
												cssStyle="height:130px;" readonly="false">
											</ps:textarea>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div id="detailsDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="connectedSortable ui-helper-reset"
										title="<ps:text name="details_formula_key"/>">
										<div class="collapsibleContainer"
											title="<ps:text name="details_formula_key"/>">
											<ps:textarea name="repSnapshotParamVO.SITCOM_FORMULA"
												id="sitcomFormula_${_pageRef}"
												cssClass="textCompSize ui-state-focus ui-corner-all"
												onfocus="$('#selectedTxtArea_'+_pageRef).val(2)"
												cssStyle="height:130px;" readonly="false">
											</ps:textarea>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div id="footerDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="connectedSortable ui-helper-reset"
										title="<ps:text name="footer_key"/>">
										<div class="collapsibleContainer"
											title="<ps:text name="footer_key"/>">
											<ps:textarea name="repSnapshotParamVO.SITCOM_FORMULA_FOOTER"
												id="sitcomFormulaFooter_${_pageRef}"
												cssClass="textCompSize ui-state-focus ui-corner-all"
												onfocus="$('#selectedTxtArea_'+_pageRef).val(3)"
												cssStyle="height:130px;" readonly="false">
											</ps:textarea>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div id="sortSnColsFrm"
										class="connectedSortable ui-helper-reset"
										title="<ps:text name="%{getText('snapshots.reportPreview')}"/>">
										<div class="collapsibleContainer" 
											title="<ps:text name="%{getText('snapshots.reportPreview')}"/>">
											<div id="ColDivPreviewFormula_<ps:property value="_pageRef" escapeHtml="true"/>" style="height: 380px;width: 790px;overflow: scroll;max-width: 790px;"
												class="collapsibleContainer" 
												title="<ps:text name="%{getText('snapshots.reportPreview')}"/>">
											</div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td width="20%" valign="top">
						<div style="height: 150px ;overflow: auto;">
						<psjg:grid id="reportParamsGrid_${_pageRef}"
							href="%{urlParamsGrid}" dataType="json" pager="false"
							navigator="false" navigatorSearch="false"
							navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
							navigatorEdit="false" navigatorAdd="false"
							navigatorDelete="false" caption=" " navigatorRefresh="true"
							viewrecords="true" sortable="false" gridModel="gridModel"
							shrinkToFit="true" ondblclick="fillTextArea(4)"
							pagerButtons="false">
							<psjg:gridColumn name="ARGUMENT_NAME" sortable="false" index="ARGUMENT_NAME" id="ARGUMENT_NAME" title="%{getText('reporting.argumentName')}" 
							colType="text" />
							<psjg:gridColumn name="ARGUMENT_LABEL" sortable="false" index="ARGUMENT_LABEL" id="ARGUMENT_LABEL" title="%{getText('reporting.argumentLabel')}" 
							colType="text" />
						</psjg:grid>
					    </div>
					</td>
				    </tr>
					<tr>
					<td>
						<div style="height: 150px ;overflow: auto;" >
							<psjg:grid
											id               ="reportColumnGrid_${_pageRef}"
										    href             ="%{urlColumnsGrid}"
										    dataType         ="json"
											pager            ="false"	navigator="false" navigatorSearch="false"
											navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
											navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
											caption =" "
											navigatorRefresh ="true" viewrecords="true" 
											sortable         ="false"
											gridModel        ="gridModel"
										  	shrinkToFit      ="true"
										  	ondblclick="fillTextArea(1)"
										    pagerButtons	 ="false">
										  	 <psjg:gridColumn  name="repSnapshotDrilColVO.COLUMN_DRILLDOWN" sortable="false" index="repSnapshotDrilColVO.COLUMN_DRILLDOWN" id="repSnapshotDrilColVO.COLUMN_DRILLDOWN" title="%{getText('snapshots.columns')}" colType="text"/>
										    </psjg:grid>
										    </div>
					</td>
				</tr>
			
				<tr>
					<td >
						<div style="height: 420px ;overflow: auto;" >
							<psjg:grid id="functionsGridId_${_pageRef}"
								href="%{urlFunctionsGrid}" dataType="json" pager="false"
								navigator="false" navigatorSearch="false"
								navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
								navigatorEdit="false" navigatorAdd="false"
								navigatorDelete="false" caption=" " navigatorRefresh="true"
								viewrecords="true" scroll="true" sortable="false"
								gridModel="gridModel" shrinkToFit="true"
								ondblclick="fillTextArea(2)" pagerButtons="false">
								<psjg:gridColumn name="VALUE_DESC" index="VALUE_DESC"
									id="VALUE_DESC" title="%{getText('snapshots.functions')}" colType="text" sortable="false"/>
							</psjg:grid>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div style="height: 120px ;overflow: auto;" >
						<psjg:grid id="expressionGridId_${_pageRef}"
							href="%{urlExpressionGrid}" dataType="json" pager="false"
							navigator="false" navigatorSearch="false"
							navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
							navigatorEdit="false" navigatorAdd="false"
							navigatorDelete="false" caption=" " navigatorRefresh="true"
							viewrecords="true" sortable="false" gridModel="gridModel"
							shrinkToFit="true" pagerButtons="false"
							ondblclick="fillTextArea(3)">
							<psjg:gridColumn name="VALUE_DESC" index="VALUE_DESC"
								id="VALUE_DESC" title="%{getText('snapshots.expression')}" colType="text" sortable="false"/>
						</psjg:grid>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script>
	hideTitleBars_formula();	       
	</script>
</html>