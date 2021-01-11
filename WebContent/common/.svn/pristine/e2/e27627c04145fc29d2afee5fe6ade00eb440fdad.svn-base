function loadDocumentMngmntSys(pageRef) {

	var timInMill = (new Date()).getTime();
	var intDivId = "intg_div_" + timInMill;
	var intDiv = $("<div id='" + intDivId + "'/>");
	intDiv.css("padding", "0");
	$('body').append(intDiv);
	var srcURL = jQuery.contextPath
			+ "/path/integration/IntegrationAction_loadServiceDialog";

	_showProgressBar(true);
	$.ajax({
		url : jQuery.contextPath
		+ '/path/integration/IntegrationAction_returnDmsSearchIndexFields',
		type : "post",
		dataType:"json",
		data : {
			'_pageRef' : pageRef
		},
		success : function(response) {
		if (response["_error"] == undefined || response["_error"] == null)
		{
			//#514292 AIB - Docuware Integration
			//Use detailed JSON string instead of string arrays.
			var mainJson = JSON.parse(response["mainJsonString"]);
			var imalIndex = mainJson["IMAL_DMS_INDEX"];
			
				for (i = 0; i < imalIndex.length; i++)
				{
				    var dmsIndex = imalIndex[i].DMS_INDEX;
				    for(j = 0; j < dmsIndex.length; j++)
					{
				    	var indexDet = dmsIndex[j];
				    	if(indexDet.type == 1)
				    	{
				    		var indexVal = returnHtmlEltValue(indexDet.id + '_'
									+ pageRef);
							if (indexVal == null
									|| indexVal == indexDet.id + '_' + pageRef
									|| indexVal == undefined
									|| indexVal == '') {
								indexVal = returnHtmlEltValue(indexDet.id);
							}
							if(indexVal == indexDet.id)
								{
								indexVal = '';
								}
							indexDet.value = indexVal;
				    	}
					}
				}

			var fieldParams = {
				'_pageRef' : pageRef,
				'mainJsonString' : JSON.stringify(mainJson)
			};
			var closeBtnLbl = (typeof signature_close_btn === undefined )? "Close" :signature_close_btn;
			var buttons = {};
			buttons[closeBtnLbl] = function() {
				$(this).dialog("close");
			};
			$.ajax({
				url : jQuery.contextPath
						+ '/path/integration/IntegrationAction_returnServiceLink',
				type : "post",
				dataType:"json",
				data : fieldParams,
				success : function(data) {
					var curParams = {
						'theURL' : data["theURL"]
					};
					if (data["_error"] == undefined && data["_error"] == null && data["theURL"] != null 
							&& data["theURL"] != undefined && data["theURL"] != '') {
						//If protocol of the server is different than protocol
						//of the Docuware URL then open in new window and not in dialog.
						//Avoiding the browser restriction "Blocked(mixed-content)".
						//635576 FIBSI170005 - integration with e-file Archiving
						if ((location.protocol == 'https:' && data["theURL"].indexOf('https:') === -1)
								|| (location.protocol == 'http:' && data["theURL"].indexOf('http:') === -1)
								|| data["dmsType"] == "2")
						{
							_showProgressBar(false);
							window.open(data["theURL"]);
						}
						else
						{
						$("#" + intDivId).load(srcURL,curParams,function() {
							$("#" + intDivId)
									.dialog({
										modal : true,
										title : documents_name_key,
										autoOpen : false,
										show : 'slide',
										position : 'center',
										width : returnMaxWidth(630),
										height : returnMaxHeight(570),
										close : function() {
											var theDialog = $(this);
											theDialog.remove();
										},
										buttons : buttons
											});
							$("#" + intDivId).dialog("open");
						});
						_showProgressBar(true);
					}
					}else
					{
						_showProgressBar(false);
					}
				}
			});
		}else
		{
			_showProgressBar(false);
		}
		}
	});
}

function docuwareDialogDivLoaded(theUrl) {
	//The iFrame source can not be included directly in 
	//the iFrame tag since the resource files for Docuware are making
	//exceptions, probably the resource files are loading
	//before iFrame is completely loaded.
	var currURL = $("#DocuwareDialogIFrame").attr("src");
	if(currURL == null || currURL == undefined || currURL == '')
	{
		$("#DocuwareDialogIFrame").attr("src", theUrl);
	}
	else
	{
		_showProgressBar(false);
	}
}