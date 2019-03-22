package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class CannotDoIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)(?:(?:can'?t|cannot|can\\snot)|(?:doesn'?t|didn't|does\\snot)|(?:won'?t|will\\snot))(.*)");

    public CannotDoIntent() {
        super("cannot_do");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
