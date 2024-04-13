package com.example.momsytdownloaderserver.service;

import com.example.momsytdownloaderserver.exception.BadRequestException;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.repository.GoogleFeign;
import com.example.momsytdownloaderserver.repository.YouTubeSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:env.properties")
public class SearchService {

    private final GoogleFeign googleFeign;
    @Value("${GOOGLE_KEY}")
    private String googleAppKey;

    public SearchService(GoogleFeign googleFeign) {
        this.googleFeign = googleFeign;
    }

    public YouTubeSearchResponse search(String query, String pageToken) {
        try {
            return googleFeign.requestYtList(
                    googleAppKey,
                    10,
                    query,
                    "snippet",
                    "video",
                    pageToken
            );
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.FOURXXCODE, e.getMessage());
        }
    }
}
