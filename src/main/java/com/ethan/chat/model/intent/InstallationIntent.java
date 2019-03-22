package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;

import java.util.regex.Pattern;

public class InstallationIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:installation|installing|install|setup|(?:setting\\sup)|upgrading|upgrade|updating|update)(.*)");

    public InstallationIntent() {
        super("installation");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
