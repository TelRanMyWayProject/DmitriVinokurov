<%@ page language="java" contentType="text/html; charset=windows-1255" pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" data-ng-app="AdminFirstApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Yimin Admin</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>


<style>
</style>
</head>
<body>
	<div class="panel-body">
		<div class="col-xs-2 form-group" id="AdminFirst">
			<label><H3>Yimin Project</H3></label><br/><br/>
			<label>Select Operation</label><br/>
			<table class="table">
				<tbody>
					<tr>
						<td>
				   			<form name="Countries" action="countries" method="GET">
			    		    	<input class="btn btn-default" type="submit" value="Countries">
					    	</form>
						</td>
						<td>
				   			<form name="Steps" action="steps" method="GET">
			    		    	<input class="btn btn-default" type="submit" value="Steps">
					    	</form>
						</td>
						<td>
				   			<form name="FieldNames" action="fieldnames" method="GET">
			    		    	<input class="btn btn-default" type="submit" value="Field Names">
					    	</form>
						</td>
					</tr>
					<tr>
						<!-- <td>
				   			<form name="Programs" action="programs" method="GET">
			    		    	<input class="btn btn-default" type="submit" value="Programs">
					    	</form>
						</td> -->
						<td>
						</td>
						<td>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
