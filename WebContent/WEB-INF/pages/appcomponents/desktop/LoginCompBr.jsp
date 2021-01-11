<%@include file="/WEB-INF/pages/common/Encoding.jsp"%>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp"%>
<%@taglib uri="/path-struts-jquery-tree-tags" prefix="psjt" %>
<ps:if test="openInDialog != true">
<% /* needed to specify meta tag decorator since on wildfly it is not detected automatically*/ %>
<meta name="decorator" content="logincompbr"/>
</ps:if>
<ps:if test="openInDialog != true">
	<title>
		<ps:if test="welcMsg == null">
			<ps:if test='%{session.sesVO.currentAppName == "OADM" || session.sesVO.appLocationType == "1"}'>
				<ps:text name="complogin_msg_key"></ps:text>
			</ps:if>
			<ps:else>
				<ps:text name="login.CoBrlogin"></ps:text>
			</ps:else>
		</ps:if>
		<ps:else>
			<ps:text name="welcome_comp_branch_key"/>
		</ps:else>
	</title>
</ps:if>
<% /* Path Solutions [Libin]  adjusted the page and changed page structure to handle alignment issues in Chrome/Firefox/IE and prevent unwanted growth of fields*/ %>
<script type="text/javascript">
 var company_branch_required_key = "<ps:text name='company_branch_required_key'/>";
</script>
<table CELLPADDING="0" CELLSPACING="0" height="100%" WIDTH="100%" border="0">


<% /* 
<tr>
<td>
<psj:a href="#" id="deniskBtn1" indicator="indicator" button="true"
												onclick="javascript:deniskBtn1Clicked()">
												<ps:text name="Denisk BTN 1"></ps:text>

											</psj:a>
<psj:a href="#" id="deniskBtn2" indicator="indicator" button="true"
	onclick="javascript:checkSelectedNodeClicked()">
	<ps:text name="Check Selected Node"></ps:text>
</psj:a>
<psj:a href="#" id="deniskBtn3" indicator="indicator" button="true"
	onclick="javascript:unCheckSelectedNodeClicked()">
	<ps:text name="Un-Check Selected Node"></ps:text>

</psj:a>
<psj:a href="#" id="deniskBtn4" indicator="indicator" button="true"
	onclick="javascript:isDisabledNode()">
	<ps:text name="Check IF Node Disabled Node"></ps:text>

</psj:a>
<psj:a href="#" id="deniskBtn5" indicator="indicator" button="true"
	onclick="javascript:enableSelectedNode()">
	<ps:text name="Enable Selected Node"></ps:text>

</psj:a>
	 
	 
	  <ps:url id="sampleBranchTreeData"
			action="deniskTestAction_loadSampleTree" namespace="/denisktest"
			escapeAmp="false">
			 <ps:param name="companyCode" value="1"></ps:param>
		</ps:url>
		  <ps:url id="sampleBranchTreeData2"
			action="deniskTestAction_loadSampleTree2" namespace="/denisktest"
			escapeAmp="false">
			 <ps:param name="companyCode" value="1"></ps:param>
		</ps:url>
		<psjt:tree id="sampleTree_id" cssClass="pathFirstTree"
			openAllOnLoad="false"
			openAllOnRefresh="false"
			checkbox="true" 
			showThemeDots="true"
			showThemeIcons="true" 
			jstreetheme="apple"
			href="%{sampleBranchTreeData}"
			onClickTopics="nodeTreeClicked"
			onDBlClickTopics="nodeTreeDblClicked"
			afterNodeCheckUncheckedTopic="afterNodeCheckUnchecked"
			dndInSameTreeEnabled="true"
			dndCheckMoveAllowedFunc="checkMove"
			dndMoveCompleteFunc="nodeMoveCompleted"
			enableRemoveNode="true"
			removeNodeFunc="nodeTreeRemoved"
			removeNodeKey="Delete Node"
			enableRenameNode="true"
			enableAddNode="true"
			ccp="false"
			disabled="false"
			
			additionalContextMenuItems="'customFunc' : { 'label': 'Custom 1', 'action': function (obj) { custom_1(obj); } 
														, 'icon' : 'ui-icon ui-icon-carat-1-se','separator_before' : 'true'},
			 'custom_func' : { 'label': 'My Custom Method', 'action':  function (obj) { myCustomMethod(obj); } }"
	
			/>
			
			
	  </td>
	  < % / *
	  
	  <td>
		<psjt:tree id="sampleTree_id_2"
			openAllOnLoad="true" cssClass="pathSecondTree"
			openAllOnRefresh="true"
			checkbox="true" 
			showThemeDots="true"
			showThemeIcons="true" 
			jstreetheme="default"
			href="%{sampleBranchTreeData2}"
			onClickTopics="nodeTreeClicked2"
			dndInSameTreeEnabled="false"
			disabled="false"
			/>
	  
	  </td>
	  * / % >
	   <script type="text/javascript">
	   function checkMove(data)
	   { 
		   console.log("checkMove object "+data.o+" data.r"+data.r);
		   console.log("checkMove id= "+data.o.attr("id") + " nodeCode="+data.o.attr("nodeCode"));
		   // if target is properly hovered
		   if(data.r != -1)
			{
		      console.log("checkMove target id= "+data.r.attr("id") + " nodeCode="+data.r.attr("nodeCode"));
			}
		   return true;
	   }
	   
	   function dropFinishCall(data)
	   { 
		   console.log("DROP Finished object "+data.o+" data.r"+data.r);
	   }
	  
	   function custom_1(theObj)
	   {
		   console.log("In custom_1 method id= "+theObj.attr("id") + " nodeCode="+theObj.attr("nodeCode"));
	   }
	  function nodeMoveCompleted(data)
	  {
	  
		console.log(" node is moved id "+data.rslt.o.attr("id")+" nodeCode "+data.rslt.o.attr("nodeCode"))
		console.log(" node is moved to Target id "+data.rslt.r.attr("id")+" nodeCode "+data.rslt.r.attr("nodeCode"))
	   
	  }
	  var currentClickedNode;
	  $("#sampleTree_id").subscribe("nodeTreeClicked",
		function(event){ 
			console.log("on click id: " + event.originalEvent.data.rslt.obj.attr("id"));
			console.log("on click nodeCode: " + event.originalEvent.data.rslt.obj.attr("nodeCode"));
			
			console.log("on click >>"+$("#sampleTree_id").jstree("returnCheckedNodes", false));
			
			currentClickedNode = event.originalEvent.data.rslt.obj;
			
		});
	  
	  $("#sampleTree_id").subscribe("nodeTreeDblClicked",
				function(event){ 
					console.log("on double click id: " + event.originalEvent.data.rslt.obj.attr("id"));
					console.log("on  double click nodeCode: " + event.originalEvent.data.rslt.obj.attr("nodeCode"));
					
					console.log("on double click >>"+$("#sampleTree_id").jstree("returnCheckedNodes", false));
					
					var currentDbleClickedNode = event.originalEvent.data.rslt.obj;
					
				});
	  
	  $("#sampleTree_id").subscribe("afterNodeCheckUnchecked",
				function(event){ 
					console.log("after Click id: " + event.originalEvent.data.rslt.obj.attr("id"));
					console.log("after Click nodeCode: " + event.originalEvent.data.rslt.obj.attr("nodeCode"));
					
					console.log("after Click>>"+$("#sampleTree_id").jstree("returnCheckedNodes", false));
				});
	  function checkSelectedNodeClicked()
	  {
		  $('#sampleTree_id').jstree("check_node",currentClickedNode,{triggerChange:false});
	  }
	  function unCheckSelectedNodeClicked()
	  {
		  $('#sampleTree_id').jstree("uncheck_node",currentClickedNode,{triggerChange:false});
	  }
	  function isDisabledNode ()
	  {
	    var disabled = $('#sampleTree_id').jstree("is_disabled",currentClickedNode);
	    console.log(" is disabled "+ disabled);
	  }

	  function deniskBtn1Clicked()
	  {
		//  $("#sampleTree_id").jstree("path_change_setting",
			//	  {url: jQuery.contextPath+"/denisktest/deniskTestAction_loadSampleTree2"
		//	  		,data: "_pageRef=DENISK",});
		 // $("#sampleTree_id").jstree("refresh");
		  console.log("buton Click>>"+$("#sampleTree_id").jstree("returnCheckedNodes", false));
		  
		  var treeData = $('#sampleTree_id').jstree("get_json",-1,["nodeCode","changed","parentNodeCode", "id" ])
			// set flat:true to get all nodes in 1-level json
			var jsonData = JSON.stringify(treeData );
			console.log("JSON tree data >>"+jsonData);
			
			 var treeData = $('#sampleTree_id').jstree("path_get_json_tree",-1,[ "id", "nodeCode","denisk_attr" ]);
			 var jsonData = JSON.stringify(treeData );
				console.log("PATH JSON tree data >>"+jsonData);
				
				
				console.log("after Click>>"+JSON.stringify($("#sampleTree_id").jstree("returnCheckedNodes", false)));
				
				$('#sampleTree_id').jstree("disable_node",currentClickedNode,true,{considerAllChildren:true});
	  }
	  function enableSelectedNode()
	  {
		  $('#sampleTree_id').jstree("disable_node",currentClickedNode,false,{considerAllChildren:true});
	  }
	  function nodeTreeRemoved(theObj)
	  {
		  console.log("In Removing Node Function (nodeTreeRemoved) id= "+theObj.attr("id") + " nodeCode="+theObj.attr("nodeCode"));
	  }
	  
	

	  </script>
</tr>
*/ %>




	<tr>
		<td>
			<ps:hidden id="app_location_type" name="session.sesVO.appLocationType"/>
			<ps:form id="compBrForm" action="loginCompBrAction_checkLoginCompBr" namespace="/pathdesktop">
			<ps:hidden id="openInDialog" name="openInDialog"/>
			<ps:if test="welcMsg == null">
			    <script type="text/javascript">
				jQuery(document).ready(function(){
				 $.struts2_jquery.require("LoginCompBr.js" ,null,jQuery.contextPath+"/common/js/login/");
				 if ($("#lookuptxt_COMP_CODE").val() == "")
					 liveSearch_makeReadOnly(true, "BRANCH_CODE");
				 
				 // ie issue with forcing mutiple click on input when become editable, might need to find better solutions
				 $("#lookuptxt_BRANCH_CODE").click(function(){ 
						if($.browser.msie && $("#lookuptxt_COMP_CODE").val() !== "")
						{
							$("#lookuptxt_BRANCH_CODE").removeAttr("readOnly");
							$("#lookuptxt_BRANCH_CODE").blur();
							$("#lookuptxt_BRANCH_CODE").focus();
						}
					 });
				});
				
				</script>
				<ps:set value="%{readOnlyProp}" var="readOnlyProp" />
				<table CELLPADDING="0" CELLSPACING="0" height="100%" WIDTH="100%" border="0">
					<tr>
					    <ps:if test="openInDialog != true">
						    <td WIDTH="30%"></td>
							<td width="40%">
						</ps:if>
						<ps:else>
						   <td width="100%">
						</ps:else>
							<fieldset class="ui-widget-content ui-corner-all">
								<legend id="legend" class="ui-widget-content ui-corner-all">
									<ps:if test='%{session.sesVO.currentAppName == "OADM" || session.sesVO.appLocationType == "1"}'>
										<ps:text name="complogin_msg_key"></ps:text>
									</ps:if>
									<ps:else>
										<ps:text name="login.CoBrlogin"></ps:text>
									</ps:else>
								</legend>
								<TABLE CELLPADDING="0" CELLSPACING="1" border="0" width="100%" class="ui-widget-content path-border-none">
									<TR>
										<TD colspan="4" style="padding:10px">
											<div class="note">
												<ps:actionerror theme="simple" />
											</div>
										</TD>
									</TR>
									<ps:if test='%{session.sesVO.appLocationType != "2"}'>
										<tr>
									</ps:if>
									<ps:else>
									 	<tr style="display:none;">
									</ps:else>
										<td width="50"></td>
										<td class="fldLabelView right" >
										  <ps:label key="compCode" for="lookuptxt_COMP_CODE"></ps:label>
										</td>
										<td width="70px" class="fldDataEdit center" >
											<psj:livesearch
												actionName="${pageContext.request.contextPath}/pathdesktop/CompaniesByUsrLookup_constructLookup"
												id="COMP_CODE" name="companiesVO.COMP_CODE"
												resultList="COMP_CODE:lookuptxt_COMP_CODE,BRIEF_DESC_ENG:companies"
												searchElement="BRIEF_DESC_ENG"
												parameter="COMP_CODE:lookuptxt_COMP_CODE,branchesVO.BRANCH_CODE:lookuptxt_BRANCH_CODE" mode="number"
												maxlength="4" maxValue="9999" size="5"
												dependency="lookuptxt_COMP_CODE:companiesVO.COMP_CODE,companies:companiesVO.BRIEF_DESC_ENG,lookuptxt_BRANCH_CODE:branchesVO.BRANCH_CODE:branchesCO.BRANCH_CODE_READONLY,branches:branchesVO.BRIEF_DESC_ENG:branchesCO.BRIEF_DESC_ENG_READONLY"
												dependencySrc="${pageContext.request.contextPath}/pathdesktop/CompaniesDependencyAction_dependencyByCompanies"
												>
											</psj:livesearch> 
										</td>
										<td  class="fldDataEdit center">
											<ps:textfield id="companies" name="companiesVO.BRIEF_DESC_ENG"
												readonly="true" tabindex="-1" cssStyle="height:20px"
												/>
										</td>
									</tr>
									<ps:if test='%{session.sesVO.appLocationType != "2" && session.sesVO.appLocationType != "1"}'>
										<tr>
									</ps:if>
									<ps:else>
									 	<tr style="display:none;">
									</ps:else>
										<td></td>
										<td class="fldLabelView right">
										  <ps:label key="branchCode" for="lookuptxt_BRANCH_CODE"></ps:label>
										</td>
										<td width="70" class="fldDataEdit center">
										<psj:livesearch
												actionName="${pageContext.request.contextPath}/pathdesktop/BranchesByUsrLookup_constructLookup"
												id="BRANCH_CODE" name="branchesVO.BRANCH_CODE"
												paramList="compCode:lookuptxt_COMP_CODE"
												mode="number" size="5"
												maxlength="4" maxValue="9999" 
												parameter="COMP_CODE:lookuptxt_COMP_CODE,BRANCH_CODE:lookuptxt_BRANCH_CODE"
												dependency="lookuptxt_BRANCH_CODE:branchesVO.BRANCH_CODE,branches:branchesVO.BRIEF_DESC_ENG"
												dependencySrc="${pageContext.request.contextPath}/pathdesktop/BranchesDependencyAction_dependencyByBranches"
												resultList="BRANCH_CODE:lookuptxt_BRANCH_CODE,BRIEF_DESC_ENG:branches"
												searchElement="BRIEF_DESC_ENG" >
											</psj:livesearch>
										</td>
										<td  class="fldDataEdit center">
											<ps:textfield id="branches" name="branchesVO.BRIEF_DESC_ENG"
												readonly="true" tabindex="-1" cssStyle="height:20px"
												/>
										</td>
									</tr>
									<ps:if test='%{#session.sesVO.tokenVerification != null && #session.sesVO.tokenVerification == "1"}'>
										<tr>
											<td></td>
											<td class="fldLabelView right">
												<ps:label id="pwdTokenLbl" for="pwdTokenId" key="token_key"></ps:label>
											</td>
											<td colspan="2" class="fldDataEdit center">
												<ps:password id="pwdTokenId" autocomplete="off" name="pwdToken"></ps:password>
											</td>
										</tr>
									</ps:if>
									<tr>
										<td class="right" colspan="4" >
										<br/>
											<psj:a href="#" id="ajaxlink" indicator="indicator" button="true"
												onclick="javascript:submitFn()">
												<ps:text name="btn.continue"></ps:text>
											</psj:a>
											<ps:if test="openInDialog == true">
												<psj:a href="#" id="cancelLink" indicator="indicator" button="true"
													onclick="javaScript:$('#switchCompDivId').remove();">
													<ps:text name="Cancel_key"></ps:text>
												</psj:a>											
											</ps:if>
										</td>
									</tr>
								</TABLE>
							</fieldset>
						</td>
						<ps:if test="openInDialog != true">
						   <td WIDTH="30%"></td>
						</ps:if>
					</tr>
				</TABLE>
				</ps:if>
				<ps:else>
				  <script type="text/javascript">
					function submitWelcomeFn()
					{
						$('#compBrForm').attr("action", jQuery.contextPath +'/pathdesktop/loginCompBrAction_moduleRedirect');
						submitEncryptedData('compBrForm');
					}

					jQuery(document).ready(function(){
						$('#continueWelcBtn').focus();
						});
				 </script>
				  <table CELLPADDING="0" CELLSPACING="0" height="100%" WIDTH="100%" border="0">
					<tr>
					<ps:if test="openInDialog != true">
					    <td WIDTH="30%"></td>
						<td width="40%">
					</ps:if>
					<ps:else>
					   <td width="100%">
					</ps:else>
						<TABLE CELLPADDING="0" CELLSPACING="0" border="0" width="653">
							<tr>
								<td class="path-welcome-left-pane" align="center">
									<table>
										<tr>
											<td class="path-welcome-left-pane" align="center">
												<div style="width: 90px; height: 60px;"
													class="pathLogoImage" />
											</td>
										</tr>
										<tr>
											<td class="path-welcome-left-pane" align="center">
												<ps:label key="welcome_details_key"
													name="welcome_details_key"
													cssClass="path-welcome-wlc-font"></ps:label>
											</td>
										</tr>
									</table>
								</td>
								<td class="path-welcome-right-pane">
									<table>
										<tr>
											<td class="path-welcome-right-pane">
												<ps:iterator value="actionMessages" var="theMess">
													<table>
														<tr>
															<td>
																<img
																	src="${pageContext.request.contextPath}/common/images/new_info_icon.png" />
															</td>
															<td class="path-welcome-msg-font">
																<ps:property value="theMess" escapeHtml="true"/>
															</td>
														</tr>
													</table>
												</ps:iterator>
												<ps:if test="actionErrors != null">
													<br />
													<ps:iterator value="actionErrors" var="theMess">
														<table>
															<tr>
																<td>
																	<image
																		src="${pageContext.request.contextPath}/common/images/new_warning_icon.png" />
																</td>
																<td class="path-welcome-msg-font">
																	<ps:property value="theMess" escapeHtml="true"/>
																</td>
															</tr>
														</table>
													</ps:iterator>
												</ps:if>
											</td>
										</tr>
										<tr>
											<td class="right path-welcome-right-pane"
												style="padding-right: 10px; padding-bottom: 5px; background-color: #E4E9ED">
												<br />
												<psj:a href="#" id="continueWelcBtn" indicator="indicator"
													button="true" onclick="javascript:submitWelcomeFn()">
													<ps:text name="btn.continue"></ps:text>
												</psj:a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</TABLE></td>
						<ps:if test="openInDialog != true">
						   <td WIDTH="30%"></td>
						</ps:if>
					</tr>
				</TABLE>
				</ps:else>
				
			</ps:form>
		</td>
	</tr>
</TABLE>