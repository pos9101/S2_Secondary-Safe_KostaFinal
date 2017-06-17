<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<section id="reference">
	<div class="container">
		<div class="row">

			<div class="sec-title text-center wow animated fadeInDown">
				<h2>Open API references</h2>
				<p>사고정보를 지역, 시간, 사고유형 등의 조건을 통해 조회합니다.</p>
			</div>
			<table class="table table-bordered wow animated fadeInRight">
				<tr class="success">
					
					<th>응답필드</th>
					<th>값</th>
					<th>설명</th>
				</tr>

				<tr>
					<td>serialnum</td>
					<td>문자열</td>
					<td>제품 module의 고유번호</td>
				</tr>
				
				<tr>
					<td>latitude</td>
					<td>문자열</td>
					<td>사건 발생 위도</td>
				</tr>
				
				<tr>
					<td>longitude</td>
					<td>문자열</td>
					<td>사건 발생 경도</td>
				</tr>
				
				<tr>
					<td>atime</td>
					<td>문자열</td>
					<td>사건 발생 시간</td>
				</tr>
				
				<tr>
					<td>status</td>
					<td>문자열</td>
					<td>사건 해결 여부 (occured: 사고발생, processing: 조치진행중, solved:사고해결)</td>
				</tr>
				</table>
				
			<p><Strong>JSON 요청 URL :</Strong> http://52.231.26.49:8080/web/json/datas
			</p>
			
			<p><Strong>Example(예제) : </Strong>
			{"totalAccidents":[{"serialnum":"SF16061500","latitude":37.398336,"longitude":127.104655,"atime":"2017.06.15/09:09","status":"processing"},{"serialnum":"SF16061501","latitude":37.408129,"longitude":127.115277,"atime":"2017.06.15/09:10","status":"occured"},{"serialnum":"SF17060800","latitude":37.4,"longitude":127.11,"atime":"2017.06.16/10:01","status":"occured"}]}
			<br>
			위와 같이 Json 형태가 구성되어있다. URI 내용을 String에 담아, totalAccidents안의 배열을 사용하면 된다. </p>
			
			
			<p><Strong>Ajax, JQuery로 사용하는 방법</Strong> <br>
			
			$.getJSON('http://52.231.26.49:8080/web/json/datas', function(data) {
			console.log(data.totalAccidents[0].latitude);
			console.log(data.totalAccidents[0].longitude);
			}</p>
			
			
			
			

				</div>
				</div>
				</section>