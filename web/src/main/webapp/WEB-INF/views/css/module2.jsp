<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- portfolio section -->
			<section id="portfolio">
				<div class="container">
					<div class="row">
					
					<div class="sec-title text-center wow animated fadeInDown">
							<h2>S2 Module</h2>
							<p>차의 전복, 급정거를 빠르게 감지하여 위치정보를 전송합니다.</p>
						</div>
					
					 <!-- row 3 -->
        <div class="row">
          <div class="col-sm-6">
            <img src="${pageContext.request.contextPath}/resources/bootstrap3.3.5/img/model22.png" class="img-responsive">
          </div>
          <div class="col-sm-6">
            <h3>S2 안전 모듈</h3>
            <p>
            사고가 발생하면 GPS를 통해 자동으로 운전자의 위치를 서버에 보고합니다. 그 후 서버에서 위치를 기반으로 관계기관의 빠른 대처를 유도하고 사고데이터를 open API화 하여 관리합니다.
            </p>
            <p>
            2가지 인식장치로 정밀한 사고 유무를 판단합니다. 자이로 센서는 차량의 pitch, roll을 계산하여 전복을 감지합니다. 압력센서는 급정지시 스프링이 달린 해머가 압력센서에 주는 충격을 감지합니다. 사고가 인식되면 GPS 정보를 wifi장치를 통해 서버로 전송합니다.
            </p>
          </div>
        </div>
        <hr>
					
			<!-- row 4 -->
        <div class="row">
          <div class="col-sm-6">
            <h3>Overview</h3>
            <p>
              <Strong>Size:</Strong> width 8mm * length 8.5mm * height 5.7mm<br>
              <Strong>Modules:</Strong> Grove-IMU 9DOF v2.0, LED, Speaker, GY-GPS6MV2, FSR, ESP8266<br>
              <Strong>Material, Charging type:</Strong> PLA, USB 2.0<br>
              <Strong>Gyro Sensor:</Strong> The gyro sensor Grove-IMU 9DOF v2.0 detects sudden inclination of the vehicle and announces a rollover accident.<br>
              <Strong>Force sensitive resistor:</Strong> The FSR pressure sensor detects sudden stop due to shock.<br>
              <Strong>Wifi Module, GPS:</Strong> The GY-GPS6MV2 receives the vehicle's location information and immediately sends it through the ESP8266 wifi device.<br>
               
            </p>
          </div>
          <div class="col-sm-6">
            <img src="${pageContext.request.contextPath}/resources/bootstrap3.3.5/img/modelingall.jpg" class="img-responsive">
          </div>
        </div>
      </div>
    </section>		
					
					
					
					
					</div>
	</div>
</section>
<!-- end about section -->