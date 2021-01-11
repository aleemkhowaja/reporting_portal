<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 
<ps:set name="maxVal_var" 				value="%{getEscText('max_val_key')}"/>
<%
 String showCbParamHidden="true";
 String showCbParamCode="%{getText('')}";
 String showCbParamSubCode="%{getText('')}";
 String showCbParamSubDescription="%{getText('')}";
 String showCbParamNameCode="%{getText('')}";
 String pgRef=(request.getParameter("_pageRef"));
 String showCbParamDocTypeHidden="true";
 if(pgRef.equals("D01MT"))
 {
 	showCbParamCode="%{getText('economicSectorCode_key')}";
 	showCbParamSubCode="%{getText('subEconomicSectorCode_key')}";
 	showCbParamSubDescription="%{getText('longEnglishDesc_key')}";
  	showCbParamHidden="false";
 }
 else if(pgRef.equals("C01MT")){
 	showCbParamCode="%{getText('colletralTypeCode_key')}";
 	showCbParamSubCode="%{getText('')}";
 	showCbParamSubDescription="%{getText('')}";
  	showCbParamHidden="true";
 }
 else if(pgRef.equals("A01MT")){
 	showCbParamCode="%{getText('productClassCode_key')}";
 	showCbParamSubCode="%{getText('')}";
 	showCbParamSubDescription="%{getText('')}";
  	showCbParamHidden="true";
 }
 else if(pgRef.equals("B01MT")){
 	showCbParamCode="%{getText('currencyCode_key')}";
 	showCbParamSubCode="%{getText('')}";
 	showCbParamSubDescription="%{getText('')}";
  	showCbParamHidden="true";
 }
 else if(pgRef.equals("E01MT")){
 	showCbParamNameCode="%{getText('productClassTFACode_key')}";
 	showCbParamCode="%{getText('productClassCode_key')}";
 	showCbParamSubCode="%{getText('')}";
 	showCbParamSubDescription="%{getText('')}";
  	showCbParamHidden="true";
  	showCbParamDocTypeHidden="false";
 }
%>

<script type="text/javascript">
var maxValCbParm			= "${maxVal_var}";	

    $(document).ready(function () 
	{
		$.struts2_jquery.require("CbParam.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/cbparam/");
		rep_cbParam_readyFunc();
	});
</script>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />

 <div title="<ps:text name='reporting.cbParamInf'/>">

   <ps:url id="urlGrid" action="cbParamList_loadGrid" namespace="/path/cbParam">
   	<ps:param name="_pageRef" value="_pageRef"></ps:param>
   </ps:url>   
    <psjg:grid
    	id="cbParamGrid_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true"
    	rowNum="-1"
    	height="530"
    	viewrecords="false" 
    	editinline="true" pagerButtons="false"
		editurl="%{urlGrid}"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
    	navigator="true"	 
    	navigatorEdit="false"  
    	navigatorAdd="false" 
    	navigatorDelete="false"
    	navigatorSearch="true"
		onSelectRowTopics="retrieveAudit" 
		>
		<psjg:gridColumn name="DOC_TYPE"      								index="DOC_TYPE"   						searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       		colType="text"    title="<%=showCbParamNameCode%>"  		editable="false"  	sortable="true" align="center" hidden="<%=showCbParamDocTypeHidden%>" />
      	<psjg:gridColumn name="ftr_cb_codeVO.ENTITY_CODE"      				index="ftr_cb_codeVO.ENTITY_CODE"   	searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       		colType="number"  title="<%=showCbParamCode%>"  		 	editable="false"  	sortable="true" align="center"  />
       	<psjg:gridColumn name="DESCRIPTION"   				 		 		index="DESCRIPTION"   					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    colType="text"    title="%{getText('longEnglishDesc_key')}" editable="false"  	sortable="true" align="center"  />
      	<psjg:gridColumn name="ftr_cb_codeVO.SUB_ENTITY_CODE"      			index="ftr_cb_codeVO.SUB_ENTITY_CODE"   searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       		colType="number"  title="<%=showCbParamSubCode%>"  			editable="false"  	sortable="true" align="center" hidden="<%=showCbParamHidden%>" />
       	<psjg:gridColumn name="SUB_DESCRIPTION"   							index="SUB_DESCRIPTION"   				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    colType="text"    title="<%=showCbParamSubDescription%>" 	editable="false"  	sortable="true" align="center" hidden="<%=showCbParamHidden%>" />
       	<psjg:gridColumn name="ftr_cb_codeVO.ENTITY_CB_CODE" 				index="ftr_cb_codeVO.ENTITY_CB_CODE"  	searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       		colType="number"  title="%{getText('cBCode_key')}" minValue="0" maxValue="9999"		editable="true"   	sortable="true" align="center"  />
   		<psjg:gridColumn name="ftr_cb_codeVO.COMP_CODE"     				index="ftr_cb_codeVO.COMP_CODE"    																			colType="number"  title="%{getText('reporting.companyCode')}"    	  				  		sortable="false" hidden="true"  />
   		<psjg:gridColumn name="ftr_cb_codeVO.ENTITY_TYPE"     				index="ftr_cb_codeVO.ENTITY_TYPE"    																		colType="text"    title="ftr_cb_codeVO.ENTITY_TYPE"    	  		sortable="false" hidden="true"  />
   		<psjg:gridColumn name="ftr_cb_codeVO.DATE_UPDATED"     				index="ftr_cb_codeVO.DATE_UPDATED"    																		colType="date"    formatoptions="{srcformat:'Y-m-d H:i:s.u',newformat:'d/m/Y H:i:s:u' }"  title="DATE_UPDATED" sortable="false" hidden="true"/>
 
    </psjg:grid>
		
</div>
<div>
	<ps:form id="cbParamForm_${_pageRef}" useHiddenProps="true" action="cbParamList_saveCbParamList" namespace="/path/cbParam">
		<ps:hidden name="updates" id="updatesCbParamList_${_pageRef}"></ps:hidden> 
	</ps:form>  
	<ptt:toolBar  id="upDownToolBar"  > 
		<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="saveCbParam()" >
			<ps:text name="reporting.save"></ps:text>
		</psj:submit>	
	</ptt:toolBar>
</div>
