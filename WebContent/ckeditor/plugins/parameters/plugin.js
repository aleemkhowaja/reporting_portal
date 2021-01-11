/*
 * annasuccar
 * add to CKEditor Toolbar an item for report parameters.
 */

(function() {
    var parametersCmd =
    {
        modes: { wysiwyg: 1, source: 1 },
        readOnly : 1,
        exec: function(editor) {
        	openParameters();
        }
    };
    
    var pluginName = 'parameters';
    
    // Register a plugin named "parameters".
    CKEDITOR.plugins.add(pluginName,
    {
      init: function(editor) {
          var command = editor.addCommand(pluginName, parametersCmd);
          editor.ui.addButton('Parameters',
          {
             label: editor.lang.parameters,
             command: pluginName,
             icon: CKEDITOR.getUrl(this.path + 'images/parameters.PNG')
          });
      }
  });
})();	