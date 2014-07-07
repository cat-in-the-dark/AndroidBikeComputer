## Installation instructions for raspberry pi.

```bash
sudo apt-get install libbluetooth-dev
git clone git://git.drogon.net/wiringPi --depth=1
cd wiringPi
./build
gpio readall
```