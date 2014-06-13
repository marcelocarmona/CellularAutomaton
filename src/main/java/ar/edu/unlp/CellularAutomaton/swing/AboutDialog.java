package ar.edu.unlp.CellularAutomaton.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import java.awt.Font;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * About Dialog
 * @author mclo
 */
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutDialog.class.getResource("/ar/edu/unlp/CellularAutomaton/util/icon.png")));
		setTitle("About");
		setBounds(100, 100, 291, 287);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JLabel label = new JLabel("");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			label.setIcon(new ImageIcon(AboutDialog.class.getResource("/ar/edu/unlp/CellularAutomaton/util/icon.png")));
			contentPanel.add(label);
		}
		{
			JLabel lblCellularAutomaton = new JLabel("Cellular Automaton");
			lblCellularAutomaton.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblCellularAutomaton.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblCellularAutomaton);
		}

		{
			JLabel lblMclo = new JLabel("Mclo");
			lblMclo.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(lblMclo);
		}
		{
			final JLabel lblWebPage = new JLabel("Mclo.github.io/CellularAutomaton");
			lblWebPage.setForeground(Color.GRAY);
			lblWebPage.setFont(new Font("Tahoma", Font.PLAIN, 9));
			lblWebPage.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI("http://mclo.github.io/CellularAutomaton/"));
					} catch (IOException | URISyntaxException e1) {
						// ignore
					}
					super.mousePressed(e);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					lblWebPage.setForeground(Color.BLUE);
					lblWebPage.setCursor(new Cursor(Cursor.HAND_CURSOR));
					super.mouseEntered(e);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lblWebPage.setForeground(Color.GRAY);
					super.mouseExited(e);
				}

			});
			Component rigidArea2 = Box.createRigidArea(new Dimension(0,5));
			contentPanel.add(rigidArea2);

			lblWebPage.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(lblWebPage);
		}
		{
			JLabel lblVersion = new JLabel("Version 1.0");
			lblVersion.setEnabled(false);
			lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 9));
			lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(lblVersion);
		}

		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}


}
