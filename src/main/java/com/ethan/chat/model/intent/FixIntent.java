package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class FixIntent extends Intent {

    private static final Pattern PATTERN = Pattern.compile("(.*)(?:fix|repair)(.*)");

    public FixIntent() {
        super("fix");
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
                    return "Sure. We can definitely help get your " + product + " fixed.";
                }
            }
            return "Of course! What exactly would you like repaired?";
        };
    }
}
