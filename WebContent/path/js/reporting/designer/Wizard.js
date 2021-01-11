function checkEditorLoadingBfrSubmit()
{
	if($("#editorDiv").html() == null)
	{
		$("#editorLoadedFrom_"+_pageRef).val("0");
		$("#imgDiv").load(jQuery.contextPath+'/path/designer/reportDesign_loadEditor.action?_pageRef='+_pageRef)
	}
	else
	{
		submitWizard();
	}
}

var wizardSubmit = false;
function submitWizard()
{
	$('#queriesDiv').load(jQuery.contextPath+"/loadQueriesMenu.action");
		
	//load the html inside the editor
	var zSrc=jQuery.contextPath+"/path/designer/wizard_generateHtml";
	params ={};
	$.getJSON(zSrc, params, function( data2, status, xhr ) 
	{
		 var html=data2['tableHTML'];
	   //clear the editor
	   var initHtml = "<table id=\"root\" cellpadding=\"0\" cellspacing=\"0\" width=\"595\" align=\"center\" style=\"border: 1px solid #e5e5e5;\">"+
					  "<tbody id=\"tbody\" style=\"font-size: 9px;\">"+
					  "<tr id=\"root_title\" height=\"75\">"+
					  "<td style=\"BACKGROUND: url("+jQuery.contextPath+"/ckeditor/images/title.JPG) no-repeat center; border: 1px solid #e5e5e5;\" valign=\"top\">"+
					  "<p>&nbsp;</p>"+
					  "</td>"+
					  "</tr>"+
					  "<tr id=\"root_detail\" height=\"165\">"+
					  "<td style=\"BACKGROUND: url("+jQuery.contextPath+"/ckeditor/images/detail.JPG) no-repeat center; border: 1px solid #e5e5e5;\" valign=\"top\">"+
					  "<p>&nbsp;</p>";
				
		if(html)		
	    	{
	    	   initHtml+= html;
	    	}  
		initHtml+=  "</td>"+
					  "</tr>"+
					  "<tr id=\"root_pageFooter\" height=\"60\">"+
					  "<td style=\"BACKGROUND: url("+jQuery.contextPath+"/ckeditor/images/footer.JPG) no-repeat center; border: 1px solid #e5e5e5;\" valign=\"top\">"+
					  "<p>&nbsp;</p>"+
					  "</td>"+
					  "</tr>"+
					  "</tbody>"+
					  "</table>";
	       CKEDITOR.instances.editor1.document.$.body.innerHTML = initHtml;
	       wizardSubmit = true;
			$('#wizardDialog').dialog('close');
	});
	cancelEvents();
	configCommands();
}


function showQueryDesigner() {
	/*check if sqb exist than reset it(editor+session) before loading designer*/
	var url = jQuery.contextPath+ "/path/designer/queryDesign_retQueryType";
	var params={};
    $.getJSON(url, params, function( data2, status, xhr )
 	{
    	var qryType=data2['updates'];
    	if(qryType=="sqb")
    	{
    		 _showConfirmMsg(resetStatQry, resetQryTitle, function(confirmcChoice, theArgs){
  	           if(confirmcChoice)
  	           {
  	        	   resetStaticQry()
  	           }
  		      }, {});  		
    	}
    	else
    	{
    	 	var dlg = $("#qryDialg").dialog({
				  width:"1100",
				  height:"500",
				  title: newQuery	 
			});
		
 			dlg.load(jQuery.contextPath+"/path/designer/wizard_showQueryDesigner.action?_pageRef="+_pageRef).dialog('open');     
			return false;
    	}
    	

    	
   
 	});
}

function openQueriesList() {
	var dlg = $("#zDialog").dialog({
				  width:"700",
				  height:"350",
				  title: list_key
			  });

	dlg.load(jQuery.contextPath+"/path/designer/wizard_showQueriesList.action")
	   .dialog('open');

	return false;
}

function resetQry() {
	$.ajax({
		url: jQuery.contextPath+'/path/designer/wizard_resetQry.action',
	    success: function(xml){				
			$("#syntax_"+_pageRef).attr("value","");
			//reload the grids of chooseFields section
	 		$("#allFieldsGrid").trigger("reloadGrid");
	 		$("#displFieldsGrid").trigger("reloadGrid"); 
		}
	});	
	return false;
}

function convertToUpperCase()
{
  var refStr=$("#refId").val();
  var re = new RegExp("[\u0600-\u06ff\ufb50-\ufdff\ufe70-\ufeff]" , "g");
  refStr=refStr.replace(re,"")
  refStr=refStr.replace(" ","");
  $("#refId").val(refStr.toUpperCase());
}

function staticQry()
{
	var url = jQuery.contextPath+ "/path/designer/queryDesign_retQueryType";
	var params={};
    $.getJSON(url, params, function( data2, status, xhr )
 	{
    	var qryType=data2['updates'];
    	if(qryType=="qbe")
    	{
        	 _showConfirmMsg(resetQryDes, resetQryTitle, function(confirmcChoice, theArgs){
	           if(confirmcChoice)
	           {
	        	   resetQD()
	           }
		      }, {});	
			   	  
    	}
    	else
    	{
    		var dlg = $("#qryDialg").dialog({
				  width:"1000",
				  height:"500",
				  title: syntax_key 
			});

			dlg.load(jQuery.contextPath+"/path/designer/wizard_showQueryDesigner.action?openSqb=true&_pageRef="+_pageRef).dialog('open');     
			return false;
    	}
 	});

}

function resetQD()
{
	resetQry();
	var dlg = $("#qryDialg").dialog({
		  width:"1000",
		  height:"500",
		  title: static_key
	});

	dlg.load(jQuery.contextPath+"/path/designer/wizard_showQueryDesigner.action?openSqb=true&_pageRef="+_pageRef).dialog('open');     
	return false;
}

function resetStaticQry()
{
	resetQry()
	//open dialog
	var dlg = $("#qryDialg").dialog({
		  width:"1100",
		  height:"500",
		  title: newQuery
	});

		dlg.load(jQuery.contextPath+"/path/designer/wizard_showQueryDesigner.action?_pageRef="+_pageRef).dialog('open');     
	return false;
}

function emptyPRefLkp()
{
	$("#pRefStr_"+_pageRef).val("");
	$("#lookuptxt_fcrPRef_"+_pageRef).val("");
}