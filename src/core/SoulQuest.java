package core;

import java.util.Scanner;

public class SoulQuest {
    private static GameEngine gameEngine;
    private static boolean isRunning;
    private static final String VERSION = "1.0.0";

    public static void main(String[] args) {
        displayWelcome();
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        String birthdate = "";
        boolean validBirthdate = false;
        
        while (!validBirthdate) {
            System.out.print("Enter your birthdate (MM/DD/YYYY): ");
            birthdate = scanner.nextLine();
            
            if (isValidBirthdate(birthdate)) {
                validBirthdate = true;
            } else {
                System.out.println("✖ Invalid format! Please use MM/DD/YYYY format (e.g., 02/15/2000)");
            }
        }
        
        PlayerHistory history = FileHandler.loadHistory(playerName, birthdate);
        gameEngine = new GameEngine(history);
        isRunning = true;

        while (isRunning) {
            try {
                gameEngine.displayMenu();
                gameEngine.handleUserInput();
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    public static void displayWelcome() {
        System.out.println("\n");
        System.out.println("███████╗ ██████╗ ██╗   ██╗██╗         ██████╗ ██╗   ██╗███████╗███████╗████████╗");
        System.out.println("██╔════╝██╔═══██╗██║   ██║██║        ██╔═══██╗██║   ██║██╔════╝██╔════╝╚══██╔══╝");
        System.out.println("███████╗██║   ██║██║   ██║██║        ██║   ██║██║   ██║█████╗  ███████╗   ██║   ");
        System.out.println("╚════██║██║   ██║██║   ██║██║        ██║▄▄ ██║██║   ██║██╔══╝  ╚════██║   ██║   ");
        System.out.println("███████║╚██████╔╝╚██████╔╝███████╗   ╚██████╔╝╚██████╔╝███████╗███████║   ██║   ");
        System.out.println("╚══════╝ ╚═════╝  ╚═════╝ ╚══════╝    ╚══▀▀═╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ");
        System.out.println();
        System.out.println("              *.*.* Discover Your True Personality Type *.*.*");
        System.out.println("                           Version " + VERSION);
        System.out.println();
    }

    public static void displayExit() {
        System.out.println("\n✦•···························•✦•···························•✦");
        System.out.println("\n        ╔════════════════════════════════════════════════════════════╗");
        System.out.println("        ║              Thank you for playing Soul Quest!             ║");
        System.out.println("        ║          May your journey continue to enlighten you.       ║");
        System.out.println("        ╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("███████╗ ██████╗ ██╗   ██╗██╗         ██████╗ ██╗   ██╗███████╗███████╗████████╗");
        System.out.println("██╔════╝██╔═══██╗██║   ██║██║        ██╔═══██╗██║   ██║██╔════╝██╔════╝╚══██╔══╝");
        System.out.println("███████╗██║   ██║██║   ██║██║        ██║   ██║██║   ██║█████╗  ███████╗   ██║   ");
        System.out.println("╚════██║██║   ██║██║   ██║██║        ██║▄▄ ██║██║   ██║██╔══╝  ╚════██║   ██║   ");
        System.out.println("███████║╚██████╔╝╚██████╔╝███████╗   ╚██████╔╝╚██████╔╝███████╗███████║   ██║   ");
        System.out.println("╚══════╝ ╚═════╝  ╚═════╝ ╚══════╝    ╚══▀▀═╝  ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ");
        System.out.println();
        System.out.println("                    Until we meet again, wanderer...");
        System.out.println();
    }

    private static void handleException(Exception e) {
        System.out.println("\nⓘ⁴⁰⁴ An unexpected error occurred: " + e.getMessage());
        System.out.println("Please try again.");
    }

    private static boolean isValidBirthdate(String birthdate) {
        if (!birthdate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        
        String[] parts = birthdate.split("/");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        if (year < 1900 || year > 2024) return false;
        
        return true;
    }
}
