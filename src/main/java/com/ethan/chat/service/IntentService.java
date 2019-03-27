package com.ethan.chat.service;

import com.ethan.chat.model.Intent;
import com.ethan.chat.model.Tag;
import com.ethan.chat.model.intent.*;
import com.knowledgebooks.nlp.util.Tokenizer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.knowledgebooks.nlp.fasttag.FastTag.tag;

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
            add(new SoftwareIntent());
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
            add(new ProvidingInfoIntent());
            add(new GenericItemHelpIntent());
            add(new ClosingIntent());
            add(new MisunderstandingIntent());
            add(new RepeatIntent());
            add(new GreetingIntent());
            add(new ThanksIntent());
            add(new InsultIntent());
            add(new FallbackIntent());
        }
    };

    public IntentService() {
        //
    }

    public static List<Intent.IntentMatch> detectIntents(String utterance) {

        List<String> words = Tokenizer.wordsToList(utterance);
        List<Tag> tags = Tag.parse(tag(words));

        System.out.println("\n:: Parts of Speech ::");
        for (int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i) + " = " + tags.get(i));
        }

        List<Intent.IntentMatch> matches = new ArrayList<>();
        for (int i = 0; i < PRIORITIZED_INTENTS.size(); i++) {
            Intent.IntentMatch match = PRIORITIZED_INTENTS.get(i).matches(utterance, words, tags);
            if (match.matches()) {
                matches.add(match);
            }
        }

        return matches;
    }
}
