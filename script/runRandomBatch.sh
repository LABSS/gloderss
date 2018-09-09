#!/bin/bash

XMLpath=src/main/resources/conf/sensitivity-analysis/randomBatch.xml
XSDpath=src/main/resources/conf/randomBatch.xsd

if [ $# -gt 0 ]
then
  if [ "$1" != "-h" -a "$1" != "-help" ]
  then
    XMLpath=$1
  else
    echo "./runRandomBatch.sh [Batch XML path]"
    exit 1
  fi
fi

cd ..
/usr/bin/mvn exec:exec -PrandomBatch -Dexec.args="$XMLpath $XSDpath"
