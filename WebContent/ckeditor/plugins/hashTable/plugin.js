(function()
{
	var hashTblCmd =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		readOnly : 1,
		exec : function( editor )
		{
			openHashTblDialog(editor);
		}
	};

	var pluginName = 'hashTable';

	// Register a plugin named "hashTable".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, hashTblCmd );
			editor.ui.addButton( 'HashTable',
				{
					label : 'HashTable',
					command : pluginName,
					icon: CKEDITOR.getUrl(this.path + 'image/hashTbl.png')
				});
		}
	});
})();


