package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class SoftwareIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:app|software)(.*)(?:crash|freez|hang|halt|lock|unrespon)(.*)");

    public SoftwareIntent() {
        super("software");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
