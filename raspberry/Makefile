CC=gcc
CFLAGS= -c -Wall
LDLIBS= -lwiringPi

all: odometr

odometr: odometr.o
	$(CC) $(LDLIBS) odometr.o -o odometr.app
odometr.o: odometr.c
	$(CC) $(CFLAGS) odometr.c

clean:
	rm -rf *.o odometr