package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class NewSequencesIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*(?:new\\ssequence)(.*)");

    public NewSequencesIntent() {
        super("new_sequence");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "I have a beta sequence I've been working on. Would you like to see it?";
        };
    }
}
