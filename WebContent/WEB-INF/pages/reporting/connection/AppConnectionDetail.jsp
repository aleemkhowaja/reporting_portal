<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>


 
 <script type="text/javascript">

$(document).ready(function() 
{
	var actionType = $("#actionType_"+_pageRef).val();
	
});


</script>

<ps:form id="appConnForm" useHiddenProps="true" validate="true">
    <ps:hidden name="actionType" id="actionType_${_pageRef}"/>
    <ps:hidden id="DATE_UPDATED_${_pageRef}" name="icAppCO.irpAppConnectionVO.DATE_UPDATED"/>
	<table class="headerPortionContent ui-widget-content"  CELLPADDING="0" CELLSPACING="5" >
		<tr>
			<td align="center" nowrap="nowrap">
			<TABLE CELLPADDING="0" CELLSPACING="8">
			
					<TR style="visibility:" id="appNameTrId">
					<td align="left"><ps:label key="connection.appName"  for="longDesc_${_pageRef}"/></td>
					<td width="80px"> 
		 				 <psj:livesearch 
				    		  id            ="appName_${_pageRef}"
		                      name          ="icAppCO.irpAppConnectionVO.APP_NAME" 
		                      mode          ="text"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/appConnection/appConnection_loadAppNameLkp"
		                      searchElement ="APP_NAME"
		                      autoSearch="true"
		                      resultList="APP_NAME:lookuptxt_appName_${_pageRef},LONG_DESC:longDesc_${_pageRef}" 
							  parameter     ="appName:lookuptxt_appName_${_pageRef}"
							  dependencySrc ="${pageContext.request.contextPath}/path/appConnection/appConnection_applyAppNameDependency"
						      dependency    ="longDesc_${_pageRef}:icAppCO.LONG_DESC,lookuptxt_appName_${_pageRef}:icAppCO.irpAppConnectionVO.APP_NAME" 
		                      >
		        		      </psj:livesearch>
		        		      
		        		 </td>
						 <td> 
		        		       <ps:textfield id="longDesc_${_pageRef}" name="icAppCO.LONG_DESC" mode="character"  tabindex="1" size="30" maxlength="30" readonly="true" required="true"/>
		        		 </td>
				</TR>
				
				<TR>
					<td align="left"><ps:label key="connection.conDescription" for="connDesc_${_pageRef}"/></td>
					<td width="80px"> 
		 				 <psj:livesearch 
				    		  id            ="conId_${_pageRef}"
		                      name          ="icAppCO.irpAppConnectionVO.CON_ID" 
		                      mode          ="number"
		                      readOnlyMode  ="false"
		                      actionName="${pageContext.request.contextPath}/path/appConnection/appConnection_loadConnectionLkp"
		                      searchElement ="CONN_ID"
		                      autoSearch="true"
		                      resultList="CONN_ID:lookuptxt_conId_${_pageRef},CONN_DESC:connDesc_${_pageRef}" 
							  parameter     ="conId:lookuptxt_conId_${_pageRef}"
							  dependencySrc ="${pageContext.request.contextPath}/path/appConnection/appConnection_applyAppConDependency"
						      dependency    ="connDesc_${_pageRef}:icAppCO.CONN_DESC,lookuptxt_conId_${_pageRef}:icAppCO.irpAppConnectionVO.CON_ID" 
		                      
		                      >
		        		      </psj:livesearch>
		        		 </td>
						 <td>     
		        		      <ps:textfield id="connDesc_${_pageRef}" name="icAppCO.CONN_DESC" mode="character"  tabindex="1" size="30" maxlength="30" readonly="true" required="true"/>
					 </td>
				</TR>
	
			</TABLE>
		    </td>
		</tr>
	</table>
</ps:form>