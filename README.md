[![N|Solid](http://www.greenvulcanotechnologies.com/wp-content/uploads/2017/04/logo_gv_FLAT-300x138.png)](http://www.greenvulcanotechnologies.com)
# GreenVulcano PropertiesHandler Plus

This unofficial GreenVulcano extension (currently work in progress) adds a new placeholder: the ```sqljson```, which acts both as ```sql``` and ```sqltable``` placeholders, depending on how many results it has to fetch.

## Getting started


### Prerequisites

First, you need to have installed Java Development Kit (JDK) 1.7 or above.

Then, you need to have installed Apache Maven (3.5.4 or higher) and Apache Karaf 4.1.5. Please refer to the following links for further reference:
- Apache Maven 3.5.4:
    - [Download](http://mirror.nohup.it/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz)
    - [Installation steps](https://maven.apache.org/install.html)
- Apache Karaf 4.2.6:
    - [Download](http://www.apache.org/dyn/closer.lua/karaf/4.2.6/apache-karaf-4.2.6.tar.gz)
    - Installation steps: simply extract the Apache Karaf directory in any path you want. Verify that the Apache Karaf installation is operational by running the executable ```./bin/karaf``` from the Apache Karaf root directory (a Karaf shell should be displayed).

Next, you need to install the GreenVulcano engine on the Apache Karaf container. Please refer to [this link](https://github.com/greenvulcano/gv-engine/blob/master/quickstart-guide.md) for further reference.

### Installation

Clone or download this repository on your computer, and then run ```mvn install``` in its root folder:

```shell
git clone https://github.com/kylan11/gv-properties-plus
cd gv-properties-plus
mvn install
```

In order to install the bundle in Apache Karaf to use it for a GreenVulcano application project, you need to install its dependencies. Open the Apache Karaf terminal by running the Karaf executable and type the following command:

```shell
karaf@root()> bundle:install -s wrap:mvn:ognl/ognl/3.2.11
```

Then, run this command to install the actual expansion component:
```shell
bundle:install -l 96 mvn:it.greenvulcano.gvesb.util/gv-properties-plus/4.1.0-SNAPSHOT
```

That's it! You can now make use of the new placeholders in your GreenVulcano project.
