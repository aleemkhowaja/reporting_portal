<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="qryNameAlert_var" 		value="%{getEscText('qry.qryNameAlert')}"/>
<ps:set name="selectFieldAlert_var" 	value="%{getEscText('qry.selectFieldAlert')}"/>
<ps:set name="queryValConfrm_var" 	value="%{getEscText('reporting.queryValConfrm')}"/>
<ps:set name="procTakeTime_var" 	value="%{getEscText('reporting.procTakeTime')}"/>

<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
	
<script type="text/javascript">
var qryNameAlert 		= '<ps:property value="qryNameAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selectFieldAlert 	= '<ps:property value="selectFieldAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var	queryValConfrm		= '<ps:property value="queryValConfrm_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var	procTakeTime		= '<ps:property value="procTakeTime_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


var mode = '<%=(String)request.getParameter("mode")%>';
var openSqb = '<%=(String)request.getParameter("openSqb")%>';
var editSqb = '<%=(String)request.getAttribute("openSqb")%>';
var showQryName = '<%=(String)request.getParameter("showQryName")%>';
var fromQry = '<%=(String)request.getParameter("fromQry")%>';
var newQry = '<%=(String)request.getParameter("newQry")%>';

//the below code is moved from js to jsp since we need to call it each time we open the page (the js is chaching the data)
if(openSqb==null || openSqb=="null")
{
	openSqb=editSqb;
}

	$(document).ready(function() {
  		$.struts2_jquery.require("QueryDesigner.js" ,null, jQuery.contextPath+"/path/js/reporting/designer/");});
  		

</script>
</head>

<body> 
<table width="100%">
	<tr>
		<td valign="top">
			<%
				String openSqb =(String)request.getParameter("openSqb");
				String editSqb = (String)request.getAttribute("openSqb");
				if(openSqb==null)
				{
					openSqb=editSqb;
				}
			%>
			<ps:form id="sqlForm_${_pageRef}" name="sqlForm" namespace="/path/designer" >
				<psj:tabbedpanel id="tabs_${_pageRef}" onselect="checkSelectedQryTab">
				    <%
			    		  if(openSqb==null  || openSqb.equals("null"))
			    		  {
			    		%>
			    		
			    		<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=newquery&_pageRef=${_pageRef}" id="tab1" key="designer.newQuery">  </psj:tab>
				        <psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=expressions&_pageRef=${_pageRef}" id="tab2" key="designer.expressions">  </psj:tab>
   			    		<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=joins&_pageRef=${_pageRef}" id="tab3" key="designer.joinEntities">  </psj:tab>
				        <%
				    		} 
				    	%>
   			    		<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=arguments&_pageRef=${_pageRef}" id="tab4" key="designer.arguments">  </psj:tab>
					   <%
			    		  if(openSqb==null  || openSqb.equals("null"))
			    		  {
			    		%>
				        <psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=aggregate&_pageRef=${_pageRef}" id="tab5" key="designer.aggregate"> </psj:tab>
						<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=order&_pageRef=${_pageRef}" id="tab6" key="reporting.order"> </psj:tab>
						<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=conditions&_pageRef=${_pageRef}" id="tab7" key="designer.conditions"> </psj:tab>
						<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=having&_pageRef=${_pageRef}" id="tab8" key="designer.having"> </psj:tab>
						<%
				    		} 
				    	%>
						<psj:tab href="${pageContext.request.contextPath}/path/designer/queryDesign_loadTabContent.action?tab=syntax&_pageRef=${_pageRef}" id="tab9" key="designer.syntax"></psj:tab>
						
				</psj:tabbedpanel>
			</ps:form>	
		</td>
	</tr>
	<tr>
	   		<td colspan="2">
	   			<div  class="ui-layout-west-panel" id="queryBeingVal_<ps:property value="_pageRef" escapeHtml="true"/>"></div>
	   		</td>
	</tr>
</table>
<table cellspacing="20" align="right">
	<tr>
		<td valign="bottom">
			<psj:submit button="true" onclick="return okQry()" type="button">
				<ps:text name="reporting.ok"/>
			</psj:submit>
		</td>
		<td valign="bottom">
			<psj:submit button="true" onclick="return cancelTheQry()" type="button">
				<ps:text name="reporting.cancel"/>
			</psj:submit>
		</td>
	</tr>
</table>
</body>
</html>