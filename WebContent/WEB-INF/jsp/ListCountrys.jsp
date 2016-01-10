<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>"Your-way" service administration</title>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
<style>
::-webkit-input-placeholder { /* WebKit browsers */
    color:    blue;
    opacity: 1;
}
:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
    color:    blue;
    opacity: 1;
}
::-moz-placeholder { /* Mozilla Firefox 19+ */
    color:    blue;
    opacity: 1;
}
:-ms-input-placeholder { /* Internet Explorer 10+ */
    color:    blue;
    opacity: 1;
}

</style>
</head>
<body>
<div data-ng-app="country_app" data-ng-controller="countrys_contr">
<p>Filter by name: <input type="text" data-ng-model="search.name"></p>
<table>
	<tr>
		<th>name</th>
		<th>link</th>
		<th></th>
		<th>Embassy</th>
		<th>Programs</th>
	</tr>
	<tr data-ng-repeat="cr in Countrys|filter:search">
		<td><input type="text" name="name" placeholder="{{cr.name}}" class={{$index}}></td>
		<td><input type="text" name="link" placeholder="{{cr.link}}" class={{$index}}></td>
		<td><button data-ng-click=SendEditRequest($event) name="button" class={{$index}}>Edit</button></td>
		<td><button data-ng-click=sendEmbassyRequest($event) name="button" class={{$index}}>Embassies</button></td>
		<td><button data-ng-click=sendProgramsRequest($event) name="button" class={{$index}}>Programs</button></td>
		<td><input type="hidden" name="countryId" value={{cr.countryId}} class={{$index}}></td>
	</tr>
	<tr>
		<td><input type="text" class="adding" name="name"></td>
		<td><input type="text" class="adding" name="link"></td>
		<td><button data-ng-click=AddCountry() name="button">Add country</button></td>
	</tr>
</table>
<h3>{{msg}}</h3>
</div>
<script>
var appl=angular.module('country_app',[]);
appl.controller('countrys_contr',function($scope,$http){
	$scope.msg;
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
		
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/country',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.name!="Error"){
				$scope.Countrys[index]=response;
				$scope.msg="";
			}else{
				$scope.msg="Country with this name already exists";
			}
			
		}).error(function(response){
				console.log(response);
			});
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
				$scope.msg="";
			}else{
				$scope.msg="Country with this name already exists";
			}
			
		}).error(function(response){
				console.log(response);
			});
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"&&values[i].name!="countryId"){
				values[i].value="";
			}
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