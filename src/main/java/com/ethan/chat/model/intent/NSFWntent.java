package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class NSFWntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:nude|naked)(.*)");

    public NSFWntent() {
        super("nsfw");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            return "This is not suitable for work. Are you sure?";
        };
    }
}
