<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>

<html>
<head>
<script type="text/javascript">
	$(document).ready(function() 
	{
		$.struts2_jquery.require("ImagesList.js", null,jQuery.contextPath+"/path/js/reporting/uploadDownloadReports/");
		imagesList_readyFunc();		
	});	
</script>
	
</head>

<body>

<div id="innerLayoutImg" style="position: relative; height: 100%; width: 100%;">	
<div class="inner-westImg" style="padding: 0">
	<div id="prevDiv" style="text-align: center;display: none">
		<img id='zImg' style='display:inline;width:300px;height=320px;align:center' />
	</div>			
</div>
<div  class="inner-centerImg"  style="padding:0"     >
</div>
<div  class="inner-eastImg"  style="padding:0"     > 
			<ps:url var="urlGrid" action="image_loadImagesList" namespace="/path/designer"></ps:url>
			<psjg:grid id="imageGrid" 
						gridModel="gridModel"
						dataType="json"
						href="%{urlGrid}" 
						pager="true" 
						navigator="true"
						navigatorSearch="false"
						navigatorEdit="false" 
						navigatorAdd="true" 
						navigatorDelete="true"
						navigatorRefresh="false" 
						viewrecords="true" rowNum="5"
						rowList="5,10,15,20" 
						onSelectRowTopics="loadImage"
						delfunc="deleteImage"
						addfunc="uploadImage"
						sortable="true">

				<psjg:gridColumn name="IMAGE_NAME" id="IMAGE_NAME" width="300"
					title="%{getText('image.imgColName')}" align="center"
					colType="text" editable="false" sortable="false"
					index="IMAGE_NAME"
					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}" />
				
			</psjg:grid>
 </div>
 <div class="inner-southImg">
 <table align="right">
	 <tr id="okCancelButton_${_pageRef}" style="">
		 <td >
		 		<psj:submit button="true" onclick="insertImg()" >
					<ps:text name="reporting.ok"></ps:text>
				</psj:submit>
		 </td>
		 <td>
		 		<psj:submit button="true" onclick="cancelImg()" >
					<ps:text name="reporting.cancel"></ps:text>
				</psj:submit>
		 </td>
	 </tr>
 </table>
 </div>
</div>



	<script type="text/javascript">
	
       $('#imageGrid').jqGrid("setGridWidth",470);	
       document.getElementById("imageGrid").style.width="460px";
        
	</script>
	
</body>