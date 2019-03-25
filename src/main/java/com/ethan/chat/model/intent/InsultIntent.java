package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class InsultIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(dumb|stupid|shit|fuck|ass|piss|bitch|sucks|suck|dick|cock|loser|idiot)(.*)");

    public InsultIntent() {
        super("insult");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
