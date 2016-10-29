'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('View_Databases', function ($scope,  $http, $sce, $compile, myservice) {

	$scope.database_name_table = "";
	$scope.tabldata = [];
	$scope.myservice = myservice;
  
	$scope.init  = function() {
        // Simple GET request example:
		$http({
		  method: 'GET',
		  url: 'http://localhost:8084/CodeMage/database/1'
		}).then(function successCallback(response) {
			$scope.tabldata = response.data;
			console.log(response.data);
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			)
		});
    };
	
	$scope.showUser = function(user){
		console.log(user);
		myservice.dbname = user;
	};
  
});
 
 
 
 /*angular.module('dashyAngular').controller('View_Databases', function ($scope,  $http, $sce, $compile) {
  
	$scope.database_name_table = "";
  
	$scope.init  = function() {
        // Simple GET request example:
		$http({
		  method: 'GET',
		  url: 'http://localhost:8084/CodeMage/database/1'
		}).then(function successCallback(response) {
			
			console.log(response.data);
			var tblBody = "";
			var i = 0;
			for(i = 0; i<response.data.length; i++){
				tblBody = tblBody + "<tr><td><a ui-sref='View_Tables'>"+response.data[i].user_dbname+"</a></td></tr>";
			} 
			
			$scope.database_name_table = $sce.trustAsHtml(tblBody);
			
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			)
		});
    };
  
});*/