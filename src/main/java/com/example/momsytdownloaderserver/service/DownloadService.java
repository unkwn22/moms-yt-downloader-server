package com.example.momsytdownloaderserver.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.momsytdownloaderserver.config.S3Config;
import com.example.momsytdownloaderserver.entity.RequestEntityCommand;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.InternalErrorException;
import com.example.momsytdownloaderserver.util.ShellBashUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DownloadService  {

    private final ShellBashUtil shellBashUtil;
    private final S3Config s3Config;

    public DownloadService(
        ShellBashUtil shellBashUtil,
        S3Config s3Config
    ) {
        this.shellBashUtil = shellBashUtil;
        this.s3Config = s3Config;
    }

    public String ytDownloadLogic(String videoId, RequestEntityCommand entityCommand) {
        String initialCommandBuilder = "sudo yt-dlp " +
            "-o "  + entityCommand.id() + ".mp3 " +
            "-P ~/moms-yt-downloader-server/download" +
            "-x --audio-format mp3 " +
            "https://www.youtube.com/watch?v=" + videoId;
        shellBashUtil.runtime(initialCommandBuilder);
        String changeFilename = entityCommand.originalTitle();
        if(!entityCommand.requestedTitle().isBlank() && !entityCommand.requestedTitle().isEmpty()) changeFilename = entityCommand.requestedTitle();

        String copyFileChangeNameCommand = "sudo cp " +
            "/home/ubuntu/moms-yt-downloader-server/download/"+ entityCommand.id() + ".mp3 " +
            "/home/ubuntu/moms-yt-downloader-server/download/" + changeFilename + ".mp3";
        shellBashUtil.runtime(copyFileChangeNameCommand);

        String deleteCommand = "sudo rm -f /home/ubuntu/moms-yt-downloader-server/download/" + entityCommand.id() + ".mp3";
        shellBashUtil.runtime(deleteCommand);

        File copiedFile = findFile(changeFilename);
        String uploadedUrl = uploadToS3(copiedFile);

        String deleteCommand2 = "sudo rm -f /home/ubuntu/moms-yt-downloader-server/download/" + changeFilename + ".mp3";
        shellBashUtil.runtime(deleteCommand2);
        return uploadedUrl;
    }

    private String uploadToS3(File file) {
        String directory = s3Config.getDirectory() + file.getName();
        AmazonS3 amazonS3Client = s3Config.amazonS3Client();
        amazonS3Client.putObject(s3Config.getBucket(), directory, file);
        return s3Config.getPrefixUrl() + directory;
    }

    private File findFile(String filename) {
        String newDirectory = "/home/ubuntu/moms-yt-downloader-server/download/" + filename + ".mp3";
        try {
            return new File(newDirectory);
        } catch (Exception e) {
            throw new InternalErrorException(ErrorCode.INTERNAL);
        }
    }
}
