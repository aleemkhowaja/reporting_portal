/*
 * annasuccar
 * customize CKEditor preview button to generate from the editor output
 * a report using JasperReport api
 */

(function()
{
	var previewCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			submitReport("preview");
		}
	};

	var pluginName = 'previewReport';

	// Register a plugin named "preview".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, previewCmd );
			editor.ui.addButton( 'Preview',
				{
					label : editor.lang.preview,
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'images/preview.png')
				});
		}
	});
})();
