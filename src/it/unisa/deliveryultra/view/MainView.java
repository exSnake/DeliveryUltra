package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnInserisciOrdine;
	private JButton btnGestisciOrdini;
	private JButton btnFiltraOrdini;
	private JButton btnCovidTracking;

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow]", "[][][]"));
		
		btnGestisciOrdini = new JButton("Gestisci Ordini");
		contentPane.add(btnGestisciOrdini, "cell 0 1,growx");
		
		setBtnInserisciOrdine(new JButton("Inserisci Ordine"));
		
		contentPane.add(getBtnInserisciOrdine(), "cell 1 1,growx");
		
		btnFiltraOrdini = new JButton("Filtra Ordini");
		contentPane.add(btnFiltraOrdini, "cell 0 2,grow");
		
		btnCovidTracking = new JButton("Covid Tracking");
		contentPane.add(btnCovidTracking, "cell 1 2,grow");
	}

	public JButton getBtnInserisciOrdine() {
		return btnInserisciOrdine;
	}

	public void setBtnInserisciOrdine(JButton btnInserisciOrdine) {
		this.btnInserisciOrdine = btnInserisciOrdine;
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
}
