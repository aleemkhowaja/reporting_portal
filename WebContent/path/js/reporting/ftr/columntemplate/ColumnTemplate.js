//Variable declaration to be used below in the page
var	valueToRemember =0;
var globTmpCodeWithLnNb="0~0";
var globalLineNbr=0;

//below added for the subline numbers	 
var userClickedColCrtOk = 1;
var shouldIncrementColCrt = 1;
var returnedNewLineNumberColCriteria = 0;
var selectedColLineGrid = 0;	


function rep_colTempl_ready()
{
		$("#CODE").val("");
		$("input:radio[name='singleColumn.PRINT_PAPER_SIZE']")[0].checked = true;
		$("input:radio[name='singleColumn.PRINT_PAPER_ORIENTATION']")[0].checked = true;
		emptyDetForm(1);
				
			$("#detGridMode").val("add");
			$("#linesGridMode").val("");
			$("#CODE").attr("readonly",false);
			
			$("#brnchDesc_"+_pageRef).attr("readonly",true);
			$("#brnchToDesc_"+_pageRef).attr("readonly",true);
			$("#region1Str_"+_pageRef).attr("readonly",true);
			$("#regionToStr_"+_pageRef).attr("readonly",true);
			$("#divCodeFromDesc_"+_pageRef).attr("readonly",true);
			$("#divCodeToDesc_"+_pageRef).attr("readonly",true);
			$("#depCodeFromDesc_"+_pageRef).attr("readonly",true);
			$("#depCodeToDesc_"+_pageRef).attr("readonly",true);
			$("#unitFromDesc_"+_pageRef).attr("readonly",true);
			$("#unitToDesc_"+_pageRef).attr("readonly",true);
			$("#compDesc_"+_pageRef).attr("readonly",true);						
			$("#frmEditColTemplateDetails_"+_pageRef).processAfterValid("addTemplateDetColsAfterOk");
			document.getElementById("additionalButtons_"+_pageRef).style.display = 'none';
			$("#gview_colGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
    		$("#colGrid_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
			
			
			$("#detColGrid_"+_pageRef).subscribe('colLineGrdComplete', 
				function(event,data) 
				{
					var pagerId = "detColGrid_"+_pageRef+"_pager_left";
				 	var myGrid = $("#detColGrid_"+_pageRef);
						myGrid.jqGrid('navButtonAdd',pagerId,{caption:"",title: reorganizeTitle, id:"NewButton_"+_pageRef,
					buttonicon :'.ui-icon-circle-arrow-s', onClickButton:rep_col_openReorganizeDialog });
					}
			);
}

function intializeControlColVars()
{

	 $("#crt_subLnNbr").val("");
	 //first double click on the lines grid
	 rowid 		=$("#detColGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	 myObject 	= $("#detColGrid_"+_pageRef).jqGrid('getRowData',rowid);
	 if(selectedColLineGrid==0)
	 {
	 	selectedColLineGrid=myObject["newLineNumber"];
	 }
	 else
	 {
		//if the user double clicked on another line in lines grid,all the controls should be reinitialized
		if(selectedColLineGrid!=myObject["newLineNumber"])
		{
			 userClickedColCrtOk = 1;
			 shouldIncrementColCrt = 1;
			 returnedNewLineNumberColCriteria = 0;
			 selectedColLineGrid = myObject["newLineNumber"];
		}
	 }
}

		
		function checkSelectedColTmplTab(selectedTabId, event)
		{
			    var url = $.data(event.originalEvent.ui.tab, 'load.tabs');
                var cached = $.data(event.originalEvent.ui.tab, 'cache.tabs');
			 	if(typeof cached == "undefined" && typeof url != "undefined")
			 	{
			      				
    			 var index;
        		 if(selectedTabId=="custTab1")
        			 	{
        			 		newUrl = jQuery.contextPath+ "/path/colTemplateMaintReport/openCriteria.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
        			 		index=0;
        			 	}
        			 	else
        			 	{
	       			 		newUrl = jQuery.contextPath+ "/path/templateExpression/openExpression.action?lineNbr="+globalLineNbr+"&_pageRef="+_pageRef+"&tempCodeWithLineNb="+globTmpCodeWithLnNb;
	       			 		index=1;
        			 	}
	       				$("#tabs_"+_pageRef).tabs("url",index,newUrl);
	       				}
}
	//Fill the Form "ColTemplateMaint.jsp" when double click on a line in the Grid "colGrid_${_pageRef}"
	function MainGridClicked() 
	{
		emptyDetForm(1);
		
		rowid = $("#colGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject = $("#colGrid_"+_pageRef).jqGrid('getRowData', rowid);
		code = myObject["colmnTmpltVO.CODE"];
		params = {};
		
		var url = jQuery.contextPath+'/path/colTemplateMaintReport/selectTemplate.action?code='+code+'&_pageRef='+_pageRef;
		
		$.post(	url,params,function(param) 
		{
			//fill the html
			$("#inEditColTemplateDiv_"+_pageRef).html(param);
			
			$("#detGridMode").val("edit");
			$("#detColGrid_"+_pageRef).jqGrid('setGridParam',
			{
					url : jQuery.contextPath+"/path/colTemplateMaintReport/fillDetailsGrid.action?detGridMode=fill&_pageRef="+_pageRef,
					page : 1
			}).trigger("reloadGrid");
			
			$("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
			{   
				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=0&_pageRef="+_pageRef,
				page : 1
			 }).trigger("reloadGrid");
			
			$("#crtGrids_"+_pageRef).jqGrid('setGridParam',
   			  	{    
   				url : jQuery.contextPath+"/path/colTemplateMaintReport/crtGridUrl.action?tempCodeWithLineNb=0~0&_pageRef="+_pageRef,
   				page : 1
   				}).trigger("reloadGrid");
			
			$("#CODE_"+_pageRef).attr("readonly",true);
		},"html");
		
		document.getElementById("additionalButtons_"+_pageRef).style.display = '';
	}

	//Empty the Form "frmEditColTemplateDetails_${_pageRef}", called from the fucntion "emptyDetForm"
	function emptyDetForm(fromWhere) 
	{
		if(fromWhere==1)// fromWhere ==2 to empty the form on ddl change 
		{
			$("#LINE_NBR_"+_pageRef).val("");
			$("#COL_TYPE_"+_pageRef).val(0);
			$("#detailColGridMode").val("add");
		}
		
		
		$("#COL_TYPE_STR").val($("#COL_TYPE_"+_pageRef+" option:selected").text());
		$("#BRIEF_NAME_ENG_"+_pageRef).val("");
		$("#BRIEF_NAME_ARAB_"+_pageRef).val("");
		$("#FROM_DATE_"+_pageRef).val("");
		$("#TO_DATE_"+_pageRef).val("");
		$("#lookuptxt_compID_"+_pageRef).val("");
		$("#chkAllCriteria").attr('checked', false);
		$("#lookuptxt_brnchCode_"+_pageRef).val("");
		$("#lookuptxt_brnchCodeTo_"+_pageRef).val("");
		$("#lookuptxt_FROM_REGION_"+_pageRef).val("");
		$("#lookuptxt_TO_REGION_"+_pageRef).val("");
		$("#lookuptxt_divCodeFrom_"+_pageRef).val("");
		$("#lookuptxt_divCodeTo_"+_pageRef).val("");
		$("#lookuptxt_depCodeFrom_"+_pageRef).val("");
		$("#regionFromStr_"+_pageRef).val("");
		$("#lookuptxt_TO_DEPT_"+_pageRef).val("");
		$("#lookuptxt_FROM_UNIT_"+_pageRef).val("");
		$("#lookuptxt_TO_UNIT_"+_pageRef).val("");
		$("#DISP_COL_TOT_ZERO").attr('checked', false);
		$("#BOLD").attr('checked', false);
				
		$("#compDesc_"+_pageRef).val("");
		$("#brnchDesc_"+_pageRef).val("");
		$("#brnchToDesc_"+_pageRef).val("");
		$("#region1Str_"+_pageRef).val("");
		$("#regionToStr_"+_pageRef).val("");
		$("#divCodeFromDesc_"+_pageRef).val("");
		$("#divCodeToDesc_"+_pageRef).val("");
		$("#depCodeFromDesc_"+_pageRef).val("");
		$("#depCodeToDesc_"+_pageRef).val("");
		$("#unitFromDesc_"+_pageRef).val("");
		$("#unitToDesc_"+_pageRef).val("");
		$("#lookuptxt_csvLookUp_"+_pageRef).val("");
		$("#currencyStr_"+_pageRef).val("");
		liveSearch_makeReadOnly(false,"compID_"+_pageRef);
		liveSearch_makeReadOnly(true,"brnchCode_"+_pageRef);
		liveSearch_makeReadOnly(true,"brnchCodeTo_"+_pageRef);
		liveSearch_makeReadOnly(true,"FROM_REGION_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_REGION_"+_pageRef);
		liveSearch_makeReadOnly(true,"divCodeFrom_"+_pageRef);
		liveSearch_makeReadOnly(true,"divCodeTo_"+_pageRef);
		liveSearch_makeReadOnly(true,"depCodeFrom_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_DEPT_"+_pageRef);
		liveSearch_makeReadOnly(true,"FROM_UNIT_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_UNIT_"+_pageRef);
		disableDatepicker("FROM_DATE_"+_pageRef,false);
		
		//disable - enable date
		var colTpe = $("#COL_TYPE_"+_pageRef).val();
		if(colTpe=="AM" || colTpe=="BM" || colTpe=="VB" || colTpe=="AMF"|| colTpe=="VBF" || colTpe=="AGC" || colTpe=="AGF")
		{
			enableToDate();
		}
		else
		{
			disableToDate();
		}
	}
	
	//Enable the "TO_DATE in the Form "frmEditColTemplateDetails_${_pageRef}"
	function enableToDate()
	{
		//$("#TO_DATE").attr("disabled",false);
		disableDatepicker("TO_DATE_"+_pageRef,false);
	}
	
	//Disable the "TO_DATE in the Form "frmEditColTemplateDetails_${_pageRef}"
	function disableToDate()
	{
		//$("#TO_DATE").attr("disabled",true);
		disableDatepicker("TO_DATE_"+_pageRef,true);
	}
	
	//Empty the Form "ColTemplateMaint.jsp" when adding new record in the "colGrid_${_pageRef}" Grid
	function emptyMainForm() {
		parseNumbers();
		$("#CODE_"+_pageRef).val("");
		$("#briefNameEng_"+_pageRef).val("");
		$("#briefNameArab_"+_pageRef).val("");
		$("input:radio[name='singleColumn.PRINT_PAPER_SIZE']")[0].checked = true;
		$("input:radio[name='singleColumn.PRINT_PAPER_ORIENTATION']")[0].checked = true;
		emptyCrtFormColTemp();
		$("#crt_tempLnNbr").val("");
		$("#detGridMode").val("add");
		
		$("#detColGrid_"+_pageRef).jqGrid('setGridParam',
				{
					url : jQuery.contextPath+"/path/colTemplateMaintReport/fillDetailsGrid.action?detGridMode=none&_pageRef="+_pageRef,
					page : 1
				}).trigger("reloadGrid");
		
		$("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
   			  	{    
   				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=0&_pageRef="+_pageRef,
   				page : 1
   				}).trigger("reloadGrid");
   				
		$("#crtGrids_"+_pageRef).jqGrid('setGridParam',
   			  	{    
   				url : jQuery.contextPath+"/path/colTemplateMaintReport/crtGridUrl.action?tempCodeWithLineNb=0~0&_pageRef="+_pageRef,
   				page : 1
   				}).trigger("reloadGrid");
		
		$("#CODE_"+_pageRef).attr("readonly",false);
		emptyDetForm(1);
		document.getElementById("additionalButtons_"+_pageRef).style.display = 'none';
		return;
	}
	
	//Delete selected row in the "colGrid_${_pageRef}" Grid
	function onMainDelClicked(rowid)
	{
		 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
         if(confirmcChoice)
         {
        	   deleteColTempl(theArgs.rowid)
         }
		}, {rowid : rowid});	
	}
	
	//Delete a record in the "colGrid_${_pageRef}" Grid
	function deleteColTempl(rowid)
	{
		var templateCode = $("#colGrid_"+_pageRef).jqGrid('getCell', rowid,'colmnTmpltVO.CODE');
		var url = jQuery.contextPath+'/path/colTemplateMaintReport/deleteColumnTemplate.action?TEMPLATE_CODE='+templateCode+'&_pageRef='+_pageRef;
		var param={};
		param["_pageRef"]=_pageRef;
		$.get(url,param,function(param) {$("#colGrid_"+_pageRef).trigger("reloadGrid");});
		
		emptyMainForm();
		emptyDetForm(1);
	}
	
	//function called when clicking on OK button or when adding a new line
	//or when changing the value of the calculation type and checks for the validity
	//of the entered data related to calculation types : GM, MB, MBF, GMF
	function checkDayMonthBalance(param)
	{
		var msg;
		var gridLineNbr;
		var frmLineNbr;
		var colType;
		var anyRowActBal=0;
		//param==4=> click on the OK button
		//param==2=> drpdwn being changed
		//param==3=> from the + button in the grid
		if(param==1)
		{
			msg = curAlreadyDefined;	
		}
		var mode = $("#linesGridMode").val();
		var allRowIds =  $("#detColGrid_"+_pageRef).jqGrid('getDataIDs');
		//if the grid contains rows
		if(allRowIds)
		{		
			for (var i =0;i<allRowIds.length;i++)
		    {
		    	llRowId=allRowIds[i];
		    	myObject =$("#detColGrid_"+_pageRef).jqGrid('getRowData',llRowId);
		    	//added condition for correct initialization of anyRowActBal
		    	if(myObject["colmnTmpltVO.COL_TYPE"] == "GM" || myObject["colmnTmpltVO.COL_TYPE"] == "MB"
		    		|| myObject["colmnTmpltVO.COL_TYPE"] == "MBF" || myObject["colmnTmpltVO.COL_TYPE"] == "GMF")
		    	{
		    		anyRowActBal=1;	
		    		//if clicking on the OK button stopper msg because the second row can't be other than EN or AR
		    		if(param==4 && allRowIds.length==2 && mode=="add")
		    		{
		    			msg = curAlreadyDefined;	
		    			_showErrorMsg(msg,error_msg_title,400,100);
		    		 	return true;
		    		}
		    	}
		    	//case where grid contains a record among the 4 only and user is inserting another line and didn't choose En or Ar
		    	if((myObject["colmnTmpltVO.COL_TYPE"] == "GM" || myObject["colmnTmpltVO.COL_TYPE"] == "MB"
		    		|| myObject["colmnTmpltVO.COL_TYPE"] == "MBF" || myObject["colmnTmpltVO.COL_TYPE"] == "GMF") 
		    			&& allRowIds.length==1 && param==2)
		    	{
		    		colType=myObject["colmnTmpltVO.COL_TYPE"];
		    		gridLineNbr	= myObject["colmnTmpltVO.LINE_NBR"];
		    		frmLineNbr 	= $("#LINE_NBR_"+_pageRef).val();
		    		if(gridLineNbr!=frmLineNbr && ($("#COL_TYPE_"+_pageRef).val()!="AR" && $("#COL_TYPE_"+_pageRef).val()!="EN"))
		    		{	 
							//fill the msg based on whether its daily or monthly
			    			if(colType == "MB" || colType == "MBF")
			    			{
			    				msg = allowedCurrencyDaily;
			    			}
			    			else
			    			{
			    				msg = allowedCurrencyMonthly;	
			    			}
			    			_showErrorMsg(msg,error_msg_title,400,100);
			    			$("#COL_TYPE_"+_pageRef).val("EN");
			    			return true;
		    		}
		    		else
		    		{
		    			return false;
		    		}
		    	 }
		    	//check if in the grid there's a row having 1 of the 4 and if the grid contains more than 1 row
		    	if((myObject["colmnTmpltVO.COL_TYPE"] == "GM" || myObject["colmnTmpltVO.COL_TYPE"] == "MB"
		    		|| myObject["colmnTmpltVO.COL_TYPE"] == "MBF" || myObject["colmnTmpltVO.COL_TYPE"] == "GMF")
		    		&& allRowIds.length>1)
		    	{	
		    		//if clicking on the + button in the grid and already nbRecords >1 => the second one cannot be other
		    		//than the EN or the AR and user is trying to insert additionnal record => stopper msg
		    		if(param==3)
		    		{
		    			msg = curAlreadyDefined;	
		    			_showErrorMsg(msg,error_msg_title,400,100);
		    		 	return true;
		    		}
		    		colType=myObject["colmnTmpltVO.COL_TYPE"];
		    		if(param==2 && ($("#COL_TYPE_"+_pageRef).val()=="AR" || $("#COL_TYPE_"+_pageRef).val()=="EN"))
		    		{
		    			return false;	
		    		}
		    		gridLineNbr	= myObject["colmnTmpltVO.LINE_NBR"];
		    		frmLineNbr 	= $("#LINE_NBR_"+_pageRef).val();
		    		if(param==2)
		    		{
		    				//user opened the div and already 2 rows in the grid and changing the type wihout updating
			    			if(frmLineNbr=="")
		    				{
			    				    detailGridClicked();	 
			    					msg = curAlreadyDefined;	
					    			_showErrorMsg(msg,error_msg_title,400,100);
					    		 	return true;
		    				}
			    			else
			    			{
				    				//first loop through the grid and check what's the available line numbers.if the frmLine nbr
				    				//isn't there => user trying to insert a new line=> blocking message
				    				found=false;
				    				for (var k =0;k<allRowIds.length;k++)
								    {
								    	llRowIdK=allRowIds[k];
								    	localObj =$("#detColGrid_"+_pageRef).jqGrid('getRowData',llRowIdK);
								    	if(frmLineNbr==localObj["colmnTmpltVO.LINE_NBR"])
								    	{
								    		found=true;
								    	}							    	
								    }
				    				if(found==false)
				    				{
				    					detailGridClicked();	 
				    					msg = curAlreadyDefined;	
						    			_showErrorMsg(msg,error_msg_title,400,100);
						    		 	return true;
				    				}
					    			//added condition for the case where the user is updating the calcType for a line that 
					    			//is already among the 4 new calc types.
					    			if(gridLineNbr!=frmLineNbr)
					    			{
							    			detailGridClicked();	 
											//fill the msg based on whether its daily or monthly
							    			if(colType == "MB" || colType == "MBF")
							    			{
							    				msg = allowedCurrencyDaily;
							    			}
							    			else
							    			{
							    				msg = allowedCurrencyMonthly;	
							    			}
							    			_showErrorMsg(msg,error_msg_title,400,100);
							    			return true;
					    			}
					    			else
					    			{
					    				return false;
					    			}
			    		    }
		    		 }
		    		 if(mode=="add")
		    		 {
		    		 	_showErrorMsg(msg,error_msg_title,400,100);
		    		 	return true;
		    		 }
		    	}
		    }
			//added for the case where adding a calctype among the new 4 and already rows in the grid not among the 4 and not EN or AR
			//param==2 => drpdwn being changed
			if(param==2 && 	(  $("#COL_TYPE_"+_pageRef).val()	=="MB" 
							|| $("#COL_TYPE_"+_pageRef).val()	=="GM" 
							|| $("#COL_TYPE_"+_pageRef).val()	=="MBF"
							|| $("#COL_TYPE_"+_pageRef).val()	=="GMF"))
			{
				//if no row in the grid is among the 4 
				if(anyRowActBal==0)
				{
					//checking if this only row is EN or AR to return false 
					if(allRowIds.length==1)
					{		    			
						myObject = $("#detColGrid_"+_pageRef).jqGrid('getRowData',allRowIds[0]);
						if(myObject["colmnTmpltVO.COL_TYPE"]=="EN" || myObject["colmnTmpltVO.COL_TYPE"]=="AR" )
						{
							return false;	
						}
						else
						{
							if($("#COL_TYPE_"+_pageRef).val() == "MB" || $("#COL_TYPE_"+_pageRef).val() == "MBF")
						   	{
						   		msg = allowedCurrencyDaily;
						   	}
						   	else
						   	{
						   		msg = allowedCurrencyMonthly;	
						   	}
							if(mode = "edit")
							{
								emptyDetForm(2);
							}
							else
							{
								_showErrorMsg(msg,error_msg_title,400,100);
								$("#COL_TYPE_"+_pageRef).val("AB");
								return true;
							}
						}
					}
					else if(allRowIds.length!=0)
					{
						if($("#COL_TYPE_"+_pageRef).val() == "MB" || $("#COL_TYPE_"+_pageRef).val() == "MBF")
					   	{
					   		msg = allowedCurrencyDaily;
					   	}
					   	else
					   	{
					   		msg = allowedCurrencyMonthly;	
					   	}
						_showErrorMsg(msg,error_msg_title,400,100);
						$("#COL_TYPE_"+_pageRef).val("AB");
						return true;
					}
				 }
				//there is a row among the 4 and the user is adding one among them
				else if(mode=="add")
				{
						if($("#COL_TYPE_"+_pageRef).val() == "MB" || $("#COL_TYPE_"+_pageRef).val() == "MBF")
					   	{
					   		msg = allowedCurrencyDaily;
					   	}
					   	else
					   	{
					   		msg = allowedCurrencyMonthly;	
					   	}
						_showErrorMsg(msg,error_msg_title,400,100);
						$("#COL_TYPE_"+_pageRef).val("AB");
						return true;
				}
			}
		 }
	}
	
	function checkRowCurType()
	{
		var allRowIds =  $("#detColGrid_"+_pageRef).jqGrid('getDataIDs');
		//checking we have only one row in the grid with calc type = 1 of the 4
		if(allRowIds.length==1)
		{
			myObject =$("#detColGrid_"+_pageRef).jqGrid('getRowData',llRowId[0]);
			if(myObject["colmnTmpltVO.COL_TYPE"] == "GM" || myObject["colmnTmpltVO.COL_TYPE"] == "MB"
		    		|| myObject["colmnTmpltVO.COL_TYPE"] == "MBF" || myObject["colmnTmpltVO.COL_TYPE"] == "GMF")
			{
				_showConfirmMsg(sureRowCurType, warning_msg_title,function(confirmcChoice, theArgs) {
				if (confirmcChoice) 
				{
				 	$("#COL_TYPE_"+_pageRef).val("EN");
				 	$("#COL_TYPE_STR").val($("#COL_TYPE_"+_pageRef+" option:selected").text());
				 	return false;
				}
				else
				{
					var collapseDiv = $("#inEditColTemplateDetailsDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
					$(collapseDiv).trigger("click");
					return true;
				}
			    }, {}, yes_confirm, no_confirm, 400, 100);
			}
		}
	}
	
	//Add new record in the  "detColGrid_${_pageRef}"
	function addTemplateDetCols() 
	{
		$("#linesOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
		document.getElementById("linesOK_"+_pageRef).disabled=false
		if(checkDayMonthBalance(3))
		{
			return;	
		}
		if(checkRowCurType())
		{
			return;
		}
		$("#linesGridMode").val("add");
		
	 	var lkpCompId=$("#lookuptxt_compID_"+_pageRef).val();
	 	var lineNbrCol=$("#LINE_NBR_"+_pageRef).val();
	 	var colType=$("#COL_TYPE_"+_pageRef).val();
	 	var fromDate=$("#FROM_DATE_"+_pageRef).val();
	 	
	 	//Get the selected row
	 	rowid = $("#detColGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	 	//Add '1' to have the line number value of the line after
		rowidAft = rowid-2+3;//because it's taking the + as concatenation
		
		object1	 =   $("#detColGrid_"+_pageRef).jqGrid('getRowData',rowid);
		object2    = $("#detColGrid_"+_pageRef).jqGrid('getRowData',rowidAft);
		lineBefore = object1["newLineNumber"];
		lineAfter =  object2["newLineNumber"];
		
		//Check if lineBefore is null, that means no lines exist in the 'detColGrid_', then empty the form and return
		//Check if lineAfter is null, that means no line after exist after the selected one in the 'detColGrid_', then empty the form and return
		if(lineBefore == null || lineAfter == null)
		{
			emptyDetForm(1);
			callDependency("LINE_NBR"+_pageRef+":singleColumnDetail.colmnTmpltVO.LINE_NBR",
					jQuery.contextPath+
					'/path/colTemplateMaintReport/col_applyRequiredFields.action',"colType:"+"AB"+",updates:"+"N","LINE_NBR","");
			enableExpressionControls(); 
		}
		//Check if the difference between the two lines is 1, then the user must reorganize the rows before adding the new one
		else if((lineAfter-lineBefore) == 1)
		{
			_showConfirmMsg(needReorganize, reorganizeTitle, function(confirmcChoice, theArgs){
           	if(confirmcChoice)
           	{
        		valueToRemember = lineBefore;
        	   	$("#reorganizeLines_"+_pageRef).dialog('open');
           	}
		    }, {rowid : rowid});	
		}
		$("#LINE_NBR_"+_pageRef).attr("readonly",false);
		
		//expand line details
 		var collapseDiv = $("#inEditColTemplateDetailsDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
		if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
		{
			$(collapseDiv).trigger("click");
		}
	}
	
	//Add new record in the Grid "detColGrid_${_pageRef}"
	function addTemplateDetColsAfterOk() 
	{
		_showProgressBar(true)
		$("#linesOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
		document.getElementById("linesOK_"+_pageRef).disabled=true
		if(checkDayMonthBalance(4))
		{
			_showProgressBar(false)
			return;
		}
		$("#linesGridMode").val("add");
		
	 	var lkpCompId=$("#lookuptxt_compID_"+_pageRef).val();
	 	var lineNbrCol=$("#LINE_NBR_"+_pageRef).val();
	 	var colType=$("#COL_TYPE_"+_pageRef).val();
	 	var fromDate=$("#FROM_DATE_"+_pageRef).val();
	 	var code =$("#CODE_"+_pageRef).val();
		//check if empty
		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/checkIfEmpty.action";
		params =$("#frmEditColTemplateDetails_"+_pageRef).serialize();
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
		{
			parseNumbers();
			if($("#CODE").val()=="" || $("#CODE").val()=="0")
			{
				$("#LINE_NBR_"+_pageRef).attr("readonly",true);
				_showProgressBar(false)	
				return;
			}
			if ($("#LINE_NBR_"+_pageRef).val() != "" && $("#LINE_NBR_"+_pageRef).val() != "0") 
			{
				if($("#CODE").val()!="" && $("#CODE").val()!="0")
				{
					var url1 = jQuery.contextPath+'/path/colTemplateMaintReport/createSession.action?linesGridMode=add';
					$.post(	url1,$("#frmEditColTemplates").serialize(),
							function(param)
							{
								$("#HIDDEN_CODE").val($("#CODE").val());
								var url = jQuery.contextPath+'/path/colTemplateMaintReport/addColumnTemplateLine.action?lineNbr='+lineNbrCol+'&code='+code;
									
								$.post(url,$("#frmEditColTemplateDetails_"+_pageRef).serialize(),
												function(param) {
													$("#detColGrid_"+_pageRef).jqGrid(
																	'setGridParam',
																	{
																		url : jQuery.contextPath+"/path/colTemplateMaintReport/fillDetailsGrid.action?detGridMode=fill&_pageRef="+_pageRef,
																		page : 1
																	}).trigger("reloadGrid");

												   	  $("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
												   			  	{    
												   				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=0&_pageRef="+_pageRef,
												   				page : 1
												   				}).trigger("reloadGrid");
												   	  emptyDetForm(1);	
												   	  _showProgressBar(false)
												});//end $.post
								//}
								_showProgressBar(true)
								$("#LINE_NBR_"+_pageRef).attr("readonly",false);
								callDependency("LINE_NBR"+_pageRef+":singleColumnDetail.colmnTmpltVO.LINE_NBR",
									jQuery.contextPath+'/path/colTemplateMaintReport/col_applyRequiredFields.action',
									"fromWhere:fg,updates:"+myObject["colmnTmpltVO.ALL_CRITERIA"],"LINE_NBR","")
									$("#tempCodeWithLineNbExpr_"+_pageRef).val("0~0");
								_showProgressBar(false);
							});
				}
				else
				{
					$("#LINE_NBR_"+_pageRef).attr("readonly",false);
					emptyDetForm(1);
					$("#tempCodeWithLineNbExpr_"+_pageRef).val("0~0");
					_showProgressBar(false);
				}
			}
			else
			{
				$("#LINE_NBR_"+_pageRef).attr("readonly",false);
				emptyDetForm(1);
				$("#tempCodeWithLineNbExpr_"+_pageRef).val("0~0");
				_showProgressBar(false);
			}		
		});
	}
	
	//Fill the Form "frmEditColTemplateDetails_${_pageRef}" when double click on a line in the Grid "detColGrid_${_pageRef}"
	function detailGridClicked() 
	{
		$("#linesOK_"+_pageRef).attr('aria-disabled',false).removeClass('ui-button-disabled ui-state-disabled');
		document.getElementById("linesOK_"+_pageRef).disabled=false
		//below func intialized the control vars new subline for criteria will be generated
	 	intializeControlColVars();
		var rows = $("#detColGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		rowObject =  $("#detColGrid_"+_pageRef).jqGrid("getRowData",rows);
		var colType = rowObject["colmnTmpltVO.COL_TYPE"];
		$("#linesGridMode").val("edit");
		
		rowid = $("#detColGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject = $("#detColGrid_"+_pageRef).jqGrid('getRowData', rowid);
		callDependency("LINE_NBR"+_pageRef+":singleColumnDetail.colmnTmpltVO.LINE_NBR",jQuery.contextPath+'/path/colTemplateMaintReport/col_applyRequiredFields.action',"fromWhere:fg,colType:"+colType+",updates:"+myObject["colmnTmpltVO.ALL_CRITERIA"],"LINE_NBR","")

		$("#COL_TYPE_"+_pageRef).val(myObject["colmnTmpltVO.COL_TYPE"]);
		var colTpe = myObject["colmnTmpltVO.COL_TYPE"];
		
		if(colTpe=="X")
		{
			disableExpressionControls();
		}
		else if(colTpe=="AM" || colTpe=="BM" || colTpe=="VB" || colTpe=="AMF"
			|| colTpe=="VBF" || colTpe=="AGC" || colTpe=="AGF"
			|| colTpe=="MB" || colTpe=="MBF" || colTpe=="GM" || colTpe=="GMF")
		{
			enableExpressionControls();
			enableToDate();
		}
		else
		{
			enableExpressionControls();
			disableToDate();
		}
		
		$("#COL_TYPE_STR").val(myObject["COL_TYPE_STR"]);
		$("#BRIEF_NAME_ENG_"+_pageRef).val(myObject["colmnTmpltVO.BRIEF_NAME_ENG"]);
		$("#BRIEF_NAME_ARAB_"+_pageRef).val(myObject["colmnTmpltVO.BRIEF_NAME_ARAB"]);
		$("#FROM_DATE_"+_pageRef).val(myObject["colmnTmpltVO.FROM_DATE"]);
		$("#TO_DATE_"+_pageRef).val(myObject["colmnTmpltVO.TO_DATE"]);
		$("#lookuptxt_compID_"+_pageRef).val(myObject["colmnTmpltVO.COMP"]);
		$("#lookuptxt_brnchCode_"+_pageRef).val(myObject["colmnTmpltVO.FROM_BRANCH"]);
		$("#lookuptxt_brnchCodeTo_"+_pageRef).val(myObject["colmnTmpltVO.TO_BRANCH"]);
		$("#lookuptxt_FROM_REGION_"+_pageRef).val(myObject["colmnTmpltVO.FROM_REGION"]);
		$("#lookuptxt_TO_REGION_"+_pageRef).val(myObject["colmnTmpltVO.TO_REGION"]);
		$("#colDaysOfWeekId_"+_pageRef).val(myObject["colmnTmpltVO.SPECIFY_DAY"]);
		$("#colListMonthsId_"+_pageRef).val(myObject["colmnTmpltVO.SPECIFY_MONTH"]);
		$("#lookuptxt_csvLookUp_"+_pageRef).val(myObject["colmnTmpltVO.FROM_CY"]);
		$("#currencyStr_"+_pageRef).val(myObject["CURRENCY_STR"])
		
		var divCodeFrm=myObject["colmnTmpltVO.FROM_DIV"];
		$("#lookuptxt_divCodeFrom_"+_pageRef).val(divCodeFrm);
		if(divCodeFrm==null || divCodeFrm=="")
		{
			liveSearch_makeReadOnly(true,"colmnTmpltVO.FROM_UNIT_"+_pageRef);
			liveSearch_makeReadOnly(true,"depCodeFrom_"+_pageRef);
		}
		else
		{
			liveSearch_makeReadOnly(false,"depCodeFrom_"+_pageRef);
		}

		var toDiv=myObject["colmnTmpltVO.TO_DIV"];
		$("#lookuptxt_divCodeTo_"+_pageRef).val(toDiv);
		if(toDiv==null || toDiv=="")
		{
			liveSearch_makeReadOnly(true,"TO_UNIT_"+_pageRef);
			liveSearch_makeReadOnly(true,"TO_DEPT_"+_pageRef);
		} 
		else
		{
			liveSearch_makeReadOnly(false,"TO_DEPT_"+_pageRef);
		}		
				
		var frmDept=myObject["colmnTmpltVO.FROM_DEPT"];
		$("#lookuptxt_depCodeFrom_"+_pageRef).val(frmDept);
		if(frmDept==null || frmDept=="")
		{
			liveSearch_makeReadOnly(true,"FROM_UNIT_"+_pageRef);
		}
		else
		{
			liveSearch_makeReadOnly(false,"FROM_UNIT_"+_pageRef);
		}

		var toDeptt=myObject["colmnTmpltVO.TO_DEPT"];
		$("#lookuptxt_TO_DEPT_"+_pageRef).val(toDeptt);
		if(toDeptt==null || toDeptt=="")
		{
			liveSearch_makeReadOnly(true,"TO_UNIT_"+_pageRef);
		}
		else
		{
			liveSearch_makeReadOnly(false,"TO_UNIT_"+_pageRef);
		}
		
		$("#lookuptxt_FROM_UNIT_"+_pageRef).val(myObject["colmnTmpltVO.FROM_UNIT"]);
		$("#lookuptxt_TO_UNIT_"+_pageRef).val(myObject["colmnTmpltVO.TO_UNIT"]);
		$("#compDesc_"+_pageRef).val(myObject["COMP_STR"]);
		$("#brnchDesc_"+_pageRef).val(myObject["FROM_BRANCH_STR"]);
		$("#brnchToDesc_"+_pageRef).val(myObject["TO_BRANCH_STR"]);
		$("#regionFromStr_"+_pageRef).val(myObject["FROM_REGION_STR"]);
		$("#regionToStr_"+_pageRef).val(myObject["TO_REGION_STR"]);
		$("#divCodeFromDesc_"+_pageRef).val(myObject["FROM_DIV_STR"]);
		$("#divCodeToDesc_"+_pageRef).val(myObject["TO_DIV_STR"]);
		$("#depCodeFromDesc_"+_pageRef).val(myObject["FROM_DEPT_STR"]);
		$("#depCodeToDesc_"+_pageRef).val(myObject["TO_DEPT_STR"]);
		$("#unitFromDesc_"+_pageRef).val(myObject["FROM_UNIT_STR"]);
		$("#unitToDesc_"+_pageRef).val(myObject["TO_UNIT_STR"]);
		
		if(colTpe=="X")
		{
			liveSearch_makeReadOnly(true,"brnchCode_"+_pageRef);
			liveSearch_makeReadOnly(true,"brnchCodeTo_"+_pageRef);
			liveSearch_makeReadOnly(true,"FROM_REGION_"+_pageRef);
			liveSearch_makeReadOnly(true,"TO_REGION_"+_pageRef);
			liveSearch_makeReadOnly(true,"divCodeFrom_"+_pageRef);
			liveSearch_makeReadOnly(true,"divCodeTo_"+_pageRef);
		}
		else
		{
			liveSearch_makeReadOnly(false,"brnchCode_"+_pageRef);
			liveSearch_makeReadOnly(false,"brnchCodeTo_"+_pageRef);
			liveSearch_makeReadOnly(false,"FROM_REGION_"+_pageRef);
			liveSearch_makeReadOnly(false,"TO_REGION_"+_pageRef);
			liveSearch_makeReadOnly(false,"divCodeFrom_"+_pageRef);
			liveSearch_makeReadOnly(false,"divCodeTo_"+_pageRef);
		}
		
		if (myObject["colmnTmpltVO.BOLD"] == "Y")
		{
			$("#BOLD").attr('checked', true);
		}
		else
		{
			$("#BOLD").attr('checked', false);
		}

		if (myObject["colmnTmpltVO.DISP_COL_TOT_ZERO"] == "Y")
		{
			$("#DISP_COL_TOT_ZERO").attr('checked', true);
		}
		else
		{
			$("#DISP_COL_TOT_ZERO").attr('checked', false);
		}

		if((myObject["colmnTmpltVO.ALL_CRITERIA"] == "Y"))
		{
			$("#chkAllCriteria").attr('checked', true);			
		}
		else
		{
			$("#chkAllCriteria").attr('checked', false);			
		}
		
		$("#LINE_NBR_"+_pageRef).val(myObject["newLineNumber"]);
		$("#HIDDEN_CODE").val($("#CODE").val());
		$("#tempCode2").val($("#CODE").val());
		$("#detailColGridMode").val("edit");
		
		var zLineNb=myObject["newLineNumber"];
		if(zLineNb==null)
		{
			return;
		}
		
		//var zTemplCode=$("#CODE_"+_pageRef).val();
		var zTemplCode=myObject["colmnTmpltVO.CODE"];
		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/checkIfExprLine.action?code="+zTemplCode+"&lineNumber="+zLineNb+"&_pageRef="+_pageRef;
				
		params ={};
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
		{
			$("#tabs_"+_pageRef).tabs('enable', 1)
			$("#tabs_"+_pageRef).tabs('enable', 0)
			
			var zSelected = $('#tabs_'+_pageRef).tabs('option', 'selected');
			globTmpCodeWithLnNb=zTemplCode+"~"+zLineNb;	
			globalLineNbr=zLineNb;
			if(zSelected=="0")
			{
				newUrl = jQuery.contextPath+ "/path/colTemplateMaintReport/openCriteria.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
				$("#tabs_"+_pageRef).tabs( "url" , 0 , newUrl );
				$("#tabs_"+_pageRef).tabs( "load" , 0 );
			}
			else if(zSelected=="1")
			{
				newUrl = jQuery.contextPath+ "/path/templateExpression/openExpression.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef+"&lineNbr="+zLineNb;
				$("#tabs_"+_pageRef).tabs( "url" , 1 , newUrl );
				$("#tabs_"+_pageRef).tabs( "load" , 1 );
			}
			else //select
			{
				$("#tabs_"+_pageRef).tabs('select',0);
			}           
			
			$("#LINE_NBR_"+_pageRef).attr("readonly",true);
			if($("#COL_TYPE_"+_pageRef).val()!="X")
			{ 
				$("#tabs_"+_pageRef).tabs("option", { "selected": 0,"disabled": [1]})
			}
			else
			{
				$("#tabs_"+_pageRef).tabs("option", { "selected": 1,"disabled": [0]})
				if(zSelected=="0")
				{
					newUrl = jQuery.contextPath+ "/path/templateExpression/openExpression.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef+"&lineNbr="+zLineNb;
					$("#tabs_"+_pageRef).tabs( "url" , 1 , newUrl );
					$("#tabs_"+_pageRef).tabs( "load" , 1 );
				}
			}
		});
		
		//expand line details
 		var collapseDiv = $("#inEditColTemplateDetailsDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
		if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
		{
			$(collapseDiv).trigger("click");
		}
		
	}
	
	function checkExprCell(obj)
	{
		myObject = $("#exprTemplGrid_"+_pageRef).jqGrid('getRowData',colExprRowId);
		var expType=myObject["EXP_TYPE"];

		if(expType=="L")
		{
			var url = jQuery.contextPath+ "/path/colTemplateMaintReport/lineOrValueChange";
		    params ={};
		    params["EXP_TYPE"]=expType;
		    params["EXP_LINE"]=obj.value;
		    $.getJSON(url, params, function( data2, status, xhr ) 
			{
			   var retVal=data2['updates'];
			   if(retVal=="false")
			   {
				 //  _showErrorMsg(invalidLnNbAlert);
					obj.value="";
			   }
	 		});
		}
	}
	
	function fromDateChanged()
	{
		if($("#COL_TYPE_"+_pageRef).val()=="X")
		{
			$("#FROM_DATE_"+_pageRef).val("");
		}
		else if($("#FROM_DATE_"+_pageRef).val()!="" && $("#TO_DATE_"+_pageRef).val()!="")
		{
			var dateCompare = compareDate("FROM_DATE_"+_pageRef, "TO_DATE_"+_pageRef);
			if (dateCompare == 0 || dateCompare == 1) 
			{
				_showErrorMsg(invalidToDate);
				$("#FROM_DATE_"+_pageRef).val("");
			}
		}
	}
	
	function toDateChanged()
	{
		if($("#COL_TYPE_"+_pageRef).val()=="X")
		{
			$("#TO_DATE_"+_pageRef).val("");
		}
		else if($("#FROM_DATE_"+_pageRef).val()!="" && $("#TO_DATE_"+_pageRef).val()!="")
		{
			var dateCompare = compareDate("FROM_DATE_"+_pageRef, "TO_DATE_"+_pageRef);
			if (dateCompare == 0 || dateCompare == 1) 
			{
				_showErrorMsg(invalidToDate);
				$("#TO_DATE_"+_pageRef).val("");
			}
		}
	}
	
	//Add a new record in the Grid "exprTemplGrid_${_pageRef}"
	function addColExprLine()
	{
		//Check if the Column Type in the Form "frmEditColTemplateDetails_${_pageRef}" is not of type "Expression"
		if($("#COL_TYPE_"+_pageRef).val()!="X")
		{
			_showErrorMsg(exprColTypeAlert);
			return;
		}
		
		//If the Line Number or Template Code = ("0" or "") in the Form "frmEditColTemplateDetails_${_pageRef}",
		//That means no record added or selected in the Grid "detColGrid_${_pageRef}"
		if($("#LINE_NBR_"+_pageRef).val()=="0" || $("#LINE_NBR_"+_pageRef).val()=="" || $("#CODE").val()=="0" || $("#CODE").val()=="")
		{
			return;
		}
		else
		{
			$("#tempCodeLineNbr").val($("#LINE_NBR_"+_pageRef).val());
			$("#tempCode2").val($("#CODE").val());
		}

		var zSrc= jQuery.contextPath+"/path/colTemplateMaintReport/findColumnTemplateDetailType.action?TEMPLATE_CODE="+ $("#CODE").val() + "&lineNbr=" + $("#LINE_NBR_"+_pageRef).val();
		params ={};
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
				{
						var currVal=data2['columnTemplateDetailsCount'];
						if(currVal=="true")
						{
						  	//save previous row
						    var jsonStringUpdates = $("#exprTemplGrid_"+_pageRef).jqGrid('getChangedRowData'); 
						    $("#updatesColTmpl").val(jsonStringUpdates); 
						    var url = $("#exprGridform_"+_pageRef).attr("action");
							myObject =  $("#exprGridform_"+_pageRef).serialize();
							$.post(url, myObject , function( param )
						 	{
						   	  $("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
						   			  	{    
						   				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=" + $("#lineNbrCol").val(),
						   				page : 1
						   				}).trigger("reloadGrid");
						   	  isAdd="true";
						   	  window.setTimeout("delayFuncColTempl()" ,1000);
						 	});
						}
						else
						{
						}	
				});
	}
	
	function templateCodeChanged(isFrom ,obj)
	{
		_showProgressBar(true)
		if(obj.value=="0")
		{
			_showErrorMsg(positiveNbAlert, error_msg_title, 300, 100);
			_showProgressBar(false)
			return;
		}
		
		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/countOfColumnTemplate.action?TEMPLATE_CODE="+ obj.value;
		params ={};
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
				{
						var currVal=data2['columnTemplateCount'];
			 			if(currVal=="1") 
			 			{
			 				_showErrorMsg(codeExistAlert, error_msg_title, 300, 100);
			 				obj.value="";
				 		}
			 			else
			 			{
			 				 if(isFrom=="0")
			 				 {
			 					 $("#CODE_"+_pageRef).attr("readonly",true);
			 					 
			 				}
			 			}
			 			 _showProgressBar(false)
			 	});
	}
	
	function delayFuncColTempl()
	{
		var rowCount = $("#exprTemplGrid_"+_pageRef).jqGrid('getGridParam', 'records');
		if(rowCount==0)
		{
			$("#exprTemplGrid_"+_pageRef).jqGrid('addInlineRow',{OPERATOR:''});
		}
		else
		{
			$("#exprTemplGrid_"+_pageRef).jqGrid('addInlineRow',{OPERATOR:'+'});
		}
	}
	
	function DetLineNumberChanged(obj) 
	{
		if(obj.value == 0)
		{
			obj.value="";
			return;
		}
		
		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/findColumnTemplateDetailsCount.action?updates="+ $("#CODE_"+_pageRef).val() + "&lineNbr=" + $("#LINE_NBR_"+_pageRef).val();
		params ={};
		params["_pageRef"]=_pageRef;
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
				{
			
					var currVal=data2['columnTemplateDetailsCount'];
					var zAlert=data2['updates'];
					if(zAlert!="")
					{
						_showErrorMsg(zAlert)
					}
		 			if(currVal=="1") 
		 			{
						_showErrorMsg(lineNumberExistAlert);
						document.getElementById("LINE_NBR_"+_pageRef).focus();
						emptyDetForm(1);
			 		}
			 	});
	}
	
function makeFieldsReadOnly(yn)
{
	liveSearch_makeReadOnly(yn,"brnchCode_"+_pageRef);
	liveSearch_makeReadOnly(yn,"brnchCodeTo_"+_pageRef);
	liveSearch_makeReadOnly(yn,"FROM_REGION_"+_pageRef);
	liveSearch_makeReadOnly(yn,"TO_REGION_"+_pageRef);
	liveSearch_makeReadOnly(yn,"divCodeFrom_"+_pageRef);
	liveSearch_makeReadOnly(yn,"divCodeTo_"+_pageRef);
	liveSearch_makeReadOnly(yn,"depCodeFrom_"+_pageRef);
	liveSearch_makeReadOnly(yn,"TO_DEPT_"+_pageRef);
	liveSearch_makeReadOnly(yn,"FROM_UNIT_"+_pageRef);
	liveSearch_makeReadOnly(yn,"TO_UNIT_"+_pageRef);
}
	
	function allCriteriaClicked()
	{
		if(document.getElementById("chkAllCriteria").checked==false)
		{
			if($("#lookuptxt_compID_"+_pageRef).val()=="")
			{
				return;
			}
			clearDependents("company");
			makeFieldsReadOnly(false);
		}
		else
		{
			if($("#lookuptxt_compID_"+_pageRef).val()=="")
			{
				$("#chkAllCriteria").attr('checked',false);
				return;
			}
			var colTpe = $("#COL_TYPE_"+_pageRef).val();
			var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/findAllCriteria.action?comp_code=" + $("#lookuptxt_compID_"+_pageRef).val()+"&updates="+colTpe;
			params ={};
			$.getJSON(zSrc, params, function( data2, status, xhr ) 
			{
			
				$("#lookuptxt_brnchCode_"+_pageRef).val(data2['FROM_BRANCH']);
				$("#lookuptxt_brnchCodeTo_"+_pageRef).val(data2['TO_BRANCH']);
				$("#lookuptxt_FROM_REGION_"+_pageRef).val(data2['FROM_REGION']);
				$("#lookuptxt_TO_REGION_"+_pageRef).val(data2['TO_REGION']);
				$("#lookuptxt_divCodeFrom_"+_pageRef).val(data2['FROM_DIV']);
				$("#lookuptxt_divCodeTo_"+_pageRef).val(data2['TO_DIV']);
				$("#lookuptxt_depCodeFrom_"+_pageRef).val(data2['FROM_DEP']);
				$("#lookuptxt_TO_DEPT_"+_pageRef).val(data2['TO_DEP']);
				$("#lookuptxt_FROM_UNIT_"+_pageRef).val(data2['FROM_UNIT']);
				$("#lookuptxt_TO_UNIT_"+_pageRef).val(data2['TO_UNIT']);
				$("#regionFromStr_"+_pageRef).val(data2['FROM_REGION_DESC']);
				$("#brnchDesc_"+_pageRef).val(data2['FROM_BRANCH_DESC']);
				$("#brnchToDesc_"+_pageRef).val(data2['TO_BRANCH_DESC']);
				$("#region1Str_"+_pageRef).val(data2['FROM_REGION_DESC']);
				$("#regionToStr_"+_pageRef).val(data2['TO_REGION_DESC']);
				$("#divCodeFromDesc_"+_pageRef).val(data2['FROM_DIV_DESC']);
				$("#divCodeToDesc_"+_pageRef).val(data2['TO_DIV_DESC']);
				$("#depCodeFromDesc_"+_pageRef).val(data2['FROM_DEP_DESC']);
				$("#depCodeToDesc_"+_pageRef).val(data2['TO_DEP_DESC']);
				$("#unitFromDesc_"+_pageRef).val(data2['FROM_UNIT_DESC']);
				$("#unitToDesc_"+_pageRef).val(data2['TO_UNIT_DESC']);
				
				makeFieldsReadOnly(true);
			});
		}
	}
		
	//Enable Controls Of the "frmEditColTemplateDetails_${_pageRef}" Form
	function enableExpressionControls()
	{
		
		$("#chkAllCriteria").attr('disabled', false);
		disableDatepicker("FROM_DATE_"+_pageRef,false);
		
		liveSearch_makeReadOnly(false,"compID_"+_pageRef);
		liveSearch_makeReadOnly(true,"brnchCode_"+_pageRef);
		liveSearch_makeReadOnly(true,"brnchCodeTo_"+_pageRef);
		liveSearch_makeReadOnly(true,"FROM_REGION_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_REGION_"+_pageRef);
		liveSearch_makeReadOnly(true,"divCodeFrom_"+_pageRef);
		liveSearch_makeReadOnly(true,"divCodeTo_"+_pageRef);
		liveSearch_makeReadOnly(true,"depCodeFrom_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_DEPT_"+_pageRef);
		liveSearch_makeReadOnly(true,"FROM_UNIT_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_UNIT_"+_pageRef);
	}
	
	//Disable Controls Of the "frmEditColTemplateDetails_${_pageRef}" Form
	function disableExpressionControls()
	{
		$("#chkAllCriteria").attr('disabled', true);
		disableDatepicker("FROM_DATE_"+_pageRef,true);
		disableDatepicker("TO_DATE_"+_pageRef,true);
		
		liveSearch_makeReadOnly(true,"compID_"+_pageRef);
		liveSearch_makeReadOnly(true,"brnchCode_"+_pageRef);
		liveSearch_makeReadOnly(true,"brnchCodeTo_"+_pageRef);
		liveSearch_makeReadOnly(true,"FROM_REGION_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_REGION_"+_pageRef);
		liveSearch_makeReadOnly(true,"divCodeFrom_"+_pageRef);
		liveSearch_makeReadOnly(true,"divCodeTo_"+_pageRef);
		liveSearch_makeReadOnly(true,"depCodeFrom_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_DEPT_"+_pageRef);
		liveSearch_makeReadOnly(true,"FROM_UNIT_"+_pageRef);
		liveSearch_makeReadOnly(true,"TO_UNIT_"+_pageRef);
	}
	
	//Delete selected record in the "detColGrid_${_pageRef}" Grid
	function onDetDelClicked(rowid) 
	{
		 _showConfirmMsg(deleteConfirm,deleteTitle, function(confirmcChoice, theArgs){
	       if(confirmcChoice)
	       {
	    	   $("#tempCodeWithLineNbExpr_"+_pageRef).val("0~0");
	    	   deleteDetails(theArgs.rowid)
	       }
	      }, {rowid : rowid});	
	}
		
	//Delete a record in the "detColGrid_${_pageRef}" Grid
	function deleteDetails(rowid)
	{
		$("#linesGridMode").val("delete");
		
		var lineNumber = $("#detColGrid_"+_pageRef).jqGrid('getCell', rowid,'colmnTmpltVO.LINE_NBR');
		//var templateCode = $("#detColGrid_"+_pageRef).jqGrid('getCell', rowid,'CODE');
		var param = {};
		param["lineNbr"] = lineNumber.toString();
		//param["singleColumnDetail.CODE"] = templateCode.toString();
		var url = jQuery.contextPath+'/path/colTemplateMaintReport/deleteColumnTemplateDetail.action?_pageRef='+_pageRef;
		$("#lineNbr").val("");
		$.get(url,param,function(param) {
							$("#detColGrid_"+_pageRef).jqGrid('setGridParam',
								{
									url : jQuery.contextPath+"/path/colTemplateMaintReport/fillDetailsGrid.action?detGridMode=fill&linesGridMode=delete&_pageRef="+_pageRef,
									page : 1
								}).trigger("reloadGrid");
							$("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
				   			  	{    
					   				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=0&_pageRef="+_pageRef,
					   				page : 1
				   				}).trigger("reloadGrid");
							$("#crtGrids_"+_pageRef).jqGrid('setGridParam',
				   			  	{    
					   				url : jQuery.contextPath+"/path/colTemplateMaintReport/crtGridUrl.action?tempCodeWithLineNb=0~0&_pageRef="+_pageRef,
					   				page : 1
				   				}).trigger("reloadGrid");
							//added to hide the combo added for daily / monthly balance (GM','MB','MBF','GMF)
							callDependency("LINE_NBR"+_pageRef+":singleColumnDetail.colmnTmpltVO.LINE_NBR",
									jQuery.contextPath+'/path/colTemplateMaintReport/col_applyRequiredFields.action',
									"fromWhere:fg,updates:"+myObject["colmnTmpltVO.ALL_CRITERIA"],"LINE_NBR","")
						});
		
		$("#LINE_NBR_"+_pageRef).attr("readonly",true);
		emptyDetForm(1);
	}
		
	//Delete selected record in the "exprTemplGrid_${_pageRef}" Grid
	function onExpressionDelClicked(rowid) 
	{
		 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
        	   deleteExprCol(theArgs.rowid)
           }
	      }, {rowid : rowid});
	}

	//Delete a record in the "exprTemplGrid_${_pageRef}" Grid
	function deleteExprCol(rowid)
	{
		var lineNumber = $("#exprTemplGrid_"+_pageRef).jqGrid('getCell', rowid, 'TMPLT_LINE_NO');
		var templateCode = $("#exprTemplGrid_"+_pageRef).jqGrid('getCell', rowid, 'CODE');
		var expressionNbr = $("#exprTemplGrid_"+_pageRef).jqGrid('getCell', rowid, 'LINE_NO');
		
		var param = {};
		param["lineNbr"] = lineNumber.toString();
		param["singleColumnDetail.CODE"] = templateCode.toString();
		param["expressionNbr"] = expressionNbr.toString();
		var url = jQuery.contextPath+'/path/colTemplateMaintReport/deleteExpression.action';
	
		$.get(url,param,function(param) {
			$("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
	   			  	{    
	   				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=" + $("#LINE_NBR_"+_pageRef).val(),
	   				page : 1
	   				}).trigger("reloadGrid");
					});
	}
	
	function columnTypeChanged()
	{
			
	//callDependency("LINE_NBR_"+_pageRef+":singleColumnDetail.colmnTmpltVO.LINE_NBR",jQuery.contextPath+'/path/colTemplateMaintReport/col_applyRequiredFields.action',"singleColumnDetail.colmnTmpltVO.LINE_NBR","LINE_NBR","")
	    if(checkDayMonthBalance(2))
	    {
	    	return;
	    }
		emptyDetForm(2);
		//reinitializing values of the combos on change of calculation type
		if($("#COL_TYPE_"+_pageRef).val()=="MB" || $("#COL_TYPE_"+_pageRef).val()=="MBF")
		{
			$("#colListMonthsId_"+_pageRef).val("");	
		}
		else if ($("#COL_TYPE_"+_pageRef).val()=="GM" || $("#COL_TYPE_"+_pageRef).val()=="GMF")
		{
			$("#colDaysOfWeekId_"+_pageRef).val("");
		}
		else if ($("#COL_TYPE_"+_pageRef).val()=="EN")
		{
			_showErrorMsg(warningEngCurrBriefName,warning_msg_title,500,100)
		}
		if($("#LINE_NBR_"+_pageRef).val()=="" ||$("#LINE_NBR_"+_pageRef).val()=="0")
		{
			_showErrorMsg(lineNbAlert);
			$("#COL_TYPE_"+_pageRef).val(0);
			return;
		}
		else if($("#COL_TYPE_"+_pageRef).val()!="X")
		{
			var colTpe = $("#COL_TYPE_"+_pageRef).val();
			if(colTpe=="AM" || colTpe=="BM" || colTpe=="VB" || colTpe=="AMF"
				|| colTpe=="VBF" || colTpe=="AGC" || colTpe=="AGF" || colTpe=="MB" || colTpe=="MBF"
					|| colTpe=="GM" || colTpe=="GMF")
			{
				enableToDate();
			}
			else
			{
				disableToDate();
			}
			
			var url = jQuery.contextPath+'/path/colTemplateMaintReport/deleteExpressions.action?lineNbr='+$("#LINE_NBR_"+_pageRef).val();
			 $.get(url, {} , function( param ){
					$("#exprTemplGrid_"+_pageRef).jqGrid('setGridParam',
			   			  	{    
			   				url : jQuery.contextPath+"/path/templateExpression/loadExprList.action?lineNbr=0&_pageRef="+_pageRef,
			   				page : 1
			   				}).trigger("reloadGrid");

				 });
			 enableExpressionControls();
		}
		if($("#COL_TYPE_"+_pageRef).val()=="X" || $("#COL_TYPE_"+_pageRef).val()=="EN" || $("#COL_TYPE_"+_pageRef).val()=="AR")
		{
			disableExpressionControls();
			clearDependents("company");
			$("#chkAllCriteria").attr('checked', false);
			$("#FROM_DATE_"+_pageRef).val("");
			$("#TO_DATE_"+_pageRef).val("");
			$("#lookuptxt_compID_"+_pageRef).val("");
			$("#compDesc_"+_pageRef).val("");
		}
		$("#COL_TYPE_STR").val($("#COL_TYPE_"+_pageRef+" option:selected").text());
	}
 
	
	//dependencies
	function clearDependents(dependent)
	{
		if(dependent=="company")
		{
			$("#lookuptxt_brnchCode_"+_pageRef).val("");
			$("#lookuptxt_brnchCodeTo_"+_pageRef).val("");
			$("#lookuptxt_FROM_REGION_"+_pageRef).val("");
			$("#lookuptxt_TO_REGION_"+_pageRef).val("");
			$("#lookuptxt_divCodeFrom_"+_pageRef).val("");
			$("#lookuptxt_divCodeTo_"+_pageRef).val("");
			$("#lookuptxt_depCodeFrom_"+_pageRef).val("");
			$("#lookuptxt_TO_DEPT_"+_pageRef).val("");
			$("#lookuptxt_FROM_UNIT_"+_pageRef).val("");
			$("#lookuptxt_TO_UNIT_"+_pageRef).val("");
			$("#regionFromStr_"+_pageRef).val("");
			$("#brnchDesc_"+_pageRef).val("");
			$("#brnchToDesc_"+_pageRef).val("");
			$("#region1Str_"+_pageRef).val("");
			$("#regionToStr_"+_pageRef).val("");
			$("#divCodeFromDesc_"+_pageRef).val("");
			$("#divCodeToDesc_"+_pageRef).val("");
			$("#depCodeFromDesc_"+_pageRef).val("");
			$("#depCodeToDesc_"+_pageRef).val("");
			$("#unitFromDesc_"+_pageRef).val("");
			$("#unitToDesc_"+_pageRef).val("");
		}
		if(dependent=="fromdivision")
		{
			$("#lookuptxt_depCodeFrom_"+_pageRef).val("");
			$("#lookuptxt_FROM_UNIT_"+_pageRef).val("");
			$("#unitFromDesc_"+_pageRef).val("");
			$("#depCodeFromDesc_"+_pageRef).val("");
		}

		if(dependent=="todivision")
		{
			$("#lookuptxt_TO_DEPT_"+_pageRef).val("");
			$("#lookuptxt_TO_UNIT_"+_pageRef).val("");
			$("#unitToDesc_"+_pageRef).val("");
			$("#depCodeToDesc_"+_pageRef).val("");
		}
		if(dependent=="fromdepartment")
		{
			$("#lookuptxt_FROM_UNIT_"+_pageRef).val("");
			$("#unitFromDesc_"+_pageRef).val("");
		}
		if(dependent=="todepartment")
		{
			$("#lookuptxt_TO_UNIT_"+_pageRef).val("");
			$("#unitToDesc_"+_pageRef).val("");
		}
	}

	function checkDept(isFrom)
	{
		if(isFrom==1 && $("#depCodeFromDesc_"+_pageRef).val()=="")
		{
			$("#lookuptxt_FROM_UNIT_"+_pageRef).val("");
			$("#unitFromDesc_"+_pageRef).val("");
		}
		if(isFrom==2 && $("#depCodeToDesc_"+_pageRef).val()=="")
		{
			$("#lookuptxt_TO_UNIT_"+_pageRef).val("");
			$("#unitToDesc_"+_pageRef).val("");
		}
		
	}
	
	function regionFromDependency()
	{
//		if($("#lookuptxt_compID_"+_pageRef).val()==null || $("#lookuptxt_compID_"+_pageRef).val()=="" )
//			{
//			_showErrorMsg(dataRequAlert);
//				$("#lookuptxt_FROM_REGION_"+_pageRef).val("");
//				return;
//			}
//	
//		if($("#lookuptxt_FROM_REGION_"+_pageRef).val()=="")
//			return;
//		
//		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/applyCurrDependency.action?allCodes=" + $("#lookuptxt_compID_"+_pageRef).val() + "~" + $("#lookuptxt_FROM_REGION_"+_pageRef).val();
//		params ={};
//		$.getJSON(zSrc, params, function( data2, status, xhr ) 
//				{
//					
//						var currVal=data2['regionDependencyVO']['TEMPLATE_DESCRIPTION2'];
//			 			if(currVal=="-1") // if the regionCode is null or the region code does not exist 
//			 			{
//							$("#region1Str_"+_pageRef).val("");
//							$("#lookuptxt_FROM_REGION_"+_pageRef).val("");
//			 			}
//			 			else
//			 			{
//			 				$("#region1Str_"+_pageRef).val(currVal);
//			 			}
//				}); 
	}

	function afterCompDependency()
	{
		$("#chkAllCriteria").attr('checked', false);
		clearDependents("company");
		makeFieldsReadOnly(false);
	}

	function checkIfFC(isFrm)
	{
//		var colTpe = $("#COL_TYPE").val();
//		//return the base region
//		 var url = jQuery.contextPath+'/path/colTemplateMaintReport/col_retBaseCurrenc';
//		 myObject = {};
//		 $.post(url, myObject , function( param )
//		 {		
//				var baseCurr=param['updates'];
//				if(isFrm==1)
//				{
//					if((colTpe=="ABF" || colTpe=="AGF" || colTpe=="AMF" ||colTpe=="VBF") && ($("#lookuptxt_FROM_REGION_"+_pageRef).val()==baseCurr))
//					{
//						$("#lookuptxt_FROM_REGION_"+_pageRef).val("");
//						$("#region1Str_"+_pageRef).val("");
//					}
//				}
//				else
//				{
//					if((colTpe=="ABF" || colTpe=="AGF" || colTpe=="AMF" ||colTpe=="VBF") && ($("#lookuptxt_TO_REGION_"+_pageRef).val()==baseCurr))
//					{
//						$("#lookuptxt_TO_REGION_"+_pageRef).val("");
//						$("#regionToStr_"+_pageRef).val("");
//					}
//				}
//		}); 
//		
//		
//		////////	
//		
	}
	
	function divisionToDependency()
	{
		if($("#lookuptxt_divCodeTo_"+_pageRef).val()=="")
			return;
		if($("#lookuptxt_compID_"+_pageRef).val()==null || $("#lookuptxt_compID_"+_pageRef).val()=="" )
		{
			_showErrorMsg(dataRequAler);
			$("#lookuptxt_divCodeTo_"+_pageRef).val("");
			return;
		}
		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/applyDivisionDependency.action?allCodes=" + $("#lookuptxt_compID_"+_pageRef).val() + "~" +$("#lookuptxt_divCodeTo_"+_pageRef).val();
		params ={};
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
				{
						var divVal=data2['lkpDependencyVO']['BRIEF_DESC_ENG'];
			 			if(divVal=="-1") // if the branch code is null or the branch code does not exist 
			 			{
							$("#lookuptxt_divCodeTo_"+_pageRef).val("");
							$("#divCodeToDesc_"+_pageRef).val("");
							clearDependents("todivision");
			 			}
			 			else
			 			{
			 				$("#divCodeToDesc_"+_pageRef).val(divVal);
			 			}
				}); 
	}

	function checkDiv(isFrom)
	{
		if(isFrom==1 && $("#lookuptxt_divCodeFrom_"+_pageRef).val()=="")
		{
			clearDependents("fromdivision");
		}
		if(isFrom==2 &&  $("#lookuptxt_divCodeTo_"+_pageRef).val()=="")
		{
			clearDependents("todivision");
		}
	}
	
	function saveAll()
	{
		if($("#CODE_"+_pageRef).val()=="" || $("#CODE_"+_pageRef).val()=="0" || $("#briefNameEng_"+_pageRef).val()=="")
		{
			_showErrorMsg(dataRequAlert,info_msg_title);
			return false;
		}
		var zRowId = $("#colGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		var myObj = $("#colGrid_"+_pageRef).jqGrid('getRowData',zRowId);
		var tmpCode=$("#CODE_"+_pageRef).val();
		
		var linesGridMode = $("#linesGridMode").val();
		var url = jQuery.contextPath+'/path/colTemplateMaintReport/saveTemplate.action?linesGridMode='+linesGridMode;
		myObject = $("#frmEditColTemplates").serialize();
		myObject+="&column1="+myObj["colmnTmpltVO.BRIEF_NAME_ENG"];
		myObject+="&column2="+myObj["colmnTmpltVO.BRIEF_NAME_ARAB"];
		myObject+="&column3="+myObj["colmnTmpltVO.PRINT_PAPER_SIZE"];
		myObject+="&updates="+myObj["colmnTmpltVO.PRINT_PAPER_ORIENTATION"];
		$.ajax({
			url: url,
	        type:"post",
			dataType:"json",
			data: myObject,
			success: function(param)
			{
		 		if(typeof param["_error"] == "undefined" || param["_error"] == null)
			    {
		 			$("#tempCodeWithLineNbExpr_"+_pageRef).val("0~0");
					emptyDetForm(1);
					emptyMainForm();
					$("#colGrid_"+_pageRef).trigger("reloadGrid");			
					var collapseDiv = $("#inEditColTemplateDetailsDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
					if(!$(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
					{
						$(collapseDiv).trigger("click");
					}
					newUrl = jQuery.contextPath+ "/path/colTemplateMaintReport/openCriteria.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
					$("#tabs_"+_pageRef).tabs( "url" , 0 , newUrl );
					$("#tabs_"+_pageRef).tabs( "load" , 0 );					
					_showConfirmMsg(runColTemplProcAlert, runProcTitle, function(confirmcChoice, theArgs){
		           	if(confirmcChoice)
		           	{
		        		rep_col_runColTemplProc(theArgs.tmpCode)
		           	}
				    }, {tmpCode : tmpCode});
				}
			}
		 });
		return false;
	}
		
	function rep_col_runColTemplProc(tmpCode)
	{
		 var url = jQuery.contextPath+ "/path/templateProcess/proc_runColTemplProcess";
		 params ={};
		 params["code"]=tmpCode;
		 $.getJSON(url, params, function( data2, status, xhr ) 
		 {
		   var procVal=data2['updates'];
		   if(procVal == "true")
		   {
			   _showErrorMsg(colTmplProcDoneAlert,info_msg_title);
		   }
	 	});
	}
	
	function rep_col_submitLinesOrder()
	{
		orgMsg="";
		if($("#startingLineValue_"+_pageRef).val()=="")
		{
			orgMsg+=startingValue;
		}
		if($("#interval_"+_pageRef).val()=="")
		{
			orgMsg+="\n"+ interval;
		}	
		if(orgMsg!="")
		{
			_showErrorMsg(orgMsg,missingElements,300,100);
			return;
		}
		_showProgressBar(true);
		var url = jQuery.contextPath+"/path/colTemplateMaintReport/reorganizeLinesOrder.action";
		params={};
		params["interval"] 			= $("#interval_"+_pageRef).val();
		params["startingLineValue"]	= $("#startingLineValue_"+_pageRef).val();
		params["valueToRemember"]		= valueToRemember;
		params["_pageRef"]=_pageRef;
		valueToRemember = 0;
		rowid 							= $("#detColGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		myObject 						= $("#detColGrid_"+_pageRef).jqGrid('getRowData',rowid);
		tempLineNb 						= myObject["colmnTmpltVO.LINE_NBR"];
		params["templateCode"]			= $("#CODE_"+_pageRef).val(); 	 
		$("#tabs_"+_pageRef).tabs('enable', 0)
		$("#tabs_"+_pageRef).tabs('enable', 1)
		var zSelected = $("#tabs_"+_pageRef).tabs('option', 'selected');
		globTmpCodeWithLnNb="0~0";
		if(zSelected=="1")
		{
		 	$("#tabs_"+_pageRef).tabs('select',0);
		}
		newUrl = jQuery.contextPath+ "/path/colTemplateMaintReport/openCriteria.action?tempCodeWithLineNb="+globTmpCodeWithLnNb+"&_pageRef="+_pageRef;
		$("#tabs_"+_pageRef).tabs( "url" , 0 , newUrl );
		$("#tabs_"+_pageRef).tabs( "load" , 0 );
		var collapseDiv = $("#inEditColTemplateDetailsDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
		emptyDetForm(1);											
		$.ajax({
 			url: url,
 			type: "POST",
 			data: params,				
 		    success: function(xml)
 		    {
				 if(xml["singleColumnDetail"]["newLineNumber"]!=null)
				 {
		 		 	$("#LINE_NBR_"+_pageRef).val(xml["singleColumnDetail"]["newLineNumber"]);	
		 		 	$("#LINE_NBR_"+_pageRef).attr('readonly',true);
		 		 }	
		 		 $("#numberAfter_"+_pageRef).val(1);
		 		 $("#detColGrid_"+_pageRef).trigger("reloadGrid");
				 $("#tabs_"+_pageRef).tabs('select',0);
				 var collapseDiv = $("#inEditColTemplateDetailsDiv_"+_pageRef+" > .collapsibleContainerTitle").get(0);
				 if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
				 {
				 	$(collapseDiv).trigger("click");
				 }
				 
				   	callDependency("LINE_NBR"+_pageRef+":singleColumnDetail.colmnTmpltVO.LINE_NBR",
	    					jQuery.contextPath+
	    					'/path/colTemplateMaintReport/col_applyRequiredFields.action',"colType:"+"AB"+",updates:"+"N","LINE_NBR","");
					enableExpressionControls();

				 $("#reorganizeLines_"+_pageRef).dialog( 'close' );
				 _showProgressBar(false);
 		   	}
	 	});
	}
	
	function rep_col_openReorganizeDialog()
	{
		$("#reorganizeLines_"+_pageRef).dialog('open');
	}
	
	function openCreateLikePopup()
	{
		$("#createLikeDialog_"+_pageRef).dialog('open');
	}
	
	function saveCreateLikeCol(obj)
	{
		if($("#newCode_"+_pageRef).val()=="")
		{
			_showErrorMsg(specifyCodeCol,error_msg_title,300,100)
			return false;
		}
		else
		{
		_showProgressBar(true)
		var zSrc=jQuery.contextPath+"/path/colTemplateMaintReport/countOfColumnTemplate.action?TEMPLATE_CODE="+ $("#newCode_"+_pageRef).val();
		var url = jQuery.contextPath+"/path/colTemplateMaintReport/saveCreateLike.action?";
					 params={};
					 params["newCode"] 			= $("#newCode_"+_pageRef).val();
					 params["_pageRef"]=_pageRef;
					 
		$.getJSON(zSrc, params, function( data2, status, xhr ) 
		{
			var currVal=data2['columnTemplateCount'];
	 			if(currVal=="1") 
	 			{
	 				_showErrorMsg(codeExistAlert);
					document.getElementById("CODE_"+_pageRef).focus();
					emptyMainForm();
		 		}
	 			else
	 			{
	 				var url = jQuery.contextPath+"/path/colTemplateMaintReport/saveCreateLike.action?";
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
										$("#colGrid_"+_pageRef).trigger("reloadGrid");
						 		    }
					 		}
				 			});
	 			}
	 			_showProgressBar(false);
	 			$(obj).dialog('close');
			}); 
		}
	}