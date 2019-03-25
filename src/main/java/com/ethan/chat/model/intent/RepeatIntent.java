package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class RepeatIntent extends Intent {

    private static final Pattern PATTERN = Pattern.compile("(.*)(?:repeat|again|(one\\smore))(.*)");

    public RepeatIntent() {
        super("repeat");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
