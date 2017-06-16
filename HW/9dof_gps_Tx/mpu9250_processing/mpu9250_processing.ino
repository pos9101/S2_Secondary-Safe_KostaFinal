
#include "I2Cdev.h"
#include "pitches.h"
#include <TinyGPS++.h> 
#include <SoftwareSerial.h> 

#include "MPU9250_9Axis_MotionApps41.h"
//#include "MPU9250.h" // not necessary if using MotionApps include file
#define LED_PIN 9

#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
#include "Wire.h"
#endif

// class default I2C address is 0x68
// specific I2C addresses may be passed as a parameter here
// AD0 low = 0x68 (default for SparkFun breakout and InvenSense evaluation board)
// AD0 high = 0x69
MPU9250 mpu;
//MPU9250 mpu(0x69); // <-- use for AD0 high




// uncomment "OUTPUT_READABLE_YAWPITCHROLL" if you want to see the yaw/
// pitch/roll angles (in degrees) calculated from the quaternions coming
// from the FIFO. Note this also requires gravity vector calculations.
// Also note that yaw/pitch/roll angles suffer from gimbal lock (for
// more info, see: http://en.wikipedia.org/wiki/Gimbal_lock)
#define OUTPUT_READABLE_YAWPITCHROLL




#define INTERRUPT_PIN 2  // use pin 2 on Arduino Uno & most boards
//#define LED_PIN 13 // (Arduino is 13, Teensy is 11, Teensy++ is 6)
bool blinkState = false;

// MPU control/status vars
bool dmpReady = false;  // set true if DMP init was successful
uint8_t mpuIntStatus;   // holds actual interrupt status byte from MPU
uint8_t devStatus;      // return status after each device operation (0 = success, !0 = error)
uint16_t packetSize;    // expected DMP packet size (default is 42 bytes)
uint16_t fifoCount;     // count of all bytes currently in FIFO
uint8_t fifoBuffer[64]; // FIFO storage buffer

            // orientation/motion vars
Quaternion q;           // [w, x, y, z]         quaternion container
VectorInt16 aa;         // [x, y, z]            accel sensor measurements
VectorInt16 aaReal;     // [x, y, z]            gravity-free accel sensor measurements
VectorInt16 aaWorld;    // [x, y, z]            world-frame accel sensor measurements
VectorFloat gravity;    // [x, y, z]            gravity vector
float euler[3];         // [psi, theta, phi]    Euler angle container
float ypr[3];           // [yaw, pitch, roll]   yaw/pitch/roll container and gravity vector

            // packet structure for InvenSense teapot demo
uint8_t teapotPacket[14] = { '$', 0x02, 0,0, 0,0, 0,0, 0,0, 0x00, 0x00, '\r', '\n' };


// ================================================================
// ===               INTERRUPT DETECTION ROUTINE                ===
// ================================================================

volatile bool mpuInterrupt = false;     // indicates whether MPU interrupt pin has gone high
void dmpDataReady() {
  mpuInterrupt = true;
}


static const int RXPin = 5, TXPin = 6;
static const int GPSBaud = 9600;

// The TinyGPS++ object 
TinyGPSPlus gps;

// The serial connection to the GPS device 
SoftwareSerial ss(RXPin, TXPin);
//====================================================================
int fsrPin = 0;     // the FSR and 10K pulldown are connected to a0
int fsrReading;     // the analog reading from the FSR resistor divider
          //====================================================================

          // ================================================================
          // ===                      INITIAL SETUP                       ===
          // ================================================================

void setup() {
  pinMode(LED_PIN, OUTPUT);
  ss.begin(GPSBaud);


  // join I2C bus (I2Cdev library doesn't do this automatically)
#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
  Wire.begin();
  Wire.setClock(400000); // 400kHz I2C clock. Comment this line if having compilation difficulties
#elif I2CDEV_IMPLEMENTATION == I2CDEV_BUILTIN_FASTWIRE
  Fastwire::setup(400, true);
#endif

  Serial.begin(9600);
  while (!Serial); // wait for Leonardo enumeration, others continue immediately


           // initialize device
  Serial.println(F("Initializing I2C devices..."));
  mpu.initialize();
  pinMode(INTERRUPT_PIN, INPUT);

  // verify connection
  Serial.println(F("Testing device connections..."));
  Serial.println(mpu.testConnection() ? F("MPU9250 connection successful") : F("MPU9250 connection failed"));


  // load and configure the DMP
  Serial.println(F("Initializing DMP..."));
  devStatus = mpu.dmpInitialize();

  // supply your own gyro offsets here, scaled for min sensitivity
  mpu.setXGyroOffset(220);
  mpu.setYGyroOffset(76);
  mpu.setZGyroOffset(-85);
  mpu.setZAccelOffset(1788); // 1688 factory default for my test chip

                 // make sure it worked (returns 0 if so)
  if (devStatus == 0) {
    // turn on the DMP, now that it's ready
    Serial.println(F("Enabling DMP..."));
    mpu.setDMPEnabled(true);

    // enable Arduino interrupt detection
    Serial.println(F("Enabling interrupt detection (Arduino external interrupt 0)..."));
    attachInterrupt(digitalPinToInterrupt(INTERRUPT_PIN), dmpDataReady, RISING);
    mpuIntStatus = mpu.getIntStatus();

    // set our DMP Ready flag so the main loop() function knows it's okay to use it
    Serial.println(F("DMP ready! Waiting for first interrupt..."));
    dmpReady = true;

    // get expected DMP packet size for later comparison
    packetSize = mpu.dmpGetFIFOPacketSize();
  }
  else {
    // ERROR!
    // 1 = initial memory load failed
    // 2 = DMP configuration updates failed
    // (if it's going to break, usually the code will be 1)
    Serial.print(F("DMP Initialization failed (code "));
    Serial.print(devStatus);
    Serial.println(F(")"));
  }

  // configure LED for output


}



// ================================================================
// ===                    MAIN PROGRAM LOOP                     ===
// ================================================================

void loop() {
  int flag = 0;
  fsrReading = analogRead(fsrPin);


  //printFloat(gps.location.lat(), gps.location.isValid(), 11, 6); 
  //printFloat(gps.location.lng(), gps.location.isValid(), 12, 6); 
  //  Serial.println(); 

  //  if (millis() > 5000 && gps.charsProcessed() < 10) 
  //  Serial.println(F("No GPS data received: check wiring")); 
  //

  // if programming failed, don't try to do anything
  if (!dmpReady) return;

  // wait for MPU interrupt or extra packet(s) available 
  //int yaw = ypr[0] * 180/M_PI;
  int pitch = ypr[1] * 180 / M_PI;
  int roll = ypr[2] * 180 / M_PI;

  while (!mpuInterrupt && fifoCount < packetSize) {
    // other program behavior stuff here
    // .
    /////////////////warning/////////////////////
    // .
    // if you are really paranoid you can frequently test in between other
    // stuff to see if mpuInterrupt is true, and if so, "break;" from the
    // while() loop to immediately process the MPU data
    // .
    // .
    // .
  }
  if (pitch>40 || pitch<-40) {
    flag = 1;
  }
  else {

  }

  if (roll>40 || roll<-40) {
    flag = 1;
  }
  else {

  }
  if (fsrReading < 100) {
    //Serial.println(" - No pressure");
  }
  else if (fsrReading < 300) {
    //Serial.println(" - Light touch");
    //Serial.println(fsrReading);
     flag = 1;
  }
  else if (fsrReading < 500) {
    flag = 1;
    //Serial.println(" - Light squeeze");
        //Serial.println(fsrReading);

  }
  else if (fsrReading < 800) {
    flag = 1;
    //Serial.println(" - Medium squeeze");
        //Serial.println(fsrReading);

  }
  else {
    //Serial.println(" - Big squeeze");
        //Serial.println(fsrReading);

  }


  // reset interrupt flag and get INT_STATUS byte
  mpuInterrupt = false;
  mpuIntStatus = mpu.getIntStatus();

  // get current FIFO count
  fifoCount = mpu.getFIFOCount();

  // check for overflow (this should never happen unless our code is too inefficient)
  if ((mpuIntStatus & 0x10) || fifoCount == 1024) {
    // reset so we can continue cleanly
    mpu.resetFIFO();
    //Serial.println(F("FIFO overflow!"));

    // otherwise, check for DMP data ready interrupt (this should happen frequently)
  }
  else if (mpuIntStatus & 0x02) {
    // wait for correct available data length, should be a VERY short wait
    while (fifoCount < packetSize) fifoCount = mpu.getFIFOCount();

    // read a packet from FIFO
    mpu.getFIFOBytes(fifoBuffer, packetSize);

    // track FIFO count here in case there is > 1 packet available
    // (this lets us immediately read more without waiting for an interrupt)
    fifoCount -= packetSize;

#ifdef OUTPUT_READABLE_YAWPITCHROLL
    // display Euler angles in degrees
    mpu.dmpGetQuaternion(&q, fifoBuffer);
    mpu.dmpGetGravity(&gravity, &q);
    mpu.dmpGetYawPitchRoll(ypr, &q, &gravity);
    //            Serial.print("ypr\t");
    //            Serial.print(ypr[0] * 180/M_PI);
    //            Serial.print(",");
    //            Serial.print("\t");
//                Serial.print(ypr[1] * 180/M_PI);
//                Serial.print(",");
//               Serial.print("\t");
//                Serial.println(ypr[2] * 180/M_PI);
#endif

    if (flag == 1) {
      tone(8, NOTE_G4, 200);
      digitalWrite(LED_PIN, HIGH);
      printFloat(gps.location.lat(), gps.location.isValid(), 11, 6);
      printFloat(gps.location.lng(), gps.location.isValid(), 12, 6);
      Serial.println("");
      delay(500);
    }
    else {


    }
    digitalWrite(LED_PIN, LOW);
    noTone(8);
    delay(500);
  }
}


//================================================================
//      gps 
//================================================================

static void smartDelay(unsigned long ms)
{
  unsigned long start = millis();
  do
  {
    while (ss.available())
      gps.encode(ss.read());
  } while (millis() - start < ms);
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
    for (int i = flen; i<len; ++i)
      Serial.print(' ');
  }
  smartDelay(0);
}


static void printStr(const char *str, int len)
{
  int slen = strlen(str);
  for (int i = 0; i<len; ++i)
    //Serial.print(i<slen ? str[i] : ' '); 
    smartDelay(0);
}

