package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class NotTurningOnItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|com|power)ing\\son|(?:turn|come|power)\\son)");

    public NotTurningOnItemIntent() {
        super("item_not_turning_on");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
