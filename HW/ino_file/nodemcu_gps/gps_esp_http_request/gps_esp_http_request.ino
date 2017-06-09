#include <TinyGPS++.h> 
#include <SoftwareSerial.h> 
#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

static const int RXPin = 13, TXPin = 15; //gps 읽어올 핀  d7,d8
static const int GPSBaud = 9600; 
TinyGPSPlus gps; 
SoftwareSerial gpsserial(RXPin, TXPin); 

SoftwareSerial mySerial(5, 4); // RX, TX nodemcu d1 d2


const char *ssid = "win10op";
const char *password = "hi123456";

const char* host = "192.168.0.180";




void setup() {
  Serial.begin(9600);
  gpsserial.begin(GPSBaud); 
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
  float x,y;
  printFloat(gps.location.lat(), gps.location.isValid(), 11, 6); 
  printFloat(gps.location.lng(), gps.location.isValid(), 12, 6); 
  x=gps.location.lat();
  y=gps.location.lng();
  if (millis() > 5000 && gps.charsProcessed() < 10) 
  Serial.println(F("No GPS data received: check wiring")); 
  
// gyro sensor value

    
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
    
  url +=x; 
  url += y;

  
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
  delay(800);
  
  
}

static void printFloat(float val, bool valid, int len, int prec) 
{ 
  if (!valid) 
  { 
    while (len-- > 1) 
    Serial.print('0'); 
    Serial.print(' '); 
  } 
  else 
  { 
    Serial.print(val, prec); 
    int vi = abs((int)val); 
    int flen = prec + (val < 0.0 ? 2 : 1); // . and - 
    flen += vi >= 1000 ? 4 : vi >= 100 ? 3 : vi >= 10 ? 2 : 1; 
    for (int i=flen; i<len; ++i) 
    Serial.print(' '); 
  } 
  delay(0); 
} 
