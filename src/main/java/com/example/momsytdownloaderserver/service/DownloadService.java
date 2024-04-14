package com.example.momsytdownloaderserver.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.momsytdownloaderserver.config.S3Config;
import com.example.momsytdownloaderserver.entity.RequestEntityCommand;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.InternalErrorException;
import com.example.momsytdownloaderserver.util.FileUtil;
import com.example.momsytdownloaderserver.util.ShellBashUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DownloadService  {

    private final ShellBashUtil shellBashUtil;
    private String directory = "~/moms-yt-downloader-server/download";
    private FileUtil fileUtil;
    private final S3Config s3Config;

    public DownloadService(
        ShellBashUtil shellBashUtil,
        FileUtil fileUtil,
        S3Config s3Config
    ) {
        this.shellBashUtil = shellBashUtil;
        this.fileUtil = fileUtil;
        this.s3Config = s3Config;
    }

    public String ytDownloadLogic(String videoId, RequestEntityCommand entityCommand) {
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
        mp3File.delete();
        String uploadedUrl = uploadToS3(copiedFile);
        copiedFile.delete();
        return uploadedUrl;
    }

    private String uploadToS3(File file) {
        String directory = s3Config.getDirectory() + file.getName();
        AmazonS3 amazonS3Client = s3Config.amazonS3Client();
        amazonS3Client.putObject(s3Config.getBucket(), directory, file);
        return s3Config.getPrefixUrl() + directory;
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
}