package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ErrorIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)error(.*)");

    public ErrorIntent() {
        super("error");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
