package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ProvidingInfoIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:my|the)\\s?(.*)(address|(phone|cell|work|mobile|office)?\\s?number)(.*)");

    public ProvidingInfoIntent() {
        super("providing_info");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
