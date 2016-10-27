(function() {
  var app = angular.module("zhang-app", []).controller('prioController', function($scope) {
    // new value entered
    $scope.currentPriority = '';
    // list of displayed priorities
    $scope.priorities = [
      'keine Schlauchräume',
      'Labor benötigt',
      'keine Computer benötigt',
      'Blockunterricht unabdingbar',
      'Wenn abends Unterricht, dann nicht morgens am darauffolgenden Tag'
    ];
    // add a new element to the list
    $scope.addPriority = function(event) {
      if(event.keyCode != '13') return;
      var newEntry = $(event.target).val();
      if(newEntry.length < 1) return alert('Mindestens zwei Buchstaben');
      $scope.priorities.push(newEntry);
      $scope.currentPriority = '';
    }

    $('#priority-list').sortable({
      items: 'li.priority-entry',
      stop: function(event, ui) {
        // adjust internal list manually
        var elements = $('#priority-list li.priority-entry')
        var newPriorities = []
        elements.each(function(i) {
          newPriorities.push($(elements.get(i)).html())
        })
        $scope.priorities = newPriorities
      }
    })

  });
})();
