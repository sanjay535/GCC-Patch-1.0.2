import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class Q2Test {

    private List<TestCase> testCases;
    private List<Answer> answers = new ArrayList<>();
    private TestRunner testRunner = new TestRunner();
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Before
    public void setUp() throws Exception {
        testCases = testRunner.getTests(2);
    }

    @After
    public void tearDown() throws Exception {
        testRunner.submitAnswers(answers, 2);
    }

    @Test
    public void testQuestion2() {
        for (int i=1; i<=testCases.size(); i++) {
            try {
                TestCase testCase = testCases.get(i-1);
                int finalI = i;
                Future<Answer> future = executorService.submit(() -> {
                    Q2Object input =
                            new ObjectMapper().readValue(testCase.getInput(),
                                    Q2Object.class);
                    long startTime = System.nanoTime();
                    Integer answer = skeleton.answers.Question2.riskAndReward(input.risk,
                            input.bonus, input.trader);
                    long endTime = System.nanoTime();
                    return new Answer(2, finalI, correct(answer,
                            testCase.getOutput()),
                            endTime-startTime);
                });

                Answer answer = future.get(1, TimeUnit.SECONDS);
                answers.add(answer);
                System.out.println(new ObjectMapper().writeValueAsString(answer));
            } catch (TimeoutException e) {
                System.out.println("A Question 2 test timed out. Tests must " +
                        "complete within one second.");
                answers.add(new Answer(2, i, "TIMED_OUT", -1));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String correct(int actual, int expected) {
        return actual == expected ? "CORRECT" : "INCORRECT";
    }

}
