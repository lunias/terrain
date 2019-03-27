package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class NotTurningOnIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|come?|power|boot|start)ing\\s?(?:on|up)|(?:turn|come?|power|boot|start)\\s?(?:on|up)).*");

    public NotTurningOnIntent() {
        super("not_turning_on");
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
                    return "Got it. Your " + product + " isn't turning on. Let me see if I can find a solution.";
                }
            }
            return "Really? I didn't get all of that though. What's not turning on?";
        };
    }
}
