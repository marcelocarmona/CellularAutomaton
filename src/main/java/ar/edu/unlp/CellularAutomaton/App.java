package ar.edu.unlp.CellularAutomaton;

import ar.edu.unlp.CellularAutomaton.view.GameFrame;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;


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
				    JFrame.setDefaultLookAndFeelDecorated(true);
				    JDialog.setDefaultLookAndFeelDecorated(true);
				    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					GameFrame frame = new GameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
