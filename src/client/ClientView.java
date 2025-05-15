package client;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class ClientView {
	
	
	public void showMenu() throws IOException {
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
	    Screen screen = new TerminalScreen(terminal);
	    screen.startScreen();
	    
	    Panel mainPanel = new Panel();
	    mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
	    
	    Panel leftPanel = new Panel();
	    mainPanel.addComponent(leftPanel.withBorder(Borders.singleLine("Left Panel")));
	    
	    mainPanel.addComponent(new EmptySpace(new TerminalSize(3,0))); 
	    
	    Panel rightPanel = new Panel();
	    mainPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Left Panel")));
	    
	    
	    
	    Panel container = new Panel(new GridLayout(1));
	    container.addComponent(new EmptySpace(new TerminalSize(0,1))); 
	    container.addComponent(mainPanel.withBorder(Borders.singleLine("Main Panel")));
	    container.addComponent(new EmptySpace(new TerminalSize(0,1))); 
	    
	    BasicWindow window = new BasicWindow();

	    window.setComponent(container);
	    
	    MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
	    gui.addWindowAndWait(window);
		
	}
	
}
