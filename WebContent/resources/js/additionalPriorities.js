(function() {
  var app = angular.module("zhang-app").controller('additionalPrioController', function($scope, $rootScope) {

      $scope.selectPrio = function(index, prio) {

          for(var i in $rootScope.additionalPriorities) {
              $rootScope.additionalPriorities[i].hideContent = true;
          }

          var newPrio = jQuery.extend(true, {}, prio);
          newPrio.origin = index;

          $rootScope.additionalPriorities.push(newPrio);

      }

      //$rootScope.additionalPriorities = [];
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
      // $scope.selectPrio = function(index, prio) {
      //     var newPrio = jQuery.extend(true, {}, prio);
      //     newPrio.origin = index;
      //     $scope.additionalPriorities.push(newPrio);
      // }

      // $scope.change = function(selected) {
      //     console.log(selected);
      //     $scope.additionalPriorities.options = selected;
      // }

    // TODO: When Checkbox "checked" add Object to $rootScope.additionalPriorities

    $scope.save = function() {

        console.log($rootScope.additionalPriorities);
	      $.ajax({
	        type: 'POST',
	        contentType : 'application/json; charset=utf-8',
	        url: '/ZhangProjectBackend/postConfig.json',
	        data: JSON.stringify($rootScope.additionalPriorities),
	        success: function(response) {
	          console.log('Responese', response);
	          alert('Die Daten wurden erfolgreich in der Datenbank gespeichert.');
	        },
	        error: function(response) {
	          console.error('Response', response);
	        }
	      });
    }

    $scope.possibleAdditionalPriorities = [
        {
            program: 'IMI-B',
            info: [
              {
                type: 'additionalPrio',
                title: 'MaxStunden',
                options: ['1','2', '3', '4', '5', '6', '7'],
                text: 'Maximale Stunden pro Tag: ',
                prios: ['hoch', 'mittel', 'niedrig'],
              },
              {
                type: 'additionalPrio',
                title: 'MaxMinuspunkte',
                options: ['-3','-2', '-1'],
                text: 'Maximale Minuspunkte: ',
                prios: ['hoch', 'mittel', 'niedrig'],

              },
              {
                type: 'additionalPrio',
                title: 'MaxPausen',
                options: ['1','2', '3', '4'],
                text: 'Maximale Pausen pro Tag: ',
                prios: ['hoch', 'mittel', 'niedrig'],
              }
            ]
        },
        {
            program: 'IMI-M',
            info: [
                {
                    type: 'additionalPrio',
                    title: 'MaxStunden',
                    options: ['1','2', '3', '4', '5', '6', '7'],
                    text: 'Maximale Stunden pro Tag: ',
                    prios: ['hoch', 'mittel', 'niedrig'],
                },
                {
                    type: 'additionalPrio',
                    title: 'MaxMinuspunkte',
                    options: ['-3','-2', '-1'],
                    text: 'Maximale Minuspunkte: ',
                    prios: ['hoch', 'mittel', 'niedrig'],

                },
                {
                    type: 'additionalPrio',
                    title: 'MaxPausen',
                    options: ['1','2', '3', '4'],
                    text: 'Maximale Pausen pro Tag: ',
                    prios: ['hoch', 'mittel', 'niedrig'],
                }
            ]
        }
    ];
  });


      angular.module("zhang-app").directive('AddPriority', function($scope) {
          return function(scope, element, attrs) {

              scope.changeOption = function(selected) {
                  scope.prio.option = selected;
              }
              scope.changePrio = function(selected) {
                  scope.prio.prio = selected;
              }

          }
      });
  })();