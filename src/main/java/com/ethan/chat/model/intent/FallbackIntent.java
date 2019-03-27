package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class FallbackIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)");

    public FallbackIntent() {
        super("fallback");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "I'm Sorry. I couldn't understand that request.";
        };
    }
}
