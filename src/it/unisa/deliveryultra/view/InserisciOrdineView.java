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
import javax.swing.JToggleButton;

public class InserisciOrdineView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtTipo;
	private JComboBox<Ristorante> cmbRistorante;
	private JComboBox<Cliente> cmbCliente;
	private JComboBox<Indirizzo> cmbIndirizzo;
	private JTextArea txtDescrizione;
	private JButton btnInserisci;
	private JLabel lblErrors;
	private JLabel lblRemainingChar;
	private JToggleButton tglbtnDisponibili;

	public InserisciOrdineView() {
		setTitle("Admin - Inserimento Nuovo Ordine");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100,100,796,400);
		JPanel pane = new JPanel();
		setContentPane(pane);
		getContentPane().setLayout(new MigLayout("", "[82.00,grow][grow][grow,leading]", "[][][][grow][][]"));
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(new TitledBorder(null, "Informazioni Ordine", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.add(panelInfo, "cell 0 0 3 1,grow");
		panelInfo.setLayout(new MigLayout("", "[329.00][][grow][grow,leading][]", "[][][][]"));
		
		JLabel lblRistorante = new JLabel("Ristorante");
		panelInfo.add(lblRistorante, "cell 0 0");
		
		JLabel lblCliente = new JLabel("Cliente");
		panelInfo.add(lblCliente, "cell 2 0");
		
		cmbRistorante = new JComboBox<>();
		panelInfo.add(cmbRistorante, "cell 0 1,growx");
		
		tglbtnDisponibili = new JToggleButton("Disponibili");
		panelInfo.add(tglbtnDisponibili, "cell 1 1");
		
		cmbCliente = new JComboBox<>();
		panelInfo.add(cmbCliente, "cell 2 1 2 1,growx");
		
		JLabel lblDestinazione = new JLabel("Destinazione (Selezionare un cliente)");
		panelInfo.add(lblDestinazione, "cell 0 2");
		
		cmbIndirizzo = new JComboBox<>();
		panelInfo.add(cmbIndirizzo, "cell 0 3 4 1,growx");
		cmbIndirizzo.setEnabled(false);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 1 3 1");
		
		JPanel panelTipo = new JPanel();
		panelTipo.setBorder(new TitledBorder(null, "Tipologia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pane.add(panelTipo, "cell 0 2 3 1,grow");
		panelTipo.setLayout(new MigLayout("", "[82.00,grow][grow][grow,leading]", "[]"));
		
		txtTipo = new JTextField();
		panelTipo.add(txtTipo, "cell 0 0 3 1,growx");
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

	public JToggleButton getTglbtnDisponibili() {
		return tglbtnDisponibili;
	}
}
