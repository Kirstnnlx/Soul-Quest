package model;

import core.FileHandler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlayerHistory {
    private String playerName;
    private String birthdate;
    private List<Personality> unlockedPersonalities;
    private int completedGames;
    private LocalDateTime lastPlayed;

    public PlayerHistory(String playerName, String birthdate) {
        this.playerName = playerName;
        this.birthdate = birthdate;
        this.unlockedPersonalities = new ArrayList<>();
        this.completedGames = 0;
        this.lastPlayed = LocalDateTime.now();
    }

    public void addPersonality(Personality personality) {
        unlockedPersonalities.add(personality);
        completedGames++;
        lastPlayed = LocalDateTime.now();
        System.out.println("\n⋆⭒˚.⋆ Personality added to your collection! ⋆⭒˚.⋆");
    }

    public void displayHistory() {
        System.out.println("\n✦•···························•✦•···························•✦");
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                 YOUR PERSONALITY COLLECTION                ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("| Player: " + playerName);
        System.out.println("| Cosmic Alignment: " + birthdate);
        System.out.println("| Games Completed: " + completedGames);
        System.out.println("| Last Played: " + lastPlayed.toLocalDate());
        System.out.println("\n| Unlocked Personalities:");
        
        if (unlockedPersonalities.isEmpty()) {
            System.out.println("  No personalities unlocked yet. Start playing!");
        } else {
            for (int i = 0; i < unlockedPersonalities.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + unlockedPersonalities.get(i));
            }
        }
        System.out.println("════════════════════════════════════════════════════════════");
    }

    public void deleteHistory() {
        unlockedPersonalities.clear();
        completedGames = 0;
        System.out.println("\n🗑♲ History cleared successfully!");
    }

    public void saveToFile() {
        FileHandler.saveHistory(this);
    }

    public String getPlayerName() { return playerName; }
    public String getBirthdate() { return birthdate; }
    public String getUserId() { return playerName.toLowerCase() + "_" + birthdate.replace("/", ""); }
    public List<Personality> getUnlockedPersonalities() { return unlockedPersonalities; }
    public int getCompletedGames() { return completedGames; }
}
