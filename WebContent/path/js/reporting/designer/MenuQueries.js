var westDiv,eastDiv,westPinAlign,eastPinAlign;
var queryName;
if(RTL_DIRECTION == "ltr")
{
	westDiv = "west";
	eastDiv = "east";
	westPinAlign = "right";
	eastPinAlign = "left";
}
else
{
	westDiv = "east";
	eastDiv = "west";
	westPinAlign = "left";
	eastPinAlign = "right";
}

var innerLayoutSettings = 
{
	name: "innerLayout",
	north__paneSelector: ".inner-north",
	south__paneSelector: ".inner-south",	
	center__paneSelector: ".inner-center",
	contentSelector: ".ui-widget-content",
	south__maxSize:	21
};

innerLayoutSettings[westDiv+"__paneSelector"] = ".inner-"+westDiv;
innerLayoutSettings[eastDiv+"__paneSelector"] = ".inner-"+eastDiv;

innerLayoutSettings[westDiv+"__togglerContent_closed"] = westMenuText
innerLayoutSettings[eastDiv+"__togglerContent_closed"] = eastMenuText;


//called upon edit icon click
function editQuery(event) 
{ 
	var elm;
	if($.browser.msie && $.browser.version <= 8 )
	{
		event.returnValue = false;
    	event.cancelBubble = true;
    	elm = event.srcElement
	}
	else
	{
		event.stopPropagation();	
		elm = event.target;
	}

	queryName = elm.parentNode.childNodes[1].nodeValue;	
	//store the qryName in the server side as post request in order to cover the arabic issues
	var url = jQuery.contextPath+ "/path/designer/queryDesign_storeMenuQryName";
		var params={};
	    params['updates']=queryName;
		$.post(url, params , function( param )
	 	{
			  var dlg = $("#qryDialg").dialog({
				  width:"1100",
				  height:"500",
				  title: '<ps:text name="modify_key"/>'	 
			});

			dlg.load(jQuery.contextPath+"/path/designer/reportDesign_openQueryDesigner.action?queryName="+escape(queryName)+"&updates=true&_pageRef="+_pageRef)
			   .dialog('open'); 
	 	});	
	
      	
}