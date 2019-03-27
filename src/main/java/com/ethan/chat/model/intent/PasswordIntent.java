package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class PasswordIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:passwords|password|secrets|secret)(.*)");

    public PasswordIntent() {
        super("password");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            // check verb
            return "I didn't understand. What's the issue with your password?";
        };
    }
}
