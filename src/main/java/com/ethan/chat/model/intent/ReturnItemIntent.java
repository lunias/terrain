package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ReturnItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*(?:return|returning)\\s(?:my\\s)?(.*)");

    public ReturnItemIntent() {
        super("return_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
