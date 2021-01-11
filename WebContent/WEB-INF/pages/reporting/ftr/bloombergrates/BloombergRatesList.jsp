<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="saveConfirm_var" 		value="%{getEscText('blrgRates.save')}"/>
<ps:set name="saveMsg_var" 		value="%{getEscText('blrgRates.saveMsg')}"/>


<script  type="text/javascript">
var saveConfirm 		= '<ps:property value="saveConfirm_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
var saveMsg 		= '<ps:property value="saveMsg_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

</script>
<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
<ps:url id="urlbloombergRatesListGrid" escapeAmp="false" action="BloombergRatesListAction_loadBloombergRatesGrid" namespace="/path/bloombergRates">
   <ps:param name="iv_crud"  value="ivcrud_${_pageRef}"></ps:param>
   <ps:param name="_pageRef" value="_pageRef"></ps:param>
</ps:url>

	<psjg:grid
		id               ="bloombergRatesListGridTbl_Id_${_pageRef}"
		caption          =" "
	    href             ="%{urlbloombergRatesListGrid}"
	    dataType         ="json"
		pager            ="true"
		sortable         ="true"
		filter           ="true"
		gridModel        ="gridModel"
		rowNum           ="5"
		rowList          ="5,10,15,20"
	    viewrecords      ="true"
	    navigator        ="true"
	    navigatorDelete  ="false"
	    navigatorEdit    ="false"
	    navigatorRefresh ="true"
	    navigatorAdd     ="false"
	    navigatorSearch  ="true"
	    navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true,sopt:['eq','ne','lt','gt','le','ge']}"
	    altRows          ="true"
	    ondblclick       ="bloombergRatesList_onDbClickedEvent()"
	    addfunc          ="bloombergRatesList_onAddClicked"
	    shrinkToFit      ="true" 
	    editinline="true"
	    editurl ="%{urlbloombergRatesListGrid}"
	    onCompleteTopics="emptyCrtTrx"
	    onPagingTopics="saveCurrentRates"
	    >
	    <psjg:gridColumn name="ftrRateUploadVO.COMP_CODE" id="COMP_CODE" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="COMP_CODE"    colType="number"    title="COMP_CODE"   sortable="false"  editable="false" hidden="true"/>
		<psjg:gridColumn name="ftrRateUploadVO.CURRENCY_CODE" id="CURRENCY_CODE" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="CURRENCY_CODE"    colType="number"    title="%{getText('line.currency')}"    sortable="true"  editable="false"/>
		<psjg:gridColumn name="ftrRateUploadVO.VALUE_DATE" id="VALUE_DATE" index="VALUE_DATE"    colType="date"    title="%{getText('ctrlRecord.valueDate')}"  sortable="true"  editable="false"/>
		<psjg:gridColumn name="ftrRateUploadVO.DISC_FACTOR" id="DISC_FACTOR" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="DISC_FACTOR"    colType="decimal"   title="%{getText('blrgRates.discFactor')}"    sortable="true"  editable="false"/>
		<psjg:gridColumn name="ftrRateUploadVO.RATE" id="RATE" searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="RATE"    colType="decimal"    title="%{getText('blrgRates.rate')}"    sortable="true"  editable="false"/>
		<psjg:gridColumn name="ftrRateUploadVO.ADJUST_RATE" id="ADJUST_RATE"  index="ADJUST_RATE" colType="number"  nbFormat="###0.#####" maxValue="999999" title="%{getText('blrgRates.adjustRate')}"   minValue="0"   sortable="true"  editable="true" required="true" />
		<psjg:gridColumn name="oldAdjustRate" id="oldAdjustRate"  index="oldAdjustRate"    colType="number"  nbFormat="###0.#####"  title="oldAdjustRate"   sortable="true"  editable="false" hidden="true"/>
		<psjg:gridColumn name="ftrRateUploadVO.NET_RATE" id="NET_RATE"  index="NET_RATE"    colType="number"  nbFormat="###0.#####"   title="%{getText('blrgRates.netRate')}"   sortable="true"  editable="false"/>
		<psjg:gridColumn name="ftrRateUploadVO.DATE_UPDATED" id="DATE_UPDATED"  formatoptions="{srcformat:'Y-m-d H:i:s.u',newformat:'d/m/Y H:i:s:u' }"   searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" index="DATE_UPDATED"    colType="date"    title="DATE_UPDATED"   sortable="true"  editable="false" hidden="true"/>
	</psjg:grid>
<br/>

<div>
	<h1 class="headerPortionContent ui-widget ui-state-default">
		<ps:label value="%{getText('blrgRates.blgRate')}" />
	</h1>
</div>
<div id="bloombergRatesListMaintDiv_id_<ps:property value="_pageRef" escapeHtml="true"/>" >
      <%@include file="BloombergRatesMaint.jsp"%>
</div>

	<table width="100%">
		<tr>
			<td>
			<ptt:toolBar id="bloombergRatesMaintToolBar_${_pageRef}" hideInAlert="true">
				<psj:a button="true" href="#" buttonIcon="ui-icon-disk" 
					   onclick="saveBlgRates()"
						id="saveBlgRates_${_pageRef}">
						<ps:text name="%{getText('opt.Save')}"></ps:text>
				</psj:a>
				</ptt:toolBar>
			</td>
		</tr>
  	</table>
  	
<script  type="text/javascript">

    $.struts2_jquery.require("BloombergRatesList.js,BloombergRatesMaint.js" ,null,jQuery.contextPath+"/path/js/reporting/ftr/bloombergrates/");
    $("#gview_bloombergRatesListGridTbl_Id_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
    
    $("#bloombergRatesListGridTbl_Id_"+_pageRef).subscribe('emptyCrtTrx',function(event, data) 
	{
		$("#bloombergRatesMaintFormId_ #auditTrxNbr_"+_pageRef).val("")
	});
	
	
$("#bloombergRatesListGridTbl_Id_"+_pageRef).subscribe('saveCurrentRates',function(event, data) 
{
var jsonStringUpdates = $("#bloombergRatesListGridTbl_Id_" + _pageRef).jqGrid('getChangedRowData');
var rowid      = $("#bloombergRatesListGridTbl_Id_"+_pageRef).jqGrid('getGridParam','selrow');

	if(jsonStringUpdates!="")
	{
		 _showConfirmMsg(saveMsg, saveConfirm, function(confirmcChoice, rowid){
		           if(confirmcChoice)
		           {
		           		$("#updatesBlgRatesList_" + _pageRef).val(jsonStringUpdates);
							var url = $("#saveBlgRatesForm_"+_pageRef).attr("action");
							url +="?_pageRef="+_pageRef;
					
							myObject =  $("#saveBlgRatesForm_"+_pageRef).serialize();
							
							 $.ajax({
							 url: url,
					         type:"post",
							 dataType:"json",
							 data: myObject,
							 success: function(param)
							 {
							 }
					    });
		           }
			      }, {rowid : rowid});	
	
	}

});	
   
</script>

 <ps:form id="saveBlgRatesForm_${_pageRef}" action="BloombergRatesListAction_saveBlgRates" namespace="/path/bloombergRates">
	<ps:hidden name="updatesBlgRatesList" id="updatesBlgRatesList_${_pageRef}"></ps:hidden>
	 
</ps:form> 

