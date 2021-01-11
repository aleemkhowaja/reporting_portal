function addNewMailServer() {
	_showProgressBar(true);
	if ($("#mailServerForm_" + _pageRef).hasChanges()) {
		//check password
		var pass = $("#msPass_" + _pageRef).val();
		var confPass = $("#msConfPass_" + _pageRef).val();
		var oldPass = $("#oldMsPass_" + _pageRef).val();
		var mailServerCode = $("#MAIL_SERVER_CODE_"+_pageRef).val();
		
		if (pass != confPass && (typeof oldPass == "undefined" || pass != oldPass)) {
			_showProgressBar(false);
			$("#msConfPass_" + _pageRef).val("");
			_showErrorMsg(diffMSPass, error_msg_title, 300, 100);
			return;
		}
		
		if (!checkMailSyntax()) {
			_showProgressBar(false);
			return;
		}
		//save /update
		var url = jQuery.contextPath
				+ "/path/mailServerConfig/mailServerConfig_saveMailserver";
		var myObject = $("#mailServerForm_" + _pageRef).serializeForm();

		$
				.ajax( {
					url : url,
					type : "post",
					dataType : "json",
					data : myObject,
					success : function(param) {
						if (typeof param["_error"] == "undefined"
								|| param["_error"] == null) {
							//reload grid
							$("#msGridId_" + _pageRef).trigger("reloadGrid");
							//empty form
							$("#msMaintDiv_" + _pageRef)
									.load(
											jQuery.contextPath
													+ "/path/mailServerConfig/mailServerConfig_emptyMsForm.action?_pageRef="
													+ _pageRef);
								callDependency(	"msSslEnabled_"+_pageRef+":mailServerCO.mailServerVO.SSL_ENABLE_YN",
									jQuery.contextPath+'/path/mailServerConfig/mailServerConfig_showHideSslPortDependency',
									"updates:0",
									"msSslEnabled",
									"")
						_showProgressBar(false);
						}
					}
				});
	} else {
		_showProgressBar(false);
		emptySmForm()
	}
}

function emptySmForm() {
	//empty form
	if ($("#mailServerForm_" + _pageRef).hasChanges())
	{
		_showConfirmMsg(
			Confirm_Proceed_key,
			proceed_msg_title,
			function(confirmcChoice, theArgs)
			{
				if (confirmcChoice)
				{
					mailserver_clearMsForm();
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
	} 
	else 
	{
		mailserver_clearMsForm();
	}

}

function mailserver_clearMsForm()
{
		var url = jQuery.contextPath
			+ "/path/mailServerConfig/mailServerConfig_emptyMsForm";
	var params = {};
	params["_pageRef"] = _pageRef;
	$.post(url, params, function(param) {
		$("#msMaintDiv_" + _pageRef).html(param);
	}, "html");
}

function loadMailServerForm() 
{
	if ($("#mailServerForm_" + _pageRef).hasChanges())
	{
		_showConfirmMsg(
			Confirm_Proceed_key,
			proceed_msg_title,
			function(confirmcChoice, theArgs)
			{
				if (confirmcChoice)
				{
					mailserver_loadMailServerForm();
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
	} 
	else 
	{
		mailserver_loadMailServerForm();
	}
}

function mailserver_loadMailServerForm() {
	msId = $("#msGridId_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var url = jQuery.contextPath
			+ "/path/mailServerConfig/mailServerConfig_retMailServerById";
	params = {};
	params["code"] = msId;
	params["_pageRef"] = _pageRef;
	$.post(url, params, function(param) {
		$("#msMaintDiv_" + _pageRef).html(param);
		$("#msConfPassLabel_"+_pageRef).css("display","none");
		$("#msConfPass_"+_pageRef).css("display","none");
		$("#msConfPass_"+_pageRef).removeAttr("required");
	}, "html");
}

function confirmOnDelete(){
	_showConfirmMsg(
			deleteConfirm,
			deleteTitle,
			function(confirmcChoice, theArgs) {
				if (confirmcChoice) {
					var url = jQuery.contextPath
							+ "/path/mailServerConfig/mailServerConfig_deleteMailServer";
					msId = $("#msGridId_" + _pageRef).jqGrid('getGridParam',
							'selrow');
					params = {};
					params["code"] = msId;
					params["_pageRef"] = _pageRef;
					$.post(url, params, function(param) {
						$("#msMaintDiv_" + _pageRef).html(param);
						//reload grid
							$("#msGridId_" + _pageRef).trigger("reloadGrid");
						}, "html");
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
}

function checkHost() {
	var regex = new RegExp("^[0-9_.]+$");
	var hostStr = $("#msHost_" + _pageRef).val();
	if (!regex.test(hostStr)) {
		$("#msHost_" + _pageRef).val(hostStr.substring(0, hostStr.length - 1))
	}
}

function testSendMail() {
	//check for required fields
	_showProgressBar(true);
	var msFrom = $("#msFrom_" + _pageRef).val();
	var msTo = $("#msTo_" + _pageRef).val();
	var msHost = $("#msHost_" + _pageRef).val();
	var msPort = $("#msPort_" + _pageRef).val();
	var msUsrName = $("#msUserName__" + _pageRef).val();
	var msPass = $("#msPass_" + _pageRef).val();

	if (msFrom == "" || msTo == "" || msHost == "" || msPort == ""
			|| msUsrName == "" || msPass == "") {
		_showErrorMsg(missingReqInputs, error_msg_title, 300, 100);
		_showProgressBar(false);
		return;
	}
	var url = jQuery.contextPath
			+ "/path/mailServerConfig/mailServerConfig_testMailserver";
	var myObject = $("#mailServerForm_" + _pageRef).serializeForm();

	$.ajax( {
		url : url,
		type : "post",
		dataType : "json",
		data : myObject,
		success : function(param) {
			if (typeof param["_error"] == "undefined"
					|| param["_error"] == null) {
				_showErrorMsg(mailSentMsg, info_msg_title, 300, 100);
				_showProgressBar(false);
			}
			else
			{
				_showProgressBar(false);
			}
		}
	});
}

function checkMailSyntax() {
	var email = $("#msMailFrom_" + _pageRef).val();
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	if (!filter.test(email)) {
		_showErrorMsg(invalidMailAlert);
		return false;
	} else {
		return true;
	}
}
function deleteMailServer() 
{
    msId = $("#msGridId_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var url = jQuery.contextPath+ "/path/mailServerConfig/mailServerConfig_checkMailUsage";	
	params = {};
	params["code"] = msId;
  	$.post(url, params, function(param) 
  	{  		
		var count = param["updates"];  
		if (count>0)
		{		
			_showErrorMsg(count+" " +usedMailServer, error_msg_title, 300, 100);		
		}
		else 
		{	
			confirmOnDelete();
		}
	});

}

function rep_mailserver_readyFunc()
{
	var updatesVal="0";
	if($("#msSslEnabled_"+_pageRef).is(":checked"))
	{
		updatesVal = "1";
	}
	callDependency(	"msSslEnabled_"+_pageRef+":mailServerCO.mailServerVO.SSL_ENABLE_YN",
									jQuery.contextPath+'/path/mailServerConfig/mailServerConfig_showHideSslPortDependency',
									"updates:'"+updatesVal+"'",
									"msSslEnabled_"+_pageRef,
									"")
	$("#mailServerForm_" + _pageRef).processAfterValid("addNewMailServer");
}

function rep_mailserver_listReadyFunc()
{
	$("#msGridId_"+_pageRef).subscribe(
					'emptyMailServerTrx',
					function(event, data) {
						$("#mailServerForm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("");
						$("#mailServerForm_"+_pageRef+" #auditObj_"+_pageRef).val("");
					});
}

function rep_mailserver_onChangePassword()
{
	var oldPass = $("#oldMsPass_" + _pageRef).val();
	var msPass = $("#msPass_" + _pageRef).val();
	var mailServerCode = $("#MAIL_SERVER_CODE_"+_pageRef).val();
	
	
	if(mailServerCode != "" && typeof mailServerCode != "undefined")
	{
		
		if(msPass != "" && msPass != oldPass)
		{
			$("#msConfPassLabel_"+_pageRef).css("display","inline");
			$("#msConfPass_"+_pageRef).css("display","inline");
			$("#msConfPass_"+_pageRef).attr("required", "true");
		}
		else
		{
			$("#msConfPassLabel_"+_pageRef).css("display","none");
			$("#msConfPass_"+_pageRef).css("display","none");
			$("#msConfPass_"+_pageRef).removeAttr("required");
		}
	}
}