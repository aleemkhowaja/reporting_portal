<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:form id="entDetFormId_${_pageRef}" action="entities_validateEntFields" useHiddenProps="true" namespace="/path/entitiesMaint" method="POST">
	<div align="left">
		<table class="headerPortionContent ui-widget-content" align="left" width="100%" >
			<tr>
				<td width="60"><ps:label value="%{getText('entities.entName')}" /></td>
				
				<td width="150">
					
					<psj:livesearch
						actionName="${pageContext.request.contextPath}/path/entitiesMaint/entities_constructEntLkp"
						id="entityName_${_pageRef}" 
						name="entitiesVO.ENTITY_DB_NAME"
						resultList="OBJECT_DB_NAME:lookuptxt_entityName_${_pageRef}" 
						searchElement="" 
						mode="text"
						onOk="entitiesOk()">
					</psj:livesearch>
					
				</td>
				
				
				
				<td width="10"></td>
				<td width="40"><ps:label value="%{getText('entities.Alias')}" /></td>
				<td width="200">
					<ps:textfield name="entitiesVO.ENTITY_ALIAS" id="entitiesAlias_${_pageRef}" maxlength="50">
					</ps:textfield>
				</td>
				
				<td>
					<ps:hidden id="dateUpdated_${_pageRef}" name="entitiesVO.DATE_UPDATED"></ps:hidden>
				</td>
			</tr>
		</table>
	</div>
</ps:form>