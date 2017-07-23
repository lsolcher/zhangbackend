(function() {
	var app = angular.module("zhang-app", []).controller(
			'courseController',
			function($scope, $rootScope) {

				$scope.search = ''
				$rootScope.name= firstName;
                $scope.getCourses = JSON.parse(slctCourses);
                $scope.initCourses = initCourses;

				if ( $scope.getCourses !== null ) {
                    $scope.selectedCourses = $scope.getCourses[0];
                    $scope.selectedCourses = $.map($scope.selectedCourses, function(el) { return el });
                } else {
                    $scope.selectedCourses = [];
				}

                for ( var i in $scope.selectedCourses ) {

                    if ($scope.selectedCourses[i]) {
                        $scope.selectedCourses[i].selected = true;
                    }

                    for( var j in $scope.initCourses) {
                    	if ( $scope.selectedCourses[i].name == $scope.initCourses[j].kurzname ) {
                            $scope.selectedCourses[i].sws = $scope.initCourses[j].sws;
                            $scope.selectedCourses[i].semester = $scope.initCourses[j].semester;
                            $scope.selectedCourses[i].kurzname = $scope.initCourses[j].kurzname;
						}
					}
                }

                //console.log('Selected Courses: ', $scope.selectedCourses);
                //console.log('Init Courses: ', $scope.initCourses);

                $scope.list = []
				$scope.selectedList = []

				$scope.selectVal = function(event) {
					$scope.search = this.course.kurzname
				}

				$scope.selectCourse = function(event) {
					// update the courselist in rootScope
					$rootScope.courseList = []
					for ( var i in $scope.list) {
						if ($scope.list[i].selected) {
							$rootScope.courseList.push($scope.list[i])
						}
					}
					// save in localstorage
                    $scope.selectedCourses = JSON
							.stringify($rootScope.courseList);
				}

				$rootScope.courseList = []

				try {
					$rootScope.courseList = $scope.selectedCourses
				} catch (e) {
					$rootScope.courseList = []
				}

				for ( var i in initCourses) {
					var temp = initCourses[i]
					temp.id = i
					// if selected apply
					for ( var j in $rootScope.courseList) {
						if ($rootScope.courseList[j].id == temp.id || $rootScope.courseList[j].courseID == temp.id)
							temp.selected = true
							temp.courseID = temp.id;
					}
					$scope.list.push(temp)
				}
				//$scope.list = initCourses
				//$scope.$apply()

				//localStorage.setItem("courselistlength", $rootScope.courseList.length);	// give list length to be able to set number of prios to be selected in priorities.js
			})
})()
