<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<ps:set name="Missing_file_name" 	value="%{getEscText('fileexpimp.missingfilename')}"/>
<ps:set name="Not_allowed" 	value="%{getEscText('fileexpimp.notallowed')}"/>
<ps:set name="Save_success" value="%{getEscText('fileexpimp.saveinfomsg')}"/>
<ps:set name="File_exists" value="%{getEscText('fileexpimp.fileexists')}"/>
<ps:set name="File_not_selected" 	value="%{getEscText('fileexpimp.filenotselected')}"/>
<ps:set name="Save_error" 	value="%{getEscText('saveReport.exceptionMsg._key')}"/>
<ps:set name="Retrieve_success" value="%{getEscText('fileexpimp.retrievesuccessmsg')}"/> 
<ps:set name="Retrieve_failed" value="%{getEscText('fileexpimp.retrievefailedmsg')}"/>
 
<% String pgRef=(request.getParameter("_pageRef"));
	String showSaveButton = "";
	String showExportButton = "";
	String showHiddenForm="";
	String showViewFile = "false";
	String showGridEditButtons ="true";
 if(pgRef.equals("FEI00MT"))
 {    
  	showHiddenForm="";
  	showExportButton = "display: none";
  	showSaveButton = "";
  	showViewFile = "false";
 	showGridEditButtons ="true";
 }
 else if(pgRef.equals("FEI00EF")){
   	showHiddenForm= "display: none";
  	showExportButton = "";
  	showSaveButton ="display: none";
  	showViewFile = "true";
  	showGridEditButtons ="false";
 }
 %>
<html>
<head>

<title><ps:text name="reporting.fileexportimport"/></title>

	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
	
<style>
.ui-jqgrid tr.jqgrow td {white-space: normal}
</style>
<script type="text/javascript">
var missingFileName 	= '<ps:property value="Missing_file_name"  escapeHtml="false" escapeJavaScript="true"/>'; 
var notAllowed 			= '<ps:property value="Not_allowed"  escapeHtml="false" escapeJavaScript="true"/>'; 
var saveSuccess 		= '<ps:property value="Save_success"  escapeHtml="false" escapeJavaScript="true"/>'; 
var fileExists 			= '<ps:property value="File_exists"  escapeHtml="false" escapeJavaScript="true"/>'; 
var fileNotSelected		= '<ps:property value="File_not_selected"  escapeHtml="false" escapeJavaScript="true"/>'; 
var saveError			= '<ps:property value="Save_error"  escapeHtml="false" escapeJavaScript="true"/>'; 
var retrieveSuccess		= '<ps:property value="Retrieve_success"  escapeHtml="false" escapeJavaScript="true"/>'; 
var retrieveFailed		= '<ps:property value="Retrieve_failed"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function() 
{	$.struts2_jquery.require("FileExportImportList.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/fileexportimport/");
	$("#fileMainGrid_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	{
			 $("#fileMainForm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("")
	}); 
});

</script>
</head>
<body>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />



	<div>
		<table width="100%" cellpadding="0">
			<tr>
				<td  width="100%" >			

<ps:url id="urlMainGrid" action="fileExportImportListAction_loadMainGrid"
	namespace="/path/fileExportImport">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
	<jsp:include page="FileExportImportMainGrid.jsp"></jsp:include>
	</td>
			</tr>
		</table>
	</div>

<br>
<div id="tbDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="headerPortion"  style="<%=showHiddenForm%>">
<jsp:include page="FileExportImportMaint.jsp"></jsp:include>
</div>
<br>
<div>
		<table width="100%" cellpadding="0">
			<tr>
				<td  width="100%" >			

<ps:url id="urlDetGrid" action="fileExportImportListAction_retrieveDetailFileList"
	namespace="/path/fileExportImport">
	
<ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
	<jsp:include page="FileExportImportDetGrid.jsp"></jsp:include>
</td>
			</tr>
		</table>
	</div>
	<div>
	<table border="0" width="100%" style="<%=showSaveButton%>">
		<tr>
			<td>
				<ptt:toolBar  id="fileExportImportFooter" > 
					<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="saveFiles()"  >
						<ps:label key="reporting.save"></ps:label>
					</psj:submit>
				</ptt:toolBar>	
			</td>			
		</tr>
	</table>
		<table border="0" width="100%" style="<%=showExportButton%>">
		<tr>
			<td>&nbsp;
				<ptt:toolBar  id="fileExportImportFooter"   > 
					<psj:submit button="true" buttonIcon="ui-icon-gear" onclick="rep_fileExpImp_RetrieveReports()"  >
						<ps:label key="retrieve_key"></ps:label>
					</psj:submit>
					
					<psj:submit button="true" buttonIcon="ui-icon-extlink" onclick="exportFile()"  >
						<ps:label key="export_key"></ps:label>
					</psj:submit>
				</ptt:toolBar>	
			</td>
		</tr>
	</table>
</div>
<ps:form id="viewFileFormId_${_pageRef}" namespace="/path/fileExportImport"
		action="fileExportImportListAction_viewFile.action" useHiddenProps="true">
		
		</ps:form>
		<ps:form id="exportFileFormId_${_pageRef}" namespace="/path/fileExportImport"
		action="fileExportImportListAction_exportFile.action" useHiddenProps="true">
		</ps:form>
</body> 

<script type="text/javascript">
$("#fileMainGrid_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>

</html>