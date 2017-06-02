/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.williamhill.cassandra;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartCassandraMojo extends AbstractMojo {

    @Parameter(defaultValue="9042")
    private int port;

    @Parameter(defaultValue="20000")
    private int timeout;

    @Parameter
    private String schemaFilePath;

    @Parameter
    private String cassandraUnit;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (schemaFilePath == null || schemaFilePath.isEmpty()) {
            System.out.println("SchemaFilePath has to be provided");
        } else if (CassandraUnit.portIsNotListening(port)) {
            CassandraUnit.startCassandra(port, timeout, schemaFilePath, cassandraUnit);

            while (CassandraUnit.portIsNotListening(port)) {
                System.out.println("Starting...");
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    public void setSchemaFilePath(final String schemaFilePath) {
        this.schemaFilePath = schemaFilePath;
    }
}
//sh cassandra-unit-3.1.4.0-SNAPSHOT/bin/cu-starter -p 9042 -t 20000 -s/home/davidrodriguez/dev/cas/trafalgar-cas/src/db/create_schema.cql -d cassandra-unit-3.1.4.0-SNAPSHOT