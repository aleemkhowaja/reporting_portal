<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="linkParamsTitle_var" 		value="%{getEscText('reporting.linkParams')}"/>
<ps:set name="procParamsTitle_var" 		value="%{getEscText('reporting.procParamsTitle')}"/>
<ps:set name="paramsOk_var" 			value="%{getEscText('reporting.ok')}"/>
<ps:set name="paramsCancel_var" 		value="%{getEscText('reporting.cancel')}"/>
<ps:set name="procOrderErrMsg_var" 		value="%{getEscText('wrongOrder_key')}"/>

	<script type="text/javascript">

	var linkParamsTitle 	= '<ps:property value="linkParamsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var procParamsTitle 	= '<ps:property value="procParamsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsOk 			= '<ps:property value="paramsOk_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsCancel 		= '<ps:property value="paramsCancel_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var procOrderErrMsg 	= '<ps:property value="procOrderErrMsg_var"  escapeHtml="false" escapeJavaScript="true"/>'

	$(document).ready(function () 
	{	
	$.struts2_jquery.require("Procedures.js", null,jQuery.contextPath + "/path/js/reporting/procedures/");		
		$("#procGrid_"+_pageRef).subscribe('disableProcName', function(event,data) 
		{
			rowId = (event["originalEvent"])["id"];
			myObject = $("#procGrid_"+_pageRef).jqGrid('getRowData',rowId);
			var procName=myObject["PROC_NAME"];
			var procId=myObject["PROC_ID"];
			if(procName !="" && procId!="")
			{
				document.getElementById(rowId+"_PROC_NAME_lookuptxt_procGrid_"+_pageRef).disabled=true;
				document.getElementById(rowId+"_PROC_NAME_spanLookup_procGrid_"+_pageRef).style.display="none";
			}
		});
			
	});
	
	
	</script>

			
	 <div>
		  	<ps:url var="urlTag" action="proc_loadProcList" namespace="/path/designer">
		  		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		  	</ps:url>
			<psjg:grid  id="procGrid_${_pageRef}" 
						gridModel="gridModel"
						dataType="json"
						href="%{urlTag}"
						pager="true"
						navigator="true"
						navigatorSearch="false "
						navigatorRefresh="false"
			        	viewrecords="true"
			        	rowNum="15" 
			  	  		rowList="5,10,15,20"
			  	  		editinline="true"
			  	  		editurl="%{urlTag}"
			        	navigatorEdit="false"
			        	addfunc="addProc" 
			        	delfunc="deleteProc"
			        	onEditInlineBeforeTopics="disableProcName">
	         
	         <psjg:gridColumn name="PROC_ID" index="PROC_ID" id="PROC_ID" title="%{getText('proc.procId')}" colType="number"  editable="false" sortable="false" hidden="true"/>
	         <psjg:gridColumn name="REPORT_ID" index="REPORT_ID" id="REPORT_ID"  title="%{getText('reportId')}" colType="number"  editable="true" sortable="true" hidden="true"/>
	     	 <psjg:gridColumn id="PROC_NAME" index="PROC_NAME" name="PROC_NAME"   
	     	 	editoptions="{ readonly: 'readonly'}"
	     	 	title="%{getText('proc.procName')}" editable="true" sortable="false" 
	     	 	colType="liveSearch" searchElement="PROC_NAME" 
	     	 	dataAction="${pageContext.request.contextPath}/path/designer/proc_procNameLkp.action?_pageRef=${_pageRef}" 
	     	 	resultList="PROC_DESC:PROC_DESC" />
	         <psjg:gridColumn name="PROC_DESC" index="PROC_DESC" id="PROC_DESC"  title="%{getText('proc.procDesc')}" colType="text"  editable="false" sortable="false"  />
	         <psjg:gridColumn name="PROC_ORDER" index="PROC_ORDER" id="PROC_ORDER"  title="%{getText('proc.procOrder')}" colType="number"  editable="true" sortable="false"  />
	         <psjg:gridColumn name="EXEC_BEFORE" index="EXEC_BEFORE" id="EXEC_BEFORE" title="%{getText('proc.execBefore')}" colType="select" editable="true" edittype="select" formatter="select" editoptions="{value:'0:%{getText('reporting.before')};1:%{getText('reporting.after')}'}" sortable="false" />
	         <psjg:gridColumn  name="PROC_PARAMS" formatter="openParams" index="PROC_PARAMS" colType="text" title="%{getText('proc.params')}"  editable="false" width="150" align="center" sortable="false"/>
	         

	
	  	 </psjg:grid> 
     
		</div>
	<div id="procGridDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	
	
	 <table align="right" id="procButBarId_${_pageRef}">
	 <tr>
		 <td >
		 		<psj:submit button="true" onclick="saveProc()" >
					<ps:text name="reporting.ok"></ps:text>
				</psj:submit>
		 </td>
		 <td>
		 		<psj:submit button="true" onclick="cancelProc()" >
					<ps:text name="reporting.cancel"></ps:text>
				</psj:submit>
		 </td>
	 </tr>
 </table>
	
	
	<ps:form id="procForm_${_pageRef}" action="proc_saveProcedures" namespace="/path/designer">
		<ps:hidden name="updates" id="updatesProc_${_pageRef}"></ps:hidden>
	</ps:form>
	<ps:form id="procParamsForm_${_pageRef}" action="proc_saveProcParams" namespace="/path/designer">
		<ps:hidden name="update1" id="update1_${_pageRef}"></ps:hidden>
	</ps:form>
	
	<script type="text/javascript">
	if(_pageRef=="RD00UD")
	{
	
		document.getElementById("procButBarId_"+_pageRef).style.display="none";
	}
	else
	{
		document.getElementById("procButBarId_"+_pageRef).style.display="inline";
	}

</script>
