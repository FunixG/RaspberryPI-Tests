#!/bin/sh

#Brancher les fils de la led sur les ports 18(+) & 20(-)
GPIO_FOLDER=gpio
PATH_GPIO=/sys/class/${GPIO_FOLDER}
PORT_GPIO=24

removeAccess() {
  cd ${PATH_GPIO} || echo "Folder ${PATH_GPIO} non existant"
  echo ${PORT_GPIO} > "unexport" || echo "gpio${PORT_GPIO} alerady unexported"
  echo "Close gpio${PORT_GPIO}"
  exit
}

turnOn() {
  cd ${PATH_GPIO}/${GPIO_FOLDER}${PORT_GPIO} || echo "Folder led ${PORT_GPIO} non existant"
  echo "1" > "value"
  echo "Led on"
}

turnOff() {
  cd ${PATH_GPIO}/${GPIO_FOLDER}${PORT_GPIO} || echo "Folder led ${PORT_GPIO} non existant"
  echo "0" > "value"
    echo "Led off"
}

cd ${PATH_GPIO} || echo "Folder ${PATH_GPIO} not existant"

echo ${PORT_GPIO} > "export" || echo "gpio${PORT_GPIO} alerady exported"
echo "Open gpio${PORT_GPIO}"
sleep 1

cd gpio${PORT_GPIO} || removeAccess

echo "out" > "direction" || echo "Error while setting out the gpio${PORT_GPIO}"

MAX_LOOPS=3
i=0

while [ $i -le $MAX_LOOPS ]
do
      turnOn
      sleep 1
      turnOff
      sleep 1

      i=$(( i + 1 ))
done

removeAccess
