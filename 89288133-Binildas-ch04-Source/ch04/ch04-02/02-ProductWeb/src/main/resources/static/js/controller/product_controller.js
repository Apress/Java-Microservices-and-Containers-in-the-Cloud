'use strict';

App.controller('ProductController', ['$scope', 'ProductService', function($scope, ProductService) {
          var self = this;
          self.products=[];
          $scope.product = {};
          $scope.isProductFormVisibe=false;
          $scope.isNew=true;
          $scope.init = function () { 
        	 self.refresh();
          
          };
          
          self.refresh = function(){ 
        	  ProductService.getProducts()
              .then(
				       function(data) {
				    	 self.products= data.products; 
				       },
  					function(errResponse){
  						console.error('Error while fetching applications');
  					}
		       );
          };
          
        
          
          
        }]);
