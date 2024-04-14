package com.example.momsytdownloaderserver.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
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
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            process.waitFor();
        } catch (IOException ioException) {
            // TODO
        } catch (InterruptedException interruptedException) {
            // TODO
        }
    }

    public void runtime(List<String> commands) {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            for(int i = 0; i < commands.size(); i++) {
                builder.command("sh", "-c", commands.get(i));
            }
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            process.waitFor();
        } catch (IOException ioException) {
            // TODO
        } catch (InterruptedException interruptedException) {
            // TODO
        }
    }
}
