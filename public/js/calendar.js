$(document).ready(function() {

  $('.option-choice a').click(function() {
    $(this).toggleClass('active').siblings().removeClass('active');
    if ($(this).hasClass('active')) {
      setPrioByOption()
    } else {
      setPrioByClick()
    }
  })

  setPrioByClick()

  $('.menu-trigger').click(function() {
    $(".menu").fadeToggle();
  });

  $(window).resize(function() {
    $(".menu").removeAttr('style');
  });

});

function setPrioByOption() {
  $('.calendar-input').unbind('click')
  $('.calendar-input').click(function(event) {

    var _prio = $('.active').attr('prio');

    $(event.target).attr('prio', _prio);

  })
}

function setPrioByClick() {
  $('.calendar-input').unbind('click')
  $('.calendar-input').click(function(event) {

    var _prio = $(event.target).attr('prio')

    if(typeof _prio == 'undefined' || _prio == '') {
      _prio = 1
    }
    _prio = parseInt(_prio)
    _prio++

    if (_prio > 3) {
      _prio = 0;
    }

    $(event.target).attr('prio', _prio);

  })
}
