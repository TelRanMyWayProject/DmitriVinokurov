<!DOCTYPE html>
<html>
<head>


<title>"Your-way" service administration</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/static/js/angular.min.js" />"></script>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>
<style>
[ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak {
  display: none !important;
}
</style>
</head>
<body>
<div data-ng-app="country_app" data-ng-controller="countrys_contr" data-ng-cloak class="col-xs-90 form-group">
<p style="max-width:15%;">Filter by name: <input type="text" data-ng-model="search.name" class="form-control input-sm"></p>
<div class="col-xs-100 form-group">
<table class="table table-hover table-condensed">
	<colgroup>
    <col class="col-xs-5"/>
    <col class="col-xs-5"/>
    <col class="col-xs-1"/>
    <col class="col-xs-2"/>
    <col class="col-xs-2"/>
    </colgroup>
	<thead>
		<tr>
		<th>name</th>
		<th>link</th>
		<th>action</th>
		<th>Embassies</th>
		<th>Programs</th>
		</tr>
	</thead>
	<tbody>
	<tr data-ng-repeat="cr in Countrys|filter:search">
		<td><input type="text" name="name" value="{{cr.name}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="link" value="{{cr.link}}" class='{{$index}} form-control input-sm'></td>
		<td><button data-ng-click=SendEditRequest($event) name="button" class='{{$index}}'>Edit</button></td>
		<td><button data-ng-click=sendEmbassyRequest($event) name="button" class={{$index}}>Embassies</button></td>
		<td><button data-ng-click=sendProgramsRequest($event) name="button" class={{$index}}>Programs</button></td>
		<td><input type="hidden" name="countryId" value={{cr.countryId}} class={{$index}}></td>
	</tr>
	<tr>
		<td><input type="text" class="adding form-control input-sm" name="name" placeholder="name"></td>
		<td><input type="text" class="adding form-control input-sm" name="link" placeholder="link"></td>
		<td><button data-ng-click=AddCountry() name="button">Add</button></td>
	</tr>
	</tbody>
</table>
</div>
</div>
<script>
var appl=angular.module('country_app',[]);
appl.controller('countrys_contr',function($scope,$http){
	$scope.GetAllCountries=function(){
		$http({
			 	method: 'GET',
				url: '/'+window.location.href.split('/')[3]+'/list_countries'
			  }).then(function successCallback(response) {
				   $scope.Countrys=response.data;
			  }, function errorCallback(response) {
				 console.log(response);
			  });
	}
	$scope.GetAllCountries();
	
	$scope.AddCountry=function(){
		var values=document.getElementsByClassName("adding");
		var index=$scope.Countrys.length;
		var data={
				name : values.name.value,
				link : values.link.value,
		};
		if(values.name.value!=""){
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/country',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.name!="Error"){
				$scope.Countrys[index]=response;
			}else{
				alert("Country with this name already exists");
			}
			
		}).error(function(response){
				console.log(response);
			});
		}else
			alert("enter the name");
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"&&values[i].name!="countryId"){
				values[i].value="";
			}
		}
	}
	
	$scope.SendEditRequest=function(event){
		var index=event.currentTarget.className;
		var values=document.getElementsByClassName(index);
		var data={
				name : values.name.value,
				link : values.link.value,
				countryId: values.countryId.value
		};
		if(values.name.value!=""){
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/country_edit',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.name!="Error"){
				for(var i=0;i<$scope.Countrys.length;i++){
					if($scope.Countrys[i].countryId==response.countryId){
						index=i;
					}
				}
				$scope.Countrys[index].name=response.name;
				$scope.Countrys[index].link=response.link;
			}else{
				values.name.value=$scope.Countrys[index].name;
				values.link.value=$scope.Countrys[index].link;
				alert("Country with this name already exists");
			}
			
		}).error(function(response){
				console.log(response);
			});}else{
				values.name.value=$scope.Countrys[index].name;
				values.link.value=$scope.Countrys[index].link;
				alert("enter the name");
			}
	}
	
	$scope.sendEmbassyRequest=function(event){
		var className=event.currentTarget.className;
		var values=document.getElementsByClassName(className);
		var form = document.createElement("form");
		form.action='Embassies';
		var element=document.createElement("input");
		element.type="hidden";
		element.name=values[values.length-1].name;
		element.value=values[values.length-1].value;
		form.appendChild(element);
		document.body.appendChild(form);
	    form.submit();
	}
	$scope.sendProgramsRequest=function(event){
		var className=event.currentTarget.className;
		var values=document.getElementsByClassName(className);
		var form = document.createElement("form");
		form.action='programs';
		var element=document.createElement("input");
		element.type="hidden";
		element.name=values[values.length-1].name;
		element.value=values[values.length-1].value;
		form.appendChild(element);
		document.body.appendChild(form);
	    form.submit();
	}
	
});
</script>
</body>
</html>