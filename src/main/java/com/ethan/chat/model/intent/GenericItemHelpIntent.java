package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class GenericItemHelpIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:my|the)(.*)");

    public GenericItemHelpIntent() {
        super("generic_item_help");
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
                    return "Let me see what I can find on the topic of '" + product + "'.";
                }
            }
            return "Which item do you require help with?";
        };
    }
}
