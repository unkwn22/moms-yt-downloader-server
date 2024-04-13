package com.example.momsytdownloaderserver.repository;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "default",
    url = "https://www.googleapis.com"
)
public interface GoogleFeign {

    @GetMapping("/youtube/v3/search")
    @Headers("Content-Type: application/json;charset=utf-8")
    YouTubeSearchResponse requestYtList (
        @RequestParam String key,                           // API key
        @RequestParam int maxResults,                       // (10)
        @RequestParam(required = false) String q,           // Query
        @RequestParam String part,                          // (snippet)
        @RequestParam String type,                          // (video)
        @RequestParam(required = false) String pageToken    // search page token
    );
}
