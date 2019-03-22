package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class PerformanceIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:performing|perform|slowing|slow|stopping|stops|stop|freezing|freeze|locking|lock|spinning|spin|dropping|drop|skipping|skip)(.*)");

    public PerformanceIntent() {
        super("performance");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
