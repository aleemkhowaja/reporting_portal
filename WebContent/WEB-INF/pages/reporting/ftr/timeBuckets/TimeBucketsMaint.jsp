<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<script type="text/javascript">
$(document).ready(function() 
{
	$("#timeBucketsForm_"+_pageRef).processAfterValid("saveTimeBuckets");	
});

</script>
			
<ps:form id="timeBucketsForm_${_pageRef}" useHiddenProps="true" validate="true" namespace="/path/timeBuckets">
	<ps:hidden id="mode_${_pageRef}" name="mode"></ps:hidden>
	<%--ps:hidden id="DATE_UPDATED_${_pageRef}" name="ftrTimeBucketsCO.ftrTimeBucketsVO.DATE_UPDATED" /--%>
  		<table class="headerPortionContent ui-widget-content" width="100%" cellspacing="0" cellpadding="5" border="0">
	  		<tr>
	  			<td width = "15%">
					<ps:label value="%{getText('designer.reportReference')}"/>
				</td>
	  			<td width="15%">
	  				<psj:livesearch 
		    		  	id            ="repRefTimeBuckets_${_pageRef}"
	                    name          ="ftrTimeBucketsCO.ftrtimebucketsVO.REP_REF" 
	                    readOnlyMode  ="false"
	                    actionName="${pageContext.request.contextPath}/path/commonLkp/commonLkpAction_reportLookup.action"
	                    searchElement ="PROG_REF" 
	                    resultList="ftrTimeBucketsCO.reportName:repName_${_pageRef}" 
	                    mode ="text"
	                    parameter     ="repRef:lookuptxt_repRefTimeBuckets_${_pageRef}"
	                    dependencySrc ="${pageContext.request.contextPath}/path/timeBuckets/timeBucketsAction_retReportRef.action"
	                    dependency    ="lookuptxt_repRefTimeBuckets_${_pageRef}:ftrTimeBucketsCO.ftrtimebucketsVO.REP_REF,repName_${_pageRef}:ftrTimeBucketsCO.reportName">
	       		    </psj:livesearch>
	  			</td>
	  			<td width = "15%">
	  				<ps:textfield id="repName_${_pageRef}" name="ftrTimeBucketsCO.ftrtimebucketsVO.REP_REF" disabled="true" readonly="true"></ps:textfield>
				</td>
				<td></td>	
	  		</tr>
	  		<tr>
				<td width = "15%">
					<ps:label value="%{getText('reporting.lkpCurrCode')}"/>
				</td>
				<td width="15%">
					<psj:livesearch
						id="currencyCodeTimeBuckets_${_pageRef}" 
						name="ftrTimeBucketsCO.ftrtimebucketsVO.CURRENCY_CODE"
						readOnlyMode  ="false"
						actionName="${pageContext.request.contextPath}/pathdesktop/currencyLookup_constructLookup"
						searchElement="CURRENCY_CODE" 
						resultList="CURRENCY_CODE:lookuptxt_currencyCodeTimeBuckets_${_pageRef},BRIEF_DESC_ENG:currCodeTimeBuckets_${_pageRef}"
						mode="number" maxlength="3" minValue="0"
						parameter="CURRENCY_CODE:lookuptxt_currencyCodeTimeBuckets_${_pageRef}"
			            dependencySrc="${pageContext.request.contextPath}/pathdesktop/CurrencyDependencyAction_dependencyByCurrencyCode.action?displayMsg=1"
			            dependency="lookuptxt_currencyCodeTimeBuckets_${_pageRef}:currency.CURRENCY_CODE,currCodeTimeBuckets_${_pageRef}:currency.BRIEF_DESC_ENG">
					</psj:livesearch>	
				</td>
				<td width = "15%">
					<ps:textfield id="currCodeTimeBuckets_${_pageRef}" name="ftrTimeBucketsCO.BRIEF_DESC_ENG" disabled="true" readonly="true"/>
				</td>
				<td></td>
			</tr>
  		</table>
	<ps:hidden name="updates" id="updatesExpr_${_pageRef}"></ps:hidden>
	<ps:hidden name="updates_1" id="updatesExpr_1${_pageRef}"></ps:hidden>
</ps:form>