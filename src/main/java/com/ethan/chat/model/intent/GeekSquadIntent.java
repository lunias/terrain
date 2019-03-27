package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class GeekSquadIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)geek\\s?squad(.*)");

    public GeekSquadIntent() {
        super("geek_squad");
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
                    return "Reaching out to Geek Squad regarding your " + product + ".";
                }
            }
            return "Ok. Are you able to provide some additional information about what we may be able to help you with?";
        };
    }
}
