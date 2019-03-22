package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class DownloadIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:downloading|download)(.*)");

    public DownloadIntent() {
        super("download");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
