<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:form  id="controlRecordForm_${_pageRef}" useHiddenProps="true"  validate="true" namespace="/path/controlRecord" >
<ps:hidden id="mode_${_pageRef}" name="mode"></ps:hidden>
<ps:hidden id="DATE_UPDATED_${_pageRef}" name="btrControlCO.btrControlVO.DATE_UPDATED"/>
 
 <div>
  <table width="90%" cellspacing="0" cellpadding="5" border="0">
    	<tr>
    		<td width = "25%"><ps:label cssStyle="display:none" key="ctrlRecord.bankCode" for="bankCode_${_pageRef}"/></td>
    		<td width = "20%"><ps:textfield cssStyle="display:none" id="bankCode_${_pageRef}" name="btrControlCO.btrControlVO.CLIENT_CODE" minValue="0" mode="number" nbFormat="####"    maxlength="4"  /></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td width = "25%"><ps:label key="ctrlRecord.bankID" for="bankID_${_pageRef}"/></td>
    		<td width = "20%"><ps:textfield id="bankID_${_pageRef}" name="btrControlCO.btrControlVO.BANK_ID" minValue="0" mode="number" nbFormat="####"  maxlength="4"	 /></td>
    		<td></td>
    	</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.bankName" for="bankName_${_pageRef}"/></td>
			<td  colspan="2"><ps:textfield id="bankName_${_pageRef}" name="btrControlCO.btrControlVO.CLIENT_BRIEF_DESC"    maxlength="20"   /></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.baseNationality" for="baseNationality_${_pageRef}"/></td>
			<td width="15%"> <psj:livesearch 
						    		  id            ="baseNationalitySearch_${_pageRef}" 
				                      name			="btrControlCO.btrControlVO.NATIONALITY"
				                      readOnlyMode  ="false"				                      
				                      actionName="${pageContext.request.contextPath}/pathdesktop/NationalityLookup_constructLookup"
				                      searchElement="CODE"  autoSearch="false"  minValue="0" mode="number"  maxlength="3" 
				                      parameter="NATIONALITY_CODE:lookuptxt_baseNationalitySearch_${_pageRef}"
				                      resultList="BRIEF_DESC_ENG:baseNationality_${_pageRef}"
				                      dependencySrc="${pageContext.request.contextPath}/pathdesktop/NationalityDependencyAction_dependencyByNationalityCode"
	                      			  dependency="baseNationality_${_pageRef}:nationalityVO.BRIEF_DESC_ENG,lookuptxt_baseNationalitySearch_${_pageRef}:nationalityVO.CODE"
				                      ></psj:livesearch>				    
			</td>
			<td width = "20%"><ps:textfield id="baseNationality_${_pageRef}" name="btrControlCO.NATIONALITY_BRIEF_DESC_ENG"     maxlength="4" readonly="true"/></td>
		</tr>
		<tr>
			<td width="25%"><ps:label key="ctrlRecord.empBaseCountry" for="empBaseCountry_${_pageRef}"/></td>
			<td width="15%"><psj:livesearch 
						    		  id            ="empBaseNationalitySearch_${_pageRef}"
				                      name			="btrControlCO.btrControlVO.EMP_BASE_COUNTRY" 
				                      mode          ="number"
				                      minValue		="0"
				                      readOnlyMode  ="false"
				                      actionName="${pageContext.request.contextPath}/pathdesktop/CountriesLookup_constructLookup"
				                      searchElement ="COUNTRY_CODE" 	maxlength="3"
				                      parameter		="COUNTRY_CODE:lookuptxt_empBaseNationalitySearch_${_pageRef}"
				                      resultList    ="BRIEF_DESC_ENG:empBaseCountry_${_pageRef}"
				                      dependencySrc="${pageContext.request.contextPath}/pathdesktop/CountriesDependencyAction_dependencyByCountryCode"
				                      dependency    ="empBaseCountry_${_pageRef}:countriesVO.BRIEF_DESC_ENG,lookuptxt_empBaseNationalitySearch_${_pageRef}:countriesVO.COUNTRY_CODE"
				                      ></psj:livesearch>
			</td>
			<td width = "20%"><ps:textfield id="empBaseCountry_${_pageRef}" name="countryVO.BRIEF_DESC_ENG"     maxlength="4" readonly="true"/></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.repCurCode" for="usdCyCode_${_pageRef}"/></td>
			<td width="15%"><psj:livesearch
							            actionName="${pageContext.request.contextPath}/pathdesktop/currencyLookup_constructLookup" 
							            id="repCurCodeSearch_${_pageRef}"
							            name="btrControlCO.btrControlVO.USD_CY_CODE"
							            searchElement="CURRENCY_CODE" 
							            resultList    ="CURRENCY_CODE:lookuptxt_repCurCodeSearch_${_pageRef},BRIEF_DESC_ENG:codeField_${_pageRef}"
							            mode="number" 
							            maxlength="3" 
							            parameter     ="CURRENCY_CODE:lookuptxt_repCurCodeSearch_${_pageRef}"
							            dependencySrc="${pageContext.request.contextPath}/pathdesktop/CurrencyDependencyAction_dependencyByCurrencyCode.action?displayMsg=1"
							            dependency    ="usdCyCode_${_pageRef}:currency.BRIEF_DESC_ENG,lookuptxt_repCurCodeSearch_${_pageRef}:currency.CURRENCY_CODE">
							</psj:livesearch>           
				</td>
			<td width = "20%"><ps:textfield id="usdCyCode_${_pageRef}" name="currencyDesc"     maxlength="4" readonly="true"/></td>
			<td></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.csv" for="csvPath_${_pageRef}"/></td>
			<td colspan="2"><ps:textfield id="csvPath_${_pageRef}" name="btrControlCO.btrControlVO.CSV_PATH"   maxlength="100" /></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.defaultLang" for="reportLang_${_pageRef}"/></td>
			<td><ps:select name="btrControlCO.btrControlVO.REPORT_LANG" id="reportLang_${_pageRef}" 
									 	list="langList" listKey="LANG_CODE" 
										listValue="LANG_NAME" >
							  </ps:select></td>
			<td></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.wkDay" for="btrWeekendDay_${_pageRef}"/></td>
			<td width = "20%">			
			<ps:select id ="btrWeekendDay_${_pageRef}" list="weekDaysList" listKey="VALUE_CODE" listValue="VALUE_DESC"
			name="btrControlCO.btrControlVO.BTR_WEEKEND_DAY" ></ps:select> 
			</td>
			<td></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.addRef" for="addRef_${_pageRef}"/></td>
			<td colspan="2"><ps:textfield id="addRef_${_pageRef}" name="btrControlCO.btrControlVO.ADD_REF"  maxlength="30"  /></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.bankCountCode" for="cbCountryCode_${_pageRef}"/></td>
			<td width = "20%"><ps:textfield id="cbCountryCode_${_pageRef}" name="btrControlCO.btrControlVO.CB_COUNTRY_CODE"    maxlength="15"  /></td>
			<td></td>
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.tradeValDat" for="tradeValue_${_pageRef}"/></td>			
			<td width = "15%"><ps:select id ="tradeValue_${_pageRef}" list="tradeList" listKey="VALUE_CODE" listValue="VALUE_DESC"	name="btrControlCO.btrControlVO.TRADE_VALUE" ></ps:select></td>
			<td></td>		
		</tr>
		<tr>
			<td width = "25%"><ps:label key="ctrlRecord.disTmpltCode" for="disableTemplateCode_${_pageRef}"/></td>
			<td colspan="2"><ps:checkbox name="btrControlCO.btrControlVO.ARGUMENTS_ENABLE_YN" valOpt="Y:N"
											id="disableTemplateCode_${_pageRef}" ></ps:checkbox></td>
		</tr>
		<tr>
			<td colspan="3" align="left">
  				<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="saveAll()" >
  					<ps:text name="reporting.save"></ps:text>
  				</psj:submit>
  		   </td>
		</tr>
    </table>
</div>
</ps:form>