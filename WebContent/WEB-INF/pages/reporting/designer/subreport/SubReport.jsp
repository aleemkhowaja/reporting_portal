<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="subReportWarning_var"  value="%{getEscText('upDown.subReportWarning')}"/>

<script type="text/javascript">
var subReportWarning = '<ps:property value="subReportWarning_var"  escapeHtml="false" escapeJavaScript="true"/>'
</script>

	<ps:url id="urlGrid" action="subrep_loadSubrepListUD" namespace="/path/designer">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="subRepGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true" 
    	viewrecords="true"
    	pagerButtons="false"
    	editinline="true"
		editurl="%{urlGrid}"
    	navigator="false"
    	>
    	
    	<psjg:gridColumn name="PARENT_REP_NAME"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="PARENT_REP_NAME"      colType="text"  title="%{getText('subRep.parentRepName')}"  sortable="true"  width="25"  hidden="false"/>
    	<psjg:gridColumn name="irpSubReportVO.REPORT_ID" id="REPORT_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="REPORT_ID"      colType="number"  title="%{getText('subRep.repId')}" sortable="true"  width="25"  hidden="false" editable="false"/>

    	<psjg:gridColumn id="SUB_REPORT_NAME" index="SUB_REPORT_NAME" name="SUB_REPORT_NAME"  
			     	 	 editoptions="{ readonly: 'readonly'}"
			     	 	 title="%{getText('subRep.name')}" 
			     	 	 editable="true" 
			     	 	 sortable="false" 
			     	 	 colType="liveSearch" searchElement="REPORT_NAME"
			     	 	 dataAction="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action"
			     	 	 paramList="parentRepId:PARENT_REPORT_ID,_pageRef:_pageRef"  
			     	 	 resultList="REPORT_ID:irpSubReportVO.REPORT_ID" 
			     	 	 width="25"/> 
		<psjg:gridColumn name="PARENT_REPORT_ID" id="PARENT_REPORT_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="PARENT_REPORT_ID"      colType="number"  title="PARENT_REPORT_ID"      sortable="true"  width="25"  hidden="true" editable="false"/>	     	 	  	 	 
    	<psjg:gridColumn name="irpSubReportVO.SUB_REPORT_EXPRESSION" id="SUB_REPORT_EXPRESSION" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="SUB_REPORT_EXPRESSION"      colType="text"  title="%{getText('subRep.subRepExp')}"       sortable="true"  width="25"  hidden="false" editable="false"/>	
    </psjg:grid>
  
 <ps:form id="subRepForm_${_pageRef}" action="subrep_saveSubRepMainUD" namespace="/path/designer">
	<ps:hidden name="updates" id="updates_${_pageRef}"></ps:hidden>
</ps:form>   
    
    
    
    