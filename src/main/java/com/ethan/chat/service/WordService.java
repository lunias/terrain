package com.ethan.chat.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class WordService {

    private static final Set<String> PRODUCTS = new HashSet<>(Arrays.asList(
            "computer", "router", "monitor", "screen", "printer",
            "laptop", "desktop", "tower", "phone", "wifi", "internet",
            "tv", "television", "calculator", "cd", "tape", "disc",
            "thing", "pc", "vcr", "dvd player", "dvd", "player", "refrigerator",
            "washing machine", "machine", "speakers", "speaker", "iphone",
            "android", "mobile phone", "pixel", "galaxy note", "note", "galaxy",
            "headphones", "headphone", "receiver", "display", "mouse", "keyboard",
            "google home", "alexa", "google", "lights", "light", "dishwasher",
            "cable", "modem", "chair", "playstation", "xbox 360", "xbox one", "xbox",
            "ps4", "ps3", "ps2", "ps", "dreamcast", "nintendo switch", "switch",
            "nintendo", "vita", "ds", "controller", "toothbrush", "ipad", "tablet",
            "mac"));

    private static final Set<String> PEOPLE = new HashSet<>(Arrays.asList(
            "mom", "mother", "dad", "father", "brother", "sister", "myself", "mine", "me", "i", "you",
            "robot", "cousin", "niece", "aunt", "uncle", "repair man", "repair woman", "repair person",
            "geek squad", "geeksquad", "grandma", "grandpa", "neighbor", "friend", "buddy", "pal",
            "guy", "gal", "person", "partner", "spouse", "wife", "husband"));

    private static final Set<String> SOFTWARE = new HashSet<>(Arrays.asList(
            "word", "microsoft word", "excel", "microsoft excel", "chrome", "google chrome", "firefox",
            "mozilla firefox", "safari", "bestbuy.com", "ios", "android"));

    public WordService() {

    }

    public boolean hasProduct(String product) {
        if (product == null) return false;
        return PRODUCTS.contains(product.toLowerCase());
    }

    public boolean hasPerson(String person) {
        if (person == null) return false;
        return PEOPLE.contains(person.toLowerCase());
    }

    public boolean hasSoftware(String software) {
        if (software == null) return false;
        return SOFTWARE.contains(software.toLowerCase());
    }
}
