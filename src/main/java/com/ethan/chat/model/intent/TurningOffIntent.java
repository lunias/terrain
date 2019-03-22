package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class TurningOffIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:keeps|keep|always|sometimes|usually)\\s(?:(?:turn|go|power|shutt)ing\\s(?:off|down)|(?:turns|turn|goes|go|powers|power|shuts|shut)\\s(?:off|down))(.*)");

    public TurningOffIntent() {
        super("turning_off");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
