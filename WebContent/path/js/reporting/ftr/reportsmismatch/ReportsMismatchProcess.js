	function showHideCrtLkp(obj)
	{
		showHideCrtLkpLbl(obj.value,true)
	}
	
	function showHideCrtLkpLbl(zVal,isReloadGrid)
	{
		if(zVal=="1") //inter
		{
			document.getElementById("misProcRep_" +_pageRef).style.display = "none";
			document.getElementById("misProcCrt_" +_pageRef).style.display = "inline";
			document.getElementById("misProcCrtGridLbl_" +_pageRef).style.display = "inline";
			document.getElementById("misProcRepGridLbl_" +_pageRef).style.display = "none";
		}
		else //intra
		{
			document.getElementById("misProcCrt_" +_pageRef).style.display = "none"
			document.getElementById("misProcRep_" +_pageRef).style.display = "inline";
			document.getElementById("misProcCrtGridLbl_" +_pageRef).style.display = "none";
			document.getElementById("misProcRepGridLbl_" +_pageRef).style.display = "inline";
		}
		document.getElementById("lookuptxt_crtProgRefLkp_"+_pageRef).value="";
		document.getElementById("repMisId_"+_pageRef).value="";
		//empty the grid
		if(isReloadGrid==true)
		{
			relaodMisProcGridAndProcess();
		}
	}
	
	function relaodMisProcGridAndProcess()
	{
		var repMisId=$("#repMisId_"+_pageRef).val();
		var srcURL=jQuery.contextPath + "/path/reportsMismatch/repMismProctAction_reloadMisProcGrid.action";
		var params={};
		params["misProcCO.TYPE"]		=$("#misTypeListComboId_"+_pageRef).val();
		if(repMisId!=null && repMisId!="")
		{
			params["misProcCO.REP_MISMATCH_ID"]	=repMisId;
			params["misProcCO.CRITERIA"]	=$("#lookuptxt_crtProgRefLkp_"+_pageRef).val();
		}
		
		$("#misProcGrid_"+_pageRef).load(srcURL, params, function()
		{
			//empty the process result div
			params ={};
			var refreshUrl=jQuery.contextPath+'/path/reportsMismatch/repMismProctAction_goProcess.action?'
			$('#misProcResultDiv_'+_pageRef).load(refreshUrl, params, function(param)
			 {
			});
		
		});
	}
	
	function resetMisProc()
	{
		var url = jQuery.contextPath+ "/path/reportsMismatch/repMismProctAction_resetMismatchProcess.action";
		var params = {};
		params["_pageRef"] = _pageRef;
		$.post(url, params, function(param)
		{
			$("#misProcDiv_" + _pageRef).html(param);
			$("#lookuptxt_crtProgRefLkp_" + _pageRef).attr('readonly', true);
			//reload grid
			relaodMisProcGridAndProcess();
		}, "html");
	}
	
	function goProcess()
	{
		//check for empty data
		var period=$("#misProcDate_"+_pageRef).val();
		var crtRep=$("#lookuptxt_crtProgRefLkp_"+_pageRef).val();
		if(period=="" || crtRep=="")
		{
			_showErrorMsg(missingReqInputs, error_msg_title, 300, 100);
			return;
		}
		else
		{
		
			var rows = $("#misProcGridId_"+_pageRef).jqGrid('getGridParam', 'selarrrow');
			if(rows.length==0)
			{
				_showErrorMsg(missingReqInputs, error_msg_title, 300, 100);
				return;
			}
			var params = $("#repMisProcForm_" + _pageRef).serialize();
			params+="&misProcCO.crtsProgRefs="+rows;
			
			var refreshUrl=jQuery.contextPath+'/path/reportsMismatch/repMismProctAction_goProcess.action?'
			$('#misProcResultDiv_'+_pageRef).load(refreshUrl, params, function(param)
			 {
			});
			
			
		}
	}