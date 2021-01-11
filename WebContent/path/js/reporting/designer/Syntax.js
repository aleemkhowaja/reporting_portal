function rep_qry_documentReadyFunc()
{
	if(openSqb=="true")
	{
		document.getElementById("sqbSyntaxArea_"+_pageRef).style.display="inline";
		document.getElementById("qbeSyntaxArea_"+_pageRef).style.display="none";
		if(showQryName=="true")
		{
		document.getElementById("qryNameTr_"+_pageRef).style.display="inline";
		}
		else
		{
			document.getElementById("connDesc_"+_pageRef).style.width="10%";
			document.getElementById("connSelect_"+_pageRef).style.width="15%";
		}
	}
	else
	{
		document.getElementById("sqbSyntaxArea_"+_pageRef).style.display="none";
		document.getElementById("qbeSyntaxArea_"+_pageRef).style.display="inline";
	}
	document.getElementById("queryBeingVal_"+_pageRef).innerHTML="";
}

function checkSqbQryName()
{
	var zSrc=jQuery.contextPath+"/path/designer/queryDesign_checkQryName";
	params ={};
	params["updates"]=$("#sqbQryName_"+_pageRef).val();
	$.getJSON(zSrc, params, function( data2, status, xhr ) 
	{
			var isExist=data2['updates'];
 			if(isExist=="true")
 			{
 				_showErrorMsg(sqbNameExistAlert);
				$("#sqbQryName_"+_pageRef).val("");
 			}
	});
}

function rep_qry_insertArgEvent()
{
		$("#syntaxArgumentsGrid_"+_pageRef).subscribe('insertArgument', function(event,data) 
		{
			rowid = (event["originalEvent"])["id"];
			myObject = $("#syntaxArgumentsGrid_"+_pageRef).jqGrid('getRowData', rowid);
		
			var argType=myObject["ARGUMENT_TYPE"];
			var argName=myObject["ARGUMENT_NAME"];
			var insertVal="";
		
			if(myObject["MULTISELECT_YN"]==1)
			{
				insertVal="$X{IN, ,"+argName+"}";
			}
			else if(argType=="NUMBER")
			{
				insertVal="$P{"+argName+"}";
			}
			else if(argType=="VARCHAR2")
			{
				insertVal="'$P!{"+argName+"}'";
			}
			else if(argType=="DATE")
			{
				insertVal="$P{"+argName+"}";
			}
			else if(argType=="DATETIME")
			{
				insertVal="$P{"+argName+"}";
			}
			var el=document.getElementById("sqbSyntax_"+_pageRef)
			var cursPosition   = returnCursorPosStart(document.getElementById("sqbSyntax_"+_pageRef));
			var startingString = ($("#sqbSyntax_"+_pageRef).val()).substring(0,cursPosition);
			var endString = ($("#sqbSyntax_"+_pageRef).val()).substring(cursPosition,($("#sqbSyntax_"+_pageRef).val()).length);
			$("#sqbSyntax_"+_pageRef).val(startingString+insertVal+endString);		
		});
}