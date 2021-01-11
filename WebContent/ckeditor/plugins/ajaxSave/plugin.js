/*
 * annasuccar
 * setup CKEditor to save via AJAX
 */

(function() {
    var saveCmd =
    {
        modes: { wysiwyg: 1, source: 1 },
        readOnly : 1,
        exec: function(editor) {
        	submitReport("save");
        }
    };
    
    var pluginName = 'ajaxSave';
    
    // Register a plugin named "ajaxSave".
    CKEDITOR.plugins.add(pluginName,
    {
      init: function(editor) {
          var command = editor.addCommand(pluginName, saveCmd);
          editor.ui.addButton('Save',
          {
             label: editor.lang.save,
             command: pluginName,
             icon: CKEDITOR.getUrl(this.path + 'images/save.png')
          });
      }
  });
})();	