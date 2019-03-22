package com.ethan.chat.service;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.intent.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IntentService {

    private static final List<Intent> PRIORITIZED_INTENTS = new ArrayList<Intent>() {
        {
            add(new VirusIntent());
            add(new CanYouHelpIntent());
            add(new HelpIntent());
            add(new CanYouFixItemIntent());
            add(new FixItemIntent());
            add(new FixIntent());
            add(new ReplaceItemIntent());
            add(new ReplaceIntent());
            add(new ReturnItemIntent());
            add(new ReturnIntent());
            add(new ProblemItemIntent());
            add(new ProblemIntent());
            add(new BrokenItemIntent());
            add(new BrokenIntent());
            add(new DamagedItemIntent());
            add(new DamagedIntent());
            add(new PerformanceItemIntent());
            add(new PerformanceIntent());
            add(new NotWorkingItemIntent());
            add(new NotWorkingIntent());
            add(new NotTurningOnItemIntent());
            add(new NotTurningOnIntent());
            add(new TurningOffItemIntent());
            add(new TurningOffIntent());
            add(new NotTurningOffItemIntent());
            add(new NotTurningOffIntent());
            add(new CannotDoIntent());
            add(new ShowingErrorIntent());
            add(new ErrorIntent());
            add(new GeekSquadIntent());
            add(new InstallationIntent());
            add(new DownloadIntent());
            add(new PasswordIntent());
            add(new RequestIntent());
            add(new YesIntent());
            add(new NoIntent());
            add(new GenericItemHelpIntent());
            add(new ClosingIntent());
            add(new GreetingIntent());
            add(new InsultIntent());
            add(new FallbackIntent());
        }
    };

    public IntentService() {
        //
    }

    public static List<Intent.IntentMatch> detectIntents(String utterance) {

        List<Intent.IntentMatch> matches = new ArrayList<>();
        for (int i = 0; i < PRIORITIZED_INTENTS.size(); i++) {
            Intent.IntentMatch match = PRIORITIZED_INTENTS.get(i).matches(utterance);
            if (match.matches()) {
                matches.add(match);
            }
        }

        return matches;
    }
}
