<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<html>
	<script type="text/javascript">
	 function qryDesignLoad()
	 {
	 	templateQryId = "";
		var dlg = $("#queriesDialg").dialog({
				  	title: '<ps:text name="query.new.design_key"/>'	 
		});

		dlg.load("${pageContext.request.contextPath}/path/designer/queryDesign_createQuery.action?fromQry=true&_pageRef="+_pageRef+"&newQry=true").dialog('open');   
		return false; 
	 }

	 function qrySyntaxLoad()
	 {
		templateQryId = "";
		var dlg = $("#queriesDialg").dialog({
			  title: '<ps:text name="query.new.syntax_key"/>'	 
		});

		dlg.load("${pageContext.request.contextPath}/path/designer/queryDesign_createStaticQuery.action?openSqb=true&showQryName=true&fromQry=true&_pageRef="+_pageRef).dialog('open');     
		return false; 
	 }

	</script>
	<body>
		<table width="100%" height="100%">
			<tr align="center">
				<td>
					<div class="headerPortion">
						<psj:submit button="true" onclick="return qryDesignLoad()" type="button" buttonIcon="ui-icon-newwin">
							<ps:text name="reporting.qryDesign"/>
						</psj:submit>
					</div>
				</td>
				<td>
					<div class="headerPortion">
						<psj:submit button="true" onclick="return qrySyntaxLoad()" type="button" buttonIcon="ui-icon-newwin">
							<ps:text name="reporting.qrySyntax"/>
						</psj:submit>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>


