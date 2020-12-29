package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnInserisciOrdine;
	private JButton btnGestisciOrdini;
	private JButton btnFiltraOrdini;
	private JButton btnCovidTracking;
	private JButton btnValutaRider;
	private JButton btnRiderReport;
	private JButton btnEs9;
	private JButton btnAssumiDipendente;
	private JLabel lblNewLabel;
	private JPanel panel;

	public MainView() {
		setTitle("DeliveryUltra - Pagina Principale");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[61.00][]"));
		
		lblNewLabel = new JLabel("DELIVERY ULTRA");
		lblNewLabel.setBackground(new Color(51, 153, 255));
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 40));
		contentPane.add(lblNewLabel, "cell 0 0,alignx center,aligny center");
		
		panel = new JPanel();
		panel.setBackground(new Color(204, 255, 204));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seleziona un'operazione", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel, "cell 0 1,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][][]"));
		
		btnGestisciOrdini = new JButton("Gestisci Ordini");
		btnGestisciOrdini.setBackground(new Color(255, 255, 204));
		btnGestisciOrdini.setForeground(new Color(0, 0, 51));
		panel.add(btnGestisciOrdini, "cell 0 0,growx");
		
		setBtnInserisciOrdine(new JButton("Inserisci Ordine"));
		
		btnFiltraOrdini = new JButton("Filtra Ordini");
		btnFiltraOrdini.setBackground(new Color(255, 255, 204));
		btnFiltraOrdini.setForeground(new Color(0, 0, 51));
		panel.add(btnFiltraOrdini, "cell 0 1,grow");
		
		btnCovidTracking = new JButton("Covid Tracking");
		btnCovidTracking.setBackground(new Color(255, 255, 204));
		btnCovidTracking.setForeground(new Color(0, 0, 51));
		panel.add(btnCovidTracking, "cell 1 1,grow");
		
		btnValutaRider = new JButton("Valuta Rider");
		btnValutaRider.setBackground(new Color(255, 255, 204));
		btnValutaRider.setForeground(new Color(0, 0, 51));
		panel.add(btnValutaRider, "cell 0 2,grow");
		
		btnRiderReport = new JButton("Report Riders");
		btnRiderReport.setBackground(new Color(255, 255, 204));
		btnRiderReport.setForeground(new Color(0, 0, 51));
		panel.add(btnRiderReport, "cell 1 2,grow");
		
		btnEs9 = new JButton("Punto 9");
		btnEs9.setBackground(new Color(255, 255, 204));
		btnEs9.setForeground(new Color(0, 0, 51));
		panel.add(btnEs9, "cell 0 3,grow");
		
		btnAssumiDipendente = new JButton("Assumi Dipendente");
		btnAssumiDipendente.setBackground(new Color(255, 255, 204));
		btnAssumiDipendente.setForeground(new Color(0, 0, 51));
		panel.add(btnAssumiDipendente, "cell 1 3,grow");
	}

	public JButton getBtnInserisciOrdine() {
		return btnInserisciOrdine;
	}

	public void setBtnInserisciOrdine(JButton btnInserisciOrdine) {
		this.btnInserisciOrdine = btnInserisciOrdine;
		btnInserisciOrdine.setForeground(new Color(0, 0, 51));
		btnInserisciOrdine.setBackground(new Color(255, 255, 204));
		panel.add(btnInserisciOrdine, "cell 1 0,growx");
	}
	public JButton getBtnGestisciOrdini() {
		return btnGestisciOrdini;
	}
	public JButton getBtnFiltraOrdini() {
		return btnFiltraOrdini;
	}
	public JButton getBtnCovidTracking() {
		return btnCovidTracking;
	}
	public JButton getBtnValutaRider() {
		return btnValutaRider;
	}
	public JButton getBtnRiderReport() {
		return btnRiderReport;
	}
	public JButton getBtnEs9() {
		return btnEs9;
	}
	public JButton getBtnAssumiDipendente() {
		return btnAssumiDipendente;
	}
}
