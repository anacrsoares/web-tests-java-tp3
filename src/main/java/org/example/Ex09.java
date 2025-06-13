package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Ex09 {
    public static void main(String[] args) {
        int id = 9;

        try {
            // create URL
            URL endpoint = URI.create("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

            // open connection
            HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();

            // define http method
            conn.setRequestMethod("DELETE");

            // get http status code and show it
            int deleteStatus = conn.getResponseCode();
            System.out.println("DELETE Status Code: " + deleteStatus);

            // close resources
            conn.disconnect();

            // GET TO CONFIRM
            // create get url
            URL getEndpoint = URI.create("https://apichallenges.eviltester.com/sim/entities/" + id).toURL();

            // open connection
            HttpURLConnection getConn = (HttpURLConnection) getEndpoint.openConnection();

            // define http method
            getConn.setRequestMethod("GET");

            // define get header
            getConn.setRequestProperty("Accept", "application/json");

            // get http status code and show it
            int getStatus = getConn.getResponseCode();
            System.out.println("GET Status Code: " + getStatus);

            if (getStatus == 404) {
                System.out.println("A entidade foi excluida com exito.");
            } else {
                System.out.println("A entidade ainda existe ou ocorreu outro problema.");
                BufferedReader in = new BufferedReader(new InputStreamReader(getConn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
            }

            getConn.disconnect();


        } catch (Exception e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
    }
}
