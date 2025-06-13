package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Ex10 {
    public static void main(String[] args) {
        int id = 2;

        try {
            // create URL
            URL url = URI.create("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // define http method
            conn.setRequestMethod("DELETE");

            // get http status code and show it
            int status = conn.getResponseCode();
            System.out.println("DELETE Status Code: " + status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            status >= 400 ? conn.getErrorStream() : conn.getInputStream(),
                            StandardCharsets.UTF_8
                    )
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            JsonArray errorMessages = jsonObject.getAsJsonArray("errorMessages");

            System.out.print("Resposta do servidor: ");
            System.out.println(errorMessages.get(0).getAsString());

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
