import java.util.*;

public class PersonalityQuestion extends Question {
    private final String[] options;
    private int selectedAnswer;

    public PersonalityQuestion(String questionText, int questionNumber, String[] options) {
        super(questionText, questionNumber);
        this.options = options;
        this.selectedAnswer = -1;
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║ Question " + questionNumber + "/10");
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
            } else throw new InvalidInputException("Please enter a number between 1 and " + options.length);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input. Please enter a number.");
        }
    }

    private String calculateTrait(int choice) {
        String[] traits = {"mystic", "cosmic", "shadow", "radiant", "ethereal", "luminary"};
        return traits[choice % traits.length];
    }
}
