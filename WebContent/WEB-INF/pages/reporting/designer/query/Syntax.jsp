<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="sqbNameExistAlert_var" 		value="%{getEscText('qry.qryNameExistAlert')}"/>

<html>

<script type="text/javascript"> 
var sqbNameExistAlert 		= '<ps:property value="sqbNameExistAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
$(document).ready(function () 
{
	$.struts2_jquery.require("Syntax.js", null,jQuery.contextPath + "/path/js/reporting/designer/");
	rep_qry_documentReadyFunc()
});

rep_qry_insertArgEvent();
</script>


<body>
	<table width="100%" id="syntaxTbl_${_pageRef}">
<tr>
		   <td nowrap="nowrap"  colspan="2">
		   		<table  width="100%">
		   			<tr>
		   				<td align="left" nowrap="nowrap" width="45%" id="qryNameTr_${_pageRef}" style="display: none;">
		   				<table width="100%">
		   					<tr>
		   						<td align="left" width="10%">
		   							<ps:label value="%{getText('queryName')}"/>
		   						</td>
		   						<td align="left" width="90%">
		  							<ps:textfield  name="queryCO.QUERY_NAME" id="sqbQryName_${_pageRef}" maxlength="50" onchange="checkSqbQryName()"></ps:textfield>
		  						</td>
		  					</tr>
		  				</table>
		  				</td>
		  			   <td align="left" nowrap="nowrap" width="55%" id="connectionsLs_<ps:property value="_pageRef" escapeHtml="true"/>">
		  			   <table width="100%">
		  			   		<tr>
		  			   			<td align="left" width="20%" id="connDesc_<ps:property value="_pageRef" escapeHtml="true"/>">
					  	    		<ps:label value="%{getText('connection.conDescription')}"/>
					  	    	</td>
					  	    	<td align="left" width="30%" id="connSelect_<ps:property value="_pageRef" escapeHtml="true"/>">
					 	   			<ps:select  id="connectionSyntax_${_pageRef}" name="queryCO.CONN_ID" list="connectionList" listKey="CONN_ID" 
					  				listValue="CONN_DESC"></ps:select>
					  			</td>
					  			<td></td>
					  	    </tr>
					  	</table>
					   </td>
		  			</tr>
		  		</table>
		  </td>
		</tr>
	
	
	   <tr id="qbeSyntaxArea_<ps:property value="_pageRef" escapeHtml="true"/>">
			<td align="center" colspan="2">
				<ps:textarea 
					cssStyle="height: 100%;"
						name="qrySyntax" 
						id="qrySyntax_${_pageRef}" 
						rows="10" 
						cols="90" 
						readonly="true"
				/>	
			</td>
	   </tr>	
	   
	   <tr id="sqbSyntaxArea_<ps:property value="_pageRef" escapeHtml="true"/>" style="display: none;">
		    <td width="30%" valign="top">
				<ps:url id="urlTag" action="queryDesign_argumenGridUrl" namespace="/path/designer">
					<ps:param name="_pageRef" value="_pageRef"></ps:param>
				</ps:url>
				<psjg:grid id="syntaxArgumentsGrid_${_pageRef}" gridModel="gridModel" dataType="json"
							href="%{urlTag}" pager="true" navigator="false" 
							viewrecords="true" rowNum="20" rowList="5,10,15,20" 
							onSelectRowTopics="insertArgument">
	
	
							<psjg:gridColumn name="ARGUMENT_NAME" index="ARGUMENT_NAME"
								id="argumentName" width="7"
								title="%{getText('syntax.syntax_argument')}" colType="text"
								editable="false" sortable="false" />
						
							<psjg:gridColumn name="ARGUMENT_TYPE" index="ARGUMENT_TYPE"
								id="argumentType" width="7"
								title="%{getText('reporting.type')}" colType="text"
								editable="false" sortable="true" hidden="false"/>
							<psjg:gridColumn name="MULTISELECT_YN" index="MULTISELECT_YN"
								id="MULTISELECT_YN" width="7"
								title="MULTISELECT_YN" colType="text"
								editable="false" sortable="true" hidden="true"/>
	
	
				</psjg:grid>
			</td>
		
			<td align="center" valign="top">
				<ps:textarea 
					cssStyle="height: 100%;"
						id="sqbSyntax_${_pageRef}" 
						name="sqbSyntax" 
						rows="15" 
						cols="100" 
						
				/>	
			</td>
	   </tr>    
	</table>				
    <p id="statuss_<ps:property value="_pageRef" escapeHtml="true"/>" class="formErrorContent"></p>
</body>

<script type="text/javascript">
       rep_designer_hideSyntGrid();
        
	</script>
</html>

	   
