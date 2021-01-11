
//Called to add a new File Header
function addNewFileMain()
{
	emptyMainFormAndDetGrid();
}

function saveFiles() {
	_showProgressBar(true);
	var fileName = document.getElementById("upload").value;
	var fileID = $("#FILE_ID_"+_pageRef).val();
	var oldFileName  = $("#FILE_NAME_" +_pageRef).val();
	var n = fileName.lastIndexOf("."); 
	var ext = fileName.substr(n , fileName.length);
	var result = $("#fileDetGrid_" + _pageRef).jqGrid('checkRequiredCells');
	if (!result) {
	 _showProgressBar(false);
	return;		
	}

	
	var jsonStringUpdates = $("#fileDetGrid_"+_pageRef).jqGrid('getChangedRowData');
	var jsonStringUpdates_1 = $("#fileDetGrid_"+_pageRef).jqGrid('getAllRows');
			
	//post the "jsonStringUpdates" to the page "fileExportImportAction"
	$("#updates_"+_pageRef).val(jsonStringUpdates);
	$("#updates_1_"+_pageRef).val(jsonStringUpdates_1);
	
	myObject =  $("#fileMainForm_"+_pageRef).serialize();
	ext = ext.toUpperCase();

	if (ext =="" && $("#FILE_ID_"+_pageRef).val() == "") {
		_showErrorMsg (missingFileName, error_msg_title, 300, 100);
		_showProgressBar(false);
		return;
	} 

	
	else {
		if ((ext == ".XLS" || ext == ".XLSX" || fileID != "")){
			var options = {
				url :jQuery.contextPath
					+ "/path/fileExportImport/fileExportImportMaintAction_saveFiles.action?_pageRef="
					+ _pageRef + "&fileId="+fileID+ "&oldFileName="+oldFileName,
				type : 'post',
				data :myObject,
				success : function(param, status, xhr) {
				_showProgressBar(false);
				var jsonObj = $.parseJSON($(param).html());
				if (jsonObj["_error"] == undefined || jsonObj["_error"] == null
					|| jsonObj["_error"] === "")
				{
					_showErrorMsg(saveSuccess,
							success_msg_title);
					emptyMainFormAndDetGrid();
					$("#actionErrorDiv").html(param);
						var url = jQuery.contextPath
								+ "/path/fileExportImport/fileExportImportListAction_loadMainGrid.action";
						$("#fileMainGrid_" + _pageRef).jqGrid('setGridParam', {
							url : url
						}).trigger("reloadGrid");
						
				}
				else
				{
					_showErrorMsg(jsonObj["_error"]);
				}

				},
			error: function(response) {
			_showProgressBar(false);
			_showErrorMsg("error"+response);
			}
			};
			$("#fileMainForm_" + _pageRef).ajaxSubmit(options)
		}
else 
	
    			 {
					if (fileID == "" && ext != ".XLS" && ext != ".XLSX"){
    						_showErrorMsg(notAllowed, error_msg_title, 300, 100);
    						_showProgressBar(false);
    						return;
    						}
    			 }
}
	 
	
	}

function editFileMain()
{
	
	_showProgressBar(true);
	rowid =$("#fileMainGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	var url=jQuery.contextPath+'/path/fileExportImport/fileExportImportListAction_retrieveMainGrid.action';
	params = {};
	var fileId=$("#fileMainGrid_"+_pageRef).jqGrid('getCell', rowid,'irp_file_mainVO.FILE_ID');
	params["_pageRef"]=_pageRef;
	params["fileId"]=fileId;

	//fill form
	$.post(url, params , function( param )
 	{	$("#tbDiv_"+_pageRef).html(param);
 			
 		//reload grid
 		 $("#fileDetGrid_"+_pageRef).jqGrid('setGridParam',
   		 {  url : jQuery.contextPath+"/path/fileExportImport/fileExportImportListAction_retrieveDetailFileList.action?fileId="+fileId+"&_pageRef="+_pageRef,
   			page : 1
   		 }).trigger("reloadGrid");
 		
 	},"html");
 	_showProgressBar(false);
}



function addFileDetails()
{
	if( !$("#fileDetGrid_"+_pageRef).jqGrid("checkRequiredCells")){
		return;
	}
	var rowId = $("#fileDetGrid_"+_pageRef).jqGrid('addInlineRow',{});
	$("#fileDetGrid_"+_pageRef).jqGrid('setSelection', rowId, true);

}

function deleteFileMain(sel_row_index)
{
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm , deleteTitle, "deleteConfFileMain", args);
}


function deleteFileDetails(sel_row_index)
{
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm , deleteTitle, "deleteConfFileDetails", args);
}

function deleteConfFileMain(confirmation, args)
{

	var selRow = args.selRow;
	if(confirmation)      
	{	var fileId=$("#fileMainGrid_"+_pageRef).jqGrid('getCell', selRow,'irp_file_mainVO.FILE_ID');
		var mainFileName=$("#fileMainGrid_"+_pageRef).jqGrid('getCell', selRow,'irp_file_mainVO.FILE_NAME');
		var url=jQuery.contextPath+'/path/fileExportImport/fileExportImportListAction_deleteMainFile.action?fileId='+fileId+'&mainFileName='+mainFileName;
		params ={};
 					$.ajax({
 						url: url,
 						type: "POST",
 						data: ({_pageRef:_pageRef}),				
 					    success: function(xml){
 					    	reloadMainGrid();
 					    	reloadDetGrid();
 					        emptyMainForm();
 		    			}
 					});
	}
	
}

function deleteConfFileDetails(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)      
	{
		$("#fileDetGrid_"+_pageRef).jqGrid('deleteGridRow');
	}
}

//Reload the Grid 'fileMainGrid_pageRef'
function reloadMainGrid()
{
	$("#fileMainGrid_"+_pageRef).trigger("reloadGrid");
}

//Reload the Grid 'fileDetGrid_pageRef'
function reloadDetGrid()
{
	$("#fileDetGrid_"+_pageRef).trigger("reloadGrid");
}

function emptyMainForm()
{
	$("#fileMainForm_"+_pageRef).load(jQuery.contextPath+ "/path/fileExportImport/fileExportImportMaintAction_emptyFileMainFrm.action?_pageRef="+_pageRef);
}

//Used to empty the Form and Grid of File Details
function emptyMainFormAndDetGrid()
{
	_showProgressBar(true);
	var url=jQuery.contextPath+'/path/fileExportImport/fileExportImportMaintAction_emptyFileMainFrm.action';
	params = {};
	params["_pageRef"]=_pageRef;
	//Empty form
	$.post(url, params , function( param )
 	{
 		$("#tbDiv_"+_pageRef).html(param);
 		//Empty grid of File Details
 		 $("#fileDetGrid_"+_pageRef).jqGrid('setGridParam',
   		 {    
   			url : jQuery.contextPath+"/path/fileExportImport/fileExportImportListAction_emptyDetailFileList.action",
   			page : 1
   		 }).trigger("reloadGrid");
 		
 	},"html");
 	_showProgressBar(false);
}
function fileExportImportList_viewFile(cellvalue, options, rowObject)
{
  return '<a href="#"  onclick="viewExportedFile(\''+options.rowId+'\')">'+viewFileTitle+'</a>';
}

function viewExportedFile(_rowid)
{
	$("#fileDetGrid_"+_pageRef).jqGrid("setSelection",_rowid,false);
	var fileId= $("#FILE_ID_"+_pageRef).val();
	var subFileName=$("#fileDetGrid_"+_pageRef).jqGrid('getCell', _rowid,'irp_file_detVO.SUB_FILE_NAME');
	var url=jQuery.contextPath+'/path/fileExportImport/fileExportImportListAction_viewFile.action';
	var objData = {};
	objData["fileId"] = fileId;
	objData["subFileName"] = subFileName;
		_showProgressBar(true);
		$.fileDownload(url,
		{
	    successCallback: function (url) {
			_showProgressBar(false);
	    },
	    failCallback: function (html, url) {	
			_showProgressBar(false);
	        _showErrorMsg(html);
	    },
	    data:objData
	});
}

	


function exportFile()
{	
		
	rowid =$("#fileMainGrid_"+_pageRef).jqGrid('getGridParam','selrow');

	var fileId=$("#fileMainGrid_"+_pageRef).jqGrid('getCell', rowid,'irp_file_mainVO.FILE_ID');
	
	if (fileId ==null || fileId =="") {
		_showErrorMsg (fileNotSelected, warning_msg_title, 300, 100);
		return;
	} 

	var url=jQuery.contextPath+'/path/fileExportImport/fileExportImportListAction_exportFile.action?fileId='+fileId;
	document.getElementById("exportFileFormId_"+ _pageRef).action=url;
	//document.getElementById("exportFileFormId_"+ _pageRef).submit();
	submitEncryptedData("exportFileFormId_"+ _pageRef);
}

function rep_fileExpImp_DetGridReady()
{
	 var rowId 	= $("#fileDetGrid_"+_pageRef).jqGrid('getGridParam','selrow');	
	 var zCol =  $("#fileDetGrid_"+_pageRef).jqGrid("getCellInputElt",rowId, "fillArgs").blur();
	 $("#fileDetGrid_"+_pageRef).jqGrid("setDialogCellReadOnlyText",rowId,"fillArgs",true);
	 //set row status to updated, only in case if it is not a new row
	 if(!rowId.startsWith("new")){
		 $("#fileDetGrid_"+_pageRef).jqGrid('setGridRowStatus',rowId,2);
	 }
 }

function rep_fileExpImp_CheckedReports()
{
	var arrayLength = $("#fileDetGrid_"+_pageRef).jqGrid('getCol','PROG_REF').length;
	var repRefsArray = new Array();
	var repRefsStr="";
	
	for(var i=1;i<=arrayLength;i++)
	{
		rowObject =  $("#fileDetGrid_"+_pageRef).jqGrid("getRowData",i);
		var progRef = rowObject["PROG_REF_D00"];
		
		var checked = rowObject["lineNoCheckBox"];
		if(checked=="Yes")
		{
			repRefsStr = repRefsStr+","+progRef;

		}

	}	
	repRefsStr = repRefsStr.substring(1);
	if ((repRefsStr !=null) && (repRefsStr !="")) {
	repRefsArray =repRefsStr.split(",");
	}
	return repRefsArray;
}
function rep_fileExpImp_RetrieveReports()
{	var repRefsArray = rep_fileExpImp_CheckedReports();
	
	if (repRefsArray.length=== 0)
		{_showErrorMsg (fileNotSelected, warning_msg_title, 300, 100);
		return;
		}
 	_showProgressBar(true);
		var url = jQuery.contextPath +"/path/repCommon/reportAction_retrieveExportImportReports.action"
	params = {};
	params["repRefsLst"] = repRefsArray;
	params["_pageRef"]=_pageRef;
	//Empty form
	$.post(url, params ,  function(param)
{_showProgressBar(false);

			if (typeof param["_error"] == "undefined"|| param["_error"] == null) 
			{
				_showErrorMsg(retrieveSuccess,info_msg_title, 300, 100);
			
			}
			else 
			{	
				_showErrorMsg(retrieveFailed,error_msg_title, 300, 100);
				
			}
		
 	});
 	
}
