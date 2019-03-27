package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class ReplaceItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*(?:replacing|replace|replacement)\\s(?:my\\s)?(.*)");

    public ReplaceItemIntent() {
        super("replace_item");
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
                    return "Looking to replace your " + product + "? I'll check on that for you.";
                }
            }
            return "What do you want a replacement for?";
        };
    }
}
