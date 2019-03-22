package com.ethan.chat.service;

import com.ethan.chat.model.Intent;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IntentService {

    private static final Map<String, Intent> INTENT_MAP = new LinkedHashMap<String, Intent>() {
        {
            put("can_you_help", new Intent("can_you_help",
                    Pattern.compile(".*help\\swith(.*)\\?$")));
            put("help_with", new Intent("help_with",
                    Pattern.compile(".*help\\swith\\s(?:my\\s)?(.*)")));
            put("help", new Intent("help",
                    Pattern.compile("(.*)help(.*)")));
            put("can_you_fix_item", new Intent("can_you_fix_item",
                    Pattern.compile(".*(?:fix|repair)\\s(?:my\\s)?(.*)\\?$")));
            put("fix_item", new Intent("fix_item",
                    Pattern.compile(".*(?:fix|repair)\\s(?:my\\s)?(.*)")));
            put("fix", new Intent("fix",
                    Pattern.compile("(.*)(?:fix|repair)(.*)")));
            put("broken_item", new Intent("broken_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:broken|broke)")));
            put("broken", new Intent("broken",
                    Pattern.compile("(.*)(broken|broke)(.*)")));
            put("damaged_item", new Intent("damaged_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:damaged|destroyed|dead)")));
            put("damaged", new Intent("damaged",
                    Pattern.compile("(.*)(?:damaged|destroyed|dead)(.*)")));
            put("not_working_item", new Intent("not_working_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work)")));
            put("not_working", new Intent("not_working",
                    Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work)")));
            put("not_turning_on_item", new Intent("not_turning_on_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:turning\\son|turn\\son)")));
            put("not_turning_on", new Intent("not_turning_on",
                    Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:turning\\son|turn\\son)")));
            put("cannot_do", new Intent("cannot_do",
                    Pattern.compile("(?:my\\s)?(.*?)(?:(?:can'?t|cannot|can\\snot)|(?:doesn'?t|didn't|does\\snot)|(?:won'?t|will\\snot))(.*)")));
            put("showing_error", new Intent("showing_error",
                    Pattern.compile("(?:my\\s)?(.*?)(?:(?:showing|show)|(?:displaying|displays|display))(.*)")));
            put("error", new Intent("error",
                    Pattern.compile("(.*)error(.*)")));
            put("geek_squad", new Intent("geek_squad",
                    Pattern.compile("(.*)geek\\s?squad(.*)")));
            put("generic_item_help", new Intent("generic_item_help",
                    Pattern.compile("(.*)(?:my)(.*)")));
            put("yes", new Intent("yes",
                    Pattern.compile("yes|ya|yeah|sure|ok(?:ay)?|yup|yep|yessir|affirmative")));
            put("no", new Intent("no",
                    Pattern.compile("no|na|nah|nope|negative")));
            put("greeting", new Intent("greeting",
                    Pattern.compile("hello|hey|hi|sup|yo|holla")));
            put("fallback", new Intent("fallback",
                    Pattern.compile("(.*)")));
        }
    };

    public IntentService() {
        //
    }

    public static Intent getIntent(String name) {
        return INTENT_MAP.get(name);
    }

    public static Intent detectIntent(String message) {
        for (Map.Entry<String, Intent> entry : INTENT_MAP.entrySet()) {
            Matcher matcher = entry.getValue().getPattern().matcher(message.trim());
            if (matcher.matches()) {
                for (int i = 0; i < matcher.groupCount(); i++) {
                    String group = matcher.group(i + 1);
                    if (group != null) {
                        entry.getValue().addGroup(group.trim()
                                .replaceFirst("^my", "")
                                .replaceFirst("^s\\s", "")
                                .replaceFirst("is$", ""));
                    }
                }
                return entry.getValue();
            }
        }
        return INTENT_MAP.get("fallback");
    }
}
