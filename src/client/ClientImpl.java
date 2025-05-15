package client;

import common.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;



public class ClientImpl {
	
	IBusManager server;
	
	ClientImpl() throws RemoteException, NotBoundException
	{
		//Registry registry = LocateRegistry.getRegistry(2002);
		//server=(IBusManager) registry.lookup("CentralBusManager");
	}
	
	void testConnection(String message) throws RemoteException { this.server.testConnection(message);}
	void getNamesTerminal() throws RemoteException
	{
		ArrayList<String> nameList = this.server.dumpTerminal();
		
		for (String name : nameList)
		{
			System.out.println(name);
		}
	}
}
