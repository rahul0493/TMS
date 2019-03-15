var app = angular.module('tmsApp',['ui.router','ngStorage','ngRoute','ui.bootstrap']);
//ui.bootstrap
app.constant('urls', {
    BASE: 'http://localhost:9090/tms',
    USER_SERVICE_API : 'http://localhost:9090/tms/api/user/'
});

app.config(function($routeProvider) {
	  $routeProvider

	  .when('/', {
	    templateUrl : '	home.jsp',
	   
	  })

	  .when('/tmsApp/tms/userPage', {
	    templateUrl : 'userPage.jsp',
	    controller  : 'UserController'
	  })

	  .when('/tmsApp/tms/monthlyRequest', {
	    templateUrl : 'monthlyRequest.jsp',
	    controller  : 'MonthlyRequestService'
	  })

	  .otherwise({redirectTo: '/'});
	});
/*app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: '/tms/list',
                controller:'UserController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, UserService) {
                        console.log('Load all users');
                        var deferred = $q.defer();
                        UserService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

*/