<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@page import="com.path.vo.common.SessionCO"%>
<%@page import="com.path.vo.common.ReportingSessionCO"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.path.bo.reporting.common.RepConstantsCommon"%>


<ps:set name="argFeRequired_var" 		value="%{getEscText('group.missingFe')}"/>
<ps:set name="canNotDeleteAlert_var" 	value="%{getEscText('reporting.delObjAlert')}"/>
<ps:set name="argUsedInSyntax_var" 	value="%{getEscText('reporting.argUsedInSyntax')}"/>
<ps:set name="argNameRepeated_var" 		value="%{getEscText('reporting.argRepeatedAlert')}"/>
<ps:set name="argNameEmpty_var" 		value="%{getEscText('reporting.argNameEmptyAlert')}"/>
<ps:set name="argumentNameError_var" 	value="%{getEscText('reporting.name')}"/>
<ps:set name="repLinkQry_var" 			value="%{getEscText('reporting.linkSrcQry')}"/>
<ps:set name="paramsOk_var" 			value="%{getEscText('reporting.ok')}"/>
<ps:set name="paramsCancel_var" 		value="%{getEscText('reporting.cancel')}"/>
<ps:set name="argumentConstraints_var" 	value="%{getEscText('reporting.argumentConstraints')}"/>
<ps:set name="queryIdkey_var" 	value="%{getEscText('queryIdkey')}"/>
<ps:set name="hasnodefinedsyntaxkey_var" 	value="%{getEscText('hasnodefinedsyntaxkey')}"/>

<%
    String paramsDisp = "display: none";
    String showRepArgQryLink = "true";
    String showDelAddFunc = "true";
    String paramsFormDisp = "display: inline";
    String atwidth = "true";
    String pgRef = (request.getParameter("_pageRef"));
    SessionCO sessionCO = (SessionCO) session.getAttribute("sesVO");
    HashMap<String, ReportingSessionCO> sessionMap = (HashMap<String, ReportingSessionCO>) sessionCO
		    .getReportingAppDet();
    ReportingSessionCO repSess = (ReportingSessionCO) sessionMap.get(pgRef);
    if(pgRef.equals("RD00R") && !repSess.isReportParams())
    {
		paramsDisp = "";
    }
    if(pgRef.equals("RD00R") && !repSess.isReportParams()
		    && ("2").equals(repSess.getSqlObj().getOutputLayout()))
    {
		showDelAddFunc = "false";
		paramsFormDisp = "display: none";

    }
    if(pgRef.equals("RD00R") && !repSess.isReportParams()
		    && ("1").equals(repSess.getSqlObj().getOutputLayout()))
    {
		showRepArgQryLink = "false";
    }
%>

<script type="text/javascript">

	var argFeRequired 		= '<ps:property value="argFeRequired_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var canNotDeleteAlert 	= '<ps:property value="canNotDeleteAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var argUsedInSyntax 	= '<ps:property value="argUsedInSyntax_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var argNameRepeated 	= '<ps:property value="argNameRepeated_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var argNameEmpty 		= '<ps:property value="argNameEmpty_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var argumentNameError 	= '<ps:property value="argumentNameError_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var repLinkQry 			= '<ps:property value="repLinkQry_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsOk 			= '<ps:property value="paramsOk_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsCancel		= '<ps:property value="paramsCancel_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var argumentConstraints = '<ps:property value="argumentConstraints_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var queriesProgRef 		= "<%=RepConstantsCommon.QUERIES_PROG_REF%>"
	var showRepArgQryLink = <%=showRepArgQryLink%>;
	var queryIdkey = "${queryIdkey_var}";
	var hasnodefinedsyntaxkey = "${hasnodefinedsyntaxkey_var}";
$(document).ready(function () 
{
	$.struts2_jquery.require("Arguments.js", null,jQuery.contextPath + "/path/js/reporting/designer/");
	rep_args_documentReadyFunc();	
}
);

rep_args_cancelEvents();
</script>

<div>
		<ps:url id="urlTag" action="queryDesign_argumenGridUrl"
			namespace="/path/designer">
			<ps:param name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psjg:grid id="argumentsGrid_${_pageRef}" gridModel="gridModel"
			dataType="json" href="%{urlTag}" pager="true" navigator="true"
			navigatorSearch="false " viewrecords="true" rowNum="15"
			rowList="5,10,15,20" navigatorEdit="false" addfunc="addArgument"
			delfunc="commonDeleteArgument" shrinkToFit="true" autowidth="true"
			ondblclick="argumentRowClicked()" navigatorAdd="<%=showDelAddFunc%>"
			navigatorDelete="<%=showDelAddFunc%>">

			<psjg:gridColumn name="index" index="index" id="index" title=""
				colType="text" editable="false" sortable="true" hidden="true"  />

			<psjg:gridColumn name="ARGUMENT_ID" index="ARGUMENT_ID"
				id="argumentId" title="" colType="text" editable="false"
				sortable="true" hidden="true" />

			<psjg:gridColumn name="ARGUMENT_ORDER" index="ARGUMENT_ORDER"
				id="argumentOrder" width="35" title="%{getText('reporting.order')}"
				colType="number" editable="false" sortable="true"
				hidden="%{hideArgsColGrid}" />

			<psjg:gridColumn name="ARGUMENT_NAME" index="ARGUMENT_NAME"
				id="argumentName" width="150" title="%{getText('reporting.name')}"
				colType="text" editable="false" sortable="true" />

			<psjg:gridColumn name="ARGUMENT_LABEL" index="ARGUMENT_LABEL"
				id="argumentLabel" width="120" title="%{getText('reporting.label')}"
				colType="text" editable="false" sortable="true"
				hidden="%{hideArgsColGrid}" />

			<psjg:gridColumn name="ARGUMENT_SOURCE" index="ARGUMENT_SOURCE"
				id="argSource" title="" colType="number" hidden="true" />

			<psjg:gridColumn name="argTypeDesc" index="argTypeDesc"
				id="argumentTypeDesc" width="50"
				title="%{getText('reporting.type')}" colType="text" editable="false"
				sortable="true" />

			<psjg:gridColumn name="ARGUMENT_TYPE" index="ARGUMENT_TYPE"
				id="argumentType" title="" colType="text" hidden="true" />

			<psjg:gridColumn name="ARGUMENT_DATE_VALUE" index="ARGUMENT_DATE_VALUE"
				id="argumentDateValue" title="" colType="text" hidden="true" />
				
			<psjg:gridColumn name="SESSION_ATTR_NAME" index="SESSION_ATTR_NAME"
				id="sessionAttrName" width="100"
				title="%{getText('arguments.sessionAttr')}" colType="text"
				editable="false" sortable="true" hidden="%{hideArgsColGrid}" />

			<psjg:gridColumn name="QUERY_ID" index="QUERY_ID" id="queryId"
				hidden="true" title="" colType="number" />

			<psjg:gridColumn name="QUERY_NAME" index="QUERY_NAME" id="queryName"
				width="120" title="%{getText('arguments.query')}" colType="text"
				editable="false" sortable="true" hidden="%{hideArgsColGrid}" />

			<psjg:gridColumn name="COLUMN_NAME" index="COLUMN_NAME" id="colName"
				title="" colType="text" editable="false" sortable="true"
				hidden="true" />
			<psjg:gridColumn name="COLUMN_DESC" index="COLUMN_DESC" id="colDesc"
				title="" colType="text" editable="false" sortable="true"
				hidden="true" />
			<psjg:gridColumn name="ARG_QUERY_OPTIONS" index="ARG_QUERY_OPTIONS" id="argQueryOptions"
				title="" colType="text" editable="false" sortable="true"
				hidden="true" />
			<psjg:gridColumn name="DEFAULT_VALUE_COL_DESC" index="DEFAULT_VALUE_COL_DESC" id="defaultValueColDesc"
				title="" colType="text" editable="false" sortable="true"
				hidden="true" />

			<psjg:gridColumn name="ARGUMENT_VALUE" index="ARGUMENT_VALUE"
				id="argumentValue" width="100"
				title="%{getText('arguments.ARGUMENT_VALUE')}" colType="text"
				editable="false" sortable="true" hidden="false" />

			<psjg:gridColumn name="DISPLAY_FLAG" index="DISPLAY_FLAG"
				id="argDispFlag" width="50" title="%{getText('display_key')}"
				colType="text" editable="false" sortable="false"
				hidden="%{hideArgsColGrid}" />

			<psjg:gridColumn name="ENABLE_FLAG" index="ENABLE_FLAG"
				id="argEnableFlag" width="50" title="%{getText('enable_key')}"
				colType="text" editable="false" sortable="false"
				hidden="%{hideArgsColGrid}" />

			<psjg:gridColumn name="FLAG_VALUE_ON" index="FLAG_VALUE_ON"
				id="flagOn" title="" colType="text" editable="false"
				sortable="false" hidden="true" />

			<psjg:gridColumn name="FLAG_VALUE_OFF" index="FLAG_VALUE_OFF"
				id="flagOff" width="1" title="" colType="text" editable="false"
				sortable="false" hidden="true" />

			<psjg:gridColumn name="LINK_REP_QRY_ARG" index="LINK_REP_QRY_ARG"
				id="LINK_REP_QRY_ARG" width="100"
				title="%{getText('arguments.linkRepArg')}" colType="text"
				editable="false" sortable="false" hidden="<%=showRepArgQryLink%>" />

			<psjg:gridColumn name="QUERY_ID_DEFAULT" index="QUERY_ID_DEFAULT"
				id="QUERY_ID_DEFAULT" title="" colType="text" editable="false"
				sortable="false" hidden="true" />
				
			<psjg:gridColumn name="ARGUMENT_SRC_DEFAULT"
				index="ARGUMENT_SRC_DEFAULT" id="ARGUMENT_SRC_DEFAULT" title=""
				colType="text" editable="false" sortable="false" hidden="true" />
				
			<psjg:gridColumn name="DEFAULT_VALUE_COL_NAME"
				index="DEFAULT_VALUE_COL_NAME" id="DEFAULT_VALUE_COL_NAME" title=""
				colType="text" editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="DEFAULT_VALUE_COL_DESC"
				index="DEFAULT_VALUE_COL_DESC" id="DEFAULT_VALUE_COL_DESC" title=""
				colType="text" editable="false" sortable="false" hidden="true" />
				
			<psjg:gridColumn name="DEFAULT_VAL_QRY_NAME"
				index="DEFAULT_VAL_QRY_NAME" id="DEFAULT_VAL_QRY_NAME" width="120"
				title="%{getText('arguments.query')}" colType="text"
				editable="false" sortable="false" hidden="%{hideArgsColGrid}" />
			<psjg:gridColumn name="MULTISELECT_YN" title=""
				index="MULTISELECT_YN" id="MULTISELECT_YN" colType="number"
				editable="false" sortable="false" hidden="true" />				
				<psjg:gridColumn name="TO_SAVE_YN" title=""
				index="TO_SAVE_YN" id="TO_SAVE_YN" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="CIF_AUDIT_YN" title=""
				index="CIF_AUDIT_YN" id="CIF_AUDIT_YN" colType="number"
				editable="false" sortable="false" hidden="true" />						
		</psjg:grid>
	</div>
<div>
	<form id="argumentsForm_${_pageRef}">
	<table  width="100%">
	<tr>
	<td>
		<table  style="<%=paramsFormDisp%>" width="100%" >
			<tr>
				<td>					
					<ps:label value="%{getText('reporting.label')}" 
						id="argumentTxt_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="argumentCO.ARGUMENT_LABEL" 
						id="argumentLabel_${_pageRef}"  tabindex="17"  >
					</ps:textfield>
				</td>
				<td>
					<ps:label value="%{getText('arguments.ARGUMENT_SOURCE')}"
						id="argumentSourceTxt_${_pageRef}" />
				</td>
				<td>
					<ps:select name="argumentCO.ARGUMENT_SOURCE"
						id="argumentSource_${_pageRef}" list="argsSourceList"  tabindex="18" 
						listKey="VALUE_CODE" listValue="VALUE_DESC"  
						onchange="showHideElements(this.value);emptyArgConstrData();">
					</ps:select>
				</td>

				<td>
					<ps:label value="%{getText('reporting.type')}"
						id="typeLabel_${_pageRef}" />
				</td>
				<td>
					<ps:select name="argumentCO.ARGUMENT_TYPE"
						id="argumentType_${_pageRef}" list="argsTypeList"
						listKey="VALUE_CODE" listValue="VALUE_DESC"    tabindex="19"
						onchange="emptyArgValue();emptyArgConstrData();"></ps:select>
				</td>
				
				<td>
					<ps:label value="%{getText('reporting.argDateValue')}"
						id="argDateValueLabel_${_pageRef}" cssStyle="display:none" />
				</td>
				<td>
					<ps:select name="argumentCO.ARGUMENT_DATE_VALUE"
						id="argumentDateValue_${_pageRef}" list="argsDateValueList"  tabindex="20"
						listKey="VALUE_CODE" listValue="VALUE_DESC" cssStyle="display:none"></ps:select>
				</td>
				<td>

					<ps:select name="argumentCO.SESSION_ATTR_NAME"
						id="sessionAttrCmb_${_pageRef}" list="sessionAttrList"   tabindex="21"
						emptyOption="true" cssStyle="display:none"
						parameter="sessionAttr:sessionAttrCmb_${_pageRef},_pageRef:_pageRef" 
						dependencySrc="${pageContext.request.contextPath}/path/designer/queryDesign_dependencyBySessionAttr"
						dependency="argumentType_${_pageRef}:argumentType,argumentValue_${_pageRef}:argDefaultVal">
					</ps:select>
					<%--			
			<psj:livesearch actionName="${pageContext.request.contextPath}/path/designer/queryDesign_constructQryLookup.action?_pageRef=${_pageRef}"
				name="argumentCO.QUERY_ID" id="queryLkp_${_pageRef}" 
		        searchElement="QUERY_ID, QUERY_NAME" autoSearch="true" 
		        paramList="QUERY_ID:lookuptxt_queryLkp_${_pageRef},_pageRef:_pageRef"
		        resultList="QUERY_ID:lookuptxt_queryLkp_${_pageRef},QUERY_NAME:queryLkpName"
		        parameter     ="queryIdLkp:lookuptxt_queryLkp_${_pageRef},_pageRef:_pageRef"
				dependencySrc ="${pageContext.request.contextPath}/path/designer/queryDesign_dependencyByQryId"
				dependency    ="lookuptxt_queryLkp_${_pageRef}:queryIdLkp,queryLkpName_${_pageRef}:queryNameLkp,columnsCmb_${_pageRef}:qryColumnsList"
				afterDepEvent="setColCmbDefaultIndex()"
				cssStyle="display:none" 
		    />
		    <ps:textfield name="argumentCO.QUERY_NAME" id="queryLkpName_${_pageRef}" readonly="true"
		    	cssStyle="display:none"></ps:textfield>
		    
		    <ps:select name="argumentCO.COLUMN_NAME"	id="columnsCmb_${_pageRef}" list="qryColumnsList" emptyOption="true"
		    	 parameter     ="columnName:columnsCmb_${_pageRef},_pageRef:_pageRef"
				 dependencySrc ="${pageContext.request.contextPath}/path/designer/queryDesign_dependencyByColName"
				 dependency    ="argumentType_${_pageRef}:argumentType,argumentValue_${_pageRef}:argDefaultVal"
		    	 cssStyle="display:none" >
			</ps:select>
		--%>

					<ps:label value="%{getText('arguments.FLAG_VALUE_ON')}"
						id="valueOnLabel_${_pageRef}" cssStyle="display:none" />
					<ps:textfield name="argumentCO.FLAG_VALUE_ON"
						id="falgValueOn_${_pageRef}" cssStyle="display:none" size="1"   tabindex="22"
						maxlength="1"></ps:textfield>
					<ps:label value="%{getText('arguments.FLAG_VALUE_OFF')}"
						id="valueOffLabel_${_pageRef}" cssStyle="display:none" />
					<ps:textfield name="argumentCO.FLAG_VALUE_OFF"
						id="falgValueOff_${_pageRef}" cssStyle="display:none" size="1"  tabindex="23"
						maxlength="1"></ps:textfield>
				</td>
				<td>
					<psj:a button="true" href="#" onclick="openLinkQueryList(1)" tabindex="24"
						id="linkQuery_${_pageRef}" cssStyle="display:none"> 
						<ps:text name="%{getText('reporting.linkSrcQry')}"></ps:text>
					</psj:a>
				</td>

				<td>
					<ps:label value="%{getText('arguments.argumentValType')}"
						id="argumentValTypeLabel_${_pageRef}" cssStyle="display:none" />
				</td>
				<td>
					<ps:select name="argumentCO.ARGUMENT_SRC_DEFAULT" tabindex="25"
						id="argumentSrcDefault_${_pageRef}" list="defaultSourceType"
						listKey="VALUE_CODE" listValue="VALUE_DESC" 
						cssStyle="display:none" onchange="dispLnkQryButt(1)"></ps:select>
				</td>


				<td align="center">
					<psj:a button="true" href="#" onclick="openLinkQueryList(2)"  tabindex="26"
						id="linkQueryDV_${_pageRef}" cssStyle="display:none"  >
						<ps:text name="%{getText('reporting.linkDfltQry')}"></ps:text>
					</psj:a>
				</td>
				<td id="valFlagChk_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none;">
					<ps:checkbox name="argumentCO.valueFlag" id="valueFlag_${_pageRef}" tabindex="27"
						cssStyle="display:none" />
				</td>
				<td  id="valLbl_<ps:property value="_pageRef" escapeHtml="true"/>">
					<ps:label value="%{getText('arguments.ARGUMENT_VALUE')}"
						id="valueLabel_${_pageRef}" />
				</td>
				<td id="argValTd_<ps:property value="_pageRef" escapeHtml="true"/>">
						<ps:textfield name="argumentCO.ARGUMENT_VALUE"  tabindex="28"
						id="argumentValue_${_pageRef}" onchange="checkInputValidity();"></ps:textfield>
			    </td>
				<td style="display: none;" id="normalDatePick_<ps:property value="_pageRef" escapeHtml="true"/>">	
						<psj:datepicker size="30" name="argumentCO.ARGUMENT_VALUE" tabindex="29"
						id="argValueDate_${_pageRef}" buttonImageOnly="true" 
						cssStyle="display:none" readonly="true" />
				</td>
				<td style="display: none;" id="detDatePick_<ps:property value="_pageRef" escapeHtml="true"/>">	
						<psj:datepicker size="30" name="argumentCO.ARGUMENT_VALUE"
						timepicker="true" timepickerShowSecond="true"
						timepickerFormat="hh:mm:ss" id="argValueDateTime_${_pageRef}" tabindex="30"
						buttonImageOnly="true" cssStyle="display:none" readonly="true" />
				</td>
				<td>
					<ps:label value="%{getText('reporting.order')}"
						id="argOrderTxt_${_pageRef}" />
				</td>
				<td>
					<ps:textfield name="argumentCO.ARGUMENT_ORDER" tabindex="31"
						id="argOrder_${_pageRef}" mode="number" maxlength="2" size="2"  
						minValue="1">
					</ps:textfield>
				</td>
				</tr>
				<tr>
				<td>
					<ps:label value="%{getText('reporting.name')}" />
				</td>
				
				<td>
					<ps:textfield name="argumentCO.ARGUMENT_NAME"    
						id="argumentName_${_pageRef}"  onchange="changedArgName()" onblur="checkArgsName()" tabindex="32">
					</ps:textfield>
				</td>
				</tr>
				</table>
				<table   style="<%=paramsFormDisp%>"  width="100%" >
				<tr>
				<td width="5%">
					<ps:label value="%{getText('display_key')}"
						id="displayFlagTxt_${_pageRef}" />
				</td>
				<td width="5%">
					<ps:checkbox name="argumentCO.displayFlag"   tabindex="33" 
						id="displayFlag_${_pageRef}" />
				</td>
				<td width="5%">
					<ps:label value="%{getText('enable_key')}"
						id="enableFlagTxt_${_pageRef}" />
				</td>
				<td width="5%">
					<ps:checkbox name="argumentCO.enableFlag"   tabindex="34" 
						id="enableFlag_${_pageRef}" />
				</td>
				<td id="multiSelLblTd_<ps:property value="_pageRef" escapeHtml="true"/>" width="5%">
					<ps:label value="%{getText('reporting.multiselectYN')}"
						id="multiselectFlagTxt_${_pageRef}" />
				</td>
				<td id="multiSelChkTd_<ps:property value="_pageRef" escapeHtml="true"/>" width="5%">
					<ps:checkbox name="argumentCO.MULTISELECT_YN_STR"  tabindex="35"
						id="multiselectFlag_${_pageRef}" />
				</td>			
				<td id="toSaveLblTd_<ps:property value="_pageRef" escapeHtml="true"/>" width="5%">
					<ps:label value="%{getText('reporting.save')}"
						id="toSaveFlagTxt_${_pageRef}" />
				</td>
				<td id="toSaveChkTd_<ps:property value="_pageRef" escapeHtml="true"/>" width="5%">
					<ps:checkbox name="argumentCO.TO_SAVE_YN_STR"  tabindex="36"
						id="toSaveFlag_${_pageRef}" />
				</td>				
				<td id="cifAuditLblTd_${_pageRef}" width="6%">
					<ps:label value="%{getText('applyCifAuditkey')}"
						id="cifAuditFlagTxt_${_pageRef}" />
				</td>
				<td id="cifAuditChkTd_${_pageRef}" width="5%">
					<ps:checkbox name="argumentCO.CIF_AUDIT_YN_STR"
						id="cifAuditFlag_${_pageRef}"/>
				</td>								
				<td align="left" id="constrContainer_<ps:property value="_pageRef" escapeHtml="true"/>">
					<psj:a button="true" href="#" onclick="openConstraintsDialog()"
						id="constraints_${_pageRef}">
						<ps:text name="%{getText('reporting.constraints')}"></ps:text>
					</psj:a>
				</td>
				<td>
					<table id="repParamsTblId_<ps:property value="_pageRef" escapeHtml="true"/>" cellpadding="5"
						style="<%=paramsDisp%>">
						<tr>
							<td>
								<ps:label value="%{getText('arguments.linkRepArg')}"
									cssStyle="font-weight: bold" />
							</td>
							<td>
								<ps:select name="argumentCO.LINK_REP_QRY_ARG"  tabindex="37"
									id="link_rep_qry_arg_${_pageRef}" list="reportParam"
									cssStyle="width:100">
								</ps:select>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2"></td>
			</tr>

		</table>
		</td>
		</tr>
		</table>
		<ps:hidden name="argumentCO.index" id="argumentIndex_${_pageRef}"
			value="0"></ps:hidden>
		<ps:hidden id="argumentsMode_${_pageRef}" name="argumentsMode"
			value="add"></ps:hidden>
		<ps:hidden name="oldArgName" id="oldArgName_${_pageRef}"></ps:hidden>
	</form>
</div>
<div id="linkQueryMainDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
<div id="constraintsMainDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
 
<ps:form id="linkQryArgsForm_${_pageRef}" action="queryDesign_putInLinkQryMap" namespace="/path/designer">
	<ps:hidden name="updatesLinkQryArgs" id="updatesLinkQryArgs_${_pageRef}"></ps:hidden>
	<ps:hidden name="repArgsName" id="repArgsName_${_pageRef}"></ps:hidden>
	<ps:hidden name="qryId" id="qryId_${_pageRef}"></ps:hidden>
	<ps:hidden name="qryName" id="qryName_${_pageRef}"></ps:hidden>
	<ps:hidden name="columnName" id="columnName_${_pageRef}"></ps:hidden>
	<ps:hidden name="columnDesc" id="columnDesc_${_pageRef}"></ps:hidden>
	<ps:hidden name="argQueryOptions" id="argQueryOptions_${_pageRef}"></ps:hidden>
	<ps:hidden name="defaultValueColDesc" id="defaultValueColDesc_${_pageRef}"></ps:hidden>
	<ps:hidden name="oldRepArgsName" id="oldRepArgsName_${_pageRef}"></ps:hidden>
	<ps:hidden name="argTypeDesc" id="argTypeDesc_${_pageRef}"></ps:hidden>
	<ps:hidden name="srcOrDflt" id="srcOrDflt_${_pageRef}"></ps:hidden>
	<ps:hidden name="qryDfltValId" id="qryDfltValId_${_pageRef}"></ps:hidden>
	<ps:hidden name="qryDfltValName" id="qryDfltValName_${_pageRef}"></ps:hidden>
	<ps:hidden name="qryDfltValColName" id="qryDfltValColName_${_pageRef}"></ps:hidden>
</ps:form>   


    
 
 