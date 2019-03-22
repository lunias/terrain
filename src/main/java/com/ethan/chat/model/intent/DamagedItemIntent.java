package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class DamagedItemIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:damaged|destroyed|dead|dying|ripped|ripping|crushed|cracking|cracked|falling|fell)");

    public DamagedItemIntent() {
        super("damaged_item");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
