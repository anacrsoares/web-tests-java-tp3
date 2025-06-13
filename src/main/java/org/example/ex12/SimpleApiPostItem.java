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

public class SimpleApiPostItem {
    public static void main(String[] args) {
        // c. Criar item com POST
        try {
            String isbn = "978-1-86197271-1"; // insira o ISBN gerado

            String body = String.format("{\"type\": \"book\", \"isbn13\": \"%s\", \"price\": 5.99, \"numberinstock\": 5}", isbn);

            URL url = URI.create("https://apichallenges.eviltester.com/simpleapi/items").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                out.write(body.getBytes());
            }

            System.out.println("Post Status: " + conn.getResponseCode());

            // Verifying
            // create URL, open conn, set http method
            HttpURLConnection verifyConn = (HttpURLConnection) url.openConnection();
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

            verifyConn.disconnect();
        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
