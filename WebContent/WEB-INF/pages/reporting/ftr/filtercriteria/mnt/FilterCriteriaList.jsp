<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 
<ps:set name="codeExistAlert_var" 				value="%{getEscText('codeExistAlert')}"/>
<ps:set name="critDel_msg_key_var" 				value="%{getEscText('critDel_msg_key')}"/>
<ps:set name="crtLevel_var" 				value="%{getEscText('reporting.crtLevel')}"/>


	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
			
<style>
.ui-jqgrid tr.jqgrow td {white-space: normal}
</style>

<script src="${pageContext.request.contextPath}/path/js/reporting/ftr/filterCriteria/FilterCriteria.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
			
<script type="text/javascript">

var codeExistAlert 			= '<ps:property value="codeExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
var critDel_msg_key			= '<ps:property value="critDel_msg_key_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var crtLevel				= '<ps:property value="crtLevel_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function() 
{
	$.struts2_jquery.require("FilterCriteria.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/filterCriteria/");
	filterCrtReadyFunc();
});

</script>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
 
 <div id="sortFC" class="connectedSortable ui-helper-reset">
 <div class="collapsibleContainer" title="<ps:text name='reporting.filterCriteriaInf'/>">
  <table width="100%" cellpadding="0">
  <tr>
  <td width="100%" >
   <ps:url id="urlGrid" action="filterCriteriaList_loadGrid" namespace="/path/filterCriteria"/>   
    <psjg:grid
    	id="filterCritTable_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
		pager="true" navigator="true" navigatorSearch="true"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		navigatorEdit="false" navigatorAdd="false" navigatorDelete="true"
		navigatorRefresh="true" viewrecords="true" rowNum="15"
		rowList="5,10,15,20"   	
    	delfunc="deleteFilterCrit"
    	ondblclick="editFilterCrit()"
    	onCompleteTopics="emptyCrtTrx"
		sortable="true"
		addfunc="emptyForm"
		caption =" "
     >

      	<psjg:gridColumn name="glstmpltCriteriaVO.COMP_CODE"             	  index="glstmpltCriteriaVO.COMP_CODE"        			searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       colType="number"  title="COMP_CODE"         editable="false"  sortable="true" search="false" hidden="true"/>
      	<psjg:gridColumn name="glstmpltCriteriaVO.CODE"             		  index="glstmpltCriteriaVO.CODE"        				searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       colType="number"  title="%{getText('reporting.code')}"         editable="false"  sortable="true" search="true" key="true"/>
       	<psjg:gridColumn name="glstmpltCriteriaVO.BRIEF_DESC_ENG"      	  	  index="glstmpltCriteriaVO.BRIEF_DESC_ENG"   	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    colType="text"    title="%{getText('FilerCriteria.Brief_Desc_Eng_key')}"  editable="false"  sortable="true" search="true"/>
      	<psjg:gridColumn name="glstmpltCriteriaVO.LONG_DESC_ENG"      	  	  index="glstmpltCriteriaVO.LONG_DESC_ENG"   	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    colType="text"    title="%{getText('FilerCriteria.Long_Desc_Eng_key')}"  editable="false"  sortable="true" search="true"/>
    	<psjg:gridColumn name="glstmpltCriteriaVO.BRIEF_DESC_ARAB"      	  index="glstmpltCriteriaVO.BRIEF_DESC_ARAB"   	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    colType="text"    title="%{getText('Brief_Name_Arab_key')}"  editable="false"  sortable="true" search="true"/>
    	<psjg:gridColumn name="glstmpltCriteriaVO.LONG_DESC_ARAB"      	  	  index="glstmpltCriteriaVO.LONG_DESC_ARAB"   	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"    colType="text"    title="%{getText('Long_Name_Arab_key')}"  editable="false"  sortable="true" search="true"/>
     	<psjg:gridColumn name="CRITERIA_TYPE_DESCRIPTION"  					  index="CRITERIA_TYPE_DESCRIPTION"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" colType="text"    title="%{getText('reporting.type')}"         editable="false"  sortable="true" search="true"/>
    	<psjg:gridColumn name="glstmpltCriteriaVO.CRI_TYPE" 		  		  index="glstmpltCriteriaVO.CRI_TYPE_CODE" 		colType="text"    title="%{getText('reporting.type')}"         editable="false"  hidden="true"   search="false"/>

    </psjg:grid>
    </td>
    </tr>
    </table>
		
</div>
</div>
<div>
	<h1 class="headerPortionContent ui-widget ui-state-default">
		<label> <ps:text name='reporting.details'/></label>
	</h1>
</div>
<div id="fcDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
	 <%@include file="FilterCriteriaMaint.jsp"%>
</div>
<ptt:toolBar  id="filterCrtSav_${_pageRef}"> 
	<psj:submit type="button" button="true" buttonIcon="ui-icon-disk" onclick="addNew()" >
		<ps:text name="reporting.save"></ps:text>
	</psj:submit>
</ptt:toolBar>
<script type="text/javascript"> 
$("#filterCritTable_" + _pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>