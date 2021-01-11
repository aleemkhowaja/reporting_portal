var innerLayoutSettings = {
		name:						  "innerLayoutImg"	,	
        center__paneSelector:         ".inner-centerImg",
        south__paneSelector:          ".inner-southImg",
        west__paneSelector:          ".inner-westImg",
        east__paneSelector:          ".inner-eastImg",
        east__size:                        480          ,
        west__size:                        480          ,
        south__size:						    50,
        south__maxSize:						0,
        west__closable:					false,
        west__resizable:				false,
        east__closable:					false,
        east__resizable:				false,
        south__closable:				false,
        south__resizable:				false

	};

function imagesList_readyFunc()
{
	innerLayoutImg = $('#innerLayoutImg').layout(innerLayoutSettings);

	//to make select box of paging and text box editable (div with zindex is on top of grid div)
    $("div .inner-eastImg").css("z-index","");
    
	$("#imageGrid").subscribe('loadImage', function(event,data) {
		if(document.getElementById("prevDiv").style.display=="none")
		{
			document.getElementById("prevDiv").style.display="inline";
		}
		rowid = (event["originalEvent"])["id"];
		myObject = $("#imageGrid").jqGrid('getRowData',rowid);
		var fileName=myObject["IMAGE_NAME"];
		var zImg = document.getElementById("zImg");
		zImg.src = jQuery.contextPath+"/path/designer/image_loadImage.action?updates="+fileName;
				
		
	});
	if(_pageRef=="RD00UD")
	{
		document.getElementById("okCancelButton_"+_pageRef).style.display = 'none';
	}
}

function cancelImg()
{
	$('#imageDialog').dialog('close');
}

function insertImg()
{
    reportHasChanged(true);       
	var imgObj = $("#imageGrid").jqGrid('getRowData',rowid);
    var fileName = imgObj["IMAGE_NAME"];
	var img = editor.document.createElement( 'img',
			{
				attributes :
				{
					src:jQuery.contextPath+"/path/designer/image_loadImage.action?updates="+encodeURIComponent(fileName),
					style : "width: 50px; height: 50px;"	
				}
			});

	editor.focus();
	range.select();
	editor.insertElement( img );
	$('#imageDialog').dialog('close');
}

function deleteImage(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
         if(confirmcChoice)
         {
        	 deleteTheImage(theArgs.rowid)
         }
	      }, {rowid : rowid});	
}

function deleteTheImage(rowid)
{
	var imgObj = $("#imageGrid").jqGrid('getRowData',rowid);
	var fileName = myObject["IMAGE_NAME"];
	
	var url = jQuery.contextPath+ "/path/designer/image_deleteImage.action";
    params = {};
    params["updates"]=fileName;
    $.post(url, params , function( param )
 	{
    	$("#imageGrid").trigger("reloadGrid");
    	//empty the image preview div
    	var zImg = document.getElementById("zImg");
		zImg.src = jQuery.contextPath+"/path/designer/image_loadImage.action";
		document.getElementById("prevDiv").style.display="none";
    	
 	});
	
}

function uploadImage()
{
	$('#uploadImageDialog').dialog('open');
}