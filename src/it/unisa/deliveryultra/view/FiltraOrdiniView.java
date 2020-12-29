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

public class FiltraOrdiniView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCerca;
	private JComboBox<Cliente> cmbCliente;
	private JButton btnElimina;
	private JPanel panel;
	private JLabel lblNomeHeader;
	private JLabel lblCognomeHeader;
	private JLabel lblNomeBody;
	private JLabel lblCognomeBody;
	private JLabel lblTelefonoHeader;
	private JLabel lblTelefonoBody;
	private JLabel lblEmailHeader;
	private JLabel lblEmailBody;
	private JLabel lblDataRegHeader;
	private JLabel lblDataRegBody;
	private JLabel lblOrdiniHeader;
	private JLabel lblOrdiniBody;
	private JLabel lblValutazioniHeader;
	private JLabel lblValutazioniBody;
	private JLabel lblIconRisto;
	private JLabel lblVisualizzazioneOrdini;
	private JPanel panelTable;
	private JTable table;
	private OrdineTableModel model;
	private JScrollPane scrollPane;
	private JButton btnOrdiniValutabili;

	public FiltraOrdiniView() {
		setTitle("Clienti - Gestione Ordini");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 768, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[82.00,grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]", "[][][][][30.00][30.00][30.00][][grow][]"));
		
		JLabel lblCliente = new JLabel("Seleziona un cliente");
		contentPane.add(lblCliente, "cell 0 0 2 1");
		
		cmbCliente = new JComboBox<>();
		contentPane.add(cmbCliente, "cell 0 1 3 1,growx");
		
		btnCerca = new JButton("Cerca");
		contentPane.add(btnCerca, "cell 3 1,grow");
		
		btnOrdiniValutabili = new JButton("Ordini Valutabili");
		contentPane.add(btnOrdiniValutabili, "cell 4 1");
		
		
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, "cell 0 4 5 3,grow");
		panel.setLayout(new MigLayout("", "[90.00][][210.00][][grow]", "[18.00][18.00][18.00][18.00][18.00]"));
		
		lblNomeHeader = new JLabel("Nome");
		lblNomeHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNomeHeader, "cell 1 0");
		
		lblNomeBody = new JLabel("Ristorante Giapponese Da Patrizio");
		panel.add(lblNomeBody, "cell 2 0");
		
		lblCognomeHeader = new JLabel("Cognome");
		lblCognomeHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblCognomeHeader, "cell 1 1");
		
		lblCognomeBody = new JLabel("Martini, Pagano e Guerra e figli");
		panel.add(lblCognomeBody, "cell 2 1");
		
		lblIconRisto = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(FiltraOrdiniView.class.getResource("/it/unisa/deliveryultra/icon/person-icon-6.png")); // load the image to a imageIcon
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
		
		lblDataRegHeader = new JLabel("Data Registrazione:");
		lblDataRegHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblDataRegHeader, "cell 1 3");
		
		lblDataRegBody = new JLabel("");
		panel.add(lblDataRegBody, "cell 2 3 3 1");
		
		lblOrdiniHeader = new JLabel("Ordini effettuati:");
		lblOrdiniHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblOrdiniHeader, "cell 1 4");
		
		lblOrdiniBody = new JLabel("12");
		panel.add(lblOrdiniBody, "cell 2 4");
		
		lblValutazioniHeader = new JLabel("Valutazioni Effettuate:");
		lblValutazioniHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblValutazioniHeader, "cell 3 4");
		
		lblValutazioniBody = new JLabel("20");
		panel.add(lblValutazioniBody, "cell 4 4");
		
		lblVisualizzazioneOrdini = new JLabel("Visualizzazione Ordini:");
		contentPane.add(lblVisualizzazioneOrdini, "cell 0 7 2 1");
		
		panelTable = new JPanel();
		contentPane.add(panelTable, "cell 0 8 5 1,grow");
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
		contentPane.add(btnElimina, "cell 4 9");
	}

	public JButton getBtnCerca() {
		return btnCerca;
	}
	public JComboBox<Cliente> getCmbClienti() {
		return cmbCliente;
	}
	public JButton getBtnElimina() {
		return btnElimina;
	}
	public JLabel getLblEmailBody() {
		return lblEmailBody;
	}
	public JLabel getLblValutazioniBody() {
		return lblValutazioniBody;
	}
	public JLabel getLblCognomeBody() {
		return lblCognomeBody;
	}
	public JLabel getLblOrdiniBody() {
		return lblOrdiniBody;
	}
	public JLabel getLblNomeBody() {
		return lblNomeBody;
	}
	public JLabel getLblTelefonoBody() {
		return lblTelefonoBody;
	}
	public JLabel getLblDataRegBody() {
		return lblDataRegBody;
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
	public JButton getBtnOrdiniValutabili() {
		return btnOrdiniValutabili;
	}
	public JLabel getLblVisualizzazioneOrdini() {
		return lblVisualizzazioneOrdini;
	}
}
