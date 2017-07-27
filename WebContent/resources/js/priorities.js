(function() {
  var app = angular.module("zhang-app").controller('prioController', function($scope, $rootScope) {

    $scope.possiblePriorities = [
          {
              type: 'SingleChoicePrio',
              title: 'Raumbeschaffenheit',
              options: ['Seminarraum','IT-Labor'],
              text: ['Ich bevorzuge ', ' Räume'],		// mehrfachauswahl - kursabhängig <-> kursunabhängige: nur einfachauswahl erlauben
              showCourses: true
          },
          {
              type: 'SingleChoicePrio',				// einfachauswahl
              title: 'Unterrichtsbeginn',
              options: ['früher','später'],
              text: ['Innerhalb der von mir angegebenen Belegzeiten bevorzuge ich den Unterrichtsbeginn je', 'desto besser.'],
              showCourses: false
          },
          {
              type: 'ExcludeDayCombinationPrio',		// einfachauswahl
              title: 'Tage kombinieren',
              text: ['Wenn ich am ',' unterrichte, möchte ich auch am ',' unterrichten.'],
              showCourses: false
          },
          {
              type: 'SingleChoicePrio',				// einfachauswahl
              title: 'Anzahl Veranstaltungen pro Tag',
              options: ['mehr Veranstaltungen pro Tag, weniger Tage die Woche','weniger Veranstaltungen pro Tag, mehr Tage die Woche'],
              text: ['Ich bevorzuge ', '.'],
              showCourses: false
          },
          {
              type: 'SimplePrio',						// mehrfachauswahl
              title: 'Wöchentliche Veranstaltungen',
              text: 'Ich ziehe es vor die vierzehntägigen 4SWS meines Unterrichts in zwei wöchentliche Einzelveranstaltungen mit je 2SWS aufzuteilen.',
              showCourses: true
          },
          {
              type: 'SingleChoicePrio',				// einfachauswahl
              title: 'Maximale Anzahl aufeinanderfolgender Lehrtage',
              options: ['1','2','3','4','5'],
              text: ['Ich möchte pro Woche nicht mehr als ', ' Tage am Stück unterrichten.'],
              showCourses: false
          },
          {
              type: 'ExcludeDayCombinationPrio',		// einfachauswahl
              title: 'Tage ausschließen',
              text: ['Wenn ich am ',' unterrichte, möchte ich nicht am ',' unterrichten.'],
              showCourses: false
          },
          {
              type: 'ExcludeDayCombinationPrio',		// einfachauswahl
              title: 'Uhrzeit ausschließen',
              text: ['Wenn ich am ',' um ',' unterrichte, möchte ich nicht am ',' um ',' unterrichten.'],
              showCourses: false
          },
          {
              type: 'SingleChoicePrio',				// einfachauswahl
              title: 'Pausen',
              options: ['1','2','3','4','5'],
              text: ['Ich möchte nach spätestens ',' aufeinanderfolgenden Vorlesungen eine längere Pause. Die Mittagspause zwischen 11:15 und 12:15 wird als längere Pause gezählt.'],
              showCourses: false
          },
          {
              type: 'SingleChoicePrio',				// einfachauswahl
              title: 'Maximale Lehrtage pro Woche',
              options: ['1','2','3','4','5'],
              text: ['Ich möchte nicht mehr als ', ' Tage pro Woche an der Hochschule unterrichten.'],
              showCourses: false
          },
          {
              type: 'FreeTextInput',					// einfachauswahl
              title: 'Weitere Sonderwünsche - wenn dringend notwendig',
              text: 'Ich hätte gerne noch: ',
              showCourses: false
          }
      ];

    $rootScope.calendar = [ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1];
    $rootScope.selectedPriorities = [ { type: 'Courses', courses: $rootScope.courseList}, { type: 'Schedule', calendar: $scope.calendar} ];

    slctPrios = slctPrios.replace(/\t+/g, "");

    var lala = JSON.parse(slctPrios);

    if ( lala !== null ) {
        $rootScope.slctPrios = lala[0];

        for( i in $rootScope.slctPrios) {
            if ( $rootScope.slctPrios[i].schedule ) {
                $rootScope.calendar = $rootScope.slctPrios[i].schedule;
            } else {
                for( j in $scope.possiblePriorities ) {
                    if ( $rootScope.slctPrios[i].name == $scope.possiblePriorities[j].title ) {
                        
                        $rootScope.slctPrios[i].option += "";
                        $rootScope.slctPrios[i].type = $scope.possiblePriorities[j].type;
                        $rootScope.slctPrios[i].text = $scope.possiblePriorities[j].text;
                        $rootScope.slctPrios[i].showCourses = $scope.possiblePriorities[j].showCourses;
                        $rootScope.slctPrios[i].title = $scope.possiblePriorities[j].title;
                        $rootScope.slctPrios[i].options = $scope.possiblePriorities[j].options;

                    }
                }

                $rootScope.selectedPriorities.unshift($rootScope.slctPrios[i])
            }
        }

    }



	console.log('Prios from Mongo: ', $rootScope.slctPrios );
    console.log('Selected Prios: ', $rootScope.selectedPriorities );

    $scope.updateCalendar = function(calendar, index){
        calendar[index] = (parseInt(calendar[index], 10) + 1);
        if (calendar[index] === 4) {
          calendar[index] = 0;
        }
    };

	$scope.selectPrio = function(index, prio) {

        for(var i in $rootScope.selectedPriorities) {
            $rootScope.selectedPriorities[i].hideContent = true;
        }

        var newPrio = jQuery.extend(true, {}, prio);
        newPrio.origin = index;

        $rootScope.selectedPriorities.unshift(newPrio);
        toastr.info('Die Prios wurde zur Liste hinzugefügt!');

    }

    $scope.removeEntry = function(index, prio) {

      var newPrios = [];
      for(var i in $rootScope.selectedPriorities) {
        if(i == index) continue;
        newPrios.push($rootScope.selectedPriorities[i]);
      }
      $rootScope.selectedPriorities = newPrios;
      toastr.info('Die Prios wurde aus der Liste entfernt!');

    }

    $scope.save = function() {

        var lastElement = $rootScope.selectedPriorities.length - 1;

        if ($rootScope.selectedPriorities[lastElement].type != 'Schedule') {
            $rootScope.selectedPriorities.push( { type: 'Schedule', calendar: $scope.calendar} );
        } else if ($rootScope.selectedPriorities[lastElement-1].type != 'Courses') {
            $rootScope.selectedPriorities.push( { type: 'Courses', courses: $rootScope.courseList} );
        } else {
            $rootScope.selectedPriorities[lastElement] = { type: 'Schedule', calendar: $scope.calendar};
            $rootScope.selectedPriorities[lastElement-1] = { type: 'Courses', courses: $rootScope.courseList};
        }

        console.log('Before Submit: ', $rootScope.selectedPriorities);
	      $.ajax({
	        type: 'POST',
	        contentType : 'application/json; charset=utf-8',
	        url: '/ZhangProjectBackend/post.json',
	        data: JSON.stringify($rootScope.selectedPriorities),
	        success: function(response) {
	          console.log('Response', response);
              toastr.success('Die Prios wurden erfolgreich gespeichert!');
	        },
	        error: function(response) {
	          console.error('Response', response);
              toastr.error('Die Prios wurden leider nicht gespeichert!');
	        }
	      });

    }

  });

  angular.module("zhang-app").directive('priority', function($rootScope) {
    return function(scope, element, attrs) {

      scope.prio.dayOne = "0";
      scope.prio.dayTwo = "0";
      scope.prio.timeOne = "0";
      scope.prio.timeTwo = "0";
      scope.prio.course = "Alle Kurse";

      scope.change = function(selected){}
      scope.changeCourse = function(selected) {
        scope.prio.course = selected;
      }
    }
  });
})();
