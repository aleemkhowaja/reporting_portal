<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<html>
	<body>
		<table border="0" id="selFieldLkup_${_pageRef}">
		 <tr>
		   <td>
	   			<ps:url var="urlFieldsLkupTag" action="entities_loadFieldLkpList" namespace="/path/entitiesMaint">
	   			<ps:param  name="entityDBName" value="ENTITY_DB_NAME"></ps:param>
	   			<ps:param  name="COLUMN_NAME" value="COLUMN_NAME"></ps:param>
	   			</ps:url>
	   			
				<psjg:grid  id="fieldLkupGridId_${_pageRef}"
					gridModel="gridModel"
					dataType="json"
					href="%{urlFieldsLkupTag}"
					pager="true"
					navigator="true"
					navigatorAdd="false"
					navigatorDelete="false"
					navigatorEdit="false"
					navigatorRefresh="false"
					navigatorView="false"
					navigatorSearch="false"
	         		rowNum="6" 
	   	  			rowList="5,7,10,15,20"
	   	  			viewrecords="true"
	   	  			>
	    			<psjg:gridColumn name="COLUMN_NAME" id="ffieldDb" index="COLUMN_NAME"   colType="text" title="%{getText('reporting.fieldName')}"   editable="false" sortable="false" hidden="false"/>
					<psjg:gridColumn name="COLUMN_TYPE" id="ffieldType" index="COLUMN_TYPE" 
								title="%{getText('entities.entFieldType')}" 
								colType="select"
								formatter="select"
								editable="false" sortable="true" align="center"	edittype="select" 
								editoptions="{value:'java.lang.String:%{getText('reporting.varchar')};java.util.Date:%{getText('reporting.date')};java.math.BigDecimal:%{getText('reporting.number')};JAVA.LANG.STRING:%{getText('reporting.varchar')};JAVA.UTIL.DATE:%{getText('reporting.date')};JAVA.MATH.BIGDECIMAL:%{getText('reporting.number')}'}" 
								
								/>
				</psjg:grid>
		   </td>
		 </tr>
		</table>	
		
		<ps:hidden name="ENTITY_DB_NAME" id="ENTITY_DB_NAME"></ps:hidden>
		
	<script type="text/javascript"> 
	 //$('#fieldLkupGridId_'+_pageRef ).jqGrid("setColProp","FIELD_DB_NAME",{width:50});
	 //$('#fieldLkupGridId_'+_pageRef ).jqGrid("setColProp","FIELD_ALIAS",{width:200});
	 //$('#fieldLkupGridId_'+_pageRef ).jqGrid("setColProp","FIELD_DATA_TYPE",{width:150});
     $("#fieldLkupGridId_"+_pageRef ).jqGrid("setGridWidth",500);	
     //document.getElementById("fieldLkupGridId_"+_pageRef).style.width="450px";
     document.getElementById("selFieldLkup_"+_pageRef).style.width="450px";
     
     
	</script>
	</body>



</html>


