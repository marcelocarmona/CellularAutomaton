package ar.edu.unlp.CellularAutomaton;

import ar.edu.unlp.CellularAutomaton.view.Window;
import java.awt.EventQueue;


/**
 * Main Class
 * @author mclo
 */
public class App 
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
