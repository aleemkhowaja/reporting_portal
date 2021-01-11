<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>

<ps:set name="checkProgRefAlert_var" 		value="%{getEscText('folders.checkProgRefAlert')}"/>
<ps:set name="missingProgRef_var" 			value="%{getEscText('folders.missingProgRef')}"/>
<ps:set name="missingAlert_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="detailsExist_var" 			value="%{getEscText('folders.detailsExist')}"/>
<ps:set name="containsSpace_var" 			value="%{getEscText('folders.containsSpace')}"/>


<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>

	
<script type="text/javascript">
	var checkProgRefAlert 		= '<ps:property value="checkProgRefAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var missingProgRef 			= '<ps:property value="missingProgRef_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var missingAlert 			= '<ps:property value="missingAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var detailsExist 			= '<ps:property value="detailsExist_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var containsSpace 			= '<ps:property value="containsSpace_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	/*
	On the page load, the code inside the below is executed first.
	*/
	$(document).ready(function()
	{
		$.struts2_jquery.require("Folders.js", null,jQuery.contextPath + "/path/js/reporting/ftr/folders/");    	
		
		//$("#foldersPRefStr_"+_pageRef).attr('readonly', true); //Making this field disabled
		$("#foldersDescEng_"+_pageRef).focus(); //Setting focus on first field , when page loads

		$("#foldersGridId_"+_pageRef).subscribe('emptyFoldHidden', function(event,data) 
		{
			 $("#foldersDetFormId_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("");
			 $("#foldersDetFormId_ #auditObj_"+_pageRef).val("");
		});
		//We are declaring that "selectRowFn" is an event , and we're defining what it should do
		//this event is called when the user selects a record from the grid 
		
	});
	
	
</script>

</head>
<body>
	
		<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
		<div id="FoldersHeader" style="padding:0">
			
				<ps:url id="urlFoldersTag" action="folders_loadFoldersList" namespace="/path/foldersMaint" />
					<psjg:grid 
					id="foldersGridId_${_pageRef}"
					gridModel="gridModel" 
					dataType="json" 
					href="%{urlFoldersTag}"
					pager="true" 
					navigator="true" 
					navigatorSearch="true"
					navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
					navigatorEdit="false" 
					navigatorAdd="false" 
					navigatorDelete="true"
					delfunc="deleteFolder"
					addfunc="addNewFolder" 
					ondblclick="selectRowFolderFn()"
					navigatorRefresh="true" 
					rowNum="3" 
					rowList="3,5,10,15,20"
					onCompleteTopics="emptyFoldHidden"
					hiddengrid="false"
					caption =" "
					sortable="true"
					viewrecords="true">
					<psjg:gridColumn name="BRIEF_NAME_ENG" id="briefNameEng"
						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
						title="%{getText('reporting.descEng')}" colType="text"
						editable="false" sortable="true" />
					<psjg:gridColumn name="BRIEF_NAME_ARAB" id="briefNameAr"
						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
						title="%{getText('reporting.descAr')}" colType="text" editable="false"
						sortable="true" />
					<psjg:gridColumn name="FOLDER_REF" id="progRef"
						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
						title="%{getText('progRef')}" colType="text"
						editable="false" sortable="true" width="80" align="center" />
					<psjg:gridColumn name="DISP_ORDER" id="displOrder" width="50"
						searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
						title="%{getText('reporting.order')}" colType="number"
						editable="false" sortable="true" align="center"/>
					<psjg:gridColumn name="PARENT_REF_STR" id="parRefStr"
						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
						title="%{getText('reporting.parentRef')}" colType="text" hidden="true"/>
		
					<psjg:gridColumn name="FOLDER_CODE" id="code"
						title="%{getText('reporting.code')}" colType="number" editable="false"
						sortable="false" key="true" hidden="true" />
					<psjg:gridColumn name="PARENT_REF" id="parentRef"
						title="%{getText('reporting.parentRef')}" colType="text"
						editable="false" sortable="true" hidden="true" />
		
					<psjg:gridColumn name="APP_NAME" id="APP_NAME"
						title="%{getText('reporting.applicationName')}" colType="text"
						editable="false" sortable="true" hidden="false" />
		
					</psjg:grid>		
		</div>



		<ps:collapsgroup id="foldersDetails_${_pageRef}">
			<ps:collapspanel id="foldersDetFrm"  key="folders.foldersDet">
				<div id="foldMaintDiv_${_pageRef}">
					<%@include file="FoldersMaint.jsp"%>
				</div>
			</ps:collapspanel>
		</ps:collapsgroup>



	</body>
</html>
<script type="text/javascript"> 
    $("#foldersGridId_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
    $("#gview_foldersGridId_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
</script>
