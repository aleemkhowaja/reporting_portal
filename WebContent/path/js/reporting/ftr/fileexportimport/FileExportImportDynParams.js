function rep_fileExpImp_saveDynParams(obj)
{
	var url = jQuery.contextPath+ "/path/fileExportImport/fileExportImportMaintAction_saveDynParamsVal.action?";
	var formParams = $("#dynParamFrmId"+htmlPageRef).serializeForm();
	var finalUrl=url+formParams+"&PROG_REF_D00="+PROG_REF_D00;
	jQuery(
			'<form id="submitFrmId_' + htmlPageRef + '" target="expImpRetIframe_'
					+ htmlPageRef + '"  action="' + returnEncryptedUrl(finalUrl)
					+ '" method="POST"></form>').appendTo("body").submit()
			.remove()
	
	window.setTimeout(function()
	{
		obj.dialog('close');
		obj.remove();
	}, 1000);	
}


function rep_fileExpImp_DynParamReady()
{
	var srcURL=jQuery.contextPath+ "/path/reportsRet/dynRepParamsAction_loadFileExpImpDynParam.action";
	var params={};
	params["_pageRef"]=PROG_REF_D00;
	params["menu"]=PROG_REF_D00;
	params["fromFileExpImp"]=1;
	$('#dynParamDiv_'+htmlPageRef).load(srcURL, params, function()
	{
	 });
}