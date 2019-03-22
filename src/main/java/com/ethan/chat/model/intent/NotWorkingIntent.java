package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class NotWorkingIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work)");

    public NotWorkingIntent() {
        super("not_working");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
