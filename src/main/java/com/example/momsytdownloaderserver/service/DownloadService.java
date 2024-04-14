package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.entity.RequestEntityCommand;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.InternalErrorException;
import com.example.momsytdownloaderserver.util.CustomMultipartFile;
import com.example.momsytdownloaderserver.util.FileUtil;
import com.example.momsytdownloaderserver.util.ShellBashUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class DownloadService  {

    private final ShellBashUtil shellBashUtil;
    private String directory = "~/moms-yt-downloader-server/download";
    private FileUtil fileUtil;

    public DownloadService(
        ShellBashUtil shellBashUtil,
        FileUtil fileUtil
    ) {
        this.shellBashUtil = shellBashUtil;
        this.fileUtil = fileUtil;
    }

    public void ytDownloadLogic(String videoId, RequestEntityCommand entityCommand) {
        String initialCommandBuilder = "yt-dlp " +
                "-o "  + entityCommand.id() + ".mp3 " +
                "-P " + directory + " " +
                "-x --audio-format mp3 " +
                "https://www.youtube.com/watch?v=" + videoId;
        shellBashUtil.runtime(initialCommandBuilder);

        String changeFilename = entityCommand.originalTitle();
        if(!entityCommand.requestedTitle().isBlank() && !entityCommand.requestedTitle().isEmpty()) changeFilename = entityCommand.requestedTitle();
        String newTarget = "/download/" + changeFilename + ".mp3";
        File mp3File = findFile(entityCommand);
        File copiedFile = fileUtil.copyFile(mp3File, newTarget);
//        mp3File.delete();
        byte[] copiedFileByteArray = fileUtil.getByteArray(copiedFile);
//        copiedFile.delete();
//        return new CustomMultipartFile(copiedFileByteArray, copiedFile);
    }

    private File findFile(RequestEntityCommand entityCommand) {
        String absolutePath = new File("").getAbsolutePath();
        String directory = absolutePath + "/download";
        try {
            return new File(directory + "/" + entityCommand.id() + ".mp3");
        } catch (Exception e) {
            throw new InternalErrorException(ErrorCode.INTERNAL);
        }
    }

    private void changeFileName(RequestEntityCommand entityCommand) {
        String fileName = entityCommand.originalTitle();
        if(entityCommand.requestedTitle().isEmpty() || entityCommand.requestedTitle().isBlank()) fileName = entityCommand.requestedTitle();
        System.out.println("Copying ");
        String changeNameCommand = "cp " +  directory + "/" + entityCommand.id() + ".mp3 " + directory + "/" + fileName + ".mp3";
        shellBashUtil.runtime(changeNameCommand);
    }

    private void removePreviousFile(RequestEntityCommand entityCommand) {
        String removeFileCommand = "rm -f " + directory + "/" + entityCommand.id() + ".mp3";
        shellBashUtil.runtime(removeFileCommand);
    }
}