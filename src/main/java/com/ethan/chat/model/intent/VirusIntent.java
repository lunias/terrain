package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class VirusIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:virus|viruses|malware|spyware|hacked)(.*)");

    public VirusIntent() {
        super("virus");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
