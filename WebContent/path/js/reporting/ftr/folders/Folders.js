
	function selectRowFolderFn() 
	{
		var rowid = $("#foldersGridId_"+_pageRef).jqGrid('getGridParam','selrow');
		var url = jQuery.contextPath+ "/path/foldersMaint/folders_selectFolder.action";
					
		var myObject = $("#foldersGridId_"+_pageRef).jqGrid('getRowData',rowid);
					
		params = {};
		paramStr = JSON.stringify(myObject);
		paramStr = "{"+ "foldersCO:"+paramStr + "}";
		params["updates"] = paramStr;
		params["_pageRef"]=_pageRef;
		$.post(url, params , function( param )
		{
				 	
			$("#foldMaintDiv_"+_pageRef).html(param);
				 		
			var collapseDiv = $("#foldersDetFrm > .collapsibleContainerTitle").get(0);
			if($(collapseDiv).next(".collapsibleContainerContent").is(":hidden"))
			{
					$(collapseDiv).trigger("click");
			}
			},"html"
		)
					
	}
	/*
	This function creates a new record or updates an already existing record
	*/
	function newFolder()
	{
		_showProgressBar(true);
	if(!$("#foldersDetFormId_"+_pageRef).hasChanges())
	{
		emptyFoldersForm()
		_showProgressBar(false);
		return;
	}
		
		
		//If Reference contains space
		if ($("#foldersRef_"+_pageRef).val().indexOf(" ") != -1 )
		{
			_showErrorMsg(containsSpace);
			$("#foldersRef_"+_pageRef).focus(); 
			_showProgressBar(false);
			return;
		}
		
		//save 
		//var url = $("#foldersDetFormId_"+_pageRef).attr("action");
		var url =  jQuery.contextPath+ "/path/foldersMaint/folders_validateFolders.action";
		parseNumbers();
		var foldersCode = $("#foldersCode_"+_pageRef).val();
		if (foldersCode == "")
		{
			$("#foldersCode_"+_pageRef).val("0");
		}
		myObject = $("#foldersDetFormId_"+_pageRef).serialize();

		 $.ajax({
			 url: url,
	         type:"post",
			 data: myObject,
			 dataType : "json",
			 success: function(param)
			 {
			   if(typeof param["_error"] == "undefined" || param["_error"] == null)
			   {
					$("#foldersGridId_"+_pageRef).trigger("reloadGrid"); //Reload Grid
					emptyFoldersForm(); //Empty form
					refreshMenu(); //Refreshing Menu so that the newly created record appears
					_showProgressBar(false);
				}
			}
		});
	};
	
	/*
	This Function deletes the selected record from the grid
	*/
	function deleteFolder(rowid)
	{
		 _showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice, theArgs){
	           if(confirmcChoice)
	           {
	        	   deleteTheFolder(theArgs.rowid)
	           }
		      }, {rowid : rowid});
	}

	function deleteTheFolder(rowid)
	{
		var url = jQuery.contextPath+"/path/foldersMaint/folders_checkDetails";
//		var refStr = $("#foldersRef_"+_pageRef).val();
//		var repAppName = $("#lookuptxt_appName_"+_pageRef).val();

		rowObject =  $("#foldersGridId_"+_pageRef).jqGrid("getRowData",rowid);
		var refStr = rowObject["FOLDER_REF"];
		var repAppName = rowObject["APP_NAME"];
		
		params = {};
		params["updates"]=refStr;
		params["repAppName"]=repAppName;
		$.post(url, params, function(param)
				{
					if(param["updates"]!="0"){
						_showErrorMsg(detailsExist);
					}
					else{
						url = jQuery.contextPath+"/path/foldersMaint/folders_deleteFolder";
						
						var myObject = $("#foldersGridId_"+_pageRef).jqGrid('getRowData',rowid);
						
						params = {};
						paramStr = JSON.stringify(myObject);
						paramStr = "{"+ "foldersCO:"+paramStr + "}";
						params["updates"] = paramStr;
						params["_pageRef"]=_pageRef;
						params["repAppName"]=repAppName;
						$.post(url, params , function( param )
						{
							$("#foldersGridId_"+_pageRef).trigger("reloadGrid"); //Reload Grid
							//empty trx input
							 $("#foldersDetFormId_"+_pageRef+" #auditTrxNbr_"+_pageRef).val("");
							 $("#foldersDetFormId_ #auditObj_"+_pageRef).val("");
							//$("#foldersRef_"+_pageRef).attr('readonly', false);//Enabled the field in case it was disabled
							emptyFoldersForm(); //Empty form
							refreshMenu(); //Refreshing Menu so that the newly created record appears				
						});
					}
					
				}
		);
	}

	
	function addNewFolder()
	{
		emptyFoldersForm();
	}
	
	/*
	This Function empties the form's fields
	*/
	function emptyFoldersForm()
	{
		//empty form
		var url = jQuery.contextPath+"/path/foldersMaint/folders_emptyFoldersForm?_pageRef="+_pageRef;
		var params = {};
		$.post(url, params, function(param) {
			$("#foldMaintDiv_"+_pageRef).html(param);
		}, "html");

	};

	/*
	This Function is used by "foldersRef" & "lookuptxt_foldersPRef" fields, to check for the availability and the validity of the Entered Reference
	--> for "foldersRef", the field should not already exist
	--> for "lookuptxt_foldersPRef", the field should exist
	*/
	
	function checkProgReference(id)
	{
		var refStr = $("#"+id).val();
		if (id == ("foldersRef_"+_pageRef))
		{
			//If folder reference contains space
			if (refStr.indexOf(" ") != -1)
			{
				_showErrorMsg(containsSpace);
				$("#foldersRef_"+_pageRef).focus();
				return;
			} 
		}

		var fcode = $("#foldersCode_"+_pageRef).val();
		//Checking If the record is being updated (fcode <> 0) and we're on the foldersRef field, we don't need to check since 
		//the user is not allowed to change this field on the update
		if (fcode != 0 & id == ("foldersRef_"+_pageRef)){
			return;
		}
		//End Checking
		//refStr = $("#"+id).val();
		refStr = refStr.toUpperCase();
		if (refStr == "")
		{
			if (id == "lookuptxt_foldersPRef_"+_pageRef)
			{
				$("#foldersPRefStr_"+_pageRef).val("");
			}
			return;
		}
		else
		{
			if (id == "lookuptxt_foldersPRef_"+_pageRef)
			{
				//var dispOrder = $("#foldersOrder_"+_pageRef).val();
				
				if (refStr == "ROOT")
				{
					$("#lookuptxt_foldersPRef_"+_pageRef).val("ROOT");
					$("#foldersPRefStr_"+_pageRef).val("ROOT");
					return;
				}
			} 
			
			var zSrc = jQuery.contextPath+"/path/foldersMaint/folders_checkProgRef";
			params = {};
			params['updates']=refStr;
			params['repAppName']=$("#lookuptxt_appName_" + _pageRef).val();
			$.getJSON(zSrc, params, function(data2, status, xhr)
			{
				var retVal = data2['updates'];
				//if the reference exists
				if (retVal != "0")
				{
					if (id == "lookuptxt_foldersPRef_"+_pageRef) //if parent
					{
						//Setting its description
						$("#foldersPRefStr_"+_pageRef).val(retVal);
					}
					else 
					{
						//Emptying field
						$("#"+id).val("");
						_showErrorMsg(checkProgRefAlert);
						$("#"+id).focus();
					}
				}
				else
				{
					if (id == "lookuptxt_foldersPRef_"+_pageRef) //if parent
					{
						//Emptying field
						$("#"+id).val("");
						$("#foldersPRefStr_"+_pageRef).val("");
						_showErrorMsg(missingProgRef);
						$("#"+id).focus();
					}
				}
				
				
				});
		}
	};

	/*
	This function refreshes the menu on the left so that the new changes take place
	*/
	function refreshMenu()
	{
		ddaccordion
				.initRoot(
						"appMenu",
						"generateMenuOnRequest?actionName=generateMenuOnRequest&targetName=content-container&appName=REP&id=appMenu",
						false);
	};
	
	function emptyUpDownPRefLkp()
	{
		$("#lookuptxt_foldersPRef_" + _pageRef).val("");
		$("#foldersPRefStr_" + _pageRef).val("");
		$("#foldersRef_" + _pageRef).val("");
	}
	