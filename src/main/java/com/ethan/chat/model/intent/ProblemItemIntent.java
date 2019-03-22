package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class ProblemItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:problem|issue|defect|locked)(:?is\\s)?\\s?(:with\\s)?(?:my\\s)?(\\s.+)");

    public ProblemItemIntent() {
        super("problem_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
