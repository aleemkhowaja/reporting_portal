(function()
{
	var subRepCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			openSubRepDialog(editor);
		}
	};

	var pluginName = 'subreports';

	// Register a plugin named "subreports".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, subRepCmd );
			editor.ui.addButton( 'Subreport',
				{
					label : 'Subreport',
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'image/subreport.png')
				});
		}
	});
})();