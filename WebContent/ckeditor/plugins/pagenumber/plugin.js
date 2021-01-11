/*
 * annasuccar
 * add to CKEditor Toolbar an item for page numbering.
 */

(function() {
    var pagenbrCmd =
    {
        modes: { wysiwyg: 1, source: 1 },
        readOnly : 1,
        exec: function(editor) {
        	insertPageNbr(editor);
        }
    };
    
    var pluginName = 'pagenumber';
    
    // Register a plugin named "pagenumber".
    CKEDITOR.plugins.add(pluginName,
    {
      init: function(editor) {
          var command = editor.addCommand(pluginName, pagenbrCmd);
          editor.ui.addButton('pagenbr',
          {
             label: editor.lang.pagenbr,
             command: pluginName,
             icon: CKEDITOR.getUrl(this.path + 'images/pagenbr.png')
          });
      }
  });
})();	