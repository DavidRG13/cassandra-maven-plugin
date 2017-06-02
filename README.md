## Introduction
This maven plugin starts up and stop and embedded Cassandra instance, using [Cassandra Unit](https://github.com/jsevellec/cassandra-unit)

## Usage
```xml
<plugin>
    <artifactId>cassandra-maven-plugin</artifactId>
    <groupId>com.williamhill</groupId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
    <schemaFilePath>${project.basedir}/src/db/create_schema.cql</schemaFilePath>
    <cassandraUnit>${project.basedir}/cassandra-unit-3.1.4.0-SNAPSHOT-bin.tar.gz</cassandraUnit>
    </configuration>
    <executions>
        <execution>
            <id>start-cassandra</id>
            <phase>pre-integration-test</phase>
            <goals>
                <goal>start</goal>
            </goals>
            <configuration>
                <schemaFilePath>${project.basedir}/src/db/create_schema.cql</schemaFilePath>
                <cassandraUnit>${project.basedir}/cassandra-unit-3.1.4.0-SNAPSHOT-bin.tar.gz</cassandraUnit>
            </configuration>
        </execution>
        <execution>
            <id>stop-cassandra</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>stop</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Configuration
| Property       | Default value | Definition |
| -------------- | ------------- | ---------- |
| port           | 9042          | Port where Cassandra will be listening |
| timeout        | 20 seconds    | Timeout in milliseconds for Cassandra to start |
| schemaFilePath | REQUIRED      | see [this](https://github.com/jsevellec/cassandra-unit/wiki/available-dataset-format) |
| cassandraUnit  | GitHub repo   | Link to download the binary from, in case your device is not allowed to access GutHub |

## What is missing for now
* Publish the plugin.
* Currently the Cassandra Unit binary is under WilliamHill repository, until it's approved by the official one.
* Windows compatible.