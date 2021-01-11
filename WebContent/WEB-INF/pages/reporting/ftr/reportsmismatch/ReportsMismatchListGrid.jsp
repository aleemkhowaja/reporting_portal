<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<ps:set name="crtMisDup_var" 		value="%{getEscText('reportsMismatch.criteriaDup')}"/>
<ps:set name="crtMisDupTitle_var" 	value="%{getEscText('reportsMismatch.duplication')}"/>
<ps:set name="crtProgMisDup_var" 	value="%{getEscText('reportsMismatch.crtProgMisDup')}"/>
<ps:set name="refAlreadyUsed_var" 	value="%{getEscText('reportsMismatch.valueInUse')}"/>
<ps:set name="invalidCrtCode_var" 	value="%{getEscText('reportsMismatch.invalidCrtCode')}"/>
<ps:set name="invalidRepRef_var" 	value="%{getEscText('reportsMismatch.invalidProgRefCode')}"/>
<ps:set name="looseAllData_var" 	value="%{getEscText('reportsMismatch.looseAllData')}"/>
<ps:set name="missingCrtCol_var" 	value="%{getEscText('reportsMismatch.missingCrtCol')}"/>
<ps:set name="notSameAsMainGrid_var" 	value="%{getEscText('reportsMismatch.notSameAsMainGridReport')}"/>
<ps:set name="notifyRelColsDel_var" 	value="%{getEscText('reportsMismatch.notifyRelColsDel')}"/>



<ps:form id="misGridFrm_${update}_${_pageRef}" useHiddenProps="true">
	<ps:hidden name="update" 							id="mainGridCalledFrom_${update}_${_pageRef}"></ps:hidden>
	<ps:hidden    name="updates" 							id="updateRelRep_${update}_${_pageRef}"></ps:hidden>
	<ps:hidden    name="updates1" 							id="updateRelRep1_${update}_${_pageRef}"></ps:hidden>
	<ps:hidden    name="repMismatchParamVO.CRITERIA_CODE" 	id="crtCode_${update}_${_pageRef}"></ps:hidden>
	<ps:hidden    name="repMismatchParamVO.COMP_CODE" 		id="compCode_${update}_${_pageRef}"></ps:hidden>
</ps:form>
<ps:hidden  name="criteriaCodeGrid" id="criteriaNotEmpty_${update}_${_pageRef}"></ps:hidden>

<ps:form applyChangeTrack="true" id="repMismatchGridForm_${_pageRef}" name="repMisGridForm" action="ReportsMismatchListAction_loadReportsMismatchGrid" namespace="/path/reportsMismatch">
	
	<ps:url id="urlreportsMismatchListGrid" escapeAmp="false"
		action="ReportsMismatchListAction_loadReportsMismatchGrid"
		namespace="/path/reportsMismatch">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		<ps:param name="update" value="update"></ps:param>
		<ps:param name="mismatchType" value="mismatchType"></ps:param>
		<ps:param name="repMisGridParamVO.REP_REFERENCE" 	value="repMismatchParamVO.REP_REFERENCE"></ps:param>
		<ps:param name="repMisGridParamVO.CRITERIA_CODE" 	value="repMismatchParamVO.CRITERIA_CODE"></ps:param>
		<ps:param name="repMisGridParamVO.REP_MISMATCH_ID"  value="repMismatchParamVO.REP_MISMATCH_ID"></ps:param>
	</ps:url>
	<psjg:grid id="reportsMismatchListGridTbl_Id_${update}_${_pageRef}"
		href="%{urlreportsMismatchListGrid}" dataType="json" pager="true"
		navigator="true" navigatorSearch="true"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true,beforeShowSearch:function(){return notifyLostMisChanges()}}"
		navigatorEdit="false" navigatorAdd="true" navigatorDelete="true"
		caption=" " navigatorRefresh="true" viewrecords="true"
		delfunc="deleteRepMisCol${update}" editinline="true" addfunc="addRepMis${update}"
		editurl="%{urlreportsMismatchListGrid}" sortable="false" 
		gridModel="gridModel" shrinkToFit="true" 
		pagerButtons="false" disableEditableFocus="true"
		onCompleteTopics="lineGrdComplete%{update}"
		onEditInlineBeforeTopics = "checkMandatoryMis%{update}">
		<psjg:gridColumn name="repMismatchParamVO.REP_MISMATCH_ID"
			index="repMismatchParamVO.REP_MISMATCH_ID" id="REP_MISMATCH_ID"
			title="REP_MISMATCH_ID" colType="text" search="false" hidden="true" />
		<psjg:gridColumn name="repMismatchParamVO.ROW_YN"
			index="repMismatchParamVO.ROW_YN" id="ROW_YN" title="ROW_YN"
			colType="text" search="false" hidden="true"/>
		<psjg:gridColumn name="repMismatchParamVO.COLUMN_TYPE"
			index="repMismatchParamVO.COLUMN_TYPE" id="COLUMN_TYPE"
			title="COLUMN_TYPE" colType="text" search="false" hidden="true"/>
		<psjg:gridColumn name="repMismatchParamVO.MISMATCH_TYPE" 
			index="repMismatchParamVO.MISMATCH_TYPE" id="MISMATCH_TYPE"
			title="MISMATCH_TYPE" colType="text" search="false" hidden="true"/>
		<psjg:gridColumn name="repMismatchParamVO.COMP_CODE"
			index="repMismatchParamVO.COMP_CODE" id="COMP_CODE"
			title="company code" colType="text" search="false" hidden="true" />
		<psjg:gridColumn name="oldCrt" index="oldCrt" id="oldCrt"
			title="old criteria" colType="text"  search="false" hidden="true" />
		<psjg:gridColumn name="oldProgRef" index="oldProgRef" id="oldProgRef"
			title="old prog ref" colType="text"  search="false" hidden="true"/>
		<psjg:gridColumn name="repMismatchParamVO.CRITERIA_CODE"   search="true" hidden="%{hideCrt}"
			index="repMismatchParamVO.CRITERIA_CODE" id="CRITERIA_CODE" 
			title="%{getText('criteria.criteriaTitle')}" colType="text"  
			editable="true" editoptions="{ dataEvents: [{ type: 'change', fn: function(e) { onChangeCriteriaCode${update}(0);checkMandatoryMis${update}();}} ,{type: 'click',fn:function(e){onClickCriteriaCode${update}()}}],maxlength : 15}" />
		<psjg:gridColumn name="repMismatchParamVO.REP_REFERENCE" search="true" 
			id="REP_REFERENCE" index="repMismatchParamVO.REP_REFERENCE"
			title="%{getText('repRef_key')}" editable="true" sortable="false"
			colType="liveSearch" searchElement="repSnapshotParamVO.REP_REFERENCE"
			dataAction="${pageContext.request.contextPath}/path/reportsMismatch/ReportsMismatchListAction_reportLookup"
			resultList="repSnapshotParamVO.REP_REFERENCE:lookuptxt_REP_REFERENCE"
			editoptions="{ dataEvents: [{ type: 'change', fn: function(e) { onChangeCriteriaCode${update}(1);checkMandatoryMis${update}();} }],readonly:'readonly',maxlength : 15}" />
		<psjg:gridColumn name="repMismatchParamVO.CRITERIA_COLUMN" search="true"
			index="repMismatchParamVO.CRITERIA_COLUMN" editable="true"
			id="repMismatchParamVO.CRITERIA_COLUMN" title="crieteria col" hidden="true"
			colType="text" />
		<psjg:gridColumn name="CRT_COL" search="false"
			editoptions="{maxlength : 50}" 
			index="CRT_COL" id="CRT_COL" 
			title="%{getText('reportsMismatch.criteriaColumn')}" colType="dialog"
			dialogUrl="/path/reportsMismatch/ReportsMismatchListAction_openCriteriaColumn?_pageRef=${_pageRef}"
			dialogOptions="{ autoOpen: false, height:600,title:'%{getText('reportsMismatch.criteriaColumn')}' , width:900 ,modal: true, close: function(){$(this).remove()}, buttons: { '%{getText('reporting.ok')}': function(){if(saveCriteriaColumnsMis${update}()){$( this ).dialog( 'close' );$( this ).remove()};},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }"
			editable="true" align="center" sortable="false" />
		<psjg:gridColumn name="relRep" hidden="%{hideRelReports}" search="false"
			dialogUrl="/path/reportsMismatch/ReportsMismatchMaintAction_openRelRepMis?_pageRef=${_pageRef}"
			dialogOptions="{ autoOpen: false, height:600,title:'%{getText('template.relatedReports')}' , width:900 ,modal: true, close: function(){$(this).remove()}, buttons: { '%{getText('reporting.ok')}': function(){if(saveRelRep${update}()){$( this ).dialog( 'close' );$( this ).remove()}},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }"
			index="relRep" colType="dialog"
			title="%{getText('template.relatedReports')}" editable="true"
			align="center" sortable="false" />
		<psjg:gridColumn name="relCrt" index="relCrt" hidden="%{hideRelCrt}" search="false"
			title="%{getText('reportsMismatch.relatedCriteria')}"
			dialogUrl="/path/reportsMismatch/ReportsMismatchListAction_openRelatedCriteria?_pageRef=${_pageRef}"
			dialogOptions="{ autoOpen: false, height:500,title:'%{getText('reportsMismatch.relatedCriteria')}' , width:700 ,modal: true, close: function(){$(this).remove()}, buttons: { '%{getText('reporting.ok')}': function(){saveRelCrt${update}();$( this ).dialog( 'close' );$( this ).remove()},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }"
			colType="dialog" width="150" editable="true" align="center"
			sortable="false" />
		<psjg:gridColumn name="relCols" search="false"
			dialogUrl="/path/reportsMismatch/ReportsMismatchListAction_openRelatedColumns?_pageRef=${_pageRef}"
			dialogOptions="{ autoOpen: false, height:600,title:'%{getText('reportsMismatch.relatedColumns')}' , width:900 ,modal: true, close: function(){$(this).remove()}, buttons: { '%{getText('reporting.ok')}': function(){if(saveRelatedColMis%{update}()){$( this ).dialog( 'close' );$( this ).remove()};},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }"
			index="relCols" colType="dialog"
			title="%{getText('reportsMismatch.relatedColumns')}" editable="true"
			align="center" sortable="false" />
	</psjg:grid>
	</ps:form>
<script>
var crtMisDup =			'<ps:property value="crtMisDup_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var crtMisDupTitle = 	'<ps:property value="crtMisDupTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var crtProgMisDup = 	'<ps:property value="crtProgMisDup_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var refAlreadyUsed =  	'<ps:property value="refAlreadyUsed_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var invalidCrtCode =  	'<ps:property value="invalidCrtCode_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var invalidRepRef =  	'<ps:property value="invalidRepRef_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var looseAllData  =     '<ps:property value="looseAllData_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingCrtCol =	    '<ps:property value="missingCrtCol_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var notSameAsMainGrid = '<ps:property value="notSameAsMainGrid_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var notifyRelColsDel=	'<ps:property value="notifyRelColsDel_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).subscribe('checkMandatoryMis'+${update}, function(event, data) {
		checkMandatoryMis${update}();
	});


function checkMandatoryMis${update}()
{
	var rowId 			= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');	
	var myObject     	= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowId);
	//2 columns are mandatory
	criteriaCode = myObject["repMismatchParamVO.CRITERIA_CODE"]	
	repReference = myObject["repMismatchParamVO.REP_REFERENCE"]	
	if(criteriaCode==""  || repReference=="")
	{
		 $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relRep",true);	    
		 $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relCols",true);
		 $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relCrt",true);	
		 $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"CRT_COL",true);    	    
	}
	else
	{
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setDialogCellReadOnlyText",rowId,"relRep",true);
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setDialogCellReadOnlyText",rowId,"relCols",true);
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setDialogCellReadOnlyText",rowId,"relCrt",true);
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setDialogCellReadOnlyText",rowId,"CRT_COL",true);
	}
	//Adding code for audit functionality
	if(${update}==1)
	{
		var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_applyMismatchParamAudit?_pageRef="+_pageRef
		rowid = $("#reportsMismatchListGridTbl_Id_${update}_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject = $("#reportsMismatchListGridTbl_Id_${update}_"+_pageRef).jqGrid('getRowData',rowid);
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

}


function onClickCriteriaCode${update}()
{
	rowid = $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getRowData',rowid);
	$("#criteriaNotEmpty_"+${update}+"_"+_pageRef).val(myObject["repMismatchParamVO.CRITERIA_CODE"]);
}



function saveRelRep${update}()
{
	//checking for missing informations
	var allRowIds =  $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid('getDataIDs');
	 if(allRowIds)
	 {		
	  	for (var i =0;i<allRowIds.length;i++)
	    {
	    	llRowId=allRowIds[i];
	    	myObject =$("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid('getRowData',llRowId);
	    	var enteredInACondition=false;
	    	//confirm(myObject["repMismatchParamVO.REP_REFERENCE"]+"      "+myObject["repMismatchParamVO.CRITERIA_COLUMN"]+"       "+myObject["repMismatchParamVO.CRITERIA_CODE"])
	    	if(myObject["repMismatchParamVO.CRITERIA_CODE"] == "" )
	    	{
	    		 _showErrorMsg(invalidCrtCode,error_msg_title,300,100);
	    		 enteredInACondition=true;
	    	}
	    	if(myObject["repMismatchParamVO.REP_REFERENCE"] == "")
	    	{
	    		_showErrorMsg(invalidRepRef,error_msg_title,300,100);
	    		enteredInACondition=true
	    	}
	    	if(myObject["repMismatchParamVO.CRITERIA_COLUMN"]=="")
	    	{
	    		_showErrorMsg(missingCrtCol,error_msg_title,300,100);
	    		enteredInACondition=true
	    	}
	    	if(enteredInACondition)
	    	{
	    		return false;
	    	}
	    }
	  }
	
	var jsonStringUpdates  = $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid('getChangedRowData');//returns deleted rows
	var jsonStringUpdates1 = $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid('getAllRows');//returns rows in the screen
 	$("#updateRelRep_2_"+_pageRef).val(jsonStringUpdates); 
 	$("#updateRelRep1_2_"+_pageRef).val(jsonStringUpdates1);
 	myObject = $("#misGridFrm_2_"+_pageRef).serialize();
 	
 	var url     = jQuery.contextPath+ "/path/reportsMismatch/ReportsMismatchListAction_saveRelatedReports";
 	 $.post(url, myObject , function()
	 {
	 });	 		
	 return true;
}
//function created to check if the progRef selected in the related reports dialog is same as the one in the main grid
function checkRelRepRef${update}()
{
	rowid = $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('getRowData',rowid);
	refNotToSelect=myObject["repMismatchParamVO.REP_REFERENCE"];
	rowidRelRep = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
	myObjectRelRep = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowidRelRep);
	selectedRef = myObjectRelRep["repMismatchParamVO.REP_REFERENCE"];
	//comparing with the main grid's progRef
	if(refNotToSelect==selectedRef)
	{  
		_showErrorMsg(notSameAsMainGrid,error_msg_title,300,100);
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowidRelRep,'repMismatchParamVO.REP_REFERENCE'," ");
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowidRelRep,"relCols",true);
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowidRelRep,"relCrt",true);
		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowidRelRep,"CRT_COL",true);
		return 1;
	}
	else //checking for duplication in progRef selection in the related reports grid
	{
		var allRowIds =  $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getDataIDs');
	  	var nbOccurences = 0;
	  	if(allRowIds)
	  	{		
		  	for (var i =0;i<allRowIds.length;i++)
		    {
		    	llRowId=allRowIds[i];
		    	myObject =$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',llRowId);
		    	if(myObject["repMismatchParamVO.REP_REFERENCE"]==myObjectRelRep["repMismatchParamVO.REP_REFERENCE"])
		    	{
		    		nbOccurences++;
		    	}	    	
		    }
		    if(nbOccurences>1)
		    {
			  _showErrorMsg(refAlreadyUsed,error_msg_title,300,100)
			  $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid('setCellValue',rowidRelRep,'repMismatchParamVO.REP_REFERENCE'," ");
			  $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid("setCellReadOnly",rowidRelRep,"relCols",true);
			  $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid("setCellReadOnly",rowidRelRep,"relCrt",true);
			  $("#reportsMismatchListGridTbl_Id_2_"+_pageRef).jqGrid("setCellReadOnly",rowidRelRep,"CRT_COL",true); 
			  return 1;	
			}
		   
	    }
	}
}

function onChangeCriteriaCode${update}(fromWhere)
{

	rowid = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
	if(myObject["repMismatchParamVO.CRITERIA_CODE"]=="" && fromWhere==0)
	{
	   _showErrorMsg(invalidCrtCode,error_msg_title,300,100);
	   $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_CODE',$("#criteriaNotEmpty_"+${update}+"_"+_pageRef).val());
	   return;
	}	
 	if(myObject["repMismatchParamVO.REP_REFERENCE"]=="" && myObject["repMismatchParamVO.REP_MISMATCH_ID"]!="")
	{
	   _showErrorMsg(invalidRepRef,error_msg_title,300,100);
	   $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relRep",true);	    
	   $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relCols",true);
	   $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relCrt",true);	
	   $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"CRT_COL",true);    	    
	   $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.REP_REFERENCE'," ");
	   return;
	}
	
	if(${update}==2)//it's the related report dialog
	{
		if(checkRelRepRef${update}()==1)
		{
			//user choosed same ref as main grid
			return;
		}
	}
	if($("#mismatchType_"+_pageRef).val()==0)//intra reports
	{
		//checking for the duplication in criteria code + progref.the combination cannot be repeated
		rowid = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
		var currentCriteria  = myObject["repMismatchParamVO.CRITERIA_CODE"];
		var currentProgRef   = myObject["repMismatchParamVO.REP_REFERENCE"];
		var oldProgRef 		= myObject["oldProgRef"];
	    var rowIds = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getDataIDs');
	    var nbOccur=0;

	    for (var i =0;i<rowIds.length;i++)
	    {
	        var lRowId = rowIds[i];
	    	lObject = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',lRowId);
	    	if(lObject["repMismatchParamVO.CRITERIA_CODE"]== currentCriteria && lObject["repMismatchParamVO.REP_REFERENCE"]== currentProgRef)
	    	{
	    		nbOccur++;
	    	}
	    }
	    if(nbOccur>1)
	    {
	    	_showErrorMsg(crtProgMisDup,error_msg_title,300,100)
	    	if(fromWhere==1)
	    	{
	    		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.REP_REFERENCE'," ");
	    	}
	    	else
	    	{
	    		$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_CODE'," ");
	    	}
	    	return "1";
	    }
	    //end checking duplication
	    else
	    {
	    	//added to check if a msg should be displayed to the user
	    	if(oldProgRef!=currentProgRef && oldProgRef!="")
	    	{
	    		//start alert that related columns will be deleted
		    	_showConfirmMsg(notifyRelColsDel, info_msg_title,function(confirmcChoice, theArgs) {
				if (!confirmcChoice) 
				{
				 $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.REP_REFERENCE',oldProgRef);
				 return; 
				}
				else
				{
					adjustHash${update}(0);	
				}
			    }, {}, yes_confirm, no_confirm, 300, 100);
		    	//end alert
	    	}
	    	else
	    	{
	    		adjustHash${update}(0);	
	    	}
	    	return 0;
	    }
	    $("#criteriaNotEmpty_"+${update}+"_"+_pageRef).val("")
	}
	else//inter reports
	{	
		//must be in the main grid.
		//code inside the if will check for criteria duplication in the main grid and adjust the hash if necessary
		rowid 				= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject 			= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
		var oldProgRef 		= myObject["oldProgRef"];
		var oldCrt			= myObject["oldCrt"];
		var newProgRef  	= myObject["repMismatchParamVO.REP_REFERENCE"];
		var newCrt			= myObject["repMismatchParamVO.CRITERIA_CODE"];
		var currentCriteria = myObject["repMismatchParamVO.CRITERIA_CODE"];
		var rowIds 			= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getDataIDs');
		var repMisId 		= myObject["repMismatchParamVO.REP_MISMATCH_ID"];
		var nbOccur 		=0;
		if( ${update}==1 )
		{
		    for (var i =0;i<rowIds.length;i++)
		    {
		        var lRowId = rowIds[i];
		    	lObject = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',lRowId);
		    	if(lObject["repMismatchParamVO.CRITERIA_CODE"]== currentCriteria)
		    	nbOccur++;
		    }
	
		    if(nbOccur>1)
		    {
		    	_showErrorMsg(crtMisDup,error_msg_title,300,100)
		    	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_CODE',$("#criteriaNotEmpty_"+${update}+"_"+_pageRef).val());
		    	return "1";
		    }
		    //create function that adjusts hash
		    else
		    {   
		    	//added to check if a msg should be displayed to the user
		    	if(oldProgRef!=newProgRef && oldProgRef!="")
		    	{
		    		//start alert that related columns will be deleted
			    	_showConfirmMsg(notifyRelColsDel, info_msg_title,function(confirmcChoice, theArgs) {
					if (!confirmcChoice) 
					{
					 $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.REP_REFERENCE',oldProgRef);
					 return; 
					}
					else
					{
						adjustHash${update}(1);	
					}
				    }, {}, yes_confirm, no_confirm, 300, 100);
			    	//end alert
		    	}
		    	else
		    	{
		    		adjustHash${update}(1);	
		    	}
		    }
		}
		//empty criteria column cell
		else if (${update}==2)//for the related reports
		{
			if(oldProgRef!="" && newProgRef!="" && oldProgRef!=newProgRef)
			{
			   	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_COLUMN','');
			   	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'CRT_COL','');
			   	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCell',rowid,'oldProgRef',newProgRef);
			   	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.COLUMN_TYPE','');
			   	
			   	
			   	myObject["repMismatchParamCO.oldProgRef"]=oldProgRef;
				myObject["repMismatchParamCO.oldCrt"]=oldCrt;
				myObject["repMismatchParamCO.repMismatchParamVO.REP_REFERENCE"]=newProgRef;
				myObject["repMismatchParamCO.repMismatchParamVO.CRITERIA_CODE"]=newCrt;
				myObject["repMismatchParamCO.repMismatchParamVO.REP_MISMATCH_ID"]=repMisId;
			   	var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_adjustHashRefCrt?_pageRef="+_pageRef+"&update="+${update}
						$.ajax({
						 url: url,
				         type:"post",
						 dataType:"json",
						 data: myObject,
						 success: function(param)
						 {
						 		 if(param["updates2"]=="")
			   					{
									$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCell',rowid,'oldCrt',newCrt);
									$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCell',rowid,'oldProgRef',newProgRef);	
								}
								else
								{
									_showErrorMsg(crtProgMisDup,error_msg_title,300,100);
									$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.REP_REFERENCE','');
									$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relRep",true);	    
		 							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relCols",true);
		 							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"relCrt",true);	
		 							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowId,"CRT_COL",true);    	    
								}	
						 }
				    });
			}
		}
	}

}

function adjustHash${update}(mismatchType)
{
	rowid 				= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject 			= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
	var oldProgRef 		= myObject["oldProgRef"];
	var oldCrt			= myObject["oldCrt"];
	var newProgRef  	= myObject["repMismatchParamVO.REP_REFERENCE"];
	var newCrt			= myObject["repMismatchParamVO.CRITERIA_CODE"];
	var currentCriteria = myObject["repMismatchParamVO.CRITERIA_CODE"];
	var rowIds 			= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getDataIDs');
	var repMisId 		= myObject["repMismatchParamVO.REP_MISMATCH_ID"];
	
	myObject["repMismatchParamCO.oldProgRef"]=oldProgRef;
	myObject["repMismatchParamCO.oldCrt"]=oldCrt;
	myObject["repMismatchParamCO.repMismatchParamVO.REP_REFERENCE"]=newProgRef;
	myObject["repMismatchParamCO.repMismatchParamVO.CRITERIA_CODE"]=newCrt;
	myObject["repMismatchParamCO.repMismatchParamVO.REP_MISMATCH_ID"]=repMisId;

    //added the condition on repMisId to handle the case where adding a new record
	if((oldProgRef!="" && oldCrt!="" && (oldProgRef!=newProgRef || oldCrt!=newCrt)) || ((oldProgRef=="" || oldCrt=="") && repMisId=="") )
	{
	    if(oldProgRef!=newProgRef)
	    {
	    	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_COLUMN','');
	    	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'CRT_COL','');
	    	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.COLUMN_TYPE','');
	    }
		var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_adjustHashRefCrt?_pageRef="+_pageRef+"&update="+${update}
		+"&mismatchType="+$("#mismatchType_"+_pageRef).val()
			$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			 		 if(param["updates2"]=="")
   					{
						$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCell',rowid,'oldCrt',newCrt);
						$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCell',rowid,'oldProgRef',newProgRef);	
					}
					else
					{
						//inter
						if(mismatchType==1)
						{ 
							_showErrorMsg(crtProgMisDup,error_msg_title,300,100);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.REP_REFERENCE','');
					     	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"relRep",true);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"relCols",true);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"relCrt",true);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"CRT_COL",true);
						}
						//intra
						else
						{
							_showErrorMsg(refAlreadyUsed,error_msg_title,300,100);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_CODE','');
					     	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"relRep",true);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"relCols",true);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"relCrt",true);
							$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid("setCellReadOnly",rowid,"CRT_COL",true);
						}
					}	
			 }
	    });
	}
   	return "0";
}

function saveCriteriaColumnsMis${update}()
{
	 recordsCount = $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getGridParam','records');
		 if(recordsCount==0)
		 {
		 	_showErrorMsg(errorColCrt,error_msg_title,300,100);
		 	return false
		 }
		 else if (recordsCount==1)//criteria column
		 {
		    var currRowId   = $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getGridParam','selrow');
		    curObj 			= $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getRowData',currRowId);
            rowid			=$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
           	myObject 		= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
           	if(currRowId!=null)
           	{
	           	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.COLUMN_TYPE',curObj["repMismatchColumnVO.COLUMN_TYPE"]);
	           	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'repMismatchParamVO.CRITERIA_COLUMN',curObj["TECH_COL_NAME"]);
	          	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'CRT_COL',curObj["repMismatchColumnVO.RELATED_COLUMN"]);	          	
	          	if(${update}==1)
	          	{
	          		if(myObject["repMismatchParamVO.REP_MISMATCH_ID"]!="")
	          		{
	          			$("#reportsMismatchListGridTbl_Id_1_"+_pageRef).jqGrid('setGridRowStatus',rowid,2);
	          		}
	          	}
		 	}
		 	//added to set old values
		 	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'oldCrt',myObject["repMismatchParamVO.CRITERIA_CODE"]);
		 	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'oldProgRef',myObject["repMismatchParamVO.REP_REFERENCE"]);
		 	//end added
		 	return true;
		 }
	 return true;	
}

function saveRelatedColMis${update}()
{ 	
 	var jsonStringUpdates = $("#reportsMismatchCrtList_"+_pageRef).jqGrid('getAllRows');
 	//added to set old values
 	rowid = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
    rowObject = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
 	//end added
 	$("#updateCrtColList_"+_pageRef).val(jsonStringUpdates); 
 	 var url = jQuery.contextPath+ "/path/reportsMismatch/ReportsMismatchListAction_saveRelatedColumnsMismatch.action"
 	 myObject =  $("#crtColListFrm_"+_pageRef).serialize();
 	 $.post(url, myObject , function()
	 {
	 	//added to set old values
		 	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'oldCrt',rowObject["repMismatchParamVO.CRITERIA_CODE"]);
		 	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'oldProgRef',rowObject["repMismatchParamVO.REP_REFERENCE"]);
		//end added
		return true;
	 });	
	  return true; 	 		
}
	
	
	

function addRepMis${update}() {
	var misType=$("#mismatchType_"+_pageRef).val();
	var crtCode=$("#crtCode_"+${update}+"_"+_pageRef).val();
    var companyCode = $("#compCode_"+${update}+"_"+_pageRef).val();
	if(${update}==1)
	{
		$("#reportsMismatchListGridTbl_Id_" + ${update} + "_" + _pageRef).jqGrid('addInlineRow',{'repMismatchParamVO.MISMATCH_TYPE':misType});
	}
	else if(${update}==2)
	{
		$("#reportsMismatchListGridTbl_Id_" + ${update} + "_" + _pageRef).jqGrid('addInlineRow',{'repMismatchParamVO.MISMATCH_TYPE':misType,
		'repMismatchParamVO.CRITERIA_CODE':crtCode,'oldCrt':crtCode,'repMismatchParamVO.COMP_CODE':companyCode});
	}
}

function deleteRepMisCol${update}() {
	rowid    	= $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject    = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);	
    //empty data hashmaps	
		var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_deleteRepMismatchRow.action?update="+${update}+"&_pageRef="+_pageRef+"&mismatchType="+$("#mismatchType_"+_pageRef).val()
			$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			
			 }
	    }); 	
	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('deleteGridRow');
}

function openDelAllRepMis${update}() {
	_showConfirmMsg(reportingDelAllLines, reportingDelAll,function(confirmcChoice, theArgs) {
		if (confirmcChoice) {
			    //sending the grid all rows to the action
				var jsonStringUpdates1 = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getAllRows');//returns rows in the screen
				if(jsonStringUpdates1!='{\"root\":[]}')
				{
				$("#updateRelRep1_"+${update}+"_"+_pageRef).val(jsonStringUpdates1); 	
				var allRowIds = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getDataIDs');
				jQuery.each(allRowIds, function(i, val) {
						jQuery('#reportsMismatchListGridTbl_Id_'+${update}+"_"+_pageRef).jqGrid('setSelection', val);
						$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('deleteGridRow', val);
					});
					
	  //now empty  hashmaps	
		myObject    =  $("#misGridFrm_"+${update}+"_"+_pageRef).serialize();
		var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchListAction_deleteAllRepMismatch.action?mismatchType="+$("#mismatchType_"+_pageRef).val()
			$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {

			 }
	    });}
	//end empty hashmaps
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
}
$(document).ready(function() {						
					$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).subscribe('lineGrdComplete'+${update},function(event, data) {
						var pagerId = "reportsMismatchListGridTbl_Id_"+ ${update}+"_"+_pageRef+"_pager_left";
						cleanSnSession${update}();
						var myGrid = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef);
						myGrid.jqGrid('navButtonAdd',pagerId,{caption : "",title : reportingDelAll,id : "NewButton_"+ _pageRef,
									  buttonicon : 'ui-icon-circle-minus',onClickButton : openDelAllRepMis${update}
									});
							});
					$("#gview_reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef+" div.ui-jqgrid-titlebar").hide();

				});
				
function cleanSnSession${update}()
{
	if(${update}==1)
	{
		var url = jQuery.contextPath+"/path/reportsMismatch/ReportsMismatchMaintAction_cleanSnSession?_pageRef="+_pageRef
		params={};
		params["mismatchType"] = $("#mismatchType_"+_pageRef).val();
						$.ajax({
						 url: url,
				         type:"post",
						 dataType:"json",
						 data: params,
						 success: function(param)
						 {
	
						 }
				    });
   }
}


function saveRelCrt${update}()
{
	var jsonStringUpdates = $("#reportsMismatchRelatedCriteriaGrid_"+_pageRef).jqGrid('getAllRows');
 	//added to set old values
 	rowid = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getGridParam','selrow');
    rowObject = $("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('getRowData',rowid);
 	//end added
 	$("#updateRelCrt_"+_pageRef).val(jsonStringUpdates); 
 	 var url = jQuery.contextPath+ "/path/reportsMismatch/ReportsMismatchListAction_saveRelatedCriteriaMismatch.action"
 	 myObject =  $("#relCrtFrm_"+_pageRef).serialize();
 	 $.post(url, myObject , function()
	 {
	 	//added to set old values
		 	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'oldCrt',rowObject["repMismatchParamVO.CRITERIA_CODE"]);
		 	$("#reportsMismatchListGridTbl_Id_"+${update}+"_"+_pageRef).jqGrid('setCellValue',rowid,'oldProgRef',rowObject["repMismatchParamVO.REP_REFERENCE"]);
		//end added
		return true;
	 });	
	  return true; 	 		
}


</script>