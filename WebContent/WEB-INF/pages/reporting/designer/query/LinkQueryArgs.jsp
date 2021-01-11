<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

 <script type="text/javascript">
$(document).ready(function() 
{
	rep_lnkQuery_readyFunc();
});
</script>

	<table>
		<tr>
			<td><ps:label value="%{getText('arguments.query')}" /></td>
			<td width="180px">		
				<psj:livesearch actionName="${pageContext.request.contextPath}/path/designer/queryDesign_constructQryLookup.action?_pageRef=${_pageRef}"
					name="argumentCO.QUERY_ID" id="queryLkp_${_pageRef}" 
			        searchElement="QUERY_ID, QUERY_NAME" autoSearch="true" 
			        paramList="QUERY_ID:lookuptxt_queryLkp_${_pageRef},_pageRef:_pageRef"
			        resultList="QUERY_ID:lookuptxt_queryLkp_${_pageRef},QUERY_NAME:queryLkpName"
			        parameter     ="repArgName:repArgsName_${_pageRef},queryIdLkp:lookuptxt_queryLkp_${_pageRef},_pageRef:_pageRef"
					dependencySrc ="${pageContext.request.contextPath}/path/designer/queryDesign_dependencyByQryId"
					dependency    ="lookuptxt_queryLkp_${_pageRef}:queryIdLkp,queryLkpName_${_pageRef}:queryNameLkp,columnsCmb_${_pageRef}:qryColumnsList,columnsDescCmb_${_pageRef}:qryColumnsList"
					afterDepEvent ="reloadLinkQryGrid()"
			    />
			  </td>
			<td width="180px">	    
			    <ps:textfield name="argumentCO.QUERY_NAME" id="queryLkpName_${_pageRef}" readonly="true"
			    	></ps:textfield>
			</td>
			<td><ps:label value="%{getText('arguments.columnName')}" /></td>
			<td width="180px">
			    <ps:select name="argumentCO.COLUMN_NAME"	id="columnsCmb_${_pageRef}" list="qryColumnsList" emptyOption="true"
			    	 parameter     ="columnName:columnsCmb_${_pageRef},_pageRef:_pageRef"
					 dependencySrc ="${pageContext.request.contextPath}/path/designer/queryDesign_dependencyByColName"
					 dependency    ="argumentType_${_pageRef}:argumentType,argumentValue_${_pageRef}:argDefaultVal"
			    	 >
				</ps:select>
			 </td>
			<td><ps:label value="%{getText('arguments.argQueryOptions')}" /></td>
			<td width="180px">
			    <ps:select name="argumentCO.ARG_QUERY_OPTIONS"	id="queryOptionsCmb_${_pageRef}" list="qryOprionsList"
			     	 parameter ="argQueryOptions:queryOptionsCmb_${_pageRef},_pageRef:_pageRef"
			     	 dependencySrc ="${pageContext.request.contextPath}/path/designer/queryDesign_dependencyByColName"
			     	 dependency    ="queryOptionsCmb_${_pageRef}:argQueryOptions"
				   	 listKey="VALUE_CODE"
					 listValue="VALUE_DESC"
					 afterDepEvent="disableDesc()"
			    	 >
				</ps:select>	
			 </td>
			 <td><ps:label value="%{getText('arguments.columnDesc')}" /></td>
			<td width="180px">
			    <ps:select name="argumentCO.COLUMN_DESC"	id="columnsDescCmb_${_pageRef}" list="qryColumnsList" emptyOption="true">
				</ps:select>
			 </td>
			 
	    </tr>
	    
	</table>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:url id="urlGrid" action="queryDesign_loadLinkQryGrid" namespace="/path/designer" escapeAmp="false">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		<ps:param name="repArgsName" value="repArgsName"></ps:param>
		<ps:param name="oldRepArgsName" value="oldRepArgsName"></ps:param>
		<ps:param name="qryName" value="qryName"></ps:param>
		<ps:param name="forSrcOrDfltVal" value="forSrcOrDfltVal"></ps:param>
		<ps:param name="queryNameDftlValLkp" value="queryNameDftlValLkp"></ps:param>
		
	</ps:url>   

    <psjg:grid
    	id="linkQryGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	editurl="%{urlGrid}"
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="5"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	navigator="true"	
    	navigatorAdd="false" 
    	navigatorEdit="false"
    	editinline="true"
    	navigatorRefresh="false"
    	navigatorView="false"
    	navigatorSearch="false"
    	navigatorDelete="false"
    	onEditInlineBeforeTopics="enableDisableParamsCells"
     >
     
     <psjg:gridColumn name="QRY_ARG_NAME" id="QRY_ARG_NAME" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="QRY_ARG_NAME"  colType="text"  title="%{getText('proc.paramName')}" sortable="true"  width="50"  hidden="false" editable="false"/>
     <psjg:gridColumn name="QRY_ARG_TYPE" id="QRY_ARG_TYPE" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="QRY_ARG_TYPE"  colType="text"  title="%{getText('proc.paramType')}" sortable="true"  width="50"  hidden="false" editable="false"/>
     
     <psjg:gridColumn name="MAP_PARAM_TYPE" index="MAP_PARAM_TYPE" id="MAP_PARAM_TYPE" title="%{getText('reporting.type')}" colType="select" editable="true" edittype="select" formatter="select" editoptions="{value:'0:%{getText('hyperLink.static')};2:%{getText('designer.arguments')}',dataEvents: [{ type: 'change', fn: function() { checkHypParamCell(this) } }]}" sortable="false" width="50" />
     <psjg:gridColumn name="irpQueryArgsMappingVO.STATIC_VALUE" index="irpQueryArgsMappingVO.STATIC_VALUE" id="irpQueryArgsMappingVO.STATIC_VALUE"   title="%{getText('hyperLink.static')}" colType="text"  editable="true" sortable="false" width="50" />
	 <psjg:gridColumn name="irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME" index="irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME" id="irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME"  
		     	 	  editoptions="{ readonly: 'readonly'}"
		     	 	  title="%{getText('designer.arguments')}" 
		     	 	  editable="true" 
		     	 	  sortable="false" 
		     	 	  colType="liveSearch" searchElement="ARGUMENT_NAME" paramList="updates:MAP_PARAM_TYPE,updates1:QRY_ARG_TYPE"
		     	 	  dataAction="${pageContext.request.contextPath}/path/designer/queryDesign_retColumnsByType.action?_pageRef=${_pageRef}"
		     	 	  resultList="ARGUMENT_NAME:irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME_lookuptxt" 
		     	 	  width="50"/> 	     	 	   				
     
       	 	   				
    </psjg:grid>
		<script type="text/javascript">
	
       $('#linkQryGridId_'+_pageRef ).jqGrid("setGridWidth",960);	
        
	</script>      
    
    <ps:hidden name="repArgsName" id="repArgsName_${_pageRef}"></ps:hidden>
    <ps:hidden name="queryIdLkp" id="queryIdLkp_${_pageRef}"></ps:hidden>
    <ps:hidden name="qryName" id="qryName_${_pageRef}"></ps:hidden>
    <ps:hidden name="columnName" id="columnName_${_pageRef}"></ps:hidden>
    <ps:hidden name="columnDesc" id="columnDesc_${_pageRef}"></ps:hidden>
    <ps:hidden name="argQueryOptions" id="argQueryOptions_${_pageRef}"></ps:hidden>
    <ps:hidden name="oldRepArgsName" id="oldRepArgsName_${_pageRef}"></ps:hidden>
    <ps:hidden name="forSrcOrDfltVal" id="forSrcOrDfltVal_${_pageRef}"></ps:hidden>
    <ps:hidden name="queryNameDftlValLkp" id="queryNameDftlValLkp_${_pageRef}"></ps:hidden>
   	<ps:hidden name="qryDfltValColName" id="qryDfltValColName_${_pageRef}"></ps:hidden>    
    <ps:hidden name="defaultValueColDesc" id="defaultValueColDesc_${_pageRef}"></ps:hidden>
	<ps:hidden name="queryNoDefSyntax" id="noDefinedSyntax_${_pageRef}"></ps:hidden>
 
    
    
    