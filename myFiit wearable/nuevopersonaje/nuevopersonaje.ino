

#include <SPI.h>
#include <TFT.h>            // Arduino TFT library

#define cs   10
#define dc   9
#define rst  8
int feliz=0; 
const int buttonPin = 4;  
int ciclosComunica = 0;

TFT screen = TFT(cs, dc, rst);
int buttonState = 0;         // variable for reading the pushbutton status
int estado =0;
void setup() {
  Serial.begin(9600); 
  pinMode(buttonPin, INPUT);
  // initialize the screen
  screen.begin();

  // make the background black
  screen.background(255,255,0);
  screen.fill(83,83,233);
  screen.stroke(83,83,233);
  screen.circle(screen.width()/2-25, screen.height()/2, 50);//cara
     screen.rect(screen.width()/2+42,screen.height()/2-31, 15,10);
   screen.rect(screen.width()/2+42,screen.height()/2+21, 15,10);
  screen.fill(200,0,0);
  screen.stroke(200,0,0);  
   screen.rect(screen.width()/2-3,screen.height()/2-44, 10,88);
   screen.rect(screen.width()/2+7,screen.height()/2-40, 10,80);
   screen.rect(screen.width()/2+17,screen.height()/2-36, 10,72);
   screen.rect(screen.width()/2+27,screen.height()/2-31, 15,10);
   screen.rect(screen.width()/2+27,screen.height()/2+21, 15,10);
   screen.fill(0,66,154);
  screen.stroke(0,66,154);   
   screen.rect(screen.width()/2+57,0, 23,screen.height());
}

void loop() {
  buttonState = digitalRead(buttonPin);
  //Serial.println(buttonState);
  while (buttonState == HIGH) {
    delay(20);
    buttonState = digitalRead(buttonPin);  
    if (buttonState==LOW){
      feliz+=10;
    }
    // turn LED on:
  }
    
  ciclosComunica ++;
  if (feliz>30){
    feliz = 30;
  }

  if (ciclosComunica > 2){
    ciclosComunica = 0;
  }

   screen.stroke(255,0,0);

  // set the fill color to grey
 // screen.fill(255,0,0);//ropa
 // screen.circle(screen.width()/2+80, screen.height()/2,50);

    // set the stroke color to white
  screen.stroke(83,83,233);

  // set the fill color to grey
  
  // draw a circle in the center of screen
 // screen.rect(screen.width()/2+10,screen.height()/2-10, 20,20);//cuello
   
     screen.fill(0, 50, 0);
  screen.stroke(0, 50, 0);
  screen.rect(screen.width()/2-57,screen.height()/2-43, 10,86);
    screen.fill(0,200, 0);
  screen.stroke(0, 200, 0);
   screen.rect(screen.width()/2-72,screen.height()/2-38, 15,76);
   screen.rect(screen.width()/2-80,screen.height()/2-33, 8,66);
  screen.fill(50,0,0);
  screen.stroke(50,0,0);
  screen.rect(screen.width()/2-13,screen.height()/2-48, 10,96);

   
   screen.fill(83,83,233);
   screen.stroke(83,83,233);
   screen.rect(screen.width()/2-3-26,screen.height()/2-15, 15,29);
   
   screen.fill(0,0,0);
   screen.stroke(0,0,0);
   if(feliz<=10 ){
    screen.rect(screen.width()/2-26,screen.height()/2-7, 5,14);
    screen.rect(screen.width()/2+3-26,screen.height()/2-12, 6,5);
    screen.rect(screen.width()/2+3-26,screen.height()/2+7, 6,5);
    if( ciclosComunica==2)
      Serial.println("a");
    }
   if (feliz>10 && feliz<=20 ){
    screen.rect(screen.width()/2-26,screen.height()/2-10, 5,20);
    if( ciclosComunica==2)
      Serial.println("b");
   }
   if(feliz>20 && feliz<=30){
    screen.rect(screen.width()/2-26,screen.height()/2-7, 5,14);
    screen.rect(screen.width()/2-3-26,screen.height()/2-12, 6,5);
    screen.rect(screen.width()/2-3-26,screen.height()/2+7, 6,5);
    if( ciclosComunica==2)
          Serial.println("c");
    }
   
  screen.circle(screen.width()/2-40, screen.height()/2+10, 5);
  screen.circle(screen.width()/2-40, screen.height()/2-10, 5);
  delay(300);
  screen.fill(83,83,233);
  screen.stroke(83,83,233);
    screen.circle(screen.width()/2-40, screen.height()/2+10, 5);
  screen.circle(screen.width()/2-40, screen.height()/2-10, 5);
  delay(100);

  //Serial.print ("nivel ");
  feliz -- ;
  if (feliz<0){
    feliz = 0;
  }

 // Serial.println(feliz);
  
}
