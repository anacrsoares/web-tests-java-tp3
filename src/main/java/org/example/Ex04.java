package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Ex04 {
    public static void main(String[] args) {
        try {
            // create URL
            String baseUrl = "https://apichallenges.eviltester.com/sim/entities";
            String categoria = "teste"; // fictitious parameter
            int limite = 5; // fictitious parameter
            String finalUrl = baseUrl + "?categoria=" + categoria + "&limite=" + limite;
            URL url = URI.create(finalUrl).toURL();

            // show URL
            System.out.println("URL montada: " + finalUrl);

            // open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // define http method
            conn.setRequestMethod("GET");

            // define headers
            conn.setRequestProperty("Accept", "application/json");

            // show status code response
            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            // response
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder responseBody = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);
                }

                in.close();
                System.out.println("Resposta do servidor:");

                // Parse Gson
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseBody.toString(), JsonObject.class);
                JsonArray entitiesArray = jsonObject.getAsJsonArray("entities");

                // pretty print
                Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                String prettyJson = prettyGson.toJson(entitiesArray);

                System.out.println("Entidades:");
                System.out.println(prettyJson);

            } else {
                System.out.println("Erro ao acessar a URL.");
            }

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
