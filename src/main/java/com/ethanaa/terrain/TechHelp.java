package com.ethanaa.terrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TechHelp {

    public static void main(String[] args) {

        System.out.println("Welcome to the Best Buy conversational tech help bot. What can I help you with today?");

        Scanner kb = new Scanner(System.in);
        while(true) {
            String message = kb.nextLine();
            if ("stop".equals(message)) {
                break;
            } else {
                String intent = getIntent(message);
                System.out.println("Intent: " + intent);
            }
        }
        System.exit(0);
    }

    private static final Map<String, Pattern> PATTERN_MAP = new TreeMap<String, Pattern>() {
        {
            put("help_with", Pattern.compile(".*help with (?:my)? (.*)"));
            put("help", Pattern.compile("help"));
            put("fix_my", Pattern.compile(".*fix (?:my)? (.*)"));
            put("fix", Pattern.compile("fix"));
            put("broken_item", Pattern.compile("(?:my)? (.+).*(?:is)?\\sbroken"));
            put("broken", Pattern.compile("broken"));
            put("can_you_help", Pattern.compile(".*help with (.*)\\?$"));
            put("greeting", Pattern.compile("hello|hi|sup|yo"));
        }
    };

    private static String getIntent(String message) {
        String intent = "fallback";
        for (Map.Entry<String, Pattern> entry : PATTERN_MAP.entrySet()) {
            Matcher matcher = entry.getValue().matcher(message);
            if (matcher.matches()) {
                intent = entry.getKey();
                for (int i = 0; i < matcher.groupCount(); i++) {
                    intent += ("\n  match #" + i + ": " + matcher.group(i + 1));
                }
                return intent;
            }
        }
        return intent;
    }
}
