
function openImages(cellvalue, options, rowObject) {
	return '<a href="#" onclick="openImagesList(\'' + options.rowId + '\')">'
			+ linkImagesName + '</a>';
}

function openImagesList(_rowid) {

	dialogOptions = {
		autoOpen : false,
		modal : true,
		height : 530,
		width : 1000,
		close : function(ev, ui) {
			$(this).dialog("destroy");
			$('#imageDialog').html();
		},
		buttons : [ {
			text : paramsOk,
			click : saveChoosenImg
		}, {
			text : paramsCancel,
			click : function() {
				$(this).dialog("destroy");
				$(this).dialog("close");
				$('#imageDialog').html();
			}
		} ]
	}
	$('#imageDialog').dialog(dialogOptions)
	$('#imageDialog').dialog('open');
	dialogUrl = jQuery.contextPath
			+ "/path/designer/image_openImagesList.action?_pageRef=" + _pageRef;
	$("#imagesGridId_" + _pageRef).jqGrid("setSelection", _rowid, false);
	var params = {};
	$.post(dialogUrl, params, function(param) {
		$('#imageDialog').html(param);
		$('#imageDialog').dialog(dialogOptions)
		//$('#imageDialog').dialog('open');
	}, "html");
}

function saveChoosenImg() {
	var sel_row_index = $("#imagesGridId_" + _pageRef).jqGrid('getGridParam',
			'selrow');
	var rowid = $("#imageGrid").jqGrid('getGridParam', 'selrow');
	var imgObj = $("#imageGrid").jqGrid('getRowData', rowid);
	var fileName = imgObj["IMAGE_NAME"];
	$("#imagesGridId_" + _pageRef).jqGrid('setCellValue', sel_row_index,
			'mappedImgName', fileName, 'false');
	$('#imageDialog').dialog('close');

}

function rep_enableReportCifAudit()
{
	if($("#cifAuditYn_"+_pageRef+":checked").val())
	{
		$("#cifAuditFlag_"+_pageRef).attr("style","visibility: inline;")
		$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: inline;")
	}
	else
	{
		$("#cifAuditFlag_"+_pageRef).attr("style","visibility: hidden;")
		$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: hidden;")
	}
}