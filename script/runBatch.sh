#!/bin/bash

XMLpath=src/main/resources/conf/batch/batch.xml
XSDpath=src/main/resources/conf/batch.xsd

if [ $# -gt 0 ]
then
  if [ "$1" != "-h" -a "$1" != "-help" ]
  then
    XMLpath=$1
  else
    echo "./runBatch.sh [Batch XML path]"
    exit 1
  fi
fi

cd ..
/usr/bin/mvn exec:exec -Pbatch -Dexec.args="$XMLpath $XSDpath"