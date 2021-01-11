function user_access_ready_func()
{
	$("#userAccessForm_" + _pageRef).processAfterValid("saveUserAccess");
	var eodViewer = $("#eodViewer_"+_pageRef).val();
	if(eodViewer==1)
	{
		document.getElementById('showHideBranchGrid_'+_pageRef).style.display='none';
	}	
	
	$("#viewedUserGridId_"+_pageRef).subscribe('checkViewedUsers',function(event, data) 
	   {
	   	var url = jQuery.contextPath+'/path/userAccess/userAccessAction_retCheckboxStr.action';
		var params=$("#userAccessForm_"+_pageRef).serialize();
		rowid = $("#userGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		if(rowid!=null)
		{
			var userId = $("#userGridId_"+_pageRef).jqGrid('getCell', rowid,'USER_ID');
			params = params+"&userId="+userId
			$.ajax({
			 url: url,
	         type:"post",
			 dataType:"json",
			 data:params,	
			 success: function(param)
			 {
				var choosenUsers =param["viewedUserIdStr"];
				if(choosenUsers)
				{
					var idsArrayUsers = choosenUsers.split(",")
					var arrayLength = $("#viewedUserGridId_"+_pageRef).jqGrid('getCol','USER_ID').length;
					for(var i=1;i<=arrayLength;i++)
					{
						rowObject =  $("#viewedUserGridId_"+_pageRef).jqGrid("getRowData",i);
						var userId = rowObject["USER_ID"];
						var toCheck =false;
						for(var k=0;k<idsArrayUsers.length;k++)
						{
							if(idsArrayUsers[k]==userId)
							{
								$("#viewedUserGridId_"+_pageRef).jqGrid('setCellValue', i, 'intCheckBox',1 ,'1');
							}
						}
					}
				}
				var choosenBranch =param["branchIdStr"];
				if(choosenBranch)
				{
					var idsArrayBranch = choosenBranch.split(",")
					var arrayBranchLength = $("#branchGridId_"+_pageRef).jqGrid('getCol','EMP_BRANCH_CODE').length;
					for(var i=1;i<=arrayBranchLength;i++)
					{
						rowObject =  $("#branchGridId_"+_pageRef).jqGrid("getRowData",i);
						var branchId = rowObject["EMP_BRANCH_CODE"];
						var toCheck =false;
						for(var k=0;k<idsArrayBranch.length;k++)
						{
							if(idsArrayBranch[k]==branchId)
							{
								$("#branchGridId_"+_pageRef).jqGrid('setCellValue', i, 'branchCheckBox',1 ,'1');
							}
						}
					}
				}
			 }
	    	});
	      }
			
	   });
	   
	   
	   
	$("#userGridId_"+_pageRef).subscribe('loadUserViewed',function(event, data) 
	 {
	 	var	rowid = $("#userGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var userId = $("#userGridId_"+_pageRef).jqGrid('getCell', rowid,'USER_ID');
		myObject =  $("#userAccessForm_"+_pageRef).serialize();
		var urll = jQuery.contextPath+"/path/userAccess/userAccessAction_fillUserAccessHm.action?userId="+userId;
		$.post(urll, myObject , function( param )
	 	{
			var url = jQuery.contextPath+"/path/userAccess/userAccessAction_loadViewedUserGrid.action?userId="+userId+"&_pageRef="+_pageRef;
			$("#viewedUserGridId_"+_pageRef).jqGrid('setGridParam', { url: url }).trigger("reloadGrid");
			var urlBranch = jQuery.contextPath+"/path/userAccess/userAccessAction_loadBranchGrid.action?userId="+userId+"&_pageRef="+_pageRef;
			$("#branchGridId_"+_pageRef).jqGrid('setGridParam', { url: urlBranch }).trigger("reloadGrid");
			var trxNbr =param["trxNumber"];
			var dtUpdated = param["dateUp"];
			
			document.getElementById('auditTrxNbr_'+_pageRef).value =trxNbr;
			
			if(dtUpdated == undefined)
			{
				document.getElementById('DATE_UPDATED_'+_pageRef).value="";
			}
			else
			{
				document.getElementById('DATE_UPDATED_'+_pageRef).value =dtUpdated;		
			}
	 	});
	});
	
	$("#viewedUserGridId_"+_pageRef).subscribe('putInAccessUserHashMap',function(event, data) 
	{
		var rowid = $("#userGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var userId = $("#userGridId_"+_pageRef).jqGrid('getCell', rowid,'USER_ID');
		if(rowid!=null)
		{
			var jsonStringUpdates = $("#viewedUserGridId_"+_pageRef).jqGrid('getAllRows');
			$("#updatesUserViewedList_"+_pageRef).val(jsonStringUpdates); 
			var url = jQuery.contextPath+'/path/userAccess/userAccessAction_putInAccessUserHashMap.action?userId='+userId;
			myObject =  $("#userAccessForm_"+_pageRef).serialize();
			$.post(url, myObject , function( param )
		 	{
				var jsonStringBranchUpdates = $("#branchGridId_"+_pageRef).jqGrid('getAllRows');
				$("#updatesBranchList_"+_pageRef).val(jsonStringBranchUpdates); 
				var urlBranch = jQuery.contextPath+'/path/userAccess/userAccessAction_putInBranchHashMap.action?userId='+userId;
				myObject =  $("#userAccessForm_"+_pageRef).serialize();
				$.post(urlBranch, myObject , function( param )
			 	{
			 	});
		 	});
		}
		else{
			_showErrorMsg(selUserError,user,300,100); 
		}
	});	   
	
	$("#branchGridId_"+_pageRef).subscribe('putInBranchHashMap',function(event, data) 
	{
		var rowid = $("#userGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var userId = $("#userGridId_"+_pageRef).jqGrid('getCell', rowid,'USER_ID');
				var jsonStringBranchUpdates = $("#branchGridId_"+_pageRef).jqGrid('getAllRows');
				$("#updatesBranchList_"+_pageRef).val(jsonStringBranchUpdates); 
				var urlBranch = jQuery.contextPath+'/path/userAccess/userAccessAction_putInBranchHashMap.action?userId='+userId;
				myObject =  $("#userAccessForm_"+_pageRef).serialize();
				$.post(urlBranch, myObject , function( param )
			 	{
			 	});
	});	
}

function user_access_load_reports(param) 
{
	var collapseDiv = $("#repUsrFrm_" + _pageRef + " > .collapsibleContainerTitle").get(0);
	if ($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
	{
		$(collapseDiv).trigger("click");
	}
	var userid = param.substring(4, param.length);
	var repGridUrl = jQuery.contextPath+ "/path/snapShot/snapShotAction_loadUserAccessSnpList.action?executedBy=" + userid+ "&_pageRef=" + _pageRef;
	$("#usrAccessRepGrid_" + _pageRef).jqGrid('setGridParam', {url : repGridUrl}).trigger("reloadGrid");
}

function saveUserAccess()
{
	var hasChanges = $("#userAccessForm_"+_pageRef).hasChanges();
	if(hasChanges==true)
	{
		var jsonStringUpdates = $("#viewedUserGridId_"+_pageRef).jqGrid('getAllRows');
		$("#updatesUserViewedList_"+_pageRef).val(jsonStringUpdates); 
		rowid = $("#userGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var userId = $("#userGridId_"+_pageRef).jqGrid('getCell', rowid,'USER_ID');
		
		
		if(rowid!=null)
		{
			_showProgressBar(true);
			var url = jQuery.contextPath+'/path/userAccess/userAccessAction_putInAccessUserHashMap.action?userId='+userId;
				 //url +="?_pageRef="+_pageRef;
				 myObject =  $("#userAccessForm_"+_pageRef).serialize();
				 $.post(url, myObject , function( param )
			 	 {
						 
					var jsonStringBranchUpdates = $("#branchGridId_"+_pageRef).jqGrid('getAllRows');
					$("#updatesBranchList_"+_pageRef).val(jsonStringBranchUpdates); 
						 var urlBranch = jQuery.contextPath+'/path/userAccess/userAccessAction_putInBranchHashMap.action?userId='+userId;
						 //url +="?_pageRef="+_pageRef;
						 myObject =  $("#userAccessForm_"+_pageRef).serialize();
						 $.post(urlBranch, myObject , function( param )
					 	 {
							 	//var rowid = $("#userGridId_"+_pageRef).jqGrid('getGridParam','selrow');
								//var userId = $("#userGridId_"+_pageRef).jqGrid('getCell', rowid,'USER_ID');
									 var dateUp = document.getElementById('DATE_UPDATED_'+_pageRef).value;
								var url2 = jQuery.contextPath+'/path/userAccess/userAccessAction_saveUserAccess.action?userId='+userId+'&dateUp='+dateUp;
								
								 var myObjectForSave =  $("#userAccessForm_"+_pageRef).serialize();
								$.post(url2, myObjectForSave , function( param )
								{
									_showProgressBar(false);
									var er =param["_error"]
									//alert("errrrr =="+er)
									if(er)
									{
										_showErrorMsg(er,user,400,200);
									}
									else
									{
										$("#userAccessForm_"+_pageRef).clearChanges();
										$("#updatesUserViewedList_"+_pageRef).val('');
										$("#updatesBranchList_"+_pageRef).val('')
										_showErrorMsg(savedSuccess,user,300,100);
										var urlReload = jQuery.contextPath+'/path/userAccess/userAccessAction_loadUserAccess.action';
										
										$("#userGridId_"+_pageRef).trigger("reloadGrid");
							//			$("#viewedUserGridId_"+_pageRef).trigger("reloadGrid");
							//			$("#branchGridId_"+_pageRef).trigger("reloadGrid");
										
										var urlViewer = jQuery.contextPath+"/path/userAccess/userAccessAction_loadUserAccessGrid.action";
										$("#userGridId_"+_pageRef).jqGrid('setGridParam', { url: urlViewer }).trigger("reloadGrid");
										
										var urlViewed = jQuery.contextPath+"/path/userAccess/userAccessAction_loadViewedUserGrid.action?userId=-1";
										$("#viewedUserGridId_"+_pageRef).jqGrid('setGridParam', { url: urlViewed }).trigger("reloadGrid");
							
										var urlBrch = jQuery.contextPath+"/path/userAccess/userAccessAction_loadBranchGrid.action?userId=-1";
										$("#branchGridId_"+_pageRef).jqGrid('setGridParam', { url: urlBrch }).trigger("reloadGrid");
									}
								
								});
					 	 });
					 
			 	 });
		
			
		}
		else{
			_showErrorMsg(selUserError,user,300,100); 
		}
	}	
}