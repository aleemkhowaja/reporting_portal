var missReqInput = '<ps:text name="reporting.missing"/>';
function saveAll()
{
			_showProgressBar(true);
		 
			 var url = jQuery.contextPath+ "/path/controlRecord/controlRecordAction_updateControlRecordData.action?";
			 params =  $("#controlRecordForm_"+_pageRef).serializeForm();

			 
	$.ajax({
			 url: url,
	         type:"post",
			 data: params,
			 success: function(param)
			 {
				 if(typeof param["_error"] == "undefined" || param["_error"] == null)
			   		{
					  $("#controlRecDiv_"+_pageRef).html(param);
			   		}
			 	else
			 		{
						_showErrorMsg(param["_error"],"error",300,200); 			 		
			 		}
				 	 _showProgressBar(false);
			 }
			
		});
}