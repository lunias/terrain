package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

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
}
