package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class ErrorIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:bsod|error|blue\\sscreen)(.*)");

    public ErrorIntent() {
        super("error");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "Can you provide any more details on the error?";
        };
    }
}
