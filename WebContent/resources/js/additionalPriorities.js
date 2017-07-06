(function() {
  var app = angular.module("zhang-app").controller('additionalPrioController', function($scope, $rootScope) {

      // $scope.additionalPriorities = [
      //     {
      //         type: 'additionalPrio',
      //         title: 'MaxStunden',
      //         text: 'Maximale Stunden pro Tag: ',
      //     },
      //     {
      //         type: 'additionalPrio',
      //         title: 'MaxMinuspunkte',
      //         text: 'Maximale Minuspunkte: ',
      //
      //     },
      //     {
      //         type: 'additionalPrio',
      //         title: 'MaxPausen',
      //         text: 'Maximale Pausen pro Tag: ',
      //     }
      // ];
      $scope.additionalPriorities = [];
      $scope.selectPrio = function(index, prio) {
          var newPrio = jQuery.extend(true, {}, prio);
          newPrio.origin = index;
          $scope.additionalPriorities.push(newPrio);
      }

      // $scope.change = function(selected) {
      //     console.log(selected);
      //     $scope.additionalPriorities.options = selected;
      // }

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


      angular.module("zhang-app").directive('AddPriority', function($scope) {
          return function(scope, element, attrs) {

              scope.changeOption = function(selected) {
                  console.log(selected);
                  scope.prio.options = selected;
              }
              scope.changePrio = function(selected) {
                  console.log(selected);
                  scope.prio.prio = selected;
              }

          }
      });
  })();