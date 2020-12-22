package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;

public class DBAccessView {

	private JButton btnAccedi;
	private JFrame frmWelcome;
	private JLabel lblErrors;
	private JLabel lblPassword;
	private JLabel lblUser;
	private JSeparator separator;
	private JTextField txtPassword;
	private JTextPane txtpnDeliveryUltra;
	private JTextField txtPorta;

	private JTextField txtServer;

	
	private JTextField txtUser;
	private JLabel lblDbName;
	private JTextField txtDbName;

	/**
	 * Crea la finestra principale.
	 */
	public DBAccessView() {
		frmWelcome = new JFrame();
		frmWelcome.getContentPane().setBackground(new Color(102, 204, 255));
		frmWelcome.setTitle("Accesso al DB");
		frmWelcome.setBounds(100, 100, 300, 300);
		frmWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcome.getContentPane().setLayout(new MigLayout("", "[grow][grow][][][grow]", "[grow][][][][][][]"));
		
		txtpnDeliveryUltra = new JTextPane();
		txtpnDeliveryUltra.setEditable(false);
		txtpnDeliveryUltra.setBackground(new Color(102, 204, 255));
		txtpnDeliveryUltra.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 23));
		txtpnDeliveryUltra.setText("DELIVERY ULTRA");
		frmWelcome.getContentPane().add(txtpnDeliveryUltra, "cell 0 0 5 1,alignx center,aligny center");
		
		separator = new JSeparator();
		separator.setForeground(new Color(0, 102, 255));
		frmWelcome.getContentPane().add(separator, "cell 0 1 5 1,alignx center,aligny center");
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		frmWelcome.getContentPane().add(lblServer, "cell 0 2,alignx left,aligny center");
		
		txtServer = new JTextField();
		txtServer.setToolTipText("L'indirizzo ip del db, es 127.0.0.1");
		frmWelcome.getContentPane().add(txtServer, "cell 1 2 2 1,growx");
		txtServer.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Porta");
		frmWelcome.getContentPane().add(lblNewLabel, "cell 3 2,alignx trailing");
		
		txtPorta = new JTextField();
		txtPorta.setToolTipText("La porta di accesso al db, es 3306");
		frmWelcome.getContentPane().add(txtPorta, "cell 4 2,growx");
		txtPorta.setColumns(5);
		
		lblDbName = new JLabel("Database");
		frmWelcome.getContentPane().add(lblDbName, "cell 0 3,alignx left");
		
		txtDbName = new JTextField();
		txtDbName.setToolTipText("Nome del db al quale accedere...");
		frmWelcome.getContentPane().add(txtDbName, "cell 1 3 4 1,growx");
		txtDbName.setColumns(10);
		
		lblUser = new JLabel("Username");
		lblUser.setToolTipText("");
		frmWelcome.getContentPane().add(lblUser, "cell 0 4,alignx left");
		
		txtUser = new JTextField();
		txtUser.setToolTipText("L'username per l'accesso al db, es. root");
		frmWelcome.getContentPane().add(txtUser, "cell 1 4 4 1,growx");
		txtUser.setColumns(10);
		
		lblPassword = new JLabel("Password");
		frmWelcome.getContentPane().add(lblPassword, "cell 0 5,alignx left");
		
		txtPassword = new JTextField();
		txtPassword.setToolTipText("La tua password di accesso al db...");
		frmWelcome.getContentPane().add(txtPassword, "cell 1 5 4 1,growx");
		txtPassword.setColumns(10);
		
		btnAccedi = new JButton("Accedi");
		
		lblErrors = new JLabel("");
		lblErrors.setForeground(new Color(153, 0, 0));
		frmWelcome.getContentPane().add(lblErrors, "cell 0 6 4 1,alignx left,aligny center");
		frmWelcome.getContentPane().add(btnAccedi, "cell 4 6,alignx right");
		
		frmWelcome.setLocationRelativeTo(null);
		frmWelcome.setVisible(true);
		frmWelcome.setResizable(false);
	}

	public JButton getBtnAccedi() {
		return btnAccedi;
	}

	public JLabel getLblErrors() {
		return lblErrors;
	}

	public JTextField getTxtPassword() {
		return txtPassword;
	}

	public JTextField getTxtPorta() {
		return txtPorta;
	}

	public JTextField getTxtServer() {
		return txtServer;
	}

	public JTextField getTxtUser() {
		return txtUser;
	}
	
	public JTextField getTxtDbName() {
		return txtDbName;
	}


}
