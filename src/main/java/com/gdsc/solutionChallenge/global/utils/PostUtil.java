package com.gdsc.solutionChallenge.global.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PostUtil {

    public static String extractVideoId(String url) {
        int start = url.indexOf("=");
        String videoId = url.substring(start + 1, start + 12);

        return videoId;
    }
}

