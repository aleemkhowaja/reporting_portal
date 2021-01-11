<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>


<ps:set name="override_snp_key_var" 			value="%{getEscText('override_snp_key')}"/>
<ps:set name="snp_declared_key_var" 			value="%{getEscText('snp_declared_key')}"/>
<ps:set name="missingAodParam_var" 			value="%{getEscText('snp.missingAodParam')}"/>
<script type="text/javascript">
var override_snp_key_alert= '<ps:property value="override_snp_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var snp_declared_key_alert= '<ps:property value="snp_declared_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingAodParam_alert= '<ps:property value="missingAodParam_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


$(document).ready(function() {
	$("#saveSnpShtFrm_${htmlPageRef}").processAfterValid("saveRepSnapshot");
});

function saveRepSnapshot()
{
	
	var asDtParamName=$("#snpAsOfDate_${htmlPageRef}").val();
	if(document.getElementById("p_param_"+asDtParamName+"_DATE_${htmlPageRef}")!=null || document.getElementById("snpAOD_${htmlPageRef}")!=null)
	{
		var aodVal;
		if(document.getElementById("p_param_"+asDtParamName+"_DATE_${htmlPageRef}")!=null )
		{
			 aodVal=document.getElementById("p_param_"+asDtParamName+"_DATE_${htmlPageRef}").value;
		}
		else
		{
			 aodVal= document.getElementById("snpAOD_${htmlPageRef}").value;
		}
		if(aodVal=="")
		{
			_showErrorMsg(missingAodParam_alert, missing_elt_msg_key, 300, 100);
		}
		else
		{
		
			//go ajax and check if the snapshot already saved for the selected frequency , if already saved return the snpRepId and set it
			//in the screen in order to be used on update	
		/*	var url = jQuery.contextPath+ "/path/snapshotParameter/SnapshotParameterMaintAction_checkIfOverrideSnp";
			var params = {};
			params["updates"]=  $("#lookuptxt_snpFreq_${htmlPageRef}").val();
			params["aod"]=aodVal;
			params["snpId"]= $("#snpId_${htmlPageRef}").val();
			$.ajax( {
				url : url,
				type : "post",
				dataType : "json",
				data : params,
				success : function(param) {
					if (typeof param["_error"] == "undefined"
							|| param["_error"] == null) 
					{
						var repSnpId=param["repSnpId"];
						var declared=param["updates"];
						if(declared=="Y")
						{
							_showErrorMsg(snp_declared_key_alert, error_msg_title, 300, 100);
						}
						else if(repSnpId!=null)
						{
							_showConfirmMsg(
								override_snp_key_alert,
								info_msg_title,
								function(confirmcChoice, theArgs) {
									if (confirmcChoice) 
									{
											$("#repSnpId_${htmlPageRef}").val(repSnpId);
											refreshRepMenuData("${htmlPageRef}",2)
											$('#saveSnpShtDlg_${htmlPageRef}').dialog('close');
									}
								}, {}, yes_confirm, no_confirm, 300, 100);
						}
						else
						{*/
							refreshRepMenuData("${htmlPageRef}",2)
							$('#saveSnpShtDlg_${htmlPageRef}').dialog('close');
						
					/*	}
					}
				}
			});*/
		}
	}
	//the specified parameter does not exist in the main screen
	else
	{
		_showErrorMsg(missingAodParam_alert, missing_elt_msg_key, 300, 100);
	}
	


}

/*function fillDefaultDescription()
{
		var asDtParamName=$("#snpAsOfDate_${htmlPageRef}").val();
		if(document.getElementById("p_param_"+asDtParamName+"_DATE_${htmlPageRef}")!=null || document.getElementById("snpAOD_${htmlPageRef}")!=null)
		{
			var aodVal;
			if(document.getElementById("p_param_"+asDtParamName+"_DATE_${htmlPageRef}")!=null )
			{
				 aodVal=document.getElementById("p_param_"+asDtParamName+"_DATE_${htmlPageRef}").value;
			}
			else
			{
				 aodVal= document.getElementById("snpAOD_${htmlPageRef}").value;
			}
			if(aodVal!="")
			{
				var url = jQuery.contextPath+ "/path/snapshotParameter/SnapshotParameterMaintAction_checkIfOverrideSnp";
				var params = {};
				params["updates"]=  $("#lookuptxt_snpFreq_${htmlPageRef}").val();
				params["aod"]=aodVal;
				params["snpId"]= $("#snpId_${htmlPageRef}").val();
				$.post(url, params, function(param)
				 {
					 $("#snpDesc_${htmlPageRef}").val(param["updates1"]);
				});
			}
			else
			{
			  $("#snpDesc_${htmlPageRef}").val("");
			}	
		}
		else
		{
		  $("#snpDesc_${htmlPageRef}").val("");
		}	
}*/

</script>

<ps:form id="saveSnpShtFrm_${htmlPageRef}" useHiddenProps="true" validate="true">
	<table width="100%" class="headerPortionContent ui-widget-content"
		cellspacing="10">

		<tr>
			<td>
				<ps:label key="snapshots.frequency" for="lookuptxt_snpFreq_${htmlPageRef}"></ps:label>
			</td>
			<td>
				<psj:livesearch id="snpFreq_${htmlPageRef}" 
					name="snpInfoCO.SNP_FRQ" mode="text" 
					readOnlyMode="false"
					actionName="${pageContext.request.contextPath}/path/snapshotParameter/SnapshotParameterListAction_snpFreqLookup.action"
					searchElement="" 
					paramList="code:_pageRef"
					resultList="repSnapshotParamVO.REP_ID:snpId_${htmlPageRef},repSnapshotParamVO.SNAPSHOT_FREQUENCY:lookuptxt_snpFreq_${htmlPageRef},AS_OF_DATE_PARAM_NAME:snpAsOfDate_${htmlPageRef}">
				</psj:livesearch>
			</td>
		</tr>

		<tr>
			<td>
				<ps:label key="reporting.description" for="snpDesc_${htmlPageRef}"></ps:label>
			</td>
			<td>
				<ps:textfield id="snpDesc_${htmlPageRef}"
					name="snpInfoCO.snpInfoVO.DESCRIPTION" maxlength="100" />
			</td>
		</tr>
		
		<tr>
			<td>
				<ps:label key="	reporting.asOfDate" for="snpAOD_${htmlPageRef}" id="snpAodLbl_${htmlPageRef}"></ps:label>
			</td>
			<td>
				<psj:datepicker name="snpInfoCO.snpInfoVO.RETREIVE_DATE"
					id="snpAOD_${htmlPageRef}" timepickerAmPm="false" 
					buttonImageOnly="true" size="50">
				</psj:datepicker>
			</td>
		</tr>
		
	</table>
	
	 <p id="snpId_${htmlPageRef}" name="repSnapshotParamVO.REP_ID" />
	 <ps:hidden id="repSnpId_${htmlPageRef}" name="repSnapshotParamVO.REP_SNP_ID" />
	 <ps:hidden id="snpAsOfDate_${htmlPageRef}" name="AS_OF_DATE_PARAM_NAME" />
	
	
	<ptt:toolBar id="saveSnpTlB_${htmlPageRef}">
		<psj:submit button="true" buttonIcon="ui-icon-disk">
			<ps:label key="reporting.save"></ps:label>
		</psj:submit>
	</ptt:toolBar>
	
	
</ps:form>


