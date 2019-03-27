package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class ReturnIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:return|returning)(.*)");

    public ReturnIntent() {
        super("return");
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
                    return "Sounds like you'd like to return your " + product + ". Do you have a receipt?";
                }
            }
            return "What would you like to return?";
        };
    }
}
