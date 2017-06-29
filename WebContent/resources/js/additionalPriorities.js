(function() {
  var app = angular.module("zhang-app").controller('additionalPrioController', function($scope, $rootScope) {

    $rootScope.additionalPriorities = [
      {
        type: 'additionalPrio',
        title: 'MaxStunden',
        options: '3',
        text: 'Maximale Stunden pro Tag: ',
        prio: 'hoch'
      },
      {
        type: 'additionalPrio',
        title: 'MaxMinuspunkte',
        options: '2',
        text: 'Maximale Stunden pro Tag: ',
        prio: 'niedrig'
      },
      {
        type: 'additionalPrio',
        title: 'MaxPausen',
        options: '1',
        text: 'Maximale Stunden pro Tag: ',
        prio: 'mittel'
      }
    ];

    // TODO: When Checkbox "checked" add Object to $rootScope.additionalPriorities

    $scope.save = function() {

        console.log($rootScope.additionalPriorities);
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
        prio: ['hoch', 'mittel', 'niedrig']
      },
      {
        type: 'additionalPrio',
        title: 'MaxMinuspunkte',
        options: ['-3','-2', '-1'],
        text: 'Maximale Minuspunkte: ',
        prio: ['hoch', 'mittel', 'niedrig']
      },
      {
        type: 'additionalPrio',
        title: 'MaxPausen',
        options: ['1','2', '3', '4'],
        text: 'Maximale Pausen pro Tag: ',
        prio: ['hoch', 'mittel', 'niedrig']
      }
    ];
  });

})();
