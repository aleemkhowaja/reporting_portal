<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@taglib prefix="menu" uri="/path-menu-tags"%>

<ps:set name="westMenuText_var" 		value="%{getEscText('reportName')}"/>
<ps:set name="eastMenuText_var" 		value="%{getEscText('reportName')}"/>

<script>
var westMenuText 		= '<ps:property value="westMenuText_var"  escapeHtml="false" escapeJavaScript="true"/>'
var eastMenuText 		= '<ps:property value="eastMenuText_var"  escapeHtml="false" escapeJavaScript="true"/>'

$(document).ready(function() {
	$.struts2_jquery.require("MenuQueries.js", null,jQuery.contextPath + "/path/js/reporting/designer/");
	$("#queriesDiv").attr("class", 'inner-'+westDiv);
	$('#reptbarCloseWest').css('float',westPinAlign);	
	innerLayout = $('#imgDiv').layout(innerLayoutSettings);
	innerLayout.addPinBtn("#reptbarCloseWest",westDiv );
	
	setTimeout(function(){
		//add next to each query name an icon for edit functionality
		var h = document.getElementById("queriesMenu").getElementsByTagName("h3");
		var anchorTag = document.createElement('a');				
		anchorTag.setAttribute('style',"float: right");	
		anchorTag.setAttribute('class',"ui-icon ui-icon-pencil");
		anchorTag.setAttribute('onclick',"editQuery(event);");							
		for(var i=0; i<h.length; i++){
			h[i].appendChild(anchorTag);
			anchorTag = anchorTag.cloneNode(true);
		}
	},500);

	//annasuccar- 6-Jul-2012: override setTitleVal function in MenuScreen.ftl
	//this code works with MenuScreen.ftl in the branch project
	//to be tested on the latest project, if this function no longer exists then remove the code below
    var orig_setTitleVal = window.setTitleVal;
    this.setTitleVal = function(This){
        var el = This;
        var queryDiv = document.getElementById("queriesMenu");
   		while (el) {
	        if (el == queryDiv) {
				//don't call the orignial setTitleVal function upon hitting a click in the queries menu
	            return false;
	        }
	        el = el.parentNode;
    	}
    	//call the orignial setTitleVal function upon hitting a click in any menu other than the queries menu.
    	orig_setTitleVal(This);
    }
    //end
    
});

</script>

<div class="ui-layout-west" style="padding:0px;">
	<div class="ui-state-default">
		 <span><ps:text name="${repQryName}"/></span>
		 <div id="reptbarCloseWest" class="ui-icon ui-icon-pin-s ui-corner-all"></div>
	</div>				 
	<menu:accordion actionName="generateQueriesMenu" collapseprev="false" targetName="editorDiv" id="queriesMenu">
	</menu:accordion>
</div>
<ps:hidden name="reportCO.REPORT_ID" id="reportId"/>

