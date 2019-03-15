<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta charset="utf-8">
       <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="/tmsApp/css/login.css" >
<title>Insert title here</title>
</head>
<body class="errorpage_401">
<div class="errorpage" >
        <h2><strong>Authorised Required !!</strong></h2>
 			 <c:if test="${sessionScope.isFrontDesk eq 'FRONT_DESK'}">
        			<a href="/tmsApp/tms/flmDashboard/admin"><h4>Click here to go back</h4></a>
    			</c:if>
    			 <c:if test="${sessionScope.isFrontDesk ne 'FRONT_DESK'}">
        			<a href="/tmsApp/tms/home"><h4>Click here to go back</h4></a>
    			</c:if>
     </div>
</body>
</html>