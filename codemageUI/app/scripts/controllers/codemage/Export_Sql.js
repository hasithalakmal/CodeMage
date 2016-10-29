'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('Export_Sql', function ($scope, $http) {
  $scope.singleModel = 1;
  $scope.database_names_select ="";

  $scope.radioModel = 'Middle';

  $scope.checkModel = {
    left: false,
    middle: true,
    right: false
  };
  
$scope.init  = function() {
		$http({
		  method: 'GET',
		  url: 'http://localhost:8084/CodeMage/database/1',
		}).then(function successCallback(response) {
			var dbs = [];
			var i = 0;
			for(i=0;i<response.data.length;i++){
				dbs[i] = response.data[i].user_dbname;
			}
			$scope.database_names_select = dbs;
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			)
		});
	}; 

$scope.submit  = function(){

	var  result = {
		"db_name":$scope.selectedDB,
	};
	console.log($scope.selectedDB); 

	$http({
		  method: 'POST',
		  data : result,
		  url: 'http://localhost:8084/CodeMage/export/',
		}).then(function successCallback(response) {
			$scope.dbData = response.data.result;
			console.log($scope.dbData);
			var content = $scope.dbData;
			var blob = new Blob([ content ], { type : 'text/plain' });
			$scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
			
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong! massa',
			  'error'
			)
		});
};	
 	
  
});