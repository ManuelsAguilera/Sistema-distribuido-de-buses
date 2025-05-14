package server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager {
    public static void getRoute(String latStart, String longStart, String latDestiny, String longDestiny) {
        try {
        	String baseUrl = "http://router.project-osrm.org/route/v1/driving/";
        	String coordinates = longStart + "," + latStart + ";" + longDestiny + "," + latDestiny;
        	String options = "?overview=false";

        	URL url = new URL(baseUrl + coordinates + options);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
            	System.out.println("aaaa");
                throw new RuntimeException("Error en la API OSRM: #" + responseCode);
            } else {
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(con.getInputStream());

                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }

                scanner.close();
                con.disconnect();

                // Parseo JSON
                JSONObject jsonResponse = new JSONObject(infoString.toString());
                JSONArray routes = jsonResponse.getJSONArray("routes");
                JSONObject route = routes.getJSONObject(0);
                
                double distance = route.getDouble("distance"); // en metros
                double duration = route.getDouble("duration"); // en segundos
                System.out.println(jsonResponse);
            
                // Muestra en consola
                System.out.println("Distancia total: " + (distance / 1000) + " km");
                System.out.println("Duración estimada: " + (duration / 60) + " minutos");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
