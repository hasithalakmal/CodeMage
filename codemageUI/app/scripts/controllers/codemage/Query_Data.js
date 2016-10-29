'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('Query_Data', function ($scope, $filter, $http, $sce, $compile) {
$scope.errMsg ='';
	$scope.alerts1 = [];
	$scope.addAlert1 = function(){
		$scope.alerts1 = [];
		$scope.alerts1.push({type: 'danger',  msg: $scope.errMsg})
	};
    $scope.closeAlert1 = function(index) {
        $scope.alerts1.splice(index, 1);
    };




var feildname=[];
var length =0;
$scope.dtypes=[];
$scope.roles  = [];
$scope.database_names_select ="";
$scope.table_names_select ="";
$scope.table_colume_select ="";
$scope.table_heder = "";
$scope.table_body = "";
$scope.input_feilds ="";
$scope.input_tbl ="";
$scope.query = "Waiting For Query";
$scope.java ="Waiting For Java Code";

$scope.alerts = [
        { type: 'info', msg: $scope.java }
    ];

	$scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
	
	$scope.showJavaCode = function(){
		$scope.alerts = [];
		$scope.alerts.push({ msg: $scope.java});
	};
	

$scope.selection = [];
$scope.toggleSelection = function toggleSelection(fruitName) {
    var idx = $scope.selection.indexOf(fruitName);

    // is currently selected
    if (idx > -1) {
      $scope.selection.splice(idx, 1);
    }

    // is newly selected
    else {
      $scope.selection.push(fruitName);
    }
  };

$('#btn-reset').on('click', function() {
  $('#builder-basic').queryBuilder('reset');
});

$('#btn-set').on('click', function() {
  $('#builder-basic').queryBuilder('setRules', rules_basic);
});

$('#btn-get').on('click', function() {
  var result = $('#builder-basic').queryBuilder('getRules');
  var logical_condition = $('#builder-basic').queryBuilder('getSQL');
  console.log(logical_condition);
});

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
	
$scope.getTable = function (){
		$http({
		  method: 'GET',
		  url: 'http://localhost:8084/CodeMage/tables/'+$scope.selectedDB
		}).then(function successCallback(response) {
				$scope.table_names_select = response.data;
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			)
		});
	};
	
$scope.getColume = function(){
		$http({
		  method: 'GET',
		  url: 'http://localhost:8084/CodeMage/colmns/'+$scope.selectedDB+'/'+$scope.selectedTBL
		}).then(function successCallback(response) {
			$scope.roles  = response.data;
 			$scope.selectedUsers =[];
			var columesdata =[];
			$scope.selectedUsers.splice(0, $scope.selectedUsers.length);
			for (var i in $scope.roles ) {
				 $scope.selectedUsers.push($scope.roles [i]);
			}
			
			$scope.selection = $scope.selectedUsers;
	
			$http({
			  method: 'GET',
			  url: 'http://localhost:8084/CodeMage/colmn-types/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {
				$scope.dtypes = response.data;
				length = response.data.length;
				
				var ind =0;
				for(ind=0;ind <$scope.roles.length;ind++){
					var varType = $scope.dtypes[ind];
					switch (varType) {
						case "INT" : varType = "integer"; break;
						case "VARCHAR" : varType = "string"; break;
						
					}
					var dataobj ={
									id: $scope.roles [ind],
									label: $scope.roles[ind],
									type: varType,
									operators: ['equal','not_equal', 'less', 'greater']
								};
					columesdata.push(dataobj);
				}
				if(columesdata.length > 0){
					 $('#builder-basic').queryBuilder({
						plugins: [],
						filters: columesdata, 
					}); 
				}			

				
			}, function errorCallback(response) {
				swal(
				  'error!',
				  'something wrong!',
				  'error'
				)
			}); 			
			
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong!',
			  'error'
			)
		});
		
		};

$scope.submit  = function(){

	feildname = $('#builder-basic').queryBuilder('getRules');
	var logical_condition = $('#builder-basic').queryBuilder('getSQL');
	var  result = {
		"db_name":$scope.selectedDB,
		"logical_condition": logical_condition.sql,
		"table_detail":{
		  "table_name": $scope.selectedTBL,
		  "table_colume": $scope.selection, // check list data array
		  "where": feildname
		}
	};

	console.log(result);
	
	$http({
		  method: 'POST',
		  data : result,
		  url: 'http://localhost:8084/CodeMage/qurey-builder'
		}).then(function successCallback(response) {
			if(response.data.err == 'true'){
				$scope.query = response.data.query;
				$scope.errMsg =response.data.msg;
				$scope.showGrowlWarning = true;
				$scope.addAlert1();
			}else{
			
				$scope.query = response.data.query;
				$scope.java = response.data.java_code;
				
				var i=0;
				var tblHeady = "<tr>";
				for(i = 0; i< $scope.selection.length; i++){
					tblHeady = tblHeady + "<th>"+$scope.selection[i]+"</th>";
				} 
				tblHeady = tblHeady + '</tr>';
				$scope.table_heder = $sce.trustAsHtml(tblHeady);
				$scope.table_body = $sce.trustAsHtml(response.data.result);
				$scope.input_tbl = "";
				
				$scope.errMsg ='';
				$scope.showGrowlWarning = false;
			}
			
		}, function errorCallback(response) {
			swal(
			  'error!',
			  'something wrong! massa',
			  'error'
			)
		});
};

});