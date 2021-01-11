<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<div id="reportViewerHeaderList_<ps:property value="_pageRef" escapeHtml="true"/>">
	<b> <ps:iterator id="var1" value="LinkPathList">
			<ps:a href="#"
				onclick="reportViewer_viewContent('%{elementPath}','%{elementName}','%{elementType}','%{countPath}')">
				<ps:property value="elementName" />
			</ps:a>
						<font style="color: rgb(29, 89, 135);font-size:8pt;">/</font>
					</ps:iterator>
	</b>
</div>