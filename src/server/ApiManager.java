package server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager {
	public static void getRoute(String latStart, String longStart, String latDestiny, String longDestiny) {
		// Solicitamos la petición
		// Get: "/{service}/{version}/{profile}/{coordinates}[.{format}]?option=value&option=value"
		try {
			// Solicitamos la petición
			URL url = new URL("https://router.project-osrm.org/route/v1/driving/-70.7519483,-34.1698248;-70.6903149,-33.4543722?overview=full&geometries=geojson");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			// Comprobamos el estado de la peticion
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				throw new RuntimeException("Ocurrio un error durante la lectura de la API: #" + responseCode);
			} else {
				// Lectura de la información
				StringBuilder informationString = new StringBuilder();
				Scanner scanner = new Scanner(url.openStream());
				
				while (scanner.hasNext())  {
					informationString.append(scanner.nextLine());
				}
				
				scanner.close();
				
				// Imprimimos la solicitud
				System.out.println(informationString);
			}
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
	} 
}
