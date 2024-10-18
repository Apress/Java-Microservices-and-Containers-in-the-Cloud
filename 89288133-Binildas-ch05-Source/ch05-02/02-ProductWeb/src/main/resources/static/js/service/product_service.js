'use strict';

App.factory('ProductService', ['$http', '$q', function($http, $q){

	return {
		
	     getProducts: function() {
			 return $http.get('../productsweb/')
					.then(
							function(response){
								return response.data;
							}, 
							function(errResponse){
								console.error('Error while fetching application');
								return $q.reject(errResponse);
							}
					);
	         }
	};

}]);
