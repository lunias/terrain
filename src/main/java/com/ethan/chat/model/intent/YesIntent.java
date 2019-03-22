package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class YesIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("yes|ya|yeah|sure|ok(?:ay)?|yup|yep|yessir|affirmative|cool|dope|sweet");

    public YesIntent() {
        super("yes");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
