
(function ($) {
    "use strict";

    
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validatereg-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
            if(passLength(input[i])==false){
            	showValidate(input[i]);
            	check=false;
            }
            if(!(input[input.length-2].equals(input[input.length-1])){
            	showValidate(input[input.length-1]);
            	check=false;
            }
        }

        return check;
    });

    $('.validatereg-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });
    
    $('.validatereg-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    function passLength(input){
    	if($(input).attr('type')=='password'|| $(input).attr('name')=='password'){
    		if($(input.length)<8){
    			return false;
    		}
    	}
    	return true;
    }
    
   

})(jQuery);