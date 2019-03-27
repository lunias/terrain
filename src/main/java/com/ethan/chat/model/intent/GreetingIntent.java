package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class GreetingIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:hello|hey|hi|sup|yo(?!u)|holla|good)(.*)");

    public GreetingIntent() {
        super("greeting");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "Hello. What can I help you with today?";
        };
    }
}
