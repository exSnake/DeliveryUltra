package it.unisa.deliveryultra.view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import it.unisa.deliveryultra.model.*;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class InserisciOrdineView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTipo;
	private JComboBox<Ristorante> cmbRistorante;
	private JComboBox<Cliente> cmbCliente;
	private JComboBox<Indirizzo> cmbIndirizzo;
	private JTextArea txtDescrizione;
	private JButton btnInserisci;
	private JLabel lblErrors;
	private JLabel lblRemainingChar;

	/**
	 * Create the panel.
	 */
	public InserisciOrdineView() {
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100,100,768,400);
		JPanel pane = new JPanel();
		setContentPane(pane);
		getContentPane().setLayout(new MigLayout("", "[82.00,grow][grow][grow][grow,leading]", "[][][][][][][][][grow][][]"));
		
		JLabel lblRistorante = new JLabel("Ristorante");
		getContentPane().add(lblRistorante, "cell 0 0");
		
		JLabel lblCliente = new JLabel("Cliente");
		getContentPane().add(lblCliente, "cell 2 0");
		
		cmbRistorante = new JComboBox<>();
		getContentPane().add(cmbRistorante, "cell 0 1 2 1,growx");
		
		cmbCliente = new JComboBox<>();
		getContentPane().add(cmbCliente, "cell 2 1 2 1,growx");
		
		JLabel lblDestinazione = new JLabel("Destinazione");
		getContentPane().add(lblDestinazione, "cell 0 2");
		
		cmbIndirizzo = new JComboBox<>();
		cmbIndirizzo.setEnabled(false);
		getContentPane().add(cmbIndirizzo, "cell 0 3 4 1,growx");
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 4 4 1");
		
		JLabel lblTipo = new JLabel("Tipologia");
		getContentPane().add(lblTipo, "cell 0 5,alignx left");
		
		txtTipo = new JTextField();
		getContentPane().add(getTxtTipo(), "cell 0 6 4 1,growx");
		getTxtTipo().setColumns(10);
		
		JLabel lblDescrizione = new JLabel("Descrizione");
		getContentPane().add(lblDescrizione, "cell 0 7");
		
		txtDescrizione = new JTextArea();
		txtDescrizione.setLineWrap(true);
		getContentPane().add(getTxtDescrizione(), "cell 0 8 4 1,grow");
		
		lblRemainingChar = new JLabel("0/255");
		getContentPane().add(getLblRemainingChar(), "cell 3 9");
		
		lblErrors = new JLabel("");
		getLblErrors().setForeground(new Color(153, 0, 0));
		getLblErrors().setFont(new Font("Tahoma", Font.PLAIN, 11));
		pane.add(getLblErrors(), "cell 0 10");
		
		JButton btnAnnulla = new JButton("Annulla");
		getContentPane().add(btnAnnulla, "cell 2 10,alignx right");
		
		btnInserisci = new JButton("Inserisci");
		getContentPane().add(getBtnInserisci(), "cell 3 10,alignx right");

	}
	
	public JComboBox<Ristorante> getCmbRistorante() {
		return cmbRistorante;
	}

	public JComboBox<Cliente> getCmbCliente() {
		return cmbCliente;
	}
	
	public JComboBox<Indirizzo> getCmbIndirizzo() {
		return cmbIndirizzo;
	}

	public JButton getBtnInserisci() {
		return btnInserisci;
	}

	public JTextArea getTxtDescrizione() {
		return txtDescrizione;
	}

	public JTextField getTxtTipo() {
		return txtTipo;
	}

	public JLabel getLblErrors() {
		return lblErrors;
	}

	public JLabel getLblRemainingChar() {
		return lblRemainingChar;
	}

}
