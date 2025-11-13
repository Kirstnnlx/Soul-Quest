public abstract class Question {
    protected String questionText;
    protected int questionNumber;

    public Question(String questionText, int questionNumber) {
        this.questionText = questionText;
        this.questionNumber = questionNumber;
    }

    public abstract void displayQuestion();
    public abstract String processAnswer(String answer);

    public String getQuestionText() { return questionText; }
    public int getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(int number) { this.questionNumber = number; }
}
