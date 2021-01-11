var globTmpCodeWithLnNb="0~0";
var reloadLineGrd = false;
var lineToSel = -1;
var userClickedCrtOk = 1;
var shouldIncrementCrt = 1;
var returnedNewLineNumberCriteria = 0;
var userClickedOk = 1;//to know if we should increment the sub line number or not
var shouldIncrement = 1;
var returnedNewLineNumber = 0;
var selectedLineGrid = 0;

function rep_template_readyFunc() {
	$("#createLikeButtonTmp_" + _pageRef).attr('disabled', true);
	$("#openRelatedRep_" + _pageRef).attr('disabled', false);

	$("#currencyStr_" + _pageRef).attr('readonly', true);
	$("#templLinesGrid_" + _pageRef).subscribe(
			'lineGrdComplete',
			function(event, data) {
				if (reloadLineGrd && lineToSel != -1) {
					$("#templLinesGrid_" + _pageRef).jqGrid("setSelection",
							lineToSel, false);
					reloadLineGrd = false;
					lineToSel = -1;
				}
			});

	$("#templLinesGrid_" + _pageRef).subscribe('lineGrdComplete',
			function(event, data) {
				var pagerId = "templLinesGrid_" + _pageRef + "_pager_left";
				var myGrid = $("#templLinesGrid_" + _pageRef);
				myGrid.jqGrid('navButtonAdd', pagerId, {
					caption : "",
					title : reorganizeTitle,
					id : "NewButton_" + _pageRef,
					buttonicon : '.ui-icon-circle-arrow-s',
					onClickButton : openReorganizeDialog
				});
			});

	$("#lineFrmId_" + _pageRef).processAfterValid("saveLine");

}

function lineGlRadioChange()
{
	var vall = $("input[name='glstmpltCO.glstmpltVO.PER_LINE_GL']:checked").val(); 
	if(vall=="L")
	{
		$("#percentage_"+_pageRef).val("");
		$("#percentage_"+_pageRef).attr("style","display:none;");
		$("#percentageLabel_"+_pageRef).attr("style","display:none;");
	}
	else if(vall=="G")
	{
		$("#percentage_"+_pageRef).attr("style","display:inline;");
		$("#percentageLabel_"+_pageRef).attr("style","display:inline;");
	}

}



function saveAllTemplateInfo()
{_showProgressBar(true)
		/*var options = { 
						url: jQuery.contextPath+ "/path/templateMaintReport/saveAll",
						type:      'post',
						success: function(param,status ,xhr) 
						{
							alert("done")
						}
					}
		 $("#mainFrmId").ajaxSubmit(options)*/
var url = jQuery.contextPath+ "/path/templateMaintReport/saveAll";
myObject =  $("#mainFrmId").serialize();
myObject+"&_pageRef="+_pageRef
 $.ajax({
	 url: url,
	 type:"post",
	 dataType:"json",
	 data: myObject,
	 success: function(param)
	 {
	   if(typeof param["_error"] == "undefined" || param["_error"] == null)
	   {
	   	  $("#templGrid_"+_pageRef).trigger("reloadGrid");
	   	  var tmpCode=$("#templCode_"+_pageRef).val();
		   	  newTemplate();
		   	  _showProgressBar(false)
			  _showConfirmMsg(runTemplProcAlert, runProcTtle, function(confirmcChoice, theArgs){
	           if(confirmcChoice)
	           {
	        	   runTemplProc(theArgs.tmpCode)
	           }
		      }, {tmpCode : tmpCode});	
		      
		}
	 	else
	 	{
	 		_showProgressBar(false);
	 		return ;
	 	}
		$("#perLineGl_"+_pageRef).val("");
		var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
		if(!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
		{
			$(collapseDiv).trigger("click");
		}
		$('input[name=glstmpltCO.glstmpltVO.PER_LINE_GL][value="L"]').prop('checked', true);
		$("#createLikeButtonTmp_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled').removeClass('ui-state-focus');
		$("#openRelatedRep_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled').removeClass('ui-state-focus');
		//reinitialize the variables of the gl section that control the incrementation of the sublinenb
		 if(userClickedOk!=null)
		 	userClickedOk = 1;//to know if we should increment the sub line number or not
		 if(shouldIncrement!=null)
		 	shouldIncrement = 1;
		 if(returnedNewLineNumber!=null)
		 	returnedNewLineNumber = 0;
		 if(openSelecAcc!=null)
			 openSelecAcc=0;
		 //same for the filter criteria
		 if(userClickedCrtOk!=null)
		 	userClickedCrtOk=1;
	 }
	 });
}

function runTemplProc(tmpCode)
{
    var url = jQuery.contextPath+ "/path/templateProcess/proc_runTemplProcess";
params ={};
params["code"]=tmpCode;
$.getJSON(url, params, function( data2, status, xhr ) 
{
   var procVal=data2['updates'];
   if(procVal == "wrong")
	      { 
		   _showErrorMsg(templProcFailedAlert)   
	      }
	   else
	   {	
		   _showErrorMsg(templProcDoneAlert,info_msg_title);
	   }
	});
}
		
		
		
					
function deleteTemplateLine(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	     _showProgressBar(true)
        	   deleteTmplLine(theArgs.rowid)
           }
	      }, {rowid : rowid});	
	
}

 
function deleteTmplLine(rowid)
 {
	//check if we deleted all the lines then enable the compCode input
 	  var lineCount=$("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','records');
 	  if(lineCount==1)
 	  {
 	  	$("#templCode_"+_pageRef).attr('readonly', false);
 	  }

	myObject = $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
var tempLineNb = myObject["glstmpltVO.LINE_NBR"];
var url = jQuery.contextPath+ "/path/templateMaintReport/deleteLine";
url+="?lineNumber="+tempLineNb+"&_pageRef="+_pageRef;
    $.get(url, myObject , function( param )
 	{
   	  $("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
   	   _showProgressBar(false)
 	});

    emptyForm();
     
}

valueToRemember =0;
function addTemplateLines()
{
		$("#linesOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
		document.getElementById("linesOK_"+_pageRef).disabled=false
	 _showProgressBar(true)
	//reinitialize the variables of the gl section that control the incrementation of the sublinenb
	 if(userClickedOk!=null)
	 	userClickedOk = 1;//to know if we should increment the sub line number or not
	 if(shouldIncrement!=null)
	 	shouldIncrement = 1;
	 if(returnedNewLineNumber!=null)
	 	returnedNewLineNumber = 0;
	 if(openSelecAcc!=null)
		 openSelecAcc=0;
	 //same for the filter criteria
	 if(userClickedCrtOk!=null)
	 	userClickedCrtOk=1;
	
	$("#lineNbr_"+_pageRef).attr('readonly', false);
	if($("#templCode_"+_pageRef).val()=="")
{
	_showErrorMsg(codeExistAlert);
	 _showProgressBar(false)
	return;
}
	emptyForm()
$("#mode1").val("add");

//bbe handling reorganize line
rowid = $("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','selrow');
object1	 =   $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
var lineBefore=object1["newLineNumber"];
var url = jQuery.contextPath+ "/path/templateMaintReport/templMaint_checkIfReorganize";
params = {};
params["code"] = lineBefore;
params["_pageRef"] = _pageRef;
	$.post(url, params, function(param) 
	{  		
	var count = param["updates"];  
	if (count>0)
	{		
		_showConfirmMsg(needReorganize, reorganizeTitle, function(confirmcChoice, theArgs){
	           if(confirmcChoice)
	           {
	        	    _showProgressBar(false)
	        	   valueToRemember = lineBefore;
	        	   $("#reorganizeLines_"+_pageRef).dialog('open');
	           }
		      }, {rowid : rowid});
	}
});
//end bbe adding code to handle reorganize line
//check if the grid still empty , and we have a new line to save , then save it before adding the new line
var lineNbr=document.getElementById("lineNbr_"+_pageRef).value;
var desc1=document.getElementById("desc1_"+_pageRef).value;
var lineCount=$("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','records');

//check if it is a new template , then save the template description in the session

if(lineCount=="0" && lineNbr=="")
{
  var url = jQuery.contextPath+ "/path/templateMaintReport/saveTemplDetails";
  myObject =  $("#mainFrmId").serialize();
	      $.post(url, myObject , function( param )
		 	{
	    	 _showProgressBar(false)
		 	});
		}


 		//expand line details
 		var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
{
	$(collapseDiv).trigger("click");
}

 _showProgressBar(false)
}

function checkTemplCode(isFrom,obj)
{
 _showProgressBar(true)
 if(obj.value=="0")
{
		_showErrorMsg(positiveNbAlertTmpl, error_msg_title, 300, 100);
		 _showProgressBar(false)
		return;
}
		
var zSrc=jQuery.contextPath+"/path/templateMaintReport/checkTemplCode.action?templCode="+obj.value;
params ={};
$.getJSON(zSrc, params, function( data2, status, xhr ) 
		{
			var isExist=data2['checkTemplCodeVO']['COMP_CODE'];
 			if(isExist!="-1")
 			{
 				_showErrorMsg(tempCodeExistance, error_msg_title, 300, 100);
				obj.value="";
		    }
 			else
 			{
 				 if(isFrom=="0")
			 	{
			 		 $("#templCode_"+_pageRef).attr("readonly",true);
	    		}
 			}			
 			
 			_showProgressBar(false)
		}); 
}

function newTemplate()
{
  _showProgressBar(true)
$("#templCode_"+_pageRef).attr('readonly', false);
$("#lineNbr_"+_pageRef).attr('readonly', false);
//clear session && //reload grids
var url = jQuery.contextPath+ "/path/templateMaintReport/submitAll";
params = {};
params["mode"]="add";
params["_pageRef"]=_pageRef;
    $.get(url, params , function( param )
 	{
   	  $("#templGrid_"+_pageRef).trigger("reloadGrid");
   	  $("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
   	    _showProgressBar(false)
 	});
	
    $("#templDesc_"+_pageRef).val("");
$("#templCode_"+_pageRef).val("");
$("#templBriefArab_"+_pageRef).val("");
$("#dispValComboId_"+_pageRef).val("D");
	callDependency(	"fcrId_"+_pageRef+":glstmpltCO.FCRStr",
					jQuery.contextPath+'/path/templateMaintReport/fcrDependency.action',
					"glstmpltCO.FCRStr:false",
					"fcrId",
					"")
			
	
    emptyForm();
}

function emptyForm()
{
	//empty inputs

$("#lineNbr_"+_pageRef).val("");
$("#desc1_"+_pageRef).val("");
$("#desc2_"+_pageRef).val("");
$("#formatComboId_"+_pageRef).val("A");
$("#printed_"+_pageRef).attr('checked', true);
$("#bold_"+_pageRef).attr('checked', false);
$("#displZero_"+_pageRef).attr('checked', false);
$("#saveData_"+_pageRef).attr('checked', false);
$("#displTotZero_"+_pageRef).attr('checked', false);
$("#round_"+_pageRef).attr('checked', false);
$("#addDesc1_"+_pageRef).val("");
$("#addDesc2_"+_pageRef).val("");
$("#bottomBorder_"+_pageRef).val("");
$("#lookuptxt_csvLookUp_"+_pageRef).val("");
$("#currencyStr_"+_pageRef).val("");
$("#csv_"+_pageRef).val("");
$("#templBriefArabLine_"+_pageRef).val("");
$("#gltypeid_"+_pageRef).val("");
$("#isSubTotal_"+_pageRef).attr('checked', false);
$("#isCsv_"+_pageRef).attr('checked', false);
$("#isForRound_"+_pageRef).attr('checked', false);
$("#postCode_"+_pageRef).val("");
$("#linePercentage_"+_pageRef).val("");

$("#tabs").tabs('enable', 0)
$("#tabs").tabs('enable', 1)
$("#tabs").tabs('enable', 2)
   		
var zSelected = $('#tabs').tabs('option', 'selected');
globTmpCodeWithLnNb="0~0";
if(zSelected=="0")
{
	url = jQuery.contextPath+ "/path/templateMaintReport/openGLList.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
$("#tabs").tabs( "url" , 0 , url );
$("#tabs").tabs( "load" , 0 );
	
}	
else
{
	$("#tabs").tabs('select',0);
	}	
	



}
		
function deleteTemplate(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
      if(confirmcChoice)
 		{
    	    _showProgressBar(true)
    	   deleteTemp(theArgs.rowid)
       }
      }, {rowid : rowid});
}

function deleteTemp(rowid)
{
	$("#createLikeButtonTmp_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled').removeClass('ui-state-focus');
	$("#openRelatedRep_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled').removeClass('ui-state-focus');
	
	var url = jQuery.contextPath+ "/path/templateMaintReport/submitAjax";
    url = url+"?mode=delete&_pageRef="+_pageRef;
	myObject = $("#templGrid_"+_pageRef).jqGrid('getRowData',rowid);
	params = {};
	paramStr = JSON.stringify(myObject)
	paramStr = "{"+ "templCO:"+paramStr + "}";
	//params["updates"] = paramStr;
	//params["_pageRef"]=_pageRef;
    $.get(url, myObject , function( param )
 	{
   	  $("#templGrid_"+_pageRef).trigger("reloadGrid"); 
   	  $("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
   	  $("#templDesc_"+_pageRef).val("");
       $("#templCode_"+_pageRef).val("");
      $("#dispValComboId_"+_pageRef).val("D");
      $("#lineNbr_"+_pageRef).attr('readonly', false);
		
	    emptyForm();
	    var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
	    if(!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	{
		$(collapseDiv).trigger("click");
	}
	    $("#templCode_"+_pageRef).attr('readonly', false);		
	      _showProgressBar(false)
 	});
}

function loadGL()	
	{
	$("#linesOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
	document.getElementById("linesOK_"+_pageRef).disabled=false
	_showProgressBar(true)
	//below func intialized the control vars new subline for criteria will be generated
	intializeControlVars();
	//end
	$("#mode1").val("update");
	rowid =$("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	viewLineDetails(rowid);
	myObject = $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
	//stopped bbe var zLineNb=myObject["glstmpltVO.LINE_NBR"];

	var zLineNb=myObject["newLineNumber"];

	var zTemplCode=$("#templCode_"+_pageRef).val();

	//check if the line has a gl or an expresssion
	var zSrc=jQuery.contextPath+"/path/templateMaintReport/checkIfExprLine.action?templateCode="+zTemplCode+"&lineNumber="+zLineNb+"&_pageRef="+_pageRef;
		params ={};
	$.getJSON(zSrc, params, function( data2, status, xhr ) 
	{
		var exprGlExist=data2['exprOrGLByLineCO']['exprOrGLByLine'];

		$("#tabs").tabs('enable', 3)
		$("#tabs").tabs('enable', 2)
		$("#tabs").tabs('enable', 1)
		$("#tabs").tabs('enable', 0)
		
			var zSelected = $('#tabs').tabs('option', 'selected');
			globTmpCodeWithLnNb=zTemplCode+"~"+zLineNb;	

			if(exprGlExist[2]=="1")
			{
					if(zSelected=="2")
					{//confirm(7)
						newUrl = jQuery.contextPath+ "/path/templateMaintReport/openExpression.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 2 , newUrl );
						$("#tabs").tabs( "load" , 2 );
					}	
					else
					{//confirm(8)
						$("#tabs").tabs('select',2);
					}
					//confirm(9)
					$("#tabs").tabs({disabled: [0,1,3]});	
			}
			
			
			else if(exprGlExist.substring(3,4)==1 && zSelected=="3")
			{
				//confirm(1)
						url = jQuery.contextPath+ "/path/templateMaintReport/openMismatch.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 3 , url );
						$("#tabs").tabs( "load" , 3 );
					
			}
			else
			{
				if(exprGlExist=="1100" || exprGlExist=="1000") //if  GLs -crt 
				{
					if(zSelected=="0")
					{//confirm(2)
						url = jQuery.contextPath+ "/path/templateMaintReport/openGLList.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 0 , url );
						$("#tabs").tabs( "load" , 0 );
					}	
					else
					{ //confirm(12)
						$("#tabs").tabs('select',0);
					}
					//confirm(13)
					$("#tabs").tabs({disabled: [2]});	
				}
				else if(exprGlExist=="0100") //if  crt-gl
				{
					if(zSelected=="1")
					{//confirm(3)
						newUrl = jQuery.contextPath+ "/path/templateMaintReport/openCriteria.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 1 , newUrl );
						$("#tabs").tabs( "load" , 1 );
					}	
					else
					{//confirm(4)
						$("#tabs").tabs('select',1);
					}	//confirm(5)
					$("#tabs").tabs({disabled: [2]});
				}
				else if(exprGlExist=="0001")
				{
					//confirm(14)
					if(zSelected=="3")
					{
						//confirm(15)
						newUrl = jQuery.contextPath+ "/path/templateMaintReport/openMismatch.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 3 , newUrl );
						$("#tabs").tabs( "load" , 3 );
					}
					else
					{
						//confirm(16)
						$("#tabs").tabs('select',3);
					}
					$("#tabs").tabs({disabled: [2]});
					
				}
				else   //if "000"  no expr and no GLs
				{
					if(zSelected=="0") //load
				{//confirm(10)
						newUrl = jQuery.contextPath+ "/path/templateMaintReport/openGLList.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 0 , newUrl );
						$("#tabs").tabs( "load" , 0 );
				}	
				else //select
				{//confirm(11)
					$("#tabs").tabs('select',0);
				}	
						
				}
			}
			_showProgressBar(false)
	}); 
}

		

function viewLineDetails(rowid)
{
	var lineNbr = $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid)["glstmpltVO.LINE_NBR"];
	var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
	if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	{
		$(collapseDiv).trigger("click");
	}
	var url = jQuery.contextPath+ "/path/templateMaintReport/openLine";
    url = url+"?mode=edit"+"&_pageRef="+_pageRef; 
	myObject = $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
/*	params = {};
	paramStr = JSON.stringify(myObject)
	paramStr = "{"+ "glstmpltCO:"+paramStr + "}";
	params["updates"] = paramStr;*/

    $.post(url, myObject , function( param )
 	{
    	$("#lineNbr_"+_pageRef).attr('readonly', true);
    	$("#csvLookUp_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.CURRENCY)
     	$("#lineNbr_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.LINE_NBR);
     	$("#desc1_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.BRIEF_NAME_ENG);
 		$("#templBriefArabLine_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.BRIEF_NAME_ARAB);
 		$("#postCode_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.POST_CODE);
 		$("#linePercentage_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.PERCENTAGE);
     	PRINTED=param["glstmpltCO"].glstmpltVO.PRINTED;
     	if(PRINTED=="0")
     	{
			$("#printed_"+_pageRef).attr('checked', false);
     	}
     	else
     	{
			$("#printed_"+_pageRef).attr('checked', true);
     	}

   	    BOLD=param["glstmpltCO"].glstmpltVO.BOLD;
   		if(BOLD=="NO")
       	{
  			$("#bold_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#bold_"+_pageRef).attr('checked', true);
       	}


   		DISPLAY_ZEROS=param["glstmpltCO"].glstmpltVO.DISPLAY_ZEROS;	
   		if(DISPLAY_ZEROS=="NO")
       	{
  			$("#displZero_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#displZero_"+_pageRef).attr('checked', true);
       	}
   		
   		SAVE_DATA=param["glstmpltCO"].glstmpltVO.SAVE_DATA;	
   		if(SAVE_DATA=="NO")
       	{
  			$("#saveData_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#saveData_"+_pageRef).attr('checked', true);
       	}

    	DISP_LINE_TOT_ZERO=param["glstmpltCO"].glstmpltVO.DISP_LINE_TOT_ZERO;	
   		if(DISP_LINE_TOT_ZERO=="NO")
       	{
  			$("#displTotZero_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#displTotZero_"+_pageRef).attr('checked', true);
       	}

   		ROUND=param["glstmpltCO"].glstmpltVO.FOR_ROUND;	

   		if(ROUND=="N" || ROUND=="NO")
       	{
  			$("#isForRound_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#isForRound_"+_pageRef).attr('checked', true);
       	}
   		
   		CSV=param["glstmpltCO"].glstmpltVO.CSV
   		if(CSV=="N" || CSV=="NO")
       	{
  			$("#isCsv_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#isCsv_"+_pageRef).attr('checked', true);
       	}
   		
   		ISSUBTOTAL=param["glstmpltCO"].glstmpltVO.IS_SUB_TOTAL
   		if(ISSUBTOTAL=="N" || ISSUBTOTAL=="N")
       	{
  			$("#isSubTotal_"+_pageRef).attr('checked', false);
       	}
       	else
       	{
  			$("#isSubTotal_"+_pageRef).attr('checked', true);
       	}
   		
   		$("#addDesc1_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.ADD_DESC);
   		$("#addDesc2_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.ADD_DESC1);
   		$("#bottomBorder_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.BOTTOM_BORDER);
   		$("#gltypeid_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.GL_TYPE);
   		
   		if(param["glstmpltCO"].glstmpltVO.CURRENCY!=0)
   		$("#lookuptxt_csvLookUp_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.CURRENCY);
   		$("#currencyStr_"+_pageRef).val(param["glstmpltCO"].CURRENCYStr);
   		$("#csv_"+_pageRef).val(param["glstmpltCO"].glstmpltVO.CSV);


        	
 	});
}




function saveLine(newlineNbr)
{ 
  if($("#templCode_"+_pageRef).val()==null || $("#templCode_"+_pageRef).val()=="undefined" || $("#templCode_"+_pageRef).val()=="")
  {
	_showErrorMsg(missingTmplCodeAlert);
	return;
  }
	//get the form values
	var lineNbr=document.getElementById("lineNbr_"+_pageRef).value;
	var mode =$("#mode1").val();
	      var url = jQuery.contextPath+ "/path/templateMaintReport/validateLine";
	      myObject =  $("#lineFrmId_"+_pageRef).serialize();
	  	  myObject += "&mode="+mode+"&templateCode="+$("#templCode_"+_pageRef).val()+"&_pageRef="+_pageRef;
		$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			   if(typeof param["_error"] == "undefined" || param["_error"] == null)
			   {
				  $("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
		    	  $("#templCode_"+_pageRef).attr('readonly', true);		    	  
		    	  
		    	  if(newlineNbr!=null)
		    	  {
		    	  	reloadLineGrd = true;
		    	  	lineToSel = newlineNbr;
		    	  }
		    	  emptyForm();
			   }
			   $("#linesOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
			   document.getElementById("linesOK_"+_pageRef).disabled=true
			 }
	    });		      
}

	var reloadLineGrd = false;
	var lineToSel = -1;

	
function selectRowFn()
{
    rowid = $("#templGrid_"+_pageRef).jqGrid('getGridParam','selrow');
    myObject =$("#templGrid_"+_pageRef).jqGrid('getRowData', rowid);
	var url = jQuery.contextPath+ "/path/templateMaintReport/submitAjax";
    url = url+"?mode=open&_pageRef="+_pageRef;
	myObject = $("#templGrid_"+_pageRef).jqGrid('getRowData',rowid);
	_showProgressBar(true);
		$.post(url, myObject , function( param )
 		{
 			$("#templDiv_"+_pageRef).html(param);	
 			$("#createLikeButtonTmp_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled').addClass('ui-state-focus');
 			$("#createLikeButtonTmp_"+_pageRef).focus();
 			$("#openRelatedRep_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled').addClass('ui-state-focus');
 			$("#openRelatedRep_"+_pageRef).focus();
 			
 			//$("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
 			$("#templLinesGrid_"+_pageRef).trigger("reloadGrid",[{page:1}]);
   			$("#templCode_"+_pageRef).attr('readonly', true);
   					//check if the lineDetailis div is expanded then collapse it
   		var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
   		if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden")==false)
		{
			$(collapseDiv).trigger("click");
			}
			emptyForm();
			_showProgressBar(false);
 		},"html");


}


function openRelatedReportScreen()
{
	$("#relatedReportsDialog_"+_pageRef).dialog('open');
}

function addLine()
{
	$("#relatedReportsGrid_"+_pageRef).jqGrid('addInlineRow',{});
}

$("#relatedReportsGrid_"+_pageRef).subscribe('emptyTemplTrx', function(event,data) 
		{
			 $("#auditTrxNbr_"+_pageRef).val("");
		});
		
		
function loadTemplateOrderCombo(){
	return $("#groupComboList_"+_pageRef).val();
}






function saveRelatedReports()
{
  _showProgressBar(true)
			 var jsonStringUpdates = $("#relatedReportsGrid_"+_pageRef).jqGrid('getChangedRowData');
		 	$("#updateRelReports_"+_pageRef).val(jsonStringUpdates); 
		 	templateCode=$("#templCode_"+_pageRef).val();
		 	 var url = $("#RelRepForm_"+_pageRef).attr("action")+"?_pageRef="+_pageRef+"&templateCode="+templateCode;
		 	 myObject =  $("#RelRepForm_"+_pageRef).serialize();
		 	 
		 	 //bbe adjustment
		 	 var result = $("#relatedReportsGrid_"+_pageRef).jqGrid('checkRequiredCells');
			 if(!result)
				 {
				 	_showProgressBar(false)
				 	return;
				 }
		 	 //bbe adjustment
		 	 
		 	 
		 	 $.post(url, myObject , function()
	 	 		{
		 		 	  _showProgressBar(false)
	 	 			return null;
	 	 		});
	 	 		return null;
}

function deleteRelRepLine(rowid)
{
	 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteRelRepLine(theArgs.rowid)
           }
	      }, {rowid : rowid});	
	
}

 function deleteRelRepLine(rowid)
	 {
	 	  _showProgressBar(true)
		myObject 			= $("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',rowid);
	    var templateCode 	= myObject["ftrTmplRefVO.TEMPLATE_CODE"];
	    var reportRef 		= myObject["ftrTmplRefVO.REPORT_REF"];
	    var reportName		= myObject["REPORT_NAME"];
	    var templateOrder	= myObject["ftrTmplRefVO.TEMPLATE_ORDER"];
	    var appName			= myObject["ftrTmplRefVO.APP_NAME"];
	    var concatKey       = myObject["concatKey"];
		var zSrc			= jQuery.contextPath+ "/path/templateMaintReport/deleteRelRep";
		
		params ={};
		params["templateCode"]	=templateCode;
		params["reportRef"]		=reportRef;
		params["templateOrder"]	=templateOrder;
		params["appName"]		=appName;
		params["reportName"]	=reportName;
		params["concatKey"]     =concatKey;
		params["_pageRef"]      =_pageRef;
		
		if(concatKey!=""&&concatKey!="undefined"&&concatKey!=null&&concatKey!="null")
		{
	 		$.ajax({
	 				url: zSrc,
	 				type: "POST",
	 				data: params,				
	 			    success: function(xml)
	 			    {
		 			   	  $("#relatedReportsGrid_"+_pageRef).trigger("reloadGrid");
		 			   	    _showProgressBar(false)
	 		    	}
	 		});
	 	}
		else
		{
			$("#relatedReportsGrid_"+_pageRef).jqGrid('deleteGridRow'); 
			_showProgressBar(false)
		}
	}
	
function addRelRep()
{	
		$("#relatedReportsGrid_"+_pageRef).jqGrid('addInlineRow',{'ftrTmplRefVO.TEMPLATE_CODE':$("#templCode_"+_pageRef).val()});	
}


function clickOtherButton()
{
	$('#mainFrmId').trigger('submit');
}

function submitLinesOrder()
{
	 _showProgressBar(true)
	var url = jQuery.contextPath+"/path/templateMaintReport/reorganizeLinesOrder.action?";
	 params={};
	 params["interval"] 			= $("#interval_"+_pageRef).val();
	 params["startingLineValue"]	= $("#startingLineValue_"+_pageRef).val();
	 params["valueToRemember"]		= valueToRemember;
	 params["_pageRef"]=_pageRef;
	 valueToRemember = 0;
	 rowid 							= $("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	 myObject 						= $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
	 tempLineNb 					= myObject["glstmpltVO.LINE_NBR"];
	 params["templateCode"]			= $("#templCode_"+_pageRef).val();
	 params["_pageRef"] = _pageRef; 
	 
			$.ajax({
 				url: url,
 				type: "POST",
 				data: params,				
 			    success: function(xml)
 			    {
				if(xml["updates"]=="1")
				{
					_showProgressBar(false)
				}	
				else
				{	
					$("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
		 			$("#lineNbr_"+_pageRef).val(xml["glstmpltCO"].newLineNumber);	
		 			$("#lineNbr_"+_pageRef).attr("readonly",true);
		 			$("#numberAfter_"+_pageRef).val(1);
		 			
		 			$("#tabs").tabs('enable', 0)
					$("#tabs").tabs('enable', 1)
					$("#tabs").tabs('enable', 2)
					   		
					var zSelected = $('#tabs').tabs('option', 'selected');
					globTmpCodeWithLnNb="0~0";
					if(zSelected=="0")
					{
						url = jQuery.contextPath+ "/path/templateMaintReport/openGLList.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
						$("#tabs").tabs( "url" , 0 , url );
						$("#tabs").tabs( "load" , 0 );
						
					}	
					else
					{
						$("#tabs").tabs('select',0);
					}	
		
					var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
					if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
					{
						$(collapseDiv).trigger("click");
					}
					 _showProgressBar(false)	 
 		    	}
				}
 		});


}


function openReorganizeDialog()
{
	$("#reorganizeLines_"+_pageRef).dialog('open');
}

function deleteAllLines()
{
	_showConfirmMsg(deleteAllLiness,deleteAll, function(confirmcChoice, theArgs){
	           if(confirmcChoice)
	           {
	        	   _showProgressBar(true)
					var zSrc = jQuery.contextPath+"/path/templateMaintReport/deleteAllTemplateLines.action?_pageRef="+_pageRef;
			 		$.ajax({
			 				url: zSrc,
			 				type: "POST",				
			 			    success: function(xml)
			 			    {
			 					$("#templGrid_"+_pageRef).trigger("reloadGrid");
				 			   	$("#templLinesGrid_"+_pageRef).trigger("reloadGrid");
				 			   	$("#glGrids_"+_pageRef).trigger("reloadGrid");
				 			   	$("#crtGrids").trigger("reloadGrid");
				 			   	$("#exprTemplGrid_"+_pageRef).trigger("reloadGrid");
								var collapseDiv = $("#GLDivId > .collapsibleContainerTitle").get(0);
								if (!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden")) 
								{
									$(collapseDiv).trigger("click");
								}
								var collapseDiv = $("#lineDivId > .collapsibleContainerTitle").get(0);
								if(!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
								{
									$(collapseDiv).trigger("click");
								}
								
								emptyForm();
								
								_showProgressBar(false)
			 		    	}
			 		
			 		});
	           }
		      });	
}


/*function openOwnershipPercentageScreen()
{
	$("#ownershipPercDialog_"+_pageRef).dialog('open');
}

function openHeaderOperatorScreen()
{
	$("#headerOperatorDialog_"+_pageRef).dialog('open');
}*/

function openCreateLikePopup()
{
	$("#createLikeDialog_"+_pageRef).dialog('open');
}

function saveCreateLike(obj)
{
	if($("#newCode_"+_pageRef).val()=="")
	{
		_showErrorMsg(specifyCode,error_msg_title,300,100)
		return ;
	}
	else
	{
			_showProgressBar(true)
			var zSrc=jQuery.contextPath+"/path/templateMaintReport/checkTemplCode.action?templCode="+$("#newCode_"+_pageRef).val();	
			var url = jQuery.contextPath+"/path/templateMaintReport/saveCreateLike.action?";
						 params={};
						 params["newCode"] 			= $("#newCode_"+_pageRef).val();
						 params["_pageRef"]=_pageRef;
						 
			$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{
				var isExist=data2['checkTemplCodeVO']['COMP_CODE'];
		 			if(isExist!="-1")
		 			{
		 				_showErrorMsg(tempCodeExistance);
						$("#templCode_"+_pageRef).val("");
						
				 	}
		 			else
		 			{
		 				var url = jQuery.contextPath+"/path/templateMaintReport/saveCreateLike.action?";
					 	params={};
					 	params["newCode"] = $("#newCode_"+_pageRef).val();
					    params["_pageRef"]=_pageRef;
							$.ajax({
				 				url: url,
				 				type: "POST",
				 				data: params,				
				 			    success: function(param)
				 			    {
								    if(typeof param["_error"] == "undefined" || param["_error"] == null)
								   		{
											$("#templGrid_"+_pageRef).trigger("reloadGrid");
							 		    }
						 		}
					 			});
		 			}
		 			_showProgressBar(false)
		 			$(obj).dialog('close');
				}); 
	}
}

function onChangeTempOrder()
{
	var rowId 			= $("#relatedReportsGrid_"+_pageRef).jqGrid('getGridParam','selrow');	
	var myObject     	= $("#relatedReportsGrid_"+_pageRef).jqGrid('getRowData',rowId);	
	if(myObject["ftrTmplRefVO.REPORT_REF"]!="" && myObject["ftrTmplRefVO.TEMPLATE_ORDER"]!="")
	{
		_showProgressBar(true);
		params={};
		params["updates"]=myObject["ftrTmplRefVO.REPORT_REF"];
		params["code"]=myObject["ftrTmplRefVO.TEMPLATE_ORDER"];
		var zSrc = jQuery.contextPath+ "/path/templateMaintReport/checkOrderInOtherTemplate";
		$.ajax({
				url: zSrc,
				type: "POST",
				data: params,				
			    success: function(xml)
			    {
				   	    _showProgressBar(false)
				   	    if(xml["updates1"]>0)
				   	    {
				   	      _showErrorMsg(orderAlreadyInUse,error_msg_title,300,100);
				   	      $("#relatedReportsGrid_"+_pageRef).jqGrid('setCellValue',rowId,'ftrTmplRefVO.TEMPLATE_ORDER',"");
				   	    }
				   	    _showProgressBar(false);
		    	}
			});
	}
}	

function checkTmplSelectedTab(selectedTabId, event)
{
	
 				var url = $.data(event.originalEvent.ui.tab, 'load.tabs');
                var cached = $.data(event.originalEvent.ui.tab, 'cache.tabs');
			 	if(typeof cached == "undefined" && typeof url != "undefined")
			 	{
  			         var index ;
      			 	if(selectedTabId=="custTab1")
      			 	{
      			 		newUrl = jQuery.contextPath+ "/path/templateMaintReport/openGLList.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
      			 	    index =0;
      			 	}
      			 	else if(selectedTabId=="custTab2")
      			 	{
      			 		newUrl = jQuery.contextPath+ "/path/templateMaintReport/openCriteria.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
      			 	    index =1;
      			 	}
      			 	else if(selectedTabId=="custTab3")
      			 	{
      			 		newUrl = jQuery.contextPath+ "/path/templateMaintReport/openExpression.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
      			 		index =2;
      			 	}
      			 	else if(selectedTabId=="custTab4")
      			 	{
      			 		newUrl = jQuery.contextPath+ "/path/templateMaintReport/openMismatch.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
      			 		index =3;
      			 	}
      			 	 $("#tabs").tabs("url",index,newUrl);

      			 }
  }

function intializeControlVars()
{

	 $("#crt_subLnNbr_"+_pageRef).val("");
	 //first double click on the lines grid
	 if(selectedLineGrid==0)
	 {
	 	rowid =$("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject = $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
	 	selectedLineGrid=myObject["newLineNumber"];
	 }
	 else
	 {
	 	rowid =$("#templLinesGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject = $("#templLinesGrid_"+_pageRef).jqGrid('getRowData',rowid);
		//if the user double clicked on another line in lines grid,all the controls should be reinitialized
		if(selectedLineGrid!=myObject["newLineNumber"])
		{
			 userClickedCrtOk = 1;
			 shouldIncrementCrt = 1;
			 returnedNewLineNumberCriteria = 0;
			 userClickedOk = 1;//to know if we should increment the sub line number or not
			 shouldIncrement = 1;
			 returnedNewLineNumber = 0;
			 selectedLineGrid = myObject["newLineNumber"];
			 $("#subLineNb_"+_pageRef).val("");
		}
	 }
}
