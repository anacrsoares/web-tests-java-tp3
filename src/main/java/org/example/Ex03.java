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

public class Ex03 {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int id = 13; // ID non-existent

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

            // show http response status
            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code for ID " + id + ": " + status);

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

                JsonObject json = gson.fromJson(responseBody.toString(), JsonObject.class);
                System.out.println("Entidade encontrada:");
                System.out.println(gson.toJson(json));

            } else if (status == 404) {
                System.out.println("Entidade com ID " + id + " nao encontrada (erro 404).");
            } else {
                // Outros erros
                System.out.println("Erro ao consultar a entidade. Codigo: " + status);
            }

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
