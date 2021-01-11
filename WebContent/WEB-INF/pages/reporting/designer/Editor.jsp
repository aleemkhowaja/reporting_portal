<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="repUpdateAccessAlert_var" 		value="%{getEscText('reporting.repUpdateAccessAlert')}"/>
<ps:set name="hasHypNote_var" 					value="%{getEscText('reporting.hasHypNote')}"/>
<ps:set name="saveRepBefore_var" 				value="%{getEscText('reporting.saveRepBefore')}"/>
<ps:set name="desRepSaveAsAccessAlert_var" 		value="%{getEscText('reporting.repSaveAsAccessAlert')}"/>
<ps:set name="repUsedInSchedEditor_var" 		value="%{getEscText('reporting.repUsedInSched')}"/>
<ps:set name="empRepClt_var" 				value="%{getEscText('upDown.empRepClt')}"/>

<head>
<script type="text/javascript">
	var CKEDITOR_BASEPATH 			= "${pageContext.request.contextPath}/ckeditor/";
	var repUpdateAccessAlert 		= '<ps:property value="repUpdateAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var hasHypNote 					= '<ps:property value="hasHypNote_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var saveRepBefore 				= '<ps:property value="saveRepBefore_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var desRepSaveAsAccessAlert 	= '<ps:property value="desRepSaveAsAccessAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var repUsedInSchedEditor		= '<ps:property value="repUsedInSchedEditor_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var empRepClt 					= '<ps:property value="empRepClt_var"  escapeHtml="false" escapeJavaScript="true"/>'
	
</script>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/path/js/reporting/designer/Editor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/path/js/reporting/designer/tabletools.js"></script>
<script>
var editor = CKEDITOR.instances.editor1;
function submitReport(action)
{
	_showProgressBar(true);
	
	//set x,y,width and length for the elements in the editor		
	setHtmlElemProperties();
	var content = encodeURIComponent(editor.getData());
	var pageWidth = editor.document.getById("root").getAttribute("width_jr");
	//get the sub report ids from ckEditor
	var arrSubRepImg = editor.document.$.getElementsByTagName("img");
	for (i = 0; i < arrSubRepImg.length; i++) {
	   var id = arrSubRepImg[i].getAttribute('id');
	}
	
	if (content.length > 0)
	{
		//check if the user has access to save the report
		if(action=="save" || action=="saveAs")
		{
			//check diff between subRep in ckEditor and subRep grid
			var arrSubRepImg = editor.document.$.getElementsByTagName("img");
		    var params ={};
			var subRepImgIds="";
			var url= "${pageContext.request.contextPath}/path/designer/subrep_checkUsedSubRep.action"
			for (i = 0; i < arrSubRepImg.length; i++) {
			   var id = arrSubRepImg[i].getAttribute('id');
			   subRepImgIds=subRepImgIds+","+id;
			}
			
			params["subRepImgIds"]=subRepImgIds;
		  $.post(url, params , function( param )
		 	{
		 	 var notUseSubRep = param["notUseSubRep"]
			  if(param["notUseSubRep"]=="yes")
			  {
			  	  _showConfirmMsg("sub report created but not used it will be rejected if you proceed","sub rep", function(confirmcChoice, theArgs){
		           if(confirmcChoice)
		           {
		           	   _showProgressBar(true);
           	           	   var params={};
				           var url= "${pageContext.request.contextPath}/path/designer/subrep_checkUsedSubRep.action"
				           params["deleteNotUseSubRep"]="yes";
				           params["subRepImgIds"]=subRepImgIds;
				     	   $.post(url, params , function( param )
				 		   {
				 		  	 var haveClt=param["haveClt"];
					 		   if(action=="save")
			           	  	   {
					 		  		var url= "${pageContext.request.contextPath}/path/designer/reportsList_checkUpdateReportAccess.action"
								    var params ={};
								    params["updates2"] = "M";//update
								    $.post(url, params , function( param )
								 	{
									 	var isAccess=param["accessStr"];
									 	var isEditable = param["editableStr"];
								 		if(isAccess=="N")
								 		{
								 			_showErrorMsg(repUpdateAccessAlert,error_msg_title,350,120);	
								 			_showProgressBar(false);
								 			return;
								 		}
										else if (isEditable == "N")
										{
											_showErrorMsg(repCantSaveUnmodifiable, error_msg_title, 350, 120);
											_showProgressBar(false);
											return;
										}
										else if(haveClt == 0)
										{
											_showErrorMsg(empRepClt, info_msg_title, 300, 100);
											_showProgressBar(false);
								 			return;
										}
								 		else
								 		{
								 			checkForRepSchedUsage(action,pageWidth);
								 		}
							 		});
							  	}
				           	   //save as
				           	   else
				           	   {
				           	   		submitReportSuccess(action,pageWidth);
				           	   }
				          }); 
		           }
			      }, {},null,null,300,100);
			  	  _showProgressBar(false);
			  }
			  
			  else
			  {
			  		var url= "${pageContext.request.contextPath}/path/designer/reportsList_checkUpdateReportAccess.action"
				    var params ={};
				    params["updates2"] = "M";//update
				    $.post(url, params , function( param )
				 	{
					 	var isAccess=param["accessStr"];
					 	var isEditable = param["editableStr"];
					 	var haveClt=param["haveClt"];
				 		if(isAccess=="N")
				 		{
				 			_showErrorMsg(repUpdateAccessAlert,error_msg_title,350,120);	
				 			_showProgressBar(false);
				 			return;
				 		}
			 			else if (isEditable == "N")
						{
							_showErrorMsg(repCantSaveUnmodifiable, error_msg_title, 350, 120);
							_showProgressBar(false);
							return;
						}
						else if(haveClt == 0)
						{
							_showErrorMsg(empRepClt, info_msg_title, 300, 100);
							_showProgressBar(false);
				 			return;
						}
				 		else
				 		{
				 			checkForRepSchedUsage(action,pageWidth);
				 		}
			 		});
			  }
			 
	 		});

		}
		else
		{
			submitReportSuccess(action,pageWidth);
		}
	}
	
	return false;
}

function checkForRepSchedUsage(action,pageWidth)
{
	var url = "${pageContext.request.contextPath}/path/designer/reportsList_retSchedUsage.action?_pageRef="+_pageRef;
	var myObject = {};
	$.post(url, myObject, function(param1) 
	{
		var isSchedRep = param1['isSchedRep'];
		if (isSchedRep == "1")//if the report used in sched
		{
				_showProgressBar(false);
				_showConfirmMsg(repUsedInSchedEditor, warning_msg_title, function(confirmeChoice, theArgss)
				{
					if (confirmeChoice) 
					{
					_showProgressBar(true);
						submitReportSuccess(action,pageWidth)
					}
				}, {}, yes_confirm, no_confirm, 400, 100);
		}
		else
		{
			submitReportSuccess(action,pageWidth)
		}
	});
			
}

function submitReportSuccess(action,pageWidth)
{
	if(action=="saveAs")
	{
		action="save";
	}
	var content = encodeURIComponent(editor.getData());
	$.ajax(
	{
		type: "POST",
		url: '${pageContext.request.contextPath}/path/designer/reportDesign_'+action+'.action?_pageRef='+_pageRef,
		dataType:"json",
		data : "content="+content+"&pageWidth="+pageWidth,
		error: function(response)
		{
			_showProgressBar(false);
		},
		success: function(response)
		{
			_showProgressBar(false);
			//clear the report changes
			reportHasChanged(false);
			 CKEDITOR.instances.editor1.resetDirty();
			if(action == "preview")
				$('#dynPrevDialog').dialog('open');	
			else{
				$("#reportId").attr("value",response['reportCO']['REPORT_ID']);
				//check if the report has a hyperlink then show a notification error to the user
				var url1 ="${pageContext.request.contextPath}/path/designer/reportsList_checkIfHasHyperlink.action?_pageRef="+_pageRef; 
				var myObject1 ={};
				$.post(url1, myObject1 , function( param1 )
				 {
					var hasHyp=param1['updates'];
					if(hasHyp=="1")//if the report has hyperlink
					{
						_showErrorMsg(response['message']+"\n\n "+hasHypNote,info_msg_title);	
					}
					else
					{
					_showErrorMsg(response['message'],info_msg_title,300,150);
					}
					//reload the menu
				 ddaccordion.initRoot("appMenu","generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",false);
				});
			}
		}
	});   
}

var obj = new Object();
var zHtml = "";
var enclosedNode;
//function called on leaf node click event in queries menu
function insertField(feName, labelAlias, fieldType, qryIndex, layout)
{
	var pattern = "";
	if( fieldType == "java.util.Date" )
		pattern = "pattern=\"dd/MM/yyyy\"";
	
	zHtml = "<input field=\"true\" name=\""+feName+"\"  type=\"text\" readOnly=\"true\" value=\"$F{"+labelAlias+"}\" class=\""+fieldType+"\" "+pattern+" style=\"font-size: 9px;\"/>";
	if(layout == 1)
	{ 
		editor.focus(); // Without this selection will be null on IE.
		enclosedNode = editor.getSelection().getStartElement();	
		var parentFoot = enclosedNode.hasAscendant( 'tfoot', 1 );
		if(parentFoot)
		{
			obj.expression = labelAlias;
			obj.qryIndex = qryIndex;
			obj.varClass = fieldType;
			obj.feName = feName;
	
			$("#variableDialog").load("${pageContext.request.contextPath}/path/designer/reportDesign_chooseValueType.action?fieldClass="+fieldType)
			   .dialog('open');
		}
		else{
			editor.insertHtml( zHtml );
		}	
	}
	else {
		editor.insertHtml( zHtml );
	}
}

function insertParameter(argName, argType)
{
	var pattern = "";
	if( argType == "java.util.Date" )
		pattern = "pattern=\"dd/MM/yyyy\"";
	
	zHtml = "<input param=\"true\" name=\""+argName+"\" type=\"text\" readOnly=\"true\" value=\"$P{"+argName+"}\" class=\""+argType+"\" "+pattern+" style=\"font-size: 9px;\"/>";
	editor.insertHtml( zHtml );
}

$(document).ready(function() {

	$('#variableDialog').subscribe('insertVariable', function(event,ui) {
		editor.insertHtml( zHtml );
	});
		
	$.subscribe('dynPrevClosed', function(event,data) 
	{
		$.ajax({url: '${pageContext.request.contextPath}/path/designer/reportDesign_deleteHtmlFile.action'});
	});
	
	
/*  $(document).bind("dragstart", function(event) {
    event.preventDefault();
  });*/
	

});

</script>
</head>

<div class="clearfix">
	<ps:url id="dynPrevUrl"  namespace="/path/designer" action="reportDesign_openDynPreview">
	<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="dynPrevDialog" 
		href="%{dynPrevUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="1200"
	    height="800"
	    onCloseTopics="dynPrevClosed" title=""
	/>    
</div>


<ps:form namespace="/path/designer" action="reportDesign" onsubmit="return submitReport();">
	
	
	<div id="queriesDiv" class="" style="padding:0px;">
						
	</div>
	
	<div id="editorDiv" class="inner-center">
		<textarea id="editor1" name="editor1">
			<table id="root" border="0" cellpadding="0" cellspacing="0" width="595" align="center">
				<tbody id="tbody" style="font-size: 9px;">
					<tr id="root_title" height="75">
						<td style="BACKGROUND: url(${pageContext.request.contextPath}/ckeditor/images/title.JPG) no-repeat center" valign="top">
							<p>&nbsp;</p>
						</td>
					</tr>
					<tr id="root_detail" height="165">
						<td style="BACKGROUND: url(${pageContext.request.contextPath}/ckeditor/images/detail.JPG) no-repeat center" valign="top">
							<p>&nbsp;</p>
						</td>
					</tr>
					<tr id="root_pageFooter" height="60">
						<td style="BACKGROUND: url(${pageContext.request.contextPath}/ckeditor/images/footer.JPG) no-repeat center" valign="top">
							<p>&nbsp;</p>
						</td>
					</tr>
				</tbody>
			</table>
		</textarea>
	</div>
	
</ps:form>
