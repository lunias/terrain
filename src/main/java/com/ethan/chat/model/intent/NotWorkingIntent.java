package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class NotWorkingIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work).*");

    public NotWorkingIntent() {
        super("not_working");
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
                    return "Your " + product + " isn't working? Have you tried turning it off and back on again?";
                }
            }
            return "What exactly isn't working?";
        };
    }
}
