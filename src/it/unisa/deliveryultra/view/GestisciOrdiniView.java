package it.unisa.deliveryultra.view;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import it.unisa.deliveryultra.model.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class GestisciOrdiniView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCerca;
	private JComboBox<Ristorante> cmbRistoranti;
	private JButton btnAssegna;
	private JButton btnElimina;
	private JButton btnConsegna;
	private JButton btnAccetta;
	private JButton btnInCoda;
	private JPanel panel;
	private JLabel lblDenominazioneHeader;
	private JLabel lblRagioneSocialeHeader;
	private JLabel lblDenominazioneBody;
	private JLabel lblTipologiaHeader;
	private JLabel lblRagioneSocialeBody;
	private JLabel lblTelefonoHeader;
	private JLabel lblTipologiaBody;
	private JLabel lblPivaHeader;
	private JLabel lblPivaBody;
	private JLabel lblTelefonoBody;
	private JLabel lblEmailHeader;
	private JLabel lblEmailBody;
	private JLabel lblIndirizzoHeader;
	private JLabel lblIndirizzoBody;
	private JLabel lblOrdiniHeader;
	private JLabel lblOrdiniBody;
	private JLabel lblCodaMaxHeader;
	private JLabel lblCodaMaxBody;
	private JLabel lblIconRisto;
	private JLabel lblVisualizzazioneOrdini;
	private JPanel panelTable;
	private JTable table;
	private OrdineTableModel model;
	private JScrollPane scrollPane;


	public GestisciOrdiniView() {
		setTitle("Ristoranti - Gestione Ordini");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 768, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[82.00,grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]", "[][][30.00][30.00][30.00][][grow][]"));
		
		JLabel lblRistorante = new JLabel("Seleziona un ristorante");
		contentPane.add(lblRistorante, "cell 0 0 2 1");
		
		cmbRistoranti = new JComboBox<>();
		contentPane.add(cmbRistoranti, "cell 0 1 3 1,growx");
		
		btnCerca = new JButton("Cerca");
		contentPane.add(btnCerca, "cell 3 1,grow");
		
		btnInCoda = new JButton("In coda");
		contentPane.add(btnInCoda, "cell 4 1,grow");
		
		
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, "cell 0 2 5 3,grow");
		panel.setLayout(new MigLayout("", "[90.00][][210.00][][grow]", "[18.00][18.00][18.00][18.00][18.00]"));
		
		lblDenominazioneHeader = new JLabel("Denominazione:");
		lblDenominazioneHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblDenominazioneHeader, "cell 1 0");
		
		lblDenominazioneBody = new JLabel("Ristorante Giapponese Da Patrizio");
		panel.add(lblDenominazioneBody, "cell 2 0");
		
		lblTipologiaHeader = new JLabel("Tipologia:");
		lblTipologiaHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblTipologiaHeader, "cell 3 0");
		
		lblTipologiaBody = new JLabel("Braceria");
		panel.add(lblTipologiaBody, "cell 4 0");
		
		lblRagioneSocialeHeader = new JLabel("Ragione Sociale:");
		lblRagioneSocialeHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblRagioneSocialeHeader, "cell 1 1");
		
		lblRagioneSocialeBody = new JLabel("Martini, Pagano e Guerra e figli");
		panel.add(lblRagioneSocialeBody, "cell 2 1");
		
		lblPivaHeader = new JLabel("PIva:");
		lblPivaHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblPivaHeader, "cell 3 1");
		
		lblPivaBody = new JLabel("IT99595890528");
		panel.add(lblPivaBody, "cell 4 1");
		
		lblIconRisto = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(GestisciOrdiniView.class.getResource("/it/unisa/deliveryultra/icon/snapshot-202012261000047.jpg")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		lblIconRisto.setIcon(imageIcon);
		panel.add(lblIconRisto, "cell 0 0 1 5");
		
		lblTelefonoHeader = new JLabel("Telefono:");
		lblTelefonoHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblTelefonoHeader, "cell 1 2");
		
		lblTelefonoBody = new JLabel("+390898944352");
		panel.add(lblTelefonoBody, "cell 2 2");
		
		lblEmailHeader = new JLabel("Email:");
		lblEmailHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblEmailHeader, "cell 3 2");
		
		lblEmailBody = new JLabel("antonio.deangelis@email.it");
		panel.add(lblEmailBody, "cell 4 2");
		
		lblIndirizzoHeader = new JLabel("Indirizzo:");
		lblIndirizzoHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblIndirizzoHeader, "cell 1 3");
		
		lblIndirizzoBody = new JLabel("Corso Armando Diaz, 128 - 84085 Mercato San Severino (SA)");
		panel.add(lblIndirizzoBody, "cell 2 3 3 1");
		
		lblOrdiniHeader = new JLabel("Ordini in Coda:");
		lblOrdiniHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblOrdiniHeader, "cell 1 4");
		
		lblOrdiniBody = new JLabel("12");
		panel.add(lblOrdiniBody, "cell 2 4");
		
		lblCodaMaxHeader = new JLabel("Coda Max:");
		lblCodaMaxHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblCodaMaxHeader, "cell 3 4");
		
		lblCodaMaxBody = new JLabel("20");
		panel.add(lblCodaMaxBody, "cell 4 4");
		
		lblVisualizzazioneOrdini = new JLabel("Visualizzazione Ordini:");
		contentPane.add(lblVisualizzazioneOrdini, "cell 0 5 2 1");
		
		panelTable = new JPanel();
		contentPane.add(panelTable, "cell 0 6 5 1,grow");
		panelTable.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		model = new OrdineTableModel();
		
		scrollPane = new JScrollPane();
		panelTable.add(scrollPane, "flowx,cell 0 1,grow");
		table = new JTable(model);
		scrollPane.setViewportView(table);
		getTable().setShowGrid(false);
		getTable().setShowHorizontalLines(false);
		getTable().setShowVerticalLines(false);
		getTable().setRowMargin(0);
		getTable().setIntercellSpacing(new Dimension(0, 0));
		getTable().setFillsViewportHeight(true);
		TableRowSorter<OrdineTableModel> sorter = new TableRowSorter<>(model);
		getTable().setRowSorter(sorter);
		
		btnElimina = new JButton("Elimina");
		btnElimina.setEnabled(false);
		contentPane.add(btnElimina, "cell 1 7");
		
		btnAccetta = new JButton("Accetta");
		btnAccetta.setEnabled(false);
		contentPane.add(btnAccetta, "cell 2 7");
		
		btnAssegna = new JButton("Assegna");
		btnAssegna.setEnabled(false);
		contentPane.add(btnAssegna, "cell 3 7");
		
		btnConsegna = new JButton("Consegna");
		btnConsegna.setEnabled(false);
		contentPane.add(btnConsegna, "cell 4 7");
	}

	public JButton getBtnCerca() {
		return btnCerca;
	}
	public JComboBox<Ristorante> getCmbRistoranti() {
		return cmbRistoranti;
	}
	public JButton getBtnAssegna() {
		return btnAssegna;
	}
	public JButton getBtnElimina() {
		return btnElimina;
	}
	public JButton getBtnConsegna() {
		return btnConsegna;
	}
	public JButton getBtnAccetta() {
		return btnAccetta;
	}
	public JButton getBtnInCoda() {
		return btnInCoda;
	}
	public JLabel getLblEmailBody() {
		return lblEmailBody;
	}
	public JLabel getLblCodaMaxBody() {
		return lblCodaMaxBody;
	}
	public JLabel getLblTipologiaBody() {
		return lblTipologiaBody;
	}
	public JLabel getLblRagioneSocialeBody() {
		return lblRagioneSocialeBody;
	}
	public JLabel getLblOrdiniBody() {
		return lblOrdiniBody;
	}
	public JLabel getLblDenominazioneBody() {
		return lblDenominazioneBody;
	}
	public JLabel getLblPivaBody() {
		return lblPivaBody;
	}
	public JLabel getLblTelefonoBody() {
		return lblTelefonoBody;
	}
	public JLabel getLblIndirizzoBody() {
		return lblIndirizzoBody;
	}
	public JTable getTable() {
		return table;
	}

	public OrdineTableModel getModel() {
		return model;
	}

	public void setModel(OrdineTableModel model) {
		this.model = model;
	}
}
