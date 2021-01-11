/*
annasuccar-- ckeditor: configuration and content manipulation 
*/
$(document).ready(function () {
	var instance = CKEDITOR.instances['editor1'];
			var isSyb = $("#isSyb_"+_pageRef)

	if(instance) {
		CKEDITOR.remove(instance);
	}
	if(isSyb.val()==1)
	{
		
	CKEDITOR.replace( 'editor1',
			{
				/*
				 * Toolbar config
				 */
				toolbar : 'MyToolbar',
			    toolbar_MyToolbar :
				[
				 	{ name: 'document',		items : [ 'Save','SaveAs','DocProps','Preview','Print' ] }, //'Source','NewPage','SaveAs'
				 	{ name: 'tools',		items : [ 'Maximize'] },
				 	'/',
					{ name: 'forms',		items : [ 'Properties', 'GroupBy', 'Parameters', 'Procedure' ] },
					{ name: 'insert',		items : [ 'Subreport','StaticText','pagenbr','Image','HorizontalRule','HashTable','Translate' ] }, //,'Table' (add it later after Image item)
					'/',
					{ name: 'basicstyles',	items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript' ] },
					{ name: 'colors',		items : [ 'TextColor'/*,'BGColor'*/ ] },
					{ name: 'styles',		items : [ 'Font','FontSize' ] },
					{ name: 'paragraph',	items : [ 'Outdent','Indent','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
					{ name: 'clipboard',	items : [ 'Undo','Redo' ] },
				 ],
				
				//scayt_autoStartup : true,
			    height : 400,  
			    width : '100%',
			    resize_enabled : false,  
			    //autoParagraph : true,
		
				extraPlugins : 'tableresize,ajaxSave,previewReport,uicolor,pagenumber,properties,groupBy,parameters,imagePreview,linkProcedure,subreports,hashTable,saveAsReport,translate', //newPage
				
				//skin
				skin : 'office2003',
				
				//font-size default label
				fontSize_defaultLabel : '9',
				
				//font default label
				font_defaultLabel : 'Arial',
				
				/*
				 * Font colors.
				 */
				colorButton_enableMore : true,

				colorButton_foreStyle :
				{
					element : 'font',
					attributes : { 'color' : '#(color)' }
				},

				colorButton_backStyle :
				{
					element : 'font',
					attributes	: { 'background-color' : '#(color)' }
				},

				on : { 'instanceReady' : configureHtmlOutput }
			});
	
}

	else
		{
			{
		
	CKEDITOR.replace( 'editor1',
			{
				/*
				 * Toolbar config
				 */
				toolbar : 'MyToolbar',
			    toolbar_MyToolbar :
				[
				 	{ name: 'document',		items : [ 'Save','SaveAs','DocProps','Preview','Print'] }, //'Source', 'NewPage'
				 	{ name: 'tools',		items : [ 'Maximize' ] },
				 	'/',
					{ name: 'forms',		items : [ 'Properties', 'GroupBy', 'Parameters', 'Procedure' ] },
					{ name: 'insert',		items : [ 'Subreport','StaticText','pagenbr','Image','HorizontalRule','Translate'] }, //,'Table' (add it later after Image item)
					'/',
					{ name: 'basicstyles',	items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript' ] },
					{ name: 'colors',		items : [ 'TextColor'/*,'BGColor'*/ ] },
					{ name: 'styles',		items : [ 'Font','FontSize' ] },
					{ name: 'paragraph',	items : [ 'Outdent','Indent','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
					{ name: 'clipboard',	items : [ 'Undo','Redo' ] }
				 ],
				
				//scayt_autoStartup : true,
			    height : 400,  
			    width : '100%',
			    resize_enabled : false,  
			    //autoParagraph : true,
		
				extraPlugins : 'tableresize,ajaxSave,previewReport,uicolor,pagenumber,properties,groupBy,parameters,imagePreview,linkProcedure,subreports,saveAsReport,translate', //newPage
				
				//skin
				skin : 'office2003',
				
				//font-size default label
				fontSize_defaultLabel : '9',
				
				//font default label
				font_defaultLabel : 'Arial',
				
				/*
				 * Font colors.
				 */
				colorButton_enableMore : true,

				colorButton_foreStyle :
				{
					element : 'font',
					attributes : { 'color' : '#(color)' }
				},

				colorButton_backStyle :
				{
					element : 'font',
					attributes	: { 'background-color' : '#(color)' }
				},

				on : { 'instanceReady' : configureHtmlOutput }
			});
	
}
		}
});

/**
 * Adjust the behavior of the dataProcessor to avoid styles
 * and make it look like FCKeditor HTML output.
 */
function configureHtmlOutput( ev )
{
	var editorLoadedFrom=$("#editorLoadedFrom_"+_pageRef).val();
	//if from wizard ,after loading the editor call the function submitWizard
	if(editorLoadedFrom=="0")
	{
		submitWizard();
		$("#editorLoadedFrom_"+_pageRef).val("");
	}
	//if from  open report,after loading the editor call the function loadReport
	else if(editorLoadedFrom=="1")
	{
		loadReport();
		$("#editorLoadedFrom_"+_pageRef).val("");
	}
	var editor = ev.editor,
		dataProcessor = editor.dataProcessor,
		htmlFilter = dataProcessor && dataProcessor.htmlFilter;

	// Make output formatting behave similar to FCKeditor
	var dtd = CKEDITOR.dtd;
	for ( var e in CKEDITOR.tools.extend( {}, dtd.$nonBodyContent, dtd.$block, dtd.$listItem, dtd.$tableContent ) )
	{
		dataProcessor.writer.setRules( e,
			{
				indent : true,
				breakBeforeOpen : true,
				breakAfterOpen : false,
				breakBeforeClose : !dtd[ e ][ '#' ],
				breakAfterClose : true
			});
	}

	// Output properties as attributes, not styles.
	htmlFilter.addRules(
		{
			attributes :
				{
					style : function( value, element )
					{
						// Return #RGB for background and border colors
						return convertRGBToHex( value );
					}
				}
		} );
	
	//configure menuItems in contextmenu
	configureMenuItems(editor);	
}


/**
* Convert a CSS rgb(R, G, B) color back to #RRGGBB format.
* @param Css style string (can include more than one color
* @return Converted css style.
*/
function convertRGBToHex( cssStyle )
{
	return cssStyle.replace( /(?:rgb\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\))/gi, function( match, red, green, blue )
		{
			red = parseInt( red, 10 ).toString( 16 );
			green = parseInt( green, 10 ).toString( 16 );
			blue = parseInt( blue, 10 ).toString( 16 );
			var color = [red, green, blue] ;

			// Add padding zeros if the hex value is less than 0x10.
			for ( var i = 0 ; i < color.length ; i++ )
				color[i] = String( '0' + color[i] ).slice( -2 ) ;

			return '#' + color.join( '' ) ;
		 });
}

/**
 * add new menuItem in contextmenu to delete textfield element
 * remove textfieldproperties menuItem from contextmenu when textfield is for page nbr.
 */
function configureMenuItems(editor)
{
	if ( editor.addMenuItems )
	{
		//add a new menu item to the context menu to delete textfield element.
		editor.addMenuItems({
			textfieldDelete :
			{
				label : 'Delete Text Field',
				command : 'textfieldDelete',
				group : 'textfield',
				order : 1
			}
			,
			statictextDelete :
			{
				label : 'Delete Static Text',
				command : 'statictextDelete',
				group : 'statictext',
				order : 1
			}
		});
		
		editor.addCommand('textfieldDelete', {
			exec : function(editor) {
				var element = editor.getSelection().getStartElement();
				if (!element)
					return;
				
				element.remove();
			}
		});
		
		editor.addCommand('statictextDelete', {
			exec : function(editor) {
				var element = editor.getSelection().getStartElement();
				if (!element)
					return;
				
				element.remove();
			}
		});
	}
	
	if ( editor.contextMenu )
	{
		editor.contextMenu.addListener( function( element, selection ) {		
			if(! element || !element.is('input'))
	            return null;
			
			if( element.getAttribute('label') )
				return null;
			
			 editor._.commands['textfield'].enable();
			 return {
				 textfieldDelete : CKEDITOR.TRISTATE_OFF
			 };
		});
		
		editor.contextMenu.addListener( function( element, selection ) {		
			if(! element || !element.is('input'))
	            return null;
			
			if( element.getAttribute('label') == null )
				return null;
			
			 editor._.commands['statictext'].enable();
			 return {
				 statictextDelete : CKEDITOR.TRISTATE_OFF
			 };
		});
	}
	
	//do not open textfield properties dialog when the textfield is for page nbr.
	editor.on( 'doubleclick', function( evt )
	{
		var element = evt.data.element;
		if ((element.is( 'img' )) || ( element.is( 'input' ) 
				&& (element.getAttribute("name") == 'pageNbr' || element.getAttribute("name") == 'pageCnt'))
			)
		{
			evt.data.dialog = '';
		}
	});
}

/**
 * cancel event on Ctrl+A keypress
 */
function cancelEvents()
{
	 var eventString;
	 if($.browser.msie)
	 {
	 	eventString = "keydown"
	 }
	 else
	 {
	 	eventString = "keypress"
	 }
	setTimeout(function(){
				events.addEvent(CKEDITOR.instances.editor1.document.$,eventString,function(event){		
					//cancel event on select all
					var charCode;
					if(event.charCode!=undefined)
					{
						charCode = event.charCode;
					}
					else
					{
						charCode = event.keyCode;
					}
					if( event.ctrlKey && ( charCode == 97 || charCode == 65 ) ) {
						return events.cancelEvent(event);
					}
					
					//cancel event on alphanumeric keys
					if( (event.keyCode == 0 || event.keyCode ==charCode) && charCode != 32 && charCode!=8) {
						return events.cancelEvent(event);						
					}	
				});
			}
		,1000);
}

/**
 * configure tabletools commands in contextmenu to be hidden for root table (used for layout) 
 * and shown for other tables.
 * remove textfieldproperties menuItem from contextmenu when textfield is for page nbr
 */
function configCommands()
{
	setTimeout(function(){
		var editor = CKEDITOR.instances.editor1;
		/*after loading the report inside the editor , call the function resetDirty
		that will set to false the changes done inside the editor*/
	    editor.resetDirty();					
		var rootTable = editor.document.$.getElementsByTagName("table")[0];
		events.addEvent(rootTable, "mousedown", 
				function(event){
			var target = event.target ? event.target : event.srcElement;
					var element = CKEDITOR.dom.element.get( target );
					var isTable = element.hasAscendant( 'table', 1 );

					if ( isTable )
					{
						var tblId = element.getAscendant('table', 1 ).getId();
						
						editor.removeMenuItem('tablerow');							
						if( element.getAscendant('table', 1 ).getId() == 'root' ){
							editor.removeMenuItem('tablecell');
							editor.removeMenuItem('tablecolumn');
							disableEnableCommands(editor,true);
						}
						else{
							if(element.getName() == "th"){
								editor.removeMenuItem('tablecell');
								editor.removeMenuItem('tablecolumn');
								disableEnableCommands(editor,true);
							}
							else{								
								addTableToolsMenuItems(editor);
								disableEnableCommands(editor,false);
							}
						}
						
						if ( element.is('input') )
						{
							if(element.getAttribute("name") == 'pageNbr' || element.getAttribute("name") == 'pageCnt')
							{
								editor._.commands['textfield'].disable();
								editor._.commands['statictext'].disable();
							}
						}
						if(element.is( 'img' )) 
						{
							editor.removeMenuItem('image');
							
						}
					}
			    });
	},1000);
}

/**
 * configure tabletools commands state in contextMenu
 */
function disableEnableCommands(editor, disable)
{	
	var command,
    commands = editor._.commands,
    commandNames = ["tableDelete","tableProperties"];
    
	for(var i=0; i<commandNames.length; i++)
	{
		command = commands[commandNames[i]];
		if(disable)
			command.disable();
		else {
			command.enable();
		}		
	}	
}

/**
 * set x,y,width and height attributes for elements in editor html content.
 */
function setHtmlElemProperties()
{
	var editor = CKEDITOR.instances['editor1'];
	var elem = [];
	
	ArrayConcat(elem, editor.document.$.getElementsByTagName("p"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("hr"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("table"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("td"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("th"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("tr"));
	ArrayConcat(elem, editor.document.$.getElementsByTagName("input"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("span"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("img"));
	ArrayConcat(elem,editor.document.$.getElementsByTagName("div"));



	var prop;
	for (i = 0; i < elem.length; i++) {
   		prop = findProperties(elem[i]);
   		elem[i].setAttribute('x', prop[0]);
   		elem[i].setAttribute('y', prop[1]);
   		elem[i].setAttribute('width_jr', prop[2]);
   		elem[i].setAttribute('height_jr', prop[3]);
	}
}
function ArrayConcat(array,obj)
{
	for (var i = 0; i < obj.length; i++) 
	{
    	array.push(obj[i]);
	}
}

function findProperties(obj)
{  
	var left   = 0;
	var top    = 0;
	var width  = 0;
	var height = 0;
	
	width  += obj.offsetWidth;
	height += obj.offsetHeight;
	top    += obj.offsetTop;
	left   += obj.offsetLeft;	

	return [left,top,width,height];
}

function insertPageNbr(editor)
{
	element = editor.document.createElement( 'input' );
	element.setAttribute( 'name', 'pageNbr' );
	element.setAttribute( 'type', 'text' );
	element.setAttribute( 'value', '\"Page \"+$V{PAGE_NUMBER}+\" of\"');
	element.setAttribute( 'style', 'width:80px; font-size: 9px;' );
	editor.insertElement( element );	
	element = editor.document.createElement( 'input' );
	element.setAttribute( 'name', 'pageCnt' );
	element.setAttribute( 'type', 'text' );
	element.setAttribute( 'value', '\" \" + $V{PAGE_NUMBER}' );
	element.setAttribute( 'style', 'width:40px; font-size: 9px;' );
	editor.insertElement( element );
}

function openProperties()
{
	$('#propDialog').dialog('open');
}

function openGroupBy()
{
	$('#groupDialog').dialog('open');
}

var range;
function openImageDialog(editor)
{
 	range = editor.getSelection().getRanges()[0];
	$('#imageDialog').dialog('open');
}

function openParameters()
{
	$('#paramDialog').dialog('open');
}


function openProcDialog(editor)
{		
	$('#procDialog').dialog('open');
}

function openSubRepDialog(editor)
{		
	range = editor.getSelection().getRanges()[0];
	$('#subRepDialog').dialog('open');
}

function openHashTblDialog(editor)
{		
	$('#hashTblDialog').dialog('open');
}

function openTranslateDialog(editor)
{		
	//check if the reportId is different then null
	var zSrc = jQuery.contextPath+"/path/designer/reportDesign_checkIfSavedReport.action";
	var params = {};
	$.post(zSrc,params,function(param) 
	{
		var isSaved=param["updates"];
		var repAppName=param["repAppName"];
		var repProgRef=param["repProgRef"];
		if(isSaved=="1")
		{
			var url = jQuery.contextPath+"/path/designer/reportsList_checkUpdateReportAccess.action"
			var params = {};
			params["_pageRef"] = _pageRef;
			//saveAs
			params["updates2"] = "SA";
			//check for saveAs access
			$.post(url,params,function(param) 
			{
				var isAccess = param["accessStr"];
				if (isAccess == "N")
				{
					_showErrorMsg(repUpdateAccessAlert,error_msg_title, 350, 120);//reporting.repUpdateAccessAlert
					return;
				}
				 else
				 {
					 //to open the translation screen
					 loadTransByAppPageRef(repAppName, repProgRef);
				 }
			});
		}
		else
		{
			_showErrorMsg(saveRepBefore,error_msg_title,350,120);
		}
	});
}

function openSaveAsReportDialog()
{
	//check if the reportId is different then null
	var zSrc = jQuery.contextPath+"/path/designer/reportDesign_checkIfSavedReport.action";
	var params = {};
	$.post(zSrc,params,function(param) 
	{
		var isSaved=param["updates"];
		if(isSaved=="1")
		{
			var url = jQuery.contextPath+"/path/designer/reportsList_checkUpdateReportAccess.action"
			var params = {};
			params["_pageRef"] = _pageRef;
			//saveAs
			params["updates2"] = "SA";
			//check for saveAs access
			$.post(url,params,function(param) 
			{
				var isAccess = param["accessStr"];
				var isEditable = param["editableStr"];
				if (isAccess == "N")
				{
					_showErrorMsg(desRepSaveAsAccessAlert,error_msg_title, 350, 120);
					return;
				}
				if(isEditable == "N")
				{
					_showErrorMsg(repCantSaveUnmodifiable, error_msg_title, 350, 120);
					return;
				}
				$('#saveAsRepDialog').dialog('open');
			});
		}
		else
		{
			_showErrorMsg(saveRepBefore,error_msg_title,350,120);
		}
	});
	
}