CC=gcc
CFLAGS= -c -Wall
LDLIBS= -lwiringPi
BTLIBS= -lbluetooth

all: odometr scaner

scaner: bluetooth_scaner.o
	$(CC) $(BTLIBS) bluetooth_scaner.o -o bluetooth_scaner.app
scaner.o: bluetooth_scaner.c
	$(CC) $(CFLAGS) bluetooth_scaner.c
odometr: odometr.o
	$(CC) $(LDLIBS) odometr.o -o odometr.app
odometr.o: odometr.c
	$(CC) $(CFLAGS) odometr.c

clean:
	rm -rf *.o odometr