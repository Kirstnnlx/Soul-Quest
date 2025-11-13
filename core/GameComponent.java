public interface GameComponent {
    void initialize();
    void execute();
    void cleanup();
    boolean isComplete();
}
