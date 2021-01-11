function addModCol()
{
	$("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('addInlineRow',{});	
}




function deleteModSnCol(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteModSnColFunc(theArgs.rowid)
           }
	      }, {rowid : rowid}, yes_confirm, no_confirm, 300, 100);	
	
}

 function deleteModSnColFunc(rowid)
	 {
		myObject 			= $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	    var toDelete 		= myObject["repSnapshotModifyColumnVO.COLUMN_MODIFY"];
		var zSrc			= jQuery.contextPath+ "/path/snapshotParameter/SnapshotModifiedColumnAction_deleteModColb.action";
 		$("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow'); 
	}
	
	
function saveModCols()
{
			var jsonStringUpdates = $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getAllRows');
		 	$("#updateModColumns_"+_pageRef).val(jsonStringUpdates); 
		 	usedProgRef	=$("#colModifProgRef_"+_pageRef).val();
		 	frequency	=$("#colModifFreq_"+_pageRef).val();
		 	 var url = jQuery.contextPath+ "/path/snapshotParameter/SnapshotModifiedColumnAction_saveModifiedColumns"
		 	 myObject =  $("#modColForm_"+_pageRef).serialize();
		 	 $.post(url, myObject , function()
	 	 	{
		 		 var rowidModifs = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
				 var myObject 	= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowidModifs);
				 var progRefOld 	= myObject["progRefOld"];
				 var freqOld		= myObject["freqOld"];
				 var newProgRef  = myObject["repSnapshotParamVO.REP_REFERENCE"];
				 var newFreq		= myObject["repSnapshotParamVO.SNAPSHOT_FREQUENCY"];
				 if(newProgRef!="" && (progRefOld=="" || freqOld==""))
				 {
					 $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCell',rowid,'freqOld',newFreq);
					 $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCell',rowid,'progRefOld',newProgRef);	
				 }
				$.data(document.getElementById($("#snpFrmId12_"+_pageRef).attr("id")),"changeTrack",true); 
	 	 	});	 	 		
}

function checkModCol()
{
	var currentColumnModifyId = $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var currentColumnModify = $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',currentColumnModifyId)["repSnapshotModifyColumnVO.COLUMN_MODIFY"]; 
  	var allRowIds =  $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
  	var nbOccurences = 0;
  	if(allRowIds)
  	{
  		
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	//jQuery('#snapshotParameterModifiedColumnGridTbl_Id_'+_pageRef).jqGrid('setSelection',llRowId); 
	    	myObject =$("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',llRowId);
	    	if(myObject["repSnapshotModifyColumnVO.COLUMN_MODIFY"]==currentColumnModify)
	    	{
	    		nbOccurences++;
	    	}	    	
	    }
	    if(nbOccurences>1)
	    {
		  _showErrorMsg(modColAlreadyUsed,error_msg_title,300,100)
		  $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('setCellValue',currentColumnModifyId,'repSnapshotModifyColumnVO.REP_ID'," ");
    	  $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('setCellValue',currentColumnModifyId,'repSnapshotModifyColumnVO.COLUMN_MODIFY'," ");
    	  $("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('setCellValue',currentColumnModifyId,'repSnapshotModifyColumnVO.COLUMN_TYPE'," ");
		}
	   
    }
}
