<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="missingRepAlert_var" 		value="%{getEscText('csv.missingRep')}"/>
<ps:set name="fill_Line" 	value="%{getEscText('timeBuckets.fillLine')}"/>

<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<ps:url id="urlcsvItemsListGrid" escapeAmp="false" action="CsvItemsListAction_loadCsvItemsGrid" namespace="/path/csvItems">
   <ps:param name="iv_crud"  value="ivcrud_${_pageRef}"></ps:param>
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>
<psjg:grid
	id               ="csvItemsListGridTbl_Id_${_pageRef}"
	caption          =" "
    href             ="%{urlcsvItemsListGrid}"
    dataType         ="json"
    hiddengrid       ='true'
	pager            ="true"
	sortable         ="true"
	filter           ="true"
	gridModel        ="gridModel"
	rowNum           ="5"
	rowList          ="5,10,15,20"
    viewrecords      ="true"
    navigator        ="true"
    navigatorDelete  ="true"
    navigatorEdit    ="false"
    navigatorRefresh ="false"
    navigatorAdd     ="false"
    navigatorSearch  ="true"
    navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true,sopt:['eq','ne','lt','gt','le','ge']}"
    altRows          ="true"
    ondblclick       ="csvItemsList_onDbClickedEvent()"
    addfunc          ="csvItemsList_onAddClicked"
    height="125"
    delfunc="deleteAllCsvItemByRep"
    onCompleteTopics="emptyCrtTrx"
    
    >
	
	<psjg:gridColumn name="cbkRptLineVO.REP_REF" id="REP_REF" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="REP_REF"    colType="text"    title="%{getText('progRef')}"   sortable="true"  editable="true"/>
	<psjg:gridColumn name="reportName"  id="reportName" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="reportName"      colType="text"   title="%{getText('reportName')}"      sortable="true"   hidden="false"/>
	
</psjg:grid>

<div>
	<h1 class="headerPortionContent ui-widget ui-state-default">
		<ps:label value="%{getText('csv.csvItem')}" />
	</h1>
</div>
<div id="csvItemsListMaintDiv_id_<ps:property value="_pageRef" escapeHtml="true"/>">
     <%@include file="CsvItemsMaint.jsp"%>
</div>
<ps:form id="csvItemsByRepForm_${_pageRef}" useHiddenProps="true" validate="true">	

		<ps:url id="urlcsvItemsByRepListGrid" escapeAmp="false" action="CsvItemsListAction_loadCsvItemsByRepGrid" namespace="/path/csvItems">
		   <ps:param name="iv_crud"  value="ivcrud_${_pageRef}"></ps:param>
		   <ps:param name="_pageRef" value="_pageRef"></ps:param>
			</ps:url>
		<psjg:grid
		    
				id="csvItemsByRepListGridTbl_Id_${_pageRef}"
		    	dataType="json" 
		    	href="%{urlcsvItemsByRepListGrid}" 
		    	gridModel="gridModel"
		    	pager="true" 
		    	rowNum="100"
		    	rowList="5,10,15,20"
		    	viewrecords="true"
		    	editinline="true"
				editurl="%{urlcsvItemsByRepListGrid}"
		    	navigator="true"	
		    	navigatorAdd="true" 
		    	navigatorEdit="false" 
		    	addfunc="addCsvItem"
		    	delfunc="deleteCsvItem"
		    	navigatorSearch="false "
				navigatorDelete="true"
				navigatorRefresh="false"
				caption          =" "
				altRows          ="true"
				navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true,sopt:['eq','ne','lt','gt','le','ge']}"
		    >
		    
			
			<psjg:gridColumn name="cbkRptLineVO.LINE_NBR"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="LINE_NBR"      colType="number"  title="%{getText('line.lineNbr')}"       sortable="false"   hidden="false" editable="false" />
			<psjg:gridColumn name="cbkRptLineVO.CSV_ITEM_ID"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"  editoptions="{maxlength : 12}" index="CSV_ITEM_ID"  colType="text"  title="%{getText('csv.csvItemId')}"   editable="true"    sortable="false"   hidden="false" required="true"/>
			<psjg:gridColumn name="cbkRptLineVO.DATE_UPDATED"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="cbkRptLineVO.DATE_UPDATED"      colType="date"  title="DATE_UPDATED"   editable="true"    sortable="true"   hidden="true"/>
			<psjg:gridColumn name="oldCsvItemId"  searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="oldCsvItemId"      colType="number"  title="oldCsvItemId"   editable="true"    sortable="true"   hidden="true"/>
			<psjg:gridColumn name="cbkRptLineVO.REP_REF" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="REP_REF"    colType="text"    title="%{getText('progRef')}"   sortable="true"  editable="true" hidden="true"/>
			
		</psjg:grid>
</ps:form>
	<table  width="100%" >
		<tr>
			<td>
				
				<ptt:toolBar id="csvItemMaintToolBar_${_pageRef}" hideInAlert="true">
				<psj:a button="true" href="#" buttonIcon="ui-icon-disk" 
					   onclick="saveCsvItems()"
						id="saveCsvItems_${_pageRef}">
						<ps:text name="%{getText('opt.Save')}"></ps:text>
				</psj:a>
				</ptt:toolBar>
	
			</td>
		</tr>
  	</table>
		
<script  type="text/javascript">

var missingRepAlert 	= '<ps:property value="missingRepAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var missingLine   = '<ps:property value="fill_Line"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function() 
{

	$("#csvItemsListGridTbl_Id_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	   {
			$("#csvItemsMaintFormId_ #auditTrxNbr_"+_pageRef).val("")
	   });

    $.struts2_jquery.require("CsvItemsList.js,CsvItemsMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/csvitems/");
    $("#gview_csvItemsListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
    $("#gview_csvItemsByRepListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
 });   
    
     $("#csvItemsListGridTbl_Id_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
    
</script>


 <ps:form id="saveCsvItemsForm_${_pageRef}" action="CsvItemsListAction_saveCsvItems" namespace="/path/csvItems">
	<ps:hidden name="updates" id="updatesCsvList_${_pageRef}"></ps:hidden>
	<ps:hidden name="deletedCsvRow" id="deletedCsvList_${_pageRef}"></ps:hidden>
	 
</ps:form>  