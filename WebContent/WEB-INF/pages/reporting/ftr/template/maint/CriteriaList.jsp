<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="selectLineAlert_var" 		value="%{getEscText('gl.selectLineAlert')}"/>
<ps:set name="missDet1Alert_var" 		value="%{getEscText('criteria.missDet1')}"/>
<ps:set name="missingReqFe_var" 		value="%{getEscText('reporting.missing')}"/>
<ps:set name="recExist_var" 			value="%{getEscText('fcr.checkProgRefAlert')}"/>
<ps:set name="calcRtvTemp_var" 			value="%{getEscText('template.calcRtvTemp')}"/>
<ps:set name="crtCurRequiredInc_var" 	value="%{getEscText('template.crtCurRequiredInc')}"/>
<ps:set name="onlyOneCur_var" 			value="%{getEscText('template.onlyOneCur')}"/>

		<script type="text/javascript">
		   var selectLineAlert 		= '<ps:property value="selectLineAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   var missDet1Alert 		= '<ps:property value="missDet1Alert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   var missingReqFe 		= '<ps:property value="missingReqFe_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   var recExist 			= '<ps:property value="recExist_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   var calcRtvTemp 			= '<ps:property value="calcRtvTemp_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   var crtCurRequiredInc 	= '<ps:property value="crtCurRequiredInc_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   var onlyOneCur 			= '<ps:property value="onlyOneCur_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
		   
		   $(document).ready(function () 
			{
				$.struts2_jquery.require("CriteriaList.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/template/");
				rep_templateCriteria_readyFunc();
			});
		   
			
		</script>
			<ps:hidden name="tempCodeWithLineNb" id="tempCodeWithLineNbCrt_${_pageRef}"></ps:hidden>
					 
						<ps:collapsgroup id="templCrtCollapsGrp_${_pageRef}">
							<ps:collapspanel id="tmplCrtFrmCollaps_${_pageRef}"  key="criteria.criteriaTitle">
						
						<ps:url var="urlTag" action="crtGridUrl?_pageRef=${_pageRef}&tempCodeWithLineNb=${tempCodeWithLineNb}" namespace="/path/templateMaintReport">
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
		          			addfunc="addCrt"
		          			delfunc="deleteCrt"
		          			rowNum="15" 
		    	  			rowList="5,10,15,20"
		    	  			ondblclick="fillCtrForm()"
		    	  			onCompleteTopics="crtGrdComplete"
		          		 >
		      
				        
				         <psjg:gridColumn name="CRITERIA_NAME" id="crtName"  title="%{getText('criteria.crtName')}" colType="text"  editable="false" sortable="false" />
						 <psjg:gridColumn name="CODE1_NAME" id="CODE1NAME" title="%{getText('criteria.details1')}" colType="text"  editable="false" sortable="false" />
						 <psjg:gridColumn name="CODE2_NAME" id="CODE2NAME" title="%{getText('criteria.details2')}" colType="text"  editable="false" sortable="false" />
						 <psjg:gridColumn name="CODE3_NAME" id="CODE3_NAME" title="%{getText('criteria.details3')}" colType="text"  editable="false" sortable="false" hidden="false"/>
						 <psjg:gridColumn name="VALUE1" id="crtVal"  title="%{getText('criteria.value')}" colType="text"  editable="false" sortable="false" />
						 <psjg:gridColumn name="VALUE2" id="VALUE2" title="%{getText('criteria.value2')}" colType="text"  editable="false" sortable="false"  />
						 <psjg:gridColumn name="glstmpltParamLinkVO.INCLUDE" id="include" title="%{getText('criteria.Include')}" colType="text"  editable="false" sortable="false" hidden ="true"/>
						 <psjg:gridColumn name="INCLUDE_TRANS" id="include_trans" title="%{getText('criteria.Include')}" colType="text"  editable="false" sortable="false" />
						 <psjg:gridColumn name="newLineNumber" 		title="tempmlate line number" colType="number" 	editable="false" sortable="false" hidden = "true"/>
						 <psjg:gridColumn name="OPERATOR_NAME" id="operName" title="%{getText('operator')}" colType="text"  editable="false" sortable="false" hidden="true"/>
						 <psjg:gridColumn name="glstmpltParamLinkVO.OPERATOR" id="oper" title="%{getText('operator')}" colType="text"  editable="false" sortable="false" hidden="true" />
						 <psjg:gridColumn name="CODE1" id="code1" title="%{getText('criteria.details1')}" colType="text"  editable="false" sortable="false" hidden="true" />
						 <psjg:gridColumn name="glstmpltParamLinkVO.CODE2" id="code2" title="%{getText('criteria.details2')}" colType="text"  editable="false" sortable="false" hidden="true"/>
						 <psjg:gridColumn name="CRI_LINE_GL" id="CRI_LINE_GL" title=""  colType="text"  editable="false" sortable="false" hidden="true"/>						
						 <psjg:gridColumn name="COMPONENT" id="COMPONENT" title="COMPONENT" colType="text"  editable="false" sortable="false" hidden="true" />
						 <psjg:gridColumn name="CRITERIA_TYPE_CODE" id="crt_TypeCode" title="crtTypeCode" colType="text"  editable="false" sortable="false" hidden="true" />
						 <psjg:gridColumn name="TABLE_NAME1" id="TABLENAME1" title="TABLE_NAME1" colType="text"  editable="false" sortable="false" hidden="true" />
						 <psjg:gridColumn name="TABLE_NAME2" id="TABLENAME2" title="TABLE_NAME2" colType="text"  editable="false" sortable="false" hidden="true" />
				         <psjg:gridColumn name="glstmpltParamLinkVO.CRITERIA_CODE" id="crtCode"  title="%{getText('criteria.crtCode')}" colType="number"  editable="false" sortable="false" hidden="true"/>
				         <psjg:gridColumn name="concatKey"   id="concat_Key" title="concat key" colType="number"  editable="false" sortable="false" key="true" hidden="true" />
				         <psjg:gridColumn name="glstmpltParamLinkVO.COMP_CODE"   id="compCode" title="COMP_CODE" colType="number"  editable="false" sortable="false" hidden="true"/>
				         <psjg:gridColumn name="glstmpltParamLinkVO.SUB_LINE_NO"   id="subLnNb" title="SUB_LINE_NO" colType="number"  editable="false" sortable="false" hidden="true"/>
				         <psjg:gridColumn name="glstmpltParamLinkVO.TEMP_CODE"   id="tmpCode" title="TEMPLATE_CODE" colType="number"  editable="false" sortable="false" hidden="true"/>
				         <psjg:gridColumn name="glstmpltParamLinkVO.LINE_NO"   id="tmpLnNb" title="TEMPLATE_LINE_NO" colType="number"  editable="false" sortable="false" hidden="true"/>
				         <psjg:gridColumn name="glstmpltParamLinkVO.DATE_TYPE"   id="glstmpltParamLinkVO.DATE_TYPE" title="glstmpltParamLinkVO.DATE_TYPE" colType="text"  editable="false" sortable="false" hidden="true"/>
		    		     <psjg:gridColumn name="glstmpltParamLinkVO.GENDER"   id="glstmpltParamLinkVO.GENDER" title="glstmpltParamLinkVO.GENDER" colType="text"  editable="false" sortable="false" hidden="true"/>
		    		     <psjg:gridColumn name="CODE3" id="CODE3" title="CODE3" colType="text"  editable="false" sortable="false" hidden="true"/>
		    		   </psjg:grid> 
		       
		       
					</ps:collapspanel>
					</ps:collapsgroup>
					
					  <div id="CrtDivId_<ps:property value="_pageRef" escapeHtml="true"/>"  class="headerPortion">
						<%@include file="CriteriaFrm.jsp"%>
					  </div>
					  <ps:hidden name="mode"		   id="modeCrt_${_pageRef}"></ps:hidden>
