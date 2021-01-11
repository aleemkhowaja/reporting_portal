function reportViewer_viewContent(elementPath,elementName,elementType,countPath)
{
	if (elementType == "File" && elementName.toUpperCase().lastIndexOf(".HTML") == (elementName.length - 5))
	{	
		var loadSrc = jQuery.contextPath+ "/path/reportViewer/reportViewer_loadDiv.action";
		var params = {};
		params["reportViewerCO.elementPath"] = elementPath;
		params["reportViewerCO.elementName"] = elementName;
		params["reportViewerCO.elementType"] = elementType;
		$("#reportViewerLoadHtml_"+_pageRef).load(loadSrc, params, function(param) {
			$("#reportViewerLoadHtml_"+_pageRef).html(param);
			var urlReportViewer=jQuery.contextPath+ "/path/reportViewer/reportViewer_previewReportViewer.action?elementPath="+encodeURIComponent(elementPath)+"&elementName="+elementName+"&elementType="+elementType;
			document.getElementById("reportViewerFrm_"+ _pageRef).action=urlReportViewer;
			//document.getElementById("reportViewerFrm_"+ _pageRef).submit();
			submitEncryptedData("reportViewerFrm_"+ _pageRef);
		});
	}
	else if (elementType == "File" && !(elementName.toUpperCase().lastIndexOf(".HTML") == (elementName.length - 5)))
	{
		var urlReportViewer=jQuery.contextPath+ "/path/reportViewer/reportViewer_previewReportViewer.action?elementPath="+encodeURIComponent(elementPath)+"&elementName="+elementName+"&elementType="+elementType;
		document.getElementById("reportViewerFrm_"+ _pageRef).action=urlReportViewer;
		//document.getElementById("reportViewerFrm_"+ _pageRef).submit();
		submitEncryptedData("reportViewerFrm_"+ _pageRef);
		$("#reportViewerLoadHtml_"+_pageRef).html("<br /><br /><br /><br /><br /><center>"+format_not_supported+"</center>");
	}
	else
	{	
	var url = jQuery.contextPath+ "/path/reportViewer/reportViewer_execute.action";
	var params = {};
	params["reportViewerCO.elementPath"] = elementPath;
	params["reportViewerCO.elementName"] = elementName;
	params["reportViewerCO.elementType"] = elementType;
	params["countPath"] = countPath;
	params["_pageRef"] = _pageRef;
	params["update"]="1";
		$.post(url, params, function(param) {
			$("#northDiv_" + _pageRef).html(param);
			$("#reportViewerLoadHtml_"+_pageRef).html("");

			params["update"] = "2";
			$.post(url, params, function(param) {
				$("#westDiv_" + _pageRef).html(param);
			});

		});
	}
}

