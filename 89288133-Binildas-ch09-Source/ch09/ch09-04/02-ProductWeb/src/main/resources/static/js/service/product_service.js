'use strict';

App.factory('ProductService', ['$http', '$q', function($http, $q){

	return {
		
	     getProducts: function() {
			 return $http.get('../productsweb')
					.then(
							function(response){
								return response.data._embedded.products;
							}, 
							function(errResponse){
								console.error('Error while fetching application');
								return $q.reject(errResponse);
							}
					);
	         },
	         
	          addProduct: function(data) {
				 return $http.post('../productsweb',data)
						.then(
								function(response){
									console.log(response);
								   return response.data;
								}, 
								function(errResponse){
									console.error('Error while fetching application');
									return $q.reject(errResponse);
								}
						);
		         },
		        editProduct: function(data) {
					 return $http.put('../productsweb/'+data.productId,data)
							.then(
									function(response){
										console.log(response);
									   return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching application');
										return $q.reject(errResponse);
									}
							);
			      },
			      deleteProduct: function(productId) {
						 return $http.delete('../productsweb/'+productId)
								.then(
										function(response){
											console.log(response);
										   return response;
										}, 
										function(errResponse){
											console.error('Error while fetching application');
											return $q.reject(errResponse);
										}
								);
				      }	

	};

}]);
