<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<html>
<head>
<script type="text/javascript">

$(document).ready(function() {
	var menu = '<%=(String)request.getParameter("menu")%>';
	
	$('#testDiv_'+menu).load("${pageContext.request.contextPath}/path/reportsRet/dynRepParamsAction_loadDynParam.action?menu="+menu);
});

</script>
</head>
<body > 
	<div id="testDiv_<%=(String)request.getParameter("menu")%>">
	</div>
</body>
</html>
