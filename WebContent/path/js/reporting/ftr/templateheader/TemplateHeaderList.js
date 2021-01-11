function rep_tempHeaderLst_readyFunc() {
	$("#templateHeaderTable_" + _pageRef).subscribe('emptyCrtTrx',function(event, data) 
	{
		$("#templateHeaderForm_" + _pageRef + " #auditTrxNbr_"+ _pageRef).val("")
	});
}

function rep_tempHeaderFrm_readyFunc() 
{
	$("#leftFirst_"+_pageRef).autocomplete({
		inputId:'leftFirst_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=LEFT1&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#leftSecond_"+_pageRef).autocomplete({
		 inputId:'leftSecond_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=LEFT2&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#leftThird_"+_pageRef).autocomplete({
		 inputId:'leftThird_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=LEFT3&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#leftFourth_"+_pageRef).autocomplete({
		 inputId:'leftFourth_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=LEFT4&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#centerFirst_"+_pageRef).autocomplete({
		 inputId:'centerFirst_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=CENTER1&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#centerSecond_"+_pageRef).autocomplete({
		 inputId:'centerSecond_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=CENTER2&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#centerThird_"+_pageRef).autocomplete({
		 inputId:'centerThird_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=CENTER3&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#rightFirst_"+_pageRef).autocomplete({
		 inputId:'rightFirst_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=RIGHT1&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#rightSecond_"+_pageRef).autocomplete({
		 inputId:'rightSecond_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=RIGHT2&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#rightThird_"+_pageRef).autocomplete({
		 inputId:'rightThird_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=RIGHT3&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	 $("#rightFourth_"+_pageRef).autocomplete({
		 inputId:'rightFourth_'+_pageRef,source: function(request,response) {
			 var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retTextFieldsAutoComplete.action?update=RIGHT4&_pageRef='+_pageRef+'&';
			 $.getJSON(url,{term: request.term},function( data2 ) {
			 response(data2['arrayValuesAutoComplete']);
	      })
		 },
	    minLength: 1,
	 });
	 
	$("#templateHeaderForm_"+_pageRef).processAfterValid("saveTemplateHeader");	 
}

function addNewTemplateHeader()
{
	emptyForm();
}


function emptyForm()
{
	$("#thDiv_"+_pageRef).load(jQuery.contextPath+ "/path/templateHeader/templateHeaderAction_emptyTemplHeaderFrm.action?_pageRef="+_pageRef);
}

function deleteTemplateHeader(sel_row_index)
{	
	var args = {selRow: sel_row_index};
	_showConfirmMsg(deleteConfirm, deleteTitle, "deleteHeader", args,yes_confirm,no_confirm,300,100);
}

function deleteHeader(confirmation, args)
{
	var selRow = args.selRow;
	if(confirmation)      
	{
		var headerCode = $("#templateHeaderTable_"+_pageRef).jqGrid('getCell', selRow,'glsheaderVO.CODE');
		var zSrc=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_deleteHeader.action';
		params ={};
		params["code"]=headerCode;
		params["_pageRef"]=_pageRef;
 		$.ajax({
 				url: zSrc,
 				type: "POST",
 				data: params,				
 			    success: function(xml)
 			    {
	 			   	reloadGridTemplateHeader();
	 			   	emptyForm();
 		    	}
 		});
	}
}

function reloadGridTemplateHeader()
{
	$("#templateHeaderTable_"+_pageRef).trigger("reloadGrid");
}

function editTemplateHeader()
{
	 _showProgressBar(true);
	rowid =$("#templateHeaderTable_"+_pageRef).jqGrid('getGridParam','selrow');
	var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retrieveTemplateHeader.action';
	params = {};
	params["_pageRef"]=_pageRef;
	params["code"]=$("#templateHeaderTable_"+_pageRef).jqGrid('getCell', rowid,'glsheaderVO.CODE');
		$.post(url, params , function( param )
 		{
 			$("#thDiv_"+_pageRef).html(param);
 			 _showProgressBar(false);
 		},"html");
	
}

function createLike()
{
		rowid =$("#templateHeaderTable_"+_pageRef).jqGrid('getGridParam','selrow');
		var url=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_retrieveTemplateHeader.action';
		params = {};
		var code = $("#templateHeaderTable_"+_pageRef).jqGrid('getCell', rowid,'glsheaderVO.CODE');
		if(code==null || code==undefined || code=='undefined' || code=="")
		{
			_showErrorMsg(chooseHeader);
			return;
		}
		params["code"]=$("#templateHeaderTable_"+_pageRef).jqGrid('getCell', rowid,'glsheaderVO.CODE');
		params["_pageRef"]=_pageRef;
		params["update"]="createLike";
		$.post(url, params , function( param )
 		{
			$("#thDiv_"+_pageRef).html(param);
 		},"html");
}

function checkIDAvailabilityTh()
{
	_showProgressBar(true);
	var zSrc=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_checkIdAvailability.action';
	params ={};
	params["code"]=$("#codeTmplHead_"+_pageRef).val();
	$.ajax({
		 url: zSrc,
         type:"post",
		 dataType:"json",
		 data: params,
		 success: function(param)
		 	{
				var currVal=param['code'];
		 		if(currVal=="1") 
		 		{
					$("#codeTmplHead_"+_pageRef).val("");
		 		}
		 		_showProgressBar(false);
		 	}
		 	});
}

function saveTemplateHeader()
{
     _showProgressBar(true);
	 var url = jQuery.contextPath+ "/path/templateHeader/templateHeaderAction_save.action";
	 myObject =  $("#templateHeaderForm_"+_pageRef).serialize();
	 $.ajax({
		 url: url,
         type:"post",
		 dataType:"json",
		 data: myObject,
		 success: function(param)
		 	{
		 		if(typeof param["_error"] == "undefined" || param["_error"] == null)
		   		{
		 			//relaod grid
		   	  		reloadGridTemplateHeader();
		   	  		//empty form
		   	  		emptyForm();
		   		}
		   	}
		 	 }
	 		);
	  _showProgressBar(false);
}

function previewTemplateHeader ()
{
	params =  $("#templateHeaderForm_"+_pageRef).serialize();
	var refreshUrl=jQuery.contextPath+'/path/templateHeader/templateHeaderAction_previewTemplateHeader.action'
	$('#thDivPreview_'+_pageRef).load(refreshUrl, params, function(param)
	 {
	 		$("#thDivPreview_"+_pageRef).html(param);
	});
}