package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class ClosingIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:later|peace|ciao|bye|cya|(?:see\\sy))(.*)");

    public ClosingIntent() {
        super("closing");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "See you later, alligator.";
        };
    }
}
