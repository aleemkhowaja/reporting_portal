<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@taglib uri="/path-struts-tags" prefix="ps"%>

<ps:set name="csm_main_title_var" 			value="%{getEscText('reporting_title_key')}"/>
<ps:set name="deleteTitle_var" 				value="%{getEscText('reporting.delete')}"/>
<ps:set name="deleteConfirm_var" 			value="%{getEscText('app.deleteConfirm')}"/>
<ps:set name="missingReqInputs_var" 		value="%{getEscText('reporting.missing')}"/>

<ps:set name="app_main_title"  value="%{getEscText('reporting_title_key')}"/>

<script type="text/javascript">
<%--Place all global script variables and its assigned trans messages --%>
var csm_main_title 			= "${csm_main_title_var}";
var deleteTitle 			= "${deleteTitle_var}";
var deleteConfirm 			= "${deleteConfirm_var}";

var app_main_title= "${app_main_title}";
var missingReqInputs 		= "${missingReqInputs_var}";
</script>	