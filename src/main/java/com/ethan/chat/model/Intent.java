package com.ethan.chat.model;

import com.ethan.chat.service.WordService;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Intent {

    private static final Pattern PHONE_NUMBER_PATT =
            Pattern.compile(".*((\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}).*");

    private static final Pattern ZIP_PATT =
            Pattern.compile(".*\\b(\\d{5}(?:[-\\s]\\d{4})?)\\b.*");

    private String name;
    private WordService wordService;

    public Intent(String name) {

        this.name = name;
        this.wordService = new WordService();
    }

    public abstract Pattern getPattern();

    public Function<User, String> respond() {
        return (user) -> "Alright! Thanks for the information.";
    }

    protected Function<String, String> getGroupPostProcessor() {
        return (groupValue) -> groupValue.trim()
                .replaceFirst("^my", "")
                .replaceFirst("^s\\s", "")
                .replaceFirst("is$", "")
                .trim();
    }

    protected Function<String, Set<Parameter>> getParameterExtractor(List<String> words, List<Tag> tags) {
        return (groupValue) -> {

            Set<Parameter> params = new HashSet<>();

            String firstNoun = null;
            String firstVerb = null;
            Set<String> people = new HashSet<>();
            Set<String> otherProducts = new HashSet<>();
            String software = null;
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                Tag tag = tags.get(i);

                if (Tag.isNoun(tag) && wordService.hasSoftware(word)) {
                    software = word;
                }

                if (firstNoun == null && Tag.isNoun(tag)) {
                    if (wordService.hasProduct(word)) {
                        firstNoun = word;
                    }
                } else if (Tag.isNoun(tag)) {
                    if (wordService.hasProduct(word)) {
                         otherProducts.add(word);
                    }
                }

                if (Tag.isPronoun(tag) || Tag.isNoun(tag)) {
                    if (wordService.hasPerson(word)) {
                        people.add(word);
                    }
                }

                if (firstVerb == null && Tag.isVerb(tag)) {
                    firstVerb = word;
                }
            }

            if (software != null) {
                params.add(new Parameter(software, ParameterType.SOFTWARE));
            }

            if (firstNoun != null) {
                params.add(new Parameter(firstNoun, ParameterType.PRODUCT));
            }

            if (!otherProducts.isEmpty()) {
                otherProducts.forEach(op -> params.add(new Parameter(op, ParameterType.PRODUCT_OTHER)));
            }

            if (!people.isEmpty()) {
                people.forEach(p -> params.add(new Parameter(p, ParameterType.PERSON)));
            }

            if (firstVerb != null) {
                params.add(new Parameter(firstVerb, ParameterType.ACTION));
            }

            String actionExtended = null;
            boolean foundFirstVerb = false;
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                Tag tag = tags.get(i);
                if (foundFirstVerb && (Tag.isAdjective(tag) || Tag.isAdverb(tag) || Tag.isPreposition(tag) || Tag.isVerb(tag))) {
                    actionExtended += " " + word;
                } else if (foundFirstVerb) {
                    break;
                }
                if (word.equals(firstVerb)) {
                    actionExtended = firstVerb;
                    foundFirstVerb = true;
                }
            }

            String location = "";
            boolean foundPreposition = false;
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                Tag tag = tags.get(i);
                if (!foundPreposition && Tag.isPreposition(tag)) {
                    location += word;
                    foundPreposition = true;
                } else if (foundPreposition) {
                    location += " " + word;
                    Tag nextTag = i < words.size() - 1 ? tags.get(i + 1) : null;
                    if (Tag.isNoun(tag) && !Tag.isNoun(nextTag)) {
                        break;
                    }
                }
            }

            if (actionExtended != null && !firstVerb.equals(actionExtended)) {
                params.add(new Parameter(actionExtended, ParameterType.ACTION_EXTENDED));
            }

            if (!location.isEmpty()) {
                params.add(new Parameter(location, ParameterType.LOCATION));
            }

            Matcher phoneNumberMatcher = PHONE_NUMBER_PATT.matcher(groupValue);
            if (phoneNumberMatcher.matches()) {
                params.add(new Parameter(phoneNumberMatcher.group(1), ParameterType.PHONE_NUMBER));
            }

            Matcher zipMatcher = ZIP_PATT.matcher(groupValue);
            if (zipMatcher.matches()) {
                params.add(new Parameter(zipMatcher.group(1), ParameterType.ZIP));
            }

            return params;
        };
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IntentMatch matches(String utterance, List<String> words, List<Tag> tags) {

        return new IntentMatch(getName(),
                getPattern().matcher(utterance.trim()),
                getGroupPostProcessor(), getParameterExtractor(words, tags),
                respond());
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

    public static class IntentMatch implements Iterable<Parameter> {

        private String intent = "fallback";
        private boolean matches = false;
        private int groupCount = -1;
        private Set<Parameter> parameters = new TreeSet<>(Parameter::compareTo);
        private Function<User, String> respond;

        protected IntentMatch(String intentName,
                              Matcher matcher,
                              Function<String, String> groupPostProcessor,
                              Function<String, Set<Parameter>> parameterExtractor,
                              Function<User, String> respond) {

            this.matches = matcher.matches();
            if (this.matches) {
                this.intent = intentName;
                this.groupCount = matcher.groupCount();
                for (int i = 0; i < this.groupCount; i++) {
                    String groupValue = matcher.group(i + 1);
                    if (groupValue != null) {
                        groupValue = groupPostProcessor.apply(groupValue);
                        if (!groupValue.isEmpty()) {
                            parameters.addAll(parameterExtractor.apply(groupValue));
                        }
                    }
                }
                this.respond = respond;
            }
        }

        public boolean matches() {
            return matches;
        }

        public int getGroupCount() {
            return groupCount;
        }

        public String getIntent() {
            return intent;
        }

        public Set<Parameter> getParameters() {
            return parameters;
        }

        public String respond(User user) {
            return this.respond.apply(user);
        }

        @Override
        public Iterator<Parameter> iterator() {
            return parameters.iterator();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntentMatch groups1 = (IntentMatch) o;
            return matches == groups1.matches &&
                    groupCount == groups1.groupCount &&
                    Objects.equals(intent, groups1.intent) &&
                    Objects.equals(parameters, groups1.parameters);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intent, matches, groupCount, parameters);
        }

        @Override
        public String toString() {
            return "IntentMatch{" +
                    "intent='" + intent + '\'' +
                    ", matches=" + matches +
                    ", groupCount=" + groupCount +
                    ", parameters=" + parameters +
                    '}';
        }
    }
}
