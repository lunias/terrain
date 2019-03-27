package com.ethan.chat.model.intent;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.Parameter;
import com.ethan.chat.model.ParameterType;
import com.ethan.chat.model.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class ProvidingInfoIntent extends Intent {

    private static final Pattern PATTERN =
            Pattern.compile("(.*)(?:my|the)\\s?(.*)(name|address|zip\\s?(?:code)?|(phone|cell|work|mobile|office)?\\s?number)(.*)");

    public ProvidingInfoIntent() {
        super("providing_info");
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    public Function<User, String> respond() {
        return (user) -> {
            Set<Parameter> parameters = user.getIntentHistory().peek().getParameters();
            for (Parameter parameter : parameters) {
                if (parameter.getTypes().contains(ParameterType.ADDRESS)) {
                    return "Okay. I've recorded your address as '" + parameter.getValue() + "'. Is this correct?";
                } else if (parameter.getTypes().contains(ParameterType.PHONE_NUMBER)) {
                    return "Sure. I've recorded your phone number as '" + parameter.getValue() + "'. Is that right?";
                } else if (parameter.getTypes().contains(ParameterType.ZIP)) {
                    return "Good to know. I've saved your zip code as '" + parameter.getValue() + "'. Look good?";
                }
            }
            return "Noted. How else can I be of service?";
        };
    }
}
