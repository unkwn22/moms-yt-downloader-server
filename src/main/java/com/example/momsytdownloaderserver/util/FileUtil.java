package com.example.momsytdownloaderserver.util;

import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.InternalErrorException;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileUtil {

    private final String absolutePath = new File("").getAbsolutePath();

    public byte[] getByteArray(File file) {
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(byteArray);
        } catch (IOException e) {
            throw new InternalErrorException(ErrorCode.INTERNAL);
        }
        return byteArray;
    }

    public File copyFile(File source, String target) {
        String newTarget = absolutePath + target;
        File copied = new File(newTarget);
        try (InputStream in = new BufferedInputStream(new FileInputStream(source));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(copied))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            throw new InternalErrorException(ErrorCode.INTERNAL);
        }
        return copied;
    }
}
