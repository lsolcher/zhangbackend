(function() {
  var app = angular.module("zhang-app").controller('prioController', function($scope, $rootScope) {

	var numberOfPriosSelected = 0;
	var maxNumberOfPriosSelected = 10;
	var numberOfSelectedRooms = 0;
	var numberOfSelectedWeeklyLections = 0;
	var notSelectedBefore = true;

  $scope.updateCalendar = function(calendar, index){
    calendar[index] = (parseInt(calendar[index], 10) + 1);
    if (calendar[index] === 4) {
      calendar[index] = 0;
    }
  };

	$scope.selectPrio = function(index, prio) {

		notSelectedBefore = true;

    	// check if priority was added already
		for (var i in $rootScope.selectedPriorities) {
			if (prio.title == $rootScope.selectedPriorities[i].title) {
				notSelectedBefore = false;
			}
		}

    	for(var i in $rootScope.selectedPriorities) {
    		$rootScope.selectedPriorities[i].hideContent = true;
	    }

    	var numberOfCourses = localStorage.getItem("courselistlength");
    	console.log(numberOfCourses);

    	var newPrio = jQuery.extend(true, {}, prio);
	    newPrio.origin = index;
	    if ((numberOfPriosSelected < maxNumberOfPriosSelected) && (newPrio.showCourses == false) && (notSelectedBefore)) { // if showCourses == false -> nur 1x auswählbar

    		$rootScope.selectedPriorities.unshift(newPrio);
	    	numberOfPriosSelected++;
	    }
	    else if ((numberOfPriosSelected < maxNumberOfPriosSelected) && (newPrio.showCourses == true)) { // if showCourses == true -> nur (numberOfCourses)x auswählbar

	    	if ((newPrio.title == "Raumbeschaffenheit") && (numberOfSelectedRooms < numberOfCourses)) {
	    		$rootScope.selectedPriorities.unshift(newPrio);
	    		numberOfSelectedRooms++;
	    		numberOfPriosSelected++;
	    	}
	    	else if ((newPrio.title == "Wöchentliche Veranstaltungen") && (numberOfSelectedWeeklyLections < numberOfCourses)) {
    			$rootScope.selectedPriorities.unshift(newPrio);
    			numberOfSelectedWeeklyLections++;
    			numberOfPriosSelected++;
    		}
		}
    	else {
    		// TODO: show error massage on website
    		console.log("You can only select 10 contraints all in all - one of each category that is not course specific; the course specific constraints each per course")
    	}
    }

    $scope.removeEntry = function(index, prio) {
      var newPrios = [];
      for(var i in $rootScope.selectedPriorities) {
        if(i == index) continue;
        newPrios.push($rootScope.selectedPriorities[i]);
      }
      $rootScope.selectedPriorities = newPrios;

      numberOfPriosSelected--;
      if (newPrio.title == "Raumbeschaffenheit") numberOfSelectedRooms--;
  	  else if (newPrio.title == "Wöchentliche Veranstaltungen") numberOfSelectedWeeklyLections--;
    }

    $rootScope.selectedPriorities = [];
    $rootScope.calendar = [ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1];
//    $rootScope.acceptedPriorities = [];			//TODO

    $scope.save = function() {

    	// check all the selected constraints (prios) for validating issues
    	// validating selected constraints -> only save prios/constraints when validated and okay
     	var noEmptyInputElements = true;
    	var noImpossibleCombinations = true;
    	var noDublication = true;
    	var calendarIsNotEmpty = true;

    	for (var i in $rootScope.selectedPriorities) {

	    	// check if any priority text areas or constraint selects are empty
    		// check if any priority text area is empty
	   	    if ($rootScope.selectedPriorities[i].type == "FreeTextInput") {
	    		if (document.getElementById("freeTextWish").value == "")
	    			noEmptyInputElements = false;
	    	}
	   	    else if (($rootScope.selectedPriorities[i].type == "ExcludeDayCombinationPrio") && ($rootScope.selectedPriorities[i].title == "Uhrzeit ausschließen")) {
	   	    	// check if any constraint selects are empty
	   	    	if (($rootScope.selectedPriorities[i].timeOne == undefined) || ($rootScope.selectedPriorities[i].timeTwo == undefined)
		   	    		|| ($rootScope.selectedPriorities[i].dayOne == undefined) || ($rootScope.selectedPriorities[i].dayTwo == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }
	   	    else if ($rootScope.selectedPriorities[i].type == "ExcludeDayCombinationPrio") {
	   	    	// check if any constraint selects are empty
	   	    	if (($rootScope.selectedPriorities[i].dayOne == undefined) || ($rootScope.selectedPriorities[i].dayTwo == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }
	   	    // check for empty selects, duplications and impossible combination in "raumbeschaffenheit" and "wöchentliche veranstaltungen"
	   	    else if (($rootScope.selectedPriorities[i].title == "Raumbeschaffenheit") || ($rootScope.selectedPriorities[i].title == "Wöchentliche Veranstaltungen")) {
	   	    	// check if any constraint selects are empty
	   	    	if (($rootScope.selectedPriorities[i].option == undefined) || ($rootScope.selectedPriorities[i].course == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
		   	    // check for duplication
				for (var j in $rootScope.selectedPriorities) {

					// check if same options were selected (for all courses)
					if (($rootScope.selectedPriorities[i].option == $rootScope.selectedPriorities[j].option) && ($rootScope.selectedPriorities[i].course == $rootScope.selectedPriorities[j].course)) {
						noDublication = false;
					}

			    	// check if some of the inputs are impossible to combine: check if a course was selected with different options
					if ($rootScope.selectedPriorities[i].course == $rootScope.selectedPriorities[j].course) {
						if ($rootScope.selectedPriorities[i].option != $rootScope.selectedPriorities[j].option)
							noImpossibleCombinations = false
					}
				}
	   	    }
	   	    else { // alle singleChoicePrios, deren titel nicht "Raumbeschaffenheit" ist ( = die keine kursauswahl haben) {
	   	    	// check if any constraint selects are empty
	   	    	if (($rootScope.selectedPriorities[i].option == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }

	   	    if (($rootScope.selectedPriorities[i].type == "ExcludeDayCombinationPrio")) {

	   	    	// check for duplication on "ExcludeDayCombinationPrio"
	   	    	// ExcludeDayCombinationPrio: wenn an tag X , <-> dann AUCH an tag X
	   	    	if ($rootScope.selectedPriorities[i].title == "Tage kombinieren") {
	   	    		if ($rootScope.selectedPriorities[i].dayOne == $rootScope.selectedPriorities[i].dayTwo) {
	   	    			noDublication = false;
	   	    		}
	   	    	}

		   	    for (var j in $rootScope.selectedPriorities) {

		   	    	// check for duplication on "ExcludeDayCombinationPrio"
		   	    	// ExcludeDayCombinationPrio: wenn an tag X , dann NICHT an tag Y <-> wenn an tag X um ... , dann NICHT an tag Y um ...
		   	    	if ((($rootScope.selectedPriorities[i].title == "Uhrzeit ausschließen") && ($rootScope.selectedPriorities[j].title == "Tage ausschließen"))) {

		   	    		if ((($rootScope.selectedPriorities[i].dayOne == $rootScope.selectedPriorities[j].dayOne) && ($rootScope.selectedPriorities[i].dayTwo == $rootScope.selectedPriorities[j].dayTwo))) {
//		   	    				|| (($rootScope.selectedPriorities[j].dayOne == $rootScope.selectedPriorities[i].dayOne) && ($rootScope.selectedPriorities[j].dayTwo == $rootScope.selectedPriorities[i].dayTwo))) {
			   	    		noDublication = false;
			   	    	}//todo: to be verified
		   	    	}

			   	    // check if some of the inputs are impossible to combine that are "ExcludeDayCombinationPrio"

		   	    	// ExcludeDayCombinationPrio: wenn an tag X , <-> dann NICHT an tag X
		   	    	if ($rootScope.selectedPriorities[i].title == "Tage ausschließen") {
		   	    		if ($rootScope.selectedPriorities[i].dayOne == $rootScope.selectedPriorities[i].dayTwo) {
		   	    			noImpossibleCombinations = false;
		   	    		}
		   	    	}

			   	    // ExcludeDayCombinationPrio: wenn an tag X , dann AUCH an tag Y <-> wenn an tag X , dann NICHT an tag Y
		   	    	if ((($rootScope.selectedPriorities[i].title == "Tage kombinieren") && ($rootScope.selectedPriorities[j].title == "Tage ausschließen"))) {

		   	    		if ((($rootScope.selectedPriorities[i].dayOne == $rootScope.selectedPriorities[j].dayOne) && ($rootScope.selectedPriorities[i].dayTwo == $rootScope.selectedPriorities[j].dayTwo))) {
		   	    				noImpossibleCombinations = false;
			   	    	}
		   	    	}
		   	    	// ExcludeDayCombinationPrio: wenn an tag X um Zeit Y , dann NICHT an tag X um Zeit Y
		   	    	if ($rootScope.selectedPriorities[i].title == "Uhrzeit ausschließen") {
		   	    		if (($rootScope.selectedPriorities[i].dayOne == $rootScope.selectedPriorities[i].dayTwo) && ($rootScope.selectedPriorities[i].timeOne == $rootScope.selectedPriorities[i].timeTwo)) {
		   	    			noImpossibleCombinations = false;
		   	    		}//todo: to be verified
		   	    	}
		   	    }
		   	 }
    	}

    	// check if calendar is Selected at all 		// TODO: check if calendar is Selected: at least = min, at most = max
    	var calendarInput = $('.calendar-input');
    	var numberOfValuesInCalendarOtherThan1 = 0;

		for (var i = 0; i < calendarInput.length; i++) {
			if (!(calendarInput[i].value == 1)) {	// if all input has prio=="1" -> no (other) prio is selected
				calendarIsNotEmpty = false;
				numberOfValuesInCalendarOtherThan1++;
		    }
		}

	    // if all fine -> save
    	if (noEmptyInputElements && noImpossibleCombinations && noDublication && calendarIsNotEmpty) {

//	      console.log('Save:', $rootScope.selectedPriorities);	// TODO: change to acceptedPriorities
        console.log('calendar:', $scope.calendar);
        console.log('Save:', $rootScope.selectedPriorities, $scope.calendar);

	      $.ajax({
	        type: 'POST',
	        contentType : 'application/json; charset=utf-8',
	        url: '/ZhangProjectBackend/post.json',
	        data: JSON.stringify($rootScope.selectedPriorities),	// TODO: change to acceptedPriorities
	        success: function(response) {
	          console.log('Response', response);
	        },
	        error: function(response) {
	          console.error('Response', response);
	        }
	      });
    	}
    	else if (noEmptyInputElements == false) {
    		// TODO: show error message telling the user what needs to be changed
    		console.log ("Please make sure that you chose any information in all of the selected constraints! ");
    	}
    	else if (noImpossibleCombinations == false) {
    		// TODO: show error message
   		 	console.log ("Please make sure that you didn't enter options that are impossible to combine! ");
    	}
    	else if (noDublication == false) {
    		// TODO: show error message
      		console.log ("Please make sure that you didn't enter duplicate information! ");
       	}
    	else { //if (calendarIsNotEmpty == false) {
    		// TODO: show error message
      		console.log ("Please make sure that you chose at least ... of the preferred time slots in the calendar! ");
      	}
    }

    $scope.possiblePriorities = [
      {
        type: 'SingleChoicePrio',
        title: 'Raumbeschaffenheit',
        options: ['breite','lange'],
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
        type: 'ExcludeDayCombinationPrio',		// einfachauswahl
        title: 'Tage kombinieren',
        text: ['Wenn ich am ',' unterrichte, möchte ich auch am ',' unterrichten.'],
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
        type: 'SingleChoicePrio',				// einfachauswahl
        title: 'Maximale Anzahl aufeinanderfolgender Lehrtage',
        options: ['1','2','3','4','5'],
        text: ['Ich möchte pro Woche nicht mehr als ', ' Tage am Stück unterrichten.'],
        showCourses: false
      },
      {
        type: 'FreeTextInput',					// einfachauswahl
        title: 'Weitere Sonderwünsche - wenn dringend notwendig',
        text: 'Ich hätte gerne noch: ',
        showCourses: false
      }
    ];
  });

  angular.module("zhang-app").directive('priority', function($rootScope) {
    return function(scope, element, attrs) {

      scope.prio.dayOne = "0";
      scope.prio.dayTwo = "0";
      scope.prio.timeOne = "0";
      scope.prio.timeTwo = "0";
      scope.prio.course = "Alle Kurse";

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
