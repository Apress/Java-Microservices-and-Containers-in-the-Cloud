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
				    	 self.products= data; 
				       },
  					function(errResponse){
  						console.error('Error while fetching applications');
  					}
		       );
          };
          
          $scope.openProductForm = function(){
        	  $scope.product= {};  
        	  $scope.isProductFormVisibe=true;	
        	  $scope.isNew=true;
          };
          
          $scope.submitProductForm = function() {
        	  if($scope.isNew==true){
        	  ProductService.addProduct($scope.product)
              .then(
				       function(data) {
				    	   self.refresh(); 
				    	   $scope.isProductFormVisibe=false;
				       },
  					function(errResponse){
  						console.error('Error while fetching applications');
  					}
		       );
        	  }else{
            	  ProductService.editProduct($scope.product)
                  .then(
    				       function(data) {
    				    	   self.refresh(); 
    				    	   $scope.isProductFormVisibe=false;
    				       },
      					function(errResponse){
      						console.error('Error while fetching applications');
      					}
    		       );
 
        		  
        	  }
          };
          
          
          $scope.openDeleteForm = function(product){
        	  $scope.product=product; 
          };
          
          $scope.deleteProduct = function(){
        	  ProductService.deleteProduct($scope.product.productId)
              .then(
				       function(data) {
				    	   self.refresh(); 
				       },
  					function(errResponse){
  						console.error('Error while fetching applications');
  					}
		       );
          };
          
          $scope.openEditForm = function(product){
        	  $scope.product=product; 
        	  $scope.isProductFormVisibe=true;
        	  $scope.isNew=false;
        	  
          };
          
          
        }]);
