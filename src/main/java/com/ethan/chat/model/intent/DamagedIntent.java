package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class DamagedIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:damaged|destroyed|dead|dying|ripped|ripping|crushed|cracking|cracked|dropped|drop|fell)(.*)");

    public DamagedIntent() {
        super("damaged");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
