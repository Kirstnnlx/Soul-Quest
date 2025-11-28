package model;

import enums.PersonalityType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Personality {
    private PersonalityType type;
    private String description;
    private String wittyMessage;
    private LocalDateTime dateUnlocked;
    private List<String> traits;

    public Personality(PersonalityType type) {
        this.type = type;
        this.description = type.getDescription();
        this.dateUnlocked = LocalDateTime.now();
        this.traits = new ArrayList<>();
        this.wittyMessage = generateWittyMessage();
    }

    private String generateWittyMessage() {
        String[] messages = {
            ">> Finalizing Soul Quest Log. (Warning: Results may reveal your true self).",
            ">> Personality unlocked! The universe nods in approval.",
            ">> Your soul signature has been decoded. Fascinating...",
            ">> Analysis complete. You're more interesting than you thought.",
            ">> Soul scan finished. The results might surprise you!",
            ">> Congratulations! You've unlocked a piece of your cosmic puzzle."
        };
        return messages[new Random().nextInt(messages.length)];
    }

    public void displayResult() {
        System.out.println("\n✦•···························•✦•···························•✦");
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  YOUR PERSONALITY REVEALED                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println(wittyMessage);
        System.out.println();
        System.out.println("You are: " + type.getDisplayName());
        System.out.println(description);
        System.out.println();
        System.out.println("Unlocked on: " + dateUnlocked.toLocalDate());
        System.out.println("════════════════════════════════════════════════════════════");
    }

    public PersonalityType getType() { return type; }
    public String getWittyMessage() { return wittyMessage; }
    
    @Override
    public String toString() {
        return type.getDisplayName() + " (Unlocked: " + dateUnlocked.toLocalDate() + ")";
    }
}
