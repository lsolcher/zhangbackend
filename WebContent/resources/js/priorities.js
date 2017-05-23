(function() {
  var app = angular.module("zhang-app").controller('prioController', function($scope, $rootScope) {

	var numberOfPriosSelected = 0; 
	var maxNumberOfPriosSelected = 10;
	
    $scope.selectPrio = function(index, prio) {
    	if (numberOfPriosSelected < maxNumberOfPriosSelected) {
    		// TODO:  if prios.showCourses == false -> nur einmal auswählbar machen (herausbekommen. ob schonmal hinzugefügt)
//    		if ($rootScope.selectedPriorities.FreeTextInput == ) {
	    		for(var i in $rootScope.selectedPriorities) {
	    	        $rootScope.selectedPriorities[i].hideContent = true;
	    	    }
	    	    var newPrio = jQuery.extend(true, {}, prio);
	    	    newPrio.origin = index;
	    	    $rootScope.selectedPriorities.unshift(newPrio);  
	    	    numberOfPriosSelected++;
//    		}    
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

    $scope.save = function() {
	      
	      
	    // validating selected prios TODO: -> only save prios when validated and okay
 
      	// TODO: check if prio inputs are empty
    	// TODO: options are not selected? 
  

//		var selectElementsValue = ($rootScope.selectedPriorities.select.options[0].value);
//      console.log(selectElementsValue);
    	
    	// TODO: textarea is empty? 
//      console.log($rootScope.selectedPriorities.FreeTextInput.value);
     
    
//      scope.prio.select;
  
  	
  	
    	         
//    try {	} catch { }
//	
//	  var conent = $rootScope.priority-conent ;
//    
//	   !$rootScope.selectedPriorities.option //.value // .select - not working
    	
//   $rootScope.selectedPriorities.dayOne.value == [0, 0]
//   $rootScope.selectedPriorities.dayTwo.value == [0, 0] 
//   !$rootScope.selectedPriorities.course.isSelected()
	
//	   $rootScope.selectedPriorities.ExcludeDayCombinationPrio
	    	
    // TODO: calendar is Selected (at least min, at most max) ?
	    	
	    	
	        
	// TODO: check if some of the inputs are impossible to combine
		
	
	
	
	// TODO: check for duplication on raumbeschaffenheit & 2sws wöchentlich

	      
	      
	      
	      

	      // if ( all fine ) { save() } else {print error message to screen}	  

//	      console.log('Save:', $rootScope.selectedPriorities);
	      
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
        text: ['Ich bevorzuge ', ' Räume'],		// TODO: kursabhängig: mehrfachauswahl <-> kursunabhängige: nur einfachauswahl erlauben // TODO: wenn showSourses == false -> nur 1x auswählbar
        showCourses: true						
      },
      {
        type: 'SingleChoicePrio',				// mehrfachauswahl
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
