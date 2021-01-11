<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>


<ps:form id="exportForm_${_pageRef}" useHiddenProps="true"
	validate="false">

<table width="100%" class="headerPortionContent ui-widget-content">
	<tr>
		<td>
			<table width="100%" cellspacing="10">
				<tr>
					<td colspan="2">
		
		
						<ps:radio list="#{'1':'BasicExp_key','2':'FullExp_key'}"
								  id="basicFullExport_${_pageRef}" 
								  name="basicFullExport"
						    	  value="1" onchange="visibilitySkip(false)"/>
		
					</td>
					<td nowrap="nowrap"><ps:label value="%{getText('reporting.skipTrans')}" id="lblSkip_${_pageRef}"></ps:label></td>
					<td><ps:checkbox name="skipTrans" id="skipTrans_${_pageRef}"></ps:checkbox></td>
				</tr>
			</table>
			<table width="100%"  cellspacing="10">
				<tr>
					<td>
						<ps:text name="ms.password" />
					</td>
					<td nowrap="nowrap">
						<ps:password id="zipPassword_${_pageRef}" name="zipPassword" mode="character" tabindex="1" maxlength="30" showPassword="true"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</ps:form>
<script>
$(document).ready(function() {
visibilitySkip(true)
});
</script>				