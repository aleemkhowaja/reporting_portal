function checkRelatedCriteriaDup()
{
	var currentRelatedCrtId = $("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	var currentRelatedCrt   = $("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('getRowData',currentRelatedCrtId)["repMismatchIntraCriteriaVO.RELATED_CRITERIA"]; 
  	var allRowIds           = $("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('getDataIDs');
  	var nbOccurences = 0;
  	if(allRowIds)
  	{
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	myObject =$("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('getRowData',llRowId);
	    	if(myObject["repMismatchIntraCriteriaVO.RELATED_CRITERIA"]==currentRelatedCrt)
	    	{
	    		nbOccurences++;
	    	}	    	
	    }
	    var rowId 			= $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getGridParam','selrow');	
		var myObject     	= $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getRowData',rowId);
		if(myObject["repMismatchParamVO.CRITERIA_CODE"]==currentRelatedCrt)
		{
			nbOccurences=2;
		}
	    if(nbOccurences>1)
		{
		  _showErrorMsg(drilColAlreadyUsed,error_msg_title,300,100)
		  deleteRelatedCrt();
		  return false;
	    }   
    }
    return true;
}		


function addRelCriteria()
{
		mismatchId = $("#misRepMisID_"+_pageRef).val();
    	$("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('addInlineRow',{'repMismatchIntraCriteriaVO.REP_MISMATCH_ID':mismatchId});
}


function deleteRelatedCrt()
{
	$("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('deleteGridRow');
}
	
	