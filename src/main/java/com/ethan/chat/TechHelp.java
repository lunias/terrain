package com.ethan.chat;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.Parameter;
import com.ethan.chat.model.User;
import com.ethan.chat.service.IntentService;

import java.util.List;
import java.util.Scanner;

public class TechHelp {

    public static void main(String[] args) {

        System.out.println("Welcome to the Best Buy conversational tech help bot. What can I help you with today?");

        User user = new User();

        Scanner kb = new Scanner(System.in);
        while(true) {
            String message = kb.nextLine().replaceAll("'", "");
            if ("stop".equals(message)) {
                break;
            } else {
                List<Intent.IntentMatch> matches = IntentService.detectIntents(message);
                System.out.println("\n:: Matches ::");
                Intent.IntentMatch firstMatch = null;
                for (Intent.IntentMatch match : matches) {
                    if (firstMatch == null) firstMatch = match;
                    System.out.println("Intent: " + match.getIntent());
                    for (Parameter parameter : match.getParameters()) {
                        System.out.println(parameter.getTypes() + " -> " + parameter.getValue());
                    }
                    System.out.println();
                }
                if (firstMatch != null) {

                    String here = user.getGoalIntentName();
                    String going = firstMatch.getIntent();

                    System.out.println("Transition: from " + here + " to " + going + "\n");

                    user.setGoalIntentName(firstMatch.getIntent());
                    user.addIntentHistory(firstMatch);

                    System.out.println((char)27 + "[33m" + "Response: " + firstMatch.respond(user) + (char)27 + "[0m" + "\n");
                    System.out.println("User: " + user + "\n");
                }
            }
        }
        System.exit(0);
    }
}
