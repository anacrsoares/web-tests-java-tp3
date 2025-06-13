package org.example.ex12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleApiGetRandomISBN {
    public static void main(String[] args) {
        // b. Gerar ISBN aleatorio
        try{
            // create url
            URL endpoint = URI.create("https://apichallenges.eviltester.com/simpleapi/randomisbn").toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();

            // define http method
            conn.setRequestMethod("GET");

            // define headers
            conn.setRequestProperty("Accept", "application/json");

            // show status code
            int status = conn.getResponseCode();
            System.out.println("Status: " + status);

            // read response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            conn.disconnect();

            System.out.println("Random ISBN: " + response.toString());

            // Verifying
            String isbn = response.toString();
            URL url = URI.create("https://openlibrary.org/isbn/" + isbn + ".json").toURL();
            HttpURLConnection verifyConn = (HttpURLConnection) url.openConnection();
            verifyConn.setRequestMethod("GET");

            int verifyStatus = verifyConn.getResponseCode();
            if (verifyStatus == 200) {
                System.out.println("Este ISBN ja existe.");
            } else if (verifyStatus == 404) {
                System.out.println("Este ISBN e novo.");
            } else {
                System.out.println("Status inesperado: " + verifyStatus);
            }
            conn.disconnect();


        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }

    }
}
