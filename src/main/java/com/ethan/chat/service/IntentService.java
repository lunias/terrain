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
            put("replace_item", new Intent("replace_item",
                    Pattern.compile(".*(?:replace|replacement)\\s(?:my\\s)?(.*)")));
            put("replace", new Intent("replace",
                    Pattern.compile("(.*)(?:replace|replacement)(.*)")));
            put("return_item", new Intent("return_item",
                    Pattern.compile(".*(?:return|returning)\\s(?:my\\s)?(.*)")));
            put("return", new Intent("return",
                    Pattern.compile("(.*)(?:return|returning)(.*)")));
            put("problem_item", new Intent("problem_item",
                    Pattern.compile("(.*)(?:problem|issue|defect)(:?is\\s)?\\s?(:with\\s)?(?:my\\s)?(\\s.+)")));
            put("problem", new Intent("problem",
                    Pattern.compile("(.*)(?:problem|issue|defect)(.*)")));
            put("broken_item", new Intent("broken_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:broken|broke|malfunctioning|malfunction)")));
            put("broken", new Intent("broken",
                    Pattern.compile("(.*)(broken|broke|malfunctioning|malfunction)(.*)")));
            put("damaged_item", new Intent("damaged_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:damaged|destroyed|dead|dying|ripped|ripping|crushed|cracking|cracked)")));
            put("damaged", new Intent("damaged",
                    Pattern.compile("(.*)(?:damaged|destroyed|dead|dying|ripped|ripping|crushed|cracking|cracked)(.*)")));
            put("performance_item", new Intent("performance_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(:?is\\s)?.*(?:performing|perform|slowing|slow|stopping|stops|stop|freezing|freeze|locking|lock|spinning|spin|dropping|drop|skipping|skip)")));
            put("performance", new Intent("performance",
                    Pattern.compile("(.*)(?:performing|perform|slowing|slow|stopping|stops|stop|freezing|freeze|locking|lock|spinning|spin|dropping|drop|skipping|skip)(.*)")));
            put("not_working_item", new Intent("not_working_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work)")));
            put("not_working", new Intent("not_working",
                    Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)).+(?:working|work)")));
            put("not_turning_on_item", new Intent("not_turning_on_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|com|power)ing\\son|(?:turn|come|power)\\son)")));
            put("not_turning_on", new Intent("not_turning_on",
                    Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|com|power)ing\\son|(?:turn|com|power)\\son)")));
            put("not_turning_off_item", new Intent("not_turning_off_item",
                    Pattern.compile("(?:my\\s)?(.*?)\\s(.*\\s)?(?:is\\s)?.*(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|go|power)ing\\soff|(?:turns|turn|goes|go|powers|power)\\soff)")));
            put("not_turning_off", new Intent("not_turning_off",
                    Pattern.compile("(?:not|isn'?t|don'?t|doesn'?t|(?:does\\snot)|won'?t|(?:will\\snot))\\s(?:(?:turn|go|power)ing\\soff|(?:turns|turn|goes|go|powers|power)\\soff)")));
            put("cannot_do", new Intent("cannot_do",
                    Pattern.compile("(?:my\\s)?(.*?)(?:(?:can'?t|cannot|can\\snot)|(?:doesn'?t|didn't|does\\snot)|(?:won'?t|will\\snot))(.*)")));
            put("showing_error", new Intent("showing_error",
                    Pattern.compile("(?:my\\s)?(.*?)(?:(?:showing|show)|(?:displaying|displays|display))(.*)")));
            put("error", new Intent("error",
                    Pattern.compile("(.*)error(.*)")));
            put("geek_squad", new Intent("geek_squad",
                    Pattern.compile("(.*)geek\\s?squad(.*)")));
            put("request", new Intent("request",
                    Pattern.compile("(.*)(?:need|needing|wanting|want|wanna|how|call|can|what)(.*)")));
            put("yes", new Intent("yes",
                    Pattern.compile("yes|ya|yeah|sure|ok(?:ay)?|yup|yep|yessir|affirmative|cool|dope")));
            put("no", new Intent("no",
                    Pattern.compile("(?:no|nah|na|nope|negative)(.*)")));
            put("generic_item_help", new Intent("generic_item_help",
                    Pattern.compile("(.*)(?:my|the)(.*)")));
            put("greeting", new Intent("greeting",
                    Pattern.compile("(?:hello|hey|hi|sup|yo|holla)(.*)")));
            put("insult", new Intent("insult",
                    Pattern.compile("(.*)(dumb|stupid|shit|fuck|ass|piss|bitch|sucks|dick|cock)(.*)")));
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
                        group = group.trim()
                                .replaceFirst("^my", "")
                                .replaceFirst("^s\\s", "")
                                .replaceFirst("is$", "")
                                .trim();
                        if (!group.isEmpty()) {
                            entry.getValue().addGroup(group);
                        }
                    }
                }
                return entry.getValue();
            }
        }
        return INTENT_MAP.get("fallback");
    }
}
