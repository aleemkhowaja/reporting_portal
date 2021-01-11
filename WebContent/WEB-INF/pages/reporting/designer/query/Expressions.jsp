<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>
<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>

<ps:set name="fillExprNameAlert_var" 	value="%{getEscText('expr.fillExprName')}"/>
<ps:set name="emptyExpAlert_var" 		value="%{getEscText('expr.emptyExpAlert')}"/>
<ps:set name="invalidExpAlert_var" 		value="%{getEscText('expr.invalidExpAlert')}"/>
<ps:set name="aggrAlert_var" 			value="%{getEscText('expr.aggrAlert')}"/>


<html>
		<script type="text/javascript">
	
		var fillExprNameAlert 	= '<ps:property value="fillExprNameAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
		var emptyExpAlert 		= '<ps:property value="emptyExpAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
		var invalidExpAlert 	= '<ps:property value="invalidExpAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
		var aggrAlert 			= '<ps:property value="aggrAlert_var"  escapeHtml="false" escapeJavaScript="true"/>'
		
		   $(document).ready(function () 
			{

	   			var collapseDiv = $("#ExprDivId_"+_pageRef+" > .collapsibleContainerTitle").get(0);
	   			if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	   			{
	   				$(collapseDiv).trigger("click");
	   			}

				document.getElementById("exprName_"+_pageRef).focus();
			}
			);
			function insertField()
			{
	   				var rowid =	$("#selFieldsExprGrid_"+_pageRef).jqGrid('getGridParam','selrow'); 
	   				myObject = $("#selFieldsExprGrid_"+_pageRef).jqGrid('getRowData',rowid); 
	   				var parentName=myObject["entityAliasWithCount"];
	   				if(parentName!=null && parentName!="")
	   				{
						parentName+=".";
	   				}
	   				// kassem.kazan and anna.succar 2-Aug-2013: add spaces in attribute values to preserve double quotation on IE8
		   			//var htmlToAdd = "<FONT onresizestart=\" event.returnValue=false; \" contentEditable=\" false \" value='#VAR#<param id=\""+myObject["index"]+"\" fieldType=\""+myObject["FIELD_DATA_TYPE"]+"\" pId=\""+myObject["PARENT_INDEX"]+"\"></param>#VAR#' fieldType="+myObject["FIELD_DATA_TYPE"]+">["+parentName+myObject["FIELD_ALIAS"]+"]</FONT>";
		   			//code above has been removed(code is now reverted to previous version) since the expression is not appearing properly in the query syntax
	   			    var htmlToAdd = "<FONT onresizestart=event.returnValue=false; contentEditable=false value='#VAR#<param id=\""+myObject["index"]+"\" fieldType=\""+myObject["FIELD_DATA_TYPE"]+"\" pId=\""+myObject["PARENT_INDEX"]+"\"></param>#VAR#' fieldType="+myObject["FIELD_DATA_TYPE"]+">["+parentName+myObject["FIELD_ALIAS"]+"]</FONT>";
	   			    insertAdjHtml(htmlToAdd);
		 	}
	   		function displaySelFields()
			{
	   				var rowid = $("#selEntExprGrid_"+_pageRef).jqGrid('getGridParam','selrow');
	   				myObject = $("#selEntExprGrid_"+_pageRef).jqGrid('getRowData', rowid);

	   				var entName=myObject["entityAliasWithCount"];
					if(entName=="AGGREGATES")
					{
						rowid="1000001111222"
					}
					//reload the Selected fields Grid
					$("#selFieldsExprGrid_"+_pageRef).jqGrid('setGridParam',
			   			  	{    
			   				url : "${pageContext.request.contextPath}/path/designer/queryDesign_loadSelFieldsGrid.action?locIndex="+rowid+"&_pageRef="+_pageRef,
			   				page : 1
			   				}).trigger("reloadGrid");
		 	}
		    function fillExprForm() 
			{
	   				rowid = $("#exprGrids_"+_pageRef).jqGrid('getGridParam','selrow');
					/*load details in the edit screen*/
					viewExprDetails(rowid);
			}


		    function viewExprDetails(rowid)
		    {
			    //expand div if collapsed
		    	var collapseDiv = $("#ExprDivId_"+_pageRef+" > .collapsibleContainerTitle").get(0);
				if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
				{
					$(collapseDiv).trigger("click");
				}

				//fill Details
				var url = jQuery.contextPath+ "/path/designer/queryDesign_openExpr";
				myObject = $("#exprGrids_"+_pageRef).jqGrid('getRowData',rowid);
				params = {};
				paramStr = JSON.stringify(myObject)
				paramStr = "{"+ "fieldCO:"+paramStr + "}";
				params["updates"] = paramStr;
				params["_pageRef"]=_pageRef;
			    $.get(url, params , function( param )
			 	{
			    	$("#exprName_"+_pageRef).val(param["fieldCO"].labelAlias);
					$("#exprId_"+_pageRef).val(param["fieldCO"].index);
					$("#statementValueAsIs_"+_pageRef).val(param["fieldCO"].html);
					// This space has been added because in IE9 when modifying a expression that ends with a field the added data will be appended to the font tag 
					document.getElementById("statementContent_"+_pageRef).innerHTML=param["fieldCO"].html+" ";
					document.getElementById("statementContent_"+_pageRef).focus();
			 	}
		    	);
				

		    }

				function addExpr()
				{
					//check if exprName is filled 
					var exprName=$("#exprName_"+_pageRef).val();
					if(exprName=="")
					{
						_showErrorMsg(fillExprNameAlert);
						return;
					}
					
					$("#statementValueAsIs_"+_pageRef).val(document.getElementById("statementContent_"+_pageRef).innerHTML);
					//check validity
					checkExpValidity();
				}


				function checkExpValidity()
				{
					var zSrc="${pageContext.request.contextPath}/path/designer/queryDesign_checkExpValidity";
					params ={};
					params["updates"] = $("#statementValueAsIs_"+_pageRef).val();
					$.getJSON(zSrc, params, function( data2, status, xhr ) 
					{
					   var expVal=data2['updates'];
					   if(expVal == "wrong")
				       {
						   _showErrorMsg(invalidExpAlert);
				           return;
				       }
					   else if(expVal=="aggr")
					   {
						   _showErrorMsg(aggrAlert);
							return;
					   }
				       else
				       {
					    	 //save expression
							 var url = $("#exprMaintFrmId_"+_pageRef).attr("action");
						     myObject =  $("#exprMaintFrmId_"+_pageRef).serialize();
						     myObject+="&updates="+expVal+"&_pageRef="+_pageRef;
						     $.post(url, myObject , function( param )
							 {
						    	  $("#exprGrids_"+_pageRef).trigger("reloadGrid");
							 });

							 emptyExprForm();
				       }
					}); 
				}



				
				function deleteExpr(rowid)
				{
				   _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
		           if(confirmcChoice)
		           {
		        	   deleteTheExpr(theArgs.rowid)
		           }
			      }, {rowid : rowid});			
				}

				function deleteTheExpr(rowid)
				{
					myObject = $("#exprGrids_"+_pageRef).jqGrid('getRowData',rowid);
				    var exprId = myObject["index"];
				    
					var url = jQuery.contextPath+ "/path/designer/queryDesign_deleteExpr";
					url+="?locIndex="+exprId+"&_pageRef="+_pageRef;
				    $.get(url, params , function( param )
				 	{
				   	  $("#exprGrids_"+_pageRef).trigger("reloadGrid");
				 	});
				    emptyExprForm();				
				}


				function emptyExprForm()
				{
					$("#exprName_"+_pageRef).val("");
					$("#exprId_"+_pageRef).val("");
					$("#statementValueAsIs_"+_pageRef).val("");
					document.getElementById("statementContent_"+_pageRef).innerHTML="";
					
				}
				

				 function insertAdjHtml(htmlToAdd)
				 {
					 // kassem.kazan and anna.succar 2-Aug-2013: commented out the call to this function 
					 //since it's not compatible on IE8
              		 // insertAdjacentHTMLForMoz(document.getElementById('statementContent_'+_pageRef),htmlToAdd,'beforeEnd');
				      $('#statementContent_'+_pageRef).focus();
				      $('#statementContent_'+_pageRef).append(htmlToAdd);   
				    
				 }
				 
				 /*
				 function insertAdjacentHTMLForMoz(nodeObj,sqlValue,where)
				 {
					  nodeObj.focus();
				       var r = nodeObj.ownerDocument.createRange();
				       r.setStartBefore(nodeObj);
				       var parsedHTML = r.createContextualFragment(sqlValue);
				       insertAdjacentElement(where,parsedHTML,nodeObj);
				 }

				 function insertAdjacentElement (where,parsedNode,nodeObj)
			        {
			            switch (where){
			            case 'beforeBegin':
			                    nodeObj.parentNode.insertBefore(parsedNode,nodeObj)
			                    break;
			            case 'afterBegin':
			                    nodeObj.insertBefore(parsedNode,nodeObj.firstChild);
			                    break;
			            case 'beforeEnd':
			                    nodeObj.appendChild(parsedNode);
			                    break;
			            case 'afterEnd':
			                    if (nodeObj.nextSibling)
			           			nodeObj.parentNode.insertBefore(parsedNode,nodeObj.nextSibling);
			             else nodeObj.parentNode.appendChild(parsedNode);
			                    break;
			            }
			        }*/

					function contextFn(event)
					{
						 event.stopPropagation();
					}
					
					function checkSpecialChar()
					{
						var expN=$("#exprName_"+_pageRef).val();
						var re = new RegExp("[\'|\"|\/|+|*|\\-|.|$|@|#]" , "g");
						expN=expN.replace(re,"")
						$("#exprName_"+_pageRef).val(expN);
					}

	
		</script>
		<ps:collapsgroup id="sort21_${_pageRef}">
			<ps:collapspanel id="exprGrid_${_pageRef}"  key="expr.ExpressionList">	
						
						<ps:url var="urlTag" action="queryDesign_loadExprGrid" namespace="/path/designer">
							<ps:param name="_pageRef" value="_pageRef"></ps:param>
						</ps:url>
						<psjg:grid  id="exprGrids_${_pageRef}" 
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
		          			addfunc="addExpr"
		          			delfunc="deleteExpr"
		          			rowNum="3" 
		    	  			rowList="5,10,15,20"
		    	  			ondblclick="fillExprForm()"
		          		 >
		      				<psjg:gridColumn name="labelAlias" index="labelAlias" id="exprAlias"   title="%{getText('expr.ExpressionName')}" colType="text"  editable="false" sortable="false" />
				        	 <psjg:gridColumn name="fieldSyntax"  index="fieldSyntax" id="fieldSyntaxExpr"  title="Parsed Expr" colType="text"  editable="false" sortable="false" hidden="true"/>
				        	 <psjg:gridColumn name="index"  index="index" id="exprId"  title="id" colType="number"  editable="false" sortable="false" hidden="true"/>
		    		       </psjg:grid> 
		       
		       </ps:collapspanel>
		       
		       
							
							<ps:collapspanel id='ExprDivId_${_pageRef}'  key="reporting.details">	
							 <ps:form id='exprMaintFrmId_${_pageRef}' name="exprMaintFrmId" action="queryDesign_validateExpr" namespace="/path/designer"  method="POST" >
							  <table width="100%"  class="headerPortionContent ui-widget-content" border="0" >
								
								<tr>
								 	<td align="right">
										 <ps:label value="%{getText('reporting.name')}"/>
									 </td>
									 <td>
										 <ps:textfield name="fieldCO.labelAlias" id="exprName_${_pageRef}" maxlength="50" onkeyup="checkSpecialChar()"></ps:textfield>
										 <ps:hidden id="exprId_${_pageRef}" name="fieldCO.index"></ps:hidden>
									 </td>
								</tr>	
									
								<tr>
								 <td colspan="2">
									 <div>
									 	<h1 class="headerPortionContent ui-widget ui-state-default">
											<ps:label value="%{getText('expr.exprEditor')}"/>
										</h1>
									</div>
		 							<div class="headerPortion">
										 <div contentEditable='true' id="statementContent_<ps:property value="_pageRef" escapeHtml="true"/>" onContextMenu='contextFn(event)' class="path-editable-div" style="overflow:auto;overflow:-moz-scrollbars-vertical;width:855px;height:100px;border: 1px gray solid;" ></div>
	               						 <ps:hidden id="statementValueAsIs_${_pageRef}" name="fieldCO.html"></ps:hidden>
									</div>
								 </td>
								</tr>	
									
								<tr>
									 <td colspan="2">
										<div>
									 	<h1 class="headerPortionContent ui-widget ui-state-default">
											<ps:label value="%{getText('query.fields')}"/>
										</h1>
									</div>
		 							<div class="headerPortion">
 						  				<table width="100%" border="0" >
 						  				<tr>
 						  				<td width="50%" valign="top">
 						  					   <div> 
											 	<ps:url var="urlSelEntExprTag" action="queryDesign_loadExprEntGrid" namespace="/path/designer">
											 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
											 	</ps:url>
												<psjg:grid  id="selEntExprGrid_${_pageRef}" 
													gridModel="gridModel"
													dataType="json"
													href="%{urlSelEntExprTag}"
													pager="true"
													navigator="true"
													navigatorAdd="false"
													navigatorDelete="false"
													navigatorEdit="false"
													navigatorRefresh="false"
													navigatorView="false"
													navigatorSearch="false"
								          			rowNum="4" 
								    	  			rowList="5,10,15,20"
								    	  			ondblclick="displaySelFields()"
								    	  			>
													 <psjg:gridColumn name="entityAliasWithCount" index="entityAliasWithCount" id="exprEntityAliasWithCount"   title="%{getText('qry.TblName')}" colType="text"  editable="false" sortable="false" />
													
													   <psjg:gridColumn name="ENTITY_ALIAS" index="ENTITY_ALIAS" id="selEntAlias"   title="%{ent alias" colType="text" hidden="true" />
													   <psjg:gridColumn name="ENTITY_DB_NAME" index="ENTITY_DB_NAME"  id="selEntDb"  title="db name" colType="text"  editable="false" sortable="false" hidden="true"/>
													   <psjg:gridColumn name="index"  index="index" id="index"  title="id" colType="number"  editable="false" sortable="false" hidden="true" key="true"/>
								    		       </psjg:grid> 
									 	</div>
 						  				</td>
 						  				
 						  				<td valign="top">
 						  					
 						  					
 						  						<div> 
											 	<ps:url var="urlSelFieldsExprTag" action="queryDesign_loadSelFieldsGrid" namespace="/path/designer">
											 		<ps:param name="_pageRef" value="_pageRef"></ps:param>
											 	</ps:url>
												<psjg:grid  id="selFieldsExprGrid_${_pageRef}" 
													gridModel="gridModel"
													dataType="json"
													href="%{urlSelFieldsExprTag}"
													pager="true"
													navigator="true"
													navigatorAdd="false"
													navigatorDelete="false"
													navigatorEdit="false"
													navigatorRefresh="false"
													navigatorView="false"
													navigatorSearch="false"
								          			rowNum="4" 
								    	  			rowList="5,10,15,20"
								    	  			ondblclick="insertField()"
								    	  			>
													 <psjg:gridColumn name="FIELD_ALIAS" index="FIELD_ALIAS" id="selFeAliasExpr"   title="%{getText('field.alias')}" colType="text"  editable="false" sortable="false" />
													 
													 <psjg:gridColumn name="entityAliasWithCount" index="entityAliasWithCount" id="selEntityAliasWithCountExpr"   title="entityAliasWithCount" colType="text"  editable="false" sortable="false" hidden="true"/>
													 <psjg:gridColumn name="FIELD_DATA_TYPE" id="fieldTypeExpr"  index="FIELD_DATA_TYPE"  colType="text" title="type"  editable="false" sortable="false" hidden="true"/>
													 <psjg:gridColumn name="PARENT_INDEX" id="pIdExpr"  index="PARENT_INDEX"  colType="number" title="parent"  editable="false" sortable="false" hidden="true"/>
													 <psjg:gridColumn name="index"  index="index" id="selFeIndexExpr"  title="id" colType="number"  editable="false" sortable="false"  hidden="true"/>
													</psjg:grid> 
					 	</div>
 						  					
 						  					
 						  					
 						  				</td>
 						  				</tr>
 						  				
 						  				</table>
 						  				
 						  				
 						  			</div>
									 </td>
								</tr>		
							
							
							 </table>
							</ps:form>
 	  					</ps:collapspanel>	
 	  					</ps:collapsgroup>		
</html>

