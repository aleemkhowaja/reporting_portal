<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>


<script type="text/javascript">

	function addHashTbl()
	{
		var jsonStringUpdates = $("#hashTblGridId_"+_pageRef).jqGrid('getAllRows');
		$("#updatesHashTbl_"+_pageRef).val(jsonStringUpdates); 
		var url = $("#hashTblForm_"+_pageRef).attr("action");
		url +="?_pageRef="+_pageRef;
		myObject =  $("#hashTblForm_"+_pageRef).serialize();
		$.post(url, myObject , function( param )
	 	 {
	 	 });	
		$("#hashTblGridId_"+_pageRef).jqGrid('addInlineRow',{});
			
	}
	
	function deleteHashTbl(sel_row_index)
	{
		var args = {selRow: sel_row_index};
		var selRow = args.selRow;
		var hashTblId = $("#hashTblGridId_"+_pageRef).jqGrid('getCell', selRow, 'irpHashTableVO.HASH_TABLE_ID');	
		
		$.ajax({
		url: jQuery.contextPath+'/path/designer/hashTbl_delFrmHashTblMap.action',
		type: "POST",
		data: ({hashTblId : hashTblId, _pageRef:_pageRef}),				
	    success: function(param){
  			}
		});
		$("#hashTblGridId_"+_pageRef).jqGrid('delRowData',selRow);//to remove selected row
		
	}

</script>




<ps:url id="urlGrid" action="hashTbl_loadHashTblList" namespace="/path/designer">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="hashTblGridId_${_pageRef}"
    	dataType="json" 
    	href="%{urlGrid}" 
    	gridModel="gridModel"
    	pager="true" 
    	rowNum="10"
    	rowList="5,10,15,20"
    	viewrecords="true"
    	editinline="true"
		editurl="%{urlGrid}"
    	navigator="true"	
    	navigatorAdd="true" 
    	navigatorEdit="false" 
    	addfunc="addHashTbl"
    	delfunc="deleteHashTbl"
    	navigatorSearch="false "
		navigatorDelete="true"
		navigatorRefresh="false"
		
    	>
    	
    	<psjg:gridColumn name="irpHashTableVO.HASH_TABLE_ID" id="HASH_TABLE_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="HASH_TABLE_ID"  colType="number"  title="%{getText('upDown.hashTblId')}"  sortable="true"  width="25"  hidden="false" editable="false"/>

    	<psjg:gridColumn name="irpHashTableVO.HASH_TABLE_NAME" index="HASH_TABLE_NAME" id="HASH_TABLE_NAME"  
			     	 	 editoptions="{ readonly: 'readonly'}"
			     	 	 title="%{getText('upDown.hashTblName')}" 
			     	 	 editable="true" 
			     	 	 sortable="false" 
			     	 	 colType="liveSearch" searchElement="HASH_TABLE_ID"
			     	 	 dataAction="${pageContext.request.contextPath}/path/designer/hashTbl_hashTblLookup.action?_pageRef=${_pageRef}" 
			     	 	 resultList="HASH_TABLE_ID:irpHashTableVO.HASH_TABLE_ID,HASH_TABLE_NAME:irpHashTableVO.HASH_TABLE_NAME_lookuptxt" 
			     	 	 paramList="_pageRef:_pageRef"
			     	 	 width="25"/> 
		<psjg:gridColumn name="REPORT_ID" id="REPORT_ID" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="REPORT_ID"  colType="number"  title="%{getText('reportId')}"  sortable="true"  width="25"  hidden="true" editable="false"/>
    </psjg:grid>
    
    
 <ps:form id="hashTblForm_${_pageRef}" action="hashTbl_putInHashTblMap" namespace="/path/designer">
	<ps:hidden name="updates" id="updatesHashTbl_${_pageRef}"></ps:hidden>
</ps:form>      
    