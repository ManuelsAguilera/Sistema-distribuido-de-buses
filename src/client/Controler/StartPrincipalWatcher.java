package client.Controler;

import common.IBusManager;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.Controler.ClientControler;

public class StartPrincipalWatcher implements Runnable {
    private final ClientControler controler;
    private volatile boolean activo = true;

    public StartPrincipalWatcher(ClientControler controler) {
        this.controler = controler;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            try {
            	Registry registry = LocateRegistry.getRegistry("localhost", 2002);
                IBusManager principal = (IBusManager) registry.lookup("CentralBusManager");
                principal.ping();  
                
                System.out.println("[Watcher] Servidor principal disponible, reconectando...");
                
                controler.cambiarAServerPrincipal(principal);
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
