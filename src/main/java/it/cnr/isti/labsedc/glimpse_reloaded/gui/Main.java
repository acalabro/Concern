package it.cnr.isti.labsedc.glimpse_reloaded.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import it.cnr.isti.labsedc.glimpse_reloaded.App;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Label;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main {

	private JFrame frmGlimpseReloaded;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmGlimpseReloaded.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGlimpseReloaded = new JFrame();
		frmGlimpseReloaded.setType(Type.UTILITY);
		frmGlimpseReloaded.setFont(new Font("Verdana", Font.PLAIN, 14));
		frmGlimpseReloaded.getContentPane().setBackground(new Color(62, 120, 202));
		frmGlimpseReloaded.setTitle("Glimpse_Reloaded - Interface");
		frmGlimpseReloaded.setBounds(100, 100, 900	, 620);
		frmGlimpseReloaded.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGlimpseReloaded.getContentPane().setLayout(null);

		Panel panel = new Panel();
		panel.setBackground(new Color(23, 35, 54));
		panel.setBounds(0, 0, 190, 590);
		frmGlimpseReloaded.getContentPane().add(panel);

		JButton btnNewButton = new JButton("RUN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					App.main(null);
					} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Label label = new Label("                                         ");
				label.setForeground(new Color(0, 0, 0));
				panel.add(label);

		Label label_1 = new Label("                                         ");
		label_1.setForeground(Color.BLACK);
		panel.add(label_1);

		JLabel lblComponentStatus_1 = new JLabel("COMPONENT STATUS");
		lblComponentStatus_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblComponentStatus_1.setForeground(new Color(255, 255, 255));
		panel.add(lblComponentStatus_1);

		Label label_2 = new Label("-----------------------------------");
		label_2.setForeground(new Color(255, 255, 255));
		panel.add(label_2);

		JCheckBox chckbxNewCheckBox = new JCheckBox("STORAGE");
		chckbxNewCheckBox.setFont(new Font("URW Gothic L", Font.BOLD, 12));
		chckbxNewCheckBox.setForeground(new Color(255, 255, 255));
		chckbxNewCheckBox.setBackground(new Color(23, 35, 54));
		chckbxNewCheckBox.setVerticalAlignment(SwingConstants.BOTTOM);
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
		panel.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("MESSAGE BROKER");
		chckbxNewCheckBox_1.setFont(new Font("URW Gothic L", Font.BOLD, 12));
		chckbxNewCheckBox_1.setBackground(new Color(23, 35, 54));
		chckbxNewCheckBox_1.setForeground(new Color(255, 255, 255));
		panel.add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("CHANNEL REGISTRY");
		chckbxNewCheckBox_2.setFont(new Font("URW Gothic L", Font.BOLD, 12));
		chckbxNewCheckBox_2.setBackground(new Color(23, 35, 54));
		chckbxNewCheckBox_2.setForeground(new Color(255, 255, 255));
		panel.add(chckbxNewCheckBox_2);

		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("CEP INSTANCES");
		chckbxNewCheckBox_3.setFont(new Font("URW Gothic L", Font.BOLD, 12));
		chckbxNewCheckBox_3.setBackground(new Color(23, 35, 54));
		chckbxNewCheckBox_3.setForeground(new Color(255, 255, 255));
		panel.add(chckbxNewCheckBox_3);

		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("SERVICE LISTENERS");
		chckbxNewCheckBox_4.setFont(new Font("URW Gothic L", Font.BOLD, 12));
		chckbxNewCheckBox_4.setBackground(new Color(23, 35, 54));
		chckbxNewCheckBox_4.setForeground(new Color(255, 255, 255));
		panel.add(chckbxNewCheckBox_4);

		JCheckBox chckbxNewCheckBox_5 = new JCheckBox("NOTIFICATION ENGINE");
		chckbxNewCheckBox_5.setFont(new Font("URW Gothic L", Font.BOLD, 12));
		chckbxNewCheckBox_5.setBackground(new Color(23, 35, 54));
		chckbxNewCheckBox_5.setForeground(new Color(255, 255, 255));
		panel.add(chckbxNewCheckBox_5);
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(113, 168, 255));
		panel_1.setBounds(189, 51, 705, 64);
		frmGlimpseReloaded.getContentPane().add(panel_1);

		JLabel lblNewLabel = new JLabel("Glimpse - Complex Event Processing Infrastructure");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(189, 0, 705, 50);
		frmGlimpseReloaded.getContentPane().add(lblNewLabel);

		JTextArea textArea = new JTextArea();
		textArea.setForeground(new Color(50, 205, 50));
		textArea.setFont(new Font("Courier 10 Pitch", Font.PLAIN, 12));
		textArea.setBackground(new Color(0, 0, 0));
		textArea.setRows(8);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(189, 481, 705, 110);
		frmGlimpseReloaded.getContentPane().add(textArea);
	}
}
