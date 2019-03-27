package com.ethan.chat.model;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Set;

public class User {

    private float confusion = 0.0f;
    private float frustration = 0.0f;
    private String goalIntentName;
    private ArrayDeque<Intent.IntentMatch> intentHistory = new ArrayDeque<>();
    private String name;
    private String address;
    private String zip;
    private String phoneNumber;
    private Set<String> products = new LinkedHashSet<>();

    public User() {
        this("fallback");
    }

    public User(String goalIntentName) {
        this.goalIntentName = goalIntentName;
    }

    public float getConfusion() {
        return confusion;
    }

    public void setConfusion(float confusion) {
        this.confusion = confusion;
    }

    public float getFrustration() {
        return frustration;
    }

    public void setFrustration(float frustration) {
        this.frustration = frustration;
    }

    public String getGoalIntentName() {
        return goalIntentName;
    }

    public void setGoalIntentName(String goalIntentName) {
        this.goalIntentName = goalIntentName;
    }

    public ArrayDeque<Intent.IntentMatch> getIntentHistory() {
        return intentHistory;
    }

    public void addIntentHistory(Intent.IntentMatch intentMatch) {
        for (Parameter parameter : intentMatch.getParameters()) {

            Set<ParameterType> types = parameter.getTypes();

            if (types.contains(ParameterType.PHONE_NUMBER)) {
                this.phoneNumber = parameter.getValue();
            } else if (types.contains(ParameterType.ZIP)) {
                this.zip = parameter.getValue();
            } else if (types.contains(ParameterType.PRODUCT)) {
                this.products.add(parameter.getValue());
            }
        }
        this.intentHistory.push(intentMatch);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Set<String> getProducts() {
        return products;
    }

    public void setProducts(Set<String> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "User{" +
                "confusion=" + confusion +
                ", frustration=" + frustration +
                ", goalIntentName='" + goalIntentName + '\'' +
                ", intentHistory=" + intentHistory +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
