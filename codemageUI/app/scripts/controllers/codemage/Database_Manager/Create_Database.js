'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('Create_Database', function ($scope) {
	$scope.databasename = '';
	console.log($scope.databasename);

	 $scope.list = '';
     $scope.submit = function() {
        
          $scope.list = ('CREATE DATABASE '+ $scope.databasename + ' ;');
        
      };
	

});