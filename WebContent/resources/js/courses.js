(function() {
  var app = angular.module("zhang-app", []).controller('courseController', function($scope, $rootScope) {

    $scope.search = ''

    $scope.list = []
    $scope.selectedList = []

    $scope.selectVal = function(event) {
      $scope.search = this.course.kurzname
    }

    $scope.clickfick = function(event) {
      // update the courselist in rootScope
      $rootScope.courseList = []
      for(var i in $scope.list) {
        if($scope.list[i].selected) {
          $rootScope.courseList.push($scope.list[i])
        }
      }
      // save in localstorage
      localStorage.coursesjo = JSON.stringify($rootScope.courseList)
    }

    $rootScope.courseList = []

    $.ajax({
      url: '/resources/json/veranstaltungen.json',
      type: 'GET',
      success: function(res) {
        // read selected prios from localstorage and apply
        try {
          $rootScope.courseList = JSON.parse(localStorage.coursesjo)
        } catch(e) {
          $rootScope.courseList = []
        }
        for(var i in res) {
          var temp = res[i]
          temp.id = i
          // if selected apply
          for(var j in $rootScope.courseList) {
            if($rootScope.courseList[j].id == temp.id) temp.selected = true
          }
          $scope.list.push(temp)
        }
        $scope.list = res
        $scope.$apply()
      },
      error: function(res) {
        console.error(res)
      }
    })
  })
})()
