package com.ethan.chat.model.intent;

import com.ethan.chat.model.GroupType;
import com.ethan.chat.model.Intent;

import java.util.function.Function;
import java.util.regex.Pattern;

public class BrokenIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(broken|broke|malfunctioning|malfunction|bricked|brick)(.*)");

    public BrokenIntent() {
        super("broken");
    }

    @Override
    protected Function<String, GroupType> getGroupTypeExtractor() {
        return super.getGroupTypeExtractor();
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
