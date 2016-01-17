<%@ page language="java" contentType="text/html; charset=windows-1255" pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" data-ng-app="FieldNamesApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>FieldNames</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script language="javascript" type="text/javascript" src="<c:url value='/static/js/angular.min.js' />" /></script>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>

<script>
	angular.module('FieldNamesApp', [])
	.controller('FieldNamesAppCtrl', function($scope, $http) {
		var jsonRes = JSON.parse('${result}');
		$scope.itemsAll = jsonRes;
		$scope.itemedit = {};
        $scope.itemadd = {"id":0,"name":"","possibleValues":""};

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
	    };
		
		$scope.resetAddItem = function () {
	        $scope.itemadd = {"id":0,"name":"","possibleValues":""};
	    };
		
		$scope.editItem = function (item) {
	        $scope.itemedit = angular.copy(item);
	    };
		
		$scope.updateItem = function(item) {
			if (item.name != "") {
	 			sendJson = item;
	 			$http({
					method:'POST',
					url: '/' + window.location.href.split('/')[3] + '/fieldnamesedit',
					data: sendJson,
					headers:{'Content-Type' :'application/x-www-form-urlencoded'}
				}).success(function(responce) {
					// console.log(responce);
					$scope.resetItem();
					for (var i = 0; i< $scope.itemsAll.length; i++) {
						if ($scope.itemsAll[i].id == responce.id) {
							$scope.itemsAll[i].name = responce.name;
							$scope.itemsAll[i].possibleValues = responce.possibleValues;
							break;
						}
					}
				}).error(function(responce) {
					// console.log("Error updating");
				});
			}
		}
		
		$scope.addItem = function(item) {
			if (item.name != "") {
	 			sendJson = item;
				$http({
					method:'POST',
					url: '/' + window.location.href.split('/')[3] + '/fieldnamesadd',
					data: sendJson,
					headers:{'Content-Type' :'application/x-www-form-urlencoded'}
				}).success(function(responce) {
					if (responce.length != 0) {
						if (responce.id > 0) {
							$scope.itemsAll.push(responce);
							$scope.itemadd.id = 0;
							$scope.itemadd.name = "";
							$scope.itemadd.possibleValues = "";
						}
					}
					else {
					}
				}).error(function(responce) {
				});
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
</style>
</head>
<body>
	<div class="panel-body" data-ng-controller="FieldNamesAppCtrl">
		<div class="col-xs-4 form-group">
			<label>List FieldNames</label>
			<table class="table table-bordered table-hover table-condensed">
		        <colgroup>
          			<col class="col-xs-4"/>
          			<col class="col-xs-6"/>
          			<col class="col-xs-1"/>
          			<col class="col-xs-1"/>
        		</colgroup>
				<thead>
					<th>Name</th>
					<th>Possible values</th>
					<th style="text-align:center" colspan="2">Actions</th>
				</thead>
				<tbody>
					<tr ng-repeat="item in itemsAll | orderBy : 'name'" ng-include="getTemplate(item)">
						<script type="text/ng-template" id="display">
							<td>{{item.name}}</td>
							<td>{{item.possibleValues}}</td>
							<td><button type="button" class="btn btn-primary" ng-click="editItem(item)"><span class="glyphicon glyphicon-pencil"></button></td>
							<td><button type="button" class="btn btn-default disabled" ng-click="deleteItem(item)"><span class="glyphicon glyphicon-trash"></button></td>
						</script>
						<script type="text/ng-template" id="edit">
							<td><input type="text" ng-model=itemedit.name class="form-control input-sm" required/></td>
							<td><input type="text" ng-model=itemedit.possibleValues class="form-control input-sm"/></td>
							<td><button type="button" class="btn btn-primary" ng-click="updateItem(itemedit)"><span class="glyphicon glyphicon-ok"></button></td>
							<td><button type="button" class="btn btn-default" ng-click="resetItem()"><span class="glyphicon glyphicon-remove"></button></td>
						</script>
					</tr>
					<tr>
						<td><input type="text" ng-model=itemadd.name placeholder="Insert name" class="form-control input-sm"/></td>
						<td><input type="text" ng-model=itemadd.possibleValues placeholder="Insert possible values" class="form-control input-sm"/></td>
						<td><button type="button" class="btn btn-info" ng-click="addItem(itemadd)"><span class="glyphicon glyphicon-plus"></button></td>
						<td><button type="button" class="btn btn-default" ng-click="resetAddItem()"><span class="glyphicon glyphicon-remove"></button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
