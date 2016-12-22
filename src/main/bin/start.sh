#!/bin/bash
BASEDIR=`dirname $0`/../lib
BASEDIR=`(cd "$BASEDIR"; pwd)`

CONFIGDIR=`dirname $0`/../resource
echo $CONFIGDIR
CONFIGDIR=`(cd $CONFIGDIR; pwd)`
echo $CONFIGDIR

FEATURE=com.jd.USTDataCollect.USTDataCollect

setsid java -server -Xms1024m -Xmx1024m -XX:MaxPermSize=1024m -XX:+UseParallelGC -XX:+UseParallelOldGC -DconfigPath=$CONFIGDIR -Djava.ext.dirs=$BASEDIR $FEATURE &