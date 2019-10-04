public class Answer {

    private int questionNumber;
    private int testNumber;
    private String correct;
    private double speed;

    public Answer(int questionNumber, int testNumber, String correct,
                  double speed) {
        this.questionNumber = questionNumber;
        this.testNumber = testNumber;
        this.correct = correct;
        this.speed = speed;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public String getCorrect() {
        return correct;
    }

    public double getSpeed() {
        return speed;
    }
}
