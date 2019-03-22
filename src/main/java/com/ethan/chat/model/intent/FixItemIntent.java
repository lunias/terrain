package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class FixItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*(?:fixing|fix|repairing|repair)\\s(?:my\\s)?(.*)");

    public FixItemIntent() {
        super("fix_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
