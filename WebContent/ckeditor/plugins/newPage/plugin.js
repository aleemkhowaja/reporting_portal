/*
 * annasuccar
 * customize CKEditor 'NewPage' button to load a new page containing the custom layout (page divided into 4 parts)
 * instead of loading a blank page
 */

(function()
{
	var newpageCmd =
	{
		modes : { wysiwyg:1, source:1 },

		exec : function( editor )
		{
			if( confirm("Are you sure to load new page ?") )
			{
				editor.setData( editor.config.newpage_html );
				editor.focus();
				$('#reportId').attr("value",0);
				cancelEvents();
				configCommands();
			}
		},
		async : true		
	};

	var pluginName = 'newPage';

	// Register a plugin named "newPage".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, newpageCmd );
			editor.ui.addButton( 'NewPage',
			{
				label : editor.lang.newPage,
				command : pluginName
			});
		}
	});
})();