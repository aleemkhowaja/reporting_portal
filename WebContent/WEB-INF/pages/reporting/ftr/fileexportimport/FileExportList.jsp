<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

<html>
<head>

<title><ps:text name="reporting.fileexport"/></title>

	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
	
<style>
.ui-jqgrid tr.jqgrow td {white-space: normal}
</style>
<script type="text/javascript">
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
	<table border="0" width="100%">
		<tr>
			<td>
				<ptt:toolBar  id="fileExportImportFooter"> 
					<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="exportFiles()" >
						<ps:label key="reporting.export"></ps:label>
					</psj:submit>
				</ptt:toolBar>	
			</td>
		</tr>
	</table>
</div>
</body> 

<script type="text/javascript">
$("#fileMainGrid_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>

</html>