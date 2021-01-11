<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %> 
<%/* selinuim error is to set a hidden div with specific id so that Selinuim will capture technical errors*/
if(response.getStatus() == 600 )
{%>
<div id="seleniumAutoError" style="display:none" ></div>
<%}
 /*needed to specify meta tag decorator since on JBOSS it is not detected automatically (it is needed in case there is error and the page is not redirected to LoginCompBr.jsp but to ErrorAction.jsp)*/
if("true".equals(request.getAttribute("enableDecorator")))
{%>
<meta name="decorator" content="logincompbr"/>
<%} %>

<H1 style="text-align: center;"> System Properties Configuration Verification </H1>


 <TABLE CELLPADDING="0" CELLSPACING="0" width="100%">
	<tr>
		<td colspan=4 align="center">
			<ps:actionmessage />
	    </td>
	 </tr>
 </TABLE>
 
<TABLE CELLPADDING="0" CELLSPACING="0" width="100%">
	<tr>
		<td colspan=4 CLASS="note" align="center" style="color: red;">
			<ps:actionerror/>
	    </td>
	 </tr>
 </TABLE>