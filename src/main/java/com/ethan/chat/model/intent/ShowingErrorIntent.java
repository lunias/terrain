package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ShowingErrorIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)(?:(?:showing|show)|(?:displaying|displays|display))(.*)");

    public ShowingErrorIntent() {
        super("showing_error");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
