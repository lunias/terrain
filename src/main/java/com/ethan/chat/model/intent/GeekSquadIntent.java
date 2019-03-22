package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class GeekSquadIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)geek\\s?squad(.*)");

    public GeekSquadIntent() {
        super("geek_squad");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
