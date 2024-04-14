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
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService  {

    private final ShellBashUtil shellBashUtil;
    private String directory = "/home/ubuntu/moms-yt-downloader-server/download";
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
        System.out.println(new File("").getAbsolutePath());

        List<String> commands = new ArrayList<>();
        String move = "cd /home/ubuntu/moms-yt-downloader-server";
        String initialCommand = "sudo yt-dlp " +
            "-o "  + entityCommand.id() + ".mp3 " +
            "-P " + directory + " " +
            "-x --audio-format mp3 " +
            "https://www.youtube.com/watch?v=" + videoId;
        commands.add(move);
        commands.add(initialCommand);
        shellBashUtil.runtime(commands);

        String changeFilename = entityCommand.originalTitle();
        if(!entityCommand.requestedTitle().isBlank() && !entityCommand.requestedTitle().isEmpty()) changeFilename = entityCommand.requestedTitle();

        String copyFileChangeNameCommand = "sudo cp " +
            directory + "/" + entityCommand.id() + ".mp3 " +
            directory + "/" + changeFilename + ".mp3";
        shellBashUtil.runtime(copyFileChangeNameCommand);

        String deleteCommand = "sudo rm -f " + directory + "/" + entityCommand.id() + ".mp3";
        shellBashUtil.runtime(deleteCommand);

        File copiedFile = findFile(changeFilename);
        String uploadedUrl = uploadToS3(copiedFile);

        String deleteCommand2 = "sudo rm -f " + directory + "/" + changeFilename + ".mp3";
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
        try {
            return new File( directory + "/" + filename + ".mp3");
        } catch (Exception e) {
            throw new InternalErrorException(ErrorCode.INTERNAL);
        }
    }
}