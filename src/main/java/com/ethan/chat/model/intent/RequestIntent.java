package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class RequestIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:need|needing|wanting|want|wanna|how|call|can|what(?!\\si\\ssa))(.*)");

    public RequestIntent() {
        super("request");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
