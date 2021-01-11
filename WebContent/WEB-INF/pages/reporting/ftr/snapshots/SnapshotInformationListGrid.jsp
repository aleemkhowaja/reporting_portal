<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>	
<ps:set name="alreadyGenerated_var" 	value="%{getEscText('snapshots.alreadyGenerated')}"/>
<ps:set name="cannotDeclared_var" 	value="%{getEscText('snapshots.cannotDeclared')}"/>
<ps:set name="notEnabledFileGen_var" 	value="%{getEscText('snapshots.notEnabledFileGen')}"/>
<ps:set name="cannotDiffFreq_var" 	value="%{getEscText('snapshots.cannotDiffFreq')}"/>

<script>
var alreadyGenerated=	'<ps:property value="alreadyGenerated_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var cannotDeclared	=	'<ps:property value="cannotDeclared_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var notEnabledFileGen=	'<ps:property value="notEnabledFileGen_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var cannotDiffFreq = 	'<ps:property value="cannotDiffFreq_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


function changeDeclaredByFunc()
{	rowid 	 = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	$("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('setGridRowStatus',rowid,2);
}

$(document).ready(function () 
	{
			$("#snapshotInformationListGridTbl_Id_"+_pageRef).subscribe('lineGrdComplete', 
						function(event,data) 
						{
							var pagerId = "snapshotInformationListGridTbl_Id_"+_pageRef+"_pager_left";
						 	var myGrid = $("#snapshotInformationListGridTbl_Id_"+_pageRef);
 							myGrid.jqGrid('navButtonAdd',pagerId,{caption:"",title: reportingDelAll, id:"NewButton_"+_pageRef,
							buttonicon :'ui-icon-circle-minus', onClickButton:openDelAllSn });
							}
				);	
				
							
			$("#snapshotInformationListGridTbl_Id_"+_pageRef).subscribe('fillDefaultRepFormat', 
				function(event,data) 
				{
					rowid = (event["originalEvent"])["id"];
					var rowObject =  $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid("getRowData",rowid);
					var myObject = {};
					myObject["repSnapshotInformationCO.repSnapshotParamVO.REP_REFERENCE"]=rowObject["repSnapshotParamVO.REP_REFERENCE"];
					myObject["repSnapshotInformationCO.REPORT_TYPE"]=rowObject["REPORT_TYPE"];
					url = jQuery.contextPath	+ "/path/snapshotParameter/SnapshotInformationMaintAction_retSnpFormatDetails.action";
					$.post(url, myObject , function( param )
				 	{
				 		var snpFormat=param["repSnapshotInformationCO"]["SNP_FORMAT"];
				 		var showHeadFoot=param["repSnapshotInformationCO"]["SHOW_HEAD_FOOT"];
				 		$("#format_"+_pageRef).val(snpFormat);
				 		if(showHeadFoot=="0")
				 		{
				 			$("#noHeadAndFoot_" + _pageRef).attr("checked",true);
				 		}
				 		else
				 		{
				 			$("#noHeadAndFoot_" + _pageRef).attr("checked",false);
				 		}
				 		
				 		
				 		
				 		checkSnpIfCSV(snpFormat);
				 		if(snpFormat=="CSV" || snpFormat=="RDTXT")
				 		{
				 			$("#csvSeparatorId_"+_pageRef).val(param["repSnapshotInformationCO"]["CSV_SEPARATOR"])
				 		}

				 	});
				}
			);	
	});
	
$("#snapshotInformationListGridTbl_Id_"+_pageRef).subscribe('applyMisInfoAudit', function(event, data) {
		applyMisInfoAudit();
	});
	
	
</script>
 <ps:form applyChangeTrack="true" id="snpInfId12_${_pageRef}" name="snpInfGridForm" action="SnapshotInformationListAction_loadSnapshotInformationGrid" namespace="/path/snapshotParameter">
		<ps:url id="urlsnapshotInformationListGrid" escapeAmp="false" action="SnapshotInformationListAction_loadSnapshotInformationGrid" namespace="/path/snapshotParameter">
		   <ps:param name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psjg:grid
			id               ="snapshotInformationListGridTbl_Id_${_pageRef}"
		    href             ="%{urlsnapshotInformationListGrid}"
		    dataType         ="json"
			pager            ="true"	navigator="true" navigatorSearch="false"
			navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			navigatorEdit="false" navigatorAdd="false" navigatorDelete="true"
			caption =" "
			navigatorRefresh ="false" viewrecords="true" 
			delfunc="deleteSnInfRec"
			editinline		 ="true"
			editurl="%{urlsnapshotInformationListGrid}"
			sortable         ="false"
			gridModel        ="gridModel"
		  	shrinkToFit      ="true"
			pagerButtons="false"
		  	disableEditableFocus="true"
		  	onSelectRowTopics="fillDefaultRepFormat"
		  	onEditInlineBeforeTopics = "applyMisInfoAudit">	  	 
		  	 <psjg:gridColumn name="repSnapshotParamVO.REP_ID" 				 index="repSnapshotParamVO.REP_ID" 				 id="repSnapshotParamVO.REP_ID" 			  title="" colType="text" hidden="true"/>
			 <psjg:gridColumn name="repSnapshotParamVO.COMP_CODE" 			 index="repSnapshotParamVO.COMP_CODE"    		 id="repSnapshotParamVO.COMP_CODE"    		  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_REFERENCE" 		 index="repSnapshotParamVO.REP_REFERENCE"    	 id="repSnapshotParamVO.REP_REFERENCE"    	  title="%{getText('repRef_key')}" colType="text" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.ADDITIONAL_REFERENCE" index="repSnapshotParamVO.ADDITIONAL_REFERENCE" id="repSnapshotParamVO.ADDITIONAL_REFERENCE" title="%{getText('snapshots.additionnalReference')}" colType="text" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotInfoVO.DESCRIPTION" width="400" index="repSnapshotInfoVO.DESCRIPTION"    	     id="repSnapshotInfoVO.DESCRIPTION"    	  	  title="%{getText('reporting.description')}" colType="text" editable="true" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SNAPSHOT_FREQUENCY" 	 index="repSnapshotParamVO.SNAPSHOT_FREQUENCY"   id="repSnapshotParamVO.SNAPSHOT_FREQUENCY"   title="%{getText('snapshots.frequency')}" colType="text" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotInfoVO.SNAPSHOT_USER" 	 	 index="repSnapshotInfoVO.SNAPSHOT_USER"   		 id="repSnapshotInfoVO.SNAPSHOT_USER"   	  title="%{getText('snapshots.snapshotUser')}" colType="text" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotInfoVO.SNAPSHOT_DATE" 		 index="repSnapshotInfoVO.SNAPSHOT_DATE" 		 id="repSnapshotInfoVO.SNAPSHOT_DATE" 		  title="%{getText('snapshots.snapshotDate')}" colType="date" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotInfoVO.RETREIVE_DATE" 		 index="repSnapshotInfoVO.RETREIVE_DATE"    	 id="repSnapshotInfoVO.RETREIVE_DATE"    	  title="%{getText('snapshots.retrievalDate')}" colType="date" sortable="true"/>  
		     <psjg:gridColumn name="repSnapshotInfoVO.DECLARED_BY" 			 index="repSnapshotInfoVO.DECLARED_BY"    		 
		     	id="repSnapshotInfoVO.DECLARED_BY"    		  title="%{getText('snapshots.declaringUser')}" colType="liveSearch" sortable="true"
		     	editable="true" searchElement="USER_ID" 
		     	dataAction="${pageContext.request.contextPath}/path/scheduler/scheduler_mailUsersListLkp.action?_pageRef=${_pageRef}"
		     	editoptions="{ dataEvents: [{ type: 'change', fn: function(e) {changeDeclaredByFunc('');} }],  readonly: 'readonly'}"/> 
		     <psjg:gridColumn name="repSnapshotInfoVO.DECLARED_DATE" 		 index="repSnapshotInfoVO.DECLARED_DATE" 		 id="repSnapshotInfoVO.DECLARED_DATE" 		  title="%{getText('snapshots.declarationDate')}" colType="date" editable="true" sortable="true"/>
		     <psjg:gridColumn name="repSnapshotInfoVO.DECLARED_YN" 		 	 index="repSnapshotInfoVO.DECLARED_YN"    		 
			     id="repSnapshotInfoVO.DECLARED_YN"    	  	  title="%{getText('snapshots.declared')}" align="center"    formatter="checkbox" 
			     colType="checkbox" edittype="checkbox" editable="true" formatoptions="{disabled : false}" editoptions="{value:'true:false',align:'middle',dataEvents: [{ type: 'click', fn: function(e) { onChangeDeclYn()}}]}"/>
		     <psjg:gridColumn name="ENABLE_SITCOM_YN" 		 				 index="ENABLE_SITCOM_YN"    	 				 id="ENABLE_SITCOM_YN"    	  				  title="%{getText('snapshots.includeFile')}" hidden="%{hideCol}" align="center" formatter="checkbox" colType="checkbox" edittype="checkbox" editable="true" formatoptions="{disabled : false}"
		     	editoptions="{value:'true:false',align:'middle', dataEvents: [{ type: 'click', fn: function(e) { onChangeIncludeFile()}}]}"/>
		     <psjg:gridColumn name="repSnapshotInfoVO.SITCOM_FILE_ID" 	     index="repSnapshotInfoVO.SITCOM_FILE_ID"   	 id="repSnapshotInfoVO.SITCOM_FILE_ID"   	  title="%{getText('snapshots.viewFile')}" hidden="true" colType="text" formatter="showlink"
				sortable="false"  search="false" editable="false" formatoptions="{baseLinkUrl: 'javascript:',showAction:'viewFile(\"',addParam:'\");'}"/>
			  <psjg:gridColumn name="repSnapshotInfoVO.DESCRIPTION" 	     index="repSnapshotInfoVO.DESCRIPTION"   	 id=""   	  title="%{getText('snapshots.viewFile')}" hidden="%{hideCol}" colType="text" formatter="showlink"
				sortable="false"  search="false" editable="false" formatoptions="{baseLinkUrl: 'javascript:',showAction:'viewFile(\"',addParam:'\");'}"/>	
		     <psjg:gridColumn name="repSnapshotParamVO.SAVE_REP_YN" 		 index="repSnapshotParamVO.SAVE_REP_YN"    		 id="repSnapshotParamVO.SAVE_REP_YN"    	  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_TYPE" 			 index="repSnapshotParamVO.REP_TYPE"    		 id="repSnapshotParamVO.REP_TYPE"    		  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_ENABLE_YN" 	 index="repSnapshotParamVO.SITCOM_ENABLE_YN"     id="repSnapshotParamVO.REP_REFERENCE"    	  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_ORDER"		 index="repSnapshotParamVO.SITCOM_ORDER"   		 id="repSnapshotParamVO.SITCOM_ORDER"   	  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_FORMULA" 		 index="repSnapshotParamVO.SITCOM_FORMULA" 		 id="repSnapshotParamVO.SITCOM_FORMULA" 	  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_MODIFY_ENABLE_YN" index="repSnapshotParamVO.REP_MODIFY_ENABLE_YN" id="repSnapshotParamVO.REP_MODIFY_ENABLE_YN" title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_COMMENTS_YN" 	 index="repSnapshotParamVO.REP_COMMENTS_YN"    	 id="repSnapshotParamVO.REP_COMMENTS_YN"      title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_AUDIT_YN" 		 index="repSnapshotParamVO.REP_AUDIT_YN" 		 id="repSnapshotParamVO.REP_AUDIT_YN" 		  title="" colType="text" hidden="true"/>
			 <psjg:gridColumn name="repSnapshotInfoVO.REP_SNAPSHOT_ID" 		 index="repSnapshotInfoVO.REP_SNAPSHOT_ID"    	 id="repSnapshotInfoVO.REP_SNAPSHOT_ID"    	  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.CREATED_BY" 			 index="repSnapshotParamVO.CREATED_BY" 			 id="repSnapshotParamVO.CREATED_BY" 		  title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="REPORT_TYPE" 							 index="repSnapshotParamVO.REPORT_TYPE" 		 id="repSnapshotParamVO.REPORT_TYPE" 		  title="report type" colType="text" hidden="true"/>
		     <psjg:gridColumn name="SNP_FRQ" 			 index="SNP_FRQ" 			 id="SNP_FRQ" 		  title="SNP_FRQ" colType="text" hidden="true"/>
		    </psjg:grid>
	     </ps:form>