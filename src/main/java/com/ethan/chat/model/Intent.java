package com.ethan.chat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Intent {

    private String name;
    private Pattern pattern;
    private List<String> groups;

    public Intent(String name, Pattern pattern, List<String> groups) {
        this.name = name;
        this.pattern = pattern;
        this.groups = groups;
    }

    public Intent(String name, Pattern pattern) {
        this(name, pattern, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public List<String> getGroups() {
        return groups;
    }

    public boolean addGroup(String group) {
        return this.groups.add(group);
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
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
                .add("groups=" + groups)
                .toString();
    }
}
