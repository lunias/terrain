package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class NotTurningOnIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|come?|power)ing\\son|(?:turn|come?|power)\\son).*");

    public NotTurningOnIntent() {
        super("not_turning_on");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
