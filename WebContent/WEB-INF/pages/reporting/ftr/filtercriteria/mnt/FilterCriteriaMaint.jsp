<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:form id="filterCritForm_${_pageRef}" useHiddenProps="true" validate="true">
    <ps:hidden name="actionType" id="actionType_${_pageRef}"/>
    <ps:hidden id="DATE_UPDATED_${_pageRef}" name="fcCO.glstmpltCriteriaVO.DATE_UPDATED"/>
    
	<table width="70%"  CELLPADDING="0" CELLSPACING="5">
		<tr>
			<td align="center" nowrap="nowrap">
			<TABLE CELLPADDING="0" CELLSPACING="8"  width="100%">
				<TR>
					<TD  nowrap="nowrap" width="10%">
						<ps:label key="reporting.code" for="codeField_${_pageRef}"/>
					</TD>
					<TD style="padding-right:  70%" align="left">
						<ps:textfield id="codeField_${_pageRef}" name="fcCO.glstmpltCriteriaVO.CODE" mode="number" minValue="0"   nbFormat="####"   maxlength="4"
						onchange="checkIDAvailability()" required="true"/>
				    </TD>
			    </tr>
			    <tr>
					<td  nowrap="nowrap" width="10%"><ps:label key="FilerCriteria.Brief_Desc_Eng_key" for="criteriaDesc_${_pageRef}"/></td>
					<td  style="padding-right:  50%"><ps:textfield id="criteriaDesc_${_pageRef}" name="fcCO.glstmpltCriteriaVO.BRIEF_DESC_ENG"    maxlength="30" required="true"/></td>
				</tr>
				<tr>	
					<td  nowrap="nowrap" width="10%"><ps:label key="FilerCriteria.Long_Desc_Eng_key" for="longDescEng_${_pageRef}"/></td>
					<td style="padding-right:  50%"><ps:textfield id="longDescEng_${_pageRef}" name="fcCO.glstmpltCriteriaVO.LONG_DESC_ENG"  maxlength="60" required="true" ></ps:textfield></td>
				</tr>	
				<tr>	
					<td  nowrap="nowrap"><ps:label key="Brief_Name_Arab_key" for="briefDescArab_${_pageRef}"/></td>
					<td style="padding-right:  50%"><ps:textfield id="briefDescArab_${_pageRef}" name="fcCO.glstmpltCriteriaVO.BRIEF_DESC_ARAB"    maxlength="30" onlyArabic="true"/></td>
				</tr>		
				<tr>
					<td  nowrap="nowrap"><ps:label key="Long_Name_Arab_key" for="longDescArab_${_pageRef}"/></td>
					<td  style="padding-right:  50%"><ps:textfield id="longDescArab_${_pageRef}" name="fcCO.glstmpltCriteriaVO.LONG_DESC_ARAB"    maxlength="60"  onlyArabic="true"/></td>
				</tr>
				<tr>
					<td  nowrap="nowrap"><ps:label key="reporting.type" for="criteriaType_${_pageRef}"/></td>
					<td style="padding-right:  50%"><ps:select id="criteriaType_${_pageRef}" list="criteriaTypes" listKey="CRITERIA_TYPE_CODE" listValue="CRITERIA_TYPE_DESCRIPTION" name="fcCO.glstmpltCriteriaVO.CRI_TYPE"
	            		emptyOption="true"  required="true" 
	            		parameter="fcCO.glstmpltCriteriaVO.CRI_TYPE:criteriaType_${_pageRef}"
	            		dependency ="criteriaType_${_pageRef}:fcCO.glstmpltCriteriaVO.CRI_TYPE"
	            		dependencySrc ="${pageContext.request.contextPath}/path/filterCriteria/filterCriteriaList_applySmartValidation.action"/></td>
				</TR>
			</TABLE>
		    </td>
		</tr>
		<tr  id = "smartLookup" >
		<td>
		<table width="100%">
					<tr> 
						<td width="10.5%" align="left" nowrap="nowrap"><ps:label key="reporting.smartType" for = "smartCode_${_pageRef}" /></td>
						<td width="14.5%" align="left">
						  <psj:livesearch 
						    		  id            ="smartLookupSearch_${_pageRef}"
				                      name          ="fcCO.glstmpltCriteriaVO.SMART_CODE" 
				                      mode          ="number"
				                      readOnlyMode  ="false"
				                      actionName    ="${pageContext.request.contextPath}/path/commonLkp/smartLkpAction_contructSmartLookup.action?" 
				                      searchElement =""
				                      parameter=	"optionCode:lookuptxt_smartLookupSearch_${_pageRef}"
				                      resultList    ="OPTION_CODE:lookuptxt_smartLookupSearch_${_pageRef},BRIEF_NAME_ENG:smartCode_${_pageRef}"
				                      dependencySrc ="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_applySmartDependency.action"
				                      dependency    ="smartCode_${_pageRef}:sAdditionsOptionsVO.BRIEF_NAME_ENG,lookuptxt_smartLookupSearch_${_pageRef}:sAdditionsOptionsVO.OPTION_CODE"
				                      >
				           </psj:livesearch>
						</td>
						<td width="75%"  align="left" style="padding-right: 50.4%">
							  <ps:textfield name="fcCO.SMART_BRIEF_NAME_ENG"  id="smartCode_${_pageRef}" readonly="false"  required="true"></ps:textfield>
						</td>						 
					</tr>
				</table>
		</td>
	</tr>
	<tr  id = "criLineGLTd_<ps:property value="_pageRef" escapeHtml="true"/>" >
		<td>
		<table width="100%" >
					<tr> 
						<td width="11%" align="left"><ps:label  key="reporting.crtLevel"  id="criLineGLId_${_pageRef}" /></td>
						<td width="15%" align="left">
						  <ps:radio name="fcCO.glstmpltCriteriaVO.CRI_LINE_GL" id="lineGlCrt_${_pageRef}" list="#{'L':'reporting.line','G':'gl.glTitle'}"></ps:radio>
						</td>
						<td width="74%" >
						</td>						 
					</tr>
		</table>
		</td>
	</tr>
	</table>
	
	<ps:textfield name="fcCO.smartInput" cssStyle="display:none"></ps:textfield>
</ps:form>
