<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>


<jsp:include page="/WEB-INF/views/css/head2.jsp"></jsp:include>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript">
	console.log("hello");
	$(function() {
		$
				.ajax({
					url : "http://52.231.26.49:8080/web/json/datas",
					type : 'get',
					contentType : "application/x-www-form-urlencoded",
					dataType : "json",
					success : function(data, status) {
						console.log("Sucess!!");
						console.log(data);
						console.log(status);
						var temp = new Array();
						temp = data.totalAccidents;
						console.log("temp" + temp);
						var str = "";
						for ( var i in temp) {
							str = str.concat("<tr>");
							str = str.concat("<td>");
							str = str.concat(temp[i].serialnum);
							str = str.concat("</td>");
							str = str.concat("<td>");
							str = str.concat(temp[i].latitude);
							str = str.concat("</td>");
							str = str.concat("<td>");
							str = str.concat(temp[i].longitude);
							str = str.concat("</td>");
							str = str.concat("<td>");
							str = str.concat(temp[i].atime);
							str = str.concat("</td>");
							str = str.concat("<td>");
							str = str.concat(temp[i].status);
							str = str.concat("</td>");
							str = str.concat("<td>");
							str = str
									.concat("<form action='/web/webUpdate.in' method='post'>");
							str = str
									.concat("<input type='hidden' name='serialnum' value="+temp[i].serialnum+">");
							if (temp[i].status == "occured") {
								str = str
										.concat("<input type='hidden' name='status' value="+"processing"+">");
							} else if (temp[i].status == "processing") {
								str = str
										.concat("<input type='hidden' name='status' value="+"solved"+">");
							} else {
								str = str
										.concat("<input type='hidden' name='status' value="+"occured"+">");
							}
							if (temp[i].status == "occured") {
								str = str
										.concat("<input type='submit' value="+"processing"+">");
							} else if (temp[i].status == "processing") {
								str = str
										.concat("<input type='submit' value="+"solved"+">");
							} else {
								str = str
										.concat("<input type='submit' value="+"occured"+">");
							}
							str = str.concat("</form>");
							str = str.concat("</td>");
							str = str.concat("</tr>");
						}
						$("#table1").append(str).trigger('create');
					},
					error : function(xhr, desc, err) {
						console.log(xhr);
						console.log("desc: " + desc + "\nErr:" + err);
					}
				});
	})
</script>
</head>
<body id="body">
	<div class="site-content" role="main">
		<section id="contact">
			<div class="container">
				<div class="row">
					<div class="sec-title text-center wow animated fadeInDown">
						<h2>Admin Page</h2>
						<p>
							<sec:authentication property="principal.username" />
							님 환영합니다.
						</p>
					</div>
					<a href=" <c:url value="/user/logout"/>"
						class="btn btn-border btn-effect pull-right">Logout</a> <a
						href="<c:url value="/"/>"
						class="btn btn-border btn-effect pull-right">Home</a>
					<table id="table1"
						class="table table-bordered wow animated fadeInRight">
						<tr>
							<td>serialnum</td>
							<td>latitude</td>
							<td>longitude</td>
							<td>time</td>
							<td>status</td>
							<td>status change</td>
						</tr>
					</table>
				</div>
			</div>
		</section>
	</div>
	<jsp:include page="/WEB-INF/views/css/main_jquery.jsp"></jsp:include>
</body>
</html>