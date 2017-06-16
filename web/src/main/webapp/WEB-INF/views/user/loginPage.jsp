<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
<jsp:include page="/WEB-INF/views/css/head2.jsp"></jsp:include>
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
</td>
</tr>
</table>
</form>
<input type="button" value="홈" onclick="location.href='/web'"><br>
${errMsg}
</center>
</body>
</html>