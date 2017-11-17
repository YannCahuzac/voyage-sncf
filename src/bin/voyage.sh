#!/usr/bin/env bash

CLASSPATH="conf/tech/tech.properties;$CLASSPATH"
for fichier in lib/*.jar
	do
		CLASSPATH="${fichier};$CLASSPATH"
	done
	
java -cp $CLASSPATH fr.batch.voyage.Main