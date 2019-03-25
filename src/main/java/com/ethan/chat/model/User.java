package com.ethan.chat.model;

import java.util.ArrayDeque;

public class User {

    private float confusion = 0.0f;
    private float frustration = 0.0f;
    private Intent objective;
    private ArrayDeque<Intent.IntentMatch> intentHistory = new ArrayDeque<>();
    private String name;
    private String address;
    private String phoneNumber;

    public User(Intent objective) {
        this.objective = objective;
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

    public Intent getObjective() {
        return objective;
    }

    public void setObjective(Intent objective) {
        this.objective = objective;
    }

    public ArrayDeque<Intent.IntentMatch> getIntentHistory() {
        return intentHistory;
    }

    public void setIntentHistory(ArrayDeque<Intent.IntentMatch> intentHistory) {
        this.intentHistory = intentHistory;
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
}
