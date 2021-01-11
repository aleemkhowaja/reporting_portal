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
	<ps:form id="organizeForm_${_pageRef}" action="reorganizeLines"
		namespace="/path/colTemplateMaintReport">
		<table>
			<tr>
				<td>
					<ps:label  key="template.startingValue" for="startingLineValue_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="startingLineValue" minValue="1" mode="number" maxlength="6" id="startingLineValue_${_pageRef}">
					</ps:textfield>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label  key="template.interval" for="interval_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="interval" minValue="1" mode="number" maxlength="4" id="interval_${_pageRef}"></ps:textfield>
				</td>
			</tr>
		</table>
	</ps:form>
</div>
