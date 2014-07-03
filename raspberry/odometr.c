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
	long double total_distance = 0.0;
	int last_cycle = millis();
	
	for(;;) {
		while(digitalRead(pin) == HIGH) { delay(1); } // wait for cycle
		
		speed = wheel_length / (millis() - last_cycle); // in m/s
		total_distance += wheel_length;
		printf("SPEED: %4.2f\tDISTANCE: %.2Lf\n", speed * multiplier, total_distance / 1000.0);
		last_cycle = millis();

		while(digitalRead(pin) == LOW){ delay(1); } // wait for unlocking reed switch (gerkon in rus)
	}

	return 0;
}
