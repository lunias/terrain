package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class PerformanceItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:performing|perform|slowing|slow|stopping|stops|stop|freezing|freeze|locking|lock|spinning|spin|dropping|drops|skipping|skip|lagging|lag)");

    public PerformanceItemIntent() {
        super("performance_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
