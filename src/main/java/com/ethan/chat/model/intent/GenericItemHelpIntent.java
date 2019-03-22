package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class GenericItemHelpIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:my|the)(.*)");

    public GenericItemHelpIntent() {
        super("generic_item_help");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
