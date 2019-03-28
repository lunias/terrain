package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class NoiseIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:noise|sound|buzz|hum)(.*)");

    public NoiseIntent() {
        super("noise");
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
                    return "Your " + product + " is making noise? Can you please elaborate on the specific sound?";
                }
            }
            return "What's making noise?";
        };
    }
}
