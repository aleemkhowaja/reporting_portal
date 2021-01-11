/*
 * annasuccar
 * add to CKEditor Toolbar an item for query group by.
 */

(function() {
    var groupByCmd =
    {
        modes: { wysiwyg: 1, source: 1 },
        readOnly : 1,
        exec: function(editor) {
        	openGroupBy();
        }
    };
    
    var pluginName = 'groupBy';
    
    // Register a plugin named "properties".
    CKEDITOR.plugins.add(pluginName,
    {
      init: function(editor) {
          var command = editor.addCommand(pluginName, groupByCmd);
          editor.ui.addButton('GroupBy',
          {
             label: editor.lang.groupBy,
             command: pluginName,
             icon: CKEDITOR.getUrl(this.path + 'images/groupBy.png')
          });
      }
  });
})();	