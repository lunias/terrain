package com.ethan.chat.model;

import java.util.*;

public class Parameter implements Comparable<Parameter> {

    private String value;
    private TreeSet<ParameterType> types;

    public Parameter(String value, TreeSet<ParameterType> types) {

        this.value = value;
        this.types = types;
    }

    public Parameter(String value, ParameterType type) {

        this(value, new TreeSet<>(Collections.singletonList(type)));
    }

    public Set<ParameterType> getTypes() {
        return types;
    }

    public void setTypes(Set<ParameterType> types) {
        this.types = new TreeSet<>(types);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(Parameter o) {

        ParameterType ours = types.iterator().next();
        ParameterType theirs = o.getTypes().iterator().next();

        if (ours == null)
            return -1;
        if (theirs == null)
            return 1;

        return theirs.compareTo(ours);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return Objects.equals(types, parameter.types) &&
                Objects.equals(value, parameter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types, value);
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "types=" + types +
                ", value='" + value + '\'' +
                '}';
    }
}
