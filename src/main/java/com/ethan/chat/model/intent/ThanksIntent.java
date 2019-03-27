package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

public class ThanksIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile(".*thanks|thank\\syou|gracias|danka|arigatou?|ty|tyvm.*");

    public ThanksIntent() {
        super("thanks");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            user.setFrustration(user.getFrustration() - 5);
            return "You're welcome.";
        };
    }
}
