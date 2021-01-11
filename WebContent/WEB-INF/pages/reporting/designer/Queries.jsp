<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="types_key_var" 		value="%{getEscText('query.types_key')}"/>
<ps:set name="modify_key_var" 		value="%{getEscText('modify_key')}"/>
<ps:set name="selectQuery_msg_key_var" 		value="%{getEscText('selectQuery_msg_key')}"/>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
<style>
.ui-jqgrid tr.jqgrow td {white-space: normal}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
<script type="text/javascript">
var types_key 				= 	'<ps:property value="types_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var modify_key 				= 	'<ps:property value="modify_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectQuery_msg_key 	= 	'<ps:property value="selectQuery_msg_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


$(document).ready(function() {
	
 	$("#queriesTempGrid").subscribe('emptyQryTrx', function(event,data) 
	{
		 $("#qryListFrm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("")
	});
});

var templateQryId;

function addQuery()
{
	templateQryId = "";
	var dlg = $("#qryTypesDialg").dialog({
			  	title: types_key	 
	});

	//dlg.load("${pageContext.request.contextPath}/path/designer/queryDesign_createQuery.action")
	//   .dialog('open');   
	dlg.load("${pageContext.request.contextPath}/path/designer/queryDesign_chooseQryType.action").dialog('open');    
}

function editSelectedQuery()
{
	var row = $("#queriesTempGrid").jqGrid('getGridParam','selarrrow');
	
	if(row.length > 1){
		_showErrorMsg('<ps:text name="multiRowSel_key"/>');
	}
	else {
		templateQryId = $("#queriesTempGrid").jqGrid('getCell', row, 'QUERY_ID'); 
		
		var url = jQuery.contextPath+ "/path/designer/queriesList_retSelQueryType.action?queryId="+templateQryId;
		var params={};
	    $.getJSON(url, params, function( data2, status, xhr )
	 	{
	    	var qryType=data2['updates'];
	    	if(qryType=="qbe")
	    	{
	    		var dlg = $("#queriesDialg").dialog({
		  			title: modify_key	 
				});

  			   dlg.load("${pageContext.request.contextPath}/path/designer/queriesList_openQuery.action?queryId="+templateQryId+"&fromQry=true&_pageRef="+_pageRef).dialog('open');  
	    	}
	    	else
	    	{
	    		var dlg = $("#queriesDialg").dialog({
		  			title: modify_key 
				});

  			   dlg.load("${pageContext.request.contextPath}/path/designer/queriesList_openQuery.action?queryId="+templateQryId+"&openSqb=true&showQryName=true&fromQry=true&_pageRef="+_pageRef).dialog('open');  

		   	}
	 	});
		
	}
}

function deleteQuery()
{	
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteSelQueries",{},yes_confirm,no_confirm,300,100);
}

function deleteSelQueries(confirmation)
{
	if(confirmation)
	{
		var rows = $("#queriesTempGrid").jqGrid('getGridParam','selarrrow');		
		var arrId;
		if(rows)
		{		
			arrId = new Array(rows.length);	
			var j = 0;	 	
			jQuery.each(rows, function(i, val) {
				var data = $("#queriesTempGrid").jqGrid('getRowData',val);
				arrId[j] = data.QUERY_ID;
				j++;
			});             
	            
				$.ajax({
					url: '${pageContext.request.contextPath}/path/designer/queriesList_delete.action',
					type: "POST",
					data: ({queriesId : arrId,_pageRef : _pageRef}),
					dataType:"json",				
				    success: function(xml){
				    
				    if(typeof xml["_error"] == "undefined" || xml["_error"] == null)
		   			{
						$("#queriesTempGrid").trigger("reloadGrid");
						$("#qryListFrm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("")
		   			}
		   		
	    			}
				});
		}
	}
}


function qryPreAuditCall()
{

	var row = $("#queriesTempGrid").jqGrid('getGridParam','selarrrow');
	if(row.length > 1 || row.length==0)
	{
	    _showErrorMsg(selectQuery_msg_key);
		return false;
	}
	else 
	{
		var zRowId = $("#queriesTempGrid").jqGrid('getGridParam','selrow');
		var url = jQuery.contextPath+ "/path/designer/queriesList_retTrxNb";
		myObject = $("#queriesTempGrid").jqGrid('getRowData',zRowId);
		params = {};
		paramStr = JSON.stringify(myObject)
		paramStr = "{"+ "vo:"+paramStr + "}";
		params["updates"] = paramStr;
		params["_pageRef"]=_pageRef
		
		$.post(url, params , function( param )
		{
			 //setTrxAudit
			 var auditTrxNbr_val=param["auditTrxNbr"];
 			 $("#qryListFrm_"+_pageRef+" #auditTrxNbr_"+_pageRef).val(auditTrxNbr_val)
 			 //open audit dialog
         	 $.__overlaybox( {
			 height : 600,
			 href : jQuery.contextPath
					+ "/path/audit/audit_showAuditReport?progRef="
					+ _pageRef + "&trxNbr="
					+ $("#auditTrxNbr_" + _pageRef).val()
			});
		});
		return false;
	}
}

$(document).ready(function () 
{
	$("#queriesDialg").subscribe('closeQryDialogClear', function(event,data) {
			$.ajax({
				url: "${pageContext.request.contextPath}/path/designer/queriesList_clearSession.action"
			});
	});			 
});
</script>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
  	<ps:collapsgroup id="sort1Qries_${_pageRef}">
			<ps:collapspanel id="qriesDiv_${_pageRef}"  key="designer.queries">

			<ps:url id="qryUrl" action="queriesList_loadGrid" namespace="/path/designer"/>   
			<psjg:grid
				id="queriesTempGrid"
				dataType="json" 
				href="%{qryUrl}" 
				gridModel="gridModel"
				pager="true" 
				rowNum="20"
				addfunc="addQuery" editfunc="editSelectedQuery"	delfunc="deleteQuery"
				rowList="5,10,15,20"
				viewrecords="true"
				navigator="true"			    		
				onErrorTopics="loadError"
				onCompleteTopics="emptyQryTrx"
				navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true,multipleSearch: true}"
			 	multiselect="true"
			>
				<psjg:gridColumn name="QUERY_ID"     searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"    index="QUERY_ID"      colType="number"  title="%{getText('query.id')}"      sortable="true"   search="true" width="25"/>
			    <psjg:gridColumn name="QUERY_NAME"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    index="QUERY_NAME"    colType="text"    title="%{getText('queryName')}"    sortable="true"   search="true"/>
			</psjg:grid>
</ps:collapspanel>
</ps:collapsgroup>

<div class="clearfix">	
	<psj:dialog
		id="queriesDialg" 
	    autoOpen="false"
	    dataType="html"
	    modal="true"  
	    closeOnEscape="false"
	    width="1100"
		height="500"
		onCloseTopics="closeQryDialogClear" 
	/>    
</div>

<div class="clearfix">	
	<psj:dialog
		id="qryTypesDialg" 
	    autoOpen="false"
	    dataType="html"
	    modal="true"  
	    closeOnEscape="false"
	    width="350"
		height="100"
	/>    
</div>

<ps:form id="qryListFrm_${_pageRef}" action="queriesList_openTemplateQueries" namespace="/path/designer" useHiddenProps="true">
			<ps:hidden name="updates" id="updateQryList_${_pageRef}"></ps:hidden>
			<ps:hidden id="auditPreCall_${_pageRef}" value="qryPreAuditCall()"></ps:hidden>
</ps:form>

<script type="text/javascript"> 
    $("#queriesTempGrid").jqGrid('filterToolbar',{searchOnEnter : true});
</script>