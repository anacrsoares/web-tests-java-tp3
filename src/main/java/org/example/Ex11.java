package org.example;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Ex11 {
    public static void main(String[] args) {
        try {
            // create URL
            URL url = URI.create("https://apichallenges.eviltester.com/sim/entities").toURL();

            // open URL connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // define HTTP method
            conn.setRequestMethod("OPTIONS");

            // show HTTP method
            int status = conn.getResponseCode();
            System.out.println("OPTIONS Status Code: " + status);

            //  read header allow
            String allowHeader = conn.getHeaderField("Allow");
            if (allowHeader != null) {
                System.out.println("Metodos permitidos: " + allowHeader);
            } else {
                System.out.println("Cabecalho 'Allow' nao encontrado.");
            }

            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
