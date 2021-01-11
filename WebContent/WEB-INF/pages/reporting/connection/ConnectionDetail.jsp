<%@include file="/WEB-INF/pages/common/Encoding.jsp" %>
<%@include file="/WEB-INF/pages/common/TLDImport.jsp" %>

<ps:set name="conSuccess_var" value="%{getEscText('connection.success')}"/>
<ps:set name="conFailed_var" value="%{getEscText('connection.failed')}"/>

<script type="text/javascript">
var conSuccess 		= '<ps:property value="conSuccess_var"  escapeHtml="false" escapeJavaScript="true"/>'
var conFailed 		= '<ps:property value="conFailed_var"  escapeHtml="false" escapeJavaScript="true"/>'

//kassem kazan 29-08-2013 validation removed bug number 91183
/* 
  $("#host_"+_pageRef).bind('keypress', function (event) {
    var regex = new RegExp("^[a-zA-Z0-9_-]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    //alert("event.which=="+event.which)
    if(event.which==8 || event.which==46 || event.which==0)// for delete and dot
    {
    	return true;
    }
    $("#host_"+_pageRef).bind("cut copy paste",function(e) {
    
          e.preventDefault();
      });
    if (!regex.test(key)) {
       event.preventDefault();
       return false;
    }
});
*/
  function testConnection()
  {
    var dbPass=$("#dbPassFake").val();
	var dbConfPass=$("#dbConfPassFake").val();
	if(dbPass!=dbConfPass)
	{
		_showErrorMsg(diffPass,dbConfirmPass,300,100); 
		return;
	}
	$("#dbPass").val(dbPass);
	$("#dbConfPass").val(dbConfPass);
  	var url = jQuery.contextPath+'/path/connection/connection_testConnection.action';
	var params=$("#filterConnForm").serialize();
	
		 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 {
		  if(param["conStatus"]=="success")
		  {
		  	  _showErrorMsg(conSuccess,conTest,300,100);
		  }
		  else if(param["conStatus"]=="missingFields")
		  {
		  }
		   else 
		  {
		  	_showErrorMsg(conTest+" "+param["conStatus"]+", "+param["conFailedMsg"],conTest,400,120);
		  }
		 }
    });
	
  }     


</script>

<ps:form id="filterConnForm" useHiddenProps="true" validate="true">
    <ps:hidden name="actionType" id="actionType_${_pageRef}"/>
    <ps:hidden id="DATE_UPDATED_${_pageRef}" name="icCO.irpConnectionsVO.DATE_UPDATED"/>
     <ps:hidden id="connId_${_pageRef}" name="icCO.irpConnectionsVO.CONN_ID"/>
     <ps:hidden id="oldDbPass_${_pageRef}" name="icCO.oldDbPass"/>
	<table class="headerPortionContent ui-widget-content"  CELLPADDING="0" CELLSPACING="5">
		<tr>
			<td align="center" nowrap="nowrap">
			<TABLE CELLPADDING="0" CELLSPACING="8">
				<TR>
					<TD>
						<ps:label  key="connection.conDescription" for="connDesc_${_pageRef}"/>
					</TD>
					<TD>
						<ps:textfield id="connDesc_${_pageRef}" name="icCO.irpConnectionsVO.CONN_DESC" tabindex="1" maxlength="30" required="true" />
					</TD>
				</TR>
				
				<TR>
					<TD>
						<ps:label key="connection.dbms"  for="dbms_${_pageRef}"/>
					</TD>
					<TD>
						<ps:select id="dbms_${_pageRef}" name="icCO.irpConnectionsVO.DBMS" list="dbmsList" listKey="code" listValue="description"  emptyOption="true"  required="true"></ps:select>
					</TD>
				</TR>
				
				<TR>
					<TD>
						<ps:label key="connection.host" for="host_${_pageRef}"/>
					</TD>
					<TD>
						<ps:textfield id="host_${_pageRef}" name="icCO.host" mode="character"  tabindex="1" maxlength="500" required="true"/>
					</TD>
				</TR>
				
				<TR>
					<TD>
						<ps:label key="connection.userId" for="userId_${_pageRef}"/>
					</TD>
					<TD>
						<ps:textfield id="userId_${_pageRef}" name="icCO.irpConnectionsVO.USER_ID" mode="character" tabindex="1" maxlength="30" required="true"/>
					</TD>
				</TR>								
				<TR>
					<TD>
						<ps:label key="connection.dbPass" for="dbPassFake"/>
					</TD>
					<TD>
						<ps:hidden id="dbPass" name="icCO.irpConnectionsVO.DB_PASS" />
						<ps:password   id="dbPassFake" autocomplete="off"  maxlength="30" required="true"/>
					</TD>
				</TR>	
				
				<TR>
					<TD>
						<ps:label key="connection.dbConfPass" for="dbConfPassFake"/>
					</TD>
					<TD>
						<ps:hidden  id="dbConfPass" name="icCO.dbConfPass"  />
						<ps:password  id="dbConfPassFake" autocomplete="off"  maxlength="30" required="true"/>
					</TD>
				</TR>
				<TR>
					<TD>
						<psj:a button="true" href="#"
									onclick="testConnection()"
									id="testConnection_${_pageRef}">
									<ps:text name="%{getText('connection.testCon')}"></ps:text>
								</psj:a>
					</TD>
				</TR>	
			</TABLE>
		    </td>
		</tr>
	</table>
</ps:form>
<script type="text/javascript">
		if ($.browser.chrome)
		{
			rep_preparePassChrome();			
		}
		
		$(document).ready(function() 
				{
					$("#dbConfPassFake").val($("#dbConfPass").val());
					$("#dbPassFake").val($("#dbPass").val());
				});
</script>