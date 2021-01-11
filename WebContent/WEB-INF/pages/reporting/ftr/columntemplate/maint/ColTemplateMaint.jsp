<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 

<ps:form id="frmEditColTemplates" action="saveTemplate" namespace="/path/colTemplateMaintReport" useHiddenProps="true">
	<ps:hidden name="detGridMode" id="detGridMode" value="add"></ps:hidden>
		<table class="headerPortionContent ui-widget-content" >
			<tr>
				<td nowrap width="5%"><ps:label value="%{getText('reporting.code')}" /></td>
				<td nowrap>
					<div style="width: 60px;">
						<ps:textfield name="singleColumn.CODE" id="CODE_${_pageRef}" mode="number" nbFormat="####" 
						maxlength="4" minValue="0" onchange="templateCodeChanged(0,this)" readonly="true"></ps:textfield>
					</div>
				</td>
			</tr>
			<tr>
				<td nowrap width="5%"><ps:label value="%{getText('reporting.briefNameEnglish')}" /></td>
				<td nowrap>
					<div style="width: 500px;">
						<ps:textfield name="singleColumn.BRIEF_NAME_ENG" id="briefNameEng_${_pageRef}"></ps:textfield>
					</div>
				</td>
			</tr>
			<tr>
				<td nowrap width="5%"><ps:label value="%{getText('reporting.briefNameArab')}"/></td>
				<td nowrap>
					<div style="width: 500px;">
						<ps:textfield name="singleColumn.BRIEF_NAME_ARAB" id="briefNameArab_${_pageRef}" onlyArabic="true"></ps:textfield>
					</div>
				</td>
			</tr>
		</table>

		<table>
			<tr>
				<td nowrap><ps:label value="%{getText('template.paperSize')}" /></td>
				<td nowrap></td>
				<td nowrap><ps:label value="%{getText('designer.orientation')}" /></td>
				<td nowrap></td>
			</tr>
			<tr>
				<td nowrap></td>
				<td nowrap>
					<ps:radio id="PRINT_PAPER_SIZE_${_pageRef}" list="paperSizeList" listKey="VALUE_CODE" listValue="VALUE_DESC"
						name="singleColumn.PRINT_PAPER_SIZE">
					</ps:radio>
				</td>
				<td nowrap></td>
				<td nowrap>
					<ps:radio id="PRINT_PAPER_ORIENTATION_${_pageRef}" list="paperOrientationList"  listKey="VALUE_CODE" 
					listValue="VALUE_DESC" name="singleColumn.PRINT_PAPER_ORIENTATION">
					</ps:radio>
				</td>
			</tr>
			<tr>
				<td nowrap></td>
				<td nowrap></td>
				<td nowrap></td>
				<td nowrap></td>
			</tr>
			<tr>
				<td colspan="4">
				<span id="additionalButtons_<ps:property value="_pageRef" escapeHtml="true"/>" cssStyle="">
					<psj:submit type="button" button="true" onclick="openCreateLikePopup()" cssStyle="width:25%">
							<ps:text name="template.createLike" />
					</psj:submit>
				</span>
				</td>
			</tr>
		</table>
</ps:form>