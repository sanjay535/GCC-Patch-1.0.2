import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TestRunner {

    private final String uuid = System.getenv("travistestidentifier");

    protected List<TestCase> getTests(int questionNumber) {
        System.out.println("Got uuid - it is: " + uuid);
        String url =
                "https://cscc-gl.herokuapp.com/tests/run/" + questionNumber;
        if (uuid != null) {
            url += "/" + uuid;
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> responseEntity =
                restTemplate.getForEntity(url,
                List.class);
        List<TestCase> testCases = new ArrayList<>();
        List<LinkedHashMap<String, Object>> map = responseEntity.getBody();
        for (LinkedHashMap<String, Object> item : map) {
            TestCase testCase = new TestCase();
            testCase.setTestNumber((Integer) item.get("testNumber"));
            testCase.setInput((String) item.get("input"));
            testCase.setOutput((Integer) item.get("output"));
            testCases.add(testCase);
        }
        return testCases;
    }

    protected void submitAnswers(List<Answer> answers, int questionNumber) {
        if (uuid != null) {
            String url =
                    "https://cscc-gl.herokuapp.com/answer/contestant/" + uuid +
                            "/" + questionNumber;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(url, answers, Void.class);
        }
    }

}
