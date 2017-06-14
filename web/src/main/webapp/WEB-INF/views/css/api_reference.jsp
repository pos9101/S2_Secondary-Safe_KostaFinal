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
					<td>사건발생시간</td>
				</tr>
				
				<tr>
					<td>status</td>
					<td>문자열</td>
					<td>사건 해결 여부 (occured: 사고발생, processing: 조치진행중, solved:사고해결)</td>
				</tr>
				</table>

				</div>
				</div>
				</section>