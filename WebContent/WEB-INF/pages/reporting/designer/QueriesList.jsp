<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="selectQuery_msg_key_var" 		value="%{getEscText('selectQuery_msg_key')}"/>
<ps:set name="multiQrySel_msg_key_var" 		value="%{getEscText('multiQrySel_msg_key')}"/>

<html>
<head>

<style>
.ui-jqgrid tr.jqgrow td {white-space: normal}
</style>

<script type="text/javascript">
var selectQuery_msg_key 		= '<ps:property value="selectQuery_msg_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var multiQrySel_msg_key			= '<ps:property value="multiQrySel_msg_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

function loadQuery()
{
	var row = $("#queriesGrid").jqGrid('getGridParam','selarrrow');
	
	if(row.length == 0){
		_showErrorMsg(selectQuery_msg_key);		
	}
	else if(row.length > 1){
		_showErrorMsg(multiQrySel_msg_key);
	}
	else {
		var queryId = $("#queriesGrid").jqGrid('getCell', row, 'QUERY_ID'); 
		$.ajax({
			url: '${pageContext.request.contextPath}/path/designer/wizard_openQuery.action',
			type: "POST",
			data: "queryId="+queryId,				
		    success: function(json){	
				$("#syntax_"+_pageRef).attr("value",json['syntax']);
				$('#zDialog').dialog('close');
				//reload the grids of chooseFields section
		 		$("#allFieldsGrid").trigger("reloadGrid");
		 		$("#displFieldsGrid").trigger("reloadGrid");
			}
		});	
	}
	return false;
}

function deleteQuery()
{
	_showConfirmMsg("Are you sure you want to delete?", "Delete Query", "deleteSelQueries");
}

function deleteSelQueries(confirmation)
{
	if(confirmation)
	{
		var rows = $("#queriesGrid").jqGrid('getGridParam','selarrrow');		
		var arrId;
		if(rows)
		{		
			arrId = new Array(rows.length);	
			var j = 0;	 	
			jQuery.each(rows, function(i, val) {
				var data = $("#queriesGrid").jqGrid('getRowData',val);
				arrId[j] = data.QUERY_ID;
				j++;
			});             
	            
				$.ajax({
					url: '${pageContext.request.contextPath}/path/designer/queriesList_delete.action',
					type: "POST",
					data: ({queriesId : arrId}),				
				    success: function(xml){
						$("#queriesGrid").trigger("reloadGrid");
	    			}
				});
		}
	}
}

</script>
</head>

<body> 
<form>

  <div id=gridContentQrylst>
  	<table cellspacing="0" width="100%" cellpadding="0" height="250">
  		<tr>
  			<td width="100%" >
			  	<ps:url id="qryUrl" action="queriesList_loadGrid" namespace="/path/designer"/>   
			    <psjg:grid
			    	id="queriesGrid"
			    	dataType="json" 
			    	href="%{qryUrl}" 
			    	gridModel="gridModel"
			    	pager="true" 
			    	rowNum="10"
			    	filter="true" 
			    	delfunc="deleteQuery"
			    	rowList="5,10,15,20"
			    	viewrecords="true"
			    	navigator="true"			    		
			    	onErrorTopics="loadError"
			    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true,multipleSearch: true}"
			    	navigatorAdd="false" navigatorEdit="false"
			    	multiselect="true"
			     >
			      	<psjg:gridColumn name="QUERY_ID"   searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"     index="QUERY_ID"      colType="number"  title="%{getText('query.id')}"      sortable="true"   search="false" width="25"/>
			       	<psjg:gridColumn name="QUERY_NAME"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    index="QUERY_NAME"    colType="text"    title="%{getText('queryName')}"    sortable="true"   search="true"/>
			    </psjg:grid>
    		</td>
    	</tr>
    </table>
    <table align="right" cellspacing="10">
  	<tr>
  		<td>
  			<psj:submit button="true" onclick="return loadQuery();" type="button">
  				<ps:text name="reporting.ok"/>
  			</psj:submit>  		
  		</td>
  		<td>
  			<psj:submit button="true" onclick="return closeDialog();" type="button">
  				<ps:text name="reporting.cancel"/>
  			</psj:submit>  
  		</td>
  	</tr>
  </table>	
  </div>
		
</form>
</body>
<script type="text/javascript">
$("#queriesGrid").jqGrid('filterToolbar',{searchOnEnter : false});
</script>
</html>
