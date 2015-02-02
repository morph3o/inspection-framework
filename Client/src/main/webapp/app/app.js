var inspectionFrameworkApp = angular.module('inspectionFrameworkApp', [
	                                                                 'ngRoute',
	                                                                 'inspectionObjectControllers'
	                                                               ]);
inspectionFrameworkApp.config(['$routeProvider', '$locationProvider',
		                    function($routeProvider, $locationProvider) {
		                      $routeProvider.
		                        when('/inspectionObjects', {
		                          templateUrl: 'views/inspection-object-list.html',
		                          controller: 'InspectionObjectListCtrl'
		                        }).
		                        when('/inspectionObjects/:inspectionObjectId', {
		                          templateUrl: 'views/inspection-object-detail.html',
		                          controller: 'InspectionObjectDetailCtrl'
		                        }).
		                        otherwise({
		                          redirectTo: '/inspectionObjects'
		                        });
		                    }]);

