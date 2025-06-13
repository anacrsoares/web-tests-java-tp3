package org.example.ex12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class SimpleApiGetIsbn {
    public static void main(String[] args) {
        // c. Criar item com POST
        try {
            URL url = URI.create("https://apichallenges.eviltester.com/simpleapi/randomisbn").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String isbn = in.readLine(); // Exemplo: "9781861972712"
            in.close();

            System.out.println("ISBN gerado: " + isbn);

            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
