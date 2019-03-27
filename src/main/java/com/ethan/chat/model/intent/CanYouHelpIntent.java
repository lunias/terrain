package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class CanYouHelpIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*help\\swith(.*)\\?$");

    public CanYouHelpIntent() {
        super("can_you_help");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "Of course! What exactly would you like help with?";
        };
    }
}
