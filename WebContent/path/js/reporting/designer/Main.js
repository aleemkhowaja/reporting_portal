function newReport()
{
	var ed_change= editorHasChanged();
	var frm_change = $("#mainForm_"+_pageRef).hasChanges();

  	if(ed_change ||frm_change )
 	{
   		 _showConfirmMsg(loose_data_key,warning_msg_title,function(confirmcChoice, theArgs) 
		 {
			if (confirmcChoice) 
			{
				    openNewRepDialog();       
                	
			}
		 }, {}, yes_confirm, no_confirm, 300, 100);
    } 
    else
    {
	   openNewRepDialog();
	}
}
	
function openReport()
{ 
	var ed_change= editorHasChanged();
	var frm_change = $("#mainForm_"+_pageRef).hasChanges();

  	if(ed_change ||frm_change )
 	{
	    _showConfirmMsg(loose_data_key,warning_msg_title,function(confirmcChoice, theArgs)
	    {
			if (confirmcChoice)
			{
				openRepDialog();
			}
		}, {}, yes_confirm, no_confirm, 300, 100);
	} 
	else 
	{  
	   openRepDialog();
	}   
}

function openRepDialog()
{
	var dlg = $("#zDialog").dialog({
					 width:"700",
					 height:"350",
					 title:  list_key
				});

	 dlg.load(jQuery.contextPath+"/path/designer/reportsList_openReportsList?_pageRef="+_pageRef).dialog('open');
}

function openNewRepDialog()
{
	$('#wizardDialog').load(jQuery.contextPath+"/path/designer/wizard.action?_pageRef="+_pageRef).dialog('open');
}

function closeDialog() 
{
	$('#zDialog').dialog('close');
	return false;
}

function closeRepDesigner()
{
	$.ajax({
			url: jQuery.contextPath+'/path/designer/reportDesign_closeRepDesigner.action',
			success: function(xml)
			{
				document.location.href = "";
    		}
	});	
}