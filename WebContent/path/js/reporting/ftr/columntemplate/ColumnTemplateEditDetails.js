function openColT()
{
	var url = jQuery.contextPath +'/path/colTemplateMaintReport/editColumnTemplateDetail.action';
	$.post(url, $("#mainForm").serialize() , function( param ){});
	$("#templColGrid").jqGrid('setGridParam',{url:"${pageContext.request.contextPath}/path/colTemplateMaintReport/openColumnDet.action?clear=0&newCode="+$("#TEMPLATE_CODE").val(),page:1}).trigger("reloadGrid");
	
	$('#colDialog').dialog('close');
	return false;
}