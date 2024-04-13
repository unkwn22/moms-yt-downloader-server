package com.example.momsytdownloaderserver.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record YouTubeSearchResponse(
    String kind,
    String etag,
    String nextPageToken,
    String prevPageToken,
    String regionCode,
    PageInfo pageInfo,
    List<Item> items
) {
    public record PageInfo(int totalResults, int resultsPerPage) {}

    public record Item(String kind, String etag, Id id, Snippet snippet) {}

    public record Id(String kind, String videoId) {}

    public record Snippet(
        String publishedAt,
        String channelId,
        String title,
        String description,
        Thumbnails thumbnails,
        String channelTitle,
        String liveBroadcastContent,
        String publishTime
    ) {
        public record Thumbnails(
            @JsonProperty("default") Default defaultThumbnail,
            Medium mediumThumbnail,
            High highThumbnail
        ) {}

        public record Default(String url, int width, int height) {}

        public record Medium(String url, int width, int height) {}

        public record High(String url, int width, int height) {}
    }
}
