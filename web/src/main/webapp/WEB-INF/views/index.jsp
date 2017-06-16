<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
<jsp:include page="/WEB-INF/views/css/head2.jsp"></jsp:include>

</head>



<body id="body">
	<jsp:include page="css/header.jsp"></jsp:include>
	<main class="site-content" role="main">
	<jsp:include page="css/slider.jsp"></jsp:include>
	<jsp:include page="css/introduction.jsp"></jsp:include>
	<jsp:include page="css/module.jsp"></jsp:include>
	<jsp:include page="css/api_reference.jsp"></jsp:include>


	<jsp:include page="css/accident_map.jsp"></jsp:include>
	<jsp:include page="css/contact.jsp"></jsp:include>
	<jsp:include page="css/main_jquery.jsp"></jsp:include>
</main>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBjbeIk1uoQYVycQ8q7SHqJKbt3bFlsE5w"
    async defer></script>                     
</body>
</html>
