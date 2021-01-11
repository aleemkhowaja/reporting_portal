var isMoz = !window.attachEvent;

var events = new EventsFunctions();
function EventsFunctions()
{
  this.addEvent = function(obj, event, fnct)
  {
    if(isMoz)
     obj.addEventListener(event, fnct, true);
    else
  	 obj.attachEvent("on" + event, fnct);
  }
  this.removeEvent = function(obj, event, fnct)
  {
  	if(isMoz)
  		try{obj.removeEventListener(event, fnct, true);} catch(e){}
  	else
  		obj.detachEvent("on" + event, fnct);
  }
  this.cancelEvent = function(event)
  {
    if(!event) event = window.event; 
    if(event) 
    {
      event.returnValue = false;
      if(event.preventDefault) event.preventDefault();
      return false;
    }
    return true;
  }
  this.cancelBubble = function(event)
  {
    var elm = (event.target? event.target : event.srcElement);
    elm.ownerDocument.cancelBubble = true;
    setTimeout(function(){elm.ownerDocument.cancelBubble = false;}, 100);
    event.cancelBubble = true;
  }
}

function editorHasChanged(){

	var editor = $("#editorDiv").html();
	var changed= false;

	if(editor != null){
		if ( CKEDITOR.instances.editor1.checkDirty() ) {
			changed = true;
		}
	}
	return changed;
}

function reportHasChanged(flag)
{		
    $.data(document.getElementById($("#mainForm_"+_pageRef).attr("id")),"changeTrack",flag); 
}

function preventSpecialCharacters(elem_id)
{
	  $("#"+elem_id).bind('keypress', function (event) {
	    var regex = new RegExp("^[\\%\\!\\@\\#\\$\\^\\&\\*\\(\\)\\{\\}\\<\\>\\=\\/\\'\\[\\]\\~\\+\\`\"]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if(event.which==8)// for delete
	    {
	    	return true;
	    }
	    $("#"+elem_id).bind("cut copy paste",function(e) {
	    
	          e.preventDefault();
	      });
	    if (regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});	
}

function onChangeComparisonRep(paramName,idSched,pageref)
{
	var myObject = $("#dynParamFrmId" +idSched+ pageref).serializeForm();
	var url=jQuery.contextPath+"/path/repCommon/reportAction_checkComparison.action?updates="+paramName;
	if (idSched != "") {
		url+="&fromMenu=N";
	}
	$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: myObject,
			success: function(param)
			{
				if(param["updates"]!=paramName)
				{
					_showErrorMsg(param["updates"],error_msg_title,500,200);
					$("#"+param["update"]).val("");
				}
			}
		});	
}

function onChangeShowHideRep(paramName,idSched,pageref)
{
	var myObject = $("#dynParamFrmId" +idSched+ pageref).serializeForm();
	var fromSched="";
	var url=jQuery.contextPath+"/path/repCommon/reportAction_checkShowHideExpr.action?updates="+paramName;
	if (idSched != "") 
	{
		fromSched="&fromMenu=N";
		url+=fromSched;
	}
	$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: myObject,
			success: function(param)
			{
				callDependency(	"fcrId_"+pageref+":glstmpltCO.FCRStr",
				jQuery.contextPath+'/path/repCommon/reportAction_adjustVisibility.action?_pageRef='+_pageRef+fromSched+
				"&var_menuId="+$("#hiddenMenuId_"+pageref).val(),
				"",
				"",
				"");
			}
		});
}

function onChangeRefreshRep(paramName,idSched,pageref)
{
	var myObject = $("#dynParamFrmId" +idSched+ pageref).serializeForm();
	var fromSched="";
	var url=jQuery.contextPath+"/path/repCommon/reportAction_fillNewArgsValuesInSession.action?updates="+paramName+"&conId="+$("#connection_id_"+pageref).val();
	if (idSched != "") 
	{
		fromSched="&fromMenu=N";
		url+=fromSched;
	}
	$.ajax({
			url: url,
			type:"post",
			dataType:"json",
			data: myObject,
			success: function(param)
			{
				callDependency(	"fcrId_"+pageref+":glstmpltCO.FCRStr",
				jQuery.contextPath+'/path/repCommon/reportAction_refreshArgumentsValues.action?_pageRef='+_pageRef+fromSched+
				"&var_menuId="+$("#hiddenMenuId_"+pageref).val(),
				"",
				"",
				"");
			}
		});
}


/*this method will be called on change of the language combo
to update the valule of the arguments of type translated session*/
function updateTransSessionArgsRep(size, langComboId, elts) 
{
	for ( var i = 1; i <= size; i++)
	{
		var elt = elts['elt' + i];
		var sessInputId = elt["id"];
		var transAr = elt["transAr"];
		var transEn = elt["transEn"];
		var lang = $("#" + langComboId).val();
		if (lang == "AR") 
		{
			$("#" + sessInputId).val(transAr);
		} 
		else
		{
			$("#" + sessInputId).val(transEn);
		}
	}
}

/*This method will take the value in the language combo and add it to the 
inputs of type Report Language*/
function updateRepLangParamsRep(pageref,idsList,namesList,idSched)
{
	var reportLang = $("#lang_id_"+pageref).val()
	var repLangArray = new Array();
	repLangArray =idsList.split(",");
	var argNamesArray = new Array();
	argNamesArray =namesList.split(",");
	for(i=0;i<repLangArray.length;i++)
	{ 
		$("#"+repLangArray[i]).val(reportLang);
		onChangeRefreshRep(argNamesArray[i],idSched,pageref)
	}
}