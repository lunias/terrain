package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ReplaceIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:replacing|replace|replacement)(.*)");

    public ReplaceIntent() {
        super("replace");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
