<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 

<ps:set name="missingRequired_var" 				value="%{getEscText('reporting.missing')}"/>

<script type="text/javascript">
var missingRequired 			= '<ps:property value="missingRequired_var"  escapeHtml="false" escapeJavaScript="true"/>'	
$(document).ready(function() 
{
	$.struts2_jquery.require("AppConnection.js" ,null, jQuery.contextPath+"/path/js/reporting/connection/");
	
	$("#appConnectionGridId_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	   {
			$("#appConnForm #auditTrxNbr_"+_pageRef).val("")
	   });
});
	function editAppConForm()
	{
			rowid = 	$("#appConnectionGridId_"+_pageRef).jqGrid('getGridParam','selrow');
			var conId = $("#appConnectionGridId_"+_pageRef).jqGrid('getCell', rowid, 'irpAppConnectionVO.CON_ID');	
			var appName = $("#appConnectionGridId_"+_pageRef).jqGrid('getCell', rowid, 'irpAppConnectionVO.APP_NAME');	
			
			myObject = $("#appConnectionGridId_"+_pageRef).jqGrid('getRowData', rowid);
			var url =jQuery.contextPath+'/path/appConnection/appConnection_retrieveAppConnData.action?actionType=edit&appName='+appName+'&conId='+conId;
			params = {};
			paramStr = JSON.stringify(myObject);
			paramStr = "{"+ "icAppVO:"+paramStr + "}";
			params["updates"] = paramStr;
			params["_pageRef"]=_pageRef;
			$.post(url, params , function( param )
	 		{
	 			$("#appIcDiv_"+_pageRef).html(param);
	 			$("#actionType_"+_pageRef).val("edit");
	 		},"html");
	}

</script>


<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:url id="urlGrid" action="appConnection_loadAppConnectionGrid" namespace="/path/appConnection">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="appConnectionGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="5"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	navigator="true"	
    	navigatorAdd="true" 
    	navigatorEdit="false"
    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
    	addfunc="addNewAppCon"
    	ondblclick="editAppConForm()" 
    	onCompleteTopics="emptyCrtTrx"
    	delfunc="deleteAppConRecord"

     >
      	<psjg:gridColumn name="irpAppConnectionVO.CON_ID"  id="CON_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="CON_ID"      colType="number"  title="%{getText('connection.conId')}"      sortable="true"  width="25"  hidden="false"/>
       	<psjg:gridColumn name="irpAppConnectionVO.APP_NAME"  id="APP_NAME" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="APP_NAME"    colType="text"    title="%{getText('connection.appName')}"    sortable="true" width="25" hidden="true"/>
       	<psjg:gridColumn name="LONG_DESC" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="LONG_DESC"    colType="text"    title="%{getText('connection.appName')}"    sortable="true" />
    </psjg:grid>
		<ps:collapsgroup id="sort1FltrCrt">
			<ps:collapspanel id='appIcDiv_${_pageRef}'  key="Details">
	 <%@include file="AppConnectionDetail.jsp"%>
</ps:collapspanel>       
</ps:collapsgroup>
<script type="text/javascript"> 
    $("#appConnectionGridId_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>