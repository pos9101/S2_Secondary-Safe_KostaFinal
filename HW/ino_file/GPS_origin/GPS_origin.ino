#include <SoftwareSerial.h>
 
SoftwareSerial gpsSerial(5,6);
 
void setup() {
  Serial.begin(57600);
  Serial.println("Start GPS... ");
  gpsSerial.begin(9600);
}
 
void loop() {
  if(gpsSerial.available())
  {
    Serial.write(gpsSerial.read());
  }
}

