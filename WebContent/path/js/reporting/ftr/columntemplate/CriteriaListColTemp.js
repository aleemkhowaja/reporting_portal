function rep_crtLst_readyFunc()
{
	$("#crtLookUp").attr('readonly', true);
		$("#code1LookUp").attr('readonly', true);
		$("#code2LookUp").attr('readonly', true);
	 	//get the glLineCount from the server side cs we didn't reload the line from each time we select a line
		
		var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt").val();
		if(tempCodeWithLineNb!="0~0")
		{
			var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/checkIfExprLine.action?code="+tempCodeWithLineNb.split("~")[0]+"&lineNumber="+tempCodeWithLineNb.split("~")[1]+"&_pageRef"+_pageRef;
			params ={};
			$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{
				/*var retVal=data2['exprOrGLByLineCO']['exprOrGLByLine'];
				if(retVal=="10" || retVal=="11")
				{
					glLineCount=1;
				}*/
			});
		}
		
		
  		//disable details
 		liveSearch_makeReadOnly(true,"crt_Code1");
		$("#code1LookUp").attr('readonly', true);
		liveSearch_makeReadOnly(true,"crt_Code2");
		$("#code2LookUp").attr('readonly', true);
		$("#crt_value1").attr('readonly', true);
		$("#crt_value2").attr('readonly', true);

 		$("#crtGrids_"+_pageRef).subscribe('crtGrdCompleteColTemp', function(event,data) 
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
			var isDisabledExprTab=$.inArray(2, $("#tabs_"+_pageRef).tabs("option", "disabled"))
		
			if(lineCount >0 &&  isDisabledExprTab=="-1")//when adding the fist record to the grid 
			{
				$("#tabs_"+_pageRef).tabs({disabled: [2]});
			}
			if(lineCount ==0 && glLineCount==0 && isDisabledExprTab=="0") //when deleting the last record
			{
				$("#tabs_"+_pageRef).tabs('enable', 2);
			}
			if($("#COL_TYPE_"+_pageRef).val()!="X")
			{ 
				$("#tabs_"+_pageRef).tabs({disabled: [1]})
			}
			else
			{
				$("#tabs_"+_pageRef).tabs('enable', 1)
			}			
 		});
}
function changeDisplayColTemp()
{
	var url = jQuery.contextPath+ "/path/colTemplateMaintReport/openCrt";
	params = {};
	myObject =  $("#crtMaintFrmIdColTemp").serialize();
    $.get(url, myObject , function( param )
 	{
    	$("#CrtDivId_"+_pageRef).html(param); 
        var crtComp=$("#crtComponent").val();
    	disableEnableDetailsColTemp(crtComp,true);
	},"html");	

}

function det1DependencyColTemp()
{
	var detCode=$("#lookuptxt_crt_Code1").val();
 	enableDisableValueColTemp(detCode);
 	if($("#crt_type_Code").val()=='GLC')
 	{
 		checkGlCodeCriteria();
 	}
 }

function checkGlCodeCriteria()
{
	if($("#lookuptxt_crt_Code1").val()!="" && $("#lookuptxt_crt_Code2").val()!="")
	{
		crtCode1 = parseInt($("#lookuptxt_crt_Code1").val());
		crtCode2 = parseInt($("#lookuptxt_crt_Code2").val());
		if(crtCode2<crtCode1)
		{
			_showErrorMsg(compareToFromAlert, error_msg_title, 300, 100);
			$("#lookuptxt_crt_Code2").val("");
			$("#code2LookUp").val("");
		}
	}
}
function addCrtColTemp()
{
	$("#dayMonthYearColTr_"+_pageRef).hide();
	$("#criteriaFMColTrId_"+_pageRef).hide();
	var tempCodeWithLineNb = $("#tempCodeWithLineNbCrt").val();
	if (tempCodeWithLineNb == "0~0") 
	{
		_showErrorMsg(selectLineAlert);
		return;
	}
	//expand div
	var collapseDiv = $("#CrtDivId_"+_pageRef+" > .collapsibleContainerTitle").get(0);
	if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	{
		$(collapseDiv).trigger("click");
	}
	//added to control renumbering	
	if(shouldIncrementColCrt==0 || (shouldIncrementColCrt==1 && $("#crt_subLnNbr").val()==""))
	{
		$("#crt_subLnNbr").val(returnedNewLineNumberColCriteria);
	}
	
	emptyCrtFormColTemp();
	
	if(userClickedColCrtOk==1 && shouldIncrementColCrt==1)
	{
	 	//get the subLineNb and put it in the subLineNb input
		var url = jQuery.contextPath+ "/path/colTemplateMaintReport/generateSubLnNbCrt";
		url+="?tempCodeWithLineNb="+tempCodeWithLineNb+"&_pageRef="+_pageRef;
	    $.get(url, params , function( param )
	 	{
	    	var newSubLine=param['commonDetVO']['SUB_LINE_NO'];
	    	$("#crt_subLnNbr").val(newSubLine);
	    	returnedNewLineNumberColCriteria	=	newSubLine;
		});
	    userClickedColCrtOk=0;
    }
	//added for renumbering
	var tempCodeWithLineNb = $("#tempCodeWithLineNbCrt").val();
	var linNb=tempCodeWithLineNb.split("~")[1];
	$("#crt_tempLnNbr").val(linNb);
	if($("#crt_subLnNbr").val()=="")
	{
		$("#crt_subLnNbr").val(returnedNewLineNumberColCriteria);
	}
	//end added for renumbering
}

function checkCrtRequiredFields()
{
	if($("#lookuptxt_crt_Code").val()=="")
	{
		return false;
	}	
	else
	{
		if(($("#lookuptxt_crt_Code1").val()=="" && $("#lookuptxt_crt_Code1").attr('readonly')!="readonly") ||
		   ($("#lookuptxt_crt_Code2").val()=="" && $("#lookuptxt_crt_Code2").attr('readonly')!="readonly")||
		   ($("#crt_value1").val()=="" && $("#crt_value1").attr('readonly')!="readonly") ||
		   ($("#crt_value2").val()=="" && $("#crt_value2").attr('readonly')!="readonly"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}

function emptyCrtFormColTemp()
{
	//clear the hidden property alsoooooo
	var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt").val();
	var linNb=tempCodeWithLineNb.split("~")[1];
	var tmpCode=tempCodeWithLineNb.split("~")[0];
	
	$("#crt_tempLnNbr").val(linNb);
	$("#crtTempCode").val(tmpCode);
	$("#crt_subLnNbr").val("");
 	$("#lookuptxt_crt_Code").val("");
 	$("#crtLookUp").val("");
 	$("#operComboId").val("+");
 	$("#crt_include").attr('checked', false);
	//hidden
	$("#crtTblName1").val("");
	$("#crtTblName2").val("");
	$("#crt_type_Code").val("");

	disableEnableDetailsColTemp("",true);
}

function saveCrtColTemp(newlineNbr)
{
   	var url = $("#crtMaintFrmIdColTemp").attr("action");
    parseNumbers();
    myObject =  $("#crtMaintFrmIdColTemp").serialize();
	
	var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt").val();
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
			    	  userClickedColCrtOk=1;
			    	  if(newlineNbr!=null)
			    	  {
			  			reloadCrtGrd = true;
			  			crtToSel = newlineNbr;
			    	  }
			    	  emptyCrtFormColTemp();
			 	}
		   }
		   _showProgressBar(false);
		 }
    });
}

function deleteCrtColTemp(rowid)
{
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs)
	{
       if(confirmcChoice)
       {
    	   deleteTheCrtColTemp(theArgs.rowid)
       }
    }, {rowid : rowid});	
}

function deleteTheCrtColTemp(rowid)
{
	var url = jQuery.contextPath+ "/path/colTemplateMaintReport/deleteCrt?_pageRef="+_pageRef;
	myObject = $("#crtGrids_"+_pageRef).jqGrid('getRowData',rowid);
	/*params = {};
	paramStr = JSON.stringify(myObject)
	paramStr = "{"+ "crtCO:"+paramStr + "}";
	params["updates"] = paramStr;*/
    $.get(url, myObject , function( param )
 	{
    	//added for line renumbering
    	$("#crt_subLnNbr").val("");
    	//reinitialize the control vars
    	userClickedColCrtOk 				= 1;
		shouldIncrementColCrt 				= 1;
 		returnedNewLineNumberColCriteria 	= 0;
 		selectedColLineGrid 				= 0;
    	$("#crtGrids_"+_pageRef).trigger("reloadGrid");
	});

	emptyCrtFormColTemp();
}
	   
function fillCtrFormColTemp()
{
	rowid = $("#crtGrids_"+_pageRef).jqGrid('getGridParam','selrow');
	/*load details in the edit screen*/
	viewCrtDetailsColTemp(rowid);
}

function validateDetailsColTemp()
{
	var selectedrecord;
	rowid = $("#lookuptable_code1LookUp").jqGrid('getGridParam','selrow'); 
	selectedrecord = $("#lookuptable_code1LookUp").jqGrid('getRowData',rowid);
	$("#lookuptxt_crt_Code1").val(selectedrecord["CODE_STR"]);
	$("#code1LookUp").val(selectedrecord["BRIEF_DESC_ENG"]);
	$("#lookupdialog_code1LookUp").dialog('close');
	var detVal=selectedrecord["CODE_STR"];
	enableDisableValueColTemp(detVal);
}

function enableDisableValueColTemp(detVal)
{
	if(detVal=="><" || detVal=="><=" || detVal==">=<" || detVal==">=<=")
	{
		$("#crt_value1").attr('readonly', false);
		$("#crt_value2").attr('readonly', false);
		$("#crt_value1").val("");
		$("#crt_value2").val("");
	}
	else
	{
		var crtComp=$("#crtComponent").val();
		disableEnableDetailsColTemp(crtComp,false);
	}
}
 
function changeCompDisplayColTemp()
{
	var selectedrecord;
	rowid = $("#lookuptable_crtLookUp").jqGrid('getGridParam','selrow'); 
	selectedrecord = $("#lookuptable_crtLookUp").jqGrid('getRowData',rowid);
	$("#lookuptxt_crt_Code").val(selectedrecord["columntmpltParamLinkVO.CRITERIA_CODE"]);
	$("#crt_type_Code").val(selectedrecord["CRITERIA_TYPE_CODE"]);
	$("#crtLookUp").val(selectedrecord["CRITERIA_DESCRIPTION"]);
	$("#crtTblName1").val(selectedrecord["TABLE_NAME1"]);
	$("#crtTblName2").val(selectedrecord["TABLE_NAME2"]);
	var crtComp=selectedrecord["COMPONENT"];
	$("#crtComponent").val(crtComp);
  	$("#lookupdialog_crtLookUp").dialog('close');

	disableEnableDetailsColTemp(crtComp,true);
}
 
function disableEnableDetailsColTemp(crtComp,emptyVals)
{
	if(crtComp=="D" || crtComp=="L")
	{
		liveSearch_makeReadOnly(false,"crt_Code1");
		liveSearch_makeReadOnly(true,"crt_Code2");
		$("#code1LookUp").attr('readonly', false);
		$("#code2LookUp").attr('readonly', true);
		$("#crt_value1").attr('readonly', true);
		$("#crt_value2").attr('readonly', true);
	}
	else if(crtComp=="T")
	{
		liveSearch_makeReadOnly(true,"crt_Code1");
		liveSearch_makeReadOnly(true,"crt_Code2");
		$("#code1LookUp").attr('readonly', true);
		$("#code2LookUp").attr('readonly', true);
		$("#crt_value1").attr('readonly', false);
		$("#crt_value2").attr('readonly', true);

	}
	else if(crtComp=="LL")
	{
		liveSearch_makeReadOnly(false,"crt_Code1");
		liveSearch_makeReadOnly(false,"crt_Code2");
		$("#code1LookUp").attr('readonly', false);
		$("#code2LookUp").attr('readonly', false);
		$("#crt_value1").attr('readonly', true);
		$("#crt_value2").attr('readonly', true);
	}
	else if(crtComp=="DT")
	{
		liveSearch_makeReadOnly(false,"crt_Code1");
		liveSearch_makeReadOnly(true,"crt_Code2");
		$("#code1LookUp").attr('readonly', false);
		$("#code2LookUp").attr('readonly', true);
		$("#crt_value1").attr('readonly', false);
		var zOper=$("#lookuptxt_crt_Code1").val();
		if(zOper !=">=<=" && zOper!="><" && zOper!="><=" && zOper!=">=<")						
		{
			$("#crt_value2").attr('readonly', true);
		}
		else
		{
			$("#crt_value2").attr('readonly', false);
		}
	}
	else if(crtComp=="TDT" || crtComp=="DTT")
	{
		liveSearch_makeReadOnly(false,"crt_Code1");
		liveSearch_makeReadOnly(true,"crt_Code2");
		$("#code1LookUp").attr('readonly', false);
		$("#code2LookUp").attr('readonly', true);
		$("#crt_value1").attr('readonly', false);
		$("#crt_value2").attr('readonly', false);
	}
	else if(crtComp=="" || crtComp==null || crtComp=="null" )
	{
		liveSearch_makeReadOnly(true,"crt_Code1");
		liveSearch_makeReadOnly(true,"crt_Code2");
		$("#code1LookUp").attr('readonly', true);
		$("#code2LookUp").attr('readonly', true);
		$("#crt_value1").attr('readonly', true);
		$("#crt_value2").attr('readonly', true);

	}
	if(emptyVals==true)
	{
		$("#lookuptxt_crt_Code1").val("");
		$("#code1LookUp").val("");
		$("#lookuptxt_crt_Code2").val("");
		$("#code2LookUp").val("");
	}
	$("#crt_value1").val("");
	$("#crt_value2").val("");
}

function viewCrtDetailsColTemp(rowid)
{
	var collapseDiv = $("#CrtDivId_"+_pageRef+" > .collapsibleContainerTitle").get(0);
	if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	{
		$(collapseDiv).trigger("click");
	}

	var url = jQuery.contextPath+ "/path/colTemplateMaintReport/openCrt?_pageRef="+_pageRef;

	myObject = $("#crtGrids_"+_pageRef).jqGrid('getRowData',rowid);
	params = {};

    $.get(url, myObject , function( param )
 	{
    	$("#CrtDivId_"+_pageRef).html(param);

     	if($("#crtComponent").val()=="L" || $("#crtComponent").val()=="D")
     	{			
	     	$("#lookuptxt_crt_Code2").val("");
			$("#code2LookUp").val("");
			$("#crt_value1").val("");
			$("#crt_value2").val("");
     	}
     	if($("#crtComponent").val()=="DT")
     	{
     		$("#lookuptxt_crt_Code2").val("");
			$("#code2LookUp").val("");
			var zOper=$("#lookuptxt_crt_Code1").val();
			if(zOper !=">=<=" && zOper!="><" && zOper!="><=" && zOper!=">=<")
			{
				$("#crt_value2").val("");
			}
     	}
     	if($("#crtComponent").val()=="TDT")
     	{
     		$("#lookuptxt_crt_Code2").val("");
			$("#code2LookUp").val("");
     	}
     	if($("#crtComponent").val()=="LL")
     	{
			$("#crt_value1").val("");
			$("#crt_value2").val("");
     	}
     	
     	var crtLkp1=$("#code1LookUp").val();
	    var crtLkp2=$("#code2LookUp").val();     	
 	});
}

function addCriteriaColTemp()
{
	_showProgressBar(true);
	var tempCodeWithLineNb=$("#tempCodeWithLineNbCrt").val();
	if(tempCodeWithLineNb=="0~0")
	{
		_showErrorMsg(selectLineAlert);
		_showProgressBar(false);
		return;
	}
	
	var subLineNb=document.getElementById("crt_subLnNbr").value;
	
	//added for renumbering
	if(returnedNewLineNumberColCriteria!=subLineNb && returnedNewLineNumberColCriteria>0)
	{
		shouldIncrementColCrt=0;
	}
	else
		shouldIncrementColCrt=1;
	if(subLineNb !="" && subLineNb!="0")
	{
		saveCrtColTemp();
	}
	else
	{
		_showProgressBar(false);	
	}
}

 function checkValue1()
 {
   		var crtTypeCode=$("#crt_type_Code").val();
   		var crtValue1 = $("#crt_value1").val();
   		if(crtTypeCode=="AMT" && crtValue1 <0)
   		{
   			_showErrorMsg(amountNegativeAlert, error_msg_title, 300, 100);
   			$("#crt_value1").val("");
   		}
 }
 

function befDepColTemp()
{
	$("#lookuptxt_crt_Code2").val("");
	$("#code2LookUp").val("");
	$("#lookuptxt_crt_Code2").val("");
	$("#code2LookUp").val("");
}

function afterDepDetails2ColTemp()
{
	if($("#crt_type_Code").val()=='SEC')
	{
		$("#code1LookUp").val($("#code2LookUp").val());
	}
	if($("#crt_type_Code").val()=='GLC')
	{
		checkGlCodeCriteria();
	}
}