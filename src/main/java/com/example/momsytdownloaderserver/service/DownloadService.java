package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.entity.RequestEntityCommand;
import com.example.momsytdownloaderserver.util.ShellBashUtil;
import org.springframework.stereotype.Service;

@Service
public class DownloadService  {

    private final ShellBashUtil shellBashUtil;

    public DownloadService(ShellBashUtil shellBashUtil) {
        this.shellBashUtil = shellBashUtil;
    }

    public void ytDownloadLogic(String videoId, RequestEntityCommand entityCommand) {
        String initialCommandBuilder = "yt-dlp -x --audio-format mp3 " +
            "-o " + "\"" + entityCommand.id() + ".mp3\" " +
            "-p " + "\"/home/ubuntu/moms-yt-downloader-server/download\"" +
            "https://www.youtube.com/watch?v=" +
            videoId;
        shellBashUtil.runtime(initialCommandBuilder);
    }

    // TODO move and rename logic
}
