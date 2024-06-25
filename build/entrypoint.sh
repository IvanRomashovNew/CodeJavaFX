#!/bin/bash
service postgresql start
su - postgres -c '
psql -c "CREATE USER postgresnew WITH PASSWORD '\''111'\'';"
createdb -O postgresnew SeaRadarDB
'
xvfb-run java -jar app.jar
