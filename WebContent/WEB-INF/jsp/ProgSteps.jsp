<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" data-ng-app="ProgStepsApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Program Steps</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>
<script language="javascript" type="text/javascript" src="<c:url value='/static/js/angular.min.js' />" /></script>

<script>
	angular.module('ProgStepsApp', ['filters'])
	.controller('ProgStepsCtrl', function($scope, $http) {
		// document.getElementById("viewnosteps").style.visibility = "hidden";
		// document.getElementById("viewstepadd").style.visibility = "hidden";
		var jsonRes = JSON.parse('${result}');
		$scope.prsteps = jsonRes.programsteps;
		$scope.program = jsonRes.program;
		$scope.itemedit = {};
		$scope.itemedit_orig = {};
		if ($scope.prsteps.length == 0) {
			$scope.prsteps = [];
			document.getElementById("viewnosteps").style.visibility = "visible";
		}

		$scope.addStep = function() {
 			$http({
				method:'POST',
				url: '/' + window.location.href.split('/')[3] + '/stepsrest',
				headers:{'Content-Type' :'application/x-www-form-urlencoded'}
			}).success(function(responce) {
				if (responce.length > 0) {
					$scope.stepsadd = responce;
					if ($scope.prsteps.length == $scope.stepsadd.length) {
						// $scope.stepsadd = [];
						document.getElementById("viewstepadd").style.visibility = "hidden";
					}
					else {
						document.getElementById("viewstepadd").style.visibility = "visible";
						if ($scope.prsteps.length != 0) {
	 						for (var i = 0; i < $scope.prsteps.length; i++) {
								size = $scope.stepsadd.length;
								for (var j = 0; j < size; j++) {
									if ($scope.stepsadd[j].name == $scope.prsteps[i].step.name) {
										$scope.stepsadd.splice(j, 1);
										size--;
										break;
									}
								}
							}
						}
					}
				}
				else{
					document.getElementById("viewstepadd").style.visibility = "hidden";
				}
			}).error(function(responce) {
			});
		}

		$scope.addStepToProgram = function(step) {
			document.getElementById("viewstepadd").style.visibility = "hidden";
			if (step.id != 0) {
				$scope.prsteps.push({"id":"0", "program": $scope.program, "step": step, "stepOrder": 99, "description": step.description, "fileName": ""})
			}
			if ($scope.prsteps.length == 0) {
				document.getElementById("viewnosteps").style.visibility = "visible";
			}
			else {
				document.getElementById("viewnosteps").style.visibility = "hidden";
			}
		}

		$scope.saveSteps = function() {
			document.getElementById("viewstepadd").style.visibility = "hidden";
			if ($scope.prsteps.length > 0) {
				document.getElementById("viewnosteps").style.visibility = "hidden";
				for (var i = 0; i< $scope.prsteps.length; i++) {
					$scope.prsteps[i].id = 0;
				}
	 			sendJson = $scope.prsteps;
				$http({
					method:'POST',
					url: '/' + window.location.href.split('/')[3] + '/programstepsofprogramsave',
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
					url: '/' + window.location.href.split('/')[3] + '/programstepsofprogramdelete',
					data: sendJson,
					headers:{'Content-Type' :'application/x-www-form-urlencoded'}
				}).success(function() {
				}).error(function() {
				});
			}
		}

		$scope.revertEdit = function() {
			document.getElementById("viewstepadd").style.visibility = "hidden";
			sendJson = $scope.program;
 			$http({
				method:'POST',
				url: '/' + window.location.href.split('/')[3] + '/programstepsofprogram',
				data: sendJson,
				headers:{'Content-Type' :'application/x-www-form-urlencoded'}
			}).success(function(responcePS) {
				if (responcePS.length > 0) {
					$scope.prsteps = responcePS;
					document.getElementById("viewnosteps").style.visibility = "hidden";
				}
				else{
					$scope.prsteps = [];
					document.getElementById("viewnosteps").style.visibility = "visible";
				}
			}).error(function(responcePS) {
				// $scope.prsteps = [];
			});
		}

		
		$scope.getTemplate = function (item) {
			if (item.id === $scope.itemedit.id && item.step.name == $scope.itemedit.step.name) {
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
		
		$scope.editItem = function (item) {
			document.getElementById("viewstepadd").style.visibility = "hidden";
			$scope.resetItem();
	        $scope.itemedit = angular.copy(item);
	        $scope.itemedit_orig = angular.copy(item);
	    };
		
		$scope.updateItem = function(item) {
			document.getElementById("viewstepadd").style.visibility = "hidden";
			if (item.value != "") {
				if (!angular.equals(item, $scope.itemedit_orig)) {
					for (var i = 0; i< $scope.prsteps.length; i++) {
						if ($scope.prsteps[i].step.name == item.step.name) {
							$scope.prsteps[i].stepOrder = item.stepOrder;
							$scope.prsteps[i].description = item.description;
							$scope.prsteps[i].fileName = item.fileName;
							break;
						}
					}
					$scope.resetItem();
				}
				else {
					alert("You do not change anything !");
				}
			}
		}
 		
		$scope.deleteItem = function(item) {
			document.getElementById("viewstepadd").style.visibility = "hidden";
			for (var i = 0; i< $scope.prsteps.length; i++) {
				if ($scope.prsteps[i].step.name == item.step.name) {
					$scope.prsteps.splice(i, 1);
					break;
				}
			}
			if ($scope.prsteps.length == 0) {
				document.getElementById("viewnosteps").style.visibility = "visible";
			}
			else {
				document.getElementById("viewnosteps").style.visibility = "hidden";
			}
		}

	});
	
	angular.module('filters', [])
	.filter('strLimit', function () {
        return function (text, length) {
            if (isNaN(length))
                length = 30;
            if (text.length <= length) {
                return text;
            }
            else {
            	return String(text).substring(0, length + 1);
            }
        };
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
	<div class="panel-body" data-ng-controller="ProgStepsCtrl">
		<div class="col-xs-6" id="steps">
			<label>Country: {{program.country.name}}</label><br/>
			<label>Steps in program: {{program.name}}</label><br/>
			<p id="viewnosteps" style="visibility:hidden;">Selected program don't have steps</p>
			<table class="table table-bordered table-hover table-condensed">
		        <colgroup>
       		  		<col width="25%"/>
       		  		<col width="35%"/>
       		  		<col width="10%"/>
       		  		<col width="24%"/>
       		  		<col width="3%"/>
       		  		<col width="3%"/>
        		</colgroup>
				<thead>
					<th>Name</th>
					<th>Description</th>
					<th>Order</th>
					<th>File Name</th>
					<th colspan="2">Actions</th>
				</thead>
				<tbody>
					<tr ng-repeat="item in prsteps | orderBy : 'stepOrder'" ng-include="getTemplate(item)">
						<script type="text/ng-template" id="display">
							<td>{{item.step.name}}</td>
							<td>{{item.description | strLimit : 30}}</td>
							<td>{{item.stepOrder}}</td>
							<td>{{item.fileName}}</td>
							<td><button type="button" class="btn btn-primary" ng-click="editItem(item)"><span class="glyphicon glyphicon-pencil"></button></td>
							<td><button type="button" class="btn btn-default" ng-click="deleteItem(item)"><span class="glyphicon glyphicon-trash"></button></td>
						</script>
						<script type="text/ng-template" id="edit">
							<td>{{item.step.name}}</td>
							<td><input type="text" ng-model=itemedit.description class="form-control input-sm"/></td>
							<td><input type="number" ng-model=itemedit.stepOrder class="form-control input-sm" required/></td>
							<td><input type="text" ng-model=itemedit.fileName class="form-control input-sm"/></td>
							<td><button type="button" class="btn btn-primary" ng-click="updateItem(itemedit)"><span class="glyphicon glyphicon-ok"></button></td>
							<td><button type="button" class="btn btn-default" ng-click="resetItem()"><span class="glyphicon glyphicon-remove"></button></td>
						</script>
					</tr>
				</tbody>
			</table>
			<div id="stepsctrl" class="col-xs-12">
				<button id="stepadd" class="btn btn-primary" ng-click="addStep()">Add Step to Program</button>
				<button class="btn btn-info" ng-click="saveSteps()">Save Steps</button>
				<button class="btn btn-primary" ng-click="revertEdit()">Revert</button>
			</div>
			<div id="viewstepadd" style="visibility:hidden;" class="col-xs-4 form-group">
				<label>Select step to add:</label><br/>
				<select class="form-control" ng-change="addStepToProgram(stepsadd.selected)" ng-model="stepsadd.selected" ng-options="ss.name for ss in stepsadd track by ss.id" ></select>
			</div>
		</div>
	</div>
</body>
</html>
