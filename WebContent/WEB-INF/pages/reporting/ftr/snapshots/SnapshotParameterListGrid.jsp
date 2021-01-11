<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>	

<script>
function emptyParametersMap()
{
	if(rowid!=null)
	{
		$("#snapshotParameterListGridTbl_Id_"+_pageRef).jqGrid('setCellValue',rowid,'PARAM_NAME'," ");
	}
}
</script>
	<ps:hidden name="btrControlCO.sitcomEnableYn" id="visibilityParam_${_pageRef}"></ps:hidden>
	 <ps:form applyChangeTrack="true" id="snpFrmId12_${_pageRef}" name="snpGridForm" action="SnapshotParameterListAction_loadSnapshotParameterGrid" namespace="/path/snapshotParameter">
		<ps:url id="urlsnapshotParameterListGrid" escapeAmp="false" action="SnapshotParameterListAction_loadSnapshotParameterGrid" namespace="/path/snapshotParameter">
		   <ps:param name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psjg:grid
			id               ="snapshotParameterListGridTbl_Id_${_pageRef}"
		    href             =""
		    dataType         ="json"
			pager            ="true"	navigator="true" navigatorSearch="false"
			navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			navigatorEdit="false" navigatorAdd="true" navigatorDelete="true"
			caption =" "
			navigatorRefresh ="true" viewrecords="true" 
			delfunc="deleteSnCol"
			editinline		 ="true"
			addfunc="addSnParam"
			editurl="%{urlsnapshotParameterListGrid}"
			sortable         ="false"
			gridModel        ="gridModel"
		  	shrinkToFit      ="false"
		    pagerButtons="false"
		  	disableEditableFocus="true" height="340"
		  	onCompleteTopics="lineGrdComplete"
		  	onEditInlineBeforeTopics = "retReadOnlyParams">
		  	 <psjg:gridColumn name="progRefOld" index="progRefOld" id="progRefOld" title="" colType="text" hidden="true"/>
		  	 <psjg:gridColumn name="appName" index="appName" id="appName" title="" colType="text" hidden="true"/>		  	 
			 <psjg:gridColumn name="freqOld" 	index="freqOld"    id="freqOld"    title="" colType="text" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_ID" index="repSnapshotParamVO.REP_ID" id="REP_ID" title="" colType="text" hidden="true"/>
			 <psjg:gridColumn name="repSnapshotParamVO.COMP_CODE" index="repSnapshotParamVO.COMP_CODE" id="COMP_CODE" title="" colType="text" hidden="true" search="false"/> 
		     <psjg:gridColumn name="repSnapshotParamVO.REP_REFERENCE" id="REP_REFERENCE" index="repSnapshotParamVO.REP_REFERENCE" title="%{getText('repRef_key')}" editable="true" 
		        sortable="false" colType="liveSearch" searchElement="PROG_REF"
		        		        dataAction="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_retReports.action?_pageRef=${_pageRef}&updates=true"
		        resultList="PROG_REF:lookuptxt_REP_REFERENCE,FTR_REP_YN:IS_FTR_FCR,APP_NAME:appName" 
		        editoptions="{ dataEvents: [{ type: 'change', fn: function(e) {changeSnFunc('');emptyParametersMap();retReadOnlyParams(); } }],  readonly: 'readonly'}"/>    
		     <psjg:gridColumn name="repSnapshotParamVO.ADDITIONAL_REFERENCE" editoptions="{maxlength : 15}" index="repSnapshotParamVO.ADDITIONAL_REFERENCE" id="ADDITIONAL_REFERENCE" title="%{getText('snapshots.additionnalReference')}"	colType="text" hidden="false" search="false" editable="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SAVE_REP_YN" index="repSnapshotParamVO.SAVE_REP_YN" id="SAVE_REP_YN" title="%{getText('snapshots.endis')}" formatter="checkbox" colType="checkbox" align="center" edittype="checkbox" editable="true" formatoptions="{disabled : false}"  search="false"/>          
		     <psjg:gridColumn name="repSnapshotParamVO.SAVE_REP_DETAILS_YN" index="repSnapshotParamVO.SAVE_REP_DETAILS_YN" id="SAVE_REP_DETAILS_YN" title="%{getText('snapshots.endisdetails')}"				formatter="checkbox" 	colType="checkbox" align="center" edittype="checkbox" editable="true"  formatoptions="{disabled : false}"  search="false"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SNAPSHOT_FREQUENCY" index="repSnapshotParamVO.SNAPSHOT_FREQUENCY" id="SNAPSHOT_FREQUENCY" title="%{getText('snapshots.frequency')}" colType="select" edittype="select" editable="true" sortable="false" search="false" formatter="select" editoptions="{ value:function() {  return loadCombo(snapshotFreqCmbUrl,'snapshotFreqCmb', 'VALUE_CODE', 'VALUE_DESC');}, dataEvents: [{ type: 'change', fn: function(e) {changeSnFunc('');} }]}"/>		         
		     <psjg:gridColumn name="formula" dialogUrl="/path/snapshotParameter/SnapshotParameterListAction_openFormulaDialog?_pageRef=${_pageRef}" 
		     	dialogOptions="{close: function (){ $(this).empty();} , autoOpen: false, height:700,title:'%{getText('snapshots.computationExpr')}' , width:1050 ,modal: true, buttons: { '%{getText('reporting.ok')}': function(){saveFormulaVal();$( this ).dialog( 'close' );$( this ).remove()},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }" 
		     	index="formula"  hidden="%{hideFields}"
		     	colType="dialog" title="%{getText('snapshots.fileFormula')}" editable="true" align="center" sortable="false"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_ENABLE_YN" 	index="repSnapshotParamVO.SITCOM_ENABLE_YN"	id="SITCOM_ENABLE_YN"  		title="%{getText('snapshots.endisFile')}" 				formatter="checkbox" 	colType="checkbox" hidden="%{hideFields}" align="center" edittype="checkbox" editable="true"  formatoptions="{disabled : false}"  search="false"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_ORDER" 	editoptions="{maxlength : 4, dataEvents: [{ type: 'change', fn: function(e) {changeSnFunc('fileOrder');} }]}"	index="repSnapshotParamVO.SITCOM_ORDER"	id="SITCOM_ORDER"  			title="%{getText('snapshots.fileOrder')}" 					colType="number" hidden="%{hideFields}" minValue="0" search="false" editable ="true" />
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_FORMULA" index="repSnapshotParamVO.SITCOM_FORMULA" id="repSnapshotParamVO.SITCOM_FORMULA" title="formula hidden" colType="text" align="center" editable="true" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_FORMULA_HEADER" index="repSnapshotParamVO.SITCOM_FORMULA_HEADER" id="repSnapshotParamVO.SITCOM_FORMULA_HEADER" title="formula header hidden" colType="text" align="center" editable="true" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.SITCOM_FORMULA_FOOTER" index="repSnapshotParamVO.SITCOM_FORMULA_FOOTER" id="repSnapshotParamVO.SITCOM_FORMULA_FOOTER" title="formula footer hidden" colType="text" align="center" editable="true" hidden="true"/>
		     <psjg:gridColumn name="repSnapshotParamVO.REP_MODIFY_ENABLE_YN" index="repSnapshotParamVO.REP_MODIFY_ENABLE_YN"	id="REP_MODIFY_ENABLE_YN"  	title="%{getText('snapshots.endisModify')}" 				formatter="checkbox" 	colType="checkbox" align="center" edittype="checkbox" editable="true"  formatoptions="{disabled : false}"  search="false"/>
		     <psjg:gridColumn name="modCol" dialogUrl="/path/snapshotParameter/SnapshotParameterListAction_openModifColDialog?_pageRef=${_pageRef}" 
		     	dialogOptions="{close: function (){ $(this).empty();}  ,autoOpen: false, height:600,title:'%{getText('snapshots.modifiedColumn')}' , width:900 ,modal: true, buttons: { '%{getText('reporting.ok')}': function(){saveModCols();$( this ).dialog( 'close' );$( this ).remove()},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }" 
		     	index="modCol" 
		     	colType="dialog" title="%{getText('snapshots.modifiedColumn')}" editable="true" align="center" sortable="false" />
		     <psjg:gridColumn name="drilCol" index="drilCol" title="%{getText('snapshots.drillDownColumn')}"
		     	dialogUrl="/path/snapshotParameter/SnapshotParameterListAction_openDrilColDialog?_pageRef=${_pageRef}" 
		     	dialogOptions="{close: function (){ $(this).empty();} , autoOpen: false, height:600,title:'%{getText('snapshots.drillDownColumn')}' , width:900 ,modal: true, buttons: { '%{getText('reporting.ok')}': function(){saveDrilCols();$( this ).dialog( 'close' );$( this ).remove()},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }" 
		     	colType="dialog" width="150" editable="true" align="center" sortable="false"
		      />        
		     <psjg:gridColumn name="repSnapshotParamVO.REP_AUDIT_YN" index="repSnapshotParamVO.REP_AUDIT_YN" id="REP_AUDIT_YN" title="%{getText('snapshots.endisAudit')}" formatter="checkbox" colType="checkbox" align="center" edittype="checkbox" editable="true"  formatoptions="{disabled : false}"  search="false"/>  	
		     <psjg:gridColumn name="repSnapshotParamVO.REP_COMMENTS_YN" index="repSnapshotParamVO.REP_COMMENTS_YN" id="REP_COMMENTS_YN" title="%{getText('snapshots.commentsMandatory')}" formatter="checkbox" colType="checkbox" align="center" edittype="checkbox" editable="true"  formatoptions="{disabled : false}"  search="false"/>        		   
		     <psjg:gridColumn name="repSnapshotParamVO.CREATED_BY" index="repSnapshotParamVO.CREATED_BY" id="repSnapshotParamVO.CREATED_BY" title="" colType="text" align="center" hidden="true"  search="false"/>        		     
		     <psjg:gridColumn name="PARAM_NAME" id="PARAM_NAME" index="PARAM_NAME" title="%{getText('snapshots.asOfDateParameter')}" 
		     	editable="true" sortable="false" colType="liveSearch" searchElement="PARAM_NAME"
		     	paramList="repSnapshotParamVO.REP_REFERENCE:repSnapshotParamVO.REP_REFERENCE"  
		        dataAction="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotParameterListAction_repParamLookup"
		        editoptions="{ dataEvents: [{ type: 'change', fn: function(e) {changeAsOfDateFunc('');} }],  readonly: 'readonly'}"/>    
		     <psjg:gridColumn name="IS_FTR_FCR"  index="IS_FTR_FCR" id="IS_FTR_FCR" title="IS_FTR_FCR"	colType="text" hidden="true" search="false" editable="true"/>  
     </psjg:grid>
   </ps:form>