<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>

<ps:set name="fillParamAlert_var" 			value="%{getEscText('sch.fillParams')}"/>
<ps:set name="fillSchedReport_var" 			value="%{getEscText('sch.fillSchedReport')}"/>
<ps:set name="selectSched_var" 				value="%{getEscText('sch.selectSched')}"/>
<ps:set name="schedRepExists_var" 			value="%{getEscText('sch.schedRepExists')}"/>
<ps:set name="schedInUse_var" 				value="%{getEscText('sch.schedInUse')}"/>
<ps:set name="fillSchedRequFe_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="selectMonth_var" 				value="%{getEscText('sch.selectMonth')}"/>
<ps:set name="selectDay_var" 				value="%{getEscText('sch.selectDay')}"/>
<ps:set name="dateChecking_var" 			value="%{getEscText('sch.dateChecking')}"/>
<ps:set name="selectRepId_var" 				value="%{getEscText('sch.selectRepId')}"/>
<ps:set name="missingToCcBcc_var" 			value="%{getEscText('sch.missingToCcBcc')}"/>
<ps:set name="missingBodySubj_var" 			value="%{getEscText('sch.missingBodySubj')}"/>
<ps:set name="repSchedNoHeadAndFoot_var"	value="%{getEscText('reporting.noHeadAndFoot')}"/>
<ps:set name="repSchedNoHeaders_var" 		value="%{getEscText('reporting.noHeaders')}"/>
<ps:set name="missingPassword_var" 			value="%{getEscText('sch.missingPassword')}"/>
<ps:set name="fillhourminutes_var" 			value="%{getEscText('sch_fillhourminutes')}"/>
<ps:set name="cannotModifySchedule" 		value="%{getEscText('cannotModifySchedule')}"/>

<html>
<script type="text/javascript">
var fillParamAlert 			= '<ps:property value="fillParamAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var fillSchedReport 		= '<ps:property value="fillSchedReport_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectSched 			= '<ps:property value="selectSched_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var schedRepExists 			= '<ps:property value="schedRepExists_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var schedInUse 				= '<ps:property value="schedInUse_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var fillSchedRequFe 		= '<ps:property value="fillSchedRequFe_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectMonth 			= '<ps:property value="selectMonth_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectDay 				= '<ps:property value="selectDay_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var dateChecking 			= '<ps:property value="dateChecking_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectRepId 			= '<ps:property value="selectRepId_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingToCcBcc 			= '<ps:property value="missingToCcBcc_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingBodySubj 		= '<ps:property value="missingBodySubj_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repSchedNoHeadAndFoot 	= '<ps:property value="repSchedNoHeadAndFoot_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repSchedNoHeaders 		= '<ps:property value="repSchedNoHeaders_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingPassword			= '<ps:property value="missingPassword_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var fillhourminutes 		= '<ps:property value="fillhourminutes_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var cannotModifySchedule 	= "${cannotModifySchedule}";
var grpCmbUrl = "";
var schedConstId="<%=RepConstantsCommon.SCHED_CONST_ID%>";


</script>

	<body>

		<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

	<ps:collapsgroup id="schedCollapsGrp_${_pageRef}">
			<ps:collapspanel id="schedCollaps_${_pageRef}"  key="sch.schedule">			
					<ps:url var="urlGrid" action="loadSchedGrid"
						namespace="/path/scheduler"></ps:url>
					<psjg:grid id="schedGrid" gridModel="gridModel" dataType="json"
						href="%{urlGrid}" pager="true" navigator="true"
						navigatorSearch="true"
						navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
						navigatorEdit="false" navigatorAdd="true" navigatorDelete="true"
						navigatorRefresh="true" viewrecords="true" rowNum="5"
						rowList="5,10,15,20" ondblclick="mainGridClicked()"
						onCompleteTopics="emptySchedTrx" sortable="true"
						addfunc="addSchedule" delfunc="onMainDelClicked">

						<psjg:gridColumn name="SCHED_VO.SCHED_ID" id="SCHED_ID" width="70"
							title="SCHED_ID" colType="number" editable="false"
							sortable="false" key="true" hidden="true" index="SCHED_ID" />

						<psjg:gridColumn name="SCHED_VO.SCHED_NAME" id="SCHED_NAME" width="70"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
							title="%{getText('sch.name')}" colType="text" editable="false"
							sortable="false" />
						<psjg:gridColumn name="SCHED_TYPE_STR" id="SCHED_TYPE_STR"
							width="70" title="%{getText('sch.schedType')}" colType="text"
							editable="false" sortable="false" hidden="false" />

						<psjg:gridColumn name="SCHED_VO.SCHED_FREQUENCY" id="SCHED_FREQUENCY"
							width="70" title="SCHED_FREQUENCY" colType="text"
							editable="false" sortable="false" hidden="true" />

						<psjg:gridColumn name="SCHED_VO.NEXT_RUN_DATE" id="NEXT_RUN_DATE"
							width="70" title="%{getText('sch.next_run_date')}" colType="text"
							formatter="date" editable="false" sortable="false" hidden="true" />

						<psjg:gridColumn name="SCHED_VO.SCHED_EXPIRY_DATE" id="SCHED_EXPIRY_DATE"
							width="70" title="%{getText('sch.schedule_expiry_date')}"
							colType="text" formatter="date" editable="false" sortable="false"
							hidden="true" />

						<psjg:gridColumn name="NEXT_RUN_DATE_STR" id="NEXT_RUN_DATE_STR"
							width="70"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
							title="%{getText('sch.next_run_date')}" colType="text"
							editable="false" sortable="false" />
						<psjg:gridColumn name="FIRST_RUN_DATE_STR" id="FIRST_RUN_DATE_STR"
							width="70" title="FIRST_RUN_DATE_STR" colType="text"
							editable="false" sortable="false" hidden="true" />
						<psjg:gridColumn name="SCHED_EXPIRY_DATE_STR"
							id="SCHED_EXPIRY_DATE_STR" width="70"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
							title="%{getText('sch.schedule_expiry_date')}" colType="text"
							editable="false" sortable="false" />

					</psjg:grid>
	</ps:collapspanel>
</ps:collapsgroup>



		<div id="schedMaintDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
			<%@include file="SchedulerMaint.jsp"%>
		</div>
		<ps:form id="frmReportsGrid">
			<div id="allReportsGridDiv">
				<div id="inAllReportsGridDiv">
					<ps:url var="urlRepGrid" action="scheduler_loadSchedReportGrid"
						namespace="/path/scheduler"></ps:url>
					<psjg:grid id="schedReportGrid" gridModel="gridModel"
						dataType="json" href="%{urlRepGrid}" pager="true" navigator="true"
						navigatorSearch="true"
						navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
						navigatorEdit="false" navigatorAdd="true" navigatorDelete="true"
						navigatorRefresh="true" viewrecords="true" rowNum="5"
						rowList="5,10,15,20" ondblclick="detGridClicked()" sortable="true"
						addfunc="addReportRec" delfunc="onDetDelClicked">

						<psjg:gridColumn name="SCHED_ID" id="SCHED_ID" width="70"
							title="SCHED_ID" colType="number" editable="false"
							sortable="false" hidden="true" index="SCHED_ID" />

						<psjg:gridColumn name="REPORT_ID" id="REPORT_ID" width="70"
							title="REPORT_ID" colType="text" editable="false" sortable="true"
							hidden="true" index="REPORT_ID" />

						<psjg:gridColumn name="REPORT_NAME" id="REPORT_NAME" width="70"
							index="REPORT_NAME" title="%{getText('reportName')}"
							colType="text" editable="false" sortable="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />

						<psjg:gridColumn name="REPORT_FORMAT_TRANS" id="REPORT_FORMAT_TRANS"
							width="70" index="REPORT_FORMAT_TRANS"
							title="%{getText('reportFormat')}" colType="text"
							editable="false" sortable="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
							
						<psjg:gridColumn name="REPORT_FORMAT" id="REPORT_FORMAT"
							width="70" index="REPORT_FORMAT"
							title="%{getText('reportFormat')}" colType="text"
							editable="false" sortable="true" hidden="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
							
						<psjg:gridColumn name="REPORT_REF" id="REPORT_REF"
							width="70" index="REPORT_REF"
							title="%{getText('reportFormat')}" colType="text"
							editable="false" sortable="true" hidden="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />	
					</psjg:grid>

				</div>
			</div>
		</ps:form>
		<div id="allReportsGridDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
			<div id="inAllReportsGridDiv" >
				<table width="100%">
					<tr>
						<td>
							<div >
								<ps:form id="frmReportsControls">
									<ps:hidden id="detMode" value="add" name="detMode"></ps:hidden>
									<table class="headerPortionContent ui-widget-content"
										width="420px" >
										<tr>
											<td>
												<ps:label key="reporting.fcrReport" />
											</td>
											<td>
												<ps:checkbox name="reportSchedCO.IS_FCR_YN" id="isFcrRep_${_pageRef}" valOpt="1:0"
												parameter="reportSchedCO.IS_FCR_YN:isFcrRep_${_pageRef}" 
												afterDepEvent="$('#embedDiv').load(jQuery.contextPath+'/path/reportsRet/dynRepParamsAction_loadDynParam.action?code=-1')"
												dependency="isFcrRep_${_pageRef}:reportSchedCO.IS_FCR_YN"
												dependencySrc="${pageContext.request.contextPath}/path/scheduler/scheduler_showHideFcr.action"
												>
												</ps:checkbox>													
											</td>
										</tr>
										<tr>
											<td width="200px">
												<psj:livesearch id="REPORT_ID"
													name="reportSchedCO.REPORT_ID" mode="number"
													readOnlyMode="false"
													actionName="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action"
													searchElement="" afterDepEvent="loadParams(1)"
													paramList="_pageRef:_pageRef"
													resultList="REPORT_ID:lookuptxt_REPORT_ID,REPORT_NAME:REPORT_NAME"
													parameter="reportId:lookuptxt_REPORT_ID"
													dependencySrc="${pageContext.request.contextPath}/path/scheduler/scheduler_findReportByID.action"
													dependency="REPORT_NAME:report.REPORT_NAME,lookuptxt_REPORT_ID:report.REPORT_ID,FROM_EMAIL_FE_VAL_${_pageRef}:emailFeLst,TO_EMAIL_FE_VAL_${_pageRef}:emailFeLst,CC_EMAIL_FE_VAL_${_pageRef}:emailFeLst,BCC_EMAIL_FE_VAL_${_pageRef}:emailFeLst,TO_EMAIL_CIF_VAL_${_pageRef}:emailCIFLst,ATTACH_FILE_NAME_${_pageRef}:emailFileNameLst,TO_EMAIL_FECOMP_VAL_${_pageRef}:emailComputeFeLst,CC_EMAIL_FECOMP_VAL_${_pageRef}:emailComputeFeLst,BCC_EMAIL_FECOMP_VAL_${_pageRef}:emailComputeFeLst,SECURED_PWD_FIELD_NAME_${_pageRef}:passwordFeLst">
												</psj:livesearch>
												<psj:livesearch id="fcrRepId_${_pageRef}"
													name="reportSchedCO.REPORT_REF" mode="text"
													readOnlyMode="false"
													actionName="${pageContext.request.contextPath}/path/scheduler/scheduler_repFcrLookup.action"
													searchElement="" afterDepEvent="loadParams(2)" 
													paramList="_pageRef:_pageRef"
													resultList="ftrOptVO.PROG_REF:lookuptxt_fcrRepId_${_pageRef},ftrOptVO.BRIEF_NAME_ENG:fcrRepName_${_pageRef},REPORT_ID:lookuptxt_REPORT_ID"
													parameter="updates:lookuptxt_fcrRepId_${_pageRef},reportId:lookuptxt_REPORT_ID"
													dependencySrc="${pageContext.request.contextPath}/path/scheduler/scheduler_findReportEngDesc.action"
													dependency="lookuptxt_fcrRepId_${_pageRef}:updates1,fcrRepName_${_pageRef}:updates,lookuptxt_REPORT_ID:reportId,REPORT_NAME:report.REPORT_NAME">
												</psj:livesearch>
											</td>
											<td>
												<ps:textfield id="REPORT_NAME"
													name="reportSchedCO.REPORT_NAME" readonly="true"
													tabindex="-1"></ps:textfield>
												<ps:textfield id="fcrRepName_${_pageRef}"
													name="reportSchedCO.FCR_REPORT_NAME" readonly="true"
													tabindex="-1"></ps:textfield>									
											</td>
										</tr>
										<tr>
											<td>
												<ps:label key="reportFormat" for="REPORT_FORMAT"/>
											</td>
											<td>
												<ps:select name="reportSchedCO.REPORT_FORMAT"
													id="REPORT_FORMAT" list="fileFormatList"
													listKey="VALUE_CODE" listValue="VALUE_DESC"
													onchange="checkSchedIfCSV(this.value)">
												</ps:select>
											</td>
										</tr>
										<tr>
											<td>
												<span id="sepLblTd_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label
														key="reporting.csvSeparator" for="csvSeparatorId_${_pageRef}" /> </span>
											</td>
											<td>
												<span id="sepValTd_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:select
														list="csvSeparators" listKey="code"
														listValue="description" name="reportSchedCO.CSV_SEPARATOR"
														id="csvSeparatorId_${_pageRef}" /> </span>
											</td>
										</tr>
										<tr>
											<td>
												<ps:label key="language_key" for="REPORT_LANGUAGE"/>
											</td>
											<td>
												<ps:select name="reportSchedCO.REPORT_LANGUAGE"
													id="REPORT_LANGUAGE" list="langList" listKey="LANG_CODE"
													listValue="LANG_NAME">
												</ps:select>
											</td>
										</tr>

										<tr>
											<td>
												<span id="noHeadAndFootLblSpan_<ps:property value="_pageRef" escapeHtml="true"/>">
												<ps:label value="%{getText('reporting.noHeadAndFoot')}" id="noHeadAndFootLbl_${_pageRef}"></ps:label>
												</span>
											</td>
											<td>
												<span id="noHeadAndFootSpan_<ps:property value="_pageRef" escapeHtml="true"/>">
												<ps:checkbox name="reportSchedCO.SHOW_HEAD_FOOT"
													id="noHeadAndFoot_${_pageRef}"></ps:checkbox>
													</span>
											</td>
										</tr>


										<tr>
											<td>
												<ps:label value="%{getText('sch.is_active')}" />
											</td>
											<td>
												<ps:checkbox id="IS_ACTIVE" name="reportSchedCO.IS_ACTIVE"></ps:checkbox>
											</td>
										</tr>
										<tr>
											<td>
												<ps:label value="%{getText('sch.print')}" />
											</td>
											<td nowrap="nowrap">
												<ps:checkbox id="IS_PRINT" name="reportSchedCO.IS_PRINT"
													onchange="isPrintChanged()">
												</ps:checkbox>
												<div id="printers">
													<ps:select name="reportSchedCO.PRINTER_NAME"
														id="PRINTER_NAME" list="printerNameList"
														listKey="VALUE_CODE" listValue="VALUE_DESC">
													</ps:select>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<ps:label value="%{getText('sch.printIfNoData')}" />
											</td>
											<td>
												<ps:checkbox name="reportSchedCO.PRINT_IF_NO_DATA_YN"
													id="PRINT_IF_NO_DATA_YN_${_pageRef}"></ps:checkbox>
											</td>
											<tr>
												<td>
													<ps:label key="sch.attFileName" for="ATTACH_FILE_NAME_${_pageRef}"/>
												</td>
												<td>
													<ps:select list="emailFileNameLst" listKey="VALUE_CODE"
														listValue="VALUE_DESC" onchange="hideShowDateDB()"
														name="reportSchedCO.ATTACH_FILE_NAME"
														id="ATTACH_FILE_NAME_${_pageRef}" />
												</td>
											</tr>

											<tr>
												<td>
													<span id="dateTypeLbl_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label
															key="sch.dateType" for="DATE_TYPE_${_pageRef}"/> </span>
												</td>
												<td>
													<span id="dateTypeVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:select
															list="dateTypeList" listKey="VALUE_CODE"
															listValue="VALUE_DESC" name="reportSchedCO.DATE_TYPE"
															id="DATE_TYPE_${_pageRef}" /> </span>
												</td>
											</tr>







											<tr>
												<td>
													<ps:label key="sch.saveReportAs" for="SAVE_DATA_TYPE_${_pageRef}" />
												</td>
												<td>
													<ps:select name="reportSchedCO.SAVE_DATA_TYPE"
														id="SAVE_DATA_TYPE_${_pageRef}" list="saveAsRepList"
														listKey="VALUE_CODE" listValue="VALUE_DESC"
														onchange="showHideMailConfig(this.value,1)">
													</ps:select>
												</td>
											</tr>
								</table>
								</ps:form>
							</div>
						</td>
						<td>
							<table >
								<tr>
									<td>
										<ps:label value="%{getText('sch.parameters')}" />
									</td>
								</tr>
								<tr>
									<td>
										<div
											style="width: 600px; height: 135px; overflow: auto; overflow-x: hidden;">
											<table class="headerPortionContent ui-widget-content"
												width="550px">
												<tr>
													<td>
														<div id="embedDiv" style="width: 1000px; height: 135px; overflow: auto; overflow-x: hidden;">
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<ps:form id="frmSchedMail_${_pageRef}">
					<table width="100%">
						<tr>
							<td colspan="2">
								<div id="mailConfigDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
									<table width="100%"
										class="headerPortionContent ui-widget-content">
										<tr>
											<td width="50%">
												<div
													style="overflow: auto; overflow-x: hidden;">
													<table width="100%">
														<tr>
															<td>
																<ps:label key="sch.ms"  for="lookuptxt_msHost_${_pageRef}"/>
															</td>
															<td>
																<span> <psj:livesearch id="msHost_${_pageRef}"
																		name="reportSchedCO.MAIL_SERVER_HOST" mode="text"
																		readOnlyMode="false"
																		actionName="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_msConfigLookup.action"
																		searchElement="" onOk="fillDefaultMS()"
																		resultList="mailServerVO.HOST:lookuptxt_msHost_${_pageRef},mailServerVO.MAIL_SERVER_CODE:msId_${_pageRef},mailServerVO.MAIL_FROM:DEFAULT_FROM_MS_${_pageRef}">
																	</psj:livesearch> </span>
																<span> <ps:hidden id="msId_${_pageRef}"
																		name="reportSchedCO.MAIL_SERVER_CODE"></ps:hidden> </span>
																<span> <ps:hidden
																		name="reportSchedCO.DEFAULT_FROM_MS"
																		id="DEFAULT_FROM_MS_${_pageRef}"></ps:hidden> </span>
															</td>
															<td align="center">
																<span>
																	<ps:label key="sch.default"/>
																</span>
																<span>
																	<ps:checkbox name="reportSchedCO.USE_DEFAULT_MS_YN" id="USE_DEFAULT_MS_YN_${_pageRef}" valOpt="1:0"
																	dependency="FROM_EMAIL_TYPE_${_pageRef}:fromMailList"
																	dependencySrc="${pageContext.request.contextPath}/path/scheduler/scheduler_defaultMsChecked.action"
																	parameter="reportSchedCO.USE_DEFAULT_MS_YN:USE_DEFAULT_MS_YN_${_pageRef}" ></ps:checkbox>
																</span>
																
															</td>

														</tr>

														<tr>
															<td>
																<ps:label key="ms.from" for="FROM_EMAIL_TYPE_${_pageRef}"/>
															</td>
															<td width="40%">
																<ps:select list="fromMailList" listKey="VALUE_CODE"
																	listValue="VALUE_DESC"
																	name="reportSchedCO.FROM_EMAIL_TYPE"
																	onchange="hideShowFromVal(this.value)"
																	id="FROM_EMAIL_TYPE_${_pageRef}" />
															</td>
															<td width="40%">
																<span id="fromVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:textfield
																		id="FROM_EMAIL_VAL_${_pageRef}"
																		name="reportSchedCO.FROM_EMAIL_VAL" readonly="true"></ps:textfield>
																</span>
																<span id="fromFeVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.FROM_EMAIL_FE_VAL"
																		onchange="fillFromMailVal()"
																		id="FROM_EMAIL_FE_VAL_${_pageRef}" /> </span>
															</td>
														</tr>

														<tr>
															<td>
																<ps:label value="%{getText('ms.to')}" />
															</td>
															<td>
																<ps:select list="toMailList" listKey="VALUE_CODE"
																	listValue="VALUE_DESC"
																	name="reportSchedCO.TO_EMAIL_TYPE"
																	onchange="hideShowToVal(this.value)"
																	id="TO_EMAIL_TYPE_${_pageRef}" />
															</td>
															<td>
																<span id="toVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:textfield
																		id="TO_EMAIL_VAL_${_pageRef}"
																		name="reportSchedCO.TO_EMAIL_VAL"></ps:textfield> </span>
																<span id="toFeVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.TO_EMAIL_FE_VAL"
																		onchange="fillToMailVal()"
																		id="TO_EMAIL_FE_VAL_${_pageRef}" /> </span>
																<span id="toFeCompVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailComputeFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.TO_EMAIL_FE_VAL"
																		onchange="fillCompToMailVal()"
																		id="TO_EMAIL_FECOMP_VAL_${_pageRef}" /> </span>
																<span id="toCIFVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailCIFLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.TO_EMAIL_CIF_VAL"
																		onchange="fillToCIFVal()"
																		id="TO_EMAIL_CIF_VAL_${_pageRef}" /> </span>
															</td>
														</tr>

														<tr>
															<td></td>
															<td colspan="2">
																<ps:collapspanel id='toUsersListDiv_${_pageRef}'  key="sch.toUsersList">
																	<div id="toUsersLisGridtDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
																</ps:collapspanel>
															</td>
														</tr>


														<tr>
															<td>
																<ps:label value="%{getText('sch.cc')}" />
															</td>
															<td>
																<ps:select list="ccMailList" listKey="VALUE_CODE"
																	listValue="VALUE_DESC"
																	name="reportSchedCO.CC_EMAIL_TYPE"
																	onchange="hideShowCcVal(this.value)"
																	id="CC_EMAIL_TYPE_${_pageRef}" />
															</td>
															<td>
																<span id="ccVal_${_pageRef}"> <ps:textfield
																		id="CC_EMAIL_VAL_${_pageRef}"
																		name="reportSchedCO.CC_EMAIL_VAL"></ps:textfield> </span>
																<span id="ccFeVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.CC_EMAIL_FE_VAL"
																		onchange="fillCCMailVal()"
																		id="CC_EMAIL_FE_VAL_${_pageRef}" /> </span>
																		
																<span id="ccFeCompVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailComputeFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.CC_EMAIL_FE_VAL"
																		onchange="fillCompCCMailVal()"
																		id="CC_EMAIL_FECOMP_VAL_${_pageRef}" /> </span>
																				
															</td>
														</tr>

														<tr>
															<td></td>
															<td colspan="2">
																<ps:collapspanel id='ccUsersListDiv_${_pageRef}'  key="sch.ccUsersList">
																	<div id="ccUsersLisGridtDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
																</ps:collapspanel>
															</td>
														</tr>



														<tr>
															<td>
																<ps:label value="%{getText('sch.bcc')}" />
															</td>
															<td>
																<ps:select list="bccMailList" listKey="VALUE_CODE"
																	listValue="VALUE_DESC"
																	name="reportSchedCO.BCC_EMAIL_TYPE"
																	onchange="hideShowBccVal(this.value)"
																	id="BCC_EMAIL_TYPE_${_pageRef}" />
															</td>
															<td>
																<span id="bccVal_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:textfield
																		id="BCC_EMAIL_VAL_${_pageRef}"
																		name="reportSchedCO.BCC_EMAIL_VAL"></ps:textfield> </span>
																<span id="bccFeVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.BCC_EMAIL_FE_VAL"
																		onchange="fillBCCMailVal()"
																		id="BCC_EMAIL_FE_VAL_${_pageRef}" /> </span>
																		
																<span id="bccFeCompVal_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none">
																	<ps:select list="emailComputeFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.BCC_EMAIL_FE_VAL"
																		onchange="fillBCCCompMailVal()"
																		id="BCC_EMAIL_FECOMP_VAL_${_pageRef}" /> </span>		
															</td>
														</tr>

														<tr>
															<td></td>
															<td colspan="2">
																	<ps:collapspanel id='bccUsersListDiv_${_pageRef}'  key="sch.bccUsersList">
																	<div id="bccUsersLisGridtDiv_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
																	</ps:collapspanel>
															</td>
														</tr>
													</table>
												</div>
											</td>
											<td width="50%">
												<div
													style="overflow: auto; overflow-x: hidden;">



													<table width="100%">

														<tr>
															<td>
																<ps:label key="ms.subject" for="EMAIL_SUBJECT_${_pageRef}"/>
															</td>
															<td>
																<ps:textfield id="EMAIL_SUBJECT_${_pageRef}"
																	name="reportSchedCO.EMAIL_SUBJECT" maxlength="200"></ps:textfield>
															</td>
														</tr>

														<tr>
															<td>
																<ps:label key="ms.body" for="EMAIL_BODY_${_pageRef}"/>
															</td>
															<td>
																<ps:textarea cssStyle="height: 100%;"
																	name="reportSchedCO.EMAIL_BODY"
																	id="EMAIL_BODY_${_pageRef}" rows="5" cols="65"
																	maxlength="4000" />
															</td>
														</tr>
														<tr>
															<td>
																<ps:label value="%{getText('sch.sendIfNoData')}" />
															</td>
															<td>
																<ps:checkbox name="reportSchedCO.SEND_IF_NO_DATA_YN"
																	id="SEND_IF_NO_DATA_YN_${_pageRef}"></ps:checkbox>
															</td>
														</tr>
														<tr>
															<td>
																<ps:label value="%{getText('sch.secured')}" id="securedLabel"/>
															</td>
															<td>
																<ps:checkbox name="reportSchedCO.SECURED_FILE_YN"
																	id="SECURED_FILE_YN_${_pageRef}" onchange="securedChanged()"></ps:checkbox>
									
															</td>	
															</tr>
															<tr>
															 <td>
															 	<div id="passwordLabel"> 
															 	<ps:label value="%{getText('sch.password')}" />
															 	</div>
															 </td>
															<td>
															<div id="password"> 
														
															<ps:select list="passwordFeLst" listKey="VALUE_CODE"
																		listValue="VALUE_DESC"
																		name="reportSchedCO.SECURED_PWD_FIELD_NAME"
																		id="SECURED_PWD_FIELD_NAME_${_pageRef}" />
															</div>
															</td>

	
													</tr>
												   </table>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>

				</ps:form>
			</div>
			<div id="splitFeDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
				<%@include file="SchedulerGroupByList.jsp"%>
			</div>
		</div>
		<br>
		<br>
	<div id="schedToolBarDivId" >
		<ptt:toolBar id="schedToolBar">
			<psj:submit id="saveSchdBut_${_pageRef}" button="true" buttonIcon="ui-ie con-disk"
				onclick="this.disabled=true;saveSchedule()">
				<ps:text name="reporting.save"></ps:text>
			</psj:submit>
		</ptt:toolBar>
	</div>
		<ps:form id="frmSchedMailGrid_${_pageRef}">
			<ps:hidden id="updates1_${_pageRef}" name="updates1"></ps:hidden>
			<ps:hidden id="updates2_${_pageRef}" name="updates2"></ps:hidden>
		</ps:form>

	</body>
	<script type="text/javascript">
$("#schedGrid").jqGrid('filterToolbar', {
	searchOnEnter : true
});

$('#mailFeGrpByGrid_' + _pageRef).jqGrid("setColProp", "FIELD_DESC", {
	width : 400
});
$('#mailFeGrpByGrid_' + _pageRef).jqGrid("setGridWidth", 420);

$(document)
		.ready(function() {
			//in updates2 we will store the value of the previous toType in order to track if the user has select the CIF option 
			$.struts2_jquery.require("Scheduler.js", null,jQuery.contextPath + "/path/js/reporting/scheduler/");
			schedReadyFunc();
			});
</script>