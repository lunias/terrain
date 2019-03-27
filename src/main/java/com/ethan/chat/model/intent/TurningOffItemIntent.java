package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class TurningOffItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:keeps|keep|always|sometimes|usually)\\s(?:(?:turn|go|power|shutt)ing\\s(?:off|down)|(?:turns|turn|goes|go|powers|power|shuts|shut)\\s(?:off|down))(.*)");

    public TurningOffItemIntent() {
        super("item_turning_off");
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
                    return "Got it. Your " + product + " turns off unexpectedly. Let me see if I can find a solution.";
                }
            }
            return "Really? I didn't get all of that though. What's turning off?";
        };
    }
}
