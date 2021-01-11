/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/
CKEDITOR.dialog.add( 'textfield', function( editor )
{
	var autoAttributes =
	{
		value : 1,
		size : 1,
		maxLength : 1
	};

	var acceptedTypes =
	{
		text : 1,
		password : 1
	};

	return {
		title : editor.lang.textfield.title,
		minWidth : 350,
		minHeight : 150,
		onShow : function()
		{
			delete this.textField;

			var element = this.getParentEditor().getSelection().getSelectedElement();
			if ( element && element.getName() == "input" &&
					( acceptedTypes[ element.getAttribute( 'type' ) ] || !element.getAttribute( 'type' ) ) )
			{
				this.textField = element;
				this.setupContent( element );
			}
		},
		onOk : function()
		{
			var editor,
				element = this.textField,
				isInsertMode = !element;

			if ( isInsertMode )
			{
				editor = this.getParentEditor();
				element = editor.document.createElement( 'input' );
				element.setAttribute( 'type', 'text' );
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
				//annasuccar- set value input to readOnly when textfield element contains a field 
				if(element.getAttribute( 'field' ) == 'true' && this.id == 'value')
					this.getInputElement().setAttribute( 'readOnly', true );
			};

			var autoCommit = function( data )
			{
				var element = data.element;
				var value = this.getValue();

				if ( value )
					element.setAttribute( this.id, value );
				else
					element.removeAttribute( this.id );
				
				//annasuccar- remove attribute readOnly
				this.getInputElement().removeAttribute( 'readOnly' );
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
				label : editor.lang.textfield.title,
				title : editor.lang.textfield.title,
				elements : [
					{
						type : 'hbox',
						widths : [ '50%', '50%' ],
						children :
						[
							{
								id : '_cke_saved_name',
								type : 'text',
								label : editor.lang.textfield.name,
								'default' : '',
								accessKey : 'N',
								setup : function( element )
								{
									this.setValue(
											element.data( 'cke-saved-name' ) ||
											element.getAttribute( 'name' ) ||
											'' );
									
									//annasuccar- set name input to readOnly when textfield element contains a field
									if(element.getAttribute( 'field' ) == 'true')
										this.getInputElement().setAttribute( 'readOnly', true );
								},
								commit : function( data )
								{
									var element = data.element;

									if ( this.getValue() )
										element.data( 'cke-saved-name', this.getValue() );
									else
									{
										element.data( 'cke-saved-name', false );
										element.removeAttribute( 'name' );
									}
									//annasuccar- remove attribute readOnly
									this.getInputElement().removeAttribute( 'readOnly' );
								}
							},
							{
								id : 'value',
								type : 'text',
								label : editor.lang.textfield.value,
								'default' : '',
								accessKey : 'V'
							}
						]
					},
					{
						type : 'hbox',
						widths : [ '50%', '50%' ],
						children :
						[
							{
								id : 'size',
								type : 'text',
								label : editor.lang.textfield.charWidth,
								'default' : '',
								accessKey : 'C',
								style : 'width:50px',
								validate : CKEDITOR.dialog.validate.integer( editor.lang.common.validateNumberFailed )
							}
							//annasuccar- remove maximum characters field
							/*,
							{
								id : 'maxLength',
								type : 'text',
								label : editor.lang.textfield.maxChars,
								'default' : '',
								accessKey : 'M',
								style : 'width:50px',
								validate : CKEDITOR.dialog.validate.integer( editor.lang.common.validateNumberFailed )
							}*/
						],
						onLoad : function()
						{
							// Repaint the style for IE7 (#6068)
							if ( CKEDITOR.env.ie7Compat )
								this.getElement().setStyle( 'zoom', '100%' );
						}
					},
					//annasuccar- add border field
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
					//annasuccar- remove type DDL
					/*,
					{
						id : 'type',
						type : 'select',
						label : editor.lang.textfield.type,
						'default' : 'text',
						accessKey : 'M',
						items :
						[
							[ editor.lang.textfield.typeText, 'text' ],
							[ editor.lang.textfield.typePass, 'password' ]
						],
						setup : function( element )
						{
							this.setValue( element.getAttribute( 'type' ) );
						},
						commit : function( data )
						{
							var element = data.element;

							if ( CKEDITOR.env.ie )
							{
								var elementType = element.getAttribute( 'type' );
								var myType = this.getValue();

								if ( elementType != myType )
								{
									var replace = CKEDITOR.dom.element.createFromHtml( '<input type="' + myType + '"></input>', editor.document );
									element.copyAttributes( replace, { type : 1 } );
									replace.replace( element );
									editor.getSelection().selectElement( replace );
									data.element = replace;
								}
							}
							else
								element.setAttribute( 'type', this.getValue() );
						}
					}*/
				]
			}
		]
	};
});
