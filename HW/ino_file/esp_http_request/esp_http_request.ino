#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(5, 4); // RX, TX nodemcu D2 D3

const char *ssid = "win10op";
const char *password = "hi123456";

const char* host = "192.168.0.180";

void setup() {
  Serial.begin(9600);
  delay(10);

  // We start by connecting to a WiFi network

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }

  //Serial.println("");
  //Serial.println("WiFi connected");  
  //Serial.println("IP address: ");
  //Serial.println(WiFi.localIP());
  Serial.println("setup com");
  delay(500);
}

int value = 0;

void loop() {
// GPS sensor value
  float Gx = mySerial.parseFloat();
  float Gy = mySerial.parseFloat();
  String ID = "Safe";


  
  
  Serial.println(Gx);
  Serial.println(Gy);

    
    //--------------------------------------
  delay(10);

  if(Gx>180){
    delay(10);
  }else if(Gy>180){
    delay(10);
  }else{
      // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 8090;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }


  String url = "/TestGetArduino/receive.do?test=";
    
  url += Gx;
  url += "%20";
  url += Gy;
  url += "%20";
  url += ID;
  
  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
               
  delay(100);
  int timeout = millis() + 5000;
  while (client.available() == 0) {
    if (timeout - millis() < 0) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }
    
  }  

 
  

    
  
  delay(100);
  
  
  
  
}
