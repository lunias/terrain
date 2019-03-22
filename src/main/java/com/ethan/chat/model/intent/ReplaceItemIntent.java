package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ReplaceItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*(?:replacing|replace|replacement)\\s(?:my\\s)?(.*)");

    public ReplaceItemIntent() {
        super("replace_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
