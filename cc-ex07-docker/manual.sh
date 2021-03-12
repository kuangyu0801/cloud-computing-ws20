#!/bin/bash
docker run -d --name tp1 --net=net1 my-tp-image
docker run -d --name mysql1 --net=net1 -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=NotesDB mysql:5.7
docker run -d --name nb1 --net=net1 -e TPMODE=remoteSingle -e TPURL=http://tp1:80/api -e DBMODE=jdbc -e DBURL=jdbc:mysql://mysql1:3306/NotesDB -e DBUSER=root -e DBPASS=secret -e DBHOST=database -p 8080:80 my-nba-image