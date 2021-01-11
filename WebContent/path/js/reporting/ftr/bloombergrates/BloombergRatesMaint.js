
function uploadBlmbrgRates()
{
		var txtName = $("#upload_"+_pageRef).val();
		if(txtName=="" || (txtName!="" && (txtName.endsWith('.txt')==false)))
		{
			_showErrorMsg(missingExt);
			return;
		}
	 _showProgressBar(true);
		var options = {
			url : jQuery.contextPath
					+ "/path/bloombergRates/BloombergRatesListAction_uploadBloombergRates.action",
			type : 'post',
			success : function(param, status, xhr) {
			
			if (xhr.responseText.indexOf("errorMessage") != -1) {
				_showProgressBar(false);
					$("#actionErrorDiv").html(param)
				}
			else
				{
				_showProgressBar(false);
					$("#actionErrorDiv").html(param);
					var url = jQuery.contextPath+"/path/bloombergRates/BloombergRatesListAction_loadBloombergRatesGrid.action";
					$("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('setGridParam', { url: url}).trigger("reloadGrid");
					
				}

			}
		}

		$("#bloombergRatesMaintFormId_"+_pageRef).ajaxSubmit(options)

}