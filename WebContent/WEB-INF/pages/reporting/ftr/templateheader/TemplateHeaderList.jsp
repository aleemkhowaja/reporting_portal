<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>


<ps:set name="chooseHeader_var" 				value="%{getEscText('tmplHeader.chooseHeader')}"/>


<script type="text/javascript">

var chooseHeader 			= '<ps:property value="chooseHeader_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	

$(document).ready(function() 
{
	$.struts2_jquery.require("TemplateHeaderList.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/templateheader/");
	rep_tempHeaderLst_readyFunc();
});

</script>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
 <div>
   <ps:url id="urlGrid" action="templateHeaderAction_loadGrid" namespace="/path/templateHeader"></ps:url>   
    <psjg:grid
    	id="templateHeaderTable_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
		pager="true" navigator="true" navigatorSearch="true"
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		navigatorEdit="false" navigatorAdd="false" navigatorDelete="true"
		caption =" "
		navigatorRefresh="true" viewrecords="true" rowNum="5"
		rowList="5,10,15,20"   	
    	delfunc="deleteTemplateHeader"
    	addfunc="addNewTemplateHeader"
    	ondblclick="editTemplateHeader()"
    	onCompleteTopics="emptyCrtTrx"
		sortable="true" 
		hiddengrid="true"
		shrinkToFit="false"
		>
      	<psjg:gridColumn name="glsheaderVO.CODE" 	  		index="CODE"  						searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"       		   			colType="number"  title="%{getText('tmplHeader.code')}"      		editable="false"  sortable="true" search="true" key="true"/>
      	<psjg:gridColumn name="glsheaderVO.BRIEF_NAME_ENG"  index="glsheaderVO.BRIEF_NAME_ENG"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.briefDescEng')}"   	editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.BRIEF_NAME_ARAB" index="glsheaderVO.BRIEF_NAME_ARAB"  searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       		colType="text"    title="%{getText('tmplHeader.briefDescArab')}"  	editable="false"  sortable="true" search="false"/>
       	<psjg:gridColumn name="glsheaderVO.LEFT1"  			index="glsheaderVO.LEFT1"  			searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.leftInf1')}"   		editable="false"  sortable="true" search="false" />
      	<psjg:gridColumn name="glsheaderVO.LEFT2"  			index="glsheaderVO.LEFT2"  			searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.leftInf2')}" 		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.LEFT3"  			index="glsheaderVO.LEFT3"  			searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.leftInf3')}" 		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.LEFT4"  			index="glsheaderVO.LEFT4"  			searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"      			colType="text"    title="%{getText('tmplHeader.leftInf4')}" 		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.CENTER1"  		index="glsheaderVO.CENTER1"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"      			colType="text"    title="%{getText('tmplHeader.centerInf1')}"		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.CENTER2"  		index="glsheaderVO.CENTER2"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.centerInf2')}"		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.CENTER3"  		index="glsheaderVO.CENTER3"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.centerInf3')}"		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.RIGHT1"  		index="glsheaderVO.RIGHT1"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.rightInf1')}"		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.RIGHT2"  		index="glsheaderVO.RIGHT2"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"     			colType="text"    title="%{getText('tmplHeader.rightInf2')}"		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.RIGHT3"  		index="glsheaderVO.RIGHT3"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.rightInf3')}"		editable="false"  sortable="true" search="false"/>
      	<psjg:gridColumn name="glsheaderVO.RIGHT4"  		index="glsheaderVO.RIGHT4"  		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('tmplHeader.rightInf4')}"		editable="false"  sortable="true" search="false"/>
 		<psjg:gridColumn name="TEMPLATE_TYPE_STR"  	index="TEMPLATE_TYPE_STR"  	searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"       			colType="text"    title="%{getText('reporting.type')}"		editable="false"  sortable="true" search="false"/>
 </psjg:grid>
</div>
<div id="thDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
	 <%@include file="TemplateHeaderFrm.jsp"%>
</div>
<script type="text/javascript">

$("#templateHeaderTable_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
$("#gview_templateHeaderTable_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
</script>  
