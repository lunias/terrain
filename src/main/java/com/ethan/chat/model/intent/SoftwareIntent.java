package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class SoftwareIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:app|software)(.*)(?:crash|freez|hang|halt|lock|unrespon)(.*)");

    public SoftwareIntent() {
        super("software");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {

            if (!user.getSoftware().isEmpty()) {
                Iterator<String> softwareIterator = new LinkedList<>(user.getSoftware()).descendingIterator();
                if (softwareIterator.hasNext()) {
                    String software = softwareIterator.next();
                    return software + " isn't behaving? Can you please elaborate?";
                }
            }
            return "Which software / application is causing the problem?";
        };
    }
}
