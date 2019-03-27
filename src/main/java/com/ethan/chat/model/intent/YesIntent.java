package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class YesIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("yes|ye?ah?|sure|ok(?:ay)?|yup|yep|yessir|affirmative|cool|dope|sweet");

    public YesIntent() {
        super("yes");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "Got it.";
        };
    }
}
