#!/bin/bash
if [ "$DBMODE" = "jdbc" ]
then 
	# wait until database is available
	while ! mysqladmin ping -h "$DBHOST" --silent; do
		sleep 1
	done
	# initialize database
	java -jar notebookapp-0.3.0.jar db migrate config.yml
fi

# start the notebook app
java -jar notebookapp-0.3.0.jar server config.yml
