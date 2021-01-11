<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@taglib prefix="toolbar" uri="/path-toolbar-tags"%>
<%@taglib prefix="pt" uri="/path-toolbar-tags"%> 

<ps:set name="list_key_var" 		value="%{getEscText('reports.list_key')}"/>
<ps:set name="loose_all_data" 		value="%{getEscText('reportsMismatch.looseAllData')}"/>


<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />
<script type="text/javascript">
var list_key 	= 	'<ps:property value="list_key_var"  escapeHtml="false" escapeJavaScript="true"/>'
var loose_data_key 	= 	'<ps:property value="loose_all_data"  escapeHtml="false" escapeJavaScript="true"/>'


	$(document).ready(function()
	{
		$.struts2_jquery.require("Main.js", null,jQuery.contextPath + "/path/js/reporting/designer/");	
		
		$.subscribe('emptyFrmPrev', function(event,data) 
		{
			$("#updateGrp_"+_pageRef).val("");
		});
		
		$("#qryDialg").subscribe('showSqll', function(event,data)
		{
			if(sqbQry=="true" && mode == "edit")
			{
				  $('#queriesDiv').load("${pageContext.request.contextPath}/loadQueriesMenu.action");
			}
			else if(cancelQry=="false") {
				if(mode == "new")
				{
					cancelQry="true";
					var zSrc="${pageContext.request.contextPath}/path/designer/wizard_generateSql";
					params ={};
					$.getJSON(zSrc, params, function( data2, status, xhr ) 
					{
					   $("#syntax_"+_pageRef).val(data2['syntax']);
					});
					//reload the grids of chooseFields section
			 		$("#allFieldsGrid").trigger("reloadGrid");
			 		$("#displFieldsGrid").trigger("reloadGrid");
				}
				else if(mode == "edit") {
					var zSrc="${pageContext.request.contextPath}/path/designer/reportDesign_updateQuery?queryName="+queryName;
					params ={};
					$.getJSON(zSrc, params, function( data2, status, xhr ) 
					{
					   $('#queriesDiv').load("${pageContext.request.contextPath}/loadQueriesMenu.action");
					});
				}
			}
	
		 	//remove dialog content
		    $('#qryDialg').empty();
		}); 
	
	
		$("#paramDialog").subscribe('closeParamDialog', function(event,data) 
		{
			//remove dialog content
		    $('#paramDialog').empty();
		    //remove reportParams from session
		    var url = jQuery.contextPath+ "/path/designer/queryDesign_clearRepParamsSession";
			params ={};
		    $.get(url, params , function( param )
		 	{
		 	});
		});	
		
		$("#wizardDialog").subscribe('wizardOnClose', function(event,data) 
		{
			var url = jQuery.contextPath+ "/path/designer/reportDesign_clearQrySession";
			params ={};
		    $.get(url, params , function( param )
		 	{
		 	});
		    
		 	//reset the report in the session to the old one opened in the editor
		    if(!wizardSubmit)
		    {
				$.ajax({
					url: '${pageContext.request.contextPath}/path/designer/wizard_resetReport'
				});
		    }
		}); 
	
		$("#uploadImageDialog").subscribe('refreshImgGrid', function(event,data) 
		{
			$("#imageGrid").trigger("reloadGrid");
		});
		
		$("#saveAsRepDialog").subscribe('closeSaveAsRepDlg', function(event,data) 
		{
			$('#saveAsRepDialog').empty();
		});
		
	});

	


 var resizeTimer;
 	$(window).bind('resize', function () {
	    clearTimeout(resizeTimer);
	    resizeTimer = setTimeout(function()
	    {
			var mainHeight = $("#mainTabs").height();
			var menuTabsHeight = $("#mainTabs ul:first-child").height();
			$("#mainDiv").height(mainHeight - menuTabsHeight - $("#toolbarDiv").height() -30);
			if($("#editorDiv").html() != null)
			{
				var mainDivHeight = $("#mainDiv").height();
				$("#editorDiv").height(mainDivHeight );
				$("#queriesDiv").height(mainDivHeight );
				$($("#imgDiv").find(".ui-layout-resizer")[0]).height(mainDivHeight ) //resizer adjustment of height
			}
	    }
	    , 220);
	});
	
	

	var mainHeight = $("#mainTabs").height();
	var menuTabsHeight = $("#mainTabs ul:first-child").height();
	$("#mainDiv").height(mainHeight - menuTabsHeight - $("#toolbarDiv").height() - 30);  //-30 to remove scroll
</script>
</head>

<body> 
<ps:form id="mainForm_${_pageRef}" useHiddenProps="true">	
  <ps:hidden id="editorLoadedFrom_${_pageRef}" name="editorLoadedFrom"></ps:hidden>
  <ps:hidden id="isSyb_${_pageRef}" name="isSyb"></ps:hidden>
    <div id="toolbarDiv" style="width=100%; ">
        <pt:toolBar id="reptoolbar"> 
            <psj:submit button="true" onclick="newReport();" type="button">
	        	<ps:text name="newReport_key"/>
	        </psj:submit>
	        <psj:submit button="true" onclick="openReport();" type="button">
	            <ps:text name="openReport_key"/>
	        </psj:submit>	        
		</pt:toolBar>        
    </div>
    <br/>
	<div id="mainDiv" style="padding:0px 0px 0px 0px">
	    <div id="imgDiv"  style="width:100%;  height:100%;">   
	      	<table width="100%" height="100%">
	      		<tr height="100%">
	      			<td align="right"><img src="../reporting/images/designer.PNG"/></td>
	      		</tr>
	      	</table> 
	    </div>
	</div>
	

<div class="clearfix">	
	<psj:dialog
		id="zDialog" 
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	/>    
</div>

<div class="clearfix">	
	<psj:dialog
		id="qryDialg" 
	    autoOpen="false"
	    dataType="html"
	    modal="true"  
	    closeOnEscape="false"
	    onCloseTopics="showSqll" 
	/>    
</div>
	
<div class="clearfix">
	<psj:dialog
	    id="wizardDialog" 
	    autoOpen="false"
	    dataType="html"
	    width="700"
	    height="470"
	    modal="true"  
		title="%{getText('newReport_key')}"
		closeOnEscape="false"
		onCloseTopics="wizardOnClose"		
	/>    
</div>

<div class="clearfix">	
	<psj:dialog
		id="variableDialog" 
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="340"
	    height="250"
	    onCloseTopics="insertVariable"
	/>    
</div>

<div class="clearfix">	
	<ps:url id="propUrl"  namespace="/path/designer" action="reportDesign_openProperties?_pageRef=${_pageRef}"/>
	<psj:dialog
		id="propDialog" 
		href="%{propUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="650"
	    height="550"
	/>    
</div>

<div class="clearfix">	
	<ps:url id="grpUrl"  namespace="/path/repCommon" action="commonReporting_openGroupBy?_pageRef=${_pageRef}"/>
	<psj:dialog
		id="groupDialog" 
		href="%{grpUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="800"
	    height="400"
	/>    
</div>

<div class="clearfix">	
	<ps:url id="paramUrl"  namespace="/path/designer" action="queryDesign_openParameters?_pageRef=${_pageRef}"/>
	<psj:dialog
		id="paramDialog" 
		href="%{paramUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="1100"
	    height="500"
	    onCloseTopics="closeParamDialog"
	/>    
</div>

<div class="clearfix">	
	<ps:url id="imgUrl"  namespace="/path/designer" action="image_openImagesList"/>
	<psj:dialog
		id="imageDialog" 
		href="%{imgUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="1000"
	    height="530"
	/>    
</div>

<div class="clearfix">
	<ps:url id="procUrl"  namespace="/path/designer" action="proc_openProcList">
	<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="procDialog" 
		href="%{procUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="800"
	    height="300"
	/>    
</div>

<div class="clearfix">
	<ps:url id="subRepUrl"  namespace="/path/designer" action="subrep_openSubRepList">
	<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="subRepDialog" 
		href="%{subRepUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="800"
	    height="300"
	/>    
</div>

<div class="clearfix">	
	<ps:url id="uploadImgUrl"  namespace="/path/designer" action="uploadImage_openUploadImage"/>
	<psj:dialog
		id="uploadImageDialog" 
		href="%{uploadImgUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="480"
	    height="130"
	    onCloseTopics="refreshImgGrid"
	/>    
</div>

<div class="clearfix">
	<ps:url id="hashTblUrl"  namespace="/path/designer" action="hashTbl_openHashTbl">
	<ps:param name="_pageRef" value="_pageRef"> </ps:param>
	</ps:url>
	<psj:dialog
		id="hashTblDialog" 
		href="%{hashTblUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="800"
	    height="300"
	/>    
</div>

<div class="clearfix">	
	<ps:url id="savaAsRepUrl"  namespace="/path/designer" action="reportsList_openSaveAsDlg?calledFrom=0&_pageRef=${_pageRef}"/>
	<psj:dialog
		id="saveAsRepDialog" 
		href="%{savaAsRepUrl}"
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="800"
	    height="240"
	    title="%{getText('opt.SaveAs')}"
	    onCloseTopics="closeSaveAsRepDlg"
	/>    
</div>

</ps:form>

</body>
</html>