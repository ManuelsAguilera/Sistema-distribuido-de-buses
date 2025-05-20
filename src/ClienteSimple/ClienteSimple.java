package ClienteSimple;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import client.*;
import common.*;
public class ClienteSimple {

	
	private static void obtenerViajeOpcion(ClientImpl client)
	{
		try {
			Scanner scanner = new Scanner(System.in);

			System.out.print("Ingrese origen: ");
            String origen = scanner.nextLine();
            System.out.print("Ingrese destino: ");
            String destino = scanner.nextLine();
			
            System.out.print("Ingrese fecha (yyyy-MM-dd): ");
            LocalDate fecha = null;
            while (fecha == null) {
                try {
                    String inputFecha = scanner.nextLine();
                    fecha = LocalDate.parse(inputFecha); // Usa el formato ISO por defecto: yyyy-MM-dd
                } catch (DateTimeParseException e) {
                    System.out.print("Formato inválido. Ingrese fecha nuevamente (yyyy-MM-dd): ");
                }
            }

            ArrayList<Viaje> viajes = client.obtenerViaje(origen, destino, fecha);

	        if (viajes.isEmpty()) {
	            System.out.println("No se encontraron viajes para el origen y destino especificados.");
	        } else if (viajes == null)
	        {
	        	System.out.println("No se encontraon viajes por algun error");
	        	return;
	        } else {
	        
	        	for (Viaje v : viajes) {
	        	    System.out.println("----- Viaje -----");
	        	    System.out.println("ID de viaje       : " + v.getidViaje());
	        	    System.out.println("ID de ruta        : " + v.getIdRuta());
	        	    System.out.println("Matrícula del bus : " + v.getMatricula());
	        	    System.out.println("Fecha             : " + v.getFecha());
	        	    System.out.println("Hora de salida    : " + v.getSalida());
	        	    System.out.println("Salida estimada   : " + v.getSalidaEstimada());
	        	    System.out.println("------------------\n");
	        	}
	        }
	            
	     } 
		catch (Exception e) {
	        System.out.println("Error al consultar los viajes: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	private static void registrarPasajeroOpcion(ClientImpl client)
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese correo: ");
        String correo = scanner.nextLine();
        
        int id = client.registrarPasajero(nombre, correo);
        		
        if (id ==-1)
        	System.out.println("No se pudo registrar hubo un error");
        else 
        	System.out.println("Pasajero registrado, entreguele la id: "+id);
        System.out.println();
        
        
        
	}
	
	
	public static void main(String Args[]) 
	{
		try {
			ClientImpl client = new ClientImpl();
			
			Scanner scanner = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("======================================");
	            System.out.println(" Sistema distribuido de buses simple");
	            System.out.println("======================================");
	            System.out.println("Ingrese una opción numérica:");
	            System.out.println("1: Buscar buses (origen y destino)");
	            System.out.println("2: Registrar pasajero");
	            System.out.println("3: Seleccionar un viaje");
	            System.out.println("4: Asignar pasaje a pasajero y viaje");
	            System.out.println("0: Salir");
	            System.out.print("Opción: ");

	            while (!scanner.hasNextInt()) {
	                System.out.print("Por favor ingrese un número válido: ");
	                scanner.next();
	            }

	            opcion = scanner.nextInt();

	            switch (opcion) {
	                case 1:
	                    System.out.println("-> Buscando buses por origen y destino...");
	                    
	                    obtenerViajeOpcion(client);
	                    break;
	                case 2:
	                    System.out.println("-> Registrando pasajero...");
	                    registrarPasajeroOpcion(client);
	                    break;
	                case 3:
	                    System.out.println("-> Seleccionando un viaje...");
	                    // Lógica para seleccionar viaje
	                    break;
	                case 4:
	                    System.out.println("-> Asignando pasaje a pasajero y viaje...");
	                    // Lógica para asignar pasaje
	                    break;
	                case 0:
	                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
	                    break;
	                default:
	                    System.out.println("Opción no válida. Intente de nuevo.");
	            }

	            System.out.println();

	        } while (opcion != 0);

	        scanner.close();
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
}
