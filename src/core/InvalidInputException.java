public class InvalidInputException extends RuntimeException {
    private String inputValue;
    private int errorCode;

    public InvalidInputException(String message) {
        super(message);
        this.inputValue = "";
        this.errorCode = 400;
    }

    public InvalidInputException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return "✖ " + super.getMessage();
    }

    public String getInputValue() { return inputValue; }
}
