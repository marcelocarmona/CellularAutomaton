package ar.edu.unlp.CellularAutomaton;

import java.awt.EventQueue;

import ar.edu.unlp.CellularAutomaton.view.Window;


/**
 * @author mclo
 * 
 * Main Class
 *
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
					new Window();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
