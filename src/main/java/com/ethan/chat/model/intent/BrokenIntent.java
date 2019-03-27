package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.Parameter;
import com.ethan.chat.model.Tag;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class BrokenIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(broken|broke|malfunctioning|malfunction|bricked|brick)(.*)");

    public BrokenIntent() {
        super("broken");
    }

    @Override
    protected Function<String, Set<Parameter>> getParameterExtractor(List<String> words, List<Tag> tags) {
        return super.getParameterExtractor(words, tags);
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }
}
