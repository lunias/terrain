package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class FallbackIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)");

    public FallbackIntent() {
        super("fallback");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
