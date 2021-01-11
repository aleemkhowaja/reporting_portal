<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 

<ps:set name="linkParamsTitle_var" 		value="%{getEscText('reporting.linkParams')}"/>
<ps:set name="paramsTitle_var" 			value="%{getEscText('proc.params')}"/>
<ps:set name="paramsOk_var" 			value="%{getEscText('reporting.ok')}"/>
<ps:set name="paramsCancel_var" 		value="%{getEscText('reporting.cancel')}"/>
<ps:set name="missingFe_var" 			value="%{getEscText('reporting.missing')}"/>

<html>
<script type="text/javascript">
	
	var linkParamsTitle 		= '<ps:property value="linkParamsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsTitle 			= '<ps:property value="paramsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsOk 				= '<ps:property value="paramsOk_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsCancel 			= '<ps:property value="paramsCancel_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var missingFe 				= '<ps:property value="missingFe_var"  escapeHtml="false" escapeJavaScript="true"/>'
	
     $(document).ready(
	 function() {
			$.struts2_jquery.require("HyperlinksMaint.js", null,jQuery.contextPath + "/path/js/reporting/hyperlinks/");
					rep_hypMaint_ready();
		});
  
</script>

	<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<div>		
	<ps:form applyChangeTrack="true" id="hypMaintFrmTrackId_${_pageRef}" name="hypMaintFrmTrack" action="hyperLinks_loadHyperlinksGrid" namespace="/path/designer">			 
	<ps:url id="urlGrid" action="hyperLinks_loadHyperlinksGrid" namespace="/path/designer">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="hypLinkGrid_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="20"
    	delfunc="deleteHyperlink"
    	addfunc="addNewHyperlink"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	navigator="true"	
    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true,multipleSearch: true}"
    	navigatorAdd="true" 
    	navigatorEdit="false" 
    	editinline="true"
		editurl="%{urlGrid}"
		onSelectRowTopics="retTrxNb"
		onCompleteTopics="emptyParamsMap"
		onEditInlineBeforeTopics="disableLiveSearch"
     >
     

		<psjg:gridColumn name="hyperlinkVO.REPORT_ID"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="REPORT_ID"      colType="number"  title="%{getText('reportId')}"      sortable="true"  hidden="true"/>
 		<psjg:gridColumn id="REPORT_NAME" index="REPORT_NAME" name="REPORT_NAME"   
     	 	title="%{getText('reportName')}" editable="true" sortable="true"
     	 	editoptions="{ dataEvents: [{ type: 'change', fn: function(e) { emptyRepDetails() } }],  readonly: 'readonly'}"
     	 	colType="liveSearch" searchElement="REPORT_NAME" 
     	 	dataAction="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action" 
     	 	resultList="REPORT_ID:hyperlinkVO.REPORT_ID" />     
      	     
      	       	
      	<psjg:gridColumn name="SAVE_FLAG"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="SAVE_FLAG"      colType="text"  title="SAVE_FLAG"      sortable="true"   hidden="true"/>
      	<psjg:gridColumn name="hyperlinkVO.FIELD_INDEX"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" editable="true"  index="FIELD_INDEX"      colType="number"  title="FIELD_INDEX"      sortable="true"   hidden="true"/>
       	<psjg:gridColumn id="COLUMN_NAME" index="COLUMN_NAME" name="hyperlinkVO.COLUMN_NAME"   
     	 	editoptions="{ readonly: 'readonly'}"
     	 	title="%{getText('hyperLink.colName')}" editable="true" sortable="false" 
     	 	colType="liveSearch" searchElement="FIELD_ALIAS" paramList="updates:hyperlinkVO.REPORT_ID"
     	 	dataAction="${pageContext.request.contextPath}/path/designer/queryDesign_retColumnsByRepId.action" 
     	 	resultList="index:hyperlinkVO.FIELD_INDEX" />  
       
       	<psjg:gridColumn name="hyperlinkVO.LINKED_REPORT_ID" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" editable="true" index="LINKED_REPORT_ID"    colType="number"    title="linked repId"    hidden="true"/>
       	<psjg:gridColumn id="LINKED_REP_NAME" index="LINKED_REP_NAME" name="LINKED_REP_NAME"   
     	 	editoptions="{ readonly: 'readonly'}"
     	 	title="%{getText('hyperLink.linkedRepName')}" editable="true" sortable="true" 
     	 	colType="liveSearch" searchElement="REPORT_NAME" 
     	 	dataAction="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action" 
     	 	resultList="REPORT_ID:hyperlinkVO.LINKED_REPORT_ID" />  
       
       	<psjg:gridColumn  name="HYPERLINK_PARAMS" formatter="openHypLnkParams" index="HYPERLINK_PARAMS" colType="text" title="%{getText('proc.params')}"  editable="false" width="150" align="center" sortable="false" search="false"/>
       	
    </psjg:grid>
    </ps:form>
  </div>
    
<div id="hypParamsGridDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>

 <table align="right" id="hypButBarId_${_pageRef}">
	 <tr>
		 <td >
		 		<psj:submit button="true" onclick="saveHyperlinks()" >
					<ps:text name="reporting.save"></ps:text>
				</psj:submit>
		 </td>
	 </tr>
 </table>

<ps:form id="hypParamsForm_${_pageRef}" action="hyperLinks_saveHypParams" namespace="/path/designer">
	<ps:hidden name="updates" id="updates_${_pageRef}"></ps:hidden>
</ps:form>

<ps:form id="hypForm_${_pageRef}" action="hyperLinks_saveHyperlinks" namespace="/path/designer" useHiddenProps="true">
	<ps:hidden name="updates" id="updateHyp_${_pageRef}"></ps:hidden>
</ps:form>

</html>
<script type="text/javascript"> 
    $("#hypLinkGrid_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>