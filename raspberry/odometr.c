#include <stdio.h>
#include <wiringPi.h>

int main(){
	const int pin = 9;	
	float wheel_length = 2133.0; // in millimeters
	float multiplier = 3.6; // to convert from m/s to km/h
	
	printf("Start bike\n");
	wiringPiSetup();
	pullUpDnControl(pin, PUD_UP);
	pinMode(pin, INPUT);
	
	float speed;
	int last_cycle = millis();
	
	for(;;) {
		while(digitalRead(pin) == HIGH) { delay(1); } // wait for cycle
		
		speed = wheel_length / (millis() - last_cycle); // in m/s
		printf("%f\n", speed * multiplier);
		last_cycle = millis();

		while(digitalRead(pin) == LOW){ delay(1); } // wait for unlocking reed switch (gerkon in rus)
	}

	return 0;
}
