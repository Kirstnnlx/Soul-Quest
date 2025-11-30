package core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

public class FileHandler {
    private static final String FILE_PATH = "soul_quest_data.txt";

    public static void saveHistory(PlayerHistory history) {
        try {
            Map<String, List<String>> allData = new HashMap<>();
            if (new File(FILE_PATH).exists()) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
                    String line;
                    String currentUserId = null;
                    List<String> currentUserData = new ArrayList<>();
                    
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("SOUL QUEST - PLAYER DATA") || 
                            line.contains("Personality Archive") || 
                            line.contains("Total Players:") ||
                            (line.startsWith("╔═══") && !line.contains("USER:"))) {
                            continue;
                        }
                        
                        if (line.contains("║ USER:")) {
                            if (currentUserId != null) {
                                allData.put(currentUserId, currentUserData);
                            }
                            currentUserId = extractUserId(line);
                            currentUserData = new ArrayList<>();
                            currentUserData.add(line);
                        } else if (currentUserId != null) {
                            currentUserData.add(line);
                        }
                    }
                    if (currentUserId != null) {
                        allData.put(currentUserId, currentUserData);
                    }
                }
            }

            String userId = history.getUserId();
            List<String> userData = new ArrayList<>();
            
            userData.add("╔═══════════════════════════════════════════════════════════════════════╗");
            userData.add("║ USER: " + centerPad(history.getPlayerName() + " (ID: " + userId + ")", 63) + " ║");
            userData.add("╠═══════════════════════════════════════════════════════════════════════╣");
            userData.add("║  Cosmic Alignment: " + padRight(history.getBirthdate(), 49) + "  ║");
            userData.add("║  User ID: " + padRight(userId, 59) + " ║");
            userData.add("║  Games Completed: " + padRight(String.valueOf(history.getCompletedGames()), 50) + "  ║");
            userData.add("║  Last Played: " + padRight(LocalDateTime.now().toLocalDate().toString(), 54) + "  ║");
            userData.add("╠═══════════════════════════════════════════════════════════════════════╣");
            userData.add("║                        PERSONALITY COLLECTION                         ║");
            userData.add("╠═══════════════════════════════════════════════════════════════════════╣");
            
            if (history.getUnlockedPersonalities().isEmpty()) {
                userData.add("║  No personalities unlocked yet!                                      ║");
            } else {
                int count = 1;
                for (Personality p : history.getUnlockedPersonalities()) {
                    String typeDisplay = p.getType().getDisplayName();
                    String line = "║  [" + count + "] " + typeDisplay;
                    userData.add(padRight(line, 72) + "║");
                    count++;
                }
            }
            
            userData.add("╚═══════════════════════════════════════════════════════════════════════╝");
            userData.add("");
            
            allData.put(userId, userData);

            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {
                writer.println("╔═══════════════════════════════════════════════════════════════════════╗");
                writer.println("║                      SOUL QUEST - PLAYER DATA                         ║");
                writer.println("║                        Personality Archive                            ║");
                writer.println("╠═══════════════════════════════════════════════════════════════════════╣");
                writer.println("║  Total Players: " + padRight(String.valueOf(allData.size()), 51) + "   ║");
                writer.println("╚═══════════════════════════════════════════════════════════════════════╝");
                writer.println();
                
                for (List<String> data : allData.values()) {
                    for (String line : data) {
                        writer.println(line);
                    }
                }
            }
            
            System.out.println("🗁🗐 Progress saved successfully!");
        } catch (IOException e) {
            System.out.println("ⓘ⁴⁰⁴ Error saving data: " + e.getMessage());
        }
    }

    private static String extractUserId(String line) {
        try {
            if (line.contains("(ID:") && line.contains(")")) {
                int startIdx = line.indexOf("(ID:") + 4;
                int endIdx = line.indexOf(")", startIdx);
                if (startIdx > 0 && endIdx > startIdx) {
                    return line.substring(startIdx, endIdx).trim();
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting user ID: " + e.getMessage());
        }
        return "";
    }

    private static String padRight(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < length) {
            sb.append(" ");
        }
        return sb.toString();
    }

    private static String centerPad(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        }
        int totalPadding = length - str.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) sb.append(" ");
        sb.append(str);
        for (int i = 0; i < rightPadding; i++) sb.append(" ");
        return sb.toString();
    }

    public static PlayerHistory loadHistory(String playerName, String birthdate) {
        String userId = playerName.toLowerCase() + "_" + birthdate.replace("/", "");
        
        if (!fileExists()) {
            System.out.println("✨ New soul detected! Creating your cosmic profile...");
            return new PlayerHistory(playerName, birthdate);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
            String line;
            boolean userFound = false;
            PlayerHistory history = null;
            int gamesCompleted = 0;
            
            while ((line = reader.readLine()) != null) {
                if (line.contains("║ USER:") && line.contains("(ID: " + userId + ")")) {
                    userFound = true;
                    history = new PlayerHistory(playerName, birthdate);
                } else if (userFound && line.contains("║  Games Completed:")) {
                    String numStr = line.substring(line.indexOf(":") + 1).trim();
                    numStr = numStr.substring(0, numStr.indexOf("║")).trim();
                    try {
                        gamesCompleted = Integer.parseInt(numStr);
                    } catch (NumberFormatException e) {
                        gamesCompleted = 0;
                    }
                } else if (userFound && line.contains("║  [") && line.contains("]")) {
                    String typeStr = extractPersonalityFromLine(line);
                    if (typeStr != null) {
                        try {
                            PersonalityType type = PersonalityType.valueOf(typeStr);
                            history.getUnlockedPersonalities().add(new Personality(type));
                        } catch (IllegalArgumentException e) {
                            // Skip invalid personality
                        }
                    }
                } else if (userFound && line.startsWith("╚═══")) {
                    break;
                }
            }
            
            if (userFound && history != null) {
                System.out.println("🗁🗐 Welcome back, " + playerName + "! Progress loaded successfully!");
                return history;
            } else {
                System.out.println("\n✨ New soul detected! Creating your cosmic profile...");
                return new PlayerHistory(playerName, birthdate);
            }
        } catch (IOException e) {
            System.out.println("ⓘ⁴⁰⁴ Error loading data: " + e.getMessage());
            return new PlayerHistory(playerName, birthdate);
        }
    }

    private static String extractPersonalityFromLine(String line) {
        for (PersonalityType type : PersonalityType.values()) {
            if (line.contains(type.getDisplayName())) {
                return type.name();
            }
        }
        return null;
    }

    public static boolean fileExists() {
        return new File(FILE_PATH).exists();
    }

    public static void deleteHistory(String userId) {
        try {
            Map<String, List<String>> allData = new HashMap<>();
            
            if (new File(FILE_PATH).exists()) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
                    String line;
                    String currentUserId = null;
                    List<String> currentUserData = new ArrayList<>();
                    
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("SOUL QUEST - PLAYER DATA") || 
                            line.contains("Personality Archive") || 
                            line.contains("Total Players:") ||
                            (line.startsWith("╔═══") && !line.contains("USER:"))) {
                            continue;
                        }
                        
                        if (line.contains("║ USER:")) {
                            if (currentUserId != null && !currentUserId.equals(userId)) {
                                allData.put(currentUserId, currentUserData);
                            }
                            currentUserId = extractUserId(line);
                            currentUserData = new ArrayList<>();
                            currentUserData.add(line);
                        } else if (currentUserId != null) {
                            currentUserData.add(line);
                        }
                    }
                    if (currentUserId != null && !currentUserId.equals(userId)) {
                        allData.put(currentUserId, currentUserData);
                    }
                }

                try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                        new FileOutputStream(FILE_PATH), StandardCharsets.UTF_8))) {
                    writer.println("╔═══════════════════════════════════════════════════════════════════════╗");
                    writer.println("║                      SOUL QUEST - PLAYER DATA                         ║");
                    writer.println("║                        Personality Archive                            ║");
                    writer.println("╠═══════════════════════════════════════════════════════════════════════╣");
                    writer.println("║  Total Players: " + padRight(String.valueOf(allData.size()), 51) + "   ║");
                    writer.println("╚═══════════════════════════════════════════════════════════════════════╝");
                    writer.println();
                    
                    for (List<String> data : allData.values()) {
                        for (String line : data) {
                            writer.println(line);
                        }
                    }
                }
            }
            
            System.out.println("🗑♲ Your history has been deleted!");
        } catch (IOException e) {
            System.out.println("ⓘ⁴⁰⁴ Error deleting history: " + e.getMessage());
        }
    }
}
