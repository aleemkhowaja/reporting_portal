/*
 * annasuccar
 * add to CKEditor Toolbar an item for report properties.
 */

(function() {
    var propertiesCmd =
    {
        modes: { wysiwyg: 1, source: 1 },
        readOnly : 1,
        exec: function(editor) {
        	openProperties();
        }
    };
    
    var pluginName = 'properties';
    
    // Register a plugin named "properties".
    CKEDITOR.plugins.add(pluginName,
    {
      init: function(editor) {
          var command = editor.addCommand(pluginName, propertiesCmd);
          editor.ui.addButton('Properties',
          {
             label: editor.lang.properties,
             command: pluginName,
             icon: CKEDITOR.getUrl(this.path + 'images/properties.png')
          });
      }
  });
})();	