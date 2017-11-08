var amplioApp = angular.module('amplioApp', ['ngRoute']);

// configure our routes
amplioApp.config(function($routeProvider, $locationProvider) {
    $routeProvider

        .when('/', {
            templateUrl : 'landing.html',
            controller  : 'mainController'
        })

        // route for the login page
        .when('/login', {
            templateUrl : 'login.html',
            controller  : 'loginController'
        })
    $locationProvider.html5Mode(true);
});

// create the controller and inject Angular's $scope
amplioApp.controller('mainController', function($scope) {
    $scope.message = 'Testing out the main controller message';
});

amplioApp.controller('loginController', function($scope) {
    $scope.message = 'The Login page controller.';
});
