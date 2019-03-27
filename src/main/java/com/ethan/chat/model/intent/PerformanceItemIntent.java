package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
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

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            if (!user.getProducts().isEmpty()) {
                Iterator<String> productIterator = new LinkedList<>(user.getProducts()).descendingIterator();
                if (productIterator.hasNext()) {
                    String product = productIterator.next();
                    return "Got it. Your " + product + " isn't performing correctly. Let's see if I have any help to provide on the issue.";
                }
            }
            return "Sounds like a performance issue? What's not working right?";
        };
    }
}
