#!/bin/bash

for pid in `jps | grep USTDataCollect | awk {'print $1'}`
    do
        kill -9 $pid
        echo "$pid stoped."
    done