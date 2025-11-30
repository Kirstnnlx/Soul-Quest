package core;

import java.util.*;

public class GameEngine implements GameComponent {
    private List<Question> questions;
    private int currentQuestionIndex;
    private PlayerHistory playerHistory;
    private Scanner scanner;
    private Map<String, Integer> answerTracker;
    private int iceBreakersShown;

    public GameEngine(PlayerHistory playerHistory) {
        this.playerHistory = playerHistory;
        this.questions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.answerTracker = new HashMap<>();
        this.currentQuestionIndex = 0;
        this.iceBreakersShown = 0;
    }

    @Override
    public void initialize() {
        loadQuestions();
        answerTracker.clear();
        currentQuestionIndex = 0;
        iceBreakersShown = 0;
    }

    @Override
    public void execute() {
        startGame();
    }

    @Override
    public void cleanup() {
        System.out.println("\n⋆⭒˚.⋆ Thank you for playing Soul Quest! ⋆⭒˚.⋆");
    }

    @Override
    public boolean isComplete() {
        return currentQuestionIndex >= questions.size();
    }

    private IceBreakerType getRandomIceBreakerType() {
        IceBreakerType[] types = IceBreakerType.values();
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }

    private void addRandomIceBreaker(int questionNumber) {
        IceBreakerType type = getRandomIceBreakerType();
        String content = "";
        Random random = new Random();

        switch (type) {
            case FUN_FACT:
                String[] facts = {
                    "Octopuses have three hearts!",
                    "Bananas are berries, but strawberries aren't.",
                    "A group of flamingos is called a flamboyance.",
                    "Honey never spoils — even after thousands of years!",
                    "The shortest war in history lasted only 38 minutes.",
                    "Venus is the only planet that spins clockwise."
                };
                content = facts[random.nextInt(facts.length)];
                questions.add(new IceBreaker(content, type, questionNumber));
                break;

            case QUOTE:
                String[] quotes = {
                    "The only way to do great work is to love what you do. - Steve Jobs",
                    "We are what we repeatedly do. Excellence, then, is not an act, but a habit. - Aristotle",
                    "You become what you believe. - Oprah Winfrey",
                    "Not all those who wander are lost. - J.R.R. Tolkien",
                    "Be yourself; everyone else is already taken. - Oscar Wilde",
                    "The future belongs to those who believe in the beauty of their dreams. - Eleanor Roosevelt"
                };
                content = quotes[random.nextInt(quotes.length)];
                questions.add(new IceBreaker(content, type, questionNumber));
                break;

            case SCRAMBLED_WORD:
                String[][] scrambledData = {
                    {"LUOS", "SOUL"},
                    {"TEUQS", "QUEST"},
                    {"THGIL", "LIGHT"},
                    {"WODHSA", "SHADOW"},
                    {"REMARED", "DREAMER"},
                    {"IRRAWRO", "WARRIOR"}
                };
                String[] selectedWord = scrambledData[random.nextInt(scrambledData.length)];
                content = selectedWord[0];
                String correctWord = selectedWord[1];
                questions.add(new IceBreaker(content, type, questionNumber, correctWord));
                break;

            case RIDDLE:
                String[][] riddleData = {
                    {"What has to be broken before you can use it?", "A promise", "An egg", "A record", "A heart", "1", "An egg"},
                    {"I speak without a mouth and hear without ears. What am I?", "A radio", "An echo", "A phone", "The wind", "1", "An echo"},
                    {"What runs but never walks?", "A river", "A clock", "Time", "A nose", "0", "A river"},
                    {"What has keys but no locks?", "A piano", "A map", "A treasure chest", "A door", "0", "A piano"},
                    {"What can travel around the world while staying in a corner?", "A memory", "A stamp", "A dream", "A flag", "1", "A stamp"},
                    {"The more you take, the more you leave behind. What am I?", "Memories", "Footsteps", "Time", "Money", "1", "Footsteps"}
                };
                
                String[] selectedRiddle = riddleData[random.nextInt(riddleData.length)];
                content = selectedRiddle[0];
                String[] choices = {selectedRiddle[1], selectedRiddle[2], selectedRiddle[3], selectedRiddle[4]};
                int correctIndex = Integer.parseInt(selectedRiddle[5]);
                String correctAnswer = selectedRiddle[6];
                
                questions.add(new IceBreaker(content, type, questionNumber, choices, correctIndex, correctAnswer));
                break;
        }
    }

    public void loadQuestions() {
        questions.clear();

        questions.add(new PersonalityQuestion(
            "When faced with a challenge, you tend to:",
            1,
            new String[]{"Analyze it carefully", "Trust your intuition", "Seek advice from others", "Jump in headfirst"}
        ));

        questions.add(new PersonalityQuestion(
            "Your ideal weekend involves:",
            2,
            new String[]{"Reading a book in solitude", "Exploring new places", "Hanging out with friends", "Working on a creative project"}
        ));

        questions.add(new PersonalityQuestion(
            "When someone asks for help, you:",
            3,
            new String[]{"Drop everything to assist", "Help if you have time", "Direct them to resources", "Solve the problem for them"}
        ));

        addRandomIceBreaker(0);

        questions.add(new PersonalityQuestion(
            "Your approach to making decisions:",
            4,
            new String[]{"Logic and facts", "Gut feeling", "Pros and cons list", "Ask for opinions"}
        ));

        questions.add(new PersonalityQuestion(
            "In a group setting, you are usually:",
            5,
            new String[]{"The leader", "The mediator", "The observer", "The entertainer"}
        ));

        questions.add(new PersonalityQuestion(
            "Your dream superpower would be:",
            6,
            new String[]{"Mind reading", "Time travel", "Invisibility", "Super strength"}
        ));

        addRandomIceBreaker(0);

        questions.add(new PersonalityQuestion(
            "When stressed, you prefer to:",
            7,
            new String[]{"Be alone", "Talk it out", "Exercise", "Sleep it off"}
        ));

        questions.add(new PersonalityQuestion(
            "Your philosophy on life is:",
            8,
            new String[]{"Live in the moment", "Plan for the future", "Learn from the past", "Balance everything"}
        ));

        questions.add(new PersonalityQuestion(
            "What motivates you the most?",
            9,
            new String[]{"Success and achievement", "Helping others", "Personal growth", "Freedom and adventure"}
        ));

        questions.add(new PersonalityQuestion(
            "How do you handle failure?",
            10,
            new String[]{"Learn from it and move on", "Analyze what went wrong", "Seek support from others", "Use it as fuel to try harder"}
        ));

        addRandomIceBreaker(0);
    }

    public void startGame() {
        System.out.println("\n✦•···························•✦•···························•✦");
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                      SOUL QUEST BEGINS                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\nAnswer 10 questions to discover your unique personality type!");
        System.out.println("There will be fun ice breakers along the way. Enjoy! (｡•̀ᴗ-)✧\n");

        for (Question q : questions) {
            q.displayQuestion();
            
            if (q instanceof IceBreaker) {
                IceBreaker iceBreaker = (IceBreaker) q;
                if (iceBreaker.getType() == IceBreakerType.SCRAMBLED_WORD || 
                    iceBreaker.getType() == IceBreakerType.RIDDLE) {
                    String answer = scanner.nextLine();
                    q.processAnswer(answer);
                } else {
                    scanner.nextLine();
                }
                continue;
            }

            boolean validAnswer = false;
            while (!validAnswer) {
                try {
                    System.out.print("Your answer: ");
                    String answer = scanner.nextLine();
                    String trait = q.processAnswer(answer);
                    
                    answerTracker.put(trait, answerTracker.getOrDefault(trait, 0) + 1);
                    validAnswer = true;
                    
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("\n✦•···························•✦•···························•✦");
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              ANALYZING YOUR SOUL SIGNATURE...              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("\n₊˚⊹♡ Processing your responses...");
        System.out.println("⋆˚꩜｡ Decoding your essence...");
        System.out.println("ᯓ★ Aligning cosmic frequencies...");
        
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        
        System.out.println("\n");
        System.out.println("    *  .  *    .   *  .    *    .  *   .   *");
        System.out.println("  .    *   .  *    .   *    .   *   .    .");
        System.out.println("*    .   *    .  *   .   *   .  *    .  *  ");
        System.out.println("  .  *   .  *   .  *   .  *   .  *  .   *  ");
        System.out.println("                                            ");
        System.out.println("       Your personality type has been       ");
        System.out.println("              determined...                 ");
        System.out.println("                                            ");
        System.out.println("  *   .  *   .  *  .   *   .  *   .  *   . ");
        System.out.println("    .   *   .   *    .  *    .   *    .  * ");
        System.out.println("  *   .   *   .  *   .   *  .   *   .   *  ");
        System.out.println();
        System.out.println("        Are you ready to discover          ");
        System.out.println("           who you truly are?              ");
        System.out.println();
        System.out.println("   ~ Press ENTER to reveal my destiny ~ ");
        
        scanner.nextLine();
        
        System.out.println("\n    Unveiling your soul essence...\n");
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}

        Personality result = processAnswers();
        result.displayResult();
        playerHistory.addPersonality(result);
        playerHistory.saveToFile();
        System.out.println("\nPress ENTER to return to main menu...");
        scanner.nextLine();
    }

    public Personality processAnswers() {
        PersonalityType resultType = calculatePersonality();
        return new Personality(resultType);
    }

    private PersonalityType calculatePersonality() {
        String dominantTrait = answerTracker.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("mystic");

        switch (dominantTrait) {
            case "cosmic": return PersonalityType.COSMIC_DREAMER;
            case "shadow": return PersonalityType.SHADOW_SAGE;
            case "radiant": return PersonalityType.RADIANT_WARRIOR;
            case "ethereal": return PersonalityType.ETHEREAL_GUIDE;
            case "luminary": return PersonalityType.LUMINARY_SOUL;
            case "serenity": return PersonalityType.SERENITY_KEEPER;
            case "phoenix": return PersonalityType.PHOENIX_SPIRIT;
            case "wild": return PersonalityType.WILD_HEART;
            case "wisdom": return PersonalityType.WISDOM_WEAVER;
            default: return PersonalityType.MYSTIC_WANDERER;
        }
    }

    public void displayMenu() {
        System.out.println("\n✦•···························•✦•···························•✦");
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                         MAIN MENU                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.println("          Choose your path, wanderer of the soul...");
        System.out.println("  ");
        System.out.println("  [1] Start New Game");
        System.out.println("  [2] View Personality Collection");
        System.out.println("  [3] Delete History");
        System.out.println("  [4] Exit");
        System.out.print("\nChoose an option: ");
    }

    public void handleUserInput() {
        String input = scanner.nextLine();
        
        switch (input.trim()) {
            case "1":
                initialize();
                execute();
                break;
            case "2":
                playerHistory.displayHistory();
                break;
            case "3":
                boolean validResponse = false;
                String confirm = "";
                
                while (!validResponse) {
                    System.out.print("Are you sure? This cannot be undone (yes/no): ");
                    confirm = scanner.nextLine().trim().toLowerCase();
                    
                    if (confirm.equals("yes") || confirm.equals("no")) {
                        validResponse = true;
                    } else {
                        System.out.println("✖ Invalid input! Please type 'yes' or 'no' only.");
                    }
                }
                
                if (confirm.equals("yes")) {
                    playerHistory.deleteHistory();
                    FileHandler.deleteHistory(playerHistory.getUserId());
                } else {
                    System.out.println("✓ Deletion cancelled.");
                }
                break;
            case "4":
                SoulQuest.displayExit();  
                System.exit(0);            
                break;
        }
    }
}
