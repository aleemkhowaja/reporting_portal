<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>






<div>
<ps:url id="urlMisProc" action="repMismProctAction_loadMismatchProcessGrid" namespace="/path/reportsMismatch" escapeAmp="false">
	<ps:param name="misProcCO.TYPE" value="misProcCO.TYPE"></ps:param>
	<ps:param name="misProcCO.REP_MISMATCH_ID" value="misProcCO.REP_MISMATCH_ID"></ps:param>
	<ps:param name="misProcCO.CRITERIA" value="misProcCO.CRITERIA"></ps:param>
</ps:url>

<psjg:grid id="misProcGridId_MISMATCH002" 
	dataType="json" 
	href="%{urlMisProc}"
	gridModel="gridModel" 
	pager="false" 
	rowNum="30" 
	rowList="30,35,40,45"
	viewrecords="true" 
	 multiselect="true" 
	>
	<psjg:gridColumn name="CRITERIA" id="CRITERIA" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
		index="CRITERIA" colType="text" title="%{getText('criteria.criteriaTitle')}"
		sortable="false" search="false"  hidden="%{hideCrt}" key="%{hideProgRef}"/>
		
	<psjg:gridColumn name="PROG_REF" id="PROG_REF"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
		index="PROG_REF" colType="text" title="%{getText('progRef')}"
		sortable="false" hidden="%{hideProgRef}" key="%{hideCrt}"/>
		
	<psjg:gridColumn name="CRITERIA_COLUMN" id="CRITERIA_COLUMN"
		searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" index="CRITERIA_COLUMN"
		colType="text" title="CRITERIA_COLUMN" sortable="false" hidden="true"/>
		
	
</psjg:grid>
</div>
