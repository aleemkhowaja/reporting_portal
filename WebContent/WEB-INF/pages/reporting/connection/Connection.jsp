<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 

<ps:set name="missingFeReq_var" 		value="%{getEscText('reporting.missing')}"/>
<ps:set name="diffPass_var" 			value="%{getEscText('connection.diffPass')}"/>
<ps:set name="portLength_var" 			value="%{getEscText('connection.portLength')}"/>
<ps:set name="saveConf_var" 			value="%{getEscText('connection.saveConf')}"/>
<ps:set name="saveTitle_var" 			value="%{getEscText('connection.saveTitle')}"/>
<ps:set name="conTest_var" 				value="%{getEscText('connection.conTest')}"/>
<ps:set name="dbConfirmPass_var" 		value="%{getEscText('connection.dbConfPass')}"/>
<ps:set name="delLinkedAppToCon_var" 	value="%{getEscText('connection.delLinkedAppToCon')}"/>


<script type="text/javascript">
var missingFeReq 		= '<ps:property value="missingFeReq_var"  escapeHtml="false" escapeJavaScript="true"/>'
var diffPass 			= '<ps:property value="diffPass_var"  escapeHtml="false" escapeJavaScript="true"/>'
var portLength 			= '<ps:property value="portLength_var"  escapeHtml="false" escapeJavaScript="true"/>'
var saveConf 			= '<ps:property value="saveConf_var"  escapeHtml="false" escapeJavaScript="true"/>'
var saveTitle 			= '<ps:property value="saveTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
var conTest 			= '<ps:property value="conTest_var"  escapeHtml="false" escapeJavaScript="true"/>'
var dbConfirmPass 		= '<ps:property value="dbConfirmPass_var"  escapeHtml="false" escapeJavaScript="true"/>'
var delLinkedAppToCon	= '<ps:property value="delLinkedAppToCon_var"  escapeHtml="false" escapeJavaScript="true"/>'
$(document).ready(function() 
{
	$.struts2_jquery.require("Connection.js" ,null, jQuery.contextPath+"/path/js/reporting/connection/");
	
	$("#connectionGridId_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	   {
			$("#filterConnForm #auditTrxNbr_"+_pageRef).val("")
	   });
});

	function editConnForm()
	{
			rowid = $("#connectionGridId_"+_pageRef).jqGrid('getGridParam','selrow');
			var connId = $("#connectionGridId_"+_pageRef).jqGrid('getCell', rowid, 'CONN_ID');	
			myObject = $("#connectionGridId_"+_pageRef).jqGrid('getRowData', rowid);
			var url =jQuery.contextPath+'/path/connection/connection_retrieveConnData';
			params = {};
			paramStr = JSON.stringify(myObject);
			paramStr = "{"+ "icVO:"+paramStr + "}";
			params["updates"] = paramStr;
			params["_pageRef"]=_pageRef;
			$.post(url, params , function( param )
	 		{
	 			$("#icDiv_"+_pageRef).html(param);
	 			$("#actionType_"+_pageRef).val("edit");
	 		},"html");
				
	}

	//BMO security and penetration testing, function added to overcome saving of password in browsers
	function rep_preparePassChrome()
	{
		var elem = document.getElementById('dbPassFake')
		var theParentNode = elem.parentNode;
		theParentNode.removeChild(elem);
		var elem = document.getElementById('dbConfPassFake')
		var theParentNode = elem.parentNode;
		theParentNode.removeChild(elem);
		
		$("#dbPass").after("<input type='text' class='textCompSize ui-state-focus ui-corner-all pwdinput' required='true' maxlength='30' id='dbPassFake' autocomplete='off'/>");
		$("#dbConfPass").after("<input type='text' class='textCompSize ui-state-focus ui-corner-all pwdinput' required='true' maxlength='30' id='dbConfPassFake' autocomplete='off'/>");
	}
</script>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:url id="urlGrid" action="connection_loadConnectionGrid" namespace="/path/connection">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="connectionGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true" 
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
    	rowNum="5"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	navigator="true"	
    	navigatorAdd="true" 
    	navigatorEdit="false"
    	ondblclick="editConnForm()" 
    	addfunc="addNewCon"
    	delfunc="deleteConRecord"
    	onCompleteTopics="emptyCrtTrx"
     >
      	<psjg:gridColumn name="CONN_ID"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="CONN_ID"      colType="number"  title="CONN_ID"      sortable="true"  width="25"  hidden="true"/>
       	<psjg:gridColumn name="CONN_DESC" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="CONN_DESC"    colType="text"    title="%{getText('connection.conDescription')}"    sortable="true" />
       	<psjg:gridColumn name="DBMS" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="DBMS"    colType="text"    title="%{getText('connection.dbms')}"    sortable="true" />
       	<psjg:gridColumn name="URL" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="URL"    colType="text"    title="%{getText('connection.host')}"    sortable="true" hidden="false"/>
   		<psjg:gridColumn name="DB_PASS" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="DB_PASS"    colType="text"    title="%{getText('connection.dbPass')}"    sortable="true" hidden="true"/>
   		<psjg:gridColumn name="USER_ID" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="USER_ID"    colType="text"    title="%{getText('connection.userId')}"    sortable="true" hidden="true"/>
   		
    </psjg:grid>
	<ps:collapsgroup id='sort1FltrCrt_${_pageRef}'>
		<ps:collapspanel id='icDiv_${_pageRef}' key="Details">
			<%@include file="ConnectionDetail.jsp"%>
		</ps:collapspanel>
	</ps:collapsgroup>
<script type="text/javascript"> 
    $("#connectionGridId_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>