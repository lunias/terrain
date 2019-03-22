package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class NotTurningOffIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|go|power)ing\\soff|(?:turns|turn|goes|go|powers|power)\\soff)");

    public NotTurningOffIntent() {
        super("not_turning_off");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
