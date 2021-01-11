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
	<ps:form id="organizeForm_${_pageRef}" action="reorganizeLines"
		namespace="/path/templateMaintReport">
		<table>
			<tr>
				<td>
					<ps:label value="%{getText('template.startingValue')}" />
				</td>
				<td>
					<ps:textfield name="startingLineValue" minValue="1" mode="number" maxlength="6" 
						id="startingLineValue_${_pageRef}">
					</ps:textfield>
				</td>
			</tr>
			<tr>
				<td>
					<ps:label value="%{getText('template.interval')}"/>
				</td>
				<td>
					<ps:textfield name="interval" id="interval_${_pageRef}" mode="number" minValue="1" maxlength="4"></ps:textfield>
				</td>
			</tr>
		</table>
	</ps:form>
</div>
