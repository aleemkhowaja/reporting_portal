<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<ps:set name="reportingDelAll_var" 			value="%{getEscText('reporting.deleteAll')}"/>
<ps:set name="reportingDelAllLines_var" 	value="%{getEscText('reporting.deleteAllLines')}"/>
<ps:set name="errorDeclaredUser_var" 		value="%{getEscText('snapshots.errorDeclaredUser')}"/>
<ps:set name="errorUncheckDeclaredUser_var" value="%{getEscText('snapshots.errorUncheckDeclaredUser')}"/>
<ps:set name="errorAlreadyDeclared_var" 		value="%{getEscText('snapshots.errorAlreadyDeclared')}"/>
<script>
var reportingDelAllLines = '<ps:property value="reportingDelAllLines_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reportingDelAll 	 = '<ps:property value="reportingDelAll_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var errorDeclaredUser    = '<ps:property value="errorDeclaredUser_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var errorUncheckDeclaredUser ='<ps:property value="errorUncheckDeclaredUser_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var errorAlreadyDeclared = '<ps:property value="errorAlreadyDeclared_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

</script>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

<div id="innerLayoutSnp" style="overflow-x:hidden;overflow-y:auto" >
<ps:collapsgroup id='SnpInfoCollapsGrp_${_pageRef}'>
	<ps:collapspanel id='snapshotInformationListMaintDiv_id_${_pageRef}'  key="snapshots.Information">
	      <%@include file="SnapshotInformationMaint.jsp"%>
</ps:collapspanel>
</ps:collapsgroup>
<div id="listInf_<ps:property value="_pageRef" escapeHtml="true"/>">	
	<%@include file="SnapshotInformationListGrid.jsp"%>
</div>
<div>
	<ptt:toolBar  id="snapshotBar${_pageRef}"  > 
		<psj:submit type="button" button="true" buttonIcon="ui-icon-disk" onclick="submitFormSnInf()" >
			<ps:text name="reporting.save"></ps:text>
		</psj:submit>
	</ptt:toolBar>
</div>
</div>

<script  type="text/javascript">
    $.struts2_jquery.require("SnapshotInformationList.js,SnapshotInformationMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/snapshots/");
    $("#gview_snapshotInformationListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
</script>
