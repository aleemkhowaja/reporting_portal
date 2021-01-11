<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="linkImagesName_var" 		value="%{getEscText('upDown.images')}"/>

<script type="text/javascript">

var linkImagesName 		= '<ps:property value="linkImagesName_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

$(document).ready(function() 
{
	$.struts2_jquery.require("UploadDownloadImagesList.js" ,null, jQuery.contextPath+"/path/js/reporting/uploadDownloadReports/");
});


		
$("#uploadImageDialog").subscribe('refreshImgGrid', function(event,data) 
{
	$("#imageGrid").trigger("reloadGrid");
});
	
</script>


<ps:url id="urlGrid" action="uploadDownloadImages_loadImagesUD" namespace="/path/designer">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>   
    <psjg:grid
    	id="imagesGridId_${_pageRef}"
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
    	navigatorAdd="false" 
    	navigatorEdit="false" 
    	navigatorSearch="false "
		navigatorDelete="false"
		navigatorRefresh="false"
		
    	>
    	
    	<psjg:gridColumn name="imgName" id="imgName" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="imgName"  colType="text"  title="%{getText('upDown.images')}"  sortable="true"  width="25"  hidden="false" editable="false"/>
 		<psjg:gridColumn name="imgLocation" id="imgLocation" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="imgLocation"  colType="text"  title="imgLocation" sortable="true"  width="25"  hidden="true" editable="false"/>
		<psjg:gridColumn  name="imgLink" formatter="openImages" index="imgLink" colType="text" title="%{getText('upDown.imgLst')}" editable="false" width="25" align="center" sortable="false"/>
    	<psjg:gridColumn name="mappedImgName" id="mappedImgName" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}"   index="mappedImgName"  colType="text"  title="%{getText('upDown.mappedImg')}"   sortable="true"  width="25"  hidden="false" editable="false"/>
 		
    </psjg:grid>
    
    
    
<div class="clearfix">	
	<ps:url id="imgUrl"  namespace="/path/designer" action="image_openImagesList">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>
	<psj:dialog
		id="imageDialog" 
	    autoOpen="false"
	    dataType="html"
	    modal="true" 
	    width="1000"
	    height="530"
	/>    
</div>    
    
  <div id="imagesLinkDialog_<ps:property value="_pageRef" escapeHtml="true"/>"></div>  
  
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
  
  
  
   