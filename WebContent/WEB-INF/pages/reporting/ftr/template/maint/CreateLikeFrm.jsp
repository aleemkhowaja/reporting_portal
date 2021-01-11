<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<script type="text/javascript">

$(document).ready(
		function() {

			$.struts2_jquery.require("TemplateMain.js", null,
					jQuery.contextPath + "/path/js/reporting/ftr/template/");
					
		});
</script>
<div>
	<ps:form id="createLikeForm_${_pageRef}" action="createLikeTemplate"
		namespace="/path/templateMaintReport">
		<table>
			<tr>
				<td>
					<ps:label key="reporting.newCode" for="newCode_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="newCode"
						id="newCode_${_pageRef}" minValue="0"
						 maxlength="4" mode="number" nbFormat="####" 
						 onchange="checkTemplCode(1,this)" >
					</ps:textfield>
				</td>
			</tr>
		</table>
	</ps:form>
</div>
