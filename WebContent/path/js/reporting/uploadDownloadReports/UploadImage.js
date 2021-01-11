function cancelUploadImg() {
	$('#uploadImageDialog').dialog('close');
}

function uploadImg() {
	var fileName = document.getElementById("upload").value;
	var ext = fileName.split(".")[1]
	if (typeof ext == 'undefined') {
		_showErrorMsg(notAllowed, error_msg_title, 300, 100);
	} else {
		ext = ext.toUpperCase();
		if (ext.toUpperCase() == "JPEG" || ext.toUpperCase() == "JPG"
				|| ext.toUpperCase() == "BMP" || ext.toUpperCase() == "GIF"
				|| ext.toUpperCase() == "PNG") {
			var options = {
				url : jQuery.contextPath
						+ "/path/designer/uploadImage_saveUploadedImage",
				type : 'post',
				success : function(response, status, xhr) {
					// annasuccar and nabihaojeil- 22/5/2014:
					// commented out the if condition below for IE compatibility
					// and it's not needed
					// if(xhr.responseXML.contentType == "text/html")
					// {
					var resultHtml = $(response).html();
					if (resultHtml != "" && resultHtml != null) {
						if (xhr.responseText.indexOf("Exists") > 0) {
							_showErrorMsg(existsError, error_msg_title, 300,
									100);
						} else if (xhr.responseText.indexOf("Warning") > 0) {
							_showConfirmMsg(
									sizeWarning,
									warning_msg_title,
									function(confirmcChoice, theArgs) {
										if (confirmcChoice) {
											var options1 = {
												url : jQuery.contextPath
														+ "/path/designer/uploadImage_confirmUploadedImage",
												type : 'post',
												success : function(response,
														status, xhr) {

													document
															.getElementById("prevDiv").style.display = "none";
													$("#imageGrid").trigger(
															"reloadGrid");
													$('#uploadImageDialog')
															.dialog('close');
												}
											}
											$("#uploadFrm")
													.ajaxSubmit(options1)
										}
									}, {}, yes_confirm, no_confirm, 350, 120);
						} else if (xhr.responseText.indexOf("Error") > 0) {
							_showErrorMsg(sizeError, error_msg_title, 300, 100);
						}
					} else {
						document.getElementById("prevDiv").style.display = "none";
						$("#imageGrid").trigger("reloadGrid");
						$('#uploadImageDialog').dialog('close');
					}
					// }
				}
			}
			$("#uploadFrm").ajaxSubmit(options)
		} else {
			_showErrorMsg(notAllowed, error_msg_title, 300, 100);
		}
	}
}