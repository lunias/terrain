package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class ProblemItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:problem|issue|defect|locked)(:?is\\s)?\\s?(:with\\s)?(?:my\\s)?(\\s.+)");

    public ProblemItemIntent() {
        super("problem_item");
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
                    return "Sounds like there's an issue with your " + product + ". Can you please elaborate?";
                }
            }
            return "The issue is with which item?";
        };
    }
}
