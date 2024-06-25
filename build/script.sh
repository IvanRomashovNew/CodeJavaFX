#!/bin/bash
service postgresql start
su - postgres -c '
psql -c "CREATE USER postgresnew WITH PASSWORD '\''111'\'';"
createdb -O postgresnew SeaRadarDB
'
Xvfb :99 -screen 0 1024x768x16 &
export DISPLAY=:99
mkdir -p /path/to/
x11vnc -storepasswd garry2016 /path/to/passwordfile
x11vnc -rfbport 5901 -no6 -forever -passwdfile /path/to/passwordfile -create -bg &
java -jar app.jar
