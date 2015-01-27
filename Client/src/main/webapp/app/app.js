var restBackendUrl = 'http://inspection-framework.herokuapp.com'
var inspectionObjectRetriever = angular.module('inspectionObjectRetriever', []);

inspectionObjectRetriever.controller('InspectionObjectListCtrl', function ($scope, $http) {
  $http.get(restBackendUrl + '/inspectionobject').success(function(data) {
	  $scope.inspectionObjects = data
  })
});


(function(){
    var app = angular.module('store', []);
    
    app.controller('StoreController', function(){
        this.products = gems;
    });
    
    var gems = [
        {
            name: 'Dodecahedron',
            price: 2.95,
            description: '...',
            canPurchase: true,
            soldOut: false
        },
        {
            name: 'Gem 2',
            price: 4.95,
            description: '...',
            canPurchase: true,
            soldOut: false
        }
    ];
})();