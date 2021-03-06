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
package com.github.william_hill_online.cassandra;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

public class CassandraUnit {

    private static final String CASSANDRA_UNIT = "cassandra-unit-3.1.4.0-SNAPSHOT";
    private static final String CASSANDRA_STARTER = CASSANDRA_UNIT + "/bin/cu-starter";
    private static final String BINARY_FILE = "cassandra-unit-3.1.4.0-SNAPSHOT-bin.tar.gz";

    public static void  startCassandra(final int port, final long timeout, final String schemaFilePath, final String cassandraUnit, final String workingDirectory) {
        File temp = new File(workingDirectory, "temp");
        if (temp.exists()) {
            File[] tempFiles = temp.listFiles();
            if (tempFiles != null) {
                for (File file : tempFiles) {
                    file.delete();
                }
            }
        } else {
            temp.mkdir();
        }

        new File(workingDirectory).mkdir();

        try {
            File cu = new File(workingDirectory, CASSANDRA_UNIT);
            if (!cu.exists()) {
                if (cassandraUnit == null || cassandraUnit.isEmpty()) {
                    String downloadLink = "https://github.com/William-Hill-Online/cassandra-unit/releases/download/SNAPSHOT/" + BINARY_FILE;
                    downloadCassandraUnitFrom(downloadLink, workingDirectory);
                } else {
                    downloadCassandraUnitFrom(cassandraUnit, workingDirectory);
                }
                new ProcessBuilder("tar", "-xvf", workingDirectory + "/" + BINARY_FILE, "-C", workingDirectory).start().waitFor();
            }

            String[] strings = {"sh", workingDirectory + "/" + CASSANDRA_STARTER, "-p", String.valueOf(port), "-t", String.valueOf(timeout), "-s", schemaFilePath, "-d", workingDirectory + "/" + CASSANDRA_UNIT};
            new ProcessBuilder(strings).redirectErrorStream(true).redirectOutput(new File(temp, "log")).start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopCassandra() {
        try {
            new ProcessBuilder("/bin/bash", "-c", "ps -ef | grep \"[c]u-loader\" | awk '{print $2}' |xargs kill").redirectErrorStream(true).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadCassandraUnitFrom(final String downloadLink, final String workingDirectory) throws IOException {
        FileUtils.copyURLToFile(new URL(downloadLink), new File(workingDirectory, BINARY_FILE));
    }

    static boolean portIsNotListening(final int port) {
        try(Socket socket = new Socket("localhost", port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
