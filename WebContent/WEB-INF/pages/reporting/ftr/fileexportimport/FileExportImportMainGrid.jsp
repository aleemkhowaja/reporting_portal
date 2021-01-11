<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@taglib prefix="ptt"  uri="/path-toolbar-tags" %>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-jquery-grid-tags" prefix="sjg"%>

<ps:set var="mainfiles_gridEditable" value="%{(_pageRef== 'FEI00MT' )?'true':'false'}" />
<ps:url id="urlGrid" action="FileExportImportListAction_loadGrid"
	namespace="/path/fileExportImport">
	
<ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>

<psjg:grid id="fileMainGrid_${_pageRef}" gridModel="gridModel" dataType="json"
	href="%{urlMainGrid}" pager="true" navigator="true"
	navigatorSearch="true"
	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
	navigatorEdit="false"  navigatorAdd="false" navigatorDelete='%{#mainfiles_gridEditable}'
	navigatorRefresh="true" viewrecords="true" rowNum="5"
	rowList="5,10,15,20" 
	onCompleteTopics="emptySchedTrx" sortable="true"
	addfunc="addNewFileMain"
	delfunc="deleteFileMain"
	ondblclick="editFileMain()">
	<psjg:gridColumn name="fileIdCheckBox"  id="fileIdCheckBox_${_pageRef}" 
					 searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   
					 index="fileIdCheckBox" colType="checkbox" title="%{getText('upDown.ExpRep')}" 
					 align="center" formatter="checkbox"  sortable="false" 
					 edittype="checkbox"  width="10" editable="true" hidden="true" search="false"
					 formatoptions="{disabled : false}" editoptions="{value:'Yes:No'}"/>
	<psjg:gridColumn name="irp_file_mainVO.FILE_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" id="FILE_ID" index = "FILE_ID" width="10"
		title="%{getText('fileexpimp.fileid')}" colType="number" editable="false"
		sortable="false" key="true" hidden="false" />

	<psjg:gridColumn name="irp_file_mainVO.FILE_NAME" id="FILE_NAME" index = "FILE_NAME" width="70"
		searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" 
		title="%{getText('fileexpimp.name')}" colType="text" editable="false"
		sortable="false" />
    <psjg:gridColumn name="irp_file_mainVO.DATE_UPDATED"   index="DATE_UPDATED" colType="date"    formatoptions="{srcformat:'Y-m-d H:i:s.u',newformat:'d/m/Y H:i:s:u' }"  title="DATE_UPDATED" sortable="false" hidden="true"/>

</psjg:grid>

