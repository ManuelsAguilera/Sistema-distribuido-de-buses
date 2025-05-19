package ClienteSimple;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import client.*;
import common.*;
public class ClienteSimple {

	
	public static void main() 
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
	                    ArrayList<Viaje> viajes = clientImpl()
	                    break;
	                case 2:
	                    System.out.println("-> Registrando pasajero...");
	                    // Lógica para registrar pasajero
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
