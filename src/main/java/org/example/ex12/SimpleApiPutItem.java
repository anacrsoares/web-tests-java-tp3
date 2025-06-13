package org.example.ex12;


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

public class SimpleApiPutItem {
    public static void main(String[] args) {
        try {
            String body = "{\"type\": \"book\", \"isbn13\": \"978-1-86-197271-1\", \"price\": 9.99, \"numberinstock\": 2}";

            URL url = URI.create("https://apichallenges.eviltester.com/simpleapi/items/17").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                out.write(body.getBytes());
            }

            System.out.println("Status: " + conn.getResponseCode());

            conn.disconnect();

            // Verifying
            // create URL, open conn, set http method
            URL verifyURL = URI.create("https://apichallenges.eviltester.com/simpleapi/items").toURL();
            HttpURLConnection verifyConn = (HttpURLConnection) verifyURL.openConnection();
            verifyConn.setRequestMethod("GET");

            // show status code
            int status = verifyConn.getResponseCode();
            System.out.println("Status: " + status);

            // read API response
            BufferedReader reader = new BufferedReader(new InputStreamReader(verifyConn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            verifyConn.disconnect();

            // Pretty print Gson
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonResponse = gson.fromJson(responseBuilder.toString(), JsonObject.class);
            System.out.println(gson.toJson(jsonResponse));

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
