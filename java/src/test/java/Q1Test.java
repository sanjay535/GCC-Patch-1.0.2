import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import skeleton.answers.Question1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class Q1Test {

    private List<TestCase> testCases;
    private List<Answer> answers = new ArrayList<>();
    private TestRunner testRunner = new TestRunner();
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Before
    public void setUp() throws Exception {
        System.out.println("Starting tests");
        testCases = testRunner.getTests(1);
    }

    @After
    public void tearDown() throws Exception {
        testRunner.submitAnswers(answers, 1);
    }

    @Test
    public void testQuestion1() {
        for (int i=1; i<=testCases.size(); i++) {
            try {
                TestCase testCase = testCases.get(i-1);
                int finalI = i;
                Future<Answer> future = executorService.submit(() -> {
                    Q1Object input =
                            new ObjectMapper().readValue(testCase.getInput(),
                                    Q1Object.class);
                    long startTime = System.nanoTime();
                    double answer =
                            Question1.calculateTotalPayment(input.initialLevelOfDebt, input.interestPercentage, input.repaymentPercentage);
                    long endTime = System.nanoTime();
                    return new Answer(1, finalI, correct(answer, testCase.getOutput()),
                            endTime-startTime);
                });


                Answer answer = future.get(1, TimeUnit.SECONDS);
                answers.add(answer);
                System.out.println(new ObjectMapper().writeValueAsString(answer));
            } catch (TimeoutException e) {
                System.out.println("A Question 1 test timed out. Tests must complete within one second.");
                answers.add(new Answer(1, i, "TIMED_OUT", -1));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String correct(double actual, double expected) {
        return actual == expected ? "CORRECT" : "INCORRECT";
    }


}
