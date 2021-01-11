
(function()
{
	var procCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			openProcDialog(editor);
		}
	};

	var pluginName = 'linkProcedure';

	// Register a plugin named "linkProcedure".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, procCmd );
			editor.ui.addButton( 'Procedure',
				{
					label : 'Procedure',
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'images/linkProcedures.png')
				});
		}
	});
})();