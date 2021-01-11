<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>
<ps:set name="checkDatesAlert_var" 				value="%{getEscText('tmplProc.checkDatesAlert')}"/>
<ps:set name="processSuccess_var" 				value="%{getEscText('reporting.autoImpSuccess')}"/>
<ps:set name="processFailed_var" 				value="%{getEscText('tmplProc.failed')}"/>
<ps:set name="procValidDate_var" 		    value="%{getEscText('reporting.enterValidDate')}"/>
<ps:set name="noReportToProcess_var" 		    value="%{getEscText('reporting_noreporttoprocess')}"/>

<script type="text/javascript">

var processSuccess 			= '<ps:property value="processSuccess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var processFailed			= '<ps:property value="processFailed_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkDatesAlert 			= '<ps:property value="checkDatesAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var procValidDate			= '<ps:property value="procValidDate_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var noReportToProcess			= '<ps:property value="noReportToProcess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function() {		
						$.struts2_jquery.require("SchedulerDateProcessMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/schedulerdateprocess/");					
							$("#schedulerDateProcessMaintFormId_"+_pageRef).processAfterValid("schedulerDateProcessMaint_processSubmit",{});
						});
</script>

<ps:form useHiddenProps="true" id="schedulerDateProcessMaintFormId_${_pageRef}"  namespace="/path/schedulerDateProcess"  method="POST">
<ps:set name="ivcrud_${_pageRef}" value="iv_crud" />
<ps:set name="checkDatesAlert_var" 				value="%{getEscText('tmplProc.checkDatesAlert')}"/>

<table width="100%" cellpadding="0" cellspacing="20">
<tr>
			<td align="right" width="10%"><ps:label key="sch.id"  id="schedIdLbl"/></td>
			
			<td width="30%" colspan ="2">
			<ps:hidden id = "selSchedId_${_pageRef}" name ="mode"></ps:hidden>
							<psj:livesearch 
						    		  id            ="schedSearch_${_pageRef}" 
				                      name			="SCHED_ID"
				                      multiSelect 	= "true"
				                      onOk="rep_schdDateProcess_retrieveReports()"
				                      multiResultInput ="selSchedId_${_pageRef}"
				                      selectColumn ="SCHED_ID"		  
				                      actionName="${pageContext.request.contextPath}/path/schedulerDateProcess/SchedulerDateProcessMaintAction_loadSchedLookup"
				                      searchElement="SCHED_ID"  autoSearch="false"  minValue="0" mode="number"  maxlength="12" 
				                      parameter="SCHED_ID:lookuptxt_SCHED_ID_${_pageRef}"
				                      resultList="SCHED_ID:lookuptxt_SCHED_ID"
				                     
				                    ></psj:livesearch>				    
			</td>
		</tr>
					<tr>
						<td align="right" width="10%">
							<ps:label value="%{getText('tmplProc.procType')}" id="procTypeLabel"/>
						</td>
				<td width="30%" colspan ="2" >
							<ps:select list="procTypeArrList" listKey="VALUE_CODE"
								listValue="VALUE_DESC" name="schedulerDateProcessCO.procType"
								id="procTypeComboId_${_pageRef}" onchange="rep_schdDateProcess_retrieveReports()"
								parameter="updates:procTypeComboId_${_pageRef}"
								dependency="procTypeComboId_${_pageRef}:updates"
			    				dependencySrc="${pageContext.request.contextPath}/path/schedulerDateProcess/SchedulerDateProcessMaintAction_showHidePeriodicDates"/>
						</td>

						<td align="right" width="10%">
							<ps:label value="%{getText('tmplProc.asOfDate')}" id="asOfDateLabel" />
						</td>
						<td >
							<psj:datepicker width="40%" name="schedulerDateProcessCO.asOfDate" id="schedAsOfDate"
								buttonImageOnly="true"></psj:datepicker>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">
							<ps:label value="%{getText('reporting.fromDate')}" id="fromDateLabel" />
						</td>
						<td width="30%" colspan ="2">
							<psj:datepicker name="schedulerDateProcessCO.fromDate" id="schedFromDate"
								disabled="true" onchange="rep_schdDateProcess_checkDates(this)"
								buttonImageOnly="true"></psj:datepicker>
						</td>

						<td align="right" width="10%">
							<ps:label value="%{getText('tmplProc.toDate')}" id="toDateLabel"/>
						</td>
						<td width="40%" >
							<psj:datepicker name="schedulerDateProcessCO.toDate" id="schedToDate"
								disabled="true" onchange="rep_schdDateProcess_checkDates(this)"
								buttonImageOnly="true"></psj:datepicker>
						</td>
					</tr>
						<tr>
						<td align="right" width="10%">
							<ps:label value="%{getText('reporting.periodicDate')}"  id="periodicDateLabel"/>
						</td>
						<td width="5%">
							<ps:textfield size="6"  id="schedPeriodicDate" name="schedulerDateProcessCO.periodicDate"
								 maxlength="6" mode="number" nbFormat="######" 
								 minValue="0"></ps:textfield>
						</td>
						
						<td width="25%">
							<ps:select list="periodTypeArrList" listKey="VALUE_CODE"
								listValue="VALUE_DESC" name="schedulerDateProcessCO.periodType"
								id="schedPeriodTypeComboId" />
						</td>
						
					</tr> 
</table>
  <ptt:toolBar id="schedulerDateProcessMaintToolBar_${_pageRef}" hideInAlert="true">
   				<psj:submit type="button" button="true" buttonIcon="ui-icon-disk"
					onclick="rep_schdDateProcess_runProc()">
					<ps:text name="tmplProc.run"></ps:text>
				</psj:submit>

</ptt:toolBar>
</ps:form>

