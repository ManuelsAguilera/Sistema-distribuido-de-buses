package client.View;
// Cliente registra pasajero

// Cliente ingresa origen y destino del pasajero
// Sistema devuelve los buses para cada viaje que tenga origen y destino del pasajero
// Mostrar solamente los buses disponibles (capacidad aun no supera el limite)
// Cliente selecciona un viaje
// Cliente asigna pasaje a pasajero
// Fin

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Button.Listener;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import client.MenuOptionListener;
import common.Bus;
import common.IBusManager;
import common.Viaje;
import server.BusManagerImpl;

public class ClientView {

	private Screen screen;
	private WindowBasedTextGUI textGUI;
	private BasicWindow window;
	private MenuOptionListener menuOptionListener;
	private TextColor foreC, backC;
	private String clientName = "Vicente Rosales";

	private IBusManager server;

	public void setMenuOptionListener(MenuOptionListener listener) {
		this.menuOptionListener = listener;
	}

	public ClientView() throws IOException, NotBoundException {
		foreC = ANSI.YELLOW;
		backC = ANSI.BLACK;
		screen = new DefaultTerminalFactory().createScreen();
		screen.startScreen();
		textGUI = new MultiWindowTextGUI(screen);
		window = new BasicWindow("BusApp TERMINAL v1.0");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));
	}

	public void initUI() throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		foreC = TextColor.ANSI.YELLOW;
		backC = TextColor.ANSI.BLACK;

	}

	@SuppressWarnings("deprecation")
	public void displayMenu() throws IOException {
		TerminalSize terminalSize = screen.getTerminalSize();

		// Layout de 3 columnas para los paneles
		Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

		// Header
		Label header = new Label("----------- Cliente: " + clientName + " -------------")
				.setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.YELLOW);
		mainPanel.addComponent(header);

		// Crear paneles para cada fila
		Panel contentPanel = new Panel(new GridLayout(3));

		// Etiquetas para cada columna
		contentPanel.addComponent(new Label("#").setForegroundColor(TextColor.ANSI.YELLOW));
		contentPanel.addComponent(new Label("Métodos").setForegroundColor(TextColor.ANSI.YELLOW));
		contentPanel.addComponent(new Label("Vista").setForegroundColor(TextColor.ANSI.YELLOW));

		// Botones que representan funcionalidades, puedes agregar más
		String[] opciones = { "Gestionar buses", "Gestionar pasajes", "Gestionar terminales", "Gestionar viajes",
				"Gestionar rutas", "Gestionar pasajeros", "Obtener informe de ventas", "Salir" };

		int index = 1;
		for (String opcion : opciones) {
			final int currentIndex = index;
			contentPanel.addComponent(new Label(String.valueOf(currentIndex)));

			Button button = new Button(opcion, () -> {
				onMenuOptionSelected(currentIndex); // Llama al método con el índice correcto
			});

			contentPanel.addComponent(button);
			contentPanel.addComponent(new Label("..."));
			index++;
		}

		// Añadir paneles al panel principal
		mainPanel.addComponent(contentPanel);

		// Footer
		Label footer = new Label("--------------------------------------------------")
				.setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.YELLOW);
		mainPanel.addComponent(footer);

		// Añadir panel principal a la ventana
		window.setComponent(mainPanel);
		window.setSize(terminalSize);

		// Mostrar la ventana (bloqueante)
		textGUI.addWindowAndWait(window);

	}

	private void onMenuOptionSelected(int opcion) {

		switch (opcion) {
		case 1:
			// showMessage("Gestionar buses")
			window.close();
			displayGestionarBuses();
			break;
		case 2:
			window.close();
			// showMessage("Gestionar pasajes");
			displayGestionarPasajes();
			break;
		case 3:
			window.close();
			// showMessage("Gestionar terminales");
			displayObtenerTerminales();
			break;
		case 4:
			window.close();
			// showMessage("Gestionar viajes");
			displayGestionarViajes();
			break;
		case 5:
			window.close();
			// showMessage("Gestionar rutas");
			displayObtenerRutas();
			break;
		case 6:
			window.close();
			// showMessage("Gestionar pasajeros");
			displayGestionarPasajero();
			break;
		case 7:
			window.close();
			showMessage("Obtener info ventas");
			// displayObtenerVentas();
			break;
		case 8: // Salir
			try {
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			showMessage("Opción no reconocida: " + opcion);
		}
		System.out.println("Opción seleccionada: " + opcion);
	}

	public void displayGestionarPasajero() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de Pasajeros");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Crear pasajero", () -> {
			window.setVisible(false);
			displayCrearPasajero();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Eliminar pasajero", () -> {
			// menuOptionListener.onMenuOptionSelected(32);
			window.close();
		}));

		panel.addComponent(new Button("Modificar pasajero", () -> {
			// menuOptionListener.onMenuOptionSelected(33);
			window.close();
		}));

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayCrearPasajero() {
		BasicWindow window = new BasicWindow("Crear Pasajero");

		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("Nombre:"));
		TextBox nombreBox = new TextBox();
		panel.addComponent(nombreBox);

		panel.addComponent(new Label("Correo:"));
		TextBox correoBox = new TextBox();
		panel.addComponent(correoBox);

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
			String nombre = nombreBox.getText();
			String correo = correoBox.getText();

			// Aquí llamas al controlador:
			menuOptionListener.onCrearPasajero(nombre, correo);

			window.close();
			showMessage("Pasajero creado.");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayGestionarBuses() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de Buses");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Crear Bus", () -> {
			// menuOptionListener.onMenuOptionSelected(31);
			window.close();
		}));

		panel.addComponent(new Button("Eliminar Bus", () -> {
			// menuOptionListener.onMenuOptionSelected(32);
			window.close();
		}));

		panel.addComponent(new Button("Modificar Bus", () -> {
			// menuOptionListener.onMenuOptionSelected(33);
			window.close();
		}));

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayObtenerRutas() {
		clearScreen();
		BasicWindow window = new BasicWindow("Obtener Rutas");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayGestionarViajes() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de viajes");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Crear viaje", () -> {
			// menuOptionListener.onMenuOptionSelected(31);
			window.close();
		}));

		panel.addComponent(new Button("Eliminar viaje", () -> {
			// menuOptionListener.onMenuOptionSelected(32);
			window.close();
		}));

		panel.addComponent(new Button("Obtener viaje", () -> {
			// menuOptionListener.onMenuOptionSelected(33);
			window.close();
		}));

		panel.addComponent(new Button("Obtener viaje", () -> {
			// menuOptionListener.onMenuOptionSelected(33);
			window.close();
		}));

		panel.addComponent(new Button("Obtener viaje por origen SIN fecha", () -> {
			// menuOptionListener.onMenuOptionSelected(33);
			window.setVisible(false);
			displayObtenerViajesSinFecha();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Obtener viaje por origen CON fecha", () -> {
			window.setVisible(false);
			displayObtenerViajesConFecha();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayObtenerViajesConFecha() {
		BasicWindow window = new BasicWindow("Obtener viajes con fecha");

		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("Ingrese origen:"));
		TextBox origenBox = new TextBox();
		panel.addComponent(origenBox);

		panel.addComponent(new Label("Ingrese destino:"));
		TextBox destinoBox = new TextBox();
		panel.addComponent(destinoBox);
		
		panel.addComponent(new Label("Ingrese fecha (YYYY-MM-DD):"));
		TextBox fechaBox = new TextBox();
		panel.addComponent(fechaBox);

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
		    String origen = origenBox.getText();
		    String destino = destinoBox.getText();
		    String inputFecha = fechaBox.getText();
		    LocalDate fecha;
		    System.out.println("chao:"+inputFecha);
		    fecha = LocalDate.parse(inputFecha);
	        System.out.println("ola:"+fecha);
		    try {
		        fecha = LocalDate.parse(inputFecha);
		        System.out.println(fecha);
		    } catch (DateTimeParseException e) {
		        showMessage("Formato inválido. Use (YYYY-MM-DD).");
		        return;
		    }

		    // Aquí llamas al controlador:
		    ArrayList<Viaje> viajes = menuOptionListener.obtenerViaje(origen, destino, fecha);

		    if (viajes.isEmpty()) {
		        showMessage("No se encontraron viajes para el origen y destino especificados.");
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

		    window.close();
		    showMessage("Pasajero creado.");
		    try {
		        displayMenu();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayObtenerViajesSinFecha() {
		BasicWindow window = new BasicWindow("Obtener viajes sin fecha");

		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("Ingrese origen:"));
		TextBox origenBox = new TextBox();
		panel.addComponent(origenBox);

		panel.addComponent(new Label("Ingrese destino:"));
		TextBox destinoBox = new TextBox();
		panel.addComponent(destinoBox);
		
		
		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
		    String origen = origenBox.getText();
		    String destino = destinoBox.getText();
		    

		    // Aquí llamas al controlador:
		    ArrayList<Viaje> viajes = menuOptionListener.obtenerViaje(origen, destino);

		    if (viajes.isEmpty()) {
		        showMessage("No se encontraron viajes para el origen y destino especificados.");
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

		    window.close();
		    showMessage("Viajes mostrados por consola exitosamente");
		    try {
		        displayMenu();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayObtenerTerminales() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de terminales");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Obtener terminal", () -> {
			// menuOptionListener.onMenuOptionSelected(31);
			window.close();
		}));

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayGestionarPasajes() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de pasajes");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Crear pasaje", () -> {
			// menuOptionListener.onMenuOptionSelected(31);
			window.close();
		}));

		panel.addComponent(new Button("Eliminar pasaje", () -> {
			// menuOptionListener.onMenuOptionSelected(32);
			window.close();
		}));

		panel.addComponent(new Button("Consultar pasaje", () -> {
			// menuOptionListener.onMenuOptionSelected(33);
			window.close();
		}));

		panel.addComponent(new Button("Volver", () -> {
			try {

				window.close();
				displayMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	public void displayObtenerVentas() {

	}

	public void showMessage(String mensaje) {
		MessageDialog.showMessageDialog(textGUI, "Información", mensaje);
	}

	public void close() throws IOException {
		if (screen != null) {
			screen.stopScreen();
		}
	}

	public void clearScreen() {
		if (window != null) {
			textGUI.removeWindow(window);
		}
		try {
			screen.clear();
			screen.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
		window = new BasicWindow("BusApp TERMINAL v1.0");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

	}

	public void cursorWait(int col, int row, int millis) {

		screen.setCursorPosition(null);

		try {
			screen.refresh();

			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void typeln(String msg, int col, int row) {
		TextColor defC = foreC;
		foreC = ANSI.GREEN;
		int interval = 11;

		for (int i = 0; i < msg.length(); i++) {
			screen.setCursorPosition(new TerminalPosition(col + i, row));
			screen.setCharacter(col + i, row, new TextCharacter(msg.charAt(i), foreC, backC));

			try {
				screen.refresh();
				Thread.sleep(ThreadLocalRandom.current().nextInt(interval * 3));
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		foreC = defC;
	}
}
