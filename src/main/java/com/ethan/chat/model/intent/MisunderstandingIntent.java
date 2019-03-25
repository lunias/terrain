package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class MisunderstandingIntent extends Intent {

    private static final Pattern PATTERN = Pattern.compile("(.*)(?:misunderstood|understand|(?:not?\\s?(right|correct|.*close|what\\si\\ssa)))(.*)");

    public MisunderstandingIntent() {
        super("misunderstanding");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
