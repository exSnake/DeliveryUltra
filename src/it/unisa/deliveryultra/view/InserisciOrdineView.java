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
import javax.swing.border.TitledBorder;

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
		setTitle("Admin - Inserimento Nuovo Ordine");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100,100,768,400);
		JPanel pane = new JPanel();
		setContentPane(pane);
		getContentPane().setLayout(new MigLayout("", "[82.00,grow][grow][grow,leading]", "[][][][grow][][]"));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Informazioni Ordine", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.add(panel_2, "cell 0 0 3 1,grow");
		panel_2.setLayout(new MigLayout("", "[329.00][grow][grow,leading]", "[][][][]"));
		
		JLabel lblRistorante = new JLabel("Ristorante");
		panel_2.add(lblRistorante, "cell 0 0");
		
		JLabel lblCliente = new JLabel("Cliente");
		panel_2.add(lblCliente, "cell 1 0");
		
		cmbRistorante = new JComboBox<>();
		panel_2.add(cmbRistorante, "cell 0 1,growx");
		
		cmbCliente = new JComboBox<>();
		panel_2.add(cmbCliente, "cell 1 1 2 1,growx");
		
		JLabel lblDestinazione = new JLabel("Destinazione (Selezionare un cliente)");
		panel_2.add(lblDestinazione, "cell 0 2");
		
		cmbIndirizzo = new JComboBox<>();
		panel_2.add(cmbIndirizzo, "cell 0 3 3 1,growx");
		cmbIndirizzo.setEnabled(false);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 1 3 1");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Tipologia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.add(panel_1, "cell 0 2 3 1,grow");
		panel_1.setLayout(new MigLayout("", "[82.00,grow][grow][grow,leading]", "[]"));
		
		txtTipo = new JTextField();
		panel_1.add(txtTipo, "cell 0 0 3 1,growx");
		getTxtTipo().setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Descrizione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.add(panel, "cell 0 3 3 1,grow");
		panel.setLayout(new MigLayout("", "[82.00,grow][grow][grow][grow,leading]", "[grow]"));
		
		txtDescrizione = new JTextArea();
		panel.add(txtDescrizione, "cell 0 0 4 1,grow");
		txtDescrizione.setLineWrap(true);
		
		lblRemainingChar = new JLabel("0/255");
		getContentPane().add(getLblRemainingChar(), "cell 2 4");
		
		lblErrors = new JLabel("");
		getLblErrors().setForeground(new Color(153, 0, 0));
		getLblErrors().setFont(new Font("Tahoma", Font.PLAIN, 11));
		pane.add(getLblErrors(), "cell 0 5");
		
		JButton btnAnnulla = new JButton("Annulla");
		getContentPane().add(btnAnnulla, "cell 1 5,grow");
		
		btnInserisci = new JButton("Inserisci");
		getContentPane().add(getBtnInserisci(), "cell 2 5,growx");

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
