package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Ex02 {
    public static void main(String[] args) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

        for (int id = 1; id <= 8; id++) {
            try {
                // create URL
                String endpoint = "https://apichallenges.eviltester.com/sim/entities/" + id;
                URL url = URI.create(endpoint).toURL();

                // open connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // define http method
                conn.setRequestMethod("GET");

                // define headers
                conn.setRequestProperty("Accept", "application/json");

                // get http status code and show
                int status = conn.getResponseCode();
                System.out.println("HTTP Status Code: " + status);

                // response
                if (status == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                    StringBuilder responseBody = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responseBody.append(inputLine);
                    }
                    in.close();

                    // Parse com Gson
                    JsonObject entityJson = prettyGson.fromJson(responseBody.toString(), JsonObject.class);
                    String prettyJson = prettyGson.toJson(entityJson);

                    System.out.println("Entidade ID " + id + ":");
                    System.out.println(prettyJson);
                    System.out.println("--------------------------------");

                } else {
                    System.out.println("Failed to retrieve entity with ID " + id);
                }

                // close resources
                conn.disconnect();

            } catch (Exception e) {
                System.err.println("Erro ao acessar a API: " + e.getMessage());
            }
        }
    }

}

