<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:form useHiddenProps="true" id="automatedImportFormId_${_pageRef}" namespace="/path/automatedImport">
	<table class="headerPortionContent ui-widget-content" width="100%">
		<tr height="20px">
			<td colspan="7"></td>
		</tr>
		<tr>
			<td width="4%">
				<ps:label key="repoting.path" for="autoImpId_${_pageRef}"/>
			</td>
			<td width="15%" nowrap="nowrap" align="left">
				<ps:textfield name="update" size="100" id="autoImpId_${_pageRef}"></ps:textfield>
			</td>
			<td width="5%"></td>
			<td width="5%">
				<ps:label key="ms.password" />
			</td>
			<td width="15%">
				<ps:password id="passwordAutoImpRep_${_pageRef}" name="zipPassword"
					showPassword="false"></ps:password>
			</td>
			<td width="10%" nowrap="nowrap" align="right">
				<psj:submit cssStyle="width:60%" id="viewRepFiles_${_pageRef}" button="true">
					<ps:label key="snapshots.view"/>
				</psj:submit>
			</td>
			<td></td>
		</tr>
		<tr height="20px">
			<td colspan="7"></td>
		</tr>
	</table>
	<ps:hidden name="updates" id="autoImpUpdate_${_pageRef}" />
	<ps:hidden name="statusChk" id="statusCheckAllOpt_${_pageRef}" value="true"/>
</ps:form>