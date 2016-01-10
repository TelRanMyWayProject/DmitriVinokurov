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
<div data-ng-app="embassies_app" data-ng-controller="embassies_contr" data-ng-init=GetAllCountries()>
<button data-ng-click=sendRequestCountries()>{{CountryName}}</button>
<table>
	<tr>
		<th>phone</th>
		<th>link</th>
		<th>address</th>
		<th>location</th>
		<th></th>
	</tr>
	<tr data-ng-repeat="emb in embassies">
		<td><input type="text" name="phone" placeholder="{{emb.phone}}" class={{$index}}></td>
		<td><input type="text" name="link" placeholder="{{emb.link}}" class={{$index}}></td>
		<td><input type="text" name="address" placeholder="{{emb.address}}" class={{$index}}></td>
		<td>
			<select name="location" 
			data-ng-model="emb.location.countryId" 
			data-ng-options="country.countryId as country.name for country in Countrys" 
			class={{$index}}>
			</select>
		</td>
		<td><button data-ng-click=SendEditRequest($event) name="button" class={{$index}}>Edit</button></td>
		<td><input type="hidden" name="embassyID" value={{emb.embassyID}} class={{$index}}></td>
	</tr>
	<tr>
		<td><input type="text" class="adding" name="phone"></td>
		<td><input type="text" class="adding" name="link"></td>
		<td><input type="text" class="adding" name="address"></td>
		<td>
			<select name="location" 
			data-ng-model="selected_value" 
			data-ng-options="country.countryId as country.name for country in Countrys" 
			class="adding">
			</select>
		</td>
		<td><button data-ng-click=AddEmbassy() name="button">Add embassy</button></td>
	</tr>
</table>
<h3>{{msg}}</h3>
</div>
<script>
var appl=angular.module('embassies_app',[]);

appl.controller('embassies_contr',function($scope,$http){
	var json=JSON.parse('${results}');
	$scope.id=json.CountryId;
	$scope.CountryName=json.name;
	$scope.msg;

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
		  }, function errorCallback(response) {
			 console.log(response);
		  });
	};
	
	
	$scope.SendEditRequest=function(event){
		var index=event.currentTarget.className;
		var values=document.getElementsByClassName(index);
		
		var data={
				phone : values.phone.value,
				link : values.link.value,
				address: values.address.value,
				location: $scope.Countrys[values.location.options[values.location.selectedIndex].value].countryId,
				embassyID: values.embassyID.value
				
		};
		
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/embassy_edit',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.phone!="Error"){
				
			$scope.embassies[index].phone=response.phone;
			$scope.embassies[index].link=response.link;
			$scope.embassies[index].address=response.address;
			$scope.embassies[index].location=response.location;
			$scope.msg="";
			}else{
				$scope.msg="Embassy with this phone already exists";
			}
		}).error(function(response){
			console.log(response);
			});
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"&&values[i].name!="embassyID"&&values[i].name!="location"){
				values[i].value="";
			}
		}
	}
	
	$scope.AddEmbassy=function(){
		var values=document.getElementsByClassName("adding");
		var index=$scope.embassies.length;
		if(values.location.options[values.location.selectedIndex].value!="?"){	
		var data={
				phone : values.phone.value,
				link : values.link.value,
				address: values.address.value,
				location: $scope.Countrys[values.location.options[values.location.selectedIndex].value].countryId,
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
				$scope.msg="";
				}else{
					$scope.msg="Embassy with this phone already exists";
				}
			}).error(function(response){
				console.log(response);
				});
			for(i=0;i<values.length;i++){
				if(values[i].name!="button"&&values[i].name!="embassyID"){
					values[i].value="";
				}
			}
		}else
			$scope.msg="Select location";
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