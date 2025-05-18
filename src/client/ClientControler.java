package client;

import client.ClientModel;
import client.ClientView;

import java.io.IOException;

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
    	
    }

    private void crearPasaje(String origen, String destino, String nombrePasajero) {
    	
    }

    private void cancelarPasaje(String idPasaje) {
    
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
