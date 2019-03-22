package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class HelpIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)help(.*)");

    public HelpIntent() {
        super("help");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
