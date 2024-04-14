package com.example.momsytdownloaderserver.util;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CustomMultipartFile implements MultipartFile {

    private byte[] input;
    private File file;

    public CustomMultipartFile(
        byte[] input,
        File file
    ) {
        this.input = input;
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getContentType() {
        return MediaType.MULTIPART_FORM_DATA_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        try(FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(input);
        }
    }

    public byte[] getInput() {
        return input;
    }

    public File getFile() {
        return file;
    }
}
