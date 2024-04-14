package com.example.momsytdownloaderserver.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class ShellBashUtil {

    public static class StreamGobbler implements Runnable {

        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }
    }

    public void runtime(String command) {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("sh", "-c", command);
            Process process = builder.start();
            System.out.println("Downloading Youtube Music");
            process.waitFor();
        } catch (IOException ioException) {
            // TODO
        } catch (InterruptedException interruptedException) {
            // TODO
        }
    }
}
