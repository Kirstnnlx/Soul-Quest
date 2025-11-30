package model;

import core.InvalidInputException;
import java.util.*;

public class PersonalityQuestion extends Question {
    private String[] options;
    private Map<String, Integer> traitWeights;
    private int selectedAnswer;

    public PersonalityQuestion(String questionText, int questionNumber, String[] options) {
        super(questionText, questionNumber);
        this.options = options;
        this.traitWeights = new HashMap<>();
        this.selectedAnswer = -1;
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Question " + questionNumber + "/10                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println(questionText);
        System.out.println();
        
        for (int i = 0; i < options.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + options[i]);
        }
        System.out.println();
    }

    @Override
    public String processAnswer(String answer) {
        try {
            int choice = Integer.parseInt(answer.trim());
            if (choice >= 1 && choice <= options.length) {
                selectedAnswer = choice;
                return calculateTrait(choice);
            } else {
                throw new InvalidInputException("Please enter a number between 1 and " + options.length);
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input. Please enter a number.");
        }
    }

    public String calculateTrait(int choice) {
        // Map each question and choice combination to personality traits
        // This ensures all 10 personalities are accessible
        String[] traits = {"mystic", "cosmic", "shadow", "radiant", "ethereal", 
                           "luminary", "serenity", "phoenix", "wild", "wisdom"};
        
        // Use question number and choice to distribute traits evenly
        // Formula: (questionNumber - 1) * 4 + (choice - 1) gives unique values 0-39
        // Then mod 10 to map to our 10 traits
        int traitIndex = ((questionNumber - 1) * 4 + (choice - 1)) % 10;
        
        return traits[traitIndex];
    }

    public String[] getOptions() { 
        return options; 
    }
}
