<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:form id="importForm_${_pageRef}" action="upDownReport_startImportProcess.action"  namespace="/path/designer" method="POST" enctype="multipart/form-data" useHiddenProps="true">
	<div id='passErrorDiv'>
	</div>
	<table width="100%" class="headerPortionContent ui-widget-content" cellspacing="10">
	    <tr>
			 <td width="10%" >
				<ps:text name="upDown.Upload" />
			</td>
			<td nowrap="nowrap" >
		   		<ps:file name="upload"  id="uploadImport_${_pageRef}"  size="17"/>
			</td>
			<td colspan="2" width="40%">
				<table id="overwriteTrans_${_pageRef}">
					<tr>
						<td>
							<ps:text   name="reporting.overwriteTrans" />
						</td>
						<td>
							<ps:checkbox  name="overwriteTrans" ></ps:checkbox>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="10%">
				<ps:text name="reporting.action" />
			</td>
			<td>
				<ps:select list="importOptionsList" cssStyle="width:195" listKey="VALUE_CODE" listValue="VALUE_DESC"
				   name="importOption" id="importOptionsListId_${_pageRef}"
				   parameter="importOption:importOptionsListId_${_pageRef}"
				   dependency ="importOptionsListId_${_pageRef}:importOption"
	               dependencySrc ="${pageContext.request.contextPath}/path/designer/upDownReport_applyImportVisibility.action"/>
			</td>
			<td colspan="2" width="40%">
				<table id="useExistingOptAccess_<ps:property value="_pageRef" escapeHtml="true"/>">
					<tr>
						<td>
							<ps:text   name="reporting.skipOptAxs" />
						</td>
						<td>
							<ps:checkbox  name="useExistingOptAccess" ></ps:checkbox>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>	
			<td>
				<ps:text name="ms.password" />
			</td>
		
			<td nowrap="nowrap">
				<ps:password id="zipPassword_${_pageRef}" name="zipPassword" mode="character" tabindex="1" maxlength="30" showPassword="true"/>
			</td>
				<td colspan="2" width="40%">
				<table id="updateVersionIfEqual_${_pageRef}">
					<tr>
						<td>
							<ps:text   name="reporting.updateVersionIfEqual" />
						</td>
						<td>
							<ps:checkbox  name="updateVersionIfEqual" ></ps:checkbox>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		         
	</table>
	

	         
</ps:form>