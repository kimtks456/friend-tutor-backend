package com.gdsc.solutionChallenge.global.utils;


public class PostUtil {

    public static String youtubeLinkParser(String url) {
        if (url.isBlank()) {
            return "";
        }

        String videoId;
        int start;

        if (url.contains("youtu.be/")) {
            // Extract video ID from youtu.be link
            start = url.lastIndexOf("/") + 1;
            videoId = url.substring(start, start + 11);
        } else if (url.contains("youtube.com/watch?v=")) {
            // Extract video ID from youtube.com link
            start = url.indexOf("v=") + 2;
            videoId = url.substring(start, start + 11);
        } else if (url.contains("youtube.com/shorts/")) {
            // Extract video ID from youtube.com shorts link
            throw new IllegalArgumentException("유튜브 숏츠는 업로드할 수 없습니다.");
        } else {
            throw new IllegalArgumentException("Youtube 링크에 youtu.be/ 또는, youtube.com/watch?v=가 없습니다.");
        }
        return videoId;
    }
}

