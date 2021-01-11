function rep_glList_readyFunc() {
	$("#compCodeGlLookUp_" + _pageRef).attr('readonly', true);
	$("#branchCodeGlLookUp_" + _pageRef).attr('readonly', true);
	$("#fromGlLookUp_" + _pageRef).attr('readonly', true);
	$("#toGlLookUp_" + _pageRef).attr('readonly', true);
	$("#divCodeLookUp_" + _pageRef).attr('readonly', true);
	$("#deptCodeLookUp_" + _pageRef).attr('readonly', true);
	//get the crtLineCount from the server side cs we didn't reload the crt from each time we select a line

	$("#glGrids_" + _pageRef)
			.subscribe(
					'glGrdComplete',
					function(event, data) {

						if (reloadGLGrd && glToSel != -1) {
							//	select the last selected row after reloading the grid
							$("#glGrids_" + _pageRef).jqGrid("setSelection",
									glToSel, false);
							reloadGLGrd = false;
							glToSel = -1;
						}

						//enable/disable tabs

						var lineCount = $("#glGrids_" + _pageRef).jqGrid(
								'getGridParam', 'records');
						var isDisabledExprTab = $.inArray(2, $("#tabs").tabs(
								"option", "disabled"))
						if (lineCount > 0 && isDisabledExprTab == "-1")//when adding the fist record to the grid 
						{
							$("#tabs").tabs( {
								disabled : [ 2 ]
							});
						}

						var tempCodeWithLineNb = $(
								"#tempCodeWithLineNb_" + _pageRef).val();
						if (tempCodeWithLineNb != null
								&& tempCodeWithLineNb != "0~0") {
							_showProgressBar(true)
							var zSrc = jQuery.contextPath
									+ "/path/templateMaintReport/checkIfExprLine.action?_pageRef="
									+ _pageRef + "&templateCode="
									+ tempCodeWithLineNb.split("~")[0]
									+ "&lineNumber="
									+ tempCodeWithLineNb.split("~")[1];
							params = {};
							$
									.getJSON(
											zSrc,
											params,
											function(data2, status, xhr) {
												var retVal = data2['exprOrGLByLineCO']['exprOrGLByLine'];//confirm(retVal+":in gllist.jsp")
												crtLineCount = 0;
												for ( var i = 0; i < retVal.length; i++) {
													if (retVal[i] == "1") {
														crtLineCount = 1;
														break;
													}
												}

												if (lineCount == 0
														&& crtLineCount == 0
														&& isDisabledExprTab == "0") //when deleting the last record
												{
													$("#tabs")
															.tabs('enable', 2);
												}
												_showProgressBar(false)
											});
						}

					});
}

function addGL() {
	hideSelecAccTemplate(0);
	//for calculation type RTV
	var allRowIds =  $("#glGrids_"+_pageRef).jqGrid('getDataIDs');
	if(allRowIds.length==1)
	{
		rowid = allRowIds[0];
		myObject =$("#glGrids_"+_pageRef).jqGrid('getRowData',rowid);
		if(myObject["glstmpltGlsDetVO.CALC_TYPE"]=='RTV')
		{
			_showErrorMsg(calcRateValExists)
			return;
		}
	}
	lineGlRadioChange();
	if ($("#templCode_"+_pageRef).val() == "") {
		_showErrorMsg(selectLineAlert);
		return;
	} 
	var tempCodeWithLineNb = $("#tempCodeWithLineNb_"+_pageRef).val();
	if (tempCodeWithLineNb == "0~0") {
		_showErrorMsg(selectLineAlert);
		return;

	}
	//expand the collapsable div
	var collapseDiv = $("#GLDivId > .collapsibleContainerTitle").get(0);
	if ($(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
		$(collapseDiv).trigger("click");
	}

	if(shouldIncrement==0)
	{
		if(returnedNewLineNumber==0)//for the case where user did not yet click on the + and have tried previously to modify another line
		{
			userClickedOk=1;
			shouldIncrement=1;
		}
		else
		$("#subLineNb_"+_pageRef).val(returnedNewLineNumber);
		
	}
	else if(shouldIncrement==1 && $("#subLineNb_"+_pageRef).val()=="")
	{
		$("#subLineNb_"+_pageRef).val(returnedNewLineNumber);
	}
	 emptyGLForm("");
}

function saveGLButton()
{		//checking if currency included only in calc RTV.IF no, cannot add a Rate value Calctype
		var allRowIds =  $("#glGrids_"+_pageRef).jqGrid('getDataIDs');
		if((allRowIds.length==1 && $("#calcTypeComboId_"+_pageRef).val()=="RTV" && $("#modeGL_"+_pageRef).val()=="add")
				|| (allRowIds.length>1 && $("#calcTypeComboId_"+_pageRef).val()=="RTV"))
		{
			_showErrorMsg(rateValAlone)
			return;
		}
		//now checking the criteria if containing other than 1 row having currency included only
		checkCriteriaIsCurrency()
			//end checking
}

function checkCriteriaIsCurrency()
{
	if( $("#calcTypeComboId_"+_pageRef).val()!="RTV")
	{
		//end checking
		var subLineNb = document.getElementById("subLineNb_" + _pageRef).value;
		if(returnedNewLineNumber!=subLineNb)
			shouldIncrement=0;
		else
			shouldIncrement=1;
		if (subLineNb != "" && subLineNb != "0") 
		{
			saveGL("edit", null);
		}
		return;
	}	
	if($("#calcTypeComboId_"+_pageRef).val()=="RTV")
	{
		var allRowIds =  $("#glGrids_"+_pageRef).jqGrid('getDataIDs');
		if(allRowIds!=null && allRowIds.length>1 && $("#modeGL_"+_pageRef).val()=="add")
		{
				_showErrorMsg(rateValAlone)
				return;
		}
	}
	
	var tempCodeWithLineNb = $("#tempCodeWithLineNb_"+_pageRef).val();
	var url = jQuery.contextPath+ "/path/templateMaintReport/checkCriteriaCurrencyIncluded";
	params = {};
	params["tempCodeWithLineNb"]=tempCodeWithLineNb;
	params["_pageRef"]=_pageRef;
		$.ajax({
		 url: url,
	     type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
		   if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   {
				if(param["updates"]=="0" && ($("#calcTypeComboId_"+_pageRef).val() == "RTV"))
				{
					_showErrorMsg(criteriaRequired);		
				}
				else if(param["updates"]=="1") 
				{
					//end checking
					var subLineNb = document.getElementById("subLineNb_" + _pageRef).value;
					if(returnedNewLineNumber!=subLineNb)
						shouldIncrement=0;
					else
						shouldIncrement=1;
					if (subLineNb != "" && subLineNb != "0") 
					{
						saveGL("edit", null);
					}
				}			
		   }
		 }
	});
}

function deleteGL(rowid) {
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice,
			theArgs) {
		if (confirmcChoice) {
			deleteTheGl(theArgs.rowid)
		}
	}, {
		rowid : rowid
	});
}

function deleteTheGl(rowid) {
	_showProgressBar(true)
	var url = jQuery.contextPath + "/path/templateMaintReport/deleteGL?_pageRef="+_pageRef;
	myObject = $("#glGrids_"+_pageRef).jqGrid('getRowData', rowid);
	/*params = {};
	paramStr = JSON.stringify(myObject)
	paramStr = "{" + "glCO:" + paramStr + "}";
	params["updates"] = paramStr;*/
	$.get(url, myObject, function(param) {
		//reinitialize the control vars
		$("#subLineNb_"+_pageRef).val("");
    	userClickedCrtOk = 1;
	    shouldIncrementCrt = 1;
 		returnedNewLineNumberCriteria = 0;
 		userClickedOk = 1;//to know if we should increment the sub line number or not
 		shouldIncrement = 1;
 		returnedNewLineNumber = 0;
 		selectedLineGrid = 0;
		$("#glGrids_"+_pageRef).trigger("reloadGrid");
		hideSelecAccTemplate(0);
		_showProgressBar(false)
	});

	emptyGLForm("deleteGL");
}

function checkFromToGl(zDest) {
	var fromGL = $("#lookuptxt_fromGLCode_" + _pageRef).val();
	var toGL = $("#lookuptxt_toGLCode_" + _pageRef).val();
	var diffGL = toGL - fromGL
	var fromGlName = $("#fromGlLookUp_" + _pageRef).val();
	var toGlName = $("#toGlLookUp_" + _pageRef).val();
		
	var fromGLId = $("#lookuptxt_fromGLCode_" + _pageRef);
	var fromGlNameId = $("#fromGlLookUp_" + _pageRef);
	var toGLId = $("#lookuptxt_toGLCode_" + _pageRef);
	var toGlNameId = $("#toGlLookUp_" + _pageRef);
	
	if (zDest == 1)//from
	{
		if (fromGlName == "" && fromGL != "") {
			_showConfirmMsg(invalid_GL, Warning_key, function(confirmcChoice,
					theArgs) {
				if (!confirmcChoice) {
					fromGLId.val("");
				} else {
					if (toGL != "" && diffGL < 0) {
						compareFromToGL(fromGLId,fromGlNameId);
					}
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
		} else {
			if (toGL != "" && diffGL < 0) {
				compareFromToGL(fromGLId,fromGlNameId);
			}
		}

	} else if (zDest == 2)//check if from < to
	{
		if (toGlName == "" && toGL != "") {

			_showConfirmMsg(invalid_GL, Warning_key, function(confirmcChoice,
					theArgs) {
				if (!confirmcChoice) {
					toGLId.val("");
				} else {
					if (fromGL != "" && toGL != "" && diffGL < 0) {
						compareFromToGL(toGLId,toGlNameId);
					}
				}
			}, {}, yes_confirm, no_confirm, 300, 100);
		} 
		else if (fromGL != "" && toGL != "" && diffGL < 0) {

			compareFromToGL(toGLId,toGlNameId);
		}
	}
}

function compareFromToGL(GLCode, GLName) {
	_showErrorMsg(compareToFromAlert, error_msg_title, 300, 100);
	GLCode.val("");
	GLName.val("");
	return;
}
var crtLineCount = 0;

function fillGlForm() {
	$("#modeGL_"+_pageRef).val("update");
	shouldIncrement=0;
	rowid = $("#glGrids_"+_pageRef).jqGrid('getGridParam', 'selrow');
	/*load details in the edit screen*/
	viewGLDetails(rowid);
}

function viewGLDetails(rowid) {
	var collapseDiv = $("#GLDivId > .collapsibleContainerTitle").get(0);
	if ($(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) {
		$(collapseDiv).trigger("click");
	}
	lineGlRadioChange();
	var subLineNb = document.getElementById("subLineNb_"+_pageRef).value;

	var url = jQuery.contextPath + "/path/templateMaintReport/openGL";
	url = url + "?mode=edit&_pageRef="+_pageRef; 
	myObject = $("#glGrids_"+_pageRef).jqGrid('getRowData', rowid);

	$.get(url, myObject, function(param) {

		$("#lnNbr_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.LINE_NBR);
		$("#glTempCode_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.CODE);
		$("#subLineNb_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.LINE_NBR_DET);
		$("#templateCode_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.CODE);
		$("#lineNumber_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.LINE_NBR);
		$("#lineNbrDet_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.LINE_NBR_DET);
		//end bbe
		
		
		
		
		
		$("#lookuptxt_fromGLCode_" + _pageRef).val(param["glCO"].glstmpltGlsDetVO.FROM_GL);
		$("#fromGlLookUp_"+_pageRef).val(param["glCO"].FROM_GLStr);
		$("#lookuptxt_toGLCode_" + _pageRef).val(param["glCO"].glstmpltGlsDetVO.TO_GL);
		$("#toGlLookUp_"+_pageRef).val(param["glCO"].TO_GLStr);
		$("#lookuptxt_glCompCode_" + _pageRef).val(param["glCO"].glstmpltGlsDetVO.GL_COMP);
		$("#compCodeGlLookUp_"+_pageRef).val(param["glCO"].GL_COMP_NAME);
		$("#lookuptxt_branchCode_" + _pageRef).val(param["glCO"].glstmpltGlsDetVO.BRANCH_CODE);
		$("#branchCodeGlLookUp_"+_pageRef).val(param["glCO"].BRANCH_NAME);
		$("#lookuptxt_divCode_" + _pageRef).val(param["glCO"].glstmpltGlsDetVO.DIV_CODE);
		$("#divCodeLookUp_"+_pageRef).val(param["glCO"].DIV_NAME);
		$("#lookuptxt_deptCode_" + _pageRef).val(param["glCO"].glstmpltGlsDetVO.DEPT_CODE);
		$("#deptCodeLookUp_"+_pageRef).val(param["glCO"].DEPT_NAME);
		$("#percentage_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.PERCENTAGE);
		$("#calcTypeComboId_"+_pageRef).val(param["glCO"].glstmpltGlsDetVO.CALC_TYPE);
		var exclClosEntry = param["glCO"].glstmpltGlsDetVO.EXCLUDE_CLOSING_ENTRY;
		if (exclClosEntry == "YES") {
			$("#exclClosingEntry_"+_pageRef).attr('checked', true);
		} else {
			$("#exclClosingEntry_"+_pageRef).attr('checked', false);
		}

		liveSearch_makeReadOnly(false, "glCompCode_" + _pageRef);
		liveSearch_makeReadOnly(false, "branchCode_" + _pageRef);
		liveSearch_makeReadOnly(false, "fromGLCode_" + _pageRef);
		liveSearch_makeReadOnly(false, "toGLCode_" + _pageRef);
		liveSearch_makeReadOnly(false, "divCode_" + _pageRef);
		liveSearch_makeReadOnly(false, "deptCode_" + _pageRef);
		$("#glOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
		document.getElementById("glOK_"+_pageRef).disabled=false
	
		callDependency(	"calcTypeComboId_"+_pageRef+":glCO.glstmpltGlsDetVO.CALC_TYPE",
					jQuery.contextPath+'/path/templateMaintReport/applyCalcTypeDependency',
					"updates:calcTypeComboId_"+_pageRef+",glCO.glstmpltGlsDetVO.CALC_TYPE:calcTypeComboId_"+_pageRef,
					"calcTypeComboId",
					"")
		hideSelecAccTemplate(1);

	});
}

function saveGL(mode, newlineNbr) {
	_showProgressBar(true)
	var subLineNb = $("#subLineNb_"+_pageRef).val();
	if (subLineNb == 0) 
	{
		_showErrorMsg(fillRequiredFieldAlert)
		$("#subLineNb_"+_pageRef).val("");
		_showProgressBar(false)
		return false;
	}
	if (mode == "edit") {
		var url = $("#glMaintFrmId_"+_pageRef).attr("action")
		
		parseNumbers();
		myObject = $("#glMaintFrmId_"+_pageRef).serialize();

		var tempCodeWithLineNb = $("#tempCodeWithLineNb_"+_pageRef).val();
		myObject += "&tempCodeWithLineNb=" + tempCodeWithLineNb;


		$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			   if(openSelecAcc==0 && (typeof param["_error"] == "undefined" || param["_error"] == null) )
			   {
				   
					$("#glGrids_"+_pageRef).trigger("reloadGrid");
					userClickedOk=1;
					if (newlineNbr != null) 
					{
						reloadGLGrd = true;
						glToSel = newlineNbr;
						
					}
					refreshGLForm();
				}
			   else
			   {
				  openSelecAcc=0;
				  if(param["_error"]!=null)
				   $("#selecAccDiag_"+_pageRef).dialog('close');
				  _showProgressBar(false)
				  return;
			   }
			 }
	    });
		
	}
}

function refreshGLForm()
{
					var urlRefresh = jQuery.contextPath+ "/path/templateMaintReport/refreshGlForm.action?_pageRef="+_pageRef;
					$.ajax({
							 url: urlRefresh,
					         type:"post",
							 success: function(param)
							 { 
								$("#glListDiv_"+_pageRef).html(param);
								var collapseDiv = $("#GLDivId > .collapsibleContainerTitle").get(0);
								if ($(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) 
								{
									$(collapseDiv).trigger("click");
								}
								lineGlRadioChange();
								_showProgressBar(false);
							 }
							});
}
//var userClickedOk = 1;//to know if we should increment the sub line number or not
//var shouldIncrement = 1;
//var returnedNewLineNumber = 0;
function emptyGLForm(fromWhere) {
	_showProgressBar(true)
	var tempCodeWithLineNb = $("#tempCodeWithLineNb_"+_pageRef).val();
	var linNb = tempCodeWithLineNb.split("~")[1];
	var tmpCode = tempCodeWithLineNb.split("~")[0];
	$("#lnNbr_"+_pageRef).val(linNb);
	$("#glTempCode_"+_pageRef).val(tmpCode);

	if(userClickedOk==1&&shouldIncrement==1)
	$("#subLineNb_"+_pageRef).val("");
	$("#lookuptxt_fromGLCode_" + _pageRef).val("");
	$("#fromGlLookUp_"+_pageRef).val("");
	$("#lookuptxt_toGLCode_" + _pageRef).val("");
	$("#toGlLookUp_"+_pageRef).val("");
	$("#lookuptxt_glCompCode_" + _pageRef).val("");
	$("#compCodeGlLookUp_"+_pageRef).val("");
	$("#lookuptxt_branchCode_" + _pageRef).val("");
	$("#branchCodeGlLookUp_"+_pageRef).val("");
	$("#lookuptxt_divCode_" + _pageRef).val("");
	$("#divCodeLookUp_"+_pageRef).val("");
	$("#lookuptxt_deptCode_" + _pageRef).val("");
	$("#deptCodeLookUp_"+_pageRef).val("");
	$("#percentage_"+_pageRef).val("");
	$("#calcTypeComboId_"+_pageRef).val("AB");
	$("#exclClosingEntry_"+_pageRef).attr('checked', false);

	liveSearch_makeReadOnly(false, "glCompCode_" + _pageRef);
	liveSearch_makeReadOnly(true, "branchCode_" + _pageRef);
	liveSearch_makeReadOnly(true, "fromGLCode_" + _pageRef);
	liveSearch_makeReadOnly(true, "toGLCode_" + _pageRef);
	liveSearch_makeReadOnly(true, "divCode_" + _pageRef);
	liveSearch_makeReadOnly(true, "deptCode_" + _pageRef);

	//get the subLineNb and put it in the subLineNb input
	if(userClickedOk==1 && shouldIncrement==1)
	{
		var url = jQuery.contextPath
				+ "/path/templateMaintReport/generateSubLnNbGL";
		url += "?_pageRef="+_pageRef+"&tempCodeWithLineNb=" + tempCodeWithLineNb;
		$.get(url, params, function(param) {
			var newSubLine = param['checkSubglstmpltCO']['glstmpltGlsDetVO']['LINE_NBR_DET'];
			$("#subLineNb_"+_pageRef).val(newSubLine);
			$("#lnNbr_"+_pageRef).val(param['checkSubglstmpltCO']['newLineNumber']);
			returnedNewLineNumber=newSubLine;
			userClickedOk=0;
			if(fromWhere=="deleteGL")
			{
				$("#glOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
				document.getElementById("glOK_"+_pageRef).disabled=true
			}
			else
			{
				$("#glOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
				document.getElementById("glOK_"+_pageRef).disabled=false
			}
			_showProgressBar(false)
		});
	}
	else
	{
		if(fromWhere=="deleteGL")
		{
			$("#glOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
			document.getElementById("glOK_"+_pageRef).disabled=true
		}
		else
		{
			$("#glOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
			document.getElementById("glOK_"+_pageRef).disabled=false
		}
		_showProgressBar(false)
	}
	$("#modeGL_"+_pageRef).val("add");
}

function checkIfEmptyDiv() {
	if ($("#lookuptxt_divCode_" + _pageRef).val() == "") {
		$("#lookuptxt_deptCode_" + _pageRef).val("");
		$("#deptCodeLookUp_"+_pageRef).val("");
	}
}

function checkIfEmptyComp() {
	if ($("#lookuptxt_glCompCode_" + _pageRef).val() == "") {
		$("#lookuptxt_branchCode_" + _pageRef).val("");
		$("#branchCodeGlLookUp_"+_pageRef).val("");

		$("#lookuptxt_fromGLCode_" + _pageRef).val("");
		$("#fromGlLookUp_"+_pageRef).val("");

		$("#lookuptxt_toGLCode_" + _pageRef).val("");
		$("#toGlLookUp_"+_pageRef).val("");

		$("#lookuptxt_divCode_" + _pageRef).val("");
		$("#divCodeLookUp_"+_pageRef).val("");

		$("#lookuptxt_deptCode_" + _pageRef).val("");
		$("#deptCodeLookUp_"+_pageRef).val("");
	}

}

function hideSelecAccTemplate(from)
{
	if(from==1)
	{	
		var calcType = $("#calcTypeComboId_"+_pageRef).val();
		if(calcType=="COC" || calcType=="RTV" || calcType=="OLC" || calcType=="ILC" || calcType=="LG" 
			|| calcType=="OBC" || calcType=="IBC" || calcType=="FUA" || calcType=="FUD" || calcType=="FUI")
		{
			$("#selecAccDiv_"+_pageRef).attr("style","display:none");
		}
		else
		{
			$("#selecAccDiv_"+_pageRef).attr("style","display:inline");
		}
	}
	else
	{
		$("#selecAccDiv_"+_pageRef).attr("style","display:inline");
	}
}

var openSelecAcc=0;
function openSelecAccScreen()
{	
	var tempCodeWithLineNb = $("#tempCodeWithLineNb_"+_pageRef).val();
	if (tempCodeWithLineNb == "0~0")
	{
		_showErrorMsg(selectLineAlert);
		return;
	}
	if($("#lnNbr_"+_pageRef).val()=="")
	{
		return;
	}
	openSelecAcc=1;
	//fill the 3 hiddens at the bottom of the page
	saveGLButton();	
	$("#selecAccDiag_"+_pageRef).dialog('open');
}

function submitAccountsNeeded()
{
/*	var rows = $("#selectAccGrid").jqGrid('getGridParam','selarrrow');	
	var condCount=$("#selectAccGrid").jqGrid('getGridParam','records');
	for(i=0;i<rows.length;i++)

	var arrId;
	if(rows)
	{		
		arrId = new Array(rows.length);	
		var j = 0;	 	
		jQuery.each(rows, function(i, val) {
			var data = $("#selectAccGrid").jqGrid('getRowData',val);
		arrId[j] = data.concatKey;
		confirm(data.toSource())
		j++;
	});   */          
	    
	    //newly added
	    var jsonStringUpdates = $("#selectAccGrid_"+_pageRef).jqGrid('getAllRows');
		 	$("#updateSelecAcc_"+_pageRef).val(jsonStringUpdates); 
		 	 var url = $("#selecAccForm_"+_pageRef).attr("action");
		 	 
		 	 myObject =  $("#selecAccForm_"+_pageRef).serialize();
		 	 $.post(url, myObject , function()
	 	 		{
	 	 			return null;
	 	 		});
	 	 		return null;
	    //end newly added
	    
		/*var params = {};
		params["templateCode"]	=	$("#tmplCodeAcc_"+_pageRef).val();
		params["lineNumber"]	=	$("#lineNumberAcc_"+_pageRef).val();
		params["lineNbrDet"]	=	$("#lineNbrDetAcc_"+_pageRef).val();
		params["accountsIds"]	=	arrId;
		$.ajax({
			url: jQuery.contextPath+'/path/templateMaintReport/saveNeededAccounts.action',
	type: "POST",
	data: (params),				
	success: function(xml){
		$("#selectAccGrid").trigger("reloadGrid");
		    			}
					});
	}*/
}		