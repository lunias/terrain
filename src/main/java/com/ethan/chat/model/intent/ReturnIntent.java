package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ReturnIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:return|returning)(.*)");

    public ReturnIntent() {
        super("return");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
