#!/bin/sh -l

GPIO_FOLDER=gpio
PATH_GPIO=/sys/class/${GPIO_FOLDER}
PORT_GPIO=24

removeAccess() {
  cd ${PATH_GPIO} || echo "Folder ${PATH_GPIO} non existant"
  echo ${PORT_GPIO} > "unexport"
  exit
}

turnOn() {
  cd ${PATH_GPIO}/${GPIO_FOLDER}${PORT_GPIO} || echo "Folder led ${PORT_GPIO} non existant"
  echo "1" > "value"
}

turnOff() {
  cd ${PATH_GPIO}/${GPIO_FOLDER}${PORT_GPIO} || echo "Folder led ${PORT_GPIO} non existant"
  echo "0" > "value"
}

cd ${PATH_GPIO} || echo "Folder ${PATH_GPIO} not existant"
echo ${PORT_GPIO} > "export"

cd gpio${PORT_GPIO} || removeAccess

echo "out" > "direction"

turnOn
sleep 1
turnOff
sleep 1
turnOn

removeAccess
