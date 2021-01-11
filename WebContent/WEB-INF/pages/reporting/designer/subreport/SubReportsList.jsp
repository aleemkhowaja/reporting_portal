<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="linkParamsTitle_var" 		value="%{getEscText('reporting.linkParams')}"/>
<ps:set name="procParamsTitle_var" 		value="%{getEscText('reporting.procParamsTitle')}"/>
<ps:set name="paramsOk_var" 			value="%{getEscText('reporting.ok')}"/>
<ps:set name="paramsCancel_var" 		value="%{getEscText('reporting.cancel')}"/>
<ps:set name="missingFe_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="expRepeated_var" 			value="%{getEscText('reporting.expRepeated')}"/>


<script type="text/javascript">

	var linkParamsTitle 		= '<ps:property value="linkParamsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var procParamsTitle 		= '<ps:property value="procParamsTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsOk 				= '<ps:property value="paramsOk_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var paramsCancel 			= '<ps:property value="paramsCancel_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var missingFe 				= '<ps:property value="missingFe_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var expRepeated 			= '<ps:property value="expRepeated_var"  escapeHtml="false" escapeJavaScript="true"/>'

	function addSubRep()
	{
			$("#subRepGridId_"+_pageRef).jqGrid('addInlineRow',{});
	}

	function deleteSubRep(rowid)
	{

		var rows = $("#subRepGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		rowObject =  $("#subRepGridId_"+_pageRef).jqGrid("getRowData",rows);
		var subRepId = rowObject["irpSubReportVO.REPORT_ID"];
		var subRepName = rowObject["SUB_REPORT_NAME"];
		var subRepExp =rowObject["irpSubReportVO.SUB_REPORT_EXPRESSION"];		
		var element = editor.document.getById(subRepExp);//var element = editor.document.getById(subRepId+"_"+subRepExp);
		if(element!=null)
		{
			removeSubRepImg(element,subRepId,subRepExp)
			//element.remove();
		}
		
		 $("#subRepGridId_"+_pageRef).jqGrid('deleteGridRow');
	}
	
	function removeSubRepImg( element, subRepId, subRepExp)
	{
		if(element!=null)
		{
			element.remove();
			var element = editor.document.getById(subRepExp);//var element = editor.document.getById(subRepId+"_"+subRepExp);
			removeSubRepImg(element,subRepId,subRepExp)
		}
		
	}
	
	function restrictSpChar(obj)
	{
	
		var sel_row_index = $("#subRepGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var cellData = $("#subRepGridId_"+_pageRef).jqGrid('getCell', sel_row_index, 'irpSubReportVO.SUB_REPORT_EXPRESSION');
		var lastChar = cellData.substr(cellData.length-1); //get the last character
		
		var regex = new RegExp("^[a-zA-Z0-9_-]+$");
		if (!regex.test(lastChar)) {
		$("#subRepGridId_"+_pageRef).jqGrid('setCellValue', sel_row_index, 'irpSubReportVO.SUB_REPORT_EXPRESSION',cellData.substring(0,cellData.length-1),'false');
	       return false;
	    } 
	    
	}


	function saveSubRepList()
	{
	    reportHasChanged(true);	    
		var jsonStringUpdates = $("#subRepGridId_"+_pageRef).jqGrid('getAllRows');
		$("#updatesSubRepList_"+_pageRef).val(jsonStringUpdates); 
		var rows = $("#subRepGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		rowObject =  $("#subRepGridId_"+_pageRef).jqGrid("getRowData",rows);
		var subRepId = rowObject["irpSubReportVO.REPORT_ID"];
		var subRepName = rowObject["SUB_REPORT_NAME"];
		var subRepExp =rowObject["irpSubReportVO.SUB_REPORT_EXPRESSION"];
		var val = $("#updatesSubRepList_"+_pageRef).val();
		 var url = $("#saveSubRepForm_"+_pageRef).attr("action");
		 url +="?_pageRef="+_pageRef;
		 myObject =  $("#saveSubRepForm_"+_pageRef).serialize();
		 $.post(url, myObject , function( param )
	 	 {
//			if(rows!=null)
//			{
//			//alert("innnn")
//				var img = editor.document.createElement( 'img',
//						{
//							attributes :
//							{
//								src:"${pageContext.request.contextPath}/ckeditor/plugins/subreports/image/subreport.png",
//								subRepProperties:subRepId+"_"+subRepExp,
//								title:subRepName,
//								style : "width: 30px; height: 30px;"	
//							}
//						});
//					editor.insertElement( img );
//			}
				
					 $('#subRepDialog').dialog('close');
					  $('#subRepDialog').html("");
	 	 });
	
	}
	
	function cancelSubRep()
	{
		$('#subRepDialog').dialog('close');
		$('#subRepDialog').html("");
	}
	
	function openSubRepParams(cellvalue, options, rowObject)
	{
		 return '<a href="#" onclick="openParamsSubRepList(\''+options.rowId+'\')">'+linkParamsTitle+'</a>';
	}
	
	function openParamsSubRepList(_rowid)
	{
	
		rowObject =  $("#subRepGridId_"+_pageRef).jqGrid("getRowData",_rowid);
		var params={};
		var jsonStringUpdates = $("#subRepGridId_"+_pageRef).jqGrid('getAllRows');
		params["subRepId"]=rowObject["irpSubReportVO.REPORT_ID"];
		params["subRepName"]=rowObject["SUB_REPORT_NAME"];
		params["subRepExp"]=rowObject["irpSubReportVO.SUB_REPORT_EXPRESSION"];
		 
		if(rowObject["irpSubReportVO.REPORT_ID"] != "" && rowObject["irpSubReportVO.SUB_REPORT_EXPRESSION"]!="")
		{
		
		 	var subRepExp = $("#subRepGridId_"+_pageRef).jqGrid('getCol','irpSubReportVO.SUB_REPORT_EXPRESSION');
			subRepExp.sort();
			var repeatedExpr=false;
			for(var i=0;i<subRepExp.length-1;i++)
			{
				if(subRepExp[i].toUpperCase()==subRepExp[i+1].toUpperCase())
				{
					repeatedExpr=true;
				}
			}
			if(repeatedExpr==false)
			{
				$("#subRepId_"+_pageRef).val(params["subRepId"]);
				$("#subRepExp_"+_pageRef).val(params["subRepExp"]);
				 
				dialogUrl= jQuery.contextPath+ "/path/designer/subrep_openSubRepParamsDialog.action?_pageRef="+_pageRef;
				dialogOptions={ autoOpen: false,
								height:300,
								title:"sub report parameters" ,
								width:1000 ,
								modal: true,
								close : function(ev, ui) 
								{
									$(this).dialog("destroy");
								},
								buttons: [{ text : paramsOk, click : saveHypLinkSubParams},
								          { text : paramsCancel, click :function(){$(this).dialog('close');}}
						          ]
				   }
				
				
					/*var	statusDiv = $("<div id='subRepParamGridDialog_"+_pageRef+"'/>");
					statusDiv.css("padding","0");
				    var theForm = $("#subRepForm_"+_pageRef);
				    statusDiv.insertAfter(theForm);
				    
				   $('#subRepParamGridDialog_'+_pageRef).load(dialogUrl,params, function(  )
				 	{
			    	  $('#subRepParamGridDialog_'+_pageRef).dialog(dialogOptions)
					  $('#subRepParamGridDialog_'+_pageRef).dialog('open');
					}) */
				$.post(dialogUrl ,params , function( param )
			 	{
		    	  $('#subRepParamGridDialog_'+_pageRef).html(param) ;
		    	  $('#subRepParamGridDialog_'+_pageRef).dialog(dialogOptions)
				  $('#subRepParamGridDialog_'+_pageRef).dialog('open');
				},"html");
			}
			else
			{
				_showErrorMsg(expRepeated,info_msg_title,350,120);
			}
		}
		else
		{
			_showErrorMsg(missingFe,info_msg_title,350,120);
		}
		
	}
	
	function saveHypLinkSubParams()
	{
		var jsonStringUpdates = $("#subRepParamGridId_"+_pageRef).jqGrid('getAllRows');
		$("#updatesSubRep_"+_pageRef).val(jsonStringUpdates); 
		 
		var val = $("#subRepId_"+_pageRef).val();
		var url = $("#subRepForm_"+_pageRef).attr("action");
		url +="?_pageRef="+_pageRef;
		myObject =  $("#subRepForm_"+_pageRef).serialize();
		$.post(url, myObject , function( param )
	 	 {
			 $('#subRepParamGridDialog_'+_pageRef).dialog('close');
		//	 $('#subRepParamGridDialog_'+_pageRef).html(""); stoped by patricia to prevent open empty Linkparams grid
	 	 });	
	}
		
	function insertSubRepImg()
	{
		var rows = $("#subRepGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		rowObject =  $("#subRepGridId_"+_pageRef).jqGrid("getRowData",rows);
		var subRepId = rowObject["irpSubReportVO.REPORT_ID"];
		var subRepName = rowObject["SUB_REPORT_NAME"];
		var subRepExp =rowObject["irpSubReportVO.SUB_REPORT_EXPRESSION"];

		if(rows!=null)
			{
				var img = editor.document.createElement( 'img',
						{
							attributes :
							{
								src:"${pageContext.request.contextPath}/ckeditor/plugins/subreports/image/subreport.png",
								//id:subRepId+"_"+subRepExp,//
								id:subRepExp,
								title:subRepExp,
								name:"subReportImg",
								expr:subRepExp,
								style : "width: 30px; height: 30px;"	
							}
						});
					editor.focus();
					range.select();
					editor.insertElement( img );
			}
				
		saveSubRepList();		

	}				

	$("#subRepGridId_"+_pageRef).subscribe('insertGridSubRepColor',function(event, data) 
	   {
			var jsonStringUpdates = $("#subRepGridId_"+_pageRef).jqGrid('getAllRows');
			var subRepId = $("#subRepGridId_"+_pageRef).jqGrid('getCol','irpSubReportVO.REPORT_ID');
			var subRepExp = $("#subRepGridId_"+_pageRef).jqGrid('getCol','irpSubReportVO.SUB_REPORT_EXPRESSION');
			for(var j=0;j<subRepExp.length;j++)
			{
				var element = editor.document.getById(subRepExp[j]);
				if(element!=null)
				{
						$("#subRepGridId_"+_pageRef).jqGrid('setRowData', j+1, false, { background: '#E0FFFF' });
				}
			}
	   });	
	   		
</script>
	<ps:url id="urlGrid" action="subrep_loadSubrepListUD" namespace="/path/designer">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="subRepGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="10"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	editinline="true"
		editurl="%{urlGrid}"
    	navigator="true"	
    	navigatorAdd="true" 
    	navigatorEdit="false" 
    	addfunc="addSubRep"
    	delfunc="deleteSubRep"
    	navigatorSearch="false "
		navigatorDelete="true"
		navigatorRefresh="false"
		ondblclick="insertSubRepImg()"
		onCompleteTopics="insertGridSubRepColor"
		
    	>
    	
    	<psjg:gridColumn name="irpSubReportVO.REPORT_ID" id="REPORT_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="REPORT_ID"  colType="number"  title="%{getText('subRep.repId')}" sortable="true"  width="25"  hidden="false" editable="false"/>

    	<psjg:gridColumn id="SUB_REPORT_NAME" index="SUB_REPORT_NAME" name="SUB_REPORT_NAME"  
			     	 	 editoptions="{ readonly: 'readonly'}"
			     	 	 title="%{getText('subRep.name')}" 
			     	 	 editable="true" 
			     	 	 sortable="false" 
			     	 	 colType="liveSearch" searchElement="REPORT_NAME"
			     	 	 dataAction="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action?_pageRef=${_pageRef}" 
			     	 	 resultList="REPORT_ID:irpSubReportVO.REPORT_ID" 
			     	 	 width="25"/> 
		<psjg:gridColumn name="PARENT_REPORT_ID" id="PARENT_REPORT_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="PARENT_REPORT_ID"  colType="number"  title="PARENT_REPORT_ID"      sortable="true"  width="25"  hidden="true" editable="false"/>
		<psjg:gridColumn name="irpSubReportVO.SUB_REPORT_EXPRESSION" id="irpSubReportVO.SUB_REPORT_EXPRESSION" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="irpSubReportVO.SUB_REPORT_EXPRESSION"  colType="text"  title="%{getText('subRep.subRepExp')}" sortable="true"  width="25"  hidden="false" editable="true" editoptions="{ dataEvents: [{ type: 'keyup', fn: function(e) { restrictSpChar(e)}} ]}"/>	     	 	  	 	 
    	<psjg:gridColumn  name="SUB_REP_PARAMS" formatter="openSubRepParams" index="SUB_REP_PARAMS" colType="text" title="%{getText('proc.params')}"  editable="false" width="25" align="center" sortable="false"/>
    </psjg:grid>
    
 <div id="subRepParamGridDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
 <table align="right" id="procButBarId_<ps:property value="_pageRef" escapeHtml="true"/>">
	 <tr>
		 <td >
		 		<psj:submit button="true" onclick="saveSubRepList()" >
					<ps:text name="reporting.ok"></ps:text>
				</psj:submit>
		 </td>
		 <td>
		 		<psj:submit button="true" onclick="cancelSubRep()" >
					<ps:text name="reporting.cancel"></ps:text>
				</psj:submit>
		 </td>
	 </tr>
 </table>   
  
 <ps:form id="subRepForm_${_pageRef}" action="subrep_putInSubRepParamMap" namespace="/path/designer">
	<ps:hidden name="updates" id="updatesSubRep_${_pageRef}"></ps:hidden>
	<ps:hidden name="subRepId" id="subRepId_${_pageRef}"></ps:hidden>
	<ps:hidden name="subRepExp" id="subRepExp_${_pageRef}"></ps:hidden>
</ps:form>   
    
 <ps:form id="saveSubRepForm_${_pageRef}" action="subrep_putInSubRepList" namespace="/path/designer">
	<ps:hidden name="updates" id="updatesSubRepList_${_pageRef}"></ps:hidden> 
</ps:form>     
