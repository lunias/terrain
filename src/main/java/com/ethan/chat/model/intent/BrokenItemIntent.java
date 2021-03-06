package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class BrokenItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:broken|broke|malfunctioning|malfunction|bricked|brick|busted|bust)");

    public BrokenItemIntent() {
        super("broken_item");
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
                    return "Dang. That sounds rough. Sorry to hear that your " + product + " is broken.";
                }
            }
            return "Ah, that's not great. What exactly is broken?";
        };
    }
}
