#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
SoftwareSerial mySerial(5, 4); // RX, TX nodemcu 

const char *ssid = "dlink-1B48";
const char *password = "ilwkf79711";

const char* host = "192.168.0.148";

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

  float Gx = mySerial.parseFloat();
  float Gy = mySerial.parseFloat();
  float Gz = mySerial.parseFloat();

  
  Serial.print(Gx);
  Serial.print(Gy);
  Serial.print(Gz);
    
    //--------------------------------------
  delay(10);
  ++value;

 
  
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
  url += Gz;
  
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
    
  
  delay(100);
  Serial.println("aaaaaaaaaaaaa");
  delay(800);
  
  
}
