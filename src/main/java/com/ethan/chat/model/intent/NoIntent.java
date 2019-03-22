package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class NoIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:no|nah|na|nope|negative)(.*)");

    public NoIntent() {
        super("no");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
