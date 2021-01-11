function notifyLostMisChanges()
{
	var jsonStringUpdates1 = $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getChangedRowData')
	if (jsonStringUpdates1 == "")
	{
		return;
	}
	_showConfirmMsg(looseAllData, info_msg_title,function(confirmcChoice, theArgs) {
		if (confirmcChoice) 
		{
		   //simulate the click on search button
		   triggerSearchGridPopup("reportsMismatchListGridTbl_Id_1_"+_pageRef);
		   return false;
		}
	   	return false;
	    }, {}, yes_confirm, no_confirm, 300, 100);
	    return false;
	
}



	
function checkRelCols()
{
	var currentRelatedColId = $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getGridParam','selrow');
	var currentRelatedCol = $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getRowData',currentRelatedColId)["repMismatchColumnVO.RELATED_COLUMN"]; 
  	var allRowIds =  $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getDataIDs');
  	var nbOccurences = 0;
  	if(allRowIds)
  	{
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	myObject =$("#reportsMismatchCrtList_"+_pageRef).jqGrid('getRowData',llRowId);
	    	if(myObject["repMismatchColumnVO.RELATED_COLUMN"]==currentRelatedCol)
	    	{
	    		nbOccurences++;
	    	}	    	
	    }
	    if(nbOccurences>1)
		{
		      _showErrorMsg(drilColAlreadyUsed,error_msg_title,300,100)
		  	  $("#reportsMismatchCrtList_"+_pageRef).jqGrid('setCellValue',currentRelatedColId,'repMismatchColumnVO.REP_MISMATCH_ID'," ");
    	  	  $("#reportsMismatchCrtList_"+_pageRef).jqGrid('setCellValue',currentRelatedColId,'repMismatchColumnVO.RELATED_COLUMN'," ");
    	  	  $("#reportsMismatchCrtList_"+_pageRef).jqGrid('setCellValue',currentRelatedColId,'repMismatchColumnVO.COLUMN_TYPE'," ");
	    }
	   
    }
}	