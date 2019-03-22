package com.ethan.chat.model;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Intent {

    private static final List<String> ITEMS = Arrays.asList(
            "computer", "router", "monitor", "screen", "printer",
            "laptop", "desktop", "tower", "phone", "wifi", "internet",
            "tv", "television", "calculator", "cd", "tape", "disc");

    private static final Map<GroupType, Pattern> GROUP_TYPE_PATTERNS = new LinkedHashMap<GroupType, Pattern>() {
        {
            put(GroupType.ITEM, Pattern.compile("(?:" + String.join("|", ITEMS) + ")"));
            put(GroupType.LOCATION, Pattern.compile(".*(?:in|at|on|by|(?:to\\sthe))\\s(.*)"));
            put(GroupType.PERSON, Pattern.compile("(dad|mon|sister|brother|aunt|friend|buddy|pal|guy|gal|boy|man|girl|woman|person)"));
            put(GroupType.SUBJECT, Pattern.compile(""));
            put(GroupType.VERB, Pattern.compile("(.*)(?:p?ed|p?ing)"));
            put(GroupType.ADJECTIVE, Pattern.compile(""));
            put(GroupType.ADVERB, Pattern.compile("(.*)(?:ly)"));
        }
    };

    private String name;

    public Intent(String name) {
        this.name = name;
    }

    public abstract Pattern getPattern();

    protected Function<String, String> getGroupPostProcessor() {
        return (groupValue) -> groupValue.trim()
                .replaceFirst("^my", "")
                .replaceFirst("^s\\s", "")
                .replaceFirst("is$", "")
                .trim();
    }

    protected Function<String, GroupType> getGroupTypeExtractor() {
        return (groupValue) -> {
            for (Map.Entry<GroupType, Pattern> entry : GROUP_TYPE_PATTERNS.entrySet()) {
                GroupType type = entry.getKey();
                Pattern pattern = entry.getValue();
                Matcher matcher = pattern.matcher(groupValue);
                if (matcher.matches()) {
                    return type;
                }
            }
            return GroupType.UNKNOWN;
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IntentMatch matches(String utterance) {

        return new IntentMatch(getName(),
                getPattern().matcher(utterance.trim()),
                getGroupPostProcessor(), getGroupTypeExtractor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Intent intent = (Intent) o;

        return name.equals(intent.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Intent.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }

    public static class IntentMatch implements Iterable<Group> {

        private String intent = "fallback";
        private boolean matches = false;
        private int groupCount = -1;
        private List<Group> groups = new ArrayList<>();

        protected IntentMatch(String intentName,
                              Matcher matcher,
                              Function<String, String> groupPostProcessor,
                              Function<String, GroupType> groupTypeExtractor) {

            this.matches = matcher.matches();
            if (this.matches) {
                this.intent = intentName;
                this.groupCount = matcher.groupCount();
                for (int i = 0; i < this.groupCount; i++) {
                    String groupValue = matcher.group(i + 1);
                    if (groupValue != null) {
                        groupValue = groupPostProcessor.apply(groupValue);
                        if (!groupValue.isEmpty()) {
                            groups.add(new Group(groupTypeExtractor.apply(groupValue), groupValue));
                        }
                    }
                }
            }
        }

        public boolean matches() {
            return matches;
        }

        public int getGroupCount() {
            return groupCount;
        }

        public List<Group> getGroups() {
            return groups;
        }

        @Override
        public Iterator<Group> iterator() {
            return groups.iterator();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntentMatch groups1 = (IntentMatch) o;
            return matches == groups1.matches &&
                    groupCount == groups1.groupCount &&
                    Objects.equals(intent, groups1.intent) &&
                    Objects.equals(groups, groups1.groups);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intent, matches, groupCount, groups);
        }

        @Override
        public String toString() {
            return "IntentMatch{" +
                    "intent='" + intent + '\'' +
                    ", matches=" + matches +
                    ", groupCount=" + groupCount +
                    ", groups=" + groups +
                    '}';
        }
    }
}
