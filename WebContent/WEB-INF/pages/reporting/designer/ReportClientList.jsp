<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<script type="text/javascript">

	function addRepClient()
	{
		$("#repClient_"+_pageRef).jqGrid('addInlineRow',{});
	}
	
	function delRepClient(rowid)
	{
		 $("#repClient_"+_pageRef).jqGrid('deleteGridRow'); 
	}
</script>

 <div >
	  	<ps:url var="urlTag" action="repClient_loadClientRepList" namespace="/path/designer">
	  		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	  	</ps:url>
		<psjg:grid  id="repClient_${_pageRef}" 
					gridModel="gridModel"
					dataType="json"
					href="%{urlTag}"
					pager="true"
					navigator="true"
					navigatorSearch="false "
					navigatorRefresh="false"
		        	viewrecords="true"
		        	rowNum="15" 
		  	  		rowList="5,10,15,20"
		  	  		editinline="true"
		  	  		editurl="%{urlTag}"
		        	navigatorEdit="false"
		        	addfunc="addRepClient"
		        	delfunc="delRepClient" 
		        	>
         
         <psjg:gridColumn name="irpClientReportVO.CLIENT_ACRONYM" index="CLIENT_ACRONYM" id="CLIENT_ACRONYM"  title="%{getText('upDown.cltAcro')}" colType="text"  editable="false" sortable="false"/>
     	 <psjg:gridColumn id="CLIENT_NAME" index="CLIENT_NAME" name="CLIENT_NAME"   
     	 	editoptions="{ readonly: 'readonly'}"
     	 	title="%{getText('upDown.cltName')}"  editable="true" sortable="false" 
     	 	colType="liveSearch" searchElement="CLIENT_NAME,CLIENT_NAME" 
     	 	dataAction="${pageContext.request.contextPath}/path/designer/repClient_clientRepLkp.action?_pageRef=${_pageRef}" 
     	 	resultList="pthClientsVO.CLIENT_ACRONYM:irpClientReportVO.CLIENT_ACRONYM,pthClientsVO.CLIENT_NAME:CLIENT_NAME_lookuptxt" 
     	 	required="true"
     	 	/>


  	 </psjg:grid> 
</div>



<ps:form id="repClientForm_${_pageRef}" action="repClient_putInRepList" namespace="/path/designer">
	<ps:hidden name="updatesRepClient" id="updatesRepClient_${_pageRef}"></ps:hidden>
</ps:form>
	
	
	