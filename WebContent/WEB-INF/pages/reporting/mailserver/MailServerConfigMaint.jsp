<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<script type="text/javascript">
$(document).ready(function() {
	rep_mailserver_readyFunc();
});
</script>

<ps:form id="mailServerForm_${_pageRef}" useHiddenProps="true"
	validate="true">
	<ps:hidden id="DATE_UPDATED_${_pageRef}"
		name="mailServerCO.mailServerVO.DATE_UPDATED" />
	<ps:hidden id="MAIL_SERVER_CODE_${_pageRef}"
		name="mailServerCO.mailServerVO.MAIL_SERVER_CODE" />
	<ps:hidden id="oldMsPass_${_pageRef}"
		name="mailServerCO.SERVER_OLD_PASS" />
	<table CELLPADDING="0" CELLSPACING="5">
		<tr>
			<td align="center" nowrap="nowrap">
				<TABLE CELLPADDING="0" CELLSPACING="8">
					<TR>
						<TD>
							<ps:label key="ms.host" for="msHost_${_pageRef}" />
						</TD>
						<TD>
							<ps:textfield id="msHost_${_pageRef}"
								name="mailServerCO.mailServerVO.HOST" tabindex="1"
								maxlength="50" required="true" />
						</TD>
						<td colspan="4"></td>
					</TR>

					<TR>
						<TD>
							<ps:label key="ms.port" for="msPort_${_pageRef}" />
						</TD>
						<TD>
							<ps:textfield id="msPort_${_pageRef}"
								name="mailServerCO.mailServerVO.PORT" tabindex="2" maxlength="5"
								required="true" mode="number" />
						</TD>
						<td colspan="4"></td>
					</TR>

					<TR>
						<TD>
							<ps:label key="ms.userName" for="msUserName_${_pageRef}" />
						</TD>
						<TD>
							<ps:textfield id="msUserName_${_pageRef}"
								name="mailServerCO.mailServerVO.SERVER_USER_NAME" tabindex="3"
								maxlength="100" required="true" />
						</TD>
						<td colspan="4"></td>
					</TR>
					<TR>
						<TD>
							<ps:label key="ms.password" for="msPass_${_pageRef}" />
						</TD>
						<TD>
							<ps:password id="msPass_${_pageRef}"
								name="mailServerCO.mailServerVO.SERVER_PASS" mode="character"
								tabindex="4" maxlength="100" showPassword="true" required="true" onchange="rep_mailserver_onChangePassword();"/>
						</TD>
						<td colspan="4"></td>
					</TR>

					<TR>
						<TD>
							<ps:label id="msConfPassLabel_${_pageRef}"  key="ms.confPassowrd" for="msConfPass_${_pageRef}" />
						</TD>
						<TD>
							<ps:password id="msConfPass_${_pageRef}"
								name="mailServerCO.SERVER_CONF_PASS" mode="character"
								tabindex="5" maxlength="100" showPassword="true" required="true" />
						</TD>
						<td colspan="4"></td>
					</TR>

					<TR>
						<TD>
							<ps:label key="ms.from" for="msMailFrom_${_pageRef}" />
						</TD>
						<TD>
							<ps:textfield id="msMailFrom_${_pageRef}"
								name="mailServerCO.mailServerVO.MAIL_FROM" tabindex="6"
								maxlength="100" required="true" onblur="checkMailSyntax()" />
						</TD>
						<td>
							<ps:label key="ms.sslEnabled" for="msSslEnabled_${_pageRef}"/>
						</td>
						<td>
							<ps:checkbox id="msSslEnabled_${_pageRef}" name="mailServerCO.mailServerVO.SSL_ENABLE_YN" 
							parameter="updates:msSslEnabled_${_pageRef}" valOpt="1:0" 
							dependency="msSslEnabled_${_pageRef}:updates"
							dependencySrc="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_showHideSslPortDependency">
							</ps:checkbox>
						</td>
						<td>
							<ps:label key="ms.sslPort" for="msSslPort_${_pageRef}"/>
						</td>
						<td>
							<ps:textfield id="msSslPort_${_pageRef}" mode="number"
								name="mailServerCO.mailServerVO.SSL_PORT_NBR" size="7" tabindex="7"
								maxlength="5" required="true"/>
						</td>
					</TR>




				</TABLE>
			</td>
		</tr>
	</table>


	<table class="headerPortionContent ui-widget-content" CELLPADDING="0"
		CELLSPACING="5">
		<tr>
			<td>
				<TABLE CELLPADDING="0" CELLSPACING="8">
					<TR>
						<TD>
							<ps:label key="ms.from" />
						</TD>
						<TD>
							<ps:textfield id="msFrom_${_pageRef}" maxlength="100" size="50"
								name="mailServerCO.FROM" tabindex="7" />
						</TD>
					</TR>

					<TR>
						<TD>
							<ps:label key="ms.to" />
						</TD>
						<TD>
							<ps:textfield id="msTo_${_pageRef}" maxlength="100" size="50"
								name="mailServerCO.TO" tabindex="8" />
						</TD>
					</TR>
					<TR>
						<TD>
							<ps:label key="ms.subject" />
						</TD>
						<TD>
							<ps:textfield id="msSubject_${_pageRef}" maxlength="100"
								size="50" name="mailServerCO.SUBJECT" tabindex="9" />
						</TD>
					</TR>
					<TR>
						<TD>
							<ps:label key="ms.body" />
						</TD>
						<TD>
							<ps:textfield id="msBody_${_pageRef}" maxlength="100" size="50"
								name="mailServerCO.BODY" tabindex="10" />
						</TD>
					</TR>
					<TR align="right">
						<td>
							<psj:a button="true" href="#" onclick="testSendMail()"
								id="testSendMail_${_pageRef}">
								<ps:text name="%{getText('ms.testSendMail')}"></ps:text>
							</psj:a>
						</td>
					</TR>

				</TABLE>
			</td>
		</tr>
	</table>
	<ptt:toolBar id="smTlb_${_pageRef}">
		<psj:submit button="true" buttonIcon="ui-icon-disk">
			<ps:label key="reporting.save"></ps:label>
		</psj:submit>
	</ptt:toolBar>


</ps:form>