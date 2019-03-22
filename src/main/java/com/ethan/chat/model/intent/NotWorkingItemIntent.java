package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class NotWorkingItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work)");

    public NotWorkingItemIntent() {
        super("item_not_working");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
