package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ThanksIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("thanks|thank\\syou|gracias|danka|arigatou?|ty|tyvm|");

    public ThanksIntent() {
        super("thanks");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
