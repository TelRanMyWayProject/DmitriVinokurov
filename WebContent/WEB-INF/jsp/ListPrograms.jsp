<!DOCTYPE html>
<html>
<head>
<title>"Your-way" service administration</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/static/js/angular.min.js" />"></script>
<script src="<c:url value="/static/js/angular-datepicker.js" />"></script>
<link href="<c:url value='/static/css/angular-datepicker.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>
<style>
[ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak {
  display: none !important;
}
</style>
</head>
<body data-ng-app="programs_app" data-ng-cloak >
<div data-ng-controller="programs_contr" class="col-xs-90 form-group">
<p>{{CountryName}} <button data-ng-click=sendRequestCountries()>back</button></p>
<p style="max-width:15%;">Filter by name: <input type="text" data-ng-model="search.name" class="form-control input-sm"></p>
<div class="panel-body">
<table class="table table-hover table-condensed">
		<colgroup>
					<col width="9%"/>
					<col width="7%"/>
	          		<col width="22%"/>
       		  		<col width="25%"/>
       		  		<col width="3%"/>
       		  		<col width="10%"/>
       		  		<col width="6%"/>
       		  		<col width="6%"/>
       		  		<col width="6%"/>
       		  		<col width="6%"/>
       	</colgroup>
	<thead>
		<tr>
		<th>name</th>
		<th>category</th>
		<th>link</th>
		<th>description</th>
		<th></th>
		<th>expiration</th>
		<th></th>
		<th>Steps</th>
		<th>Documents</th>
		<th>Custom data</th>
		</tr>
	</thead>
	<tr data-ng-repeat="prog in progr|filter:search" class="repeats">
		<td><input type="text" name="name" value="{{prog.name}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="category" value="{{prog.category}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="link" value="{{prog.link}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="text" name="description" value="{{prog.description}}" class='{{$index}} form-control input-sm'></td>
		<td><input type="checkbox" name="enabled"  data-ng-model=prog.enabled class='{{$index}} checkbox'></td>
		<td><datepicker date-format="yyyy-MM-dd" selector="form-control" date-set={{prog.expiration|date:'yyyy-MM-dd'}}>
<input type="text" value="{{prog.expiration|date:'yyyy-MM-dd'}}" class="form-control {{$index}}"/>
								</datepicker></td>
		<td><button data-ng-click=SendEditRequest($event) name="button" class={{$index}} >Edit</button></td>
		<td><button data-ng-click=sendStepRequest($event) name="button" class={{$index}}>Steps</button></td>
		<td><button data-ng-click=sendDocumentsRequest($event) name="button" class={{$index}}>Documents</button></td>
		<td><button data-ng-click=sendProgramCustomDataRequest($event) name="button" class={{$index}}>requirements</button></td>
		<td><input type="hidden" name="programId" value={{prog.programId}} class={{$index}}></td>
	</tr>
	<tr>
		<td><input type="text" class="adding form-control input-sm" name="name" placeholder="name"></td>
		<td><input type="text" class="adding form-control input-sm" name="category" placeholder="category"></td>
		<td><input type="text" class="adding form-control input-sm" name="link" placeholder="link"></td>
		<td><input type="text" class="adding form-control input-sm" name="description" placeholder="description"></td>
		<td><input type="checkbox" class="adding checkbox" name="enabled"></td>
		<td><datepicker date-format="yyyy-MM-dd" selector="form-control" date-set={{prog.expiration|date:'yyyy-MM-dd'}}>
<input type="text" class="form-control adding" placeholder="yyyy-MM-dd"/>
								</datepicker></td>
		<td><button data-ng-click=AddProgram() name="button">Add program</button></td>
		<td><input type="hidden" class="adding" name="CountryId" value={{id}}></td>
	</tr>
</table>
</div>
</div>
<script>


var appl=angular.module('programs_app',['720kb.datepicker']);
appl.controller('programs_contr',function($scope,$http,$filter){
	var json=JSON.parse('${results}');
	$scope.id=json.CountryId;
	$scope.CountryName=json.name;
	
	
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
				enabled : values.enabled.checked.toString(),
				expiration : values[5].value,
				countryId: $scope.id
		};
		if(values.name.value!=""){
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
				alert("Program with this name already exists");
			}
		}).error(function(response){
			console.log(response);
			});
		}else
			alert("Enter the name");
		for(i=0;i<values.length;i++){
			if(values[i].name!="button"){
				values[i].value="";
			}
			if(values[i].name=="enabled"){
				values[i].checked=false;
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
				enabled : values.enabled.checked.toString(),
				expiration : values[5].value,
				programId : values.programId.value,
				countryId: $scope.id
		};
		if(values.name.value!=""){
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
				$scope.progr[index].enabled=response.enabled;
				$scope.progr[index].expiration=response.expiration;
			}else{
				values.name.value=$scope.progr[index].name;
				values.category.value=$scope.progr[index].category;
				values.link.value=$scope.progr[index].link;
				values.description.value=$scope.progr[index].description;
				values[5].value=$filter('date')($scope.progr[index].expiration,"yyyy-MM-dd");
				$scope.progr[index].enabled=response.enabled;
				alert("Program with this name already exists");
			}
		}).error(function(response){
			console.log(response);
			});
		}else{
			values.name.value=$scope.progr[index].name;
			values.category.value=$scope.progr[index].category;
			values.link.value=$scope.progr[index].link;
			values.description.value=$scope.progr[index].description;
			values[5].value=$filter('date')($scope.progr[index].expiration,"yyyy-MM-dd");
			alert("Enter the name");
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
		form.action='progsteps';
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
		form.action='progcustdata';
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