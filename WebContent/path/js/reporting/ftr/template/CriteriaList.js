var reloadCrtGrd = false;
var crtToSel = -1;
var glLineCount=0;

function rep_templateCriteria_readyFunc()
{
	$("#crtLookUp_"+_pageRef).attr('readonly', true);
	$("#code1LookUp_"+_pageRef).attr('readonly', true);
	$("#code2LookUp_"+_pageRef).attr('readonly', true);
	//get the glLineCount from the server side cs we didn't reload the line from each time we select a line
	$("div#sort7CritTemp .collapsibleContainer").collapsiblePanel();
	$("div#sort9 .collapsibleContainer").collapsiblePanel();
    //disable details
   	liveSearch_makeReadOnly(true,"crt_Code1_"+_pageRef);
	$("#code1LookUp_"+_pageRef).attr('readonly', true);
	liveSearch_makeReadOnly(true,"lookuptxt_crt_Code2_"+_pageRef);
	liveSearch_makeReadOnly(true,"lookuptxt_crt_Code3_"+_pageRef);
	$("#code2LookUp_"+_pageRef).attr('readonly', true);
	$("#crt_value1_"+_pageRef).attr('readonly', true);
	$("#crt_value2_"+_pageRef).attr('readonly', true);
	
	$("#crtGrids_"+_pageRef).subscribe('crtGrdComplete', function(event,data) 
	{
		   			if(reloadCrtGrd && crtToSel != -1)
		   			{
			   		    //select the last selected row after reloading the grid
	   					$("#crtGrids_"+_pageRef).jqGrid("setSelection",crtToSel,false);
	   					reloadCrtGrd = false;
	   					crtToSel = -1;	   					
		   			}
		   			//enable/disable tabs
					var lineCount=$("#crtGrids_"+_pageRef).jqGrid('getGridParam','records');
					var isDisabledExprTab=$.inArray(2, $("#tabs").tabs("option", "disabled"))
					if(lineCount >0 &&  isDisabledExprTab=="-1")//when adding the fist record to the grid 
					{
						$("#tabs").tabs({disabled: [2]});
					}					
					var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt_"+_pageRef).val();
					if(tempCodeWithLineNb!="0~0")
					{
						var zSrc=jQuery.contextPath+"/path/templateMaintReport/checkIfExprLine.action?_pageRef="+_pageRef+"&templateCode="+tempCodeWithLineNb.split("~")[0]+"&lineNumber="+tempCodeWithLineNb.split("~")[1];
						params ={};
						$.getJSON(zSrc, params, function( data2, status, xhr ) 
						{
							var retVal=data2['exprOrGLByLineCO']['exprOrGLByLine'];
							glLineCount=0;
							for (var i=0;i<retVal.length;i++)
							{
								if(retVal[i]=="1")
								{
									glLineCount=1;	
									break;
								}	
							}
							if(lineCount ==0 && glLineCount==0 && isDisabledExprTab=="0") //when deleting the last record
							{ 
								$("#tabs").tabs('enable', 2);
							}
						});
					}
	  });
	$("#criteriaOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
	document.getElementById("criteriaOK_"+_pageRef).disabled=true
}

function changeDisplay(flag)
{ 
	if($("#lookuptxt_crt_Code_"+_pageRef).val() == '-5')
		{
			$("#lookuptxt_crt_Code_"+_pageRef).val("");
			return;
		}
	var url = jQuery.contextPath+ "/path/templateMaintReport/openCrt";
	params = {};
	if(flag)
	{
		$("#updtCrtList_"+_pageRef).val("default");
	}
	else
	{
		$("#updtCrtList_"+_pageRef).val("changedCriteria");
	}
	myObject =  $("#crtMaintFrmId").serialize();
    $.get(url, myObject , function( param )
 	{
    	$("#CrtDivId_"+_pageRef).html(param); 
		var collapseDiv = $("#critTempId > .collapsibleContainerTitle").get(0);
		if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
		{
			$(collapseDiv).trigger("click");
		}
		if(flag)
		{
			$("#criteriaOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
	   		document.getElementById("criteriaOK_"+_pageRef).disabled=true;
	   	}
		else
		{	if(flag==false && $("#crt_tempLnNbr_"+_pageRef).val()!="")
			{
				$("#criteriaOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
				document.getElementById("criteriaOK_"+_pageRef).disabled=false;
			}
			else
			{
				$("#criteriaOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
	   			document.getElementById("criteriaOK_"+_pageRef).disabled=true;
			}
		}
	},"html");
disableEnableDetails(null,true);
}

function det1Dependency()
{	if($("#lookuptxt_crt_Code1_"+_pageRef).val()=='-5')
	{
		$("#lookuptxt_crt_Code1_"+_pageRef).val("");
		_showProgressBar(false);
		return;
	}
 	var detCode=$("#lookuptxt_crt_Code1_"+_pageRef).val();
 	enableDisableValue(detCode);								 					
	_showProgressBar(false);
}


function befDep()
{
	$("#lookuptxt_crt_Code2_"+_pageRef).val("");
	$("#code2LookUp_"+_pageRef).val("");			
	$("#lookuptxt_crt_Code3_"+_pageRef).val("");
	$("#code3LookUp_"+_pageRef).val("");		
	_showProgressBar(true);
}

function afterDepDetails2()
{
	$("#lookuptxt_crt_Code3_"+_pageRef).val("");
	$("#code3LookUp_"+_pageRef).val("");
	if($("#lookuptxt_crt_Code2_"+_pageRef).val()=='-5')
	{
		$("#lookuptxt_crt_Code2_"+_pageRef).val("");
		_showProgressBar(false);
		return;
	}
	if($("#crt_type_Code_"+_pageRef).val()=='SEC')
	{
		$("#code1LookUp_"+_pageRef).val($("#code2LookUp_"+_pageRef).val());
	}
	_showProgressBar(false);
}

function rep_template_afterDepDetails()
{
	if($("#lookuptxt_crt_Code3_"+_pageRef).val()=='-5')
	{
		$("#lookuptxt_crt_Code3_"+_pageRef).val("");
		_showProgressBar(false);
		return;
	}
	if($("#crt_type_Code_"+_pageRef).val()=='SEC')
	{
		$("#code1LookUp_"+_pageRef).val($("#code2LookUp_"+_pageRef).val());
	}
	_showProgressBar(false);
}

function addCrt()
{
	$("#modeCrt_"+_pageRef).val("add");
	$("#dayMonthYearTr_"+_pageRef).hide();
	$("#criteriaFMTrId_"+_pageRef).hide();
		var tempCodeWithLineNb = $("#tempCodeWithLineNbCrt_"+_pageRef).val();
		if (tempCodeWithLineNb == "0~0") 
		{
			_showErrorMsg(selectLineAlert);
			return;
		}
	//expand div
	var collapseDiv = $("#critTempId > .collapsibleContainerTitle").get(0);
	if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	{
		$(collapseDiv).trigger("click");
	}

	if(shouldIncrementCrt==0 || (shouldIncrementCrt==1 && $("#crt_subLnNbr_"+_pageRef).val()==""))
	{
		$("#crt_subLnNbr_"+_pageRef).val(returnedNewLineNumberCriteria);
	}
	emptyCrtForm();
	 	//get the subLineNb and put it in the subLineNb input
	 
	if(userClickedCrtOk==1 && shouldIncrementCrt==1)
	{
		var url = jQuery.contextPath+ "/path/templateMaintReport/generateSubLnNbCrt";
		url+="?tempCodeWithLineNb="+tempCodeWithLineNb+"&_pageRef="+_pageRef;
	    $.get(url, params , function( param )
	 	{
	    	var newSubLine=param['commonDetVO']['SUB_LINE_NO'];
	    	$("#crt_subLnNbr_"+_pageRef).val(newSubLine);
	    	returnedNewLineNumberCriteria=newSubLine;
		});
	    userClickedCrtOk=0;
	}
	var linNb=tempCodeWithLineNb.split("~")[1];
	$("#crt_tempLnNbr_"+_pageRef).val(linNb);
	if($("#crt_subLnNbr_"+_pageRef).val()=="")
	{
		$("#crt_subLnNbr_"+_pageRef).val(returnedNewLineNumberCriteria);
	}
	$("#criteriaOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
	document.getElementById("criteriaOK_"+_pageRef).disabled=false
}

function emptyCrtForm()
{

//clear the hidden property alsoooooo
	var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt_"+_pageRef).val();
	var tmpCode=tempCodeWithLineNb.split("~")[0];
	$("#crt_subLnNbr_"+_pageRef).val("");
	$("#crt_tempLnNbr_"+_pageRef).val("");
	$("#crtTempCode_"+_pageRef).val(tmpCode);
 	$("#lookuptxt_crt_Code_"+_pageRef).val("");
 	$("#crtLookUp_"+_pageRef).val("");
 	$("#operComboId_"+_pageRef).val("+");
 	$("#crt_include_"+_pageRef).attr('checked', false);
	//hidden
	$("#crtTblName1_"+_pageRef).val("");
	$("#crtTblName2_"+_pageRef).val("");
	$("#crt_type_Code_"+_pageRef).val("");
	$("#dayMonthYear_"+_pageRef).val("Y");
	disableEnableDetails("",true);	
}

function saveCrt(newlineNbr)
{
	_showProgressBar(true)
   var url = $("#crtMaintFrmId").attr("action");
    parseNumbers();
    myObject =  $("#crtMaintFrmId").serialize();
	
	var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt_"+_pageRef).val();
    myObject += "&tempCodeWithLineNb="+tempCodeWithLineNb;

		$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			   if(typeof param["_error"] == "undefined" || param["_error"] == null)
			   {
					var fnd=param["updates"];
				 	if(fnd=="1")
				 	{
				 		_showErrorMsg(recExist);
				 	}
				 	else
				 	{
				    	  $("#crtGrids_"+_pageRef).trigger("reloadGrid"); 
				    	  userClickedCrtOk=1;
				    	  if(newlineNbr!=null)
				    	  {
				  			reloadCrtGrd = true;
				  			crtToSel = newlineNbr;
				    	  }
				    	  emptyCrtForm();
				    	  $("#modeCrt_"+_pageRef).val("add");
				 	}
				 changeDisplay(true);
			   }
			   _showProgressBar(false)
			 }
	    });
}

function deleteCrt(rowid)
{
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs)
	{
       if(confirmcChoice)
       {
    	   deleteTheCrt(theArgs.rowid)
       }
    }, {rowid : rowid});	
}


function deleteTheCrt(rowid)
{
	_showProgressBar(true)
	var url = jQuery.contextPath+ "/path/templateMaintReport/deleteCrt?_pageRef="+_pageRef;
	myObject = $("#crtGrids_"+_pageRef).jqGrid('getRowData',rowid);
	
    $.get(url, myObject , function( param )
 	{
    	$("#crt_subLnNbr_"+_pageRef).val("");
    	//reinitialize the control vars
    		userClickedCrtOk = 1;
		    shouldIncrementCrt = 1;
 			returnedNewLineNumberCriteria = 0;
 			userClickedOk = 1;//to know if we should increment the sub line number or not
 			shouldIncrement = 1;
 			returnedNewLineNumber = 0;
 			selectedLineGrid = 0;
    	$("#crtGrids_"+_pageRef).trigger("reloadGrid");
    	_showProgressBar(false)
	});

	    emptyCrtForm();
	}

	   
function fillCtrForm()
	{
		rowid = $("#crtGrids_"+_pageRef).jqGrid('getGridParam','selrow');
		$("#modeCrt_"+_pageRef).val("update")
		viewCrtDetails(rowid);return;
}



function validateDetails()
{
	var selectedrecord;
	rowid = $("#lookuptable_code1LookUp_"+_pageRef).jqGrid('getGridParam','selrow'); 
	selectedrecord = $("#lookuptable_code1LookUp_"+_pageRef).jqGrid('getRowData',rowid);
	$("#lookuptxt_crt_Code1_"+_pageRef).val(selectedrecord["CODE_STR"]);
	$("#code1LookUp_"+_pageRef).val(selectedrecord["BRIEF_DESC_ENG"]);
	$("#lookupdialog_code1LookUp_"+_pageRef).dialog('close');
	var detVal=selectedrecord["CODE_STR"];
	enableDisableValue(detVal);
	
}

 function enableDisableValue(detVal)
 {
	var url = jQuery.contextPath+ "/path/templateMaintReport/openCrt";
	params = {};
	$("#fromFiltCrt_"+_pageRef).val(detVal);
	myObject =  $("#crtMaintFrmId").serialize();
    $.get(url, myObject , function( param )
 	{
    	$("#CrtDivId_"+_pageRef).html(param); 
		var collapseDiv = $("#critTempId > .collapsibleContainerTitle").get(0);
		if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
		{
			$(collapseDiv).trigger("click");
		}
		if($("#crt_tempLnNbr_"+_pageRef).val()=="")
		{
			$("#criteriaOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
	   		document.getElementById("criteriaOK_"+_pageRef).disabled=true;
	   		$("#crt_value2_"+_pageRef).caretTo('');
		}
	},"html");

	if(detVal=="><" || detVal=="><=" || detVal==">=<" || detVal==">=<=")
	{
		$("#crt_value1_"+_pageRef).val("");
		$("#crt_value2_"+_pageRef).val("");
	}
	else
	{
		var crtComp=$("#crtComponent_"+_pageRef).val();
		disableEnableDetails(crtComp,false);
	}
	$("#fromFiltCrt_"+_pageRef).val("");
 }
 
function changeCompDisplay()
{

	var selectedrecord;
	rowid = $("#lookuptable_crtLookUp_"+_pageRef).jqGrid('getGridParam','selrow'); 
	selectedrecord = $("#lookuptable_crtLookUp_"+_pageRef).jqGrid('getRowData',rowid);
	$("#lookuptxt_crt_Code_"+_pageRef).val(selectedrecord["glstmpltParamLinkVO.CRITERIA_CODE"]);
	$("#crt_type_Code_"+_pageRef).val(selectedrecord["CRITERIA_TYPE_CODE"]);
	$("#crtLookUp_"+_pageRef).val(selectedrecord["CRITERIA_DESCRIPTION"]);
	$("#crtTblName1_"+_pageRef).val(selectedrecord["TABLE_NAME1"]);
	$("#crtTblName2_"+_pageRef).val(selectedrecord["TABLE_NAME2"]);
	var crtComp=selectedrecord["COMPONENT"];
	$("#crtComponent_"+_pageRef).val(crtComp);
  	$("#lookupdialog_crtLookUp_"+_pageRef).dialog('close');

	disableEnableDetails(crtComp,true);


	
}
 
function disableEnableDetails(crtComp,emptyVals)
{
	if(emptyVals==true)
	{
		$("#lookuptxt_crt_Code1_"+_pageRef).val("");
		$("#code1LookUp_"+_pageRef).val("");
		$("#lookuptxt_crt_Code2_"+_pageRef).val("");
		$("#code2LookUp_"+_pageRef).val("");
		$("#lookuptxt_crt_Code3_"+_pageRef).val("");
		$("#code3LookUp_"+_pageRef).val("");
	}
	$("#crt_value1_"+_pageRef).val("");
	$("#crt_value2_"+_pageRef).val("");
}

function viewCrtDetails(rowid)
{
	var url = jQuery.contextPath+ "/path/templateMaintReport/openCrt?_pageRef="+_pageRef;

	myObject = $("#crtGrids_"+_pageRef).jqGrid('getRowData',rowid);
	params = {};

    $.get(url, myObject , function( param )
 	{
    	$("#CrtDivId_"+_pageRef).html(param);
    	//$("#crt_value2_"+_pageRef).caretTo('');
		var collapseDiv = $("#critTempId > .collapsibleContainerTitle").get(0);
		if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
		{
			$(collapseDiv).trigger("click");
		}
     	if($("#crtComponent_"+_pageRef).val()=="L" || $("#crtComponent_"+_pageRef).val()=="D")
     	{			
	     	$("#lookuptxt_crt_Code2_"+_pageRef).val("");
			$("#code2LookUp_"+_pageRef).val("");
			$("#crt_value1_"+_pageRef).val("");
			$("#crt_value2_"+_pageRef).val("");
     	}
     	if($("#crtComponent_"+_pageRef).val()=="DT")
     	{
     		$("#lookuptxt_crt_Code2_"+_pageRef).val("");
			$("#code2LookUp_"+_pageRef).val("");
			var zOper=$("#lookuptxt_crt_Code1_"+_pageRef).val();
			if(zOper !=">=<=" && zOper!="><" && zOper!="><=" && zOper!=">=<")
			{
				$("#crt_value2_"+_pageRef).val("");
			}
     	}
     	if($("#crtComponent_"+_pageRef).val()=="TDT")
     	{
     		$("#lookuptxt_crt_Code2_"+_pageRef).val("");
			$("#code2LookUp_"+_pageRef).val("");
     	}
     	if($("#crtComponent_"+_pageRef).val()=="LL")
     	{
			$("#crt_value1_"+_pageRef).val("");
			$("#crt_value2_"+_pageRef).val("");
     	}
     	
     	var crtLkp1=$("#code1LookUp_"+_pageRef).val();
	    var crtLkp2=$("#code2LookUp_"+_pageRef).val();
	    $("#criteriaOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
		document.getElementById("criteriaOK_"+_pageRef).disabled=false
 	});
}

function addCriteria()
{
	var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt_"+_pageRef).val();
	var allRowIds =  $("#crtGrids_"+_pageRef).jqGrid('getDataIDs');
	//add checking for calctype RTV
	//check in action if calc type RTV in gl and if so => force the user to choose cur as criteria
	var url = jQuery.contextPath+ "/path/templateMaintReport/checkIfCalcTypeRateValue";
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
				//there's RTV
				if(param["updates"]=="1" && ($("#crt_type_Code_"+_pageRef).val() != "CUR"))
				{
					_showErrorMsg(calcRtvTemp);
					return;			
				}
				else if(param["updates"]=="1" && $("#crt_type_Code_"+_pageRef).val() == "CUR" 
							&& $("#crt_include_"+_pageRef).is(':checked')==false ) 
				{
					_showErrorMsg(crtCurRequiredInc);	
				}
				else if(allRowIds.length==1 && (param["updates"]=="1" && $("#crt_type_Code_"+_pageRef).val() == "CUR" 
							&& $("#crt_include_"+_pageRef).is(':checked')==true) && $("#modeCrt_"+_pageRef).val()=="add")
				{
					_showErrorMsg(onlyOneCur)	
				}
				else
				{
					if(tempCodeWithLineNb=="0~0")
					{
						_showErrorMsg(selectLineAlert);
						return;
					}
					
					var subLineNb=document.getElementById("crt_subLnNbr_"+_pageRef).value;
					if(subLineNb!="" && subLineNb!="undefined" && subLineNb!=null)
					{
						if(returnedNewLineNumberCriteria!=subLineNb && returnedNewLineNumberCriteria>0)
						{
							shouldIncrementCrt=0;
						}
						else
							shouldIncrementCrt=1;
						if(subLineNb!="0")
						{
							saveCrt();
						}
					}
				}
				
		   }
		 }
	});
	//end checking RTV
}