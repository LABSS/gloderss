#!/bin/bash

XMLpath=src/test/resources/conf/sampleScenario.xml
XSDpath=src/main/resources/conf/scenario.xsd

if [ $# -gt 0 ]
then
  if [ "$1" != "-h" -a "$1" != "-help" ]
  then
    XMLpath=$1
  else
    echo "./runSim.sh [Scenario XML path]"
    exit 1
  fi
fi

cd ..
/usr/bin/mvn exec:exec -Pexec -Dexec.args="$XMLpath $XSDpath"