package server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.Duration;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager {

    private static JSONObject getRouteData(String coordinates) throws Exception {
        String baseUrl = "http://router.project-osrm.org/route/v1/driving/";
        String options = "?overview=false";

        URL url = new URL(baseUrl + coordinates + options);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Error en la API OSRM: #" + responseCode);
        }

        StringBuilder infoString = new StringBuilder();
        try (Scanner scanner = new Scanner(con.getInputStream())) {
            while (scanner.hasNext()) {
                infoString.append(scanner.nextLine());
            }
        }

        con.disconnect();
        return new JSONObject(infoString.toString());
    }

    public static void getRoute(String latStart, String longStart, String latDestiny, String longDestiny) {
        try {
            String coordinates = longStart + "," + latStart + ";" + longDestiny + "," + latDestiny;
            JSONObject jsonResponse = getRouteData(coordinates);

            JSONObject route = jsonResponse.getJSONArray("routes").getJSONObject(0);
            double distance = route.getDouble("distance"); // metros
            double duration = route.getDouble("duration"); // segundos

            System.out.printf("Distancia total: %.2f km\n", distance / 1000);
            System.out.printf("Duración estimada: %.2f minutos\n", duration / 60);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LocalTime getTime(String latStart, String longStart, String latDestiny, String longDestiny) {
        try {
            String coordinates = longStart + "," + latStart + ";" + longDestiny + "," + latDestiny;
            JSONObject jsonResponse = getRouteData(coordinates);

            double durationSeconds = jsonResponse.getJSONArray("routes").getJSONObject(0).getDouble("duration");
            Duration duration = Duration.ofSeconds((long) durationSeconds);

            // Convertir Duration a LocalTime 
            LocalTime time = LocalTime.MIDNIGHT.plus(duration);
            System.out.println("Duración estimada: " + time);

            return time;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Manejo de posible error 
    }
}
