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

    $("select option[data-select='true']").attr("selected","selected");

});


// function lala() {
//     var url = window.location.href;
//     var getParams = new URLSearchParams(url);
//     for (var p of getParams) {
//     	  console.log(p);
//     	}
//     console.log(getParams.has("string") === true)
//     if( getParams.has("userExists") ) {
//     	console.log('BOOM ERROR');
//     } else if ( getParams.get("string") === "userExists" ) {
//     	console.log('BOOM');
//     }
// }

(function() {
    var app = angular.module("zhang-app").controller('renderData', function($scope) {

        $scope.data = initCourses;
        $scope.infoText = info;

        $scope.info = function () {
            $scope.infoWindow = true;
        }

        $scope.infoClose = function () {
            $scope.infoWindow = false;
        }

    });
})();

