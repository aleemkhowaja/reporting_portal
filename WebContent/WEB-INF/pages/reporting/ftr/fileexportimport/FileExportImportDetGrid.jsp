<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
 <html>

<ps:set var="detfiles_gridEditable" value="%{(_pageRef== 'FEI00MT' )?'true':'false'}" />
<ps:set var="detfiles_viewFile" value="%{(_pageRef== 'FEI00MT' )?'true':'false'}" />
<ps:set name="viewFileTitle_var" 		value="%{getEscText('fileexpimp.viewfile')}"/>
 <script type="text/javascript">
var viewFileTitle 	= '<ps:property value="viewFileTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
$("#fileDetGrid_"+_pageRef).subscribe('rep_fileExpImp_disableCols', function(event, data) 
{
	rep_fileExpImp_DetGridReady();
});

</script>

<psjg:grid id="fileDetGrid_${_pageRef}" gridModel="gridModel" dataType="json"
	href="%{urlDetGrid}" pager="true" navigator="true"
	navigatorSearch="false"
	navigatorRefresh="false"
	navigatorDelete ='%{#detfiles_gridEditable}' 
	navigatorAdd='%{#detfiles_gridEditable}'  
	viewrecords="true" rowNum="5"
	rowList="5,10,15,20" 
	editinline='true'  
	editurl="%{urlDetGrid}"
	navigatorEdit="false"
	delfunc="deleteFileDetails"
	addfunc="addFileDetails"
	onEditInlineBeforeTopics="rep_fileExpImp_disableCols"
	>
<psjg:gridColumn name="lineNoCheckBox"  id="lineNoCheckBox_${_pageRef}" 
		 searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   
		 index="lineNoCheckBox" colType="checkbox" title="%{getText('upDown.ExpRep')}" 
		 align="center" formatter="checkbox"  sortable="false" 
		 edittype="checkbox"  width="10" editable="%{#detfiles_gridEditable}" hidden='%{#detfiles_viewFile}' search="false"
		 formatoptions="{disabled : false}" editoptions="{value:'Yes:No'}"/>	
			 

<psjg:gridColumn name="irp_file_detVO.LINE_NO"  id="LINE_NO" width="30"
		title="%{getText('fileexpimp.lineno')}" colType="number" editable="false"  
		sortable="false" key="true" hidden="false" index="LINE_NO"/>
	

<psjg:gridColumn name="irp_file_detVO.APP_NAME" id="APP_NAME" width="4"
		title="Application Name" colType="text"
		sortable="false"  hidden="true" editable="false"  index = "APP_NAME"/>


	<psjg:gridColumn name="PROG_REF"  id="PROG_REF" index = "PROG_REF"
		title="%{getText('fileexpimp.reference')}" editable="%{#detfiles_gridEditable}" sortable="false"
		colType="liveSearch" 
		editoptions="{ readonly: 'readonly'}"  
		dataAction="${pageContext.request.contextPath}/path/fileExportImport/fileExportImportLookupAction_reportLookup.action?_pageRef=${_pageRef}"
		searchElement="PROG_REF"     
		required = "true"
		resultList="PROG_REF:lookuptxt_PROG_REF"
		/>
		
<psjg:gridColumn name="irp_file_detVO.SUB_FILE_NAME" id="SUB_FILE_NAME" width="150"
		title="%{getText('fileexpimp.subfilename')}" colType="text" editable="%{#detfiles_gridEditable}"
		sortable="false" index = "SUB_FILE_NAME"  required = "true" />
   <psjg:gridColumn name="irp_file_detVO.DATE_UPDATED" id="DATE_UPDATED"   index="DATE_UPDATED" colType="date"    formatoptions="{srcformat:'Y-m-d H:i:s.u',newformat:'d/m/Y H:i:s:u' }"  title="DATE_UPDATED" sortable="false" hidden="true"/>

   <psjg:gridColumn  name="VIEW_FILE" formatter="fileExportImportList_viewFile" index="VIEW_FILE" colType="text" title="%{getText('fileexpimp.viewfile')}"  editable="false" width="120" align="center" sortable="false"  hidden='%{#detfiles_viewFile}' />	
	
	<psjg:gridColumn name="PROG_REF_D00" id="PROG_REF_D00" width="150"
		title="PROG_REF_D00" colType="text"
		sortable="false" editable="false"  index = "PROG_REF_D00" hidden="true"/>		     

	<psjg:gridColumn name="fillArgs" search="false" width="60"
			dialogUrl="/path/fileExportImport/fileExportImportMaintAction_openDynParam?_pageRef=${_pageRef}"
			dialogOptions="{ autoOpen: false, height:425,title:'%{getText('reporting.paramsLbl')}' , width:1150 ,modal: true,
			close: function(){$(this).remove()},
			buttons: { '%{getText('reporting.ok')}': function(){rep_fileExpImp_saveDynParams($(this))}
					  ,'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$( this ).remove()} } }"
			index="fillArgs" colType="dialog" id="fillArgs"
			title="%{getText('reporting.paramsLbl')}" editable="true" editoptions="{maxlength : 20}"
			align="center" sortable="false" 
			 hidden='%{#detfiles_viewFile}' />
</psjg:grid>

 </html>