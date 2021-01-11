<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="repScheSnapEmpty_var" 			value="%{getEscText('archive.repScheSnap')}"/>
<ps:set name="repScheSnapEmptyError_var" 		value="%{getEscText('archive.archive')}"/>
<ps:set name="userEmpty_var" 					value="%{getEscText('archive.userEmpty')}"/>
<ps:set name="userError_var" 					value="%{getEscText('archive.user')}"/>
<ps:set name="wrongFromToDate_var" 				value="%{getEscText('tmplProc.checkDatesAlert')}"/>
<ps:set name="startOrEndEmpty_var" 				value="%{getEscText('tmplProc.startOrEndEmpty')}"/>
<ps:set name="missingDBName_var" 				value="%{getEscText('reporting.missingDBName')}"/>
<ps:set name="checkDatesAlert_var" 				value="%{getEscText('tmplProc.checkDatesAlert')}"/>

<%
String connectionListDisp="display: none";
	
%>
<script type="text/javascript">
var repScheSnapEmpty 		= '<ps:property value="repScheSnapEmpty_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var repScheSnapEmptyError 	= '<ps:property value="repScheSnapEmptyError_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var userEmpty 				= '<ps:property value="userEmpty_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var userError 				= '<ps:property value="userError_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var wrongFromToDate 		= '<ps:property value="wrongFromToDate_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var startOrEndEmpty 		= '<ps:property value="startOrEndEmpty_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingDBName			= '<ps:property value="missingDBName_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkDatesAlert 		= '<ps:property value="checkDatesAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function () 
{
	$.struts2_jquery.require("Archive.js", null,jQuery.contextPath + "/path/js/reporting/snapshots/");			
	 rep_archive_readyFunc();
});	
	
	



</script>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

<ps:form id="archiveForm_${_pageRef}" useHiddenProps="true" validate="true">

<ps:collapspanel id='executionDate_${_pageRef}'  key="snapShot.execDate">	

	<table class="headerPortionContent ui-widget-content"  CELLPADDING="0" CELLSPACING="5" border="0">
		<tr>
			<td align="center" nowrap="nowrap">
				<TABLE CELLPADDING="0" CELLSPACING="20" border="0" class="headerPortionContent ui-widget-content" >
					<TR>
						<td><ps:radio name="archiveCO.dateRange" value="1"  onclick="applySmartValidation(this)" 	   
									   list="dateRange[0]" 
									   listKey="VALUE_CODE" 
									   listValue="VALUE_DESC"/></td>
						<td></td>
						<td  colspan="2"><ps:select name="archiveCO.selDate" id="selDate_${_pageRef}" 
									   list="datePeriod" 
									   listKey="VALUE_CODE" 
									   listValue="VALUE_DESC"/></td>
						<td></td>
						<td></td>
					</TR>

					<TR >
						<td colspan="2"><ps:radio name="archiveCO.dateRange" onclick="applySmartValidation(this)" id="dateRange_${_pageRef}"
									  list="dateRange[1]" 
									  listKey="VALUE_CODE" 
									  listValue="VALUE_DESC" />
										</td>
						<td><ps:label key="reporting.fromDate" /></td><td><psj:datepicker id="startRangeDate_${_pageRef}" name="archiveCO.startRangeDate" buttonImageOnly="true"/></td>
						<td><ps:label key="col.toDate" /></td><td><psj:datepicker id="endRangeDate_${_pageRef}" name="archiveCO.endRangeDate" buttonImageOnly="true" onchange="checkDates(this)" /></td>
					</TR>
					
					<TR>
						<td ><ps:label key="archive.location" /></td>
						<td></td>
						<td colspan="2"><ps:select name="archiveCO.archiveLocation" id="archiveCO.archiveLocation_${_pageRef}" 
									   list="archiveLocation" 
									   listKey="VALUE_CODE" 
									   listValue="VALUE_DESC"
									   onchange="showHideCon(this)"/></td>
						<td colspan="2">
						<span id="conSelect_${_pageRef}" style="">
							<ps:select id="connection_${_pageRef}" name="archiveCO.connection" list="connectionsList" listKey="CONN_ID" listValue="CONN_DESC" emptyOption="false"></ps:select>
						</span>
						</td>
					</TR>
					
					
					<TR>
						<td><ps:label key="archive.reportSnapshot" /></td>
						<td></td>
						<td><ps:checkbox name="archiveCO.reportSnapshot" id="reportSnapshot_${_pageRef}"></ps:checkbox></td>
						<td></td>
						<td></td>
					</TR>
					
					<TR>
						
						<td ><ps:label key="archive.reportScheduler" /></td>
						<td></td>
						<td><ps:checkbox name="archiveCO.reportScheduler" id="reportScheduler_${_pageRef}"></ps:checkbox></td>
						<td></td>
						<td></td>
					</TR>
	
				</TABLE>
			</td>
			
			<td align="left" nowrap="nowrap" style="width:100%;" class="headerPortionContent ui-widget-content">
				<ps:url id="urlGrid" action="archiveAction_loadUserLkpGrid" namespace="/path/archive">
				</ps:url>   
					<table width="100%" border="0">
					<tr> 
						<td><ps:radio name="userRadioBut" list="usersList"  value="1" onchange="showHideUserGrid(this)"
									  id="userRadioBut_${_pageRef}"
									  listKey="VALUE_CODE" 
									  listValue="VALUE_DESC"/>
						</td></tr>
					<tr id="userGrid_${_pageRef}" style="" >
						<td width="100%" >	
							<span >
							    <psjg:grid
							    	id="userGridId_${_pageRef}"
							    	dataType="json" 
							    	href="%{urlGrid}" 
							    	gridModel="gridModel"
							    	pager="true" 
							    	rowNum="10"
							    	rowList="10,15,20"
							    	viewrecords="true"
							    	navigator="true"	
							    	navigatorAdd="false" 
							    	navigatorEdit="false"
							    	navigatorDelete="false"
							    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
							    	onCompleteTopics="emptyCrtTrx"
							    	multiselect="true"
							    	
							     >
							      	<psjg:gridColumn name="userId"  id="userId_${_pageRef}" searchoptions= "{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"   index="userId"      colType="text"  title="%{getText('connection.userId')}"      sortable="true"  width="15"  hidden="false"/>
							    </psjg:grid>
							</span> 
						</td>
					</tr>
					</table>
			</td>
		</tr>	
	</table>	



</ps:collapspanel>
	<table class="headerPortionContent ui-widget-content"  CELLPADDING="0" CELLSPACING="5" >
		<TR>
					<TD>
						<psj:a button="true" href="#"
									onclick="archive()"
									id="archive_${_pageRef}">
									<ps:text name="%{getText('archive.archive')}"></ps:text>
								</psj:a>
					</TD>
				</TR>
  </table>
</ps:form>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:url id="urlLogsGrid" action="archiveAction_loadArchiveLogsGrid" namespace="/path/archive">
	</ps:url>   
<table width="100%" border="0">
<tr>
<td width="10%">	
    <psjg:grid
    	id="logsGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlLogsGrid}" 
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="10"
    	rowList="10,15,20"
    	viewrecords="true"
    	navigator="true"	
    	navigatorAdd="false" 
    	navigatorEdit="false"
    	navigatorDelete="false"
    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
    	onCompleteTopics="emptyCrtTrx"
    	
     >
      	<psjg:gridColumn name="irpSnapshotsLogsVO.SNAPSHOT_ID"  id="SNAPSHOT_ID_${_pageRef}" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="SNAPSHOT_ID"      colType="text"  title="%{getText('archive.snapShotId')}"      sortable="true"  width="15"  hidden="false"/>
    	<psjg:gridColumn name="irpSnapshotsLogsVO.ARCHIVE_DATE"  id="ARCHIVE_DATE_${_pageRef}"  search="false" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"   index="ARCHIVE_DATE"      colType="text"  title="%{getText('archive.date')}"      sortable="true"  width="15"  hidden="false"/>
    	<psjg:gridColumn name="irpSnapshotsLogsVO.STATUS"  id="STATUS_${_pageRef}" search="true" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"   index="STATUS"      colType="text"  title="%{getText('archive.status')}"      sortable="true"  width="15"  hidden="true"/>
    	<psjg:gridColumn name="statusDesc"  id="statusDesc_${_pageRef}"  search="false" searchoptions= "{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  index="statusDesc"      colType="text"  title="%{getText('archive.status')}"      sortable="true"  width="15"  hidden="false"/>
    	
    	<psjg:gridColumn name="irpSnapShotsCO.REPORT_NAME"  id="irpSnapShotsCO.REPORT_NAME_${_pageRef}"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"   index="REPORT_NAME"      colType="text"  title="%{getText('reportName')}"      sortable="true"  width="15"  hidden="false"/>
    	<psjg:gridColumn name="irpSnapShotsCO.EXECUTED_BY"  id="irpSnapShotsCO.EXECUTED_BY_${_pageRef}" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  index="EXECUTED_BY"      colType="text"  title="%{getText('connection.userId')}"      sortable="true"  width="15"  hidden="false"/>
    	<psjg:gridColumn name="irpSnapShotsCO.EXECUTION_DATE_STR"  id="irpSnapShotsCO.EXECUTION_DATE_STR_${_pageRef}" searchoptions= "{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"   index="EXECUTION_DATE_STR"      colType="text"  title="%{getText('snapShot.execDate')}"       sortable="true"  width="15"  hidden="false"/>
    	
    	
    </psjg:grid>
</td>
</tr>
</table>

