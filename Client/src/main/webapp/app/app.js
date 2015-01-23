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