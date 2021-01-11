function showHideElements(source,sessVal)
	{
		resetForm();
		if((source =="1" || source =="3" || source =="10") && $("#cifAuditYn_"+_pageRef+":checked").val())
		{
			$("#cifAuditFlag_"+_pageRef).attr("style","visibility: inline;")
			$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: inline;")
		}
		else
		{
			$("#cifAuditFlag_"+_pageRef).attr("style","visibility: hidden;")
			$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: hidden;")
		}
		document.getElementById('argumentType_'+_pageRef).style.display = 'none';
		document.getElementById('typeLabel_'+_pageRef).style.display = 'none';
		document.getElementById("multiselectFlag_"+_pageRef).style.display = 'none';
		document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'none';		
		document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'none';
		document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'none';	
		document.getElementById('linkQuery_'+_pageRef).style.display = 'none';

		if(source == "1")
		{
			document.getElementById('argumentType_'+_pageRef).style.display = '';
			document.getElementById('typeLabel_'+_pageRef).style.display = '';
			document.getElementById('valueLabel_'+_pageRef).style.display = '';
			document.getElementById('valLbl_'+_pageRef).style.display = '';
			document.getElementById('argumentValue_'+_pageRef).style.display = '';
			document.getElementById('argValTd_'+_pageRef).style.display = '';
			document.getElementById('linkQueryDV_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = '';
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = '';
			document.getElementById('constrContainer_'+_pageRef).style.display='inline';
		}
		else
		if(source == "2" || source=="8")
		{
			document.getElementById('sessionAttrCmb_'+_pageRef).style.display = '';
			document.getElementById('valueLabel_'+_pageRef).style.display = '';
			document.getElementById('valLbl_'+_pageRef).style.display = '';
			document.getElementById('argumentValue_'+_pageRef).style.display = '';
			document.getElementById('argValTd_'+_pageRef).style.display = '';
			document.getElementById('linkQueryDV_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = 'none';
			document.getElementById('constrContainer_'+_pageRef).style.display='none';
			callDependency("sessionAttrCmb_"+_pageRef+":sessionAttrList,argumentType_"+_pageRef+":argumentType",
			jQuery.contextPath + "/path/designer/queryDesign_fillSessionArgBySource?update1="+source+"&_pageRef="+_pageRef
			+"&sessionAttr="+sessVal,"" , "sessionAttrCmb_"+_pageRef, "fillSessionComboValue('"+sessVal+"')");
		}
		else
		if(source == "3" || source == "10")
		{
			document.getElementById('linkQuery_'+_pageRef).style.display = '';
			
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = '';
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = '';
			document.getElementById('argumentValue_'+_pageRef).style.display = '';
			document.getElementById('argValTd_'+_pageRef).style.display = '';
			document.getElementById('constrContainer_'+_pageRef).style.display='inline';
			document.getElementById("multiselectFlag_"+_pageRef).style.display = 'inline';
			document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'inline';
			document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'inline';
			document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'inline';
		}
		else
		if(source == "4")
		{
			document.getElementById('valueFlag_'+_pageRef).style.display = '';
			document.getElementById('valFlagChk_'+_pageRef).style.display = '';
			document.getElementById('valueOnLabel_'+_pageRef).style.display = '';
			document.getElementById('valueOffLabel_'+_pageRef).style.display = '';
			document.getElementById('falgValueOn_'+_pageRef).style.display = '';
			document.getElementById('falgValueOff_'+_pageRef).style.display = '';
			
			$("#falgValueOn_"+_pageRef).val("Y");
			$("#falgValueOff_"+_pageRef).val("N");
			$('#argumentType_'+_pageRef).val("VARCHAR2");
			document.getElementById('linkQueryDV_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = '';
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = '';
			
			
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('valueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('valLbl_'+_pageRef).style.display = 'none';
			document.getElementById('constrContainer_'+_pageRef).style.display='inline';
			
		}
		
		else if(source == "5")
		{
			document.getElementById('argumentType_'+_pageRef).style.display = '';
			document.getElementById('typeLabel_'+_pageRef).style.display = '';
			document.getElementById('linkQueryDV_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('valueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('valLbl_'+_pageRef).style.display = 'none';
			document.getElementById('constrContainer_'+_pageRef).style.display='none';
			
			$('#argumentType_'+_pageRef).val("VARCHAR2");
		}
		else if(source=="6" || source=="7")
		{
			document.getElementById('argumentType_'+_pageRef).style.display = '';
			$('#argumentType_'+_pageRef).val("DATETIME");
			
			document.getElementById('argumentType_'+_pageRef).disabled = true;
			
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('valueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('valLbl_'+_pageRef).style.display = 'none';
			if(source=="6")
			{
				document.getElementById('constrContainer_'+_pageRef).style.display='inline';
			}
			else
			{
				document.getElementById('constrContainer_'+_pageRef).style.display='none';	
			}
		}
		else if(source=="9")
		{
			document.getElementById('argumentType_'+_pageRef).style.display = '';
			$('#argumentType_'+_pageRef).val("VARCHAR2");		
			$("#argumentType_"+_pageRef).attr("disabled","disabled");		
			document.getElementById('argumentSrcDefault_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('valueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('valLbl_'+_pageRef).style.display = 'none';
			document.getElementById('constrContainer_'+_pageRef).style.display='none';	
		}
		showHideUnusedInput();
	}
	
	function fillSessionComboValue(sessVal)
	{
		$("#sessionAttrCmb_"+_pageRef).val(sessVal);
		//check if combo empty otherwise if choosing session attributes of type varchar or date they will be saved as number
		if($("#argumentType_"+_pageRef).val()=="" || $("#argumentType_"+_pageRef).val()==null)
		{	
			$("#argumentType_"+_pageRef).val($("#argumentType_"+_pageRef+" option:first").val());
		}
	}

autoCompleteConstraints = function(theInputId,expression_cust_tags)
			{ 
				 var theInput = $("#"+theInputId);
				 // don't navigate away from the field on tab when selecting an item
			     theInput.bind( "keydown", function( event )
			     {
				   	if( event.keyCode === $.ui.keyCode.DOWN && !theInput.isopened)
				   	{
				       	theInput.autocomplete( "search", "" );
				   	}
			     })
			     .autocomplete({
			       minLength: 0,
			       inputId:theInputId,
			       source: expression_cust_tags,
			       open: function( event, ui )
			       {
			       	theInput.isopened = true;
			       },
			       close: function( event, ui )
			       {
			       	theInput.isopened = false;
			       },
			       focus: function()
			       {
			         // prevent value inserted on focus
			         return false;
			       },
			       select: function( event, ui )
			       {
			          var cursPosition   = returnCursorPosStart(document.getElementById(theInputId));
			    	  var strTillCurrPos = this.value.substring(0,cursPosition)
			    	  /**
			    	   * [MarwanMaddah]: this pattern will catch all the words 
			    	   * that are exists in the input from index 0 untill the current cursor position
			    	   * then the last word will be replaced by the selected value from the Search result
			    	   */
			    	  var patt       = /\w+/g;
			          var result     = strTillCurrPos.match(patt);
			          var firstPart  = "";
			          if(result!= null && result.length > 0)
			          {        	  
			             var resultLgth = result.length;
			    	     firstPart  = strTillCurrPos.substring(0,strTillCurrPos.lastIndexOf(result[resultLgth-1])); 
			          }
			          else
			          {
			        	 firstPart = strTillCurrPos; 
			          }
			          this.value = firstPart + " " +ui.item.value +" "+ this.value.substring(cursPosition);
			          return false;
			       }
			     });
};	

function changedArgName()
{
	if(_pageRef!="RD00Q")
	{
		var argumentsMode = $("#argumentsMode_"+_pageRef).val();
		//if mode add and user changed the arg name then update the session entry 
		if(argumentsMode=="add")
		{
			var oldArgName = $("#oldArgName_"+_pageRef).val();
			var newArgName = $("#argumentName_"+_pageRef).val();
			if(oldArgName!='')
			{
				params={};
				params["_pageRef"]		=_pageRef;
				params["oldArgName"]	=oldArgName;	
				params["repArgsName"]	=newArgName;
				params	
				var url =jQuery.contextPath+"/path/designer/queryDesign_adjustArgMap";
				$.ajax({
				 url: url,
		         type:"post",
				 dataType:"json",
				 data: params,
				 success: function(param)
						 {
						   $("#oldArgName_"+_pageRef).val($("#argumentName_"+_pageRef).val());
						 }
		    	      });			
		     } 
		     else
		     {
		     	$("#oldArgName_"+_pageRef).val($("#argumentName_"+_pageRef).val());
		     }
		}
	}
}

function openConstraintsDialog()
{			

	if(!requiredArgFieldsFilled())
	{		
		return;
	}
	var argIndex = $("#argumentIndex_"+_pageRef).val();
	var argumentType = $("#argumentType_"+_pageRef).val();
	var source = $("#argumentSource_"+_pageRef).val();
	var argName = $("#argumentName_"+_pageRef).val();
	params = {};
	params["_pageRef"] 		=_pageRef;
	params["updates"]		=argIndex;
	params["argumentType"] 	=argumentType;
	params["updates1"]		=source;
	params["repArgsName"]	=argName;
	dialogUrl= jQuery.contextPath+ "/path/designer/queryDesign_openArgConstraintsDialog"
			dialogOptions={ autoOpen: false,
							height:450,
							title:argumentConstraints,
							width:575 ,
							modal: true,
							close: function( event, ui ) {closeArgConstrDialog},
							buttons: [{ text : paramsOk,click : saveArgConstr},
							          { text : paramsCancel, click : closeArgConstrDialog}
					          ]
			   			  }
	 		$.post(dialogUrl ,params , function( param )
		 	{
	    	  $('#constraintsMainDialog_'+_pageRef).html(param) ;
			  $('#constraintsMainDialog_'+_pageRef).dialog(dialogOptions)
			  $('#constraintsMainDialog_'+_pageRef).dialog('open');
			  compShowHideData(argIndex);
			},"html");  
			
					
}


function compShowHideData(argIndex)
{
		var argumentType 	= $("#argumentType_"+_pageRef).val();
		var argumentLabel 	= $("#argumentLabel_"+_pageRef).val();
		params={};
		params["_pageRef"]		=_pageRef;
		params["updates"]		=argIndex;
		params["argumentType"]	=argumentType;
		params["repArgsName"]	=argumentLabel;

	    var url =jQuery.contextPath+"/path/designer/queryDesign_retTextAreaData";
		$.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
				 {
				   expression_comparison=(param["updates1"]).split(",");
				   expression_showHide = (param["update1"]).split(",");
				   autoCompleteConstraints("comparisonId_"+_pageRef,expression_comparison);
				   autoCompleteConstraints("showExprId_"+_pageRef,expression_showHide);
				   autoCompleteConstraints("hideExprId_"+_pageRef,expression_showHide);
				 }
    	      });		 
}

function closeArgConstrDialog()
{
	 $('#constraintsMainDialog_'+_pageRef).dialog('close');	
}

function saveArgConstr()
{
	_showProgressBar(true);
	var argIndex = $("#argumentIndex_"+_pageRef).val();
	var argumentName = $("#argumentName_"+_pageRef).val();
	//var argumentLabel = $("#argumentLabel_"+_pageRef).val();
	var url = jQuery.contextPath+"/path/designer/queryDesign_saveArgConstraints.action?updates="+argIndex+"&updates1="+argumentName;//+"&update1="+argumentLabel;
	myObject =  $("#argConstrFrmId_"+_pageRef).serialize();
		$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data: myObject,
			 success: function(param)
			 {
			  	 if(param["updates1"]!=argumentName)
			  	 {
			  		_showProgressBar(false);
			  	 	_showErrorMsg(param["updates1"],error_msg_title,300 ,150);
			  	 }
			  	 else
			  	 {
			  		_showProgressBar(false);
			  	 	$('#constraintsMainDialog_'+_pageRef).dialog('close');	
			  	 	$("#argumentName_"+_pageRef).attr('readonly',true);	
			  	 }
			 }
	    });		
}		
	
function emptyArgConstrData()
{
	if("RD00Q"!=_pageRef)
	{
		//added below to empty the arg constraint data
		var argIndex = $("#argumentIndex_"+_pageRef).val();
		var argName = $("#argumentName_"+_pageRef).val();
		params = {};
		params["_pageRef"] 		=_pageRef;
		params["updates"]		=argIndex;
		params["updates1"]		=argName;
		var url =jQuery.contextPath+"/path/designer/queryDesign_emptyArgConstraintData";
		$.ajax({
		 url: url,
		       type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
				 {
				   
				 }
		 });
	}
}

function argumentRowClicked()
{
	parseNumbers();
	rowid = $("#argumentsGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	myObject = $("#argumentsGrid_"+_pageRef).jqGrid('getRowData', rowid);
	fillFormElements(myObject);
	$("#argumentName_"+_pageRef).attr('readonly',true);	
	$("#argumentsMode_"+_pageRef).val("edit");
	if(_pageRef==queriesProgRef)
	{
		document.getElementById("constrContainer_"+_pageRef).style.display = "none";
	}
}

function emptyArguments()
{
		$("#argValueDate_"+_pageRef).val("");
		$("#argValueDateTime_"+_pageRef).val("");
		document.getElementById('argValueDate_'+_pageRef).style.display = 'none';
		document.getElementById('normalDatePick_'+_pageRef).style.display = 'none';
		document.getElementById('argValueDate_'+_pageRef).disabled=true;
		document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
		document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
		document.getElementById('argValueDateTime_'+_pageRef).disabled=true;
		$("#argumentValue_"+_pageRef).attr('disabled',false);
		$("#argumentLabel_"+_pageRef).val("");
		$("#argumentIndex_"+_pageRef).val("0");
		$("#argumentName_"+_pageRef).val("");
		$("#oldArgName_"+_pageRef).val("");
		$("#argumentsMode_"+_pageRef).val("add");
		$("#argOrder_"+_pageRef).val("");
		$("#displayFlag_"+_pageRef).prop('checked', '');
		$("#enableFlag_"+_pageRef).prop('checked', '');
		$("#argumentSrcDefault_"+_pageRef).val("");
		document.getElementById("linkQueryDV_"+_pageRef).style.display = 'none';
		$("#argumentValue_"+_pageRef).attr('readonly', false);
		$("#multiselectFlag_"+_pageRef).attr('checked', false);
		document.getElementById("multiselectFlag_"+_pageRef).style.display = 'none';//bbe
		document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'none';
		document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'none';
		document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'none';
		
		$("#qryDfltValId_"+_pageRef).val("");
		$("#qryDfltValName_"+_pageRef).val("");
		$("#qryDfltValColName_"+_pageRef).val("");
		$("#defaultValueColDesc_"+_pageRef).val("");
		$("#srcOrDflt_"+_pageRef).val("");

		$("#updatesLinkQryArgs_"+_pageRef).val("");
		$("#repArgsName_"+_pageRef).val("");
		$("#qryId_"+_pageRef).val("");
		$("#link_rep_qry_arg_"+_pageRef).val("");
		
		if(_pageRef=="RD00UD")// || _pageRef=="RD00R")// to prevent empty the qry name in queries section
	    {
			$("#qryName_"+_pageRef).val("");
	    }
		$("#columnName_"+_pageRef).val("");
		$("#columnDesc_"+_pageRef).val("");
		$("#argQueryOptions_"+_pageRef).val("1");
		$("#oldRepArgsName_"+_pageRef).val("");
		
		showHideElements(1);
		$("#argumentSource_"+_pageRef).prop('selectedIndex', '0');
		if(document.getElementById('repParams_'+_pageRef)!=null)
		{
			document.getElementById('repParams_'+_pageRef).selectedIndex = 0;
		}
		if(_pageRef==queriesProgRef)
		{
			document.getElementById("constrContainer_"+_pageRef).style.display = "none";
		}
}


/**********************************************/

	function dispLnkQryButt(from)
	{
		if(_pageRef=="RD00UD" || _pageRef=="RD00R")
		{
			var srcVal = $("#argumentSrcDefault_"+_pageRef).val();
			var argSrc = $("#argumentSource_"+_pageRef).val();
			document.getElementById("valueFlag_"+_pageRef).style.display = 'none';
			document.getElementById('valFlagChk_'+_pageRef).style.display = 'none';
			if(srcVal=="1")
			{
				
				document.getElementById("linkQueryDV_"+_pageRef).style.display = 'none';
				document.getElementById("argumentSrcDefault_"+_pageRef).style.display = '';
				document.getElementById("argumentValTypeLabel_"+_pageRef).style.display = '';
				document.getElementById("argumentValue_"+_pageRef).style.display = '';
				document.getElementById('argValTd_'+_pageRef).style.display = '';
				document.getElementById("valueLabel_"+_pageRef).style.display = '';
				document.getElementById('valLbl_'+_pageRef).style.display = '';
				$("#argumentValue_"+_pageRef).attr('readonly', false);
				if(argSrc==4)
				{
					document.getElementById("valueFlag_"+_pageRef).style.display = '';
					document.getElementById('valFlagChk_'+_pageRef).style.display = '';
					document.getElementById("argumentValue_"+_pageRef).style.display = 'none';
					document.getElementById('argValTd_'+_pageRef).style.display = 'none';
					document.getElementById("valueLabel_"+_pageRef).style.display = 'none';
					document.getElementById('valLbl_'+_pageRef).style.display = 'none';
				}
				
			}
			else if(srcVal=="2")
			{
				document.getElementById("linkQueryDV_"+_pageRef).style.display = '';
				$("#argumentValue_"+_pageRef).val("");
				document.getElementById("argumentValue_"+_pageRef).style.display = 'none';
				document.getElementById('argValTd_'+_pageRef).style.display = 'none';
				document.getElementById("valueLabel_"+_pageRef).style.display = 'none';
				document.getElementById('valLbl_'+_pageRef).style.display = 'none';
				if(argSrc==4)
				{
					document.getElementById("valueFlag_"+_pageRef).style.display = 'none';
					document.getElementById('valFlagChk_'+_pageRef).style.display = 'none';
				}
			}
			if(from==0)
			{
				document.getElementById("multiselectFlag_"+_pageRef).style.display = 'none';
				document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'none';
				document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'none';
				document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'none';
			}
		}
		
	}

	

	function fillFormElements(obj)
	{
		var source = obj["ARGUMENT_SOURCE"];
		showHideElements(source,obj["SESSION_ATTR_NAME"]);
		$("#argumentLabel_"+_pageRef).val(obj["ARGUMENT_LABEL"]);
		if(obj["ARGUMENT_TYPE"]=="NUMBER" && $("#cifAuditYn_"+_pageRef+":checked").val())
		{
			$("#cifAuditFlag_"+_pageRef).attr("style","visibility: inline;")
			$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: inline;")
		}
		else
		{
			$("#cifAuditFlag_"+_pageRef).attr("style","visibility: hidden;")
			$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: hidden;")
		}
		if(obj["SESSION_ATTR_NAME"]=="")
		{
			$("#argumentType_"+_pageRef).val(obj["ARGUMENT_TYPE"]);
		}
		$("#argumentType_"+_pageRef).val(obj["ARGUMENT_TYPE"]);
		document.getElementById("multiselectFlag_"+_pageRef).style.display = 'none';//bbe
		document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'none';
		document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'none';
		document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'none';
		if(obj["ARGUMENT_TYPE"]=='DATE')
		{
			document.getElementById('argValueDate_'+_pageRef).style.display = 'inline';
			document.getElementById('normalDatePick_'+_pageRef).style.display = 'inline';
			document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'inline';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'inline';
			document.getElementById('argValueDate_'+_pageRef).disabled = false;
			document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
			document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
			document.getElementById('argValueDateTime_'+_pageRef).disabled = true;
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).disabled = true;
			$("#argValueDate_"+_pageRef).attr('readonly', false);
			$("#argValueDate_"+_pageRef).val(obj["ARGUMENT_VALUE"]);
			$('#argumentDateValue_'+_pageRef).val(obj["ARGUMENT_DATE_VALUE"]);
		}
		else if (obj["ARGUMENT_TYPE"]=='DATETIME')
		{
			document.getElementById('argValueDateTime_'+_pageRef).style.display = 'inline';
			document.getElementById('detDatePick_'+_pageRef).style.display = 'inline';
			document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'inline';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'inline';
			document.getElementById('argValueDateTime_'+_pageRef).disabled = false;
			document.getElementById('argValueDate_'+_pageRef).style.display = 'none';
			document.getElementById('normalDatePick_'+_pageRef).style.display = 'inline';
			document.getElementById('argValueDate_'+_pageRef).disabled = true;
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).disabled = true;
			$("#argValueDateTime_"+_pageRef).attr('readonly', false);
			$("#argValueDateTime_"+_pageRef).val(obj["ARGUMENT_VALUE"]);
			$('#argumentDateValue_'+_pageRef).val(obj["ARGUMENT_DATE_VALUE"]);
		}
		else
		{	document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
		    document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
		   	document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValueDateTime_'+_pageRef).disabled = true;
			document.getElementById('argValueDate_'+_pageRef).style.display = 'none';
			document.getElementById('normalDatePick_'+_pageRef).style.display = 'none';
			document.getElementById('argValueDate_'+_pageRef).disabled = true;
			document.getElementById('argumentValue_'+_pageRef).style.display = 'inline';
			document.getElementById('argValTd_'+_pageRef).style.display = 'inline';
			document.getElementById('argumentValue_'+_pageRef).disabled = false;
			$("#argumentValue_"+_pageRef).val(obj["ARGUMENT_VALUE"]);
			$('#argumentDateValue_'+_pageRef).val(obj["ARGUMENT_DATE_VALUE"]);
		}
		$("#argumentIndex_"+_pageRef).val(obj["index"]);
		$("#argumentName_"+_pageRef).val(obj["ARGUMENT_NAME"]);
		$("#argumentSource_"+_pageRef).val(obj["ARGUMENT_SOURCE"]);
		$("#argOrder_"+_pageRef).val(obj["ARGUMENT_ORDER"]);
		$("#displayFlag_"+_pageRef).attr('checked',obj["DISPLAY_FLAG"] == 'Y'? true : false);
		$("#enableFlag_"+_pageRef).attr('checked',obj["ENABLE_FLAG"] == 'Y'? true : false);
		$("#argumentSrcDefault_"+_pageRef).val(obj["ARGUMENT_SRC_DEFAULT"]);
		
		$("#link_rep_qry_arg_"+_pageRef).val(obj["LINK_REP_QRY_ARG"]);
		
		document.getElementById('linkQuery_'+_pageRef).style.display = 'none';
		
		$("#repArgsName_"+_pageRef).val(obj["ARGUMENT_NAME"]);
		$("#oldRepArgsName_"+_pageRef).val(obj["ARGUMENT_NAME"]);
		
		$("#argTypeDesc_"+_pageRef).val(obj["ARGUMENT_TYPE"]);
		
		$("#qryDfltValName_"+_pageRef).val(obj["DEFAULT_VAL_QRY_NAME"]);
		$("#qryDfltValColName_"+_pageRef).val(obj["DEFAULT_VALUE_COL_NAME"]);
		$("#defaultValueColDesc_"+_pageRef).val(obj["DEFAULT_VALUE_COL_DESC"]);
		$("#qryDfltValId_"+_pageRef).val(obj["QUERY_ID_DEFAULT"]);
		
		document.getElementById("valueFlag_"+_pageRef).style.display = 'none';
		document.getElementById('valFlagChk_'+_pageRef).style.display = 'none';

		if(obj["DEFAULT_VAL_QRY_NAME"])
		{
			document.getElementById("linkQueryDV_"+_pageRef).style.display = '';
			document.getElementById("valueFlag_"+_pageRef).style.display = 'none';
			document.getElementById('valFlagChk_'+_pageRef).style.display = 'none';
			document.getElementById("argumentValue_"+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById("valueLabel_"+_pageRef).style.display = 'none';
			document.getElementById('valLbl_'+_pageRef).style.display = 'none';
			
			$("#argumentValue_"+_pageRef).attr('readonly', true);
			$.ajax({
			url: jQuery.contextPath+'/path/designer/queryDesign_dependencyByQryDfltValId.action?_pageRef='+_pageRef,
			type: "POST",
			data: ({queryDfltValIdLkp : obj["QUERY_ID_DEFAULT"]}),				
		    success: function(json){
		    	var options = [];
		    	var colsName = json["qryColumnsDfltValList"]; 
		  
	  			}
		});
			
		}
		else
		{
			$("#argumentValue_"+_pageRef).attr('readonly', false);
			document.getElementById("linkQueryDV_"+_pageRef).style.display = 'none';
			if(source == "4")
			{
				document.getElementById("valueFlag_"+_pageRef).style.display = '';
				document.getElementById('valFlagChk_'+_pageRef).style.display = '';
			}
		}
	
	    if(obj["ARGUMENT_SRC_DEFAULT"]=="2")
		{
			$("#argumentValue_"+_pageRef).attr('readonly', true);
			document.getElementById("linkQueryDV_"+_pageRef).style.display = '';
			
		}
		
		
		if(_pageRef=="RD00UD")// || _pageRef=="RD00R")// to prevent empty the qry name in queries section
	    {
			$("#qryName_"+_pageRef).val("");
	    }
		$("#columnName_"+_pageRef).val("");
		$("#columnDesc_"+_pageRef).val("");
		$("#argQueryOptions_"+_pageRef).val("1");
		
		$("#qryId_"+_pageRef).val("");
		if(obj["TO_SAVE_YN"]==1)
		{	
			$("#toSaveFlag_"+_pageRef).attr('checked', true);
		}
		else
		{
			$("#toSaveFlag_"+_pageRef).attr('checked', false);
		}
		if($("#cifAuditYn_"+_pageRef+":checked").val())
		{
			if(obj["CIF_AUDIT_YN"]==1)
			{	
				$("#cifAuditFlag_"+_pageRef).attr('checked', true);
			}
			else
			{
				$("#cifAuditFlag_"+_pageRef).attr('checked', false);
			}
		}
		if(source == "2")
		{
			$("#sessionAttrCmb_"+_pageRef).val(obj["SESSION_ATTR_NAME"]);
		}
		else if(source == "3" || source == "10")
		{
			$("#qryName_"+_pageRef).val(obj["QUERY_NAME"]);
			$("#columnName_"+_pageRef).val(obj["COLUMN_NAME"]);
			$("#columnDesc_"+_pageRef).val(obj["COLUMN_DESC"]);
			$("#argQueryOptions_"+_pageRef).val(obj["ARG_QUERY_OPTIONS"]);
			$("#qryId_"+_pageRef).val(obj["QUERY_ID"]);
			document.getElementById('linkQuery_'+_pageRef).style.display = '';

			$.ajax({
				url: jQuery.contextPath+'/path/designer/queryDesign_dependencyByQryId.action?_pageRef='+_pageRef,
				type: "POST",
				data: ({queryIdLkp : obj["QUERY_ID"]}),				
			    success: function(json){
			    	var options = [];
			    	var colsName = json["qryColumnsList"]; 
			    	var selected = "";
			    	
			    	// THIS BOUCLE IS NOT USED 
				    for (var i = 0; i < colsName.length; i++) {
					    if(colsName[i] == obj["COLUMN_NAME"])
						    selected = "selected";
					    else
					    	selected = "";

				    	options.push('<option value="',
				    			colsName[i], '" ',selected,'>',
				    			colsName[i], '</option>');
				    }
    			}
			});
			if((_pageRef=="RD00R" && showRepArgQryLink==false))
			{
				document.getElementById("multiselectFlag_"+_pageRef).style.display = 'none';
				document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'none';
				document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'none';
				document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'none';
			}	
			else
			{
				document.getElementById("multiselectFlag_"+_pageRef).style.display = 'inline';
				document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'inline';
				document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'inline';
				document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'inline';
			}
			if(obj["MULTISELECT_YN"]==1)
			{	
				$("#multiselectFlag_"+_pageRef).attr('checked', true);
			}
			else
			{
				$("#multiselectFlag_"+_pageRef).attr('checked', false);
			}
		}
		else if(source == "4")
		{
			var falgValueOn = obj["FLAG_VALUE_ON"];
			var falgValueOff = obj["FLAG_VALUE_OFF"];
			var checked = obj["ARGUMENT_VALUE"] == falgValueOn? true : false;
			$("#valueFlag_"+_pageRef).attr('checked',checked);
			$("#falgValueOn_"+_pageRef).val(falgValueOn);
			$("#falgValueOff_"+_pageRef).val(falgValueOff);
			
		}
		else if(source =="6" || source =="7")
		{
		  document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
		  document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
		}
		else if(source=="9")
		{
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).disabled = true;
		}
	}
	
		

	function requiredArgFieldsFilled()
	{
	
	   if(_pageRef=="RD00UD" || _pageRef=="RD00R")
	   {
	   	  if(showRepArgQryLink == false)
	   	  {
	   	  	if($("#argumentType_"+_pageRef).val()==""
				|| $("#argumentName_"+_pageRef).val() == "" )
			{
				_showErrorMsg(argFeRequired);
				return 0;
			}
	   	  }
	   	  else
	   	  {
			if($("#argumentLabel_"+_pageRef).val()=="" || $("#argumentType_"+_pageRef).val()==""
				|| $("#argumentName_"+_pageRef).val() == "" || $("#argOrder_"+_pageRef).val()=="")
			{
				_showErrorMsg(argFeRequired);
				return 0;
			}
	   	  }
	   }
	   else if(_pageRef=="RD00Q")
	   {
	   		if($("#argumentType_"+_pageRef).val()==""
				|| $("#argumentName_"+_pageRef).val() == "" || ($("#argumentValue_"+_pageRef).val()=="" && $("#argValueDate_"+_pageRef).val()=="" && $("#argValueDateTime_"+_pageRef).val()==""))
			{
				_showErrorMsg(argFeRequired);
				return 0;
			}
	   }

		return 1;
	}
	function checkInputValidity()
	{
		if($("#argumentType_"+_pageRef).val()=="NUMBER")
		{
			if(isNaN($("#argumentValue_"+_pageRef).val()))
				$("#argumentValue_"+_pageRef).val("");
		}
		else if($("#argumentType_"+_pageRef).val()=="DATE")
		{
			var reg = new RegExp("^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$");
			if(!reg.test($("#argumentValue_"+_pageRef).val()))
			{
				$("#argumentValue_"+_pageRef).val("");
			}
		}
	}
	function addArgument()
	{
		$("#argumentName_"+_pageRef).attr('readonly',false);	
		if(_pageRef=="RD00R")
		{
	    	reportHasChanged(true);
	    }
		if(!requiredArgFieldsFilled())
			return;
			
		parseNumbers();
		var queryName = $("#qryName_"+_pageRef).val();
		var queryIdLkp = $("#qryId_"+_pageRef).val();
		var columnName = $("#columnName_"+_pageRef).val();
		var columnDesc = $("#columnDesc_"+_pageRef).val();	
		var argQueryOptions = $("#argQueryOptions_"+_pageRef).val();	
		var qryDfltValName = $("#qryDfltValName_"+_pageRef).val();
		var qryDfltValId = $("#qryDfltValId_"+_pageRef).val();
		var qryDfltValColName = $("#qryDfltValColName_"+_pageRef).val();
		var defaultValueColDesc = $("#defaultValueColDesc_"+_pageRef).val();	
		var srcOrDflt = $("#srcOrDflt_"+_pageRef).val();
		var dfltValType = $("#argumentSrcDefault_"+_pageRef).val();
	
		//this fuction is to adjust the hm of link qry args where arg name is changed
		reloadLinkQryGridOnUpdt();
		
		if( $("#argumentSource_"+_pageRef).val() == '4' )
		{
			//set arg default value when arg source is flag
			var falgValueOn = $("#falgValueOn_"+_pageRef).val();
			var falgValueOff = $("#falgValueOff_"+_pageRef).val();
			if( document.getElementById('valueFlag_'+_pageRef).checked )
				$('#argumentValue_'+_pageRef).val(falgValueOn);
			else
				$('#argumentValue_'+_pageRef).val(falgValueOff);
		}
		
		$.ajax({
			url: jQuery.contextPath+'/path/designer/queryDesign_addUpdateArgument.action?_pageRef='+_pageRef+'&qryName='+queryName+'&queryIdLkp='+queryIdLkp+'&columnName='+columnName+'&columnDesc='+columnDesc+'&argQueryOptions='+argQueryOptions+'&srcOrDflt='+dfltValType+'&queryDfltValIdLkp='+qryDfltValId+'&queryNameDftlValLkp='+qryDfltValName+'&qryDfltValColName='+qryDfltValColName+'&defaultValueColDesc='+defaultValueColDesc+"&"+$("#argumentsForm_"+_pageRef).serialize(),
			success: function(json){
				var params = json["parametersList"]; 
				if(params)
				{
					var options = [];
			    	$.each(params, function(key, value) { 
			    	    options.push('<option value="',
				    			key, '" >',
				    			value, '</option>');
			    	});
				    $("#repParams_"+_pageRef).html(options.join(''));
				}
			    	 
			    $("#argumentsGrid_"+_pageRef).trigger("reloadGrid");
			}
		});

		emptyArguments();
	}
	
	
 function reloadLinkQryGridOnUpdt()
 {
	var queryIdLkp  = $("#qryId_"+_pageRef).val();
	var repArgsName = $("#argumentName_"+_pageRef).val();
	var oldRepArgsName = $("#oldRepArgsName_"+_pageRef).val();
	var argumentSource = $("#argumentSource_"+_pageRef).val(); 

	if(repArgsName!=oldRepArgsName && (argumentSource=="3" || argumentSource=="10"))
	{
		url = jQuery.contextPath+"/path/designer/queryDesign_loadLinkQryGrid.action?queryIdLkp="+queryIdLkp+"&repArgsName="+repArgsName+"&oldRepArgsName="+oldRepArgsName+"&_pageRef="+_pageRef;
			$.post(url,{}, function( param )//to reload the form
	 		{
	 		});
	
	}
 }
 
 /*
	Author:Fares Kassab
	Tar number:848166
	Description: this function is added in order to open query designer screen
              from CSM module
*/
function commonDeleteArgument(rowid) 
{
	if(window.name && window.name.indexOf("extScreenFrame") == 0)
	{   
		var grid = $("#argumentsGrid_"+_pageRef);
		$("#argumentIndex_"+_pageRef).val("0");
		parseNumbers();
		var lineNumber = grid.jqGrid('getCell', rowid,'index');
		var argId = grid.jqGrid('getCell', rowid,'ARGUMENT_ID');
		var deleteUrl = jQuery.contextPath+'/path/designer/queryDesign_deleteArgument.action';
		var params={};
		params["_pageRef"] = _pageRef;
		params["argumentCO.index"] = lineNumber;
		params["argumentCO.ARGUMENT_ID"] = argId;
		$.post(deleteUrl,params,function(param) 
		{
			$("#argumentsGrid_"+_pageRef).jqGrid('deleteGridRow',rowid);
			$("#argumentsGrid_"+_pageRef).trigger("reloadGrid");
			//empty form
			emptyArguments();
			$("#argumentName_"+_pageRef).attr('readonly',false);	
		});
	}
	else
	{
		deleteArgument(rowid);
	}	
}
 
	function deleteArgument(rowid) 
	{

	    if(_pageRef=="RD00R")
	    {
    	   reportHasChanged(true);
        }
		var lineNumber = $("#argumentsGrid_"+_pageRef).jqGrid('getCell', rowid,'index');
		var argName = $("#argumentsGrid_"+_pageRef).jqGrid('getCell', rowid,'ARGUMENT_NAME');
		var url = jQuery.contextPath+ "/path/designer/queryDesign_checkIfArgInSyntax.action";
		params = {};
		params["_pageRef"] = _pageRef;
		params["repArgsName"]=argName;
		// from wizard (report or query section)
	 	if(document.getElementById("sqbSyntax_"+_pageRef)!=null)
	 	{
		 	if(openSqb=="true" ) 
		 	{
				params["sqbSyntax"]=document.getElementById("sqbSyntax_"+_pageRef).value;
				$.post(url, params, function(param)
				{
					var isUsed=param['updates'];
					if(isUsed=="1")
					{
							_showErrorMsg(argUsedInSyntax,error_msg_title,300 ,100);
							return;
					}
					else
					{
						confirmDeleteArg(rowid);
					}
				});	
			}
			else
			{
				confirmDeleteArg(rowid);
			}
		}
		else
		{
			$.post(url, params, function(param)
			{
				var isUsed=param['updates'];
				if(isUsed=="1")
				{
						_showErrorMsg(argUsedInSyntax,error_msg_title,300 ,100);
						return;
				}
				else
				{
					confirmDeleteArg(rowid);
				}
			});	
		}
	}
	
	function confirmDeleteArg(rowid)
	{
	 	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteTheArgument(theArgs.rowid)
           }
	      }, {rowid : rowid});	
	}

	function deleteTheArgument(rowid)
	{
		$("#argumentIndex_"+_pageRef).val("0");
		parseNumbers();
		var lineNumber = $("#argumentsGrid_"+_pageRef).jqGrid('getCell', rowid,'index');
		var argId = $("#argumentsGrid_"+_pageRef).jqGrid('getCell', rowid,'ARGUMENT_ID');

		//check if the argument is used in conditon or having then do not delete it
		var url = jQuery.contextPath+ "/path/designer/queryDesign_argumentUsage";
		url+="?updates=" + lineNumber;
		url+="&_pageRef=" + _pageRef;
		var params={};
	    $.getJSON(url, params, function( data2, status, xhr )
	 	{
	    	//check if we selected a fields 
		 	var isUsed=data2['updates'];
		 	if(isUsed!="")
		 	{	
		 		_showErrorMsg(canNotDeleteAlert+" "+isUsed)
			 	return;
		 	}
		 	else
		 	{
				var url1 = jQuery.contextPath+'/path/designer/queryDesign_deleteArgument.action?_pageRef='+_pageRef+'&argumentCO.index=' + lineNumber+'&argumentCO.ARGUMENT_ID=' + argId;
				var param={};
				$.get(url1,param,function(param) 
				{
					$("#argumentsGrid_"+_pageRef).trigger("reloadGrid");
					//empty form
					emptyArguments();
					$("#argumentName_"+_pageRef).attr('readonly',false);	
				}
				);
		 	}
		 	
	 	});	
	}

	function setArgName(value)
	{
		$('#argumentName_'+_pageRef).val(value.replace(' ','_'));
	}

	function emptyArgValue()
	{
		$('#argumentValue_'+_pageRef).val('');
		$('#argValueDate_'+_pageRef).val('');
		$('#argValueDateTime_'+_pageRef).val('');
		$('#argumentDateValue_'+_pageRef).val('');
		if (document.getElementById('argumentType_'+_pageRef).value=='NUMBER' && $("#cifAuditYn_"+_pageRef+":checked").val())
		{
			$("#cifAuditFlag_"+_pageRef).attr("style","visibility: inline;");
			$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: inline;");
		}
		else
		{
			$("#cifAuditFlag_"+_pageRef).attr("style","visibility: hidden;")
			$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: hidden;")
		}
		if (document.getElementById('argumentType_'+_pageRef).value=='DATE')
	{
			//hide the textfield and show the psj:datepicker
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).disabled=true;
			document.getElementById('argValueDate_'+_pageRef).style.display = 'inline';
			document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'inline';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'inline';
			document.getElementById('normalDatePick_'+_pageRef).style.display = 'inline';
			document.getElementById('argValueDate_'+_pageRef).disabled = false;
			document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
			document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
			document.getElementById('argValueDateTime_'+_pageRef).disabled = true;
			$("#argValueDate_"+_pageRef).attr('readonly', false);
		
}
else if (document.getElementById('argumentType_'+_pageRef).value=='DATETIME')
{
	//hide the textfield and show the psj:datepicker
			document.getElementById('argumentValue_'+_pageRef).style.display = 'none';
			document.getElementById('argValTd_'+_pageRef).style.display = 'none';
			document.getElementById('argumentValue_'+_pageRef).disabled=true;
			document.getElementById('argValueDate_'+_pageRef).style.display = 'none';
			document.getElementById('normalDatePick_'+_pageRef).style.display = 'none';
			document.getElementById('argValueDate_'+_pageRef).disabled = true;
			document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'inline';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'inline';
			document.getElementById('argValueDateTime_'+_pageRef).style.display = 'inline';
			document.getElementById('detDatePick_'+_pageRef).style.display = 'inline';
			document.getElementById('argValueDateTime_'+_pageRef).disabled = false;
			
			$("#argValueDateTime_"+_pageRef).attr('readonly', false);
}

		else
		{
			document.getElementById('argumentValue_'+_pageRef).style.display='inline';
			document.getElementById('argValTd_'+_pageRef).style.display = 'inline';
			document.getElementById('argumentValue_'+_pageRef).disabled=false;
			document.getElementById('argValueDate_'+_pageRef).style.display = 'none';
			document.getElementById('normalDatePick_'+_pageRef).style.display = 'none';
			document.getElementById('argValueDate_'+_pageRef).disabled = true;
			document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
			document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
			document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'none';
	
			document.getElementById('argValueDateTime_'+_pageRef).disabled = true;
		}
	}

	function resetForm()
	{
		document.getElementById('argumentType_'+_pageRef).disabled =false;
		document.getElementById('sessionAttrCmb_'+_pageRef).style.display = 'none';
		document.getElementById('valueFlag_'+_pageRef).style.display = 'none';
		document.getElementById('valFlagChk_'+_pageRef).style.display = 'none';
		document.getElementById('valueOnLabel_'+_pageRef).style.display = 'none';
		document.getElementById('valueOffLabel_'+_pageRef).style.display = 'none';
		document.getElementById('falgValueOn_'+_pageRef).style.display = 'none';
		document.getElementById('falgValueOff_'+_pageRef).style.display = 'none';
		document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'none';
		document.getElementById('argumentDateValue_'+_pageRef).style.display = 'none';

		document.getElementById('argumentType_'+_pageRef).selectedIndex = 0;
		document.getElementById('argumentValue_'+_pageRef).value = "";
		document.getElementById('argumentDateValue_'+_pageRef).value = "";
		$("#argumentValue_"+_pageRef).attr('readonly', false);
		document.getElementById('sessionAttrCmb_'+_pageRef).selectedIndex = 0;
		document.getElementById('valueFlag_'+_pageRef).checked = false;
		document.getElementById('falgValueOn_'+_pageRef).value = "";
		document.getElementById('falgValueOff_'+_pageRef).value = "";
		document.getElementById('argumentSrcDefault_'+_pageRef).value = "1";
		document.getElementById('linkQueryDV_'+_pageRef).style.display = 'none';
		document.getElementById('toSaveFlag_'+_pageRef).checked = false;
		document.getElementById('cifAuditFlag_'+_pageRef).checked = false;
	}  

	// this function is to hide the input of the argruments screen open form queries
	function showHideUnusedInput()
	{
	  //handling the date picker
	  document.getElementById('argValueDate_'+_pageRef).style.display = 'none';
	  document.getElementById('normalDatePick_'+_pageRef).style.display = 'none';
	  document.getElementById('argValueDate_'+_pageRef).disabled = true;	
  	  document.getElementById('argValueDateTime_'+_pageRef).style.display = 'none';
  	  document.getElementById('detDatePick_'+_pageRef).style.display = 'none';
	  document.getElementById('argValueDateTime_'+_pageRef).disabled = true;	
	  
	   if(_pageRef=="RD00Q" || (_pageRef=="RD00R" && showRepArgQryLink==false))
	   {
	   		document.getElementById('argumentLabel_'+_pageRef).style.display = 'none';
	   		document.getElementById('argumentTxt_'+_pageRef).style.display = 'none';
	   		document.getElementById('argumentSourceTxt_'+_pageRef).style.display = 'none';
	   		document.getElementById('argumentSource_'+_pageRef).style.display = 'none';
	   		document.getElementById('argDateValueLabel_'+_pageRef).style.display = 'none';
			document.getElementById('argumentDateValue_'+_pageRef).style.display = 'none';
			document.getElementById('valueFlag_'+_pageRef).style.display = 'none';
	   		document.getElementById('valFlagChk_'+_pageRef).style.display = 'none';
	   		document.getElementById('argOrderTxt_'+_pageRef).style.display = 'none';
	   		document.getElementById('argOrder_'+_pageRef).style.display = 'none';
	   		document.getElementById('displayFlagTxt_'+_pageRef).style.display = 'none';
	   		document.getElementById('displayFlag_'+_pageRef).style.display = 'none';
	   		document.getElementById('enableFlagTxt_'+_pageRef).style.display = 'none';
	   		document.getElementById('enableFlag_'+_pageRef).style.display = 'none';
	   		document.getElementById('argumentSrcDefault_'+_pageRef).style.display = 'none';
	   		document.getElementById('linkQueryDV_'+_pageRef).style.display = 'none';
	   		document.getElementById('argumentValTypeLabel_'+_pageRef).style.display = 'none';
	   		//document.getElementById("multiselectFlag_"+_pageRef).disabled = true;
	   		document.getElementById("multiselectFlag_"+_pageRef).style.display='none';
			document.getElementById("multiselectFlagTxt_"+_pageRef).style.display = 'none';
			document.getElementById("multiSelLblTd_"+_pageRef).style.display = 'none';
			document.getElementById("multiSelChkTd_"+_pageRef).style.display = 'none';
	   		
	   }	   
	}

	function setColCmbDefaultIndex()
	{
		//$('#columnsCmb_'+_pageRef).prop("selectedIndex",0);
	}

	function openLinkQueryList(forSrcOrDfltVal)
	{
	
		var repArgsName = $("#argumentName_"+_pageRef).val();
		var repeatArg = checkArgsName();
		if(repArgsName=="")
		{
			_showErrorMsg(argNameEmpty,argumentNameError,300,100); 
		}
		else if(repeatArg==false)
		{
		
			$("#repArgsName_"+_pageRef).val(repArgsName);
			var queryIdLkp = $("#qryId_"+_pageRef).val();
			var queryName = $("#qryName_"+_pageRef).val();
			var columnName = $("#columnName_"+_pageRef).val();
			var columnDesc = $("#columnDesc_"+_pageRef).val();	
			var argQueryOptions = $("#argQueryOptions_"+_pageRef).val();	
			var oldRepArgsName = $("#oldRepArgsName_"+_pageRef).val();
			var queryDfltValIdLkp = $("#qryDfltValId_"+_pageRef).val();
			var queryNameDftlValLkp = $("#qryDfltValName_"+_pageRef).val();
			var qryDfltValColName = $("#qryDfltValColName_"+_pageRef).val();
			var defaultValueColDesc = $("#defaultValueColDesc_"+_pageRef).val();	
	
	
			
			dialogUrl= jQuery.contextPath+ "/path/designer/queryDesign_openLinkQueryMainDialog.action?_pageRef="+_pageRef+"&repArgsName="+repArgsName+"&queryIdLkp="+queryIdLkp+"&qryName="+queryName+"&columnName="+columnName+"&columnDesc="+columnDesc+"&argQueryOptions="+argQueryOptions+"&oldRepArgsName="+oldRepArgsName+"&forSrcOrDfltVal="+forSrcOrDfltVal+"&queryDfltValIdLkp="+queryDfltValIdLkp+"&queryNameDftlValLkp="+queryNameDftlValLkp+"&qryDfltValColName="+qryDfltValColName+'&defaultValueColDesc='+defaultValueColDesc;
			dialogOptions={ autoOpen: false,
							height:300,
							title:repLinkQry,
							width:1000 ,
							modal: true,
							close: function( event, ui ) {closeLinkQryArgs(1)},
							buttons: [{ text : paramsOk,click : saveLinkQryArgs},
							          { text : paramsCancel, click : closeLinkQryArgs}
					          ]
			   }
			var params={};
	 		$.post(dialogUrl ,params , function( param )
		 	{
 			  $('#linkQueryMainDialog_'+_pageRef).html(param) ;
 			  $('#linkQueryMainDialog_'+_pageRef).dialog(dialogOptions)
 			  $('#linkQueryMainDialog_'+_pageRef).dialog('open');
			});  
		}		
		   	
	}
	
	function closeLinkQryArgs(fromWhere)
	{
		var repArgName = $("#repArgsName_"+_pageRef).val();
		var oldRepArgsName = $("#oldRepArgsName_"+_pageRef).val();
		
		
		var newArgName = $("#repArgsName_"+_pageRef).val();
		$("#oldRepArgsName_"+_pageRef).val(newArgName);
		
		if(oldRepArgsName !="" && repArgName!=oldRepArgsName)
		{
			//saveLinkQryArgs();
		}
		if(fromWhere!=1)
		{
		 $('#linkQueryMainDialog_'+_pageRef).dialog('close');	
		}
		
	}
	
	function saveLinkQryArgs()
	{
		var jsonStringUpdates = $("#linkQryGridId_"+_pageRef).jqGrid('getAllRows');
		$("#updatesLinkQryArgs_"+_pageRef).val(jsonStringUpdates); 
		
		
		 var forSrcOrDfltVal = document.getElementById('forSrcOrDfltVal_'+_pageRef).value    //$("#forSrcOrDfltVal_"+_pageRef).val();
		
		if(forSrcOrDfltVal =="1")
		{
			var queryIdLkp  = $("#lookuptxt_queryLkp_"+_pageRef).val();
			$("#qryId_"+_pageRef).val(queryIdLkp);  
			
			var qryName  = $("#queryLkpName_"+_pageRef).val();
			$("#qryName_"+_pageRef).val(qryName);
			
		    var columnName  = $("#columnsCmb_"+_pageRef).val();
			$("#columnName_"+_pageRef).val(columnName);
			
			var columnDesc = $("#columnsDescCmb_"+_pageRef).val();
			$("#columnDesc_"+_pageRef).val(columnDesc);
			
			var argQueryOptions =$("#queryOptionsCmb_"+_pageRef).val();
			$("#argQueryOptions_"+_pageRef).val(argQueryOptions);
			
		}
		else
		{
			var queryIdLkp  = $("#lookuptxt_queryLkp_"+_pageRef).val();
			$("#qryDfltValId_"+_pageRef).val(queryIdLkp);  
			
			var qryName  = $("#queryLkpName_"+_pageRef).val();
			$("#qryDfltValName_"+_pageRef).val(qryName);
			
		    var columnName  = $("#columnsCmb_"+_pageRef).val();
			$("#qryDfltValColName_"+_pageRef).val(columnName);
			
			var columnDesc = $("#columnsDescCmb_"+_pageRef).val();	
			$("#defaultValueColDesc_"+_pageRef).val(columnDesc);
			
	
		}

		$("#srcOrDflt_"+_pageRef).val(forSrcOrDfltVal);
		var newArgName = $("#repArgsName_"+_pageRef).val();
		$("#oldRepArgsName_"+_pageRef).val(newArgName);		
		var url = $("#linkQryArgsForm_"+_pageRef).attr("action");
		url +="?_pageRef="+_pageRef;
		myObject =  $("#linkQryArgsForm_"+_pageRef).serialize();
		if(columnName!="" && queryIdLkp!="")
		{
			$.post(url, myObject , function( param )
		 	 {
				 $('#linkQueryMainDialog_'+_pageRef).dialog('close');
		 	 });	
		}
		else{
			_showErrorMsg(argFeRequired,argumentNameError,300,100); 
		}
	}	
	
	
	function checkArgsName()
	{
	
		var repArgsNameArr = $("#argumentsGrid_"+_pageRef).jqGrid('getCol','ARGUMENT_NAME');
		var repeatedExpr=false;
		newArgName = $("#argumentName_"+_pageRef).val();
		var argumentsMode = $("#argumentsMode_"+_pageRef).val();
		var oldArgName = $("#oldRepArgsName_"+_pageRef).val()
		var argNameRepeatNbr =0;
		if(argumentsMode =="add")
		{
			for(var i=0;i<repArgsNameArr.length;i++)
			{
				if(repArgsNameArr[i].toUpperCase()==newArgName.toUpperCase())
				{
					repeatedExpr=true;
					break;
				}
			}
		}
		else
		{
			for(var i=0;i<repArgsNameArr.length;i++)
			{
				if(repArgsNameArr[i].toUpperCase()==newArgName.toUpperCase())
				{
					if(newArgName.toUpperCase()!=oldArgName.toUpperCase()) 
					{
						repeatedExpr=true;
					}
				}
			}
		}
		if(repeatedExpr==true)
		{
			$("#argumentName_"+_pageRef).val('');
			_showErrorMsg(argNameRepeated,argumentNameError,300,100); 
		}
		
		return repeatedExpr;
	}	
function rep_args_documentReadyFunc()
{
	showHideUnusedInput();
	dispLnkQryButt(0);
	//hiding the constraints button from the queries screen
	if(_pageRef==queriesProgRef)
	{
		document.getElementById("constrContainer_"+_pageRef).style.display = "none";
	}
	if(!$("#cifAuditYn_"+_pageRef+":checked").val())
	{
		$("#cifAuditFlag_"+_pageRef).attr("style","visibility: hidden;")
		$("#cifAuditFlagTxt_"+_pageRef).attr("style","visibility: hidden;")
	}
}

function rep_args_cancelEvents()
{
		  //prevent the user from entering special characters
	  $("#argumentName_"+_pageRef).bind('keypress', function (event) {
	    var regex = new RegExp("^[a-zA-Z0-9_-]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    //  confirm (event.which)
	    if(event.which==8 || event.which==0 )// for delete(8) and tab(0)
	    {
	    	return true;
	    }
	    $("#argumentName_"+_pageRef).bind("cut copy paste",function(e) {
	    
	          e.preventDefault();
	      });
	    if (!regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});
}

function reloadLinkQryGrid()
{
	var queryIdLkp  = $("#lookuptxt_queryLkp_"+_pageRef).val();
	var repArgsName = $("#repArgsName_"+_pageRef).val();
	var oldRepArgsName = $("#oldRepArgsName_"+_pageRef).val();
	var qryName = $("#queryLkpName_"+_pageRef).val();
	
	var forSrcOrDfltVal = $("#forSrcOrDfltVal_"+_pageRef).val();
	var queryDfltValIdLkp = $("#lookuptxt_queryLkp_"+_pageRef).val();
	
	
	url = jQuery.contextPath+"/path/designer/queryDesign_loadLinkQryGrid.action?queryIdLkp="+queryIdLkp+"&repArgsName="+repArgsName+"&oldRepArgsName="+oldRepArgsName+"&_pageRef="+_pageRef+"&qryName="+qryName+"&forSrcOrDfltVal="+forSrcOrDfltVal+"&queryDfltValIdLkp="+queryDfltValIdLkp;
	$("#linkQryGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
}
 
 function enableAll() 
{
	$("#columnsCmb_"+_pageRef).removeAttr("disabled");	
}

function disableDesc()
{
    enableAll();
	var queryOptions = $("#queryOptionsCmb_"+_pageRef).val();
	if (queryOptions == "1") 
	{
		$("#columnsDescCmb_"+_pageRef).attr('disabled','disabled');	
		$("#columnsDescCmb_"+_pageRef).val(""); 
	} 
	else 
	{
		$("#columnsDescCmb_"+_pageRef).removeAttr("disabled");	
		$("#columnsDescCmb_"+_pageRef).val("");
	}
}
 
 
function rep_lnkQuery_readyFunc()
{
	if($("#noDefinedSyntax_"+_pageRef).val()==1)
	{
		_showErrorMsg(queryIdkey+" "+$("#queryIdLkp_"+_pageRef).val()+" "+hasnodefinedsyntaxkey);
		$("#lookuptxt_queryLkp_"+_pageRef).val("");
		return;
	}
	var arqQueryOptions =  $("#argQueryOptions_"+_pageRef).val();
 	var forSrcOrDfltVal =$("#forSrcOrDfltVal_"+_pageRef).val();
	if(forSrcOrDfltVal=="1")
	{
 		var queryId  = $("#queryIdLkp_"+_pageRef).val();
		$("#lookuptxt_queryLkp_"+_pageRef).val(queryId);
		var qryName  = $("#qryName_"+_pageRef).val();
		$("#queryLkpName_"+_pageRef).val(qryName);
		var columnName  = $("#columnName_"+_pageRef).val();
		$("#columnsCmb_"+_pageRef).val(columnName);
		var columnDesc  = $("#columnDesc_"+_pageRef).val();
		$("#columnsDescCmb_"+_pageRef).val(columnDesc);
		$("#queryOptionsCmb_"+_pageRef).val(parseInt(arqQueryOptions));
		if($("#multiselectFlag_"+_pageRef+":checked").val())
		{
			$("#columnsDescCmb_"+_pageRef).attr('disabled','disabled');
			$("#columnsDescCmb_"+_pageRef).val("");
			$("#queryOptionsCmb_"+_pageRef).attr('disabled','disabled');
			$("#queryOptionsCmb_"+_pageRef).val(1);
		}	
		else if(arqQueryOptions==1)
		{
			$("#columnsDescCmb_"+_pageRef).attr('disabled','disabled');	
			$("#columnsDescCmb_"+_pageRef).val("");	
		}
	}
	else
	{
		if(arqQueryOptions=="")
		{
			$("#queryOptionsCmb_"+_pageRef).val(1);	
			$("#columnsDescCmb_"+_pageRef).attr('disabled','disabled');	
			$("#columnsDescCmb_"+_pageRef).val("");	
		}
		else
		{
			if($("#multiselectFlag_"+_pageRef+":checked").val())
			{
				$("#columnsDescCmb_"+_pageRef).attr('disabled','disabled');
				$("#columnsDescCmb_"+_pageRef).val("");
				$("#queryOptionsCmb_"+_pageRef).attr('disabled','disabled');
				$("#queryOptionsCmb_"+_pageRef).val(1);
			}	
			else
			{
				$("#queryOptionsCmb_"+_pageRef).val(parseInt(arqQueryOptions));	
			}
		}
		$("#queryOptionsCmb_"+_pageRef).attr('disabled','disabled');
		var queryId  = $("#queryIdLkp_"+_pageRef).val();
		$("#lookuptxt_queryLkp_"+_pageRef).val(queryId);
		var qryName  = $("#queryNameDftlValLkp_"+_pageRef).val();
		$("#queryLkpName_"+_pageRef).val(qryName);
		var columnName  = $("#qryDfltValColName_"+_pageRef).val();
		$("#columnsCmb_"+_pageRef).val(columnName);
		var columnDesc  = $("#defaultValueColDesc_"+_pageRef).val();
		$("#columnsDescCmb_"+_pageRef).val(columnDesc);
		if(arqQueryOptions==1)
		{
			$("#columnsDescCmb_"+_pageRef).attr('disabled','disabled');	
			$("#columnsDescCmb_"+_pageRef).val("");	
		}
	}
}
 
 
$("#linkQryGridId_"+_pageRef).subscribe('enableDisableParamsCells', function(event,data) 
{
	rowId = (event["originalEvent"])["id"];
	myObject = $("#linkQryGridId_"+_pageRef).jqGrid('getRowData',rowId);
	var argType=myObject["MAP_PARAM_TYPE"];
	applyEnableDisableParamsCells(argType,rowId)
});
		

function checkHypParamCell(obj)
{
	var rowId = $("#linkQryGridId_"+_pageRef).jqGrid('getGridParam','selrow');
	applyEnableDisableParamsCells(obj.value,rowId)
}
 
function applyEnableDisableParamsCells(argType,rowId)
{
   	var $tbl = $("#linkQryGridId_"+_pageRef);
  	var VALUE_ARGUMENT = $tbl.jqGrid('getCellInputElt',rowId,'irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME'); 		
   	if(argType==0) //static
   	{
   		$("#linkQryGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME','','true');
  		var STATIC_VALUE = $tbl.jqGrid('getCell',rowId,'irpQueryArgsMappingVO.STATIC_VALUE');
   		$("#linkQryGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'irpQueryArgsMappingVO.STATIC_VALUE',STATIC_VALUE ,'false');   		
   	}
   	else if(argType==1) //field
   	{
   		$("#linkQryGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'irpQueryArgsMappingVO.STATIC_VALUE','','true');
   		$("#linkQryGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME','','true');
   	}
   	else if(argType==2)//session
   	{
   		$("#linkQryGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'irpQueryArgsMappingVO.STATIC_VALUE','','true');
   		var VALUE_ARGUMENT = $tbl.jqGrid('getCell',rowId,'irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME');
   		$("#linkQryGridId_"+_pageRef).jqGrid('setCellValue', rowId, 'irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME',VALUE_ARGUMENT,'false');
   		$(document.getElementById(rowId + "_irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME_lookuptxt_linkQryGridId_"+_pageRef)).attr('readonly','readonly');
   	}   	
}	