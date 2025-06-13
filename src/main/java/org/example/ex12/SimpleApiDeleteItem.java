package org.example.ex12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleApiDeleteItem {
    public static void main(String[] args) {
        // g. Remover item com DELETE
        try {
            URL url = URI.create("https://apichallenges.eviltester.com/simpleapi/items/11").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
            System.out.println("Delete Status: " + conn.getResponseCode());

            conn.disconnect();

            URL url2 = URI.create("https://apichallenges.eviltester.com/simpleapi/items/15").toURL();
            HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("DELETE");
            conn2.setRequestProperty("Accept", "application/json");
            System.out.println("Delete Status: " + conn2.getResponseCode());

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
