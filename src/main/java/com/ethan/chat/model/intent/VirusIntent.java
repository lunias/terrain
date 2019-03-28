package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class VirusIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:virus|viruses|malware|spyware|hacked)(.*)");

    public VirusIntent() {
        super("virus");
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
                    return "A virus on your " + product + "? Sounds serious.";
                }
            }
            return "You mentioned a virus. Do you think that you might have one?";
        };
    }
}
