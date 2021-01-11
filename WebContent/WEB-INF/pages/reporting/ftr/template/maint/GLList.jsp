<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>

<ps:set name="selectLineAlert_var" 			value="%{getEscText('gl.selectLineAlert')}"/>
<ps:set name="compareToFromAlert_var" 		value="%{getEscText('gl.compareToFromAlert')}"/>
<ps:set name="compareFromToAlert_var" 		value="%{getEscText('gl.compareFromToAlert')}"/>
<ps:set name="fillRequiredFieldAlert_var" 	value="%{getEscText('reporting.requiredFieldsAlert')}"/>
<ps:set name="subLineNbExistance_var" 		value="%{getEscText('gl.subLineNbExistance')}"/>
<ps:set name="tmpCodeExistAlert_var" 		value="%{getEscText('template.fillTemplCode')}"/>
<ps:set name="selectLineAlert_var" 			value="%{getEscText('gl.selectLineAlert')}"/>
<ps:set name="fillDivCodeAlert_var" 		value="%{getEscText('gl.fillDivCode')}"/>
<ps:set name="allBr_var" 					value="%{getEscText('gl.allBranches')}"/>
<ps:set name="allDiv_var" 					value="%{getEscText('gl.allDivisions')}"/>
<ps:set name="allDept_var" 					value="%{getEscText('gl.allDept')}"/>
<ps:set name="missingFeldsAlert_var" 		value="%{getEscText('gl.missingFields')}"/>
<ps:set name="invalid_GL" 					value="%{getEscText('invalid_GL_key')}"/>
<ps:set name="calcRateValExists_var" 		value="%{getEscText('template.calcRateValExists')}"/>
<ps:set name="rateValAlone_var" 			value="%{getEscText('template.rateValAlone')}"/>
<ps:set name="criteriaRequired_var" 		value="%{getEscText('template.criteriaRequired')}"/>


<script type="text/javascript">
	var selectLineAlert 			= '<ps:property value="selectLineAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var compareToFromAlert 			= '<ps:property value="compareToFromAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var compareFromToAlert 			= '<ps:property value="compareFromToAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var fillRequiredFieldAlert 		= '<ps:property value="fillRequiredFieldAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var subLineNbExistance 			= '<ps:property value="subLineNbExistance_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var tmpCodeExistAlert 			= '<ps:property value="tmpCodeExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var selectLineAlert 			= '<ps:property value="selectLineAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var fillDivCodeAlert 			= '<ps:property value="fillDivCodeAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var allBr 						= '<ps:property value="allBr_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var allDiv 						= '<ps:property value="allDiv_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var allDept 					= '<ps:property value="allDept_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var missingFeldsAlert 			= '<ps:property value="missingFeldsAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var reloadGLGrd = false;
	var glToSel = -1;
	var invalid_GL 			= '<ps:property value="invalid_GL"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var calcRateValExists	= '<ps:property value="calcRateValExists_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var rateValAlone    	= '<ps:property value="rateValAlone_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var criteriaRequired   	= '<ps:property value="criteriaRequired_var"  escapeHtml="false" escapeJavaScript="true"/>'; 


	


$(document).ready(function() 
	{
		$.struts2_jquery.require("GLList.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/template/");
		rep_glList_readyFunc();
	});

</script>
<ps:hidden name="tempCodeWithLineNb" id="tempCodeWithLineNb_${_pageRef}"></ps:hidden>


<ps:collapsgroup id="tmplGlListCollapsGrp_${_pageRef}">
			<ps:collapspanel id="tmplGlListCollaps_${_pageRef}"  key="gl.glTitle">
		<ps:url var="urlTagGL" action="glGridUrl?_pageRef=${_pageRef}&tempCodeWithLineNb=${tempCodeWithLineNb}" namespace="/path/templateMaintReport">
		</ps:url>
		<psjg:grid id="glGrids_${_pageRef}" gridModel="gridModel" dataType="json"
			href="%{urlTagGL}" pager="true" navigator="true"
			navigatorSearch="false " navigatorEdit="false"
			navigatorRefresh="false" viewrecords="true"
			navigatorEditOptions="{height:280,reloadAfterSubmit:false}"
			addfunc="addGL" delfunc="deleteGL" rowNum="15" rowList="5,10,15,20"
			ondblclick="fillGlForm()" onCompleteTopics="glGrdComplete">


			<psjg:gridColumn name="glstmpltGlsDetVO.LINE_NBR_DET" width="50" id="sbuLine"
				title="%{getText('reporting.subLineNb')}" colType="number"
				editable="false" sortable="false" hidden = "false"/>
			<psjg:gridColumn name="newLineNumber" width="50" id="sbuLine"
				title="tempmlate line number" colType="number"
				editable="false" sortable="false" hidden = "true"/>
			<psjg:gridColumn name="glstmpltGlsDetVO.FROM_GL" width="50" id="from_gl"
				title="%{getText('reporting.from')}" colType="number"
				editable="false" sortable="false" />
			<psjg:gridColumn name="glstmpltGlsDetVO.TO_GL" width="50" id="to_gl"
				title="%{getText('reporting.to')}" colType="number" editable="false"
				sortable="false" />
			<psjg:gridColumn name="GL_COMP_NAME" id="glCompName" 
				title="%{getText('gl.glCompanyName')}" colType="text"
				editable="false" sortable="false" />
			<psjg:gridColumn name="BRANCH_NAME" id="branchName"
				title="%{getText('gl.branchName')}" colType="text" editable="false"
				sortable="false" />
			<psjg:gridColumn name="DIV_NAME" id="divName"
				title="%{getText('gl.divName')}" colType="text" editable="false"
				sortable="false" />
			<psjg:gridColumn name="DEPT_NAME" id="deptName"
				title="%{getText('gl.deptName')}" colType="text" editable="false"
				sortable="false" />
			<psjg:gridColumn name="glstmpltGlsDetVO.PERCENTAGE" id="percentage"
				title="%{getText('gl.percentage')}" colType="number"
				editable="false" sortable="false" hidden="true" nbFormat="##.##"/>
			<psjg:gridColumn name="CALC_TYPE_NAME" id="calcTypeName"
				title="%{getText('reporting.calculationType')}" colType="text"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.EXCLUDE_CLOSING_ENTRY" id="excl_clos_entry"
				title="%{getText('gl.ExclClosingEntry')}" colType="text"
				editable="false" sortable="false" hidden="true" />

			<psjg:gridColumn name="glstmpltGlsDetVO.CALC_TYPE" id="calc_type"
				title="%{getText('reporting.calculationType')}" colType="text"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="concatKey" id="concat_Key" title="concat key"
				colType="number" editable="false" sortable="false" hidden="true"
				key="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.COMP_CODE" id="comp_code"
				title="%{getText('reporting.companyCode')}" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.CODE" id="template_code"
				title="%{getText('reporting.code')}" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.LINE_NBR" id="templ_lineNb"
				title="%{getText('line.lineNbr')}" colType="number" editable="false"
				sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.GL_COMP" id="gl_comp_code"
				title="GL CompCode" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.BRANCH_CODE" id="branch_code"
				title="Branch Code" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.DIV_CODE" id="div_code"
				title="%{getText('gl.divCode')}" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.DEPT_CODE" id="dept_code"
				title="%{getText('gl.deptCode')}" colType="number"
				editable="false" sortable="false" hidden="true" />
			<psjg:gridColumn name="FROM_GLStr" id="from_glStr"
				title="%{getText('reporting.from')}" colType="text" editable="false"
				sortable="false" hidden="true" />
			<psjg:gridColumn name="TO_GLStr" id="to_glStr"
				title="%{getText('reporting.to')}" colType="text" editable="false"
				sortable="false" hidden="true" />
			<psjg:gridColumn name="glstmpltGlsDetVO.GL_COMP" id="GLCOMPCODE"
				title="%{getText('reporting.to')}" colType="text" editable="false"
				sortable="false" hidden="true" />
		</psjg:grid>


</ps:collapspanel>
</ps:collapsgroup>

<div class="headerPortion" id="glListDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
<%@include file="GLFrm.jsp"%>
</div>

<div id="selecAccDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="clearfix">
	<ps:url id="selecAccUrl"  namespace="/path/templateMaintReport" action="selectAccount">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>
	<psj:dialog
		id="selecAccDiag_${_pageRef}" 
		href="%{selecAccUrl}"
	    autoOpen="false"
	    dataType="html"
	    title="%{getText('reporting.selectAccount')}"
	    modal="true" 
	    width="900"
	    height="600"
	   buttons="{%{getText('reporting.ok')}: function(){submitAccountsNeeded();$( this ).dialog( 'close' );},%{getText('reporting.cancel')}:function(){$( this ).dialog( 'close' );return null;}}"
	/>    
</div> 	  
<ps:hidden id="fromHidd_${_pageRef}" value="from"></ps:hidden>
<ps:hidden id="toHidd_${_pageRef}" value="to"></ps:hidden>
<ps:hidden name="templateCode" id="templateCode_${_pageRef}"></ps:hidden>	
<ps:hidden name="lineNumber"   id="lineNumber_${_pageRef}"></ps:hidden>	
<ps:hidden name="lineNbrDet"   id="lineNbrDet_${_pageRef}"></ps:hidden>	
<ps:hidden name="mode"		   id="modeGL_${_pageRef}"></ps:hidden>

