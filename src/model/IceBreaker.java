package model;

import enums.IceBreakerType;
import java.util.Scanner;

public class IceBreaker extends Question {
    private String content;
    private IceBreakerType type;
    private String answer;
    private Scanner scanner;
    private String[] riddleChoices;
    private int correctAnswerIndex;

    public IceBreaker(String content, IceBreakerType type, int questionNumber) {
        super("Ice Breaker Time!", questionNumber);
        this.content = content;
        this.type = type;
        this.scanner = new Scanner(System.in);
        this.riddleChoices = new String[0];
        this.correctAnswerIndex = -1;
        this.answer = "";
    }

    public IceBreaker(String content, IceBreakerType type, int questionNumber, String[] choices, int correctIndex, String answer) {
        super("Ice Breaker Time!", questionNumber);
        this.content = content;
        this.type = type;
        this.scanner = new Scanner(System.in);
        this.riddleChoices = choices;
        this.correctAnswerIndex = correctIndex;
        this.answer = answer;
    }
    
    public IceBreaker(String content, IceBreakerType type, int questionNumber, String correctAnswer) {
        super("Ice Breaker Time!", questionNumber);
        this.content = content;
        this.type = type;
        this.scanner = new Scanner(System.in);
        this.riddleChoices = new String[0];
        this.correctAnswerIndex = -1;
        this.answer = correctAnswer;
    }

    private void displayRiddle() {
        System.out.println(" Riddle: " + content);
        System.out.println();
        for (int i = 0; i < riddleChoices.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + riddleChoices[i]);
        }
        System.out.println("  [" + (riddleChoices.length + 1) + "] Give up");
        System.out.println("\nYour answer: ");
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ ICE BREAKER TIME!                                          ║");   
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        switch (type) {
            case FUN_FACT:
                System.out.println("☆ Fun Fact: " + content);
                System.out.println("\nPress ENTER to continue...");
                break;
            case QUOTE:
                System.out.println("☘︎ Quote of the moment: \"" + content + "\"");
                System.out.println("\nPress ENTER to continue...");
                break;
            case SCRAMBLED_WORD:
                System.out.println("꩜ Unscramble this word: " + content);
                System.out.println("\nType your answer or 'give up' to skip: ");
                break;
            case RIDDLE:
                displayRiddle();
                break;
        }
    }

    @Override
    public String processAnswer(String answer) {
        if (type == IceBreakerType.SCRAMBLED_WORD) {
            if (answer.trim().equalsIgnoreCase("give up")) {
                System.out.println("\n☠︎︎ No worries! The answer was: " + this.answer);
            } else if (answer.trim().equalsIgnoreCase(this.answer)) {
                System.out.println("\nᯓ★ Correct! You unscrambled it!");
            } else {
                System.out.println("\n✖ Incorrect! The answer was: " + this.answer);
            }
        } else if (type == IceBreakerType.RIDDLE) {
            try {
                int choice = Integer.parseInt(answer.trim());
                if (choice == riddleChoices.length + 1) {
                    System.out.println("\n☠︎︎ No worries! The answer was: " + this.answer);
                } else if (choice == correctAnswerIndex + 1) {
                    System.out.println("\nᯓ★ Correct! Well done!");
                } else {
                    System.out.println("\n✖ Incorrect! The correct answer was: " + this.answer);
                }
            } catch (NumberFormatException e) {
                System.out.println("\n✖ Invalid input. The correct answer was: " + this.answer);
            }
        }
        return "icebreaker_complete";
    }

    public IceBreakerType getType() { return type; }
}
