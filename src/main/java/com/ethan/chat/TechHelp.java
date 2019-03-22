package com.ethan.chat;

import com.ethan.chat.model.Intent;
import com.ethan.chat.service.IntentService;

import java.util.ArrayList;
import java.util.Scanner;

public class TechHelp {

    public static void main(String[] args) {

        System.out.println("Welcome to the Best Buy conversational tech help bot. What can I help you with today?");

        Scanner kb = new Scanner(System.in);
        while(true) {
            String message = kb.nextLine();
            if ("stop".equals(message)) {
                break;
            } else {
                Intent intent = IntentService.detectIntent(message);
                System.out.println("Intent: " + intent);
                intent.setGroups(new ArrayList<>()); // TODO hack
            }
        }
        System.exit(0);
    }
}
