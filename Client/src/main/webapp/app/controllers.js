var inspectionObjectControllers = angular.module('inspectionObjectControllers', []);

inspectionObjectControllers.controller('InspectionObjectListCtrl', ['$scope', '$http', 
  function ($scope, $http) {
	  $http.get(REST_BACKEND_URL + '/inspectionobject').success(function(data) {
		  $scope.inspectionObjects = data
	  })
	  $scope.orderProp = 'objectName';
	}]);

inspectionObjectControllers.controller('InspectionObjectDetailCtrl', ['$scope', '$routeParams',
                                                   function($scope, $routeParams) {
                                                     $scope.inspectionObjectId = $routeParams.inspectionObjectId;
                                                   }]);