<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
<jsp:include page="/WEB-INF/views/css/head2.jsp"></jsp:include>
</head>
<body>


	<center>
		<br> <a href='/web'><img
			src="${pageContext.request.contextPath}/resources/bootstrap3.3.5/img/logo_cut.png"></a>


		<form action='<c:url value="/user/login"/>' method="post">
			<div class="container-fluid">
				<div class="row">

					<hgroup>
					<div class="container">
						<p class="text-center">
							ID <input type="text" name="email" id="email" />
						</p>
					</div>
					</hgroup>

					<hgroup>
					<div class="container">
						<p class="text-center">
							PW <input type="password" name="passwd" id="passwd" />
						</p>
					</div>
					</hgroup>
					<input type="submit" value="LOGIN" class="btn btn-blue btn-effect">
				</div>
		</form>
		<br> <input type="button" value="HOME"
			onclick="location.href='/web'" class="btn btn-blue btn-effect"><br>
		${errMsg}
	</center>

</body>
</html>