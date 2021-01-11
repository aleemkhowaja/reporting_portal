function rep_query_readyFunc()
{
	document.getElementById("qryName_"+_pageRef).focus();
	if(newQry=="true")
	{
		document.getElementById("qryName_"+_pageRef).value="";
	}
	if(fromQry=="true")
	{
		queryNameReadOnly=false;
	}
	$("#qryName_"+_pageRef).attr('readonly', queryNameReadOnly);
}

function addField() {
	var rowid = $("#dBFieldsGrid_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var url = jQuery.contextPath + "/path/designer/queryDesign_addField";
	myObject = $("#dBFieldsGrid_" + _pageRef).jqGrid('getRowData', rowid);
	params = {};
	paramStr = JSON.stringify(myObject)
	paramStr = "{" + "fieldCO:" + paramStr + "}";
	params["updates"] = paramStr;
	params["_pageRef"] = _pageRef;
	$.get(url, params, function(param) {
		$("#selFieldsGrid_" + _pageRef).trigger("reloadGrid");
	});
}
function showDBFields() {
	var rowid = $("#selEntGrid_" + _pageRef).jqGrid('getGridParam', 'selrow');
	myObject = $("#selEntGrid_" + _pageRef).jqGrid('getRowData', rowid);
	var dbTblName = myObject["ENTITY_DB_NAME"];
	var index = myObject["index"];
	params = {};
	params["updates"] = dbTblName;

	//reload the DB fields Grid
	$("#dBFieldsGrid_" + _pageRef)
			.jqGrid(
					'setGridParam',
					{
						url : jQuery.contextPath + "/path/designer/queryDesign_loadDbFieldsGrid.action?updates="
								+ dbTblName + "&locIndex=" + index,
						page : 1
					}).trigger("reloadGrid");

	//reload the Selected fields Grid
	$("#selFieldsGrid_" + _pageRef)
			.jqGrid(
					'setGridParam',
					{
						url : jQuery.contextPath + "/path/designer/queryDesign_loadSelFieldsGrid.action?locIndex="
								+ index + "&_pageRef=" + _pageRef,
						page : 1
					}).trigger("reloadGrid");
}
function addEntity() {
	var rowid = $("#dbEntGrid_" + _pageRef).jqGrid('getGridParam', 'selrow');
	var url = jQuery.contextPath + "/path/designer/queryDesign_addEntity";
	myObject = $("#dbEntGrid_" + _pageRef).jqGrid('getRowData', rowid);
	params = {};
	paramStr = JSON.stringify(myObject)
	paramStr = "{" + "entCO:" + paramStr + "}";
	params["updates"] = paramStr;
	params["_pageRef"] = _pageRef;
	$.get(url, params, function(param) {
		$("#selEntGrid_" + _pageRef).trigger("reloadGrid");
	});
}
function delEntity(rowid) {
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice,
			theArgs) {
		if (confirmcChoice) {
			delTheEntity(theArgs.rowid)
		}
	}, {
		rowid : rowid
	});
}

function delTheEntity(rowid) {
	myObject = $("#selEntGrid_" + _pageRef).jqGrid('getRowData', rowid);
	var index = myObject["index"];

	//check if this entity is used in any section then do not delete it
	var zSrc = jQuery.contextPath + "/path/designer/queryDesign_checkUsage";
	var params = {};
	params["locIndex"] = index
	params["_pageRef"] = _pageRef;
	$
			.getJSON(
					zSrc,
					params,
					function(data2, status, xhr) {
						var usage = data2['updates'];
						if (usage == "") //field not used anywhere and can be deleted
						{
							//delete entity and related selected fields
							var url = jQuery.contextPath
									+ "/path/designer/queryDesign_delEntity";
							var dbTblName = myObject["ENTITY_DB_NAME"];
							params = {};
							paramStr = JSON.stringify(myObject)
							paramStr = "{" + "entCO:" + paramStr + "}";
							params["updates"] = paramStr;
							params["_pageRef"] = _pageRef;
							$
									.get(
											url,
											params,
											function(param) {
												//reload the Selected entities Grid	
												$("#selEntGrid_" + _pageRef)
														.trigger("reloadGrid");

												//reload the DB fields Grid
												$("#dBFieldsGrid_" + _pageRef)
														.jqGrid(
																'setGridParam',
																{
																	url : jQuery.contextPath + "/path/designer/queryDesign_loadDbFieldsGrid.action?updates=&locIndex=-1",
																	page : 1
																}).trigger(
																"reloadGrid");

												//reload the Selected fields Grid
												$("#selFieldsGrid_" + _pageRef)
														.jqGrid(
																'setGridParam',
																{
																	url : jQuery.contextPath + "/path/designer/queryDesign_loadSelFieldsGrid.action?locIndex=-1&_pageRef="
																			+ _pageRef,
																	page : 1
																}).trigger(
																"reloadGrid");
											});
						} else {
							_showErrorMsg(delObjAlert + " " + usage);
							return;
						}
					});
}

function delField(rowid) {
	_showConfirmMsg(deleteConfirm, deleteTitle, function(confirmcChoice,
			theArgs) {
		if (confirmcChoice) {
			delTheField(theArgs.rowid)
		}
	}, {
		rowid : rowid
	});
}

function delTheField(rowid) {
	myObject = $("#selFieldsGrid_" + _pageRef).jqGrid('getRowData', rowid);

	//check if this fields is used in aggr. or in expr then do not delete it 
	var feId = myObject["index"];
	var zSrc = jQuery.contextPath + "/path/designer/queryDesign_checkUsage";
	var params = {};
	params["locIndex"] = feId;
	params["updates"] = "isField";
	params["_pageRef"] = _pageRef;
	$
			.getJSON(zSrc,
					params,
					function(data2, status, xhr) {
						var usage = data2['updates'];
						if (usage == "") //field not used anywhere and can be deleted
					{
						var url = jQuery.contextPath
								+ "/path/designer/queryDesign_delField";
						var pId = myObject["PARENT_INDEX"];
						params = {};
						paramStr = JSON.stringify(myObject)
						paramStr = "{" + "fieldCO:" + paramStr + "}";
						params["updates"] = paramStr;
						params["_pageRef"] = _pageRef;
						$
								.get(url,
										params,
										function(param) {
											//reload the Selected fields Grid
										$("#selFieldsGrid_" + _pageRef)
												.jqGrid(
														'setGridParam',
														{
															url : jQuery.contextPath + "/path/designer/queryDesign_loadSelFieldsGrid.action?locIndex="
																	+ pId
																	+ "&_pageRef="
																	+ _pageRef,
															page : 1
														})
												.trigger("reloadGrid");
									});
					} else {
						_showErrorMsg(delObjAlert + " " + usage);
						return;
					}
				});
}

function checkQryName(value) {
	var zSrc = jQuery.contextPath + "/path/designer/queryDesign_checkQryName";
	params = {};
	params["updates"] = value;
	$.getJSON(zSrc, params, function(data2, status, xhr) {
		var isExist = data2['updates'];
		if (isExist == "true") {
			_showErrorMsg(qryNameExistAlert);
			$("#qryName").val("");
		}
	});
}