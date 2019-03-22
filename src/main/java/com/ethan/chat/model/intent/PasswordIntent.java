package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class PasswordIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:passwords|password|secrets|secret)(.*)");

    public PasswordIntent() {
        super("password");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
