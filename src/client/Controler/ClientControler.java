package client.Controler;

import client.MenuOptionListener;
import client.Model.ClientModel;
import client.View.ClientView;
import common.Bus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public class ClientControler implements MenuOptionListener {
    private ClientView view;
    private ClientModel model;

    public ClientControler(ClientView view, ClientModel model) throws Exception {
        this.view = view;
        this.view.setMenuOptionListener(this);
        
        this.model = model;
    }

    
    private void mostrarListaBuses() {
    	try {
    		//view.displayBusList();
    		ArrayList<Bus> buses = model.obtenerListabuses();
    	} catch (Exception e) {
    		view.showMessage("Error al obtener la lista " + e.getMessage());
    	}
    	
    	
    }

    private void crearPasaje(String origen, String destino, String nombrePasajero) {
    	try {
    		// int idPasajero, int idOrigen, int idDestino, LocalDateTime fechaCompra, float precio, int asiento, int idViaje
    		// view.
    		model.crearPasaje(origen, destino, nombrePasajero, nombrePasajero);
    		
    	} catch (Exception e) {
    		view.showMessage("Error al crear el pasaje" + e.getMessage());
    	}
    }

    private void cancelarPasaje(String idPasaje) {
    	try {
    		//view.
    		model.cancelarPasaje(idPasaje);
    	} catch (Exception e) {
    		view.showMessage("Error al cancelar el pasaje " + e.getMessage());
    	}
    }

	@Override
	public void onMenuOptionSelected(int option) {
		// Opciones del menu
        switch (option) {
            case 1:
            	//mostrarListaBuses();
                break;
            case 2:
            	//crearPasaje();
            	break;
            case 3:
            	//cancelarPasaje();
            	break;
            case 4:
                try {
                    view.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            
        }
		
	}

}
