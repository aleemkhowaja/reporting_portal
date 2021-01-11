
(function()
{
	var imageCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			openImageDialog(editor);
		}
	};

	var pluginName = 'imagePreview';

	// Register a plugin named "imagePreview".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, imageCmd );
			editor.ui.addButton( 'Image',
				{
					label : editor.lang.common.image,
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'images/insert_image.png')
				});
		}
	});
})();