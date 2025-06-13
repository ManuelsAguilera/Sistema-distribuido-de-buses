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
import common.Pasajero;
import common.Viaje;
import server.BusManagerImpl;

public class ClientView {
	
	// Atributos
	private Screen screen;
	private WindowBasedTextGUI textGUI;
	private BasicWindow window;
	private MenuOptionListener menuOptionListener;
	private TextColor foreC, backC;
	private String clientName = "Vicente Rosales";

	
	// Controlador del MVC
	public void setMenuOptionListener(MenuOptionListener listener) {
		this.menuOptionListener = listener;
	}
	
	
	// Constructor
	public ClientView() throws IOException, NotBoundException {
		foreC = ANSI.YELLOW;
		backC = ANSI.BLACK;
		screen = new DefaultTerminalFactory().createScreen();
		screen.startScreen();
		textGUI = new MultiWindowTextGUI(screen);
		window = new BasicWindow("BusApp TERMINAL v1.0");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));
	}
	
	// ============================================= Sección de Vista principal ============================================= \\
	
	// def initUI()
	
	// def displayMenu()
	
	// def onMenuOptionSelected()
	public void initUI() throws IOException {
		screen = new DefaultTerminalFactory().createScreen();
		foreC = TextColor.ANSI.YELLOW;
		backC = TextColor.ANSI.BLACK;

	}

	@SuppressWarnings("deprecation")
	public void displayMenu() throws IOException {
		TerminalSize terminalSize = screen.getTerminalSize();
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

		// Nombres de los botones
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
			displayObtenerVentas();
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
	
	// ============================================= Sección de Pasajeros ============================================= \\
	
	// def displayGestionarPasajero()
	
	// def displayCrearPasajero()
	
	// def displayEliminarPasajero()
	
	// def displayModificarPasajero()
	
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
			window.setVisible(false);
			displayEliminarPasajero();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Modificar pasajero", () -> {
			window.setVisible(false);
			displayModificarPasajero();
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
			
			Pasajero pasajero = new Pasajero(nombre, correo);
			
			menuOptionListener.crearPasajero(pasajero);

			window.close();
			showMessage("Pasajero creado.");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayEliminarPasajero() {
		clearScreen();
		BasicWindow window = new BasicWindow("Eliminar Pasajero");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));
		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("id Pasajero:"));
		TextBox idPasajeroBox = new TextBox();
		panel.addComponent(idPasajeroBox);

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
			String idPasajero = idPasajeroBox.getText();
			
			menuOptionListener.eliminarPasajero(Integer.parseInt(idPasajero));

			window.close();
			showMessage("Pasajero Eliminado!");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayModificarPasajero() {
		BasicWindow window = new BasicWindow("Modificar Pasajero");

		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("id Pasajero:"));
		TextBox idPasajeroBox = new TextBox();
		panel.addComponent(idPasajeroBox);
		
		panel.addComponent(new Label("Nombre:"));
		TextBox nombreBox = new TextBox();
		panel.addComponent(nombreBox);

		panel.addComponent(new Label("Correo:"));
		TextBox correoBox = new TextBox();
		panel.addComponent(correoBox);

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
			int idPasajero = Integer.parseInt(idPasajeroBox.getText());
			String nombre = nombreBox.getText();
			String correo = correoBox.getText();
			
			Pasajero pasajeroModificado = new Pasajero(idPasajero, nombre, correo);
			
			menuOptionListener.modificarPasajero(idPasajero, pasajeroModificado);

			window.close();
			showMessage("Pasajero modificado!");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}

	// ============================================= Sección de buses ============================================= \\
	
	// def displayGestionarBuses()
	
	// def displayCrearBus()
	
	// def displayEliminarBus()
	
	// def displayModificarBus()
	
	public void displayGestionarBuses() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de Buses");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Crear Bus", () -> {
			window.setVisible(false);
			displayCrearBus();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Eliminar Bus", () -> {
			window.setVisible(false);
			displayEliminarBus();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Modificar Bus", () -> {
			window.setVisible(false);
			displayModificarBus();
			window.setVisible(true);
			window.close();
		}));

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayCrearBus() {
		BasicWindow window = new BasicWindow("Crear Bus");

		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("Matricula:"));
		TextBox matriculaBox = new TextBox();
		panel.addComponent(matriculaBox);
		
		panel.addComponent(new Label("Modelo:"));
		TextBox modeloBox = new TextBox();
		panel.addComponent(modeloBox);

		panel.addComponent(new Label("Asientos totales:"));
		TextBox asientosTotalesBox = new TextBox();
		panel.addComponent(asientosTotalesBox);

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
			String matricula = matriculaBox.getText();
			String modelo = modeloBox.getText();
			int asientosTotales = Integer.parseInt(asientosTotalesBox.getText());
			
			Bus bus = new Bus(matricula, modelo, asientosTotales);
	
			menuOptionListener.crearBus(bus);

			window.close();
			showMessage("Bus creado!");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayEliminarBus() {
		BasicWindow window = new BasicWindow("Eliminar Bus");

		Panel panel = new Panel(new GridLayout(2));

		panel.addComponent(new Label("Matricula:"));
		TextBox matriculaBox = new TextBox();
		panel.addComponent(matriculaBox);
		

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
			String matricula = matriculaBox.getText();
			
			menuOptionListener.eliminarBus(matricula);

			window.close();
			showMessage("Bus eliminado!");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayModificarBus() {
		BasicWindow window = new BasicWindow("Modificar Bus");

		Panel panel = new Panel(new GridLayout(2));
		
		panel.addComponent(new Label("Matricula:"));
		TextBox matriculaBox = new TextBox();
		panel.addComponent(matriculaBox);
		
		panel.addComponent(new Label("Modelo:"));
		TextBox modeloBox = new TextBox();
		panel.addComponent(modeloBox);

		panel.addComponent(new Label("Asientos totales:"));
		TextBox asientosTotalesBox = new TextBox();
		panel.addComponent(asientosTotalesBox);

		panel.addComponent(new EmptySpace());
		panel.addComponent(new Button("Guardar", () -> {
			String matricula = matriculaBox.getText();
			String modelo = modeloBox.getText();
			int asientosTotales = Integer.parseInt(asientosTotalesBox.getText());
			
			Bus bus = new Bus(matricula, modelo, asientosTotales);
			
			menuOptionListener.modificarBus(bus);
			
			window.close();
			showMessage("Bus Modificado!");
			try {
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));
		
		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	// ============================================= Sección de Rutas ============================================= \\
	
	// def displayObtenerRutas()
	
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
	
	// ============================================= Sección de Viajes ============================================= \\
	
	// def displayGestionarViajes()
	
	// def displayCrearViaje()
	
	// def displayEliminarViaje()
	
	// def displayModificarViaje()
	
	// def displayObtenerViajesConFecha()
	
	// def displayObtenerViajesSinFecha()
	
	// def displayListaViajes()
	
	public void displayGestionarViajes() {
		clearScreen();
		BasicWindow window = new BasicWindow("Gestion de viajes");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));

		panel.addComponent(new Button("Crear viaje", () -> {
			window.setVisible(false);
			displayCrearViaje();
			window.setVisible(true);
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

		panel.addComponent(new Button("Modificar viaje", () -> {
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
		
		//panel.addComponent()

		panel.addComponent(new Button("Volver", () -> {
			try {
				window.close();
				displayMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayCrearViaje() {
		BasicWindow window = new BasicWindow("Creación de viaje");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));
		
		Panel panel = new Panel(new GridLayout(2));
		
		panel.addComponent(new Label("Ingrese nombre:"));
		TextBox origenBox = new TextBox();
		panel.addComponent(origenBox);

		panel.addComponent(new Label("Ingrese destino:"));
		TextBox destinoBox = new TextBox();
		panel.addComponent(destinoBox);
		
		panel.addComponent(new Label("Ingrese fecha (YYYY-MM-DD):"));
		TextBox fechaBox = new TextBox();
		panel.addComponent(fechaBox);
	
	}
	
	public void displayEliminarViaje() {
		
	}
	
	public void displayModificarViaje() {
		
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
		    
		    fecha = LocalDate.parse(inputFecha);
		    try {
		        fecha = LocalDate.parse(inputFecha);
		        System.out.println(fecha);
		    } catch (DateTimeParseException e) {
		        showMessage("Formato inválido. Use (YYYY-MM-DD).");
		        return;
		    }

		    ArrayList<Viaje> viajes = menuOptionListener.obtenerViaje(origen, destino, fecha);
		    if (viajes.isEmpty()) {
		    	System.out.println("Vacia");
		    
		    } else {
		    	window.setVisible(false);
			    displayListaViajes(viajes);
		    }
		    
		    window.close();
		    displayListaViajes(viajes);
		    

		    window.close();
		    
		    try {
		        displayMenu();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}));
		
		panel.addComponent(new Button("Volver", () -> {
	        window.close();
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
		    
		    ArrayList<Viaje> viajes = menuOptionListener.obtenerViaje(origen, destino);
		    
		    if (viajes.isEmpty()) {
		    	System.out.println("Vacia");
		    
		    } else {
		    	window.setVisible(false);
			    displayListaViajes(viajes);
		    }

		    window.close();
		    try {
		    	displayMenu();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}));
		
		panel.addComponent(new Button("Volver", () -> {
	        window.close();
	        try {
	            displayMenu();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }));

		window.setComponent(panel);
		textGUI.addWindowAndWait(window);
	}
	
	public void displayListaViajes(ArrayList<Viaje> viajes) {
	    BasicWindow window = new BasicWindow("Lista de Viajes");
		window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));
		
	    Panel panel = new Panel(new GridLayout(1));

	    if (viajes.isEmpty()) {
	        panel.addComponent(new Label("No se encontraron viajes."));
	    } else {
	        int contador = 1;
	        for (Viaje v : viajes) {
	            panel.addComponent(new Label("Viaje #" + contador++));
	            panel.addComponent(new Label("ID Viaje       : " + v.getidViaje()));
	            panel.addComponent(new Label("ID Ruta        : " + v.getIdRuta()));
	            panel.addComponent(new Label("Matrícula Bus  : " + v.getMatricula()));
	            panel.addComponent(new Label("Fecha          : " + v.getFecha()));
	            panel.addComponent(new Label("Hora Salida    : " + v.getSalida()));
	            panel.addComponent(new Label("Salida Estimada: " + v.getSalidaEstimada()));
	            panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
	        }
	    }

	    panel.addComponent(new EmptySpace());
	    panel.addComponent(new Button("Volver", () -> {
	        window.close();
	        try {
	            displayMenu();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }));

	    window.setComponent(panel);
	    textGUI.addWindowAndWait(window);
	}
	
	// ============================================= Sección de Terminales ============================================= \\
	
	// def displayObtenerTerminales()
	
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
	
	// ============================================= Sección de Pasajes ============================================= \\
	
	// def displayGestionarPasajes()
	
	// def displayCrearPasajes()
	
	// def displayEliminarPasajes()
	
	// def displayConsultarPasajes()
	
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
	
	public void displayCrearPasaje() {}
	
	public void displayEliminarPasaje() {}
	
	public void dusplayConsultarPasaje() {}
	
	// ============================================= Sección de Ventas ============================================= \\
	
	// def displayObtenerVentas()
	
	public void displayObtenerVentas() {
	    clearScreen();
	    BasicWindow window = new BasicWindow("Informe de Ventas");
	    window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));

	    Panel panel = new Panel(new GridLayout(2));

	    panel.addComponent(new Label("Ingrese fecha inicio (YYYY-MM-DD):"));
	    TextBox fechaInicioBox = new TextBox();
	    panel.addComponent(fechaInicioBox);

	    panel.addComponent(new Label("Ingrese fecha fin (YYYY-MM-DD):"));
	    TextBox fechaFinBox = new TextBox();
	    panel.addComponent(fechaFinBox);

	    panel.addComponent(new EmptySpace());
	    panel.addComponent(new Button("Consultar", () -> {
	        String inicioStr = fechaInicioBox.getText();
	        String finStr = fechaFinBox.getText();

	        LocalDate fechaInicio;
	        LocalDate fechaFin;
	        try {
	            fechaInicio = LocalDate.parse(inicioStr);
	            fechaFin = LocalDate.parse(finStr);
	        } catch (DateTimeParseException e) {
	            showMessage("Formato de fecha inválido. Use YYYY-MM-DD.");
	            return;
	        }
	        
	        // TODO: Falta agregar metodoObtenerVentas en interfaz
	        double totalVentas =  2.0; //menuOptionListener.obtenerVentas(fechaInicio, fechaFin);

	        showMessage("Ventas entre " + fechaInicio + " y " + fechaFin + ": $" + totalVentas);

	        window.close();
	        try {
	            displayMenu();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }));

	    panel.addComponent(new Button("Volver", () -> {
	        window.close();
	        try {
	            displayMenu();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }));

	    window.setComponent(panel);
	    textGUI.addWindowAndWait(window);
	}
	
	// ============================================= Sección de varios ============================================= \\
	// def showMessage()
	
	// def close()
	
	// def clearScreen()
	
	// def cursorWait(int col, int row, int millis)
	
	// def typeln(String msg, int col, int row)
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
