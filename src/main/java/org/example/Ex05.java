package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Ex05 {
    public static void main(String[] args) {
        try {
            // create URL
            String endpoint = "https://apichallenges.eviltester.com/sim/entities";
            URL url = URI.create(endpoint).toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // define http method
            conn.setRequestMethod("POST");

            // define headers
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // must for POST data sent allowance

            // JSON
            String jsonInputString = """
                   {
                       "name": "aluno"
                    }""";

            // send request body
            try (OutputStream wr = conn.getOutputStream()) {
                byte [] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                wr.write(input);
            }

            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            // read API response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            conn.disconnect();

            // Pretty print Gson
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonResponse = gson.fromJson(responseBuilder.toString(), JsonObject.class);
            System.out.println(gson.toJson(jsonResponse));


        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
