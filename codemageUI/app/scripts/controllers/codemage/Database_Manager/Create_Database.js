'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('Create_Database', function ($scope,$http) {
	//define variables
	$scope.databasename = '';
	$scope.query = 'waiting for query';
	
	
	$scope.errMsg ='';
	$scope.alerts1 = [];
	$scope.addAlert1 = function(){
		$scope.alerts1 = [];
		
		//this is the part for custom error message
		var reeString = $scope.errMsg;
		if(reeString.includes("1007")){
			$scope.errMsg = reeString+ "your databse server has samebd. pleace don't do itxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		} else if (reeString.includes("1064")){
			$scope.errMsg = reeString+ "You should put valid database name. pleace don't do itxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		}
		//end of the custom err msg
		
		$scope.alerts1.push({type: 'danger',  msg: $scope.errMsg})
	};
    $scope.closeAlert1 = function(index) {
        $scope.alerts1.splice(index, 1);
    };
	
	
	 
	//define methodes
    $scope.showQuery = function() {
        $scope.query = ('CREATE DATABASE '+ $scope.databasename + ' ;');
    };

    $scope.submit = function() {
        // Simple GET request example:
		$http({
		  method: 'POST',
		  url: 'http://localhost:8084/CodeMage/database',
		  data : {
					"user_dbname":$scope.databasename,
					"userid": 1
				}
		}).then(function successCallback(response) {
			if(response.data.err == 'true'){
				$scope.errMsg =response.data.msg;
				console.log(response.data);
				$scope.showGrowlWarning = true;
				$scope.addAlert1();
			}else{
				swal("Success!", "Database is Succesfully Created" , "success");
				$scope.databasename = '';
				$scope.query = 'waiting for query';
				console.log(response);
				$scope.errMsg ='';
				$scope.showGrowlWarning = false;
			}
		  }, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			)
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		  });
    };
	
	

});