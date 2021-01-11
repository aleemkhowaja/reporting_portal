<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<html>
<head>
<ps:set name="verifySnapshotTitle_var" 		value="%{getEscText('snapshots.verify')}"/>
<ps:set name="verifySnapshot_var" 		value="%{getEscText('snapshots.verifySnapshot')}"/>
<ps:set name="verifiedSnapshot_var"		value="%{getEscText('snapshots.verifiedSnapshot')}"/>
<script type="text/javascript">

var verifySnapshotTitle 		= '<ps:property value="verifySnapshotTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var verifySnapshotMessage		= '<ps:property value="verifySnapshot_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var verifiedSnapshot = '<ps:property value="verifiedSnapshot_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	
	$(document).ready(function() 
	{
		//stopped by haytham for IE
		$.struts2_jquery.require("Snapshots.js", null,
					jQuery.contextPath + "/path/js/reporting/snapshots/");
		$("#SnapShotGrid_"+_pageRef).subscribe('emptyTrx', function(event,data) 
		{
		 $("#SnapShotLogFrm #auditTrxNbr_"+_pageRef).val("")
		});
		
		$("#SnapShotGrid_"+_pageRef).subscribe('retSnpTrxNb', function(event,data) 
		{
				var rowid = (event["originalEvent"])["id"];
				var url = jQuery.contextPath+ "/path/snapShot/snapShotAction_retSnpTrxNb";
				myObject =$("#SnapShotGrid_"+_pageRef).jqGrid('getRowData',rowid);
				params = {};
				params["snpId"] = myObject["SNAPSHOT_ID"];
				params["_pageRef"]=_pageRef
	   			$.get(url, params , function( param )
	 			{
	 				 var trxAudit=param["auditTrxNbr"];
	 				 $("#SnapShotLogFrm #auditTrxNbr_"+_pageRef).val(trxAudit)
				});
		});
		
			
				
						$("#SnapShotGrid_"+_pageRef).subscribe('verifyButtonFn', 
						function(event,data) 
						{
							var pagerId = "SnapShotGrid_"+_pageRef+"_pager_left";

							var myGrid = $("#SnapShotGrid_"+_pageRef);

							
							myGrid.jqGrid('navButtonAdd',pagerId,{caption:"",title: verifySnapshotTitle, id:"VerifyButton_"+_pageRef,
							buttonicon :'ui-icon-circle-check', onClickButton:function(){ 
							_showConfirmMsg(verifySnapshotMessage, verifySnapshotTitle, function(confirmcChoice, theArgs){
				            if(confirmcChoice)
				            { 
				           
				              verifySnapshot()
				            }
			        	   
					       }, {},yes_confirm,no_confirm,300,100);
							} });
							
						}
				);
		
	});


</script>

</head>

<body>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<div id="innerLayoutSnapShot" style="position: relative; height: 100%; width: 100%;">	
<div  class="inner-centerSnapShot"  style="padding:0"     > 
<br />
<br />
<div id="title"
	style="font-family: Tahoma; font-size: 14pt; text-align: center">
</div>
<br>

<ps:form id="SnapShotLogFrm" useHiddenProps="true">
	<table width="100%">
		<tr>
			<td>
			<ps:url var="urlGrid" action="snapShotAction_loadSnapShotsList" namespace="/path/snapShot"></ps:url>
			<ps:param name="_pageRef" value="_pageRef"></ps:param>
			<psjg:grid id="SnapShotGrid_${_pageRef}" 
						gridModel="gridModel"
						dataType="json"
						href="%{urlGrid}" 
						pager="true" 
						navigator="true"
						navigatorSearch="true"
						navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
						navigatorEdit="false" 
						navigatorAdd="false" 
						navigatorDelete="false"
						navigatorRefresh="true" 
						viewrecords="true" rowNum="15"
						rowList="5,10,15,20" 
						sortable="true" 
						onSelectRowTopics="retSnpTrxNb"
						onCompleteTopics="emptyTrx"
						onGridCompleteTopics="verifyButtonFn">

				<psjg:gridColumn name="SNAPSHOT_ID" id="SNAPSHOT_ID" width="300" title="SNAPSHOT_ID" colType="number" editable="false" sortable="false" index="SNAPSHOT_ID" hidden="true"/>
				<psjg:gridColumn name="CONN_ID" id="CONN_ID" width="300" title="CONN_ID" colType="number" editable="false" sortable="false" index="CONN_ID" hidden="true"/>
				<psjg:gridColumn name="IS_DB" id="IS_DB" width="300" title="IS_DB" colType="number" editable="false" sortable="false" index="IS_DB" hidden="true"/>
				<psjg:gridColumn name="FILE_NAME" id="FILE_NAME" width="300" title="FILE_NAME" colType="text" editable="false" sortable="false" index="FILE_NAME" hidden="true"/>
				<psjg:gridColumn name="REPORT_NAME" id="REPORT_NAME" width="300" title="%{getText('reportName')}" colType="text" editable="false" sortable="true" index="REPORT_NAME"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
				<psjg:gridColumn name="APP_NAME" id="APP_NAME" width="70" title="%{getText('connection.appName')}" colType="text" editable="false" sortable="true" index="APP_NAME"/>
				<psjg:gridColumn name="REPORT_FORMAT" id="REPORT_FORMAT" width="70" title="%{getText('reportFormat')}" colType="text" editable="false" sortable="true" index="REPORT_FORMAT"  hidden="true"/>
				<psjg:gridColumn name="REPORT_FORMAT_TRANS" id="REPORT_FORMAT_TRANS" width="70" title="%{getText('reportFormat')}" colType="text" editable="false" sortable="true" index="REPORT_FORMAT_TRANS"  />
				<psjg:gridColumn name="EXECUTION_DATE_STR" id="EXECUTION_DATE_STR" width="150" title="%{getText('snapShot.execDate')}" colType="text" editable="false" sortable="true" index="EXECUTION_DATE_STR" />
				<psjg:gridColumn name="EXECUTED_BY" id="EXECUTED_BY" width="150" title="EXECUTED_BY" colType="text" editable="false" sortable="true" index="EXECUTED_BY" hidden="true"/>				
				<psjg:gridColumn name="REPORT_NAME" index="REPORT_NAME" title="%{getText('snapShot.preview')}"
								colType="text" editable="false" sortable="false"  search="false"
								formatter="showlink" width="300"
    							formatoptions="{baseLinkUrl: 'javascript:',showAction:'viewSnapshots(\"',addParam:'\");'}" />
    			<psjg:gridColumn name="SNAPSHOT_REF" id="SNAPSHOT_REF" title="SNAPSHOT_REF" colType="number" editable="false" sortable="false" index="SNAPSHOT_REF" hidden="true"/>				
			</psjg:grid>
			<br></br>
			</td>

		</tr>
	</table>
</ps:form>
<ps:form id="snapShotsFrm" namespace="/path/snapShot" action="snapShotAction_previewSnapshot.action" target="_blank">
</ps:form>


 </div>
</div>
</body>
<script type="text/javascript"> 
    $("#SnapShotGrid_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>