<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>

<ps:set name="checkProgRefSaveAsAlert_var" 		value="%{getEscText('fcr.checkProgRefAlert')}"/>
<ps:set name="missingSaveAsFeReq_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="checkOptAxsSaveAsAlert_var" 		value="%{getEscText('upDown.checkOptAxsAlert')}"/>
<ps:set name="saveAsArgProcSaved_var" 			value="%{getEscText('upDown.argProcSaved')}"/>
<ps:set name="saveAsRepLoadLAlert_var" 			value="%{getEscText('reporting.saveAsRepLoadLAlert')}"/>

<script type="text/javascript">
var checkProgRefSaveAsAlert 		= '<ps:property value="checkProgRefSaveAsAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingSaveAsFeReq 				= '<ps:property value="missingSaveAsFeReq_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var checkOptAxsSaveAsAlert 			= '<ps:property value="checkOptAxsSaveAsAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var saveAsArgProcSaved 				= '<ps:property value="saveAsArgProcSaved_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var saveAsRepLoadLAlert 			= '<ps:property value="saveAsRepLoadLAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready
(
	function() {
	if("${calledFrom}"=="1")
	{
		checkUpdDownIfCSV($("#format_" + _pageRef+"_${calledFrom}").val(),"_${calledFrom}");
	}
	
	$("#lookuptxt_appName_" + _pageRef+"_${calledFrom}").attr('readonly', true);
	$("#refId_"+ _pageRef+"_${calledFrom}").val("");
});

function checkSaveAsUpdDownIfCSV(val)
{
	checkUpdDownIfCSV(val,"_${calledFrom}");
}

function emptySaveAsUpDownPRefLkp()
{
	$("#pRefStr_" + _pageRef+"_${calledFrom}").val("");
	$("#lookuptxt_fcrPRef_" + _pageRef+"_${calledFrom}").val("");
	$("#refId_" + _pageRef+"_${calledFrom}").val("");
}

function upperSaveAsProgRef()
{
	var refStr = $("#refId_" + _pageRef+"_${calledFrom}").val();
	//Regular expression that not allow the user to insert arabic characters
	var re = new RegExp("[\u0600-\u06ff\ufb50-\ufdff\ufe70-\ufeff]", "g");
	refStr = refStr.replace(re, "")
	refStr = refStr.replace(" ", "");
	$("#refId_" + _pageRef+"_${calledFrom}").val(refStr.toUpperCase());
}	

function checkSaveAsProgRef(obj)				
{
	if ($("#refId_" + _pageRef+"_${calledFrom}").attr('readonly') == "readonly") {
		return;
	}

	var refStr = $("#" + obj.id).val();
	var repAppName = $("#lookuptxt_appName_" + _pageRef+"_${calledFrom}").val();
	if (refStr == "") {
		return;
	} else {
		var zSrc = "${pageContext.request.contextPath}/path/designer/upDownReport_checkProgRef.action";
		params = {};
		params["updates"] = refStr;
		params["update"] = repAppName;
		$.getJSON(zSrc, params, function(data2, status, xhr) {
			var retVal = data2['updates'];
			//check if the progRef exists in the irp_ad_hoc_report
				if (retVal != "0") // if the reference exist  
				{
					$("#" + obj.id).val("");
					_showErrorMsg(checkProgRefSaveAsAlert,error_msg_title,350,120);
				}
			});
	}
}

function saveAsReport()
{
	_showProgressBar(true);
	var skipOptAxsChk = document.getElementById("skipOptAxs_"+ _pageRef+"_${calledFrom}").checked;
	var repRef = $("#refId_"+ _pageRef+"_${calledFrom}").val();
	var appName = $("#lookuptxt_appName_"+ _pageRef+"_${calledFrom}").val();

	var skipPrefValidation="0";
	if (skipOptAxsChk==true) //if update or use existing opt
	{
		skipPrefValidation="1";
	}

	if (repRef == "") 
	{
		_showErrorMsg(missingSaveAsFeReq,error_msg_title,350,120);
		_showProgressBar(false);
		return;
	}
	
	var zSrc = "${pageContext.request.contextPath}/path/designer/upDownReport_checkProgRef.action";
	params = {};
	params["updates"] = repRef;
	params["update"] = appName;
	params["skipPrefValidation"]=skipPrefValidation;
	$.getJSON(zSrc, params, function(data2, status, xhr) {
		var retVal = data2['updates'];
		//check if the progRef exists 
			if (retVal != "0")  
			{
				$("#refId_"+ _pageRef+"_${calledFrom}").val("");
				_showErrorMsg(retVal);
				_showProgressBar(false);
			}
			else
			{
				if ("${calledFrom}"=="1" && skipOptAxsChk == true) 
				{
					var zSrc = "${pageContext.request.contextPath}/path/designer/upDownReport_checkIfSkipOptAxs.action";
					var params = {};
					params["updates"] = repRef;
					params["update"] = appName;
					$.post(zSrc,params,function(param) 
					{
						var isOptExist = param["update"];
						if (isOptExist == "-1") 
						{
							$("#refId_"+ _pageRef+"_${calledFrom}").val("");
							_showErrorMsg(checkOptAxsSaveAsAlert,error_msg_title,350,120);
							_showProgressBar(false);
							return;
						}
						else
						{
							doSaveAsReport();
						}
					});
				}
				else
				{
					doSaveAsReport();
				}
			}
		});
}

function doSaveAsReport()
{
	//check for empty inputs
	var repName = $("#repName_"+ _pageRef+"_${calledFrom}").val();
	var pRef = $("#lookuptxt_fcrPRef_"+ _pageRef+"_${calledFrom}").val();
	var repRef = $("#refId_"+ _pageRef+"_${calledFrom}").val();
	var appName = $("#lookuptxt_appName_"+ _pageRef+"_${calledFrom}").val();
	var repFormat = $("#format_"+ _pageRef+"_${calledFrom}").val();
	if (repName == "" || pRef == "" || repRef == "" || appName=="" || repFormat=="") 
	{
		_showErrorMsg(missingSaveAsFeReq,error_msg_title,350,120);
		_showProgressBar(false);
		return;
	}

	//from designer
	if("${calledFrom}"=="0")
	{
	
		_showConfirmMsg(saveAsRepLoadLAlert, info_msg_title, function(confirmcChoice, theArgs) {
				if (confirmcChoice) 
				{
					//fill the reportCO object with the data filled in the saveAs screen
					var zSrc = "${pageContext.request.contextPath}/path/designer/reportDesign_fillSaveAsDetails.action";
					var myObject = $("#downUpFrm_" + _pageRef+"_${calledFrom}").serializeForm();
					$.post(zSrc,myObject,function(param) 
					{
						//call the submitReport function of the Editor.jsp
						submitReport("saveAs");
						//close the dialog
						$('#saveAsRepDialog').dialog('close');
					});
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
			
			
		_showProgressBar(false);
	
	}
	//from upload download
	else
	{
		var zSrc = "${pageContext.request.contextPath}/path/designer/upDownReport_saveAsReport.action";
		var myObject = $("#downUpFrm_" + _pageRef+"_${calledFrom}").serializeForm();
		$.post(zSrc,myObject,function(param) 
		{
			var newRepId = param["update"];
			//reload the grid
			$("#upDownGrid_" + _pageRef).trigger("reloadGrid");
			//open the saved report
			loadTheReportAndForm(newRepId,repRef,appName)
			//show msg
			_showErrorMsg(saveAsArgProcSaved, info_msg_title, 300,100);			
			//close the dialog
			$('#saveAsDialog_'+_pageRef).dialog('close');
			_showProgressBar(false);
		    //reload menu
		ddaccordion
			.initRoot(
					"appMenu",
					"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
					false);
		});
	}

}

</script>

<ps:form id="downUpFrm_${_pageRef}_${calledFrom}"
	namespace="/path/designer" method="POST" enctype="multipart/form-data"
	useHiddenProps="true">
	<table width="100%" class="headerPortionContent ui-widget-content"
		>
		<tr>
			<td width="20%">
				<ps:text name="reportName"></ps:text>
			</td>
			<td>
				<ps:textfield name="repCO.REPORT_NAME"
					id="repName_${_pageRef}_${calledFrom}" maxlength="100" size="70" />
			</td>
		</tr>
		<tr>
			<td width="20%">
				<ps:text name="reporting.applicationName" />
			</td>
			<td nowrap="nowrap">
				<table width="50%">
					<tr>
						<td>
							<psj:livesearch
								actionName="${pageContext.request.contextPath}/pathdesktop/ApplicationsLookup_constructLookup.action?lang_spec=1"
								name="repCO.APP_NAME" id="appName_${_pageRef}_${calledFrom}"
								searchElement="APP_NAME" autoSearch="true"
								onOk="emptySaveAsUpDownPRefLkp()" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<ps:label value="%{getText('reporting.skipOptAxs')}" id="skipOptAxsLbl_${_pageRef}_${calledFrom}"></ps:label>
			</td>
			<td>
				<ps:checkbox name="repCO.SKIP_OPT_AXS"
					id="skipOptAxs_${_pageRef}_${calledFrom}" onchange="checkProgRefs('_1')"></ps:checkbox>
			</td>
		</tr>
		<tr>
			<td width="20%">
				<ps:text name="designer.reportReference" />
			</td>
			<td>
				<ps:textfield name="repCO.PROG_REF"
					id="refId_${_pageRef}_${calledFrom}" maxlength="12" size="70"
					onkeyup="upperSaveAsProgRef()"/>
			</td>
		</tr>

		<tr>
			<td width="20%">
				<ps:text name="reporting.parentRef" />
			</td>
			<td nowrap="nowrap">
				<table width="50%">
					<tr>
						<td nowrap="nowrap" width="25%">
							<psj:livesearch
								actionName="${pageContext.request.contextPath}/path/fcrRep/fcr_loadPRefLkp"
								name="repCO.PARENT_REF" id="fcrPRef_${_pageRef}_${calledFrom}"
								searchElement="" autoSearch="true"
								paramList="pRefLkp:lookuptxt_fcrPRef_${_pageRef}_${calledFrom},applName:lookuptxt_appName_${_pageRef}_${calledFrom}"
								resultList="PROG_REF:lookuptxt_fcrPRef_${_pageRef}_${calledFrom},PROG_DESC:pRefStr_${_pageRef}_${calledFrom}"
								parameter="applName:lookuptxt_appName_${_pageRef}_${calledFrom},updates:lookuptxt_fcrPRef_${_pageRef}_${calledFrom}"
								dependencySrc="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByPRefId"
								dependency="lookuptxt_fcrPRef_${_pageRef}_${calledFrom}:commonDetVO.CODE_STR,pRefStr_${_pageRef}_${calledFrom}:commonDetVO.BRIEF_DESC_ENG" />
						</td>
						<td>
							<ps:textfield name="repCO.PARENT_REF_STR"
								id="pRefStr_${_pageRef}_${calledFrom}" readonly="true"
								tabindex="-1"></ps:textfield>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="20%">
				<ps:label value="%{getText('designer.defaultFormat')}" id="formatLbl_${_pageRef}_${calledFrom}"></ps:label>
			</td>
			<td nowrap="nowrap">
				<table width="50%">
					<tr>
						<td>
							<ps:select id="format_${_pageRef}_${calledFrom}"
								name="repCO.DEFAULT_FORMAT" list="reportFormats"
								listKey="VALUE_CODE" listValue="VALUE_DESC" emptyOption="true"
								onchange="checkSaveAsUpdDownIfCSV(this.value)"></ps:select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="20%">
				<span id="sepLblTd_${_pageRef}_${calledFrom}"><ps:label id="sepValTdLbl_${_pageRef}_${calledFrom}"
						value="%{getText('reporting.csvSeparator')}" /> </span>
			</td>
			<td nowrap="nowrap">

				<table width="50%">
					<tr>
						<td>
							<span id="sepValTd_${_pageRef}_${calledFrom}"> <ps:select
									list="csvSeparators" listKey="code" listValue="description"
									name="repCO.CSV_SEPARATOR"
									id="csvSeparatorId_${_pageRef}_${calledFrom}" /> </span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>

			<div id="noHeadFootSpan_${_pageRef}_${calledFrom}">
				<td>
					<ps:label value="%{getText('reporting.langIndep')}"
						id="langIndepLbl_${_pageRef}_${calledFrom}"></ps:label>
				</td>
			<td>
				<table>
					<tr>
						<td>
							<ps:checkbox name="repCO.LANG_INDEPENDENT_YN"
								id="langIndep_${_pageRef}_${calledFrom}" valOpt="1:0"></ps:checkbox>
						</td>

						<td width="5%"></td>
						<td>
							<ps:label value="%{getText('reporting.noHeadAndFoot')}"
								id="noHeadAndFootLbl_${_pageRef}_${calledFrom}"></ps:label>
						</td>
						<td>
							<ps:checkbox name="repCO.SHOW_HEAD_FOOT"
								id="noHeadAndFoot_${_pageRef}_${calledFrom}"></ps:checkbox>
						</td>
					</tr>
				</table>
			</td>
			</div>
		</tr>
		<tr>
			<td width="20%">
				<ps:label value="%{getText('designer.defaultConnection')}" id="connectionLbl_${_pageRef}_${calledFrom}"></ps:label>
			</td>
			<td nowrap="nowrap">
				<table width="50%">
					<tr>
						<td>
							<ps:select id="connection_${_pageRef}_${calledFrom}"
								name="repCO.CONN_ID" list="connectionsList" listKey="CONN_ID"
								listValue="CONN_DESC" emptyOption="true"></ps:select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style ="display: none;">
		<td width="20%" >
		 <ps:label value="%{getText('sch.ms')}" />
		</td>
		<td nowrap="nowrap"  >
		   <table width = "50%">
		     <tr>
		      <td nowrap="nowrap" width="25%" >
			   <psj:livesearch id="msCode_${_pageRef}_${calledFrom}"
						name="repCO.MAIL_SERVER_CODE" mode="number"
						readOnlyMode="false" 
						actionName="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_msConfigLookup.action"
						searchElement="" 
						resultList="mailServerVO.HOST:msHost_${_pageRef}_${calledFrom},mailServerVO.MAIL_SERVER_CODE:lookuptxt_msCode_${_pageRef}_${calledFrom}"
						parameter="code:lookuptxt_msCode_${_pageRef}_${calledFrom}"					
						dependency="lookuptxt_msCode_${_pageRef}_${calledFrom}:mailServerCO.mailServerVO.MAIL_SERVER_CODE,msHost_${_pageRef}_${calledFrom}:mailServerCO.mailServerVO.HOST"
					 	dependencySrc="${pageContext.request.contextPath}/path/mailServerConfig/mailServerConfig_retMailServerDependency.action"
						>
					</psj:livesearch> 
			</td>
			<td>
				  <ps:textfield name="repCO.HOST" id="msHost_${_pageRef}_${calledFrom}"  readonly="true"> </ps:textfield>
				  
			</td>
		    </tr>
		  </table>
		 </td>
		</tr>
		
	</table>
</ps:form>

<div id="upDownToolBarDivId_${_pageRef}_${calledFrom}">
	<ptt:toolBar id="upDownToolBar_${_pageRef}_${calledFrom}">
		<psj:submit button="true" buttonIcon="ui-icon-disk"
			onclick="saveAsReport()">
			<ps:text name="reporting.save"></ps:text>
		</psj:submit>
	</ptt:toolBar>
</div>

