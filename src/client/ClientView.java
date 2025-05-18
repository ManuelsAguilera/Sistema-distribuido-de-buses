package client;

import common.Bus;

import java.io.File;
import java.io.IOException;
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

public class ClientView {

	private Screen screen;
	private WindowBasedTextGUI textGUI;
	private BasicWindow window;
	private MenuOptionListener menuOptionListener;
	private TextColor foreC, backC;
	private String clientName = "Vicente Rosales";
	
	
	public void setMenuOptionListener(MenuOptionListener listener) {
	    this.menuOptionListener = listener;
	}
	
	public ClientView() throws IOException {
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
	public void displayData() throws IOException {
		TerminalSize terminalSize = screen.getTerminalSize();

		// Layout de 3 columnas para los paneles
	    Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

	    // Header
        Label header = new Label("----------- Cliente: " + clientName + " -------------")
            .setForegroundColor(TextColor.ANSI.BLACK)
            .setBackgroundColor(TextColor.ANSI.YELLOW);
        mainPanel.addComponent(header);

        // Crear paneles para cada fila
        Panel contentPanel = new Panel(new GridLayout(3));
        
        // Etiquetas para cada columna
	    contentPanel.addComponent(new Label("#").setForegroundColor(TextColor.ANSI.YELLOW));
	    contentPanel.addComponent(new Label("Métodos").setForegroundColor(TextColor.ANSI.YELLOW));
	    contentPanel.addComponent(new Label("Vista").setForegroundColor(TextColor.ANSI.YELLOW));
        
        
        // Botones que representan funcionalidades, puedes agregar más
        String[] opciones = {
                "Ver listado de buses",
                "Reservar asiento",
                "Cancelar pasaje",
                "Salir"
        };
        
        int index = 1;
	    for (String opcion : opciones) {
	    	contentPanel.addComponent(new Label(String.valueOf(index)));
	    	contentPanel.addComponent(new Button(opcion, () -> {
	            MessageDialog.showMessageDialog(textGUI, "Acción", "Seleccionaste: " + opcion);
	        }));
	    	contentPanel.addComponent(new Label("...")); 
	        index++;
	    }
	    
	    // Añadir paneles al panel principal
	    mainPanel.addComponent(contentPanel);
	    
	    // Footer
        Label footer = new Label("--------------------------------------------------")
            .setForegroundColor(TextColor.ANSI.BLACK)
            .setBackgroundColor(TextColor.ANSI.YELLOW);
        mainPanel.addComponent(footer);
        
	    // Añadir panel principal a la ventana
	    window.setComponent(mainPanel);
	    window.setSize(terminalSize);

	    // Mostrar la ventana (bloqueante)
	    textGUI.addWindowAndWait(window);

	}
	
	private void onMenuOptionSelected(int opcion) {
        // Solo imprime por ahora, controlador debería conectar acá para recibir eventos.
        System.out.println("Opción seleccionada: " + opcion);
    }
	
	/*public void displayBusList(List<Bus> buses) {
        // Por simplicidad, mostramos lista simple
        StringBuilder sb = new StringBuilder("Listado de buses:\n");
        for (Bus bus : buses) {
            sb.append(String.format("ID: %d, Origen: %s, Destino: %s, Asientos disponibles: %d\n",
                    bus.getId(), bus.getOrigen(), bus.getDestino(), bus.getAsientosDisponibles()));
        }
        MessageDialog.showMessageDialog(textGUI, "Buses", sb.toString());
    }
    
    
     Pide al usuario que ingrese el ID de un bus.
     Este método bloquea hasta que se ingresa algo.
     
    public int promptBusSelection() {
        TextInputDialog dialog = new TextInputDialog("Seleccionar Bus", "Ingrese ID del bus:", textGUI);
        String input = dialog.showDialog();
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            return -1; // Indica error o cancelación
        }
    }
    
    // Pide al usuario que seleccione asiento.
    public int seatSelection() {
        TextInputDialog dialog = new TextInputDialog("Seleccionar Asiento", "Ingrese número de asiento:", textGUI);
        String input = dialog.showDialog();
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            return -1;
        }
    }
    
    // Muestra resultado de la operación de reserva.
    public void showReservationState(boolean estado) {
        String mensaje = estado ? "Reserva exitosa!" : "Reserva fallida.";
        MessageDialog.showMessageDialog(textGUI, "Reserva", mensaje);
    }
    
    
    */
	
	public void showMessage(String mensaje) {
        MessageDialog.showMessageDialog(textGUI, "Información", mensaje);
    }
	
	public void close() throws IOException {
        if (screen != null) {
            screen.stopScreen();
        }
    }

	private void setup() throws IOException {
		cursorWait(0, 0, 1111);
		typeln(">_ PREPARANDO INTERFAZ", 0, 0);
		cursorWait(0, 0, 999);
		typeln(">_ INICIALIZANDO SISTEMA", 0, 1);
		cursorWait(0, 0, 888);
		typeln(">_ ....................................................", 0, 2);
		cursorWait(0, 0, 777);
		typeln(">_ CONECTANDOSE A EL %SERVIDOR%", 0, 3);
		typeln(">_ CONEXION EXITOSA!", 0, 4);
		cursorWait(0, 0, 666);
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

/*
 @SuppressWarnings("deprecation")
	private void displayData() throws IOException {
		TerminalSize terminalSize = screen.getTerminalSize();

	    // Crear el GUI sobre la screen
	    WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

	    // Ventana base
	    BasicWindow window = new BasicWindow("BusApp TERMINAL v1.0");
	    window.setHints(Set.of(Window.Hint.CENTERED, Window.Hint.FIT_TERMINAL_WINDOW));
	    
	    // Layout de 3 columnas para los paneles
	    Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        
	    // Header
        Label header = new Label("----------- Cliente: " + clientName + " -------------")
            .setForegroundColor(TextColor.ANSI.BLACK)
            .setBackgroundColor(TextColor.ANSI.YELLOW);
        mainPanel.addComponent(header);
	    
        // Crear paneles para cada fila
        Panel contentPanel = new Panel(new GridLayout(3));

	    // Etiquetas para cada columna
	    contentPanel.addComponent(new Label("#").setForegroundColor(TextColor.ANSI.YELLOW));
	    contentPanel.addComponent(new Label("Métodos").setForegroundColor(TextColor.ANSI.YELLOW));
	    contentPanel.addComponent(new Label("Vista").setForegroundColor(TextColor.ANSI.YELLOW));

	    // Opciones de menú (aquí como botones de ejemplo)
	    String[] opciones = {
	        "Ver listado de buses",
	        "Buscar bus por ID",
	        "Reservar asiento",
	        "Liberar asiento",
	        "Ver asientos disponibles",
	        "Reiniciar sistema",
	        "Salir"
	    };

	    int index = 1;
	    for (String opcion : opciones) {
	    	if (opcion == "Salir") {
	    		contentPanel.addComponent(new Label(String.valueOf(index)));
		    	contentPanel.addComponent(new Button(opcion, () -> {
		            try {
						turnOff();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }));
		    	contentPanel.addComponent(new Label("...")); 
		        break;
	    	}
	    	
	    	contentPanel.addComponent(new Label(String.valueOf(index)));
	    	contentPanel.addComponent(new Button(opcion, () -> {
	            MessageDialog.showMessageDialog(textGUI, "Acción", "Seleccionaste: " + opcion);
	        }));
	    	contentPanel.addComponent(new Label("...")); // Puedes colocar aquí un estado o ícono
	        index++;
	    }

	    // Añadir paneles al panel principal
	    mainPanel.addComponent(contentPanel);
	    
	    
	    
	    // Footer
        Label footer = new Label("--------------------------------------------------")
            .setForegroundColor(TextColor.ANSI.BLACK)
            .setBackgroundColor(TextColor.ANSI.YELLOW);
        mainPanel.addComponent(footer);
        
	    // Añadir panel principal a la ventana
	    window.setComponent(mainPanel);
	    window.setSize(terminalSize);

	    // Mostrar la ventana (bloqueante)
	    textGUI.addWindowAndWait(window);
	}
 */
