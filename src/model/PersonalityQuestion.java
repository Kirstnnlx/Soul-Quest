package model;

import core.InvalidInputException;
import java.util.HashMap;
import java.util.Map;

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
        // Map choices to personality traits
        String[] traits = {"mystic", "cosmic", "shadow", "radiant", "ethereal", "luminary", "serenity", "phoenix", "wild", "wisdom"};
        return traits[choice % traits.length];
    }

    public String[] getOptions() { return options; }
}
