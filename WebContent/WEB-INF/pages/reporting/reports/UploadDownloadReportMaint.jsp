<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<ps:form id="downUpFrm_${_pageRef}" namespace="/path/designer"
	method="POST" enctype="multipart/form-data" useHiddenProps="true">
	<table width="100%" class="headerPortionContent ui-widget-content"
		cellspacing="10">

		<tr>
			<td width="15%">
				<ps:label key="reporting.applicationName"
					for="lookuptxt_appName_${_pageRef}"></ps:label>
			</td>
			<td width="30%" colspan="2">
				<psj:livesearch
					actionName="${pageContext.request.contextPath}/pathdesktop/ApplicationsLookup_constructLookup.action?lang_spec=1"
					paramList="webAppsOnly:webAppsOnly_${_pageRef}"
					name="repCO.APP_NAME" id="appName_${_pageRef}"
					searchElement="APP_NAME" autoSearch="true"
					onOk="emptyUpDownPRefLkp()"
					dependency="appName_${_pageRef}:repCO.APP_NAME,APP_IS_WEB_YN_${_pageRef}:repCO.APP_IS_WEB_YN"
					dependencySrc="${pageContext.request.contextPath}/path/designer/reportDesign_depCheckBoxVisiblity"
					parameter="repCO.APP_NAME:lookuptxt_appName_${_pageRef},repCO.PROG_REF:refId_${_pageRef}"
					tabindex="1" />					
					<ps:hidden id="APP_IS_WEB_YN_${_pageRef}" name="repCO.APP_IS_WEB_YN"></ps:hidden>
			</td>
			<td width="5%"></td>
			<td width="15%">
				<ps:text name="reporting.whenNoData"></ps:text>
			</td>
			<td width="30%">
				<ps:select id="whenNoData_${_pageRef}" name="repCO.WHEN_NO_DATA"
					list="whenNoDataList" listKey="VALUE_CODE" listValue="VALUE_DESC"
					tabindex="9"></ps:select>
			</td>
			<td width="5%"></td>
		</tr>

		<tr>
			<td>
				<ps:label key="designer.reportReference" for="refId_${_pageRef}"></ps:label>
			</td>


			<td colspan="2">
				<ps:textfield name="repCO.PROG_REF" id="refId_${_pageRef}"
					maxlength="12" size="70" onkeyup="upperProgRef()"
					dependency="refId_${_pageRef}:repCO.PROG_REF"
					dependencySrc="${pageContext.request.contextPath}/path/designer/reportDesign_depCheckBoxVisiblity"
					parameter="repCO.PROG_REF:refId_${_pageRef},repCO.APP_NAME:lookuptxt_appName_${_pageRef}"
					tabindex="2" />

			</td>
			<td></td>

			<td>
				<table>
					<tr>
						<td width="60%">
							<ps:label value="%{getText('reporting.langIndep')}"
								id="langIndepLbl_${_pageRef}"></ps:label>
						</td>
						<td width="38%">
							<ps:checkbox name="repCO.LANG_INDEPENDENT_YN" valOpt="1:0"
								id="langIndep_${_pageRef}" tabindex="10"></ps:checkbox>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<div id="noHeadFootSpan_<ps:property value="_pageRef" escapeHtml="true"/>">
					<table>
						<tr>
							<td width="40%">
								<ps:label value="%{getText('reporting.noHeadAndFoot')}"
									id="noHeadAndFootLbl_${_pageRef}"></ps:label>
							</td>
							<td width="38%">
								<ps:checkbox name="repCO.SHOW_HEAD_FOOT"
									id="noHeadAndFoot_${_pageRef}" tabindex="11"></ps:checkbox>
							</td>
							<td>
								<table width="100%">
									<tr>
										<td width="10%" nowrap="nowrap" style="margin-left: 0px;"><ps:label
												key="applyCifAuditkey"></ps:label></td>
										<td align="right"><ps:checkbox name="repCO.CIF_AUDIT_YN" valOpt="1:0"
												id="cifAuditYn_${_pageRef}" onchange="rep_enableReportCifAudit()"/></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</td>


			<td></td>
		</tr>

		<tr>
			<td>
				<ps:label key="reporting.parentRef"
					for="lookuptxt_fcrPRef_${_pageRef}"></ps:label>
			</td>
			<td nowrap="nowrap">
				<psj:livesearch
					actionName="${pageContext.request.contextPath}/path/fcrRep/fcr_loadPRefLkp"
					name="repCO.PARENT_REF" id="fcrPRef_${_pageRef}" searchElement=""
					autoSearch="true"
					paramList="pRefLkp:lookuptxt_fcrPRef_${_pageRef},applName:lookuptxt_appName_${_pageRef}"
					resultList="PROG_REF:lookuptxt_fcrPRef_${_pageRef},PROG_DESC:pRefStr_${_pageRef}"
					parameter="applName:lookuptxt_appName_${_pageRef},updates:lookuptxt_fcrPRef_${_pageRef},code:APP_IS_WEB_YN_${_pageRef}"
					dependencySrc="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByPRefId"
					dependency="lookuptxt_fcrPRef_${_pageRef}:commonDetVO.CODE_STR,pRefStr_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_CATEGORY_ID_${_pageRef}:commonDetVO.CODE,CATEGORY_DESC_${_pageRef}:commonDetVO.CATEGORY"
					tabindex="3" />
			</td>
			<td>
				<ps:textfield name="repCO.PARENT_REF_STR" id="pRefStr_${_pageRef}"
					readonly="true" tabindex="-1"></ps:textfield>
			</td>

			<td></td>
			<td>
				<ps:label value="%{getText('reporting.skipSqlValid')}"></ps:label>
			</td>
			<td>
				<ps:checkbox name="repCO.SKIP_QRY_VALIDATTION"
					id="skipQryValidation_${_pageRef}" tabindex="11"></ps:checkbox>
			</td>
			<td></td>

		</tr>
		<tr>
			<td>
			<ps:label key="reporting.lkpCategory" for="lookuptxt_CATEGORY_ID_${_pageRef}"></ps:label>
			</td>
			<td nowrap="nowrap">
				<psj:livesearch
					actionName="${pageContext.request.contextPath}/path/fcrRep/fcr_loadCategoriesLkp"
					name="repCO.CATEGORY_ID" id="CATEGORY_ID_${_pageRef}" searchElement="" autoSearch="true"
					resultList="CODE:lookuptxt_CATEGORY_ID_${_pageRef},BRIEF_DESC_ENG:CATEGORY_DESC_${_pageRef}"
					dependencySrc="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByCategoryId"
					dependency="lookuptxt_CATEGORY_ID_${_pageRef}:commonDetVO.CODE,CATEGORY_DESC_${_pageRef}:commonDetVO.BRIEF_DESC_ENG"
					parameter="code:lookuptxt_CATEGORY_ID_${_pageRef}"
					tabindex="3" />
			</td>
			<td>
				<ps:textfield name="repCO.CATEGORY_DESC" id="CATEGORY_DESC_${_pageRef}" readonly="true" tabindex="-1"></ps:textfield>
			</td>
			<td colspan="4"></td>
		</tr>

		<tr>
			<td>
				<ps:label value="%{getText('reporting.skipOptAxs')}"></ps:label>

			</td>
			<td colspan="2">
				<table>
					<tr>
						<td width="5%">
							<ps:checkbox name="repCO.SKIP_OPT_AXS"
								id="skipOptAxs_${_pageRef}" onchange="checkProgRefs()"
								tabindex="5">
							</ps:checkbox>
						</td>
						<td width="30%" align="right" id="menuTitleEngLbl_<ps:property value="_pageRef" escapeHtml="true"/>">
							<ps:label key="reporting.menuTitleEng"
								for="menuTitleEng_${_pageRef}"></ps:label>
						</td>
						<td width="65%" id="menuTitleEngTxt_<ps:property value="_pageRef" escapeHtml="true"/>">
							<ps:textfield name="repCO.MENU_TITLE_ENG"
								id="menuTitleEng_${_pageRef}" maxlength="100" />
						</td>
					</tr>
				</table>
			</td>
			<td></td>
			<td>
				<ps:text name="designer.defaultConnection"></ps:text>
			</td>

			<td>
				<ps:select id="connection_${_pageRef}" name="repCO.CONN_ID"
					list="connectionsList" listKey="CONN_ID" listValue="CONN_DESC"
					emptyOption="true" tabindex="12"></ps:select>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				<ps:label key="reportName" for="repName_${_pageRef}"></ps:label>
			</td>
			<td colspan="2">
				<ps:textfield name="repCO.REPORT_NAME" id="repName_${_pageRef}"
					maxlength="100" tabindex="6" />
			</td>
			<td></td>
			<td>
				<ps:text name="reporting.centralBnkRep" />
			</td>
			<td>
				<table width="100%">
					<tr>
						<td width="15%">
							<ps:checkbox name="repCO.REPORT_TYPE" id="reportType_${_pageRef}"
								tabindex="13"></ps:checkbox>
						</td>
						<td width="15%"></td>
						<td>
							<div id="xlsNameTr_<ps:property value="_pageRef" escapeHtml="true"/>">
								<table border="0" width="100%">
									<tr>
										<td nowrap="nowrap" width="10%">
											<ps:label key="reporting.xlsName" for="xlsName_${_pageRef}" />
										</td>
										<td width="5%"></td>

										<td width="38%">
											<ps:textfield name="repCO.xlsName" id="xlsName_${_pageRef}"
												maxlength="250" size="35" tabindex="14" />
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				<ps:label key="designer.defaultFormat" for="format_${_pageRef}"></ps:label>
			</td>
			<td colspan="2">
				<ps:select id="format_${_pageRef}" name="repCO.DEFAULT_FORMAT"
					list="reportFormats" listKey="VALUE_CODE" listValue="VALUE_DESC"
					emptyOption="true" onchange="checkUpdDownIfCSV(this.value)"
					tabindex="7"></ps:select>
			</td>

			<td></td>
			<td>
				<ps:label key="reporting.argPerRow"  for="argPerRow_${_pageRef}"/>
			</td>
			<td nowrap="nowrap">
				<ps:textfield id="argPerRow_${_pageRef}" name="repCO.NBR_DISPLAYED_ARG_PER_ROW"  maxlength="1" 
							  mode="number"  tabindex="15" size="4" minValue="1"/>
			</td>
		</tr>
		<tr>
			<td>
				<span id="sepLblTd_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label
						value="%{getText('reporting.csvSeparator')}" />
				</span>
			</td>
			<td colspan="2">
				<span id="sepValTd_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:select
						list="csvSeparators" listKey="code" listValue="description"
						name="repCO.CSV_SEPARATOR" id="csvSeparatorId_${_pageRef}"
						tabindex="8" /> </span>
			</td>
			<td></td>
			<td style="display: none;">
				<ps:label value="%{getText('sch.ms')}" />
			</td>
			<td nowrap="nowrap" style="display: none;">
				<table>
					<tr>
						<td width="5%">
							<psj:livesearch id="msCode_${_pageRef}"
								name="repCO.MAIL_SERVER_CODE" mode="number" readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_msConfigLookup.action"
								searchElement=""
								resultList="mailServerVO.HOST:msHost_${_pageRef},mailServerVO.MAIL_SERVER_CODE:lookuptxt_msCode_${_pageRef}"
								parameter="code:lookuptxt_msCode_${_pageRef}"
								dependency="lookuptxt_msCode_${_pageRef}:mailServerCO.mailServerVO.MAIL_SERVER_CODE,msHost_${_pageRef}:mailServerCO.mailServerVO.HOST"
								dependencySrc="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_retMailServerDependency.action">
							</psj:livesearch>
						</td>

						<td width="20%">
							<ps:textfield name="repCO.HOST" id="msHost_${_pageRef}"
								readonly="true" tabindex="-1">
							</ps:textfield>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="reporting_generated_file_name" for="generatedFileName_${_pageRef}"></ps:label>
			</td>
			<td colspan="2">
				<ps:textarea id="generatedFileName_${_pageRef}" name="repCO.GENERATED_FILE_NAME"  maxlength="500" />
				</td>
			<td></td>
			<td>
				<table  width="100%">
					<tr>
						<td width="5%" nowrap="nowrap" style="margin-left: 0px;"><ps:label
								key="reporting_addMetadataReport"></ps:label></td>
						<td><ps:checkbox name="repCO.METADATA_REPORT_YN" valOpt="1:0"
								id="metadataRepYn_${_pageRef}"
								parameter="repCO.METADATA_REPORT_YN:metadataRepYn_${_pageRef}"
								dependency="metadataRepYn_${_pageRef}:repCO.METADATA_REPORT_YN"
								dependencySrc="${pageContext.request.contextPath}/path/designer/upDownReport_reportMetadataVisiblity.action" /></td>
					</tr>
				</table>
			</td>
			<td>
				<table width="100%">
					<tr>
						<td width="10%" nowrap="nowrap" align="left"><ps:label
								key="reporting_metadataReport"
								for="lookuptxt_metadataReport_${_pageRef}"></ps:label></td>
						<td width="25%"><psj:livesearch
								id="metadataReport_${_pageRef}" name="repCO.METADATA_REPORT_ID"
								mode="number" readOnlyMode="false"
								actionName="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action"
								searchElement="" paramList="_pageRef:_pageRef"
								resultList="REPORT_ID:lookuptxt_metadataReport_${_pageRef},REPORT_NAME:metadataRepName_${_pageRef}"
								parameter="reportId:lookuptxt_metadataReport_${_pageRef}"
								dependencySrc="${pageContext.request.contextPath}/path/scheduler/scheduler_findReportByID.action"
								dependency="metadataRepName_${_pageRef}:report.REPORT_NAME,lookuptxt_metadataReport_${_pageRef}:report.REPORT_ID">
							</psj:livesearch></td>
						<td><ps:textfield id="metadataRepName_${_pageRef}"
								name="repCO.METADATA_REPORT_NAME" readonly="true"></ps:textfield>
						</td>
					</tr>
				</table>
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td colspan="4"></td>
			<td colspan="3">
				<table  width="100%">
					<tr>
						<td width="15%" nowrap="nowrap"><ps:label
								key="reporting_metadatarepextension"
								for="metadataRepExt_${_pageRef}"></ps:label></td>
						<td width="15%"><ps:textfield id="metadataRepExt_${_pageRef}"
								name="repCO.METADATA_REPORT_EXT" maxlength="10" size="15"></ps:textfield>
						</td>
						<td width="10%" align="right" nowrap="nowrap"><ps:label
							    key="reporting_location"
								for="location_${_pageRef}"></ps:label></td>
						<td><ps:textfield id="location_${_pageRef}"
								name="repCO.METADATA_GENERATED_LOCATION" maxlength="100"
								size="52"></ps:textfield></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label key="upDown.Upload" for="upload_${_pageRef}"></ps:label>
			</td>
			<td nowrap="nowrap" colspan="2">
				<ps:file name="upload" id="upload_${_pageRef}" size="57"
					tabindex="16" />
			</td>
				<td width="5%"></td>
			<td width="15%">
				<ps:label key="reporting.reportVersion"></ps:label>
			</td>
			<td width="30%"> 
			${repCO.REPORT_VERSION}

			</td>
			<td width="5%">
				<ps:textfield name="repCO.REPORT_MODIFIED_YN" id="reportModifiedYN_${_pageRef}"
					maxlength="16" tabindex="-1" readonly="true"  hidden ="true"/></td>
			<td width="5%"><ps:textfield name="repCO.REPORT_VERSION" id="reportVersion_${_pageRef}"
					maxlength="16" tabindex="-1" readonly="true" hidden ="true"/></td>
		</tr>
	</table>
	<ps:hidden id="dateUpdated_${_pageRef}" name="repCO.DATE_UPDATED" />
	<ps:hidden name="updatesImages" id="updatesImages_${_pageRef}"></ps:hidden>
	<ps:hidden id="webAppsOnly_${_pageRef}" name="webAppsOnlyRep" value="1"></ps:hidden>
</ps:form>

