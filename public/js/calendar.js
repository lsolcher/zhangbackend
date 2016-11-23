$(document).ready(function() {

  $('.option-choice a').click(function() {

    $(this).toggleClass('active').siblings().removeClass('active');

  })

  $('.calendar-input').click(function(event) {

    var _prio = $(event.target).attr('prio')

    if(typeof _prio == 'undefined' || _prio == '')
    _prio = 1
    _prio = parseInt(_prio)
    _prio++


    if ($(event.target).attr('prio') >= "3") {
      _prio = 0;
    }

    $(event.target).attr('prio', _prio);

  })

  $('.menu-trigger').click(function() {
    $(".menu").fadeToggle();    
  });
  
  $(window).resize(function() {
    $(".menu").removeAttr('style');
  });

});
