# `gloderss` (GLODERS Simulator)

(Linux) Instructions to download the GLODERS Simulator, compile and execute it

## 1. Software Pre-Requisites

- Git
- Maven
- Oracle Java SE 8

## 2. Download projects from GitHub

```bash
$ git clone git@github.com:gnardin/emilia.git

$ git clone git@github.com:LABSS/gloderss.git
```

## 3. Compile and install both projects

```bash
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
```

## 4. Configure

Edit the file at `gloderss/src/main/resources/conf/scenario.xml` and change parameters' value.

## 5. Execute

```bash
$ mvn exec:exec -Pexec -Dexec.args="src/main/resources/conf/scenario.xml src/main/resources/conf/scenario.xsd"
```

The `log` and `output` directories are created under the `gloderss` directory (if not changed the default values in the configuration `scenario.xml` file).


## 6. Analysis

There is a script located in the `script` directory that you can execute using R Statistics software to summarize the results in the files created in the `output` directory.
