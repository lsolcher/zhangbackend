(function() {
  var app = angular.module("zhang-app").controller('prioController', function($scope, $rootScope) {
    // new value entered
    $scope.currentPriority = '';
    // list of displayed priorities
    $scope.priorities = [];

    $scope.getNewPrioId = function() {
      return $scope.possiblePriorities.length + $scope.priorities.length
    }

    // options for select box (replace this with actual options)
    $scope.possiblePriorities = [
      new SingleChoicePrio(10, 'Raumbeschaffenheit', ['breite','lange'], 'Ich bevorzuge ||| Räume'),
      new SingleChoicePrio(0, 'Unterrichtsbeginn', ['früher','später'], 'Innerhalb der von mir angegebenen Belegzeiten bevorzuge ich den Unterrichtsbeginn je ||| desto besser.'),
      new SingleChoicePrio(1, 'Anzahl Veranstaltungen pro Tag', ['mehr Veranstaltungen pro Tag, weniger Tage die Woche','weniger Veranstaltungen pro Tag, mehr Tage die Woche'], 'Ich bevorzuge |||.', false),
      //new SimplePrio(2, 'Blockunterricht aufteilen', 'Ich möchte meinen Blockunterricht (4SWS) nach Möglichkeit in zwei Einzelveranstaltungen (2SWS) aufteilen.'),
      new SimplePrio(2, 'Wöchentliche Veranstaltungen', 'Ich ziehe es vor die vierzehntägigen 4SWS meines Unterrichts in zwei wöchentliche Einzelveranstaltungen mit je 2SWS aufzuteilen.'),
      new ExcludeDayCombinationPrio(6, 'Tage ausschließen', 'Wenn ich am ||| unterrichte, möchte ich <b>nicht</b> am ||| unterrichten.', false),
      new ExcludeDayCombinationPrio(13, 'Uhrzeit ausschließen', 'Wenn ich am ||| um ||| unterrichte, möchte ich <b>nicht</b> am ||| um ||| unterrichten.'),
      new ExcludeDayCombinationPrio(11, 'Tage kombinieren', 'Wenn ich am ||| unterrichte, möchte ich <b>auch</b> am ||| unterrichten.'),
  	  new SingleChoicePrio(9, 'Pausen', ['1','2','3','4','5'], 'Ich möchte nach spätestens ||| aufeinanderfolgenden Vorlesungen eine längere Pause. Die Mittagspause zwischen 11:15 und 12:15 wird als längere Pause gezählt.'),
  	  new SingleChoicePrio(8, 'Maximale Lehrtage pro Woche', ['1','2','3','4','5'], 'Ich möchte nicht mehr als ||| Tage pro Woche an der Hochschule unterrichten.', false),
  	  new SingleChoicePrio(12, 'Maximale Anzahl aufeinanderfolgender Lehrtage', ['1','2','3','4','5'], 'Ich möchte pro Woche nicht mehr als ||| Tage am Stück unterrichten.', false),

      new FreeTextInput(20, 'Weitere Sonderwünsche - wenn dringend notwendig', 'Ich hätte gerne noch: |||')
    ];

    $scope.addPriorityToDom = function(prioElement) {
      var li = $('<li></li>').addClass('priority-entry').attr('data-id', prioElement.id)
      li.append($('<div></div>').addClass('priority-label').html(prioElement.getLabel()))
      li.append($('<div></div>').addClass('priority-delete').html('x').click(function(event) {

        var el = $(event.target).parents('.priority-entry')
        var id = parseInt(el.attr('data-id'))
        for(var i in $scope.priorities) {
          if(id != $scope.priorities[i].id) continue
          delete $scope.priorities[i]
          break
        }
        el.remove()

        // remove selected label if neccessary
        var removeSelectedClass = true
        for( var i in $scope.priorities) {
          if(prioElement.label == $scope.priorities[i].label) {
            removeSelectedClass = false
            break
          }
        }
        if(removeSelectedClass) {
          for(var i in $scope.possiblePriorities) {
            if($scope.possiblePriorities[i].label == prioElement.label) {
              $scope.possiblePriorities[i].jo_class = ""
              $scope.$digest()
              break;
            }
          }
        }
      }))
      li.append($('<div></div>').addClass('priority-conent').append(prioElement.getContent()))
      if(typeof prioElement.showCourses == 'undefined' || prioElement.showCourses) {
        var prioCourseSelect = $('<select></select>')
        prioCourseSelect.append($('<option></option>').html('Alle Kurse').val('-1'))
        for(var i in $rootScope.courseList) {
          prioCourseSelect.append($('<option></option>').val($rootScope.courseList[i].id).html($rootScope.courseList[i].kurzname))
        }
        li.append($('<div></div>').addClass('prio-course-select').append($('<label></label>').html('Kurs')).append(prioCourseSelect))
      }
      $('#priority-list').append(li)
    }

    // add initial priorities to list
    for(var i in $scope.priorities) {
      $scope.addPriorityToDom($scope.priorities[i])
    }

    $scope.selectedPrio = null
    $scope.$watch('selectedPrio', function(newValue, oldValue) {
      if(newValue == null) return
      $scope.priorities.push(jQuery.extend(true, {}, newValue)) // deep copy
      $scope.addPriorityToDom(newValue)
      newValue.id = $scope.getNewPrioId()
    })

    $scope.addPriority = function(event) {
      console.log(event)
    }

    $scope.selectPriority = function(event) {
      console.log(event)
      var el = $(event.target)
      if(!el.hasClass('priority-select-list-entry')) el = el.parents('.priority-select-list-entry')
      var id = parseInt(el.attr('data-id'))

      for(var i in $scope.possiblePriorities) {
        if($scope.possiblePriorities[i].id != id) continue
        $scope.selectedPrio = $scope.possiblePriorities[i]
        break
      }
    }

  });

  // parent of all Priority Objects
  var AbstractPrio = function() {
    this.content = this.getContent()
  }

  AbstractPrio.prototype.getLabel = function() {
    return "Priorität"
  }
  /*
   * This returns the content of the constraint if it has extra content
   */
  AbstractPrio.prototype.getContent = function() {
    return false
  }

  /*
   * implementation of a priority without content but its label
   */
  var SimplePrio = function(id, label, showCourses) {
    this.id = id
    this.label = label
    this.showCourses = typeof showCourses == 'undefined' ? true : showCourses
  }
  // do this for right
  SimplePrio.prototype = new AbstractPrio()
  SimplePrio.prototype.constructor = SimplePrio

  SimplePrio.prototype.getLabel = function() {
    return this.label
  }
  // no need to override getContent, because it doesnt have content


  /*
   * Example Implementation of a prio with content
   */
  var ExamplePrio = function(id, label) {
    this.id = id
    this.label = label
    this.content = this.getContent()
  }
  // do this for right
  ExamplePrio.prototype = new AbstractPrio()
  ExamplePrio.prototype.constructor = SimplePrio

  ExamplePrio.prototype.getLabel = function() {
    return this.label
  }

  ExamplePrio.prototype.getContent = function() {
     var out = $('<div></div>').addClass('somecontent')
     out.html('Example Content')
     return out
  }

  /*
   * Example Implementation of a prio with content
   */
  var Example2Prio = function(id, label) {
    this.id = id
    this.label = label
    this.content = this.getContent()
    this.value = 0
  }
  // do this for right
  Example2Prio.prototype = new AbstractPrio()
  Example2Prio.prototype.constructor = Example2Prio

  Example2Prio.prototype.getLabel = function() {
    return this.label
  }

  Example2Prio.prototype.getContent = function() {
     var out = $('<div></div>').addClass('somecontent')
     var button = $('<button>Klick mich!</button>')
     button.click(function(event) {
       // access the scope
       var scope = angular.element($('.wishes-section').get(0)).scope()
       for(var i in scope.priorities) {
         // find the right entity by id
         if(scope.priorities[i].id != $(event.target).parents('.priority-entry').attr('data-id')) continue
         // change some value
         scope.priorities[i].setValue(scope.priorities[i].value+1)
         console.log('That entities value', scope.priorities[i].value)
         break
       }
     })
     out.append(button)
     out.append('<span>'+this.value+'</span>')
     return out
  }
  // Example for changing the value and the dom at the same time
  Example2Prio.prototype.setValue = function(value) {
    this.value = value
    $('.priority-entry[data-id='+this.id+'] span').html(this.value)
  }



  // real implementations go here ...

  var DayTimePrio = function(id, label) {
    this.id = id
    this.label = label
    this.content = this.getContent()
  }
  // do this for right
  DayTimePrio.prototype = new AbstractPrio()
  DayTimePrio.prototype.constructor = DayTimePrio

  DayTimePrio.prototype.getLabel = function() {
    return this.label
  }

  DayTimePrio.prototype.getContent = function() {
     var out = $('<div></div>').addClass('somecontent')

     var days = ['Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag']
     var times = [
       '9:45',
       '10:30',
       '11:30',
       '12:30',
       '13:30',
       '14:30',
       '15:30'
     ]

    //  var daySelect = $('<select></select>').addClass('someselectclass')
    //  for(var i in days) {
    //    daySelect.append($('<option></option>').val(i).html(days[i]))
    //  }
    //  out.append(daySelect)
     //


    var c = $('<div></div>').addClass('weekchooser')

    for(var i in days) {
      var d = $('<div></div>').addClass('day selected').html(days[i]).attr('data-id', i)
      c.append(d)
      d.click(function(event) {
        $(event.target).toggleClass('selected')
      })
    }
    out.append(c)

    var timeSelect = $('<select></select>').addClass('someselectclass')
    for(var i in times) {
      timeSelect.append($('<option></option>').val(i).html(times[i]))
    }
    out.append(timeSelect)

     return out
  }



  var EarlyDayPrio = function(id, label) {
    this.id = id
    this.label = label
    this.content = this.getContent()
  }
  // do this for right
  EarlyDayPrio.prototype = new AbstractPrio()
  EarlyDayPrio.prototype.constructor = EarlyDayPrio

  EarlyDayPrio.prototype.getLabel = function() {
    return this.label
  }

  EarlyDayPrio.prototype.getContent = function() {
    var out = $('<div></div>').addClass('somecontent')

    var days = ['Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag']

    var c = $('<div></div>').addClass('weekchooser')

    for(var i in days) {
      var d = $('<div></div>').addClass('day selected').html(days[i]).attr('data-id', i)
      c.append(d)
      d.click(function(event) {
        $(event.target).toggleClass('selected')
      })
    }
    out.append(c)
    return out
  }



  var ExcludeDayCombinationPrio = function(id, label, text, showCourses) {
    this.id = id
    this.label = label
    this.content = this.getContent()
    this.text = text
    this.showCourses = typeof showCourses == 'undefined' ? true : showCourses
  }
  // do this for right
  ExcludeDayCombinationPrio.prototype = new AbstractPrio()
  ExcludeDayCombinationPrio.prototype.constructor = ExcludeDayCombinationPrio

  ExcludeDayCombinationPrio.prototype.getLabel = function() {
    return this.label
  }

  ExcludeDayCombinationPrio.prototype.getContent = function() {
     var out = $('<div></div>').addClass('somecontent')

     out.append($('#template1').clone())

     if(typeof this.text == 'string') {
       var pList = out.find('.priotext')
       var textArray = this.text.split('|||')
       for(var i in textArray) {
         $(pList.get(i)).html(textArray[i])
       }
     }
     return out
  }


  var SingleChoicePrio = function(id, label, input, text, showCourses) {
    this.id = id
    this.label = label
    this.input = input
    this.content = this.getContent()
    this.text = text
    this.showCourses = showCourses
    if(typeof this.showCourses === 'undefined') this.showCourses = true
  }
  // do this for right
  SingleChoicePrio.prototype = new AbstractPrio()
  SingleChoicePrio.prototype.constructor = SingleChoicePrio

  SingleChoicePrio.prototype.getLabel = function() {
    return this.label
  }

  SingleChoicePrio.prototype.getContent = function() {
    var out = $('<div></div>').addClass('somecontent')

    //var days = ['1', '2', '3', '4', '5']
    var textArray = new Array();
    if(typeof this.text == 'string') {
      textArray = this.text.split('|||')
      out.append($('<p></p>').addClass('priotext').html(textArray[0]))
    }


	   var daySelect = $('<select></select>').addClass('someselectclass')
     for(var i in this.input) {
       daySelect.append($('<option></option>').val(i).html(this.input[i]))
    }
    out.append(daySelect)
    if(textArray.length >= 2) {
      out.append($('<p></p>').addClass('priotext').html(textArray[1]))
    }
    return out
  }



  var FreeTextInput = function(id, label, text) {
    this.id = id
    this.label = label
    this.content = this.getContent()
    this.text = text
  }
  // do this for right
  FreeTextInput.prototype = new AbstractPrio()
  FreeTextInput.prototype.constructor = FreeTextInput

  FreeTextInput.prototype.getLabel = function() {
    return this.label
  }

  FreeTextInput.prototype.getContent = function() {
    var out = $('<div></div>').addClass('somecontent')

    //var days = ['1', '2', '3', '4', '5']
    var textArray = new Array();
    if(typeof this.text == 'string') {
      textArray = this.text.split('|||')
      out.append($('<p></p>').addClass('priotext').html(textArray[0]))
    }

    var freeWish = $('<textarea id="freeTextWish" name="freeTextWish" cols="35" rows="4"></textarea>').addClass('someselectclass')
    out.append(freeWish)
    return out
  }



})();

$(document).ready(function() {

    $('#course-list-next').click(function(){
      $('.course-selector').slideUp()
    })
    $('#add-course').click(function(){
      $('.course-selector').slideDown()
    })

    $('.menu-trigger').click(function() {
      $(".menu").fadeToggle();
    });

    $(window).resize(function() {
      $(".menu").removeAttr('style');
    });

});
