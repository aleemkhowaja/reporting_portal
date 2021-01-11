function addDrilCol()
{
	$("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('addInlineRow',{});	
}




function deleteDrilSnCol(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteDrilSnColFunc(theArgs.rowid)
           }
	      }, {rowid : rowid}, yes_confirm, no_confirm, 300, 100);	
	
}

 function deleteDrilSnColFunc(rowid)
	 {
		myObject 			= $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
	    var toDelete 		= myObject["repSnapshotDrilColVO.COLUMN_DRILLDOWN"];
		var zSrc			= jQuery.contextPath+ "/path/snapshotParameter/SnapshotDrilldownColumnAction_deleteDrilCol.action";
 		$("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow'); 
	}
	
	
function saveDrilCols()
{

			var jsonStringUpdates = $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('getAllRows');
		 	$("#updateDrilColumns_"+_pageRef).val(jsonStringUpdates); 
		 	usedProgRef	=$("#colDrilProgRef_"+_pageRef).val();
		 	frequency	=$("#colDrilFreq_"+_pageRef).val();
		 	 var url = jQuery.contextPath+ "/path/snapshotParameter/SnapshotDrilldownColumnAction_saveDrilldownColumns"
		 	 myObject =  $("#drilColForm_"+_pageRef).serialize();
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

function checkDrilCol()
{
	var currentColumnModifyId = $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	var currentColumnModify = $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',currentColumnModifyId)["repSnapshotDrilColVO.COLUMN_DRILLDOWN"]; 
  	var allRowIds =  $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
  	var nbOccurences = 0;
  	if(allRowIds)
  	{
  		
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	//jQuery('#snapshotParameterModifiedColumnGridTbl_Id_'+_pageRef).jqGrid('setSelection',llRowId); 
	    	myObject =$("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',llRowId);
	    	if(myObject["repSnapshotDrilColVO.COLUMN_DRILLDOWN"]==currentColumnModify)
	    	{
	    		nbOccurences++;
	    	}	    	
	    }
	    if(nbOccurences>1)
		{
		      _showErrorMsg(drilColAlreadyUsed,error_msg_title,300,100)
		  	  $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('setCellValue',currentColumnModifyId,'repSnapshotDrilColVO.REP_ID'," ");
    	  	  $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('setCellValue',currentColumnModifyId,'repSnapshotDrilColVO.COLUMN_DRILLDOWN'," ");
    	  	  $("#snapshotParameterDrilColumnGridTbl_Id_"+_pageRef).jqGrid('setCellValue',currentColumnModifyId,'repSnapshotDrilColVO.COLUMN_TYPE'," ");
	    }
	   
    }
}	
