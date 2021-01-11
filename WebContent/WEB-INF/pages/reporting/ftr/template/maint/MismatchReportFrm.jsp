<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>

		<ps:collapspanel id="misMatchDivId"  key="template.mismatchDetails">	
		<ps:form id="misMaintFrmId_${_pageRef}" name="misMaintFrmId" action="validateMis" namespace="/path/templateMaintReport" method="POST" useHiddenProps="true" validate="true">
			<table width="100%" >
					<tr> 
						<td width="5%" nowrap="nowrap"><ps:label key="template.template" for="lookuptxt_existingTemplatesLook_${_pageRef}"/></td>
						<td width="25%" nowrap="nowrap">
				<psj:livesearch 
						    		  id            ="existingTemplatesLook_${_pageRef}"
				                      name          ="ftrMissRepCO.ftrMissRepVO.TMPLT_CODE1" 
				                      mode          ="number"
				                      readOnlyMode  ="false"
				                      actionName    ="${pageContext.request.contextPath}/path/templateMaintReport/existingTemplatesLookup.action" 
				                      searchElement ="" 
				                      parameter		="templateCode:lookuptxt_existingTemplatesLook_${_pageRef},currentTemplate:templCode_${_pageRef}"
				                      resultList    ="glstmpltVO.CODE:lookuptxt_existingTemplatesLook_${_pageRef},glstmpltVO.BRIEF_NAME_ENG:tmpltName_${_pageRef}"
				                      dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyExistingTemplatesDependency.action"
				                      dependency    ="tmpltName_${_pageRef}:glstmpltVO.BRIEF_NAME_ENG,lookuptxt_existingTemplatesLook_${_pageRef}:glstmpltVO.CODE"
				                      afterDepEvent="emptyLineField()"
				                      >
				           </psj:livesearch>
						</td>
						<td width="25%" nowrap="nowrap">
							  <ps:textfield readonly="true"  name="ftrMissRepCO.TMPLT_NAME"  id="tmpltName_${_pageRef}"  ></ps:textfield>
						</td>		
						<td></td>				 
					</tr>
					<tr> 
						<td width="5%" nowrap="nowrap"><ps:label key="reporting.line" for="lookuptxt_relatedLinesLook_${_pageRef}"/></td>
						<td width="25%" nowrap="nowrap">
						  <psj:livesearch 
						    		  id            ="relatedLinesLook_${_pageRef}"
				                      name          ="ftrMissRepCO.ftrMissRepVO.SUB_LINE_NO" 
				                      mode          ="number"
				                      readOnlyMode  ="false"
				                      actionName    ="${pageContext.request.contextPath}/path/templateMaintReport/existingTemplateLinesLookup.action"
				                      searchElement =""
				                      paramList		="templateCode:lookuptxt_existingTemplatesLook_${_pageRef}"
				                      parameter		="lineNbr:lookuptxt_relatedLinesLook_${_pageRef},templateCode:lookuptxt_existingTemplatesLook_${_pageRef}"
				                      resultList    ="glstmpltVO.LINE_NBR:lookuptxt_relatedLinesLook_${_pageRef},glstmpltVO.BRIEF_NAME_ENG:lineName_${_pageRef}"
				                      dependencySrc ="${pageContext.request.contextPath}/path/templateMaintReport/applyExistingTemplateLinesDependency.action"
				                      dependency    ="lineName_${_pageRef}:glstmpltVO.BRIEF_NAME_ENG,lookuptxt_relatedLinesLook_${_pageRef}:glstmpltVO.LINE_NBR"
				                      >
				           </psj:livesearch>
						</td>
						<td width="25%" nowrap="nowrap">
							  <ps:textfield readonly="true"  name="ftrMissRepCO.LINE_NAME"  id="lineName_${_pageRef}" ></ps:textfield>
						</td>		
						<td></td>				 
					</tr>
					<tr>
						<td colspan="4">
							<ptt:toolBar id="misTool_${_pageRef}">
								<psj:submit   button="true" buttonIcon="ui-icon-disk" id="mismatchOK_${_pageRef}">
									<ps:label key="reporting.ok"></ps:label>
								</psj:submit>
			         		</ptt:toolBar> 
						</td>
					</tr>
					<ps:hidden name="ftrMissRepCO.concatKey" id="concatKey_${_pageRef}"></ps:hidden>
				</table>

		</ps:form>
</ps:collapspanel>
	<script>
		$("#misMaintFrmId_"+_pageRef).processAfterValid("saveMisButton",['edit',null]);	
		$("#mismatchOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
		document.getElementById("mismatchOK_"+_pageRef).disabled=true
	</script>