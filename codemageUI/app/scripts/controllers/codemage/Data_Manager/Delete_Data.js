'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('Delete_Data', function ($scope, $filter, $http, $sce, $compile) {
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
$scope.dtypes=[];
 $scope.users = []; 
var length =0;
var updateIndex=0;
var deleteIndex = 0;

$scope.database_names_select ="";
$scope.table_names_select ="";
$scope.table_heder = "";
$scope.table_body = "";
$scope.table_newdata = "";
$scope.input_feilds ="";
$scope.input_tbl ="";

$scope.addDataBtn = "";
		
$scope.query = "Waiting For Query";
$scope.java ="	/*STEP 1. Import required packages*/\n"
                + "import java.sql.*;\n"
                + "\n"
                + "public class CodeMageExample {\n"
                + "\n"
                + "    /* JDBC driver name and database URL */\n"
                + "    static final String JDBC_DRIVER = \"com.mysql.jdbc.Driver\";\n"
                + "    static final String DB_URL = \"jdbc:mysql://localhost/db1\";\n"
                + "\n"
                + "    /*  Change Your Database credentials */\n"
                + "    static final String USER = \"username\";\n"
                + "    static final String PASS = \"password\";\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "        Connection conn = null;\n"
                + "        Statement stmt = null;\n"
                + "        try {\n"
                + "            /*STEP 2: Register JDBC driver */\n"
                + "            Class.forName(\"com.mysql.jdbc.Driver\");\n"
                + "\n"
                + "            /* STEP 3: Open a connection */\n"
                + "            System.out.println(\"Connecting to a selected database...\");\n"
                + "            conn = DriverManager.getConnection(DB_URL, USER, PASS);\n"
                + "            System.out.println(\"Connected database successfully...\");\n"
                + "\n"
                + "            /* STEP 4: Execute a query */\n"
                + "            System.out.println(\"Deleteing records in the table...\");\n"
                + "            stmt = conn.createStatement();\n"
                + "\n"
                + "            String sql = \"Enter Your Query \";\n"
                + "            stmt.executeUpdate(sql);\n"
                + "            System.out.println(\"Deleted records in the table...\");\n"
                + "\n"
                + "        } catch (SQLException | ClassNotFoundException se) {\n"
                + "        } finally {\n"
                + "            /* finally block used to close resources */\n"
                + "            try {\n"
                + "                if (stmt != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "            try {\n"
                + "                if (conn != null) {\n"
                + "                    conn.close();\n"
                + "                }\n"
                + "            } catch (SQLException se) {\n"
                + "            }\n"
                + "        }\n"
                + "        System.out.println(\"Goodbye!\");\n"
                + "    }\n"
                + "}";

$scope.inputFeild ="";


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
	
	
	
	
	


// remove user
  $scope.saveUser = function(index) {
	updateIndex = index;
	console.log(index);
  };

  // remove user
  $scope.removeUser = function(index) {
	deleteIndex = index;
    //$scope.users.splice(index, 1);
	console.log( deleteIndex);
  };

  // add user
  $scope.addUser = function() {
    $scope.inserted = {
      id: $scope.users.length+1,
      feild1: '',
      feild2: '',
      feild3: '', 
	  feild4: '',
      feild5: '',
      feild6: '', 
	  feild7: '',
      feild8: '',
      feild9: '', 
	  feild10: '',
      feild11: '',
      feild12: '', 
	  feild13: '',
      feild14: '',
      feild15: ''
    };

   $scope.users.push($scope.inserted);
   console.log( $scope.users);
  };

  
  
  
  
  
  
  
  
  
  
  
  
  
  $scope.submit  = function(){
			$http({
			  method: 'GET',
			  url: 'http://localhost:8084/CodeMage/colmns/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {
				feildname = response.data;
				var i=0;
				var tblHeady = "<tr>";
				for(i = 0; i< response.data.length; i++){
					tblHeady = tblHeady + "<th>"+response.data[i]+"</th>";
				} 
				tblHeady = tblHeady + '</tr>';
				$scope.table_heder = $sce.trustAsHtml(tblHeady);
			}, function errorCallback(response) {
				swal(
				  'error!',
				  'something wrong!',
				  'error'
				)
			});
			
			$http({
			  method: 'GET',
			  url: 'http://localhost:8084/CodeMage/data-delete/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {	
				$scope.table_body = $sce.trustAsHtml(response.data.result);
				$scope.input_feilds = $sce.trustAsHtml(response.data.table);
				$scope.input_tbl = "";
			}, function errorCallback(response) {
				swal(
				  'error!',
				  'something wrong!',
				  'error'
				)
			});
			
			
		$http({
			  method: 'GET',
			  url: 'http://localhost:8084/CodeMage/data-json/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {		
				var usersss  =  JSON.parse(response.data.query);
				$scope.users = usersss;
			}, function errorCallback(response) {
				swal(
				  'error!',
				  'something wrong!',
				  'error'
				)
			});
	
			
			
		
		$http({
			  method: 'GET',
			  url: 'http://localhost:8084/CodeMage/colmn-types/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {
				$scope.dtypes = response.data;
				length = response.data.length;
			}, function errorCallback(response) {
				swal(
				  'error!',
				  'something wrong!',
				  'error'
				)
			});

			
		};


$scope.insertData = function(){
		var valuesofData = [];
		switch(length) {
				case 1:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					break;
				case 2:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					break;
				case 3:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					break;
				case 4:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					break;
				case 5:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					break;
				case 6:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					break;
				case 7:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					break;
				case 8:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					break;
				case 9:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					break;
				case 10:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					valuesofData[9] = $scope.users[deleteIndex].feild9;
					break;
				case 11:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					valuesofData[9] = $scope.users[deleteIndex].feild9;
					valuesofData[10] = $scope.users[deleteIndex].feild10;
					break;
				case 12:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					valuesofData[9] = $scope.users[deleteIndex].feild9;
					valuesofData[10] = $scope.users[deleteIndex].feild10;
					valuesofData[11] = $scope.users[deleteIndex].feild11;
					break;
				case 13:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					valuesofData[9] = $scope.users[deleteIndex].feild9;
					valuesofData[10] = $scope.users[deleteIndex].feild10;
					valuesofData[11] = $scope.users[deleteIndex].feild11;
					valuesofData[12] = $scope.users[deleteIndex].feild12;
					break;
				case 14:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					valuesofData[9] = $scope.users[deleteIndex].feild9;
					valuesofData[10] = $scope.users[deleteIndex].feild10;
					valuesofData[11] = $scope.users[deleteIndex].feild11;
					valuesofData[12] = $scope.users[deleteIndex].feild12;
					valuesofData[13] = $scope.users[deleteIndex].feild13;
					break;
				case 15:
					valuesofData[0] = $scope.users[deleteIndex].feild0;
					valuesofData[1] = $scope.users[deleteIndex].feild1;
					valuesofData[2] = $scope.users[deleteIndex].feild2;
					valuesofData[3] = $scope.users[deleteIndex].feild3;
					valuesofData[4] = $scope.users[deleteIndex].feild4;
					valuesofData[5] = $scope.users[deleteIndex].feild5;
					valuesofData[6] = $scope.users[deleteIndex].feild6;
					valuesofData[7] = $scope.users[deleteIndex].feild7;
					valuesofData[8] = $scope.users[deleteIndex].feild8;
					valuesofData[9] = $scope.users[deleteIndex].feild9;
					valuesofData[10] = $scope.users[deleteIndex].feild10;
					valuesofData[11] = $scope.users[deleteIndex].feild11;
					valuesofData[12] = $scope.users[deleteIndex].feild12;
					valuesofData[13] = $scope.users[deleteIndex].feild13;
					valuesofData[14] = $scope.users[deleteIndex].feild14;
					break;

				default:
					console.log('something wrong');
			}
		
		console.log($scope.users[deleteIndex].feild0);
		
	var deleteJSON ={
					  "db_name": $scope.selectedDB,
					  "delete_data": {
						"table_name":  $scope.selectedTBL,
						"condition": {
						  "base_feild": feildname[0],
						  "data_type": $scope.dtypes[0],
						  "operation": "=",
						  "value": $scope.users[deleteIndex].feild0
						}
					  }
					};
	
						
		console.log(deleteJSON);
		$http({
			  method: 'DELETE',
			  data : deleteJSON,
			  url: 'http://localhost:8084/CodeMage/delete'
			}).then(function successCallback(response) {
				if(response.data.err == 'true'){
					$scope.query = response.data.query;
					$scope.errMsg =response.data.msg;
					$scope.showGrowlWarning = true;
					$scope.addAlert1();
				}else{
				
					console.log(response.data);
					console.log(response.data.java_code);
					$scope.query = response.data.query;
					$scope.java = response.data.java_code;
					$scope.submit();
					
					
					$scope.errMsg ='';
					$scope.showGrowlWarning = false;
				}
			
			
				
			}, function errorCallback(response) {
				console.log(response);
				swal(
				  'error!',
				  'something wrong! massa',
				  'error'
				)
			});
	
};
 
});