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
	private JPanel panel_1;


	/**
	 * Create the frame.
	 */
	public AssumiDipendenteView() {
		setTitle("Admin - Creazione Dipendente Ristorante");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 440);
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
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dati Assunzione", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel_1, "cell 1 3,grow");
		panel_1.setLayout(new MigLayout("", "[90.00][427.00]", "[grow][][][]"));
		
		JLabel lblRistorante = new JLabel("Ristorante");
		panel_1.add(lblRistorante, "cell 0 0,alignx left");
		
		cmbRistoranti = new JComboBox<>();
		panel_1.add(cmbRistoranti, "cell 1 0,grow");
		
		lblDescrizione = new JLabel("Descrizione");
		panel_1.add(lblDescrizione, "cell 0 1,alignx left");
		
		txtDescrizione = new JTextField();
		panel_1.add(txtDescrizione, "cell 1 1,growx");
		txtDescrizione.setColumns(10);
		
		lblCadenza = new JLabel("Cadenza");
		panel_1.add(lblCadenza, "cell 0 2,alignx left");
		
		txtCadenza = new JTextField();
		panel_1.add(txtCadenza, "cell 1 2,growx");
		txtCadenza.setColumns(10);
		
		btnAnnulla = new JButton("Annulla");
		btnAnnulla.addActionListener(e -> closeThis());
		panel_1.add(btnAnnulla, "flowx,cell 1 3,alignx right");
		
		btnInserisci = new JButton("Inserisci");
		panel_1.add(btnInserisci, "cell 1 3,alignx right");
		
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
}
