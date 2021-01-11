<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %> 

<ps:set name="missingLnNb_var" 				value="%{getEscText('template.missingLnNb')}"/>
<ps:set name="dialogTitle_var" 				value="%{getEscText('reporting.template')}"/>
<ps:set name="codeExistAlert_var" 			value="%{getEscText('template.fillTemplCode')}"/>
<ps:set name="deleteTemplAlert_var" 		value="%{getEscText('template.deleteTemplate')}"/>
<ps:set name="tempCodeExistance_var" 		value="%{getEscText('template.tempCodeExistance')}"/>
<ps:set name="runTemplProcAlert_var" 		value="%{getEscText('template.runTemplProcAlert')}"/>
<ps:set name="templProcDoneAlert_var" 		value="%{getEscText('tmplProc.tmplProcDoneAlert')}"/>
<ps:set name="templProcFailedAlert_var" 	value="%{getEscText('tmplProc.tmplProcFailedAlert')}"/>
<ps:set name="errorPathValidation_var" 		value="%{getEscText('reporting.pathValidation')}"/>
<ps:set name="runProcTtle_var" 				value="%{getEscText('template.runProcTitle')}"/>
<ps:set name="missReqInput_var" 			value="%{getEscText('reporting.missing')}"/>
<ps:set name="loadAllTitle_var" 			value="%{getEscText('entities.loadAllTitle')}"/>
<ps:set name="loadAllFields_var" 			value="%{getEscText('entities.entLoadAll')}"/>
<ps:set name="needReorganize_var" 			value="%{getEscText('template.needReorganize')}"/>
<ps:set name="reorganizeTitle_var" 			value="%{getEscText('template.reorganizeTitle')}"/>
<ps:set name="lineNumberExists_var" 		value="%{getEscText('template.lineNumberExists')}"/>
<ps:set name="deleteAll_var" 				value="%{getEscText('reporting.deleteAll')}"/>
<ps:set name="deleteAllLiness_var" 			value="%{getEscText('reporting.deleteAllLines')}"/>
<ps:set name="enteredPathCorrect_var" 		value="%{getEscText('template.enteredLocation')}"/>
<ps:set name="missingTmplCodeAlert_var" 	value="%{getEscText('col.missingTmplCodeAlert')}"/>
<ps:set name="specifyCode_var" 				value="%{getEscText('template.specifyCode')}"/>
<ps:set name="templateTitle_var"			value="%{getEscText('template.title')}"/>
<ps:set name="positiveNbAlertTmpl_var"			value="%{getEscText('col.positiveNbAlert')}"/>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jQuery-collapsiblePanel.js"></script> <%--Collapsible panels--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/pageComponentStyles.css" />	<%--Common component styles--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/style/jQuery-collapsiblePanel.css" /> <%--Collapsible panel styles--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery.wizard-tabs-0.1.js"></script> <%--Wizard tabs --%>
<script type="text/javascript"> 
	var missingLnNb 			= '<ps:property value="missingLnNb_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var dialogTitle 			= 	'<ps:property value="dialogTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var codeExistAlert 			= 	'<ps:property value="codeExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	var deleteTemplAlert 		= '<ps:property value="deleteTemplAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var tempCodeExistance 		= '<ps:property value="tempCodeExistance_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var runTemplProcAlert 		= '<ps:property value="runTemplProcAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var templProcDoneAlert 		= '<ps:property value="templProcDoneAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var templProcFailedAlert 	= '<ps:property value="templProcFailedAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var errorPathValidation 	= '<ps:property value="errorPathValidation_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var runProcTtle 			= '<ps:property value="runProcTtle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var missReqInput 			= '<ps:property value="missReqInput_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var loadAllTitle 			= '<ps:property value="loadAllTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var loadAllFields 			= '<ps:property value="loadAllFields_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var needReorganize 			= '<ps:property value="needReorganize_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var reorganizeTitle 		= '<ps:property value="reorganizeTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var lineNumberExists 		= '<ps:property value="lineNumberExists_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var deleteAll 				= '<ps:property value="deleteAll_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var deleteAllLiness 		= '<ps:property value="deleteAllLiness_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var enteredPathCorrect 		= '<ps:property value="enteredPathCorrect_var"  escapeHtml="false" escapeJavaScript="true"/>'; 	
	var missingTmplCodeAlert    = '<ps:property value="missingTmplCodeAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var specifyCode    			= '<ps:property value="specifyCode_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var templateTitle			= '<ps:property value="templateTitle_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
    var positiveNbAlertTmpl		= '<ps:property value="positiveNbAlertTmpl_var"  escapeHtml="false" escapeJavaScript="true"/>'; 
	
   $(document).ready(function () 
	{
 		$.struts2_jquery.require("TemplateMain.js" ,null, jQuery.contextPath+"/path/js/reporting/ftr/template/");
 		rep_template_readyFunc();
	});

</script>
<ps:hidden name="groupComboList"	id="groupComboList_${_pageRef}"  />

<div id="relRepDialog_<ps:property value="_pageRef" escapeHtml="true"/>" class="clearfix">
	<ps:url id="relatedReports"  namespace="/path/templateMaintReport" action="relatedReportsDialogUrl?_pageRef=${_pageRef}">
	</ps:url>
	<psj:dialog
		id="relatedReportsDialog_${_pageRef}" 
		href="%{relatedReports}"
	    autoOpen="false"
	    dataType="html"
	    title="%{getText('template.relatedReports')}"
	    modal="true" 
	    width="500"
	    height="300"
	    formIds="mainFrmId"
	    buttons="{%{getText('reporting.ok')}: function(){saveRelatedReports();$( this ).dialog( 'close' );},%{getText('reporting.cancel')}:function(){$( this ).dialog( 'close' );return null;}}"
	/>    
</div> 

<div id="reorganizeLinesDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="clearfix">
	<ps:url id="reorganizeLn"  namespace="/path/templateMaintReport" action="reorganizeLines">
	<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>
	<psj:dialog
		id="reorganizeLines_${_pageRef}" 
		href="%{reorganizeLn}"
	    autoOpen="false"
	    dataType="html"
	    title="%{getText('template.reorganizeTitle')}"
	    modal="true" 
	    width="300"
	    height="160"
	    buttons="{%{getText('reporting.cancel')}:function(){$( this ).dialog( 'close' );return null;},%{getText('reporting.ok')}: function(){submitLinesOrder();$( this ).dialog( 'close' );}}"
	/>    
</div> 	  


<!--<div id="ownershipPercDialogDiv_${_pageRef}" class="clearfix">
	<ps:url id="ownershipPercUrl"  namespace="/path/templateMaintReport" action="ownershipPercDialogUrl">
	</ps:url>
	<psj:dialog
		id="ownershipPercDialog_${_pageRef}" 
		href="%{ownershipPercUrl}"
	    autoOpen="false"
	    dataType="html"
	    title="%{getText('template.relatedReports')}"
	    modal="true" 
	    width="500"
	    height="300"
	    formIds="mainFrmId"
	    buttons="{Cancel:function(){$( this ).dialog( 'close' );return null;},Save: function(){saveRelatedReports();}}"
	/>    
</div> 

--><!--<div id="headerOperatorDiv_${_pageRef}" class="clearfix">
	<ps:url id="headerOperatorUrl"  namespace="/path/templateMaintReport" action="headerOperatorUrl">
	</ps:url>
	<psj:dialog
		id="headerOperatorDialog_${_pageRef}" 
		href="%{headerOperatorUrl}"
	    autoOpen="false"
	    dataType="html"
	    title="%{getText('template.relatedReports')}"
	    modal="true" 
	    width="500"
	    height="300"
	    formIds="mainFrmId"
	    buttons="{Cancel:function(){$( this ).dialog( 'close' );return null;},Save: function(){saveRelatedReports();}}"
	/>    
</div> 
			
		 	 	-->
<div id="createLikeDiv_<ps:property value="_pageRef" escapeHtml="true"/>" class="clearfix">
	<ps:url id="createLikeUrl"  namespace="/path/templateMaintReport" action="createLikeUrl">
		<ps:param name="_pageRef" value="_pageRef"></ps:param>
	</ps:url>
	<psj:dialog
		id="createLikeDialog_${_pageRef}" 
		href="%{createLikeUrl}"
	    autoOpen="false"
	    dataType="html"
	    title="%{getText('template.createLike')}"
	    modal="true" 
	    width="300"
	    height="150"
	    buttons="{%{getText('reporting.cancel')}:function(){$( this ).dialog( 'close' );},%{getText('reporting.ok')}: function(){saveCreateLike(this);}}"
	/>    
</div> 


<jsp:include page="/WEB-INF/pages/common/login/InfoBar.jsp" />
		 	 	<ps:url var="urlTag" action="templGridUrl" namespace="/path/templateMaintReport">
		 	 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
		 	 	</ps:url>
						<psjg:grid  id="templGrid_${_pageRef}"  
									gridModel="gridModel"
									dataType="json"
									href="%{urlTag}"
									pager="true"
									navigator="true"
									navigatorSearch="true"
									navigatorSearchOptions="{closeOnEscape: true,closeAfterSearch: true, multipleSearch: true}"
				          			navigatorEdit="false"
				          			navigatorAdd="false"
				          			navigatorDelete="true"
				          			caption =" "
				          			addfunc="newTemplate"
				          			delfunc="deleteTemplate"
				          			ondblclick="selectRowFn()"
				          			navigatorRefresh="true"
				          			hiddengrid="true"
				          			rowNum="3" 
				    	  			rowList="3,5,10,15,20"
				          			sortable="true"
				          			viewrecords="true"
				          			 >
						         <psjg:gridColumn name="glstmpltVO.CODE" index="glstmpltVO.CODE" searchoptions="{sopt:['eq','ne','lt','gt','le','ge']}" width="10" id="templ_Code" title="%{getText('reporting.code')}" colType="number"  editable="false" sortable="true" />
						         <psjg:gridColumn name="glstmpltVO.BRIEF_NAME_ENG" search="true"  index="glstmpltVO.BRIEF_NAME_ENG" id="templ_Desc" title="%{getText('reporting.description')}" colType="text"  editable="false" sortable="true"   />
						         <psjg:gridColumn name="glstmpltVO.DISPLAY_VALUES" index="glstmpltVO.DISPLAY_VALUES" id="displ_val"   title="%{getText('template.displayValules')}" colType="text"  editable="false" sortable="true"   hidden="true"/>
						         <psjg:gridColumn name="glstmpltVO.COMP_CODE" index="glstmpltVO.COMP_CODE" id="comp_code"  title="%{getText('reporting.companyCode')}" colType="number"  editable="false" sortable="true" hidden="true" />
						         <psjg:gridColumn name="concatKey" index="concatKey" id="concat_key" title="concatKeys" colType="number"  editable="false" sortable="false" key="true" hidden="true" />
				       </psjg:grid> 
				       
		 	 
	
	
				<div>
			 	 	<h1 class="headerPortionContent ui-widget ui-state-default">
						<ps:label value="%{getText('template.templateDetails')}"/>
					</h1>
				</div>
	 			<div class="headerPortion" id="templDiv_<ps:property value="_pageRef" escapeHtml="true"/>">
					 <%@include file="TemplateMaint.jsp"%>
				  </div>

	 	<ps:collapsgroup id='tmpMaintCollapsGrp_${_pageRef}'>
			<ps:collapspanel id='tmplMaintCollaps_${_pageRef}'  key="line.lineTitle">
				<ps:url id="urlLinesTag_${_pageRef}" action="templLinesGridUrl" namespace="/path/templateMaintReport">
					<ps:param name="_pageRef" value="_pageRef"></ps:param>
				</ps:url>
				<psjg:grid  id="templLinesGrid_${_pageRef}" 
							gridModel="gridModel"
							dataType="json"
							href="%{urlLinesTag_${_pageRef}}"
							pager="true"
							navigator="true"
							navigatorSearch="false"
							rowNum="5" 
		    	  			rowList="5,10,15,20"
		          			viewrecords="true"
		          			navigatorEdit="false"
		          			navigatorRefresh="false"
		          			navigatorEditOptions="{height:280,reloadAfterSubmit:false}" 
		          			addfunc="addTemplateLines"
		          			delfunc="deleteTemplateLine"
		          			ondblclick="loadGL()"
		          			onCompleteTopics="lineGrdComplete"
		          			 >
		      			 <psjg:gridColumn name="newLineNumber" id="newLineNumber" width="30" title="%{getText('line.lineNbr')}" colType="number"  editable="false" sortable="false" key="true" hidden="false"  /> 
				         <psjg:gridColumn name="glstmpltVO.BRIEF_NAME_ENG" id="desc1" title="%{getText('reporting.desc1')}" colType="text"  editable="false" sortable="false"  />
				         <psjg:gridColumn name="CURRENCYStr" id="currencyStr" width="30" title="%{getText('line.currency')}" colType="text"  /> 				         
				         <psjg:gridColumn name="PRINTEDStr" id="printedStr" title="%{getText('line.printed')}" hidden="true" colType="text"  editable="false" sortable="false"  />
				         <psjg:gridColumn name="glstmpltVO.GL_TYPE" id="glstmpltVO.GL_TYPE"   title="%{getText('line.format')}" hidden="true" colType="text"  editable="false" sortable="false" />
				         <psjg:gridColumn name="glstmpltVO.BOLD" id="bold"  title="%{getText('line.bold')}" colType="text" hidden="true" editable="false" sortable="false" />
				         <psjg:gridColumn name="glstmpltVO.DISPLAY_ZEROS" id="displayZero" title="%{getText('line.displayZero')}" hidden="true" colType="text"  editable="false" sortable="false" />
				         <psjg:gridColumn name="glstmpltVO.SAVE_DATA" id="saveData" title="%{getText('template.saveData')}" hidden="true" colType="text"  editable="false" sortable="false" />
				         <psjg:gridColumn name="glstmpltVO.DISP_LINE_TOT_ZERO" id="dispLineTotZero"  title="%{getText('line.displayLineTotalZero')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="glstmpltVO.FOR_ROUND" id="round"  title="%{getText('line.round')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="glstmpltVO.ADD_DESC1" id="addDesc1"  title="%{getText('line.addDesc1')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="glstmpltVO.ADD_DESC" id="addDesc1"  title="%{getText('line.addDesc1')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="CSV" id="csv"  title="%{getText('line.currency')}" colType="text" hidden="true" />
		     			 <psjg:gridColumn name="glstmpltVO.PRINTED" id="printed"   title="%{getText('line.printed')}" colType="number" hidden="true" />
		     			 <psjg:gridColumn name="glstmpltVO.CURRENCY" id="currency"  title="%{getText('line.currency')}" colType="number"  hidden="true"/>
		      			 <psjg:gridColumn name="glstmpltVO.BOTTOM_BORDER" id="bottomBorder"  title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="glstmpltVO.LINE_NBR" id="temp_line_no" width="30" title="%{getText('line.lineNbr')}" colType="number"  editable="false" sortable="false"  hidden="true" />
				         <psjg:gridColumn name="BOTTOM_BORDERStr" id="bottomBorderStr"  title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="isSubTotalStr" id="isSubTotalStr"  title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />       		
				         <psjg:gridColumn name="glstmpltVO.IS_SUB_TOTAL" id="isSubTotalSt"  title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />
				         <psjg:gridColumn name="glstmpltVO.CSV" id="isSubTotalSt"  title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />       		
				         <psjg:gridColumn name="glstmpltVO.FOR_ROUND" id="isSubTotalSt"  title="%{getText('line.bottomBorder')}" colType="text" hidden="true" />       		       		
		        		 <psjg:gridColumn name="glstmpltVO.CODE" id="glstmpltVO.CODE"  title="code" colType="text" hidden="true" />
		       			 <psjg:gridColumn name="glstmpltVO.BRIEF_NAME_ARAB" colType="text"   id="glstmpltVO.BRIEF_NAME_ARAB"  title="BRIEF_NAME_ARAB"  hidden="true" />
		       			 <psjg:gridColumn name="glstmpltVO.POST_CODE" colType="text"   id="glstmpltVO.POST_CODE"  title="POST_CODE"  hidden="true" />
		       			 <psjg:gridColumn name="glstmpltVO.PERCENTAGE" colType="text"   id="glstmpltVO.PERCENTAGE"  title="glstmpltVO.PERCENTAGE"  hidden="true" />
		        </psjg:grid> 
		        	<ptt:toolBar id="deleteAllTool_${_pageRef}">
						<table width="100%">
							<tr>
								<td width="100%">
									<psj:a   button="true"  onclick="deleteAllLines()">
										<ps:text name="reporting.deleteAll"/>
									</psj:a>
								</td>
							</tr>
						</table>
					</ptt:toolBar>
	 	</ps:collapspanel> 
 	 
<ps:collapspanel id="lineDivId"  key="line.lineDetails">
 <ps:form id="lineFrmId_${_pageRef}" name="lineFrmId" action="validateLine" namespace="/path/templateMaintReport"  method="POST" >
 	 <table class="headerPortionContent ui-widget-content" width="100%" >
		<tr>
			 <td width="10%"><ps:label key="line.lineNbr" for="lineNbr_${_pageRef}"/></td>
			 <td width="6%"><ps:textfield name="glstmpltCO.glstmpltVO.LINE_NBR" id="lineNbr_${_pageRef}" size="3" mode="number" minValue="1" emptyValue="0" nbFormat="######" maxlength="6" required="true"></ps:textfield></td>
			 <td width="12%" align="right"><ps:label value="%{getText('reporting.postCode')}"/></td>
			 <td colspan="3" width="22%"> 
			 <ps:textfield size="32" name="glstmpltCO.glstmpltVO.POST_CODE" id="postCode_${_pageRef}" maxlength="30"></ps:textfield>
	         </td>
	         
			 <td  width="10%" align="center"><ps:label value="%{getText('line.bottomBorder')}"/></td>
			 <td colspan="2"   width="16%"> 
				<ps:select
					list="bottomBorderArrList"
					listKey="VALUE_CODE"
					listValue="VALUE_DESC"
					name="glstmpltCO.glstmpltVO.BOTTOM_BORDER" 
					id="bottomBorder_${_pageRef}"
					/>
			 </td>
			
			
			 <td  width="8%" align="center"><ps:label value="%{getText('line.currency')}"/></td>
			<td  width="16%" colspan="2"> 
			
				<table width="100%">
					<tr>
						<td width="45%">
							<psj:livesearch
								id="csvLookUp_${_pageRef}" 
								name="glstmpltCO.glstmpltVO.CURRENCY"
								readOnlyMode  ="false"
								actionName="${pageContext.request.contextPath}/pathdesktop/currencyLookup_constructLookup"
								searchElement="CURRENCY_CODE" 
								resultList="CURRENCY_CODE:lookuptxt_csvLookUp_${_pageRef},BRIEF_DESC_ENG:currencyStr_${_pageRef}"
								mode="number" maxlength="3"
								parameter="CURRENCY_CODE:lookuptxt_csvLookUp_${_pageRef}"
					            dependencySrc="${pageContext.request.contextPath}/pathdesktop/CurrencyDependencyAction_dependencyByCurrencyCode.action?displayMsg=1"
					            dependency="lookuptxt_csvLookUp_${_pageRef}:currency.CURRENCY_CODE,currencyStr_${_pageRef}:currency.BRIEF_DESC_ENG">
							</psj:livesearch>	
					    </td>
						<td width = "55%">
							<ps:textfield id="currencyStr_${_pageRef}" name="glstmpltCO.CURRENCYStr"  readonly="true"/>
						</td>						 
					</tr>
				</table>
			</td>
		
		</tr>
		
		<tr>
			 <td nowrap="nowrap"><ps:label  key="template.briefName" for="desc1_${_pageRef}"/></td>
			 <td colspan="5"><ps:textfield size="70" name="glstmpltCO.glstmpltVO.BRIEF_NAME_ENG" id="desc1_${_pageRef}" maxlength="500"></ps:textfield></td>
			 <td align="left"><ps:label value="%{getText('reporting.briefNameArab')}" /></td>
			 <td colspan="5"> 
				<ps:textfield onlyArabic="true" size="70" name="glstmpltCO.glstmpltVO.BRIEF_NAME_ARAB" id="templBriefArabLine_${_pageRef}" maxlength="500"></ps:textfield>
		     </td>
		</tr>
		
		<tr>
			<td nowrap="nowrap"><ps:label value="%{getText('line.addDesc1')}"/></td>
			<td colspan="5"> <ps:textfield size="70" name="glstmpltCO.glstmpltVO.ADD_DESC" id="addDesc1_${_pageRef}" maxlength="30"></ps:textfield></td>
			
			 <td align="right" nowrap="nowrap"><ps:label value="%{getText('line.addDesc2')}"/></td>
			<td colspan="5"> <ps:textfield size="70" name="glstmpltCO.glstmpltVO.ADD_DESC1" id="addDesc2_${_pageRef}" maxlength="256"></ps:textfield></td>
		</tr>
		
		<tr>
		  <td colspan="12">
		   <table>
		   <tr>
				<td width="2%" nowrap="nowrap"><ps:label value="%{getText('line.displayLineTotalZero')}"/></td>
				<td width="2%" nowrap="nowrap"> <ps:checkbox name="glstmpltCO.glstmpltVO.DISP_LINE_TOT_ZERO" id="displTotZero_${_pageRef}"></ps:checkbox></td>
				
				<td width="2%" align="right" nowrap="nowrap"><ps:label value="%{getText('line.bold')}"/></td>
				<td width="2%" align="center" nowrap="nowrap"><ps:checkbox name="glstmpltCO.glstmpltVO.BOLD" id="bold_${_pageRef}"></ps:checkbox></td>
				
				<td width="2%" align="right" nowrap="nowrap"><ps:label value="%{getText('line.printed')}"/></td>
				<td width="2%" align="center" nowrap="nowrap"><ps:checkbox id="printed_${_pageRef}"  name="glstmpltCO.PRINTEDStr"></ps:checkbox></td>
			
				<td  width="2%" align="center" nowrap="nowrap"><ps:label value="%{getText('template.glType')}"/></td>
				<td  width="5%" nowrap="nowrap"> 
					<ps:select list="glDispArrList" listKey="VALUE_CODE" emptyOption="true" listValue="VALUE_DESC" name="glstmpltCO.glstmpltVO.GL_TYPE" id="gltypeid_${_pageRef}"	/>
				</td>
				<td width="2%" align="center" nowrap="nowrap"><ps:label value="%{getText('template.subTotal')}"/></td>
				<td width="2%" nowrap="nowrap"><ps:checkbox name="glstmpltCO.isSubTotalStr" id="isSubTotal_${_pageRef}"></ps:checkbox></td>
				<td width="2%" align="center" nowrap="nowrap"><ps:label value="%{getText('template.csv')}"/></td>
				<td width="2%" nowrap="nowrap"><ps:checkbox name="glstmpltCO.csvStr" id="isCsv_${_pageRef}"></ps:checkbox></td>
				<td width="2%" align="center" nowrap="nowrap"><ps:label value="%{getText('line.round')}"/></td>
				<td width="2%" nowrap="nowrap"><ps:checkbox name="glstmpltCO.forRoundStr" id="isForRound_${_pageRef}"></ps:checkbox></td>
				<td width="2%" align="center" nowrap="nowrap"><ps:label value="%{getText('line.displayZero')}"/></td>
				<td width="2%" nowrap="nowrap"><ps:checkbox name="glstmpltCO.glstmpltVO.DISPLAY_ZEROS" id="displZero_${_pageRef}"></ps:checkbox></td>
		  		<td width="2%" align="center" nowrap="nowrap"><ps:label value="%{getText('template.saveData')}"/></td>
		  		<td width="2%" nowrap="nowrap"><ps:checkbox name="glstmpltCO.glstmpltVO.SAVE_DATA" id="saveData_${_pageRef}"></ps:checkbox></td>
		  		<td width="2%" nowrap="nowrap"><ps:label value="%{getText('reporting.percentage')}" id="linePercentageLbl_${_pageRef}"/></td>
			    <td width="3%" nowrap="nowrap"><ps:textfield name="glstmpltCO.glstmpltVO.PERCENTAGE" id="linePercentage_${_pageRef}"  mode="number" minValue="0" maxLenBeforeDec="10"	nbFormat="#.000000"></ps:textfield></td>
		  </tr>
		  </table>
		 </td>
		</tr>
		<tr>
			<td colspan="12">
				<ptt:toolBar id="templTlBar_${_pageRef}">
					<psj:submit id="linesOK_${_pageRef}"  button="true" buttonIcon="ui-icon-disk">
						<ps:label key="reporting.ok"></ps:label>
					</psj:submit>
         		</ptt:toolBar> 
			</td>
		</tr>
		</table>
		 <ps:hidden name="glstmpltCO.numberAfter" id="numberAfter_${_pageRef}"></ps:hidden>
		</ps:form>
 	 </ps:collapspanel>
 	 
	 	 	<ps:collapspanel id='templMaintDetCollaps_${_pageRef}'  key="reporting.details">
		 		<psj:tabbedpanel id="tabs" onselect="checkTmplSelectedTab">
	 	 	
				<psj:tab id="custTab1" href="${pageContext.request.contextPath}/path/templateMaintReport/openGLList.action?tempCodeWithLineNb=0~0&_pageRef=${_pageRef}" key="gl.glTitle"/>
				<psj:tab id="custTab2" href="${pageContext.request.contextPath}/path/templateMaintReport/openCriteria.action?tempCodeWithLineNb=6~0&_pageRef=${_pageRef}" key="criteria.criteriaTitle" />
				<psj:tab id="custTab3" href="${pageContext.request.contextPath}/path/templateMaintReport/openExpression.action?tempCodeWithLineNb=0~0&_pageRef=${_pageRef}" key="designer.expressions" />
				<psj:tab id="custTab4" href="${pageContext.request.contextPath}/path/templateMaintReport/openMismatch.action?tempCodeWithLineNb=0~0&_pageRef=${_pageRef}" key="template.mismatch" />
				
              </psj:tabbedpanel>
		</ps:collapspanel>
 	 
 	</ps:collapsgroup> 
 	 
 	 



	<ptt:toolBar  id="templTlBar_${_pageRef}"  > 
	<psj:submit type="button" button="true" buttonIcon="ui-icon-disk" onclick="clickOtherButton()" >
		<ps:text name="reporting.save"></ps:text>
	</psj:submit>
	</ptt:toolBar>

		
 <ps:hidden name="updates" id="updates"/>
 <ps:hidden name="mode" id="mode1"></ps:hidden>
 <ps:hidden name="templateCode" id="templateCode"></ps:hidden>	
 
		
	<script  type="text/javascript">
    $("#gview_templGrid_"+_pageRef+" div.ui-jqgrid-titlebar").hide();
	$("#templGrid_"+_pageRef).jqGrid('filterToolbar',{searchOnEnter : true});
	$("#linesOK_"+_pageRef).attr('aria-disabled',true).addClass('ui-button-disabled ui-state-disabled');
	document.getElementById("linesOK_"+_pageRef).disabled=true
</script>

