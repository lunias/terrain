package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
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

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            if (!user.getProducts().isEmpty()) {
                Iterator<String> productIterator = new LinkedList<>(user.getProducts()).descendingIterator();
                if (productIterator.hasNext()) {
                    String product = productIterator.next();
                    return "I'll attempt to redirect your request for '" + product + "' to the appropriate channel.";
                }
            }
            return "Understood. Let me see what I can do about that.";
        };
    }
}
