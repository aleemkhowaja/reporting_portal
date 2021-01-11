(function()
{
	var translateCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			openTranslateDialog(editor);
		}
	};

	var pluginName = 'translate';

	// Register a plugin named "translate".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, translateCmd );
			editor.ui.addButton( 'Translate',
				{
					label : 'Translate',
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'image/translate.png')
				});
		}
	});
})();