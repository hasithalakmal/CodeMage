'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('Create_Table', function ($scope, $filter, $http) { 
	$scope.errMsg ='';
	$scope.alerts1 = [];
	$scope.addAlert1 = function(){
		$scope.alerts1 = [];
		
		//this is the part for custom error message
		var reeString = $scope.errMsg;
		if(reeString.includes("1050")){
			$scope.errMsg = reeString+ "your table is alredy exist. pleace don't do itxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		} else if (reeString.includes("1064")){
			$scope.errMsg = reeString+ "You should put valid table name. pleace don't do itxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		}
		//end of the custom err msg
		
		$scope.alerts1.push({type: 'danger',  msg: $scope.errMsg})
	};
    $scope.closeAlert1 = function(index) {
        $scope.alerts1.splice(index, 1);
    };



   $scope.users = []; 
   
   $scope.showQuery = function(){
		$scope.query = "Create Table "+ $scope.tblName + "();";
   };

   $scope.query = "Waiting For Query...";
  $scope.datatype = [
    {value: 'varchar(45)', text: 'String'},
    {value: 'INT(11)', text: 'Integer'},
	{value: 'Double', text: 'Double'},
	{value: 'Float', text: 'Float'},
    {value: 'Date', text: 'Date'}
  ]; 

  $scope.database_names_select ="";

  $scope.init  = function() {
		$http({
		  method: 'GET',
		  url: 'http://localhost:8084/CodeMage/database/1'
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
	
	

  $scope.showDataTypes = function(user) {
    var selected = [];
    if(user.datatype) {
      selected = $filter('filter')($scope.datatype, {value: user.datatype});
    }
    return selected.length ? selected[0].text : 'Not set';
  };

  $scope.saveUser = function(data, id, pk, nn, uq, ai) {
    //$scope.user not updated yet
    angular.extend(data, {id: id},{pk: pk}, {nn: nn}, {uq: uq}, {ai: ai});
	
	//console.log($scope.tblName);
	//console.log($scope.users);
  };

 var feildCreator = function (){
		//console.log($scope.users);
		
		var test = $scope.users;
		
		console.log(test[0]);
		var val ='';
		var i = 0;
		for(i = 0; i < test.length-1 ;i++){
			//console.log(test[i]);
			val = val+' '+ test[i].name + ' ' + test[i].datatype;
			if(test[i].pk==true){
				val =val + ' PRIMARY KEY';
			}
			if(test[i].nn == true){
				val =val + ' NOT NULL';
			}
			if(test[i].uq==true){
				val =val + ' UNIQUE';
			}
			if(test[i].ai==true){
				val =val + ' AUTO_INCREMENT';
			}
			val = val+',\n';

		}
		console.log(val);
		return val;
  };
  
  // remove user
  $scope.removeUser = function(index) {
    $scope.users.splice(index, 1);
  };

  // add user
  $scope.addUser = function() {
    $scope.inserted = {
      id: $scope.users.length+1,
      name: '',
      datatype: null,
      pk: false ,
	  nn : false,
	  uq : false,
	  ai :false
    };
    $scope.users.push($scope.inserted);
	$scope.query = "CREATE TABLE "+ $scope.tblName + "(\n"+ feildCreator() +"\n);";
	
  };
  
  
  
  $scope.createTable = function(){
	var i=0;
	var fld = [];
	for(i=0; i<$scope.users.length; i++){
		var feild = {
		  "feild_name": $scope.users[i].name,
          "data_type": $scope.users[i].datatype,
          "primary_key": $scope.users[i].pk,
          "auto_incriment": $scope.users[i].ai,
          "not_null": $scope.users[i].nn,
          "unique": $scope.users[i].uq
		  };
		fld[i] = feild;
	};
	
	var output = {
	"db_name":$scope.selected,
	"table_detail":{
		 "table_name": $scope.tblName,
		  "fileds": fld
	}
	};
		
	$http({
		  method: 'POST',
		  url: 'http://localhost:8084/CodeMage/table',
		  data : output
		}).then(function successCallback(response) {
			if(response.data.err == 'true'){
				$scope.query = response.data.query;
				$scope.errMsg =response.data.msg;
				$scope.showGrowlWarning = true;
				$scope.addAlert1();
			}else{
			
				$scope.query = response.data.query;
				swal(
				  'Good Job!',
				  'Database Table is successfully Created!',
				  'success'
				);
				
				$scope.errMsg ='';
				$scope.showGrowlWarning = false;
			}
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			);
		});
	
	

	
	
  };
});