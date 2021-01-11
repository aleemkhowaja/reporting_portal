/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/
CKEDITOR.dialog.add( 'statictext', function( editor )
{
	var autoAttributes =
	{
		value : 1,
		size : 1
	};

	var acceptedTypes =
	{
		text : 1,
		password : 1
	};

	return {
		title : editor.lang.statictext?editor.lang.statictext.title:'Static Text Properties',
		minWidth : 350,
		minHeight : 150,
		onShow : function()
		{
			delete this.statictext;

			var element = this.getParentEditor().getSelection().getSelectedElement();
			if ( element && element.getName() == "input" &&
					( acceptedTypes[ element.getAttribute( 'type' ) ] || !element.getAttribute( 'type' ) ) )
			{
				this.statictext = element;
				this.setupContent( element );
			}
		},
		onOk : function()
		{
			var editor,
				element = this.statictext,
				isInsertMode = !element;

			if ( isInsertMode )
			{
				editor = this.getParentEditor();
				element = editor.document.createElement( 'input' );
				element.setAttribute( 'type', 'text' );
				element.setAttribute( 'label', 'true' );
				element.setAttribute( 'style', 'font-size: 9px;' );
			}

			if ( isInsertMode )
				editor.insertElement( element );
			this.commitContent( { element : element } );
		},
		onCancel : function()
		{
			this.foreach( function( contentObj )
					{
						contentObj.getInputElement().removeAttribute( 'readOnly' );
					} );
		},
		onLoad : function()
		{
			var autoSetup = function( element )
			{
				var value = element.hasAttribute( this.id ) && element.getAttribute( this.id );
				this.setValue( value || '' );
			};

			var autoCommit = function( data )
			{
				var element = data.element;
				var value = this.getValue();

				if ( value )
					element.setAttribute( this.id, value );
				else
					element.removeAttribute( this.id );
			};

			this.foreach( function( contentObj )
				{
					if ( autoAttributes[ contentObj.id ] )
					{
						contentObj.setup = autoSetup;
						contentObj.commit = autoCommit;
					}
				} );
		},
		contents : [
			{
				id : 'info',
				label : editor.lang.statictext?editor.lang.statictext.title:'Static Text Properties',
				title : editor.lang.statictext?editor.lang.statictext.title:'Static Text Properties',
				elements : [
					{
						id : 'value',
						type : 'text',
						label : editor.lang.statictext?editor.lang.statictext.value:'Value',
						'default' : '',
						accessKey : 'V',
						style : 'width:180px'
					},
					{
						type : 'hbox',
						widths : [ '50%', '50%' ],
						children :
						[
							{
								id : 'size',
								type : 'text',
								label : editor.lang.statictext?editor.lang.statictext.charWidth:'Character Width',
								'default' : '',
								accessKey : 'C',
								style : 'width:50px',
								validate : CKEDITOR.dialog.validate.integer( editor.lang.common.validateNumberFailed )
							}
						],
						onLoad : function()
						{
							// Repaint the style for IE7 (#6068)
							if ( CKEDITOR.env.ie7Compat )
								this.getElement().setStyle( 'zoom', '100%' );
						}
					},
					{
						type : 'text',
						id : 'txtBorder',
						'default' : 0,
						label : editor.lang.table.border,
						controlStyle : 'width:3em',
						validate : CKEDITOR.dialog.validate['number']( editor.lang.table.invalidBorder ),
						setup : function( element )
						{
							this.setValue( element.getAttribute( 'border' ) || '' );
						},
						commit : function( data )
						{
							var element = data.element;
							if ( this.getValue() )
								element.setAttribute( 'border', this.getValue() );
							else
								element.removeAttribute( 'border' );
						}
					}
				]
			}
		]
	};
});
