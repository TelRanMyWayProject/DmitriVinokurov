<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" data-ng-app="StepsApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Steps</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>
<script language="javascript" type="text/javascript" src="<c:url value='/static/js/angular.min.js' />" /></script>

<script>
	angular.module('StepsApp', [])
	.controller('StepsCtrl', function($scope, $http) {
		var jsonRes = JSON.parse('${result}');
		$scope.itemsAll = jsonRes;
		$scope.itemedit = {};
		$scope.itemedit_orig = {};
        $scope.itemadd = {"id":0,"name":"","description":""};

		$scope.getTemplate = function (item) {
	        if (item.id === $scope.itemedit.id) {
				return 'edit';
			}
	        else {
	        	return 'display';
	        }
	    };
		
		$scope.resetItem = function () {
	        $scope.itemedit = {};
			$scope.itemedit_orig = {};
	    };
		
		$scope.resetAddItem = function () {
	        $scope.itemadd = {"id":0,"name":"","description":""};
	    };
		
		$scope.editItem = function (item) {
	        $scope.itemedit = angular.copy(item);
	        $scope.itemedit_orig = angular.copy(item);
	    };
		
		$scope.updateItem = function(item) {
			if (item.name != "") {
				if (!angular.equals(item, $scope.itemedit_orig)) {
					flag = true;
					for (var i = 0; i< $scope.itemsAll.length; i++) {
						if ($scope.itemsAll[i].name == item.name && $scope.itemsAll[i].id != item.id) {
							flag = false;
							alert("The Step name is already exist !");
							break;
						}
					}
					if (flag) {
			 			sendJson = item;
			 			$http({
							method:'POST',
							url: '/' + window.location.href.split('/')[3] + '/stepedit',
							data: sendJson,
							headers:{'Content-Type' :'application/x-www-form-urlencoded'}
						}).success(function(responce) {
							// console.log(responce);
							$scope.resetItem();
							for (var i = 0; i< $scope.itemsAll.length; i++) {
								if ($scope.itemsAll[i].id == responce.id) {
									$scope.itemsAll[i].name = responce.name;
									$scope.itemsAll[i].description = responce.description;
									break;
								}
							}
						}).error(function(responce) {
							alert("Error updating step !");
							// console.log("Error updating");
						});
					}
				}
				else {
					alert("You do not change anything !");
				}
			}
		}
		
		$scope.addItem = function(item) {
			if (item.name != "") {
				flag = true;
				for (var i = 0; i< $scope.itemsAll.length; i++) {
					if ($scope.itemsAll[i].name == item.name) {
						flag = false;
						alert("The Step name is already exist !");
						break;
					}
				}
				if (flag) {
		 			sendJson = item;
					$http({
						method:'POST',
						url: '/' + window.location.href.split('/')[3] + '/stepadd',
						data: sendJson,
						headers:{'Content-Type' :'application/x-www-form-urlencoded'}
					}).success(function(responce) {
						if (responce.length != 0) {
							if (responce.id > 0) {
								$scope.itemsAll.push(responce);
								$scope.resetAddItem();
							}
						}
						else {
						}
					}).error(function(responce) {
						alert("Error adding step !");
					});
				}
			}
		}

		$scope.deleteItem = function(item) {
		}

	});
</script>
<style type="text/css">
[ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak {
  display: none !important;
}
th {
	text-align:center;
}
</style>
</head>
<body>
	<div class="panel-body" data-ng-controller="StepsCtrl">
		<div class="col-xs-5 form-group">
			<label>List Steps</label>
			<table class="table table-bordered table-hover table-condensed">
		        <colgroup>
       		  		<col width="40%"/>
       		  		<col width="50%"/>
       		  		<col width="5%"/>
       		  		<col width="5%"/>
        		</colgroup>
				<thead>
					<th>Name</th>
					<th>Description</th>
					<th colspan="2">Actions</th>
				</thead>
				<tbody>
					<tr ng-repeat="item in itemsAll | orderBy : 'name'" ng-include="getTemplate(item)">
						<script type="text/ng-template" id="display">
							<td>{{item.name}}</td>
							<td>{{item.description}}</td>
							<td><button type="button" class="btn btn-primary" ng-click="editItem(item)"><span class="glyphicon glyphicon-pencil"></button></td>
							<td><button type="button" class="btn btn-default disabled" ng-click="deleteItem(item)"><span class="glyphicon glyphicon-trash"></button></td>
						</script>
						<script type="text/ng-template" id="edit">
							<td><input type="text" ng-model=itemedit.name class="form-control input-sm" required/></td>
							<td><input type="text" ng-model=itemedit.description class="form-control input-sm"/></td>
							<td><button type="button" class="btn btn-primary" ng-click="updateItem(itemedit)"><span class="glyphicon glyphicon-ok"></button></td>
							<td><button type="button" class="btn btn-default" ng-click="resetItem()"><span class="glyphicon glyphicon-remove"></button></td>
						</script>
					</tr>
					<tr>
						<td><input type="text" ng-model=itemadd.name placeholder="Insert name" class="form-control input-sm"/></td>
						<td><input type="text" ng-model=itemadd.description placeholder="Insert description" class="form-control input-sm"/></td>
						<td><button type="button" class="btn btn-info" ng-click="addItem(itemadd)"><span class="glyphicon glyphicon-plus"></button></td>
						<td><button type="button" class="btn btn-default" ng-click="resetAddItem()"><span class="glyphicon glyphicon-remove"></button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
