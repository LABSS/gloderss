gloderss (GLODERS Simulator)
============================

(Linux) Instructions to download the GLODERS Simulator, compile and execute it

1. Software Pre-Requisites
--------------------------
Git

Maven

Java SE 7+


2. Download projects from Git
-----------------------------

$ git clone git@github.com:gnardin/emilia.git

$ git clone git@github.com:gnardin/gloderss.git


3. Compile and install both projects
------------------------------------
$ cd emilia

$ mvn clean

$ mvn compile

$ mvn package

$ mvn install

$ cd ../gloderss

$ mvn clean

$ mvn compile

$ mvn package

$ mvn install


4. Configure
------------
Edit the file at gloderss/src/main/resources/conf/scenario.xml and change parameters' value


5. Execute
----------
$ mvn exec:exec -Pexec -Dexec.args="src/main/resources/conf/scenario.xml src/main/resources/conf/scenario.xsd"

The 'log' and 'output' directories is created under gloderss directory (if not changed the default values in the configuration scenario.xml file).
