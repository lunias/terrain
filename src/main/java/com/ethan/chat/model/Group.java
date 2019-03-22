package com.ethan.chat.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {

    private GroupType type;
    private String value;
    private Set<GroupTag> tags;

    public Group(GroupType type, String value, Collection<GroupTag> groupTags) {

        this.type = type;
        this.value = value;
        this.tags = new HashSet<>(groupTags);
    }

    public Group(GroupType type, String value) {

        this(type, value, new HashSet<>());
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
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

        if (type != group.type) return false;
        if (!Objects.equals(value, group.value)) return false;
        return Objects.equals(tags, group.tags);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", tags=" + tags +
                '}';
    }
}
