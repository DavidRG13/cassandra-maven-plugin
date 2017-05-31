package com.williamhill.cassandra;

import java.io.IOException;

public class CassandraUnit {

    private static final String CASSANDRA_UNIT = "cassandra-unit-3.1.4.0-SNAPSHOT";
    private static final String CASSANDRA_STARTER = "sh " + CASSANDRA_UNIT + "/bin/cu-starter";
    private static Process cassandraProcess;
    private static boolean running;

    public static void  startCassandra(final int port, final long timeout, final String schemaFilePath) {
        if (!running) {
            String path = CassandraUnit.class.getResource("/startCassandra.sh").getPath();
            try {
                new ProcessBuilder("sh " + path).start();
                String command = CASSANDRA_STARTER +" -p " + port + " -t " + timeout + " -s " + schemaFilePath + " -d " +CASSANDRA_UNIT;
                cassandraProcess = new ProcessBuilder(command).start();
                running = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopCassandra() {
        cassandraProcess.destroy();
        running = false;
    }
}
