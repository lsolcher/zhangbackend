(function() {
  var app = angular.module("zhang-app").controller('prioController', function($scope, $rootScope) {

	var numberOfPriosSelected = 0; 
	var maxNumberOfPriosSelected = 10;
	
	var numberOfSelectedRooms = 0;
	var numberOfSelectedWeeklyLections = 0;
	
	var notSelectedBefore = true;
	
	$scope.selectPrio = function(index, prio) {
    	// TODO: check all the priorities for validating issues
    	
    	notSelectedBefore = true;
    	
    	// check if priority was added already
		for (var i in $rootScope.selectedPriorities) {
			if (prio.title == $rootScope.selectedPriorities[i].title) {
				notSelectedBefore = false;
			}
		}
		
    	for(var i in $rootScope.selectedPriorities) {
    		$rootScope.selectedPriorities[i].hideContent = true;
//	    	TODO: selectedPriorities[i].title -> notSelectedBefore[prio] = false
	    }

    	var numberOfCourses = localStorage.getItem("courselistlength");
    	
    	console.log(numberOfCourses);
    	
    	
    	var newPrio = jQuery.extend(true, {}, prio);
	    newPrio.origin = index;
	    if ((numberOfPriosSelected < maxNumberOfPriosSelected) 
	    	&& (((newPrio.showCourses == true) && (numberOfSelectedRooms < numberOfCourses) && (numberOfSelectedWeeklyLections < numberOfCourses))// if showCourses == true -> nur (numberOfCourses)x auswählbar
	    	|| ((newPrio.showCourses == false) && (notSelectedBefore)))) {	// if showCourses == false -> nur 1x auswählbar
	  	  
	    	
			$rootScope.selectedPriorities.unshift(newPrio);  
	    	numberOfPriosSelected++;
	    	if (newPrio.title == "Raumbeschaffenheit") numberOfSelectedRooms++;
	    	else if (newPrio.title == "Wöchentliche Veranstaltungen") numberOfSelectedWeeklyLections++;
	    } 
    	else {
    		// TODO: print error massage on screen "You can only select 10 wishes"
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
    }

    $rootScope.selectedPriorities = [];
//    $rootScope.alreadySelectedPriorities = [];	//TODO
//    $rootScope.acceptedPriorities = [];			//TODO

    $scope.save = function() {
	    // validating selected prios -> TODO: only save prios when validated and okay
 
    	var noEmptyInputElements = true;
    	var noImpossibleCombinations = true;
    	var noDublication = true;
    	var calendarIsNotEmpty = true;
    	
    	for (var i in $rootScope.selectedPriorities) {
    		
	    	// check if any priority text areas or selects are empty
	   	    if ($rootScope.selectedPriorities[i].type == "FreeTextInput") {
	    		if (document.getElementById("freeTextWish").value == "") 
	    			noEmptyInputElements = false; //console.log("text area is empty");
	    	}
	   	    else if (($rootScope.selectedPriorities[i].type == "ExcludeDayCombinationPrio") && ($rootScope.selectedPriorities[i].title == "Uhrzeit ausschließen")) {
		   	    if (($rootScope.selectedPriorities[i].timeOne == undefined) || ($rootScope.selectedPriorities[i].timeTwo == undefined) 
		   	    		|| ($rootScope.selectedPriorities[i].dayOne == undefined) || ($rootScope.selectedPriorities[i].dayTwo == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }
	   	    else if (($rootScope.selectedPriorities[i].type == "ExcludeDayCombinationPrio")) {
		   	    if (($rootScope.selectedPriorities[i].dayOne == undefined) || ($rootScope.selectedPriorities[i].dayTwo == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }
	   	    else if (($rootScope.selectedPriorities[i].title == "Raumbeschaffenheit") && ($rootScope.selectedPriorities[i].title == "Wöchentliche Veranstaltungen")) {
		   	    if (($rootScope.selectedPriorities[i].option == undefined) || ($rootScope.selectedPriorities[i].course == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }
	   	    else { // alle singleChoicePrios, deren titel nicht "Raumbeschaffenheit" ist ( = die keine kursauswahl haben) {
		   	    if (($rootScope.selectedPriorities[i].option == undefined)) {
		   	    	noEmptyInputElements = false;
		   	    }
	   	    }
	   	    

	    	console.log("title: " + $rootScope.selectedPriorities[i].title);
	    	console.log("option: " + $rootScope.selectedPriorities[i].option); 
	    	console.log("course: " + $rootScope.selectedPriorities[i].course); 
//	    	console.log("course: " + $rootScope.selectedPriorities[i].dayOne); 
//	    	console.log("course: " + $rootScope.selectedPriorities[i].dayTwo); 
//	    	$rootScope.selectedPriorities[i].timeOne
//	    	$rootScope.selectedPriorities[i].timeTwo
	    	
	    	
//	   		$rootScope.selectedPriorities[i].dayOne
//	   		$rootScope.selectedPriorities[i].dayTwo.value == [0, 0] 
//	     	!$rootScope.selectedPriorities[i].course.isSelected()
			
			for (var j in $rootScope.selectedPriorities) {
				// TODO: check for duplication on 'Raumbeschaffenheit' & 'Wöchentliche Veranstaltung'
				if ($rootScope.selectedPriorities[i].title == "Raumbeschaffenheit") {
					
					
					// TODO check all selected options and make sure they're not the same
					for (var k = 0; k < 7; k++ ) {
						for (var m = 0; m < 7; m++ ) {
							
							if ($rootScope.selectedPriorities[i].option == $rootScope.selectedPriorities[j].option)
								noDublication = false;
						}
					}
					
					
					// TODO: check if same option was selected (for all courses)
					// 0 - 0 in same course
					// 1 - 1 in same course
						
				}
				
	//			if ($rootScope.selectedPriorities[i].title == "Wöchentliche Veranstaltungen") {
					// 0 - 0 -> same course
					// 1 - 1 
					// ... (number of courses)
	//			}
					// else noDublication = false;
				
		    	
		    	
		    	// TODO: check if some of the inputs are impossible to combine
	//			console.log("option: " + $rootScope.selectedPriorities[i].option.value); 
					// else noImpossibleCombinations = false
		    	
		    	
		    	
			}
	    	    
    	}

    	// TODO: check if calendar is Selected (any, at least min, at most max) ?
//    	var calendarInput = document.getElementsByClass('calendar-input'); //$('.option-choice a')
//
//		for(var i = 0; i < calendarInput.length; i++) { 
//			if (calendarInput[i].value ) {	// TODO: if all calendarInput[i]:prio has the prio=="1" -> no (other) prio selected
//				calendarIsNotEmpty = false;
//		    }
//		}
	      
        
//      try {	} catch { }
//  	$rootScope.selectedPriorities.ExcludeDayCombinationPrio
	      
	      

	    // if all fine -> save	  
    	if (noEmptyInputElements && noImpossibleCombinations && noDublication && calendarIsNotEmpty) {
    		
//	      console.log('Save:', $rootScope.selectedPriorities);	// TODO: change to acceptedPriorities
	      
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
    	else {
    		// TODO: error message telling the user what needs to be changed - like "please check that  you choose any of the options..."
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
      // console.log(element, attrs.priority, scope.prio);

      scope.prio.dayOne = "0";
      scope.prio.dayTwo = "0";
      scope.prio.timeOne = "0";
      scope.prio.timeTwo = "0";

//      console.log(scope.prio.dayOne + " " + scope.prio.dayTwo);

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
