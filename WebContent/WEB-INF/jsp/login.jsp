<html>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>Yimin Admin</title>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap-theme.min.css' />" rel="stylesheet"></link>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in to continue</h1>
            <div class="account-wall">
                <form class="form-signin" method="post" action="checkLogin">
                <input type="text" class="form-control" name="email" placeholder="Email" required autofocus>
                <input type="password" class="form-control" name="pass" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Sign in</button>
               
                </form>
            </div>
          </div>
    </div>
</div>
</body>
</html>
