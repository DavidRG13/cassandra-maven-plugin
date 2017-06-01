#!/bin/bash

if [ -d "temp" ]; then
rm -rf temp
fi
mkdir temp

DIRECTORY=cassandra-unit-3.1.4.0-SNAPSHOT

if [ ! -d "$DIRECTORY" ]; then
tar -xvf build/resources/main/cassandra-unit-3.1.4.0-SNAPSHOT-bin.tar.gz
fi

#sh cassandra-unit-3.1.4.0-SNAPSHOT-bin/bin/cu-starter -p {port} -t {timeout} -s {schema}


sh $DIRECTORY/bin/cu-starter -p 9042 -d $DIRECTORY -s src/db/create_schema.cql &
CASSANDRA_PID=`ps -ef | grep cu-starter`

function ctrl_c() {
    JAVA_PROCESS=`ps -ef | grep "[c]u-loader" | awk '{print $2}'`
    JAVA_PROCESS=`ps -s --ppid $CASSANDRA_PID | grep -v PID | awk '{print $2}'`
    kill $CASSANDRA_PID
    kill $JAVA_PROCESS
}