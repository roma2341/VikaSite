
$(document).ready(function() {

   //init popups
    if ($(".popup-link").length) {

	    $('.popup-link').magnificPopup({
	      type: 'inline',
	       preloader: false,
	        callbacks: {
	          beforeOpen: function() {
	            $.magnificPopup.close();
	          }
	        }	    
	    });

    }

    $('[data-toggle="tooltip"]').tooltip();

    //style form

	$('select:not(.value-select)').selectpicker();
	$("#id_date_picker").datetimepicker({"format": "MM/DD/YYYY"});

	// jQuery to collapse the navbar on scroll
	$(window).scroll(function() {
	    UpdateHeaderSet ()
	});

	if ($(".toggle-user-menu").length) {
        $('.toggle-user-menu').html($('.left-user-menu li.active a').text());
    }

    $('.toggle-user-menu').on('click', function(e){
        e.preventDefault();
        var _this = $(this)
        _this.next().slideToggle();
        _this.toggleClass('open');       
    });

    $('.remove-item').on('click', function(e){
        e.preventDefault();
        var _this = $(this)
        _this.parent().remove();      
    });

    $('.toggle-off-menu > span').on('click', function(e){
        e.preventDefault();
        var _this = $(this)
        id = _this.parent().attr('data-toggle'); 

        $('.toggle-off-menu').removeClass('open');
        $('.wrap-off-canvas-menu').removeClass('open');

        if (!$(id).length) return false;

        $(id).toggleClass('open');
        _this.parent().toggleClass('open');

    });

    $('.close-off-canvas-menu').on('click', function(e){
        e.preventDefault();
        $('.toggle-off-menu').removeClass('open');
        $('.wrap-off-canvas-menu').removeClass('open');
    });

    

    $('.toggle-password').on('click','span' ,function(e){
        e.preventDefault();
        var _this = $(this).parent()
        _this.toggleClass('open');       
    });

    $('.wrap-message textarea').on('keyup', function(e){
        e.preventDefault();

        var _this = $(this);
        var _Wrapform = _this.closest('form');

        if(_this.val().length!=0)  {
            _Wrapform.find('.btn-check-changes').removeClass('disabled');
        } 
        else {
           _Wrapform.find('.btn-check-changes').addClass('disabled'); 
        }
        
       
  
    });


});

function UpdateHeaderSet (){
    if ($(".navbar").offset().top > 50) {
        $(".navbar-fixed-top").addClass("top-nav-collapse");
    } else {
        $(".navbar-fixed-top").removeClass("top-nav-collapse");
    }
}

