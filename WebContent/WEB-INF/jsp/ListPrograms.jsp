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
<div data-ng-app="programs_app" data-ng-controller="programs_contr">
<p>{{CountryName}}</p>
<p><button data-ng-click=sendRequestCountries()>back</button></p>
<p>Filter by name: <input type="text" data-ng-model="search.name"></p>
<table>
	<tr>
		<th>name</th>
		<th>category</th>
		<th>link</th>
		<th>description</th>
		<th>Enabled</th>
		<th>expiration</th>
		<th>
		<th>Steps</th>
		<th>Documents</th>
		<th>Custom data</th>
	</tr>
	<tr data-ng-repeat="prog in progr|filter:search" class="repeats">
		<td><input type="text" name="name" placeholder="{{prog.name}}" class={{$index}}></td>
		<td><input type="text" name="category" placeholder="{{prog.category}}" class={{$index}}></td>
		<td><input type="text" name="link" placeholder="{{prog.link}}" class={{$index}}></td>
		<td><input type="text" name="description" placeholder="{{prog.description}}" class={{$index}}></td>
		<td><input type="text" name="enabled" placeholder="{{prog.enabled}}" class={{$index}}></td>
		<td><input type="text" name="expiration" placeholder="{{prog.expiration|date:'MM/dd/yyyy'}}" class={{$index}}></td>
		<td><button data-ng-click=SendEditRequest($event) name="button" class={{$index}} >Edit</button></td>
		<td><button data-ng-click=sendStepRequest($event) name="button" class={{$index}}>Steps</button></td>
		<td><button data-ng-click=sendDocumentsRequest($event) name="button" class={{$index}}>Documents</button></td>
		<td><button data-ng-click=sendProgramCustomDataRequest($event) name="button" class={{$index}}>requirements</button></td>
		<td><input type="hidden" name="programId" value={{prog.programId}} class={{$index}}></td>
	</tr>
	<tr>
		<td><input type="text" class="adding" name="name"></td>
		<td><input type="text" class="adding" name="category"/></td>
		<td><input type="text" class="adding" name="link"></td>
		<td><input type="text" class="adding" name="description"></td>
		<td><input type="text" class="adding" name="enabled"></td>
		<td><input type="text" class="adding" name="expiration"></td>
		<td><button data-ng-click=AddProgram() name="button">Add program</button></td>
		<td><input type="hidden" class="adding" name="CountryId" value={{id}}></td>
	</tr>
</table>
<h3>{{msg}}</h3>
</div>
<script>
var appl=angular.module('programs_app',[]);
appl.controller('programs_contr',function($scope,$http){
	var json=JSON.parse('${results}');
	$scope.id=json.CountryId;
	$scope.CountryName=json.name;
	$scope.msg;
	
	$scope.getAllPrograms=function(){
		$http({
		 	method: 'GET',
			url: '/'+window.location.href.split('/')[3]+'/list_programs'+'?countryId='+$scope.id
		  }).then(function successCallback(response) {
			   $scope.progr=response.data;
		  }, function errorCallback(response) {
			 console.log(response);
		  });
	}
	$scope.getAllPrograms();
	
	$scope.AddProgram=function(){
		var values=document.getElementsByClassName("adding");
		var index=$scope.progr.length;
		
		var data={
				name : values.name.value,
				category : values.category.value,
				link : values.link.value,
				description : values.description.value,
				enabled : values.enabled.value,
				expiration : values.expiration.value,
				countryId: $scope.id
		};
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/program',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.name!="Error"){
				$scope.progr[index]=response;
				$scope.progr[index].Enabled=response.enabled.toString();
				$scope.progr[index].expiration=response.expiration;
				$scope.msg="";
			}else{
				$scope.msg="Program with this name already exists";
			}
		}).error(function(response){
			console.log(response);
			});
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"&&values[i].name!="programId"){
				values[i].value="";
			}
		}
	}
	
	
	$scope.SendEditRequest=function(event){
		var index=event.currentTarget.className;
		var values=document.getElementsByClassName(index);
		var data={
				name : values.name.value,
				category : values.category.value,
				link : values.link.value,
				description : values.description.value,
				enabled : values.enabled.value,
				expiration : values.expiration.value,
				programId : values.programId.value,
				countryId: $scope.id
		};
		$http({
			method:'POST',
			url:'/'+window.location.href.split('/')[3]+'/program_edit',
			data: data,
			headers:{'Content-Type' :'application/json'}
		}).success(function(response){
			if(response.name!="Error"){
				for(var i=0;i<$scope.progr.length;i++){
					 if($scope.progr[i].programId==response.programId){
						index=i;
					} 
				}
				$scope.progr[index].name=response.name;
				$scope.progr[index].category=response.category;
				$scope.progr[index].link=response.link;
				$scope.progr[index].description=response.description;
				$scope.progr[index].enabled=response.enabled.toString();
				$scope.progr[index].expiration=response.expiration;
				$scope.msg="";
			}else{
				$scope.msg="Program with this name already exists";
			}
		}).error(function(response){
			console.log(response);
			});
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"&&values[i].name!="programId"){
				values[i].value="";
			}
		}
		
	}
	$scope.sendRequestCountries=function(){
		var form = document.createElement("form");
		form.action='countries';
		document.body.appendChild(form);
	    form.submit();
	}
	
	$scope.sendStepRequest=function(event){
		var className=event.currentTarget.className;
		var values=document.getElementsByClassName(className);
		var form = document.createElement("form");
		form.action='Steps';
		var element=document.createElement("input");
		element.type="hidden";
		element.name=values[values.length-1].name;
		element.value=values[values.length-1].value;
		form.appendChild(element);
		document.body.appendChild(form);
	    form.submit();
	}
	$scope.sendDocumentsRequest=function(event){
		var className=event.currentTarget.className;
		var values=document.getElementsByClassName(className);
		var form = document.createElement("form");
		form.action='Documents';
		var element=document.createElement("input");
		element.type="hidden";
		element.name=values[values.length-1].name;
		element.value=values[values.length-1].value;
		form.appendChild(element);
		document.body.appendChild(form);
	    form.submit();
	}
	$scope.sendProgramCustomDataRequest=function(event){
		var className=event.currentTarget.className;
		var values=document.getElementsByClassName(className);
		var form = document.createElement("form");
		form.action='ProgramCustomData';
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