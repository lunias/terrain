package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class BrokenItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:broken|broke|malfunctioning|malfunction|bricked|brick)");

    public BrokenItemIntent() {
        super("broken_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
