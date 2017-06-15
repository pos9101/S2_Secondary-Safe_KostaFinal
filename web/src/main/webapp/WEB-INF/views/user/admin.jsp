<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Managerpage</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css"/>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" ></script>
<script type="text/javascript">
	console.log("hello");
	$(function(){
		$.ajax({
			url : "http://localhost:8080/web/json/datas",
			type : 'get',
			contentType : "application/x-www-form-urlencoded",
			dataType : "json",
			success : function(data, status) {
				console.log("Sucess!!");
				console.log(data);
				console.log(status);
				var temp = new Array();
				temp = data.totalAccidents;
				console.log("temp"+temp);
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
					str = str.concat("<form action='/web/webUpdate.in' method='post'>");
					str = str.concat("<input type='hidden' name='serialnum' value="+temp[i].serialnum+">");
					if(temp[i].status == "occured"){
						str = str.concat("<input type='hidden' name='status' value="+"processing"+">");
					}else if(temp[i].status == "processing"){
						str = str.concat("<input type='hidden' name='status' value="+"solved"+">");
					}else{
						str = str.concat("<input type='hidden' name='status' value="+"occured"+">");
					}
					if(temp[i].status == "occured"){
					str = str.concat("<input type='submit' value="+"processing"+">");
					}else if(temp[i].status == "processing"){
					str = str.concat("<input type='submit' value="+"solved"+">");
					}else{
					str = str.concat("<input type='submit' value="+"occured"+">");
					}
					str = str.concat("</form>");
					str = str.concat("</td>");
					str = str.concat("</tr>");
				}	
				$("#table1").append(str); 
			},
			error : function(xhr, desc, err) {
				console.log(xhr);
				console.log("desc: " + desc + "\nErr:" + err);
			}
		});
	})
</script>
</head>
<body>
<h1>Admin Page</h1>
<sec:authentication property="principal.username"/>님 환영합니다.
<a href=" <c:url value="/user/logout"/>">Logout</a>
<p><a href="<c:url value="/"/>">Home</a> </p>
</body>
<table id="table1">
<tr><td>serialnum</td><td>latitude</td><td>longitude</td><td>time</td><td>status</td></tr>
</table>
</html>