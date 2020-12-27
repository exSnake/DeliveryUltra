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

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][]", "[][]"));
		
		btnGestisciOrdini = new JButton("Gestisci Ordini");
		contentPane.add(btnGestisciOrdini, "cell 0 1");
		
		setBtnInserisciOrdine(new JButton("Inserisci Ordine"));
		
		contentPane.add(getBtnInserisciOrdine(), "cell 1 1");
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
}
