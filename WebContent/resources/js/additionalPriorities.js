(function() {
  var app = angular.module("zhang-app").controller('additionalPrioController', function($scope, $rootScope) {

    $rootScope.additionalPriorities = [];

    $scope.selectPrio = function(index, prio) {

      for(var i in $rootScope.additionalPriorities) {
          $rootScope.additionalPriorities[i].hideContent = true;
      }

      var newPrio = jQuery.extend(true, {}, prio);
      newPrio.origin = index;

      $rootScope.additionalPriorities.push(newPrio);

    }

    $scope.save = function() {

        console.log($rootScope.additionalPriorities);
          $.ajax({
            type: 'POST',
            contentType : 'application/json; charset=utf-8',
            url: '/ZhangProjectBackend/postConfig.json',
            data: JSON.stringify($rootScope.additionalPriorities),
            success: function(response) {
              console.log('Responese', response);
              toastr.success('Die Daten wurden erfolgreich in der Datenbank gespeichert.');
            },
            error: function(response) {
              console.error('Response', response);
              toastr.error('Die Daten wurden leider nicht in der Datenbank gespeichert.');
            }
          });
    }

    $scope.possibleAdditionalPriorities = [
        {
            program: 'IMI-B',
            type: 'additionalPrio',
            props: [
              {
                title: 'MaxStunden',
                options: ['1','2', '3', '4', '5', '6', '7'],
                text: 'Maximale Stunden pro Tag: ',
                prios: ['hoch', 'mittel', 'niedrig']
              },
              {
                title: 'MaxTage',
                options: ['3','4'],
                text: 'Maximale Lehrtage pro Woche: ',
                prios: ['hoch', 'mittel', 'niedrig']

              },
              {
                title: 'MaxPausen',
                options: ['1','2', '3', '4'],
                text: 'Maximale zusammenhängende Pausen pro Tag: ',
                prios: ['hoch', 'mittel', 'niedrig']
              }
            ]
        },
        {
            program: 'IMI-M',
            type: 'additionalPrio',
            props: [
                {
                    title: 'MaxStunden',
                    options: ['1','2', '3', '4', '5', '6', '7'],
                    text: 'Maximale Stunden pro Tag: ',
                    prios: ['hoch', 'mittel', 'niedrig']
                },
                {
                    title: 'MaxTage',
                    options: ['3','4'],
                    text: 'Maximale Lehrtage pro Woche: ',
                    prios: ['hoch', 'mittel', 'niedrig']

                },
                {
                    title: 'MaxPausen',
                    options: ['1','2', '3', '4'],
                    text: 'Maximale zusammenhängende Pausen pro Tag: ',
                    prios: ['hoch', 'mittel', 'niedrig']
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