package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.Parameter;
import com.ethan.chat.model.Tag;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class BrokenIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(broken|broke|malfunctioning|malfunction|bricked|brick|busted|bust)(.*)");

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

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            if (!user.getProducts().isEmpty()) {
                Iterator<String> productIterator = new LinkedList<>(user.getProducts()).descendingIterator();
                if (productIterator.hasNext()) {
                    String product = productIterator.next();
                    return "Dang. That sounds rough. Sorry to hear that your " + product + " is broken.";
                }
            }
            return "Ah, that's not great. What exactly is broken?";
        };
    }
}
