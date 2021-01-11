<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>

<ps:set name="missingAlert_var" 		value="%{getEscText('reporting.missing')}"/>
<ps:set name="selectEntDbName_var" 		value="%{getEscText('entities.selectEntDbName')}"/>
<ps:set name="loadAllFields_var" 		value="%{getEscText('entities.entLoadAll')}"/>
<ps:set name="unableToModifyField_var" 	value="%{getEscText('entities.unableToModifyField')}"/>
<ps:set name="loadAllTitle_var" 		value="%{getEscText('entities.loadAllTitle')}"/>


<script type="text/javascript">
	var missingAlert 		= '<ps:property value="missingAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var selectEntDbName 	= '<ps:property value="selectEntDbName_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var loadAllFields 		= '<ps:property value="loadAllFields_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var unableToModifyField = '<ps:property value="unableToModifyField_var"  escapeHtml="false" escapeJavaScript="true"/>'
	var loadAllTitle 		= '<ps:property value="loadAllTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'
	
	var entDbName="";


	function deleteEntity(rowId)
	{
	     _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
           if(confirmcChoice)
           {
           	deleteTheEntity(theArgs.rowId)
           }
	      }, {rowId : rowId});
	}

	function deleteTheEntity(rowId)
	{
		myObject = $("#entitiesGridId_"+_pageRef).jqGrid('getRowData',rowId);
	    myObject["entityDBName"]= myObject["ENTITY_DB_NAME"];
	    myObject["_pageRef"]=_pageRef;
		var url = jQuery.contextPath+"/path/entitiesMaint/entities_deleteEntity.action";
	    $.post(url, myObject , function( param )
	 	{
	 		
		   $("#entitiesGridId_"+_pageRef).trigger("reloadGrid");
		   $("#entFieldsGridId_"+_pageRef).trigger("reloadGrid");
		   //empty form
		   $("#lookuptxt_entityName_"+_pageRef).val("");
		   $("#entitiesAlias_"+_pageRef).val("");
		   $("#dateUpdated_"+_pageRef).val("");
		   liveSearch_makeReadOnly(false, "entityName_"+_pageRef);
			$("#lookuptxt_entityName_"+_pageRef).attr('readonly', true);
			$("#newEntity").val('1')
	 	});
	}
	
	function newEntity(){

		var url = $("#entDetFormId_"+_pageRef).attr("action");

		//check empty fields
		if ($("#lookuptxt_entityName_"+_pageRef).val() == "" || $("#entitiesAlias_"+_pageRef).val() == "" )
		{
			_showErrorMsg(missingAlert);
			return;
		}

		var newEntity = $("#newEntity").val();
		if  ( newEntity !='0'){
			$("#newEntity").val('1')
		}

		params = $("#entDetFormId_"+_pageRef).serialize();
		params+="&updates="+ $("#newEntity").val();

		$("#newEntity").val('1')
		;
		
		 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
	 	   if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   {
			
				$("#entitiesGridId_"+_pageRef).trigger("reloadGrid"); //Reload Entities Grid
	
				url = jQuery.contextPath+"/path/entitiesMaint/entities_loadFieldsList.action?entityDBName=";
	
	     		$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
	
				liveSearch_makeReadOnly(false, "entityName_"+_pageRef);//Enabled the field in case it was disabled
				$("#lookuptxt_entityName_"+_pageRef).attr('readonly', true);
				emptyEntities(); //Empty Entity
			}
		   }
			
		});
		
	};

	function emptyEntities()
	{
		$("#lookuptxt_entityName_"+_pageRef).val("");
		$("#entitiesAlias_"+_pageRef).val("");
		$("#dateUpdated_"+_pageRef).val("");
	};

	
	function addFields(){

		if (entDbName==""){
			_showErrorMsg(selectEntDbName);
			return;
		}
		
		var jsonStringUpdates = $("#entFieldsGridId_"+_pageRef).jqGrid('getChangedRowData'); 
	    url = jQuery.contextPath+"/path/entitiesMaint/entities_changeFieldList.action";

		params = {};
		params["updates"] = jsonStringUpdates;
		$.post(url, params , function( param )
			 	{

			   		url = jQuery.contextPath+"/path/entitiesMaint/entities_loadFieldsList.action?entityDBName="+entDbName;

		     		$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");

		     		window.setTimeout("delayFunc()" ,1000);
		     	
			 	});

	};

	function delayFunc()
	{
		$("#entFieldsGridId_"+_pageRef).jqGrid('addInlineRow',{ENTITY_DB_NAME:entDbName});
	}
	
	function checkRowChanged(){
		
	  	selectedRowId = $("#entFieldsGridId_"+_pageRef).jqGrid('getGridParam','selrow');
	  	
	  	fieldDbName = $("#inpt"+selectedRowId+"_FIELD_DB_NAME").val();
		fieldAlias  = $("#"+selectedRowId+"_FIELD_ALIAS").val();

	  	changedRowArr = new Array(2);

		changedRowArr[0] = fieldDbName;
		changedRowArr[1] = fieldAlias;
		
		//Send the Changed Value to the Server
		$.ajax({
		   	
			url: '${pageContext.request.contextPath}/path/entitiesMaint/entities_changeFieldList.action',
			type: "POST",
			data: ({changedRow : changedRowArr}),
		    success: function(xml){

				var url = jQuery.contextPath+"/path/entitiesMaint/entities_loadFieldsList.action?entityDBName="+entDbName;

		     	$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
			}
		});
	};
	
	function okButtonFieldName(){
		
		parentRowId = $("#entFieldsGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var oldField = $("#"+parentRowId+"_oldField").val();
			
		if(oldField=='1'){
			_showErrorMsg(unableToModifyField);
		}
		else{
			rowid = $("#fieldLkupGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		  	myObject = $("#fieldLkupGridId_"+_pageRef).jqGrid('getRowData',rowid);
		  	
		   	$("#inpt"+parentRowId+"_FIELD_DB_NAME").val(myObject["COLUMN_NAME"]);

		   	//$("#"+parentRowId+"_FIELD_DATA_TYPE").val(myObject["COLUMN_TYPE"]); //This needs to be changed whenever the setCell issue is fixed by paty

			//if this --> the dialog button is being replaced by "NUMBER"
			$("#entFieldsGridId_"+_pageRef).jqGrid('setCell',parentRowId,'FIELD_DATA_TYPE',myObject["COLUMN_TYPE"],'',null,true);
			//if this --> the first field is being replaced + dialog button is being removed
			//$("#entFieldsGridId_"+_pageRef).jqGrid('setCell',parentRowId,'FIELD_DB_NAME',myObject["COLUMN_TYPE"],'',null,true);
			//if this --> the first field is being replaced
			//$("#entFieldsGridId_"+_pageRef).jqGrid('setCell',parentRowId,'FIELD_ALIAS',myObject["COLUMN_TYPE"],'',null,true);
			//if this --> the second field is being replaced + is setted automatically to not editable
			//$("#entFieldsGridId_"+_pageRef).jqGrid('setCell',parentRowId,'oldField',myObject["COLUMN_TYPE"],'',null,true);
		}
	    $('#gridDialog').dialog('close');
	}


	function delFields()
	{	
		  _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
	           if(confirmcChoice)
	           {
	        	   deleteTheFields()
	           }
		      }, {});

	}

function deleteTheFields()
{
	
	var rows = $("#entFieldsGridId_"+_pageRef).jqGrid('getGridParam','selarrrow');		
	var arrId;
	if(rows)
	{		
			arrId = new Array(rows.length);	
			var j = 0;	 	
			jQuery.each(rows, function(i, val) {
				selectedRowId = $("#entFieldsGridId_"+_pageRef).jqGrid('getGridParam','selrow');
				if (val==selectedRowId){
					fieldValue = $("#inpt"+selectedRowId+"_FIELD_DB_NAME").val();
					if(fieldValue == null)
					{
						var obj = $("#entFieldsGridId_"+_pageRef).jqGrid('getRowData',val);
						fieldValue = obj.FIELD_DB_NAME;
					}
				}else{
					var obj = $("#entFieldsGridId_"+_pageRef).jqGrid('getRowData',val);
					fieldValue = obj.FIELD_DB_NAME;
				}
				
				arrId[j] = fieldValue;
				j++
			});
		   	$.ajax({
			   	
					url: '${pageContext.request.contextPath}/path/entitiesMaint/entities_deleteFields.action',
					type: "POST",
					data: ({delFieldsList : arrId}),				
				    success: function(xml){

						var url = jQuery.contextPath+"/path/entitiesMaint/entities_loadFieldsList.action?entityDBName="+entDbName;

				     	$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
				     	$.data(document.getElementById($("#entitiesMaintFrmtrack").attr("id")),"changeTrack",true);
	    			}
			});
	}
	
}
	
	function entitiesOk(){
		entDbName = $("#lookuptxt_entityName_"+_pageRef).val();
	}
	
	/*
	On the page load, the code inside the below is executed first.
	*/
	$(document).ready(function()
			{
		    	
				$("#lookuptxt_entityName_"+_pageRef).attr('readonly', true);
				
				$("#entFieldsGridId_"+_pageRef).subscribe('emptyEntTrx', function(event,data) 
				{
				 		$("#entDetFormId_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("");
				});
				
				$("#entFieldsGridId_"+_pageRef).subscribe('addNavButtonFn', 
						function(event,data) 
						{
							var pagerId = "entFieldsGridId_"+_pageRef+"_pager_left";

							var myGrid = $("#entFieldsGridId_"+_pageRef);

							
							myGrid.jqGrid('navButtonAdd',pagerId,{caption:"",title: loadAllTitle, id:"NewButton_"+_pageRef,
							buttonicon :'ui-icon-circle-plus', onClickButton:function(){ 
							//myGrid[0].toggleToolbar() ;
							_showConfirmMsg(loadAllFields, loadAllTitle, function(confirmcChoice, theArgs){
				            if(confirmcChoice)
				            {
				        	   loadAllEntFields(theArgs.tmpCode)
				            }
					       }, {});	
							} });
							
						}
				);

				$("#entFieldsGridId_"+_pageRef).subscribe('disableFeDbName', function(event,data) 
				{
					rowId = (event["originalEvent"])["id"];
					document.getElementById("inpt"+rowId+"_FIELD_DB_NAME").disabled=true;
				});
				
				
	});

	function selectEntity()			
	{
		var rowid = $("#entitiesGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var url = jQuery.contextPath+ "/path/entitiesMaint/entities_selectEntity.action";
							
		var myObject = $("#entitiesGridId_"+_pageRef).jqGrid('getRowData',rowid);
							
		params = {};
		paramStr = JSON.stringify(myObject);
		paramStr = "{"+ "entitiesVO:"+paramStr + "}";
		params["updates"] = paramStr;
		params["_pageRef"]=_pageRef;
		$.post(url, params , function( param )
		{
			$("#entDetFrm").html(param);
			liveSearch_makeReadOnly(true, "entityName_"+_pageRef);
			$("#lookuptxt_entityName_"+_pageRef).attr('readonly', true);

			entDbName =$("#lookuptxt_entityName_"+_pageRef).val();
			$("#newEntity").val("0");

			var url = jQuery.contextPath+"/path/entitiesMaint/entities_loadFieldsList.action?updates=-1&entityDBName="+entDbName;
						     	
			$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid",[{page:1}]);
	
		},"html");
	}

	function loadAllEntFields()
	{
		if (entDbName==""){
			_showErrorMsg(selectEntDbName);
			return;
		}
		
		var url = jQuery.contextPath+"/path/entitiesMaint/entities_loadAllFieldsList.action?entityDBName="+entDbName;
 	
 		$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");

	}

	function SaveAllEntFields()
	{
		
		//check empty required fields
		if ($("#lookuptxt_entityName_"+_pageRef).val() == "" || $("#entitiesAlias_"+_pageRef).val() == "" )
		{
			_showErrorMsg(missingAlert);
			return;
		}
		
		var url = $("#entDetFormId_"+_pageRef).attr("action");
	    
	    params = $("#entDetFormId_"+_pageRef).serialize();
		params+="&updates ="+ $("#newEntity").val();
		
 	    $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
	 	   if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   {
				var res = param['updates'];
				if (res=="-1"){
					_showErrorMsg(missingAlert);
					return;
				}
				else{
					$("#entitiesGridId_"+_pageRef).trigger("reloadGrid");
			   		var url = jQuery.contextPath+"/path/entitiesMaint/entities_loadFieldsList.action?entityDBName=";
		     		$("#entFieldsGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
		     		emptyEntities(); //Empty Entity
		     		$.data(document.getElementById($("#entitiesMaintFrmtrack").attr("id")),"changeTrack",false);
				}
			 }
		  }
	 	});
	 }
	</script>

	
			<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
			<div title="<ps:text name='entities.entList'/>"> 
			
				<ps:url id="urlEntTag" action="entities_loadEntitiesList" namespace="/path/entitiesMaint" />
					<psjg:grid 
					id="entitiesGridId_RD00EMT" 
					gridModel="gridModel" 
					dataType="json" 
					href="%{urlEntTag}"
					pager="true" 
					navigator="true" 
					navigatorSearch="true"
					navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
					navigatorEdit="false" 
					navigatorAdd="true" 
					navigatorDelete="true"
					addfunc="newEntity" 
					delfunc="deleteEntity" 
					ondblclick="selectEntity()"
					navigatorRefresh="true" 
					rowNum="3" 
					rowList="3,5,10,15,20"
					onCompleteTopics="emptyEntTrx"
					sortable="true">
					<psjg:gridColumn name="ENTITY_DB_NAME" id="entityDbName"
						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
						title="%{getText('entities.entName')}" colType="text"
						editable="false" sortable="true" />
					<psjg:gridColumn name="ENTITY_ALIAS" id="entityAlias"
						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
						title="%{getText('entities.Alias')}" colType="text" editable="false"
						sortable="true" />
					</psjg:grid>
			</div>
			
		 	 
				
			
			
			<div>
		 	 	<h1 class="headerPortionContent ui-widget ui-state-default">
					<ps:label value="%{getText('entities.entDet')}"/>
				</h1>
			</div>
			<div class="headerPortion">
 	 			<div id="entDetFrm">
					<%@include file="EntityMaint.jsp"%>
				</div>
				<ps:hidden name="newEntity" id="newEntity"></ps:hidden>
			</div>
			
			
			
			<div style="height:50"></div>
			
		 
	 
 	 		<ps:collapsgroup id="EntFieldsDetails_${_pageRef}">
				<ps:collapspanel id="EntFieldsDetDiv_${_pageRef}"  key="entities.entFieldList">
 	 			<ps:form applyChangeTrack="true" id="entitiesMaintFrmtrack" name="entMainTrackForm" action="entities_loadEntitiesList" useHiddenProps="true" namespace="/path/entitiesMaint">			
 	 				<ps:url id="urlEntityTag" action="entities_loadFieldsList" namespace="/path/entitiesMaint" >

 	 				</ps:url>
					<div id="entFieldsGrid" style="overflow: hidden;width:100%;">
						<psjg:grid 
						id="entFieldsGridId_${_pageRef}"
						gridModel="gridModel" 
						dataType="json" 
						href="%{urlEntityTag}"
						pager="true" 
						navigator="true" 
						navigatorSearch="false"
						navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
						navigatorEdit="false" 
						navigatorAdd="true" 
						navigatorDelete="true"
						addfunc="addFields" 
						delfunc="delFields" 
						navigatorRefresh="false" 
						rowNum="15" 
						rowList="3,5,10,15,20"
						sortable="true"
						editurl="%{urlEntityTag}"
						editinline="true"
						multiselect="true"
						multiboxonly="true"
						viewrecords="true" onEditInlineBeforeTopics="disableFeDbName"
						onGridCompleteTopics="addNavButtonFn"
						>
						<psjg:gridColumn name="ENTITY_DB_NAME" id="fieldEntDbName" title="entity db name" 
							colType="text" hidden="true" editable="true" sortable="false" />

						<psjg:gridColumn name="FIELD_DB_NAME" id="fieldDbName" index="FIELD_DB_NAME" 
							title="%{getText('reporting.fieldName')}" 
							colType="dialog" 
							editable="true" 
							dialogOptions=" { autoOpen: false,close: function(){$(this).remove()}, height:300,title:'%{getText('entities.entFieldLkup')}' , width:530 ,modal: true, buttons: { '%{getText('reporting.ok')}': function() {okButtonFieldName();$(this).dialog('close');$(this).remove()},'%{getText('reporting.cancel')}':function(){$(this).dialog('close');$(this).remove()} } }"
							dialogUrl="/path/entitiesMaint/entities_openSelFieldsDialog.action?_pageRef=${_pageRef}"
							sortable="true" width="50"
							/>
						
						<psjg:gridColumn name="FIELD_ALIAS" id="fieldAlias" index="FIELD_ALIAS"
							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"
							title="%{getText('entities.Alias')}" colType="text"
							editable="true" sortable="true" width="100" 
							editoptions="{maxlength:50}"
							/>
							
						<psjg:gridColumn name="FIELD_DATA_TYPE" id="fieldType" index="FIELD_DATA_TYPE" width="50" 
							title="%{getText('entities.entFieldType')}" 
							colType="select"
							formatter="select"
							editable="false" sortable="true" align="center"	edittype="select" 
							editoptions="{value:'java.lang.String:%{getText('reporting.varchar')};java.util.Date:%{getText('reporting.date')};java.math.BigDecimal:%{getText('reporting.number')};JAVA.LANG.STRING:%{getText('reporting.varchar')};JAVA.UTIL.DATE:%{getText('reporting.date')};JAVA.MATH.BIGDECIMAL:%{getText('reporting.number')}'}"  
							
							/>
						
						<psjg:gridColumn name="oldField" id="oldField" index="oldField" width="50" 
							title="Old Field" colType="text" editable="true" hidden="true"></psjg:gridColumn>
							
						
						
						</psjg:grid>
					</div>
						</ps:form>
					</ps:collapspanel>
					</ps:collapsgroup>
			
		
		
 
		
	<ptt:toolBar  id="entitiesTlBar"  > 
	<psj:submit button="true" buttonIcon="ui-icon-disk" onclick="SaveAllEntFields()" >
		<ps:text name="reporting.save"></ps:text>
	</psj:submit>
	</ptt:toolBar>

<ps:form applyChangeTrack="true" id="fieldsGridform" action="saveFields" namespace="/path/entitiesMaint">
		<ps:hidden name="updates" id="updates_${_pageRef}"></ps:hidden>
</ps:form>
<script type="text/javascript"> 
    $("#entitiesGridId_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
</script>