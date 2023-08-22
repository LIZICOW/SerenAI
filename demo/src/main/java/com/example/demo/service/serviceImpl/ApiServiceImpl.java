package com.example.demo.service.serviceImpl;

import com.example.demo.service.ApiService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;

@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public String sendPostRequest(String apiUrl, String question, String conversationId) {
        StringBuilder response = new StringBuilder();

        // URL encode the parameters
        String encodedParam1 = URLEncoder.encode(question);
        String encodedParam2 = URLEncoder.encode(conversationId);

        // Construct the complete URL with encoded parameters
        if(Objects.equals(conversationId, "0"))
            apiUrl = apiUrl + "?question=" + encodedParam1;// + "&paramName2=" + encodedParam2;
        else
            apiUrl = apiUrl + "?question=" + encodedParam1 + "&conversationId=" + encodedParam2;
        try {
            URL url = new URL(apiUrl);
            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method and enable input/output streams
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;


            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Print response
            System.out.println("Response: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
