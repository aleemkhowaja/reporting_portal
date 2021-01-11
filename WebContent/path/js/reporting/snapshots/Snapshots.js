function verifySnapshot()
	{
		var rowid = $("#SnapShotGrid_"+_pageRef).jqGrid('getGridParam','selrow');
		var snapshotId = $("#SnapShotGrid_" + _pageRef).jqGrid('getCell', rowid,'SNAPSHOT_ID');
		var reportName = $("#SnapShotGrid_" + _pageRef).jqGrid('getCell', rowid,'REPORT_NAME');
		var snapshotRef = $("#SnapShotGrid_" + _pageRef).jqGrid('getCell', rowid,'SNAPSHOT_REF');
		var fileName = $("#SnapShotGrid_" + _pageRef).jqGrid('getCell', rowid,'FILE_NAME');
		var params = {};
		params["repName"] = reportName;
		
		params["snpId"] = snapshotId;
		
		params["snpRef"] = snapshotRef;

		params["fileName"] = fileName;
	
		var url = jQuery.contextPath+ "/path/snapShot/snapShotAction_verifySnapshot";	
	
		$.post(url, params , function( param )	
			{		
			_showErrorMsg(verifiedSnapshot,verifySnapshotTitle, 300,100);			
				});
	}

function viewSnapshots(param)
	{	
		var sel_row_index=param.substring(4,param.length);
		var repName = $("#SnapShotGrid_"+_pageRef).jqGrid('getCell', sel_row_index, 'REPORT_NAME');
		var repFormat = $("#SnapShotGrid_"+_pageRef).jqGrid('getCell', sel_row_index, 'REPORT_FORMAT');
		var isDB = $("#SnapShotGrid_"+_pageRef).jqGrid('getCell', sel_row_index, 'IS_DB');
		if(isDB=="")
		{
			isDB="0";
		}
		var snpId = $("#SnapShotGrid_"+_pageRef).jqGrid('getCell', sel_row_index, 'SNAPSHOT_ID');
		var fileName = $("#SnapShotGrid_"+_pageRef).jqGrid('getCell', sel_row_index, 'FILE_NAME');
		var connId = $("#SnapShotGrid_"+_pageRef).jqGrid('getCell', sel_row_index, 'CONN_ID');
		var urlSnapShot=document.getElementById("snapShotsFrm").action;
		if(urlSnapShot.indexOf("&repFormat=")==-1)
		{
			document.getElementById("snapShotsFrm").action+="?repName="+repName+"&repFormat="+repFormat+"&fileName="+fileName+"&isDB="+isDB+"&snpId="+snpId+"&_pageRef="+_pageRef+"&connId="+connId;
		}
		else
		{
			urlSnapShot =urlSnapShot.substring(0,urlSnapShot.indexOf("?repName="));
			document.getElementById("snapShotsFrm").action=urlSnapShot+"?repName="+repName+"&repFormat="+repFormat+"&fileName="+fileName+"&isDB="+isDB+"&snpId="+snpId+"&_pageRef="+_pageRef+"&connId="+connId;
		}
		//document.getElementById("snapShotsFrm")	submitEncryptedData("snapShotsFrm");();
		submitEncryptedData("snapShotsFrm");
	}