(function() {
  var app = angular.module("zhang-app").controller('additionalPrioController', function($scope, $rootScope) {

/*      $scope.selectAdditionalPrio = function(index, prio) {
          for(var i in $rootScope.additionalPriorities) {
              $rootScope.additionalPriorities[i].hideContent = true;
          }
          var newPrio = jQuery.extend(true, {}, prio);
          newPrio.origin = index;
          $rootScope.additionalPriorities.push(newPrio);
      }*/

      $rootScope.additionalPriorities = [
          {
              type: 'additionalPrio',
              title: 'MaxStunden',
              options: '3',
              text: 'Maximale Stunden pro Tag: ',
              prio: 'hoch',
              program: 'IMI-B'
          },
          {
              type: 'additionalPrio',
              title: 'MaxMinuspunkte',
              options: '2',
              text: 'Maximale Stunden pro Tag: ',
              prio: 'niedrig',
              program: 'IMI-B'
          },
          {
              type: 'additionalPrio',
              title: 'MaxPausen',
              options: '1',
              text: 'Maximale Stunden pro Tag: ',
              prio: 'mittel',
              program: 'IMI-B'
          }
      ];
      //$scope.additionalPriorities = [];


    // TODO: When Checkbox "checked" add Object to $rootScope.additionalPriorities

    $scope.save = function() {

        console.log($scope.additionalPriorities);
	      $.ajax({
	        type: 'POST',
	        contentType : 'application/json; charset=utf-8',
	        url: '/ZhangProjectBackend/postConfig.json',
	        data: JSON.stringify($rootScope.additionalPriorities),
	        success: function(response) {
	          console.log('Response', response);
	        },
	        error: function(response) {
	          console.error('Response', response);
	        }
	      });
    }

    $scope.possibleAdditionalPriorities = [
      {
        type: 'additionalPrio',
        title: 'MaxStunden',
        options: ['1','2', '3', '4', '5', '6', '7'],
        text: 'Maximale Stunden pro Tag: ',
        prio: ['hoch', 'mittel', 'niedrig'],
        program: ['IMI-B', 'IMI-M']
      },
      {
        type: 'additionalPrio',
        title: 'MaxMinuspunkte',
        options: ['-3','-2', '-1'],
        text: 'Maximale Minuspunkte: ',
        prio: ['hoch', 'mittel', 'niedrig'],
        program: ['IMI-B', 'IMI-M']

      },
      {
        type: 'additionalPrio',
        title: 'MaxPausen',
        options: ['1','2', '3', '4'],
        text: 'Maximale Pausen pro Tag: ',
        prio: ['hoch', 'mittel', 'niedrig'],
        program: ['IMI-B', 'IMI-M']
      }
    ];
  });


    /*      angular.module("zhang-app").directive('AddPriority', function($rootScope) {
              return function(scope, element, attrs) {

                  scope.prio.course = "Alle Kurse";

                  scope.change1 = function(selected) {
                      scope.prio.option = selected;
                  }
              }
      });*/
  })();