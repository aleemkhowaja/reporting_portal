<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="selectLineAlert_var" 		value="%{getEscText('gl.selectLineAlert')}"/>
<ps:set name="missDet1Alert_var" 		value="%{getEscText('criteria.missDet1')}"/>
<ps:set name="missingReqFe_var" 		value="%{getEscText('reporting.missing')}"/>
<ps:set name="recExist_var" 			value="%{getEscText('fcr.checkProgRefAlert')}"/>
<ps:set name="amountNegativeAlert_var" 			value="%{getEscText('fcr.amountNegativeAlert')}"/>


<script type="text/javascript">
   var selectLineAlert 		= '<ps:property value="selectLineAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
   var missDet1Alert 		= '<ps:property value="missDet1Alert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
   var missingReqFe 		= '<ps:property value="missingReqFe_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
   var recExist 			= '<ps:property value="recExist_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
   var amountNegativeAlert 			= '<ps:property value="amountNegativeAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 

	var reloadCrtGrd = false;
	var crtToSel = -1;
	var glLineCount=0;
   $(document).ready(function () 
	{
		$.struts2_jquery.require("CriteriaListColTemp.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/columntemplate/");
		rep_crtLst_readyFunc();	
	});
   
	
</script>
<ps:hidden name="tempCodeWithLineNb" id="tempCodeWithLineNbCrt"></ps:hidden>

	
<ps:collapsgroup id='colTemplCrtCollapsGrp_${_pageRef}'>
	<ps:collapspanel id='critCollaps_${_pageRef}'  key="criteria.criteriaTitle">	
		<ps:url var="urlTag" action="crtGridUrl" namespace="/path/colTemplateMaintReport">
			<ps:param  name="tempCodeWithLineNb" value="tempCodeWithLineNb"></ps:param>
			<ps:param  name="_pageRef" value="_pageRef"></ps:param>
		</ps:url>
		<psjg:grid  id="crtGrids_${_pageRef}" 
			gridModel="gridModel"
			dataType="json"
			href="%{urlTag}"
			pager="true"
			navigator="true"
			navigatorSearch="false "
     		navigatorEdit="false"
     		navigatorRefresh="false"
     		viewrecords="true"
    		navigatorEditOptions="{height:280,reloadAfterSubmit:false}" 
     		addfunc="addCrtColTemp"
     		delfunc="deleteCrtColTemp"
     		rowNum="15"
  			rowList="5,10,15,20"
  			ondblclick="fillCtrFormColTemp()"
  			onCompleteTopics="crtGrdCompleteColTemp"
     		 >
		<psjg:gridColumn name="CRITERIA_NAME" id="crtName"  title="%{getText('criteria.crtName')}" colType="text"  editable="false" sortable="false" />				        
		<psjg:gridColumn name="CODE1_NAME" id="CODE1NAME" title="%{getText('criteria.details1')}" colType="text"  editable="false" sortable="false" />
		<psjg:gridColumn name="CODE2_NAME" id="CODE2NAME" title="%{getText('criteria.details2')}" colType="text"  editable="false" sortable="false" />
		<psjg:gridColumn name="VALUE1" id="crtVal"  title="%{getText('criteria.value')}" colType="text"  editable="false" sortable="false" />
		<psjg:gridColumn name="VALUE2" id="VALUE2" title="%{getText('criteria.value2')}" colType="text"  editable="false" sortable="false"  />
		<psjg:gridColumn name="columntmpltParamLinkVO.INCLUDE" id="include" title="%{getText('criteria.Include')}" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="newLineNumber" 		title="tempmlate line number" colType="number" 	editable="false" sortable="false" hidden = "true"/>
		<psjg:gridColumn name="INCLUDE_TRANS" id="include_trans" title="%{getText('criteria.Include')}" colType="text"  editable="false" sortable="false" />
		<psjg:gridColumn name="OPERATOR_NAME" id="operName" title="%{getText('operator')}" colType="text"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="columntmpltParamLinkVO.OPERATOR" id="oper" title="%{getText('operator')}" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="CODE1" id="code1" title="%{getText('criteria.details1')}" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="columntmpltParamLinkVO.CODE2" id="code2" title="%{getText('criteria.details2')}" colType="text"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="CRI_LINE_GL" id="CRI_LINE_GL" title=""  colType="text"  editable="false" sortable="false" hidden="true"/>						
		<psjg:gridColumn name="COMPONENT" id="COMPONENT" title="COMPONENT" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="CRITERIA_TYPE_CODE" id="crt_TypeCode" title="crtTypeCode" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="TABLE_NAME1" id="TABLENAME1" title="TABLE_NAME1" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="TABLE_NAME2" id="TABLENAME2" title="TABLE_NAME2" colType="text"  editable="false" sortable="false" hidden="true" />
		<psjg:gridColumn name="columntmpltParamLinkVO.CRITERIA_CODE" id="crtCode"  title="%{getText('criteria.crtCode')}" colType="number"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="concatKey"   id="concat_Key" title="concat key" colType="number"  editable="false" sortable="false" key="true" hidden="true" />
		<psjg:gridColumn name="columntmpltParamLinkVO.COMP_CODE"   id="compCode" title="COMP_CODE" colType="number"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="columntmpltParamLinkVO.SUB_LINE_NO"   id="subLnNb" title="SUB_LINE_NO" colType="number"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="columntmpltParamLinkVO.TEMP_CODE"   id="tmpCode" title="TEMPLATE_CODE" colType="number"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="columntmpltParamLinkVO.LINE_NO"   id="tmpLnNb" title="TEMPLATE_LINE_NO" colType="number"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="columntmpltParamLinkVO.DATE_TYPE"   id="columntmpltParamLinkVO.DATE_TYPE" title="columntmpltParamLinkVO.DATE_TYPE" colType="text"  editable="false" sortable="false" hidden="true"/>
		<psjg:gridColumn name="columntmpltParamLinkVO.GENDER"   id="columntmpltParamLinkVO.GENDER" title="columntmpltParamLinkVO.GENDER" colType="text"  editable="false" sortable="false" hidden="true"/>
		</psjg:grid> 
</ps:collapspanel>
	<ps:collapspanel id='CrtDivId_${_pageRef}'  key="criteria.crieteriaDetails" cssClass="collapsed details-div">
		<%@include file="CriteriaFrm.jsp"%>
</ps:collapspanel>
</ps:collapsgroup>