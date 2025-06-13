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

public class Ex01 {
    public static void main(String[] args) {
        try {
            // create URL
            String endpoint = "https://apichallenges.eviltester.com/sim/entities";
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
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody.toString(), JsonObject.class);
            JsonArray entitiesArray = jsonObject.getAsJsonArray("entities");

            // pretty print
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(entitiesArray);

            System.out.println("Entidades:");
            System.out.println(prettyJson);

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
