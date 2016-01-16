<%@ page language="java" contentType="text/html; charset=windows-1255" pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" data-ng-app="ProgCustDataApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Program Custom Data</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script language="javascript" type="text/javascript" src="<c:url value='/static/js/angular.min.js' />" /></script>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>

<script>
	angular.module('ProgCustDataApp', [])
	.controller('ProgCustDataCtrl', function($scope, $http) {
		// document.getElementById("viewnocustdata").style.visibility = "hidden";
		// document.getElementById("viewcustdataadd").style.visibility = "hidden";
		var jsonRes = JSON.parse('${result}');
		$scope.prcustdata = jsonRes.programcustdata;
		$scope.program = jsonRes.program;
		$scope.itemedit = {};
		if ($scope.prcustdata.length == 0) {
			$scope.prcustdata = [];
			document.getElementById("viewnocustdata").style.visibility = "visible";
		}

		$scope.addCustData = function() {
 			$http({
				method:'POST',
				url: '/' + window.location.href.split('/')[3] + '/fieldnamesrest',
				headers:{'Content-Type' :'application/x-www-form-urlencoded'}
			}).success(function(responce) {
				if (responce.length > 0) {
					$scope.cdadd = responce;
					if ($scope.prcustdata.length == $scope.cdadd.length) {
						// $scope.cdadd = [];
						document.getElementById("viewcustdataadd").style.visibility = "hidden";
					}
					else {
						document.getElementById("viewcustdataadd").style.visibility = "visible";
						if ($scope.prcustdata.length != 0) {
	 						for (var i = 0; i < $scope.prcustdata.length; i++) {
								size = $scope.cdadd.length;
								for (var j = 0; j < size; j++) {
									if ($scope.cdadd[j].name == $scope.prcustdata[i].fieldNames.name) {
										$scope.cdadd.splice(j, 1);
										size--;
										break;
									}
								}
							}
						}
					}
				}
				else{
					document.getElementById("viewcustdataadd").style.visibility = "hidden";
				}
			}).error(function(responce) {
			});
		}

		$scope.addCustDataToProgram = function(fn) {
			document.getElementById("viewcustdataadd").style.visibility = "hidden";
			if (fn.id != 0) {
				$scope.prcustdata.push({"id":"0", "program": $scope.program, "fieldNames": fn, "value": fn.possibleValues})
			}
			if ($scope.prcustdata.length == 0) {
				document.getElementById("viewnocustdata").style.visibility = "visible";
			}
			else {
				document.getElementById("viewnocustdata").style.visibility = "hidden";
			}
		}

		$scope.saveCustData = function() {
			document.getElementById("viewcustdataadd").style.visibility = "hidden";
			if ($scope.prcustdata.length > 0) {
				document.getElementById("viewnocustdata").style.visibility = "hidden";
	 			sendJson = $scope.prcustdata;
				$http({
					method:'POST',
					url: '/' + window.location.href.split('/')[3] + '/programcustdataofprogramsave',
					data: sendJson,
					headers:{'Content-Type' :'application/x-www-form-urlencoded'}
				}).success(function() {
				}).error(function() {
				});
			}
			else {
	 			sendJson = $scope.program;
				$http({
					method:'POST',
					url: '/' + window.location.href.split('/')[3] + '/programcustdataofprogramdelete',
					data: sendJson,
					headers:{'Content-Type' :'application/x-www-form-urlencoded'}
				}).success(function() {
				}).error(function() {
				});
			}
		}

		$scope.revertEdit = function() {
			document.getElementById("viewcustdataadd").style.visibility = "hidden";
			sendJson = $scope.program;
 			$http({
				method:'POST',
				url: '/' + window.location.href.split('/')[3] + '/programcustdatasofprogram',
				data: sendJson,
				headers:{'Content-Type' :'application/x-www-form-urlencoded'}
			}).success(function(responce) {
				if (responce.length > 0) {
					$scope.prcustdata = responce;
					document.getElementById("viewnocustdata").style.visibility = "hidden";
				}
				else{
					$scope.prcustdata = [];
					document.getElementById("viewnocustdata").style.visibility = "visible";
				}
			}).error(function(responce) {
				// $scope.prcustdata = [];
			});
		}


		
		$scope.getTemplate = function (item) {
			if (item.id === $scope.itemedit.id && item.fieldNames.name == $scope.itemedit.fieldNames.name) {
				return 'edit';
			}
	        else {
	        	return 'display';
	        }
 		};
		
		$scope.resetItem = function () {
	        $scope.itemedit = {};
	    };
		
		$scope.editItem = function (item) {
			document.getElementById("viewcustdataadd").style.visibility = "hidden";
			$scope.resetItem();
	        $scope.itemedit = angular.copy(item);
	    };
		
		$scope.updateItem = function(item) {
			document.getElementById("viewcustdataadd").style.visibility = "hidden";
			if (item.value != "") {
				for (var i = 0; i< $scope.prcustdata.length; i++) {
					if ($scope.prcustdata[i].fieldNames.name == item.fieldNames.name) {
						$scope.prcustdata[i].value = item.value;
						break;
					}
				}
				$scope.resetItem();
			}
		}
 		
		$scope.deleteItem = function(item) {
			document.getElementById("viewcustdataadd").style.visibility = "hidden";
			for (var i = 0; i< $scope.prcustdata.length; i++) {
				if ($scope.prcustdata[i].fieldNames.name == item.fieldNames.name) {
					$scope.prcustdata.splice(i, 1);
					break;
				}
			}
			if ($scope.prsteps.length == 0) {
				document.getElementById("viewnocustdata").style.visibility = "visible";
			}
			else {
				document.getElementById("viewnocustdata").style.visibility = "hidden";
			}
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
	<div class="panel-body" data-ng-controller="ProgCustDataCtrl">
		<div class="col-xs-5" id="customdata">
			<label>Country: {{program.country.name}}</label><br/>
			<label>Custom data in program: {{program.name}}</label><br/>
			<p id="viewnocustdata" style="visibility:hidden;">Selected program don't have custom data</p>
			<table class="table table-bordered table-hover table-condensed">
		        <colgroup>
          			<col class="col-xs-4"/>
          			<col class="col-xs-6"/>
          			<col class="col-xs-1"/>
          			<col class="col-xs-1"/>
        		</colgroup>
				<thead>
					<tr>
						<th>Name</th>
						<th>Value</th>
						<th style="text-align:center" colspan="2">Actions</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in prcustdata | orderBy : 'fieldNames.name'" ng-include="getTemplate(item)">
						<script type="text/ng-template" id="display">
							<td>{{item.fieldNames.name}}</td>
							<td>{{item.value}}</td>
							<td><button type="button" class="btn btn-primary" ng-click="editItem(item)"><span class="glyphicon glyphicon-pencil"></button></td>
							<td><button type="button" class="btn btn-default" ng-click="deleteItem(item)"><span class="glyphicon glyphicon-trash"></button></td>
						</script>
						<script type="text/ng-template" id="edit">
							<td>{{itemedit.fieldNames.name}}</td>
							<td><input type="text" ng-model=itemedit.value class="form-control input-sm"/></td>
							<td><button type="button" class="btn btn-primary" ng-click="updateItem(itemedit)"><span class="glyphicon glyphicon-ok"></button></td>
							<td><button type="button" class="btn btn-default" ng-click="resetItem()"><span class="glyphicon glyphicon-remove"></button></td>
						</script>
					</tr>
				</tbody>
			</table>
			<div id="stepsctrl" class="col-xs-12">
				<button class="btn btn-primary" ng-click="addCustData()">Add Custom Data to Program</button>
				<button class="btn btn-info" ng-click="saveCustData()">Save Custom Data</button>
				<button class="btn btn-primary" ng-click="revertEdit()">Revert</button>
			</div>
			<div id="viewcustdataadd" style="visibility:hidden;" class="col-xs-7 form-group">
				<label>Select custom data to add:</label><br/>
				<select class="form-control" ng-change="addCustDataToProgram(cdadd.selected)" ng-model="cdadd.selected" ng-options="cd.name for cd in cdadd track by cd.id" ></select>
			</div>
		</div>
	</div>
</body>
</html>
