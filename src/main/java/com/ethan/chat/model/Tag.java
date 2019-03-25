package com.ethan.chat.model;

import java.util.EnumSet;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public enum Tag {

    NOUN("NN", "Noun sing. or mass", "dog"),
    NOUN_PLURAL("NNS", "Noun, plural", "dogs"),
    PROPER_NOUN_SING("NNP", "Proper noun, sing.", "Edinburgh"),
    PROPER_NOUN_PLURAL("NNPS", "Proper noun, plural", "Smiths"),

    PERSONAL_PRONOUN("PRP", "Personal pronoun", "I,you,she"),
    POSSESSIVE_PERSONAL_PRONOUN("PRP$", "Personal pronoun", "I,you,she"),
    POSSESSIVE_PRONOUN("PP$", "Possessive pronoun", "my,one's"),
    WH_PRONOUN("WP", "Wh pronoun", "who,what"),

    ADVERB("RB", "Adverb", "quickly"),
    ADVERB_COMPARATIVE("RBR", "Adverb, comparative", "faster"),
    ADVERB_SUPERLATIVE("RBS", "Adverb, superlative", "fastest"),
    WH_ADVERB("WRB", "Wh-adverb", "how,where"),

    ADJECTIVE("JJ", "Adjective", "big"),
    ADJ_COMPARATIVE("JJR", "Adj., comparative", "bigger"),
    ADJ_SUPERLATIVE("JJS", "Adj., superlative", "biggest"),

    VERB_BASE_FORM("VB", "verb, base form", "eat"),
    VERB_PRESENT("VBP", "Verb, present", "eat"),
    VERB_GERUND("VBG", "verb, gerund", "eating"),
    VERB_PRESENT_Z("VBZ", "Verb, present", "eats"),
    VERB_PAST_PART("VBN", "verb, past part", "eaten"),
    VERB_PAST_TENSE("VBD", "verb, past tense", "ate"),

    TO("TO", "'to'", "to"),

    DETERMINER("DT", "Determiner", "the,some"),
    WH_DETERMINER("WDT", "Wh-determiner", "which,that"),
    PREDETERMINER("PDT", "Predeterminer", "all, both"),

    CARDINAL_NUMBER("CD", "Cardinal number", "one,two"),

    SYMBOL("SYM", "Symbol", "+,%,&"),
    DOLLAR_SIGN("$", "Dollar sign", "$"),
    POUND_SIGN("#", "Pound sign", "#"),
    QUOTE("\"", "quote", "\""),
    LEFT_PAREN("(", "Left paren", "("),
    RIGHT_PAREN(")", "Right paren", ")"),
    COMMA(",", "Comma", ","),
    MID_PUNCTUATION(":", "Mid-sent punctuation", ": ; Ñ"),
    FINAL_PUNCTUATION(".", "Sent-final punctuation", ". ! ?"),

    INTERJECTION("UH", "Interjection", "oh, oops"),
    COORD_CONJUNCN("CC", "Coord Conjuncn", "and,but,or"),
    EXISTENTIAL_THERE("EX", "Existential there", "there"),
    FOREIGN_WORD("FW", "Foreign Word", "mon dieu"),
    LIST_ITEM_MARKER("LS", "List item marker", "1,One"),
    PREPOSITION("IN", "Preposition", "of,in,by"),
    POSSESSIVE_ENDING("POS", "Possessive ending", "'s"),
    MODAL("MD", "Modal", "can,should"),
    PARTICLE("RP", "Particle", "up,off"),
    POSSESSIVE_WH("WP$", "Possessive-Wh", "whose"),
    UNKNOWN("UK", "Unknown", "");

    private String abbreviation;
    private String description;
    private String example;

    Tag(String abbreviation, String description, String example) {

        this.abbreviation = abbreviation;
        this.description = description;
        this.example = example;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public String getExample() {
        return example;
    }

    public static Tag parse(String abbreviation) {
        if (abbreviation != null && !abbreviation.isEmpty()) {
            Tag[] tags = Tag.values();
            for (int i = 0; i < tags.length; i++) {
                if (abbreviation.equalsIgnoreCase(tags[i].abbreviation)) {
                    return tags[i];
                }
            }
        }
        System.out.println("unknown abbreviation: " + abbreviation);
        return UNKNOWN;
    }

    public static List<Tag> parse(List<String> abbreviations) {
        return abbreviations.stream().map(Tag::parse).collect(Collectors.toList());
    }

    public static boolean isNoun(Tag tag) {
        return EnumSet.range(Tag.NOUN, Tag.PROPER_NOUN_PLURAL).contains(tag);
    }

    public static boolean isVerb(Tag tag) {
        return EnumSet.range(Tag.VERB_BASE_FORM, VERB_PAST_TENSE).contains(tag);
    }

    public static boolean isAdverb(Tag tag) {
        return EnumSet.range(Tag.ADVERB, Tag.WH_ADVERB).contains(tag);
    }

    public static boolean isAdjective(Tag tag) {
        return EnumSet.range(Tag.ADJECTIVE, Tag.ADJ_SUPERLATIVE).contains(tag);
    }

    public static boolean isPronoun(Tag tag) {
        return EnumSet.range(Tag.PERSONAL_PRONOUN, Tag.WH_PRONOUN).contains(tag);
    }

    public static boolean isPreposition(Tag tag) {
        return Tag.PREPOSITION.equals(tag);
    }

    public static boolean isDeterminer(Tag tag) {
        return EnumSet.of(Tag.DETERMINER, Tag.WH_DETERMINER, Tag.PREDETERMINER).contains(tag);
    }

    public static boolean isTo(Tag tag) {
        return Tag.TO.equals(tag);
    }

    public static boolean isNumber(Tag tag) {
        return Tag.CARDINAL_NUMBER.equals(tag);
    }

    public static boolean isSymbol(Tag tag) {
        return EnumSet.range(Tag.SYMBOL, Tag.FINAL_PUNCTUATION).contains(tag);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("abbreviation='" + abbreviation + "'")
                .add("description='" + description + "'")
                .add("example='" + example + "'")
                .toString();
    }
}
