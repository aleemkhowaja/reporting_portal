<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@ taglib prefix="ptt"  uri="/path-toolbar-tags" %>
<script type="text/javascript">

$(document).ready(function()
{	
	rep_tempHeaderFrm_readyFunc();
});

</script>
<ps:form applyChangeTrack="true" id="templateHeaderForm_${_pageRef}" useHiddenProps="true"	namespace="/path/templateHeader">
	<ps:hidden id="mode_${_pageRef}" name="mode"></ps:hidden>
	<ps:hidden id="DATE_UPDATED_${_pageRef}"
		name="glsheaderCO.glsheaderVO.DATE_UPDATED" />
  		<table width="100%" cellspacing="0" cellpadding="5" border="0">
  		<tr>
	  			<td colspan="8"><h1 class="headerPortionContent ui-widget ui-state-default"><ps:label key="tmplHeader.templateHeaderInf"/></h1></td>
	  			<td colspan="0"></td>
	  	</tr>
  		<tr>
  			<td width="5%"><ps:label key="tmplHeader.code" for="codeTmplHead_${_pageRef}" /></td>
  			<td width="11%"><ps:textfield onchange="checkIDAvailabilityTh()" id="codeTmplHead_${_pageRef}" name="glsheaderCO.glsheaderVO.CODE" mode="number" size="4"  minValue="0" maxlength="4" nbFormat="####" /></td>
  			<td width="9%"><ps:label key="tmplHeader.briefDescEng" for="nameTmplHead_${_pageRef}" /></td>
  			<td width="11%"><ps:textfield id="nameTmplHead_${_pageRef}" name="glsheaderCO.glsheaderVO.BRIEF_NAME_ENG"  maxlength="80"/></td>
  			<td width="9%"><ps:label key="tmplHeader.briefDescArab" for="namearabTmplHead_${_pageRef}" /></td>
  			<td width="11%"><ps:textfield id="namearabTmplHead_${_pageRef}" name="glsheaderCO.glsheaderVO.BRIEF_NAME_ARAB"  maxlength="80"/></td>
  			<td width="9%" ><ps:label key="reporting.type" for="tmpltTypeTmplHead_${_pageRef}" /></td>
			<td  width="11%"><ps:select id="tmpltTypeTmplHead_${_pageRef}" name="glsheaderCO.glsheaderVO.TEMPLATE_TYPE" list="tmpltTypeList" listKey="VALUE_CODE" listValue="VALUE_DESC" ></ps:select></td>
  			<td colspan="2"></td>		
  		</tr>
  		<tr>
  			<td colspan="8"></td>
  		</tr>
  		<tr>
  			<td colspan="8"><h1 class="headerPortionContent ui-widget ui-state-default"><ps:label key="tmplHeader.leftInf"/></h1></td>
  			<td colspan="0"></td>
  		</tr>
  		<tr>
  			<td width="5%"><ps:label key="tmplHeader.first" for="leftFirst_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="leftFirst_${_pageRef}" name="glsheaderCO.glsheaderVO.LEFT1"   maxlength="60"/></td>
			<td width="5%"><ps:label key="tmplHeader.second" for="leftSecond_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="leftSecond_${_pageRef}" name="glsheaderCO.glsheaderVO.LEFT2"   maxlength="60"/></td>
			<td width="5%"><ps:label key="tmplHeader.third" for="leftThird_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="leftThird_${_pageRef}" name="glsheaderCO.glsheaderVO.LEFT3"   maxlength="60"/></td>
			<td width="5%"><ps:label key="tmplHeader.fourth" for="leftFourth_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="leftFourth_${_pageRef}" name="glsheaderCO.glsheaderVO.LEFT4"   maxlength="60" /></td>				
		</tr>
		<tr><td colspan="8"></td></tr>
		<tr>
  			<td colspan="8"><h1 class="headerPortionContent ui-widget ui-state-default"><ps:label key="tmplHeader.centerInf"/></h1></td>
  			<td colspan="0"></td>
  		</tr>
		<tr>
			
  			<td width="5%"><ps:label key="tmplHeader.first" for="centerFirst_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="centerFirst_${_pageRef}" name="glsheaderCO.glsheaderVO.CENTER1"   maxlength="100"/></td>
			<td width="5%"><ps:label key="tmplHeader.second" for="centerSecond_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="centerSecond_${_pageRef}" name="glsheaderCO.glsheaderVO.CENTER2"   maxlength="100"/></td>
			<td width="5%"><ps:label key="tmplHeader.third" for="centerThird_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="centerThird_${_pageRef}" name="glsheaderCO.glsheaderVO.CENTER3"   maxlength="100"/></td>
			<td colspan="2"></td>		
		</tr>
		<tr><td colspan="8"></td></tr>
		<tr>
  			<td colspan="8"><h1 class="headerPortionContent ui-widget ui-state-default"><ps:label key="tmplHeader.rightInf"/></h1></td>
  			<td colspan="0"></td>
  		</tr>
		<tr>
			
			<td width="5%"><ps:label key="tmplHeader.first" for="rightFirst_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="rightFirst_${_pageRef}" name="glsheaderCO.glsheaderVO.RIGHT1"   maxlength="60"/></td>
			<td width="5%"><ps:label key="tmplHeader.second" for="rightSecond_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="rightSecond_${_pageRef}" name="glsheaderCO.glsheaderVO.RIGHT2"   maxlength="60"/></td>
			<td width="5%"><ps:label key="tmplHeader.third" for="rightThird_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="rightThird_${_pageRef}" name="glsheaderCO.glsheaderVO.RIGHT3"   maxlength="60"/></td>
			<td width="5%"><ps:label key="tmplHeader.fourth" for="rightFourth_${_pageRef}"/></td>
			<td width="17%"><ps:textfield id="rightFourth_${_pageRef}" name="glsheaderCO.glsheaderVO.RIGHT4"   maxlength="60"/></td>				
		</tr>
	</table>

<table border="0" width="100%">
<tr><td>
<ptt:toolBar  id="templTlBar"> 
	<psj:submit  button="true" buttonIcon="ui-icon-disk" >
		<ps:label key="reporting.save"></ps:label>
	</psj:submit>
	<psj:submit type="button" button="true"  buttonIcon="ui-icon-disk" onclick="createLike()" >
		<ps:label key="tmplHeader.createLike"></ps:label>
	</psj:submit>
	<psj:submit type="button"  button="true" buttonIcon="ui-icon-disk" onclick="previewTemplateHeader()" >
		<ps:label key="tmplHeader.preview"></ps:label>
	</psj:submit>
</ptt:toolBar>	
</td></tr>
<tr><td>

<div id="thDivPreview_<ps:property value="_pageRef" escapeHtml="true"/>">
</div>
</td></tr></table>
</ps:form>

