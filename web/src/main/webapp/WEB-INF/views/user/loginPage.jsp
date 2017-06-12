<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<center>
<h2>로그인</h2>
<form action='<c:url value="/user/login"/>' method="post">
<table border = "1" bgcolor = "lightyellow" cellpadding = "5">
<tr>
<td> ID  </td>
<td> <input type="text" name="email" id="email"/></td>
</tr>
<tr>
<td>PW </td>
<td> <input type="password" name="passwd" id="passwd"/></td>
</tr>

<tr>
<td colspan = "2" align = "center">
	<input type="submit" value="로그인">
	<input type="button" value="회원가입" onclick="location.href='member'">
</td>
</tr>
</table>
</form>
<input type="button" value="홈" onclick="location.href='/web'"><br>
${errMsg}
</center>
<%-- <P>  The time on the server is ${serverTime}. </P> --%>
<%-- <p>principal : <sec:authentication property="principal"/></p> --%>
<%-- <form action='<c:url value="/user/insertUser"/>' method="post"> --%>
<!-- 	<input type="text" name="email" id="email"/> -->
<!-- 	<input type="password" name="passwd" id="passwd"/> -->
<!-- 	<select name="authority"> -->
<!-- 		<option value="ROLE_USER">사용자</option> -->
<!-- 		<option value="ROLE_ADMIN">관리자</option> -->
<!-- 	</select> -->
<!-- 	<input type="submit" value="회원가입"> -->
<!-- </form> -->
</body>
</html>