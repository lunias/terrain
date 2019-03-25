package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

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
}
