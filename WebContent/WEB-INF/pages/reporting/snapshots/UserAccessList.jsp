<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>
<ps:set name="user_var" 				value="%{getEscText('archive.user')}"/>
<ps:set name="selUserError_var" 		value="%{getEscText('userAccess.selUser')}"/>
<ps:set name="savedSuccess_var" 		value="%{getEscText('upDown.argProcSaved')}"/>

<script type="text/javascript">
var user 					= '<ps:property value="user_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var selUserError 			= '<ps:property value="selUserError_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var savedSuccess 			= '<ps:property value="savedSuccess_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function() 
{
	$.struts2_jquery.require("UserAccessList.js" ,null, jQuery.contextPath+"/path/js/reporting/snapshots/");
	user_access_ready_func();
});

</script>

<ps:form id="userAccessForm_${_pageRef}" useHiddenProps="true" validate="true">	

<div id="userAccessDiv_<ps:property value="_pageRef" escapeHtml="true"/>">

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
	<ps:url id="urlUserGrid" action="userAccessAction_loadUserAccessGrid" namespace="/path/userAccess">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
	<table >
		<tr>
			<td style="width: 328px;">	
		<div  style="overflow: hidden; width: 100%;">
			    <psjg:grid
			    	id="userGridId_${_pageRef}"
			    	dataType="json" 
			    	href="%{urlUserGrid}" 
			    	gridModel="gridModel"
			    	pager="true" 
			    	rowNum="10"
			    	rowList="5,10,15,20"
			    	viewrecords="true"
			    	navigator="false"	
			    	navigatorAdd="false" 
			    	navigatorEdit="false"
			    	navigatorDelete="false"
			    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			    	onSelectRowTopics="loadUserViewed"
			     >
			      	<psjg:gridColumn name="USER_ID"  id="USER_ID_${_pageRef}" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="userId"      colType="text"  title="%{getText('connection.userId')}" align="center"     sortable="false"  width="15"  hidden="false"/>
			    	
			    </psjg:grid>
			 </div>   
			</td>
	
	<ps:url id="urlViewedUserGrid" action="userAccessAction_loadViewedUserGrid" namespace="/path/userAccess">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url> 			
			<td style="width: 328px;">	
			<div>
			    <psjg:grid
			    	id="viewedUserGridId_${_pageRef}"
			    	dataType="json" 
			    	href="%{urlViewedUserGrid}" 
			    	gridModel="gridModel"
			    	pager="true" 
			    	rowNum="10"
			    	rowList="5,10,15,20"
			    	viewrecords="true"
			    	navigator="false"	
			    	navigatorAdd="false" 
			    	navigatorEdit="false"
			    	navigatorDelete="false"
			    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			    	onCompleteTopics="checkViewedUsers"
			    	onPagingTopics="putInAccessUserHashMap"
			     >
			      	<psjg:gridColumn name="USER_ID" key="true" id="USER_ID_${_pageRef}" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="userId"      colType="text"  title="%{getText('connection.userId')}" align="center"     sortable="false"  hidden="false"/>
			    	<psjg:gridColumn name="intCheckBox"  id="intCheckBox_${_pageRef}" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="intCheckBox"      colType="checkbox"   title="%{getText('userAccess.sel')}" align="center"  formatter="checkbox"   sortable="false" edittype="checkbox" editable="true" hidden="false" formatoptions="{disabled : false}"/>
					<psjg:gridColumn name="USER_ID" index="USER_ID"	title="%{getText('reporting.viewReports')}" colType="text" editable="false" sortable="false" search="false"	formatter="showlink" formatoptions="{baseLinkUrl: 'javascript:',showAction:'user_access_load_reports(\"',addParam:'\");'}" />
				</psjg:grid>
					</div> 
			    </td>
	<ps:url id="urlBranchGrid" action="userAccessAction_loadBranchGrid" namespace="/path/userAccess">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url> 			
			<td  style="width: 328px;">	
			<div>
			<span id="showHideBranchGrid_${_pageRef}" style="">	
			    <psjg:grid
			    	id="branchGridId_${_pageRef}"
			    	dataType="json" 
			    	href="%{urlBranchGrid}" 
			    	gridModel="gridModel"
			    	pager="true" 
			    	rowNum="10"
			    	rowList="5,10,15,20"
			    	viewrecords="true"
			    	navigator="false"	
			    	navigatorAdd="false" 
			    	navigatorEdit="false"
			    	navigatorDelete="false"
			    	navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
			    	onCompleteTopics="checkViewedUsers"
			    	onPagingTopics="putInBranchHashMap"
			 
			     >
			      	<psjg:gridColumn name="EMP_BRANCH_CODE"  id="EMP_BRANCH_CODE_${_pageRef}" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="EMP_BRANCH_CODE"      colType="text"  title="%{getText('gl.branchCode')}" align="center"     sortable="false"  width="15"  hidden="false"/>
			    	<psjg:gridColumn name="branchCheckBox"  id="branchCheckBox_${_pageRef}" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="branchCheckBox"      colType="checkbox"   title="%{getText('userAccess.sel')}" align="center"  formatter="checkbox"   sortable="false" edittype="checkbox"  width="15" editable="true" hidden="false" formatoptions="{disabled : false}"/>
			    	<psjg:gridColumn name="BRANCH_DESC"    id="BRANCH_DESC" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="FIRST_NAME_ARABIC"      colType="text"  title="%{getText('reporting.description')}" align="left"     sortable="false"  width="15"  hidden="false"/>
			    	<psjg:gridColumn name="DATE_UPDATED"    id="DATE_UPDATED" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="DATE_UPDATED"      colType="date"  title="DATE_UPDATED" align="left"     sortable="false"  width="15"  hidden="true"/>
			    	
			    </psjg:grid>
			   </span>	
			   </div>   
			    </td>
		</tr>
	</table>
<br/>
		<ptt:toolBar id="saveUserAccess_${_pageRef}">
			<psj:submit button="true" buttonIcon="ui-icon-disk">
				<ps:label key="reporting.save"></ps:label>
			</psj:submit>
		</ptt:toolBar>
		<ps:hidden name="userAccessCO.eodViewer" id="eodViewer_${_pageRef}"></ps:hidden>
				<ps:hidden name="updatesUserViewedList" id="updatesUserViewedList_${_pageRef}"></ps:hidden>
				<ps:hidden name="updatesBranchList" id="updatesBranchList_${_pageRef}"></ps:hidden>  
		<ps:hidden id="DATE_UPDATED_${_pageRef}" name="userAccessCO.DATE_UPDATED" />
	</div>
</ps:form>

<br/>
<ps:collapsgroup id="repUsr_${_pageRef}">
	<ps:collapspanel id="repUsrFrm_${_pageRef}" key="reports.list_key">
		<div>
			<ps:url id="urlGrid" action="snapShotAction_loadUserAccessSnpList" namespace="/path/snapShot">
				<ps:param name="_pageRef" value="_pageRef"></ps:param>
			</ps:url>
			<psjg:grid id="usrAccessRepGrid_${_pageRef}" dataType="json"
				href="%{urlGrid}" gridModel="gridModel" pager="true" rowNum="10"
				filter="true" rowList="5,10,15,20" viewrecords="true"
				navigator="true"
				navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true,multipleSearch: true}"
				navigatorAdd="false" navigatorEdit="false" navigatorDelete="false">
				<psjg:gridColumn name="REPORT_NAME"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="REPORT_NAME" colType="text" title="%{getText('reportName')}"
					sortable="false" search="true" />
				<psjg:gridColumn name="REPORT_FORMAT_TRANS"  width="45"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
					index="REPORT_FORMAT_TRANS" colType="text" title="%{getText('reportFormat')}"
					sortable="false" search="true" />
				<psjg:gridColumn name="BRANCH_CODE"  width="45"
					searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"
					index="BRANCH_CODE" colType="number"
					title="%{getText('gl.branchCode')}" sortable="false" search="true" />
			</psjg:grid>
		</div>
	</ps:collapspanel>
</ps:collapsgroup>
<script type="text/javascript">
document.getElementById('userGridId_'+_pageRef+'_pager_right').style.display='none';
document.getElementById('viewedUserGridId_'+_pageRef+'_pager_right').style.display='none';
document.getElementById('branchGridId_'+_pageRef+'_pager_right').style.display='none';
</script>