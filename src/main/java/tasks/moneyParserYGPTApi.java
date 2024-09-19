package tasks;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static tasks.moneyParserYGPTApi.run;

// Небольшой бонус =)

public class moneyParserYGPTApi {

    private static final String URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion";
    private static final String IAM_TOKEN = "token";
    private static final String FOLDER_ID = "folder_id";

    public static String run(String userText) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        Map<String, Object> data = new HashMap<>();
        data.put("modelUri", "gpt://" + FOLDER_ID + "/yandexgpt");

        Map<String, Object> completionOptions = new HashMap<>();
        completionOptions.put("temperature", 0.3);
        completionOptions.put("maxTokens", 1000);
        data.put("completionOptions", completionOptions);

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("text", "Переведи цифры в прописное значение. Цифры могут быть дробными, они являюся суммой в рулях и комейках. " +
                "Отвечай только результатом без пояснений. Если ввели не цифры, ответь - Введите правильный формат цифр");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("text", userText);

        data.put("messages", new Map[]{systemMessage, userMessage});

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + IAM_TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().replaceAll(".*\"text\":\"", "")
                .replaceAll("\".*", "");
    }
}

class moneyParserYGPTApiTest {
    public static void main(String[] args) throws Exception {
        System.out.println(run("1878.98"));
    }
}

