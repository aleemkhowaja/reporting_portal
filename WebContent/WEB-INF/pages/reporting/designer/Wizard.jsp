<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="resetQryDes_var" 			value="%{getEscText('wizard.resetQryDes')}"/>
<ps:set name="resetQryTitle_var" 		value="%{getEscText('wizard.resetQryTitle')}"/>
<ps:set name="resetStatQry_var" 		value="%{getEscText('wizard.resetStatQry')}"/>
<ps:set name="finish_key_var" 			value="%{getEscText('finish_key')}"/>
<ps:set name="next_key_var" 			value="%{getEscText('next_key')}"/>
<ps:set name="Back_key_var" 			value="%{getEscText('Back_key')}"/>
<ps:set name="selectRepFields_var" 		value="%{getEscText('reporting.selectRepFields')}"/>
<ps:set name="missingFe_var" 			value="%{getEscText('group.missingFe')}"/>
<ps:set name="reportName_var" 			value="%{getEscText('reportName')}"/>
<ps:set name="reportReference_var" 		value="%{getEscText('designer.reportReference')}"/>
<ps:set name="wizard_first_err_msg_var" value="%{getEscText('wizard_first_err_msg')}"/>
<ps:set name="missingQry_var"		 	value="%{getEscText('report.missingQry')}"/>
<ps:set name="newQuery_var"		 		value="%{getEscText('designer.newQuery')}"/>
<ps:set name="list_key_var"		 		value="%{getEscText('queries.list_key')}"/>
<ps:set name="syntax_key_var"		 	value="%{getEscText('query.new.syntax_key')}"/>
<ps:set name="static_key_var"		 	value="%{getEscText('query.new.static_key')}"/>

<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/js/plugins/jquery.form.wizard.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/js/plugins/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery/js/plugins/jquery.form.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/form-wizard/form-wizard.css">

<script type="text/javascript">
var resetQryDes 			= '<ps:property value="resetQryDes_var"  escapeHtml="false" escapeJavaScript="true"/>';  
var resetQryTitle 			= '<ps:property value="resetQryTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var resetStatQry 			= '<ps:property value="resetStatQry_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var finish_key 				= '<ps:property value="finish_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var next_key 				= '<ps:property value="next_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var Back_key				= '<ps:property value="Back_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectRepFields			= '<ps:property value="selectRepFields_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingFe				= '<ps:property value="missingFe_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reportName				= '<ps:property value="reportName_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var reportReference			= '<ps:property value="reportReference_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var wizard_first_err_msg	= '<ps:property value="wizard_first_err_msg_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingQry				= '<ps:property value="missingQry_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var newQuery				= '<ps:property value="newQuery_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var list_key				= '<ps:property value="list_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var syntax_key				= '<ps:property value="syntax_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var static_key				= '<ps:property value="static_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

//form wizard initialization
$(function()
{
	$.struts2_jquery.require("Wizard.js", null,jQuery.contextPath + "/path/js/reporting/designer/");	
	//set the default parent reference as Report designer
		var pRef=$("#lookuptxt_fcrPRef_"+_pageRef).val();
		$("#lookuptxt_appName_"+_pageRef).attr('readonly', true);
		if(!pRef || pRef=="")
		{
			var zSrc="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByPRefId.action?updates=R00";
			params ={};
			$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{
				var retVal=data2['commonDetVO']['BRIEF_DESC_ENG'];
					if(retVal=="-1")
					{
						$("#lookuptxt_fcrPRef_"+_pageRef).val("");
						$("#pRefStr_"+_pageRef).val("");
					}
					else
					{
						$("#pRefStr_"+_pageRef).val(retVal);
						$("#lookuptxt_fcrPRef_"+_pageRef).val("R00");
						$("#lookuptxt_appName_"+_pageRef).val(data2['commonDetVO']['APPL_NAME']);
					}
			}); 

			
		}
	
	$("#reportWizardForm").formwizard({ 
	 	formPluginEnabled: true,
	 	validationEnabled: true,
	 	focusFirstInput : true,
	 	textSubmit : finish_key,
	 	textNext : next_key,
	 	textBack : Back_key,
    	formOptions :{	 
			beforeSubmit : function(){
				var recCnt = $("#displFieldsGrid").jqGrid('getGridParam','reccount');
				if( recCnt == 0 )
				{
					_showErrorMsg(selectRepFields);	
					return false;
				}
			}, 	
			success : function(json){
		 				//submitWizard();
		 				checkEditorLoadingBfrSubmit();
					},
			dataType: 'json',
			resetForm: true					
	 	},
 	 
 	    remoteAjax:
	 		{
	 		    "firstStep":
	 		    {
	 		        // add a remote ajax call when moving next from the first step
	 		        type: "POST",
	 		        dataType: "json",
	 		        url: "${pageContext.request.contextPath}/path/designer/wizard_checkReportInfo.action",
	 		        success: function(json)
	 		        {   
	 		        	var result;
						var msg = '';		 		 		        

						//check if emptyyy
						var wizRepName=document.getElementById("wizRepName").value;
						var wizRef= document.getElementById("refId").value;	 		        
						var wizPRef=document.getElementById("lookuptxt_fcrPRef_"+_pageRef).value;
						var appName=document.getElementById("lookuptxt_appName_"+_pageRef).value;
						if(wizRepName=="" || wizRef=="" || wizPRef=="" || appName=="")
						{
							result = false;
							$("#status").html(missingFe);
						}
								 						 				
						else if(!json['repNameExists'] && !json['refExists'])
	 		        	{
	 		        		$("#status").html("");
			 		    	  result = true;
	 		        	}
	 		        	else{
	 		        		result = false;
		 		    		if(json['repNameExists']){
		 		    			msg += reportName;		 		 		        
		 					}
		 					if(json['refExists']){
		 						if(msg != '')
			 						msg += ', ';
		 						msg += reportReference;		 		 		        
		 						
		 					}
		 					$("#status").html(wizard_first_err_msg +msg);
	 		        	}
	 		        	if (json['update'] != "0") 
						{
							$("#refId").val("");
							_showErrorMsg(json['update']);
						}
	 		    	  return result;
	 		        }
	 			},
	 			"secondStep":
	 		    {
	 		        // add a remote ajax call when moving next from the second step
	 		        type: "POST",
	 		        dataType: "json",
	 		        url: "${pageContext.request.contextPath}/path/designer/wizard_checkQuery.action",
	 		        success: function(json)
	 		        {   
						//check if query is missing						
						if(json['missingQry'])
						{
							$("#status").html(missingQry);
						}
						else {
							$("#status").html("");
						}
	 		    	  	return !json['missingQry'];
	 		        }
	 			}
	 		}
		 } 
	 
	);
});



</script>
</head>

<body>

<ps:form id="reportWizardForm" action="wizard_submitWizard.action" namespace="/path/designer">  
<div id="wizardDiv" class="frm-wizard-container">
  <div style="height: 70%">
    <div id="firstStep" class="step">
	 <ps:label cssClass="frm-wizard-label" key="wizard.StepOne" cssStyle="font: bold 11pt Tahoma !important;"></ps:label>
	  <br/>
	  <hr/>
	  <table width="100%" height="50%">
	  <tr><td>
	  <!-- 
	  <table width="100%" class="headerPortionContent ui-widget-content ui-helper-reset" border="0">
	   -->
	  <table width="100%" class="frm-wizard-table" cellspacing="10">
	    <tr>
			<td width="17%">
				<ps:text name="reportName"></ps:text>
			</td>
			<td>
				<ps:textfield name="reportName" id="wizRepName" tabindex="1" maxlength="100" size="70"/>
			</td>
	    </tr>
	    <tr>
			<td width="17%">
				<ps:text name="designer.reportReference" />
			</td>
			<td>
				<ps:textfield name="reference" id="refId"  tabindex="2" maxlength="9" size="70" onkeyup="convertToUpperCase()"/>
			</td>
	    </tr>
	    <tr>
	    	<td width="10%">
				<ps:text name="reporting.applicationName" />
			</td>
			<td nowrap="nowrap">
			<table width="50%">
				<tr>
					<td>
					<psj:livesearch 
						actionName="${pageContext.request.contextPath}/pathdesktop/ApplicationsLookup_constructLookup.action?lang_spec=1"
						name="applicationName"
						id="appName_${_pageRef}"
				        searchElement="APP_NAME" 
				        autoSearch="true" 
				        onOk="emptyPRefLkp()" 
			        />	
					</td>
				</tr>
			</table>
			 </td>
	    </tr>       
	    <tr>
			<td width="17%">
				<ps:text name="reporting.parentRef" />
			</td>
			<td nowrap="nowrap">
			  <table width="80%">
				<tr>
					<td nowrap="nowrap" width="15%">
						<psj:livesearch actionName="${pageContext.request.contextPath}/path/fcrRep/fcr_loadPRefLkp"
							name="PARENT_REF" id="fcrPRef_${_pageRef}"
					        searchElement="" autoSearch="true" 
					        paramList="pRefLkp:lookuptxt_fcrPRef_${_pageRef},applName:lookuptxt_appName_${_pageRef}"
					        resultList="PROG_REF:lookuptxt_fcrPRef_${_pageRef},PROG_DESC:pRefStr_${_pageRef}" 
					        parameter="updates:lookuptxt_fcrPRef_${_pageRef},applName:lookuptxt_appName_${_pageRef}"
					        dependencySrc ="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByPRefId"
							dependency="lookuptxt_fcrPRef_${_pageRef}:commonDetVO.CODE_STR,pRefStr_${_pageRef}:commonDetVO.BRIEF_DESC_ENG"
					    />	
					</td>
					<td><ps:textfield name="PARENT_REF_STR" id="pRefStr_${_pageRef}" cssStyle="width:70%" readonly="true"></ps:textfield></td>
				</tr>
			</table>
		 </td>
	    </tr>
        </table>
        </td></tr>
        </table>
	  <br/>
    </div>
    <div id="secondStep" class="step">
	 <ps:label cssClass="frm-wizard-label" key="wizard.StepSecond" cssStyle="font: bold 11pt Tahoma !important;"></ps:label>
      <br/>
	  <hr/>
	  <br/>
	  <table cellspacing="10" style="margin-left: 10">
	   <tr>
			<td>
				<psj:a cssClass="fg-button ui-state-default  ui-corner-all" onclick="return showQueryDesigner();">
					<ps:text name="%{getText('reporting.qryDesign')}"></ps:text>
				</psj:a>				  
			</td>
			<td>
				<psj:a cssClass="fg-button ui-state-default  ui-corner-all" onclick="return staticQry();">
				    <ps:text name="%{getText('reporting.qrySyntax')}"></ps:text>
				</psj:a>
			</td>						
			<td>
				<psj:a cssClass="fg-button ui-state-default  ui-corner-all" onclick="return openQueriesList();">
					<ps:text name="%{getText('reporting.loadQry')}"></ps:text>
				</psj:a>
			</td>
			<td>
				<psj:a cssClass="fg-button ui-state-default  ui-corner-all" onclick="return resetQry();">
				    <ps:text name="%{getText('reporting.resetQry')}"></ps:text>
				</psj:a>
			</td>
		</tr>
	  </table>
	  <table width="100%">
	   <tr>
		<td align="center">
			<ps:textarea 
				cssStyle="height: 100%;"
					id="syntax_${_pageRef}" 
					name="syntax" 
					rows="10" 
					cols="90" 
					readonly="true"
			/>	
		</td>
	   </tr>	    
	  </table>
	</div>
	<div id="thirdStep" class="step">
	 <ps:label cssClass="frm-wizard-label" key="wizard.StepThird" cssStyle="font: bold 11pt Tahoma !important;"></ps:label>
	  <br/>
	  <hr/>
	  <table width="100%" height="50%">
	  <tr><td>
	  	<table width="100%" class="frm-wizard-table" cellspacing="10">
		    <tr>
				<td width="15%" valign="top">
					<ps:text name="designer.reportLayout"></ps:text>
				</td>
				<td>
					<ps:radio id="repLayout" list="repLayout" listKey="VALUE_CODE" listValue="VALUE_DESC" name="reportLayout" value="1"></ps:radio>
				</td>
		    </tr>
        </table>
        <%-- 
        <table id="tblSize" width="100%" class="frm-wizard-table" cellspacing="5">
        	<tr>
		    	<td width="30%" align="right">
		    		<ps:text name="designer.columns"></ps:text>
		    	</td>
		    	<td align="left">
		    		<ps:textfield name="columnsNbr" size="5" maxlength="2" mode="number" nbFormat="##" 
		    			minValue="1" cssClass="required">
		    		</ps:textfield>
		    	</td>
		    </tr>
		    <tr>
		    	<td width="30%" align="right">
		    		<ps:text name="designer.rows"></ps:text>
		    	</td>
		    	<td align="left">
		    		<ps:textfield name="rowsNbr" size="5" maxlength="2" mode="number" nbFormat="##" 
		    			maxValue="20" minValue="1" cssClass="required">
		    		</ps:textfield>
		    	</td>
		    </tr>
        </table>
         --%>
      </td></tr>
      </table>
	  <br/>
    </div>
	
	<div id="fourthStep" class="step">
	 <ps:label cssClass="frm-wizard-label" key="wizard.StepFourth" cssStyle="font: bold 11pt Tahoma !important;"></ps:label>
      <br/>
	  <hr/>
	  <table width="100%" height="80%">
	    <tr>
	   	  <td valign="top">
			<jsp:include page="query/ChooseFields.jsp"></jsp:include>
		  </td>
	    </tr>
	  </table>
      <br/>
	</div>
    </div>
    <hr/>
    <div id="navigationbtn" class="frm-wizard-navigation"> 							
	    <input class="frm-wizard-navigation_btn" id="back" value="Back" type="reset" />
		<input class="frm-wizard-navigation_btn" id="next" value="Next" type="submit" />
    </div>
    <hr/>
    <p id="status" class="formErrorContent"></p>
 </div>
</ps:form>

</body>
<script>

   $(document).ready(function () 
	{
		preventSpecialCharacters("refId");
	});
</script>
</html>