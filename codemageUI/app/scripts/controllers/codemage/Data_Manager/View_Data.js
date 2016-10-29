'use strict';

/**
 * @ngdoc function
 * @name dashyAngular.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of dashyAngular
 */
angular.module('dashyAngular').controller('View_Data', function ($scope, $http,$sce) {
		$scope.database_names_select ="";
		$scope.table_names_select ="";
		$scope.query ="Wating for Query";
	
		$scope.table_heder = "";
		$scope.table_body = "";
		
		$scope.query = "Waiting For Query";
		$scope.java ="/*STEP 1. Import required packages*/\n"
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
                + "            System.out.println(\"Inserting records into the table...\");\n"
                + "            stmt = conn.createStatement();\n"
                + "\n"
                + "            String sql = \"Enter Your Query Here\";\n"
                + "            ResultSet rs = stmt.executeQuery(sql);\n"
                + "\n"
                + "            ResultSetMetaData rsmd = rs.getMetaData();\n"
                + "            int columnsNumber = rsmd.getColumnCount();\n"
                + "\n"
                + "            /* Iterate through the data in the result set and display it. */\n"
                + "            while (rs.next()) {\n"
                + "                /*Print one row*/          \n"
                + "                for (int i = 1; i <= columnsNumber; i++) {\n"
                + "                    /*Print one element of a row*/\n"
                + "                    System.out.print(rs.getString(i) + \" \"); \n"
                + "                }\n"
                + "                /*Move to the next line to print the next row.  */\n"
                + "                System.out.println();\n"
                + "            }\n"
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
	
	
	$scope.submit  = function(){
			$http({
			  method: 'GET',
			  url: 'http://localhost:8084/CodeMage/colmns/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {				
				var i=0;
				var tblHeady = "<tr>";
				console.log(response.data.length);
				
				for(i = 0; i< response.data.length; i++){
					tblHeady = tblHeady + "<th>"+response.data[i]+"</th>";
				} 
				
				tblHeady = tblHeady + '</tr>';
				console.log(tblHeady);
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
			  url: 'http://localhost:8084/CodeMage/data/'+$scope.selectedDB+'/'+$scope.selectedTBL
			}).then(function successCallback(response) {	
				console.log(response.data);
				$scope.table_body = $sce.trustAsHtml(response.data.result);
				$scope.query = response.data.query;
				$scope.java = response.data.java;
			}, function errorCallback(response) {
				swal(
				  'error!',
				  'something wrong!',
				  'error'
				)
			});
		};
	
});