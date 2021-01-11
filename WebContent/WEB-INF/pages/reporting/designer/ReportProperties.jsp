<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="empRepClt_var" 				value="%{getEscText('upDown.empRepClt')}"/>
<ps:set name="cltRepeated_var" 				value="%{getEscText('upDown.cltRepeated')}"/>

<html>
<head>
	
<script type="text/javascript">
$(document).ready(function() 
	{
	    $.struts2_jquery.require("ReportProperties.js", null,
					jQuery.contextPath + "/path/js/reporting/designer/");
		var cltRepFlag = $("#cltRepFlag_" + _pageRef)
		
			if (cltRepFlag.val() == 1) {
				document.getElementById("repClientDiv_"+_pageRef).innerHTML = "";
			}
		
		checkPropIfCSV($('#format').val());
	});


var empRepClt 				= '<ps:property value="empRepClt_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var cltRepeated 			= '<ps:property value="cltRepeated_var"  escapeHtml="false" escapeJavaScript="true"/>'; 



</script>
</head>
<body>
<ps:form id="propertiesFrm_${_pageRef}" namespace="/path/designer" method="POST" enctype="multipart/form-data" useHiddenProps="true">
			<div>
				<h1 class="headerPortionContent ui-widget ui-state-default">
					<ps:label value="%{getText('designer.properties')}" />
				</h1>
			</div>
			<div>
				<table width="100%" cellspacing="10">
					<ps:hidden id="cltRepFlag_${_pageRef}" name="cltRepFlag"></ps:hidden>
					<tr>
						<td>
							<ps:text name="designer.orientation"></ps:text>
						</td>
						<td>
							<ps:select id="orientation" name="repCO.orientation"
								list="orientationsList" listKey="VALUE_CODE"
								listValue="VALUE_DESC" tabindex="1"></ps:select>
						</td>
					</tr>
					<tr>
						<td>
							<ps:text name="designer.titleRepeat"></ps:text>
						</td>
						<td>
							<ps:checkbox id="titleRepeated" name="repCO.titleRepeated"
								tabindex="2"></ps:checkbox>
						</td>
					</tr>
					<tr>
						<td>
							<ps:text name="designer.defaultFormat"></ps:text>
						</td>
						<td>
							<ps:select id="format" name="repCO.DEFAULT_FORMAT" tabindex="3"
								list="reportFormats" listKey="VALUE_CODE" listValue="VALUE_DESC"
								onchange="checkPropIfCSV(this.value)"></ps:select>
						</td>
					</tr>
					<tr>
						<td>
							<span id="sepLblTd_${_pageRef}"><ps:label
									value="%{getText('reporting.csvSeparator')}" />
							</span>
						</td>
						<td>
							<span id="sepValTd_${_pageRef}"><ps:select tabindex="4"
									list="csvSeparators" listKey="code" listValue="description"
									name="repCO.CSV_SEPARATOR" id="csvSeparatorId_${_pageRef}" />
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<ps:label value="%{getText('reporting.langIndep')}"
								id="langIndepLbl_${_pageRef}"></ps:label>
						</td>

						<td>
							<table>
								<tr>
									<td>
										<ps:checkbox tabindex="5" name="repCO.LANG_INDEPENDENT_YN"
											valOpt="1:0" id="langIndep_${_pageRef}"></ps:checkbox>
									</td>


									<td width="5%"></td>
									<td>
										<ps:label value="%{getText('reporting.noHeadAndFoot')}"></ps:label>
									</td>
									<td>
										<ps:checkbox tabindex="6" name="repCO.SHOW_HEAD_FOOT"
											id="noHeadAndFoot_${_pageRef}"></ps:checkbox>
									</td>

								</tr>
							</table>
						</td>
					</tr>
					<tr>

					</tr>

					<tr>
						<td>
							<ps:text name="designer.defaultConnection"></ps:text>
						</td>
						<td>
							<ps:select id="connection" tabindex="7" name="repCO.CONN_ID"
								list="connectionsList" listKey="CONN_ID" listValue="CONN_DESC"
								emptyOption="true"></ps:select>
						</td>
					</tr>

					<tr>
						<td>
							<ps:text name="reporting.whenNoData"></ps:text>
						</td>
						<td>
							<ps:select id="whenNoData_${_pageRef}" name="repCO.WHEN_NO_DATA"
								list="whenNoDataList" listKey="VALUE_CODE"
								listValue="VALUE_DESC" tabindex="8"></ps:select>
						</td>
					</tr>
					<tr style="display: none;">
						<td width="10%">
							<ps:label value="%{getText('sch.ms')}" />
						</td>
						<td colspan="2" width="100%">
							<table>
								<tr>
									<td width="25%">
										<psj:livesearch id="msCode_${_pageRef}"
											name="repCO.MAIL_SERVER_CODE" mode="number"
											readOnlyMode="false"
											actionName="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_msConfigLookup.action"
											searchElement=""
											resultList="mailServerVO.HOST:msHost_${_pageRef},mailServerVO.MAIL_SERVER_CODE:lookuptxt_msCode_${_pageRef}"
											parameter="code:lookuptxt_msCode_${_pageRef}"
											dependency="lookuptxt_msCode_${_pageRef}:mailServerCO.mailServerVO.MAIL_SERVER_CODE,msHost_${_pageRef}:mailServerCO.mailServerVO.HOST"
											dependencySrc="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_retMailServerDependency.action">
										</psj:livesearch>
									</td>
									<td width="75%">
										<ps:textfield name="repCO.HOST" id="msHost_${_pageRef}"
											readonly="true">
										</ps:textfield>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							<ps:collapsgroup id="repClientDiv_${_pageRef}">
								<ps:collapspanel id="repClientCollDiv_${_pageRef}"
									key="upDown.cltName">
									<%@include file="ReportClientList.jsp"%>
								</ps:collapspanel>
							</ps:collapsgroup>
						</td>
					</tr>


				</table>




				<table align="right" cellspacing="10">
					<tr>
						<td>
							<psj:submit button="true" onclick="return submitProperties();"
								type="button">
								<ps:text name="reporting.ok" />
							</psj:submit>
						</td>
						<td>
							<psj:submit button="true" onclick="return closePropDlg();"
								type="button">
								<ps:text name="reporting.cancel" />
							</psj:submit>
						</td>
					</tr>
				</table>
			</div>


		</ps:form>



</body>
</html>