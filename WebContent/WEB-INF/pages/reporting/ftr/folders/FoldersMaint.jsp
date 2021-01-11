<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	$("#foldersDetFormId_"+_pageRef).processAfterValid("newFolder");
	  //prevent the user from entering special characters
	  preventSpecialCharacters("foldersRef_"+_pageRef);	 
});
</script>

<ps:form id="foldersDetFormId_${_pageRef}" validate="true"
	name="foldersDetFormId"  useHiddenProps="true"
	>
	<ps:hidden name="foldersCO.FOLDER_CODE" id="foldersCode_${_pageRef}"></ps:hidden>
	<div align="left">
	<table class="headerPortionContent ui-widget-content" align="left" width="100%">
		<tr>
			<td><ps:label key="reporting.descEng"  for="foldersDescEng_${_pageRef}" /></td>
			<td><ps:textfield name="foldersCO.BRIEF_NAME_ENG"
				id="foldersDescEng_${_pageRef}" maxlength="40" ></ps:textfield></td>
		</tr>
		<tr>
			<td><ps:label key="reporting.descAr"  for="foldersDescAr_${_pageRef}"/></td>
			<td><ps:textfield name="foldersCO.BRIEF_NAME_ARAB"
				id="foldersDescAr_${_pageRef}" maxlength="40"  onlyArabic="true"></ps:textfield></td>
		</tr>
		
					 <tr>
			    	<td>
						<ps:label key="reporting.applicationName" for="lookuptxt_appName_${_pageRef}" />
					</td>
					<td nowrap="nowrap" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80px"> 
							<psj:livesearch 
								actionName="${pageContext.request.contextPath}/pathdesktop/ApplicationsLookup_constructLookup.action?lang_spec=1"
								name="foldersCO.APP_NAME"
								paramList="webAppsOnly:webAppsOnly_${_pageRef}"
								id="appName_${_pageRef}"
						        searchElement="APP_NAME" 
						        autoSearch="true"
						        onOk="emptyUpDownPRefLkp()" 
						        readOnlyMode="true"
					        />	
							</td>
						</tr>
					</table>
					 </td>
			    </tr>
			    
		<tr>
			<td><ps:label key="progRef"  for="foldersRef_${_pageRef}"/></td>
			<td><ps:textfield name="foldersCO.FOLDER_REF" id="foldersRef_${_pageRef}"
				maxlength="15" onchange="checkProgReference(this.id)"
				cssStyle="text-transform:uppercase;width:120px"></ps:textfield></td>
		</tr>
		
		<tr>
			<td><ps:label  key="reporting.parentRef" for="lookuptxt_foldersPRef_${_pageRef}"/></td>
			<td nowrap="nowrap">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					
					
					<td width="80px"> 
		   				 <psj:livesearch 
				    		  id            ="foldersPRef_${_pageRef}"
		                      name          ="foldersCO.PARENT_REF" 
		                      mode          ="text"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/fcrRep/fcr_loadPRefLkp"
		                      searchElement =""
		                      resultList="PROG_REF:lookuptxt_foldersPRef_${_pageRef},PROG_DESC:foldersPRefStr_${_pageRef}"
		                      paramList="applName:lookuptxt_appName_${_pageRef}"
							  parameter     ="updates:lookuptxt_foldersPRef_${_pageRef},applName:lookuptxt_appName_${_pageRef}"
		                      dependencySrc ="${pageContext.request.contextPath}/path/fcrRep/fcr_dependencyByPRefId.action"
		                      dependency    ="foldersPRefStr_${_pageRef}:commonDetVO.BRIEF_DESC_ENG,lookuptxt_foldersPRef_${_pageRef}:commonDetVO.CODE_STR" 

		                      >
		        		      </psj:livesearch>
					  </td>
					<td>
						<ps:textfield id="foldersPRefStr_${_pageRef}" name="foldersCO.PARENT_REF_STR" readonly="true" tabindex="-1"></ps:textfield>
					  </td>
				</tr>
			</table>
			</td>
		</tr>
		
		<tr>
			<td><ps:label key="reporting.order" for="foldersOrder_${_pageRef}" /></td>
			<td><ps:textfield name="foldersCO.DISP_ORDER"
				id="foldersOrder_${_pageRef}" mode="number" emptyValue="0" nbFormat="######"
				maxlength="4" minValue="0" size="37" cssStyle="width:120px"></ps:textfield></td>
		</tr>
		<tr>
		<td>
			
				<ptt:toolBar id="fcrToolbar">
		<psj:submit button="true" buttonIcon="ui-icon-disk">
			<ps:label key="reporting.save"></ps:label>
		</psj:submit>
	</ptt:toolBar>
			
			
			</td>
		</tr>

	</table>
	</div>
	<ps:hidden id="DATE_UPDATED_${_pageRef}" name="foldersCO.DATE_UPDATED"/>
	<ps:hidden id="webAppsOnly_${_pageRef}" name="webAppsOnlyFold" value="1"></ps:hidden>
</ps:form>

<script type="text/javascript">
$(document).ready(function() {
$("#lookuptxt_appName_" + _pageRef).attr('readonly', true);
});
</script>