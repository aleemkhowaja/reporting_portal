<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@ taglib prefix="ptt" uri="/path-toolbar-tags"%>
<ps:form id="selecAccForm_${_pageRef}" action="saveNeededAccounts"  namespace="/path/templateMaintReport" cssStyle="display:none" useHiddenProps="true">

	<ps:textfield name="templateCode" 	id="tmplCodeAcc_${_pageRef}"></ps:textfield>
	<ps:textfield name="lineNumber"   	id="lineNumberAcc_${_pageRef}"></ps:textfield>
	<ps:textfield name="lineNbrDet"   	id="lineNbrDetAcc_${_pageRef}"></ps:textfield>
	<ps:textfield name="updateSelecAcc" id="updateSelecAcc_${_pageRef}"></ps:textfield>

</ps:form>
<script type="text/javascript">
	
	$("#tmplCodeAcc_"+_pageRef).val($("#glTempCode_"+_pageRef).val());
	$("#lineNumberAcc_"+_pageRef).val($("#lnNbr_"+_pageRef).val());
	$("#lineNbrDetAcc_"+_pageRef).val($("#subLineNb_"+_pageRef).val());
	$(document).ready(
			function() {	
				$.struts2_jquery.require("GLList.js", null, jQuery.contextPath+"/path/js/reporting/ftr/template/");									
});			


function selectAllAcc()
{
		var params = {};
		params["templateCode"]	=	$("#tmplCodeAcc_"+_pageRef).val();
		params["lineNumber"]	=	$("#lineNumberAcc_"+_pageRef).val();
		params["lineNbrDet"]	=	$("#lineNbrDetAcc_"+_pageRef).val();
		params["_pageRef"]		=	_pageRef;
		$.ajax({
			url: jQuery.contextPath+'/path/templateMaintReport/selectAllAcc.action',
			type: "POST",
			data:(params),		
			success: function(xml){
		$("#selectAccGrid_"+_pageRef).trigger("reloadGrid");
		$("#deselectAll_"+_pageRef).attr("checked",false);
		    			}
					});
}

function deselectAllAcc()
{
		var params = {};
		params["templateCode"]	=	$("#tmplCodeAcc_"+_pageRef).val();
		params["lineNumber"]	=	$("#lineNumberAcc_"+_pageRef).val();
		params["lineNbrDet"]	=	$("#lineNbrDetAcc_"+_pageRef).val();
		params["_pageRef"]		=	_pageRef;
		$.ajax({
			url: jQuery.contextPath+'/path/templateMaintReport/deSelectAllAcc.action',
			type: "POST",
			data:(params),
			success: function(xml){
		$("#selectAccGrid_"+_pageRef).trigger("reloadGrid");
		$("#selectAll_"+_pageRef).attr("checked",false);
		    			}
					});
}
</script>


  <table width="100%" cellpadding="0">
  <tr>
    	<td>
    		<table>
    		<tr>
    			<td><ps:label value="%{getText('reporting.selectAll')}" /></td>
    			<td><ps:checkbox id="selectAll_${_pageRef}" name=""  onchange="selectAllAcc()"></ps:checkbox></td>
    			<td></td>
    			<td><ps:label value="%{getText('reporting.deselectAll')}" /></td>
    			<td><ps:checkbox id="deselectAll_${_pageRef}" name="" onchange="deselectAllAcc()"></ps:checkbox></td>
    		</tr>
    		</table>
    	</td>
    </tr>
  <tr>
  <td  width="100%" >  
  <ps:url id="urlSelectAcc" action="selAccGridUrl" namespace="/path/templateMaintReport"></ps:url>
    <psjg:grid 
    	id="selectAccGrid_${_pageRef}"
    	dataType="json" 
    	gridModel="gridModel"	
    	href="" 
		pager="false" navigator="false" navigatorSearch="true" 
		navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
		navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
		caption =" "
		navigatorRefresh="true"
		viewrecords="true"  
		scroll="true"  	
		rowNum="-1"
		sortable="true"
		shrinkToFit="true"
		editurl="%{urlSelectAcc}"
		editinline="true">
		
		<psjg:gridColumn name="concatKey" 									index="concatKey"									searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="Sl NO" 	editable="false" hidden="true"  sortable="true" search="false"/>  
		<psjg:gridColumn name="ftrAccountVO.COMP_CODE" 						index="ftrAccountVO.COMP_CODE"						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="%{getText('gl.compCodeSep')}" 			editable="false"  sortable="true" search="false"/>  
      	<psjg:gridColumn name="ftrAccountVO.BRANCH_CODE" 					index="ftrAccountVO.BRANCH_CODE"  					searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="%{getText('gl.branchCode')}"   	editable="false"  sortable="true" search="false" />
      	<psjg:gridColumn name="ftrAccountVO.GL_CODE" 						index="ftrAccountVO.GL_CODE"  						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text" 	title="%{getText('gl.glCode')}"  editable="false"   sortable="true" hidden="false" search="false" />
  		<psjg:gridColumn name="ftrAccountVO.CURRENCY_CODE" 					index="ftrAccountVO.CURRENCY_CODE"  				searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"  	title="%{getText('reporting.lkpCurrCode')}"    	editable="false" hidden="false"  sortable="true" search="false" /> 
      	<psjg:gridColumn name="ftrAccountVO.CIF_SUB_NO" 					index="ftrAccountVO.CIF_SUB_NO"						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="%{getText('gl.cifSubNo')}" 	editable="false" hidden="false"  sortable="true" search="false"/>  
      	<psjg:gridColumn name="ftrAccountVO.SL_NO" 							index="ftrAccountVO.SL_NO"							searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="%{getText('gl.slNo')}"	editable="false" hidden="false"  sortable="true" search="false"/>  
     	<psjg:gridColumn name="ftrAccountVO.INCLUDE" 						index="ftrAccountVO.INCLUDE"						searchoptions="{sopt:['eq','ne','bw','bn','ew','en','cn','nc']}"  colType="text"    title="Sl NO" 	editable="false" hidden="true"  sortable="true" search="false"/>  
     	<psjg:gridColumn name="incl" hidden = "false"  		index="incl" 	title="%{getText('criteria.Include')}" formatter="checkbox" 	colType="checkbox" edittype="checkbox" editable="true"  formatoptions="{disabled : false}" editoptions="{value:'true:false'}"/>
      </psjg:grid>
    </td>
    </tr>
    </table>

<script type="text/javascript">

	$("#selectAccGrid_"+_pageRef).jqGrid(
	'setGridParam',
	{
		url : "${pageContext.request.contextPath}/path/templateMaintReport/selAccGridUrl.action?templateCode="+$("#tmplCodeAcc_"+_pageRef).val()+"&lineNumber="+$("#lineNumberAcc_"+_pageRef).val()+"&lineNbrDet="+$("#lineNbrDetAcc_"+_pageRef).val()
		+"&div="+$("#lookuptxt_divCode_"+_pageRef).val()+"&dpt="+$("#lookuptxt_deptCode_"+_pageRef).val()+"&fromGL="+$("#lookuptxt_fromGLCode_"+_pageRef).val()
		+"&toGL="+$("#lookuptxt_toGLCode_"+_pageRef).val()+"&_pageRef="+_pageRef
		+"&cmpCode="+$("#lookuptxt_glCompCode_"+_pageRef).val()+"&brCode="+$("#lookuptxt_branchCode_"+_pageRef).val()
	}).trigger("reloadGrid");
</script>