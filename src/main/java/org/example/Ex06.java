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

public class Ex06 {
    public static void main(String[] args) {
        int id = 11; // new ID created previously
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // create url
            String endpoint = "https://apichallenges.eviltester.com/sim/entities/" + id;
            URL url = URI.create(endpoint).toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // define http method
            conn.setRequestMethod("GET");

            // define headers
            conn.setRequestProperty("Accept", "application/json");

            // show http status code response
            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            // response
            if (status == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse e pretty print
                JsonObject json = gson.fromJson(response.toString(), JsonObject.class);
                String prettyJson = gson.toJson(json);

                System.out.println("Entidade retornada:");
                System.out.println(prettyJson);
            } else {
                System.out.println("Falha ao recuperar a entidade com ID " + id);
            }

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
