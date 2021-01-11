<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 
<ps:set name="orderDupSn_var" 				value="%{getEscText('snapshots.orderDup')}"/>
<ps:set name="repFreqDup_var" 				value="%{getEscText('snapshots.repFreqDup')}"/>
<ps:set name="snapshotsDuplication_var" 	value="%{getEscText('snapshots.duplication')}"/>
<ps:set name="reportingDelAllLines_var" 	value="%{getEscText('reporting.deleteAllLines')}"/>
<ps:set name="reportingDelAll_var" 			value="%{getEscText('reporting.deleteAll')}"/>
<ps:set name="rowHasSavedSn_var" 			value="%{getEscText('snapshots.recordHasSnInformation')}"/>
<ps:set name="missingAsOf_var" 			value="%{getEscText('snapshots.missingAsOF')}"/>

<script>
snapshotFreqCmbUrl="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotParameterListAction_loadSnapshotfrequencyCmb.action?_pageRef="+_pageRef;
	var orderDupSn 					= '<ps:property value="orderDupSn_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var repFreqDup 					= '<ps:property value="repFreqDup_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var snapshotsDuplication 		= '<ps:property value="snapshotsDuplication_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var reportingDelAllLines 		= '<ps:property value="reportingDelAllLines_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var reportingDelAll 			= '<ps:property value="reportingDelAll_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var rowHasSavedSn				= '<ps:property value="rowHasSavedSn_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var missingAsOf					= '<ps:property value="missingAsOf_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


 			$("#snapshotParameterListGridTbl_Id_"+_pageRef).subscribe('lineGrdComplete', 
						function(event,data) 
						{
							var pagerId = "snapshotParameterListGridTbl_Id_"+_pageRef+"_pager_left";
						 	var myGrid = $("#snapshotParameterListGridTbl_Id_"+_pageRef);
						 	cleanSnSession();
 							myGrid.jqGrid('navButtonAdd',pagerId,{caption:"",title: reportingDelAll, id:"NewButton_"+_pageRef,
							buttonicon :'ui-icon-circle-minus', onClickButton:openDelAllSn });
							}
				);	

	$("#snapshotParameterListGridTbl_Id_"+_pageRef).subscribe('retReadOnlyParams', function(event, data) {
		retReadOnlyParams();
	});
function retReadOnlyParams()
{
		var rowId 			= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');	
		var myObject     	= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowId);
		if(myObject["IS_FTR_FCR"]==0)
		{
		 	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid("setCellReadOnly",rowId,"PARAM_NAME",true);
		 	$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid("setCellReadOnly",rowId,"repSnapshotParamVO.SAVE_REP_YN",true);
		}
		else if(myObject["IS_FTR_FCR"]==1)
		{
			$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid("setCellReadOnly",rowId,"PARAM_NAME",false);
			$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid("setDialogCellReadOnlyText",rowId,"PARAM_NAME",true);
			$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid("setCellReadOnly",rowId,"repSnapshotParamVO.SAVE_REP_YN",false);
		}
		
		var url = jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_applySnParamAudit?_pageRef="+_pageRef
	    rowid = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	    myObject = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',rowid);
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


function openDelAllSn()
{
	_showConfirmMsg(reportingDelAllLines,reportingDelAll, function(confirmcChoice, theArgs)
	{
		if(confirmcChoice)
		{		           	
	            /*$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('clearGridData');*/
	           	//var rows = $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','colModel');
				/*for (var i =0;i<allRowIds.length;i++)
	           	{
	           		llRowId=allRowIds[i];
	           		confirm($("#snapshotParameterModifiedColumnGridTbl_Id_"+_pageRef).jqGrid('getRowData',llRowId))
	 				$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow',llRowId); 
	 			}*/
				//$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('delRowData',val); 
	           	var allRowIds =  $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getDataIDs');
	           	var rowNotDeleted=false;
	           	jQuery.each(allRowIds, function(i, val) 
	           	{
	           		jQuery('#snapshotParameterListGridTbl_Id_'+_pageRef).jqGrid('setSelection',val); 
					myObject 	= $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('getRowData',val);
					url = jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_checkSnInformation?_pageRef="+_pageRef
						$.ajax({
						 url: url,
				         type:"post",
						 dataType:"json",
						 async:false,
						 data: myObject,
						 success: function(param)
						 {
							if(param["updates2"]=="0")	
							{
								$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('deleteGridRow',val); 
							}
							else
							{
								rowNotDeleted=true;
							}
						 }
						});
						
	           	});
	           	if(rowNotDeleted)
	           	{
	           		_showErrorMsg(rowHasSavedSn,error_msg_title,300,100);
	           	}
		}
	}, {}, yes_confirm, no_confirm, 300, 100);
} 
</script>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

<div id="innerLayoutSnp" style="overflow-x:hidden;overflow-y:auto" >	
	<ps:collapsgroup id='snpParamCollapsGrp_${_pageRef}'>
			<ps:collapspanel id='snapshotParameterListMaintDiv_id_${_pageRef}'  key="snapshots.fileSetup">
				   <%@include file="SnapshotParameterMaint.jsp"%>
			</ps:collapspanel>
	
	
			<ps:collapspanel id='snpParamLstCollaps_${_pageRef}'  key="snapshots.Paranmeters">
				 <div id="snapshotGrid_<ps:property value="_pageRef" escapeHtml="true"/>">
				  <%@include file="SnapshotParameterListGrid.jsp"%>
				  </div>
			</ps:collapspanel>
			
	</ps:collapsgroup>
<div>
		<ptt:toolBar  id="snapshotBar${_pageRef}"  > 
			<psj:submit type="button" button="true" buttonIcon="ui-icon-disk" onclick="submitFormSn()" >
				<ps:text name="reporting.save"></ps:text>
			</psj:submit>
		</ptt:toolBar>
</div>
<script  type="text/javascript">
    $.struts2_jquery.require("SnapshotParameterList.js,SnapshotParameterMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/snapshots/");
    $("#gview_snapshotParameterListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
    
    $("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid(
	'setGridParam',
	{
		url :jQuery.contextPath+"/path/snapshotParameter/SnapshotParameterListAction_loadSnapshotParameterGrid?_pageRef="+_pageRef
	}).trigger("reloadGrid");
</script>
