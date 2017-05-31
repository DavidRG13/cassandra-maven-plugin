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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (schemaFilePath == null || schemaFilePath.isEmpty()) {
            System.out.println("SchemaFilePath has to be provided");
        } else {
            CassandraUnit.startCassandra(port, timeout, schemaFilePath);
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
