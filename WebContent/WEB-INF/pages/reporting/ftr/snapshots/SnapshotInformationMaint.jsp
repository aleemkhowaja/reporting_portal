<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<ps:set name="noFileSelPerFreq_var" 	value="%{getEscText('snapshots.noFileSelPeriodFrequency')}"/>
<ps:set name="missingPeriod_var" 		value="%{getEscText('snapshots.missingPeriod')}"/>
<ps:set name="missingFrequency_var" 	value="%{getEscText('snapshots.missingFrequency')}"/>
<ps:set name="noSnSelFileGen_var" 		value="%{getEscText('snapshots.noSnSelFileGen')}"/>
<ps:set name="samePeriodError_var" 		value="%{getEscText('snapshot.samePeriodError')}"/>
<ps:set name="selectRow_key_var" 			value="%{getEscText('reporting.selectRowAlert')}"/>
<ps:set name="modifySnp_key_var" 			value="%{getEscText('snp.modifySnpashot')}"/>
<ps:set name="snpNoHeadAndFoot_var" 		value="%{getEscText('reporting.noHeadAndFoot')}"/>
<ps:set name="snpNoHeaders_var" 			value="%{getEscText('reporting.noHeaders')}"/>
<ps:set name="sameFreqPeriodCheckError_var" 		value="%{getEscText('snapshot.sameFreqPeriodCheckError')}"/>
<script type="text/javascript">

var noFileSelPerFreq 	= '<ps:property value="noFileSelPerFreq_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingPeriod 		= '<ps:property value="missingPeriod_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingFrequency 	= '<ps:property value="missingFrequency_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var noSnSelFileGen		= '<ps:property value="noSnSelFileGen_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var samePeriodError     = '<ps:property value="samePeriodError_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectSnpRowAlert= '<ps:property value="selectRow_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var modifySnpTitle= '<ps:property value="modifySnp_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var snpNoHeadAndFoot 		= '<ps:property value="snpNoHeadAndFoot_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var snpNoHeaders 			= '<ps:property value="snpNoHeaders_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var sameFreqPeriodCheckError = '<ps:property value="sameFreqPeriodCheckError_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

function modifySnapshot()
{
	var row = $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');
	if(row==null)
	{
			_showErrorMsg(selectSnpRowAlert,warning_msg_title, 300, 100);
	}
	else
	{
		rowObject =  $("#snapshotInformationListGridTbl_Id_"+_pageRef).jqGrid("getRowData",row);
		var snpRepId = rowObject["repSnapshotInfoVO.REP_SNAPSHOT_ID"];

		var params={};
		params["code"]=snpRepId;
		params["updates"]="Y";
		viewSnpUrl= jQuery.contextPath+ "/path/snapshotParameter/SnapshotInformationMaintAction_viewSnapshot.action?_pageRef="+_pageRef;
		dialogOptions={ autoOpen: false,
					height:700,
					title:modifySnpTitle,
					width:800 ,
					modal: true,
					buttons: [{ text : "OK", click : saveSnapshotModifications},
					          { text : "cancel", click : closeViewSnpDlg}
			          ]
	   }
	   	$.post(viewSnpUrl ,params , function( param )
		{
	  	  $('#viewSnpDlg_'+_pageRef).html(param) ;
	  	  $('#viewSnpDlg_'+_pageRef).dialog(dialogOptions)
	  	  $('#viewSnpDlg_'+_pageRef).dialog('open');
		},"html");
	}

}

function closeViewSnpDlg()
{
	$('#viewSnpDlg_'+_pageRef).html("");
	$('#viewSnpDlg_'+_pageRef).dialog('close');
}

function saveSnapshotModifications()
{
 	 var repChanges=""; 
	 $("#snpDataDiv_${_pageRef} input[type='text'][changed='1']").each(function(index, obj) 
	 {
		repChanges+="&"+$(this).attr("name")+"="+$(this).val();
	 });
	 $("#viewSnpChanges_"+_pageRef).val(repChanges);
	 
	 //save 
	 	var url = jQuery.contextPath+ "/path/snapshotParameter/SnapshotInformationMaintAction_saveSnapshotDataChanges.action";
		var myObject = $("#viewSnpFrm_" + _pageRef).serialize();
	 
	 	$.ajax( {
		url : url,
		type : "post",
		dataType : "json",
		data : myObject,
		success : function(param) {
			if (typeof param["_error"] == "undefined"|| param["_error"] == null) 
			{
				_showErrorMsg(savedMsg,info_msg_title, 300, 100);
				$('#viewSnpDlg_'+_pageRef).html("");
				$('#viewSnpDlg_'+_pageRef).dialog('close');
			}
		}
	});
}


function setChanged(elt) 
{
	$(elt).attr("changed", "1");
}

</script>


<div id="viewSnpDlg_<ps:property value="_pageRef" escapeHtml="true"/>"></div>


<ps:form useHiddenProps="true"
	id="snapshotInformationMaintFormId_${_pageRef}"
	namespace="/path/snapshotParameter">
	<table class="headerPortionContent ui-widget-content" width="100%" cellpadding="4">
		<tr>
			<td nowrap="nowrap">
				<ps:label key='repRef_key' />
			</td>
			<td>
				<psj:livesearch id="repRef_${_pageRef}" 
					name="repSnapshotInformationCO.repSnapshotParamVO.REP_REFERENCE"
					mode="text"
					actionName="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotInformationListAction_repRefLookup.action?_pageRef=${_pageRef}"
					searchElement=""
					resultList="repSnapshotParamVO.REP_REFERENCE:lookuptxt_repRef_${_pageRef}">
				</psj:livesearch>
			</td>
			<td width="3%" colspan="3"></td>
			<td nowrap="nowrap">
				<ps:label key='snapshots.additionnalReference' />
			</td>
			<td>
				<ps:textfield size="30"
					name="repSnapshotInformationCO.repSnapshotParamVO.ADDITIONAL_REFERENCE"
					maxlength="15" id="addRef_${_pageRef}"></ps:textfield>
			</td>
			<td nowrap="nowrap">
				<ps:label key='sch.status' />
			</td>
			<td>
				<span id="span4_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:select list="infStatusList"
						cssStyle="width:195" listKey="VALUE_CODE" listValue="VALUE_DESC"
						name="repSnapshotInformationCO.repSnapshotInfoVO.DECLARED_YN"
						id="infStatusListComboId_${_pageRef}" /> </span>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
				<ps:label key='snapshots.snapshotUser' />
			</td>
			<td>
				<psj:livesearch id="snUser_${_pageRef}"
					name="repSnapshotInformationCO.repSnapshotInfoVO.SNAPSHOT_USER"
					mode="text" maxlength="8" readOnlyMode="false"
					actionName="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotInformationListAction_snapshotUserLookup.action"
					searchElement=""
					resultList="USER_ID:lookuptxt_snUser_${_pageRef}">
				</psj:livesearch>
			</td>
			<td width="3%" colspan="3"></td>
			<td nowrap="nowrap">
				<ps:label key='snapshots.snapshotDate' />
			</td>
			<td>
				<span id="span2_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker size="30"
						name="repSnapshotInformationCO.repSnapshotInfoVO.SNAPSHOT_DATE"
						id="snpDate_${_pageRef}" buttonImageOnly="true" /> </span>
			</td>
			<td nowrap="nowrap">
				<ps:label key='snapshots.retrievalDate' />
			</td>
			<td>
				<span id="span3_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker size="30"
						name="repSnapshotInformationCO.repSnapshotInfoVO.RETREIVE_DATE"
						buttonImageOnly="true" id="retrievalDate_${_pageRef}" /> </span>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
				<ps:label key='snapshots.declaringUser' />
			</td>
			<td>
				<psj:livesearch id="declaringUser_${_pageRef}"
					name="repSnapshotInformationCO.repSnapshotInfoVO.DECLARED_BY"
					mode="text" maxlength="8" readOnlyMode="false"
					actionName="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotInformationListAction_snapshotUserLookup.action"
					searchElement=""
					resultList="USER_ID:lookuptxt_declaringUser_${_pageRef}">
				</psj:livesearch>
			</td>
			<td width="3%" colspan="3"></td>
			<td nowrap="nowrap">
				<ps:label key='snapshots.declarationDate' />
			</td>
			<td>
				<span id="span1_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker size="30"
						name="repSnapshotInformationCO.repSnapshotInfoVO.DECLARED_DATE"
						buttonImageOnly="true" id="declarationDate_${_pageRef}" /> </span>
			</td>
			<td nowrap="nowrap">
				<ps:label key='snapshots.reportFrequency' />
			</td>
			<td>
				<span id="span5_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:select cssStyle="width:195"
						list="reportFrequencyList" listKey="VALUE_CODE"
						listValue="VALUE_DESC"
						name="repSnapshotInformationCO.repSnapshotParamVO.SNAPSHOT_FREQUENCY"
						id="reportFrequencyListId_${_pageRef}" /> </span>
			</td>
		</tr>
		<tr>
		<td nowrap="nowrap" >
				<psj:a id="filterSnInf_${_pageRef}" button="true"
					onclick="searchSnInformation();">
					<ps:text name="reportsMismatch.filter" />
				</psj:a>
			</td>
			<td colspan="6">
				<psj:a id="resetSnInf_${_pageRef}" button="true"
					onclick="resetSnInformation();">
					<ps:text name="wizard.resetQryTitle" />
				</psj:a>
			</td>
			<td>
				<ps:label key='snapshots.filePeriod' />
			</td>
			<td>
				<span id="spanFilePeriod_<ps:property value="_pageRef" escapeHtml="true"/>"> <psj:datepicker size="30" name="filePeriod"
						id="snpFilePeriod_${_pageRef}" buttonImageOnly="true" displayFormat="mm-yy"  /> </span>
			</td>
		</tr>
	</table>
	<table  cellspacing="10">
		<tr>
			<td>
				<ps:label key='designer.defaultFormat' />
			</td>
			<td>
				<ps:select id="format_${_pageRef}" name="repSnapshotInformationCO.SNP_FORMAT"
					list="reportFormatsList" listKey="VALUE_CODE"
					listValue="VALUE_DESC" 
					onchange="checkSnpIfCSV(this.value)"></ps:select>
			</td>


			<td>
				<span id="sepLblTd_<ps:property value="_pageRef" escapeHtml="true"/>"><ps:label
						value="%{getText('reporting.csvSeparator')}" />
				</span>
			</td>
			<td nowrap="nowrap">

				<table>
					<tr>
						<td>
							<span id="sepValTd_<ps:property value="_pageRef" escapeHtml="true"/>"> <ps:select
									list="csvSeparatorsList" listKey="code" listValue="description"
									name="repSnapshotInformationCO.CSV_SEPARATOR" id="csvSeparatorId_${_pageRef}" /> </span>
						</td>
					</tr>
				</table>
			</td>

			<td>
				<span id="noHeadFootSpan_<ps:property value="_pageRef" escapeHtml="true"/>">
				 <ps:label
						value="%{getText('reporting.noHeadAndFoot')}"
						id="noHeadAndFootLbl_${_pageRef}">
				</ps:label> 
				<ps:checkbox
						name="repSnapshotInformationCO.SHOW_HEAD_FOOT" id="noHeadAndFoot_${_pageRef}">
						</ps:checkbox>
				</span>
			</td>




			<td nowrap="nowrap">
				<psj:a id="viewSnp_${_pageRef}" button="true"
					onclick="viewSnapshot();">
					<ps:label key="snp.viewSnpashot" />
				</psj:a>
			</td>
			<td nowrap="nowrap">
				<psj:a id="modifySnp_${_pageRef}" button="true"
					onclick="modifySnapshot();">
					<ps:label key="snp.modifySnpashot" />
				</psj:a>
			</td>
			<td nowrap="nowrap">
				<psj:a id="genFile_${_pageRef}" button="true"
					onclick="genFile();">
					<ps:label id="genFileLabel_${_pageRef}"/>
				</psj:a>
			</td>
			<td nowrap="nowrap">
				<psj:a id="viewFile_${_pageRef}" button="true"
					onclick="viewFile(1);">
					<ps:label id="viewFileLabel_${_pageRef}"/>
				</psj:a>
			</td>
		</tr>
	</table>
	<ps:hidden name="updates" id="updateSnInfo_${_pageRef}"></ps:hidden>
	<ps:hidden name="repSnapshotInfoVO.DESCRIPTION" 		id="descriptionSnInfo_${_pageRef}"></ps:hidden>
	<ps:hidden name="repSnapshotInfoVO.REP_SNAPSHOT_ID" 	id="repSnapshotId_${_pageRef}"></ps:hidden>
	<ps:hidden name="repSnapshotParamVO.REP_ID" 			id="repId_${_pageRef}"></ps:hidden>
	<ps:hidden name="repSnapshotInfoVO.RETREIVE_DATE" 		id="retDateId_${_pageRef}"></ps:hidden>
	<ps:hidden name="repSnapshotParamVO.SNAPSHOT_FREQUENCY" id="snpFrqId_${_pageRef}"></ps:hidden>
	<ps:hidden name="shouldCheckPerFreq"					id="shouldCheckPerFreq_${_pageRef}"></ps:hidden>
	<ps:hidden name="repSnapshotParamVO.REP_REFERENCE"		id="reportRef_${_pageRef}"></ps:hidden>
</ps:form>
<script>
$(document).ready(
		function() {
			$.struts2_jquery.require("SnapshotInformationMaint.js", null,
					jQuery.contextPath + "/path/js/reporting/ftr/snapshots/");
			$("#snapshotInformationMaintFormId_" + _pageRef).processAfterValid(
					"snapshotInformationMaint_processSubmit", {});
			$("#lookuptxt_repRef_"+_pageRef).attr('readonly', true);
			$("#lookuptxt_snUser_"+_pageRef).attr('readonly', true);
			$("#lookuptxt_declaringUser_"+_pageRef).attr('readonly', true);
		});

</script>
