package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class FixIntent extends Intent {

    private static final Pattern PATTERN = Pattern.compile("(.*)(?:fix|repair)(.*)");

    public FixIntent() {
        super("fix");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
