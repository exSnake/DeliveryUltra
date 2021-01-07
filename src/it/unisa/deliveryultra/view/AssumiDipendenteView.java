package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import it.unisa.deliveryultra.model.Delivery;
import it.unisa.deliveryultra.model.Ristorante;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;

public class AssumiDipendenteView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JTextField txtCf;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtCurriculum;
	private JButton btnInserisci;
	private JComboBox<Ristorante> cmbRistoranti;
	private JButton btnAnnulla;
	private JSpinner spinAnniEsperienza;
	private JLabel lblErrors;
	private JLabel lblDescrizione;
	private JTextField txtDescrizione;
	private JLabel lblCadenza;
	private JTextField txtCadenza;
	private JPanel panelDatiAssunzione;
	private JPanel panelNuovoDelivery;
	private JComboBox<Delivery> cmbDelivery;
	private JLabel lblDelivery;
	private JLabel lblExDescrizione;
	private JLabel lblExCadenza;
	private JTextField txtExDescrizione;
	private JTextField txtExCadenza;
	private JPanel panelDeliveryInterno;
	private JButton btnResetDelivery;


	/**
	 * Create the frame.
	 */
	public AssumiDipendenteView() {
		setTitle("Admin - Creazione Dipendente Ristorante");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow,leading]", "[grow][][grow,center][][grow][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dati Dipendente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel, "cell 1 1,grow");
		panel.setLayout(new MigLayout("", "[80.00][364.00,grow]", "[grow,center][grow][grow,center][grow,center][grow][grow,center][grow,center]"));
		
		JLabel lblNome = new JLabel("Nome");
		panel.add(lblNome, "cell 0 0,alignx left");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtNome = new JTextField();
		panel.add(txtNome, "cell 1 0,growx");
		txtNome.setColumns(10);
		
		JLabel lblCognome = new JLabel("Cognome");
		panel.add(lblCognome, "cell 0 1,alignx left");
		
		txtCognome = new JTextField();
		panel.add(txtCognome, "cell 1 1,growx");
		txtCognome.setColumns(10);
		
		JLabel lblCf = new JLabel("CF");
		panel.add(lblCf, "cell 0 2,alignx left");
		lblCf.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtCf = new JTextField();
		panel.add(txtCf, "cell 1 2,growx");
		txtCf.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		panel.add(lblTelefono, "cell 0 3,alignx left");
		lblTelefono.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtTelefono = new JTextField();
		panel.add(txtTelefono, "cell 1 3,growx");
		txtTelefono.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		panel.add(lblEmail, "cell 0 4,alignx left");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtEmail = new JTextField();
		panel.add(txtEmail, "cell 1 4,growx");
		txtEmail.setColumns(10);
		
		JLabel lblCurriculum = new JLabel("Curriculum");
		panel.add(lblCurriculum, "cell 0 5,alignx left");
		lblCurriculum.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtCurriculum = new JTextField();
		panel.add(txtCurriculum, "cell 1 5,growx");
		txtCurriculum.setColumns(10);
		
		JLabel lblAnnIEsperienza = new JLabel("Anni Esperienza");
		panel.add(lblAnnIEsperienza, "cell 0 6,alignx left");
		lblAnnIEsperienza.setHorizontalAlignment(SwingConstants.LEFT);
		
		spinAnniEsperienza = new JSpinner();
		panel.add(spinAnniEsperienza, "cell 1 6,growx");
		
		panelDatiAssunzione = new JPanel();
		panelDatiAssunzione.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dati Assunzione", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelDatiAssunzione, "cell 1 3,grow");
		panelDatiAssunzione.setLayout(new MigLayout("", "[90.00][427.00,grow]", "[grow][][][]"));
		
		JLabel lblRistorante = new JLabel("Ristorante");
		panelDatiAssunzione.add(lblRistorante, "cell 0 0,alignx left");
		
		cmbRistoranti = new JComboBox<>();
		panelDatiAssunzione.add(cmbRistoranti, "cell 1 0,grow");
		
		panelDeliveryInterno = new JPanel();
		panelDeliveryInterno.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Delivery Interno Esistente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDatiAssunzione.add(panelDeliveryInterno, "cell 0 1 2 1,grow");
		panelDeliveryInterno.setLayout(new MigLayout("", "[90.00][427.00,grow]", "[][][]"));
		
		lblDelivery = new JLabel("Codice");
		lblDelivery.setEnabled(false);
		panelDeliveryInterno.add(lblDelivery, "cell 0 0,alignx trailing");
		
		cmbDelivery = new JComboBox<>();
		cmbDelivery.setEnabled(false);
		panelDeliveryInterno.add(cmbDelivery, "flowx,cell 1 0,growx");
		
		lblExDescrizione = new JLabel("Descrizione");
		panelDeliveryInterno.add(lblExDescrizione, "cell 0 1,alignx right");
		
		txtExDescrizione = new JTextField();
		txtExDescrizione.setEditable(false);
		txtExDescrizione.setEnabled(true);
		panelDeliveryInterno.add(txtExDescrizione, "cell 1 1,growx");
		txtExDescrizione.setText("");
		txtExDescrizione.setColumns(10);
		
		lblExCadenza = new JLabel("Cadenza");
		panelDeliveryInterno.add(lblExCadenza, "cell 0 2,alignx trailing");
		
		txtExCadenza = new JTextField();
		txtExCadenza.setEditable(false);
		panelDeliveryInterno.add(txtExCadenza, "cell 1 2,growx");
		txtExCadenza.setColumns(10);
		
		btnResetDelivery = new JButton("R");
		btnResetDelivery.setEnabled(false);
		panelDeliveryInterno.add(btnResetDelivery, "cell 1 0,alignx center,aligny center");
		
		panelNuovoDelivery = new JPanel();
		panelNuovoDelivery.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nuovo delivery interno", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDatiAssunzione.add(panelNuovoDelivery, "cell 0 2 2 1,grow");
		panelNuovoDelivery.setLayout(new MigLayout("", "[90.00][427.00]", "[][]"));
		
		lblDescrizione = new JLabel("Descrizione");
		panelNuovoDelivery.add(lblDescrizione, "cell 0 0,alignx left");
		
		txtDescrizione = new JTextField();
		panelNuovoDelivery.add(txtDescrizione, "cell 1 0,growx");
		txtDescrizione.setColumns(10);
		
		lblCadenza = new JLabel("Cadenza");
		panelNuovoDelivery.add(lblCadenza, "cell 0 1,alignx left");
		
		txtCadenza = new JTextField();
		panelNuovoDelivery.add(txtCadenza, "cell 1 1,growx");
		txtCadenza.setColumns(10);
		
		btnAnnulla = new JButton("Annulla");
		btnAnnulla.addActionListener(e -> closeThis());
		panelDatiAssunzione.add(btnAnnulla, "flowx,cell 1 3,alignx right");
		
		btnInserisci = new JButton("Inserisci");
		panelDatiAssunzione.add(btnInserisci, "cell 1 3,alignx right");
		
		lblErrors = new JLabel("");
		lblErrors.setForeground(new Color(153, 0, 0));
		lblErrors.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblErrors, "flowx,cell 1 5");
	}

	private void closeThis() {
		this.dispose();
	}

	public JTextField getTxtNome() {
		return txtNome;
	}
	public JTextField getTxtCurriculum() {
		return txtCurriculum;
	}
	public JButton getBtnInserisci() {
		return btnInserisci;
	}
	public JTextField getTxtCf() {
		return txtCf;
	}
	public JComboBox<Ristorante> getCmbRistoranti() {
		return cmbRistoranti;
	}
	public JButton getBtnAnnulla() {
		return btnAnnulla;
	}
	public JSpinner getSpinAnniEsperienza() {
		return spinAnniEsperienza;
	}
	public JTextField getTxtTelefono() {
		return txtTelefono;
	}
	public JTextField getTxtEmail() {
		return txtEmail;
	}
	public JTextField getTxtCognome() {
		return txtCognome;
	}
	public JLabel getLblErrors() {
		return lblErrors;
	}
	public JTextField getTxtCadenza() {
		return txtCadenza;
	}
	public JTextField getTxtDescrizione() {
		return txtDescrizione;
	}
	public JTextField getTxtExDescrizione() {
		return txtExDescrizione;
	}
	public JLabel getLblExDescrizione() {
		return lblExDescrizione;
	}
	public JLabel getLblExCadenza() {
		return lblExCadenza;
	}
	public JTextField getTxtExCadenza() {
		return txtExCadenza;
	}
	public JComboBox<Delivery> getCmbDelivery() {
		return cmbDelivery;
	}
	public JLabel getLblDelivery() {
		return lblDelivery;
	}
	public JButton getBtnResetDelivery() {
		return btnResetDelivery;
	}
}
