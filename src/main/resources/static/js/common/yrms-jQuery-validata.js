/************************************************************************************
* 
* 项目名称：yueqingRMS   
* 类名称：yrms-jQuery-validata.js  
* 类描述：jQuery-validata.js 进行初始化封装
* 创建人：林曌   
* 创建时间：2017年7月10日 上午11:16:55   
* 修改人：   
* 修改时间：2017年7月10日 上午11:16:55   
* 修改备注：   
* @version  
*  
************************************************************************************/

(function(){ 
	
	//jQuery-validata-重置状态
	$.extend( $.validator.prototype,{
		resetForm: function() {
			//源码部分start   不许动
			if ( $.fn.resetForm ) {
				$( this.currentForm ).resetForm();
			}
			this.invalid = {};
			this.submitted = {};
			this.prepareForm();
			this.hideErrors();
			var elements = this.elements()
				.removeData( "previousValue" )
				.removeAttr( "aria-invalid" );
			this.resetElements( elements );
			//源码部分end
			
			//重置自定义状态
			var eles = $( this.currentForm )
				.find( "input, select, textarea, [contenteditable]" )
				.not( ":submit, :reset, :image, :disabled" )
				.not( this.settings.ignore );
			eles.parent().removeClass( "has-success" ).removeClass( "has-error" );
		}
	});
	
	var validataoption = {
			errorElement: "em",
			errorPlacement: function ( error, element ) {
				// Add the `help-block` class to the error element
				error.addClass( "help-block" );
	
				// Add `has-feedback` class to the parent div.form-group
				// in order to add icons to inputs
				element.parent().addClass( "has-feedback" );
	
				if ( element.prop( "type" ) === "checkbox" ) {
					error.insertAfter( element.parent( "label" ) );
				} else {
					error.insertAfter( element );
				}
	
				// Add the span element, if doesn't exists, and apply the icon classes to it.
	//			if ( !element.next( "span" )[ 0 ] ) {
	//				$( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
	//			}
			},
			success: function ( label, element ) {
				// Add the span element, if doesn't exists, and apply the icon classes to it.
	//			if ( !$( element ).next( "span" )[ 0 ] ) {
	//				$( "<span class='glyphicon glyphicon-ok form-control-feedback'></span>" ).insertAfter( $( element ) );
	//			}
			},
			highlight: function ( element, errorClass, validClass ) {
				$( element ).parent().addClass( "has-error" ).removeClass( "has-success" );
	//			$( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
			},
			unhighlight: function ( element, errorClass, validClass ) {
				$( element ).parent().addClass( "has-success" ).removeClass( "has-error" );
	//			$( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
			}	
		};
		$.validator.setDefaults(validataoption);
})();