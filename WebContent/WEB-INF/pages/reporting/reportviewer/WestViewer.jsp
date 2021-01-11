<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<table>
	<tr>
		<td class="ui-widget-header ui-state-focus ui-corner-all"
			style="border-style:none;"><ps:label>Folder Lookup</ps:label></td>
	</tr>
	<tr>
		<td
			style="width:10%;vertical-align:top;border-color: #79b7e7;border-radius: 5px;"><div
				id="reportViewerLeftMenuList_<ps:property value="_pageRef" escapeHtml="true"/>" style="overflow: hidden;">
				<ps:iterator id="var2" value="LeftMenuList">
					<ps:if test='${elementType == "File"}'>
						<img src="../reporting/images/NewFile.png" width="5%" />
					</ps:if>
					<ps:else>
						<img src="../reporting/images/folder.png" width="5%" />
					</ps:else>
					<ps:a href="#" style="text-decoration:none;" cssStyle="color: rgb(29, 89, 135);font: 10px"
						onclick="reportViewer_viewContent('%{elementPath}','%{elementName}','%{elementType}','%{countPath}')">
						<ps:property value="elementName" />
					</ps:a>
					<br />
				</ps:iterator>
			</div></td>
	</tr>
</table>