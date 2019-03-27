package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.regex.Pattern;

public class DownloadIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:downloading|download)(.*)");

    public DownloadIntent() {
        super("download");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {

            if (!user.getSoftware().isEmpty()) {
                Iterator<String> softwareIterator = new LinkedList<>(user.getSoftware()).descendingIterator();
                if (softwareIterator.hasNext()) {
                    String software = softwareIterator.next();
                    return "Let me see if I can find a link to " + software + ".";
                }
            }
            return "Which software can I help you download?";
        };
    }
}
