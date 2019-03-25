package com.ethan.chat.model;

import java.util.*;

public class Group {

    private List<GroupType> types;
    private String value;
    private Set<GroupTag> tags;

    public Group(List<GroupType> types, String value, Collection<GroupTag> groupTags) {

        this.types = types;
        this.value = value;
        this.tags = new HashSet<>(groupTags);
    }

    public Group(List<GroupType> types, String value) {

        this(types, value, new HashSet<>());
    }

    public List<GroupType> getTypes() {
        return types;
    }

    public void setTypes(List<GroupType> types) {
        this.types = types;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<GroupTag> getTags() {
        return tags;
    }

    public void setTags(Set<GroupTag> tags) {
        this.tags = tags;
    }

    public boolean addTag(GroupTag tag) {
        return this.tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (types != null ? !types.equals(group.types) : group.types != null) return false;
        if (value != null ? !value.equals(group.value) : group.value != null) return false;
        return tags != null ? tags.equals(group.tags) : group.tags == null;
    }

    @Override
    public int hashCode() {
        int result = types != null ? types.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Group.class.getSimpleName() + "[", "]")
                .add("types=" + types)
                .add("value='" + value + "'")
                .add("tags=" + tags)
                .toString();
    }
}
