<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<script  type="text/javascript">
   $(document).ready(function() 
{

	  $.struts2_jquery.require("SchedulerDateProcessList.js,SchedulerDateProcessMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/schedulerdateprocess/");

});
  
</script>
	
<div id="tbDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="headerPortion"  >
<jsp:include page="SchedulerDateProcessMaint.jsp"></jsp:include>
</div>


		<ps:form id="frmReportsGrid">
			<div id="allReportsGridDiv">
				<div id="inAllReportsGridDiv">
					<ps:url var="urlRepGrid" action="scheduler_loadSchedReportGrid"
						namespace="/path/scheduler"></ps:url>
					<psjg:grid id="schedReportGrid_${_pageRef}" gridModel="gridModel"
						dataType="json" href="%{urlRepGrid}" pager="true" navigator="true"
						navigatorSearch="false"
						navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
						navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
						navigatorRefresh="true" viewrecords="true" rowNum="5"
						rowList="5,10,15,20" ondblclick="detGridClicked()" sortable="true"
						addfunc="addReportRec" delfunc="onDetDelClicked">
						
						<psjg:gridColumn name="reportLineNoCheckBox"  id="reportLineNoCheckBox" 
						 index="reportLineNoCheckBox" colType="checkbox" title="" 
						 align="center" formatter="checkbox"  sortable="false" 
						 edittype="checkbox"  width="10" editable="true" hidden='false' search="false"
						 formatoptions="{disabled : false}" editoptions="{value:'Yes:No'}"/>	
			
						<psjg:gridColumn name="SCHED_ID" id="SCHED_ID" width="70"
							title="SCHED_ID" colType="number" editable="false"
							sortable="false" hidden="true" index="SCHED_ID" />
							
														<psjg:gridColumn name="SCHED_NAME" id="SCHED_NAME" width="70"
							index="SCHED_NAME" title="%{getText('sch.name')}"
							colType="text" editable="false" sortable="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
							
						<psjg:gridColumn name="REPORT_ID" id="REPORT_ID" width="70"
							title="REPORT_ID" colType="text" editable="false" sortable="true"
							hidden="true" index="REPORT_ID" />

						<psjg:gridColumn name="REPORT_NAME" id="REPORT_NAME" width="70"
							index="REPORT_NAME" title="%{getText('reportName')}"
							colType="text" editable="false" sortable="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />

						<psjg:gridColumn name="REPORT_FORMAT_TRANS" id="REPORT_FORMAT_TRANS"
							width="70" index="REPORT_FORMAT_TRANS"
							title="%{getText('reportFormat')}" colType="text"
							editable="false" sortable="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
							
						<psjg:gridColumn name="REPORT_FORMAT" id="REPORT_FORMAT"
							width="70" index="REPORT_FORMAT"
							title="%{getText('reportFormat')}" colType="text"
							editable="false" sortable="true" hidden="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
							
						<psjg:gridColumn name="REPORT_REF" id="REPORT_REF"
							width="70" index="REPORT_REF"
							title="%{getText('reportFormat')}" colType="text"
							editable="false" sortable="true" hidden="true"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />	
					</psjg:grid>

				</div>
			</div>
		</ps:form>
<script type="text/javascript">
$("#schedReportGrid_" + _pageRef).jqGrid('filterToolbar', {
	searchOnEnter : true
});
</script>
