package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Ex08 {
    public static void main(String[] args) {
        int id = 10;
        String jsonBody = "{\"name\": \"atualizado\"}";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // put request
            URL putUrl = URI.create("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();
            HttpURLConnection putConn = (HttpURLConnection) putUrl.openConnection();
            putConn.setRequestMethod("PUT");
            putConn.setRequestProperty("Content-Type", "application/json");
            putConn.setDoOutput(true);

            try (DataOutputStream out = new DataOutputStream(putConn.getOutputStream())) {
                out.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                out.flush();
            }

            int putStatus = putConn.getResponseCode();
            System.out.println("PUT Status Code: " + putStatus);

            // show put read
            BufferedReader putReader = new BufferedReader(new InputStreamReader(putConn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder putResponse = new StringBuilder();
            String line;
            while ((line = putReader.readLine()) != null) {
                putResponse.append(line);
            }
            putReader.close();
            putConn.disconnect();

            JsonObject putJson = gson.fromJson(putResponse.toString(), JsonObject.class);
            System.out.println(gson.toJson(putJson));

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
