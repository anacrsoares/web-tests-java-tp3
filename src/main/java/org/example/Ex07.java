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

public class Ex07 {
    public static void main(String[] args) {
        int id = 10;
        String updatedName = "atualizado";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // create url
            String postEndpoint = "https://apichallenges.eviltester.com/sim/entities/" + id;
            URL postUrl = URI.create(postEndpoint).toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();

            // define http method
            conn.setRequestMethod("POST");

            // define headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // must for POST data sent allowance

            // JSON
            String jsonInputString = String.format("{\"name\": \"%s\"}", updatedName);

            // send request body
            try (OutputStream wr = conn.getOutputStream()) {
                byte [] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                wr.write(input);
            }

            // response http status code
            int postStatus = conn.getResponseCode();
            System.out.println("POST Status Code: " + postStatus);

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
            JsonObject jsonResponse = gson.fromJson(responseBuilder.toString(), JsonObject.class);
            System.out.println(gson.toJson(jsonResponse));

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }

        /// verificacao com GET
        try {
            // create URL
            String endpoint = "https://apichallenges.eviltester.com/sim/entities/10";
            URL url = URI.create(endpoint).toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // define http method
            conn.setRequestMethod("GET");

            // define headers
            conn.setRequestProperty("Accept", "application/json");

            // get http status code and show it
            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            // response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder responseBody = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine).append("\n");
            }

            // close resources
            in.close();
            conn.disconnect();

            // Parse com Gson
            JsonObject jsonObject = gson.fromJson(responseBody.toString(), JsonObject.class);

            // pretty print
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(jsonObject);

            System.out.println("Entidades:");
            System.out.println(prettyJson);

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
