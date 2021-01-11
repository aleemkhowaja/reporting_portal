function openDelAllSn()
{
	_showConfirmMsg(reportingDelAllLines, reportingDelAll,function(confirmcChoice, theArgs) {
		if (confirmcChoice) {
			    //sending the grid all rows to the action
				var alreadyDec=false
				var allRowIds = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
				jQuery.each(allRowIds, function(i, val) {
						jQuery('#snapshotInformationListGridTbl_Id_'+_pageRef).jqGrid('setSelection', val);
						myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',val);
						if(myObject["repSnapshotInfoVO.DECLARED_YN"]=='false')
						{
							$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow',val); 
						}
						else
						{
							alreadyDec=true
						}		
					});
				if(alreadyDec)
				{
					_showErrorMsg(errorAlreadyDeclared,error_msg_title,300,100);
				}
					
				var jsonStringUpdates1 = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getChangedRowData');//returns rows in the screen
				if(jsonStringUpdates1!='{\"root\":[]}')
				{	
					$("#updateSnInfo_"+_pageRef).val(jsonStringUpdates1); 	
				}	
					
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
}

function deleteSnInfRec()
{
	rowid 	 = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   	if(myObject["repSnapshotInfoVO.DECLARED_YN"]=='false')
				{
					$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow'); 
				}
				else
				{
					_showErrorMsg(errorAlreadyDeclared,error_msg_title,300,100);
				}
        
           }
	      }, {}, yes_confirm, no_confirm, 300, 100);	

}

function submitFormSnInf()
{
	var jsonStringUpdates = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getChangedRowData');
	var allRowIds = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
	var isCheckedDecl=false;
	var isUncheckedDecl=false;
	var rowidProblem;
	jQuery.each(allRowIds, function(i, val) {
				myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',val);
				//removed old code to avoid audit PK issues (each setSelection was calling the retrieve audit)
				if((myObject["repSnapshotInfoVO.DECLARED_YN"]=='true' && myObject["repSnapshotInfoVO.DECLARED_BY"]=="")
					|| (myObject["repSnapshotInfoVO.DECLARED_YN"]=='true' && myObject["repSnapshotInfoVO.DECLARED_DATE"]==""))
				{
					isCheckedDecl=true;	 
					rowidProblem=val;
				}
				else if( (myObject["repSnapshotInfoVO.DECLARED_YN"]=='false' && myObject["repSnapshotInfoVO.DECLARED_DATE"]!="")
					|| (myObject["repSnapshotInfoVO.DECLARED_YN"]=='false' && myObject["repSnapshotInfoVO.DECLARED_BY"]!=""))
					{
					isUncheckedDecl=true;	 
					rowidProblem=val;
					}
				
				
										});
	 jQuery('#snapshotInformationListGridTbl_Id_'+_pageRef).jqGrid('setSelection',rowidProblem);
	 if(isCheckedDecl)
	 {
	 	_showErrorMsg(errorDeclaredUser,error_msg_title,300,100);
	 	return;
	 }
	 else if (isUncheckedDecl)
		 {
		 	_showErrorMsg(errorUncheckDeclaredUser,error_msg_title,300,100);
	 	return;
		 }
	 else
	 {
		$("#updateSnInfo_"+_pageRef).val(jsonStringUpdates); 
		$("#snapshotInformationMaintFormId_"+_pageRef).trigger('submit');
	 }

}

function onChangeIncludeFile()
{ 
	rowid = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	//checking case where the user is removing the check from include file
	
	
	if(myObject["ENABLE_SITCOM_YN"]=="true")
	{
		//check the enable/disable in parameter screen
		   	var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_checkIncludeFileConditions?_pageRef="+_pageRef
			$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			 		if(param["updates"]=="1")
	  				{
	  					//empty the declared informations
	  					if(myObject["repSnapshotInfoVO.DECLARED_YN"]=='true')
				        {
				        	_showErrorMsg(cannotDeclared,error_msg_title,300,100);
							$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','false');
							return;	
				        }
				        $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','true');
					}
					else if(param["updates"]=="2")
					{
						 _showConfirmMsg(alreadyGenerated, info_msg_title, function(confirmcChoice, theArgs){
				           if(!confirmcChoice)
				           {
				        	   $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','false');
				           }
				           else
				           {
				           		//empty the declared informations
				           		if(myObject["repSnapshotInfoVO.DECLARED_YN"]=='true')
				           		{
				           			_showErrorMsg(cannotDeclared,error_msg_title,300,100);
									$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','false');	
									return;
				           		}
				           		$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','true');
				           }
					      }, {rowid : rowid}, yes_confirm, no_confirm, 300, 100);	
					}	
					else if(param["updates"]=="3")
					{
						_showErrorMsg(cannotDeclared,error_msg_title,300,100);
						$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','false');	
					}
					else if(param["updates"]=="4")
					{
						_showErrorMsg(notEnabledFileGen,error_msg_title,300,100);
						$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','false');	
					}
			 }
	    });    
	}
}


function onChangeDeclYn()
{
	rowid 	 = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	if(myObject["repSnapshotInfoVO.DECLARED_YN"]=='false')
	{
				$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotInfoVO.DECLARED_BY'," ");
				$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'repSnapshotInfoVO.DECLARED_DATE'," ");
	}
	else
	{
		$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'ENABLE_SITCOM_YN','false');
	}
}


function applyMisInfoAudit()
{
				   	var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotInformationListAction_applyMisInfoAudit?_pageRef="+_pageRef
				    rowid = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
				    myObject = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
					$.ajax({
					 url: url,
			         type:"post",
					 dataType:"json",
					 data: myObject,
					 success: function(param)
					 {
					   $("#auditTrxNbr_"+_pageRef).val(param["auditTrxNbr"]);
					 }
				    });
}