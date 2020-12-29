package it.unisa.deliveryultra.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import it.unisa.deliveryultra.model.ImpiegoTableModel;
import it.unisa.deliveryultra.model.Rider;
import it.unisa.deliveryultra.model.ValutazioneTableModel;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTable;

public class RiderReportView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableValutazioni;
	private JTable tableSocieta;
	private ValutazioneTableModel modelValutazione;
	private ValutazioneTableModel modelValutazioniBasse;
	private JLabel lblTargaHeader;
	private JLabel lblDisponibilitaBody;
	private JLabel lblCognomeBody;
	private JLabel lblCfBody;
	private JLabel lblTipoAutoBody;
	private JLabel lblScoreMedioBody;
	private JLabel lblDataPrimaImpiegoBody;
	private JList<Rider> lstRider;
	private JLabel lblAutomunitoBody;
	private JLabel lblEmailBody;
	private JLabel lblTelefonoBody;
	private JLabel lblNumImpieghiBody;
	private JLabel lblNumValutazioniBody;
	private JLabel lblNomeBody;
	private JLabel lblTargaBody;
	private JLabel lblTipoAutoHeader;
	private ImpiegoTableModel modelImpiego;
	private JTable tableValutazioniBasse;

	public RiderReportView() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 860, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[190.00][105.00,grow]", "[][grow][grow][grow]"));
		
		JPanel panelRider = new JPanel();
		panelRider.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seleziona Rider", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelRider, "cell 0 0 1 4,grow");
		panelRider.setLayout(new MigLayout("", "[190.00]", "[grow][grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panelRider.add(scrollPane, "flowy,cell 0 0 1 2,growy");
		
		lstRider = new JList<>();
		scrollPane.setViewportView(lstRider);
		
		JPanel panelDatiRider = new JPanel();
		panelDatiRider.setBackground(Color.WHITE);
		panelDatiRider.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dati Rider", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelDatiRider, "cell 1 0,grow");
		panelDatiRider.setLayout(new MigLayout("", "[105.00:105.00][][grow][][grow]", "[][][][][][][]"));
		
		JLabel lblIcon = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(GestisciOrdiniView.class.getResource("/it/unisa/deliveryultra/icon/person-icon-6.png")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		lblIcon.setIcon(imageIcon);
		panelDatiRider.add(lblIcon, "cell 0 0 1 7,alignx center,growy");
		
		JLabel lblNomeHeader = new JLabel("Nome:");
		lblNomeHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblNomeHeader, "cell 1 0");
		
		lblNomeBody = new JLabel("Daniele");
		panelDatiRider.add(lblNomeBody, "cell 2 0");
		
		JLabel lblCognomeHeader = new JLabel("Cognome:");
		lblCognomeHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblCognomeHeader, "cell 3 0,alignx left");
		
		lblCognomeBody = new JLabel("Giaquinto");
		panelDatiRider.add(lblCognomeBody, "cell 4 0");
		
		JLabel lblCfHeader = new JLabel("Codice Fiscale:");
		lblCfHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblCfHeader, "cell 1 1");
		
		lblCfBody = new JLabel("GQNDNL79S25H703S");
		panelDatiRider.add(lblCfBody, "cell 2 1");
		
		JLabel lblTelefonoHeader = new JLabel("Telefono:");
		lblTelefonoHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblTelefonoHeader, "cell 1 2");
		
		lblTelefonoBody = new JLabel("+39089893080");
		panelDatiRider.add(lblTelefonoBody, "cell 2 2");
		
		JLabel lblEmailHeader = new JLabel("Email:");
		lblEmailHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblEmailHeader, "cell 3 2");
		
		lblEmailBody = new JLabel("d.giaquinto2@studenti.unisa.it");
		panelDatiRider.add(lblEmailBody, "cell 4 2");
		
		JLabel lblScoreMedioHeader = new JLabel("Score Medio:");
		lblScoreMedioHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblScoreMedioHeader, "cell 1 3");
		
		lblScoreMedioBody = new JLabel("4.68");
		panelDatiRider.add(lblScoreMedioBody, "cell 2 3");
		
		JLabel lblNumValutazioniHeader = new JLabel("Valutazioni:");
		lblNumValutazioniHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblNumValutazioniHeader, "cell 3 3");
		
		lblNumValutazioniBody = new JLabel("12");
		panelDatiRider.add(lblNumValutazioniBody, "cell 4 3");
		
		JLabel lblDataPrimaImpiegoHeader = new JLabel("Primo impiego:");
		lblDataPrimaImpiegoHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblDataPrimaImpiegoHeader, "cell 1 4");
		
		lblDataPrimaImpiegoBody = new JLabel("25/02/2012");
		panelDatiRider.add(lblDataPrimaImpiegoBody, "cell 2 4");
		
		JLabel lblNumImpieghiHeader = new JLabel("Impieghi:");
		lblNumImpieghiHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblNumImpieghiHeader, "cell 3 4");
		
		lblNumImpieghiBody = new JLabel("5");
		panelDatiRider.add(lblNumImpieghiBody, "cell 4 4");
		
		JLabel lblAutomunitoHeader = new JLabel("Automunito:");
		lblAutomunitoHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblAutomunitoHeader, "cell 1 5");
		
		lblAutomunitoBody = new JLabel("Si");
		panelDatiRider.add(lblAutomunitoBody, "cell 2 5");
		
		JLabel lblDisponibilitaHeader = new JLabel("Disponibile:");
		lblDisponibilitaHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblDisponibilitaHeader, "cell 3 5");
		
		lblDisponibilitaBody = new JLabel("Si");
		panelDatiRider.add(lblDisponibilitaBody, "cell 4 5");
		
		lblTargaHeader = new JLabel("Targa Auto:");
		lblTargaHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblTargaHeader, "cell 1 6");
		
		lblTargaBody = new JLabel("FH097MJ");
		panelDatiRider.add(lblTargaBody, "cell 2 6");
		
		lblTipoAutoHeader = new JLabel("Tipologia:");
		lblTipoAutoHeader.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelDatiRider.add(lblTipoAutoHeader, "cell 3 6");
		
		lblTipoAutoBody = new JLabel("Automobile");
		panelDatiRider.add(lblTipoAutoBody, "cell 4 6");
		
		JPanel panelValutazioni = new JPanel();
		panelValutazioni.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Valutazioni", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelValutazioni, "cell 1 1,grow");
		panelValutazioni.setLayout(new MigLayout("", "[105.00,grow][426.00]", "[grow]"));
		
		JScrollPane scrollPaneValutazioni = new JScrollPane();
		panelValutazioni.add(scrollPaneValutazioni, "cell 0 0 2 1,grow");
		
		modelValutazione = new ValutazioneTableModel();
		tableValutazioni = new JTable(modelValutazione);
		scrollPaneValutazioni.setViewportView(tableValutazioni);
		tableValutazioni.setShowGrid(false);
		tableValutazioni.setShowHorizontalLines(false);
		tableValutazioni.setShowVerticalLines(false);
		tableValutazioni.setRowMargin(0);
		tableValutazioni.setIntercellSpacing(new Dimension(0, 0));
		tableValutazioni.setFillsViewportHeight(true);
		TableRowSorter<ValutazioneTableModel> sorterValutazioni = new TableRowSorter<>(getModelValutazione());
		tableValutazioni.setRowSorter(sorterValutazioni);
		
		JPanel panelAziende = new JPanel();
		panelAziende.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Societa di delivery", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelAziende, "cell 1 2,grow");
		panelAziende.setLayout(new MigLayout("", "[105.00,grow]", "[grow]"));
		
		JScrollPane scrollPaneImpiegi = new JScrollPane();
		panelAziende.add(scrollPaneImpiegi, "cell 0 0,grow");
		
		modelImpiego = new ImpiegoTableModel();
		tableSocieta = new JTable(modelImpiego);
		scrollPaneImpiegi.setViewportView(tableSocieta);
		tableSocieta.setShowGrid(false);
		tableSocieta.setShowHorizontalLines(false);
		tableSocieta.setShowVerticalLines(false);
		tableSocieta.setRowMargin(0);
		tableSocieta.setIntercellSpacing(new Dimension(0, 0));
		tableSocieta.setFillsViewportHeight(true);
		TableRowSorter<ImpiegoTableModel> sorterImpieghi = new TableRowSorter<>(getModelImpiego());
		tableSocieta.setRowSorter(sorterImpieghi);
		
		JPanel panelValutazioniBasse = new JPanel();
		panelValutazioniBasse.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Valutazioni minori dello score medio", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panelValutazioniBasse, "cell 1 3,grow");
		panelValutazioniBasse.setLayout(new MigLayout("", "[105.00,grow]", "[grow]"));
		
		JScrollPane scrollPanelValutazioniBasse = new JScrollPane();
		panelValutazioniBasse.add(scrollPanelValutazioniBasse, "cell 0 0,grow");
		
		modelValutazioniBasse = new ValutazioneTableModel();
		tableValutazioniBasse = new JTable(modelValutazioniBasse);
		scrollPanelValutazioniBasse.setViewportView(tableValutazioniBasse);
		tableValutazioniBasse.setShowGrid(false);
		tableValutazioniBasse.setShowHorizontalLines(false);
		tableValutazioniBasse.setShowVerticalLines(false);
		tableValutazioniBasse.setRowMargin(0);
		tableValutazioniBasse.setIntercellSpacing(new Dimension(0, 0));
		tableValutazioniBasse.setFillsViewportHeight(true);
		TableRowSorter<ValutazioneTableModel> sorterValutazioniBasse = new TableRowSorter<>(modelValutazioniBasse);
		tableValutazioniBasse.setRowSorter(sorterValutazioniBasse);
	}

	public JLabel getLblTargaHeader() {
		return lblTargaHeader;
	}
	public JLabel getLblDisponibilitaBody() {
		return lblDisponibilitaBody;
	}
	public JLabel getLblCognomeBody() {
		return lblCognomeBody;
	}
	public JLabel getLblCfBody() {
		return lblCfBody;
	}
	public JLabel getLblTipoAutoBody() {
		return lblTipoAutoBody;
	}
	public JLabel getLblScoreMedioBody() {
		return lblScoreMedioBody;
	}
	public JLabel getLblDataPrimaImpiegoBody() {
		return lblDataPrimaImpiegoBody;
	}
	public JList<Rider> getLstRider() {
		return lstRider;
	}
	public JLabel getLblAutomunitoBody() {
		return lblAutomunitoBody;
	}
	public JLabel getLblEmailBody() {
		return lblEmailBody;
	}
	public JLabel getLblTelefonoBody() {
		return lblTelefonoBody;
	}
	public JLabel getLblNumImpieghiBody() {
		return lblNumImpieghiBody;
	}
	public JLabel getLblNumValutazioniBody() {
		return lblNumValutazioniBody;
	}
	public JLabel getLblNomeBody() {
		return lblNomeBody;
	}
	public JLabel getLblTargaBody() {
		return lblTargaBody;
	}
	public JLabel getLblTipoAutoHeader() {
		return lblTipoAutoHeader;
	}
	public JTable getTableValutazioni() {
		return tableValutazioni;
	}
	public JTable getTableSocieta() {
		return tableSocieta;
	}

	public ImpiegoTableModel getModelImpiego() {
		return modelImpiego;
	}

	public ValutazioneTableModel getModelValutazione() {
		return modelValutazione;
	}
	public ValutazioneTableModel getModelValutazioniBasse() {
		return modelValutazioniBasse;
	}
}
