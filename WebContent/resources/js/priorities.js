(function() {
  var app = angular.module("zhang-app").controller('prioController', function($scope, $rootScope) {

	var numberOfPriosSelected = $rootScope.selectedPriorities.length; 
	var maxNumberOfPriosSelected = 5;
	
    $scope.selectPrio = function(index, prio) {
    	if (numberOfPriosSelected <= maxNumberOfPriosSelected) {
    		for(var i in $rootScope.selectedPriorities) {
    	        $rootScope.selectedPriorities[i].hideContent = true;
    	    }
    	    var newPrio = jQuery.extend(true, {}, prio);
    	    newPrio.origin = index;
    	    $rootScope.selectedPriorities.unshift(newPrio);  
    	} 
    	else {
    		// TODO: print error massage on screen
    	}
    }

    $scope.removeEntry = function(index, prio) {
      var newPrios = [];
      for(var i in $rootScope.selectedPriorities) {
        if(i == index) continue;
        newPrios.push($rootScope.selectedPriorities[i]);
      }
      $rootScope.selectedPriorities = newPrios;
    }

    $rootScope.selectedPriorities = [];

    $scope.save = function() {
      
      console.log('Save:', $rootScope.selectedPriorities);
      $.ajax({
        type: 'POST',
        contentType : 'application/json; charset=utf-8',
        url: '/ZhangProjectBackend/post.json',
        data: JSON.stringify($rootScope.selectedPriorities),
        success: function(response) {
          console.log('Response', response);
        },
        error: function(response) {
          console.error('Response', response);
        }
      });
    }

    $scope.possiblePriorities = [
      {
        type: 'SingleChoicePrio',
        title: 'Raumbeschaffenheit',
        options: ['breite','lange'],
        text: ['Ich bevorzuge ', ' Räume'],
        showCourses: true
      },
      {
        type: 'SingleChoicePrio',
        title: 'Unterrichtsbeginn',
        options: ['früher','später'],
        text: ['Innerhalb der von mir angegebenen Belegzeiten bevorzuge ich den Unterrichtsbeginn je', 'desto besser.'],
        showCourses: true
      },
      {
        type: 'SingleChoicePrio',
        title: 'Anzahl Veranstaltungen pro Tag',
        options: ['mehr Veranstaltungen pro Tag, weniger Tage die Woche','weniger Veranstaltungen pro Tag, mehr Tage die Woche'],
        text: ['Ich bevorzuge ', '.'],
        showCourses: false
      },
      {
        type: 'SimplePrio',
        title: 'Wöchentliche Veranstaltungen',
        text: 'Ich ziehe es vor die vierzehntägigen 4SWS meines Unterrichts in zwei wöchentliche Einzelveranstaltungen mit je 2SWS aufzuteilen.',
        showCourses: true
      },
      {
        type: 'ExcludeDayCombinationPrio',
        title: 'Tage ausschließen',
        text: ['Wenn ich am ',' unterrichte, möchte ich nicht am ',' unterrichten.'],
        showCourses: false
      },
      {
        type: 'ExcludeDayCombinationPrio',
        title: 'Uhrzeit ausschließen',
        text: ['Wenn ich am ',' um ',' unterrichte, möchte ich nicht am ',' um ',' unterrichten.'],
        showCourses: true
      },
      {
        type: 'ExcludeDayCombinationPrio',
        title: 'Tage kombinieren',
        text: ['Wenn ich am ',' unterrichte, möchte ich auch am ',' unterrichten.'],
        showCourses: true
      },
      {
        type: 'SingleChoicePrio',
        title: 'Pausen',
        options: ['1','2','3','4','5'],
        text: ['Ich möchte nach spätestens ',' aufeinanderfolgenden Vorlesungen eine längere Pause. Die Mittagspause zwischen 11:15 und 12:15 wird als längere Pause gezählt.'],
        showCourses: false
      },
      {
        type: 'SingleChoicePrio',
        title: 'Maximale Lehrtage pro Woche',
        options: ['1','2','3','4','5'],
        text: ['Ich möchte nicht mehr als ', ' Tage pro Woche an der Hochschule unterrichten.'],
        showCourses: false
      },
      {
        type: 'SingleChoicePrio',
        title: 'Maximale Anzahl aufeinanderfolgender Lehrtage',
        options: ['1','2','3','4','5'],
        text: ['Ich möchte pro Woche nicht mehr als ', ' Tage am Stück unterrichten.'],
        showCourses: false
      },
      {
        type: 'FreeTextInput',
        title: 'Weitere Sonderwünsche - wenn dringend notwendig',
        text: 'Ich hätte gerne noch: ',
        showCourses: true
      }
    ];
  });

  angular.module("zhang-app").directive('priority', function($rootScope) {
    return function(scope, element, attrs) {
      // console.log(element, attrs.priority, scope.prio);

      scope.prio.dayOne = [ "day1", "time1" ];
      scope.prio.dayTwo = [ "day2", "time2" ];

      // scope.prio.ExcludeDayCombinationPrio = [ {
      //   dayOne: [
      //     "day", "time"
      //   ],
      //   dayTwo: [
      //     "day", "time"
      //   ] }
      // ];

      scope.prio.course = "Alle Kurse";
      // scope.prio

      scope.change = function(selected) {
        scope.prio.option = selected;
      }
    }
  });
})();

$(document).ready(function() {

    $('#course-list-next').click(function(){
      $('.course-selector-wrapper').fadeOut()
    })
    $('#add-course').click(function(){
      $('.course-selector-wrapper').fadeIn()
    })

    $('.menu-trigger').click(function() {
      $(".menu").fadeToggle();
    });

    $(window).resize(function() {
      $(".menu").removeAttr('style');
    });

});
