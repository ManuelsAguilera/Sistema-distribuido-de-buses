package client.Controler;

import common.IBusManager;
import java.rmi.RemoteException;

import client.Controler.ClientControler;

public class StartHeartbeat implements Runnable {
    private final ClientControler controler;
    private final IBusManager serverActual;
    private volatile boolean activo = true;

    public StartHeartbeat(ClientControler controler, IBusManager serverActual) {
        this.controler = controler;
        this.serverActual = serverActual;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                serverActual.ping();  
                Thread.sleep(1000);   
            } catch (RemoteException e) {
                System.out.println("[Heartbeat] Servidor actual caído. Cambiando al respaldo...");
                controler.cambiarAServerRespaldo();
                break;
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
