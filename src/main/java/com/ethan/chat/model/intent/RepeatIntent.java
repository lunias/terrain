package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.Parameter;
import com.ethan.chat.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class RepeatIntent extends Intent {

    private static final Pattern PATTERN = Pattern.compile("(.*)(?:repeat|again|(onc?e\\smore))(.*)");

    public RepeatIntent() {
        super("repeat");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            user.setConfusion(user.getConfusion() + 1);
            Iterator<IntentMatch> parameters = user.getIntentHistory().iterator();
            for (int i = 0; parameters.hasNext() && i < 2; i++) {
                IntentMatch intentMatch = parameters.next();
                if (i == 1) {
                    if (intentMatch.getIntent().equalsIgnoreCase("repeat")) {
                        break;
                    }
                    return intentMatch.respond(user);
                }
            }
            return "I'm not sure what to repeat. What can I help you with?";
        };
    }
}
