package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ProblemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:problem|issue|defect|locked)(.*)");

    public ProblemIntent() {
        super("problem");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
