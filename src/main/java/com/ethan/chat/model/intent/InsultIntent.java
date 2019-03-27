package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
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

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            user.setFrustration(user.getFrustration() + 5);
            return "That's not very nice. I'm here to help.";
        };
    }
}
