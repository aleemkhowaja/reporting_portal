<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<% /* needed to specify meta tag decorator since on wildfly it is not detected automatically*/ %>
<meta name="decorator" content="main"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/path/js/reporting/Common.js"></script>
<html>
<head>
<title><ps:text name="reporting_title_key"/></title>

<script type="text/javascript"  src="${pageContext.request.contextPath}/common/js/tabs/TabbedPanel.js"></script>
<script type="text/javascript">
	var RTL_DIRECTION = "${isRTL}";

	$(document).ready(function() {
		intializeMainTabs("mainTabs",{url:jQuery.contextPath+"/path/loadScreen?",reloadAlert:"<ps:text name='reload_contents_key'/>",closeAlert:"<ps:text name='close'/>"});
	});
</script>
</head>
<body>
<div id="mainTabs" style=" visibility: hidden;height: 100%; overflow: auto;"><%--Required for toolbar positioning--%>
	<ul></ul>
</div>
<jsp:include page="AppCommonTrans.jsp"></jsp:include>
</body>

</html>