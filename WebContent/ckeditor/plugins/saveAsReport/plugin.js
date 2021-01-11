
(function()
{
	var saveAsReportCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			openSaveAsReportDialog(editor);
		}
	};

	var pluginName = 'saveAsReport';

	// Register a plugin named "saveAsReport".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, saveAsReportCmd );
			editor.ui.addButton( 'SaveAs',
				{
					label : 'Save As',
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'images/saveAsReport.png')
				});
		}
	});
})();