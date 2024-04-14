package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.entity.RequestEntityCommand;
import com.example.momsytdownloaderserver.util.ShellBashUtil;
import org.springframework.stereotype.Service;

@Service
public class DownloadService  {

    private final ShellBashUtil shellBashUtil;
    private String directory = "~/moms-yt-downloader-server/download";

    public DownloadService(ShellBashUtil shellBashUtil) {
        this.shellBashUtil = shellBashUtil;
    }

    public void ytDownloadLogic(String videoId, RequestEntityCommand entityCommand) {
        String initialCommandBuilder = "yt-dlp " +
                "-o "  + entityCommand.id() + ".mp3 " +
                "-P " + directory + " " +
                "-x --audio-format mp3 " +
                "https://www.youtube.com/watch?v=" + videoId;
        shellBashUtil.runtime(initialCommandBuilder);

        changeFileName(entityCommand);
        removePreviousFile(entityCommand);
    }

    private void changeFileName(RequestEntityCommand entityCommand) {
        String fileName = entityCommand.originalTitle();
        if(entityCommand.requestedTitle().isEmpty() || entityCommand.requestedTitle().isBlank()) fileName = entityCommand.requestedTitle();

        String changeNameCommand = "cp " + entityCommand.id() + ".mp3 " + directory + "/" + fileName + ".mp3";
        shellBashUtil.runtime(changeNameCommand);
    }

    private void removePreviousFile(RequestEntityCommand entityCommand) {
        String removeFileCommand = "rm -f " + directory + "/" + entityCommand.id() + ".mp3";
        shellBashUtil.runtime(removeFileCommand);
    }
}