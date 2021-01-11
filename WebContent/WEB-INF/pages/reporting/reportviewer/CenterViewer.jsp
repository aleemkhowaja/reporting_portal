<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<table style="width: 100%;">
	<tr>
		<td class="ui-widget-header ui-state-focus ui-corner-all"
			style="border-style:none;"><ps:label>Html Preview</ps:label></td>
	</tr>
	<ps:if test='${folderNotExist != null}'>
		<tr>
			<td align="center">
				<br /> <br /> <br /> <br /> <br />
						<ps:label name="folderNotExist" id="folderNotExist${_pageRef}" cssStyle="color:#f53546;font: 10px" cssClass="boldLabel"></ps:label>
			</td>
		</tr>
	</ps:if>
	<tr>
		<td style="width:90%;border-color: #79b7e7;border-radius: 5px"><div
				id="reportViewerLoadHtml_<ps:property value="_pageRef" escapeHtml="true"/>"
				style="height: 500px;max-width: 100%;overflow: auto;"></div></td>
	</tr>
</table>