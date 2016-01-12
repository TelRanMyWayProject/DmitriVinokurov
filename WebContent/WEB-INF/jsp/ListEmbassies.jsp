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
<div data-ng-app="embassies_app" data-ng-controller="embassies_contr" data-ng-init=GetAllCountries() data-ng-cloak class="col-xs-90 form-group">
<p>{{CountryName}} <button data-ng-click=sendRequestCountries()>back</button></p>
<div class="col-xs-100 form-group">
<table class="table table-hover table-condensed">
	<colgroup>
    <col class="col-xs-1"/><!-- phone -->
    <col class="col-xs-1"/><!--fax-->
    <col class="col-xs-1"/><!--email-->
    <col class="col-xs-1"/><!--type-->
    <col class="col-xs-3"/><!--link-->
    <col class="col-xs-3"/><!--address-->
    <col class="col-xs-3"/><!--location-->
    <col class="col-xs-1"/><!--action-->
    </colgroup>
	<thead>
		<tr>
		<th>phone</th>
		<th>fax</th>
		<th>email</th>
		<th>type</th>
		<th>link</th>
		<th>address</th>
		<th>location</th>
		<th>action</th>
		</tr>
	</thead>
	<tbody>
	<tr data-ng-repeat="emb in embassies">
		<td><input type="text" name="phone" value="{{emb.phone}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="fax" value="{{emb.fax}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="email" value="{{emb.email}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="type" value="{{emb.type}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="link" value="{{emb.link}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="address" value="{{emb.address}}" class='{{$index}} form-control input-sm'></td>
		<td>
			<select name="location" 
			data-ng-model="emb.location.countryId" 
			data-ng-options="country.countryId as country.name for country in Countrys" 
			class='{{$index}} form-control'>
			</select>
		</td>
		<td><button data-ng-click=SendEditRequest($event) name="button" class={{$index}}>Edit</button></td>
		<td><input type="hidden" name="embassyID" value={{emb.embassyID}} class={{$index}}></td>
	</tr>
	<tr>
		<td><input type="text" class="adding form-control input-sm" name="phone" placeholder="phone"></td>
		<td><input type="text" class="adding form-control input-sm" name="fax" placeholder="fax"></td>
		<td><input type="text" class="adding form-control input-sm" name="email" placeholder="email"></td>
		<td><input type="text" class="adding form-control input-sm" name="type" placeholder="type"></td>
		<td><input type="text" class="adding form-control input-sm" name="link" placeholder="link"></td>
		<td><input type="text" class="adding form-control input-sm" name="address" placeholder="address"></td>
		<td>
			<select name="location" 
			data-ng-options="country.name for country in Countrys track by country.countryId" 
			data-ng-model="selected_value" 
			class="adding form-control">
			</select>
		</td>
		<td><button data-ng-click=AddEmbassy() name="button">Add embassy</button></td>
	</tr>
	</tbody>
</table>
</div>
</div>
<script>
var appl=angular.module('embassies_app',[]);

appl.controller('embassies_contr',function($scope,$http){
	var json=JSON.parse('${results}');
	$scope.id=json.CountryId;
	$scope.CountryName=json.name;
	
	$scope.selected_value;
	$scope.GetAllEmbassies=function () {
		$http({
		 	method: 'GET',
			url: '/'+window.location.href.split('/')[3]+'/list_embassies'+'?countryId='+$scope.id
		  }).then(function successCallback(response) {
			   $scope.embassies=response.data;
		  }, function errorCallback(response) {
			 console.log(response);
		  });
	};
	
	$scope.GetAllEmbassies();
	
	$scope.GetAllCountries=function () {
		$http({
		 	method: 'GET',
			url: '/'+window.location.href.split('/')[3]+'/list_countries'
		  }).then(function successCallback(response) {
			  
			   $scope.Countrys=response.data;
			   $scope.Countrys[$scope.Countrys.length]={name: "Choose country", link: "", countryId: -1};
			   $scope.selected_value=$scope.Countrys[$scope.Countrys.length-1];
		  }, function errorCallback(response) {
			 console.log(response);
		  });
	};
	
	
	$scope.SendEditRequest=function(event){
		var index=event.currentTarget.className;
		var values=document.getElementsByClassName(index);
		var data={
				phone : values.phone.value,
				fax : values.fax.value,
				email : values.email.value,
				type : values.type.value,
				link : values.link.value,
				address: values.address.value,
				location: $scope.Countrys[values.location.selectedIndex].countryId,
				embassyID: values.embassyID.value
				
		};
		if(values.phone.value!=""){
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/embassy_edit',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.phone!="Error"){
			$scope.embassies[index].phone=response.phone;
			$scope.embassies[index].fax=response.fax;
			$scope.embassies[index].email=response.email;
			$scope.embassies[index].type=response.type;
			$scope.embassies[index].link=response.link;
			$scope.embassies[index].address=response.address;
			$scope.embassies[index].location=response.location;
			}else{
				values.phone.value=$scope.embassies[index].phone;
				values.fax.value=$scope.embassies[index].fax;
				values.email.value=$scope.embassies[index].email;
				values.type.value=$scope.embassies[index].type;
				values.link.value=$scope.embassies[index].link;
				values.address.value=$scope.embassies[index].address;
				alert("Embassy with this phone already exists");
			}
		}).error(function(response){
			console.log(response);
			});
		}else{
			values.phone.value=$scope.embassies[index].phone;
			values.fax.value=$scope.embassies[index].fax;
			values.email.value=$scope.embassies[index].email;
			values.type.value=$scope.embassies[index].type;
			values.link.value=$scope.embassies[index].link;
			values.address.value=$scope.embassies[index].address;
			alert("enter the phone");
		}
	}
	
	$scope.AddEmbassy=function(){
		var values=document.getElementsByClassName("adding");
		var index=$scope.embassies.length;
		
		if(values.location.selectedIndex!=$scope.Countrys.length-1&&values.phone.value!=""){	
		var data={
				phone : values.phone.value,
				fax : values.fax.value,
				email : values.email.value,
				type : values.type.value,
				link : values.link.value,
				address: values.address.value,
				location: $scope.Countrys[values.location.selectedIndex].countryId,
				countryId: $scope.id
				
		};
			$http({
				method:'POST',
				url:'/'+window.location.href.split('/')[3]+'/embassy',
				data: data,
				headers:{'Content-Type' :'application/json'}
			}).success(function(response){
				if(response.phone!="Error"){
				$scope.embassies[index]=response;
				$scope.embassies[index].location=response.location;
				}else{
					alert("Embassy with this phone already exists");
				}
			}).error(function(response){
				console.log(response);
				});
		}else
			alert("Select location and enter the phone");
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"&&values[i].name!="embassyID"&&values[i].name!="location"){
				values[i].value="";
			}
		}
		$scope.selected_value=$scope.Countrys[$scope.Countrys.length-1];
	}
	$scope.sendRequestCountries=function(){
		var form = document.createElement("form");
		form.action='countries';
		document.body.appendChild(form);
	    form.submit();
	}
	
})

</script>

</body>
</html>