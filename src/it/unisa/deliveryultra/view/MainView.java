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
import javax.swing.SwingConstants;

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
	private JButton btnGestisciRistoranti;
	private JButton btnPunto1;
	private JButton btnPunto2;
	private JButton btnPunto3;
	private JButton btnPunto4;
	private JButton btnPunto5;
	private JButton btnPunto6;
	private JButton btnPunto7;
	private JButton btnPunto8;
	private JButton btnPunto9;
	private JButton btnPunto10;
	private JButton btnPunto11;
	private JButton btnPunto12;
	private JButton btnPunto13;
	private JButton btnPunto14;
	private JButton btnPunto15;
	private JPanel panel_1;

	public MainView() {
		setTitle("DeliveryUltra - Pagina Principale");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 853);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[61.00][][]"));
		
		lblNewLabel = new JLabel("DELIVERY ULTRA");
		lblNewLabel.setBackground(new Color(51, 153, 255));
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 40));
		contentPane.add(lblNewLabel, "cell 0 0,alignx center,aligny center");
		
		panel = new JPanel();
		panel.setBackground(new Color(204, 255, 255));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seleziona un'operazione", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel, "cell 0 1,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[][][][][]"));
		
		btnGestisciOrdini = new JButton("Gestisci Ordini");
		btnGestisciOrdini.setBackground(new Color(255, 255, 204));
		btnGestisciOrdini.setForeground(new Color(0, 0, 51));
		panel.add(btnGestisciOrdini, "cell 0 0,growx");
		
		setBtnInserisciOrdine(new JButton("Inserisci Ordine"));
		
		btnGestisciRistoranti = new JButton("Gestisci Ristoranti");
		panel.add(btnGestisciRistoranti, "cell 0 1 2 1,growx");
		
		btnFiltraOrdini = new JButton("Filtra Ordini");
		btnFiltraOrdini.setBackground(new Color(255, 255, 204));
		btnFiltraOrdini.setForeground(new Color(0, 0, 51));
		panel.add(btnFiltraOrdini, "cell 0 2,grow");
		
		btnCovidTracking = new JButton("Covid Tracking");
		btnCovidTracking.setBackground(new Color(255, 255, 204));
		btnCovidTracking.setForeground(new Color(0, 0, 51));
		panel.add(btnCovidTracking, "cell 1 2,grow");
		
		btnValutaRider = new JButton("Valuta Rider");
		btnValutaRider.setBackground(new Color(255, 255, 204));
		btnValutaRider.setForeground(new Color(0, 0, 51));
		panel.add(btnValutaRider, "cell 0 3,grow");
		
		btnRiderReport = new JButton("Report Riders");
		btnRiderReport.setBackground(new Color(255, 255, 204));
		btnRiderReport.setForeground(new Color(0, 0, 51));
		panel.add(btnRiderReport, "cell 1 3,grow");
		
		btnEs9 = new JButton("Punto 9");
		btnEs9.setBackground(new Color(255, 255, 204));
		btnEs9.setForeground(new Color(0, 0, 51));
		panel.add(btnEs9, "cell 0 4,grow");
		
		btnAssumiDipendente = new JButton("Assumi Dipendente");
		btnAssumiDipendente.setBackground(new Color(255, 255, 204));
		btnAssumiDipendente.setForeground(new Color(0, 0, 51));
		panel.add(btnAssumiDipendente, "cell 1 4,grow");
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 255, 255));
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Fase 3", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][][][][][][][]"));
		
		btnPunto1 = new JButton("1. Registrazione di un Ordine");
		panel_1.add(btnPunto1, "cell 0 0,grow");
		btnPunto1.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto2 = new JButton("2. Consegna di un ordine");
		panel_1.add(btnPunto2, "cell 0 1,growx");
		btnPunto2.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto3 = new JButton("3. Verifica della possibilit\u00E0 di effettuare un ordine ad un determinato ristorante");
		panel_1.add(btnPunto3, "cell 0 2,growx");
		btnPunto3.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto4 = new JButton("4. Visualizzazione dei ristoranti disponibili all\u2019accettazione di un ordine");
		panel_1.add(btnPunto4, "cell 0 3,growx");
		btnPunto4.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto5 = new JButton("5. Valutazione di un rider");
		panel_1.add(btnPunto5, "cell 0 4,growx");
		btnPunto5.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto6 = new JButton("6. Visualizzazione per ogni cliente del numero di ordini effettuati");
		panel_1.add(btnPunto6, "cell 0 5,growx");
		btnPunto6.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto7 = new JButton("7. Abilitazione dell\u2019affidamento ad una societ\u00E0 di un servizio di delivery (gi\u00E0 presente)");
		panel_1.add(btnPunto7, "cell 0 6,growx");
		btnPunto7.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto8 = new JButton("8. Assunzione di un nuovo dipendente per la consegna degli ordini");
		panel_1.add(btnPunto8, "cell 0 7,growx");
		btnPunto8.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto9 = new JButton("9. Visualizzazione dei nomi dei ristoranti che impiegano dipendenti propri per la consegna o che si affidano ai servizi della societ\u00E0 \u00ABFood Delivery\u00BB");
		panel_1.add(btnPunto9, "cell 0 8,growx");
		btnPunto9.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto10 = new JButton("10. Visualizzazione degli ordini consegnati da Raider ancora non valutati");
		panel_1.add(btnPunto10, "cell 0 9,growx");
		btnPunto10.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto11 = new JButton("11. Cancellazione di un ordine ancora non consegnato");
		panel_1.add(btnPunto11, "cell 0 10,growx");
		btnPunto11.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto12 = new JButton("12. Stampa di tutte le persone (nome, cognome) che abbiano consegnato ordini a \u00ABGiuseppe Verdi\u00BB");
		panel_1.add(btnPunto12, "cell 0 11,growx");
		btnPunto12.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto13 = new JButton("13. Stampa di un report che mostri i dati dei ristoranti, incluso la coda di ordini attuale");
		panel_1.add(btnPunto13, "cell 0 12,growx");
		btnPunto13.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto14 = new JButton("14. Stampa settimanale di un report che mostri i dati dei rider, incluso lo score medio ottenuto nelle valutazioni da parte dei clienti");
		panel_1.add(btnPunto14, "cell 0 13,growx");
		btnPunto14.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnPunto15 = new JButton("15. Stampa settimanale di tutti i clienti che nell\u2019ultima settimana abbiano effettuato almeno una valutazione inferiore allo score medio di un raider");
		panel_1.add(btnPunto15, "cell 0 14,growx");
		btnPunto15.setHorizontalAlignment(SwingConstants.LEFT);
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
	public JButton getBtnGestisciRistoranti() {
		return btnGestisciRistoranti;
	}
	public JButton getBtnPunto3() {
		return btnPunto3;
	}
	public JButton getBtnPunto5() {
		return btnPunto5;
	}
	public JButton getBtnPunto6() {
		return btnPunto6;
	}
	public JButton getBtnPunto13() {
		return btnPunto13;
	}
	public JButton getBtnPunto2() {
		return btnPunto2;
	}
	public JButton getBtnPunto15() {
		return btnPunto15;
	}
	public JButton getBtnPunto1() {
		return btnPunto1;
	}
	public JButton getBtnPunto10() {
		return btnPunto10;
	}
	public JButton getBtnPunto11() {
		return btnPunto11;
	}
	public JButton getBtnPunto8() {
		return btnPunto8;
	}
	public JButton getBtnPunto7() {
		return btnPunto7;
	}
	public JButton getBtnPunto9() {
		return btnPunto9;
	}
	public JButton getBtnPunto12() {
		return btnPunto12;
	}
	public JButton getBtnPunto4() {
		return btnPunto4;
	}
	public JButton getBtnPunto14() {
		return btnPunto14;
	}
}
