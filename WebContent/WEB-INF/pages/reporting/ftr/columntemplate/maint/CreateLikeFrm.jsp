<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<script type="text/javascript">

$(document).ready(
		function() {

			$.struts2_jquery.require("ColumnTemplate.js", null,
					jQuery.contextPath + "/path/js/reporting/ftr/columntemplate/");
					
		});
</script>

<div>
	<ps:form id="createLikeForm_${_pageRef}" action="createLikeTemplate" namespace="/path/colTemplateMaintReport">
		<table>
			<tr>
				<td>
					<ps:label key="reporting.newCode" for="newCode_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="newCodeCol"
						id="newCode_${_pageRef}" minValue="0"
						mode="number" nbFormat="####" 
						maxlength="4" onchange="templateCodeChanged(1,this)">
					</ps:textfield>
				</td>
			</tr>
		</table>
	</ps:form>
</div>